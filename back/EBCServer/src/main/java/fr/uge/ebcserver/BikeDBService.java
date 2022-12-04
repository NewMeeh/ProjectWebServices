package fr.uge.ebcserver;

import fr.uge.ebcserver.exception.ForbiddenException;
import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import fr.uge.rmi.common.IUGEDB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public class BikeDBService extends UnicastRemoteObject implements IBikeDB {

    private final Logger logger = Logger.getLogger(BikeDBService.class.getName());
    private final IUGEDB ugeService;

    private long currentBikeId = 0;
    private final ArrayList<Bike> bikes = new ArrayList<>();

    protected BikeDBService() throws RemoteException, MalformedURLException, NotBoundException {
        ugeService = (IUGEDB) Naming.lookup("rmi://localhost:1099/ugeService");
    }

    private long checkValidAndGetId(String token) {
        try {
            long id = ugeService.isTokenValid(token);
            if (id < 0) throw new ForbiddenException(new IllegalArgumentException("Token is not valid"));
            return id;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBike(String token, BikeDB.BikeForm bike) {
        Objects.requireNonNull(bike);
        try {
            long userId = checkValidAndGetId(token);
            bikes.add(new Bike(userId, ugeService.getNameById(userId), currentBikeId++, bike.name(), bike.locationPrice(), bike.resalePrice(), bike.description()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        logger.info(bikes.toString());
    }

    public void remove(String token, long id) {
        checkValidAndGetId(token);
        bikes.remove(getBikeById(id));
    }

    public Collection<Bike> getBikes(String token) {
        checkValidAndGetId(token);
        return bikes;
    }

    private Bike getBikeById(long bikeId) {
        return bikes.stream().filter(bike -> bike.getId() == bikeId).findFirst().orElse(null);
    }

    public Bike getBikeById(String token, long id) {
        checkValidAndGetId(token);
        return getBikeById(id);
    }


    public int rent(String token, long bikeId) {
        Objects.requireNonNull(token);
        long userId = checkValidAndGetId(token);
        Bike bike = this.getBikeById(bikeId);

        if(!bike.isInCart()) {
            if (bike.isRented()) {
                bike.add(userId);
                logger.info(bike.toString());
                return 2;
            }
            bike.setUserId(userId);
            logger.info(bike.toString());
            return 1;
        }
        return 0;
    }

    public void rank(String token, BikeDB.RankForm rankForm) {
        Objects.requireNonNull(token);
        checkValidAndGetId(token);
        if (rankForm.grade() < 1 || rankForm.grade() > 5) throw new IllegalArgumentException("Grade must be between 1 and 5");
        var bike = this.getBikeById(rankForm.bikeId());
        bike.addGrade(rankForm.grade());
        logger.info(bike.toString());
    }
    
    public void turnIn(String token, long bikeId) {
        Objects.requireNonNull(token);
        checkValidAndGetId(token);
        var userId = checkValidAndGetId(token);
        var bike = this.getBikeById(bikeId);
        var size = bike.UnsetUserId(userId);
        if (size > 0) {
            System.out.println(userId + " mec c'est à ton tour."); // TODO Send notification to the client
            bike.setNextUserId();
            logger.info(bike.toString());
        }
    }

    @Override
    public Collection<Bike> getSellBikes() throws RemoteException {
        return bikes.stream().filter(b -> b.isUsed() && !b.isRented()).toList();
    }

    @Override
    public Bike getSellBikeById(long bikeId) throws RemoteException {

        var bikeToSell = bikes.stream().filter(b -> b.getBikeId() == bikeId).findFirst().orElse(null);
        if (bikeToSell != null && bikeToSell.isUsed() && !bikeToSell.isRented()) {
            return bikeToSell;
        }
        return null;
    }

    @Override
    public void unRent(long bikeId) throws RemoteException {
        getBikeById(bikeId).unRent();
    }

    @Override
    public void reRent(long bikeId) throws RemoteException {
        getBikeById(bikeId).reRent();
    }


    @Override
    public void remove(long bikeId) throws RemoteException {
        bikes.remove(bikeId);
    }

    public HashMap<String, String> getUserInfo(String token) {
        return ugeService.getUserInfo(token);
    }
    //TODO mes vélo que je loue, mes vélo que j'utilise actuellement, mes vélo que je vend
}


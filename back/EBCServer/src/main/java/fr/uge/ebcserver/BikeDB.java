package fr.uge.ebcserver;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/bikes")
public class BikeDB extends UnicastRemoteObject implements IBikeDB {

    Logger logger = Logger.getLogger(BikeDB.class.getName());
    private final HashMap<Bike, ArrayDeque<Long>> bikes = new HashMap<>();

    protected BikeDB() throws RemoteException {
    }

    private Optional<Bike> getBikeById(long id) {
        return bikes.keySet().stream().filter(b -> b.getId() == id).findFirst();
    }

    @PostMapping(value = "/add")
    @Override
    public void addBike(@RequestBody Bike bike) throws RemoteException {
        Objects.requireNonNull(bike);
        bikes.put(bike, new ArrayDeque<>());
        logger.info(bikes.toString());
    }
    @PostMapping(value = "/rent/{bikeId}/{userId}")
    @Override
    public int rent(@PathVariable String bikeId, @PathVariable String userId) throws RemoteException {
        Objects.requireNonNull(bikeId);
        Objects.requireNonNull(userId);
        var bike = this.getBikeById(Long.parseLong(bikeId)).get();

        if (bike.isRented()) {
            bike.add(Long.parseLong(userId));
            logger.info(bike.toString());
            return 2;
        }
        bike.setUserId(Long.parseLong(userId));
        logger.info(bike.toString());
        return 1;
    }

    @PostMapping(value = "/rank/{bikeId}/{grade}")
    @Override
    public void rank(@PathVariable String bikeId, @PathVariable String grade) throws RemoteException {
        Objects.requireNonNull(bikeId);
        Objects.requireNonNull(grade);
        if (Integer.getInteger(grade) < 1 || Integer.getInteger(grade) > 5) throw new IllegalArgumentException("Grade must be between 1 and 5");
        var bike = this.getBikeById(Long.parseLong(bikeId)).get();
        bike.addGrade(Integer.getInteger(grade));
        logger.info(bike.toString());
    }
    @PostMapping(value = "/turnIn/{bikeId}/{userId}")
    @Override
    public void turnIn(@PathVariable String bikeId, @PathVariable String userId) throws RemoteException {
        Objects.requireNonNull(bikeId);
        Objects.requireNonNull(userId);
        var bike = this.getBikeById(Long.parseLong(bikeId)).get();
        var size = bike.UnsetUserId(Long.parseLong(userId));
        if (size > 0) {
            System.out.println(userId + " mec c'est à ton tour."); // TODO Send notification to the client
            bike.setNextUserId();
        }
        logger.info(bike.toString());
    }

    @Override
    public Set<Bike> displayBikes() throws RemoteException {
        logger.info(bikes.toString());
        return bikes.keySet();
    }

    @PostMapping(value = "/{bikeId}")
    @Override
    public Bike displayBikeById(@PathVariable String bikeId) throws RemoteException {
        var bike = this.getBikeById(Long.parseLong(bikeId)).get();
        logger.info(bike.toString());
        return bike;
    }


}

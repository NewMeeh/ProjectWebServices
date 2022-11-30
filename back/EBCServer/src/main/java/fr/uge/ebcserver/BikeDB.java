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
    private final ArrayList<Bike> bikes = new ArrayList<>();

    protected BikeDB() throws RemoteException {
    }

    /*
    private Optional<Bike> getBikeById(long id) {
        return bikes.stream().filter(b -> b.getId() == id).findFirst();
    }
*/
    @PostMapping(value = "/add")
    @Override
    public void addBike(@RequestBody Bike bike) throws RemoteException {
        Objects.requireNonNull(bike);
        bikes.add(bike);
        logger.info(bikes.toString());
    }

    @DeleteMapping(value = "/remove")
    public void remove(@RequestBody Bike bike) throws RemoteException {
        Objects.requireNonNull(bike);
        bikes.remove(bike);
    }

    @GetMapping("/bikes")
    public Collection<Bike> getBikes() throws RemoteException {
        return bikes;
    }

    @GetMapping("bikes/{id}")
    public Bike getBikeById(@PathVariable long id) throws RemoteException {
        return bikes.stream().filter(bike -> bike.getId() == id).findFirst().orElse(null);
    }


    @PostMapping(value = "/rent/{bikeId}/{userId}")
    @Override
    public int rent(@PathVariable String bikeId, @PathVariable String userId) throws RemoteException {
        Objects.requireNonNull(bikeId);
        Objects.requireNonNull(userId);
        Bike bike = this.getBikeById(Long.parseLong(bikeId));

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
        var bike = this.getBikeById(Long.parseLong(bikeId));
        bike.addGrade(Integer.getInteger(grade));
        logger.info(bike.toString());
    }
    @PostMapping(value = "/turnIn/{bikeId}/{userId}")
    @Override
    public void turnIn(@PathVariable String bikeId, @PathVariable String userId) throws RemoteException {
        Objects.requireNonNull(bikeId);
        Objects.requireNonNull(userId);
        var bike = this.getBikeById(Long.parseLong(bikeId));
        var size = bike.UnsetUserId(Long.parseLong(userId));
        if (size > 0) {
            System.out.println(userId + " mec c'est à ton tour."); // TODO Send notification to the client
            bike.setNextUserId();
        }
        logger.info(bike.toString());
    }

    @Override
    public Collection<Bike> displayBikes() throws RemoteException {
        logger.info(bikes.toString());
        return bikes;
    }

    @PostMapping(value = "/{bikeId}")
    @Override
    public Bike displayBikeById(@PathVariable String bikeId) throws RemoteException {
        var bike = this.getBikeById(Long.parseLong(bikeId));
        logger.info(bike.toString());
        return bike;
    }


}

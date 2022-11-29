package fr.uge.ebcserver;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

@RestController
@RequestMapping("/bikes")
public class BikeDB extends UnicastRemoteObject implements IBikeDB {

    private final HashMap<Bike, ArrayDeque<Long>> bikes = new HashMap<>();

    protected BikeDB() throws RemoteException {
    }

    @PostMapping(value = "/add")
    @Override
    public void addBike(@RequestBody Bike bike) throws RemoteException {
        Objects.requireNonNull(bike);
        bikes.put(bike, new ArrayDeque<>());
    }

    @Override
    public int rent(Bike bike, long userId) throws RemoteException {
        Objects.requireNonNull(bike);
        var l = bikes.get(bike);

        if (bike.isRented()) {
            l.add(userId);
            return 2;
        }

        bike.setUserId(userId);
        return 1;
    }

    @Override
    public void rank(Bike bike, int grade) throws RemoteException {
        Objects.requireNonNull(bike);
        if (grade < 1 || grade > 5) throw new IllegalArgumentException("Grade must be between 1 and 5");

        bike.addGrade(grade);
    }

    @Override
    public void turnIn(Bike bike, long userId) throws RemoteException {
        Objects.requireNonNull(bike);
        bike.UnsetUserId(userId);

        var l = bikes.get(bike);
        if (l.size() > 0) {
            System.out.println(userId + " mec c'est à ton tour."); // TODO Send notification to the client
            bike.setUserId(l.poll());
        }
    }

    @Override
    public void displayBikes() {
        for (Bike bike:
             bikes.keySet()) {
            System.out.println(bike);
        }
    }

    public Optional<Bike> getBikebyId(long id) {
        return bikes.keySet().stream().filter(b -> b.getId() == id).findFirst();
    }
}

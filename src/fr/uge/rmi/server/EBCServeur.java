package fr.uge.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBike;

public class EBCServeur {
    public static void main(String args[]) {
        try
        {
            LocateRegistry.createRegistry(1099);
            IUGEDB ugedb = new UGEDB();
            IBikeDB bikeDB = new BikeDB();

            bikeDB.addBike(new Bike((long) ugedb.getId("Nader"), 1.30, 1));
            bikeDB.addBike(new Bike((long) ugedb.getId("Nader"), 2.50, 2));
            bikeDB.addBike(new Bike((long) ugedb.getId("Nader"), 8.80, 3));
            bikeDB.addBike(new Bike((long) ugedb.getId("Nader"), 11.80, 4));

            Naming.rebind("rmi://localhost:1099/EiffelBikeCorpService/ConnectPage", ugedb);
            Naming.rebind("rmi://localhost:1099/EiffelBikeCorpService/bikesPage", bikeDB);


        } catch(Exception e){
            System.out.println("Exception:" + e);
        }
    }
}
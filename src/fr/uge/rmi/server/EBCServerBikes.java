package fr.uge.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;
import fr.uge.rmi.common.IUGEDB;

public class EBCServerBikes {
    public static void main(String args[]) {
        try
        {
            LocateRegistry.createRegistry(1100);
            IUGEDB ugedb = (IUGEDB) Naming.lookup("UGEDB");

            IBikeDB bikeDB = new BikeDB();

            bikeDB.addBike(new Bike(ugedb.getId("Nader"), 1.30F, 1));
            bikeDB.addBike(new Bike(ugedb.getId("Nader"), 2.50F, 2));
            bikeDB.addBike(new Bike(ugedb.getId("Nader"), 8.80F, 3));
            bikeDB.addBike(new Bike(ugedb.getId("Nader"), 11.80F, 4));

            Naming.rebind("BikeDB", bikeDB);

        } catch(Exception e){
            System.out.println("Exception:" + e);
        }
    }
}
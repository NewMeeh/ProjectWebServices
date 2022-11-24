package fr.uge.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import fr.uge.rmi.common.Bike;
import fr.uge.rmi.common.IBikeDB;

public class EBCServer {
    public static void main(String args[]) {
        try
        {
            LocateRegistry.createRegistry(1099);
            var ugedb = new UGEDB();
            IBikeDB bikeDB = new BikeDB();

            bikeDB.addBike(new Bike(ugedb.getId("Nader").get(), 1.30F, 1));
            bikeDB.addBike(new Bike(ugedb.getId("Nader").get(), 2.50F, 2));
            bikeDB.addBike(new Bike(ugedb.getId("Nader").get(), 8.80F, 3));
            bikeDB.addBike(new Bike(ugedb.getId("Nader").get(), 11.80F, 4));

            Naming.rebind("rmi://localhost:1099/EiffelBikeCorpService/ConnectPage", ugedb);
            Naming.rebind("rmi://localhost:1099/EiffelBikeCorpService/bikesPage", bikeDB);

        } catch(Exception e){
            System.out.println("Exception:" + e);
        }
    }
}
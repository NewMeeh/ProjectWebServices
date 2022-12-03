package fr.uge.ebcserver;

import java.rmi.Naming;
import fr.uge.rmi.common.IUGEDB;

public class EBCServer {
    public static void main(String[] args) {
        try
        {
            IUGEDB ugeService = (IUGEDB) Naming.lookup("rmi://localhost:1099/ugeService");

            long a = ugeService.isTokenValid("024240a6-668b-46e5-af3a-9723fc6ef159");
            System.out.println(a);

/*
            LocateRegistry.createRegistry(1100);
            IBikeDB bikeDB = new BikeDB();
            Naming.rebind("BikeDB", bikeDB);
*/
        } catch(Exception e){
            System.out.println("Exception:" + e);
        }
    }
}
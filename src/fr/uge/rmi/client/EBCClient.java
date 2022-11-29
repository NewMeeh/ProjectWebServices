package fr.uge.rmi.client;

import java.rmi.Naming;
import fr.uge.rmi.common.IBikeDB;
import fr.uge.rmi.common.IUGEDB;

public class EBCClient {

    public static void main(String[] args) {
        try {
            IUGEDB ugedb = (IUGEDB) Naming.lookup("UGEDB");
            if(ugedb.login("Julien", "pwd")) {
                IBikeDB bikeDB = (IBikeDB) Naming.lookup("BikeDB");
                bikeDB.displayBikes();
                //TODO utiliser les methodes de IBikeDB.java
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
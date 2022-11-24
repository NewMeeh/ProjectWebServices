package fr.uge.rmi.client;

import java.rmi.Naming;
import fr.uge.rmi.common.IBikeDB;
import fr.uge.rmi.common.IUGEDB;

public class EBCClient {

    public static void main(String[] args) {
        try {
            IUGEDB ugedb = (IUGEDB) Naming.lookup("rmi://localhost:1099/EiffelBikeCorpService/ConnectPage");

            if(ugedb.login("Julien", "password")) {
                IBikeDB bikeDB = (IBikeDB) Naming.lookup("rmi://localhost:1099/EiffelBikeCorpService/bikesPage");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
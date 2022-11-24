package fr.uge.rmi.client;

import java.rmi.Naming;
import fr.uge.rmi.comon.IBikeDB;

public class EBCClient {

    public static void main(String[] args) {
        try {
            IUGEDB ugedb = (IUGEDB) Naming.lookup("rmi://localhost:1099/EiffelBikeCorpService/ConnectPage");

            if(ugedb.login()) {
                IBikeDB bikeDB = (IBikeDB) Naming.lookup("rmi://localhost:1099/EiffelBikeCorpService/bikesPage");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
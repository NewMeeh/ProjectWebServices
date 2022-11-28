package fr.uge.ugeserver;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class UGEServer {
    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(1099);
            UGEDB ugedb = new UGEDB();
            Naming.rebind("UGEDB", ugedb);
        } catch(Exception e){
            System.out.println("Exception:" + e);
        }
    }
}
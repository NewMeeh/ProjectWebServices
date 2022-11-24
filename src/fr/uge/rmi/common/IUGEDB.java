package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUGEDB extends Remote {

    boolean login(String name, String password) throws RemoteException;


}

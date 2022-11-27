package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;

public interface IUGEDB extends Remote {

    boolean login(String name, String password) throws RemoteException;
    long getId(String name) throws RemoteException;


}

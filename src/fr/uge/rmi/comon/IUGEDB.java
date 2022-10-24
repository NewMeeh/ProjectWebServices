package fr.uge.rmi.comon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUGEDB extends Remote {

    long login(String user, String password) throws RemoteException;


}

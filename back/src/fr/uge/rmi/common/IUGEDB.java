package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface IUGEDB extends Remote {
    long isTokenValid(String token) throws RemoteException;
    String getNameById(long id) throws RemoteException;

    HashMap<String, String> getUserInfo(String token);
}

package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUGEDB extends Remote {
    long isTokenValid(String token) throws RemoteException;
}

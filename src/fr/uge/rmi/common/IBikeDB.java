package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IBikeDB extends Remote {

    void addBike(Bike bike) throws RemoteException;
    int rent(Bike bike, long userId) throws RemoteException;
    void rank(Bike bike, int grade) throws RemoteException;
    void turnIn(Bike bike, long userId) throws RemoteException;


}

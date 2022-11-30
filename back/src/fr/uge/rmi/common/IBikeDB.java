package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;


public interface IBikeDB extends Remote {

    void addBike(Bike bike) throws RemoteException;
    int rent(String bikeId, String userId) throws RemoteException;
    void rank(String bikeId, String grade) throws RemoteException;
    void turnIn(String bikeId, String userId) throws RemoteException;
    Set<Bike> displayBikes() throws RemoteException;
    Bike displayBikeById(String bikeId) throws RemoteException;

}

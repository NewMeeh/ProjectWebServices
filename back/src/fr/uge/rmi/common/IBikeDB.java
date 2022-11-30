package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


public interface IBikeDB extends Remote {

    void addBike(Bike bike) throws RemoteException;
    int rent(String bikeId, String userId) throws RemoteException;
    void rank(String bikeId, String grade) throws RemoteException;
    void turnIn(String bikeId, String userId) throws RemoteException;
    Collection<Bike> displayBikes() throws RemoteException;
    Bike displayBikeById(String bikeId) throws RemoteException;

    //TODO Method
    /*toSell
    remove*/

}

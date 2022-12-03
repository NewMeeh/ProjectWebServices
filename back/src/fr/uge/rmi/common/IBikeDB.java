package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


public interface IBikeDB extends Remote {

    Collection<Bike> displayBikes() throws RemoteException;
    Bike displayBikeById(String bikeId) throws RemoteException;
    void remove(String bikeId) throws RemoteException;

    //TODO Method
    /*toSell*/

}

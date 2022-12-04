package fr.uge.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


public interface IBikeDB extends Remote {

    Collection<Bike> getSellBikes() throws RemoteException;
    Bike getSellBikeById(long bikeId) throws RemoteException;
    void remove(long bikeId) throws RemoteException;
    void unRent(long bikeId) throws RemoteException;
    public void reRent(long bikeId) throws RemoteException;

    //TODO Method
    /*toSell*/

}

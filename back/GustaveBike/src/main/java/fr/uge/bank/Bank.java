/**
 * Bank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.bank;

public interface Bank extends java.rmi.Remote {
    public boolean pay(java.lang.String cardNumber, java.lang.String expirationDate, java.lang.String cvv, float amount) throws java.rmi.RemoteException;
}

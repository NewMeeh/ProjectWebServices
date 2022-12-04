/**
 * Exchange.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.exchange;

public interface Exchange extends java.rmi.Remote {
    public float exchange(java.lang.String originCurrency, java.lang.String targetCurrency, float amount) throws java.rmi.RemoteException;
}

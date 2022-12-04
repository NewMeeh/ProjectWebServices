/**
 * ExchangeService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.exchange;

public interface ExchangeService extends javax.xml.rpc.Service {
    public java.lang.String getExchangeAddress();

    public fr.uge.exchange.Exchange getExchange() throws javax.xml.rpc.ServiceException;

    public fr.uge.exchange.Exchange getExchange(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

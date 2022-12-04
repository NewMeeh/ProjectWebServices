package fr.uge.exchange;

public class ExchangeProxy implements fr.uge.exchange.Exchange {
  private String _endpoint = null;
  private fr.uge.exchange.Exchange exchange = null;
  
  public ExchangeProxy() {
    _initExchangeProxy();
  }
  
  public ExchangeProxy(String endpoint) {
    _endpoint = endpoint;
    _initExchangeProxy();
  }
  
  private void _initExchangeProxy() {
    try {
      exchange = (new fr.uge.exchange.ExchangeServiceLocator()).getExchange();
      if (exchange != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)exchange)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)exchange)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (exchange != null)
      ((javax.xml.rpc.Stub)exchange)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.uge.exchange.Exchange getExchange() {
    if (exchange == null)
      _initExchangeProxy();
    return exchange;
  }
  
  public float exchange(java.lang.String originCurrency, java.lang.String targetCurrency, float amount) throws java.rmi.RemoteException{
    if (exchange == null)
      _initExchangeProxy();
    return exchange.exchange(originCurrency, targetCurrency, amount);
  }
  
  
}
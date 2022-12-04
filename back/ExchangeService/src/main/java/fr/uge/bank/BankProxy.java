package fr.uge.bank;

public class BankProxy implements fr.uge.bank.Bank {
  private String _endpoint = null;
  private fr.uge.bank.Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new fr.uge.bank.BankServiceLocator()).getBank();
      if (bank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bank != null)
      ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.uge.bank.Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public boolean pay(java.lang.String cardNumber, java.lang.String expirationDate, java.lang.String cvv, float amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.pay(cardNumber, expirationDate, cvv, amount);
  }
  
  
}
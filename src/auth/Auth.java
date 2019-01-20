package auth;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Auth extends Remote {

    String Authenticate(String _username, String _password) throws RemoteException;

}

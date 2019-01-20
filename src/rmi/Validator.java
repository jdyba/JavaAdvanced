package rmi;

import java.rmi.*;

public interface Validator extends Remote{

    String validate(String _UserName, String _Password)
            throws RemoteException;
}

package rmi;

import data.AllWorkers;

import java.rmi.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;

public class ValidatorServant extends UnicastRemoteObject implements Validator {

    // UnicastRemoteObject implements Remote interface
    private HashMap<String, String> memberMap;

    public ValidatorServant() throws RemoteException {
        memberMap = new HashMap<>();
        memberMap.put("John", "123");  // here are some users and password
        memberMap.put("Michal", "123");
    }

    private Map getMemberMap () {
        return memberMap;
    }

    public String  validate (String _UserName, String _Password)
            throws RemoteException{

        if(getMemberMap().containsKey(_UserName) &&
                getMemberMap().get(_UserName).equals(_Password))
        {
            return "Correct"; // early return if password is valid
        }

        return "Sorry invalid login information!";
    }

}

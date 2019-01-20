package auth;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Hashtable;
import java.util.List;

public class AuthServant extends UnicastRemoteObject implements Auth {

    private class Token {
        public String token;
        public String user;
    }

    private List<Token> tokens;

    public AuthServant () throws RemoteException
    {
        this.tokens = new ArrayList<>();
    }


    public String Authenticate(String _username, String _password) throws RemoteException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://82.145.72.13:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, _username);
        env.put(Context.SECURITY_CREDENTIALS, _password);


        try {
            DirContext ctx = new InitialDirContext(env);
            System.out.println("pass");
            ctx.close();

        } catch (NamingException e) {
            e.printStackTrace();
            return "false";
        }

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[64];
        random.nextBytes(bytes);
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        AddTkn(_username, token);
        return token;
        }

        public void AddTkn(String _username, String token)
        {
            Token myToken = new Token();
            myToken.user = _username;
            myToken.token = token;
        }

}

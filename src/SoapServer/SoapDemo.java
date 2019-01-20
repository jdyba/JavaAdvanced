package SoapServer;

import org.w3c.dom.Document;

import javax.jws.*;

@WebService
public interface SoapDemo {

    @WebMethod
    public String helloWorld();

    @WebMethod
    public String getDocument();
}


package SoapServer.SoapClient;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SoapDemo", targetNamespace = "http://SoapServer/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SoapDemo {


    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "helloWorld", targetNamespace = "http://SoapServer/", className = "SoapServer.SoapClient.HelloWorld")
    @ResponseWrapper(localName = "helloWorldResponse", targetNamespace = "http://SoapServer/", className = "SoapServer.SoapClient.HelloWorldResponse")
    @Action(input = "http://SoapServer/SoapDemo/helloWorldRequest", output = "http://SoapServer/SoapDemo/helloWorldResponse")
    public String helloWorld();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDocument", targetNamespace = "http://SoapServer/", className = "SoapServer.SoapClient.GetDocument")
    @ResponseWrapper(localName = "getDocumentResponse", targetNamespace = "http://SoapServer/", className = "SoapServer.SoapClient.GetDocumentResponse")
    @Action(input = "http://SoapServer/SoapDemo/getDocumentRequest", output = "http://SoapServer/SoapDemo/getDocumentResponse")
    public String getDocument();

}
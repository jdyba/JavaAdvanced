package SoapServer;

import data.AllWorkers;
import org.w3c.dom.Document;
import pojo.workerspackage.Director;
import pojo.workerspackage.Tradesman;
import pojo.workerspackage.Workers;

import javax.jws.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringWriter;
import java.util.List;

@WebService (endpointInterface = "SoapServer.SoapDemo")
public class SoapDemoImpl implements SoapDemo {

    @Override
    public String helloWorld() { return "Hello World"; }

    @Override
    public String getDocument() {

        List<Director> temp = AllWorkers.getDirectorsPojo();
        List<Tradesman> temp2 = AllWorkers.getTradesmanPojo();

        temp.add(new Director("91080517455", "Michal", "Matysik", "Dyrektor", 12, 12, 13, "12", 12));

        try {
            Workers workers = new Workers(temp, temp2);
            JAXBContext jaxbContext = JAXBContext.newInstance(Workers.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");


            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(workers, sw);
            String result = sw.toString();


            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

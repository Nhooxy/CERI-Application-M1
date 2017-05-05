package client;

import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

import webservice.stream.*;

public class Client {

    public static void main(String[] args) {
        WebServiceStream wsStream = null;
        String url = null;
        Sound test = null;
        String requete = null;

        try {
            URL wsdlUrl = new URL("http://127.0.0.1:8080/WebServiceStream/WebServiceStreamService?wsdl");
            QName qname = new QName("http://stream.webservice/", "WebServiceStreamService");
            Service service = Service.create(wsdlUrl, qname);
            wsStream = service.getPort(WebServiceStream.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (null != wsStream) {
            System.out.println(wsStream.bonjour("Pierre"));
            requete = "Pierre.ecouter.dazzle";
            url = wsStream.requeteClient(requete);
            System.out.println(url);
        }

        try {
            test = new Sound(url);
            test.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
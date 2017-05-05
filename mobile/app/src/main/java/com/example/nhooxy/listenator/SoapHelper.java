package com.example.nhooxy.listenator;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapHelper extends CommonLoginActivity {

    public static SoapObject soap(String property, String value) throws Exception {
        // Création de la requête SOAP
        SoapObject request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME);

        // Ajout de propriété: addProperty(nom de variable, valeur) ->
        // Le nom de la variable vient du fichier WSDL
        if (null != property) {
            request.addProperty(property, value);
        }
        // Toutes les données demandées sont mises dans une enveloppe.
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        // Préparation de la requête
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
        // on l'utilise pour savoir si nous voulons ou non utiliser
        // un paquet "sniffer" pour vérifier le message original (androidHttpTransport.requestDump)
        androidHttpTransport.debug = true;
        // Envoie de la requête
        androidHttpTransport.call(SOAP_ACTION, envelope);
        // Obtention du résultat
        SoapObject soapResult = (SoapObject) envelope.getResponse();

        return soapResult;
    }
}

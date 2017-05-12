package com.example.nhooxy.listenator.ws;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.UUID;


public class BARMarshalGuid implements Marshal {
    public Object readInstance(XmlPullParser parser, String namespace, String name, PropertyInfo expected) throws IOException, XmlPullParserException {
        return UUID.fromString(parser.nextText());
    }

    public void register(SoapSerializationEnvelope cm) {
        cm.addMapping(cm.xsd, "guid", UUID.class, this);
    }

    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
        writer.text(obj.toString());
    }
}

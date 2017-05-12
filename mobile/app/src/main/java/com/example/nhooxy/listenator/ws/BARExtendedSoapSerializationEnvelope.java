package com.example.nhooxy.listenator.ws;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.*;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Vector;

public class BARExtendedSoapSerializationEnvelope extends SoapSerializationEnvelope {
    static HashMap<String, Class> classNames = new HashMap<String, Class>();

    private static final String TYPE_LABEL = "type";

    public BARExtendedSoapSerializationEnvelope(int soapVersion) {
        super(soapVersion);
        implicitTypes = true;
        setAddAdornments(false);
        new BARMarshalGuid().register(this);
        new MarshalFloat().register(this);
    }

    @Override
    protected void writeProperty(XmlSerializer writer, Object obj, PropertyInfo type) throws IOException {
        if (obj == null || obj == SoapPrimitive.NullNilElement) {
            writer.attribute(xsi, version >= VER12 ? NIL_LABEL : NULL_LABEL, "true");
            return;
        }
        Object[] qName = getInfo(null, obj);
        if (!type.multiRef && qName[2] == null) {

            if (!implicitTypes || (obj.getClass() != type.type && !(obj instanceof Vector) && type.type != String.class)) {
                String xmlName = BARHelper.getKeyByValue(classNames, obj.getClass());
                if (xmlName != null) {
                    String[] parts = xmlName.split("\\^\\^");
                    String prefix = writer.getPrefix(parts[0], true);
                    writer.attribute(xsi, TYPE_LABEL, prefix + ":" + parts[1]);
                } else {
                    if (type.type instanceof String) {
                        String xsdPrefix = writer.getPrefix(xsd, true);
                        String myType = (String) type.type;
                        String[] parts = myType.split("\\^\\^");
                        if (parts.length == 2) {
                            xsdPrefix = writer.getPrefix(parts[0], true);
                            myType = parts[1];
                        }

                        writer.attribute(xsi, TYPE_LABEL, xsdPrefix + ":" + myType);
                    } else {
                        String prefix = writer.getPrefix(type.namespace, true);
                        writer.attribute(xsi, TYPE_LABEL, prefix + ":" + obj.getClass().getSimpleName());
                    }

                }
            }
            //super.writeProperty(writer,obj,type);

            writeElement(writer, obj, type, qName[QNAME_MARSHAL]);
        } else {
            super.writeProperty(writer, obj, type);
        }
    }

    public Object get(Object soap, Class cl, boolean typeFromClass) {
        if (soap == null) {
            return null;
        }
        try {
            if (soap instanceof Vector || typeFromClass) {
                Constructor ctor = cl.getConstructor(Object.class, BARExtendedSoapSerializationEnvelope.class);
                return ctor.newInstance(soap, this);
            }
            {
                if (soap instanceof SoapObject) {
                    if (cl == SoapObject.class) {
                        return soap;
                    }
                    String key = String.format("%s^^%s", ((SoapObject) soap).getNamespace(), ((SoapObject) soap).getName());
                    if (classNames.containsKey(key)) {
                        cl = classNames.get(key);
                    }
                }
                Constructor ctor = cl.getConstructor(Object.class, BARExtendedSoapSerializationEnvelope.class);
                return ctor.newInstance(soap, this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
} 

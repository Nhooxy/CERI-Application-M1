package com.example.nhooxy.listenator.ws;

import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;
import java.util.List;


public class BARWebServiceStreamPortBinding {
    interface BARIWcfMethod {
        BARExtendedSoapSerializationEnvelope CreateSoapEnvelope() throws Exception;

        Object ProcessResult(BARExtendedSoapSerializationEnvelope __envelope, Object result) throws Exception;
    }

    public String url = "REPLACE_WITH_ACTUAL_URL";

    public int timeOut = 60000;
    public List<HeaderProperty> httpHeaders;
    public boolean enableLogging;

    public BARWebServiceStreamPortBinding(String url) {
        this.url = url;
    }

    protected Transport createTransport() {
        try {
            java.net.URI uri = new java.net.URI(url);
            if (uri.getScheme().equalsIgnoreCase("https")) {
                int port = uri.getPort() > 0 ? uri.getPort() : 443;
                return new HttpsTransportSE(uri.getHost(), port, uri.getPath(), timeOut);
            } else {
                return new HttpTransportSE(url, timeOut);
            }

        } catch (java.net.URISyntaxException e) {
        }
        return null;
    }

    protected BARExtendedSoapSerializationEnvelope createEnvelope() {
        BARExtendedSoapSerializationEnvelope envelope = new BARExtendedSoapSerializationEnvelope(BARExtendedSoapSerializationEnvelope.VER11);
        return envelope;
    }

    protected List sendRequest(String methodName, BARExtendedSoapSerializationEnvelope envelope, Transport transport) throws Exception {
        return transport.call(methodName, envelope, httpHeaders);
    }

    public String bonjour(final String arg0) throws Exception {
        return (String) execute(new BARIWcfMethod() {
            @Override
            public BARExtendedSoapSerializationEnvelope CreateSoapEnvelope() {
                BARExtendedSoapSerializationEnvelope __envelope = createEnvelope();
                SoapObject __soapReq = new SoapObject("http://stream.webservice/", "bonjour");
                __envelope.setOutputSoapObject(__soapReq);

                PropertyInfo __info = null;
                __info = new PropertyInfo();
                __info.namespace = "";
                __info.name = "arg0";
                __info.type = PropertyInfo.STRING_CLASS;
                __info.setValue(arg0 != null ? arg0 : SoapPrimitive.NullSkip);
                __soapReq.addProperty(__info);
                return __envelope;
            }

            @Override
            public Object ProcessResult(BARExtendedSoapSerializationEnvelope __envelope, Object __result) throws Exception {
                SoapObject __soap = (SoapObject) __result;
                Object obj = __soap.getProperty("return");
                if (obj != null && obj.getClass().equals(SoapPrimitive.class)) {
                    SoapPrimitive j = (SoapPrimitive) obj;
                    return j.toString();
                } else if (obj != null && obj instanceof String) {
                    return (String) obj;
                }
                return null;
            }
        }, "");
    }

    public String requeteClient(final String arg0) throws Exception {
        return (String) execute(new BARIWcfMethod() {
            @Override
            public BARExtendedSoapSerializationEnvelope CreateSoapEnvelope() {
                BARExtendedSoapSerializationEnvelope __envelope = createEnvelope();
                SoapObject __soapReq = new SoapObject("http://stream.webservice/", "requeteClient");
                __envelope.setOutputSoapObject(__soapReq);

                PropertyInfo __info = null;
                __info = new PropertyInfo();
                __info.namespace = "";
                __info.name = "arg0";
                __info.type = PropertyInfo.STRING_CLASS;
                __info.setValue(arg0 != null ? arg0 : SoapPrimitive.NullSkip);
                __soapReq.addProperty(__info);
                return __envelope;
            }

            @Override
            public Object ProcessResult(BARExtendedSoapSerializationEnvelope __envelope, Object __result) throws Exception {
                SoapObject __soap = (SoapObject) __result;
                Object obj = __soap.getProperty("return");
                if (obj != null && obj.getClass().equals(SoapPrimitive.class)) {
                    SoapPrimitive j = (SoapPrimitive) obj;
                    return j.toString();
                } else if (obj != null && obj instanceof String) {
                    return (String) obj;
                }
                return null;
            }
        }, "");
    }

    protected Object execute(BARIWcfMethod wcfMethod, String methodName) throws Exception {
        Transport __httpTransport = createTransport();
        __httpTransport.debug = enableLogging;
        BARExtendedSoapSerializationEnvelope __envelope = wcfMethod.CreateSoapEnvelope();
        try {
            sendRequest(methodName, __envelope, __httpTransport);

        } finally {
            if (__httpTransport.debug) {
                if (__httpTransport.requestDump != null) {
                    android.util.Log.i("requestDump", __httpTransport.requestDump);

                }
                if (__httpTransport.responseDump != null) {
                    android.util.Log.i("responseDump", __httpTransport.responseDump);
                }
            }
        }
        Object __retObj = __envelope.bodyIn;
        if (__retObj instanceof org.ksoap2.SoapFault) {
            org.ksoap2.SoapFault __fault = (org.ksoap2.SoapFault) __retObj;
            throw convertToException(__fault, __envelope);
        } else {
            return wcfMethod.ProcessResult(__envelope, __retObj);
        }
    }

    Exception convertToException(org.ksoap2.SoapFault fault, BARExtendedSoapSerializationEnvelope envelope) {

        return new Exception(fault.faultstring);
    }
}

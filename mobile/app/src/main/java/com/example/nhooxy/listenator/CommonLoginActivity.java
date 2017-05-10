package com.example.nhooxy.listenator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.nhooxy.listenator.ws.BARWebServiceStreamPortBinding;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CommonLoginActivity extends Activity {

    protected final String EXTRA_LOGIN = "user_login";

    protected MediaPlayer mediaPlayer;
    protected static final int REQUEST_CODE = 1234;
    protected ListView wordsList;

    protected static String SOAP_URL = "";

    protected final static String SOAP_METHOD_NAME = "requeteClient";
    protected final static String SOAP_NAMESPACE = "http://stream.webservice/";
    protected final static String SOAP_ACTION = SOAP_NAMESPACE + SOAP_METHOD_NAME;

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //changer activity
                    return true;
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_list:
                    return true;
            }
            return false;
        }
    };

    /**
     * Permet d'afficher une alerte avec la string passe en parametre.
     *
     * @param str
     */
    public void showAlert(String str) {
        new AlertDialog.Builder(CommonLoginActivity.this)
                .setTitle("Alerte")
                .setMessage(str)
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public String callServiceSoap(final String param) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        BARWebServiceStreamPortBinding service = new BARWebServiceStreamPortBinding(SOAP_URL);

        try {
             return service.requeteClient(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

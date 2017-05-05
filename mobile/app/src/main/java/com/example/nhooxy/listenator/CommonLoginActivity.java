package com.example.nhooxy.listenator;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.ListView;

public class CommonLoginActivity extends Activity {

    protected final String EXTRA_LOGIN = "user_login";

    protected MediaPlayer mediaPlayer;
    protected static final int REQUEST_CODE = 1234;
    protected ListView wordsList;

    protected static String SOAP_URL = "";
    protected final static String SOAP_ACTION = "WebServiceStreamService";
    protected final static String SOAP_METHOD_NAME = "requeteClient";
    protected final static String SOAP_NAMESPACE = "http://stream.webservice/";

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
}

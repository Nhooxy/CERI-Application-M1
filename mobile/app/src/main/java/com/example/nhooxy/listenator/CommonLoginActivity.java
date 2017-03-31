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
    protected static final String URL_WEB_SERVICE = "http://MonWebService.com/search?q=";
    //    protected static final String URL_SERVER_STREAM = "http://192.168.1.18:8090/dazzle.mp3";
    protected static final String URL_SERVER_STREAM = "http://10.120.10.85:8090/dazzle.mp3";
    protected ListView wordsList;

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

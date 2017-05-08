package com.example.nhooxy.listenator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginMainActivity extends CommonLoginActivity {

    private List<ResolveInfo> activities;

    /**
     * Au début de l'initialisation de l'app
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Intent intent = getIntent();
        TextView loginDisplay = (TextView) findViewById(R.id.email_display);

        if (null != intent) {
            loginDisplay.setText(intent.getStringExtra(EXTRA_LOGIN));
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button speakButton = (Button) findViewById(R.id.speakButton);

        wordsList = (ListView) findViewById(R.id.list);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
                0
        );
        if (0 == activities.size()) {
            speakButton.setEnabled(false);
            speakButton.setText(R.string.pasdetranslateur);
        }

        //todo here !
        // faire reconnaissance
        //envoyer le string de reconnaissance
        // activities.toString().replaceAll(" ", ".")
        //lire le flux avec lecture();
        //this.lecture(URL_SERVER_STREAM);

    }

    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v) {
        // todo remove (on enleve la reconnaisance pour accellerr les test
        String result = "ecouter dazzle";
        String url = WSListenator(UUID.randomUUID().toString() + "." + result.replaceAll(" ", "."));
        showAlert(url);
        // todo fin remove
        //startVoiceRecognitionActivity();
    }

    /**
     * Handle the action of the button being clicked
     */
    public void urlButtonClicked(View v) {
        final EditText urlText = (EditText) findViewById(R.id.editText);
        // pour les tests adresse par defaut.
        SOAP_URL = urlText.getText().toString().equals("") ? "http://192.168.1.18:8080/WebServiceStream/WebServiceStreamService?wsdl"
                : "http://" + urlText.getText().toString() + ":8080/WebServiceStream/WebServiceStreamService?wsdl";
        showAlert(SOAP_URL);
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.reconnaisance);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            this.wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));

            String result = matches.get(0);
            String url = WSListenator(UUID.randomUUID().toString() + "." + result.replaceAll(" ", "."));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Permet d'appeler le web service et d'avoir l'url en reponse.
     * @param reconnaissance
     * @return
     */
    public String WSListenator(String reconnaissance) {
        try {
            showAlert("test");
            return SoapHelper.soap("requeteClient", reconnaissance).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Web Service non atteint.";
    }

    // lecteuer
    public void lecture(String url) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(LoginMainActivity.this, Uri.parse(url));
            mediaPlayer.start();

//            Button btStart = (Button) findViewById(R.id.start);
//            Button btStop = (Button) findViewById(R.id.stop);
//            Button btPause = (Button) findViewById(R.id.pause);
//
//            btStart.setOnClickListener(new Button.OnClickListener() {
//                public void onClick(View v) {
//                    try {
//                        mediaPlayer.start();
//                    } catch (Exception e) {
//                        texthaut.setText("erreur " + e.getMessage());
//                    }
//
//                }
//            });
//
//            btStop.setOnClickListener(new Button.OnClickListener() {
//                public void onClick(View v) {
//                    try {
//                        mediaPlayer.stop();
//                        mediaPlayer.prepare();
//                    } catch (Exception e) {
//                        texthaut.setText("erreur " + e.getMessage());
//                    }
//
//                }
//            });
//
//            btPause.setOnClickListener(new Button.OnClickListener() {
//                public void onClick(View v) {
//                    try {
//                        mediaPlayer.pause();
//                    } catch (Exception e) {
//                        texthaut.setText("erreur " + e.getMessage());
//                    }
//
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

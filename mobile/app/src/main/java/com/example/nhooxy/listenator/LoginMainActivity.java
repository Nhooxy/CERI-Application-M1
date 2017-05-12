package com.example.nhooxy.listenator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMainActivity extends CommonLoginActivity {

    private String RECONNAISSANCE;

    private Player player;

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

        player = new Player(getApplicationContext());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button speakButton = (Button) findViewById(R.id.speakButton);

        wordsList = (ListView) findViewById(R.id.list);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
                0
        );
        if (0 == activities.size()) {
            speakButton.setEnabled(false);
            speakButton.setText(R.string.pasdetranslateur);
        }
    }

    /**
     * Quand on presse le bouton "record" pour la reconnaissance vocal.
     */
    public void speakButtonClicked(View v) {
        startVoiceRecognitionActivity();
    }

    /**
     * Quand on click sur le bouton pour entrer 'url du WS.
     */
    public void urlButtonClicked(View v) {
        final EditText urlText = (EditText) findViewById(R.id.editText);
        // pour les tests adresse par defaut.
        //SOAP_URL = urlText.getText().toString().equals("") ? "http://192.168.1.18:8080/WebServiceStream/WebServiceStreamService?wsdl"
        SOAP_URL = urlText.getText().toString().equals("") ? "http://10.120.12.132:8080/WebServiceStream/WebServiceStreamService?wsdl"
                : "http://" + urlText.getText().toString() + ":8080/WebServiceStream/WebServiceStreamService?wsdl";
        showAlert(SOAP_URL);
    }

    /**
     * Permet de demarrer le reconnaisance vocal.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.reconnaisance);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Permet d'analyser la reconnaisance vocal une fois qu'elle est faites.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            this.wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));

            RECONNAISSANCE = matches.get(0);
            WSListenator();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Permet d'appeler le web service et d'avoir l'url en reponse.
     *
     * @return
     */
    public String WSListenator() {
        RECONNAISSANCE = RECONNAISSANCE.toLowerCase();
        Pattern patternPause = Pattern.compile(Commande.PAUSE.getRegex(), Pattern.CASE_INSENSITIVE);
        Matcher matcherPause = patternPause.matcher(RECONNAISSANCE);
        Pattern patternStop = Pattern.compile(Commande.STOP.getRegex(), Pattern.CASE_INSENSITIVE);
        Matcher matcherStop = patternStop.matcher(RECONNAISSANCE);
        if (matcherPause.matches()) {
            player.pause();
        }
        if (matcherStop.matches()) {
            player.stop();
            String stop = this.callServiceSoap(UUID.randomUUID().toString() + ".stop");
            showAlert(stop);
            return stop;
        }
        try {
            String url = this.callServiceSoap(UUID.randomUUID().toString() + "." + RECONNAISSANCE.replaceAll(" ", "."));
            if (null != url) {
                showAlert(" Veuillez patienter ");
                player.play(url);
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showAlert(" Probleme lors de la communication avec le service... ");

        return null;
    }
}

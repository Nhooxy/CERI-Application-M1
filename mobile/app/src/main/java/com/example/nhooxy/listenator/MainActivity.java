package com.example.nhooxy.listenator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Ecran de connection.
 */
public class MainActivity extends Activity {

    final String EXTRA_LOGIN = "user_login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText login = (EditText) findViewById(R.id.user_email);
        final EditText pass = (EditText) findViewById(R.id.user_password);
        final Button loginButton = (Button) findViewById(R.id.connect);
        final Button createButton = (Button) findViewById(R.id.create_account);

        Intent intent = getIntent();
        if (null != intent) {
            login.setText(intent.getStringExtra(EXTRA_LOGIN));
        }

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginTxt = login.getText().toString();
                final String passTxt = pass.getText().toString();

                if (loginTxt.equals("") || passTxt.equals("")) {
                    Toast.makeText(MainActivity.this,
                            R.string.email_or_password_empty,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = emailPattern.matcher(loginTxt);
                if (!m.matches()) {
                    Toast.makeText(
                            MainActivity.this,
                            R.string.email_format_error,
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // todo WS id
                // Ici on change d'activité parce que les identifiant sont valide donc ,
                // vérifier via WS si les id sont valide =)
                Intent intent = new Intent(
                        MainActivity.this,
                        LoginMainActivity.class
                );

                intent.putExtra(EXTRA_LOGIN, loginTxt);
                startActivity(intent);
            }
        });

        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        CreateUserActivity.class
                );

                startActivity(intent);
            }
        });
    }
}

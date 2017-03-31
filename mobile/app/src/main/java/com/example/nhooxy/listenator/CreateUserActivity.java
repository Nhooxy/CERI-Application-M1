package com.example.nhooxy.listenator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ecran de création de compte.
 */
public class CreateUserActivity extends Activity {

    final String EXTRA_LOGIN = "user_login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.create_user_main);
        super.onCreate(savedInstanceState);
        final EditText login = (EditText) findViewById(R.id.user_email);
        final EditText pass = (EditText) findViewById(R.id.user_password);
        final Button createButton = (Button) findViewById(R.id.connect);

        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginTxt = login.getText().toString();
                final String passTxt = pass.getText().toString();

                if (loginTxt.equals("") || passTxt.equals("")) {
                    Toast.makeText(CreateUserActivity.this,
                            R.string.email_or_password_empty,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = emailPattern.matcher(loginTxt);
                if (!m.matches()) {
                    Toast.makeText(
                            CreateUserActivity.this,
                            R.string.email_format_error,
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                // todo WS id
                // Ici on creer le compte si c'est ok ok , sinon toast avec erreur communication.
                //check
                boolean check = false;

                //pas ok
                /*
                if (!check) {
                    Toast.makeText(
                            CreateUserActivity.this,
                            R.string.connection_create,
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                */

                Intent intent = new Intent(
                        CreateUserActivity.this,
                        MainActivity.class
                );

                intent.putExtra(EXTRA_LOGIN, loginTxt);
                startActivity(intent);
            }
        });
    }
}

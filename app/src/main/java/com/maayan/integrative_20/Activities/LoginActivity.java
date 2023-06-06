package com.maayan.integrative_20.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.maayan.integrative_20.R;
import com.maayan.integrative_20.Utils.CONSTANTS;
import com.maayan.integrative_20.Utils.UserOperations;


public class LoginActivity extends AppCompatActivity {

    private EditText getUsername;
    private EditText getEmail;
    private Button submit;
    private TextView createNew;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        getUsername = findViewById(R.id.TXT_username);
        getEmail = findViewById(R.id.TXT_email);
        submit = findViewById(R.id.BTN_login);
        createNew = findViewById(R.id.LBL_dont_have_account);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String email = getEmail.getText().toString();
                getUser1(CONSTANTS.SUPERAPPNAME, email);

                Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);

                // Start the new Intent
                startActivity(newIntent);


            }
        });

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(LoginActivity.this, SignUpActivity.class);

                // Start the new Intent
                startActivity(newIntent);


            }
        });


    }


    private void getUser1(String superapp, String email) {
        UserOperations userOperations = new UserOperations();
        userOperations.getUser(superapp, email);
    }


}

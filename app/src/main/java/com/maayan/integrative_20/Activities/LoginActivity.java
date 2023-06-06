package com.maayan.integrative_20.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Boundaries.UserId;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.Model.UserRole;
import com.maayan.integrative_20.R;
import com.maayan.integrative_20.Utils.CONSTANTS;
import com.maayan.integrative_20.Utils.UserOperations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private void changeUserDetails(String superapp, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.22:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface api_interface = retrofit.create(API_Interface.class);

        UserBoundary updatedUser = new UserBoundary(new UserId("maayan@gmail.com"), UserRole.SUPERAPP_USER.toString(), "NEWNAME", "PIC123");
        Call<Void> call = api_interface.updateUserDetails(superapp, email, updatedUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }


    private void getUser1(String superapp, String email) {
        UserOperations userOperations = new UserOperations();
        userOperations.getUser(superapp, email);
    }


    private void getUser(String superapp, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.25.249:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<UserBoundary> currentUser = api_interface.loginValidUserAndRetrieveUserDetails(superapp, email);
        currentUser.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                if (response.isSuccessful()) {
                    // object created successfully
                    UserBoundary user = response.body();
                } else {
                    // error creating object
                    if (response.errorBody() != null) {
                    }
                }
            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
            }
        });

    }

}

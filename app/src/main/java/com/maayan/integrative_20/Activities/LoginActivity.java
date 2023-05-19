package com.maayan.integrative_20.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity  extends AppCompatActivity {

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
                //getUsers("2023b.Liran.Sorokin-Student4U", email);
                //only test
                changeUserDetails("2023b.Liran.Sorokin-Student4U", email);
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
     //   private UserId userId;
     //   private String role;
     //   private String username;
     //   private String avatar;
        UserBoundary updatedUser = new UserBoundary(new UserId("maayan@gmail.com"), UserRole.SUPERAPP_USER.toString(), "NEWNAME", "PIC123");
        Call <Void> call =  api_interface.updateUserDetails(superapp, email, updatedUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("XX1", "we are here " + response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("XX1", "oh man " + t.getMessage());
            }
        });
    }

    private void getUsers(String superapp, String email) {
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
                    Log.d("XX1", "Object created successfully: " + response.message());
                    UserBoundary user = response.body();
                    Log.d("XX1", response.body().toString());
                } else {
                    // error creating object
                    Log.d("XX1", "Response code: " + response.code());
                    if (response.errorBody() != null) {
                        Log.d("XX1", "Error message: " + response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                Log.d("XX1", "oh no :( " + t.getMessage());
            }
        });


}


}

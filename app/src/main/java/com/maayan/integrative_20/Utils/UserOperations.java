package com.maayan.integrative_20.Utils;

import android.util.Log;

import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.UserRole;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOperations {

    private RetrofitClient retrofitClient;
    private CurrentUser currentUser;
    public UserOperations() {
        retrofitClient = new RetrofitClient(CONSTANTS.retroFitIP);
        currentUser = CurrentUser.getInstance();
    }

    public Call<UserBoundary>  createNewUser(String username, String avatar, String email) throws IOException {

        NewUserBoundary newUser = new NewUserBoundary(UserRole.SUPERAPP_USER.toString(), username, avatar, email);
        Log.d("XX1222", newUser.toString());
        Call<UserBoundary> user = retrofitClient.getApi_interface().createANewUser(newUser);
        Log.d("XX124", user.request().toString());

    return user;

    }

    public void getUser(String superapp, String email) {

        Call<UserBoundary> currentUser = retrofitClient.getApi_interface().loginValidUserAndRetrieveUserDetails(superapp, email);
        currentUser.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                if (response.isSuccessful()) {
                    // object created successfully
                    Log.d("XX1Z", "Object retrieved successfully: " + response.message());
                    UserBoundary user = response.body();
                    Log.d("XX1Z", response.body().toString());
                } else {
                    // error creating object
                    Log.d("XX1Z", "Response code: " + response.code());
                    if (response.errorBody() != null) {
                        Log.d("XX1Z", "Error message: " + response.errorBody().toString());
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

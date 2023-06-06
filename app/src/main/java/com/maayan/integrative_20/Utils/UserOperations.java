package com.maayan.integrative_20.Utils;


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

    public Call<UserBoundary> createNewUser(String username, String avatar, String email) throws IOException {

        NewUserBoundary newUser = new NewUserBoundary(UserRole.SUPERAPP_USER.toString(), username, avatar, email);
        Call<UserBoundary> user = retrofitClient.getApi_interface().createANewUser(newUser);

        return user;

    }

    public void getUser(String superapp, String email) {

        Call<UserBoundary> currentUser1 = retrofitClient.getApi_interface().loginValidUserAndRetrieveUserDetails(superapp, email);
        currentUser1.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                if (response.isSuccessful()) {
                    // object created successfully
                    UserBoundary user = response.body();

                    currentUser.setUserId(user.getUserId());
                    currentUser.setChosenAvatar(user.getAvatar());
                    currentUser.setChosenUsername(user.getUsername());
                    currentUser.setChosenRole(user.getRole());
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

package com.maayan.integrative_20.Interfaces;

import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Model.UserEntity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Interface {
    /*
        @GET("/superapp/admin/miniapp/hi")//GET request
        //what is written after / in URL
    Call<List<MiniappCommandEn>> getMiniAppCommand();
     */


    @GET("/superapp/admin/users")
    Call<List<UserEntity>> getAllUsers();

    @POST("/superapp/objects")
    Call<ResponseBody> createAnObject(@Body String requestBody);

    @POST("/superapp/users")
    Call<UserBoundary> createANewUser(@Body NewUserBoundary newUser);
}

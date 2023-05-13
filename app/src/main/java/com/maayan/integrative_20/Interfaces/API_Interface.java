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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API_Interface {
    /*
        @GET("/superapp/admin/miniapp/hi")//GET request
        //what is written after / in URL
    Call<List<MiniappCommandEn>> getMiniAppCommand();
     */


    @GET("/superapp/admin/users")//
    Call<UserBoundary[]> getAllUsers();

    @POST("/superapp/objects")
    Call<ResponseBody> createAnObject(@Body String requestBody);



    @POST("/superapp/users")//
    Call<UserBoundary> createANewUser(@Body NewUserBoundary newUser);

    @GET("/superapp/users/login/{superapp}/{email}")//
    Call<UserBoundary> loginValidUserAndRetrieveUserDetails(@Path("superapp") String superapp, @Path("email") String email);

    @PUT("/superapp/users/{superapp}/{userEmail}")
    void updateUserDetails(@Path("superapp") String superapp, @Path("userEmail") String email);
}

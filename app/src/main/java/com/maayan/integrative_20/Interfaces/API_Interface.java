package com.maayan.integrative_20.Interfaces;

import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.ObjectBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;

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
    Call<ObjectBoundary> createAnObject(@Body Object objectBoundary);

    @PUT("/superapp/objects/{superapp}/{internalObjectId}")
    Call<Void> updateAnObject(@Path("superapp") String superapp, @Path("internalObjectId") String objectId, @Body ObjectBoundary objectBoundary);

    @GET("/superapp/objects{superapp}/{internalObjectId}")
    Call<ObjectBoundary> retrieveObject(@Path("superapp") String superapp, @Path("internalObjectId") String objectId);

    @GET("/superapp/objects")
    Call<ObjectBoundary[]> getAllObjects();



    @POST("/superapp/users")//working
    Call<UserBoundary> createANewUser(@Body NewUserBoundary newUser);

    @GET("/superapp/users/login/{superapp}/{email}")//working
    Call<UserBoundary> loginValidUserAndRetrieveUserDetails(@Path("superapp") String superapp, @Path("email") String email);

    @PUT("/superapp/users/{superapp}/{userEmail}")//working
    Call<Void> updateUserDetails(@Path("superapp") String superapp, @Path("userEmail") String email, @Body UserBoundary userBoundary);


}

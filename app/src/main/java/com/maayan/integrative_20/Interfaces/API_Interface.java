package com.maayan.integrative_20.Interfaces;

import com.maayan.integrative_20.Boundaries.MiniAppCommandBoundary;
import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.SuperAppObjectIdBoundary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API_Interface {
    /*
        @GET("/superapp/admin/miniapp/hi")//GET request
        //what is written after / in URL
    Call<List<MiniappCommandEn>> getMiniAppCommand();
     */


    @GET("/superapp/admin/users")//
    Call<UserBoundary[]> getAllUsers();



    @POST("/superapp/objects")
    Call<SuperAppObjectBoundary> createAnObject(@Body Object objectBoundary);

    //
    @PUT("/superapp/objects/{superapp}/{InternalObjectId}")
    Call<Void> updateAnObject(@Path("superapp") String superapp, @Path("InternalObjectId") String internalObjectId, @Query("userSuperapp") String userSuperapp, @Query("userEmail") String userEmail, @Body SuperAppObjectBoundary updateBoundary);

//
    @GET("/superapp/objects/{superapp}/{internalObjectId}")
    Call<SuperAppObjectBoundary> retrieveObject(@Path("superapp") String superapp, @Path("internalObjectId") String objectId, @Query("userSuperapp") String userSuperapp, @Query("email") String email);
//
    @GET("/superapp/objects")
    Call<SuperAppObjectBoundary[]> getAllObjects(@Query("userSuperapp") String userSuperapp, @Query("userEmail") String userEmail, @Query("size") int size, @Query("page") int page);

    @GET("/superapp/objects/search/byType/{type}")
    Call<SuperAppObjectBoundary[]> searchObjectsByType(@Path("type") String type, @Query("userSuperapp") String userSuperapp, @Query("userEmail") String userEmail, @Query("size") int size, @Query("page") int page);

    //
    @PUT("/superapp/objects/{superapp}/{internalObjectId}/children")
    Call<Void> BindAnExistingObjectToExistingChildObject(@Path("superapp") String superapp, @Path("internalObjectId") String internalObjectId, @Body SuperAppObjectIdBoundary superAppObjectIdBoundary, @Query("userSuperapp") String userSuperapp, @Query("userEmail") String userEmail);

    //
    @GET("/superapp/objects/{superapp}/{internalObjectId}/children")
    Call<SuperAppObjectBoundary[]> getAllChildrenOfAnExistingObject(@Path("superapp") String superapp, @Path("internalObjectId") String internalObjectId, @Query("userSuperapp") String userSuperapp, @Query("email") String email, @Query("size") int size, @Query("page") int page);
    //
    @GET("/superapp/objects/{superapp}/{internalObjectId}/parents")
    Call<SuperAppObjectBoundary[]> getAnArrayWithObjectParent(@Path("superapp") String superapp, @Path("internalObjectId") String internalObjectId, @Query("userSuperapp") String userSuperapp, @Query("email") String email, @Query("size") int size, @Query("page") int page);


    @POST("/superapp/users")
    Call<UserBoundary> createANewUser(@Body NewUserBoundary newUser);

    @GET("/superapp/users/login/{superapp}/{email}")//working
    Call<UserBoundary> loginValidUserAndRetrieveUserDetails(@Path("superapp") String superapp, @Path("email") String email);

    @PUT("/superapp/users/{superapp}/{userEmail}")//working
    Call<Void> updateUserDetails(@Path("superapp") String superapp, @Path("userEmail") String email, @Body UserBoundary userBoundary);


    @POST("/superapp/miniapp/{miniAppName}")
    Call<Object> invokeMiniAppCommand(@Path("miniAppName") String miniAppName, @Body MiniAppCommandBoundary miniAppCommandBoundary, @Query("async") Boolean asyncFlag);
}

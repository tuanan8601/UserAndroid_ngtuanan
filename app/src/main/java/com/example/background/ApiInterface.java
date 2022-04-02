package com.example.background;

import com.example.background.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUsersByID(@Path("id") int id);

    String token = "47ebddbbe403f8d6cd99f89268c7ed16987befd7822ff6bd1a02df5c3d6db9d7";
    @POST("users?access-token=" + token)
    Call<User> addUser(@Body() User user);

    @PUT("users/{id}?access-token=" + token)
    Call<User> updateUserByID(@Path("id") int id,@Body() User user);

    @DELETE("users/{id}?access-token="+ token)
    Call<User> deleteUsersByID(@Path("id") int id);
}
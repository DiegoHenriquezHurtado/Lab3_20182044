package com.example.pomodoro.services;

import com.example.pomodoro.dto.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DummyjsonService {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Users> existeUser(@Field("username") String username , @Field("password") String password);

    @GET("/users")
    Call<Users> getUsers();
}

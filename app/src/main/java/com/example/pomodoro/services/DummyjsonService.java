package com.example.pomodoro.services;

import com.example.pomodoro.dto.Tareas;
import com.example.pomodoro.dto.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DummyjsonService {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Users> existeUser(@Field("username") String username , @Field("password") String password);

    @GET("/todos/user/{id}")
    Call<Tareas> getTareasPorUsuario(@Path("id") int id);
}

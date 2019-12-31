package com.reshika.taskmanager.api;

import com.reshika.taskmanager.serverresponse.SignUpResponse;
import com.reshika.taskmanager.model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersApi {

    @POST("users/signup")
    Call<SignUpResponse> registerUser(@Body Users users);

}

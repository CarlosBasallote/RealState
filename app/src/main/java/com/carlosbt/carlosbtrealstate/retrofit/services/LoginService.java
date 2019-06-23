package com.carlosbt.carlosbtrealstate.retrofit.services;

import com.carlosbt.carlosbtrealstate.model.Registro;
import com.carlosbt.carlosbtrealstate.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/auth")
    Call<LoginResponse> doLogin(@Header("Authorization") String authorization);

    @POST("/users")
    Call<LoginResponse> doRegister(@Body Registro registro);




}

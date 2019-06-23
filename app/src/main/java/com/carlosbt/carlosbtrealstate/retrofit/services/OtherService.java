package com.carlosbt.carlosbtrealstate.retrofit.services;

import com.carlosbt.carlosbtrealstate.model.User;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OtherService {


    @GET("/users")
    Call<ResponseContainer<User>> listUsers();

    @GET("/users/{id}")
    Call<User> getUser(@Path("id") Long id);

}

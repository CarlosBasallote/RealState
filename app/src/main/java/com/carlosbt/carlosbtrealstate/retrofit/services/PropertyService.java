package com.carlosbt.carlosbtrealstate.retrofit.services;

import com.carlosbt.carlosbtrealstate.model.PropertyDTO;
import com.carlosbt.carlosbtrealstate.response.FavResponse;
import com.carlosbt.carlosbtrealstate.response.Mine;
import com.carlosbt.carlosbtrealstate.response.PropertyResponse;
import com.carlosbt.carlosbtrealstate.response.PropertyResponseOne;
import com.carlosbt.carlosbtrealstate.response.ResponseContainer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropertyService {

    @GET("properties")
    Call<ResponseContainer<PropertyResponse>> listProperties();

    @GET("properties/{id}")
    Call<PropertyResponseOne> oneProperty(@Path("id") String id);

    @DELETE("properties/{id}")
    Call<ResponseContainer<PropertyResponse>> deleteProperty(@Path("id") String id);

    @POST("properties")
    Call<PropertyResponseOne> addProperty(@Body PropertyDTO Property);

    @GET("/properties/mine")
    Call<ResponseContainer<Mine>> getUserProperties();

    @GET("/properties/fav")
    Call<ResponseContainer<PropertyResponse>> getFavouritesProperties();

    @POST("/properties/fav/{id}")
    Call<FavResponse> addPropertiesFav(@Path("id") String id);

    @DELETE("/properties/fav/{id}")
    Call<FavResponse> deletePropertiesFav(@Path("id") String id);


}

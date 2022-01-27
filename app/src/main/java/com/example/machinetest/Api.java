package com.example.machinetest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://randomuser.me/api/";
    @GET("?inc=gender,name,nat,location,picture,email&results=20")
    Call<JsonObject> getUserList();
}

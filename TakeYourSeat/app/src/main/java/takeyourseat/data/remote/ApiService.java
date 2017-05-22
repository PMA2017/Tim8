package com.example.nenad.retrofittest.data.remote;

import com.example.nenad.retrofittest.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nenad on 5/9/2017.
 */

public interface ApiService {

    @GET("/api/data/getall/user")
    Call<List<User>> getAllUsers();
}

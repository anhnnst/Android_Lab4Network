package com.loabten.anhnn.lab4network.retrofit;

import com.loabten.anhnn.lab4network.model.ServerRequest;
import com.loabten.anhnn.lab4network.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("learn-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}

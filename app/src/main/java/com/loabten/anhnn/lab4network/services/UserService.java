package com.loabten.anhnn.lab4network.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loabten.anhnn.lab4network.model.ServerRequest;
import com.loabten.anhnn.lab4network.model.ServerResponse;
import com.loabten.anhnn.lab4network.model.User;
import com.loabten.anhnn.lab4network.retrofit.ApiClient;
import com.loabten.anhnn.lab4network.retrofit.RequestInterface;
import com.loabten.anhnn.lab4network.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserService {
    private static final String TAG = UserService.class.getName();

    public void registerUser(User user) {

        Retrofit retrofit = ApiClient.getClient();

        RequestInterface request = retrofit.create(RequestInterface.class);

        ServerRequest srequest = new ServerRequest();
        srequest.setOperation(Constants.REGISTER_OPERATION);
        srequest.setUser(user);

        Call<ServerResponse> call = request.operation(srequest);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse jsonResponse = response.body();
                Log.d(TAG, "onResponse: New User has been inserted");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    public void checkLogin(User user,  Callback<ServerResponse> callback) {

        Retrofit retrofit = ApiClient.getClient();

        RequestInterface request = retrofit.create(RequestInterface.class);

        ServerRequest srequest = new ServerRequest();
        srequest.setOperation(Constants.LOGIN_OPERATION);
        srequest.setUser(user);

        Call<ServerResponse> call = request.operation(srequest);
        call.enqueue(callback);

    }

    public void checkLogin(String email, String oldPass, String newPass,  Callback<ServerResponse> callback) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = ApiClient.getClient();

        RequestInterface request = retrofit.create(RequestInterface.class);

        ServerRequest srequest = new ServerRequest();
        srequest.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        User user = new User();
        user.setNew_password(newPass);
        user.setOld_password(oldPass);
        user.setEmail(email);

        srequest.setUser(user);

        Call<ServerResponse> call = request.operation(srequest);
        call.enqueue(callback);

    }

    public void changePassword(User user, Callback<ServerResponse> callback) {

        Retrofit retrofit = ApiClient.getClient();

        RequestInterface request = retrofit.create(RequestInterface.class);

        ServerRequest srequest = new ServerRequest();
        srequest.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        srequest.setUser(user);

        Call<ServerResponse> call = request.operation(srequest);
        call.enqueue(callback);

    }
}

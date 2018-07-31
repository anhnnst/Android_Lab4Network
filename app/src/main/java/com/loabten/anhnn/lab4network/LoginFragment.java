package com.loabten.anhnn.lab4network;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.loabten.anhnn.lab4network.model.ServerResponse;
import com.loabten.anhnn.lab4network.model.User;
import com.loabten.anhnn.lab4network.services.UserService;
import com.loabten.anhnn.lab4network.utils.Constants;

import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements Callback<ServerResponse>, View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();
    private TextInputEditText mEtEmail, mEtPassword;
    private ProgressBar mPbProgress;
    private SharedPreferences mPrefs;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mPrefs = this.getActivity().getPreferences(0);

        mEtEmail = view.findViewById(R.id.etEmail);
        mEtPassword = view.findViewById(R.id.etPassword);
        mPbProgress = view.findViewById(R.id.pbProgress);

        view.findViewById(R.id.tvRegister).setOnClickListener(this);

        Button btn = view.findViewById(R.id.btLogin);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        ServerResponse resp = response.body();

        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
        mPbProgress.setVisibility(View.INVISIBLE);

        saveProfile(resp.getUser());

        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void saveProfile(User user) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.NAME, user.getName());
        editor.putString(Constants.EMAIL, user.getEmail());
        editor.putString(Constants.UNIQUE_ID, user.getUnique_id());
        editor.apply();
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
        mPbProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogin:
                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                if (email == null || email.equals("") || password == null || password.equals("")) {
                    Snackbar.make(getView(), "Email or password must be entered!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                mPbProgress.setVisibility(ProgressBar.VISIBLE);

                UserService userService = new UserService();
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);

                userService.checkLogin(user, LoginFragment.this);
                break;
            case R.id.tvRegister:
                MainActivityFragment fragment = new MainActivityFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;
        }
    }
}

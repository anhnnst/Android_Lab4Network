package com.loabten.anhnn.lab4network;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.loabten.anhnn.lab4network.model.User;
import com.loabten.anhnn.lab4network.services.UserService;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private Button mBtRegister;
    private TextInputEditText mEtName, mEtPassword, mEtEmail;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mBtRegister = view.findViewById(R.id.btLogin);

        mBtRegister.setOnClickListener(this);

        mEtName = view.findViewById(R.id.etName);
        mEtPassword = view.findViewById(R.id.etPassword);
        mEtEmail = view.findViewById(R.id.etEmail);

        return view;
    }

    @Override
    public void onClick(View v) {
        try {

            String name = mEtName.getText().toString();
            String password = mEtPassword.getText().toString();
            String email = mEtEmail.getText().toString();

            if (name == null || name.equals("")
                    || password == null || password.equals("")){
                Snackbar.make(getView(), "Name or password must be entered!", Snackbar.LENGTH_LONG);
                return;
            }

            UserService userService = new UserService();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            userService.registerUser(user);
            showLogin();
        }catch (Exception ex){
            ex.printStackTrace();
            Snackbar.make(getView(), "Error: " + ex.getMessage(), Snackbar.LENGTH_LONG);
        }
    }

    private void showLogin(){
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

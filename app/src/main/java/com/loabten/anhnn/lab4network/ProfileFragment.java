package com.loabten.anhnn.lab4network;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loabten.anhnn.lab4network.dialogs.ChangePasswordDialog;
import com.loabten.anhnn.lab4network.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView mTvName, mTvEmail;
    private SharedPreferences mPrefs;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mTvName = view.findViewById(R.id.tvName);
        mTvEmail = view.findViewById(R.id.tvEmail);

        view.findViewById(R.id.btLogout).setOnClickListener(this);
        view.findViewById(R.id.btChangePassword).setOnClickListener(this);

        mPrefs = getActivity().getPreferences(0);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btChangePassword:
                ChangePasswordDialog dialog = new ChangePasswordDialog(getContext());
                break;
            case R.id.btLogout:
                logout();

                LoginFragment fragment = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    private void logout(){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, false);
        editor.putString(Constants.NAME, "");
        editor.putString(Constants.EMAIL, "");
        editor.putString(Constants.UNIQUE_ID, "");
        editor.apply();
    }
}

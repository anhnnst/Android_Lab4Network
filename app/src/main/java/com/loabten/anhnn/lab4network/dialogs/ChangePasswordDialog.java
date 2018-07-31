package com.loabten.anhnn.lab4network.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loabten.anhnn.lab4network.R;
import com.loabten.anhnn.lab4network.model.ServerResponse;
import com.loabten.anhnn.lab4network.model.User;
import com.loabten.anhnn.lab4network.services.UserService;
import com.loabten.anhnn.lab4network.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialog implements Callback<ServerResponse> {
    private AlertDialog mDialog;
    private TextInputEditText mEtOldPassword, mEtNewPassword, mEtConfirmPassword ;
    private TextView mTvMessage;
    public ChangePasswordDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.dialog_change_password, null);

        mTvMessage = view.findViewById(R.id.tvMessage);
        mEtOldPassword = view.findViewById(R.id.etOldPassword);
        mEtNewPassword = view.findViewById(R.id.etNewPassword);
        mEtConfirmPassword = view.findViewById(R.id.etConfimPassword);

//        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String old = mEtOldPassword.getText().toString();
                String newP = mEtNewPassword.getText().toString();
                String confirm = mEtConfirmPassword.getText().toString();

                if (old.equals("") || newP.equals("") || confirm.equals("") || !newP.equals(confirm)){
                    mTvMessage.setText("All fields must be entered \nor new password and confirm password are not identical");
                    mTvMessage.setVisibility(View.VISIBLE);
                    return;
                }
                UserService userService = new UserService();
                User user = new User();
                user.setOld_password(old);
                user.setNew_password(newP);

                userService.changePassword(user, ChangePasswordDialog.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog = builder.create();
        mDialog.show();

    }

    @Override
    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
        ServerResponse resp = response.body();

        if (resp.getResult().equals(Constants.SUCCESS)){
            mDialog.dismiss();
            return;
        }

        mTvMessage.setText("Password can't be changed!");
        mTvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(Call<ServerResponse> call, Throwable t) {
        mTvMessage.setText("Error: " + t.getLocalizedMessage());
        mTvMessage.setVisibility(View.VISIBLE);

    }
}

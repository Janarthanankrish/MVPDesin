package com.mvpdesign.login;

import android.content.Context;

import com.commonfeaturelib.Common.CommonTextValidations;
import com.mvpdesign.R;

public class LoginPresenter {
    LoginView loginView;
    Context context;

    public LoginPresenter(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    public void checkLogin(String email, String password) {
        if (email.trim().equals(""))
            loginView.showError("email", context.getResources().getString(R.string.email_error));
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            loginView.showError("email", context.getResources().getString(R.string.valid_email_error));
        else if (password.trim().equals(""))
            loginView.showError("password", context.getResources().getString(R.string.password_error));
        else if (password.length() < 6)
            loginView.showError("password", context.getResources().getString(R.string.valid_password_error));
        else {
            CommonTextValidations.SetPrefString(context, "UserEmail", email, context.getResources().getString(R.string.app_name));
            loginView.movetoDashboard();}
    }
}

package com.mvpdesign.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.commonfeaturelib.Common.CommonTextValidations;
import com.mvpdesign.R;
import com.mvpdesign.dashboard.DashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    LoginPresenter loginPresenter;
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_password)
    EditText password;
    @BindView(R.id.btn_login)
    Button login;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(LoginActivity.this, LoginActivity.this);
        login.setOnClickListener(new Onclick());

    }

    @Override
    public void movetoDashboard() {
        CommonTextValidations.SetPrefString(LoginActivity.this, "UserDetail", "Yes", getResources().getString(R.string.app_name));
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();
    }

    @Override
    public void showError(String type, String error) {
        if (type.equalsIgnoreCase("email")) {
            email.requestFocus();
            email.setError(error);
        } else {
            password.requestFocus();
            password.setError(error);
        }

    }

    private class Onclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == login)
                loginPresenter.checkLogin(email.getText().toString(), password.getText().toString());
        }
    }
}

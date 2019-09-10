package com.mvpdesign.splash;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mvpdesign.R;
import com.mvpdesign.dashboard.DashboardActivity;
import com.mvpdesign.login.LoginActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashPresenter = new SplashPresenter(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashPresenter.checkUserDetails(SplashActivity.this, "UserDetail", getResources().getString(R.string.app_name));
            }
        }, 3000);
    }

    @Override
    public void movetoLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void movetoDashboard() {
        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
        finish();
    }
}

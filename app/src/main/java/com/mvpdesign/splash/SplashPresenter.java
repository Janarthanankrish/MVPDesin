package com.mvpdesign.splash;

import android.content.Context;

import com.commonfeaturelib.Common.CommonTextValidations;

public class SplashPresenter {

    private SplashView splashView;

    public SplashPresenter(SplashView view) {
        this.splashView = view;
    }

    public void checkUserDetails(Context context, String key, String appName) {
        if (!CommonTextValidations.GetPrefString(context, key, appName).equals(""))
            splashView.movetoDashboard();
        else splashView.movetoLogin();
    }
}

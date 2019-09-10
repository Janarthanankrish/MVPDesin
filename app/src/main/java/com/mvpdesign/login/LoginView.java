package com.mvpdesign.login;

public interface LoginView {
    void movetoDashboard();
    void showError(String type,String error);
}

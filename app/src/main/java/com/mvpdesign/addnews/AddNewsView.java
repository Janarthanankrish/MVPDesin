package com.mvpdesign.addnews;



public interface AddNewsView {
    void showError(String type,String error);
    void successMessage(boolean status,String message);
}

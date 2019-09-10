package com.mvpdesign.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;

import com.commonfeaturelib.Common.CommonTextValidations;
import com.commonfeaturelib.apidetails.BackGroundTask;
import com.commonfeaturelib.apidetails.GetValuesFromApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvpdesign.Common.AppConstant;
import com.mvpdesign.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardPresenter implements GetValuesFromApi {
    DashboardView dashboardView;
    Context context;

    public DashboardPresenter(Context context, DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        this.context = context;
    }

    public void getNewsList(boolean flag) {
        if (CommonTextValidations.isNetworkAvailable(context)) {
            JSONObject jsHeader = new JSONObject();
            try {
                jsHeader.put("consumer-secret", AppConstant.Client_Secrete);
                jsHeader.put("consumer-key", AppConstant.Key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BackGroundTask backGroundTask = new BackGroundTask(context, flag, DashboardPresenter.this, "GET", "Get News");
            backGroundTask.execute(AppConstant.Get_News, "", jsHeader.toString());
        } else dashboardView.loadError(context.getResources().getString(R.string.network_error));
    }

    @Override
    public void valuesfromServer(String values, String flag) {
        try {
            JSONObject jsonObject = new JSONObject(values);
            if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("payload");
                ArrayList<DashboardPojo> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<DashboardPojo>>() {
                }.getType());
                if (list != null && list.size() > 0)
                    dashboardView.loadSuccess(list);
                else
                    dashboardView.loadError(context.getResources().getString(R.string.news_error));
            } else
                dashboardView.loadError(context.getResources().getString(R.string.news_error));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLogoutAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.logout_msg));
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences settings = context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        dashboardView.logoutAlert();
                    }
                });

        alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    public void movetoNextActivity(){
        dashboardView.movetoAddNews();
    }
}

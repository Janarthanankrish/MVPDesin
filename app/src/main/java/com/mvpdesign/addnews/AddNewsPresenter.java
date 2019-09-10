package com.mvpdesign.addnews;

import android.content.Context;

import com.commonfeaturelib.Common.CommonTextValidations;
import com.commonfeaturelib.apidetails.BackGroundTask;
import com.commonfeaturelib.apidetails.GetValuesFromApi;
import com.mvpdesign.Common.AppConstant;
import com.mvpdesign.R;
import com.mvpdesign.dashboard.DashboardPresenter;

import org.json.JSONObject;

public class AddNewsPresenter implements GetValuesFromApi {
    AddNewsView addNewsView;
    Context context;

    public AddNewsPresenter(Context context, AddNewsView addNewsView) {
        this.context = context;
        this.addNewsView = addNewsView;
    }

    public void validateNews(String name, String eid, String idbar, String unifiedNumber, String mobileNo) {
        if (name.trim().equals(""))
            addNewsView.showError("name", context.getResources().getString(R.string.name_error));
        else if (eid.trim().equals(""))
            addNewsView.showError("eid", context.getResources().getString(R.string.eid_error));
        else if (idbar.trim().equals(""))
            addNewsView.showError("idbar", context.getResources().getString(R.string.idbarahno_error));
        else if (unifiedNumber.trim().equals(""))
            addNewsView.showError("unifiedNumber", context.getResources().getString(R.string.unified_number_error));
        else if (mobileNo.trim().equals(""))
            addNewsView.showError("mobileNo", context.getResources().getString(R.string.number_error));
        else {
            saveNews(name, eid, idbar, unifiedNumber, mobileNo);
        }

    }

    public void saveNews(String name, String eid, String idbar, String unifiedNumber, String mobileNo) {
        if (CommonTextValidations.isNetworkAvailable(context)) {
            JSONObject jsHeader = new JSONObject();
            JSONObject jsBody = new JSONObject();
            try {
                jsHeader.put("consumer-secret", AppConstant.Client_Secrete);
                jsHeader.put("consumer-key", AppConstant.Key);
                jsBody.put("name", name);
                jsBody.put("eid", Integer.parseInt(eid));
                jsBody.put("idbarahno", Integer.parseInt(idbar));
                jsBody.put("unifiednumber", Integer.parseInt(unifiedNumber));
                jsBody.put("mobileno", mobileNo);
                jsBody.put("emailaddress", CommonTextValidations.GetPrefString(context, "UserEmail", context.getResources().getString(R.string.app_name)));

            } catch (Exception e) {
                e.printStackTrace();
            }
            BackGroundTask backGroundTask = new BackGroundTask(context, true, AddNewsPresenter.this, "POST", "Save News");
            backGroundTask.execute(AppConstant.Add_News, jsBody.toString(), jsHeader.toString());
        } else
            addNewsView.showError("offline", context.getResources().getString(R.string.network_error));
    }

    @Override
    public void valuesfromServer(String values, String flag) {
        try {
            JSONObject jsonObject = new JSONObject(values);
            addNewsView.successMessage(jsonObject.getBoolean("success"), jsonObject.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

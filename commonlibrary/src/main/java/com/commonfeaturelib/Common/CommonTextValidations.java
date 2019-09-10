package com.commonfeaturelib.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by janarthananr on 21/3/18.
 */

public class CommonTextValidations {


    public static void setPrefBoolean(Context context, String key, boolean value, String appname) {
        SharedPreferences prefs = context.getSharedPreferences(appname,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPrefBoolean(Context context, String key, String appname) {
        SharedPreferences prefs = context.getSharedPreferences(appname,
                Context.MODE_PRIVATE);
        boolean prefstr = prefs.getBoolean(key, false);
        return prefstr;
    }

    public static void SetPrefString(Context context, String key, String value, String appname) {
        SharedPreferences prefs = context.getSharedPreferences(appname, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String GetPrefString(Context context, String key, String appname) {
        SharedPreferences prefs = context.getSharedPreferences(appname, Context.MODE_PRIVATE);
        String prefstr = prefs.getString(key, "");
        return prefstr;
    }

    public static void RemovePrefValues(Context context, String appname) {
        SharedPreferences prefs = context.getSharedPreferences(appname, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    public static void LoadGlideImages(Context context, String imageurl, ImageView imageview, int errorimage) {
        try {
            if (imageurl != null && !imageurl.equals("")) {
                if (imageurl.contains(" "))
                    imageurl = imageurl.replaceAll(" ", "%20");
                Glide.with(context)
                        .load(imageurl)
                        .apply(new RequestOptions().placeholder(errorimage).error(errorimage).timeout(10000))
                        .into(imageview);
            } else {
                Glide.with(context)
                        .load(errorimage)
                        .into(imageview);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

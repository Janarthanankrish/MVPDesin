package com.commonfeaturelib.apidetails;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by janarthananr on 21/3/18.
 */

public class CustomApiClient {
    public String get(okhttp3.Request getRequest) throws IOException {
        String result = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        okhttp3.Response getResponse = client.newCall(getRequest).execute();
        result = new String(getResponse.body().string());
        return result;
    }
}

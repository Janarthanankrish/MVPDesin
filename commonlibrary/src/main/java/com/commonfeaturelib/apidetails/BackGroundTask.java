package com.commonfeaturelib.apidetails;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;


import com.commonfeaturelib.R;
import com.commonfeaturelib.apidetails.CustomUploadFilepojo;
import com.commonfeaturelib.apidetails.GetValuesFromApi;
import com.commonfeaturelib.apidetails.UploadFilepojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by janarthananr on 21/3/18.
 */
@SuppressLint("NewApi")
public class BackGroundTask extends AsyncTask<String, String, String> {
    Context mContext;
    GetValuesFromApi getValuesFromApi;
    String apitype = "", outputfromapi = "", apiname = "", ApiUrl = "", ApiParametersRequest = "",
            ApiHeadersRequest = "", filepath = "", uploadnodename = "";
    MediaType mediatype;
    boolean flagloading = false;
    Dialog dialog;
    ArrayList<UploadFilepojo> ApiUploadfiles = new ArrayList<>();
    ArrayList<CustomUploadFilepojo> UploadfileList = new ArrayList<>();

    //TODO Call this when You Don't have File Upload
    public BackGroundTask(Context context, boolean flagloading, GetValuesFromApi getValuesFromApi,
                          String type, String apiname) {
        this.mContext = context; // Activity Context
        this.flagloading = flagloading; // Loading dialogue
        this.getValuesFromApi = getValuesFromApi; // Output InterFace
        this.apitype = type; // Api type like (Get or Post)
        this.apiname = apiname; // Where we called

    }

    //TODO Call this when You have File Upload
    public BackGroundTask(Context context, boolean flagloading, GetValuesFromApi getValuesFromApi,
                          String type, String apiname, ArrayList<UploadFilepojo> ApiUploadfiles) {
        this.mContext = context; // Activity Context
        this.flagloading = flagloading; // Loading dialogue
        this.getValuesFromApi = getValuesFromApi; // Output InterFace
        this.apitype = type; // Api type like (Get or Post)
        this.apiname = apiname; // Where we called
        this.ApiUploadfiles = ApiUploadfiles; // No.of files list to upload

    }

    public BackGroundTask(Context context, boolean flagloading, GetValuesFromApi getValuesFromApi, ArrayList<CustomUploadFilepojo> ApiUploadfiles, String type, String apiname) {
        this.mContext = context; // Activity Context
        this.flagloading = flagloading; // Loading dialogue
        this.getValuesFromApi = getValuesFromApi; // Output InterFace
        this.apitype = type; // Api type like (Get or Post)
        this.apiname = apiname; // Where we called
        this.UploadfileList = ApiUploadfiles; // No.of files list to upload

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (flagloading) {
            dialog = new Dialog(mContext, R.style.FullscreenDialog);
            dialog.setContentView(R.layout.progressbar);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    @SuppressWarnings("static-access")
    @Override
    protected String doInBackground(String... strings) {
        CustomApiClient Chttpclient = new CustomApiClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        ApiUrl = strings[0];
        ApiParametersRequest = strings[1];
        ApiHeadersRequest = strings[2];
        System.out.println("Api URL--" + ApiUrl);
        System.out.println("ApiParametersRequest--" + ApiParametersRequest);
        System.out.println("ApiHeadersRequest--" + ApiHeadersRequest);
        Headers.Builder headerbuilder = null;
        okhttp3.Request request = null;
        MultipartBody.Builder multibuilder = null;
        RequestBody postparamsbuilder = null;
        try {

            if (ApiHeadersRequest != null && !ApiHeadersRequest.equals("")) {
                headerbuilder = new Headers.Builder();
                JSONObject headerjson = new JSONObject(ApiHeadersRequest);
                Iterator<String> IterforHeader = headerjson.keys();
                while (IterforHeader.hasNext()) {
                    String key = IterforHeader.next();
                    String paraStr = headerjson.getString(key);
                    headerbuilder.add(key, paraStr);
                }
            }
//            TODO File Upload Implementations
            if (ApiUploadfiles != null && ApiUploadfiles.size() > 0) {
                multibuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (int i = 0; i < ApiUploadfiles.size(); i++) {
                    mediatype = MediaType.parse(ApiUploadfiles.get(i).mediatype);
                    filepath = ApiUploadfiles.get(i).filepath;
                    uploadnodename = ApiUploadfiles.get(i).uploadnodename;
                    if (filepath != null && !filepath.equals(""))
                        multibuilder.addFormDataPart(uploadnodename, new File(filepath).getName(),
                                RequestBody.create(mediatype, new File(filepath)));
                }
                if (ApiParametersRequest != null && !ApiParametersRequest.equals("")) {
                    JSONObject headerjson = new JSONObject(ApiParametersRequest);
                    Iterator<String> IterforHeader = headerjson.keys();
                    while (IterforHeader.hasNext()) {
                        String key = IterforHeader.next();
                        String paraStr = headerjson.getString(key);
                        multibuilder.addFormDataPart(key, paraStr);
                    }
                }
            } else if (UploadfileList != null && UploadfileList.size() > 0) {
                multibuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (int i = 0; i < UploadfileList.size(); i++) {
                    CustomUploadFilepojo customUploadFilepojo = UploadfileList.get(i);
                    if (customUploadFilepojo.imagelist.size() > 0) {
                        for (UploadFilepojo uploadFilepojo : customUploadFilepojo.imagelist) {
                            if (uploadFilepojo.filepath != null && !uploadFilepojo.filepath.equals("")) {
                                mediatype = MediaType.parse(uploadFilepojo.mediatype);
                                filepath = uploadFilepojo.filepath;
                                uploadnodename = uploadFilepojo.uploadnodename;
                                multibuilder.addFormDataPart(uploadnodename, new File(filepath).getName(),
                                        RequestBody.create(mediatype, new File(filepath)));
                            }
                        }
                    }
                    if (customUploadFilepojo.paramslist.size() > 0) {
                        Set entrySet = customUploadFilepojo.paramslist.entrySet();
                        Iterator IterforApiparameters = entrySet.iterator();
                        while (IterforApiparameters.hasNext()) {
                            Map.Entry me = (Map.Entry) IterforApiparameters.next();
                            multibuilder.addFormDataPart(me.getKey().toString(), me.getValue().toString());
                        }
                    }
                }
                if (ApiParametersRequest != null && !ApiParametersRequest.equals("")) {
                    JSONObject headerjson = new JSONObject(ApiParametersRequest);
                    Iterator<String> IterforHeader = headerjson.keys();
                    while (IterforHeader.hasNext()) {
                        String key = IterforHeader.next();
                        String paraStr = headerjson.getString(key);
                        multibuilder.addFormDataPart(key, paraStr);
                    }
                }
            } else {
//                if (apiname.equalsIgnoreCase("Hazard Observation Images Upload")) {
//                    multibuilder = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM);
//                    JSONArray jsonArray = new JSONArray(ApiParametersRequest);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        mediatype = MediaType.parse(jsonObject.getString("MediaType"));
//                        filepath = jsonObject.getString("FilePath");
//                        uploadnodename = jsonObject.getString("UploadName");
//                        if (filepath != null && !filepath.equals(""))
//                            multibuilder.addFormDataPart(uploadnodename, new File(filepath).getName(),
//                                    RequestBody.create(mediatype, new File(filepath)));
//                        postparamsbuilder = multibuilder.addFormDataPart("hazardObservationId", jsonObject.getString("hazardObservationId"))
//                                .addFormDataPart("propertyId", jsonObject.getString("propertyId"))
//                                .addFormDataPart("subPropertyId", jsonObject.getString("subPropertyId")).build();
//                    }
//
////                    JSONObject jsonObject = new JSONObject(ApiParametersRequest);
////                    Iterator<String> iterator = jsonObject.keys();
////                    while (iterator.hasNext()) {
////                        String key = iterator.next();
////                        if (key.equalsIgnoreCase("primary")) {
////                            JSONObject primary = jsonObject.getJSONObject(key);
////                            Iterator<String> primaryiterator = primary.keys();
////                            while (primaryiterator.hasNext()) {
////                                String primarykey = primaryiterator.next();
////                                String primaryvalues = primary.getString(primarykey);
////                                multibuilder.addFormDataPart("primary[" + primarykey + "]", primaryvalues);
////                            }
////                        } else if (key.equalsIgnoreCase("secondary")) {
////                            JSONArray secondary = jsonObject.getJSONArray(key);
////                            for (int i = 0; i < secondary.length(); i++) {
////                                JSONObject jssecondary = secondary.getJSONObject(i);
////                                Iterator<String> secondaryiterator = jssecondary.keys();
////                                while (secondaryiterator.hasNext()) {
////                                    String secondarykey = secondaryiterator.next();
////                                    if (secondarykey.equalsIgnoreCase("UploadFiles")) {
////                                        JSONArray uploadfile = jssecondary.getJSONArray(secondarykey);
////                                        for (int j = 0; j < uploadfile.length(); j++) {
////                                            JSONObject jsuploadfile = uploadfile.getJSONObject(j);
////                                            mediatype = MediaType.parse(jsuploadfile.getString("mediatype"));
////                                            filepath = jsuploadfile.getString("filepath");
////                                            uploadnodename = jsuploadfile.getString("uploadnodename");
////                                            if (filepath != null && !filepath.equals(""))
////                                                multibuilder.addFormDataPart(uploadnodename, new File(filepath).getName(),
////                                                        RequestBody.create(mediatype, new File(filepath)));
////                                        }
////                                    } else {
////                                        String primaryvalues = jssecondary.getString(secondarykey);
////                                        multibuilder.addFormDataPart("secondary[][" + secondarykey + "]", primaryvalues);
////                                    }
////                                }
////                            }
////                        } else {
////                            String paraStr = jsonObject.getString(key);
////                            multibuilder.addFormDataPart(key, paraStr);
////                        }
////                    }
//
//                } else
                if (ApiParametersRequest != null && !ApiParametersRequest.equals("")) {
                    postparamsbuilder = RequestBody.create(JSON, ApiParametersRequest);
                }
            }

            if (apitype.equalsIgnoreCase("POST")) {
//                if (apiname.equalsIgnoreCase("Hazard Observation Images Upload")) {
//                    request = new okhttp3.Request.Builder()
//                            .url(ApiUrl)
//                            .headers(headerbuilder.build())
//                            .post(postparamsbuilder).build();
//                } else
                if (headerbuilder != null && multibuilder != null) {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .post(multibuilder.build()).build();
                } else if (headerbuilder != null && postparamsbuilder != null) {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .post(postparamsbuilder).build();
                } else if (multibuilder != null) {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .post(multibuilder.build()).build();
                } else if (postparamsbuilder != null) {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .post(postparamsbuilder).build();
                }
            } else if (apitype.equalsIgnoreCase("Multipart")) {
                if (headerbuilder != null) {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .post(multibuilder.build()).build();
                } else {
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .post(multibuilder.build()).build();
                }
            } else if (apitype.equalsIgnoreCase("Delete")) {
                if (headerbuilder != null && postparamsbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .delete(postparamsbuilder)
                            .headers(headerbuilder.build())
                            .build();
                else if (headerbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .delete()
                            .headers(headerbuilder.build())
                            .build();
                else if (postparamsbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .delete(postparamsbuilder)
                            .build();
                else request = new okhttp3.Request.Builder()
                            .url(ApiUrl).delete()
                            .build();
            } else if (apitype.equalsIgnoreCase("Get")) {
                if (headerbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .build();
                else request = new okhttp3.Request.Builder()
                        .url(ApiUrl)
                        .build();
            } else if (apitype.equalsIgnoreCase("Put")) {
                if (multibuilder != null && headerbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .put(multibuilder.build()).build();
                else if (multibuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .put(multibuilder.build()).build();
                else if (headerbuilder != null)
                    request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .headers(headerbuilder.build())
                            .put(postparamsbuilder).build();
                else request = new okhttp3.Request.Builder()
                            .url(ApiUrl)
                            .put(postparamsbuilder).build();
            }

            outputfromapi = Chttpclient.get(request);
            System.out.println("outputfromapi--" + outputfromapi);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outputfromapi;
    }

    @Override
    protected void onPostExecute(String output) {
        super.onPostExecute(output);
        if (flagloading && dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
        this.getValuesFromApi.valuesfromServer(output, apiname);
    }
}

package com.commonfeaturelib.apidetails;

import com.commonfeaturelib.apidetails.UploadFilepojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by janarthananr on 14/5/18.
 */

public class CustomUploadFilepojo implements Serializable {
    public ArrayList<UploadFilepojo> imagelist = new ArrayList<>();
    public HashMap<String, String> paramslist = new HashMap<>();
}

package com.easiio.openrtspdemo.utils;

import com.easiio.openrtspdemo.RtspAPP;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtils {

    public static OkHttpClient okHttpClient = RtspAPP.getOkHttpClient();

    public static void get(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}

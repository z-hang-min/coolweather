package com.coolweather.android.util;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhangmin on 2017/5/17 0017.
 */

public class HttpUtil {
    public static final String TAG="NEW_REQUEST";
    public static void sendOkHttpRequest(String address, Callback callback) {
        Log.e(TAG,"URL:"+address);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}

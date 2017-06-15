package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangmin on 2017/6/15 0015.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}

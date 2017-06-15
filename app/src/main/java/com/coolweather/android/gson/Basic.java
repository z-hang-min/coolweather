package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangmin on 2017/6/15 0015.
 */

public class Basic {
    /**
     * city : su
     * id : cccc
     * update : {"loc":"2016-08-08 21:58"}
     */

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;



    public static class Update {
        /**
         * log : 2016-08-08 21:58
         */

        @SerializedName("loc")
        public String updateTime;


    }
}

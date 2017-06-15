package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class AQI {
    @SerializedName("city")
    public AQICity aqiCity;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}

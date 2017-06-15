package com.coolweather.android.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.R;
import com.coolweather.android.gson.ForeCast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private TextView titleCity, titleUpdateTime, degreeText,
            weatherInfoText, aqiText, pm25Text,
            comfortText, carWashText, sportText;
    private LinearLayout forecastLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        scrollView = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_text);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
//        SharedPreferences perfs = PreferenceManager.getDefaultSharedPreferences(this);
//        String weatherString = perfs.getString("weather", null);
//        if (weatherString != null) {
//            Weather weather = Utility.handleWeatherResponse(weatherString);
//            showWeatherInfo(weather);
//        } else {
            String weatherid = getIntent().getStringExtra("weather_id");
            scrollView.setVisibility(View.INVISIBLE);
            requestWeather(weatherid);
//
//        }

    }

    private void requestWeather(String weatherid) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherid + "&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败....", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);

                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        if (weather == null)
            return;
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        if (!TextUtils.isEmpty(cityName))
            titleCity.setText(cityName);
        if (!TextUtils.isEmpty(updateTime))
//            titleUpdateTime.setText(updateTime);
        if (!TextUtils.isEmpty(degree))
            degreeText.setText(degree);
        if (!TextUtils.isEmpty(weatherInfo))
            weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (ForeCast foreCast : weather.foreCastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.data_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(foreCast.date);
            infoText.setText(foreCast.more.info);
            maxText.setText(foreCast.temperature.max);
            minText.setText(foreCast.temperature.min);
            forecastLayout.addView(view);
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.aqiCity.aqi);
            pm25Text.setText(weather.aqi.aqiCity.pm25);
        }

        String comfortInfo = weather.suggestion.comfort.info;
        String carWashInfo = weather.suggestion.carWash.info;
        String sportInfo = weather.suggestion.sport.info;
        if (!TextUtils.isEmpty(comfortInfo))
            comfortText.setText("舒适度:" + comfortInfo);
        if (!TextUtils.isEmpty(carWashInfo))
        carWashText.setText("洗车指数:" + carWashInfo);
        if (!TextUtils.isEmpty(sportInfo))
        sportText.setText("运动建议:" + sportInfo);
        scrollView.setVisibility(View.VISIBLE);

    }
}

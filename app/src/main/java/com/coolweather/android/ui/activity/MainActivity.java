package com.coolweather.android.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coolweather.android.R;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
    }
}

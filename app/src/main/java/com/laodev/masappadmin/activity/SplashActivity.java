package com.laodev.masappadmin.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.laodev.masappadmin.R;
import com.laodev.masappadmin.util.AppUtils;

public class SplashActivity extends AppCompatActivity {

    private void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUIView();
        initEvent();
    }

    private void initUIView() {
        final Handler handler = new Handler();
        int SPLASH_TIME_OUT = 3000;
        handler.postDelayed(this::onShowNextView, SPLASH_TIME_OUT);
    }

    private void onShowNextView() {
        AppUtils.showOtherActivity(this, LoginActivity.class, 0);
        finish();
    }

}

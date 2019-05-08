package com.example.vachhani.place_order.Activity;

import android.content.Intent;
import android.os.Handler;

import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.TokenRefresh;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    TokenRefresh tokenRefresh;
    @AfterViews
    void Init() {
        tokenRefresh = new TokenRefresh(this,pref);
        tokenRefresh.onTokenRefresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.isLogIn().get()) {
                    startActivity(new Intent(SplashActivity.this, MenuDisplayActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, RegistrationActivity_.class));
                    finish();
                }


            }
        }, 3000);
    }

}

package com.example.vachhani.place_order.Utils;

import android.content.Context;
import android.util.Log;

import com.example.vachhani.place_order.Activity.BaseActivity;
import com.example.vachhani.place_order.Activity.BaseActivity_;
import com.example.vachhani.place_order.Activity.MainActivity;
import com.example.vachhani.place_order.Utils.AppPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.androidannotations.annotations.App;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nisarg on 05/09/18.
 */

public class TokenRefresh extends FirebaseInstanceIdService{

    private Context context;
    public TokenRefresh(Context context)
    {
        this.context=context;
    }
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("Refreshed token: ", refreshedToken);
        AppPreferences appPreferences=new AppPreferences(context);
        appPreferences.set("token",refreshedToken);
        //Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //  sendRegistrationToServer(refreshedToken);
    }
}

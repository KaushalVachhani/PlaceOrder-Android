package com.example.vachhani.place_order.Utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class TokenRefresh extends FirebaseInstanceIdService{


    CPref_ pref;
    private Context context;
    public TokenRefresh(Context context, CPref_ pref)
    {
        this.context=context;
        this.pref=pref;
    }
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("Refreshed token: ", refreshedToken);
        pref.token().put(refreshedToken);

        //Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //  sendRegistrationToServer(refreshedToken);
    }
}

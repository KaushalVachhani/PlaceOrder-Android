package com.example.vachhani.place_order.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Utility {
    private static ProgressDialog pd;
    public static ProgressDialog getDialog(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setMessage("Please Wait...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        return pd;
    }

    public static String api="http://192.168.43.229/API/";

    public static void set_table(Context context,int i)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tableNo", String.valueOf(i));
        editor.apply();
    }
    public static String get_table(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String table = preferences.getString("tableNo", "");
        return table;
    }


}

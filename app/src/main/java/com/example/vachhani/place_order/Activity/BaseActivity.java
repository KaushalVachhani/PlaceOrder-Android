package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Utils.CPref_;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;
import java.util.Map;

@EActivity
public class BaseActivity extends AppCompatActivity {

    DataContext dataContext;
    ProgressDialog pd;
    CountDownTimer countDownTimer;
    long timeLeft = 600000;
    static int flag = 0;

    @Pref
    CPref_ pref;


    public void loads() {
        dataContext = new DataContext(this);
        pd = Utility.getDialog(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startTimer(final String tableNo, final Context context) {

        flag = 1;
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
            }

            @Override
            public void onFinish() {
                if (flag != 0) {
                    onExpire(tableNo, context);
                    Toast.makeText(context, "Session Expired \n Book the table again !!", Toast.LENGTH_SHORT).show();
                    Log.d("Expire------>", "Session");

                }
            }
        }.start();
    }

    public void onExpire(String tableNo, Context context) {

        pref = new CPref_(context);
        pref.table_num().remove();
        load1(tableNo, context);


    }

    public void load1(final String tableNo, Context context) {

        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p11_removetable.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // pd.dismiss();
                        Log.i("RESPONSE----->", s);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //pd.dismiss();
                Log.d("error", String.valueOf(volleyError));
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("table_id", tableNo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public void changeFlag() {
        flag = 0;
    }
}

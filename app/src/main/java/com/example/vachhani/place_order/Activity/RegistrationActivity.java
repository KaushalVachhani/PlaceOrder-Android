package com.example.vachhani.place_order.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends BaseActivity {

    @ViewById
    EditText edt_name, edt_mobile, edt_email;

    @ViewById
    LinearLayout llRegistration;

    @ViewById
    TextView txtTitle;

    @ViewById
    Button btn_register;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void init() {
        loads();
        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(getString(R.string.registration));
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_name.getText().toString().equals("") && !edt_email.getText().toString().equals("") && edt_mobile != null) {
                    load();
                } else
                    Toast.makeText(RegistrationActivity.this, "Please fill the details !!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void load() {

        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p8_registration.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object1 = object.getJSONObject("Response");
                            Log.d("Registration Response", String.valueOf(object1));
                            Log.d("Registration status", object1.getString("status"));
                            if (object1.getString("status").equals("1")) {

                                //regitration successful snackbar
                                Snackbar snackbar = Snackbar.make(llRegistration, "Registration successful !!", Snackbar.LENGTH_SHORT);
                                snackbar.show();

                                //getting registration id from response to set into preferance
                                JSONObject object2 = object1.getJSONObject("id");
                                Log.d("Registration id", object2.getString("$id"));

                                pref.edit().isLogIn().put(true)
                                        .userID().put(object2.getString("$id"))
                                        .userName().put(edt_name.getText().toString())
                                        .email().put(edt_email.getText().toString())
                                        .mobile_num().put(edt_mobile.getText().toString()).apply();

                                startActivity(new Intent(RegistrationActivity.this,MainActivity_.class));

                            }
                            pd.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.d("error", String.valueOf(volleyError));
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", edt_name.getText().toString());
                params.put("mobile", edt_mobile.getText().toString());
                params.put("email", edt_email.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

}

package com.example.vachhani.place_order.Adapter.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vachhani.place_order.Activity.MenuDisplayActivity_;
import com.example.vachhani.place_order.Data.Tables;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.CPref_;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;
import java.util.Map;

@EViewGroup(R.layout.table_row)
public class TableItemView extends LinearLayout {

    @Pref
    CPref_ cPref;

    ProgressDialog pd;
    @ViewById
    TextView txtTableNo;

    @ViewById
    public RelativeLayout root;

    @ViewById
    ImageView imgStatus;

    Tables data;

    public TableItemView(Context context) {
        super(context);
    }

    public void bind(Tables data) {
        this.data = data;
        txtTableNo.setText(data.tableNo);
        imgStatus.setVisibility(Integer.parseInt(data.status) == 0 ? VISIBLE : GONE);
    }

    @Click
    void root() {
        if (data.status.equalsIgnoreCase("1")) {

            Log.d("table number------->", data.tableNo);

            //set the table number in pref.
            cPref.table_num().put(Integer.valueOf(data.tableNo));
            getContext().startActivity(new Intent(getContext(), MenuDisplayActivity_.class));
            load();
        } else
            new AlertDialog.Builder(getContext()).setMessage("opps!!!! \n \nTable is already booked").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

    }

    public void load() {

        Utility.getDialog(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p7_settable.php",
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
                params.put("table_id", data.tableNo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

}

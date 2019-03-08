package com.example.vachhani.place_order.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends BaseActivity {

    HorizontalStepView horizontalStepView;
    ImageView imgQrCode;
    String orderId;
    String product_img;
    String[] product_name = new String[10];
    String[] qty = new String[10];
    String[] topings = new String[10];
    String[] price = new String[10];
    String date;
    int total;
    TextView tv_name, tv_price, tv_item_total, tv_total, tv_delivery, txtTitle;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        imgQrCode = findViewById(R.id.imgQrCode);
        horizontalStepView = findViewById(R.id.horizontalStepView);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_item_total = findViewById(R.id.tv_item_total);
        tv_total = findViewById(R.id.tv_total);
        tv_delivery = findViewById(R.id.tv_delivery);
        toolbar = findViewById(R.id.toolbar);
        txtTitle = findViewById(R.id.txtTitle);


        String message = getIntent().getStringExtra("msg");
        orderId = getIntent().getStringExtra("orderId");
        product_img = getIntent().getStringExtra("img");
        product_name = getIntent().getStringArrayExtra("product_name");
        qty = getIntent().getStringArrayExtra("qty");
        topings = getIntent().getStringArrayExtra("topings");
        price = getIntent().getStringArrayExtra("price");
        date = getIntent().getStringExtra("date");

        Log.d("product_img--->", Arrays.toString(product_name));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        txtTitle.setText(getString(R.string.your_order));


        loads();
        generateQr(orderId);
        load();
        setup();


    }

    private void setup() {
        int length = count(product_name);
        for (int i = 0; i < length; i++) {
            tv_name.append(product_name[i] + "  x  " + qty[i] + "\n");
            tv_price.append(price[i] + "\n");
            try {
                total += Integer.parseInt(price[i]);
            } catch (Exception e) {

            }


        }

        tv_item_total.setText("Item Total");
        tv_total.setText(total+"");

    }

    public void generateQr(String qr) {
        Resources r = getResources();
        int dp = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()));

        Log.e("size", String.valueOf(dp));

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qr, BarcodeFormat.QR_CODE, dp, dp);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void load() {

        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p10_orderstatus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object1 = object.getJSONObject("Response");
                            JSONArray array = object1.getJSONArray("Order");
                            Log.d("Response", String.valueOf(array));

                            int status;

                            status = Integer.parseInt(array.getJSONObject(0).getString("status"));
                            Log.d("Status", String.valueOf(status));

                            int accepted = 1, start = -1, ready = -1;

                            if (status == 1)
                                start = 1;
                            if (status == 2) {
                                start = 1;
                                ready = 1;
                            }

                            Log.e("accepted", String.valueOf(accepted));
                            Log.e("start", String.valueOf(start));
                            Log.e("ready", String.valueOf(ready));

                            List<StepBean> sources = new ArrayList<>();
                            sources.add(new StepBean("Accepted", accepted));
                            sources.add(new StepBean("Start", start));
                            sources.add(new StepBean("Ready", ready));

                            //attributes for tracking bar
                            horizontalStepView.setStepViewTexts(sources)
                                    .setTextSize(12)
                                    .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.colorPrimaryDark))
                                    .setStepViewComplectedTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.colorPrimaryDark))
                                    .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.colorPrimaryDark))
                                    .setStepViewUnComplectedTextColor(ContextCompat.getColor(OrderDetailActivity.this, R.color.uncompleted_text_color))
                                    .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(OrderDetailActivity.this, R.drawable.completed))
                                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(OrderDetailActivity.this, R.drawable.end_icon));

                            if (ready == 1) {
                                tv_delivery.setVisibility(View.VISIBLE);
                                tv_delivery.setText("order delivered on " + date);
                            }

                            pd.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("error", String.valueOf(volleyError));
                pd.dismiss();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", orderId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetailActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    public int count(Object[] array) {
        int c = 0;
        for (Object el : array) {
            if (el != null) c++;
        }
        return c;
    }
}

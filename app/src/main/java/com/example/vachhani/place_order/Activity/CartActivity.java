package com.example.vachhani.place_order.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.example.vachhani.place_order.Adapter.CartAdapter;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity implements PaymentResultListener {

    DataContext dataContext = new DataContext(this);
    ArrayList<TableCart> list = new ArrayList<>();
    TableCart cart;
    int total,payment=0;
    String currentDateandTime,order_id;
    SimpleDateFormat sdf,ft;
    ProgressDialog pd;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle, txtEmpty;

    @ViewById
    RecyclerView rvCart;

    @ViewById
    TextView txtTotal;

    @ViewById
    RelativeLayout llCart;

    @Bean
    CartAdapter adapter;

    @AfterViews
    public void init() {

        loads();
        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        txtTitle.setText(getString(R.string.cart_item));
        rvCart.setLayoutManager(new GridLayoutManager(this, 1));
        rvCart.setAdapter(adapter);
        list.clear();
        fillCart();
        txtTotal.setText("Total Amount : " + String.valueOf(total));
        Date dNow = new Date();
        sdf= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        ft = new SimpleDateFormat("yyMMddhhmmssMs");
        currentDateandTime = sdf.format(dNow);
        order_id = ft.format(dNow);

    }

    //This method will get all cart items
    private void fillCart() {
        try {
            dataContext.userObjectSet.fill();
            total = 0;
            for (int i = 0; i < dataContext.userObjectSet.size(); i++) {
                cart = new TableCart();
                cart.product_img = dataContext.userObjectSet.get(i).product_img;
                cart.product_id = dataContext.userObjectSet.get(i).product_id;
                cart.product_name = dataContext.userObjectSet.get(i).product_name;
                cart.qty = dataContext.userObjectSet.get(i).qty;
                cart.price = dataContext.userObjectSet.get(i).price;
                total = total + (cart.qty * cart.price);
                list.add(cart);

                Log.d("cart", String.valueOf(cart.product_name));

            }

            if (list.size() < 1) {
                txtEmpty.setVisibility(View.VISIBLE);
                txtEmpty.setText("No Items in cart !  ");
            }
            adapter.setList(list);

        } catch (AdaFrameworkException e) {
            e.printStackTrace();
            Log.e("error", String.valueOf(e));
        }

    }

    //This method will refresh the data as item deletes
    public void refresh() {
        list.clear();
        fillCart();
        txtTotal.setText("Total Amount : " + String.valueOf(total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_item1);
        menuItem.getActionView();
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            case R.id.menu_item1: {
//                // Do something
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    AlertDialog dialog;

    //on pay button click payment options will be open
    @OptionsItem(R.id.menu_item1)
    void onMenuItemClick(MenuItem menuItem) {

        if (checkCart()) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.custom_payment_dialog, null);
            Button btnCash = alertLayout.findViewById(R.id.btnCash);
            Button btnOnline = alertLayout.findViewById(R.id.btnOnline);

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setView(alertLayout);

            btnCash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    load();
                }
            });

            btnOnline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    payment = 1;
                    startPayment(CartActivity.this);
                }
            });

            alertDialog.show();
        } else {
            Snackbar snackbar = Snackbar.make(llCart, "No items in cart!!!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        

    }

    private boolean checkCart() {
        try {
            dataContext.userObjectSet.fill();
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }
        if (dataContext.userObjectSet.size() > 0) //Is there any item in cart or not will be checked
            return true;
        else
            return false;

    }


    public String composeJSONfromSQLite() {
        try {
            dataContext.userObjectSet.fill();

        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < dataContext.userObjectSet.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("product_id", dataContext.userObjectSet.get(i).product_id);
            map.put("qty", String.valueOf(dataContext.userObjectSet.get(i).qty));
            map.put("price", String.valueOf(dataContext.userObjectSet.get(i).price));
            map.put("table_no", pref.table_num().get()+"");
            map.put("order_id", order_id);
            map.put("token", pref.token().get());
            map.put("uid",pref.userID().get());
            map.put("datetime", String.valueOf(currentDateandTime));
            map.put("payment", String.valueOf(payment));
            Log.i("token--------->", pref.token().get());
            wordList.add(map);
        }
        Gson gson = new GsonBuilder().create();
        //use GSON to serialize array list ot JSON
        return gson.toJson(wordList);
    }

    //method to place order.
    public void load() {

        pd.show();

        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p6_order.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.hide();
                        pd.dismiss();
                        Log.i("RESPONSE----->", s );
                        try {
                            dataContext.userObjectSet.fill();
                            for (int i = 0; i < dataContext.userObjectSet.size(); i++) {
                                TableCart cart = dataContext.userObjectSet.get(i);
                                cart.setStatus(Entity.STATUS_DELETED);
                            }
                            dataContext.userObjectSet.save();

                        } catch (AdaFrameworkException e) {
                            e.printStackTrace();
                        }
                        refresh();
                        Snackbar snackbar = Snackbar.make(llCart, "order is placed!!!!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        Intent intent = new Intent(CartActivity.this,MenuDisplayActivity_.class);
                        startActivity(intent);

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
                params.put("order_item", composeJSONfromSQLite().toString());
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

    //method which integrate the payment gateway.
    public void startPayment(Context context) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9999999999");

            options.put("prefill", preFill);

            co.open((Activity) context, options);
        } catch (Exception e) {
            Toast.makeText(context, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            //Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Log.d("payment successful : ", razorpayPaymentID);
            load(); //order placement method will be called after successful payment.
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            //Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            Log.d("Payment failed: ", "" + code);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}






package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_whole_product)
public class WholeProductActivity extends BaseActivity {

    ProgressDialog pd;

    @ViewById
    ImageView imgProduct;

    @ViewById
    RelativeLayout rlProduct;


    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtProductName, txtPrice, txtDesc, txtTitle;

    @ViewById
    Button btnAddOrder, btnAddFav;

    TextView txtBadge;

    @OptionsMenuItem
    MenuItem menu_item;

    @AfterViews
    public void init() {
        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        txtTitle.setText(getString(R.string.whole_item));
        txtProductName.setText(getIntent().getStringExtra("productName"));
        txtPrice.setText(getIntent().getStringExtra("productPrice"));
        txtDesc.setText(getIntent().getStringExtra("productDetails"));
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("productImg")).into(imgProduct);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderClik();
            }
        });
/*
        View actionView = MenuItemCompat.getActionView(menu_item);
        txtBadge= actionView.findViewById(R.id.txtBadge);
        txtBadge.setText("121212121");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_item);
        menuItem.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setBadge(){
//        txtBadge.setText("12121");
//    }

    @OptionsItem(R.id.menu_item)
    void onMenuItemClick(MenuItem menuItem) {
        Snackbar snackbar = Snackbar.make(rlProduct, "Item added to cart", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    void onOrderClik() {
        Snackbar snackbar = Snackbar.make(rlProduct, "Item added to order", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


}

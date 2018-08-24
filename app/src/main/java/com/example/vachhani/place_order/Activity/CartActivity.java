package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.vachhani.place_order.Adapter.CartAdapter;
import com.example.vachhani.place_order.Adapter.TablesAdapter;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.Data.Tables;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {

    DataContext dataContext = new DataContext(this);
    ArrayList<TableCart> list=new ArrayList<>();
    TableCart cart=new TableCart();
    ProgressDialog pd;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle;

    @ViewById
    RecyclerView rvCart;

    @Bean
    CartAdapter adapter;

    @AfterViews
    public void init() {


        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        txtTitle.setText(getString(R.string.cart_item));
        rvCart.setLayoutManager(new GridLayoutManager(this, 1));
        rvCart.setAdapter(adapter);


        try {
            dataContext.userObjectSet.fill();
            for(int i=0;i<dataContext.userObjectSet.size();i++)
            {
                cart.product_img=dataContext.userObjectSet.get(i).product_img;
                cart.product_id=dataContext.userObjectSet.get(i).product_id;
                cart.product_name=dataContext.userObjectSet.get(i).product_name;
                cart.qty=dataContext.userObjectSet.get(i).qty;
                cart.price=dataContext.userObjectSet.get(i).price;
                list.add(cart);
                adapter.setList(list);

            }

        } catch (AdaFrameworkException e) {
            e.printStackTrace();
            Log.e("error",String.valueOf(e));
        }


    }
    public void refresh(){
        try {
            dataContext.userObjectSet.fill();
            for(int i=0;i<dataContext.userObjectSet.size();i++)
            {
                cart.product_img=dataContext.userObjectSet.get(i).product_img;
                cart.product_id=dataContext.userObjectSet.get(i).product_id;
                cart.product_name=dataContext.userObjectSet.get(i).product_name;
                cart.qty=dataContext.userObjectSet.get(i).qty;
                cart.price=dataContext.userObjectSet.get(i).price;
                list.add(cart);
                adapter.setList(list);

            }

        } catch (AdaFrameworkException e) {
            e.printStackTrace();
            Log.e("error",String.valueOf(e));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_item1);
        menuItem.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_item1: {
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OptionsItem(R.id.menu_item1)
    void onMenuItemClick(MenuItem menuItem) {
       load();
    }

    public String composeJSONfromSQLite()
    {
        try {
            dataContext.userObjectSet.fill();

        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }
        ArrayList<HashMap<String,String>> wordList;
       wordList=new ArrayList<HashMap<String,String>>();
       for(int i=0;i<dataContext.userObjectSet.size();i++)
       {
           HashMap<String,String> map=new HashMap<>();
           map.put("product_id",dataContext.userObjectSet.get(i).product_id);
           map.put("qty", String.valueOf(dataContext.userObjectSet.get(i).qty));
           map.put("price", String.valueOf(dataContext.userObjectSet.get(i).price));
           wordList.add(map);
       }
        Gson gson=new GsonBuilder().create();
       //use GSON to serialize array list ot JSON
        return  gson.toJson(wordList);
    }


    public void load(){

        pd.show();

        StringRequest request=new StringRequest(Request.Method.POST,Utility.api+"p6_order.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.hide();pd.dismiss();
                        Log.i("RESPONSE----->",s);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.d("error", String.valueOf(volleyError));
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order_item",composeJSONfromSQLite().toString());
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






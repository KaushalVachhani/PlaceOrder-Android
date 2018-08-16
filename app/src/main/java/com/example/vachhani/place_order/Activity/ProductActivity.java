package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
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
import com.example.vachhani.place_order.Adapter.MainCategoryAdapter;
import com.example.vachhani.place_order.Adapter.ProductAdapter;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.product_list)
public class ProductActivity extends BaseActivity {


    ProgressDialog pd;

    @Bean
    ProductAdapter adapter;

    @ViewById
    RecyclerView rvProduct;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle;

    ArrayList<Product> list=new ArrayList<>();
    public String parent_id;


    @AfterViews
    public void init()
    {
        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        txtTitle.setText(getString(R.string.select_item));
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        rvProduct.setAdapter(adapter);
        parent_id=getIntent().getStringExtra("parent_id");
        load();
    }

    public void load(){

        pd.show();
        StringRequest request=new StringRequest(Request.Method.POST,Utility.api+"p2_product.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object=new JSONObject(s);
                            JSONObject object1=object.getJSONObject("Response");
                            JSONArray array=object1.getJSONArray("product");
                            Log.d("Response", String.valueOf(array));

                            for(int i=0;i<array.length();i++){
                                Product product =new Product();
                                product.productName=array.getJSONObject(i).getString("name");
                                product.productImage=array.getJSONObject(i).getString("img");
                                product.productPrice=array.getJSONObject(i).getString("price");
                                product.productDetails=array.getJSONObject(i).getString("details");
                                list.add(product);
                            }
                            adapter.setList(list);
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("parent_id",parent_id);
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

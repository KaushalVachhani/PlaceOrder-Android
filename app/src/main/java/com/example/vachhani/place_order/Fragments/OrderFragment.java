package com.example.vachhani.place_order.Fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.vachhani.place_order.Adapter.OrdersAdapter;
import com.example.vachhani.place_order.Data.Order;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.CPref_;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@EFragment(R.layout.fragment_order)
public class OrderFragment extends Fragment {

    ArrayList<Order> list = new ArrayList<Order>();
    ProgressDialog pd;
    CPref_ pref;
    String order_date;

    @Bean
    OrdersAdapter adapter;

    @ViewById
    RecyclerView rvMainCat;

    @AfterViews
    void init() {

        Toast.makeText(getActivity(), "Order fragment", Toast.LENGTH_SHORT).show();
        rvMainCat.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvMainCat.setAdapter(adapter);
        pd = Utility.getDialog(getActivity());
        pref = new CPref_(getActivity());
        load();

    }


    public void load() {

        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p9_order_history.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object1 = object.getJSONObject("Response");
                            JSONArray array = object1.getJSONArray("data");
                            Log.d("Response", String.valueOf(array));

                            for (int i = 0; i < array.length(); i++) {
                                Order order = new Order();
                                order.productId = array.getJSONObject(i).getString("product_id");
                                order.productImg = array.getJSONObject(i).getString("product_image");
                                order.productName = array.getJSONObject(i).getString("product_name");
                                order.qty = array.getJSONObject(i).getString("qty");
                                int qty = Integer.parseInt(order.qty);
                                order.toping = array.getJSONObject(i).getString("topings");
                                order.totalPrice = Integer.parseInt((array.getJSONObject(i).getString("price"))) * qty + "";
                                order.orderId = array.getJSONObject(i).getJSONObject("id").getString("$id");
                                order_date = String.valueOf(array.getJSONObject(i).getString("date"));
                                StringTokenizer tk = new StringTokenizer(order_date);
                                String date = tk.nextToken();
                                Log.d("date", date);
                                order.date = date;

                                list.add(order);

                            }
                            adapter.setList(list);

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
                params.put("uid", pref.userID().get());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        request.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }


}

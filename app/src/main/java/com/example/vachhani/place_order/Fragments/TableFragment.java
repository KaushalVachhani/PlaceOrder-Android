package com.example.vachhani.place_order.Fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vachhani.place_order.Adapter.TablesAdapter;
import com.example.vachhani.place_order.Data.Tables;
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

@EFragment(R.layout.fragment_tables)
public class TableFragment extends Fragment {

    ProgressDialog pd;
    CPref_ pref;

    @ViewById
    RecyclerView rvTables;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle;

    ArrayList<Tables> list = new ArrayList<>();

    @Bean
    TablesAdapter adapter;

    @AfterViews
    void init() {
        pd = Utility.getDialog(getActivity());
        pref = new CPref_(getActivity());
        load();
        rvTables.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvTables.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        load();
    }

    public void load() {

        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, Utility.api + "p3_tables.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object1 = object.getJSONObject("Response");
                            JSONArray array = object1.getJSONArray("table");
                            Log.d("Response", String.valueOf(array));
                            list.clear();
                            for (int i = 0; i < array.length(); i++) {
                                Tables tables = new Tables();
                                tables.tableNo = array.getJSONObject(i).getString("table");
                                tables.status = array.getJSONObject(i).getString("status");
                                tables.seats = array.getJSONObject(i).getString("seats");
                                list.add(tables);
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
                pd.dismiss();
                Log.d("error", String.valueOf(volleyError));
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
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
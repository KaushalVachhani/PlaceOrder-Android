package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
import com.example.vachhani.place_order.Data.Category;
import com.example.vachhani.place_order.Fragments.OrderFragment;
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

@EActivity(R.layout.activity_menu_display)
public class MenuDisplayActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pd;
    String category_type;
    ArrayList<Category> list = new ArrayList<>();
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    @Bean
    MainCategoryAdapter adapter;

    @ViewById
    DrawerLayout drawer;

    @ViewById
    RecyclerView rvMainCat;

    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle;

    @ViewById
    TabLayout tabLayout;

    @AfterViews
    void init() {
        loads();
        Toast.makeText(this, "table no:" + pref.table_num().get(), Toast.LENGTH_SHORT).show();
        pd = Utility.getDialog(this);


        //toolbar setting
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        rvMainCat.setLayoutManager(new GridLayoutManager(this, 1));
        rvMainCat.setAdapter(adapter);
        txtTitle.setText(getString(R.string.select_menu));

        //setting of toggle in drawer
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setting tab layout
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.vegetarian)), true);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.non_vegetarian)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.drinks)));

        //By default tab layout will be 0.
        category_type = "0";
        list.clear();

        //initialization of food menu.
        load();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category_type = String.valueOf(tab.getPosition());
                list.clear();
                load();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragment = new OrderFragment();

        } else if (id == R.id.nav_gallery) {
            fragment = new OrderFragment();

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_slideshow) {


        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FrameLayout navScreen = findViewById(R.id.navScreen);
            navScreen.setVisibility(View.VISIBLE);
            fragmentTransaction.replace(R.id.navScreen, fragment);
            fragmentTransaction.commit();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void load() {

        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, Utility.api + "p5_maincategory.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject object1 = object.getJSONObject("Response");
                            JSONArray array = object1.getJSONArray("Category");
                            Log.d("Response", String.valueOf(array));

                            for (int i = 0; i < array.length(); i++) {
                                Category category = new Category();
                                category.category_name = array.getJSONObject(i).getString("name");
                                category.category_id = array.getJSONObject(i).getJSONObject("id").getString("$id");
                                category.img = array.getJSONObject(i).getString("img");
                                list.add(category);

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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category_type", category_type);
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

package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachhani.place_order.Adapter.CartAdapter;
import com.example.vachhani.place_order.Adapter.TablesAdapter;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {

    DataContext dataContext = new DataContext(this);
    ArrayList<TableCart> list=new ArrayList<>();
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
            Toast.makeText(this, "cart called", Toast.LENGTH_SHORT).show();
            dataContext.userObjectSet.fill();
            for(int i=0;i<dataContext.userObjectSet.size();i++)
            {
                TableCart cart=new TableCart();
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


}






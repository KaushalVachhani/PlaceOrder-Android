package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachhani.place_order.Activity.CartActivity;
import com.example.vachhani.place_order.Activity.MenuDisplayActivity_;
import com.example.vachhani.place_order.Activity.ProductActivity_;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.Data.Tables;
import com.example.vachhani.place_order.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.cart_row)
public class CartView extends LinearLayout {

    Context context = getContext();
    DataContext dataContext = new DataContext(context);

    @ViewById
    TextView txtProductName, txtQty, txtTotal, txtRemove;

    @ViewById
    ImageView imgProduct;

    @ViewById
    LinearLayout llCart;


    TableCart data;

    public CartView(Context context) {
        super(context);
    }

    public void bind(final TableCart data) {
        this.data = data;
        int total = (data.price) * (data.qty);
        txtProductName.setText(data.product_name);
        txtQty.setText(String.valueOf("Quantity : " + data.qty));
        txtTotal.setText(String.valueOf("Total : " + total));
        Log.e("name_____________", data.product_name);
        Picasso.with(getContext()).load(data.product_img).into(imgProduct);

    }

    @Click
    void txtRemove() {
        try {
            dataContext.userObjectSet.fill("product_id=?", new String[]{data.product_id}, null);
            Log.e("remove___________", String.valueOf(dataContext.userObjectSet.get(0)));
            TableCart cart = dataContext.userObjectSet.get(0);
            cart.setStatus(Entity.STATUS_DELETED);
            dataContext.userObjectSet.save();
            Snackbar snackbar = Snackbar.make(llCart, "Item deleted!!!!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }

        ((CartActivity) context).refresh();
    }
}

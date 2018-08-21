package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.MenuDisplayActivity_;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.Data.Tables;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.cart_row)
public class CartView extends LinearLayout {

    @ViewById
    TextView txtProductName,txtQty,txtTotal;

    @ViewById
    Button btnRemove;

    @ViewById
    ImageView imgProduct;

    TableCart data;

    public CartView(Context context) {
        super(context);
    }

    public void bind(TableCart data) {
        this.data = data;
        int total=(data.price)*(data.qty);
        txtProductName.setText(data.product_name);
        txtQty.setText(String.valueOf(data.qty));
        txtTotal.setText(String.valueOf(total));
        Picasso.with(getContext()).load(data.product_img).into(imgProduct);
    }

}

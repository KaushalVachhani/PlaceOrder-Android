package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Data.Order;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.order_row)
public class OrderView extends LinearLayout {

    @ViewById
    TextView tvOrder,tvItem,tvPrice;

    @ViewById
    ImageView imgProduct;

    Order data;

    public OrderView(Context context) {
        super(context);
    }

    public void bind(Order data) {
        this.data = data;
        Picasso.with(getContext()).load(data.productImg).into(imgProduct);
        Log.d("product image",data.productImg.toString());
        tvOrder.setText("Order Id : " + data.orderId );
        tvItem.setText(data.productName);
        tvPrice.setText(data.totalPrice+" rs...\nOrdered on "+data.date);
    }

}

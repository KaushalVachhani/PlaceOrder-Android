package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.OrderDetailActivity;
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

    @ViewById
    LinearLayout llOrder;

    Order data;

    public OrderView(Context context) {
        super(context);
    }

    public void bind(final Order data) {
        this.data = data;
        Picasso.with(getContext()).load(data.productImg).into(imgProduct);
        Log.d("product image",data.productImg.toString());
        tvOrder.setText("Order Id : " + data.orderId );
        tvItem.setText(data.productName[0]);
        tvPrice.setText(data.totalPrice+" rs...\nOrdered on "+data.date);
        llOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(),OrderDetailActivity.class).putExtra("orderId",data.orderId).putExtra("img",data.productImg).putExtra("product_name",data.productName).putExtra("qty",data.qty).putExtra("topings",data.toping).putExtra("price",data.totalPrice).putExtra("date",data.date));
            }
        });

    }

}

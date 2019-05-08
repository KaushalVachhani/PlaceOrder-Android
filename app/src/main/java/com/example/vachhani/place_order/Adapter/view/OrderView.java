package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.OrderDetailActivity;
import com.example.vachhani.place_order.Data.Order;
import com.example.vachhani.place_order.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.order_row)
public class OrderView extends LinearLayout {

    @ViewById
    TextView tvOrder, tvItem,tvDate,tvDetails;

//    @ViewById
//    ImageView imgProduct;

    @ViewById
    LinearLayout llOrder;

    Order data;

    public OrderView(Context context) {
        super(context);
    }

    public void bind(final Order data) {
        this.data = data;
        int length = count(data.productName);
        for (int i = 0; i < length; i++) {
            tvItem.append("\n"+data.productName[i]+" x ("+data.qty[i]+")");
        }

        tvDate.setText("Ordered On : "+data.date);
        //Picasso.with(getContext()).load(data.productImg).into(imgProduct);
        tvOrder.setText("Order Id : " + data.orderId);
        tvDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), OrderDetailActivity.class).putExtra("orderId", data.orderId).putExtra("img", data.productImg).putExtra("product_name", data.productName).putExtra("qty", data.qty).putExtra("topings", data.toping).putExtra("price", data.totalPrice).putExtra("date", data.date));
            }
        });

    }

    public int count(Object[] array) {
        int c = 0;
        for (Object el : array) {
            if (el != null) c++;
        }
        return c;
    }

}

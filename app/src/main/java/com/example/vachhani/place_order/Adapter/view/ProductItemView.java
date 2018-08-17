package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.WholeProductActivity_;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.product_row)
public class ProductItemView extends LinearLayout {

    @ViewById
    TextView txtProductName,txtPrice;

    @ViewById
    public LinearLayout rlProduct;

    @ViewById
    ImageView imgProduct;

    Product data;

    public ProductItemView(Context context) {
        super(context);
    }

    public void bind(Product data) {
        this.data = data;
        txtProductName.setText(data.productName);
        txtPrice.setText("Rs."+data.productPrice);
        Picasso.with(getContext()).load(data.productImage).into(imgProduct);

    }

    @Click
    void rlProduct() {
        getContext().startActivity(new Intent(getContext(), WholeProductActivity_.class).putExtra("productId",data.productId).putExtra("productName",data.productName).putExtra("productImg",data.productImage).putExtra("productPrice",data.productPrice).putExtra("productDetails",data.productDetails));
    }
}

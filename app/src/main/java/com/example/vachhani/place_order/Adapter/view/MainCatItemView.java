package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.ProductActivity_;
import com.example.vachhani.place_order.Data.Category;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.category_row)
public class MainCatItemView extends LinearLayout {

    @ViewById
    TextView tvCat;

    @ViewById
    public RelativeLayout llMainCat;

    @ViewById
    ImageView imgCat;

    Category data;

    public MainCatItemView(Context context) {
        super(context);
    }

    public void bind(Category data) {
        this.data = data;
        tvCat.setText(data.category_name);
        Picasso.with(getContext()).load(data.img).into(imgCat);
    }

    @Click
    void llMainCat() {
          getContext().startActivity(new Intent(getContext(), ProductActivity_.class).putExtra("parent_id",data.category_id));
    }
}

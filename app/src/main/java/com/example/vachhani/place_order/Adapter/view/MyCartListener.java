package com.example.vachhani.place_order.Adapter.view;

import android.content.Intent;
import android.view.View;

import com.example.vachhani.place_order.Activity.CartActivity_;

class MyCartListener implements View.OnClickListener {


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), CartActivity_.class);
        view.getContext().startActivity(intent);



    }
}

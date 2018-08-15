package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.MenuDisplayActivity_;
import com.example.vachhani.place_order.Data.Tables;
import com.example.vachhani.place_order.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.table_row)
public class TableItemView extends LinearLayout {

    @ViewById
    TextView txtTableNo;

    @ViewById
    public RelativeLayout root;

    @ViewById
    ImageView imgStatus;

    Tables data;

    public TableItemView(Context context) {
        super(context);
    }

    public void bind(Tables data) {
        this.data = data;
        txtTableNo.setText(data.tableNo);
        imgStatus.setVisibility(Integer.parseInt(data.status) == 0 ? VISIBLE : GONE);
    }

    @Click
    void root() {
        if (data.status.equalsIgnoreCase("1")) {
            getContext().startActivity(new Intent(getContext(), MenuDisplayActivity_.class));
        } else
            new AlertDialog.Builder(getContext()).setMessage("opps!!!! \n \nTable is already booked").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();

    }
}

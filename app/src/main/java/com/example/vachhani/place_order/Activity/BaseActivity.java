package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.AppPreferences;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity
public class BaseActivity extends AppCompatActivity {

    AppPreferences appPreferences;
    DataContext dataContext;
    ProgressDialog pd;

    public void loads(){
        dataContext=new DataContext(this);
        appPreferences=new AppPreferences(this);
        pd = Utility.getDialog(this);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Utils.CPref_;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity
public class BaseActivity extends AppCompatActivity {

    DataContext dataContext;
    ProgressDialog pd;

    @Pref
    CPref_ pref;


    public void loads(){
        dataContext=new DataContext(this);
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

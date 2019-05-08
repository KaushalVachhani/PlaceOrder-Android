package com.example.vachhani.place_order.Activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends BaseActivity {

    @ViewById
    EditText edt_name, edt_mobile, edt_email;

    @ViewById
    LinearLayout llRegistration;

    @ViewById
    TextView txtTitle;

    @ViewById
    Button btn_register;

//    @ViewById
//    SignInButton btn_google;

    @ViewById
    Toolbar toolbar;


    @AfterViews
    void init() {
        loads();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pd = Utility.getDialog(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(getString(R.string.registration));
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,OtpActivity_.class).putExtra("number",edt_mobile.getText().toString()).putExtra("name",edt_name.getText().toString()).putExtra("email",edt_email.getText().toString()).putExtra("mobile",edt_mobile.getText().toString()));
            }
        });


    }








}

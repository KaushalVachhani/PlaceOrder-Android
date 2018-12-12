package com.example.vachhani.place_order.Utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface CPref {

    @DefaultBoolean(false)
    boolean isLogIn();

    @DefaultString("")
    public String token();

//    @DefaultLong(0)
//    public long cart_count();
//
//    @DefaultLong(0)
//    public long cart_id();

    @DefaultString("")
    public String userID();

    @DefaultString("")
    public String userName();

    @DefaultString("")
    public String email();

    @DefaultString("")
    public String mobile_num();

    @DefaultInt(0)
    public int table_num();
//
//    @DefaultString("")
//    public String ownCode();
//
//    @DefaultString("")
//    public String referralCode();
}

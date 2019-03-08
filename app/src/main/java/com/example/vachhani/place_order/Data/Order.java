package com.example.vachhani.place_order.Data;

import java.util.Arrays;

public class Order {

    public  String productId[]= new String[10],productImg,totalPrice[] = new String[10],toping[]=new String[10],qty[]=new String[10],orderId,productName[]=new String[10],date;
    public String toString(){
        return "id : " + Arrays.toString(productId) + "\n name : " + Arrays.toString(productName) + "\nqty : " + Arrays.toString(qty);
    }
}

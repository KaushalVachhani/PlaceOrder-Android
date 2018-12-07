package com.example.vachhani.place_order.Data;

import android.content.Entity;

import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "cart")
public class TableCart extends com.mobandme.ada.Entity{

    @TableField(name = "user_id",datatype = DATATYPE_STRING)
    public String user_id;
    @TableField(name = "product_id",datatype = DATATYPE_STRING)
    public String product_id;
    @TableField(name = "qty",datatype = DATATYPE_INTEGER)
    public int qty;
    @TableField(name = "price",datatype = DATATYPE_INTEGER)
    public int price;
    @TableField(name = "unit",datatype = DATATYPE_STRING)
    public String unit;
    @TableField(name = "product_name",datatype = DATATYPE_STRING)
    public String product_name;
    @TableField(name = "product_img",datatype = DATATYPE_STRING)
    public String product_img;
}

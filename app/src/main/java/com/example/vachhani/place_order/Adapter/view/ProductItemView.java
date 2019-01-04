package com.example.vachhani.place_order.Adapter.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EViewGroup(R.layout.product_row)
public class ProductItemView extends LinearLayout {

    int qty = 0;
    DataContext dataContext = new DataContext(getContext());
    TableCart tableCart = null;
    ArrayList<TableCart> list = new ArrayList<>();
    @ViewById
    TextView txtProductName, txtPrice, txtMinus, txtQty, txtAdd;

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
        txtPrice.setText("Rs." + data.productPrice);
        Picasso.with(getContext()).load(data.productImage).into(imgProduct);

    }

    @Click
    void rlProduct() {
        //getContext().startActivity(new Intent(getContext(), WholeProductActivity_.class).putExtra("productId", data.productId).putExtra("productName", data.productName).putExtra("productImg", data.productImage).putExtra("productPrice", data.productPrice).putExtra("productDetails", data.productDetails));
    }

    @Click
    void txtMinus() {
        qty--;
        setView();
        unsetItem();
    }


    @Click
    void txtAdd() {
        qty++;
        setView();
        setItem();

    }

    @Click
    void txtQty() {
        qty++;
        setView();
        setItem();

    }


    //To set the visibility od minus button and display snackbar for cart
    private void setView() {
        if (qty > 0) {
            txtMinus.setVisibility(VISIBLE);
            txtQty.setText(qty + "");
            Snackbar snackbar = Snackbar.make(rlProduct, "view cart ", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.go_to_cart, new MyCartListener());
            snackbar.show();
        } else {
            txtMinus.setVisibility(GONE);
            txtQty.setText("Add");
        }
    }

    private void setItem() {

        int flag = 0;

        try {
            dataContext.userObjectSet.fill();
            for (int i = 0; i < dataContext.userObjectSet.size(); i++) {
                if (dataContext.userObjectSet.get(i).product_id.equals(data.productId)) {
                    tableCart = dataContext.userObjectSet.get(i);
                    tableCart.qty = qty;
                    tableCart.setStatus(Entity.STATUS_UPDATED);
                    dataContext.userObjectSet.save(tableCart);
                    flag = 1;
                }
            }

            if (flag == 0) {
                tableCart = new TableCart();
                tableCart.product_id = data.productId;
                tableCart.product_name = data.productName;
                tableCart.product_img = data.productImage;
                tableCart.price = Integer.parseInt(data.productPrice);
                tableCart.qty = qty;
                try {
                    dataContext.userObjectSet.save(tableCart);
                } catch (AdaFrameworkException e) {
                    e.printStackTrace();
                }
            }


        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }


    }

    private void unsetItem() {

        try {
            dataContext.userObjectSet.fill();
            for (int i = 0; i < dataContext.userObjectSet.size(); i++) {
                if (dataContext.userObjectSet.get(i).product_id.equals(data.productId))
                    tableCart = dataContext.userObjectSet.get(i);
            }
            if (this.qty > 0) {
                tableCart.qty = this.qty;
                tableCart.setStatus(Entity.STATUS_UPDATED);
                dataContext.userObjectSet.save(tableCart);
            } else {
                tableCart.setStatus(Entity.STATUS_DELETED);
                dataContext.userObjectSet.save(tableCart);
            }


        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }

    }


}

package com.example.vachhani.place_order.Adapter.view;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vachhani.place_order.Activity.CartActivity;
import com.example.vachhani.place_order.Data.DataContext;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.cart_row)
public class CartView extends LinearLayout {

    Context context = getContext();
    DataContext dataContext = new DataContext(context);


    @ViewById
    TextView txtProductName, txtTotal, txtRemove, txtTopings;

    @ViewById
    ImageView imgProduct;

    @ViewById
    LinearLayout llCart;

    String temp = "Topings : ";

    TableCart data;

    public CartView(Context context) {
        super(context);
    }

    public void bind(final TableCart data) {
        this.data = data;
        int total = (data.price) * (data.qty);
        txtProductName.setText(data.product_name + " x " + data.qty);
        txtTotal.setText(String.valueOf("Rs " + total));
        txtTopings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //setting Bottom sheet dialog
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                View parentView = ((Activity) context).getLayoutInflater().inflate(R.layout.bottom_sheet_topings, null);
                bottomSheetDialog.setContentView(parentView);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetDialog.show();

                final EditText edtTopings = parentView.findViewById(R.id.edtTopings);

                edtTopings.setText(temp);

                Button btnAdd = parentView.findViewById(R.id.btnAdd);

                //setting on click when add topings clicked
                btnAdd.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        temp = edtTopings.getText().toString();
                        txtTopings.setText(edtTopings.getText().toString());

                        //Adding the topings to the userobject
                        try {
                            dataContext.userObjectSet.fill("product_id=?", new String[]{data.product_id}, null);
                            TableCart cart = dataContext.userObjectSet.get(0);
                            cart.topings = txtTopings.getText().toString();
                            cart.setStatus(Entity.STATUS_UPDATED);
                            Log.d("Toping in view----", cart.topings);
                            dataContext.userObjectSet.save(cart);
                            Snackbar snackbar = Snackbar.make(llCart, "Instruction added!!!!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } catch (AdaFrameworkException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Click
    void txtRemove() {
        try {
            dataContext.userObjectSet.fill("product_id=?", new String[]{data.product_id}, null);
            Log.e("remove___________", String.valueOf(dataContext.userObjectSet.get(0)));
            TableCart cart = dataContext.userObjectSet.get(0);
            cart.setStatus(Entity.STATUS_DELETED);
            dataContext.userObjectSet.save();
            Snackbar snackbar = Snackbar.make(llCart, "Item deleted!!!!", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } catch (AdaFrameworkException e) {
            e.printStackTrace();
        }

        ((CartActivity) context).refresh();
    }


}

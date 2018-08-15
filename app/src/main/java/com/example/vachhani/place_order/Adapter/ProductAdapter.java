package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vachhani.place_order.Data.Category;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    ArrayList<Product> list=new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.product_row,viewGroup,false);
            holder=new Holder(view);
            view.setTag(holder);
        }
        else
        {
            holder= (Holder) view.getTag( );
        }
        holder.tvProduct.setText(list.get(i).productName+"\n Price : "+list.get(i).productPrice+"\n Details : "+list.get(i).productDetails);
        Picasso.with(context).load(list.get(i).productImage).into(holder.img);
        return view;
    }

    static class Holder{

        TextView tvProduct;
        ImageView img;
        public Holder(View view){
            tvProduct=view.findViewById(R.id.tvProduct);
            img=view.findViewById(R.id.imgData);
        }
    }
}

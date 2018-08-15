package com.example.vachhani.place_order.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachhani.place_order.Activity.ProductActivity_;
import com.example.vachhani.place_order.Data.Category;
import com.example.vachhani.place_order.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends BaseAdapter {

    ArrayList<Category> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CategoryAdapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.category_row, viewGroup, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.tv_cat.setText(list.get(i).category_name);
        Picasso.with(context).load(list.get(i).img).into(holder.img_cat);
        Picasso.with(context).load(list.get(i).img).into(holder.imgCat);
        holder.llcrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity(new Intent(context, ProductActivity_.class).putExtra("parent_id","5b4de24136fa05283900004b"));
                Intent intent = new Intent(context, ProductActivity_.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("parent_id", list.get(i).category_id);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }

    static class Holder {

        TextView tv_cat;
        ImageView imgCat;
        RelativeLayout llcrow;
        CircleImageView img_cat;

        public Holder(View view) {
            tv_cat = view.findViewById(R.id.tv_cat);
            img_cat = view.findViewById(R.id.img_cat);
            llcrow = view.findViewById(R.id.llcrow);
            imgCat = view.findViewById(R.id.imgCat);
        }
    }
}

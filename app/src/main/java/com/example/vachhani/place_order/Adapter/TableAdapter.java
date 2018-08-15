package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Data.Tables;

import java.util.ArrayList;

public class TableAdapter extends BaseAdapter {

    ArrayList<Tables> list=new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public TableAdapter(Context context, ArrayList<Tables> list){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        Holder holder;
        if(view==null)
        {
            view=inflater.inflate(R.layout.table_row,viewGroup,false);
            holder=new Holder(view);
            view.setTag(holder);
        }
        else
        {
            holder= (Holder) view.getTag( );
        }
       /* holder.btntbl.setText("table "+list.get(i).tableNo);
        if(list.get(i).status.equalsIgnoreCase("0"))
        {
            GradientDrawable bgShape = (GradientDrawable)holder.btntbl.getBackground();
            bgShape.setColor(Color.RED);
        }
        else
        {
            GradientDrawable bgShape = (GradientDrawable)holder.btntbl.getBackground();
            bgShape.setColor(Color.GRAY);
        }
        holder.btntbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).status.equalsIgnoreCase("0"))
                    context.startActivity(new Intent(context, MenuDisplayActivity.class));
                else
                    Toast.makeText(context, "This table is reserved", Toast.LENGTH_SHORT).show();

            }
        });
*/
        return view;
    }

    static class Holder{

        Button btntbl;
        public Holder(View view){
/*
            btntbl=view.findViewById(R.id.btntbl);
*/
        }
    }
}

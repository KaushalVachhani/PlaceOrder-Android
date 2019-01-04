package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.vachhani.place_order.Adapter.view.OrderView;
import com.example.vachhani.place_order.Adapter.view.OrderView_;
import com.example.vachhani.place_order.Data.Category;
import com.example.vachhani.place_order.Data.Order;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class OrdersAdapter extends RecyclerViewAdapterBase<Category, OrderView>{

    @RootContext
    Context context;

    private List<Order> items = new ArrayList<Order>();

    public void setList(List<Order> uploadDocumentList) {
        items.clear();
        items.addAll(uploadDocumentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<OrderView> holder, int position) {
        final Order document= items.get(position);
        OrderView view = holder.getView();
        view.bind(document);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    protected OrderView onCreateItemView(ViewGroup parent, int viewType) {
        OrderView o = OrderView_.build(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        o.setLayoutParams(lp);
        return o;
    }

    OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }


    public  interface OnDeleteListener{
        void onDelete(Order document);
    }

    public interface  OnEditListener{
        void onEdit(Order document);
    }

    OnEditListener onEditListener;
    public  void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }
}

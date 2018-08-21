package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vachhani.place_order.Adapter.view.CartView;
import com.example.vachhani.place_order.Adapter.view.CartView_;
import com.example.vachhani.place_order.Adapter.view.TableItemView;
import com.example.vachhani.place_order.Adapter.view.TableItemView_;
import com.example.vachhani.place_order.Data.TableCart;
import com.example.vachhani.place_order.Data.Tables;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class CartAdapter extends RecyclerViewAdapterBase<TableCart, CartView>{

    @RootContext
    Context context;

    private List<TableCart> items = new ArrayList<TableCart>();

    public void setList(List<TableCart> uploadDocumentList) {
        items.clear();
        items.addAll(uploadDocumentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<CartView> holder, int position) {
        final TableCart document= items.get(position);
        CartView view = holder.getView();
        view.bind(document);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    protected CartView onCreateItemView(ViewGroup parent, int viewType) {
        CartView o = CartView_.build(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        o.setLayoutParams(lp);
        return o;
    }

    OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }


    public  interface OnDeleteListener{
        void onDelete(CartView document);
    }

    public interface  OnEditListener{
        void onEdit(CartView document);
    }

    OnEditListener onEditListener;
    public  void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }
}

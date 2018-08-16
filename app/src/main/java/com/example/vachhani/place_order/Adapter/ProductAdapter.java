package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vachhani.place_order.Adapter.view.ProductItemView;
import com.example.vachhani.place_order.Adapter.view.ProductItemView_;
import com.example.vachhani.place_order.Adapter.view.TableItemView;
import com.example.vachhani.place_order.Adapter.view.TableItemView_;
import com.example.vachhani.place_order.Data.Product;
import com.example.vachhani.place_order.Data.Tables;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class ProductAdapter extends RecyclerViewAdapterBase<Product, ProductItemView>{

    @RootContext
    Context context;

    private List<Product> items = new ArrayList<Product>();

    public void setList(List<Product> uploadDocumentList) {
        items.clear();
        items.addAll(uploadDocumentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ProductItemView> holder, int position) {
        final Product document= items.get(position);
        ProductItemView view = holder.getView();
        view.bind(document);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    protected ProductItemView onCreateItemView(ViewGroup parent, int viewType) {
        ProductItemView o = ProductItemView_.build(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        o.setLayoutParams(lp);
        return o;
    }

    OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }


    public  interface OnDeleteListener{
        void onDelete(Product document);
    }

    public interface  OnEditListener{
        void onEdit(Product document);
    }

    OnEditListener onEditListener;
    public  void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }
}

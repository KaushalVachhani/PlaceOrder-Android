package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.vachhani.place_order.Adapter.view.MainCatItemView;
import com.example.vachhani.place_order.Adapter.view.MainCatItemView_;
import com.example.vachhani.place_order.Data.Category;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class MainCategoryAdapter extends RecyclerViewAdapterBase<Category, MainCatItemView>{

    @RootContext
    Context context;

    private List<Category> items = new ArrayList<Category>();

    public void setList(List<Category> uploadDocumentList) {
        items.clear();
        items.addAll(uploadDocumentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MainCatItemView> holder, int position) {
        final Category document= items.get(position);
        MainCatItemView view = holder.getView();
        view.bind(document);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    protected MainCatItemView onCreateItemView(ViewGroup parent, int viewType) {
        MainCatItemView o = MainCatItemView_.build(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        o.setLayoutParams(lp);
        return o;
    }

    OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }


    public  interface OnDeleteListener{
        void onDelete(Category document);
    }

    public interface  OnEditListener{
        void onEdit(Category document);
    }

    OnEditListener onEditListener;
    public  void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }
}

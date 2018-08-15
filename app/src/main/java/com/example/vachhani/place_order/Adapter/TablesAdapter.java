package com.example.vachhani.place_order.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vachhani.place_order.Adapter.view.TableItemView;
import com.example.vachhani.place_order.Adapter.view.TableItemView_;
import com.example.vachhani.place_order.Data.Tables;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class TablesAdapter extends RecyclerViewAdapterBase<Tables, TableItemView>{

    @RootContext
    Context context;

    private List<Tables> items = new ArrayList<Tables>();

    public void setList(List<Tables> uploadDocumentList) {
        items.clear();
        items.addAll(uploadDocumentList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewWrapper<TableItemView> holder, int position) {
        final Tables document= items.get(position);
        TableItemView view = holder.getView();
        view.bind(document);

        }

    @Override
    protected TableItemView onCreateItemView(ViewGroup parent, int viewType) {
        TableItemView o = TableItemView_.build(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        o.setLayoutParams(lp);
        return o;
    }

    OnDeleteListener onDeleteListener;
    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.onDeleteListener = onDeleteListener;
    }


    public  interface OnDeleteListener{
         void onDelete(Tables document);
    }

    public interface  OnEditListener{
        void onEdit(Tables document);
    }

    OnEditListener onEditListener;
    public  void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }
}

package com.example.vachhani.place_order.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vachhani.place_order.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private BottomSheetListener bottomSheetListener;
    Button btnAdd;
    EditText edtTopings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_topings, container, false);
        btnAdd = v.findViewById(R.id.btnAdd);
        edtTopings = v.findViewById(R.id.edtTopings);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetListener.onTopingsInserted(edtTopings.getText().toString());
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListener {
        void onTopingsInserted(String s);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement bottomSheetListener");
        }

    }
}
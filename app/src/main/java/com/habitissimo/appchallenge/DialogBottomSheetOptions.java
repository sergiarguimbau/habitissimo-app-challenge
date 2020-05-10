package com.habitissimo.appchallenge;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogBottomSheetOptions extends BottomSheetDialogFragment {

    private BottomSheetOptionsListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_bottom_sheet_options, container, false);

        // Find Bottom Sheet views
        LinearLayout option_share = v.findViewById(R.id.layout_option_share);
        LinearLayout option_edit = v.findViewById(R.id.layout_option_edit);
        LinearLayout option_remove = v.findViewById(R.id.layout_option_remove);

        // Options OnClickListeners
        option_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOptionMethodClicked(getString(R.string.NT_options_share));
                dismiss();
            }
        });

        option_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOptionMethodClicked(getString(R.string.NT_options_edit));
                dismiss();
            }
        });

        option_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOptionMethodClicked(getString(R.string.NT_options_remove));
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetOptionsListener {
        void onOptionMethodClicked(String method);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (BottomSheetOptionsListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement BottomSheetOptionsListener");
        }
    }
}

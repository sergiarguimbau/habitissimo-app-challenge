package com.habitissimo.appchallenge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogFillContact extends DialogFragment {

    private EditText editText_name;
    private EditText editText_phone;
    private EditText editText_email;

    private TextInputLayout inputLayout_name;
    private TextInputLayout inputLayout_phone;
    private TextInputLayout inputLayout_email;


    public DialogFillContact() { /*empty*/ }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_fill_contact, null);

        // Find views
        editText_name = (EditText) view.findViewById(R.id.edittext_name);
        editText_phone = (EditText) view.findViewById(R.id.edittext_phone);
        editText_email = (EditText) view.findViewById(R.id.edittext_email);

        inputLayout_name = (TextInputLayout) view.findViewById(R.id.inputlayout_name);
        inputLayout_phone = (TextInputLayout) view.findViewById(R.id.inputlayout_phone);
        inputLayout_email = (TextInputLayout) view.findViewById(R.id.inputlayout_email);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getActivity().getString(R.string.contact_details));
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: Data Validation pending
                String name = editText_name.getText().toString();
                String phone = editText_phone.getText().toString();
                String email = editText_email.getText().toString();
                ((QuotationActivity)getActivity()).setFillContact(name, phone, email);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }


}

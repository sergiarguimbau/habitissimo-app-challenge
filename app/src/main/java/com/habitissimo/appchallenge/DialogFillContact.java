package com.habitissimo.appchallenge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // Update Text Input Layout error state for: name, phone, email
        editText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(inputLayout_name.isErrorEnabled()){
                    boolean valid_name = charSequence.toString().matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
                    inputLayout_name.setError(valid_name ? null : " ");
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });

        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(inputLayout_phone.isErrorEnabled()) {
                    boolean valid_phone = charSequence.toString().matches("(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}"); // Valid for Spain
                    inputLayout_phone.setError(valid_phone ? null : " ");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });

        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(inputLayout_email.isErrorEnabled()){
                    boolean valid_email = Patterns.EMAIL_ADDRESS.matcher(charSequence.toString()).matches();
                    inputLayout_email.setError(valid_email ? null : " ");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}

        });

        // Bu8ld dialog with custom view and two buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getActivity().getString(R.string.contact_details));
        builder.setPositiveButton(R.string.confirm, null); // Custom listener validates input
        builder.setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                // Prevent dialog dismiss with this Custom listener for the Positive Button
                // to be capable of validating the input of the EditTexts: name, phone, email
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Data Validation
                        String name = editText_name.getText().toString();
                        String phone = editText_phone.getText().toString();
                        String email = editText_email.getText().toString();

                        boolean valid_name = name.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
                        boolean valid_phone = phone.matches("(\\+34|0034|34)?[ -]*(6|7)[ -]*([0-9][ -]*){8}"); // Valid for Spain
                        boolean valid_email = Patterns.EMAIL_ADDRESS.matcher(email).matches();

                        inputLayout_name.setError(valid_name ? null : " ");
                        inputLayout_phone.setError(valid_phone ? null : " ");
                        inputLayout_email.setError(valid_email ? null : " ");

                        if (valid_name && valid_phone && valid_email) {
                            ((QuotationActivity)getActivity()).setFillContact(name, phone, email);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), getString(R.string.contact_validate), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return dialog;
    }


}

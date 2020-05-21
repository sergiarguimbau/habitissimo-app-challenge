package com.habitissimo.appchallenge;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogContact extends DialogFragment {

    private String name;
    private String phone;
    private String email;
    private String location;

    public DialogContact() { /*empty*/ }

    public DialogContact(Contact contact) {
        this.name = contact.getName();
        this.phone = contact.getPhone();
        this.email = contact.getEmail();
        this.location = contact.getLocation();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_contact, null);

        // Find views
        TextView text_name = (TextView) view.findViewById(R.id.text_name);
        TextView text_phone = (TextView) view.findViewById(R.id.text_phone);
        TextView text_email = (TextView) view.findViewById(R.id.text_email);
        TextView text_location = (TextView) view.findViewById(R.id.text_location);

        // Set Texts
        text_name.setText(name);
        text_phone.setText(phone);
        text_email.setText(email);
        text_location.setText(location);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getActivity().getString(R.string.contact_details));
        builder.setPositiveButton(android.R.string.copy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Copy Contact Details to Clipboard
                Toast.makeText(getContext(), getString(R.string.contact_copied), Toast.LENGTH_SHORT).show();
                String contact = getString(R.string.contact_copy, name, phone, email, location);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("contact", contact);
                clipboard.setPrimaryClip(clip);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }


}

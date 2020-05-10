package com.habitissimo.appchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogBottomSheetOptions.BottomSheetOptionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.quotation_requests));

        // Inflate fragment_container with FragmentQuotationRequests (only once)
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new FragmentQuotationRequests()).commit();
        }

    }

    @Override
    public void onOptionMethodClicked(String method) {

        if (method.equals(getString(R.string.NT_options_share))) {
            Toast.makeText(getApplicationContext(), "Share clicked", Toast.LENGTH_SHORT).show();
        } else if (method.equals(getString(R.string.NT_options_edit))) {
            Toast.makeText(getApplicationContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
        } else if (method.equals(getString(R.string.NT_options_remove))) {
            Toast.makeText(getApplicationContext(), "Remove clicked", Toast.LENGTH_SHORT).show();
        }
    }
}

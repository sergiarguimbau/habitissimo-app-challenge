package com.habitissimo.appchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
    public void onOptionMethodClicked(int position, String option) {

        if (option.equals(getString(R.string.NT_options_share))) {
            Toast.makeText(getApplicationContext(), "Share clicked " + position, Toast.LENGTH_SHORT).show();
        } else if (option.equals(getString(R.string.NT_options_edit))) {
            Toast.makeText(getApplicationContext(), "Edit clicked " + position, Toast.LENGTH_SHORT).show();
        } else if (option.equals(getString(R.string.NT_options_delete))) {
            Toast.makeText(getApplicationContext(), "Remove clicked " + position, Toast.LENGTH_SHORT).show();
        }
    }
}

package com.habitissimo.appchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.quotation_requests));

        // Inflate fragment_container with FragmentQuotationRequests
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new FragmentQuotationRequests()).commit();
    }
}

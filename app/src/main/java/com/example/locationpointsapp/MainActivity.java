package com.example.locationpointsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent inten = new Intent(this, MapsLocationActivity.class);
        startActivity(inten);
        finish();
    }
}

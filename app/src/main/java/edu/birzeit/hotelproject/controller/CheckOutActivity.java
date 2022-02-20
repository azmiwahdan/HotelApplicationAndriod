package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.birzeit.hotelproject.R;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
    }

    public void checkOutOnClick(View view) {

        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }
}
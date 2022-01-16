package edu.birzeit.hotelproject.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;

public class LogoutActivity extends AppCompatActivity {

    TextView logoutText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        logoutText = findViewById(R.id.logoutText);

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                SharedPreferences pref = getSharedPreferences("CheckBox" , MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("rememberMe" , "false");
                editor.apply();
                finish();
            }
        });

        BottomNavigationView BNV = findViewById(R.id.nav_id);

        BNV.setSelectedItemId(R.id.logout);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.roomsBooking:
                        startActivity(new Intent(getApplicationContext(), AvailableRoomsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.contactus:
                        startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.logout:
                        return true;
                }
                return false;
            }
        });

    }
}
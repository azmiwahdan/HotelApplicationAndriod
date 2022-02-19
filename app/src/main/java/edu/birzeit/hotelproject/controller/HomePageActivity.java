package edu.birzeit.hotelproject.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.birzeit.hotelproject.R;

public class HomePageActivity extends AppCompatActivity {

    private TextView temp_id;
    private final String TEMP_URL = "https://api.openweathermap.org/data/2.5/weather?q=Ramallah&appid=605aed8bd0a0b035866cd2ab30832bee";
//    private final String APP_API = "605aed8bd0a0b035866cd2ab30832bee";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        temp_id = findViewById(R.id.temp_id);
        getTemp();


        BottomNavigationView BNV = findViewById(R.id.nav_id);

        BNV.setSelectedItemId(R.id.homepage);

        BNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homepage:
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
                        startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void getTemp() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TEMP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("ajkshdkjahsd","kjashdkasdasda");
                String temp = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = new JSONArray("weather");
//                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    double temp_d = jsonObjectMain.getDouble("temp") - 273.15;
                    temp_id.setText("Temperature: " + String.valueOf(temp_d).substring(0,3) + "C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
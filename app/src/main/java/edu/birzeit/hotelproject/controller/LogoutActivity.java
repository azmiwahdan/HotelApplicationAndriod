package edu.birzeit.hotelproject.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Reserve;
import edu.birzeit.hotelproject.models.Room;
import edu.birzeit.hotelproject.models.UserInfoShared;

public class LogoutActivity extends AppCompatActivity {

    TextView logoutText;
    public static final String CUSTOMER_PREF = "CUSTOMER_PREF";
    private RecyclerView recyclerView ;
    public List<Reserve> arrayListReserve = new ArrayList<>();
    private TextView textViewUserName ;
    private TextView textViewPhoneNumber ;
    public UserInfoShared user_info = new UserInfoShared();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        prepareNavigationBar();
        setTexLogOutOnAction();

        textViewUserName = findViewById(R.id.userName);
        textViewPhoneNumber = findViewById(R.id.phoneNumber);

        recyclerView = findViewById(R.id.recyclerViewReservation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendGetRequest();

        SharedPreferences sharedPreferences = getSharedPreferences(user_info.getCustomerPref(),MODE_PRIVATE);
        String name = sharedPreferences.getString(user_info.getUSERNAME(),"");
        textViewUserName.setText(name);

        String phone = sharedPreferences.getString(user_info.getPhoneNumber(),"");
        textViewPhoneNumber.setText(phone);


    }

    private void sendGetRequest(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/hotalAppBackend/controllers/reservationController/getReservation.php?user_name=6";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //showAlert("info" , response);
                        Log.d("response = " , response);

                       try {

                           JSONArray jsonArray = new JSONArray(response);
                           //ArrayList<Reserve> arrayList = new ArrayList<>();


                           for (int i = 0 ; i < jsonArray.length() ; i++){

                               JSONObject jsonObject = jsonArray.getJSONObject(i);

                               String roomNum = jsonObject.get("room").toString();
                               String roomType = jsonObject.get("start_date").toString();
                               String roomPrice = jsonObject.get("end_date").toString();

                               Reserve reserve = new Reserve(Integer.parseInt(roomNum) , roomType , roomPrice);
                               arrayListReserve.add(reserve);

                           }

                           AdapterReserve adapterReserve = new AdapterReserve(LogoutActivity.this , arrayListReserve);
                           recyclerView.setAdapter(adapterReserve);





                        } catch (JSONException e) {
                            showAlert("Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showAlert("Error", error.toString());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void showAlert(String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(LogoutActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();

    }

    private void prepareNavigationBar(){

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

    private void setTexLogOutOnAction(){


        logoutText = findViewById(R.id.logoutText);

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_logout_pref();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finishAffinity();
                SharedPreferences pref = getSharedPreferences("CheckBox" , MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("rememberMe" , "false");
                editor.apply();
            }
        });

    }

    private void user_logout_pref() {
        UserInfoShared userInfoShared = new UserInfoShared();
        SharedPreferences sharedPreferences = getSharedPreferences(userInfoShared.getUserCheckSignPref(),MODE_PRIVATE);
        sharedPreferences.edit().remove(userInfoShared.getUserCheckSign()).commit();
    }


}
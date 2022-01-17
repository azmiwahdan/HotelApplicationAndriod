package edu.birzeit.hotelproject.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import edu.birzeit.hotelproject.R;

public class ReserveConfirmationActivity extends AppCompatActivity {

    private EditText customer_username;
    private EditText customer_phone_number;
    private EditText customer_credit;
    private EditText customer_msg;
    private EditText customer_start_date;
    private EditText customer_end_date;
    public String serverURL = "http://10.0.2.2/hotalAppBackend/controllers/bookingController/post.php";
    public static final String USERNAME = "USERNAME";
    public static final String CREDIT_CARD = "CREDIT_CARD";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CUSTOMER_PREF = "CUSTOMER_PREF";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    String customer_id = "";
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_page_confirmation);
        customer_username = findViewById(R.id.customer_username);
        customer_credit = findViewById(R.id.customer_credit);
        customer_msg = findViewById(R.id.customer_msg);
        customer_phone_number = findViewById(R.id.customer_phone_number);
        customer_start_date = findViewById(R.id.customer_start_date);
        customer_end_date = findViewById(R.id.customer_end_date);
        queue = Volley.newRequestQueue(this);
        setEditTextFields();

    }

    private void setEditTextFields() {
        SharedPreferences sharedPreferences = getSharedPreferences(CUSTOMER_PREF,MODE_PRIVATE);
        customer_username.setText(sharedPreferences.getString(USERNAME,""), TextView.BufferType.EDITABLE);
        customer_credit.setText(sharedPreferences.getString(CREDIT_CARD,""), TextView.BufferType.EDITABLE);
        customer_phone_number.setText(sharedPreferences.getString(PHONE_NUMBER,""), TextView.BufferType.EDITABLE);
        customer_id = sharedPreferences.getString(CUSTOMER_ID,"");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void reserve_action(View view) {
        InsertData insertData = new InsertData();
        Thread thread = new Thread(insertData);
        thread.start();
        goDonePage();

    }

    class InsertData implements Runnable{

        Bundle extras = getIntent().getExtras();
        String customer = customer_id;
        String room = extras.getString("RoomID");
        String start_date = customer_start_date.getText().toString();
        String end_date = customer_end_date.getText().toString();
        String customer_message = customer_msg.getText().toString();

        @Override
        public void run() {
            insertData(customer,room,start_date,end_date,customer_message);
        }
    }

    private void insertData(String customerID, String roomID, String customer_start_date, String customer_end_date, String customer_msg) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Room reserved successfully",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error in reservation process",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> key = new HashMap<>();
                key.put("customer",customerID);
                key.put("room",roomID);
                key.put("start_date",customer_start_date);
                key.put("end_date",customer_end_date);
                key.put("customer_message",customer_msg);

                return key;
            }

        };

        queue.add(stringRequest);

    }



    private void goDonePage() {
        Intent intent = new Intent(this,HomePageActivity.class);
        startActivity(intent);
    }
}
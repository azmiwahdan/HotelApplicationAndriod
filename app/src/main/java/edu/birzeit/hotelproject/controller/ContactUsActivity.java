package edu.birzeit.hotelproject.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.UserInfoShared;

public class ContactUsActivity extends AppCompatActivity {
    private EditText customer_name;
    private EditText customer_email;
    private EditText customer_topic;
    private EditText customer_msg;
    public SharedPreferences sharedPreferences;
    public UserInfoShared user_info = new UserInfoShared();
    public String serverURL = "http://10.0.2.2/hotel_app_backend/controllers/contactController/post.php";
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        BottomNavigationView BNV = findViewById(R.id.nav_id);
        BNV.setSelectedItemId(R.id.contactus);
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
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        customer_name = findViewById(R.id.customer_name);
        customer_email = findViewById(R.id.customer_email);
        customer_topic = findViewById(R.id.customer_topic);
        customer_msg = findViewById(R.id.customer_msg);
        queue = Volley.newRequestQueue(this);
        setSharedPref();

    }

    private void setSharedPref() {
        sharedPreferences = getSharedPreferences(user_info.getCustomerPref(),MODE_PRIVATE);
        setValueEditText();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void send_btn(View view) {
        if(check_fields()){
            InsertDataContact insertDataContact = new InsertDataContact();
            Thread thread = new Thread(insertDataContact);
            thread.start();
//            Toast.makeText(this, "Thank you!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean check_fields() {
        String[] arr_fields = {
                customer_name.getText().toString(),
                customer_email.getText().toString(),
                customer_topic.getText().toString(),
                customer_msg.getText().toString()
        };

        for(String field : arr_fields){
            if(field.equalsIgnoreCase("")){
                return false;
            }
        }
        return true;
    }

    private void setValueEditText() {
        customer_name.setText(sharedPreferences.getString(user_info.getUSERNAME(),"None"));
    }

    private class InsertDataContact implements Runnable{
        String customer_id = sharedPreferences.getString(user_info.getCustomerId(),"");
        String customer_email = ContactUsActivity.this.customer_email.getText().toString();
        String customer_topic = ContactUsActivity.this.customer_topic.getText().toString();
        String customer_msg = ContactUsActivity.this.customer_msg.getText().toString();

        @Override
        public void run() {
            insertDataContact(customer_id,customer_email,customer_topic,customer_msg);
        }
    }

    private void insertDataContact(String customer_id, String customer_email, String customer_topic, String customer_msg) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Sent successfully",Toast.LENGTH_SHORT).show();
                        Log.d("Yesss","HOORAAYY");
                        goToHomeIntent();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error in process",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> key = new HashMap<>();
                key.put("id",customer_id);
                key.put("email",customer_email);
                key.put("topic",customer_topic);
                key.put("message",customer_msg);
                return key;
            }
        };

        queue.add(stringRequest);
    }

    private void goToHomeIntent() {
        Intent intent = new Intent(this,HomePageActivity.class);
        startActivity(intent);//
    }
}
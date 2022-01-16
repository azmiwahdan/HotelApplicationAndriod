package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Customer;
import edu.birzeit.hotelproject.services.LoginService;

public class AddRoomActivity extends AppCompatActivity {
    EditText roomNumberEditText,roomTypeEditText,roomPriceEditText,roomDescriptionEditText;
    private String roomNumber;
    private String roomType;
    private String roomPrice;
    private String roomDescription;
    private List<Customer>customerList;
    public static String EXTRA_MESSAGE;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Intent intent=getIntent();

        setViews();
    }

    private void setViews() {
        roomNumberEditText=findViewById(R.id.roomnumberID);
        roomTypeEditText=findViewById(R.id.roomtypeID);
        roomPriceEditText=findViewById(R.id.roompriceID);
        roomDescriptionEditText=findViewById(R.id.roomdescID);
    }

    private void getFieldsString(){
        roomNumber=roomNumberEditText.getText().toString();
        roomType=roomTypeEditText.getText().toString();
        roomPrice=roomPriceEditText.getText().toString();
        roomDescription=roomDescriptionEditText.getText().toString();
    }


    public void addRoom(View view) {
        getFieldsString();
      PostRoom postRoom=new PostRoom();
        Thread thread = new Thread(postRoom);
        thread.start();
    }


    class PostRoom implements Runnable{
        @Override
        public void run() {
            String url="http://10.0.2.2/hotalAppBackend/controllers/RoomController/post.php";
            RequestQueue queue = Volley.newRequestQueue(AddRoomActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("TAG", "RESPONSE IS " + response);
                    if (response.equalsIgnoreCase("New record created successfully")){
                        Toast.makeText(AddRoomActivity.this,"room added succefully",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AddRoomActivity.this,ReceptionMenue.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(AddRoomActivity.this,"Operation failed, please try again!",Toast.LENGTH_LONG).show();

                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // method to handle errors.
                    Toast.makeText(AddRoomActivity.this,
                            "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    Log.d("errorr",error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    // as we are passing data in the form of url encoded
                    // so we are passing the content type below
                    return "application/x-www-form-urlencoded; charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() {

                    // below line we are creating a map for storing
                    // our values in key and value pair.
                    Map<String, String> params = new HashMap<String, String>();

                    // on below line we are passing our
                    // key and value pair to our parameters.

                    params.put("room_number", roomNumber);
                    params.put("room_type",roomType);
                    params.put("room_price",roomPrice);
                    params.put("imageUrl","r1.jpg");
                    params.put("room_description",roomDescription);
                    params.put("room_reserve","false");

                    // at last we are returning our params.
                    return params;
                }
            };
            // below line is to make
            // a json object request.
            queue.add(request);
        }
    }
}

package edu.birzeit.hotelproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Room;

public class AvailableRoomsActivity extends AppCompatActivity {

    public List<Room> available_rooms = new ArrayList<>();
    public Gson gsonObjectRoom = new Gson();
    public static final String URL_CUSTOMER = "http://10.0.2.2:80/hotalAppBackend/controllers/RoomController/get.php";
    public RecyclerView recyclerView;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);

        BottomNavigationView BNV = findViewById(R.id.nav_id);
        BNV.setSelectedItemId(R.id.roomsBooking);
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
                        startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.available_room_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);
        GetData getData = new GetData();
        Thread thread = new Thread(getData);
        thread.start();
    }


    class GetData implements Runnable{

        @Override
        public void run() {
            getAvailableRooms();
        }
    }

    public void getAvailableRooms() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CUSTOMER,
                new Response.Listener<String>() {
                    JSONObject jsonRoom = null;
                    JSONArray jsonArrayRoom = null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsonRoom = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            jsonArrayRoom = jsonRoom.getJSONArray("rooms");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray finalJsonArrayRoom = jsonArrayRoom;
                        String json_room_string = finalJsonArrayRoom.toString();
                        Room[] rooms = gsonObjectRoom.fromJson(json_room_string,Room[].class);

                        for (Room room : rooms){
//                            available_rooms.add("Number : " + room.getRoom_number() + " " + "Type :"
//                                    + room.getRoom_type() + "  price : " + room.getRoom_price() +"is Reserved ?"
//                                    + room.isRoom_reserve());
                            if(!room.isRoom_reserve()){
                                available_rooms.add(room);
                            }

                        }

                        AdapterForRoom room_adapter = new AdapterForRoom(AvailableRoomsActivity.this,available_rooms);
                        recyclerView.setAdapter(room_adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


}
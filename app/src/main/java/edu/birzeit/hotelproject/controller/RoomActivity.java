package edu.birzeit.hotelproject.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


public class RoomActivity extends AppCompatActivity {
    private ListView roomListView;
    private RequestQueue queue;

    private Gson gson = new Gson();
    private List<String> rooms = new ArrayList<>();
    String url = "http://10.0.2.2:80/hotalAppBackend/controllers/RoomController/get.php";
    List<Room>roomList=new ArrayList<>();
    List<Room>singleRoom,doubleRoom;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        recycler=findViewById(R.id.rooms_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        queue = Volley.newRequestQueue(this);
        GetData getData=new GetData();
        Thread thread=new Thread(getData);
        thread.start();

    }


    class GetData implements Runnable{

        @Override
        public void run() {
         getRooms();

        }
    }

    class CollectRoomType implements Runnable{

        @Override
        public void run() {
            singleRoom=new ArrayList<>();
            doubleRoom=new ArrayList<>();
            for (Room room : roomList) {
                if (room.getRoom_type().equalsIgnoreCase("single")){
                    singleRoom.add(room);
                }else{
                    doubleRoom.add(room);
                }
            }
            Log.d("length single room",singleRoom.size()+"");
        }
    }

    public void getRooms() {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

            //Response is a string that needs to be converted to an a json object then json array
                    JSONObject  jsnobject =null;
                    JSONArray  jsonArray=null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsnobject = new JSONObject(response);
                            Log.d("room json object",jsnobject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonArray = jsnobject.getJSONArray("rooms");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray  finalJsonArray = jsonArray;
                        String g = finalJsonArray.toString();
                        Log.d("room messages",g);
                        Room[] roomsArray = gson.fromJson(g, Room[].class);


                        for (Room room : roomsArray) {
                                rooms.add("Number : " + room.getRoom_number() + " " + "Type :" + room.getRoom_type() + "  price : " + room.getRoom_price() +"is Reserved ?" + room.isRoom_reserve());
                                roomList.add(room);
                            }

                        AdapterForRoom adapter=new AdapterForRoom(RoomActivity.this,roomList);
                        recycler.setAdapter(adapter);

                        CollectRoomType collectRoomType=new CollectRoomType();
                        Thread thread=new Thread(collectRoomType);
                        thread.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}
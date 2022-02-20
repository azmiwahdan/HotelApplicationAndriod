package edu.birzeit.hotelproject.controller;

import static java.util.List.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Reservations;
import edu.birzeit.hotelproject.models.Room;

public class AllRservationActivity extends AppCompatActivity {
    private RequestQueue queue;
    private Gson gson = new Gson();
    private List<String> bookingsString = new ArrayList<>();
    String url = "http://10.0.2.2:80/hotalAppBackend/controllers/bookingController/get.php";
    List<Reservations>books=new ArrayList<>();//this is will use in Recycler view.
    private RecyclerView recycler;
    List<Reservations> list= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rservation);


        recycler = findViewById(R.id.books_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));




        queue = Volley.newRequestQueue(this);

        GetData getData=new GetData();
        Thread thread=new Thread(getData);
        thread.start();



    }



    class GetData implements Runnable{

        @Override
        public void run() {
            getBookings();
            Log.d("length",books.size()+"");


        }
    }



    public void getBookings() {

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    //Response is a string that needs to be converted to an a json object then json array
                    JSONObject jsnobject =null;
                    JSONArray jsonArray=null;
                    @Override
                    public void onResponse(String response) {
                        try {
                            jsnobject = new JSONObject(response);
                            Log.d("room json object",jsnobject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonArray = jsnobject.getJSONArray("bookings");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray  finalJsonArray = jsonArray;
                        String g = finalJsonArray.toString();
                        Log.d("room messages",g);


                        Reservations[]reservations=gson.fromJson(g,Reservations[].class);


                        for (Reservations reservation : reservations) {
                            bookingsString.add("customer id :" + reservation.getCustomer() +"room id :" + reservation.getRoom() +" start date : " + reservation.getStart_date() + "enda date : " + reservation.getEnd_date());
                            books.add(reservation);
                        }

                        AdapterForAllReservation adapter = new AdapterForAllReservation(AllRservationActivity.this,
                                books);
                        recycler.setAdapter(adapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }
}
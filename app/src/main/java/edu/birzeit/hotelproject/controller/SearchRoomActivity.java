package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.birzeit.hotelproject.R;

public class SearchRoomActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textViewRoomNum;
    private TextView textViewRoomType;
    private TextView textViewRoomPrice;
    private ImageView imageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_room);
        setAllContentView();

    }


    private void setAllContentView() {

        editText = findViewById(R.id.etSearch);
        textViewRoomNum = findViewById(R.id.roomNum);
        textViewRoomType = findViewById(R.id.roomType);
        textViewRoomPrice = findViewById(R.id.roomPri);
        imageView = findViewById(R.id.imageView);

    }

    @SuppressLint("LongLogTag")
    public void imageSearchOnClick(View view) {

        String roomNum = editText.getText().toString().trim();
        if (roomNum.trim().equalsIgnoreCase(""))
            showAlert("Warning", "Pleas enter room number");
        else {
            GetSearch getSearch = new GetSearch();
            Thread thread = new Thread(getSearch);
            thread.start();
        }

    }

    class GetSearch implements Runnable{

        @Override
        public void run() {
            String roomNum = editText.getText().toString().trim();
            sendGetRequest(roomNum);

        }
    }

    private void sendGetRequest(String roomNumber) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/hotalAppBackend/controllers/searchController/getRoom.php?key=" + roomNumber;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equalsIgnoreCase("null")) {
                            showAlert("Information", "room number not found ..");
                            return;
                        }

                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String roomNum = jsonObject.get("room_number").toString();
                            String roomType = jsonObject.get("room_type").toString();
                            String roomPrice = jsonObject.get("room_price").toString();

                            textViewRoomNum.setText("Room Number : " + roomNum);
                            textViewRoomType.setText("Room Type : " + roomType);
                            textViewRoomPrice.setText("Room Price : " + roomPrice + "$");
                            //imageView.setImageResource(R.drawable.room2);

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

        AlertDialog alertDialog = new AlertDialog.Builder(SearchRoomActivity.this)
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
}



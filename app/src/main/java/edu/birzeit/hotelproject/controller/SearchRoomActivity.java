package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import edu.birzeit.hotelproject.R;

public class SearchRoomActivity extends AppCompatActivity {


    private EditText editText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_room);
        editText = findViewById(R.id.etSearch);

    }




    @SuppressLint("LongLogTag")
    public void imageSearchOnClick(View view) {

        Log.d("image search : ", "clicked !!!");
        String roomId = editText.getText().toString().trim() ;
        Log.d("room id String edit text = " , roomId);
        sendGetRequest(roomId);

    }

    private void sendGetRequest(String roomId){

        Log.d("method get request : ", "entered");

        RequestQueue requestQueue = Volley.newRequestQueue(SearchRoomActivity.this);
        String url = "http://10.0.2.2//hotel_app_backend/controllers/searchController/getRoom.php";

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Request = " , response);
                Toast.makeText(SearchRoomActivity.this , response , Toast.LENGTH_SHORT);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response error = ", error.toString());
                Toast.makeText(SearchRoomActivity.this , error.toString() , Toast.LENGTH_SHORT);
            }
        });

    }
}



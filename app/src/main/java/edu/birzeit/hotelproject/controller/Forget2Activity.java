package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import edu.birzeit.hotelproject.MainActivity;
import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.UserInfoShared;

public class Forget2Activity extends AppCompatActivity {

    private EditText usernameId;
    private RequestQueue queue;
    private String url = "http://10.0.2.2/hotalAppBackend/controllers/ForgetController/post.php";
//    public UserInfoShared user_info = new UserInfoShared();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget2);
        usernameId = findViewById(R.id.usernameId);
        queue = Volley.newRequestQueue(this);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        changePassword();
        Toast.makeText(this,"Password Updated",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void changePassword() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(),"Room reserved successfully",Toast.LENGTH_SHORT).show();
                        Log.d("Yesss","HOORAAYY");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error in password",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Bundle extras = getIntent().getExtras();
                Map<String, String> key = new HashMap<>();
                key.put("username",extras.getString("username"));
                key.put("password",usernameId.getText().toString());
                return key;
            }

        };

        queue.add(stringRequest);
    }
}
package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.birzeit.hotelproject.R;

public class Forget1Activity extends AppCompatActivity {

    private EditText email_Id_conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget1);
        email_Id_conf = findViewById(R.id.email_Id_conf);
    }

    public void checkEmail(View view) {
        Intent intent = new Intent(this,Forget2Activity.class);
        intent.putExtra("username",email_Id_conf.getText().toString());
        Toast.makeText(this,"User already confirmed",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.birzeit.hotelproject.R;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
    }

    public void checkOutOnClick(View view) {

        showAlert("Information" , "Check Out successfully");
    }

    private void showAlert(String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(CheckOutActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent(CheckOutActivity.this , HomePageActivity.class);
                        startActivity(intent);
                    }
                })
                .create();
        alertDialog.show();

    }
}
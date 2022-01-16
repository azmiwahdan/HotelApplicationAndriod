package edu.birzeit.hotelproject.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.birzeit.hotelproject.R;



public class BookDetailsActivity extends AppCompatActivity {

    private TextView textViewRoomNumber ;
    private TextView textViewRoomType ;
    private TextView textViewRoomDescription;
    private TextView textViewRoomPrice ;
    String roomID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        setAllContentView();
        getDataFromIntent();
    }

    private void setAllContentView(){

        textViewRoomNumber = findViewById(R.id.room_number_details);
        textViewRoomType = findViewById(R.id.room_type_details);
        textViewRoomDescription = findViewById(R.id.room_descreption_details);
        textViewRoomPrice = findViewById(R.id.room_price_details);

    }

    private void getDataFromIntent(){

        Bundle extras = getIntent().getExtras();

        if (extras != null){

            String roomNum = extras.getString("RoomNumber");
            String roomType = extras.getString("RoomType");
            String roomDesc = extras.getString("RoomDescreption");
            String roomPrice = extras.getString("RoomPrice");
            roomID = String.valueOf(extras.getInt("RoomID"));
            fillInformationInViews(roomNum , roomType , roomDesc , roomPrice);

        }

    }

    private void fillInformationInViews(String roomNum , String roomType , String roomDesc , String roomPrice){

        textViewRoomNumber.setText(roomNum);
        textViewRoomType.setText(roomType);
        textViewRoomDescription.setText(roomDesc);
        textViewRoomPrice.setText(roomPrice);

    }

    public void btnReserveOnClick(View view) {

        String[] arrDataCollection = collectDataFromViews();

        Intent intent = new Intent(this , ReserveConfirmationActivity.class);
        intent.putExtra("RoomNumber" , arrDataCollection[0]);
        intent.putExtra("RoomType" , arrDataCollection[1]);
        intent.putExtra("RoomPrice" , arrDataCollection[2]);
        intent.putExtra("RoomDescreption" , arrDataCollection[3]);
        intent.putExtra("RoomID",roomID);
        startActivity(intent);

    }

    private String[] collectDataFromViews() {

        String roomNumber = textViewRoomNumber.getText().toString();
        String roomType = textViewRoomType.getText().toString();
        String roomDescription = textViewRoomDescription.getText().toString();
        String roomPrice = textViewRoomPrice.getText().toString();

        String[] arrDataCollection = new String[]{roomNumber , roomType , roomPrice , roomDescription};
        return arrDataCollection ;
    }
}
package edu.birzeit.hotelproject.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.birzeit.hotelproject.R;
import edu.birzeit.hotelproject.models.Customer;

public class customerActivity extends AppCompatActivity {
    List<Customer>customerList;
    List<String> customerStrings;
    Gson gson=new Gson();
    String cusString;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer);
        recycler=findViewById(R.id.customer_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        cusString=intent.getStringExtra(ReceptionMenue.CUSTOMERS_MESSAGE);
        GetData getData=new GetData();
        Thread thread=new Thread(getData);
        thread.start();

    }

    public void getCustomers(){
        customerList=new ArrayList<>();
        customerStrings =new ArrayList<>();

        Customer[]customersArray=gson.fromJson(cusString,Customer[].class);

        for (Customer customer : customersArray) {
            customerStrings.add(customer.getCustomer_name());
            customerList.add(customer);
        }
            AdapterForCustomer adapter=new AdapterForCustomer(customerActivity.this,
                    customerList);
        recycler.setAdapter(adapter);
    }

    class GetData implements  Runnable{

        @Override
        public void run() {

            getCustomers();
        }
    }
}
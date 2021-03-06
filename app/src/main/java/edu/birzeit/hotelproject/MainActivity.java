package edu.birzeit.hotelproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import edu.birzeit.hotelproject.controller.Forget1Activity;
import edu.birzeit.hotelproject.controller.Forget2Activity;
import edu.birzeit.hotelproject.controller.HomePageActivity;
import edu.birzeit.hotelproject.controller.ReceptionMenue;
import edu.birzeit.hotelproject.controller.SignUpActivity;
import edu.birzeit.hotelproject.models.Customer;
import edu.birzeit.hotelproject.models.Receptionist;
import edu.birzeit.hotelproject.models.UserInfoShared;
import edu.birzeit.hotelproject.services.LoginService;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    UserInfoShared userInfoShared = new UserInfoShared();


    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor shared_editor;
    private SharedPreferences.Editor editor;
    List<Receptionist> receptionistList = new ArrayList<>();
    List<Customer> customerList = new ArrayList<>();
    private RequestQueue queue;
    String urlReceptions = "http://10.0.2.2/hotalAppBackend/controllers/receptionController/get.php";
    String urlCustomers = "http://10.0.2.2/hotalAppBackend/controllers/customerController/get.php";
    Gson gson = new Gson();
    String customers;
    public static String EXTRA_MESSAGE;
    public static String CUSTOMER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        check_user_login();

        queue = Volley.newRequestQueue(this);
        setViews();

        setSharedPref();

        checkPreference();
        GetData getData = new GetData();
        Thread thread = new Thread(getData);
        thread.start();


        Intent intent = getIntent();
        String message=intent.getStringExtra(SignUpActivity.EXTRA_MESSAGE);
        if (message!=null){
            Customer customer=gson.fromJson(message,Customer.class);
            usernameEditText.setText(customer.getCustomer_username());
            passwordEditText.setText(customer.getCustomer_password());
        }



        findViewById(R.id.text_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra(EXTRA_MESSAGE, customers);
                startActivity(intent);
                finish();
            }
        });
    }

    private void check_user_login() {
        SharedPreferences sharedPreferences = getSharedPreferences(userInfoShared.getUserCheckSignPref(),MODE_PRIVATE);
//        String str = sharedPreferences.getString("USER_TRUE","not found");
//        Log.d("Check_STR", str);
        if(sharedPreferences.contains(userInfoShared.getUserCheckSign())){
           Intent intent = new Intent(this,HomePageActivity.class);
           startActivity(intent);
           finish();
        }

    }


    private void setViews() {
        usernameEditText = findViewById(R.id.usernameId);
        passwordEditText = findViewById(R.id.passwordId);
    }

    private void setSharedPref() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();


    }


    private void checkPreference() {
        String username = preferences.getString(userInfoShared.getUSERNAME(), "");
        String password = preferences.getString(userInfoShared.getPASSWORD(), "");
        usernameEditText.setText(username);
        passwordEditText.setText(password);
    }



    // this is handler when user click log in
    public void signin_btn(View view) {
        set_user_logged();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        editor.putString("","");

        if (LoginService.isReceptionist(username, password, receptionistList)) {
            Log.d("username", username);
            editor.putString(userInfoShared.getUSERNAME(), username);
            editor.putString(userInfoShared.getPASSWORD(), password);

            editor.commit();
            Intent intent = new Intent(this, ReceptionMenue.class);
            intent.putExtra(EXTRA_MESSAGE, customers);
            startActivity(intent);
            finish();
        } else {
            if (LoginService.isCustomer(username, password, customerList)) {
                editor.putString(userInfoShared.getUSERNAME(), username);
                editor.putString(userInfoShared.getPASSWORD(), password);
                setSharedPrefCustomerCredit(username,password,customerList);
                editor.commit();
                Customer customer=LoginService.getCustomerByUsername(username,password,customerList);
                String customerString=gson.toJson(customer);
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.putExtra(CUSTOMER,customerString);// this message will send to home activity ,then send to availableRooms activity,to save customer information.
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "you have to create account",
                        Toast.LENGTH_LONG).show();
            }
        }

        // now i will asume the customer login is successful

    }

    private void set_user_logged() {
        SharedPreferences sharedPreferences = getSharedPreferences(userInfoShared.getUserCheckSignPref(),MODE_PRIVATE);
        sharedPreferences.edit().putString(userInfoShared.getUserCheckSign(),"logged").commit();
    }

    private void setSharedPrefCustomerCredit(String username, String password, List<Customer> customerList) {
        SharedPreferences sharedPreferences = getSharedPreferences(userInfoShared.getCustomerPref(),MODE_PRIVATE);
        SharedPreferences.Editor customer_editor = sharedPreferences.edit();

        customer_editor.putString(userInfoShared.getCreditCard(),getCustomerInfo(username,password,customerList,userInfoShared.getCreditCard()));
        customer_editor.putString(userInfoShared.getPhoneNumber(), getCustomerInfo(username,password,customerList,userInfoShared.getPhoneNumber()));
        customer_editor.putString(userInfoShared.getCustomerId(),getCustomerInfo(username,password,customerList,userInfoShared.getCustomerId()));
        customer_editor.putString(userInfoShared.getUSERNAME(),username);

        customer_editor.apply();
        
    }



    private String getCustomerInfo(String userName , String userPass, List<Customer> customerList, String data ) {
        for (Customer customer : customerList){
            if (userName.equalsIgnoreCase(customer.getCustomer_username()) && customer.getCustomer_password().equalsIgnoreCase(userPass)){
                if (data.equalsIgnoreCase(userInfoShared.getCreditCard())){
                    return customer.getCustomer_visa();
                }
                else if (data.equalsIgnoreCase(userInfoShared.getPhoneNumber())){
                    return customer.getCustomer_phone();
                }
                else if(data.equalsIgnoreCase(userInfoShared.getCustomerId())){
                    return String.valueOf(customer.getCustomer_id());
                }
            }
        }
        return "";
    }



    private void getReceptionsList() {
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlReceptions,
                new Response.Listener<String>() {

                    JSONObject jsnobject = null;
                    JSONArray jsonArray = null;

                    @Override
                    public void onResponse(String response) {
                        try {
                            jsnobject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonArray = jsnobject.getJSONArray("receptionist");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray finalJsonArray = jsonArray;
                        String g = finalJsonArray.toString();
                        Receptionist[] receptionistsArray = gson.fromJson(g, Receptionist[].class);
                        for (Receptionist receptionist : receptionistsArray) {
                            receptionistList.add(receptionist);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);


    }

    public void getCustomersList() {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlCustomers,
                new Response.Listener<String>() {

                    JSONObject jsnobject = null;
                    JSONArray jsonArray = null;

                    @Override
                    public void onResponse(String response) {
                        try {
                            jsnobject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            jsonArray = jsnobject.getJSONArray("customers");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONArray finalJsonArray = jsonArray;
                        String g = finalJsonArray.toString();
                        customers = g;
                        Customer[] customersArray = gson.fromJson(g, Customer[].class);
                        for (Customer customer : customersArray) {
                            customerList.add(customer);
                            Log.d("customer length", customerList.size() + "");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);


    }

    public void forgetPage(View view) {
        Intent intent = new Intent(this, Forget1Activity.class);
        startActivity(intent);
    }

    class GetData implements Runnable {

        @Override
        public void run() {
            getReceptionsList();
            getCustomersList();

        }
    }
}
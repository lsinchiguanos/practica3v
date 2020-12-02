package com.example.practica3v;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RequestQueue requestQueue;
    private static final String url = "https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        initUI();
        //stringRequest();
        jsonRequest();
    }

    private void initUI(){
        textView = findViewById(R.id.lbJson);
    }

    private void jsonRequest() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                String name = jsonObject.getString("name");
                                textView.append(name + "\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
                return params;
            }
        };
    }

    private void stringRequest() {
        StringRequest sr = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                })
        {
            /*@Override*/
            /*protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
                return params;
            }*/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
                return params;
            }
        };
        requestQueue.add(sr);
    }

}
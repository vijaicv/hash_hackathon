package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class usertype extends AppCompatActivity {

    private CardView ngo,doctor,others;
    private SharedPreferences sp;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);

        intialise();
        queue = Volley.newRequestQueue(usertype.this);
        sp = getSharedPreferences("communities", Context.MODE_PRIVATE);
        Boolean selected= sp.getBoolean("selected",false);
        Log.d("selected", "onCreate: "+selected);
        if(selected==true){
            Intent intent =new Intent(usertype.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=sp.getString("mail","");
                Request(mail,"users");
            }
        });
    }

    private void intialise() {
        ngo =(CardView)findViewById(R.id.NGO);
        doctor =(CardView)findViewById(R.id.Doctor);
        others =(CardView)findViewById(R.id.Others);
    }

    private void Request(final String mail, String type) {
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/usertype?mail="+mail+"&type="+type, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean stat=response.getBoolean("stat");
                    Log.d("response", "onResponse: "+response);
                    Log.d("response", "onResponse: "+mail);
                    if(stat==true){
                        Intent intent=new Intent(usertype.this,nametag.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseerr", "onErrorResponse: "+error);
            }
        });

        queue.add(jsonreq);
    }
}

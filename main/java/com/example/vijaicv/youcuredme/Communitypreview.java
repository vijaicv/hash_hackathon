package com.example.vijaicv.youcuredme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Communitypreview extends AppCompatActivity {

    private RequestQueue queue;
    private TextView title;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communitypreview);
        queue = Volley.newRequestQueue(Communitypreview.this);

        String comname=getIntent().getStringExtra("comname");

        title = (TextView)findViewById(R.id.community_title);
        desc = (TextView)findViewById(R.id.community_desc);
        Request2(comname);
    }


    private void Request2(final String comname) {
        comname.replaceAll(" ","%20");
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/comdetails?comname="+comname, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    title.setText(response.getString("name"));
                    Log.d("res", "onResponse: "+response.getString("name"));
                    desc.setText(response.getString("desc"));
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

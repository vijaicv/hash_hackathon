package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class question extends AppCompatActivity {

    private RequestQueue queue;
    private EditText ques;
    private SharedPreferences sp;
    String comname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        comname=getIntent().getStringExtra("name");
        Button sendbtn=(Button)findViewById(R.id.sendbtn);
        queue = Volley.newRequestQueue(question.this);
        ques = (EditText)findViewById(R.id.editText);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void Request(final String mail) {

        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/addquestion?question="+ques.getText().toString()+"&comname="+comname, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("stat")==true){
                        Toast.makeText(question.this, "Posted Question", Toast.LENGTH_SHORT).show();
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

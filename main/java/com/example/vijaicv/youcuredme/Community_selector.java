package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
import java.util.List;

public class Community_selector extends AppCompatActivity {

    private GridView grid;
    List<community_data> comdata;
    private Button continuebtn;
    private SharedPreferences sp,sp2;
    private RequestQueue queue;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_selector);
        queue = Volley.newRequestQueue(Community_selector.this);
        initialise();

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp2=getSharedPreferences("selectedcommunities",Context.MODE_PRIVATE);
                String[] names=sp2.getString("comlist","").split(",");
                if(names.length==0){
                    Toast.makeText(Community_selector.this,"Select atleast one community",Toast.LENGTH_LONG).show();
                }
                else{
                    sp=getSharedPreferences("communities",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sp.edit();
                    editor.putBoolean("selected",true);
                    editor.commit();
                    mail = sp.getString("mail","");
                    String str=sp2.getString("comlist","");
                    Toast.makeText(Community_selector.this, ""+str, Toast.LENGTH_SHORT).show();
                    Request(mail,str);


                }

            }
        });

        sp=getSharedPreferences("communities",Context.MODE_PRIVATE);
        mail = sp.getString("mail","");
        Request2(mail);


    }

    private void initialise() {
        grid =(GridView)findViewById(R.id.communitygrid);
        continuebtn =(Button)findViewById(R.id.continuebtn);
    }


    private void Request(final String mail, String comname) {
        comname.replaceAll(" ","%20");
        JsonObjectRequest jsonreq2=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/followcommunity?mail="+mail+"&comname="+comname, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean stat=response.getBoolean("stat");
                    if(stat==true){
                        Intent intent =new Intent(Community_selector.this,MainActivity.class);
                        finish();
                        startActivity(intent);
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

        queue.add(jsonreq2);
    }

    private void Request2(final String mail) {
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/getcommnames?mail="+mail, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray comm=response.getJSONArray("communities");
                    comdata=new ArrayList<>();
                    for(int i=0;i<comm.length();i++){
                        JSONObject community=comm.getJSONObject(i);
                        comdata.add(new community_data(community.getString("name")));
                    }
                    Community_adapter comgridadapter=new Community_adapter(comdata,Community_selector.this);
                    grid.setAdapter(comgridadapter);

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

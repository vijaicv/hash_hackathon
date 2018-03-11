package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class CommunityPage extends AppCompatActivity {

    private RequestQueue queue;
    TextView desc,followers,cmname;
    public static List<feeddata> feedlist;
    private JSONObject response;
    private commmunityfeedadapter adapter;
    private RecyclerView feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        desc=(TextView)findViewById(R.id.community_description_text);
        followers=(TextView)findViewById(R.id.followercount);
        cmname=(TextView)findViewById(R.id.comname);

        queue = Volley.newRequestQueue(CommunityPage.this);

        feed = (RecyclerView)findViewById(R.id.feed);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        feedlist = new ArrayList<>();
        SharedPreferences sp=getSharedPreferences("selectedcommunities", Context.MODE_PRIVATE);
        String comlist=sp.getString("comlist","Youcuredme");
        final String[] names=comlist.split(",");

        final int pos=getIntent().getIntExtra("pos",0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommunityPage.this,question.class);
                intent.putExtra("name",names[pos]);
                startActivity(intent);
            }
        });
        cmname.setText(names[pos]);
        Request(names[pos]);
        Request2(names[pos]);
    }


    private void Request2(final String comname) {
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/feed?comname="+comname, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject returnedvalue) {
                try {
                    JSONArray responsedat=returnedvalue.getJSONArray("feed");

                    for(int i=0;i<responsedat.length();i++){
                        response = responsedat.getJSONObject(i);
//
//                        String[] arr={};
//                        JSONArray jarr=response.getJSONArray("opinions");
//                        for(int j=0;i<jarr.length();j++){
//                           JSONObject jobj=jarr.getJSONObject(j);
//                           arr[i]=jobj.getString("text");
//                        }

                        feedlist.add(new feeddata(response.getString("title"), response.getString("content"), response.getInt("upvotes"), response.getString("by"), response.getString("date")));
                        RecyclerView.LayoutManager lm= new LinearLayoutManager(CommunityPage.this);
                        adapter = new commmunityfeedadapter(feedlist,CommunityPage.this);

                        feed.setLayoutManager(lm);
                        feed.setAdapter(adapter);
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

    private void Request(final String comname) {
        comname.replaceAll(" ","%20");
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/comdetails?comname="+comname, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    desc.setText(response.getString("desc"));
                    followers.setText("Followers : "+response.getString("followers"));
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

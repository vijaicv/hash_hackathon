package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class nametag extends AppCompatActivity {

    private GridView taggrid;
    ArrayList<String> taglist;
    private View grid;
    String str="Youcuredme";
    private RequestQueue queue;
    ArrayList<String> selectedlist;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nametag);
        queue = Volley.newRequestQueue(nametag.this);
        Button nextbtn=(Button)findViewById(R.id.nextbtn);
        final TextInputEditText nickname=(TextInputEditText)findViewById(R.id.name);
        sp = getSharedPreferences("communities", Context.MODE_PRIVATE);
        taglist=new ArrayList<>();
        selectedlist =new ArrayList<>();
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = selectedlist.size();
                if(length!=0){
                    for(int i=0;i<selectedlist.size();i++){
                        str.concat(","+selectedlist.get(i));
                    }
                    String mail=sp.getString("mail","");
                    String namex=nickname.getText().toString();
                    if(TextUtils.isEmpty(namex)){
                        Toast.makeText(nametag.this, "pick a nickname", Toast.LENGTH_SHORT).show();
                    }else{
                        Request(mail,namex,str);
                    }
                    
                }else{
                    Toast.makeText(nametag.this, "select atleast one topic", Toast.LENGTH_SHORT).show();
                }

            }
        });
        taglist.add("heart");
        taglist.add("pregnancy");
        taglist.add("diabetes");

        taggrid =(GridView)findViewById(R.id.taggrid);
        taggrid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return taglist.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if(view==null){
                    LayoutInflater inflater=(LayoutInflater)nametag.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    grid= new View(nametag.this);
                    grid=inflater.inflate(R.layout.tagelement,null);
                    TextView name=(TextView)grid.findViewById(R.id.tagtext);
                    name.setText(taglist.get(i));
                }
                else{
                    grid =(View)view;
                }
                return grid;
            }
        });
        taggrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tagtext=(TextView)view.findViewById(R.id.tagtext);
                if(selectedlist.contains(tagtext.getText().toString())){
                    selectedlist.remove(tagtext.getText().toString());
                    Log.d("selectedlist", "onItemClick: "+selectedlist);
                    tagtext.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    selectedlist.add(taglist.get(i));
                    Log.d("selectedlist", "onItemClick: "+selectedlist);
                    tagtext.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });
    }


    private void Request(final String mail, String name,String str) {
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/nametag?mail="+mail+"&name="+name+"&str="+str, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean stat=response.getBoolean("stat");
                    if(stat==true){
                        Intent intent=new Intent(nametag.this,Community_selector.class);
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

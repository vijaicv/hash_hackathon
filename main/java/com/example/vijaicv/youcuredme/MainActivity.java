package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<community_data> comdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        GridView grid=(GridView)findViewById(R.id.maingrid);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Community_selector.class);
                startActivity(intent);
            }
        });

        SharedPreferences sp=getSharedPreferences("selectedcommunities", Context.MODE_PRIVATE);
        String comlist=sp.getString("comlist","Youcuredme");
        String[] names=comlist.split(",");



        List<community_data> communitynames=new ArrayList<>(); //TODO: remove
        for(int i=0;i<names.length;i++){
            communitynames.add(new community_data(names[i]));
        }
        maingridadapter adapter=new maingridadapter(communitynames,MainActivity.this);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(MainActivity.this,CommunityPage.class);
                intent.putExtra("pos",i);
                startActivity(intent);
            }
        });

    }

}

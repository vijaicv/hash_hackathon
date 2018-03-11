package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijaicv on 3/10/18.
 */

public class Community_adapter extends BaseAdapter{
    List<community_data> data;Context mcontext;
    private View grid;

    public Community_adapter(final List<community_data> data, final Context mcontext) {
        this.data = data;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid= new View(mcontext);
            grid=inflater.inflate(R.layout.community_grid_element,null);
            TextView name=(TextView)grid.findViewById(R.id.community_name);
            Button followbtn=(Button)grid.findViewById(R.id.followbtn);
            name.setText(data.get(i).getName());
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(mcontext,Communitypreview.class);
                    intent.putExtra("comname",data.get(i).getName());
                    mcontext.startActivity(intent);
                }
            });
            follow(i,followbtn);
        }
        else{
            grid =(View)view;
        }
        return grid;
    }

    private void follow(final int i, Button followbtn) {
       followbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences sp= mcontext.getSharedPreferences("selectedcommunities",Context.MODE_PRIVATE);
               SharedPreferences.Editor editor=sp.edit();
               String comlist=sp.getString("comlist","Youcuredme");
               editor.putString("comlist",comlist.concat(","+data.get(i).getName()));
               editor.commit();
               Toast.makeText(mcontext,"started following : "+data.get(i).getName(),Toast.LENGTH_LONG).show();
           }
       });
    }
}

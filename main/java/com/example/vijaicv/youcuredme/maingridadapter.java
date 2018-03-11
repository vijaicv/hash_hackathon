package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vijaicv on 3/10/18.
 */

public class maingridadapter extends BaseAdapter
{
    public maingridadapter(List<community_data> selected_commuities, Context mcontext) {
        this.selected_commuities = selected_commuities;
        this.mcontext = mcontext;
    }

    List<community_data> selected_commuities;Context mcontext;
    @Override
    public int getCount() {
        return selected_commuities.size();
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
        LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View maingridelement=inflater.inflate(R.layout.main_grid_element,null);
        TextView title=(TextView)maingridelement.findViewById(R.id.community_name_maingrid);
        title.setText(selected_commuities.get(i).getName());
        return maingridelement;
    }
}

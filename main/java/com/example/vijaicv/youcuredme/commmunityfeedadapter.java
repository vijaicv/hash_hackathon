package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vijaicv on 3/10/18.
 */

public class commmunityfeedadapter extends RecyclerView.Adapter<commmunityfeedadapter.mviewholder> {
    List<feeddata> feeddat;Context feedcontext;

    public commmunityfeedadapter(List<feeddata> feeddat, Context feedcontext) {
        this.feeddat = feeddat;
        this.feedcontext = feedcontext;
    }

    @Override

    public mviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_element,null);

        return new mviewholder(itemview);
    }

    @Override
    public int getItemCount() {
        return feeddat.size();
    }

    @Override
    public void onBindViewHolder(mviewholder holder, final int position) {
        holder.title.setText(feeddat.get(position).getArticletitle());
        holder.article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(feedcontext,articleview.class);
                intent.putExtra("pos",position);
                feedcontext.startActivity(intent);
            }
        });
    }

    public class mviewholder extends RecyclerView.ViewHolder{
        TextView title;
        CardView article;
        public mviewholder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.article_title);
            article=(CardView)itemView.findViewById(R.id.article);
        }

    }


}

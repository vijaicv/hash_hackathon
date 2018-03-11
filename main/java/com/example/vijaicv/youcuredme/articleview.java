package com.example.vijaicv.youcuredme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class articleview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articleview);

        int pos =getIntent().getIntExtra("pos",0);
        Toast.makeText(articleview.this, ""+pos, Toast.LENGTH_SHORT).show();
        ImageView artimage=(ImageView)findViewById(R.id.artimage);
        TextView title=(TextView)findViewById(R.id.title_of_article);
        TextView content=(TextView)findViewById(R.id.content_of_article);
        TextView upvotes=(TextView)findViewById(R.id.upvotes_of_article);
        TextView opinions=(TextView)findViewById(R.id.opinion_of_article);

//        Glide.with(articleview.this).load(CommunityPage.feedlist.get(pos).getImage()).into(artimage);
        title.setText(CommunityPage.feedlist.get(pos).getArticletitle());
        content.setText(CommunityPage.feedlist.get(pos).getContent());
//        upvotes.setText(CommunityPage.feedlist.get(pos).getUpvotes());


    }
}

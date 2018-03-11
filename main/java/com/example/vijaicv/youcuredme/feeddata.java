package com.example.vijaicv.youcuredme;

/**
 * Created by vijaicv on 3/10/18.
 */

public class feeddata {
    String articletitle;
    String content ;
    int upvotes;
    String by;
    String date;


    public feeddata(String articletitle, String content, int upvotes, String by, String date) {
        this.articletitle = articletitle;
        this.content = content;
        this.upvotes = upvotes;
        this.by = by;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }
}

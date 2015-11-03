package com.example.dipshil.nucan;

import java.util.ArrayList;

/**
 * Created by Dipshil on 31-10-2015.
 */
public class Item {
        public String news;
        public byte[] image;
        public String date;

    public String getdate() {
        return date;
    }
    public String getNews() {
        return news;
    }
    public byte[] getImage(){
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setNews(String news) {
        this.news = news;
    }
    public void setdate(String url) {
        this.date = url;
    }
    public Item(){}
}


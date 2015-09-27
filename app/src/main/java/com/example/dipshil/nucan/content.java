package com.example.dipshil.nucan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class content extends ArrayAdapter<String> {

    public content(Context context,String[] news) {
        super(context,R.layout.content, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflater = LayoutInflater.from(getContext());
        View contentview = myinflater.inflate(R.layout.content, parent, false);

        String newshead = getItem(position);
        TextView newstext = (TextView) contentview.findViewById(R.id.newshead);
        ImageView newsimage=(ImageView) contentview.findViewById(R.id.newsimage);

        newstext.setText(newshead);
        newsimage.setImageResource(R.drawable.nucan);
        return contentview;
    }
}

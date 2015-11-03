package com.example.dipshil.nucan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Dipshil on 31-10-2015.
 */
public class Content_adapter extends ArrayAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    public Context mContext;
    private List<Item> items;
    ImageLoader imageloader=CustomVolleyRequestQueue.getInstance(getContext()).getImageLoader();
    public Content_adapter(Context context, List<Item> items){
        super(context,R.layout.content,items);
        this.mContext=context;
        this.items=items;
    }

    LayoutInflater myinflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            if(myinflater==null) {
                myinflater = LayoutInflater.from(mContext);
            }

            if(convertView==null) {
                convertView = myinflater.inflate(R.layout.content,null);
            }


                imageloader = CustomVolleyRequestQueue.getInstance(mContext).getImageLoader();
                NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.newsimage);
                TextView news = (TextView) convertView.findViewById(R.id.newshead);
                TextView date = (TextView) convertView.findViewById(R.id.date);
                Item item = items.get(position);
/*                byte[] byteArray=item.getImage();
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(bmp);
                //imageView.setImageUrl(item.getImage(),imageloader);*/
                news.setText(item.getNews());
                date.setText(item.getdate());
                /*convertView = myinflater.inflate(R.layout.main_news_fragment,null);
                TextView main_news=(TextView) convertView.findViewById(R.id.tnews);
                main_news.setText(item.getNews());*/


        return convertView;
    }
}

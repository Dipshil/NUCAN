package com.example.dipshil.nucan;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class MainNewsFragment extends Fragment {

    private static TextView main_news;
    private static ImageView main_news_image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_news_fragment, container, false);

        main_news=(TextView) view.findViewById(R.id.tnews);
        main_news_image=(ImageView) view.findViewById(R.id.topimage);
        return view;
    }
}

package com.example.dipshil.nucan;

import android.support.v4.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainNewsFragment extends Fragment {

    private RequestQueue mQueue;
    private static TextView main_news;
    private static TextView desc;
    private static NetworkImageView main_news_image;
    private static ImageLoader imageloader;
    String url;
    private ListView l1;
    public void volleyconnect(String url){
        /*final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();*/


        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        Log.d("Response", response.toString());
                        String nickname = "";
                        JSONObject jresponse = null;

                        // name = new String[response.length()];

                        try {

                            jresponse = response.getJSONObject(0);

                            main_news.setText(jresponse.getString("text"));
                            desc.setText(jresponse.getString("description"));
                            String iurl="http://nucan.comxa.com/";
                            main_news_image.setImageUrl(iurl + jresponse.getString("image"), imageloader);
                                /*byte[] imageBytes = Base64.decode(jresponse.getString("image"), Base64.DEFAULT);
                                item.setImage(imageBytes);*/
                            //dialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                       // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        //mTextView.setText("Error: " + error.getMessage());
                        Toast.makeText(getActivity(), "could not fetch data", Toast.LENGTH_SHORT).show();
                        //pDialog.dismiss();
                    }
                }
        );
        mQueue.add(getRequest);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_news_fragment, container, false);
        mQueue = CustomVolleyRequestQueue.getInstance(getActivity()).getRequestQueue();
        main_news=(TextView) view.findViewById(R.id.tnews);
        imageloader=CustomVolleyRequestQueue.getInstance(getActivity()).getImageLoader();
        main_news_image=(NetworkImageView) view.findViewById(R.id.topimage);
        desc=(TextView)view.findViewById(R.id.description);
        url = MainActivity.url;
        volleyconnect(url);
        return view;
    }

}

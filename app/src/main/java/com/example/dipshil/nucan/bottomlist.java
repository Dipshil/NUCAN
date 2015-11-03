package com.example.dipshil.nucan;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.AdapterView;


import android.widget.ListView;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class bottomlist extends ListFragment {

    private RequestQueue mQueue;
    //String url =MainActivity.url;//"http://nucan.comxa.com/CSI.php";
    String url;
    ProgressDialog pDialog;
    private List<Item> array = new ArrayList<Item>();
    private ListView l1;
    private Content_adapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mQueue = CustomVolleyRequestQueue.getInstance(getActivity()).getRequestQueue();

        url = MainActivity.url;

        View view = inflater.inflate(R.layout.bottomlist, container, false);
        adapter = new Content_adapter(getActivity(), array);

        //l1.setAdapter(adapter);
       //pDialog = new ProgressDialog(getActivity());
        //pDialog.setMessage("Loading...");
        //pDialog.show();

        l1 = (ListView) view.findViewById(android.R.id.list);
        volleyconnect(url);
        l1.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String news = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(getActivity(), news, Toast.LENGTH_LONG).show();
                    }
                }
        );

        return view;
    }
    public void volleyconnect(String url){
        final ProgressDialog dialog;
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        Log.d("Response", response.toString());
                        String nickname = "";
                        JSONObject jresponse = null;
                        array.clear();

                        // name = new String[response.length()];
                        for (int i = 0; i < response.length(); i=i+1) {


                            try {

                                jresponse = response.getJSONObject(i);
                                Item item = new Item();
                                item.setNews(jresponse.getString("text"));
                                item.setdate(jresponse.getString("date"));
                                /*byte[] imageBytes = Base64.decode(jresponse.getString("image"), Base64.DEFAULT);
                                item.setImage(imageBytes);*/
                                array.add(item);
                                dialog.dismiss();
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        l1.setAdapter(adapter);

                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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
}



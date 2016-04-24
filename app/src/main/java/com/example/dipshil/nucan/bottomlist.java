package com.example.dipshil.nucan;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.app.AlarmManager;

import android.widget.AdapterView;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class bottomlist extends ListFragment {

    private RequestQueue mQueue;
    //String url =MainActivity.url;//"http://nucan.comxa.com/CSI.php";
    String url;
    //ProgressDialog pDialog;
    private List<Item> array = new ArrayList<Item>();
    //private ListView l1;
    private Content_adapter adapter;
    TextView event_news,event_date,event_time;
    NetworkImageView event_image;
    ImageLoader imageloader=CustomVolleyRequestQueue.getInstance(getActivity()).getImageLoader();
    public AlertDialog alertDialog;
    static int flag=1;
    AlarmManager alarmManager;
    //AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
    private PendingIntent pendingIntent;
    private MediaPlayer player;


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

       final View dialog_view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog,null);
        mQueue = CustomVolleyRequestQueue.getInstance(getActivity()).getRequestQueue();

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(getActivity(), AlarmReciever.class);
        final Calendar calendar = Calendar.getInstance();
        pendingIntent = PendingIntent.getBroadcast(getActivity(),0,mIntent,0);

        event_news = (TextView)dialog_view.findViewById(R.id.eventname);
        event_date = (TextView)dialog_view.findViewById(R.id.eventdate);
        event_image = (NetworkImageView) dialog_view.findViewById(R.id.image);
        event_time = (TextView)dialog_view.findViewById(R.id.eventtime);
        l.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //String news = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(getActivity(), news, Toast.LENGTH_LONG).show();
                        imageloader = CustomVolleyRequestQueue.getInstance(getActivity()).getImageLoader();
                        Item event = array.get(position);
                        String enews = event.getNews();
                        String date = event.getdate();
                        event_image.setImageUrl(event.getImage(),imageloader);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setView(dialog_view);


                        builder.setTitle("Event Details").setCancelable(true).setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(player.isPlaying()){
                                        player.stop();}
                                        dialog.cancel();


                                    }
                                }).setPositiveButton("Set Reminder",
                                new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                            calendar.set(Calendar.HOUR_OF_DAY,9);
                                            calendar.set(Calendar.MINUTE,0);
                                player = new MediaPlayer();
                                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                try {
                                    player.setDataSource(getActivity(), uri);
                                    final AudioManager audio = (AudioManager) getActivity()
                                            .getSystemService(Context.AUDIO_SERVICE);
                                    if (audio.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                                        player.setAudioStreamType(AudioManager.STREAM_ALARM);
                                        player.prepare();
                                        player.start();
                                    }
                                } catch (IOException e) {
                                    Log.e("Error....","Check code...");
                                }

                                           // alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                dialog.dismiss();
                            }
                        });

                        event_news.setText(enews);
                        event_date.setText(date);
                        event_time.setText("9:30");
                        //event_image.setImageUrl(imgurl,imageloader);
                            if(flag!=0){
                            alertDialog = builder.create();
                            flag=0;
                            }
                            alertDialog.show();



                    }
                }
        );
    }

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

       // l1 = (ListView) view.findViewById(android.R.id.list);

        volleyconnect(url);
        setListAdapter(adapter);

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
                                String iurl="http://nucan.comxa.com/";
                                item.setImage(iurl+jresponse.getString("image"));
                                /*byte[] imageBytes = Base64.decode(jresponse.getString("image"), Base64.DEFAULT);
                                item.setImage(imageBytes);*/
                                array.add(item);
                                dialog.dismiss();
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        setListAdapter(adapter);


                        //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        //mTextView.setText("Error: " + error.getMessage());
                        Toast.makeText(getActivity(), "could not fetch data", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //pDialog.dismiss();
                    }
                }
        );
        mQueue.add(getRequest);

    }
}
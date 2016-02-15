package com.example.matt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import model.Weather.WeatherDAO;
import model.forcast.ForcastDAO;
import model.forcast.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import service.WeatherService;

public class ForcastActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ListView list;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = (ListView) findViewById(R.id.list);
        final ArrayList<String> arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview , arrayList);
        list.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        String BASE_URL = "http://api.openweathermap.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Intent intent = getIntent();
        int days = Integer.parseInt(intent.getStringExtra(DisplayMessageActivity.NUMBER_OF_DAYS));
        Call<ForcastDAO> call = weatherService.getForcast(days);
        call.enqueue(new Callback<ForcastDAO>() {
            @Override
            public void onResponse(Response<ForcastDAO> response, Retrofit retrofit) {
                for(List day : response.body().getList()) {
                    Date time=new Date((long)day.getDt()*1000);
                        arrayList.add(time.toString() + " " + day.getWeather().get(0).getDescription());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


}

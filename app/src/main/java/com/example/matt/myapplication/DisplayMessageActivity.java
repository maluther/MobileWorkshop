package com.example.matt.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import hugo.weaving.DebugLog;
import model.Weather.WeatherDAO;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import service.WeatherService;

@DebugLog
public class DisplayMessageActivity extends AppCompatActivity implements Callback<WeatherDAO> {
    public final static String NUMBER_OF_DAYS = "com.example.matt.myapplication.DAYS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.c
        textView.setText(message);


        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);
    }
    public void spewConfetti(View view) {
        new ParticleSystem(this, 100, R.drawable.star_pink, 800)
                .setSpeedRange(0.1f, 0.25f)
                .oneShot(view, 100);
    }
    public void getWeather(View view) {
        String BASE_URL = "http://api.openweathermap.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);

        Call<WeatherDAO> call = weatherService.getWeather();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<WeatherDAO> response, Retrofit retrofit) {
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setText(response.body().getMain().getTemp().toString());
    }

    @Override
    public void onFailure(Throwable t) {

    }

    public void changeToForcast(View view)
    {
        Intent intent = new Intent(this, ForcastActivity.class);
        EditText editText = (EditText) findViewById(R.id.time);
        String days = editText.getText().toString();
        intent.putExtra(NUMBER_OF_DAYS, days);
        startActivity(intent);
    }
}

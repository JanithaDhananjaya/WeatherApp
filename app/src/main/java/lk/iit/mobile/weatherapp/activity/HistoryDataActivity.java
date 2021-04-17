package lk.iit.mobile.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import lk.iit.mobile.weatherapp.R;

public class HistoryDataActivity extends AppCompatActivity {

    private WeatherDB weatherDB;

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_data);

        info = findViewById(R.id.info);

        weatherDB = new WeatherDB(this);

        TextView txtCity = (TextView) findViewById(R.id.lbl_city);

        Intent i = getIntent();
        String city = i.getStringExtra("city");


        String weatherInfoByCity = weatherDB.getWeatherInfoByCity(city);
        if (weatherInfoByCity!=null){
            txtCity.setText(city);
            info.setText(weatherInfoByCity);
        }else{
            info.setText("City Not Found!");
        }


    }
}
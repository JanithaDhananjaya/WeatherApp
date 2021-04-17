package lk.iit.mobile.weatherapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import lk.iit.mobile.weatherapp.R;
import lk.iit.mobile.weatherapp.model.WeatherModel;

public class CityWeatherActivity extends AppCompatActivity {

    private TextView city_current_temp;
    private TextView city_feel_like_temp;
    private TextView lbl_feel_like;
    private TextView lbl_temp;
    private TextView txt_sunrise;
    private TextView txt_sunset;
    private TextView txt_description;
    private EditText txt_city;
    private Button btn_search;
    private Button btn_history;

    private WeatherDB weatherDB;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        city_current_temp = findViewById(R.id.city_current_temp);
        city_feel_like_temp = findViewById(R.id.city_feel_like_temp);
        lbl_temp = findViewById(R.id.lbl_temp);
        lbl_feel_like = findViewById(R.id.lbl_feel_like);
        txt_sunrise = findViewById(R.id.sunrise);
        txt_sunset = findViewById(R.id.sunset);
        txt_description = findViewById(R.id.description);
        txt_city = findViewById(R.id.txt_city);
        city_current_temp = findViewById(R.id.city_current_temp);
        btn_history = findViewById(R.id.btn_history);

//        btn_history.setVisibility(View.INVISIBLE);

        weatherDB = new WeatherDB(this);
    }

    public void findWeather(View view) {
        final String[] temp = {""};
        cityName = txt_city.getText().toString().toLowerCase();

        if (cityName.length() != 0) {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=met%20ric&appid=94607105b95a29ca2aff330627a181d9";
            getWeather task = new getWeather();
            try {
                temp[0] = task.execute(url).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btn_history.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Cannot find the City!", Toast.LENGTH_SHORT).show();
        }
    }

    public void seeHistoricalData(View view) {
        cityName = txt_city.getText().toString().toLowerCase();
        Intent i = new Intent(getApplicationContext(), HistoryDataActivity.class);
        i.putExtra("city", cityName);
        startActivity(i);
    }

    private class getWeather extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject main = jsonObject.getJSONObject("main");

                JSONObject sys = jsonObject.getJSONObject("sys");
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                Date sunrise = new Date(sys.getLong("sunrise") * 1000);
                Date sunset = new Date(sys.getLong("sunset") * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");

                String temp = main.getString("temp");
                String feels_like = main.getString("feels_like");

                lbl_temp.setVisibility(View.VISIBLE);
                lbl_feel_like.setVisibility(View.VISIBLE);

                city_current_temp.setText(temp);
                city_feel_like_temp.setText(feels_like);

                txt_sunrise.setText("Sunrise : " + sdf.format(sunrise));
                txt_sunset.setText("Sunset : " + sdf.format(sunset));
                txt_description.setText("Description: " + weather.getString("description"));

                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                WeatherModel weatherModel = new WeatherModel(txt_city.getText().toString().toLowerCase(),
                        temp, feels_like,
                        sdf.format(sunrise),
                        sdf.format(sunset),
                        weather.getString("description"),
                        date);
                weatherDB.insertWeatherData(weatherModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
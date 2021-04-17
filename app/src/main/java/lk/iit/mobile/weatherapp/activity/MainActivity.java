package lk.iit.mobile.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import lk.iit.mobile.weatherapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView current_temp;
    private TextView feel_like_temp;
    private Button btn_find_City;


    class getWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
                String temp = main.getString("temp");
                String feels_like = main.getString("feels_like");
                current_temp.setText(temp);
                feel_like_temp.setText(feels_like);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current_temp = findViewById(R.id.current_temp);
        feel_like_temp = findViewById(R.id.feel_like_temp);
        btn_find_City = findViewById(R.id.btn_find_City);

        londonWeather();
    }

    public void findWeatherByCityName(View view) {
        Intent intent = new Intent(getApplicationContext(), CityWeatherActivity.class);
        view.getContext().startActivity(intent);
    }

    public void londonWeather() {
        final String[] temp = {""};
        String cityName = "London";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=met%20ric&appid=94607105b95a29ca2aff330627a181d9";
        getWeather task = new getWeather();
        try {
            temp[0] = task.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
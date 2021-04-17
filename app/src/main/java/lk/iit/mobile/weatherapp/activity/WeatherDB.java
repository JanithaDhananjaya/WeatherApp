package lk.iit.mobile.weatherapp.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import lk.iit.mobile.weatherapp.model.WeatherModel;

import static android.provider.BaseColumns._ID;

public class WeatherDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "weather";
    private static final String TABLE_NAME = "temperature";
    private static final int DB_VERSION = 1;

    private Context context;
    private SQLiteDatabase database;

    public WeatherDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "city TEXT NOT NULL, " +
                "temperature DECIMAL(2,2) NOT NULL, " +
                "feelsLikeTemp DECIMAL(2,2) NOT NULL, " +
                "sunrise DATE NOT NULL, " +
                "sunset DATE NOT NULL, " +
                "description TEXT NOT NULL, " +
                "dateTime DATE DEFAULT CURRENT_TIMESTAMP);"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS temperature");
        onCreate(db);
    }

    public void insertWeatherData(WeatherModel weatherModel) {
        database = getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (city, temperature, feelsLikeTemp, sunrise, sunset, description, dateTime) VALUES (?,?,?,?,?,?,?);";
        database.execSQL(query, new String[]{weatherModel.getCity(), weatherModel.getTemperature(), weatherModel.getFeelsLikeTemp(), weatherModel.getSunrise(), weatherModel.getSunset(), weatherModel.getDescription(), weatherModel.getDateTIme()});
        database.close();
    }

//    public ArrayList<WeatherModel> getWeatherInfoByCity(String city) {
//        StringBuilder builder = new StringBuilder();
//        database = getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE city = ?", new String[]{city});
//
//        ArrayList<WeatherModel> weatherInfo = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//            weatherInfo.add(
//                    new WeatherModel(
//                            cursor.getInt(0),
//                            cursor.getString(1),
//                            cursor.getString(2),
//                            cursor.getString(3),
//                            cursor.getString(4),
//                            cursor.getString(5),
//                            cursor.getString(6),
//                            cursor.getString(7)
//                    ));
//        }
//
//        Log.i("Weather Info of " + city, weatherInfo.toString());
//        return weatherInfo;
//    }

    public String getWeatherInfoByCity(String city) {
        StringBuilder builder = new StringBuilder();
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE city =?", new String[]{city});

        while (cursor.moveToNext()) {
            builder.append(cursor.getInt(0)).append("). \n");
            builder.append("DateTime : " + cursor.getString(7)).append("\n");
            builder.append("Temperature : " + cursor.getString(2)).append("\n\n\n");
        }
        if (builder.length() > 0) {
            return builder.toString();
        } else {
            return null;
        }

    }
}

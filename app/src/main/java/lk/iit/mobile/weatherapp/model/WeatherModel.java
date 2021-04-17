package lk.iit.mobile.weatherapp.model;

public class WeatherModel {
    private int id;
    private String city;
    private String temperature;
    private String feelsLikeTemp;
    private String sunrise;
    private String sunset;
    private String description;
    private String dateTIme;

    public WeatherModel() {
    }

    public WeatherModel(int id, String city, String temperature, String feelsLikeTemp, String sunrise, String sunset, String description, String dateTIme) {
        this.id = id;
        this.city = city;
        this.temperature = temperature;
        this.feelsLikeTemp = feelsLikeTemp;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.description = description;
        this.dateTIme = dateTIme;
    }

    public WeatherModel(String city, String temperature, String feelsLikeTemp, String sunrise, String sunset, String description, String dateTIme) {
        this.city = city;
        this.temperature = temperature;
        this.feelsLikeTemp = feelsLikeTemp;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.description = description;
        this.dateTIme = dateTIme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public void setFeelsLikeTemp(String feelsLikeTemp) {
        this.feelsLikeTemp = feelsLikeTemp;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTIme() {
        return dateTIme;
    }

    public void setDateTIme(String dateTIme) {
        this.dateTIme = dateTIme;
    }
}

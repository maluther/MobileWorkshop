package service;

import model.Weather.WeatherDAO;
import model.forcast.ForcastDAO;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WeatherService {
    @GET("/data/2.5/weather?zip=72764,us&appid=44db6a862fba0b067b1930da0d769e98")
    Call<WeatherDAO> getWeather();

    @GET("/data/2.5/forecast/daily?lat=36.186744&lon=-94.128814&mode=json&appid=44db6a862fba0b067b1930da0d769e98")
    Call<ForcastDAO> getForcast(@Query("cnt") int cnt);
}
package com.frank.myfirstapp.utils;

public class Future {
	
	private String temperature;
	private String weather;
	private Weather_id weather_id;
	private String wind;
	private String week;
	private String data;
	
	public void setTemperature(String temperature){
		this.temperature = temperature;
	}
	public void setWeather(String weather){
		this.weather = weather;
	}
	public void setWeather_id(Weather_id weather_id){
		this.weather_id = weather_id;
	}
	public void setWind(String wind){
		this.wind = wind;
	}
	public void setWeek(String week){
		this.week = week;
	}
	public void setData(String data){
		this.data = data;
	}
	
	public String getTemperature(){
		return this.temperature;
	}
	public String getWeather(){
		return this.weather;
	}
	public Weather_id getWeather_id(){
		return this.weather_id ;
	}
	public String getWind(){
		return this.wind;
	}
	public String getWeek(){
		return this.week;
	}
	public String getData(){
		return this.data;
	}

}

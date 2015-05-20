package com.frank.myfirstapp.utils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.frank.myfirstapp.R;
import com.frank.myfirstapp.applications.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {
	
	public static WeatherData mWeatherData;
	
	public static boolean updataWeatherData(WeatherData mData){
		SharedPreferences pref = MyApplication.getContext().getSharedPreferences("WeatherData", 0);
		SharedPreferences.Editor editor = pref.edit();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		//Today
		editor.putString("cityNameText", mData.getResult().getToday().getCity());
		editor.putString("currentDataText", sdf.format(new Date()));
		editor.putString("weatherDesp", mData.getResult().getToday().getWeather());
		editor.putString("weatherTemp", mData.getResult().getToday().getTemperature());
		
		//SK
		editor.putString("skNameText", "实时天气");
		editor.putString("currentTimeText", mData.getResult().getSk().getTime());
		editor.putString("weatherTemp", mData.getResult().getSk().getTemp());
		editor.putString("weatherWind", mData.getResult().getSk().getWind_direction()+"  "+mData.getResult().getSk().getWind_strength());
		
		return editor.commit();
	}
	
	public static WeatherData praseJsonWithGson(String json) {
		// TODO Auto-generated method stub
//		WeatherData mWeatherData;
		Gson gson = new Gson();
		Type type = new TypeToken<WeatherData>(){}.getType();
		mWeatherData = gson.fromJson(json, type);
		if(mWeatherData == null){
			Log.d("May", "utils null");
		}
		else{
			Log.d("May", "utils not null");
		}
//		Toast.makeText(MyApplication.getContext(), "mWeatherData got", Toast.LENGTH_SHORT).show();
		return mWeatherData;
	}

}


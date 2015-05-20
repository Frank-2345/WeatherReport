package com.frank.myfirstapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frank.myfirstapp.R;
import com.frank.myfirstapp.utils.WeatherData;

public class TodayFragment extends Fragment {
	
	WeatherData mData;
	
	public TodayFragment(){
		
	}
	
	public TodayFragment(WeatherData data) {
		// TODO Auto-generated constructor stub
		mData = data;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_today_layout, container, false);
		TextView cityNameText = (TextView) rootView.findViewById(R.id.city_name);
		TextView currentDataText = (TextView) rootView.findViewById(R.id.current_date);
		TextView weatherDesp = (TextView) rootView.findViewById(R.id.weather_desp);
		TextView weatherTemp = (TextView) rootView.findViewById(R.id.weather_temp);
		
		SharedPreferences sp = getActivity().getSharedPreferences("WeatherData", 0);
		
		cityNameText.setText(sp.getString("cityNameText", ""));
		currentDataText.setText(sp.getString("currentDataText", ""));
		weatherDesp.setText(sp.getString("weatherDesp", ""));
		weatherTemp.setText(sp.getString("weatherTemp", ""));
		
		return rootView;
	}

}

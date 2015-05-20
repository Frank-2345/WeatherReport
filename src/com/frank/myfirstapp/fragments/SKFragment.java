package com.frank.myfirstapp.fragments;

import com.frank.myfirstapp.R;
import com.frank.myfirstapp.utils.WeatherData;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SKFragment extends Fragment {
	
	private WeatherData mData;
	
	public SKFragment(){
		
	}
	
	public SKFragment(WeatherData data) {
		// TODO Auto-generated constructor stub
		mData = data;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_sk_layout, container, false);
		TextView skNameText = (TextView) rootView.findViewById(R.id.sk_name);
		TextView currentTimeText = (TextView) rootView.findViewById(R.id.current_time);
		TextView weatherTemp = (TextView) rootView.findViewById(R.id.weather_temp);
		TextView weatherWind = (TextView) rootView.findViewById(R.id.weather_wind);
		
		SharedPreferences sp = getActivity().getSharedPreferences("WeatherData", 0);
		
		skNameText.setText(sp.getString("skNameText", ""));
		currentTimeText.setText("更新时间" + sp.getString("currentTimeText", ""));
		weatherTemp.setText(sp.getString("weatherTemp", "")+"℃");
		weatherWind.setText(sp.getString("weatherWind", ""));
		/**
		 * 在main activity中实现
		 */
//		skNameText.setText("实时天气");
//		currentTimeText.setText("更新时间" + mData.getResult().getSk().getTime());
//		weatherTemp.setText(mData.getResult().getSk().getTemp()+"℃");
//		weatherWind.setText(mData.getResult().getSk().getWind_direction()+"  "+mData.getResult().getSk().getWind_strength());
		return rootView;
	}

}

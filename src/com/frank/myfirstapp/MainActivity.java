package com.frank.myfirstapp;



import java.lang.reflect.Type;
import java.util.List;

import com.frank.myfirstapp.fragments.SKFragment;
import com.frank.myfirstapp.fragments.TodayFragment;
import com.frank.myfirstapp.utils.HttpCallBackListener;
import com.frank.myfirstapp.utils.SendHttpURLRequestUtils;
import com.frank.myfirstapp.utils.WeatherData;




import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.NavigationMode;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements HttpCallBackListener {
	
	public static final int INIT_WEATHERDATA = 0 ;
	
	private ActionBar bar;
	private ProgressBar mProgressBar;
	private Fragment todayFragment;
	private Fragment skFragment;
	@SuppressWarnings("deprecation")
	private Tab todayTab;
	private Tab skTab;
	private LocationListener locationListener;
	private Handler handler;
	
	private WeatherData mWeatherData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化bar
		bar = getSupportActionBar();
		bar.setTitle("天气预报");
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		/*
		 * 设置progressbar
		 */
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		mProgressBar.setVisibility(View.VISIBLE);
		prepareWeatherData();
		
		
	}

	@SuppressWarnings("deprecation")
	private void initTabs() {
		// TODO Auto-generated method stub
		todayTab = bar.newTab().setText("今日天气").setTabListener(new TestListener(todayFragment));
		skTab = bar.newTab().setText("实时天气").setTabListener(new TestListener(skFragment));
		bar.addTab(todayTab);
		bar.addTab(skTab);
	}

	private void initFragments() {
		// TODO Auto-generated method stub
		if(mWeatherData != null){
			Toast.makeText(this, "mWeather isnot null",Toast.LENGTH_SHORT).show();
			todayFragment = new TodayFragment(mWeatherData);
			skFragment = new SKFragment(mWeatherData);
		}
	}

	private void prepareWeatherData() {
		// TODO Auto-generated method stub
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case INIT_WEATHERDATA:
					String response = (String) msg.obj;
					praseJsonWithGson(response);
					break;

				default:
					break;
				}
			}
		};
		initLocatonListener();
		Location mLocation = getLocation();
		String mAddress = getAddress(mLocation);
		SendHttpURLRequestUtils.sendHttpURLRequest(mAddress, this);
	}
	protected void praseJsonWithGson(String json) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Type type = new TypeToken<WeatherData>(){}.getType();
		mWeatherData = gson.fromJson(json, type);
		Toast.makeText(this, "mWeatherData got", Toast.LENGTH_SHORT).show();
		mProgressBar.setVisibility(View.GONE);
		initFragments();
		initTabs();
	}
	private String getAddress(Location location) {
		// TODO Auto-generated method stub
		
		String key = "0592c33c2af8304804de563b82a277f9";
		String ret = "http://v.juhe.cn/weather/geo?format=2&key=" 
					+ key
					+ "&lon=" 
					+ location.getLongitude()
					+ "&lat=" 
					+ location.getLatitude();
		return ret;
		
	}
	
	private void initLocatonListener() {
		// TODO Auto-generated method stub
		locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location loc) {
				// TODO Auto-generated method stub
				Log.d("MainActivity,","onLocationChanged");
		          if (loc != null) {
		                   Log.d("MainActivity", "onLocationChanged. latitude: "
		                            + loc.getLatitude() + " , longtitude: " + loc.getLongitude());

		         } else {
		             Toast.makeText( MainActivity.this, "Your current location is temporarily unavailable.",
		                 Toast.LENGTH_SHORT).show();
		         }
			}
		};
		
	}
	
	private Location getLocation() {
		// TODO Auto-generated method stub
		
		Location location = null;

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
//		Location location = null;
		String provider = null ;
		List<String> providerList = locationManager.getProviders(true);
		
		if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
			
			
			provider = LocationManager.NETWORK_PROVIDER;
		}
		else if(providerList.contains(LocationManager.GPS_PROVIDER)){
			provider = LocationManager.GPS_PROVIDER;
		}
		else{
			Toast.makeText(this, "please open your GPS or NetWork", Toast.LENGTH_SHORT).show();
			return null;
		}
//		provider = locationManager.NETWORK_PROVIDER;
		
		
		if(provider != null){
			location = locationManager.getLastKnownLocation(provider);
			Toast.makeText(this, "provider got" , Toast.LENGTH_SHORT).show();
			while(location == null){
				locationManager.requestLocationUpdates(provider, 80000, 100, locationListener);
//				location = locationManager.getLastKnownLocation(provider);
			}
			return location;
			
		}
		
		
		else{
			Log.d("MainActivity", "no location");
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class TestListener implements TabListener{
		
		private Fragment fragment;
		
		public TestListener(Fragment fragment) {
			// TODO Auto-generated constructor stub
			this.fragment = fragment;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			ft.add(R.id.activity_main, fragment, null);
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.remove(fragment);
		}
		
	}
	
	/**
	 * Httpcallbacklistener 的方法
	 * @param response
	 */
	@Override
	public void onFinish(String response) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = 0;
		msg.obj = response; /*把返回的Json数据转为String传入Message*/
		
		handler.sendMessage(msg);/*发送Message*/
	}

	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
		
	}
}

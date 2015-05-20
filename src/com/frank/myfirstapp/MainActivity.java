package com.frank.myfirstapp;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
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

import com.frank.myfirstapp.fragments.SKFragment;
import com.frank.myfirstapp.fragments.TodayFragment;
import com.frank.myfirstapp.utils.HttpCallBackListener;
import com.frank.myfirstapp.utils.SendHttpURLRequestUtils;
import com.frank.myfirstapp.utils.Utils;
import com.frank.myfirstapp.utils.WeatherData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends ActionBarActivity  {
	
	public static final int INIT_WEATHERDATA = 0 ;
	
	private UpdateAsyncTask upDateAsyncTask;
	
	private String address;
	
	private Location location;
	
	private ActionBar bar;
	private ProgressBar mProgressBar;
	private TodayFragment todayFragment;
	private SKFragment skFragment;
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
		
		/**
		 * 初始化ActionBar ProgressBar LocationListener
		 */
		initViews();
		
		UpDate();
		
		
	}

	private void UpDate() {
		// TODO Auto-generated method stub
		location = getLocation();
		address = getAddress(location);
		
		/**
		 * 根据address返回WeatherData,存入Sharepreference,然后initFragment initTabs
		 */
		upDateAsyncTask = new UpdateAsyncTask();
		upDateAsyncTask.execute(address);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		bar = getSupportActionBar();
		bar.setTitle("天气预报");
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		/*
		 * 设置progressbar
		 */
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
//		initFragments();
		initLocatonListener();
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
	
		todayFragment = new TodayFragment();
		skFragment = new SKFragment();
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
			Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
			bar.removeAllTabs();
			UpDate();
			
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
	

	class UpdateAsyncTask extends AsyncTask<String, Void, WeatherData>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mProgressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected WeatherData doInBackground(String... addresses) {
			// TODO Auto-generated method stub
			String address = addresses[0];
			
			HttpURLConnection connection = null;
			InputStream input = null;
			BufferedReader reader = null;
			WeatherData mWeatherData = null;
			try {
				URL url = new URL(address);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setReadTimeout(8000);
				connection.setConnectTimeout(8000);
				input = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(input));
				StringBuffer sb = new StringBuffer();
				String line ;
				while((line = reader.readLine()) != null){
					sb.append(line);
				}
				Log.d("may", "done herer");
				mWeatherData = Utils.praseJsonWithGson(sb.toString());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if(input != null){
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(reader != null){
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				connection.disconnect();
			}
			if(mWeatherData == null){
				Log.d("May", "null data");
			}
			return mWeatherData;
		}
		
		@Override
		protected void onPostExecute(WeatherData result) {
			// TODO Auto-generated method stub
			/*
			 * 更新数据库
			 */
			Utils.updataWeatherData(result);
			initFragments();
			initTabs();
			mProgressBar.setVisibility(View.GONE);
			super.onPostExecute(result);
		}

		
		
	}
	
	
}

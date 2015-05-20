package com.frank.myfirstapp.services;

import com.frank.myfirstapp.broadcasts.ContiniueLongRunningServiceBroadcastReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("Service", "running");
				
			}
		}).start();
		
		
		
		/*
		 * 设置触发间隙
		 */
		int Aminiute = 1000 * 60 ;
		long triggerAtMillis = SystemClock.elapsedRealtime() + Aminiute;
		
		/**
		 * 设置PendingIntent
		 */
		Intent i = new Intent(this, ContiniueLongRunningServiceBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		/*
		 * 设置AlarmManager
		 */
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pi);
		
		return super.onStartCommand(intent, flags, startId);
	}

}

package com.frank.myfirstapp.broadcasts;

import com.frank.myfirstapp.services.LongRunningService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ContiniueLongRunningServiceBroadcastReceiver extends
		BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "weather data updated", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(context, LongRunningService.class);
		context.startService(i);
	}

}

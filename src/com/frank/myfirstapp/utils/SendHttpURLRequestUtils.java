package com.frank.myfirstapp.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class SendHttpURLRequestUtils {
	
	public static void sendHttpURLRequest(final String address ,  final HttpCallBackListener listener ){
		new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpURLConnection connection = null;
			try{
				URL url = new URL(address);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("GET");
				connection.setReadTimeout(8000);
				connection.setConnectTimeout(8000);
				
				InputStream in = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				
				String line;
				StringBuffer sb = new StringBuffer();
				
				while((line = reader.readLine()) != null){
					
					sb.append(line).append("\n");/* ������Ҫ�ᳵ��*/
				}
				
				if(listener != null){
					listener.onFinish(sb.toString());
				}
				
//				Message msg = new Message();
//				msg.what = 0;
//				msg.obj = sb.toString(); /*�ѷ��ص�Json����תΪString����Message*/
//				
//				handler.sendMessage(msg);/*����Message*/
				
			}
			catch(Exception e){
				if(listener != null){
					listener.onError(e);
				}
			}
			finally{
				if(connection != null){
					connection.disconnect();
				}
			}
		}
	}).start();
	}

}

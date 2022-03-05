package com.yssj.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import com.alibaba.fastjson.JSONObject;

public class GPSService extends Service {

	// GPS定位用到位置服务
	private LocationManager lm;
	private MyLoacationListener listener;
	private SharedPreferences sp;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);

		/*
		 * //获得GPS定位的方式 List<String> providers = lm.getAllProviders();
		 * System.out.println(providers.toString());
		 */
		
		listener = new MyLoacationListener();

		// 注册监听位置服务
		// 获取最好的可用位置提供者，因为有三种方式，不要写死一种，根据情况使用哪种位置提供者

		// 给位置提供者提供条件
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 设置精确度越精准越好
		criteria.setAltitudeRequired(false);// 不要求海拔信息
		criteria.setBearingRequired(false);// 不要求方位信息
		criteria.setCostAllowed(true);// 是否允许付费
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 对电量的要求

		String provider = lm.getBestProvider(criteria, true);

		lm.requestLocationUpdates(provider, 0, 0, listener);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消监听位置服务
		lm.removeUpdates(listener);
		listener = null;
	}

	class MyLoacationListener implements LocationListener {

		/**
		 * 当位置改变的时候回调，这样获获取的经纬度是标准坐标，放到google地图搜索会有偏移，因此需要通过火星坐标转换
		 */
		@Override
		public void onLocationChanged(Location location) {

			Double longitude =  location.getLongitude() ; // 获得经度
			Double latitude =  location.getLatitude() ;
			
			
			reverse(latitude,longitude);
			
		}

		/**
		 * 当状态发生改变的时候回调（开启到关闭，从关闭到开启）
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		/**
		 * 当某一个位置提供者可以使用了回调
		 */
		@Override
		public void onProviderEnabled(String provider) {

		}

		/**
		 * 当某一个位置提供者不可以使用了回调
		 */
		@Override
		public void onProviderDisabled(String provider) {

		}
	}
	
	/**解析坐标为地址*/
	private void reverse(final Double latitude, final Double longitude){
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				URLConnection connection ;
				try {
					URL url = new URL(
							"http://api.map.baidu.com/geocoder/v2/?ak="
//									+ "pA2N9r3tH7RFaElIfst6CzlY"
									+ "iYAdCiInWrWMnuR0GGiCn73S"
									+ "&location="
									+ latitude + "," + longitude
									+ "&output=json&coordtype=wgs84ll");
					connection = url.openConnection();
					connection.setDoOutput(true);
					OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
					out.flush();
					out.close();
					// 一旦发送成功，用以下方法就可以得到服务器的回应：
					InputStream  l_urlStream = connection.getInputStream();
					String content = getStringFromInputStream(l_urlStream);
					
					
					JSONObject obj = JSONObject.parseObject(content);
					if(obj !=null && "0".equals(obj.getString("status"))){
						
						JSONObject jsonObject = obj.getJSONObject("result");
						String address = jsonObject.getString("formatted_address");
						
//						System.out.println("address:" + address);
						
						sp = getSharedPreferences("config", MODE_PRIVATE) ;
						Editor edit = sp.edit();
						edit.putString("lastlocation", address);
						edit.commit();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		}.execute();
	}
	
	/**
	 * 根据流返回一个字符串信息
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	private static String getStringFromInputStream(InputStream is) throws IOException{
		// 缓存流
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		
		while((len = is.read(buffer))!=-1 ){
			bao.write(buffer, 0, len);
		}
		
		is.close();
		
		String html = bao.toString();
		bao.close();
		return html;
	}

}

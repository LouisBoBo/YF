package com.yssj.utils;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CheckStrUtil {
	/**
	 * 判断邮编
	 * 
	 * @param paramString
	 * @return
	 */
//	public static boolean isZipNO(String zipString) {
//		String str = "^[0-9][0-9]{5}$";
//		return Pattern.compile(str).matcher(zipString).matches();
//	}
	
	// 校验 极光推送 Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    
    public static String getImei(Context context) {
    	String imei = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			LogYiFu.e(CheckStrUtil.class.getSimpleName(), e.getMessage());
		}
		return imei;
	}

//	public static String getMac(Context context) {
//				//获取用户MAC
//		
//				String macAddress = null;
//				WifiManager wifiMgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//				WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());  
//				macAddress = info.getMacAddress();  
//				LogYiFu.e("macAddress",macAddress);
//				 
//		return macAddress;
//	}
	
	
	
	public static String getMac(Context context){  
        String str="";  
        String macSerial="";  
        try {  
            Process pp = Runtime.getRuntime().exec(  
                    "cat /sys/class/net/wlan0/address ");  
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
  
            for (; null != str;) {  
                str = input.readLine();  
                if (str != null) {  
                    macSerial = str.trim();// 去空格  
                    break;  
                }  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        if (macSerial == null || "".equals(macSerial)) {  
            try {  
                return loadFileAsString("/sys/class/net/eth0/address")  
                        .toUpperCase().substring(0, 17);  
            } catch (Exception e) {  
                e.printStackTrace();  
                  
            }  
              
        }  
        return macSerial;  
    }  
      public static String loadFileAsString(String fileName) throws Exception {  
            FileReader reader = new FileReader(fileName);    
            String text = loadReaderAsString(reader);  
            reader.close();  
            return text;  
        }  
      public static String loadReaderAsString(Reader reader) throws Exception {  
            StringBuilder builder = new StringBuilder();  
            char[] buffer = new char[4096];  
            int readLength = reader.read(buffer);  
            while (readLength >= 0) {  
                builder.append(buffer, 0, readLength);  
                readLength = reader.read(buffer);  
            }  
            return builder.toString();  
        }  
	
	
	
	
	
	
	
	
	
	
}

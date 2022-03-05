
/** 
* @author 作者 E-mail: 
* @version 创建时间：2016年8月24日 下午5:33:08 
* 类说明 
*/ 
package com.yssj.utils;


import com.yssj.activity.R;
import android.annotation.SuppressLint;  
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;  
import android.widget.Toast; 

/**
* @author Administrator
* @date 2016年8月24日下午5:33:08
*/
public class CenterToast {

   public static  Toast toast;  
      
  
    public static void centerToast(Context context ,String msg){
        if(toast == null){
            toast = new Toast(context);
        }
        toast.setDuration(2);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_custom, null);
        LinearLayout toastLayout = (LinearLayout)view;
        TextView txtToast = (TextView)toastLayout.findViewById(R.id.txt_toast);
        txtToast.getBackground().setAlpha(204);
        txtToast.setText(msg);
        toast.setView(toastLayout);
        toast.show();
    }
    public static void centerLongToast(Context context ,String msg){
    	if(toast == null){
    		toast = new Toast(context);
    	}
    	toast.setDuration(1);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	LayoutInflater inflater = LayoutInflater.from(context);
    	View view = inflater.inflate(R.layout.toast_custom_long, null);
    	LinearLayout toastLayout = (LinearLayout)view;
    	TextView txtToast = (TextView)toastLayout.findViewById(R.id.txt_toast);
    	txtToast.getBackground().setAlpha(204);
    	txtToast.setText(msg);
    	toast.setView(toastLayout);
    	toast.show();
    }
	
	
	
}

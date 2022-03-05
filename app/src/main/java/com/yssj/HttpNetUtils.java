package com.yssj;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;

import com.yssj.app.SAsyncTask;
import com.yssj.network.YConn;

import java.util.HashMap;

public class HttpNetUtils {




    //个人中心数据
//    public static void getPerosnCenterCount(FragmentActivity mContext ,final HashMap<String,String> pairsMap , final MyHttpUtilsInterface myHttpUtilsInterface) {
//
//        new SAsyncTask<Void, Void, String>(mContext, 0) {
//            @Override
//            protected String doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//
//                return  YConn.basedPost2(YUrl.PERSON_CENTER_COUNT,pairsMap);
//
//            }
//
//            @SuppressLint("UseValueOf")
//            @Override
//            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null && result != null) {
//                    myHttpUtilsInterface.onSuccess(result);
//                }else{
//                    myHttpUtilsInterface.onError();
//                }
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//        }.execute();
//
//    }


//    public interface MyHttpUtilsInterface {
//        void onSuccess(String result);
//        void onError();
//    }


}

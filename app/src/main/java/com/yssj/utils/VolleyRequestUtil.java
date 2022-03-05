//package com.yssj.utils;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.toolbox.StringRequest;
//import com.yssj.YJApplication;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.network.YConn;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import static com.yssj.network.YConn.Base64Encode;
//
///**
// * Created by lifeng on 2017/6/29.
// */
//
//public class VolleyRequestUtil {
//
//
//    public static StringRequest stringRequest;
//    public static Context context;
//
//    /*
//    * 获取GET请求内容
//    * 参数：
//    * context：当前上下文；
//    * url：请求的url地址；
//    * tag：当前请求的标签；
//    * volleyListenerInterface：VolleyListenerInterface接口；
//    * */
//    public static void RequestGet(Context context, String url, String tag, VolleyListenerInterface volleyListenerInterface) {
//        // 清除请求队列中的tag标记请求
//        YJApplication.getRequestQueue().cancelAll(tag);
//        // 创建当前的请求，获取字符串内容
//        stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener());
//        // 为当前请求添加标记
//        stringRequest.setTag(tag);
//        // 将当前请求添加到请求队列中
//        YJApplication.getRequestQueue().add(stringRequest);
//        // 重启当前请求队列
//        YJApplication.getRequestQueue().start();
//    }
//
//    /*
//    * 获取POST请求内容（请求的代码为Map）
//    * 参数：
//    * context：当前上下文；
//    * url：请求的url地址；
//    * tag：当前请求的标签；
//    * params：POST请求内容；
//    * volleyListenerInterface：VolleyListenerInterface接口；
//    * */
//    public static void RequestPost(Context context, String url, String tag, final Map<String, String> paramsMap, VolleyListenerInterface volleyListenerInterface) throws Exception {
//        // 清除请求队列中的tag标记请求
//        YJApplication.getRequestQueue().cancelAll(tag);
//
//        //添加必传参数
//
//
////        HttpParams params = new BasicHttpParams();
////        HttpConnectionParams.setConnectionTimeout(params, 30 * 1000); // 30 s
////        HttpConnectionParams.setSoTimeout(params, 30 * 1000); // 30s
//
//
//        HttpPost post = new HttpPost(url);
//
//        HttpEntity entity = new UrlEncodedFormEntity(new ArrayList<NameValuePair>(), "utf-8");
//
//        if (entity != null) {
//            post.setEntity(entity);
//        }
//
//
//        String urlAuth = TextUtils.isEmpty(EntityUtils.toString(entity, "utf-8")) ? post.getURI().toURL().toString()
//                : post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
//
//        String authKey = AuthKeyTools.builderURL(urlAuth, YConn.AUTH_Key);
//        String ss = Base64Encode(authKey.toString());
//
//
//        paramsMap.put("authKey", authKey);
//        paramsMap.put("I10o", ss);
//        paramsMap.put("channel", DeviceUtils.getChannelCode(context));
//        paramsMap.put("version", ComModel2.versionCode);
//
//        LogYiFu.e("posturl", url+ "?" + paramsMap);
//
//        // 创建当前的POST请求，并将请求内容写入Map中
//        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.responseListener(), volleyListenerInterface.errorListener()) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return paramsMap;
//            }
//        };
//        // 为当前请求添加标记
//        stringRequest.setTag(tag);
//        // 将当前请求添加到请求队列中
//        YJApplication.getRequestQueue().add(stringRequest);
//        // 重启当前请求队列
//        YJApplication.getRequestQueue().start();
//    }
//
//
//}

package com.yssj.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.yssj.Json;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.dialog.ForgetPasswordDidalog;
import com.yssj.utils.AuthKeyTools;
import com.yssj.utils.CenterToast;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.JSONUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.YCache;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import static com.yssj.model.ComModel2.versionCode;

@SuppressWarnings("JniMissingFunction")
public class YConn {
    public static final String AUTH_Key = "yunshangshiji";
    public static int i = 0;




    // com.yssj.network.YConn
    // Java_com_yssj_network_Yconn_函数名

    static {
        System.loadLibrary("Base64zzq");
    }

    public static native String Base64Encode(String cs); // 第三方库函数

    public static void showErrorToast(final Context context, Throwable throwable, String message) {
        boolean showError = true;

        String text = null;
        String msg = "";
        //异常判断
        if (throwable instanceof YException) {
            msg = throwable.getMessage();
            int errorCode = ((YException) throwable).getErrorCode();
            if (errorCode == YException.EMPTY_CODE) {
                text = context.getString(R.string.empty_result);

            } else if (errorCode == 10030) {
                CommonUtils.clearClipboardContent(context);
                SharedPreferencesUtil.saveStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
                // 您的账号已在另外一台设备上登录，请重新登录...
                text = "账号已在别处登陆,请重新登录";
                SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false);
                SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
                YJApplication.instance.setLoginSucess(false);
            } else {
                text = (msg == null ? context.getString(R.string.unknow_error) : msg);
            }
        } else if (throwable instanceof ConnectTimeoutException) {
            // 连接超时
            text = context.getString(R.string.conect_timeout);
        } else if (throwable instanceof SocketTimeoutException) {
            // 访问超时
            text = context.getString(R.string.timeout);
        } else if (throwable instanceof IOException) {
            // 网络链接错误 ----网络开小差啦，请检查一下网络
            text = context.getString(R.string.not_connect);
        } else if (throwable instanceof JSONException) {
            showError = false;
            // 解析出错
            text = context.getString(R.string.parse_error);
        } else {
            showError = false;
            // text = context.getString(R.string.unknow_error);
            text = "发生未知错误";
//			SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
            // true代表已经登录
//			Boolean ISCHUCHIDNEGLU = SharedPreferencesUtil.getBooleanData(context, "ISCHUCHIDNEGLU", false);

//			if (ISCHUCHIDNEGLU) {
//
//				SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改为未登录
//
//				SharedPreferencesUtil.getBooleanData(context, Pref.ISACLASS, false);
//
//				YCache.cleanToken(context);
//				YCache.cleanUserInfo(context);
//				ComModel.clearLoginFlag(context);
//				AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//				YJApplication.isLogined = false;
//
//				if(MainMenuActivity.instances!=null){
//				MainFragment fragment = MainMenuActivity.instances.getFragment();
//
//				if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
//					try {
//						fragment.getChildFragmentManager().beginTransaction()
//						.remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//				}
//
//				if (LoginActivity.instances != null) {
//					LoginActivity.instances.finish();
//				}
//
//				Intent intent = new Intent(context, LoginActivity.class);
//				intent.putExtra("login_register", "login");
//
//				((FragmentActivity) context).startActivity(intent);
//
//			}

        }

        if(text.indexOf("解析") != -1){
            return;
        }
        //错误信息判断
        if (msg.equals("unknow") || msg.endsWith("每天只能获取一次.") || msg.equals("店铺编号不能为空") || msg.contains("乱搞") || msg.contains("参数错误") || msg.contains("你已经开过团")) {

        } else if (msg.contains("验证码已过期") || msg.contains("请填写正确的验证码")) {
            CenterToast.centerToast(context, msg);
        } else if (msg.contains("手机号已被注册")) {
            new ForgetPasswordDidalog(context).show();
        } else if (msg.equals("操作成功.")) {
            CenterToast.centerToast(context, "操作成功");
        } else {

            if ((showError || YUrl.debug)&& !text.contains("别处")) {
                CenterToast.centerToast(context, text);

            }
        }

        // MyLogYiFu.e("context", text);

    }


    public static void showErrorToastErrorUrl(final Context context, Throwable throwable, String message, String errorUrl) {
        boolean showError = true;
        String text = null;
        String msg = "";
        //异常判断
        if (throwable instanceof YException) {
            msg = throwable.getMessage();
            int errorCode = ((YException) throwable).getErrorCode();
            if (errorCode == YException.EMPTY_CODE) {
                text = context.getString(R.string.empty_result);

            } else if (errorCode == 10030) {
                CommonUtils.clearClipboardContent(context);
                SharedPreferencesUtil.saveStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
                // 您的账号已在另外一台设备上登录，请重新登录...
                text = "账号已在别处登陆,请重新登录";
                SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false);
                SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
            } else {
                text = (msg == null ? context.getString(R.string.unknow_error) : msg);
            }
        } else if (throwable instanceof ConnectTimeoutException) {
            // 连接超时
            text = context.getString(R.string.conect_timeout);
        } else if (throwable instanceof SocketTimeoutException) {
            // 访问超时
            text = context.getString(R.string.timeout);
        } else if (throwable instanceof IOException) {
            // 网络链接错误 ----网络开小差啦，请检查一下网络
            text = context.getString(R.string.not_connect);
        } else if (throwable instanceof JSONException) {
            showError = false;
            // 解析出错
            text = context.getString(R.string.parse_error);
        } else {
            showError = false;
            // text = context.getString(R.string.unknow_error);
            text = "发生未知错误-----" + errorUrl;
//			SharedPreferencesUtil.saveBooleanData(context, "isrelogin", true);
            // true代表已经登录
//			Boolean ISCHUCHIDNEGLU = SharedPreferencesUtil.getBooleanData(context, "ISCHUCHIDNEGLU", false);

//			if (ISCHUCHIDNEGLU) {
//
//				SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改为未登录
//
//				SharedPreferencesUtil.getBooleanData(context, Pref.ISACLASS, false);
//
//				YCache.cleanToken(context);
//				YCache.cleanUserInfo(context);
//				ComModel.clearLoginFlag(context);
//				AppManager.getAppManager().finishAllActivityOfEveryDayTask();
//				YJApplication.isLogined = false;
//
//				if(MainMenuActivity.instances!=null){
//				MainFragment fragment = MainMenuActivity.instances.getFragment();
//
//				if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
//					try {
//						fragment.getChildFragmentManager().beginTransaction()
//						.remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//				}
//
//				if (LoginActivity.instances != null) {
//					LoginActivity.instances.finish();
//				}
//
//				Intent intent = new Intent(context, LoginActivity.class);
//				intent.putExtra("login_register", "login");
//
//				((FragmentActivity) context).startActivity(intent);
//
//			}

        }

        if(text.indexOf("解析") != -1){
            return;
        }

        //错误信息判断
        if (msg.equals("unknow") || msg.endsWith("每天只能获取一次.") || msg.equals("店铺编号不能为空") || msg.contains("乱搞") || msg.contains("参数错误") || msg.contains("你已经开过团")) {

        } else if (msg.contains("验证码已过期") || msg.contains("请填写正确的验证码")) {
            CenterToast.centerToast(context, msg);
        } else if (msg.contains("手机号已被注册")) {
            new ForgetPasswordDidalog(context).show();
        } else if (msg.equals("操作成功.")) {
            CenterToast.centerToast(context, "操作成功");
        } else {

            if ((showError || YUrl.debug)&& !text.contains("别处")) {
                CenterToast.centerToast(context, text);

            }

        }

        // MyLogYiFu.e("context", text);

    }


    public static String getCheckGifCode(final Context context, String url, List<NameValuePair> nameValuePairs)
            throws Exception {

        HttpPost post = new HttpPost(url);
        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
        if (entity != null) {
            post.setEntity(entity);
        }
        String urlAuth = TextUtils.isEmpty(EntityUtils.toString(entity, "utf-8")) ? post.getURI().toURL().toString()
                : post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
//        LogYiFu.e("urlAuth", "" + urlAuth);

        String authKey = AuthKeyTools.builderURL(urlAuth, AUTH_Key);

        String ss = Base64Encode(authKey.toString());

        nameValuePairs.add(new BasicNameValuePair("authKey", authKey));
        nameValuePairs.add(new BasicNameValuePair("I10o", ss));
        nameValuePairs.add(new BasicNameValuePair("channel", DeviceUtils.getChannelCode(context)));


        PackageManager pm = null;
        String appVersion = "0";
        try {
            pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appVersion = "V" + pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameValuePairs.add(new BasicNameValuePair("appVersion", appVersion));

        entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
        if (entity != null) {
            post.setEntity(entity);
        }


        String mUrl = post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
        LogYiFu.e("posturl", "" + post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8"));

        return mUrl;
    }


    public static String basedPost(final Context context, String url, List<NameValuePair> nameValuePairs)
            throws Exception {

        String result = "";

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 30 * 1000); // 30 s
        HttpConnectionParams.setSoTimeout(params, 30 * 1000); // 30s
        DefaultHttpClient client = new DefaultHttpClient(params);

        HttpPost post = new HttpPost(url);
        // post.setHeader("User-Agent", getUserAgent(context));
        // post.setHeader("Cookie", getCookieString(context));

        post.setHeader("Accept-Encoding", "gzip, deflate");
        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
        if (entity != null) {
            post.setEntity(entity);
        }
        String urlAuth = TextUtils.isEmpty(EntityUtils.toString(entity, "utf-8")) ? post.getURI().toURL().toString()
                : post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
//        LogYiFu.e("urlAuth", "" + urlAuth);

        // StringBuilder sb = new StringBuilder();
        // String authKey = AuthKeyTools.MD5(urlAuth, AUTH_Key);
        String authKey = AuthKeyTools.builderURL(urlAuth, AUTH_Key);

        // String ss = MyBase64.Base64Encode(authKey.toString().getBytes());
        String ss = Base64Encode(authKey.toString());
        nameValuePairs.add(new BasicNameValuePair("authKey", authKey));
        nameValuePairs.add(new BasicNameValuePair("I10o", ss));
        nameValuePairs.add(new BasicNameValuePair("channel", DeviceUtils.getChannelCode(context)));
        nameValuePairs.add(new BasicNameValuePair("app_id", YUrl.APP_ID));


        PackageManager pm = null;
        String appVersion = "0";
        try {
            pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appVersion = "V" + pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameValuePairs.add(new BasicNameValuePair("appVersion", appVersion));


        entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
        if (entity != null) {
            post.setEntity(entity);
        }
        LogYiFu.e("posturl", "" + post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8"));

        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {


            Header[] headers = response.getHeaders("Content-Encoding");
            boolean isGzip = false;
            boolean isDeflate = false;

            for (Header header : headers) {
                String value = header.getValue();
                if (value.equals("gzip")) {
                    isGzip = true;
                }
            }

            for (Header header : headers) {
                String value = header.getValue();
                if (value.equals("deflate")) {
                    isDeflate = true;
                }
            }


            if (isGzip) {
                LogYiFu.e("isGzip-url", url);
                InputStream is = response.getEntity().getContent();
                GZIPInputStream gzipIn = new GZIPInputStream(is);
                result = streamToString(gzipIn);
                LogYiFu.e("isGzip", result);

            } else if (isDeflate) {
                LogYiFu.e("isDeflate-url", url);
                InputStream is = response.getEntity().getContent();
                DeflaterInputStream deflaterIn = new DeflaterInputStream(is);
                result = streamToString(deflaterIn);
                LogYiFu.e("isDeflate-url", result);

            } else {
//                LogYiFu.e("mainUrl", url);

                result = EntityUtils.toString(response.getEntity());
//                LogYiFu.e("mainUrl", result);

            }


            LogYiFu.e("result", result);
            if (TextUtils.isEmpty(result)) {
                throw new YException(YException.EMPTY_CODE);
            }
            JSONObject jsonObj = new JSONObject(result);

            int jsonStatus = jsonObj.optInt(Json.status);
            String jsonMessage = jsonObj.optString(Json.message);
            if (jsonStatus == 1) {// 注册成功
                return result;
            } else if (jsonStatus == 2) {
                if ("修改订单状态失败".equals(jsonMessage)) {
                    throw new YException(jsonStatus, "等收货了再晒也不迟哟~~");
                } else if ("无返回数据.".equals(jsonMessage) || "".equals(jsonMessage) || jsonMessage == null) {
                } else {
                    throw new YException(jsonStatus, jsonMessage);
                }
            } else if (jsonStatus == 10030) {
                CommonUtils.clearClipboardContent(context);
                SharedPreferencesUtil.saveStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
                // true代表已经登录
                Boolean ISCHUCHIDNEGLU = SharedPreferencesUtil.getBooleanData(context, "ISCHUCHIDNEGLU", true);

                if (ISCHUCHIDNEGLU) {

                    SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改为未登录

                    YCache.cleanToken(context);
                    YCache.cleanUserInfo(context);
                    ComModel.clearLoginFlag(context);
//					AppManager.getAppManager().finishAllActivityOfEveryDayTask();
                    YJApplication.isLogined = false;

                    MainFragment fragment = MainMenuActivity.instances.getFragment();

                    if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
                        fragment.getChildFragmentManager().beginTransaction()
                                .remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
                    }

                    if (LoginActivity.instances != null) {
                        LoginActivity.instances.finish();
                    }

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("login_register", "login");
                    intent.putExtra("mustLogin",true);

                    ((FragmentActivity) context).startActivity(intent);

                    throw new YException(jsonStatus, jsonMessage);

                }


            } else if (jsonStatus == 101) {
                if (!jsonMessage.equals("设置成功.")) {
                    UserInfo user = EntityFactory.createYUser(context, result);
                    EntityFactory.createStore(context, result);// 保存店铺数据
                    // user.setUsertype(-1);
                    String logStringoken = JSONUtils.getString(result, Json.token, "");
                    YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                    YCache.cacheUserInfo(context, logStringoken, user);
                }

                throw new YException(jsonStatus, jsonMessage);
            } else if (jsonStatus == 1051) {
                UserInfo user = EntityFactory.createYUser(context, result);
                EntityFactory.createStore(context, result);// 保存店铺数据
                // user.setUsertype(-1);
                String logStringoken = JSONUtils.getString(result, Json.token, "");
                YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                YCache.cacheUserInfo(context, logStringoken, user);
                throw new YException(jsonStatus, jsonMessage);
            } else if (jsonStatus == 20001) {//用户没有资格参与集赞活动

                //无需提示用户
//                throw new YException(jsonStatus, jsonMessage);


            } else { //其他情况
                throw new YException(jsonStatus, jsonMessage);

            }

        } else if (status == 500) {// 内部服务器错误

            throw new YException(status, context.getString(R.string.server_is_busy));
        } else {
            throw new YException(status);
        }
        return result;
    }

    public interface MyHttpPostListener {
        void onSuccess(String result);

        void onError();
    }


    public static void basedPost2(Context context, final String url, final HashMap<String, String> pairsMap, final MyHttpPostListener myHttpPostListener) {

        new SAsyncTask<Void, Void, String>((FragmentActivity) context, 0) {
            @Override
            protected String doInBackground(FragmentActivity context, Void... paramss)
                    throws Exception {

                String result = "";

                List<NameValuePair> nameValuePairs = new ArrayList<>();

                for (Map.Entry<String, String> entry : pairsMap.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                nameValuePairs.add(new BasicNameValuePair("version", versionCode));
                if (!StringUtils.isEmpty(YCache.getCacheToken(context))) {
                    nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
                }

                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 30 * 1000); // 30 s
                HttpConnectionParams.setSoTimeout(params, 30 * 1000); // 30s
                DefaultHttpClient client = new DefaultHttpClient(params);

                HttpPost post = new HttpPost(url);
                // post.setHeader("User-Agent", getUserAgent(context));
                // post.setHeader("Cookie", getCookieString(context));

                post.setHeader("Accept-Encoding", "gzip, deflate");
                HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                if (entity != null) {
                    post.setEntity(entity);
                }
                String urlAuth = TextUtils.isEmpty(EntityUtils.toString(entity, "utf-8")) ? post.getURI().toURL().toString()
                        : post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
//        LogYiFu.e("urlAuth", "" + urlAuth);

                // StringBuilder sb = new StringBuilder();
                // String authKey = AuthKeyTools.MD5(urlAuth, AUTH_Key);
                String authKey = AuthKeyTools.builderURL(urlAuth, AUTH_Key);

                // String ss = MyBase64.Base64Encode(authKey.toString().getBytes());
                String ss = Base64Encode(authKey.toString());

                nameValuePairs.add(new BasicNameValuePair("authKey", authKey));
                nameValuePairs.add(new BasicNameValuePair("I10o", ss));
                nameValuePairs.add(new BasicNameValuePair("channel", DeviceUtils.getChannelCode(context)));
                nameValuePairs.add(new BasicNameValuePair("app_id", YUrl.APP_ID));


                PackageManager pm = null;
                String appVersion = "0";
                try {
                    pm = context.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                    appVersion = "V" + pi.versionName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameValuePairs.add(new BasicNameValuePair("appVersion", appVersion));


                entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                if (entity != null) {
                    post.setEntity(entity);
                }
                LogYiFu.e("posturl", "" + post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8"));

                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {


                    Header[] headers = response.getHeaders("Content-Encoding");
                    boolean isGzip = false;
                    boolean isDeflate = false;

                    for (Header header : headers) {
                        String value = header.getValue();
                        if (value.equals("gzip")) {
                            isGzip = true;
                        }
                    }

                    for (Header header : headers) {
                        String value = header.getValue();
                        if (value.equals("deflate")) {
                            isDeflate = true;
                        }
                    }


                    if (isGzip) {
                        LogYiFu.e("isGzip-url", url);
                        InputStream is = response.getEntity().getContent();
                        GZIPInputStream gzipIn = new GZIPInputStream(is);
                        result = streamToString(gzipIn);
                        LogYiFu.e("isGzip", result);

                    } else if (isDeflate) {
                        LogYiFu.e("isDeflate-url", url);
                        InputStream is = response.getEntity().getContent();
                        DeflaterInputStream deflaterIn = new DeflaterInputStream(is);
                        result = streamToString(deflaterIn);
                        LogYiFu.e("isDeflate-url", result);

                    } else {
//                        LogYiFu.e("mainUrl", url);

                        result = EntityUtils.toString(response.getEntity());
//                        LogYiFu.e("mainUrl", result);

                    }


                    LogYiFu.e("result", result);
                    if (TextUtils.isEmpty(result)) {
                        throw new YException(YException.EMPTY_CODE);
                    }
                    JSONObject jsonObj = new JSONObject(result);

                    int jsonStatus = jsonObj.optInt(Json.status);
                    String jsonMessage = jsonObj.optString(Json.message);
                    if (jsonStatus == 1) {// 注册成功


                    } else if (jsonStatus == 2) {
                        if ("修改订单状态失败".equals(jsonMessage)) {
                            throw new YException(jsonStatus, "等收货了再晒也不迟哟~~");
                        } else if ("无返回数据.".equals(jsonMessage) || "".equals(jsonMessage) || jsonMessage == null) {
                        } else {
                            throw new YException(jsonStatus, jsonMessage);
                        }
                    } else if (jsonStatus == 10030) {
                        CommonUtils.clearClipboardContent(context);
                        SharedPreferencesUtil.saveStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
                        // true代表已经登录
                        Boolean ISCHUCHIDNEGLU = SharedPreferencesUtil.getBooleanData(context, "ISCHUCHIDNEGLU", true);

                        if (ISCHUCHIDNEGLU) {

                            SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改为未登录

                            YCache.cleanToken(context);
                            YCache.cleanUserInfo(context);
                            ComModel.clearLoginFlag(context);
//					AppManager.getAppManager().finishAllActivityOfEveryDayTask();
                            YJApplication.isLogined = false;

                            MainFragment fragment = MainMenuActivity.instances.getFragment();

                            if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
                                fragment.getChildFragmentManager().beginTransaction()
                                        .remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
                            }

                            if (LoginActivity.instances != null) {
                                LoginActivity.instances.finish();
                            }

                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("login_register", "login");
                            intent.putExtra("mustLogin",true);

                            ((FragmentActivity) context).startActivity(intent);

                            throw new YException(jsonStatus, jsonMessage);

                        }


                    } else if (jsonStatus == 101) {
                        if (!jsonMessage.equals("设置成功.")) {
                            UserInfo user = EntityFactory.createYUser(context, result);
                            EntityFactory.createStore(context, result);// 保存店铺数据
                            // user.setUsertype(-1);
                            String logStringoken = JSONUtils.getString(result, Json.token, "");
                            YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                            YCache.cacheUserInfo(context, logStringoken, user);
                        }

                        throw new YException(jsonStatus, jsonMessage);
                    } else if (jsonStatus == 1051) {
                        UserInfo user = EntityFactory.createYUser(context, result);
                        EntityFactory.createStore(context, result);// 保存店铺数据
                        // user.setUsertype(-1);
                        String logStringoken = JSONUtils.getString(result, Json.token, "");
                        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                        YCache.cacheUserInfo(context, logStringoken, user);
                        throw new YException(jsonStatus, jsonMessage);
                    } else if (jsonStatus == 20001) {//用户没有资格参与集赞活动

                        //无需提示用户
//                throw new YException(jsonStatus, jsonMessage);


                    } else { //其他情况
                        throw new YException(jsonStatus, jsonMessage);

                    }

                } else if (status == 500) {// 内部服务器错误

                    throw new YException(status, context.getString(R.string.server_is_busy));
                } else {
                    throw new YException(status);
                }


                return result;


            }

            @SuppressLint("UseValueOf")
            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);


                if (e == null && result != null) {
                    myHttpPostListener.onSuccess(result);


                } else {
                    myHttpPostListener.onError();

                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }
        }.execute();


    }


    public static void httpPost(Context context, final String url, final HashMap<String, String> pairsMap, final HttpListener httpListener) {

        new SAsyncTask<Void, Void, String>((FragmentActivity) context, 0) {
            @Override
            protected String doInBackground(FragmentActivity context, Void... paramss)
                    throws Exception {

                String result = "";

                List<NameValuePair> nameValuePairs = new ArrayList<>();

                for (Map.Entry<String, String> entry : pairsMap.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }

                nameValuePairs.add(new BasicNameValuePair("version", versionCode));
                if (!StringUtils.isEmpty(YCache.getCacheToken(context))) {
                    nameValuePairs.add(new BasicNameValuePair("token", YCache.getCacheToken(context)));
                }

                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 30 * 1000); // 30 s
                HttpConnectionParams.setSoTimeout(params, 30 * 1000); // 30s
                DefaultHttpClient client = new DefaultHttpClient(params);

                HttpPost post = new HttpPost(url);
                // post.setHeader("User-Agent", getUserAgent(context));
                // post.setHeader("Cookie", getCookieString(context));

                post.setHeader("Accept-Encoding", "gzip, deflate");
                HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                if (entity != null) {
                    post.setEntity(entity);
                }
                String urlAuth = TextUtils.isEmpty(EntityUtils.toString(entity, "utf-8")) ? post.getURI().toURL().toString()
                        : post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");
//        LogYiFu.e("urlAuth", "" + urlAuth);

                // StringBuilder sb = new StringBuilder();
                // String authKey = AuthKeyTools.MD5(urlAuth, AUTH_Key);
                String authKey = AuthKeyTools.builderURL(urlAuth, AUTH_Key);

                // String ss = MyBase64.Base64Encode(authKey.toString().getBytes());
                String ss = Base64Encode(authKey.toString());

                nameValuePairs.add(new BasicNameValuePair("authKey", authKey));
                nameValuePairs.add(new BasicNameValuePair("I10o", ss));
                nameValuePairs.add(new BasicNameValuePair("channel", DeviceUtils.getChannelCode(context)));

                if (null != pairsMap.get("isXCXqrCode")
                        && "1".equals(pairsMap.get("isXCXqrCode"))) {

//                    boolean flag = SharedPreferencesUtil.getBooleanData(YJApplication.instance, "change_change", true);
//                    String mApp_id;


//                    if (flag) {
//                        mApp_id = "wx79b8d262f2563c52";
//                    } else {
//                        mApp_id = "wxc211367f634ba3e9";
//                    }


                    //生成小程序的二维码需要用小程序的APP_ID
                    nameValuePairs.add(new BasicNameValuePair("app_id", YUrl.WX_MINIAPP_ID));


                } else {
                    nameValuePairs.add(new BasicNameValuePair("app_id", YUrl.APP_ID));

                }


                PackageManager pm = null;
                String appVersion = "0";
                try {
                    pm = context.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                    appVersion = "V" + pi.versionName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameValuePairs.add(new BasicNameValuePair("appVersion", appVersion));


                entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
                if (entity != null) {
                    post.setEntity(entity);
                }

                String httpYul = "" + post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8");

                LogYiFu.e("posturl", httpYul);

                HttpResponse response = client.execute(post);
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {


                    Header[] headers = response.getHeaders("Content-Encoding");
                    boolean isGzip = false;
                    boolean isDeflate = false;

                    for (Header header : headers) {
                        String value = header.getValue();
                        if (value.equals("gzip")) {
                            isGzip = true;
                        }
                    }

                    for (Header header : headers) {
                        String value = header.getValue();
                        if (value.equals("deflate")) {
                            isDeflate = true;
                        }
                    }


                    if (isGzip) {
                        LogYiFu.e("isGzip-url", url);
                        InputStream is = response.getEntity().getContent();
                        GZIPInputStream gzipIn = new GZIPInputStream(is);
                        result = streamToString(gzipIn);
                        LogYiFu.e("isGzip", result);

                    } else if (isDeflate) {
                        LogYiFu.e("isDeflate-url", url);
                        InputStream is = response.getEntity().getContent();
                        DeflaterInputStream deflaterIn = new DeflaterInputStream(is);
                        result = streamToString(deflaterIn);
                        LogYiFu.e("isDeflate-url", result);

                    } else {
//                        LogYiFu.e("mainUrl", url);

                        result = EntityUtils.toString(response.getEntity());
//                        LogYiFu.e("mainUrl", result);

                    }


                    LogYiFu.e("result", result);
                    if (TextUtils.isEmpty(result)) {
                        throw new YException(YException.EMPTY_CODE);
                    }


                    //测试用
//                    if(url.equals(YUrl.QUERY_TIQIAN_TXCJ_MONEY)){
//                        result = "{\"extract_money\":0.2,\"raffle_money\":0.2,\"multiple\":5,\"all_money\":0,\"message\":\"操作成功.\",\"day\":7,\"maxType\":0,\"status\":\"1\"}";
//                    }


                    JSONObject jsonObj = new JSONObject(result);

                    int jsonStatus = jsonObj.optInt(Json.status);
                    String jsonMessage = jsonObj.optString(Json.message);
                    if (jsonStatus == 1) {// 注册成功


                    } else if (jsonStatus == 2) {
                        if ("修改订单状态失败".equals(jsonMessage)) {
                            throw new YException(jsonStatus, "等收货了再晒也不迟哟~~");
                        } else if ("无返回数据.".equals(jsonMessage) || "".equals(jsonMessage) || jsonMessage == null) {
                        } else {
                            throw new YException(jsonStatus, jsonMessage);
                        }
                    } else if (jsonStatus == 10030) {
                        CommonUtils.clearClipboardContent(context);
                        SharedPreferencesUtil.saveStringData(context,YCache.FENGKONG_CLIPBOARDCONTENT,"");
                        // true代表已经登录
                        Boolean ISCHUCHIDNEGLU = SharedPreferencesUtil.getBooleanData(context, "ISCHUCHIDNEGLU", true);

                        if (ISCHUCHIDNEGLU) {

                            SharedPreferencesUtil.saveBooleanData(context, "ISCHUCHIDNEGLU", false); // 标志改为未登录

                            YCache.cleanToken(context);
                            YCache.cleanUserInfo(context);
                            ComModel.clearLoginFlag(context);
//					AppManager.getAppManager().finishAllActivityOfEveryDayTask();
                            YJApplication.isLogined = false;

                            MainFragment fragment = MainMenuActivity.instances.getFragment();

                            if (fragment.getChildFragmentManager().findFragmentByTag("1") != null) {
                                fragment.getChildFragmentManager().beginTransaction()
                                        .remove(fragment.getChildFragmentManager().findFragmentByTag("1")).commit();
                            }

                            if (LoginActivity.instances != null) {
                                LoginActivity.instances.finish();
                            }

                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("login_register", "login");
                            intent.putExtra("mustLogin",true);
                            ((FragmentActivity) context).startActivity(intent);

                            throw new YException(jsonStatus, jsonMessage);

                        }


                    } else if (jsonStatus == 101) {
                        if (!jsonMessage.equals("设置成功.")) {
                            UserInfo user = EntityFactory.createYUser(context, result);
                            EntityFactory.createStore(context, result);// 保存店铺数据
                            // user.setUsertype(-1);
                            String logStringoken = JSONUtils.getString(result, Json.token, "");
                            YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                            YCache.cacheUserInfo(context, logStringoken, user);
                        }

                        throw new YException(jsonStatus, jsonMessage);
                    } else if (jsonStatus == 1051) {
                        UserInfo user = EntityFactory.createYUser(context, result);
                        EntityFactory.createStore(context, result);// 保存店铺数据
                        // user.setUsertype(-1);
                        String logStringoken = JSONUtils.getString(result, Json.token, "");
                        YCache.setCacheUser(context, user);// 登陆成功后设置缓存
                        YCache.cacheUserInfo(context, logStringoken, user);
                        throw new YException(jsonStatus, jsonMessage);
                    } else if (jsonStatus == 20001) {//用户没有资格参与集赞活动

                        //无需提示用户
//                throw new YException(jsonStatus, jsonMessage);


                    } else { //其他情况
                        throw new YException(jsonStatus, jsonMessage);

                    }

                } else if (status == 500) {// 内部服务器错误

                    throw new YException(status, context.getString(R.string.server_is_busy));
                } else {
                    throw new YException(status);
                }


                return result;


            }

            @SuppressLint("UseValueOf")
            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);


                if (e == null && result != null) {
                    try{
                        httpListener.onSuccess(com.alibaba.fastjson.JSONObject.parseObject(result, httpListener.getType()));

                    }catch (Exception e1){
                        e1.printStackTrace();
                    }

                } else {
                    httpListener.onError();


                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }
        }.execute();


    }


    public static String postKuaidi100(Context context, String url, List<NameValuePair> nameValuePairs)
            throws Exception {

        String result = "";

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 30 * 1000); // 10 s
        HttpConnectionParams.setSoTimeout(params, 30 * 1000); // 30s
        DefaultHttpClient client = new DefaultHttpClient(params);

        HttpPost post = new HttpPost(url);
        // post.setHeader("User-Agent", getUserAgent(context));
        // post.setHeader("Cookie", getCookieString(context));
        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "utf-8");
        if (entity != null) {
            post.setEntity(entity);
        }
        LogYiFu.e("posturl", "" + post.getURI().toURL().toString() + "?" + EntityUtils.toString(entity, "utf-8"));

        HttpResponse response = client.execute(post);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            result = EntityUtils.toString(response.getEntity());

            LogYiFu.e("result", "" + result);
            if (TextUtils.isEmpty(result)) {
                throw new YException(YException.EMPTY_CODE);
            }
            JSONObject jsonObj = new JSONObject(result);

            int jsonStatus = jsonObj.optInt(Json.status);
            String jsonMessage = jsonObj.optString(Json.message);

            if (jsonStatus == 1) {// 注册成功
                return result;
            } else if (jsonStatus == 2) {
                throw new YException(jsonStatus, jsonMessage);
            } else if (jsonStatus == 3) {
                throw new YException(jsonStatus, jsonMessage);
            }

        } else if (status == 500) {// 内部服务器错误
            throw new YException(status, context.getString(R.string.server_is_busy));
        } else {
            throw new YException(status);
        }
        return result;
    }

    public static String streamToString(InputStream stream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        try {
            while ((len = stream.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

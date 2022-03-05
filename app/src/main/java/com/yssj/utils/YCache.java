package com.yssj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import com.yssj.YJApplication;
import com.yssj.YConstance.Pref;
import com.yssj.entity.Store;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.receiver.TaskReceiver;

public class YCache {

    // 缓存文件都保存在 Android/data/...里面
    private static final String USER_FILE_NAME = "local_userinfo";
    private static UserInfo userCacheInMemory;

    private static final String STORE_FILE_NAME = "local_store";
    private static Store storeCache;

    public  static final String USER_FISRT_LOGIN = "user_fisrt_login";
    public  static final String NEED_FENG_KONG = "need_feng_kong";
    public  static final String FENGKONG_CLIPBOARDCONTENT = "fengkong_clipboardcontent";
    public  static final String START_APP_YSQX = "start_app_ysqx";


    /***
     * 保存用户资料
     *
     * @param context
     * @param user
     */
    public static void setCacheUser(Context context, UserInfo user) {
        if (user == null) {
            throw new NullPointerException("User is null!");
        }

        userCacheInMemory = user;
        // 111111111111111
        // private int user_id;
        SharedPreferencesUtil.saveStringData(context, Pref.USER_ID, "" + user.getUser_id());

        if(YJApplication.instance.isLoginSucess()){
            SharedPreferencesUtil.saveStringData(context, Pref.REVIEWERS, "" + getCacheUser(context).getReviewers());
        }else{
            SharedPreferencesUtil.saveStringData(context, Pref.REVIEWERS, "" + user.getReviewers());
        }





        SharedPreferencesUtil.saveStringData(context, Pref.ADD_DATE, "" + user.getAdd_date());

        // private String phone;
        SharedPreferencesUtil.saveStringData(context, Pref.PHONE, "" + user.getPhone());
        // private String email;
        SharedPreferencesUtil.saveStringData(context, Pref.EMAIL, "" + user.getEmail());
        // private String nickname;
        SharedPreferencesUtil.saveStringData(context, Pref.NICKNAME, "" + user.getNickname());
        // private int user_type;
        SharedPreferencesUtil.saveStringData(context, Pref.USER_TYPE, "" + user.getUser_type());
        // private String account;
        SharedPreferencesUtil.saveStringData(context, Pref.ACCOUNT, "" + user.getAccount());
        // private String hobby;
        SharedPreferencesUtil.saveStringData(context, Pref.HOBBY, "" + user.getHobby());
        // private String imei;
        SharedPreferencesUtil.saveStringData(context, Pref.IMEI, "" + user.getImei());
        // private String mac;
        SharedPreferencesUtil.saveStringData(context, Pref.MAC, "" + user.getMac());
        // private String tag;
        SharedPreferencesUtil.saveStringData(context, Pref.TAG, "" + user.getTag());
        // private String user_name; // N String 50 真实姓名
        SharedPreferencesUtil.saveStringData(context, Pref.USER_NAME, "" + user.getUser_name());
        // private String user_ident; // Y String 25 身份证
        SharedPreferencesUtil.saveStringData(context, Pref.USER_IDENT, "" + user.getUser_ident());
        // private int age; // Y Integer 年龄
        SharedPreferencesUtil.saveStringData(context, Pref.AGE, "" + user.getAge());
        // private int gender;// Y Int 1 性别 （0=保密，1=男，2=女）
        SharedPreferencesUtil.saveStringData(context, Pref.GENDER, "" + user.getGender());
        // private String remarks; // Y String 备注
        SharedPreferencesUtil.saveStringData(context, Pref.REMARKS, "" + user.getRemarks());
        // private String birthday; // Y Date 生日
        SharedPreferencesUtil.saveStringData(context, Pref.BIRTHDAY, "" + user.getBirthday());
        // private String pic;// Y String 200 用户头像
        SharedPreferencesUtil.saveStringData(context, Pref.PIC, "" + user.getPic());
        // private String v_ident;// 用户的V
        SharedPreferencesUtil.saveStringData(context, Pref.V_IDENT, "" + user.getV_ident());
        // private String city; // Y Integer 城市编号 关联areatbl
        SharedPreferencesUtil.saveStringData(context, Pref.CITY, "" + user.getCity());
        // private int email_status;
        SharedPreferencesUtil.saveStringData(context, Pref.EMAIL_STATUS, "" + user.getEmail_status());
        // private String street;// 街道
        SharedPreferencesUtil.saveStringData(context, Pref.STREET, "" + user.getStreet());
        // private String area;
        SharedPreferencesUtil.saveStringData(context, Pref.AREA, "" + user.getArea());
        // private String province;
        SharedPreferencesUtil.saveStringData(context, Pref.PROVINCE, "" + user.getProvince());
        // private String uuid;// 微信授权获取的uuid
        SharedPreferencesUtil.saveStringData(context, Pref.UUID, "" + user.getUuid());
        // private int Is_location;// 是否开启位置服务 Integer 1 必选 0不开启,1开启,默认为开启
        SharedPreferencesUtil.saveStringData(context, Pref.IS_LOCATION, "" + user.getIs_location());
        // private int usertype;// 判断是否是第三方登陆接口
        SharedPreferencesUtil.saveStringData(context, Pref.USERTYPE, "" + user.getUsertype());
        // private String userth_id;
        SharedPreferencesUtil.saveStringData(context, Pref.USERTH_ID, "" + user.getUserth_id());
        // private String userSign;
        SharedPreferencesUtil.saveStringData(context, Pref.USERSIGN, "" + user.getUserSign());
        // private String is_member;// 0普通用户1已通过验证2会员
        SharedPreferencesUtil.saveStringData(context, Pref.IS_MEMBER, "" + user.getIs_member());
        // 111111111111111
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(USER_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /***
     * 得到用户
     *
     * @param context
     * @return
     */
    public static UserInfo getCacheUserSafe(Context context) {
        try {
            return getCacheUser(context);
        } catch (Exception e) {
            // do nothing
            new UserInfo();
        }
        return null;
    }

    public static UserInfo getCacheUser(Context context) {
//		if (userCacheInMemory == null) {
//			try {
//
//				FileInputStream fileInputStream = context.openFileInput(USER_FILE_NAME);
//				ObjectInputStream objectInpuStream = new ObjectInputStream(fileInputStream);
//				Serializable object = (Serializable) objectInpuStream.readObject();
//				objectInpuStream.close();
//				if (object != null) {
////					UserInfo user = new UserInfo();
//					userCacheInMemory = (UserInfo) object;
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
        // throw new IllegalStateException("User数据结构有问�?!");
        // userCacheInMemory = new YUser();
//			}
//		}
        // if (userCacheInMemory == null) {
        // throw new NullPointerException("You must set local user first!!!");
        // }
//		if (userCacheInMemory == null) {
        // 111111111111111111111
        UserInfo user = new UserInfo();
        // private String phone;
        String phone = SharedPreferencesUtil.getStringData(context, Pref.PHONE, "");
        user.setPhone("" + phone);
        // private String email;
        String email = SharedPreferencesUtil.getStringData(context, Pref.EMAIL, "");
        user.setEmail("" + email);
        // private String nickname;
        String nickname = SharedPreferencesUtil.getStringData(context, Pref.NICKNAME, "");
        user.setNickname("" + nickname);
        // private int user_type;
        String user_type = SharedPreferencesUtil.getStringData(context, Pref.USER_TYPE, "0");
        if (null == user_type || "".equals(user_type) || "null".equals(user_type)) {
            user_type = "0";
        }
        user.setUser_type(Integer.parseInt(user_type));
        // private String account;
        String account = SharedPreferencesUtil.getStringData(context, Pref.ACCOUNT, "");
        user.setAccount("" + account);

        String add_date = SharedPreferencesUtil.getStringData(context, Pref.ADD_DATE, "");
        user.setAdd_date("" + add_date);

        // private String hobby;
        String hobby = SharedPreferencesUtil.getStringData(context, Pref.HOBBY, "");
        user.setHobby("" + hobby);
        // private String imei;
        String imei = SharedPreferencesUtil.getStringData(context, Pref.IMEI, "");
        user.setImei("" + imei);
        // private String mac;
        String mac = SharedPreferencesUtil.getStringData(context, Pref.MAC, "");
        user.setMac("" + mac);
        // private String tag;
        String tag = SharedPreferencesUtil.getStringData(context, Pref.TAG, "");
        user.setTag("" + tag);
        // private String user_name; // N String 50 真实姓名
        String user_name = SharedPreferencesUtil.getStringData(context, Pref.USER_NAME, "");
        user.setUser_name("" + user_name);
        // private String user_ident; // Y String 25 身份证
        String user_ident = SharedPreferencesUtil.getStringData(context, Pref.USER_IDENT, "");
        user.setUser_ident("" + user_ident);
        // private int age; // Y Integer 年龄
        String age = SharedPreferencesUtil.getStringData(context, Pref.AGE, "0");
        if (null == age || "".equals(age) || "null".equals(age)) {
            age = "0";
        }
        user.setAge(Integer.parseInt(age));
        // private int gender;// Y Int 1 性别 （0=保密，1=男，2=女）
        String gender = SharedPreferencesUtil.getStringData(context, Pref.GENDER, "0");
        if (null == gender || "".equals(gender) || "null".equals(gender)) {
            gender = "0";
        }
        user.setGender(Integer.parseInt(gender));
        // private String remarks; // Y String 备注
        String remarks = SharedPreferencesUtil.getStringData(context, Pref.REMARKS, "");
        user.setRemarks("" + remarks);
        // private String birthday; // Y Date 生日
        String birthday = SharedPreferencesUtil.getStringData(context, Pref.BIRTHDAY, "");
        user.setBirthday("" + birthday);
        // private String pic;// Y String 200 用户头像
        String pic = SharedPreferencesUtil.getStringData(context, Pref.PIC, "");
        user.setPic("" + pic);
        //用户的V
        String v_ident = SharedPreferencesUtil.getStringData(context, Pref.V_IDENT, "");
        user.setV_ident(v_ident);
        // private String city; // Y Integer 城市编号 关联areatbl
        String city = SharedPreferencesUtil.getStringData(context, Pref.CITY, "");
        user.setCity("" + city);
        // private int email_status;
        String email_status = SharedPreferencesUtil.getStringData(context, Pref.EMAIL_STATUS, "0");
        if (null == email_status || "".equals(email_status) || "null".equals(email_status)) {
            email_status = "0";
        }
        user.setEmail_status(Integer.parseInt(email_status));
        // private String street;// 街道
        String street = SharedPreferencesUtil.getStringData(context, Pref.STREET, "");
        user.setStreet("" + street);
        // private String area;
        String area = SharedPreferencesUtil.getStringData(context, Pref.AREA, "");
        user.setArea("" + area);
        // private String province;
        String province = SharedPreferencesUtil.getStringData(context, Pref.PROVINCE, "");
        user.setProvince("" + province);
        // private String uuid;// 微信授权获取的uuid
        String uuid = SharedPreferencesUtil.getStringData(context, Pref.UUID, "");
        user.setUuid("" + uuid);
        // private int Is_location;// 是否开启位置服务 Integer 1 必选 0不开启,1开启,默认为开启
        String Is_location = SharedPreferencesUtil.getStringData(context, Pref.IS_LOCATION, "0");
        if (null == Is_location || "".equals(Is_location) || "null".equals(Is_location)) {
            Is_location = "0";
        }
        user.setIs_location(Integer.parseInt(Is_location));
        // private int usertype;// 判断是否是第三方登陆接口
        String usertype = SharedPreferencesUtil.getStringData(context, Pref.USERTYPE, "0");
        if (null == usertype || "".equals(usertype) || "null".equals(usertype)) {
            usertype = "0";
        }
        user.setUsertype(Integer.parseInt(usertype));
        // private String userth_id;
        String userth_id = SharedPreferencesUtil.getStringData(context, Pref.USERTH_ID, "");
        user.setUserth_id("" + userth_id);
        // private String userSign;
        String userSign = SharedPreferencesUtil.getStringData(context, Pref.USERSIGN, "");
        user.setUserSign("" + userSign);
        // private String is_member;// 0普通用户1已通过验证2会员
        String is_member = SharedPreferencesUtil.getStringData(context, Pref.IS_MEMBER, "");
        user.setIs_member("" + is_member);

        if (!"".equals(user.getUuid())) {
            userCacheInMemory = user;
        }
        // private int user_id;
        String user_id = SharedPreferencesUtil.getStringData(context, Pref.USER_ID, "");
        if (null != user_id && !("".equals(user_id)) && !("null".equals(user_id))) {
            user.setUser_id(Integer.parseInt(user_id));
            userCacheInMemory = user;
        }


        String reviewers = SharedPreferencesUtil.getStringData(context, Pref.REVIEWERS, "");
        if (null != reviewers && !("".equals(reviewers)) && !("null".equals(reviewers))) {
            user.setReviewers(Integer.parseInt(reviewers));
            userCacheInMemory = user;
        }

        // 1111111111
//		}
        return userCacheInMemory;
    }

    /***
     * 保存用户店铺资料
     *
     * @param context
     */
    public static void setCacheStore(Context context, Store store) {
        if (store == null) {
            throw new NullPointerException("Store is null!");
        }

        storeCache = store;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(STORE_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(store);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		11111111111
//		private Integer id;
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_ID, "" + store.getId());
//		private Integer user_id;// 店主ID
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_USER_ID, "" + store.getUser_id());
//		private String s_code;// 店铺编号
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_CODE, "" + store.getS_code());
//		private String s_name;// 店铺名称
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_NAME, "" + store.getS_name());
//		private String s_pic;// 店铺图片
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_PIC, "" + store.getS_pic());
//		private String s_bg_pic;//模板编号
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_BG_PIC, "" + store.getS_bg_pic());
//		private String s_sign;//店招图片
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_SIGN, "" + store.getS_sign());
//		private String s_content;// 店铺介绍
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_CONTENT, "" + store.getS_content());
//		private String notice;// 店铺公告
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_NOTICE, "" + store.getNotice());
//		private Integer s_clicks;// 店铺点击数
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_CLICKS, "" + store.getS_clicks());
//		private Integer s_fans;// 店铺粉丝数
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_S_FANS, "" + store.getS_fans());
//		private String coupon_list;// 优惠券
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_COUPON_LIST, "" + store.getCoupon_list());
//		private String remark;// 备注
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_REMARK, "" + store.getRemark());
//		private String realm;// 店铺域名
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_REALM, "" + store.getRealm(context));
//		private Integer is_up;// 是否可以修改域名,0不可以,1可以,默认为可以
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_IS_UP, "" + store.getIs_up());
//		private String templet_code;//模板编号
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_TEMPLET_CODE, "" + store.getTemplet_code());
//		private String circle_sys_pic;//系统推送轮播商品编号及大图
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_CIRCLE_SYS_PIC, "" + store.getCircle_sys_pic());
//		private String circle_user_pic;//用户选择轮播商品编号及大图
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_CIRCLE_USER_PIC, "" + store.getCircle_user_pic());
//		private Integer circle_status;//0为系统推送，1为用户设定
        SharedPreferencesUtil.saveStringData(context, Pref.STORE_CIRCLE_STATUS, "" + store.getCircle_status());
//		11111111111
    }

    public static void cleanCacheStore(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(STORE_FILE_NAME, Context.MODE_PRIVATE);
            File fl = context.getFileStreamPath(STORE_FILE_NAME);
            if (fl.exists()) {
                fl.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 得到店铺
     *
     * @param context
     * @return
     */
    public static Store getCacheStoreSafe(Context context) {
        try {
            return getCacheStore(context);
        } catch (Exception e) {
            // do nothing
        }
        return null;
    }

    public static Store getCacheStore(Context context) {
        if (storeCache == null) {
            try {

                FileInputStream fileInputStream = context.openFileInput(STORE_FILE_NAME);
                ObjectInputStream objectInpuStream = new ObjectInputStream(fileInputStream);
                Serializable object = (Serializable) objectInpuStream.readObject();
                objectInpuStream.close();
                if (object != null) {
                    storeCache = (Store) object;
                }

            } catch (Exception e) {
                e.printStackTrace();
                // throw new IllegalStateException("User数据结构有问�?!");
                // userCacheInMemory = new YUser();
            }
        }
        // if (storeCache == null) {
        // throw new NullPointerException("You must set local store first!!!");
        // }
        if (storeCache == null) {
            Store store = new Store();
//		private Integer id;
            String id = SharedPreferencesUtil.getStringData(context, Pref.STORE_ID, "0");
            if (null == id || "".equals(id) || "null".equals(id)) {
                id = "0";
            }
            store.setId(Integer.parseInt(id));
//		private String s_code;// 店铺编号
            String s_code = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_CODE, "");
            store.setS_code(s_code);
//		private String s_name;// 店铺名称
            String s_name = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_NAME, "");
            store.setS_name(s_name);
//		private String s_pic;// 店铺图片
            String s_pic = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_PIC, "");
            store.setS_pic(s_pic);
//		private String s_bg_pic;//模板编号
            String s_bg_pic = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_BG_PIC, "");
            store.setS_bg_pic(s_bg_pic);
//		private String s_sign;//店招图片
            String s_sign = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_SIGN, "");
            store.setS_sign(s_sign);
//		private String s_content;// 店铺介绍
            String s_content = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_CONTENT, "");
            store.setS_content(s_content);
//		private String notice;// 店铺公告
            String notice = SharedPreferencesUtil.getStringData(context, Pref.STORE_NOTICE, "");
            store.setNotice(notice);
//		private Integer s_clicks;// 店铺点击数
            String s_clicks = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_CLICKS, "0");
            if (null == s_clicks || "".equals(s_clicks) || "null".equals(s_clicks)) {
                s_clicks = "0";
            }
            store.setS_clicks(Integer.parseInt(s_clicks));
//		private Integer s_fans;// 店铺粉丝数
            String s_fans = SharedPreferencesUtil.getStringData(context, Pref.STORE_S_FANS, "0");
            if (null == s_fans || "".equals(s_fans) || "null".equals(s_fans)) {
                s_fans = "0";
            }
            store.setS_fans(Integer.parseInt(s_fans));
//		private String coupon_list;// 优惠券
            String coupon_list = SharedPreferencesUtil.getStringData(context, Pref.STORE_COUPON_LIST, "");
            store.setCoupon_list(coupon_list);
//		private String remark;// 备注
            String remark = SharedPreferencesUtil.getStringData(context, Pref.STORE_REMARK, "");
            store.setRemark(remark);
//		private String realm;// 店铺域名
            String realm = SharedPreferencesUtil.getStringData(context, Pref.STORE_REALM, "");
            store.setRealm(realm);
//		private Integer is_up;// 是否可以修改域名,0不可以,1可以,默认为可以
            String is_up = SharedPreferencesUtil.getStringData(context, Pref.STORE_IS_UP, "0");
            if (null == is_up || "".equals(is_up) || "null".equals(is_up)) {
                is_up = "0";
            }
            store.setIs_up(Integer.parseInt(is_up));
//		private String templet_code;//模板编号
            String templet_code = SharedPreferencesUtil.getStringData(context, Pref.STORE_TEMPLET_CODE, "");
            store.setTemplet_code(templet_code);
//		private String circle_sys_pic;//系统推送轮播商品编号及大图
            String circle_sys_pic = SharedPreferencesUtil.getStringData(context, Pref.STORE_CIRCLE_SYS_PIC, "");
            store.setCircle_sys_pic(circle_sys_pic);
//		private String circle_user_pic;//用户选择轮播商品编号及大图
            String circle_user_pic = SharedPreferencesUtil.getStringData(context, Pref.STORE_CIRCLE_USER_PIC, "");
            store.setCircle_user_pic(circle_user_pic);
//		private Integer circle_status;//0为系统推送，1为用户设定
            String circle_status = SharedPreferencesUtil.getStringData(context, Pref.STORE_CIRCLE_STATUS, "0");
            if (null == circle_status || "".equals(circle_status) || "null".equals(circle_status)) {
                circle_status = "0";
            }
            store.setCircle_status(Integer.parseInt(circle_status));
//		private Integer user_id;// 店主ID
            String user_id = SharedPreferencesUtil.getStringData(context, Pref.STORE_USER_ID, "0");
            if (null != user_id && !("".equals(user_id)) && !("null".equals(user_id))) {
                store.setUser_id(Integer.parseInt(user_id));
                storeCache = store;
            }
        }

        return storeCache;
    }

    /**
     * 设置用户APP本地信息 token
     */
    public static void cacheUserInfo(final Context context, String token, UserInfo user) {
        Editor editor = context.getSharedPreferences(Pref.YEAMO, Context.MODE_PRIVATE).edit();
        editor.putString(Pref.LOGIN_TOKEN_3, token);
        editor.putInt(Pref.YEAMO_UID, user.getUser_id());
        editor.commit();

        Intent intentBorad = new Intent();
        intentBorad.setAction(TaskReceiver.onebuysubmitoderend);
        //通知更新商品信息
        context.sendBroadcast(intentBorad);


    }

    public static String getCacheToken(Context context) {
        if (null != context) {
            return context.getSharedPreferences(Pref.YEAMO, 0).getString(Pref.LOGIN_TOKEN_3, null);
        } else {
            return YJApplication.mContext.getSharedPreferences(Pref.YEAMO, 0).getString(Pref.LOGIN_TOKEN_3, null);
        }

    }

    /****
     * 清除token
     *
     * @param context
     */
    public static void cleanToken(Context context) {
        Editor editor = context.getSharedPreferences(Pref.YEAMO, Context.MODE_PRIVATE).edit();
        editor.remove(Pref.LOGIN_TOKEN_3);
        editor.commit();
    }

    /**
     * 清除用户资料
     */
    public static void cleanUserInfo(Context context) {
        Editor editor = context.getSharedPreferences(Pref.YEAMO, Context.MODE_PRIVATE).edit();
        editor.remove(Pref.LOGIN_TOKEN_3);
        editor.remove(Pref.YEAMO_UID);
        editor.commit();
        cleanFileCache(context);
        userCacheInMemory = null;
        storeCache = null;
    }

    /**
     * 清除缓存文件
     */
    public static void cleanFileCache(Context context) {
        File file = context.getFilesDir();
        FileUtils.deleteAllFilesOfDir(file, false);
        file = context.getCacheDir();
        FileUtils.deleteAllFilesOfDir(file, false);
        SharedPreferencesUtil.saveStringData(context, Pref.USER_ID, "");//保存成""字符串，
    }

    /**
     * 保存下单令牌
     */
    public static void saveOrderToken(Context context, String orderToken) {
        context.getSharedPreferences(Pref.ORDER_TOKEN, Context.MODE_PRIVATE).edit()
                .putString(Pref.ORDER_TOKEN, orderToken).commit();
    }

    /**
     * 删除下单令牌
     */
    public static void cleanOrderToken(Context context) {
        context.getSharedPreferences(Pref.ORDER_TOKEN, Context.MODE_PRIVATE).edit().clear().commit();
    }

    /**
     * 获取下单令牌
     */
    public static String getOrderToken(Context context) {
        return context.getSharedPreferences(Pref.ORDER_TOKEN, Context.MODE_PRIVATE).getString(Pref.ORDER_TOKEN, "");
    }
}

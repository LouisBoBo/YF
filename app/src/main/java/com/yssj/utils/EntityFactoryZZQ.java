//package com.yssj.utils;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.yssj.Json;
//import com.yssj.Json.JUserInfo;
//import com.yssj.Json.RetInfo;
//import com.yssj.YConstance.Pref;
//import com.yssj.YJApplication;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.Business;
//import com.yssj.entity.CheckPwdInfo;
//import com.yssj.entity.Comment;
//import com.yssj.entity.DeliveryAddress;
//import com.yssj.entity.DuoBaoJiLu_user;
//import com.yssj.entity.FundDetail;
//import com.yssj.entity.Help;
//import com.yssj.entity.IntegralShop;
//import com.yssj.entity.Like;
//import com.yssj.entity.MatchAttr;
//import com.yssj.entity.MatchAttr.ShopAttrBean;
//import com.yssj.entity.MatchAttr.StocktypeBean;
//import com.yssj.entity.MatchShop;
//import com.yssj.entity.MatchShop.AttrList;
//import com.yssj.entity.MatchShop.CollocationShop;
//import com.yssj.entity.MyBankCard;
//import com.yssj.entity.Order;
//import com.yssj.entity.OrderShop;
//import com.yssj.entity.QueryEmailInfo;
//import com.yssj.entity.QueryPhoneInfo;
//import com.yssj.entity.RemainShipInfo;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ReturnShop;
//import com.yssj.entity.ShareShop;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopAttr;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.ShopComment;
//import com.yssj.entity.ShopOption;
//import com.yssj.entity.ShopTag;
//import com.yssj.entity.ShopType;
//import com.yssj.entity.StockType;
//import com.yssj.entity.Store;
//import com.yssj.entity.SuppComment;
//import com.yssj.entity.UserInfo;
//import com.yssj.utils.sqlite.ShopCartDao;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//public class EntityFactoryZZQ {
//	/***
//	 * 注册返回用户信息
//	 * 
//	 * @param context
//	 * @param j
//	 * @return
//	 * @throws JSONException
//	 *             \ ]
//	 */
//
//	public static final UserInfo createYUser(Context context, JSONObject j) throws JSONException {
//
//		UserInfo user = new UserInfo();
//		if (!j.has(JUserInfo.user_id) || null == j.getString(JUserInfo.user_id)
//				|| j.getString(JUserInfo.user_id).equals("null") || j.getString(JUserInfo.user_id).equals("")) {
//			user.setUser_id(-1000);
//		} else {
//			user.setUser_id(j.getInt(JUserInfo.user_id));
//		}
//		// if (!j.has(JUserInfo.phone)||null == j.getString(JUserInfo.phone) ||
//		// j.getString(JUserInfo.phone).equals("null") ||
//		// j.getString(JUserInfo.phone).equals("")) {
//		// user.setPhone(""+(-1000));
//		// } else {
//		user.setPhone(j.optString(JUserInfo.phone));
//		// }
//
//		user.setNickname(j.optString(JUserInfo.nickname));
//		user.setEmail(j.optString(JUserInfo.email));
//		user.setUser_type(j.optInt("user_type"));
//
//		return user;
//	}
//
//	public static final HashMap<String, Object> createLoginsInfos(Context context, String result) throws JSONException {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			mapRet.put("status", "-1000");
//		} else {
//			mapRet.put("status", j.getString(RetInfo.status));
//		}
//
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			mapRet.put("message", "");
//		} else {
//			mapRet.put("message", j.getString(RetInfo.message));
//		}
//		// mapRet.put("device", j.optString("device"));
//		if (!j.has("loginTime") || null == j.getString("loginTime") || j.getString("loginTime").equals("null")
//				|| j.getString("loginTime").equals("")) {
//			mapRet.put("loginTime", 0);
//		} else {
//			mapRet.put("loginTime", j.opt("loginTime"));
//		}
//
//		if (!j.has("phone_status") || null == j.getString("phone_status") || j.getString("phone_status").equals("null")
//				|| j.getString("phone_status").equals("")) {
//			mapRet.put("phone_status", 1);// 1为未设置，2为已设置
//		} else {
//			mapRet.put("phone_status", j.getInt("phone_status"));// 1为未设置，2为已设置
//		}
//
//		if (!j.has("email_status") || null == j.getString("email_status") || j.getString("email_status").equals("null")
//				|| j.getString("email_status").equals("")) {
//			mapRet.put("email_status", 1);// 1为未设置，2为已设置
//		} else {
//			mapRet.put("email_status", j.getInt("email_status"));// 1为未设置，2为已设置
//		}
//
//		if (!j.has("paypwd_status") || null == j.getString("paypwd_status")
//				|| j.getString("paypwd_status").equals("null")) {
//			mapRet.put("paypwd_status", -1);
//		} else {
//			mapRet.put("paypwd_status", j.getInt("paypwd_status"));
//		}
//		return mapRet;
//	}
//
//	public static final ReturnInfo createRetInfo(Context context, String result) throws JSONException {
//
//		ReturnInfo retInfo = new ReturnInfo();
//		JSONObject j = new JSONObject(result);
//
//		retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
//		retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
//		retInfo.setPwdflag(j.has("pwdflag") ? j.getInt(RetInfo.pwdflag) : 5);
//
//		return retInfo;
//	}
//
//	/**
//	 * 开启余额翻倍
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, Object> createDoubleBalance(Context context, String result)
//			throws JSONException {
//
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("now") || null == jsonObject.getString("now")
//					|| jsonObject.getString("now").equals("null") || jsonObject.getString("now").equals("")) {
//				mapRet.put("now", 0);
//			} else {
//				mapRet.put("now", jsonObject.has("now") ? jsonObject.getLong("now") : 0);
//			}
//			if (!jsonObject.has("vt") || null == jsonObject.getString("vt") || jsonObject.getString("vt").equals("null")
//					|| jsonObject.getString("vt").equals("")) {
//				mapRet.put("vt", 0);
//			} else {
//				mapRet.put("vt", jsonObject.has("vt") ? jsonObject.getInt("vt") : 24);
//			}
//
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	public static final ReturnInfo createRetInfoKaiDian(Context context, String result) throws JSONException {
//		LogYiFu.e("开启小店", result + "");
//
//		ReturnInfo retInfo = new ReturnInfo();
//		JSONObject j = new JSONObject(result);
//
//		SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS, "3"); // 余额翻倍倍数
//
//		if (!j.has("t_time") || null == j.getString("t_time") || j.getString("t_time").equals("null")
//				|| j.getString("t_time").equals("")) {
//			SharedPreferencesUtil.saveStringData(context, Pref.END_DATE, "-1"); // 余额翻倍剩余时间
//		} else {
//			SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
//					j.has("t_time") ? (j.getLong("t_time") + "") : "-1"); // 余额翻倍剩余时间
//		}
//		SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true); // 余额翻倍是否有开启资格
//		SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN, "0");// 是否已开启
//																			// 0：未开启
//																			// 1：已开启
//
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			retInfo.setStatus("-1000");
//		} else {
//			retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
//		}
//
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			retInfo.setMessage("");
//		} else {
//			retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
//		}
//
//		if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
//				|| j.getString("pwdflag").equals("")) {
//			retInfo.setPwdflag(5);
//		} else {
//			retInfo.setPwdflag(j.has("pwdflag") ? j.getInt(RetInfo.pwdflag) : 5);
//		}
//		return retInfo;
//	}
//
//	// public static final ReturnInfo createIEMI(Context context, String result)
//	// throws JSONException {
//	// ReturnInfo retInfo = new ReturnInfo();
//	// JSONObject j = new JSONObject(result);
//	// retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
//	// retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
//	// // retInfo.setIsCart(j.optInt("isCart"));
//	//
//	// MyLogYiFu.e("返回状态", retInfo.getStatus());
//	// MyLogYiFu.e("返回信息", retInfo.getMessage());
//	// return retInfo;
//	// }
//
//	public static final ReturnInfo createInfos(Context context, String result) throws JSONException {
//		ReturnInfo retInfo = new ReturnInfo();
//		JSONObject j = new JSONObject(result);
//		retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) : "");
//		retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) : "");
//		// retInfo.setIsCart(j.optInt("isCart"));
//		int isMember = j.has("ismember") ? j.optInt("ismember") : 0;
//		UserInfo user = YCache.getCacheUserSafe(context);
//		if (null != user) {
//			user.setIs_member(isMember + "");
//			YCache.setCacheUser(context, user);
//		}
//		if (j.has("twofoldness")) {
//			String s = j.getString("twofoldness");
//			LogYiFu.e("helllo", "!!!!!!" + s);
//			if (s != null && !("".equals(s)) && !("{}".equals(s))) {
//				JSONObject j2 = new JSONObject(s);
//				SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);
//				SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS,
//						j2.has("twofoldness") ? (j2.getInt("twofoldness") + "") : "-1");
//				SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
//						j2.has("end_date") ? (j2.getLong("end_date") + "") : "-1");
//				SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN,
//						j2.has("is_open") ? (j2.getInt("is_open") + "") : "-1");
//			} else {
//				SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
//			}
//		} else {
//			SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
//			LogYiFu.e("helllo", "ddd");
//		}
//
//		LogYiFu.e("返回状态", retInfo.getStatus());
//		LogYiFu.e("返回信息", retInfo.getMessage());
//		return retInfo;
//	}
//
//	// 提现接口返回信息
//	public static final HashMap<String, Object> createWithDrawInfo(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			map.put("status", "-1000");
//		} else {
//			map.put("status", j.has("status") ? j.getString("status") : "");
//		}
//		if (!j.has("message") || null == j.getString("message")
//				|| j.getString("message").equals("")) {
//			map.put("message", "");
//		} else {
//			map.put("message", j.has("message") ? j.getString("message") : "");
//		}
//
//		if (!j.has("flag") || null == j.getString("flag") || j.getString("flag").equals("null")
//				|| j.getString("flag").equals("")) {
//			map.put("flag", "");
//		} else {
//			map.put("flag", j.has("flag") ? j.optInt("flag") : "");
//		}
//		if (!j.has("money") || null == j.getString("money") || j.getString("money").equals("null")
//				|| j.getString("money").equals("")) {
//			map.put("money", "0");
//		} else {
//			map.put("money", j.has("money") ? j.optDouble("money") : "0");
//		}
//		if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
//				|| j.getString("pwdflag").equals("")) {
//			map.put("pwdflag", 5);
//		} else {
//			map.put("pwdflag", j.has("pwdflag") ? j.optInt("pwdflag") : 5);
//		}
//
//		return map;
//	}
//
//	public static final HashMap<String, Object> createSignRetInfo(Context context, String result) throws JSONException {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			map.put("status", "-1000");
//		} else {
//			map.put("status", j.has("status") ? j.getString("status") : "");
//		}
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			map.put("message", "");
//		} else {
//			map.put("message", j.has("message") ? j.getString("message") : "");
//		}
//		if (!j.has("integral") || null == j.getString("integral") || j.getString("integral").equals("null")
//				|| j.getString("integral").equals("")) {
//			map.put("integral", "0");
//		} else {
//			map.put("integral", j.has("integral") ? j.optInt("integral") : "");
//		}
//		if (!j.has("pwdflag") || null == j.getString("pwdflag") || j.getString("pwdflag").equals("null")
//				|| j.getString("pwdflag").equals("")) {
//			map.put("pwdflag", 5);
//		} else {
//			map.put("pwdflag", j.has("pwdflag") ? j.optInt("pwdflag") : 5);
//		}
//
//		// retInfo.setIsCart(j.optInt("isCart"));
//		return map;
//	}
//
//	public static final QueryPhoneInfo createQueryPhoneInfo(Context context, String result) throws JSONException {
//		QueryPhoneInfo Info = new QueryPhoneInfo();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			Info.setStatus("");
//		} else {
//			Info.setStatus(j.has("status") ? j.getString("status") : "");
//		}
//
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			Info.setMessage("");
//		} else {
//			Info.setMessage(j.has("message") ? j.getString("message") : "");
//		}
//
//		if (!j.has("bool") || null == j.getString("bool") || j.getString("bool").equals("null")
//				|| j.getString("bool").equals("")) {
//			Info.setBool(false);
//		} else {
//			Info.setBool(j.has("bool") ? j.getBoolean("bool") : false);
//		}
//
//		if (!j.has("phone") || null == j.getString("phone") || j.getString("phone").equals("null")
//				|| j.getString("phone").equals("")) {
//			Info.setPhone("");
//		} else {
//			Info.setPhone(j.has("phone") ? j.getString("phone") : "");
//		}
//		return Info;
//	}
//
//	public static final QueryEmailInfo createQueryEmailInfo(Context context, String result) throws JSONException {
//		QueryEmailInfo emailInfo = new QueryEmailInfo();
//		JSONObject j = new JSONObject(result);
//
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			emailInfo.setStatus("");
//		} else {
//			emailInfo.setStatus(j.has("status") ? j.getString("status") : "");
//		}
//
//		if (!j.has("message") || null == j.getString("message")
//				|| j.getString("message").equals("")) {
//			emailInfo.setMessage("");
//		} else {
//			emailInfo.setMessage(j.has("message") ? j.getString("message") : "");
//		}
//		if (!j.has("bool") || null == j.getString("bool") || j.getString("bool").equals("null")
//				|| j.getString("bool").equals("")) {
//			emailInfo.setBool(false);
//		} else {
//			emailInfo.setBool(j.has("bool") ? j.getBoolean("bool") : false);
//		}
//		if (!j.has("email") || null == j.getString("email") || j.getString("email").equals("null")
//				|| j.getString("email").equals("")) {
//			emailInfo.setEmail("");
//		} else {
//			emailInfo.setEmail(j.has("email") ? j.getString("email") : "");
//		}
//		return emailInfo;
//	}
//
//	public static final RemainShipInfo createRemainShipInfo(Context context, String result) throws JSONException {
//		RemainShipInfo Info = new RemainShipInfo();
//		JSONObject j = new JSONObject(result);
//
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			Info.setStatus("");
//		} else {
//			Info.setStatus(j.has("status") ? j.getString("status") : "");
//		}
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			Info.setMessage("");
//		} else {
//			Info.setMessage(j.has("message") ? j.getString("message") : "");
//		}
//
//		if (!j.has("falg") || null == j.getString("falg") || j.getString("falg").equals("null")
//				|| j.getString("falg").equals("")) {
//			Info.setFalg(0);
//		} else {
//			Info.setFalg(j.has("falg") ? j.getInt("falg") : 0);
//		}
//		if (!j.has("time") || null == j.getString("time") || j.getString("time").equals("null")
//				|| j.getString("time").equals("")) {
//			Info.setTime(0);
//		} else {
//			Info.setTime(j.has("time") ? j.getLong("time") : 0);
//		}
//
//		return Info;
//	}
//
//	// public static final ReturnShop createReturnShop(Context context,
//	// String result) throws JSONException {
//	// //result = result.replace("null", "\"\"");
//	//
//	// ReturnShop retShop = new ReturnShop();
//	// JSONObject jsonObject = new JSONObject(result);
//	//
//	// JSONObject jb = new JSONObject(jsonObject.getString("returnShop"));
//	//
//	// retShop.setAdd_time(jb.optString("add_time"));
//	// retShop.setCause(jb.optString("cause"));
//	// retShop.setExplain(jb.optString("explain"));
//	// retShop.setMoney(jb.optString("money"));
//	// retShop.setStatus(jb.optString("status"));
//	// retShop.setStore_code(jb.optString("store_code"));
//	//
//	// return retShop;
//	// }
//
//	public static final CheckPwdInfo createCheckPwdInfo(Context context, String result) throws JSONException {
//		CheckPwdInfo Info = new CheckPwdInfo();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			Info.setStatus("");
//		} else {
//			Info.setStatus(j.has("status") ? j.getString("status") : "");
//		}
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			Info.setMessage("");
//		} else {
//			Info.setMessage(j.has("message") ? j.getString("message") : "");
//		}
//
//		if (!j.has("flag") || null == j.getString("flag") || j.getString("flag").equals("null")
//				|| j.getString("flag").equals("")) {
//			Info.setFlag("");
//		} else {
//			Info.setFlag(j.has("flag") ? j.getString("flag") : "");
//		}
//		return Info;
//	}
//
//	public static final HashMap<String, Object> creatInfos(Context context, String result) throws JSONException {
//		HashMap<String, Object> mapObj = new HashMap<String, Object>();
//		ReturnInfo retInfo = new ReturnInfo();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			retInfo.setStatus("");
//		} else {
//			retInfo.setStatus(j.has("status") ? j.getString("status") : "");
//		}
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			retInfo.setMessage("");
//		} else {
//			retInfo.setMessage(j.has("message") ? j.getString("message") : "");
//		}
//		// retInfo.setIsCart(j.optInt("isCart"));
//
//		if (!j.has("status") || null == j.getString("status") || j.getString("status").equals("null")
//				|| j.getString("status").equals("")) {
//			mapObj.put("status", "");
//		} else {
//			mapObj.put("status", j.has("status") ? j.getString(RetInfo.status) : "");
//		}
//		if (!j.has("message") || null == j.getString("message") 
//				|| j.getString("message").equals("")) {
//			mapObj.put("message", "");
//		} else {
//			mapObj.put("message", j.has("message") ? j.getString(RetInfo.message) : "");
//		}
//
//		if (j.getInt("status") == 1062 || j.getInt("status") == 1) {
//			Business bs = new Business();
//			if (null != j.getString("business")) {
//				bs = JSON.parseObject(j.getString("business"), Business.class);
//				mapObj.put("business", bs);
//			}
//		}
//		LogYiFu.e("获取数据的时候", retInfo.toString());
//		return mapObj;
//	}
//
//	/****
//	 * 登陆返回用户信息
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final UserInfo createYUser(Context context, String result) throws Exception {
//		UserInfo user = new UserInfo();
//		JSONObject jsonObject = new JSONObject(result);
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (jsonObject.has("twofoldness")) {
//			String s = jsonObject.getString("twofoldness");
//			LogYiFu.e("helllo", "!!!!!!" + s);
//			if (s != null && !("".equals(s)) && !("{}".equals(s))) {
//				JSONObject j2 = new JSONObject(s);
//				SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, true);
//				SharedPreferencesUtil.saveStringData(context, Pref.TWOFOLDNESS,
//						j2.has("twofoldness") ? (j2.getInt("twofoldness") + "") : "-1");
//				SharedPreferencesUtil.saveStringData(context, Pref.END_DATE,
//						j2.has("end_date") ? (j2.getLong("end_date") + "") : "-1");
//				SharedPreferencesUtil.saveStringData(context, Pref.IS_OPEN,
//						j2.has("is_open") ? (j2.getInt("is_open") + "") : "-1");
//				LogYiFu.e("helllo", "!!!!!!***" + j2.getInt("is_open"));
//			} else {
//				SharedPreferencesUtil.saveBooleanData(context, Pref.IS_QULIFICATION, false);
//			}
//		}
//		// JSONObject j2= new JSONObject(jsonObject.getString("status"));
//		// user.setStatus(jsonObject.getString("status"));
//		JSONObject j = new JSONObject(jsonObject.getString(Json.userinfo));
//
//		if (!j.has("unionid") || null == j.getString("unionid") || j.getString("unionid").equals("null")
//				|| j.getString("unionid").equals("")) {
//			user.setUuid(j.optString(""));
//		} else {
//			user.setUuid(j.optString("unionid"));
//		}
//		user.setUser_id(j.getInt(JUserInfo.user_id));
//
//		if (!j.has(JUserInfo.phone) || null == j.getString(JUserInfo.phone)
//				|| j.getString(JUserInfo.phone).equals("null") || j.getString(JUserInfo.phone).equals("")) {
//			user.setPhone("");
//		} else {
//			user.setPhone(j.optString(JUserInfo.phone));
//		}
//		if (!j.has(JUserInfo.email) || null == j.getString(JUserInfo.email)
//				|| j.getString(JUserInfo.email).equals("null") || j.getString(JUserInfo.email).equals("")) {
//			user.setEmail("");
//		} else {
//			user.setEmail(j.optString(JUserInfo.email));
//		}
//		if (!j.has(JUserInfo.nickname) || null == j.getString(JUserInfo.nickname)
//				 || j.getString(JUserInfo.nickname).equals("")) {
//			user.setNickname("");
//		} else {
//			user.setNickname(j.optString(JUserInfo.nickname));
//		}
//
//		if (!j.has(JUserInfo.user_type) || null == j.getString(JUserInfo.user_type)
//				|| j.getString(JUserInfo.user_type).equals("null")) {
//			user.setUser_type(-1000);
//		} else {
//			user.setUser_type(j.optInt(JUserInfo.user_type));
//		}
//		if (!j.has(JUserInfo.account) || null == j.getString(JUserInfo.account)
//				) {
//			user.setAccount("");
//		} else {
//			user.setAccount(j.optString(JUserInfo.account));
//		}
//
//		if (!j.has(JUserInfo.user_name) || null == j.getString(JUserInfo.user_name)
//				) {
//			user.setUser_name("");
//		} else {
//			user.setUser_name(j.optString(JUserInfo.user_name));
//		}
//
//		if (!j.has(JUserInfo.user_ident) || null == j.getString(JUserInfo.user_ident)
//				) {
//			user.setUser_ident("");
//		} else {
//			user.setUser_ident(j.optString(JUserInfo.user_ident));
//		}
//		// user.setHome_address(j.optString(JUserInfo.home_address));
//		// user.setOccupation(j.optString(JUserInfo.occupation));
//		user.setAge(j.optInt(JUserInfo.age));
//		user.setGender(j.optInt(JUserInfo.gender));
//		if (!j.has(JUserInfo.remarks) || null == j.getString(JUserInfo.remarks)
//			) {
//			user.setRemarks("");
//		} else {
//			user.setRemarks(j.optString(JUserInfo.remarks));
//		}
//
//		if (!j.has(JUserInfo.birthday) || null == j.getString(JUserInfo.birthday)
//				) {
//			user.setBirthday("");
//		} else {
//			user.setBirthday(j.optString(JUserInfo.birthday));
//		}
//
//		if (!j.has(JUserInfo.pic) || null == j.getString(JUserInfo.pic) || j.getString(JUserInfo.pic).equals("null")) {
//			user.setPic("");
//		} else {
//			user.setPic(j.optString(JUserInfo.pic));
//		}
//
//		if (!j.has(JUserInfo.city) || null == j.getString(JUserInfo.city)
//				) {
//			user.setCity("");
//		} else {
//			user.setCity(j.has(JUserInfo.city) ? j.optString(JUserInfo.city) : "");
//		}
//
//		if (!j.has(JUserInfo.email_status) || null == j.getString(JUserInfo.email_status)
//				|| j.getString(JUserInfo.email_status).equals("null")
//				|| j.getString(JUserInfo.email_status).equals("")) {
//			user.setEmail_status(0);
//		} else {
//			user.setEmail_status(j.has(JUserInfo.email_status) ? j.optInt(JUserInfo.email_status) : 0);
//		}
//
//		if (!j.has(JUserInfo.street) || null == j.getString(JUserInfo.street)
//			|| j.getString(JUserInfo.street).equals("")) {
//			user.setStreet("");
//		} else {
//			user.setStreet(j.has(JUserInfo.street) ? j.optString(JUserInfo.street) : "");
//		}
//
//		if (!j.has(JUserInfo.area) || null == j.getString(JUserInfo.area) 
//				|| j.getString(JUserInfo.area).equals("")) {
//			user.setArea("");
//		} else {
//			user.setArea(j.has(JUserInfo.area) ? j.optString(JUserInfo.area) : "");
//		}
//
//		if (!j.has(JUserInfo.province) || null == j.getString(JUserInfo.province)
//				|| j.getString(JUserInfo.province).equals("null") || j.getString(JUserInfo.province).equals("")) {
//			user.setProvince("");
//		} else {
//			user.setProvince(j.has(JUserInfo.province) ? j.optString(JUserInfo.province) : "");
//		}
//
//		if (!j.has("person_sign") || null == j.getString("person_sign") || j.getString("person_sign").equals("null")
//				|| j.getString("person_sign").equals("")) {
//			user.setUserSign("");
//		} else {
//			user.setUserSign(j.optString("person_sign"));
//		}
//
//		if (!j.has("birthday") || null == j.getString("birthday") || j.getString("birthday").equals("null")
//				|| j.getString("birthday").equals("")) {
//			user.setBirthday("");
//		} else {
//			user.setBirthday(j.optString("birthday"));
//		}
//
//		if (!j.has("province") || null == j.getString("province") || j.getString("province").equals("null")
//				|| j.getString("province").equals("")) {
//			user.setProvince("");
//		} else {
//			user.setProvince(j.optString("province"));
//		}
//
//		if (!j.has("city") || null == j.getString("city") 
//				|| j.getString("city").equals("")) {
//			user.setCity("");
//		} else {
//			user.setCity(j.optString("city"));
//		}
//
//		if (!j.has("hobby") || null == j.getString("hobby") 
//				|| j.getString("hobby").equals("")) {
//			user.setHobby("");
//		} else {
//			user.setHobby(j.optString("hobby"));
//		}
//
//		if (!j.has("code_type") || null == j.getString("code_type") || j.getString("code_type").equals("null")
//				|| j.getString("code_type").equals("")) {
//			user.setCode_type("");// 判断是否 是商家预审核
//		} else {
//			user.setCode_type(j.optString("code_type"));// 判断是否 是商家预审核
//		}
//
//		if (!j.has("is_member") || null == j.getString("is_member") || j.getString("is_member").equals("null")
//				|| j.getString("is_member").equals("")) {
//			user.setIs_member("");// 判断是否是会员
//		} else {
//			user.setIs_member(j.optString("is_member"));// 判断是否是会员
//		}
//
//		if (!j.has("imei") || null == j.getString("imei") || j.getString("imei").equals("null")
//				|| j.getString("imei").equals("")) {
//			user.setImei("");
//		} else {
//			user.setImei(j.optString("imei"));
//		}
//		if (!j.has("mac") || null == j.getString("mac") || j.getString("mac").equals("null")
//				|| j.getString("mac").equals("")) {
//			user.setMac("");
//		} else {
//			user.setMac(j.optString("mac"));
//		}
//		return user;
//	}
//
//	/****
//	 * 登陆返回用户信息
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final Store createStore(Context context, String result) throws Exception {
//		Store store = new Store();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject) || !jsonObject.has("store")) {
//			return null;
//		}
//		String storeStr = jsonObject.has("store") ? jsonObject.getString("store") : "";
//		if (null == storeStr || "".equals(storeStr)) {
//			return null;
//		}
//		store = JSON.parseObject(storeStr, Store.class);
//		YCache.setCacheStore(context, store);
//		return store;
//	}
//
//	/****
//	 * 保存下单令牌
//	 */
//	public static final void saveOrderToken(Context context, String result) throws Exception {
//		JSONObject j = new JSONObject(result);
//
//		if (!j.has("orderToken") || null == j.getString("orderToken") || j.getString("orderToken").equals("null")
//				|| j.getString("orderToken").equals("")) {
//			YCache.saveOrderToken(context, "");
//		} else {
//			YCache.saveOrderToken(context, j.optString("orderToken"));
//		}
//	}
//
//	/***
//	 * Help列表
//	 */
//	public static final HashMap<String, Object> createHelpList(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		List<Help> helps = new ArrayList<Help>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.has("helps") ? jsonObject.getString("helps") : "");
//		for (int i = 0; i < jsonArray.length(); i++) {
//			Help help = new Help();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			help = JSON.parseObject(j.toString(), Help.class);
//			helps.add(help);
//		}
//		mapRet.put("helps", helps);
//
//		String jsonType = jsonObject.has("helpType") ? jsonObject.getString("helpType") : "";
//		LinkedHashMap<Integer, String> retInfo = JSON.parseObject(jsonType,
//				new TypeReference<LinkedHashMap<Integer, String>>() {
//				});
//		mapRet.put("helpType", retInfo);
//		return mapRet;
//	}
//
//	/**
//	 * 客服系统自动提醒热门问题
//	 */
//	public static final HashMap<String, Object> createHelpCentre(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		List<Help> helps = new ArrayList<Help>();
//
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		long time = 0;
//		if (!jsonObject.has("time") || null == jsonObject.getString("time")
//				|| jsonObject.getString("time").equals("null") || jsonObject.getString("time").equals("")) {
//		} else {
//			time = jsonObject.getLong("time");
//		}
//		mapRet.put("time", time);
//
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("helpList"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			Help help = new Help();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			help = JSON.parseObject(j.toString(), Help.class);
//			helps.add(help);
//		}
//		mapRet.put("helpList", helps);
//
//		String jsonType = "";
//		if (!jsonObject.has("helpType") || null == jsonObject.getString("helpType")
//			|| jsonObject.getString("helpType").equals("")) {
//		} else {
//			jsonType = jsonObject.getString("helpType");
//		}
//		LinkedHashMap<Integer, String> retInfo = JSON.parseObject(jsonType,
//				new TypeReference<LinkedHashMap<Integer, String>>() {
//				});
//		mapRet.put("helpType", retInfo);
//		return mapRet;
//	}
//
//	/***
//	 * Help详情
//	 */
//	public static final Help createHelp(Context context, String result) throws Exception {
//
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONObject obj = jsonObject.getJSONObject("question");
//		Help help = new Help();
//		help = JSON.parseObject(obj.toString(), Help.class);
//		return help;
//	}
//
//	public static final HashMap<String, String> createVersionInfo(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//
//		if (!j.has("version_no") || null == j.getString("version_no") 
//				|| j.getString("version_no").equals("")) {
//			retInfo.put("version_no", "");
//		} else {
//			retInfo.put("version_no", j.getString("version_no"));
//		}
//
//		if (!j.has("path") || null == j.getString("path") 
//				|| j.getString("path").equals("")) {
//			retInfo.put("path", "");// 下载路径
//		} else {
//			retInfo.put("path", j.getString("path"));// 下载路径
//		}
//
//		if (!j.has("is_update") || null == j.getString("is_update") || j.getString("is_update").equals("null")
//				|| j.getString("is_update").equals("")) {
//			retInfo.put("is_update", "");// 是否强制更新 0：否 1：是
//		} else {
//			retInfo.put("is_update", j.getString("is_update"));// 是否强制更新 0：否 1：是
//		}
//
//		if (!j.has("msg") || null == j.getString("msg") 
//				|| j.getString("msg").equals("")) {
//			retInfo.put("msg", "");// 更新的内容
//		} else {
//			retInfo.put("msg", j.getString("msg"));// 更新的内容
//		}
//		return retInfo;
//	}
//
//	/***
//	 * 订单列表
//	 */
//	public static final List<Order> createOrderList(Context context, String result) throws Exception {
//																										
//																										
//		List<Order> orders = new ArrayList<Order>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("orders"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			Order order = new Order();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			String text = "";
//			if (!j.has("orderShops") || null == j.getString("orderShops")
//					|| j.getString("orderShops").equals("")) {
//			} else {
//				text = j.optString("orderShops");
//			}
//			List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
//			order = JSON.parseObject(j.toString(), Order.class);
//			// if (list != null && list.size() > 0) {
//			// order.setList(list);
//			// }
//			order.setList(list);
//			orders.add(order);
//		}
//
//		return orders;
//	}
//
//	/***
//	 * 卡券说明
//	 */
//	public static final HashMap<String, String> createKaquan(Context context, String result) throws Exception {
//
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//
//		LogYiFu.e("Kr", result + "");
//		JSONObject j = new JSONObject(result);
//		if (j.getString("status").equals(1 + "")) {
//			String data = "";
//			if (!j.has("data") || null == j.getString("data") 
//					|| j.getString("data").equals("")) {
//			} else {
//				data = j.getString("data");
//			}
//			retInfo.put("data", data);
//		}
//		return retInfo;
//	}
//
//	/***
//	 * 获得夺宝参入号码
//	 */
//	public static final HashMap<String, String> createDuoBaoNumber(Context context, String result) throws Exception {
//
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//
//		LogYiFu.e("Kr", result + "");
//		JSONObject j = new JSONObject(result);
//		if (j.getString("status").equals(1 + "")) {
//			String data = "";
//			if (!j.has("data") || null == j.getString("data") 
//					|| j.getString("data").equals("")) {
//			} else {
//				data = j.getString("data");
//			}
//			retInfo.put("data", data);
//		}
//		return retInfo;
//	}
//
//	/***
//	 * 订单列表modify(待用)
//	 */
//	public static final HashMap<String, Object> createOrderListmodify(Context context, String result) throws Exception {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		List<Order> orders = new ArrayList<Order>();
//		JSONObject jsonObject = new JSONObject(result);
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (!jsonObject.has("data") || null == jsonObject.getString("data")
//				|| jsonObject.getString("data").equals("null") || jsonObject.getString("data").equals("")) {
//			retInfo.put("now", 0);
//		} else {
//			retInfo.put("now", jsonObject.getLong("now"));
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("orders"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			Order order = new Order();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			String text = "";
//			if (!j.has("orderShops") || null == j.getString("orderShops") 
//					|| j.getString("orderShops").equals("")) {
//			} else {
//				text = j.optString("orderShops");
//			}
//			List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
//			order = JSON.parseObject(j.toString(), Order.class);
//			if (list != null && list.size() > 0) {
//				order.setList(list);
//			}
//			orders.add(order);
//		}
//		retInfo.put("orders", orders);
//		return retInfo;
//	}
//
//	/***
//	 * 资金明细列表
//	 */
//	public static final List<FundDetail> createFundList(Context context, String result) throws Exception {
//																											
//		List<FundDetail> funds = new ArrayList<FundDetail>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("fundDetails"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			FundDetail fund = new FundDetail();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			fund = JSON.parseObject(j.toString(), FundDetail.class);
//			funds.add(fund);
//		}
//		return funds;
//	}
//
//	/***
//	 * 我的银行卡列表
//	 */
//	public static final List<MyBankCard> createCardList(Context context, String result) throws Exception {
//																											
//		List<MyBankCard> bankCards = new ArrayList<MyBankCard>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("bankCards"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			MyBankCard bankCard = new MyBankCard();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			bankCard = JSON.parseObject(j.toString(), MyBankCard.class);
//			bankCards.add(bankCard);
//		}
//		return bankCards;
//	}
//
//	/***
//	 * 我的卡券列表
//	 */
//	public static final List<HashMap<String, Object>> createMyCouponListNew(Context context, String result)
//			throws Exception {
//		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONArray ja = new JSONArray(j.getString("data"));
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//
//			if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")
//					|| jo.getString("id").equals("")) {
//				map.put("id", 0);
//			} else {
//				map.put("id", jo.has("id") ? jo.optInt("id") : 0);
//			}
//
//			if (!jo.has("c_type") || null == jo.getString("c_type") || jo.getString("c_type").equals("null")
//					|| jo.getString("c_type").equals("")) {
//				map.put("c_type", 0);
//			} else {
//				map.put("c_type", jo.has("c_type") ? jo.optInt("c_type") : 0);
//			}
//
//			if (!jo.has("c_price") || null == jo.getString("c_price") || jo.getString("c_price").equals("null")
//					|| jo.getString("c_price").equals("")) {
//				map.put("c_price", 0);
//			} else {
//				map.put("c_price", jo.has("c_price") ? jo.optDouble("c_price") : 0);
//			}
//
//			if (!jo.has("c_cond") || null == jo.getString("c_cond") || jo.getString("c_cond").equals("null")
//					|| jo.getString("c_cond").equals("")) {
//				map.put("c_cond", 0);
//			} else {
//				map.put("c_cond", jo.has("c_cond") ? jo.optInt("c_cond") : 0);
//			}
//
//			if (!jo.has("c_overlying") || null == jo.getString("c_overlying")
//					|| jo.getString("c_overlying").equals("null") || jo.getString("c_overlying").equals("")) {
//				map.put("c_overlying", "");
//			} else {
//				map.put("c_overlying", jo.has("c_overlying") ? jo.optString("c_overlying") : "");
//			}
//
//			if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")
//					|| jo.getString("user_id").equals("")) {
//				map.put("user_id", "");
//			} else {
//				map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
//			}
//
//			if (!jo.has("s_code") || null == jo.getString("s_code") || jo.getString("s_code").equals("null")
//					|| jo.getString("s_code").equals("")) {
//				map.put("s_code", "");
//			} else {
//				map.put("s_code", jo.has("s_code") ? jo.optString("s_code") : "");
//			}
//
//			if (!jo.has("c_add_time") || null == jo.getString("c_add_time") || jo.getString("c_add_time").equals("null")
//					|| jo.getString("c_add_time").equals("")) {
//				map.put("c_add_time", "");
//			} else {
//				map.put("c_add_time", jo.has("c_add_time") ? jo.optString("c_add_time") : "");
//			}
//
//			if (!jo.has("c_last_time") || null == jo.getString("c_last_time")
//					|| jo.getString("c_last_time").equals("null") || jo.getString("c_last_time").equals("")) {
//				map.put("c_last_time", "");
//			} else {
//				map.put("c_last_time", jo.has("c_last_time") ? jo.optString("c_last_time") : "");
//			}
//
//			if (!jo.has("is_use") || null == jo.getString("is_use") || jo.getString("is_use").equals("null")
//					|| jo.getString("is_use").equals("")) {
//				map.put("is_use", "");
//			} else {
//				map.put("is_use", jo.has("is_use") ? jo.optString("is_use") : "");
//			}
//
//			if (!jo.has("c_remark") || null == jo.getString("c_remark") || jo.getString("c_remark").equals("null")
//					|| jo.getString("c_remark").equals("")) {
//				map.put("c_remark", "");
//			} else {
//				map.put("c_remark", jo.has("c_remark") ? jo.optString("c_remark") : "");
//			}
//			// ////// 在这加这东西干嘛？
//			// map.put("voucher", jo.has("voucher") ? jo.optString("voucher")
//			// : "");
//			// map.put("snum", jo.has("snum") ? jo.optString("snum")
//			// : "");
//			// map.put("price", jo.has("price") ? jo.optString("price")
//			// : "");
//			// /////
//			lists.add(map);
//
//		}
//		return lists;
//	}
//
//	/***
//	 * 我的卡券列表
//	 */
//	public static final List<HashMap<String, Object>> createMyCouponList(Context context, String result)
//			throws Exception {
//		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONArray ja = new JSONArray(j.getString("coupons"));
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//
//			if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")
//					|| jo.getString("id").equals("")) {
//				map.put("id", 0);
//			} else {
//				map.put("id", jo.has("id") ? jo.optInt("id") : 0);
//			}
//
//			if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")
//					|| jo.getString("user_id").equals("")) {
//				map.put("user_id", 0);
//			} else {
//				map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
//			}
//
//			if (!jo.has("c_id") || null == jo.getString("c_id") || jo.getString("c_id").equals("null")
//					|| jo.getString("c_id").equals("")) {
//				map.put("c_id", 0);
//			} else {
//				map.put("c_id", jo.has("c_id") ? jo.optInt("c_id") : 0);
//			}
//
//			if (!jo.has("num") || null == jo.getString("num") || jo.getString("num").equals("null")
//					|| jo.getString("num").equals("")) {
//				map.put("num", 0);
//			} else {
//				map.put("num", jo.has("num") ? jo.optInt("num") : 0);
//			}
//
//			if (!jo.has("add_time") || null == jo.getString("add_time") || jo.getString("add_time").equals("null")
//					|| jo.getString("add_time").equals("")) {
//				map.put("add_time", "");
//			} else {
//				map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
//			}
//
//			if (!jo.has("last_time") || null == jo.getString("last_time") || jo.getString("last_time").equals("null")
//					|| jo.getString("last_time").equals("")) {
//				map.put("last_time", "");
//			} else {
//				map.put("last_time", jo.has("last_time") ? jo.optString("last_time") : "");
//			}
//			lists.add(map);
//		}
//		return lists;
//	}
//
//	/***
//	 * 我的卡券总数
//	 */
//	public static final int createMyCouponCount(Context context, String result) throws Exception {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return 0;
//		}
//
//		int count = 0;
//		if (!j.has("count") || null == j.getString("count") || j.getString("count").equals("null")
//				|| j.getString("count").equals("")) {
//		} else {
//			count = j.getInt("count");
//		}
//		return count;
//	}
//
//	/***
//	 * 我的最爱列表
//	 */
//	public static final List<Like> createMyFavourList(Context context, String result) throws Exception {
//
//		List<Like> favours = new ArrayList<Like>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		String pageCount = jsonObject.has("pageCount") ? jsonObject.getString("pageCount") : "";
//
//		if (!TextUtils.isEmpty(pageCount)) {
//			JSONArray jsonArray = new JSONArray(jsonObject.has("likes") ? jsonObject.getString("likes") : null);
//			for (int i = 0; i < jsonArray.length(); i++) {
//				Like like = new Like();
//				JSONObject j = (JSONObject) jsonArray.opt(i);
//				like = JSON.parseObject(j.toString(), Like.class);
//				like.setPageCount(pageCount);
//				favours.add(like);
//			}
//		}
//		return favours;
//	}
//
//	/***
//	 * 个人中心统计
//	 */
//	public static final HashMap<String, Object> createPersonCenterCount(Context context, String result)
//			throws Exception {
//		// result = result.replace("null", "\"\"");
//		// MycenterCount centerCount = null;
//		// List<String> lists = new ArrayList<String>();
//		HashMap<String, Object> mapCount = new HashMap<String, Object>();
//		JSONObject jsonObject;
//
//		jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		
//		if (!jsonObject.has("like_count") || null == jsonObject.getString("like_count") || jsonObject.getString("like_count").equals("null")
//				|| jsonObject.getString("like_count").equals("")) {
//			mapCount.put("like_count",  "0");
//		} else {
//			mapCount.put("like_count", jsonObject.optInt("like_count") + "");//我的最爱数
//		}
//	
//		if (!jsonObject.has("store_shop_count") || null == jsonObject.getString("store_shop_count") || jsonObject.getString("store_shop_count").equals("null")
//				|| jsonObject.getString("store_shop_count").equals("")) {
//			mapCount.put("store_shop_count",  "0");// 店铺美衣数
//		} else {
//			mapCount.put("store_shop_count",jsonObject.optInt("store_shop_count") + "");// 店铺美衣数
//		}
//		
//		if (!jsonObject.has("integral") || null == jsonObject.getString("integral") || jsonObject.getString("integral").equals("null")
//				|| jsonObject.getString("store_shop_count").equals("")) {
//			mapCount.put("integral", "0");// 我的积分数
//		} else {
//			mapCount.put("integral", jsonObject.optInt("integral") + "");// 我的积分数
//		}
//
//	
//		if (!jsonObject.has("balance_show") || null == jsonObject.getString("balance_show") || jsonObject.getString("balance_show").equals("null")
//				|| jsonObject.getString("balance_show").equals("")) {
//			mapCount.put("balance_show","0");// 返回钱包是否应该显示红点
//																										// 1显示,其他不显示
//		} else {
//			mapCount.put("balance_show",
//					jsonObject.has("balance_show") ? (jsonObject.optString("balance_show") + "") : "0");// 返回钱包是否应该显示红点
//																										// 1显示,其他不显示
//		}
//		
//		if (!jsonObject.has("coupon_show") || null == jsonObject.getString("coupon_show") || jsonObject.getString("coupon_show").equals("null")
//				|| jsonObject.getString("coupon_show").equals("")) {
//			mapCount.put("coupon_show",  "0");// 获取优惠券的红点
//		} else {
//			mapCount.put("coupon_show", jsonObject.has("coupon_show") ? (jsonObject.optString("coupon_show") + "") : "0");// 获取优惠券的红点
//		}																												// 1显示,其他不显示
//	
//		if (!jsonObject.has("balance") || null == jsonObject.getString("balance") || jsonObject.getString("balance").equals("null")
//				|| jsonObject.getString("balance").equals("")) {
//			mapCount.put("balance","0.0"); // 获取钱包的余额
//		} else {
//			mapCount.put("balance", jsonObject.has("balance") ? (jsonObject.optDouble("balance") + "") : "0.0"); // 获取钱包的余额
//		}	
//	
//		if (!jsonObject.has("coupon_sum") || null == jsonObject.getString("coupon_sum") || jsonObject.getString("coupon_sum").equals("null")
//				|| jsonObject.getString("coupon_sum").equals("")) {
//			mapCount.put("coupon_sum","0.0");// 获取优惠券的总额
//		} else {
//			mapCount.put("coupon_sum", jsonObject.has("coupon_sum") ? (jsonObject.optDouble("coupon_sum") + "") : "0.0");// 获取优惠券的总额
//		}	
//		
//		if (!jsonObject.has("mySteps_count") || null == jsonObject.getString("mySteps_count") || jsonObject.getString("mySteps_count").equals("null")
//				|| jsonObject.getString("mySteps_count").equals("")) {
//			mapCount.put("mySteps_count","0");// 我的足迹
//		} else {
//			mapCount.put("mySteps_count", jsonObject.optInt("mySteps_count") + "");// 我的足迹
//		}	
//		mapCount.put("pay_count",
//				jsonObject.optInt("pay_count") + "" == null || (jsonObject.optInt("pay_count") + "").equals("null")
//						|| (jsonObject.optInt("pay_count") + "").equals("") ? "0"
//								: jsonObject.optInt("pay_count") + "");// 待付款数
//		mapCount.put("send_count",
//				jsonObject.optInt("send_count") + "" == null || (jsonObject.optInt("send_count") + "").equals("null")
//						|| (jsonObject.optInt("send_count") + "").equals("") ? "0"
//								: jsonObject.optInt("send_count") + "");// 待发货数
//		mapCount.put("furl_count", jsonObject.optInt("furl_count") + "");// 待收货数
//		mapCount.put("ass_count",
//				jsonObject.optInt("ass_count") + "" == null || (jsonObject.optInt("ass_count") + "").equals("null")
//						|| (jsonObject.optInt("ass_count") + "").equals("") ? "0"
//								: jsonObject.optInt("ass_count") + "");// 待评价数
//	
//		if (!jsonObject.has("refund_count") || null == jsonObject.getString("refund_count") || jsonObject.getString("refund_count").equals("null")
//				|| jsonObject.getString("refund_count").equals("")) {
//			mapCount.put("refund_count", "0");// 退款中数
//		} else {
//			mapCount.put("refund_count", jsonObject.optInt("change_count") + "");// 退款中数
//		}	
//		JSONObject jo = jsonObject.optJSONObject("home_info");
//
//		
//		if (!jsonObject.has("circle_count") || null == jsonObject.getString("circle_count") || jsonObject.getString("circle_count").equals("null")
//				|| jsonObject.getString("circle_count").equals("")) {
//			mapCount.put("circle_count","0" );
//		} else {
//			mapCount.put("circle_count",
//					(jo.optInt("circle_count") + "").equals("") ? "0" : (jo.optInt("circle_count") + ""));
//		}	
//	
//		if (!jsonObject.has("finCount") || null == jsonObject.getString("finCount") || jsonObject.getString("finCount").equals("null")
//				|| jsonObject.getString("finCount").equals("")) {
//			mapCount.put("finCount","0");
//		} else {
//			mapCount.put("finCount",
//					(jsonObject.optInt("finCount") + "").equals("") ? "0" : (jsonObject.optInt("finCount") + ""));
//		}
//
//		if (!jsonObject.has("fans_count") || null == jsonObject.getString("fans_count") || jsonObject.getString("fans_count").equals("null")
//				|| jsonObject.getString("fans_count").equals("")) {
//			mapCount.put("fans_count", "0");
//		} else {
//			mapCount.put("fans_count", (jo.optInt("fans_count") + "").equals("") ? "0" : (jo.optInt("fans_count") + ""));
//		}
//		// YCache.getCacheUser(context).setFans_count(ja.);
//
//		// centerCount = JSON.parseObject(result, MycenterCount.class);
//		// centerCount.setCircle_count(ja.optInt(0, "circle_count"));
//		// centerCount.setFans_count(ja.optString(0, "fans_count"));
//
//		return mapCount;
//	}
//
//	/***
//	 * 我的积分信息
//	 */
//	public static final HashMap<String, List<HashMap<String, String>>> createIntegralInfo(Context context,
//			String result) throws Exception {
//		HashMap<String, String> mapRet = new HashMap<String, String>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		HashMap<String, List<HashMap<String, String>>> maplist = new HashMap<String, List<HashMap<String, String>>>();
//		List<HashMap<String, String>> listMap = new ArrayList<HashMap<String, String>>();
//
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("mission"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject obj = (JSONObject) jsonArray.opt(i);
//			HashMap<String, String> mapMission = new HashMap<String, String>();
//			
//			if (!obj.has("id") || null == obj.getString("id") 
//					|| obj.getString("id").equals("")) {
//				mapMission.put("id", "");
//			} else {
//				mapMission.put("id", obj.getString("id"));
//			}
//			
//			if (!obj.has("id") || null == obj.getString("id") 
//					|| obj.getString("id").equals("")) {
//				mapMission.put("m_name", "");
//			} else {
//				mapMission.put("m_name", obj.getString("m_name"));
//			}
//			listMap.add(mapMission);
//		}
//		maplist.put("mission", listMap);
//
//		List<HashMap<String, String>> listMapInfo = new ArrayList<HashMap<String, String>>();
//	
//		if (!jsonObject.has("income") || null == jsonObject.getString("income") 
//				|| jsonObject.getString("income").equals("")) {
//			mapRet.put("income", "");
//		} else {
//			mapRet.put("income", jsonObject.getString("income"));
//		}
//		
//		if (!jsonObject.has("expense") || null == jsonObject.getString("expense") 
//				|| jsonObject.getString("expense").equals("")) {
//			mapRet.put("expense", "");
//		} else {
//			mapRet.put("expense", jsonObject.getString("expense"));
//		}
//	
//		if (!jsonObject.has("integral") || null == jsonObject.getString("integral") 
//				|| jsonObject.getString("integral").equals("")) {
//			mapRet.put("integral", "");
//		} else {
//			mapRet.put("integral", jsonObject.getString("integral"));
//		}
//		
//		if (!jsonObject.has("signDay") || null == jsonObject.getString("signDay") 
//				|| jsonObject.getString("signDay").equals("")) {
//			mapRet.put("signDay", "");
//		} else {
//			mapRet.put("signDay", jsonObject.getString("signDay"));
//		}
//		
//		if (!jsonObject.has("is_sign") || null == jsonObject.getString("is_sign") 
//				|| jsonObject.getString("is_sign").equals("")) {
//			mapRet.put("is_sign", "");
//		} else {
//			mapRet.put("is_sign", jsonObject.getString("is_sign"));
//		}
//		String fulfill = jsonObject.getString("fulfill").toString();
//		mapRet.put("mapFulfill", fulfill);
//
//		listMapInfo.add(mapRet);
//
//		maplist.put("info", listMapInfo);
//		return maplist;
//	}
//
//	/***
//	 * 将数据得到的商品属性数据插入数据库
//	 * 
//	 * @param context
//	 * @param result
//	 * @throws Exception
//	 */
//	public static final void updateSyncDatas(Context context, String result) throws Exception {
//		YDBHelper helpler = new YDBHelper(context);
//		List<String> sqls = new ArrayList<String>();
//		JSONObject jo = new JSONObject(result);
//
//		if (jo.getString("status").equals("1")) {
//			// 分类数据
//			JSONArray jType = new JSONArray(jo.getString("shop_type"));
//			LogYiFu.e("获取的数据的长度", jType.length() + "");
//			if (null != jType && jType.length() > 0) {
//				helpler.delete("delete from sort_info");
//				for (int i = 0; i < jType.length(); i++) {
//					ShopType sType = JSON.parseObject(jType.getString(i), ShopType.class);
//					String sql = "insert into sort_info(_id,sort_name,icon,is_show,p_id,group_flag,sequence)values('"
//							+ sType.getId() + "','" + sType.getType_name() + "','" + sType.getIco() + "','"
//							+ sType.getIs_show() + "','" + sType.getParent_id() + "','" + sType.getGroup_flag() + "','"
//							+ sType.getSequence() + "')";
//					sqls.add(sql);
//
//				}
//			}
//
//			// 属性数据
//
//			/*
//			 * JSONArray jAttr = new JSONArray(jo.getString("shop_attr")); if
//			 * (null != jAttr && jAttr.length() > 0) { helpler.delete(
//			 * "delete from attr_info"); for (int i = 0; i < jAttr.length();
//			 * i++) { ShopAttr sAttr = JSON.parseObject(jAttr.getString(i),
//			 * ShopAttr.class); String sql =
//			 * "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('"
//			 * + sAttr.getId() + "','" + sAttr.getAttr_name() + "','" +
//			 * sAttr.getIco() + "','" + sAttr.getParent_id() + "','" +
//			 * sAttr.getIs_show() + "')"; sqls.add(sql);
//			 * 
//			 * } }
//			 */
//
//			/*
//			 * JSONArray jAttr = new JSONArray(jo.getString("shop_attr")); if
//			 * (null != jAttr && jAttr.length() > 0) { helpler.delete(
//			 * "delete from attr_info"); for (int i = 0; i < jAttr.length();
//			 * i++) { ShopAttr sAttr = JSON.parseObject(jAttr.getString(i),
//			 * ShopAttr.class); String sql =
//			 * "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('"
//			 * + sAttr.getId() + "','" + sAttr.getAttr_name() + "','" +
//			 * sAttr.getIco() + "','" + sAttr.getParent_id() + "','" +
//			 * sAttr.getIs_show() + "')"; sqls.add(sql);
//			 * 
//			 * } }
//			 */
//
//			// 商家标签数据
//			/*
//			 * JSONArray jaBusTag = new JSONArray(jo.getString("bus_tag")); if
//			 * (null != jaBusTag && jaBusTag.length() > 0) { helpler.delete(
//			 * "delete from bus_tag"); for (int i = 0; i < jaBusTag.length();
//			 * i++) { BusinessTag sTag = JSON.parseObject(jaBusTag.optString(i),
//			 * BusinessTag.class); String sql =
//			 * "insert into bus_tag(_id,attr_name,icon,p_id,sequence,is_show)values('"
//			 * + sTag.getId() + "','" + sTag.getTag_name() + "','" +
//			 * sTag.getIco() + "','" + sTag.getParent_id() + "','" +
//			 * sTag.getSequence() + "','" + sTag.getIs_show() + "')";
//			 * sqls.add(sql); } }
//			 */
//
//			// 标签数据
//			JSONArray jaTag = new JSONArray(jo.getString("shop_tag"));
//			if (null != jaTag && jaTag.length() > 0) {
//				helpler.delete("delete from tag_info");
//				for (int i = 0; i < jaTag.length(); i++) {
//					ShopTag sTag = JSON.parseObject(jaTag.getString(i), ShopTag.class);
//					String sql = "insert into tag_info(_id,attr_name,icon,p_id,e_name,sequence,is_show)values('"
//							+ sTag.getId() + "','" + sTag.getTag_name() + "','" + sTag.getIco() + "','"
//							+ sTag.getParent_id() + "','" + sTag.getE_name() + "','" + sTag.getSequence() + "','"
//							+ sTag.getIs_show() + "')";
//					sqls.add(sql);
//
//				}
//			}
//			helpler.update(sqls);
//			// helpler.close();
//			/*
//			 * context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
//			 * .edit() .putString(Pref.sync_attr_date,
//			 * jo.getString("attr_data")) .commit();
//			 */
//			context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
//					.putString(Pref.sync_sort_date, jo.getString("type_data")).commit();
//			context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE).edit()
//					.putString(Pref.sync_tag_date, jo.getString("tag_data")).commit();
//			// context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
//			// .edit()
//			// .putString(Pref.sync_bus_tag_date,
//			// jo.optString("bus_tag_data")).commit();
//			/*
//			 * context.getSharedPreferences(Pref.sync, Context.MODE_PRIVATE)
//			 * .edit() .putString(Pref.sync_bus_tag_date,
//			 * jo.optString("bus_tag_data")).commit();
//			 */
//		}
//
//	}
//
//	public static final String[] createMyWalletInfo(Context context, String result) throws JSONException {
//		JSONObject jo = new JSONObject(result);
//		String str[] = new String[4];
//		if (jo.getInt("status") == 1) {
//			
//			if (!jo.has("balance") || null == jo.getString("balance") || jo.getString("balance").equals("null")
//					|| jo.getString("balance").equals("")) {
//				str[0] = "0"; // 余额
//			} else {
//				str[0] = jo.optString("balance"); // 余额
//			}
//		
//			if (!jo.has("conponCount") || null == jo.getString("conponCount") || jo.getString("conponCount").equals("null")
//					|| jo.getString("conponCount").equals("")) {
//				str[1] = "0"; // 我的券张数
//			} else {
//				str[1] = jo.optString("conponCount"); // 我的券张数
//			}
//			
//			if (!jo.has("two_balance") || null == jo.getString("two_balance") || jo.getString("two_balance").equals("null")
//					|| jo.getString("two_balance").equals("")) {
//				str[2] = "0";// 二级金额
//			} else {
//				str[2] = jo.has("two_balance") ? jo.optString("two_balance") : "0";// 二级金额
//			}
//			
//			if (!jo.has("two_freeze_balance") || null == jo.getString("two_freeze_balance") || jo.getString("two_freeze_balance").equals("null")
//					|| jo.getString("two_freeze_balance").equals("")) {
//				str[3] = "0";// 二级冻结金额
//			} else {
//				str[3] = jo.has("two_freeze_balance") ? jo.optString("two_freeze_balance") : "0";// 二级冻结金额
//			}
//		}
//		return str;
//	}
//
//	/**
//	 * 个人中心邀请好友
//	 */
//	public static final HashMap<String, Object> createInviteFriend(Context context, String result)
//			throws JSONException {
//
//		HashMap<String, Object> mapObject = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONObject pShop = j.optJSONObject("data");
//		if (!pShop.has("user_id") || null == pShop.getString("user_id") 
//				|| pShop.getString("user_id").equals("")) {
//			mapObject.put("user_id","");
//		} else {
//			mapObject.put("user_id", pShop.has("user_id") ? pShop.getString("user_id") : "");
//		}
//		if (!pShop.has("link") || null == pShop.getString("link") 
//				|| pShop.getString("link").equals("")) {
//			mapObject.put("link", "");
//		} else {
//			mapObject.put("link", pShop.has("link") ? pShop.getString("link") : "");
//		}
//		if (!pShop.has("pic") || null == pShop.getString("pic") 
//				|| pShop.getString("pic").equals("")) {
//			mapObject.put("pic","");
//		} else {
//			mapObject.put("pic", pShop.has("pic") ? pShop.getString("pic") : "");
//		}
//		return mapObject;
//	}
//
//	/**
//	 * 主界面商品列表
//	 */
//	public static final List<HashMap<String, Object>> createProductList(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("listShop"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			
//			if (!jo.has("def_pic") || null == jo.getString("def_pic") 
//					|| jo.getString("def_pic").equals("")) {
//				mapObject.put("def_pic", "");
//			} else {
//				mapObject.put("def_pic", jo.getString("def_pic"));
//			}
//			if (!jo.has("shop_code") || null == jo.getString("shop_code") 
//					|| jo.getString("shop_code").equals("")) {
//				mapObject.put("shop_code", "");
//			} else {
//				mapObject.put("shop_code", jo.getString("shop_code"));
//			}
//			
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") ||jo.getString("shop_se_price").equals("null") 
//					|| jo.getString("shop_se_price").equals("")) {
//				mapObject.put("shop_se_price", "0");
//			} else {
//				mapObject.put("shop_se_price", jo.getString("shop_se_price"));
//			}
//			// TODO:
//			// mapObject.put("shop_price", jo.getString("shop_price"));
//			// mapObject.put("id", jo.getString("id"));
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") 
//					|| jo.getString("shop_name").equals("")) {
//				mapObject.put("shop_name", "");
//			} else {
//				mapObject.put("shop_name", jo.getString("shop_name"));
//			}
//			// mapObject.put("love_num", jo.getString("love_num"));
//			
//			if (!jo.has("kickback") || null == jo.getString("kickback")||jo.getString("kickback").equals("null")
//					|| jo.getString("kickback").equals("")) {
//				mapObject.put("kickback", jo.getString("0"));
//			} else {
//				mapObject.put("kickback", jo.getString("kickback"));
//			}
//			//
//			// if (jo.has("isLike")) {
//			// mapObject.put("isLike", jo.getInt("isLike"));
//			// } else {
//			// mapObject.put("isLike", 0);
//			// }
//			//
//			// if (jo.has("isCart")) {
//			// mapObject.put("isCart", jo.getInt("isCart"));
//			// } else {
//			// mapObject.put("isCart", 0);
//			// }
//
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 主界面商品列表
//	 */
//	public static final HashMap<String, Object> createProductPicList(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		String[] picList = j.getString("pic_list").split(",");
//		retInfo.put("picList", picList);
//		retInfo.put("status", j.getString("status"));
//		return retInfo;
//	}
//
//	/**
//	 * 搭配 首页
//	 */
//	public static final List<HashMap<String, Object>> createMatchList(Context context, String result)
//			throws JSONException {//TODO:ZZQ
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (!"1".equals(j.getString("status"))) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("listShop"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("collocation_name") || null == jo.getString("collocation_name") 
//					|| jo.getString("collocation_name").equals("")) {
//				mapObject.put("collocation_name", "");
//			} else {
//				mapObject.put("collocation_name", jo.has("collocation_name") ? jo.optString("collocation_name") : "");
//			}
//			if (!jo.has("collocation_code") || null == jo.getString("collocation_code") 
//					|| jo.getString("collocation_code").equals("")) {
//				mapObject.put("collocation_code", "");
//			} else {
//				mapObject.put("collocation_code", jo.has("collocation_code") ? jo.optString("collocation_code") : "");
//			}
//			if (!jo.has("collocation_pic") || null == jo.getString("collocation_pic") 
//					|| jo.getString("collocation_pic").equals("")) {
//				mapObject.put("collocation_pic","");
//			} else {
//				mapObject.put("collocation_pic", jo.has("collocation_pic") ? jo.optString("collocation_pic") : "");
//			}
//			List<HashMap<String, Object>> listCollocationShop = new ArrayList<HashMap<String, Object>>();
//			JSONArray jsonArrayCollocationShop = jo.getJSONArray("collocation_shop");
//			for (int k = 0; k < jsonArrayCollocationShop.length(); k++) {
//				JSONObject jk = (JSONObject) jsonArrayCollocationShop.opt(k);
//				HashMap<String, Object> mapObjectCollocationShop = new HashMap<String, Object>();
//				if (!jk.has("shop_code") || null == jk.getString("shop_code") 
//						|| jk.getString("shop_code").equals("")) {
//					mapObjectCollocationShop.put("shop_code", "");
//				} else {
//					mapObjectCollocationShop.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
//				}
//				if (!jk.has("shop_name") || null == jk.getString("shop_name") 
//						|| jk.getString("shop_name").equals("")) {
//					mapObjectCollocationShop.put("shop_name","");
//				} else {
//					mapObjectCollocationShop.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
//				}
//				if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")||"null".equals(jk.getString("shop_se_price")) 
//						|| jk.getString("shop_se_price").equals("")) {
//					mapObjectCollocationShop.put("shop_se_price", "0");
//				} else {
//					mapObjectCollocationShop.put("shop_se_price",
//							jk.has("shop_se_price") ? jk.optString("shop_se_price") : "");
//				}
//				if (!jk.has("option_flag") || null == jk.getString("option_flag")
//						|| jk.getString("option_flag").equals("")) {
//					mapObjectCollocationShop.put("option_flag","");
//				} else {
//					mapObjectCollocationShop.put("option_flag", jk.has("option_flag") ? jk.optString("option_flag") : "");
//				}
//				if (!jk.has("shop_x") || null == jk.getString("shop_x")
//						|| jk.getString("shop_x").equals("")) {
//					mapObjectCollocationShop.put("shop_x", "");
//				} else {
//					mapObjectCollocationShop.put("shop_x", jk.has("shop_x") ? jk.optString("shop_x") : "");
//				}
//				if (!jk.has("shop_y") || null == jk.getString("shop_y")
//						|| jk.getString("shop_y").equals("")) {
//					mapObjectCollocationShop.put("shop_y","");
//				} else {
//					mapObjectCollocationShop.put("shop_y", jk.has("shop_y") ? jk.optString("shop_y") : "");
//				}
//				listCollocationShop.add(mapObjectCollocationShop);
//			}
//			mapObject.put("collocation_shop", listCollocationShop);
//
//			List<HashMap<String, Object>> listShopType = new ArrayList<HashMap<String, Object>>();
//			JSONArray jsonArrayShopType = jo.getJSONArray("shop_type_list");
//			for (int m = 0; m < jsonArrayShopType.length(); m++) {
//
//				JSONObject jsonObjectShopType2 = jsonArrayShopType.getJSONObject(m);
//
//				// mapObjectShopType2.put("type_id",
//				// jsonObjectShopType2.has("type_id") ?
//				// jsonObjectShopType2.optString("type_id"): "");
//
//				JSONArray jsonArrayShopType2 = jsonObjectShopType2.getJSONArray("list");
//				for (int k = 0; k < jsonArrayShopType2.length(); k++) {
//					JSONObject jk = (JSONObject) jsonArrayShopType2.opt(k);
//					HashMap<String, Object> mapObjectShopType2 = new HashMap<String, Object>();
//					if (!jk.has("shop_code") || null == jk.getString("shop_code")
//							|| jk.getString("shop_code").equals("")) {
//						mapObjectShopType2.put("shop_code", "");
//					} else {
//						mapObjectShopType2.put("shop_code", jk.has("shop_code") ? jk.optString("shop_code") : "");
//					}
//					if (!jk.has("shop_name") || null == jk.getString("shop_name")
//							|| jk.getString("shop_name").equals("")) {
//						mapObjectShopType2.put("shop_name","");
//					} else {
//						mapObjectShopType2.put("shop_name", jk.has("shop_name") ? jk.optString("shop_name") : "");
//					}
//					if (!jk.has("shop_price") || null == jk.getString("shop_price")||"null".equals(jk.getString("shop_price"))
//							|| jk.getString("shop_price").equals("")) {
//						mapObjectShopType2.put("shop_price","0");
//					} else {
//						mapObjectShopType2.put("shop_price", jk.has("shop_price") ? jk.optString("shop_price") : "");
//					}
//					if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price")||"null".equals(jk.getString("shop_se_price"))
//							|| jk.getString("shop_se_price").equals("")) {
//						mapObjectShopType2.put("shop_se_price", "0");
//					} else {
//						mapObjectShopType2.put("shop_se_price",
//								jk.has("shop_se_price") ? jk.optString("shop_se_price") : "");
//					}
//					if (!jk.has("type1") || null == jk.getString("type1")
//							|| jk.getString("type1").equals("")) {
//						mapObjectShopType2.put("type1", "");
//					} else {
//						mapObjectShopType2.put("type1", jk.has("type1") ? jk.optString("type1") : "");
//					}
//					if (!jk.has("type2") || null == jk.getString("type2")
//							|| jk.getString("type2").equals("")) {
//						mapObjectShopType2.put("type2","");
//					} else {
//						mapObjectShopType2.put("type2", jk.has("type2") ? jk.optString("type2") : "");
//					}
//					if (!jk.has("def_pic") || null == jk.getString("def_pic")
//							|| jk.getString("def_pic").equals("")) {
//						mapObjectShopType2.put("def_pic", "");
//					} else {
//						mapObjectShopType2.put("def_pic", jk.has("def_pic") ? jk.optString("def_pic") : "");
//					}
//					if (!jk.has("type_id") || null == jk.getString("type_id")
//							|| jk.getString("type_id").equals("")) {
//						mapObjectShopType2.put("type_id", "");
//					} else {
//						mapObjectShopType2.put("type_id",
//								jsonObjectShopType2.has("type_id") ? jsonObjectShopType2.optString("type_id") : "");
//					}
//					listShopType.add(mapObjectShopType2);
//				}
//			}
//			mapObject.put("shop_type_list", listShopType);
//
//			retInfo.add(mapObject);
//
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 搭配详情
//	 */
//	// public static final List<HashMap<String, Object>> createMatchDetails(
//	// Context context, String result) throws JSONException {
//	// List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// if(!"1".equals(j.getString("status"))){
//	// return null;
//	// }
//	//
//	// JSONObject jo = new JSONObject("shop");
//	// if (null == jo || "".equals(jo)) {
//	// return null;
//	// }
//	// HashMap<String, Object> shopHashMap = new HashMap<String, Object>();
//	// shopHashMap.put("collocation_name", jo.has("collocation_name") ?
//	// jo.optString("collocation_name"): "");
//	// shopHashMap.put("collocation_code", jo.has("collocation_code") ?
//	// jo.optString("collocation_code"): "");
//	// shopHashMap.put("collocation_pic", jo.has("collocation_pic") ?
//	// jo.optString("collocation_pic"): "");
//	// shopHashMap.put("add_time", jo.has("add_time") ?
//	// jo.optString("add_time"): "");
//	// shopHashMap.put("collocation_remark", jo.has("collocation_remark") ?
//	// jo.optString("collocation_remark"): "");
//	// shopHashMap.put("type_relation_ids", jo.has("type_relation_ids") ?
//	// jo.optString("type_relation_ids"): "");
//	//
//	// JSONArray ja =jo.getJSONArray("collocation_shop");
//	// List<HashMap<String, Object>> listCollocationShop = new
//	// ArrayList<HashMap<String, Object>>();
//	// for (int k = 0; k < ja.length();k++) {
//	// JSONObject jk = (JSONObject) ja.opt(k);
//	// HashMap<String, Object> mapObjectCollocationShop = new HashMap<String,
//	// Object>();
//	// mapObjectCollocationShop.put("shop_code", jk.has("shop_code") ?
//	// jk.optString("shop_code"): "");
//	// mapObjectCollocationShop.put("shop_name", jk.has("shop_name") ?
//	// jk.optString("shop_name"): "");
//	// mapObjectCollocationShop.put("shop_se_price", jk.has("shop_se_price") ?
//	// jk.optString("shop_se_price"): "");
//	// mapObjectCollocationShop.put("option_flag", jk.has("option_flag") ?
//	// jk.optString("option_flag"): "");
//	// mapObjectCollocationShop.put("shop_x", jk.has("shop_x") ?
//	// jk.optString("shop_x"): "");
//	// mapObjectCollocationShop.put("shop_y", jk.has("shop_y") ?
//	// jk.optString("shop_y"): "");
//	// mapObjectCollocationShop.put("seq", jk.has("seq") ? jk.optString("seq"):
//	// "");
//	// listCollocationShop.add(mapObjectCollocationShop);
//	// }
//	// shopHashMap.put("collocation_shop", listCollocationShop);
//	//
//	// retInfo.add(shopHashMap);
//	// return retInfo;
//	// }
//	/**
//	 * 搭配详情
//	 */
//	public static final MatchShop createMatchDetails(Context context, String result) throws JSONException {
//		MatchShop mShop = new MatchShop();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (!"1".equals(j.getString("status"))) {
//			return null;
//		}
//
//		JSONObject jo = j.getJSONObject("shop");
//		if (null == jo || "".equals(jo)) {
//			return null;
//		}
//		if (!jo.has("collocation_name") || null == jo.getString("collocation_name") 
//				|| jo.getString("collocation_name").equals("")) {
//			mShop.setCollocation_name("");
//		} else {
//			mShop.setCollocation_name(jo.optString("collocation_name"));
//		}
//		if (!jo.has("collocation_code") || null == jo.getString("collocation_code") 
//				|| jo.getString("collocation_code").equals("")) {
//			mShop.setCollocation_code("");
//		} else {
//			mShop.setCollocation_code(jo.optString("collocation_code"));
//		}
//		if (!jo.has("collocation_pic") || null == jo.getString("collocation_pic") 
//				|| jo.getString("collocation_pic").equals("")) {
//			mShop.setCollocation_pic("");
//		} else {
//			mShop.setCollocation_pic(jo.optString("collocation_pic"));
//		}
//		if (!jo.has("add_time") || null == jo.getString("add_time") 
//				|| jo.getString("add_time").equals("")) {
//			mShop.setAdd_time("0");
//		} else {
//			mShop.setAdd_time(jo.optString("add_time"));
//		}
//		if (!jo.has("collocation_remark") || null == jo.getString("collocation_remark") 
//				|| jo.getString("collocation_remark").equals("")) {
//			mShop.setCollocation_remark("");
//		} else {
//			mShop.setCollocation_remark(jo.optString("collocation_remark"));
//		}
//		if (!jo.has("type_relation_ids") || null == jo.getString("type_relation_ids") 
//				|| jo.getString("type_relation_ids").equals("")) {
//			mShop.setType_relation_ids("");
//		} else {
//			mShop.setType_relation_ids(jo.optString("type_relation_ids"));
//		}
//		JSONArray ja = jo.getJSONArray("collocation_shop");
//		List<CollocationShop> collocation_shop_list = new ArrayList<MatchShop.CollocationShop>();
//		for (int k = 0; k < ja.length(); k++) {
//			JSONObject jk = (JSONObject) ja.opt(k);
//			CollocationShop collocation_shop = new CollocationShop();
//
//			if (!jk.has("type_relation_ids") || null == jk.getString("type_relation_ids") 
//					|| jk.getString("type_relation_ids").equals("")) {
//				collocation_shop.setShop_code("");
//			} else {
//				collocation_shop.setShop_code(jk.optString("shop_code"));
//			}
//			if (!jk.has("shop_name") || null == jk.getString("shop_name") 
//					|| jk.getString("shop_name").equals("")) {
//				collocation_shop.setShop_name("");
//			} else {
//				collocation_shop.setShop_name(jk.optString("shop_name"));
//			}
//			if (!jk.has("shop_se_price") || null == jk.getString("shop_se_price") 
//					|| jk.getString("shop_se_price").equals("")) {
//				collocation_shop.setShop_se_price(0);
//			} else {
//				collocation_shop.setShop_se_price(jk.optDouble("shop_se_price", 0));
//			}
//			if (!jk.has("option_flag") || null == jk.getString("option_flag") 
//					|| jk.getString("option_flag").equals("")) {
//				collocation_shop.setOption_flag(1);
//			} else {
//				collocation_shop.setOption_flag(jk.optInt("option_flag", 1));
//			}
//			if (!jk.has("shop_x") || null == jk.getString("shop_x") 
//					|| jk.getString("shop_x").equals("")) {
//				collocation_shop.setShop_x(0);
//			} else {
//				collocation_shop.setShop_x(jk.optDouble("shop_x", 0));
//			}
//			if (!jk.has("shop_y") || null == jk.getString("shop_y") 
//					|| jk.getString("shop_y").equals("")) {
//				collocation_shop.setShop_y(0);
//			} else {
//				collocation_shop.setShop_y(jk.optDouble("shop_y", 0));
//			}
//			if (!jk.has("seq") || null == jk.getString("seq") 
//					|| jk.getString("seq").equals("")) {
//				collocation_shop.setSeq(0);
//			} else {
//				collocation_shop.setSeq(jk.has("seq")?jk.optInt("seq"):0);
//			}
//			if (!jk.has("kickback") || null == jk.getString("kickback") ||jk.getString("kickback").equals("null")
//					|| jk.getString("kickback").equals("")) {
//				collocation_shop.setKickback(0);
//			} else {
//				collocation_shop.setKickback(jk.has("kickback")?jk.optDouble("kickback",0):0);
//			}
//			collocation_shop_list.add(collocation_shop);
//		}
//		mShop.setCollocation_shop(collocation_shop_list);
//
//		JSONArray ja2 = jo.getJSONArray("attrList");
//		List<MatchShop.AttrList> attrList_list = new ArrayList<MatchShop.AttrList>();
//		for (int m = 0; m < ja2.length(); m++) {
//			JSONObject jm = (JSONObject) ja2.opt(m);
//			AttrList attrList = new AttrList();
//			
//			if (!jm.has("attr_name") || null == jm.getString("attr_name") 
//					|| jm.getString("attr_name").equals("")) {
//				attrList.setAttr_name("");
//			} else {
//				attrList.setAttr_name(jm.optString("attr_name"));
//			}
//			if (!jm.has("is_show") || null == jm.getString("is_show") 
//					|| jm.getString("is_show").equals("")) {
//				attrList.setIs_show("");
//			} else {
//				attrList.setIs_show(jm.optString("is_show"));
//			}
//			if (!jm.has("ico") || null == jm.getString("ico") 
//					|| jm.getString("ico").equals("")) {
//				attrList.setIco("");
//			} else {
//				attrList.setIco(jm.optString("ico"));
//			}
//			if (!jm.has("color_type") || null == jm.getString("color_type") 
//					|| jm.getString("color_type").equals("")) {
//				attrList.setColor_type("");
//			} else {
//				attrList.setColor_type(jm.optString("color_type"));
//			}
//			if (!jm.has("id") || null == jm.getString("id") 
//					|| jm.getString("id").equals("")) {
//				attrList.setId("");
//			} else {
//				attrList.setId(jm.optString("id"));
//			}
//			if (!jm.has("parent_id") || null == jm.getString("parent_id") 
//					|| jm.getString("parent_id").equals("")) {
//				attrList.setParent_id("");
//			} else {
//				attrList.setParent_id(jm.optString("parent_id"));
//			}
//			attrList_list.add(attrList);
//		}
//		mShop.setAttrList(attrList_list);
//
//		return mShop;
//	}
//
//	/**
//	 * 搭配详情 相关商品
//	 */
//	public static final List<HashMap<String, Object>> createMatchDetailsRec(Context context, String result)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (!"1".equals(j.getString("status"))) {
//			return null;
//		}
//
//		JSONArray ja = j.getJSONArray("listShop");
//
//		List<HashMap<String, Object>> listShop = new ArrayList<HashMap<String, Object>>();
//
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//			HashMap<String, Object> mapList = new HashMap<String, Object>();
//			if (!jo.has("shop_code") || null == jo.getString("shop_code") 
//					|| jo.getString("shop_code").equals("")) {
//				mapList.put("shop_code","");
//			} else {
//				mapList.put("shop_code", jo.has("shop_code") ? jo.optString("shop_code") : "");
//			}
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") 
//					|| jo.getString("shop_name").equals("")) {
//				mapList.put("shop_name", "");
//			} else {
//				mapList.put("shop_name", jo.has("shop_name") ? jo.optString("shop_name") : "");
//			}
//			
//			if (!jo.has("shop_price") || null == jo.getString("shop_price") ||jo.getString("shop_price").equals("null")
//					|| jo.getString("shop_price").equals("")) {
//				mapList.put("shop_price", "0");
//			} else {
//				mapList.put("shop_price", jo.has("shop_price") ? jo.optString("shop_price") : "0");
//			}
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") ||jo.getString("shop_se_price").equals("null")
//					|| jo.getString("shop_se_price").equals("")) {
//				mapList.put("shop_se_price","0");
//			} else {
//				mapList.put("shop_se_price", jo.has("shop_se_price") ? jo.optString("shop_se_price") : "0");
//			}
//			if (!jo.has("kickback") || null == jo.getString("kickback") ||jo.getString("kickback").equals("null")
//					|| jo.getString("kickback").equals("")) {
//				mapList.put("kickback", "0");
//			} else {
//				mapList.put("kickback", jo.has("kickback") ? jo.optString("kickback") : "0");
//			}
//			if (!jo.has("def_pic") || null == jo.getString("def_pic") ||jo.getString("def_pic").equals("null")
//					|| jo.getString("def_pic").equals("")) {
//				mapList.put("def_pic", "");
//			} else {
//				mapList.put("def_pic", jo.has("def_pic") ? jo.optString("def_pic") : "");
//			}
//			listShop.add(mapList);
//		}
//		return listShop;
//	}
//
//	/**
//	 * 搭配商品 属性选择
//	 */
//	public static final MatchAttr createMatchAttr(Context context, String result) throws JSONException {//ZZQ
//		JSONObject j = new JSONObject(result);
//		MatchAttr matchAttr = new MatchAttr();
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (!"1".equals(j.getString("status"))) {
//			return null;
//		}
//
//		JSONObject jo = j.getJSONObject("shop_attr");
//		ShopAttrBean shopAttrBean = new ShopAttrBean();
//		HashMap<String, String> shopAttrHashMap = new HashMap<String, String>();
//
//		matchAttr.setShop_attr(shopAttrBean);
//
//		JSONArray ja = j.getJSONArray("stocktype");
//		List<StocktypeBean> stocktypeList = new ArrayList<MatchAttr.StocktypeBean>();
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject ji = (JSONObject) ja.opt(i);
//			StocktypeBean stocktypeBean = new StocktypeBean();
//			if (!ji.has("shop_code") || null == ji.getString("shop_code") 
//					|| ji.getString("shop_code").equals("")) {
//				stocktypeBean.setShop_code("");
//			} else {
//				stocktypeBean.setShop_code(ji.getString("shop_code"));
//			}
//			shopAttrHashMap.put(ji.getString("shop_code"), jo.optString(ji.getString("shop_code")));
//			shopAttrBean.setShopAttrHashMap(shopAttrHashMap);
//			if (!ji.has("original_price") || null == ji.getString("original_price") ||ji.getString("original_price").equals("null")
//					|| ji.getString("original_price").equals("")) {
//				stocktypeBean.setOriginal_price("0");
//			} else {
//				stocktypeBean.setOriginal_price(ji.getString("original_price"));
//			}
//			if (!ji.has("pic") || null == ji.getString("pic") 
//					|| ji.getString("pic").equals("")) {
//				stocktypeBean.setPic("");
//			} else {
//				stocktypeBean.setPic(ji.getString("pic"));
//			}
//			if (!ji.has("kickback") || null == ji.getString("kickback") ||ji.getString("kickback").equals("null")
//					|| ji.getString("kickback").equals("")) {
//				stocktypeBean.setKickback("");
//			} else {
//				stocktypeBean.setKickback(ji.getString("kickback"));
//			}
//			if (!ji.has("shop_name") || null == ji.getString("shop_name") 
//					|| ji.getString("shop_name").equals("")) {
//				stocktypeBean.setShop_name("");
//			} else {
//				stocktypeBean.setShop_name(ji.getString("shop_name"));
//			}
//			if (!ji.has("id") || null == ji.getString("id") 
//					|| ji.getString("id").equals("")) {
//				stocktypeBean.setId("");
//			} else {
//				stocktypeBean.setId(ji.getString("id"));
//			}
//			if (!ji.has("stock") || null == ji.getString("stock") 
//					|| ji.getString("stock").equals("")) {
//				stocktypeBean.setStock("");
//			} else {
//				stocktypeBean.setStock(ji.getString("stock"));
//			}
//			if (!ji.has("color_size") || null == ji.getString("color_size") 
//					|| ji.getString("color_size").equals("")) {
//				stocktypeBean.setColor_size("");
//			} else {
//				stocktypeBean.setColor_size(ji.getString("color_size"));
//			}
//			if (!ji.has("price") || null == ji.getString("price") ||ji.getString("price").equals("null")
//					|| ji.getString("price").equals("")) {
//				stocktypeBean.setPrice("0");
//			} else {
//				stocktypeBean.setPrice(ji.getString("price"));
//			}
//			if (!ji.has("shop_price") || null == ji.getString("shop_price") ||ji.getString("shop_price").equals("null")
//					|| ji.getString("shop_price").equals("")) {
//				stocktypeBean.setShop_price("");
//			} else {
//				stocktypeBean.setShop_price(ji.getString("shop_price"));
//			}
//			if (!ji.has("supp_id") || null == ji.getString("supp_id")
//					|| ji.getString("supp_id").equals("")) {
//				stocktypeBean.setSupp_id("");
//			} else {
//				stocktypeBean.setSupp_id(ji.getString("supp_id"));
//			}
//			if (!ji.has("three_kickback") || null == ji.getString("three_kickback")
//					|| ji.getString("three_kickback").equals("")) {
//				stocktypeBean.setThree_kickback("");
//			} else {
//				stocktypeBean.setThree_kickback(ji.getString("three_kickback"));
//			}
//			if (!ji.has("two_kickback") || null == ji.getString("two_kickback")
//					|| ji.getString("two_kickback").equals("")) {
//				stocktypeBean.setTwo_kickback("");
//			} else {
//				stocktypeBean.setTwo_kickback(ji.getString("two_kickback"));
//			}
//			stocktypeList.add(stocktypeBean);
//		}
//		matchAttr.setStocktype(stocktypeList);
//
//		return matchAttr;
//	}
//
//	/**
//	 * 积分商城商品列表
//	 */
//	public static final List<HashMap<String, Object>> createIntegralProd(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("listShop"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("def_pic") || null == jo.getString("def_pic")
//					|| jo.getString("def_pic").equals("")) {
//				mapObject.put("def_pic", "");
//			} else {
//				mapObject.put("def_pic", jo.getString("def_pic"));
//			}
//			if (!jo.has("shop_code") || null == jo.getString("shop_code")
//					|| jo.getString("shop_code").equals("")) {
//				mapObject.put("shop_code", "");
//			} else {
//				mapObject.put("shop_code", jo.getString("shop_code"));
//			}
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")||jo.getString("shop_se_price").equals("null")
//					|| jo.getString("shop_se_price").equals("")) {
//				mapObject.put("shop_se_price","0");
//			} else {
//				mapObject.put("shop_se_price", jo.getString("shop_se_price"));
//			}
//			if (!jo.has("id") || null == jo.getString("id")
//					|| jo.getString("id").equals("")) {
//				mapObject.put("id", "");
//			} else {
//				mapObject.put("id", jo.getString("id"));
//			}
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//	/***
//	 * 得到商品详情
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final Shop createShop(Context context, String result) throws JSONException {
//		// result = result.replace("null", "\"\"");
//
//		YDBHelper helper = new YDBHelper(context);
//		Shop shop = new Shop();
//		JSONObject j = new JSONObject(result);
//		// //////
//		// JSONObject jAttr1 = new JSONObject(j.optString("share_shop"));
//		// ShareShop shareShop = new ShareShop();
//		// shareShop.setCount(jAttr1.optLong("count"));
//		// System.out.println("这里是share_shop="+jAttr1);
//
//		// shop.setCount(jAttr1.optInt("count"));
//		// /////
//
//		if (!j.has("id") || null == j.getString("id")||j.getString("id").equals("null")
//				|| j.getString("id").equals("")) {
//			shop.setKickback(0);
//		} else {
//			shop.setKickback(j.optDouble("kickback"));
//		}
//		if (!j.has("color_count") || null == j.getString("color_count")||j.getString("color_count").equals("null")
//				|| j.getString("color_count").equals("")) {
//			shop.setColor_count(0);
//		} else {
//			shop.setColor_count((float) j.optInt("color_count", 0));
//		}
//		if (!j.has("type_count") || null == j.getString("type_count")||j.getString("type_count").equals("null")
//				|| j.getString("type_count").equals("")) {
//			shop.setType_count(0);
//		} else {
//			shop.setType_count((float) j.optInt("type_count", 0));
//		}
//		if (!j.has("work_count") || null == j.getString("work_count")||j.getString("work_count").equals("null")
//				|| j.getString("work_count").equals("")) {
//			shop.setWork_count(0);
//		} else {
//			shop.setWork_count((float) j.optInt("work_count", 0));
//		}
//		if (!j.has("cost_count") || null == j.getString("cost_count")||j.getString("cost_count").equals("null")
//				|| j.getString("cost_count").equals("")) {
//			shop.setCost_count(0);
//		} else {
//			shop.setCost_count((float) j.optInt("cost_count", 0));
//		}
//		if (!j.has("star_count") || null == j.getString("star_count")||j.getString("star_count").equals("null")
//				|| j.getString("star_count").equals("")) {
//			shop.setStar_count(0);
//		} else {
//			shop.setStar_count(j.optDouble("star_count"));
//		}
//		if (!j.has("praise_count") || null == j.getString("praise_count")||j.getString("praise_count").equals("null")
//				|| j.getString("praise_count").equals("")) {
//			shop.setPraise_count(0); // 好评数 Integer
//		} else {
//			shop.setPraise_count(j.optInt("praise_count")); // 好评数 Integer
//		}
//		if (!j.has("med_count") || null == j.getString("med_count")||j.getString("med_count").equals("null")
//				|| j.getString("med_count").equals("")) {
//			shop.setMed_count(0); // 中评数 Integer
//		} else {
//			shop.setMed_count(j.optInt("med_count")); // 中评数 Integer
//		}
//		if (!j.has("bad_count") || null == j.getString("bad_count")||j.getString("bad_count").equals("null")
//				|| j.getString("bad_count").equals("")) {
//			shop.setBad_count(0); // 差评数 Integer
//		} else {
//			shop.setBad_count(j.optInt("bad_count")); // 差评数 Integer
//		}
//		if (!j.has("eva_count") || null == j.getString("eva_count")||j.getString("eva_count").equals("null")
//				|| j.getString("eva_count").equals("")) {
//			shop.setEva_count(0); // 评价总数 Integer
//		} else {
//			shop.setEva_count((float) j.optInt("eva_count", 0)); // 评价总数 Integer
//		}
//		// /
//		if (YJApplication.instance.isLoginSucess()) {
//			// shop.setS_time(j.optLong("s_time"));
//			if (!j.has("c_time") || null == j.getString("c_time")||j.getString("c_time").equals("null")
//					|| j.getString("c_time").equals("")) {
//				shop.setC_time(0);
//			} else {
//				shop.setC_time(j.optLong("c_time"));
//			}
//		}
//		if (!j.has("s_time") || null == j.getString("s_time")||j.getString("s_time").equals("null")
//				|| j.getString("s_time").equals("")) {
//			shop.setS_time(0);
//		} else {
//			shop.setS_time(j.optLong("s_time"));
//		}
//		// /
//
//		shop.setLike_id(("-1".equals(j.optString("like_id")) || !j.has("like_id")) ? -1 : 1); // 评价总数
//																								// Integer
//		if (!j.has("cart_count") || null == j.getString("cart_count")||j.getString("cart_count").equals("null")
//				|| j.getString("cart_count").equals("")) {
//			shop.setCart_count(0);// 购物车数量
//		} else {
//			shop.setCart_count(j.optInt("cart_count"));// 购物车数量
//		}
//		JSONObject obj = j.getJSONObject("shop");
//		// ///
//		if (!obj.has("audit_time") || null == obj.getString("audit_time")||obj.getString("audit_time").equals("null")
//				|| obj.getString("audit_time").equals("")) {
//			shop.setAudit_time("0"); // 七天倒计时
//		} else {
//			shop.setAudit_time(obj.has("audit_time") ? obj.optString("audit_time") : "0"); // 七天倒计时
//		}
//		// ///
//		if (!obj.has("supp_id") || null == obj.getString("supp_id")
//				|| obj.getString("supp_id").equals("")) {
//			shop.setSupp_id(0);
//		} else {
//			shop.setSupp_id(obj.optInt("supp_id"));
//		}
//		if (!obj.has("shop_se_price") || null == obj.getString("shop_se_price")||obj.getString("shop_se_price").equals("null")
//				|| obj.getString("shop_se_price").equals("")) {
//			shop.setShop_se_price(0);
//		} else {
//			shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
//		}
//		if (!obj.has("shop_price") || null == obj.getString("shop_price")||obj.getString("shop_price").equals("null")
//				|| obj.getString("shop_price").equals("")) {
//			shop.setShop_price(0);
//		} else {
//			shop.setShop_price(obj.optDouble("shop_price", 0));
//		}
//		if (!obj.has("shop_code") || null == obj.getString("shop_code")
//				|| obj.getString("shop_code").equals("")) {
//			shop.setShop_code("wu");
//		} else {
//			shop.setShop_code(obj.optString("shop_code", "wu"));
//		}
//		if (!obj.has("collocation_code") || null == obj.getString("collocation_code")
//				|| obj.getString("collocation_code").equals("")) {
//			shop.setCollocation_code("");
//		} else {
//			shop.setCollocation_code(obj.optString("collocation_code", ""));
//		}
//		if (!obj.has("core") || null == obj.getString("core")
//				|| obj.getString("core").equals("")) {
//			shop.setCore(0);
//		} else {
//			shop.setCore(obj.optInt("core"));
//		}
//		if (!obj.has("remark") || null == obj.getString("remark")
//				|| obj.getString("remark").equals("")) {
//			shop.setRemark("wu");
//		} else {
//			shop.setRemark(obj.optString("remark", "wu"));
//		}
//		if (!obj.has("def_pic") || null == obj.getString("def_pic")
//				|| obj.getString("def_pic").equals("")) {
//			shop.setDef_pic("wu");
//		} else {
//			shop.setDef_pic(obj.optString("def_pic", "wu"));
//		}
//		shop.setRandom(obj.optDouble("random", 0));
//		shop.setKickback(obj.optDouble("kickback", 0));
//		shop.setShop_up_time(obj.optLong("shop_up_time", 0));
//		shop.setInvertory_num(obj.optInt("invertory_num", 0));
//		shop.setShop_name(obj.optString("shop_name", "wu"));
//		shop.setId(obj.optInt("id", 0));
//		shop.setContent(obj.optString("content", "wu"));
//		shop.setActual_sales(obj.optInt("actual_sales", 0));
//		shop.setIs_new(obj.optString("is_new", "wu"));
//		shop.setShop_pic(obj.optString("shop_pic", "wu"));
//		shop.setClicks(obj.optInt("clicks", 0));
//		shop.setIs_hot(obj.optString("is_hot", "wu"));
//		shop.setVirtual_sales(obj.optInt("virtual_sales", 0));
//		shop.setLove_num(obj.optInt("love_num", 0));
//		shop.setShop_tag(obj.optString("shop_tag"));
//		String ss = obj.optString("shop_type_id");
//		if (!TextUtils.isEmpty(ss)) {
//			ss = ss.replace("[", "");
//			ss = ss.replace("]", "");
//			String[] shop_type_id = ss.split(",");
//			shop.setShop_type_id(shop_type_id);
//		}
//
//		shop.setType1(obj.optInt("type1"));
//
//		String text = obj.getString("shop_attr");
//
//		List<String[]> liststr = new ArrayList<String[]>();
//		String str[] = text.split("_");
//		for (int i = 0; i < str.length; i++) {
//			String strson[] = str[i].split(",");
//			int length = strson.length;
//			String s[] = new String[length];
//			for (int d = 0; d < strson.length; d++) {
//				s[d] = strson[d];
//
//			}
//			liststr.add(strson);
//		}
//		shop.setShop_attr(liststr);
//
//		// 属性数据
//		List<String> sqls = new ArrayList<String>();
//		JSONArray jAttr = new JSONArray(j.optString("attrList"));
//		if (null != jAttr && jAttr.length() > 0) {
//			helper.delete("delete from attr_info");
//			for (int i = 0; i < jAttr.length(); i++) {
//				ShopAttr sAttr = JSON.parseObject(jAttr.getString(i), ShopAttr.class);
//				String sql = "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() + "','"
//						+ sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" + sAttr.getParent_id() + "','"
//						+ sAttr.getIs_show() + "')";
//				sqls.add(sql);
//
//			}
//			helper.update(sqls);
//		}
//		// System.out.println("112233 shop=" + shop);
//		return shop;
//	}
//
//	// TODO ZZQ
//	/**
//	 * shareshop查询的解析
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final ShareShop createShopShareshop(Context context, String result) throws JSONException {
//
//		ShareShop shareshop = new ShareShop();
//		JSONObject j = new JSONObject(result);
//		if (null == j || j.equals("")) {
//			return null;
//		}
//
//		String jj = j.getString("share_shop");
//
//		LogYiFu.e("jj", jj + "");
//
//		if (jj.equals("null") || jj == null) {
//			return null;
//		} else {
//
//			JSONObject j1 = new JSONObject(j.optString("share_shop"));
//
//			shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);
//
//			JSONArray jArray = new JSONArray(j1.has("user_list") ? j1.optString("user_list") : null);
//
//			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//
//			for (int i = 0; i < jArray.length(); i++) {
//				HashMap<String, Object> hashMap = new HashMap<String, Object>();
//				JSONObject object = (JSONObject) jArray.get(i);
//				Object object2 = object.getString("pic");
//				// shareshop.setUser_list(null);
//				hashMap.put("pic", object2);
//				list.add(hashMap);
//			}
//			shareshop.setUser_list(list);
//			// System.out.println("看看这个shareshop=" + shareshop);
//			return shareshop;
//		}
//
//	}
//
//	public static HashMap<String, Object> createShopMeal(Context context, String result) throws JSONException {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//
//		JSONObject json = new JSONObject(result);
//
//		YDBHelper helper = new YDBHelper(context);
//
//		JSONObject pShop = json.optJSONObject("pShop");
//		if (pShop != null) {
//			map.put("name", pShop.optString("name", "套餐"));
//			map.put("price", pShop.optString("price", "0"));
//			map.put("num", pShop.optString("num", "0"));// 套餐总数
//			map.put("seq", pShop.optString("seq"));
//			map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
//			map.put("postage", pShop.optString("postage", "0"));
//			map.put("p_type", pShop.optString("type"));
//		}
//
//		map.put("color_count", json.optString("color_count", "0"));
//		map.put("work_count", json.optString("work_count", "0"));
//		map.put("star_count", json.optString("star_count", "5.0"));
//		map.put("type_count", json.optString("type_count", "0"));
//		map.put("eva_count", json.optString("eva_count", "0"));
//		map.put("cost_count", json.optString("cost_count", "0"));
//		map.put("cart_count", json.optString("cart_count", "0"));
//
//		if (YJApplication.instance.isLoginSucess()) {
//			map.put("c_time", json.has("c_time") ? json.getLong("c_time") : 0);
//			map.put("s_time", json.has("s_time") ? json.getLong("s_time") : 0);
//		}
//
//		JSONArray shopList = json.optJSONArray("shopList");
//
//		if (shopList != null && shopList.length() > 0) {
//
//			List<Shop> list = new ArrayList<Shop>();
//
//			for (int i = 0; i < shopList.length(); i++) {
//				JSONObject obj = shopList.optJSONObject(i);
//				Shop shop = new Shop();
//				shop.setShop_pic(obj.optString("shop_pic"));
//				shop.setShop_code(obj.optString("shop_code"));
//				shop.setShop_price(obj.optDouble("shop_price", 0));
//				shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
//				shop.setDef_pic(obj.optString("def_pic", ""));
//				shop.setSupp_id(obj.optInt("supp_id", 0));
//				shop.setShop_name(obj.optString("shop_name", ""));
//				/**
//				 * shop.setAge(obj.optInt("age",0));
//				 * shop.setStuff(obj.optInt("stuff",0));
//				 * shop.setStuff2(obj.optInt("stuff2",0));
//				 * shop.setFix_price(obj.optInt("fix_price",0));
//				 * shop.setOccasion(obj.optInt("occasion",0));
//				 * shop.setFavorite(obj.optInt("favorite",0));
//				 * shop.setStuff3(obj.optInt("stuff3",0));
//				 * shop.setStuff4(obj.optInt("stuff4",0));
//				 * shop.setSys_color(obj.optInt("sys_color",0));
//				 * shop.setPattern(obj.optInt("pattern",0));
//				 * shop.setTrait(obj.optInt("trait",0));
//				 * shop.setTrait2(obj.optInt("trait2",0));
//				 * shop.setTrait3(obj.optInt("trait3",0));
//				 * shop.setStyle(obj.optInt("style",0));
//				 */
//
//				shop.setTrait(obj.optInt("age", 0) + "," + obj.optInt("stuff", 0) + "," + obj.optInt("stuff2", 0) + ","
//						+ obj.optInt("fix_price", 0) + "," + obj.optInt("occasion", 0) + "," + obj.optInt("favorite", 0)
//						+ "," + obj.optInt("stuff3", 0) + "," + obj.optInt("stuff4", 0) + ","
//						+ obj.optInt("sys_color", 0) + "," + obj.optInt("pattern", 0) + "," + obj.optInt("trait", 0)
//						+ "," + obj.optInt("trait2", 0) + "," + obj.optInt("trait3", 0) + "," + obj.optInt("style", 0));
//
//				String text = obj.getString("shop_attr");
//				List<String[]> liststr = new ArrayList<String[]>();
//				String str[] = text.split("_");
//				for (int j = 0; j < str.length; j++) {
//					String strson[] = str[j].split(",");
//					int length = strson.length;
//					String s[] = new String[length];
//					for (int d = 0; d < strson.length; d++) {
//						s[d] = strson[d];
//					}
//					liststr.add(strson);
//				}
//				shop.setShop_attr(liststr);
//				list.add(shop);
//			}
//			map.put("shopList", list);
//		}
//
//		JSONArray attrList = json.optJSONArray("attrList");
//
//		if (attrList != null && attrList.length() > 0) {
//			List<String> sqls = new ArrayList<String>();
//			helper.delete("delete from attr_info");
//			for (int i = 0; i < attrList.length(); i++) {
//				ShopAttr sAttr = JSON.parseObject(attrList.getString(i), ShopAttr.class);
//				String sql = "insert into attr_info(_id,attr_name,icon,p_id,is_show)values('" + sAttr.getId() + "','"
//						+ sAttr.getAttr_name() + "','" + sAttr.getIco() + "','" + sAttr.getParent_id() + "','"
//						+ sAttr.getIs_show() + "')";
//				sqls.add(sql);
//			}
//			helper.update(sqls);
//		}
//		return map;
//	}
//
//	/***
//	 * 得到评论列表
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<ShopComment> createListShop_comment(Context context, String result) throws JSONException {
//
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("comments");
//		// List<ShopComment> list = JSON.parseArray(text, ShopComment.class);
//		// List<Object> list = JSON.parseObject(text,new
//		// TypeReference<List<Object>>(){});
//		JSONArray jsonArray = new JSONArray(j.getString("comments"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		List<ShopComment> list = new ArrayList<ShopComment>();
//		for (int i = 0; i < jsonArray.length(); i++) {
//			// JSONObject jo = (JSONObject) jsonArray.opt(i);
//			com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject) JSON.parse(jsonArray.getString(i));
//			ShopComment sComment = new ShopComment();
//			sComment.setColor(jo.getInteger("color"));
//			sComment.setAdd_date(jo.getLong("add_date") == null ? 0 : jo.getLong("add_date"));
//			sComment.setComment_type(jo.getInteger("comment_type"));
//			sComment.setContent(jo.getString("content"));
//			sComment.setCost(jo.getInteger("cost"));
//			sComment.setId(jo.getInteger("id"));
//			// sComment.setIs_del(jo.getInteger("is_del"));
//			sComment.setPic(jo.getString("pic"));
//			sComment.setShop_code(jo.getString("shop_code"));
//			sComment.setShop_color(jo.getString("shop_color"));
//			sComment.setShop_name(jo.getString("shop_name"));
//			sComment.setShop_price(jo.getString("shop_price"));
//			sComment.setShop_size(jo.getString("shop_size"));
//			sComment.setStar(jo.getInteger("star"));
//			sComment.setStore_code(jo.getString("store_code"));
//			// sComment.setSupp_add_date(jo.getLong("supp_add_date"));
//			sComment.setSupp_content(jo.getString("supp_content"));
//			// sComment.setSupp_id(jo.getInteger("supp_id"));
//			sComment.setSupp_pic(jo.getString("supp_pic"));
//			// sComment.setSupp_verify_status(supp_verify_status)
//			sComment.setType(jo.getInteger("type"));
//
//			sComment.setUser_id(jo.getInteger("user_id"));
//			sComment.setUser_name(jo.getString("user_name"));
//			sComment.setUser_url(jo.getString("user_url"));
//			// sComment.setVerify_info(jo.getString("verify_info"));
//			// sComment.setVerify_status(jo.getInteger("verify_status"));
//			// sComment.setVerify_time(jo.getLong("verify_time"));
//			// sComment.setVerify_user(jo.getInteger("verify_user"));
//			sComment.setWork(jo.getInteger("work"));
//			// 卖家回复
//			if (null != jo.getString("suppComment")) {
//				JSONArray jASuppComment = new JSONArray(jo.getString("suppComment"));
//				if (null == jASuppComment || "".equals(jASuppComment) || jASuppComment.length() == 0) {
//					sComment.setSuppComment(null);
//				} else {
//					List<SuppComment> suppComments = new ArrayList<SuppComment>();
//					for (int k = 0; k < jASuppComment.length(); k++) {
//						JSONObject joSupp = (JSONObject) jASuppComment.opt(k);
//						SuppComment suppComment = new SuppComment();
//						suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						suppComment.setSupp_content(joSupp.optString("supp_content"));
//						// suppComment.setSupp_pic(joSupp.getString("supp_pic"));
//						// suppComment.setSupp_verify_status(joSupp.getInt("supp_verify_status"));
//						suppComments.add(suppComment);
//					}
//					sComment.setSuppComment(suppComments);
//				}
//			}
//			// 买家追评
//			if (null != jo.getString("comment")) {
//				JSONArray jAComment = new JSONArray(jo.getString("comment"));
//				if (null == jAComment || "".equals(jAComment) || jAComment.length() == 0) {
//					sComment.setComment(null);
//				} else {
//					List<Comment> comments = new ArrayList<Comment>();
//					for (int k = 0; k < jAComment.length(); k++) {
//						JSONObject joComment = (JSONObject) jAComment.opt(k);
//						Comment comment = new Comment();
//						comment.setAdd_date(joComment.optLong("add_date", 0));
//						comment.setContent(joComment.optString("content"));
//						// comment.setId(joComment.getInt("id"));
//						comment.setPic(joComment.optString("pic"));
//						// comment.setVerify_status(joComment.getInt("verify_status"));
//						comments.add(comment);
//					}
//					sComment.setComment(comments);
//				}
//			}
//
//			// 卖家回复追评
//			if (null != jo.getString("suppEndComment")) {
//				JSONArray jASuppReply = new JSONArray(jo.getString("suppEndComment"));
//				if (null == jASuppReply || "".equals(jASuppReply) || jASuppReply.length() == 0) {
//					sComment.setSuppEndComment(null);
//				} else {
//					List<SuppComment> suppReplys = new ArrayList<SuppComment>();
//					for (int k = 0; k < jASuppReply.length(); k++) {
//						JSONObject joSupp = (JSONObject) jASuppReply.opt(k);
//						SuppComment suppComment = new SuppComment();
//						suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						suppComment.setSupp_content(joSupp.optString("supp_content"));
//						// suppComment.setSupp_pic(joSupp.getString("supp_pic"));
//						// suppComment.setSupp_verify_status(joSupp.getInt("supp_verify_status"));
//						suppReplys.add(suppComment);
//					}
//					sComment.setSuppEndComment(suppReplys);
//				}
//
//			}
//			list.add(sComment);
//		}
//
//		return list;
//	}
//
//	/***
//	 * 特卖得到商品购物车
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<ShopCart> createListShop_Cart(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		List<ShopCart> list;
//		long p_deadline = 0;
//		long s_time = 0;
//		int rowCount = 0;
//		if (1 == j.getInt("status")) {
//			String s = j.getString("pager");
//			JSONObject j2 = new JSONObject(s);
//			if (j2.has("rowCount")) {
//				String ss = j2.getString("rowCount");
//				rowCount = Integer.parseInt(ss);
//			}
//			if (j.has("p_deadline")) {
//				String s1 = j.getString("p_deadline");
//				p_deadline = Long.parseLong(s1);
//			}
//			if (j.has("s_time")) {
//				String s2 = j.getString("s_time");
//				s_time = Long.parseLong(s2);
//			}
//			String text = j.getString("listcart");
//			list = JSON.parseArray(text, ShopCart.class);
//			for (int i = 0; i < list.size(); i++) {
//				ShopCart shopCart = list.get(i);
//				shopCart.setP_deadline(p_deadline);
//				shopCart.setS_time(s_time);
//				shopCart.setRowCount(rowCount);
//			}
//		} else
//			return null;
//		return list;
//	}
//
//	/***
//	 * 正价 得到商品购物车
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<List<ShopCart>> createListShop_CartNew(Context context, String result)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		List<List<ShopCart>> list = new ArrayList<List<ShopCart>>();
//		List<ShopCart> inList;
//		long s_deadline = 0;
//		long s_time = 0;
//		// int p_count=0;
//		// int s_count=0;
//		if (1 == j.getInt("status")) {
//			String text = j.getString("listcart");
//			if (j.has("s_deadline")) {
//				String s1 = j.getString("s_deadline");
//				s_deadline = Long.parseLong(s1);
//			}
//			if (j.has("s_time")) {
//				String s2 = j.getString("s_time");
//				s_time = Long.parseLong(s2);
//			}
//			// if(j.has("p_count")){
//			// String s3=j.getString("p_count");
//			// p_count=Integer.parseInt(s3);
//			// LogYiFu.e("zzqyifu", ""+p_count);
//			// }
//			// if(j.has("s_count")){
//			// String s4=j.getString("s_count");
//			// s_count=Integer.parseInt(s4);
//			// LogYiFu.e("zzqyifu", ""+s_count);
//			// }
//			// list = JSON.parseArray(text, ShopCart.class);
//			if (null != j.getString("listcart")) {
//				JSONArray JShopCartList = new JSONArray(j.getString("listcart"));
//				for (int i = 0; i < JShopCartList.length(); i++) {
//					JSONObject joSupp = (JSONObject) JShopCartList.opt(i);
//					if (joSupp.has("shop_list")) {
//						String text2 = joSupp.getString("shop_list");
//						inList = JSON.parseArray(text2, ShopCart.class);
//						for (int k = 0; k < inList.size(); k++) {
//							ShopCart shopCart = inList.get(i);
//							shopCart.setS_deadline(s_deadline);
//							shopCart.setS_time(s_time);
//						}
//						list.add(inList);
//					} else {
//						List<ShopCart> sList = new ArrayList<ShopCart>();
//						String text3 = joSupp.toString();
//						ShopCart shopCart = JSON.parseObject(text3, ShopCart.class);
//						shopCart.setS_deadline(s_deadline);
//						shopCart.setS_time(s_time);
//						// shopCart.setP_count(p_count);
//						// shopCart.setS_count(s_count);
//						sList.add(shopCart);
//						inList = sList;
//						list.add(inList);
//					}
//				}
//			}
//		} else {
//			return null;
//		}
//		return list;
//	}
//
//	/**
//	 * 得到特卖商品购物车
//	 */
//	/*
//	 * public static final List<ShopCartSpecial>
//	 * createListShop_CartSpecial(Context context, String result) throws
//	 * JSONException { JSONObject j = new JSONObject(result);
//	 * List<ShopCartSpecial> list; if (1 == j.getInt("status")) { String text =
//	 * j.getString("listcart"); list = JSON.parseArray(text,
//	 * ShopCartSpecial.class); } else return null; return list; }
//	 */
//	/***
//	 * 得到邮寄地址
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<DeliveryAddress> createDeliverAddrList(Context context, String result)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("listdt");
//		List<DeliveryAddress> list = JSON.parseArray(text, DeliveryAddress.class);
//
//		return list;
//	}
//
//	/***
//	 * 得到默认邮寄地址
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static HashMap<String, String> createDefaultDeliverAddrList(Context context, String result)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("address");
//		HashMap<String, String> mapRet = new HashMap<String, String>();
//		JSONObject jo = new JSONObject(text);
//		if (jo == null || jo.equals("") || text.equals("{}")) {
//			return null;
//		}
//		mapRet.put("address", jo.getString("address"));
//		mapRet.put("consignee", jo.getString("consignee"));
//		mapRet.put("phone", jo.getString("phone"));
//		mapRet.put("postcode", jo.getString("postcode"));
//
//		return mapRet;
//	}
//
//	/***
//	 * 库存属性接口
//	 * 
//	 * @param context
//	 * @param result
//	 * @param shop
//	 * @throws JSONException
//	 */
//	public static Shop createStock_type(Context context, String result, Shop shop) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("stocktype");
//		List<StockType> list = JSON.parseArray(text, StockType.class);
//		shop.setList_stock_type(list);
//
//		return shop;
//	}
//
//	/***
//	 * 积分商品库存属性接口
//	 * 
//	 * @param context
//	 * @param result
//	 * @param shop
//	 * @throws JSONException
//	 */
//	public static IntegralShop createInteGoodStock_type(Context context, String result, IntegralShop shop)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("stocktype");
//		List<StockType> list = JSON.parseArray(text, StockType.class);
//		shop.setList_stock_type(list);
//
//		return shop;
//	}
//
//	/***
//	 * 库存属性接口
//	 * 
//	 * @param context
//	 * @param result
//	 * @param shop
//	 * @throws JSONException
//	 */
//	public static List<StockType> createStock_type(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("stocktype");
//		List<StockType> list = JSON.parseArray(text, StockType.class);
//		return list;
//	}
//
//	/***
//	 * 订单详情接口
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static List<OrderShop> createOrderShop_Details(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		String text = j.getString("shops");
//		List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
//		return list;
//	}
//
//	/**
//	 * 积分商品详情
//	 */
//	public static final HashMap<String, Object> createIntegralGood(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		IntegralShop shop = JSON.parseObject(j.getString("shop"), IntegralShop.class);
//		retInfo.put("shop", shop);
//		retInfo.put("praise_count", j.getString("praise_count"));
//		retInfo.put("eva_count", j.getString("eva_count"));
//		retInfo.put("med_count", j.getString("med_count"));
//		retInfo.put("bad_count", j.getString("bad_count"));
//		retInfo.put("status", j.getString("status"));
//		return retInfo;
//	}
//
//	/**
//	 * 晒单详情
//	 */
//	public static final HashMap<String, Object> createShaiDanDetials(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		mapRet.put("status", j.has("status") ? j.getString("status") : "");
//		mapRet.put("message", j.has("message") ? j.getString("message") : "");
//
//		JSONObject jo = j.getJSONObject("comment");
//		mapRet.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");// 商品编号
//		mapRet.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//		mapRet.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//		mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
//		mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
//		mapRet.put("pic", jo.has("pic") ? jo.getString("pic") : "");// 评论图片
//		mapRet.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//		mapRet.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
//		mapRet.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
//		mapRet.put("click", jo.has("click") ? jo.getString("click") : "");
//
//		mapRet.put("count", jo.has("count") ? jo.getString("count") : "");// 本期参与次数
//		mapRet.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");// 期数
//		mapRet.put("lucky_number", jo.has("lucky_number") ? jo.getString("lucky_number") : "");// 幸运号码
//		mapRet.put("otime", jo.has("otime") ? jo.getString("otime") : "");// 揭晓时间
//		mapRet.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");// 商品名称
//		LogYiFu.e("shaidanMap", mapRet.toString());
//		return mapRet;
//	}
//
//	/**
//	 * 晒单评论列表
//	 */
//	public static final HashMap<String, List<HashMap<String, Object>>> createShaiDanDetialsComment(Context context,
//			String result) throws JSONException {
//		HashMap<String, List<HashMap<String, Object>>> resultMap = new HashMap<String, List<HashMap<String, Object>>>();
//		JSONObject j = new JSONObject(result);
//		LogYiFu.e("shaidanMap", j.toString());
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonComment = j.getJSONArray("comments");
//		List<HashMap<String, Object>> commentsDataList = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < jsonComment.length(); i++) {
//			JSONObject jo = (JSONObject) jsonComment.opt(i);
//			HashMap<String, Object> commentMap = new HashMap<String, Object>();
//			commentMap.put("to_user_id", jo.has("to_user_id") ? jo.getString("to_user_id") : "");
//			commentMap.put("to_user_name", jo.has("to_user_name") ? jo.getString("to_user_name") : "");
//			commentMap.put("reuser_id", jo.has("reuser_id") ? jo.getString("reuser_id") : "");
//			commentMap.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//			commentMap.put("content", jo.has("content") ? jo.getString("content") : "");
//			commentMap.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//			commentMap.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//			commentsDataList.add(commentMap);
//		}
//		resultMap.put("commentsDataList", commentsDataList);// 添加晒单评论列表
//		return resultMap;
//	}
//
//	/***
//	 * 晒单发表评论
//	 */
//	public static final HashMap<String, Object> createShaiDanComment(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/***
//	 * 晒单点赞
//	 */
//	public static final HashMap<String, Object> createShaiDanClick(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 我的店铺信息
//	 */
//	public static final HashMap<String, Object> createMyShopInfo(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jaGoods = j.getJSONArray("shops");
//		List<HashMap<String, String>> listGoods = new ArrayList<HashMap<String, String>>();
//		for (int i = 0; i < jaGoods.length(); i++) {
//			JSONObject jo = (JSONObject) jaGoods.opt(i);
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("def_pic", jo.getString("def_pic"));
//			map.put("shop_price", jo.getString("shop_price"));
//			map.put("shop_code", jo.getString("shop_code"));
//			map.put("shop_se_price", jo.getString("shop_se_price"));
//			map.put("shop_name", jo.getString("shop_name"));
//			listGoods.add(map);
//		}
//		retInfo.put("listMyFavor", listGoods);// 店铺商品列表
//
//		Store store = JSON.parseObject(j.getString("store"), Store.class);
//		retInfo.put("store", store);// 店铺信息
//
//		JSONArray jaMyFavor = j.getJSONArray("likes");
//		List<HashMap<String, String>> listMyFavor = new ArrayList<HashMap<String, String>>();
//		for (int i = 0; i < jaMyFavor.length(); i++) {
//			JSONObject jo = (JSONObject) jaMyFavor.opt(i);
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("shop_price", jo.getString("shop_price"));
//			map.put("shop_code", jo.getString("shop_code"));
//			map.put("shop_se_price", jo.getString("shop_se_price"));
//			map.put("shop_name", jo.getString("shop_name"));
//			map.put("four_pic", jo.getString("four_pic"));
//			listMyFavor.add(map);
//		}
//		retInfo.put("listMyFavor", listMyFavor);// 我的最爱列表
//
//		return retInfo;
//	}
//
//	/**
//	 * 添加订单
//	 */
//	public static final HashMap<String, Object> addOrder(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		retInfo.put("order_code", j.getString("order_code"));
//		retInfo.put("url", j.getInt("url"));
//		retInfo.put("price", j.getDouble("price"));
//		String array = j.optString("outShops");
//		if (null != array && !"".equals(array)) {
//			JSONArray ja = new JSONArray(j.optString("outShops"));
//			if (null == ja || "".equals(ja)) {
//				return retInfo;
//			}
//		}
//		YCache.saveOrderToken(context, j.optString("orderToken"));
//
//		return retInfo;
//	}
//
//	/**
//	 * 特卖添加订单
//	 */
//	public static final HashMap<String, Object> addOrderSpecial(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		retInfo.put("g_code", j.getString("g_code"));
//		retInfo.put("url", j.getInt("url"));
//		retInfo.put("price", j.getDouble("price"));
//		String array = j.optString("outShops");
//		if (null != array && !"".equals(array)) {
//			JSONArray ja = new JSONArray(j.optString("outShops"));
//			if (null == ja || "".equals(ja)) {
//				return retInfo;
//			}
//		}
//		YCache.saveOrderToken(context, j.optString("orderToken"));
//
//		return retInfo;
//	}
//
//	/**
//	 * 会员商品添加订单
//	 */
//	public static final HashMap<String, Object> addMemberOrder(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		retInfo.put("order_code", j.getString("order_code"));
//		retInfo.put("url", j.getInt("url"));
//		retInfo.put("price", j.getDouble("price"));
//		// JSONArray ja = new JSONArray(j.getString("outShops"));
//		// if (null == ja || "".equals(ja)) {
//		// return retInfo;
//		// }
//		// YCache.saveOrderToken(context, j.optString("orderToken"));
//
//		return retInfo;
//	}
//
//	/**
//	 * 我的圈子数据
//	 */
//	// public static final List<HashMap<String, Object>> createMyCircle(
//	// Context context, String result) throws JSONException {
//	// List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// JSONArray jsonArray = new JSONArray(j.getString("circles"));
//	// if (null == jsonArray || "".equals(jsonArray)) {
//	// return null;
//	// }
//	// for (int i = 0; i < jsonArray.length(); i++) {
//	// JSONObject jo = (JSONObject) jsonArray.opt(i);
//	// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//	// mapObject.put("circle_id", jo.getString("circle_id"));
//	// mapObject.put("pic", jo.getString("pic"));
//	// mapObject.put("title", jo.getString("title"));
//	// mapObject.put("content", jo.getString("content"));
//	// mapObject.put("u_count", jo.getString("u_count"));
//	// mapObject.put("n_count", jo.getString("n_count"));
//	// mapObject.put("isNo", jo.getString("isNo"));
//	// retInfo.add(mapObject);
//	// }
//	// return retInfo;
//	// }
//
//	/**
//	 * 我的粉丝列表
//	 */
//	// public static final List<HashMap<String, Object>> createMyFansList(
//	// Context context, String result) throws JSONException {
//	// List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// JSONArray jsonArray = new JSONArray(j.getString("fansList"));
//	// if (null == jsonArray || "".equals(jsonArray)) {
//	// return null;
//	// }
//	// for (int i = 0; i < jsonArray.length(); i++) {
//	// JSONObject jo = (JSONObject) jsonArray.opt(i);
//	// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//	// mapObject.put("user_id",
//	// jo.has("user_id") ? jo.getString("user_id") : "");
//	// mapObject.put("person_sign",
//	// jo.has("person_sign") ? jo.getString("person_sign") : "");
//	// mapObject.put("nickname",
//	// jo.has("nickname") ? jo.getString("nickname") : "");
//	// mapObject.put("isNo", jo.has("isNo") ? jo.getString("isNo") : "");
//	// mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
//	// mapObject.put("news_count",
//	// jo.has("news_count") ? jo.getString("news_count") : "");
//	// mapObject.put("fans_count",
//	// jo.has("fans_count") ? jo.getString("fans_count") : "");
//	// retInfo.add(mapObject);
//	// }
//	// return retInfo;
//	// }
//
//	/**
//	 * 所有的圈子数据
//	 */
//	// public static final List<HashMap<String, Object>> createAllCircle(
//	// Context context, String result) throws JSONException {
//	// List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// JSONArray jsonArray = new JSONArray(j.getString("circles"));
//	// if (null == jsonArray || "".equals(jsonArray)) {
//	// return null;
//	// }
//	// for (int i = 0; i < jsonArray.length(); i++) {
//	// JSONObject jo = (JSONObject) jsonArray.opt(i);
//	// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//	// mapObject.put("circle_id", jo.getString("circle_id"));
//	// mapObject.put("pic", jo.getString("pic"));
//	// mapObject.put("title", jo.getString("title"));
//	// mapObject.put("content", jo.getString("content"));
//	// mapObject.put("day_count", jo.getString("day_count"));
//	// mapObject.put("isNo", jo.getString("isNo"));
//	// retInfo.add(mapObject);
//	// }
//	// return retInfo;
//	// }
//
//	/**
//	 * 查询圈子个人主页
//	 */
//	// public static final Map<String, Object> createCircleHomePager(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//	// mapObject.put("isNo", j.getString("isNo"));
//	// JSONObject jsonObject = new JSONObject(j.getString("home_info"));
//	// if (null == jsonObject || "".equals(jsonObject)) {
//	// return null;
//	// }
//	// mapObject.put("birthday",
//	// jsonObject.has("birthday") ? jsonObject.getString("birthday")
//	// : "");
//	// mapObject.put(
//	// "fans_count",
//	// jsonObject.has("fans_count") ? jsonObject
//	// .getString("fans_count") : "");
//	// mapObject.put(
//	// "fol_user_id",
//	// jsonObject.has("fans_count") ? jsonObject
//	// .getString("fol_user_id") : "");
//	// mapObject.put(
//	// "circle_count",
//	// jsonObject.has("circle_count") ? jsonObject
//	// .getString("circle_count") : "");
//	// mapObject.put("user_id",
//	// jsonObject.has("user_id") ? jsonObject.getString("user_id")
//	// : "");
//	// mapObject.put(
//	// "person_sign",
//	// jsonObject.has("person_sign") ? jsonObject
//	// .getString("person_sign") : "");
//	// mapObject.put("nickname",
//	// jsonObject.has("nickname") ? jsonObject.getString("nickname")
//	// : "");
//	// mapObject.put("id", jsonObject.has("id") ? jsonObject.getString("id")
//	// : "");
//	// mapObject.put("pic",
//	// jsonObject.has("pic") ? jsonObject.getString("pic") : "");
//	// mapObject.put("hobby",
//	// jsonObject.has("hobby") ? jsonObject.getString("hobby") : "");
//	// mapObject.put("city",
//	// jsonObject.has("city") ? jsonObject.getString("city") : "");
//	// mapObject.put("province",
//	// jsonObject.has("province") ? jsonObject.getString("province")
//	// : "");
//	// MyLogYiFu.e("用户信息个人主页", mapObject.toString());
//	// return mapObject;
//	// }
//
//	/**
//	 * 推荐圈子数据
//	 */
//	// public static final List<HashMap<String, Object>> createRecomCircle(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// JSONArray jsonArray = new JSONArray(j.getString("circles"));
//	// if (null == jsonArray || "".equals(jsonArray)) {
//	// return null;
//	// }
//	// for (int i = 0; i < jsonArray.length(); i++) {
//	// JSONObject jo = (JSONObject) jsonArray.opt(i);
//	// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//	// mapObject.put("ftitle", jo.optString("ftitle"));
//	// mapObject.put("send_time", jo.optLong("send_time"));
//	// mapObject.put("circle_id", jo.optString("circle_id"));
//	// mapObject.put("user_id", jo.optString("user_id"));
//	// mapObject.put("ctitle", jo.optString("ctitle"));
//	// mapObject.put("nickname", jo.optString("nickname"));
//	// mapObject.put("r_count", jo.optString("r_count"));
//	// mapObject.put("pic", jo.optString("pic"));
//	// mapObject.put("pic_list", jo.optString("pic_list"));
//	// mapObject.put("news_id", jo.optString("news_id"));
//	// mapObject.put("content", jo.optString("content"));
//	//
//	// retInfo.add(mapObject);
//	//
//	// }
//	// // JSONArray jsonArray2 = new JSONArray(j.getString("page"));
//	// // if (null == jsonArray2 || "".equals(jsonArray2)) {
//	// // return null;
//	// // }
//	// // for( int a = 0; a < jsonArray2.length())
//	// return retInfo;
//	// }
//
//	// public static final List<HashMap<String, Object>> createRecomCircle2(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// List<HashMap<String, Object>> retInfo2 = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// JSONArray jsonArray = new JSONArray(j.getString("page"));
//	// if (null == jsonArray || "".equals(jsonArray)) {
//	// return null;
//	// }
//	// for (int i = 0; i < jsonArray.length(); i++) {
//	// JSONObject jo = (JSONObject) jsonArray.opt(i);
//	// HashMap<String, Object> mapObject2 = new HashMap<String, Object>();
//	// mapObject2.put("pageCount", jo.optString("pageCount"));
//	//
//	// retInfo2.add(mapObject2);
//	//
//	// }
//	// // JSONArray jsonArray2 = new JSONArray(j.getString("page"));
//	// // if (null == jsonArray2 || "".equals(jsonArray2)) {
//	// // return null;
//	// // }
//	// // for( int a = 0; a < jsonArray2.length())
//	// return retInfo2;
//	// }
//
//	/**
//	 * 微信
//	 */
//	public static final Map<String, String> createMapWxPrepay(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		Map<String, String> map = new HashMap<String, String>();
//		JSONObject jo = new JSONObject(j.getString("xml"));
//		map.put("nonce_str", jo.getString("nonce_str"));
//		map.put("appid", jo.getString("appid"));
//		map.put("sign", jo.getString("sign"));
//		map.put("trade_type", jo.getString("trade_type"));
//		map.put("return_msg", jo.getString("return_msg"));
//		map.put("result_code", jo.getString("result_code"));
//		map.put("mch_id", jo.getString("mch_id"));
//		map.put("return_code", jo.getString("return_code"));
//		map.put("prepay_id", jo.getString("prepay_id"));
//		return map;
//		// WxPayUtil.decodeXml(j.getString("xml"));
//	}
//
//	/**
//	 * 全部帖子列表数据
//	 */
//	// public static final Map<String, List<HashMap<String, Object>>>
//	// createPostList(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// Map<String, List<HashMap<String, Object>>> allMap = new
//	// LinkedHashMap<String, List<HashMap<String, Object>>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// int isNo = j.optInt("isNo");
//	// List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONArray ja = new JSONArray(j.getString("news"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("send_time", jo.optLong("send_time"));
//	// map.put("top", jo.optString("top"));
//	// map.put("user_id", jo.optString("user_id"));
//	// map.put("fine", jo.optString("fine"));
//	// map.put("hot", jo.optString("hot"));
//	// map.put("tag", jo.optString("tag"));
//	// map.put("pic_list", jo.optString("pic_list"));
//	//
//	// map.put("nickname", jo.optString("nickname"));
//	// map.put("r_count", jo.optString("r_count"));
//	// map.put("title", jo.optString("title"));
//	// map.put("news_id", jo.optString("news_id"));
//	//
//	// listData.add(map);
//	//
//	// }
//	// allMap.put("listData", listData);
//	//
//	// List<HashMap<String, Object>> uCountData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONArray jaUCount = new JSONArray(j.getString("u_count"));
//	// for (int i = 0; i < jaUCount.length(); i++) {
//	// JSONObject jo = (JSONObject) jaUCount.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("circle_id", jo.optString("circle_id"));
//	// map.put("count", jo.optString("count"));
//	// uCountData.add(map);
//	// }
//	// allMap.put("uCountData", uCountData);
//	//
//	// List<HashMap<String, Object>> nCountData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONArray jaNCount = new JSONArray(j.getString("n_count"));
//	// for (int i = 0; i < jaNCount.length(); i++) {
//	// JSONObject jo = (JSONObject) jaNCount.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("circle_id", jo.optString("circle_id"));
//	// map.put("count", jo.optString("count"));
//	// nCountData.add(map);
//	// }
//	// allMap.put("nCountData", nCountData);
//	//
//	// List<HashMap<String, Object>> circlesData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject circleObj = new JSONObject(j.getString("circle"));
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("pager", circleObj.optString("pager"));
//	// map.put("circle_id", circleObj.optString("circle_id"));
//	// map.put("title", circleObj.optString("title"));
//	// map.put("content", circleObj.optString("content"));
//	// map.put("pic", circleObj.optString("pic"));
//	// map.put("bg_pic", circleObj.optString("bg_pic"));
//	// map.put("create_time", circleObj.optString("create_time"));
//	// map.put("user_id", circleObj.optString("user_id"));
//	// map.put("admin", circleObj.optString("admin"));
//	// map.put("circle_ids", circleObj.optString("circle_ids"));
//	// map.put("tag", circleObj.optString("tag"));
//	// map.put("isNo", isNo);
//	// circlesData.add(map);
//	// allMap.put("circlesData", circlesData);
//	//
//	// List<HashMap<String, Object>> adminsData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONArray jaAdmins = new JSONArray(j.getString("admins"));
//	// for (int i = 0; i < jaAdmins.length(); i++) {
//	// JSONObject jo = (JSONObject) jaAdmins.opt(i);
//	// HashMap<String, Object> mapAdmins = new HashMap<String, Object>();
//	// mapAdmins.put("user_id",
//	// jo.has("user_id") ? jo.optString("user_id") : "");
//	// mapAdmins.put("nickname",
//	// jo.has("nickname") ? jo.optString("nickname") : "");
//	// mapAdmins
//	// .put("admin", jo.has("admin") ? jo.optString("admin") : "");
//	// mapAdmins.put("pic", jo.has("pic") ? jo.optString("pic") : "");
//	// adminsData.add(mapAdmins);
//	// }
//	// allMap.put("adminsData", adminsData);
//	//
//	// List<HashMap<String, Object>> rnCountData = new ArrayList<HashMap<String,
//	// Object>>();
//	// HashMap<String, Object> rnCountMap = new HashMap<String, Object>();
//	// String rn_count = j.has("rn_count") ? j.getString("rn_count") : "";
//	// rnCountMap.put("rn_count", rn_count);
//	// rnCountData.add(rnCountMap);
//	// allMap.put("rnCountData", rnCountData);
//	//
//	// return allMap;
//	// }
//
//	/**
//	 * 精品和热门帖子列表数据
//	 */
//	// public static final Map<String, List<HashMap<String, Object>>>
//	// createPostListForJPAndHot(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// Map<String, List<HashMap<String, Object>>> allMap = new
//	// LinkedHashMap<String, List<HashMap<String, Object>>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	//
//	// List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONArray ja = new JSONArray(j.getString("news"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("send_time", jo.optLong("send_time"));
//	// map.put("top", jo.optString("top"));
//	// map.put("user_id", jo.optString("user_id"));
//	// map.put("fine", jo.optString("fine"));
//	// map.put("hot", jo.optString("hot"));
//	// map.put("tag", jo.optString("tag"));
//	// map.put("pic_list", jo.optString("pic_list"));
//	//
//	// map.put("nickname", jo.optString("nickname"));
//	// map.put("r_count", jo.optString("r_count"));
//	// map.put("title", jo.optString("title"));
//	// map.put("news_id", jo.optString("news_id"));
//	//
//	// listData.add(map);
//	// }
//	// allMap.put("listData", listData);
//	//
//	// return allMap;
//	// }
//
//	/**
//	 * 我的记录和动态数据
//	 */
//	// public static final List<HashMap<String, Object>>
//	// createMyRecordAndDynamic(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// List<HashMap<String, Object>> lists = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	//
//	// JSONArray ja = new JSONArray(j.getString("news"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("send_time", jo.optLong("send_time"));
//	// map.put("top", jo.optString("top"));
//	// map.put("user_id", jo.optString("user_id"));
//	// map.put("fine", jo.optString("fine"));
//	// map.put("hot", jo.optString("hot"));
//	// map.put("tag", jo.optString("tag"));
//	// map.put("pic_list", jo.optString("pic_list"));
//	// map.put("pic", jo.optString("pic"));
//	// map.put("circle_id", jo.optString("circle_id"));
//	//
//	// map.put("nickname", jo.optString("nickname"));
//	// map.put("r_count", jo.optString("r_count"));
//	// map.put("title", jo.optString("title"));
//	// map.put("news_id", jo.optString("news_id"));
//	// lists.add(map);
//	// }
//	//
//	// return lists;
//	// }
//
//	/**
//	 * 收藏帖子列表数据
//	 */
//	// public static final List<HashMap<String, Object>> createCollectList(
//	// Context context, String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// System.out.println("result：" + result);
//	// List<HashMap<String, Object>> lists = new ArrayList<HashMap<String,
//	// Object>>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	//
//	// JSONArray ja = new JSONArray(j.getString("collects"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("upic", jo.optString("upic"));
//	// map.put("nickname", jo.optString("nickname"));
//	// map.put("pic", jo.optString("pic"));
//	// map.put("title", jo.optString("title"));
//	// map.put("npic", jo.optString("npic"));
//	// map.put("tag", jo.optString("tag"));
//	// map.put("news_id", jo.optString("news_id"));
//	// map.put("user_id", jo.optString("user_id"));
//	// map.put("circle_id", jo.optString("circle_id"));
//	//
//	// lists.add(map);
//	// }
//	//
//	// return lists;
//	// }
//
//	/**
//	 * 圈子成员列表
//	 */
//	// public static final List<HashMap<String, Object>> createCircleMem(
//	// Context context, String result) throws JSONException {
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,
//	// Object>>();
//	//
//	// JSONArray ja = new JSONArray(j.getString("circleUsers"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("user_id", jo.getString("user_id"));
//	// map.put("nickname", jo.getString("nickname"));
//	// map.put("admin", jo.has("admin") ? jo.getString("admin") : "");
//	// map.put("pic", jo.has("pic") ? jo.getString("pic") : "");
//	// listData.add(map);
//	// }
//	// return listData;
//	// }
//
//	/**
//	 * 圈子成员列表
//	 */
//	// public static final List<Map<String, Object>> createCommentList(
//	// Context context, String result) throws JSONException {
//	// // //result = result.replace("null", "\"\"");
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// List<Map<String, Object>> listData = new ArrayList<Map<String,
//	// Object>>();
//	//
//	// JSONArray ja = new JSONArray(j.getString("rennews"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// Map<String, Object> map = new HashMap<String, Object>();
//	// map.put("user_id", jo.getString("user_id"));
//	// map.put("nickname", jo.has("nickname") ? jo.getString("nickname")
//	// : "");
//	// map.put("re_id", jo.getString("re_id"));
//	// map.put("news_id", jo.getString("news_id"));
//	// map.put("content", jo.getString("content"));
//	// map.put("ren_time", jo.getLong("ren_time"));
//	// map.put("pic", jo.has("pic") ? jo.getString("pic") : "");
//	// listData.add(map);
//	// }
//	// return listData;
//	// }
//
//	/**
//	 * 圈子发帖选择标签
//	 */
//	// public static final List<Map<String, Object>> createTags(Context context,
//	// String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	// List<Map<String, Object>> listData = new ArrayList<Map<String,
//	// Object>>();
//	//
//	// JSONArray ja = new JSONArray(j.getString("circle_tag"));
//	// for (int i = 0; i < ja.length(); i++) {
//	// JSONObject jo = (JSONObject) ja.opt(i);
//	// HashMap<String, Object> map = new HashMap<String, Object>();
//	// map.put("tag_name", jo.getString("tag_name"));
//	// map.put("id", jo.getString("id"));
//	//
//	// listData.add(map);
//	// }
//	// return listData;
//	// }
//
//	// public static final HashMap<String, Object> createPostInfo(Context
//	// context,
//	// String result) throws JSONException {
//	// // result = result.replace("null", "\"\"");
//	// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//	// JSONObject j = new JSONObject(result);
//	// if (null == j || "".equals(j)) {
//	// return null;
//	// }
//	//
//	// JSONObject jo = new JSONObject(j.getString("news"));
//	// mapRet.put("skim_count", jo.optString("skim_count"));
//	// mapRet.put("send_time", jo.optLong("send_time"));
//	// mapRet.put("circle_id", jo.optString("circle_id"));
//	// mapRet.put("upic", jo.optString("upic"));
//	// mapRet.put("top", jo.optString("top"));
//	// mapRet.put("hot", jo.optString("hot"));
//	// mapRet.put("nickname", jo.optString("nickname"));
//	// mapRet.put("rn_count", jo.optString("rn_count"));
//	// mapRet.put("tag", jo.optString("tag"));
//	//
//	// mapRet.put("fine", jo.optString("fine"));
//	// mapRet.put("user_id", jo.optString("user_id"));
//	// mapRet.put("title", jo.optString("title"));
//	// mapRet.put("pic_list", jo.optString("pic_list"));
//	// mapRet.put("news_id", jo.optString("news_id"));
//	// mapRet.put("content", jo.optString("content"));
//	// mapRet.put("status", jo.optString("status"));
//	// try {
//	// mapRet.put("circleTitle", j.optJSONObject("circle").opt("title"));
//	// } catch (Exception e) {
//	// // TODO Auto-generated catch block
//	// // e.printStackTrace();
//	// mapRet.put("circleTitle", "");
//	// }
//	// return mapRet;
//	// }
//
//	public static final HashMap<String, Object> createLogisticsInfo(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		mapRet.put("status", j.getString(RetInfo.status));
//		mapRet.put("message", j.getString(RetInfo.message));
//		JSONObject jo = new JSONObject(j.getString("logistics"));
//		if (null == jo || jo.equals("")) {
//			return null;
//		}
//		mapRet.put("logi_name", jo.getString("logi_name"));
//		mapRet.put("logi_code", jo.getString("logi_code"));
//		return mapRet;
//	}
//
//	/**
//	 * 我的物流数据
//	 */
//	public static final List<HashMap<String, Object>> createLogistics(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.optString("data"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		JSONObject jj = (JSONObject) jsonArray.opt(0);
//		if (null == jj || "".equals(jj)) {
//			return null;
//		}
//		JSONObject jjj = jj.optJSONObject("lastResult");
//		// JSONArray jsonArray2 = new JSONArray(jj.optString("lastResult"));
//		if (null == jjj || "".equals(jjj)) {
//			return null;
//		}
//		if (!jjj.has("data")) {
//			return null;
//		}
//		JSONArray jsonArray3 = new JSONArray(jjj.has("data") ? jjj.optString("data") : null);
//		if (null == jsonArray3 || "".equals(jsonArray3)) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray3.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray3.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			mapObject.put("time", jo.optString("time"));
//			mapObject.put("context", jo.optString("context"));
//			mapObject.put("ftime", jo.optString("ftime"));
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//
//}

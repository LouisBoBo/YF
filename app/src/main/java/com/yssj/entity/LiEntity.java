
/** 
* @author 作者 E-mail: 
* @version 创建时间：2016年8月22日 下午12:04:22 
* 类说明 
*/
package com.yssj.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.YCache;

import android.content.Context;
import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2016年8月22日下午12:04:22
 */
//public class LiEntity {
//
//	/**
//	 * 主界面商品列表
//	 */
//	public static final HashMap<String, Object> createMainTuijianData(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (j.has("topShops")) {
//			List<ShopOption> topShops = JSON.parseArray(j.getString("topShops"), ShopOption.class);
//			retInfo.put("topShops", topShops);
//		}
//
//		List<ShopOption> centShops = JSON.parseArray(j.has("centShops") ? j.getString("centShops") : "",
//				ShopOption.class);
//		retInfo.put("centShops", centShops);
//		return retInfo;
//	}
//
//	/**
//	 * 主界面商品列表
//	 */
//	public static final HashMap<String, Object> createZeroShopData(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (j.has("zeroShops")) {
//			List<ShopOption> topShops = JSON.parseArray(j.getString("zeroShops"), ShopOption.class);
//			retInfo.put("topShops", topShops);
//		}
//
//		return retInfo;
//	}
//
//	/**
//	 * 每日金额
//	 */
//	public static final List<HashMap<String, Object>> createDailyAmountData(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray ja = new JSONArray(j.getString("data"));
//		if (null == ja || "".equals(ja)) {
//			return null;
//		}
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//			HashMap<String, Object> map = new HashMap<String, Object>();
//
//			if (!jo.has("t") || null == jo.getString("t") || jo.getString("t").equals("null")) {
//				map.put("date", "0");
//			} else {
//
//				map.put("date", jo.getString("t"));
//			}
//
//			if (!jo.has("count") || null == jo.getString("count") || jo.getString("count").equals("null")
//					|| jo.getString("count").equals("")) {
//				map.put("count", -1000);
//			} else {
//
//				map.put("count", jo.getInt("count"));
//			}
//
//			if (!jo.has("sum") || null == jo.getString("sum") || jo.getString("sum").equals("null")
//					) {
//				map.put("sum", "-1000");
//			} else {
//
//				map.put("sum", jo.getString("sum"));
//			}
//
//			// map.put("date", jo.getString("t"));
//			// map.put("count", jo.getInt("count"));
//			// map.put("sum", jo.getString("sum"));
//			retInfo.add(map);
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 每日访客
//	 */
//	public static final List<HashMap<String, Object>> createDailyVisitorData(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (j.getString("data").equals("null")) {
//			return null;
//		}
//		JSONArray ja = new JSONArray(j.getString("data"));
//		if (null == ja || "".equals(ja)) {
//			return null;
//		}
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			
//			
//
//			if (!jo.has("t") || null == jo.getString("t") || jo.getString("t").equals("null")
//					) {
//				map.put("date", "0");
//			} else {
//
//				map.put("date", jo.getString("t"));
//			}
//			
//			
//			if (!jo.has("sum") || null == jo.getString("sum") || jo.getString("sum").equals("null")
//					) {
//				map.put("sum", "0");
//			} else {
//
//				map.put("sum", jo.getString("sum"));
//			}
//			
//			
//			
//			
//			
//			
//			
////			map.put("date", jo.getString("t"));
////			map.put("sum", jo.getString("sum"));
//			retInfo.add(map);
//			
//			
//			
//			
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 资金明细
//	 */
//	public static final List<FundDetail> createFundDetailsData(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		List<FundDetail> topShops = JSON.parseArray(j.getString("fundDetails"), FundDetail.class);
//		return topShops;
//	}
//
//	/**
//	 * 回佣列表
//	 */
//	public static final List<HashMap<String, Object>> createRebateList(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONArray ja = new JSONArray(j.getString("data"));
//		if (null == ja || "".equals(ja)) {
//			return null;
//		}
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			
//			
//			if (!jo.has("order_code") || null == jo.getString("order_code") || jo.getString("order_code").equals("null")
//					) {
//				map.put("order_code", "0");
//			} else {
//
//				map.put("order_code", jo.getString("order_code"));
//			}
//			
//			
//			if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")
//					) {
//				map.put("add_date",0);
//			} else {
//
//				map.put("add_date", jo.getLong("add_date"));
//			}
//			
//			
//			
//			if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")
//					) {
//				map.put("money",0);
//			} else {
//
//				map.put("money", jo.getDouble("money"));
//			}
//			
//			
//			if (!jo.has("is_free") || null == jo.getString("is_free") || jo.getString("is_free").equals("null")
//					) {
//				map.put("is_free",0);
//			} else {
//
//				map.put("is_free", jo.getInt("is_free"));
//			}
//			
//			
//			
//			if (!jo.has("status") || null == jo.getString("status") || jo.getString("status").equals("null")
//					) {
//				map.put("status","");
//			} else {
//
//				map.put("status", jo.has("status") ? jo.getString("status") : "0");
//			}
//			
//			
//
//			if (!jo.has("user_name") || null == jo.getString("user_name")
//					) {
//				map.put("user_name","");
//			} else {
//
//				map.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "0");
//			}
//			
//			
//			
//			if (!jo.has("order_price") || null == jo.getString("order_price") || jo.getString("order_price").equals("null")
//					) {
//				map.put("order_price",0);
//			} else {
//
//				map.put("order_price", jo.has("order_price") ? jo.getString("order_price") : "0");
//			}
//			
//			
//			
//			
////			map.put("order_code", jo.getString("order_code"));
////			map.put("add_date", jo.getLong("add_date"));
////			map.put("money", jo.getDouble("money"));
////			map.put("is_free", jo.getInt("is_free"));
////			map.put("status", jo.has("status") ? jo.getString("status") : "0");
////			map.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
////			map.put("order_price", jo.has("order_price") ? jo.getString("order_price") : "");
//			retInfo.add(map);
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 我的足迹列表
//	 */
//	public static final List<HashMap<String, Object>> createMyStepsList(Context context, String result)
//			throws JSONException {
//		// result = result.replace("null", "\"\"");
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONArray jsonArray = j.getJSONArray("mysList");
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			// JSONObject jo = (JSONObject) jsonArray.opt(i);
//			JSONObject jo = jsonArray.getJSONObject(i);
//			if (jo == null || "".equals(jo)) {
//				continue;
//			}
//			if (jo.optString("def_pic").equals("null") || jo.optString("def_pic").equals("")
//					|| jo.optString("def_pic") == null) {
//				continue;
//			}
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			
//			
//			if (!jo.has("kickback") || null == jo.getString("kickback") || jo.getString("kickback").equals("null")
//					) {
//				mapObject.put("kickback",0);
//			} else {
//
//				mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
//			}
//			
//			
//			
//			
//			
//			if (!jo.has("def_pic") || null == jo.getString("def_pic") || jo.getString("def_pic").equals("null")
//					) {
//				mapObject.put("def_pic",0);
//			} else {
//
//				mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "0");
//			}
//			
//			
//			
//			if (!jo.has("shop_price") || null == jo.getString("shop_price") || jo.getString("shop_price").equals("null")
//					) {
//				mapObject.put("shop_price", jo.optString("shop_price"));
//			} else {
//
//				mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
//			}
//			
//			
//			
//			if (!jo.has("shop_code") || null == jo.getString("shop_code") || jo.getString("shop_code").equals("null")
//					) {
//				mapObject.put("shop_code","0");
//			} else {
//
//				mapObject.put("shop_code", jo.optString("shop_code"));
//			}
//			
//			if (!jo.has("isLike") || null == jo.getString("isLike") || jo.getString("isLike").equals("null")
//					) {
//				mapObject.put("isLike","0");
//			} else {
//
//				mapObject.put("isLike", jo.optString("isLike"));
//			}
//			
//			
//			if (!jo.has("is_del") || null == jo.getString("is_del") || jo.getString("is_del").equals("null")
//					) {
//				mapObject.put("is_del","0");
//			} else {
//
//				mapObject.put("is_del", jo.optString("is_del"));
//			}
//			
//			
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") || jo.getString("shop_se_price").equals("null")
//					) {
//				mapObject.put("shop_se_price","0");
//			} else {
//
//				mapObject.put("shop_se_price", jo.optString("shop_se_price"));
//			}
//			
//			
//			
//			if (!jo.has("isCart") || null == jo.getString("isCart") || jo.getString("isCart").equals("null")
//					) {
//				mapObject.put("isCart","0");
//			} else {
//
//				mapObject.put("isCart", jo.optString("isCart"));
//			}
//			
//			
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") || jo.getString("shop_name").equals("null")
//					) {
//				mapObject.put("shop_name","0");
//			} else {
//
//				mapObject.put("shop_name", jo.optString("shop_name"));
//			}
//			
//			
////			mapObject.put("kickback", jo.optString("kickback"));
////			mapObject.put("def_pic", jo.optString("def_pic"));
////			mapObject.put("shop_price", jo.optString("shop_price"));
////			mapObject.put("shop_code", jo.optString("shop_code"));
////			mapObject.put("isLike", jo.optString("isLike"));
////			mapObject.put("is_del", jo.optString("is_del"));
////			mapObject.put("shop_se_price", jo.optString("shop_se_price"));
////			mapObject.put("isCart", jo.optString("isCart"));
////			mapObject.put("shop_name", jo.optString("shop_name"));
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 登陆设备列表
//	 */
//	public static final List<HashMap<String, Object>> loginDeviceList(Context context, String result)
//			throws JSONException {
//		// result = result.replace("null", "\"\"");
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("loginlist"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			
//			
//			
//			if (!jo.has("user_id") || null == jo.getString("user_id") || jo.getString("user_id").equals("null")
//					) {
//				mapObject.put("user_id","0");
//			} else {
//
//				mapObject.put("user_id", jo.optString("user_id"));
//			}
//			
//			if (!jo.has("login_time") || null == jo.getString("login_time") || jo.getString("login_time").equals("null")
//					) {
//				mapObject.put("login_time",0);
//			} else {
//
//				mapObject.put("login_time", jo.optLong("login_time"));
//			}
//			
//			
//			if (!jo.has("ip") || null == jo.getString("ip") || jo.getString("ip").equals("null")
//					) {
//				mapObject.put("ip","0");
//			} else {
//
//				mapObject.put("ip", jo.optString("ip"));
//			}
//			
//			
//			
//			if (!jo.has("id") || null == jo.getString("id") || jo.getString("id").equals("null")
//					) {
//				mapObject.put("id","0");
//			} else {
//
//				mapObject.put("id", jo.optString("id"));
//			}
//			
//			if (!jo.has("device") || null == jo.getString("device") 
//					) {
//				mapObject.put("device","0");
//			} else {
//
//				mapObject.put("device", jo.optString("device"));
//			}
//			
//			
//			
////			mapObject.put("user_id", jo.optString("user_id"));
////			mapObject.put("login_time", jo.optLong("login_time"));
////			mapObject.put("ip", jo.optString("ip"));
////			mapObject.put("id", jo.optString("id"));
////			mapObject.put("device", jo.optString("device"));
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//	/***
//	 * 我的银行卡列表
//	 */
//	public static final List<MyBankCard> createMyBankCardList(Context context, String result) throws Exception {
//		List<MyBankCard> myBankCards = new ArrayList<MyBankCard>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("bankCards"));
//		for (int i = 0; i < jsonArray.length(); i++) {
//			MyBankCard myBankCard = new MyBankCard();
//			JSONObject j = (JSONObject) jsonArray.opt(i);
//			myBankCard = JSON.parseObject(j.toString(), MyBankCard.class);
//			myBankCards.add(myBankCard);
//		}
//		return myBankCards;
//	}
//
//	/***
//	 * 得到买家信息
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createBuyerInfo(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		
//		
//		if (!j.has("status") || null == j.getString("status") 
//				) {
//			retInfo.put("status","-1000");
//		} else {
//
//			retInfo.put("status", j.getString("status"));
//		}
//		
//		
//		if (!j.has("message") || null == j.getString("message") 
//				) {
//			retInfo.put("message","");
//		} else {
//
//			retInfo.put("message", j.getString("message"));
//		}
//		
//		
//		if (!j.has("nick_name") || null == j.getString("nick_name") 
//				) {
//			retInfo.put("nick_name","");
//		} else {
//
//			retInfo.put("nick_name", j.getString("nick_name"));
//		}
//		
//		
//		if (!j.has("sell_msg") || null == j.getString("sell_msg") 
//				) {
//			retInfo.put("sell_msg","");
//		} else {
//
//			retInfo.put("sell_msg", j.getString("sell_msg"));
//		}
//		
////		retInfo.put("status", j.getString("status"));
////		retInfo.put("message", j.getString("message"));
////		retInfo.put("nick_name", j.getString("nick_name"));
////		retInfo.put("sell_msg", j.getString("sell_msg"));
//		return retInfo;
//	}
//
//	/***
//	 * 最大最小值界定
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createMaxAndMin(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		
//		
//		if (!j.has("status") || null == j.getString("status") 
//				) {
//			retInfo.put("status","");
//		} else {
//
//			retInfo.put("status", j.getString("status"));
//		}
//		
//		if (!j.has("message") || null == j.getString("message") 
//				) {
//			retInfo.put("message","");
//		} else {
//
//			retInfo.put("message", j.getString("message"));
//		}
//		
//		
//		if (!j.has("min") || null == j.getString("min") || j.getString("add_date").equals("null")
//				) {
//			retInfo.put("min","");
//		} else {
//
//			retInfo.put("min", j.has("min") ? j.getString("min") : "5");
//		}
//		
//		
//		if (!j.has("max") || null == j.getString("max") || j.getString("add_date").equals("null")
//				) {
//			retInfo.put("max","");
//		} else {
//
//			retInfo.put("max", j.has("max") ? j.getString("max") : "2000");
//		}
//		
//		
//		
////		retInfo.put("status", j.getString("status"));
////		retInfo.put("message", j.getString("message"));
////		retInfo.put("min", j.has("min") ? j.getString("min") : "5");
////		retInfo.put("max", j.has("max") ? j.getString("max") : "2000");
//		return retInfo;
//	}
//
//	/***
//	 * 我的钱包-账户明细-提现
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<HashMap<String, Object>> createMyBankDepositModel(Context context, String result)
//			throws JSONException {
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
//			if (!jo.has("id") || null == jo.getString("id") 
//					) {
//				map.put("id", 0);
//			} else {
//
//				map.put("id", jo.has("id") ? jo.optInt("id") : 0);
//			}
//			
//			if (!jo.has("business_code") || null == jo.getString("business_code") 
//					) {
//				map.put("business_code", 0);
//			} else {
//
//				map.put("business_code", jo.has("business_code") ? jo.optString("business_code") : 0);
//			}
//			
//			if (!jo.has("add_date") || null == jo.getString("add_date") || jo.getString("add_date").equals("null")
//					) {
//				map.put("add_date", 0);
//			} else {
//
//				map.put("add_date", jo.has("add_date") ? jo.optLong("add_date") : 0);
//			}
//			
//			
//			if (!jo.has("money") || null == jo.getString("money") || jo.getString("money").equals("null")
//					) {
//				map.put("money", 0);
//			} else {
//
//				map.put("money", jo.has("money") ? jo.optString("money") : 0);
//			}
//			
//			
//			
//			if (!jo.has("collect_bank_name") || null == jo.getString("collect_bank_name") 
//					) {
//				map.put("collect_bank_name", "");
//			} else {
//
//				map.put("collect_bank_name", jo.has("collect_bank_name") ? jo.optString("collect_bank_name") : "");
//			}
//			
//			
//			
//			if (!jo.has("collect_name") || null == jo.getString("collect_name") 
//					) {
//				map.put("collect_name", "");
//			} else {
//
//				map.put("collect_name", jo.has("collect_name") ? jo.optString("collect_name") : "");
//			}
//			
//			
//			if (!jo.has("collect_bank_code") || null == jo.getString("collect_bank_code") 
//					) {
//				map.put("collect_bank_code", "");
//			} else {
//
//				map.put("collect_bank_code", jo.has("collect_bank_code") ? jo.optString("collect_bank_code") : "");
//			}
//			
//			
//			if (!jo.has("collect_phone") || null == jo.getString("collect_phone") 
//					) {
//				map.put("collect_phone", "");
//			} else {
//
//				map.put("collect_phone", jo.has("collect_phone") ? jo.optString("collect_phone") : "");
//			}
//			
//			
//			if (!jo.has("user_id") || null == jo.getString("user_id") 
//					) {
//				map.put("user_id", "");
//			} else {
//
//				map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
//			}
//			
//			if (!jo.has("message") || null == jo.getString("message") 
//					) {
//				map.put("message", "");
//			} else {
//
//				map.put("message", jo.has("message") ? jo.optString("message") : "");
//			}
//			
//			if (!jo.has("check") || null == jo.getString("check") 
//					) {
//				map.put("check", "");
//			} else {
//
//				map.put("check", jo.has("check") ? jo.optString("check") : "");
//			}
//			
//			
//			if (!jo.has("check_date") || null == jo.getString("check_date") 
//					) {
//				map.put("check_date", "");
//			} else {
//
//				map.put("check_date", jo.has("check_date") ? jo.optString("check_date") : "");
//			}
//			
//			
//			if (!jo.has("check_code") || null == jo.getString("check_code") 
//					) {
//				map.put("check_code", "");
//			} else {
//
//				map.put("check_code", jo.has("check_code") ? jo.optString("check_code") : "");
//			}
//			
//			
//			if (!jo.has("check_message") || null == jo.getString("check_message") 
//					) {
//				map.put("check_message", "");
//			} else {
//
//				map.put("check_message", jo.has("check_message") ? jo.optString("check_message") : "");
//			}
//			
//			
//			if (!jo.has("fail_details") || null == jo.getString("fail_details") 
//					) {
//				map.put("fail_details", "");
//			} else {
//
//				map.put("fail_details", jo.has("fail_details") ? jo.optString("fail_details") : "");
//			}
//			
//			
//			if (!jo.has("batch_no") || null == jo.getString("batch_no") 
//					) {
//				map.put("batch_no", "");
//			} else {
//
//				map.put("batch_no", jo.has("batch_no") ? jo.optString("batch_no") : "");
//			}
//			
//			
//			
//			if (!jo.has("transfer_error") || null == jo.getString("transfer_error") 
//					) {
//				map.put("transfer_error", "");
//			} else {
//
//				map.put("transfer_error", jo.has("transfer_error") ? jo.optString("transfer_error") : "");
//			}
//			
////			map.put("id", jo.has("id") ? jo.optInt("id") : 0);
////			map.put("business_code", jo.has("business_code") ? jo.optString("business_code") : 0);
////			map.put("add_date", jo.has("add_date") ? jo.optLong("add_date") : 0);
////			map.put("money", jo.has("money") ? jo.optString("money") : 0);
////			map.put("collect_bank_name", jo.has("collect_bank_name") ? jo.optString("collect_bank_name") : "");
////			map.put("collect_name", jo.has("collect_name") ? jo.optString("collect_name") : "");
////			map.put("identity", jo.has("identity") ? jo.optString("identity") : "");
////			map.put("collect_bank_code", jo.has("collect_bank_code") ? jo.optString("collect_bank_code") : "");
////			map.put("collect_phone", jo.has("collect_phone") ? jo.optString("collect_phone") : "");
////			map.put("user_id", jo.has("user_id") ? jo.optString("user_id") : "");
////			map.put("message", jo.has("message") ? jo.optString("message") : "");
////			map.put("check", jo.has("check") ? jo.optString("check") : "");
////			map.put("check_date", jo.has("check_date") ? jo.optString("check_date") : "");
////			map.put("check_code", jo.has("check_code") ? jo.optString("check_code") : "");
////			map.put("check_message", jo.has("check_message") ? jo.optString("check_message") : "");
////			map.put("fail_details", jo.has("fail_details") ? jo.optString("fail_details") : "");
////			map.put("batch_no", jo.has("batch_no") ? jo.optString("batch_no") : "");
////			map.put("transfer_error", jo.has("transfer_error") ? jo.optString("transfer_error") : "");
//
//			lists.add(map);
//		}
//		return lists;
//	}
//
//	/****
//	 * 待付款--选择多个订单一起支付
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createOrders(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		
//		if (!j.has("transfer_error") || null == j.getString("transfer_error") 
//				) {
//			retInfo.put("status","-1000");
//		} else {
//
//			retInfo.put("status", j.getString("status"));
//		}
//		
//		
//		if (!j.has("message") || null == j.getString("message") 
//				) {
//			retInfo.put("message","-1000");
//		} else {
//
//			retInfo.put("message", j.getString("message"));
//		}
//		
//		
////		retInfo.put("status", j.getString("status"));
////		retInfo.put("message", j.getString("message"));
//		if (1 == j.getInt("status")) {
//			
//			if (!j.has("g_code") || null == j.getString("g_code") 
//					) {
//				retInfo.put("g_code","-1000");
//			} else {
//
//				retInfo.put("g_code", j.getString("g_code"));
//			}
//			
//			if (!j.has("orderCodes") || null == j.getString("orderCodes") 
//					) {
//				retInfo.put("orderCodes","0");
//			} else {
//
//				retInfo.put("orderCodes", j.getString("orderCodes"));
//			}
//			
//			
//			if (!j.has("orderPrice") || null == j.getString("orderPrice") 
//					) {
//				retInfo.put("orderPrice","0");
//			} else {
//
//				retInfo.put("orderPrice", j.getString("orderPrice"));
//			}
//			
////			retInfo.put("g_code", j.getString("g_code"));
////			retInfo.put("orderCodes", j.getString("orderCodes"));
////			retInfo.put("orderPrice", j.getString("orderPrice"));
//		}
//		return retInfo;
//	}
//
//	/****
//	 * 查看退换货
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final ReturnShop createReturnShop(Context context, String result) throws JSONException {
//		ReturnShop rShop = null;
//		JSONObject j = new JSONObject(result);
//		if (1 == j.getInt("status")) {
//			rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
//		}
//		return rShop;
//	}
//
//	/** 修改信息后 */
//	public static final UserInfo createUserInfo(Context context, String result) throws JSONException {
//		UserInfo userInfo = null;
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1) {
//			userInfo = JSON.parseObject(j.getString("userinfo"), UserInfo.class);
//		}
//		LogYiFu.e("yong户信息", userInfo.toString());
//
//		YCache.setCacheUser(context, userInfo);
//		return userInfo;
//	}
//
//	/***
//	 * 获取各大公司的物流
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<String> createGetKuaiDiCompany(Context context, String result) throws JSONException {
//		List<String> lists = new ArrayList<String>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONObject obj = new JSONObject(j.getString("data"));
//		Iterator<String> keys = obj.keys();
//		while (keys.hasNext()) {
//			String next = keys.next();
//			String value = obj.optString(next);
//			lists.add(next + "==" + value);
//		}
//		return lists;
//	}
//
//	/** 得到商品链接 */
//	public static final String createShopLink(Context context, String result) throws JSONException {
//		String shopLink = null;
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1 || j.getInt("status") == 1050) {
//			
//			if (!j.has("link") || null == j.getString("link") 
//					) {
//				shopLink = "";
//			} else {
//
//				shopLink = j.getString("link");
//			}
//			
////			shopLink = j.getString("link");
//		}
//		return shopLink;
//	}
//
//	/**
//	 * 搭配购商品 得到商品链接 isPic=true返回图片（用于分享） =false只返回链接（用于联系客服发送宝贝链接）
//	 */
//	public static final HashMap<String, String> createMatchShopLinkOrPic(Context context, String result)
//			throws JSONException {
//
//		HashMap<String, String> resultInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1 || j.getInt("status") == 1050) {
//			
//			
//			if (!j.has("status") || null == j.getString("status") 
//					) {
//				resultInfo.put("status", "");
//			} else {
//
//				resultInfo.put("status", j.optString("status"));
//			}
//			
//			
//			if (!j.has("link") || null == j.getString("link") 
//					) {
//				resultInfo.put("link", "");
//			} else {
//
//				resultInfo.put("link", j.optString("link"));
//			}
//			
//			
//			if (!j.has("pic") || null == j.getString("pic") 
//					) {
//				resultInfo.put("pic", "");
//			} else {
//
//				resultInfo.put("pic", j.optString("pic"));
//			}
//			
//			
//			
////			resultInfo.put("status", j.optString("status"));
////			resultInfo.put("link", j.optString("link"));
////			resultInfo.put("pic", j.optString("pic"));
//		}
//		return resultInfo;
//	}
//
//	/** 得到商品链接 */
//	public static final HashMap<String, String> createShareContent(Context context, String result)
//			throws JSONException {
//		HashMap<String, String> resultInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || j.equals("")) {
//			return null;
//		}
//		if (j.getInt("status") == 1) {
//			
//			if (!j.has("pic") || null == j.getString("pic") 
//					) {
//				resultInfo.put("status", "-1000");
//			} else {
//
//				resultInfo.put("status", j.optString("status"));
//			}
//			
//			
//			if (!j.has("link") || null == j.getString("link") 
//					) {
//				resultInfo.put("link", "");
//			} else {
//
//				resultInfo.put("link", j.optString("link"));
//			}
//			
//			
//			if (!j.has("QrLink") || null == j.getString("QrLink") 
//					) {
//				resultInfo.put("QrLink", "");
//			} else {
//
//				resultInfo.put("QrLink", j.optString("QrLink"));
//			}
//			
//			
//			if (!j.has("QrLink") || null == j.getString("QrLink") 
//					) {
//				resultInfo.put("QrLink", "");
//			} else {
//
//				resultInfo.put("QrLink", j.optString("QrLink"));
//			}
//			
//			
////			resultInfo.put("status", j.optString("status"));
////			resultInfo.put("link", j.optString("link"));
////			resultInfo.put("QrLink", j.optString("QrLink"));
////			resultInfo.put("status", j.optString("status"));
//
//			if (j.getString("shop") != null) {
//
//				JSONObject object = new JSONObject(j.getString("shop"));
//				if (null == object || object.equals("")) {
//					return resultInfo;
//				}
//				
//				
//				if (!j.has("content") || null == j.getString("content") 
//						) {
//					resultInfo.put("content","");
//				} else {
//
//					resultInfo.put("content", object.getString("content"));
//				}
//				
//				
//				if (!j.has("shop_code") || null == j.getString("shop_code") 
//						) {
//					resultInfo.put("shop_code","");
//				} else {
//
//					resultInfo.put("shop_code", object.getString("shop_code"));
//				}
//				
//				if (!j.has("shop_name") || null == j.getString("shop_name") 
//						) {
//					resultInfo.put("shop_name","");
//				} else {
//
//					resultInfo.put("shop_name", object.getString("shop_name"));
//				}
//				
//				if (!j.has("shop_pic") || null == j.getString("shop_pic") 
//						) {
//					resultInfo.put("shop_pic","");
//				} else {
//
//					resultInfo.put("shop_pic", object.getString("shop_pic"));
//				}
//				
//				
//				if (!j.has("def_pic") || null == j.getString("def_pic") 
//						) {
//					resultInfo.put("def_pic","");
//				} else {
//
//					resultInfo.put("def_pic", object.getString("def_pic"));
//				}
//				
//				
//				if (!j.has("shop_se_price") || null == j.getString("shop_se_price") 
//						) {
//					resultInfo.put("shop_se_price","0");
//				} else {
//
//					resultInfo.put("shop_se_price", object.getString("shop_se_price"));
//				}
//				
//				
////				resultInfo.put("content", object.getString("content"));
////				resultInfo.put("four_pic", object.getString("four_pic"));
////				resultInfo.put("shop_code", object.getString("shop_code"));
////				resultInfo.put("shop_name", object.getString("shop_name"));
//
//				// resultInfo.put("qr_pic", object.getString("qr_pic"));
////				resultInfo.put("shop_pic", object.getString("shop_pic"));
////				resultInfo.put("def_pic", object.getString("def_pic"));
////				resultInfo.put("shop_se_price", object.getString("shop_se_price"));
//			}
//
//		} else if (j.getInt("status") == 1050) {
//			
//			
//			
//			if (!j.has("status") || null == j.getString("status") 
//					) {
//				resultInfo.put("status", "-1000");
//			} else {
//
//				resultInfo.put("status", j.optString("status"));
//			}
//			
//			
//			
////			resultInfo.put("status", j.optString("status"));
//		}
//
//		LogYiFu.e("日日日", resultInfo + "");
//		return resultInfo;
//	}
//
//	/** 签到分享特价商品 */
//	public static final HashMap<String, String> createSignShareZero(Context context, String result)
//			throws JSONException {
//		HashMap<String, String> resultInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || j.equals("")) {
//			return null;
//		}
//		if ("1".equals(j.getString("status"))) {
//			resultInfo.put("status", j.optString("status"));
//			
//			
//			if (!j.has("link") || null == j.getString("link") 
//					) {
//				resultInfo.put("link", "");
//			} else {
//
//				resultInfo.put("link", j.optString("link"));
//			}
//			
//			
//			if (!j.has("message") || null == j.getString("message") 
//					) {
//				resultInfo.put("message", "");
//			} else {
//
//				resultInfo.put("message", j.getString("message"));
//			}
//			
//			
//			if (!j.has("price") || null == j.getString("price") 
//					) {
//				resultInfo.put("price", "");
//			} else {
//
//				resultInfo.put("price", j.getString("price"));
//			}
//			
////			resultInfo.put("link", j.optString("link"));
////			resultInfo.put("message", j.getString("message"));
////			resultInfo.put("price", j.getString("price"));
//			if (j.getString("Pshop") != null) {
//
//				JSONObject object = new JSONObject(j.getString("Pshop"));
//				if (null == object || object.equals("")) {
//					return resultInfo;
//				}
//
//				JSONArray ja = new JSONArray(object.getString("shop_list"));
//				JSONObject shopListArray = (JSONObject) ja.opt(0);
//				if (null == shopListArray || shopListArray.equals("")) {
//					return resultInfo;
//				}
//				
//				
//				if (!j.has("shop_code") || null == j.getString("shop_code") 
//						) {
//					resultInfo.put("shop_code", "");
//				} else {
//
//					resultInfo.put("shop_code", shopListArray.getString("shop_code"));
//				}
//				
//				if (!j.has("four_pic") || null == j.getString("four_pic") 
//						) {
//					resultInfo.put("four_pic", "");
//				} else {
//
//					resultInfo.put("four_pic", shopListArray.getString("four_pic"));
//				}
//
//
////				resultInfo.put("shop_code", shopListArray.getString("shop_code"));
////				resultInfo.put("four_pic", shopListArray.getString("four_pic"));
//
//				// resultInfo.put("qr_pic", object.getString("qr_pic"));
//			}
//
//		} else if (j.getInt("status") == 1050) {
//			
//			
//			if (!j.has("status") || null == j.getString("status") 
//					) {
//				resultInfo.put("status", "-1000");
//			} else {
//
//				resultInfo.put("status", j.optString("status"));
//			}
//			
//			
////			resultInfo.put("status", j.optString("status"));
//		}
//
//		return resultInfo;
//	}
//
//	/** 得到套餐商品链接 */
//	public static final HashMap<String, Object> createpShareContent(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> resultInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || j.equals("")) {
//			return null;
//		}
//		String four_pic = null;
//		if (j.getInt("status") == 1) {
//			resultInfo.put("status", j.optString("status"));
//			resultInfo.put("link", j.optString("link"));
//			resultInfo.put("shop_se_price", j.optString("price"));
//
//			if (j.getString("Pshop") != null) {
//
//				JSONArray jArray = new JSONArray(j.getString("Pshop"));
//				if (null == jArray || jArray.equals("")) {
//					return resultInfo;
//				}
//
//				StringBuffer sb = new StringBuffer();
//
//				List<String> shopCodes = new ArrayList<String>();
//				List<HashMap<String, String>> pics = new ArrayList<HashMap<String, String>>();
//				JSONObject jo2 = (JSONObject) jArray.opt(0);
//				four_pic = jo2.optString("shop_code").substring(1, 4) + "/" + jo2.optString("shop_code") + "/"
//						+ jo2.optString("four_pic");
//				for (int i = 0; i < jArray.length(); i++) {
//					JSONObject jo = (JSONObject) jArray.opt(i);
//					
//					
//					
//					if (!jo.has("shop_code") || null == jo.getString("shop_code") 
//							) {
//						shopCodes.add("");
//					} else {
//
//						shopCodes.add(jo.optString("shop_code"));
//					}
//					
////					shopCodes.add(jo.optString("shop_code"));
//
//					HashMap<String, String> hashMap = new HashMap<String, String>();
//					
//					
//					
//					if (!jo.has("shop_code") || null == jo.getString("shop_code") 
//							) {
//						hashMap.put(jo.optString("shop_code"), "");
//					} else {
//
//						hashMap.put(jo.optString("shop_code"), jo.optString("shop_pic"));
//					}
//					
//					
////					hashMap.put(jo.optString("shop_code"), jo.optString("shop_pic"));
//					pics.add(hashMap);
//					
//					
//					if (!jo.has("shop_pic") || null == jo.getString("shop_pic") 
//							) {
//						sb.append("");
//					} else {
//
//						sb.append(jo.optString("shop_pic"));
//					}
//					
//					
////					sb.append(jo.optString("shop_pic"));
//					if (i != jArray.length() - 1) {
//						sb.append(",");
//					}
//				}
//				resultInfo.put("shopCodes", shopCodes);
//				resultInfo.put("pics", pics);
//				resultInfo.put("four_pic", four_pic);
//
//				resultInfo.put("shop_pic", sb.toString());
//			}
//
//		} else if (j.getInt("status") == 1050) {
//			resultInfo.put("status", j.optString("status"));
//		}
//		return resultInfo;
//	}
//
//	/****
//	 * 获取最优优惠卡
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, Object> createProxCoupon(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		
//		
//		if (!j.has("status") || null == j.getString("status") 
//				) {
//			retInfo.put("status", "-1000");
//		} else {
//
//			retInfo.put("status", j.getString("status"));
//		}
//		
//		
//		if (!j.has("message") || null == j.getString("message") 
//				) {
//			retInfo.put("message", "-1000");
//		} else {
//
//			retInfo.put("message", j.getString("message"));
//		}
//		
//		
////		retInfo.put("status", j.getString("status"));
////		retInfo.put("message", j.getString("message"));
//		if (1 == j.getInt("status")) {
//			retInfo = JSON.parseObject(result, new TypeReference<HashMap<String, Object>>() {
//			});
//		}
//		return retInfo;
//	}
//
//	/****
//	 * 查询积分记录
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<HashMap<String, Object>> createIntergralDetail(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray ja = new JSONArray(j.getString("data"));
//		for (int i = 0; i < ja.length(); i++) {
//			JSONObject jo = (JSONObject) ja.opt(i);
//
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			
//			
//			if (!jo.has("remark") || null == jo.getString("remark") 
//					) {
//				map.put("remark", "");
//			} else {
//
//				map.put("remark", jo.has("remark") ? jo.optString("remark") : "");
//			}
//			
//			
//			if (!jo.has("id") || null == jo.getString("id") ||jo.getString("id").equals("null")
//					) {
//				map.put("id", 0);
//			} else {
//
//				map.put("id", jo.has("id") ? jo.optInt("id") : 0);
//			}
//			
//
//			if (!jo.has("user_id") || null == jo.getString("user_id") ||jo.getString("user_id").equals("null")
//					) {
//				map.put("user_id", 0);
//			} else {
//
//				map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
//			}
//			
//			if (!jo.has("num") || null == jo.getString("num") ||jo.getString("num").equals("null")
//					) {
//				map.put("num", 0);
//			} else {
//
//				map.put("num", jo.has("num") ? jo.optInt("num") : 0);
//			}
//			
//			
//			if (!jo.has("type") || null == jo.getString("type") ||jo.getString("type").equals("null")
//					) {
//				map.put("type", 0);
//			} else {
//
//				map.put("type", jo.has("type") ? jo.optInt("type") : 0);
//			}
//			
//			
//			
//			if (!jo.has("add_time") || null == jo.getString("add_time") ||jo.getString("add_time").equals("null")
//					) {
//				map.put("add_time", "");
//			} else {
//
//				map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
//			}
//			
//			
////			map.put("remark", jo.has("remark") ? jo.optString("remark") : "");
////			map.put("id", jo.has("id") ? jo.optInt("id") : 0);
////			map.put("user_id", jo.has("user_id") ? jo.optInt("user_id") : 0);
////			map.put("num", jo.has("num") ? jo.optInt("num") : 0);
////			map.put("type", jo.has("type") ? jo.optInt("type") : 0);
////			map.put("add_time", jo.has("add_time") ? jo.optString("add_time") : "");
//			lists.add(map);
//		}
//		LogYiFu.e("TAG", lists.toString());
//		return lists;
//	}
//
//	/****
//	 * 查询我的积分
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final Integer createMyIntergral(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		if (j.getInt("status") != 1) {
//			return null;
//		}
//		
//		if (!j.has("integral") || null == j.getString("integral") || j.getString("integral").equals("null")
//				) {
//			return -1000;
//		} else {
//
//			return j.getInt("integral");
//		}
//		
//		
//		
//		
//
////		return j.getInt("integral");
//	}
//
//	/****
//	 * 获取阿里参数
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createAliParam(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (1 == j.getInt("status")) {
//			
//			
//			
//			
//			
//			if (!j.has("private_key") || null == j.getString("private_key") ||j.getString("private_key").equals("null")
//					) {
//				retInfo.put("private_key", "");
//			} else {
//
//				retInfo.put("private_key", j.getString("private_key"));
//			}
//			
//			if (!j.has("partner") || null == j.getString("partner") ||j.getString("partner").equals("null")
//					) {
//				retInfo.put("partner", "");
//			} else {
//
//				retInfo.put("partner", j.getString("partner"));
//			}
//			
//			
//			if (!j.has("ali_public_key") || null == j.getString("ali_public_key") ||j.getString("ali_public_key").equals("null")
//					) {
//				retInfo.put("ali_public_key", "");
//			} else {
//
//				retInfo.put("ali_public_key", j.getString("ali_public_key"));
//			}
//			
//			if (!j.has("sign_type") || null == j.getString("sign_type") ||j.getString("sign_type").equals("null")
//					) {
//				retInfo.put("sign_type", "");
//			} else {
//
//				retInfo.put("sign_type", j.getString("sign_type"));
//			}
//			
//			if (!j.has("seller") || null == j.getString("seller") ||j.getString("seller").equals("null")
//					) {
//				retInfo.put("seller", "");
//			} else {
//
//				retInfo.put("seller", j.getString("seller"));
//			}
//			
//			if (!j.has("pay_url") || null == j.getString("pay_url") ||j.getString("pay_url").equals("null")
//					) {
//				retInfo.put("pay_url", "");
//			} else {
//
//				retInfo.put("pay_url", j.optString("pay_url"));
//			}
//			
//			
//			
//			if (!j.has("price") || null == j.getString("price") ||j.getString("price").equals("null")
//					) {
//				retInfo.put("price", "0");
//			} else {
//
//				retInfo.put("price", j.optString("price"));
//			}
//			
////			retInfo.put("private_key", j.getString("private_key"));
////			retInfo.put("partner", j.getString("partner"));
////			retInfo.put("ali_public_key", j.getString("ali_public_key"));
////			retInfo.put("sign_type", j.getString("sign_type"));
////			retInfo.put("seller", j.getString("seller"));
////			retInfo.put("pay_url", j.optString("pay_url"));
////			retInfo.put("price", j.optString("price"));
//		}
//		return retInfo;
//	}
//
//	/****
//	 * 获取微信支付参数
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createWXParam(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (1 == j.getInt("status")) {
//			
//			
//			if (!j.has("key") || null == j.getString("key")
//					) {
//				retInfo.put("key", "");
//			} else {
//
//				retInfo.put("key", j.getString("key"));
//			}
//			
//			if (!j.has("appID") || null == j.getString("appID")
//					) {
//				retInfo.put("appID", "");
//			} else {
//
//				retInfo.put("appID", j.getString("appID"));
//			}
//			
//			if (!j.has("AppSecret") || null == j.getString("AppSecret")
//					) {
//				retInfo.put("AppSecret", "");
//			} else {
//
//				retInfo.put("AppSecret", j.optString("AppSecret"));
//			}
//			
//			if (!j.has("mchID") || null == j.getString("mchID")
//					) {
//				retInfo.put("mchID", "");
//			} else {
//
//				retInfo.put("mchID", j.optString("mchID"));
//			}
//			
//			
//			
////			retInfo.put("key", j.getString("key"));
////			retInfo.put("appID", j.getString("appID"));
////			retInfo.put("AppSecret", j.optString("AppSecret"));
////			retInfo.put("mchID", j.optString("mchID"));
//		}
//		return retInfo;
//	}
//
//	/****
//	 * 获取阿里参数
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final HashMap<String, String> createInviteCode(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (1 == j.getInt("status")) {
//			
//			if (!j.has("link") || null == j.getString("link")
//					) {
//				retInfo.put("link", "");
//			} else {
//
//				retInfo.put("link", j.getString("link"));
//			}
//			
//			
//			if (!j.has("inviteCode") || null == j.getString("inviteCode")
//					) {
//				retInfo.put("inviteCode", "");
//			} else {
//
//				retInfo.put("inviteCode", j.getString("inviteCode"));
//			}
//			
//			
//			if (!j.has("content") || null == j.getString("content")
//					) {
//				retInfo.put("content", "");
//			} else {
//
//				retInfo.put("content", j.getString("content"));
//			}
//			
//			
//			if (!j.has("useCode") || null == j.getString("useCode")
//					) {
//				retInfo.put("useCode", "");
//			} else {
//
//				retInfo.put("useCode", j.getString("useCode"));
//			}
//			
////			retInfo.put("link", j.getString("link"));
////			retInfo.put("inviteCode", j.getString("inviteCode"));
////			retInfo.put("content", j.getString("content"));
////			retInfo.put("useCode", j.getString("useCode"));
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 分享邀请码之后调用此接口.以便记录数据,后期好统计
//	 */
//	public static final HashMap<String, String> createShareNumber(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		// if (1 == j.getInt("status")) {
//		//
//		// }
//		return retInfo;
//	}
//
//	/**
//	 * 3293 【AND】 运营数据统计
//	 */
//	public static final HashMap<String, String> createYunYingTongJi(Context context, String result)
//			throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		// if (1 == j.getInt("status")) {
//		//
//		// }
//		return retInfo;
//	}
//
//	/****
//	 * 商家联盟收益统计
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
////
////	public static final HashMap<String, Object> createRevenueStatistics(Context context, String result)
////			throws JSONException {
////
////		JSONObject j = new JSONObject(result);
////		if (null == j || "".equals(j)) {
////			return null;
////		}
////
////		HashMap<String, Object> map = new HashMap<String, Object>();
////		map.put("monthMoney", j.has("monthMoney") ? j.optDouble("monthMoney") : "");
////		map.put("todayMoney", j.has("todayMoney") ? j.optDouble("todayMoney") : 0);
////		map.put("weekMoney", j.has("weekMoney") ? j.optDouble("weekMoney") : 0);
////		map.put("status", j.has("status") ? j.optString("status") : "");
////		map.put("message", j.has("message") ? j.optString("message") : "");
////
////		LogYiFu.e("收益统计", map.toString());
////		return map;
////	}
//
//	/**
//	 * 超级合伙人会员
//	 */
////	public static final List<HashMap<String, Object>> createAllianceMember(Context context, String result)
////			throws JSONException {
////		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
////		JSONObject j = new JSONObject(result);
////		if (null == j || "".equals(j)) {
////			return null;
////		}
////		JSONArray jsonArray = new JSONArray(j.getString("list"));
////		if (null == jsonArray || "".equals(jsonArray)) {
////			return null;
////		}
////		for (int i = 0; i < jsonArray.length(); i++) {
////			JSONObject jo = (JSONObject) jsonArray.opt(i);
////			HashMap<String, Object> mapObject = new HashMap<String, Object>();
////			mapObject.put("start_time", jo.has("start_time") ? jo.getString("start_time") : "");
////			mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
////			mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
////			mapObject.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
////			mapObject.put("phone", jo.has("phone") ? jo.getString("phone") : "");
////			mapObject.put("city", jo.has("city") ? jo.getString("city") : "");
////			mapObject.put("province", jo.has("province") ? jo.getString("province") : "");
////			mapObject.put("plaintext", jo.has("plaintext") ? jo.getString("plaintext") : "");
////			mapObject.put("buyer_id", jo.has("buyer_id") ? jo.getString("buyer_id") : "");
////			mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no") : "");
////			mapObject.put("count", jo.has("count") ? jo.getString("count") : "0");
////			retInfo.add(mapObject);
////
////		}
//
////		return retInfo;
////	}
//
//	/**
//	 * 超级合伙人会员下级会员
//	 */
////	public static final List<HashMap<String, Object>> createAllianceH5Member(Context context, String result)
////			throws JSONException {
////
////		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
////
////		JSONObject j = new JSONObject(result);
////		if (null == j || "".equals(j)) {
////			return null;
////		}
////		JSONArray jsonArray = new JSONArray(j.getString("list"));
////		if (null == jsonArray || "".equals(jsonArray)) {
////			return null;
////		}
////		for (int i = 0; i < jsonArray.length(); i++) {
////			JSONObject jo = (JSONObject) jsonArray.opt(i);
////			HashMap<String, Object> mapObject = new HashMap<String, Object>();
////			mapObject.put("time", jo.has("time") ? jo.getString("time") : "");
////			mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
////			mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
////			mapObject.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
////			mapObject.put("phone", jo.has("phone") ? jo.getString("phone") : "");
////			mapObject.put("city", jo.has("city") ? jo.getString("city") : "");
////			mapObject.put("province", jo.has("province") ? jo.getString("province") : "");
////			mapObject.put("sumMoney", jo.has("sumMoney") ? jo.getString("sumMoney") : "");
////			retInfo.add(mapObject);
////
////		}
//
////		return retInfo;
////	}
//
//	/**
//	 * 联盟商家主页信息获取
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
////	public static HashMap<String, String> leagueHome(Context context, String result) throws JSONException {
////		HashMap<String, String> retInfo = new HashMap<String, String>();
////		JSONObject j = new JSONObject(result);
////		retInfo.put("status", j.optString("status"));
////		retInfo.put("message", j.optString("message"));
////		if (1 == j.optInt("status")) {
////			retInfo.put("two_balance", j.optString("two_balance"));
////			retInfo.put("user_add_date", j.optString("user_add_date"));
////			retInfo.put("user_pic", j.getString("user_pic"));
////			retInfo.put("user_name", j.getString("user_name"));
////			retInfo.put("orderCount", j.getString("orderCount"));
////			retInfo.put("two_freeze_balance", j.getString("two_freeze_balance"));
////			retInfo.put("juniorUserCount", j.getString("juniorUserCount"));
////			retInfo.put("depositMoneySuccessSum", j.getString("depositMoneySuccessSum"));
////		}
////		return retInfo;
////	}
//
//	/**
//	 * 联盟商家提现记录获取
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
////	public static HashMap<String, Object> leagueLogDetail(Context context, String result) throws JSONException {
////		HashMap<String, Object> retInfo = new HashMap<String, Object>();
////		JSONObject j = new JSONObject(result);
////		retInfo.put("status", j.optString("status"));
////		retInfo.put("message", j.optString("message"));
////		if (1 == j.optInt("status")) {
////			retInfo.put("depositMoneySuccessSum", j.optString("depositMoneySuccessSum"));
////			JSONArray ja = j.optJSONArray("data");
////			if (ja != null && ja.length() > 0) {
////				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
////				for (int i = 0; i < ja.length(); i++) {
////					JSONObject js = ja.optJSONObject(i);
////					HashMap<String, String> map = new HashMap<String, String>();
////					map.put("collect_bank_code", js.optString("collect_bank_code"));
////					map.put("collect_bank_name", js.optString("collect_bank_name"));
////					map.put("money", js.optString("money"));
////					map.put("add_date", js.optString("add_date"));
////					map.put("check", js.optString("check"));
////					list.add(map);
////				}
////				retInfo.put("data", list);
////			}
////		}
////		return retInfo;
////	}
//
//	/**
//	 * 联盟商家佣金记录获取
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
////	public static HashMap<String, Object> leagueYJLog(Context context, String result) throws JSONException {
////		HashMap<String, Object> retInfo = new HashMap<String, Object>();
////		JSONObject j = new JSONObject(result);
////		retInfo.put("status", j.optString("status"));
////		retInfo.put("message", j.optString("message"));
////		if (1 == j.optInt("status")) {
////			retInfo.put("pageCount", j.optString("pageCount"));
////			JSONArray ja = j.optJSONArray("data");
////			if (ja != null && ja.length() > 0) {
////				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
////				for (int i = 0; i < ja.length(); i++) {
////					JSONObject js = ja.optJSONObject(i);
////					HashMap<String, String> map = new HashMap<String, String>();
////					map.put("NICKNAME", js.optString("user_name"));
////					map.put("money", js.optString("money"));
////					map.put("add_date", js.optString("add_date"));
////					map.put("is_free", js.optString("is_free"));
////					map.put("status", js.optString("status"));
////					list.add(map);
////				}
////				retInfo.put("data", list);
////			}
////		}
////		return retInfo;
////	}
//
//	/**
//	 * 联盟商家提现记录获取
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
////	public static HashMap<String, Object> leagueTXLog(Context context, String result) throws JSONException {
////		HashMap<String, Object> retInfo = new HashMap<String, Object>();
////		JSONObject j = new JSONObject(result);
////		retInfo.put("status", j.optString("status"));
////		retInfo.put("message", j.optString("message"));
////		if (1 == j.optInt("status")) {
////			retInfo.put("pageCount", j.optString("pageCount"));
////			JSONArray ja = j.optJSONArray("data");
////			if (ja != null && ja.length() > 0) {
////				List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
////				for (int i = 0; i < ja.length(); i++) {
////					JSONObject js = ja.optJSONObject(i);
////					HashMap<String, String> map = new HashMap<String, String>();
////					map.put("collect_bank_code", js.optString("collect_bank_code"));
////					map.put("collect_bank_name", js.optString("collect_bank_name"));
////					map.put("money", js.optString("money"));
////					map.put("check", js.optString("check"));
////					map.put("add_date", js.optString("add_date"));
////					list.add(map);
////				}
////				retInfo.put("data", list);
////			}
////
////		}
////		return retInfo;
////	}
//
//	/**
//	 * 获取供应商号码
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static String getSuppPhone(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1) {
//			
//			if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")
//					) {
//				return j.optString("0");
//			} else {
//
//				return j.optString("phone");
//			}
//			
//			
////			return j.optString("phone");
//		}
//		return null;
//	}
//
//	// 统计分享次数
//	public static String createShareCount(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1) {
//			return j.optString("status");
//		}
//		return null;
//	}
//
//	/**
//	 * 0元购套餐列表
//	 */
//	public static final Map<String, List<HashMap<String, Object>>> createZeroShopProductList(Context context,
//			String result) throws JSONException {
//		// System.out.println("套餐检查=" + result);
//		Map<String, List<HashMap<String, Object>>> allMap = new HashMap<String, List<HashMap<String, Object>>>();
//		List<HashMap<String, Object>> zeroShopList2 = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		JSONArray jsonArray2 = new JSONArray(j.has("pList") ? j.getString("pList") : "");
//		for (int i = 0; i < jsonArray2.length(); i++) {
//			JSONObject jo2 = (JSONObject) jsonArray2.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			
//
//			if (!jo2.has("p_status") || null == jo2.getString("p_status") || jo2.getString("p_status").equals("null")
//					) {
//				mapObject.put("p_status", 0); // 有数据了再看是不是在这个位置
//			} else {
//
//				mapObject.put("p_status", jo2.has("p_status") ? jo2.getInt("p_status") : "0"); // 有数据了再看是不是在这个位置
//			}
//
////			mapObject.put("p_status", jo2.has("p_status") ? jo2.getInt("p_status") : "0"); // 有数据了再看是不是在这个位置
//
//			if (!jo2.has("code") || null == jo2.getString("code") || jo2.getString("code").equals("null")
//					) {
//				mapObject.put("code", "");
//			} else {
//
//				mapObject.put("code", jo2.has("code") ? jo2.getString("code") : "");
//			}
////			mapObject.put("code", jo2.has("code") ? jo2.getString("code") : "");
//			
//			if (!jo2.has("name") || null == jo2.getString("name") || jo2.getString("name").equals("null")
//					) {
//				mapObject.put("name", "");
//			} else {
//
//				mapObject.put("name", jo2.has("name") ? jo2.getString("name") : "");
//			}
////			mapObject.put("name", jo2.has("name") ? jo2.getString("name") : "");
//			
//			if (!jo2.has("postage") || null == jo2.getString("postage") || jo2.getString("postage").equals("null")
//					) {
//				mapObject.put("postage", "");
//			} else {
//
//				mapObject.put("postage", jo2.has("postage") ? jo2.getString("postage") : "");
//			}
////			mapObject.put("postage", jo2.has("postage") ? jo2.getString("postage") : "");
//			
//			if (!jo2.has("type") || null == jo2.getString("type") || jo2.getString("type").equals("null")
//					) {
//				mapObject.put("type", "");
//			} else {
//
//				mapObject.put("type", jo2.has("type") ? jo2.getString("type") : "");
//			}
////			mapObject.put("type", jo2.has("type") ? jo2.getString("type") : "");
//			
//			if (!jo2.has("price") || null == jo2.getString("price") || jo2.getString("price").equals("null")
//					) {
//				mapObject.put("price", "0");
//			} else {
//
//				mapObject.put("price", jo2.has("price") ? jo2.getString("price") : "");
//			}
//			
////			mapObject.put("price", jo2.has("price") ? jo2.getString("price") : "");
//			
//			if (!jo2.has("content") || null == jo2.getString("content") 
//					) {
//				mapObject.put("content", "");
//			} else {
//
//				mapObject.put("content", jo2.has("content") ? jo2.getString("content") : "");
//			}
//			
////			mapObject.put("content", jo2.has("content") ? jo2.getString("content") : "");
//			
//			if (!jo2.has("seq") || null == jo2.getString("seq") 
//					) {
//				mapObject.put("seq", "");
//			} else {
//
//				mapObject.put("seq", jo2.has("seq") ? jo2.getString("seq") : "");
//			}
//			
////			mapObject.put("seq", jo2.has("seq") ? jo2.getString("seq") : "");
//			
//			if (!jo2.has("virtual_sales") || null == jo2.getString("virtual_sales")|| jo2.getString("virtual_sales").equals("null")
//					) {
//				mapObject.put("virtual_sales", "0");
//			} else {
//
//				mapObject.put("virtual_sales", jo2.has("virtual_sales") ? jo2.getString("virtual_sales") : "0");
//			}
//			
//
////			mapObject.put("virtual_sales", jo2.has("virtual_sales") ? jo2.getString("virtual_sales") : "0");
//			/*
//			 * mapObject.put("p_type", jo2.has("p_type") ?
//			 * jo2.getString("p_type") : "999");
//			 */
//			mapObject.put("num", jo2.has("num") ? jo2.getString("num") : "0");
//			mapObject.put("r_num", jo2.has("r_num") ? jo2.getString("r_num") : "0");
//			JSONArray jsonArray3 = new JSONArray(jo2.has("shop_list") ? jo2.getString("shop_list") : "");
//			List<HashMap<String, Object>> zeroShopList1 = new ArrayList<HashMap<String, Object>>();
//			for (int p = 0; p < jsonArray3.length(); p++) {
//				JSONObject jo = (JSONObject) jsonArray3.opt(p);
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				
//				
//				if (!jo.has("p_code") || null == jo.getString("p_code")|| jo.getString("p_code").equals("null")
//						) {
//					mapObject.put("p_code", "0");
//				} else {
//
//					map.put("p_code", jo.has("p_code") ? jo.getString("p_code") : "");
//				}
//				
////				map.put("p_code", jo.has("p_code") ? jo.getString("p_code") : "");
//				/*
//				 * // TODO: map.put("p_type", jo.has("p_type") ?
//				 * jo.getString("p_type") : "");
//				 */
//				
//				if (!jo.has("shop_name") || null == jo.getString("shop_name")|| jo.getString("shop_name").equals("null")
//						) {
//					mapObject.put("shop_name", "");
//				} else {
//
//					map.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//				}
////				map.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//				
//				if (!jo.has("invertory_num") || null == jo.getString("invertory_num")|| jo.getString("invertory_num").equals("null")
//						) {
//					mapObject.put("invertory_num", "");
//				} else {
//
//					map.put("invertory_num", jo.has("invertory_num") ? jo.getString("invertory_num") : "");
//				}
//				
////				map.put("invertory_num", jo.has("invertory_num") ? jo.getString("invertory_num") : "");
//				// map.put("virtual_sales", jo.has("virtual_sales") ?
//				// jo.getString("virtual_sales") : "");
//				
//				if (!jo.has("four_pic") || null == jo.getString("four_pic")|| jo.getString("four_pic").equals("null")
//						) {
//					mapObject.put("four_pic", "");
//				} else {
//
//					map.put("four_pic", jo.has("four_pic") ? jo.getString("four_pic") : "");
//				}
////				map.put("four_pic", jo.has("four_pic") ? jo.getString("four_pic") : "");
//				
//				
//				if (!jo.has("shop_price") || null == jo.getString("shop_price")|| jo.getString("shop_price").equals("null")
//						) {
//					mapObject.put("shop_price", "0");
//				} else {
//
//					map.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "");
//				}
//				
////				map.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "");
//				
//				if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price")|| jo.getString("shop_se_price").equals("null")
//						) {
//					mapObject.put("shop_se_price", "0");
//				} else {
//
//					map.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "");
//				}
////				map.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "");
//
//				// map.put("content",
//				// jo.has("content") ? jo.getString("content")
//				// : "");
//				
//				if (!jo.has("shop_code") || null == jo.getString("shop_code")|| jo.getString("shop_code").equals("null")
//						) {
//					mapObject.put("shop_code", "");
//				} else {
//
//					map.put("shop_code", jo.optString("shop_code", ""));
//				}
////				map.put("shop_code", jo.optString("shop_code", ""));
//				zeroShopList1.add(map);
//			}
//
//			mapObject.put("shop_list", zeroShopList1);
//			zeroShopList2.add(mapObject);
//		}
//		allMap.put("pList", zeroShopList2);
//		if (zeroShopList2.isEmpty()) {
//			return null;
//		}
//
//		return allMap;
//	}
//
//	/**
//	 * 购物车统计
//	 */
//	public static String createShopCartCount(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//		if (j.getInt("status") == 1) {
//			
//			if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")
//					) {
//				return j.optString("0");
//			} else {
//
//				return j.optString("cart_count");
//			}
//			
//			
//			
////			return j.optString("cart_count");
//		}
//		return "0";
//	}
//
//	/**
//	 * 搭配购 购物车数量统计 和获取倒计时
//	 */
//	public static HashMap<String, Object> createMatchShopCartCount(Context context, String result)
//			throws JSONException {
//		JSONObject j = new JSONObject(result);
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		if (j.getInt("status") == 1) {
//			
//			
//			if (!j.has("cart_count") || null == j.getString("cart_count") || j.getString("cart_count").equals("null")
//					) {
//				hashMap.put("cart_count", "0");
//			} else {
//
//				hashMap.put("cart_count", j.optString("cart_count"));
//			}
//			
//			if (!j.has("s_time") || null == j.getString("s_time") || j.getString("s_time").equals("null")
//					) {
//				hashMap.put("s_time", 0);
//			} else {
//
//				hashMap.put("s_time", j.optLong("s_time", 0));
//			}
//			
//			if (!j.has("s_deadline") || null == j.getString("s_deadline") || j.getString("s_deadline").equals("null")
//					) {
//				hashMap.put("s_deadline", 0);
//			} else {
//
//				hashMap.put("s_deadline", j.optLong("s_deadline", 0));
//			}
//			
////			hashMap.put("cart_count", j.optString("cart_count"));
////			hashMap.put("s_time", j.optLong("s_time", 0));
////			hashMap.put("s_deadline", j.optLong("s_deadline", 0));
//		}
//		return hashMap;
//	}
//
//	public static final HashMap<String, String> createThTkInfo(Context context, String result) throws JSONException {
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (j.optInt("status") == 1) {
//			
//			if (!j.has("useCoupon") || null == j.getString("useCoupon") || j.getString("useCoupon").equals("null")
//					) {
//				retInfo.put("useCoupon", "0");// 优惠券抵用的钱
//			} else {
//
//				retInfo.put("useCoupon", j.getString("useCoupon"));// 优惠券抵用的钱
//			}
//			
//			
//			if (!j.has("useInegral") || null == j.getString("useInegral") || j.getString("useInegral").equals("null")
//					) {
//				retInfo.put("useInegral", "0");// 优惠券抵用的钱
//			} else {
//
//				retInfo.put("useInegral", j.getString("useInegral"));// 优惠券抵用的钱
//			}
//			
//			if (!j.has("money") || null == j.getString("money") || j.getString("money").equals("null")
//					) {
//				retInfo.put("money", "0");// 优惠券抵用的钱
//			} else {
//
//				retInfo.put("money", j.getString("money"));// 优惠券抵用的钱
//			}
////			retInfo.put("useCoupon", j.getString("useCoupon"));// 优惠券抵用的钱
////			retInfo.put("useInegral", j.getString("useInegral"));// 积分抵用的钱
////			retInfo.put("money", j.getString("money"));// 可退金额
//		}
//
//		return retInfo;
//	}
//
//	public static final HashMap<String, Object> createReturnShopNew(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (j.optInt("status") == 1) {
//			ReturnShop rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
//			retInfo.put("returnShop", rShop);
//		}
//
//		return retInfo;
//	}
//
//	// 夺宝退款
//	public static final HashMap<String, Object> createReturnDuobaoShopNew(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (j.optInt("status") == 1) {
//			ReturnShop rShop = JSON.parseObject(j.getString("returnShop"), ReturnShop.class);
//			retInfo.put("returnShop", rShop);
//		}
//
//		return retInfo;
//	}
//
//	public static final List<ReturnShop> createReturnShopList(Context context, String result) throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		retInfo.put("status", j.getString("status"));
//		retInfo.put("message", j.getString("message"));
//		if (j.optInt("status") == 1) {
//
//			List<ReturnShop> listShop = JSON.parseArray(j.optString("data"), ReturnShop.class);
//			return listShop;
//		}
//
//		return null;
//	}
//
//	/***
//	 * 0元购商品属性
//	 */
//	public static final List<StockType> createShopsAttrs(Context context, String result) throws Exception {
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		List<StockType> list = null;
//		if (j.optInt("status") == 1) {
//			list = JSON.parseArray(j.getString("stocktype"), StockType.class);
//		}
//		return list;
//	}
//
//	/***
//	 * 0元购下单
//	 */
//	public static final HashMap<String, Object> createZeroOrder(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		
//		
//		if (!jsonObject.has("order_code") || null == jsonObject.getString("order_code") || jsonObject.getString("order_code").equals("null")
//				) {
//			mapRet.put("order_code", "");
//		} else {
//
//			mapRet.put("order_code", jsonObject.optString("order_code"));
//		}
//		
//		
//		if (!jsonObject.has("price") || null == jsonObject.getString("price") || jsonObject.getString("price").equals("null")
//				) {
//			mapRet.put("price", "0");
//		} else {
//
//			mapRet.put("price", jsonObject.optString("price"));
//		}
//		
//		
//		if (!jsonObject.has("orderToken") || null == jsonObject.getString("orderToken") || jsonObject.getString("orderToken").equals("null")
//				) {
//			YCache.saveOrderToken(context, "");
//		} else {
//
//			YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
//		}
//		
//		if (!jsonObject.has("url") || null == jsonObject.getString("url") || jsonObject.getString("url").equals("null")
//				) {
//			mapRet.put("url", 0);
//		} else {
//
//			mapRet.put("url", jsonObject.optInt("url"));
//		}
//		
//		
////		mapRet.put("order_code", jsonObject.optString("order_code"));
////		mapRet.put("price", jsonObject.optString("price"));
////		YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
////		mapRet.put("url", jsonObject.optInt("url"));
//		mapRet.put("status", jsonObject.optString("status"));
//		mapRet.put("message", jsonObject.optString("message"));
//		return mapRet;
//	}
//
//	/***
//	 * 夺宝下单
//	 */
//	public static final HashMap<String, Object> createDuobaoOrder(Context context, String result) throws Exception {
//
//		// MyLogYiFu.e("是是是", result);
//		// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		// JSONObject jsonObject = new JSONObject(result);
//		// if (null == jsonObject || "".equals(jsonObject)) {
//		// return null;
//		// }
//		//
//		// mapRet.put("status",jsonObject.has("status")?
//		// jsonObject.getString("status"):"");
//		//
//		// JSONArray array = jsonObject.optJSONArray("data");
//		// for (int i = 0; i < array.length(); i++) {
//		//
//		// JSONObject js = array.optJSONObject(i);
//		//
//		// // mapObject.put("ISODate", jo.has("ISODate") ?
//		// // jo.getString("ISODate") : "");
//		//
//		// mapRet.put("order_code", js.optString("order_code"));
//		// mapRet.put("price", js.optString("price"));
//		JSONObject j = new JSONObject(result);
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		if (1 == j.getInt("status")) {
//			JSONObject jj = j.optJSONObject("data");
//			if (null == jj || "".equals(jj)) {
//				return null;
//			}
//			
//			
//			if (!jj.has("price") || null == jj.getString("price") || jj.getString("price").equals("null")
//					) {
//				retInfo.put("price","0");
//			} else {
//
//				retInfo.put("price", jj.has("price") ? jj.getString("price") : "");
//			}
//			
//			if (!jj.has("order_code") || null == jj.getString("order_code") || jj.getString("order_code").equals("null")
//					) {
//				retInfo.put("order_code", "");
//			} else {
//
//				retInfo.put("order_code", jj.has("order_code") ? jj.getString("order_code") : "");
//			}
//			
//			
//
////			retInfo.put("price", jj.has("price") ? jj.getString("price") : "");
////			retInfo.put("order_code", jj.has("order_code") ? jj.getString("order_code") : "");
//
//			return retInfo;
//		} else {
//			return null;
//		}
//
//		/**
//		 * JSONObject jsonObject = new JSONObject(result); if (null ==
//		 * jsonObject || "".equals(jsonObject)) { return null; } if (1 ==
//		 * jsonObject.getInt("status")) { JSONObject j =
//		 * jsonObject.optJSONObject("data"); if (null == j || "".equals(j)) {
//		 * return null; }
//		 * 
//		 * mapRet.put("link", j.has("link") ? j.getString("link") : "");
//		 * mapRet.put("content", j.has("content") ? j.getString("content") :
//		 * ""); mapRet.put("name", j.has("name") ? j.getString("name") : "");
//		 * 
//		 * return mapRet; } else { return null; }
//		 */
//
//	}
//
//	/***
//	 * 0元购下单(签到)
//	 */
//	public static final HashMap<String, Object> createZeroOrderSign(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		
//		if (!jsonObject.has("order_code") || null == jsonObject.getString("order_code") || jsonObject.getString("order_code").equals("null")
//				) {
//			mapRet.put("order_code", "");
//		} else {
//
//			mapRet.put("order_code", jsonObject.optString("order_code"));
//		}
//		
//		if (!jsonObject.has("price") || null == jsonObject.getString("price") || jsonObject.getString("price").equals("null")
//				) {
//			mapRet.put("price", "0");
//		} else {
//
//			mapRet.put("price", jsonObject.optString("price"));
//		}
//		
//		if (!jsonObject.has("orderToken") || null == jsonObject.getString("orderToken") || jsonObject.getString("orderToken").equals("null")
//				) {
//			YCache.saveOrderToken(context, "");
//		} else {
//
//			YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
//		}
//		
//		if (!jsonObject.has("url") || null == jsonObject.getString("url") || jsonObject.getString("url").equals("null")
//				) {
//			mapRet.put("url", 0);
//		} else {
//
//			mapRet.put("url", jsonObject.optInt("url"));
//		}
//		
//		
////		mapRet.put("order_code", jsonObject.optString("order_code"));
////		mapRet.put("price", jsonObject.optString("price"));
////		YCache.saveOrderToken(context, jsonObject.optString("orderToken"));
////		mapRet.put("url", jsonObject.optInt("url"));// (单个还是多个) 这里永远返回1
//													// ,调用单个订单支付就行.
//		mapRet.put("status", jsonObject.optString("status"));
//		mapRet.put("message", jsonObject.optString("message"));
//		return mapRet;
//	}
//
//	/***
//	 * 分享信息
//	 */
//	public static final HashMap<String, Object> createP_ShopLink(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		LogYiFu.e("JSONObject", jsonObject.toString());
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		mapRet.put("status", jsonObject.optInt("status"));
//		mapRet.put("message", jsonObject.optString("message"));
//		
//		if (!jsonObject.has("link") || null == jsonObject.getString("link") || jsonObject.getString("link").equals("null")
//				) {
//			mapRet.put("link", "");
//		} else {
//
//			mapRet.put("link", jsonObject.optString("link"));
//		}
//		
////		mapRet.put("link", jsonObject.optString("link"));
//		
//		
//		if (!jsonObject.has("price") || null == jsonObject.getString("price") || jsonObject.getString("price").equals("null")
//				) {
//			mapRet.put("price", "");
//			
//		} else {
//
//			mapRet.put("price", jsonObject.optString("price"));
//		}
//		
//		
////		mapRet.put("price", jsonObject.optString("price"));
//		if (!TextUtils.isEmpty(jsonObject.optString("Pshop"))) {
//			JSONArray j = new JSONArray(jsonObject.optString("Pshop"));
//			
//			if (null == ((JSONObject) j.opt(0)).optString("four_pic")|| ((JSONObject) j.opt(0)).optString("four_pic").equals("null")
//					) {
//				mapRet.put("four_pic", "");
//			} else {
//				mapRet.put("four_pic", ((JSONObject) j.opt(0)).optString("four_pic"));
//			}
//			
//			
////			mapRet.put("four_pic", ((JSONObject) j.opt(0)).optString("four_pic"));
//			
//			
//			if (null == ((JSONObject) j.opt(0)).optString("shop_code")|| ((JSONObject) j.opt(0)).optString("shop_code").equals("null")
//					) {
//				mapRet.put("shop_code", "");
//			} else {
//				mapRet.put("shop_code", ((JSONObject) j.opt(0)).optString("shop_code"));
//			}
//			
////			mapRet.put("shop_code", ((JSONObject) j.opt(0)).optString("shop_code"));
//			
//			
//			if (null == ((JSONObject) j.opt(0)).optString("shop_pic")|| ((JSONObject) j.opt(0)).optString("shop_pic").equals("null")
//					) {
//				mapRet.put("shop_pic", "");
//			} else {
//				mapRet.put("shop_pic", ((JSONObject) j.opt(0)).optString("shop_pic"));
//			}
//			
////			mapRet.put("shop_pic", ((JSONObject) j.opt(0)).optString("shop_pic"));
//		}
//		/*
//		 * "Pshop": { "num": 300, "price": 0, "r_num": 300, "name": "套餐三",
//		 * "seq": 3, "def_pic": "shop/banner_16.jpg", "code": "AFI125", "type":
//		 * 1, "postage": 11 },
//		 */
//		LogYiFu.e("mapRet", mapRet.toString());
//		return mapRet;
//	}
//
//	/***
//	 * 下单之前
//	 */
//	public static final HashMap<String, Object> preSubmitOrder(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (jsonObject.getInt("status") == 1) {
//			mapRet.put("status", jsonObject.optInt("status"));
//			mapRet.put("message", jsonObject.optString("message"));
//			
//			
//
//			if (!jsonObject.has("integral") || null == jsonObject.getString("integral") || jsonObject.getString("integral").equals("null")
//					) {
//				mapRet.put("integral", 0);
//			} else {
//
//				mapRet.put("integral", jsonObject.optInt("integral"));
//			}
//			
////			mapRet.put("integral", jsonObject.optInt("integral"));
//			
//			if (!jsonObject.has("isOneBuy") || null == jsonObject.getString("isOneBuy") || jsonObject.getString("isOneBuy").equals("null")
//					) {
//				mapRet.put("isOneBuy", 0);
//			} else {
//
//				mapRet.put("isOneBuy", jsonObject.optInt("isOneBuy"));
//			}
//			
//			
////			mapRet.put("isOneBuy", jsonObject.optInt("isOneBuy"));
//			
//			if (!jsonObject.has("needIntegral") || null == jsonObject.getString("needIntegral") || jsonObject.getString("needIntegral").equals("null")
//					) {
//				mapRet.put("needIntegral", 0);
//			} else {
//
//				mapRet.put("needIntegral", jsonObject.optInt("needIntegral"));
//			}
//			
////			mapRet.put("needIntegral", jsonObject.optInt("needIntegral"));
//
//		}
//
//		return mapRet;
//	}
//
//	/***
//	 * 获取我的积分信息
//	 */
//	public static final HashMap<String, Object> createMyIntegral(Context context, String result) throws Exception {
//		HashMap<String, Object> parseObject = null;
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (jsonObject.getInt("status") == 1) {
//			parseObject = JSON.parseObject(result, new TypeReference<HashMap<String, Object>>() {
//			});
//
//		}
//
//		return parseObject;
//	}
//
//	/***
//	 * Help列表
//	 */
//	public static final HashMap<String, Integer> createFinCount(Context context, String result) throws Exception {
//		HashMap<String, Integer> mapRet = new HashMap<String, Integer>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		
//		
//		if (!jsonObject.has("H5Count") || null == jsonObject.getString("H5Count") || jsonObject.getString("H5Count").equals("null")
//				) {
//			mapRet.put("H5Count", 0);
//		} else {
//
//			mapRet.put("H5Count", jsonObject.optInt("H5Count"));
//		}
//		
//		
////		mapRet.put("H5Count", jsonObject.optInt("H5Count"));
//		
//		if (!jsonObject.has("finCount") || null == jsonObject.getString("finCount") || jsonObject.getString("finCount").equals("null")
//				) {
//			mapRet.put("finCount", 0);
//		} else {
//
//			mapRet.put("finCount", jsonObject.optInt("finCount"));
//		}
//		
//		
////		mapRet.put("finCount", jsonObject.optInt("finCount"));
//		return mapRet;
//		
//	}
//	
//}

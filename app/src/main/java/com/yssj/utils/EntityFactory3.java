package com.yssj.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.yssj.Json.RetInfo;
import com.yssj.YConstance.Pref;
import com.yssj.data.YDBHelper;
import com.yssj.entity.DuoBaoJiLu_user;
import com.yssj.entity.FundDetail;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopAttr;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopOption;
import com.yssj.utils.sqlite.ShopCartDao;

import android.content.Context;
import android.text.TextUtils;

//public class EntityFactory3 {
//	/***
//	 * 订单列表
//	 */
//	public static final Order createOrder(Context context, String result) throws Exception {
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		Order order = JSON.parseObject(jsonObject.optString("order"), Order.class);
//		// List<OrderShop> list =
//		// JSON.parseArray(jsonObject.optString("orderShops"), OrderShop.class);
//		List<OrderShop> list = new ArrayList<OrderShop>();
//		String str = jsonObject.optString("orderShops");
//
//		if (str != null || !("".equals(str))) {
//			JSONArray ja = new JSONArray(str);
//			for (int i = 0; i < ja.length(); i++) {
//				JSONObject j = (JSONObject) ja.get(i);
//				OrderShop shop = new OrderShop();
//				if (!j.has("order_code")||null == j.getString("id") || "null".equals(j.getString("id")) || "".equals(j.getString("id"))) {
//					shop.setId(-1);
//				}else{
//					shop.setId(j.has("id") ? j.optInt("id") : -1);	
//				}
//				if (!j.has("order_code")||null == j.getString("order_code") || "null".equals(j.getString("order_code")) || "".equals(j.getString("order_code"))) {
//					shop.setOrder_code(-1+"");
//				}else{
//					shop.setOrder_code(j.has("order_code") ? j.optString("order_code") : -1 + "");
//				}
//				if (!j.has("shop_name")||null == j.getString("shop_name") || 
//						"".equals(j.getString("shop_name"))) {
//					shop.setShop_name("");
//				}else{
//					shop.setShop_name(j.has("shop_name") ? j.optString("shop_name") : "");
//				}
//				if (!j.has("shop_code")||null == j.getString("shop_code") || 
//						"null".equals(j.getString("shop_code")) || 
//						"".equals(j.getString("shop_code"))) {
//					shop.setShop_code(-1+"");
//				}else{
//					shop.setShop_code(j.has("shop_code") ? j.optString("shop_code") : -1 + "");
//				}
//				if (!j.has("shop_price")||null == j.getString("shop_price") || 
//						"null".equals(j.getString("shop_price")) || 
//						"".equals(j.getString("shop_price"))) {
//					shop.setShop_price(-1.0);
//				}else{
//					shop.setShop_price(j.has("shop_price") ? j.optDouble("shop_price") : -1);
//				}
//				if (!j.has("shop_pic")||null == j.getString("shop_pic") || 
//						"".equals(j.getString("shop_pic"))) {
//					shop.setShop_pic(-1+"");
//				}else{
//					shop.setShop_pic(j.has("shop_pic") ? j.optString("shop_pic") : -1 + "");
//				}
//				if (!j.has("shop_num")||null == j.getString("shop_num") || 
//						"null".equals(j.getString("shop_num")) || 
//						"".equals(j.getString("shop_num"))) {
//					shop.setShop_num(-1);
//				}else{
//					shop.setShop_num(j.has("shop_num") ? j.optInt("shop_num") : -1);
//				}
//				if (!j.has("size")||null == j.getString("size") || 
//						"null".equals(j.getString("size")) || 
//						"".equals(j.getString("size"))) {
//					shop.setSize(-1+"");
//				}else{
//					shop.setSize(j.has("size") ? j.optString("size") : -1 + "");
//				}
//				if (!j.has("color")||null == j.getString("color") || 
//						"null".equals(j.getString("color")) || 
//						"".equals(j.getString("color"))) {
//					shop.setColor(-1+"");
//				}else{
//					shop.setColor(j.has("color") ? j.optString("color") : -1 + "");
//				}
//				if (!j.has("logi_code")||null == j.getString("logi_code") || 
//						"null".equals(j.getString("logi_code")) || 
//						"".equals(j.getString("logi_code"))) {
//					shop.setLogi_code(-1+"");
//				}else{
//					shop.setLogi_code(j.has("logi_code") ? j.optString("logi_code") : -1 + "");
//				}
//				if (!j.has("message")||null == j.getString("message") || 
//						"".equals(j.getString("message"))) {
//					shop.setMessage(-1+"");
//				}else{
//					shop.setMessage(j.has("message") ? j.optString("message") : -1 + "");
//				}
//				if (!j.has("bak")||null == j.getString("bak") || 
//						"null".equals(j.getString("bak")) || 
//						"".equals(j.getString("bak"))) {
//					shop.setBak(-1+"");
//				}else{
//					shop.setBak(j.has("bak") ? j.optString("bak") : -1 + "");
//				}
//				
//				if (!j.has("stocktypeid")||null == j.getString("stocktypeid") || 
//						"null".equals(j.getString("stocktypeid")) || 
//						"".equals(j.getString("stocktypeid"))) {
//					shop.setStocktypeid(-1);
//				}else{
//					shop.setStocktypeid(j.has("stocktypeid") ? j.optInt("stocktypeid") : -1);
//				}
//				if (!j.has("change")||null == j.getString("change") || 
//						"null".equals(j.getString("change")) || 
//						"".equals(j.getString("change"))) {
//					shop.setChange(-1);
//				}else{
//					shop.setChange(j.has("change") ? j.optInt("change") : -1);
//				}
//				if (!j.has("last_time")||null == j.getString("last_time") || 
//						"null".equals(j.getString("last_time")) || 
//						"".equals(j.getString("last_time"))) {
//					shop.setLast_timess(-1L);
//				}else{
//					shop.setLast_timess(j.has("last_time") ? j.optLong("last_time") : -1L);
//				}
//				list.add(shop);
//			}
//		}
//
//		if (list != null && list.size() > 0) {
//			order.setList(list);
//			return order;
//		}
//		return order;
//	}
//
//	/**
//	 * 获取会员商品列表
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<Map<String, String>> createMembersShopList(Context context, String result) throws Exception {
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//
//		JSONObject jsonObject = new JSONObject(result);
//
//		JSONArray jsonArray = jsonObject.optJSONArray("data");
//
//		if (jsonArray == null || jsonArray.length() == 0) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//
//			JSONObject j = jsonArray.getJSONObject(i);
//
//			Map<String, String> map = new HashMap<String, String>();
//
//			if (!j.has("shop_code")||null == j.getString("shop_code") || 
//					"null".equals(j.getString("shop_code")) || 
//					"".equals(j.getString("shop_code"))) {
//				map.put("shop_code","");
//			}else{
//				map.put("shop_code", j.optString("shop_code", ""));
//			}
//			if (!j.has("shop_name")||null == j.getString("shop_name") || 
//					"null".equals(j.getString("shop_name")) || 
//					"".equals(j.getString("shop_name"))) {
//				map.put("shop_name","");
//			}else{
//				map.put("shop_name", j.optString("shop_name", ""));
//			}
//			if (!j.has("shop_se_price")||null == j.getString("shop_se_price") || 
//					"null".equals(j.getString("shop_se_price")) || 
//					"".equals(j.getString("shop_se_price"))) {
//				map.put("shop_se_price","0");
//			}else{
//				map.put("shop_se_price", j.optString("shop_se_price", "0"));
//			}
//			if (!j.has("def_pic")||null == j.getString("def_pic") || 
//					"null".equals(j.getString("def_pic")) || 
//					"".equals(j.getString("def_pic"))) {
//				map.put("def_pic","");
//			}else{
//				map.put("def_pic", j.optString("def_pic", ""));
//			}
//			list.add(map);
//		}
//
//		return list.isEmpty() ? null : list;
//	}
//
//	/***
//	 * 会员验证
//	 */
//	public static final HashMap<String, Object> createMemberVerify(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//				"null".equals(jsonObject.getString("status")) || 
//				"".equals(jsonObject.getString("status"))) {
//			mapRet.put("status","");
//		}else{
//			mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
//		}
//		if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//				"".equals(jsonObject.getString("message"))) {
//			mapRet.put("message","");
//		}else{
//			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//		}
//		if (!jsonObject.has("is_member")||null == jsonObject.getString("is_member") || 
//				"null".equals(jsonObject.getString("is_member")) || 
//				"".equals(jsonObject.getString("is_member"))) {
//			mapRet.put("is_member","");
//		}else{
//			mapRet.put("is_member", jsonObject.has("is_member") ? jsonObject.optInt("is_member") : "");
//		}
//		return mapRet;
//	}
//
//	/**
//	 * 获取会员轮播
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//
//	public static List<ShopOption> createMembersImgList(Context context, String result) throws Exception {
//		List<ShopOption> list = new ArrayList<ShopOption>();
//
//		JSONObject jsonObject = new JSONObject(result);
//
//		JSONArray jsonArray = jsonObject.optJSONArray("topShops");
//
//		if (jsonArray == null || jsonArray.length() == 0) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//
//			JSONObject j = jsonArray.getJSONObject(i);
//
//			ShopOption shop = new ShopOption();
//
//			if (!j.has("id")||null == j.getString("id") || 
//					"null".equals(j.getString("id")) || 
//					"".equals(j.getString("id"))) {
//				shop.setId(0);
//			}else{
//				shop.setId(j.optInt("id", 0));
//			}
//			if (!j.has("remark")||null == j.getString("remark") || 
//					"null".equals(j.getString("remark")) || 
//					"".equals(j.getString("remark"))) {
//				shop.setRemark("");
//			}else{
//				shop.setRemark(j.optString("remark", ""));
//			}
//			if (!j.has("shop_code")||null == j.getString("shop_code") || 
//					"null".equals(j.getString("shop_code")) || 
//					"".equals(j.getString("shop_code"))) {
//				shop.setShop_code("");
//			}else{
//				shop.setShop_code(j.optString("shop_code", ""));
//			}
//			if (!j.has("shop_name")||null == j.getString("shop_name") || 
//					"null".equals(j.getString("shop_name")) || 
//					"".equals(j.getString("shop_name"))) {
//				shop.setShop_name("");
//			}else{
//				shop.setShop_name(j.optString("shop_name", ""));
//			}
//			if (!j.has("type")||null == j.getString("type") || 
//					"null".equals(j.getString("type")) || 
//					"".equals(j.getString("type"))) {
//				shop.setType(0);
//			}else{
//				shop.setType(j.optInt("type", 0));
//			}
//			if (!j.has("url")||null == j.getString("url") || 
//					"null".equals(j.getString("url")) || 
//					"".equals(j.getString("url"))) {
//				shop.setUrl("");
//			}else{
//				shop.setUrl(j.optString("url", ""));
//			}
//			list.add(shop);
//		}
//
//		return list.isEmpty() ? null : list;
//	}
//
//	/***
//	 * 获取会员卡
//	 */
//	public static final HashMap<String, Object> createQueryVipCard(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//				"null".equals(jsonObject.getString("status")) || 
//				"".equals(jsonObject.getString("status"))) {
//			mapRet.put("status","");
//		}else{
//			mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
//		}
//		if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//				"null".equals(jsonObject.getString("message")) || 
//				"".equals(jsonObject.getString("message"))) {
//			mapRet.put("message","");
//		}else{
//			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//		}
//		if (!jsonObject.has("card_no")||null == jsonObject.getString("card_no") || 
//				"null".equals(jsonObject.getString("card_no")) || 
//				"".equals(jsonObject.getString("card_no"))) {
//			mapRet.put("card_no","");
//		}else{
//			mapRet.put("card_no", jsonObject.has("card_no") ? jsonObject.getString("card_no") : "");
//		}
//		if (!jsonObject.has("plaintext")||null == jsonObject.getString("plaintext") || 
//				"null".equals(jsonObject.getString("plaintext")) || 
//				"".equals(jsonObject.getString("plaintext"))) {
//			mapRet.put("plaintext","");
//		}else{
//			mapRet.put("plaintext", jsonObject.has("plaintext") ? jsonObject.getString("plaintext") : "");
//		}
//		if (!jsonObject.has("time")||null == jsonObject.getString("time") || 
//				"null".equals(jsonObject.getString("time")) || 
//				"".equals(jsonObject.getString("time"))) {
//			mapRet.put("time","0");
//		}else{
//			mapRet.put("time", jsonObject.has("time") ? jsonObject.getString("time") : "0");
//		}
//		return mapRet;
//	}
//
//	/***
//	 * 获取用户头像
//	 */
//	public static final String createQueryUserPic(Context context, String result) throws Exception {
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!jsonObject.has("pic")||null == jsonObject.getString("pic") || 
//				"null".equals(jsonObject.getString("pic")) || 
//				"".equals(jsonObject.getString("pic"))) {
//			return "";
//		}else{
//			return jsonObject.has("pic") ? jsonObject.getString("pic") : "";
//		}
//	}
//
//	/**
//	 * 超级合伙人卡号列表
//	 */
//	public static final List<HashMap<String, Object>> createSupermanCardList(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		if (!j.has("status")||null == j.getString("status") || 
//				"null".equals(j.getString("status")) || 
//				"".equals(j.getString("status"))) {
//			return null;
//		}
//		if (1 == j.getInt("status")) {
//			JSONArray jsonArray = new JSONArray(j.getString("cardList"));
//			if (null == jsonArray || "".equals(jsonArray)) {
//				return null;
//			}
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jo = (JSONObject) jsonArray.opt(i);
//				HashMap<String, Object> mapObject = new HashMap<String, Object>();
//				if (!jo.has("card_no")||null == jo.getString("card_no") || 
//						"null".equals(jo.getString("card_no")) || 
//						"".equals(jo.getString("card_no"))) {
//					mapObject.put("card_no","");
//				}else{
//					mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no") : "");
//				}
//				if (!jo.has("id")||null == jo.getString("id") || 
//						"null".equals(jo.getString("id")) || 
//						"".equals(jo.getString("id"))) {
//					mapObject.put("id","");
//				}else{
//					mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
//				}
//				if (!jo.has("plaintext")||null == jo.getString("plaintext") || 
//						"null".equals(jo.getString("plaintext")) || 
//						"".equals(jo.getString("plaintext"))) {
//					mapObject.put("plaintext","");
//				}else{
//					mapObject.put("plaintext", jo.has("plaintext") ? jo.getString("plaintext") : "");
//				}
//				if (!jo.has("buyer_id")||null == jo.getString("buyer_id") || 
//						"null".equals(jo.getString("buyer_id")) || 
//						"".equals(jo.getString("buyer_id"))) {
//					mapObject.put("buyer_id","");
//				}else{
//					mapObject.put("buyer_id", jo.has("buyer_id") ? jo.getString("buyer_id") : "");
//				}
//				if (!jo.has("is_use")||null == jo.getString("is_use") || 
//						"null".equals(jo.getString("is_use")) || 
//						"".equals(jo.getString("is_use"))) {
//					mapObject.put("is_use","");
//				}else{
//					mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") : "");
//				}
//				if (!jo.has("nickname")||null == jo.getString("nickname") || 
//						"null".equals(jo.getString("nickname")) || 
//						"".equals(jo.getString("nickname"))) {
//					mapObject.put("nickname","");
//				}else{
//					mapObject.put("nickname", jo.has("nickname") ? jo.getString("nickname") : "");
//				}
//				list.add(mapObject);
//			}
//		} else
//			return null;
//
//		return list;
//	}
//
//	/**
//	 * 强制浏览
//	 */
//	////////
//	public static final List<HashMap<String, Object>> createForceLook(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		// System.out.println("这是强制浏览的result=" + result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("list"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.get(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//
//			if (!jo.has("shop_code")||null == jo.getString("shop_code") || 
//					"null".equals(jo.getString("shop_code")) || 
//					"".equals(jo.getString("shop_code"))) {
//				mapObject.put("shop_code","");
//			}else{
//				mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
//			}
//			if (!jo.has("shop_name")||null == jo.getString("shop_name") || 
//					"null".equals(jo.getString("shop_name")) || 
//					"".equals(jo.getString("shop_name"))) {
//				mapObject.put("shop_name","");
//			}else{
//				mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//			}
//			if (!jo.has("shop_price")||null == jo.getString("shop_price") || 
//					"null".equals(jo.getString("shop_price")) || 
//					"".equals(jo.getString("shop_price"))) {
//				mapObject.put("shop_price","0");
//			}else{
//				mapObject.put("shop_price", jo.has("shop_price") ? jo.getString("shop_price") : "0");
//			}
//			if (!jo.has("shop_se_price")||null == jo.getString("shop_se_price") || 
//					"null".equals(jo.getString("shop_se_price")) || 
//					"".equals(jo.getString("shop_se_price"))) {
//				mapObject.put("shop_se_price","0");
//			}else{
//				mapObject.put("shop_se_price", jo.has("shop_se_price") ? jo.getString("shop_se_price") : "0");
//			}
//			if (!jo.has("def_pic")||null == jo.getString("def_pic") || 
//					"null".equals(jo.getString("def_pic")) || 
//					"".equals(jo.getString("def_pic"))) {
//				mapObject.put("def_pic","");
//			}else{
//				mapObject.put("def_pic", jo.has("def_pic") ? jo.getString("def_pic") : "");
//			}
//			if (!jo.has("kickback")||null == jo.getString("kickback") || 
//					"null".equals(jo.getString("kickback")) || 
//					"".equals(jo.getString("kickback"))) {
//				mapObject.put("kickback","0");
//			}else{
//				mapObject.put("kickback", jo.has("kickback") ? jo.getString("kickback") : "0");
//			}
//			list.add(mapObject);
//		}
//
//		// for (int i = 0; i < jsonArray.length(); i++) {
//		// JSONObject jo = (JSONObject) jsonArray.opt(i);
//		// HashMap<String, Object> mapObject = new HashMap<String, Object>();
//		// mapObject.put("card_no", jo.has("card_no") ? jo.getString("card_no")
//		// : "");
//		// mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
//		// mapObject.put("plaintext", jo.has("plaintext") ?
//		// jo.getString("plaintext") : "");
//		// mapObject.put("buyer_id", jo.has("buyer_id") ?
//		// jo.getString("buyer_id") : "");
//		// mapObject.put("is_use", jo.has("is_use") ? jo.getString("is_use") :
//		// "");
//		// mapObject.put("nickname", jo.has("nickname") ?
//		// jo.getString("nickname") : "");
//		// list.add(mapObject);
//		// }
//
//		return list;
//	}
//
//	///////
//	/***
//	 * 检测uid
//	 */
//	public static final HashMap<String, Object> createUID(Context context, String result) throws JSONException {
//		HashMap<String, Object> mapObject = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("flag")||null == j.getString("flag") || 
//				"null".equals(j.getString("flag")) || 
//				"".equals(j.getString("flag"))) {
//			mapObject.put("flag","");
//		}else{
//			mapObject.put("flag", (j.has("flag") ? j.getString("flag") : ""));
//		}
//		if (!j.has("uid")||null == j.getString("uid") || 
//				"null".equals(j.getString("uid")) || 
//				"".equals(j.getString("uid"))) {
//			mapObject.put("uid","");
//			YCache.getCacheUser(context).setUuid("");
//		}else{
//			mapObject.put("uid", j.has("uid") ? j.getString("uid") : "");
//			YCache.getCacheUser(context).setUuid(j.getString("uid"));
//		}
//
//		return mapObject;
//	}
//
//	/***
//	 * 塞进红包
//	 */
//	public static final HashMap<String, Object> creatRedPackets(Context context, String result) throws JSONException {
//		HashMap<String, Object> mapObject = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (!j.has("rp_id")||null == j.getString("rp_id") || 
//				"null".equals(j.getString("rp_id")) || 
//				"".equals(j.getString("rp_id"))) {
//			mapObject.put("rp_id","");
//		}else{
//			mapObject.put("rp_id", (j.has("rp_id") ? j.getString("rp_id") : ""));
//		}
//		if (!j.has("message")||null == j.getString("message") || 
//				"null".equals(j.getString("message")) || 
//				"".equals(j.getString("message"))) {
//			mapObject.put("message","");
//		}else{
//			mapObject.put("message", j.has("message") ? j.getString("message") : "");
//		}
//		if (!j.has("status")||null == j.getString("status") || 
//				"null".equals(j.getString("status")) || 
//				"".equals(j.getString("status"))) {
//			mapObject.put("status","");
//		}else{
//			mapObject.put("status", j.has("status") ? j.getString("status") : "");
//		}
//
//		return mapObject;
//	}
//
//	/***
//	 * 获取签到首页数据
//	 */
//	public static final HashMap<String, Object> createSignData(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//				"null".equals(jsonObject.getString("status")) || 
//				"".equals(jsonObject.getString("status"))) {
//			return null;
//		}
//		if (1 == jsonObject.getInt("status")) {
//
//			if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//					"null".equals(jsonObject.getString("status")) || 
//					"".equals(jsonObject.getString("status"))) {
//				mapRet.put("status","");
//			}else{
//				mapRet.put("status", jsonObject.has("status") ? jsonObject.getString("status") : "");
//			}
//			if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//					"null".equals(jsonObject.getString("message")) || 
//					"".equals(jsonObject.getString("message"))) {
//				mapRet.put("message","");
//			}else{
//				mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			}
//			if (!jsonObject.has("sCount")||null == jsonObject.getString("sCount") || 
//					"null".equals(jsonObject.getString("sCount")) || 
//					"".equals(jsonObject.getString("sCount"))) {
//				mapRet.put("sCount","0");
//			}else{
//				mapRet.put("sCount", jsonObject.has("sCount") ? jsonObject.getString("sCount") : "0");
//			}
//			if (!jsonObject.has("iCount")||null == jsonObject.getString("iCount") || 
//					"null".equals(jsonObject.getString("iCount")) || 
//					"".equals(jsonObject.getString("iCount"))) {
//				mapRet.put("iCount","0");
//			}else{
//				mapRet.put("iCount", jsonObject.has("iCount") ? jsonObject.getString("iCount") : "0");
//			}
//			if (!jsonObject.has("bCount")||null == jsonObject.getString("bCount") || 
//					"null".equals(jsonObject.getString("bCount")) || 
//					"".equals(jsonObject.getString("bCount"))) {
//				mapRet.put("bCount","0");
//			}else{
//				mapRet.put("bCount", jsonObject.has("bCount") ? jsonObject.getDouble("bCount") : "0");
//			}
//			if (!jsonObject.has("cCount")||null == jsonObject.getString("cCount") || 
//					"null".equals(jsonObject.getString("cCount")) || 
//					"".equals(jsonObject.getString("cCount"))) {
//				mapRet.put("cCount","0");
//			}else{
//				mapRet.put("cCount", jsonObject.has("cCount") ? jsonObject.getString("cCount") : "0");
//			}
//			return mapRet;
//		}
//
//		return null;
//	}
//
//	/***
//	 * 获取红包详情
//	 */
//	public static final HashMap<String, Object> creatRedpackets(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (1 == jsonObject.getInt("status")) {
//			JSONObject j = jsonObject.optJSONObject("data");
//			if (null == j || "".equals(j)) {
//				return null;
//			}
//			if (!j.has("link")||null == j.getString("link") || 
//					"null".equals(j.getString("link")) || 
//					"".equals(j.getString("link"))) {
//				mapRet.put("link","");
//			}else{
//				mapRet.put("link", j.has("link") ? j.getString("link") : "");
//			}
//			if (!j.has("content")||null == j.getString("content") || 
//					"null".equals(j.getString("content")) || 
//					"".equals(j.getString("content"))) {
//				mapRet.put("content","");
//			}else{
//				mapRet.put("content", j.has("content") ? j.getString("content") : "");
//			}
//			if (!j.has("name")||null == j.getString("name") || 
//					"".equals(j.getString("name"))) {
//				mapRet.put("name","");
//			}else{
//				mapRet.put("name", j.has("name") ? j.getString("name") : "");
//			}
//
//			return mapRet;
//		} else {
//			return null;
//		}
//	}
//
//	/**
//	 * 签到任务列表
//	 */
//	public static final HashMap<String, List<HashMap<String, Object>>> createSignList(Context context, String result)
//			throws JSONException
//
//	{
//
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//
//		// MyLogYiFu.e("resultA", result + "");
//		HashMap<String, List<HashMap<String, Object>>> maplist = new HashMap<String, List<HashMap<String, Object>>>();
//
//		List<HashMap<String, Object>> taskList = new ArrayList<HashMap<String, Object>>();
//		if (1 == j.getInt("status")) {
//
//			JSONArray jsonArray = new JSONArray(j.getString("taskList"));
//			if (null == jsonArray || "".equals(jsonArray)) {
//				return null;
//			}
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jo = (JSONObject) jsonArray.opt(i);
//				HashMap<String, Object> mapObject = new HashMap<String, Object>();
//				if (!jo.has("t_id")||null == jo.getString("t_id") || 
//						"null".equals(jo.getString("t_id")) || 
//						"".equals(jo.getString("t_id"))) {
//					mapObject.put("t_id","");
//				}else{
//					mapObject.put("t_id", jo.has("t_id") ? jo.getString("t_id") : "");
//				}
//				if (!jo.has("t_name")||null == jo.getString("t_name") || 
//						"null".equals(jo.getString("t_name")) || 
//						"".equals(jo.getString("t_name"))) {
//					mapObject.put("t_name","");
//				}else{
//					mapObject.put("t_name", jo.has("t_name") ? jo.getString("t_name") : "");
//				}
//				if (!jo.has("type_id")||null == jo.getString("type_id") || 
//						"null".equals(jo.getString("type_id")) || 
//						"".equals(jo.getString("type_id"))) {
//					mapObject.put("type_id","");
//				}else{
//					mapObject.put("type_id", jo.has("type_id") ? jo.getString("type_id") : "");
//				}
//				if (!jo.has("add_time")||null == jo.getString("add_time") || 
//						"null".equals(jo.getString("add_time")) || 
//						"".equals(jo.getString("add_time"))) {
//					mapObject.put("add_time","0");
//				}else{
//					mapObject.put("add_time", jo.has("add_time") ? jo.getString("add_time") : "0");
//				}
//				if (!jo.has("ISODate")||null == jo.getString("ISODate") || 
//						"null".equals(jo.getString("ISODate")) || 
//						"".equals(jo.getString("ISODate"))) {
//					mapObject.put("ISODate","");
//				}else{
//					mapObject.put("ISODate", jo.has("ISODate") ? jo.getString("ISODate") : "");
//				}
//				if (!jo.has("is_show")||null == jo.getString("is_show") || 
//						"null".equals(jo.getString("is_show")) || 
//						"".equals(jo.getString("is_show"))) {
//					mapObject.put("is_show","");
//				}else{
//					mapObject.put("is_show", jo.has("is_show") ? jo.getString("is_show") : "");
//				}
//				if (!jo.has("value")||null == jo.getString("value") || 
//						"null".equals(jo.getString("value")) || 
//						"".equals(jo.getString("value"))) {
//					mapObject.put("value","0");
//				}else{
//					mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//				}
//				if (!jo.has("condition")||null == jo.getString("condition") || 
//						"null".equals(jo.getString("condition")) || 
//						"".equals(jo.getString("condition"))) {
//					mapObject.put("condition","");
//				}else{
//					mapObject.put("condition", jo.has("condition") ? jo.getString("condition") : "");
//				}
//				taskList.add(mapObject);
//			}
//
//			maplist.put("taskList", taskList);
//
//			List<HashMap<String, Object>> motaskList = new ArrayList<HashMap<String, Object>>();
//
//			JSONArray jsonArraymotaskList = new JSONArray(j.getString("motaskList"));
//
//			if (null == jsonArraymotaskList || "".equals(jsonArraymotaskList)) {
//				return null;
//			}
//
//			// MyLogYiFu.e("jsonArraymotaskList", jsonArraymotaskList + "");
//			for (int i = 0; i < jsonArraymotaskList.length(); i++) {
//				JSONObject jo = (JSONObject) jsonArraymotaskList.opt(i);
//				HashMap<String, Object> mapObject = new HashMap<String, Object>();
//				if (!jo.has("id")||null == jo.getString("id") || 
//						"null".equals(jo.getString("id")) || 
//						"".equals(jo.getString("id"))) {
//					mapObject.put("id","");
//				}else{
//					mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
//				}
//				if (!jo.has("value")||null == jo.getString("value") || 
//						"null".equals(jo.getString("value")) || 
//						"".equals(jo.getString("value"))) {
//					mapObject.put("value","0");
//				}else{
//					mapObject.put("value", jo.has("value") ? jo.getString("value") : "0");
//				}
//				if (!jo.has("type")||null == jo.getString("type") || 
//						"null".equals(jo.getString("type")) || 
//						"".equals(jo.getString("type"))) {
//					mapObject.put("type","");
//				}else{
//					mapObject.put("type", jo.has("type") ? jo.getString("type") : "");
//				}
//				if (!jo.has("index")||null == jo.getString("index") || 
//						"null".equals(jo.getString("index")) || 
//						"".equals(jo.getString("index"))) {
//					mapObject.put("index","");
//				}else{
//					mapObject.put("index", jo.has("index") ? jo.getString("index") : "");
//				}
//
//				motaskList.add(mapObject);
//			}
//			maplist.put("motaskList", motaskList);
//
//			List<HashMap<String, Object>> taskTypeList = new ArrayList<HashMap<String, Object>>();
//
//			JSONArray jsonArraytaskTypeList = new JSONArray(j.getString("taskTypeList"));
//			if (null == jsonArraytaskTypeList || "".equals(jsonArraytaskTypeList)) {
//				return null;
//			}
//			for (int i = 0; i < jsonArraytaskTypeList.length(); i++) {
//				JSONObject jo = (JSONObject) jsonArraytaskTypeList.opt(i);
//				HashMap<String, Object> mapObject = new HashMap<String, Object>();
//				if (!jo.has("id")||null == jo.getString("id") || 
//						"null".equals(jo.getString("id")) || 
//						"".equals(jo.getString("id"))) {
//					mapObject.put("id","");
//				}else{
//					mapObject.put("id", jo.has("id") ? jo.getString("id") : "");
//				}
//				if (!jo.has("t_name")||null == jo.getString("t_name") || 
//						"null".equals(jo.getString("t_name")) || 
//						"".equals(jo.getString("t_name"))) {
//					mapObject.put("t_name","");
//				}else{
//					mapObject.put("t_name", jo.has("t_name") ? jo.getString("t_name") : "");
//				}
//				if (!jo.has("type_id")||null == jo.getString("type_id") || 
//						"null".equals(jo.getString("type_id")) || 
//						"".equals(jo.getString("type_id"))) {
//					mapObject.put("type_id","");
//				}else{
//					mapObject.put("type_id", jo.has("type_id") ? jo.getString("type_id") : "");
//				}
//				if (!jo.has("add_time")||null == jo.getString("add_time") || 
//						"null".equals(jo.getString("add_time")) || 
//						"".equals(jo.getString("add_time"))) {
//					mapObject.put("add_time","0");
//				}else{
//					mapObject.put("add_time", jo.has("add_time") ? jo.getString("add_time") : "0");
//				}
//				if (!jo.has("ISODate")||null == jo.getString("ISODate") || 
//						"null".equals(jo.getString("ISODate")) || 
//						"".equals(jo.getString("ISODate"))) {
//					mapObject.put("ISODate","");
//				}else{
//					mapObject.put("ISODate", jo.has("ISODate") ? jo.getString("ISODate") : "");
//				}
//				if (!jo.has("is_show")||null == jo.getString("is_show") || 
//						"null".equals(jo.getString("is_show")) || 
//						"".equals(jo.getString("is_show"))) {
//					mapObject.put("is_show","");
//				}else{
//					mapObject.put("is_show", jo.has("is_show") ? jo.getString("is_show") : "");
//				}
//				taskTypeList.add(mapObject);
//
//			}
//
//			maplist.put("taskTypeList", taskTypeList);
//
//			return maplist;
//
//		}
//
//		return null;
//	}
//
//	public static final List<HashMap<String, Object>> createSignYetList(Context context, String result)
//			throws JSONException {
//
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
//
//		if (1 == jsonObject.getInt("status")) {
//
//			JSONArray jsonArray = new JSONArray(jsonObject.getString("taskList"));
//
//			LogYiFu.e("jsonArray", jsonArray + "");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject obj = (JSONObject) jsonArray.opt(i);
//				HashMap<String, Object> mapMission = new HashMap<String, Object>();
//				if (!obj.has("add_time")||null == obj.getString("add_time") || 
//						"null".equals(obj.getString("add_time")) || 
//						"".equals(obj.getString("add_time"))) {
//					mapMission.put("add_time","0");
//				}else{
//					mapMission.put("add_time", obj.getString("add_time"));
//				}
//				if (!obj.has("t_id")||null == obj.getString("t_id") || 
//						"null".equals(obj.getString("t_id")) || 
//						"".equals(obj.getString("t_id"))) {
//					mapMission.put("t_id","");
//				}else{
//					mapMission.put("t_id", obj.getString("t_id"));
//				}
//				listMap.add(mapMission);
//			}
//
//			String signIn_status = jsonObject.getString("signIn_status");
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			if (!jsonObject.has("signIn_status")||null == jsonObject.getString("signIn_status") || 
//					"null".equals(jsonObject.getString("signIn_status")) || 
//					"".equals(jsonObject.getString("signIn_status"))) {
//				map.put("signIn_status","");
//			}else{
//				map.put("signIn_status", signIn_status);
//			}
//			listMap.add(map); // 签到状态存放在返回list中最后1个元素
//
//			// context.getSharedPreferences("sign", Context.MODE_PRIVATE)
//			// .edit()
//			// .putString("signIn_status",
//			// jsonObject.getString("signIn_status")).commit();// 签到状态已存放在本地
//			return listMap;
//		}
//
//		return null;
//	}
//
//	public static final HashMap<String, Object> createMyShopLink(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//
//		LogYiFu.e("result555", result + "");
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//					"null".equals(jsonObject.getString("message")) || 
//					"".equals(jsonObject.getString("message"))) {
//				mapRet.put("message","");
//			}else{
//				mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			}
//			// context.getSharedPreferences("buqianka", Context.MODE_PRIVATE)
//			// .edit()
//			// .putBoolean(
//			// "bool",
//			// jsonObject.has("bool") ? jsonObject
//			// .getBoolean("bool") : false).commit();
//			// mapRet.put("bool",
//			// jsonObject.has("bool") ? jsonObject.getString("bool") : "");
//			if (!jsonObject.has("url")||null == jsonObject.getString("url") || 
//					"null".equals(jsonObject.getString("url")) || 
//					"".equals(jsonObject.getString("url"))) {
//				mapRet.put("url","");
//			}else{
//				mapRet.put("url", jsonObject.has("url") ? jsonObject.getString("url") : "");
//			}
//			if (!jsonObject.has("pic")||null == jsonObject.getString("pic") || 
//					"null".equals(jsonObject.getString("pic")) || 
//					"".equals(jsonObject.getString("pic"))) {
//				mapRet.put("pic","");
//			}else{
//				mapRet.put("pic", jsonObject.has("pic") ? jsonObject.getString("pic") : "");
//			}
//
//			return mapRet;
//		}
//
//		return null;
//
//	}
//
//	// /***
//	// * 检查是否有补签卡
//	// */
//	// public static final HashMap<String, Object> createCheckbuqianka(
//	// Context context, String result) throws Exception {
//	// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//	// JSONObject jsonObject = new JSONObject(result);
//	// if (null == jsonObject || "".equals(jsonObject)) {
//	// return null;
//	// }
//	//
//	// if (1 == jsonObject.getInt("status")) {
//	// mapRet.put("message",
//	// jsonObject.has("message") ? jsonObject.getString("message")
//	// : "");
//	// context.getSharedPreferences("buqianka", Context.MODE_PRIVATE)
//	// .edit()
//	// .putBoolean(
//	// "bool",
//	// jsonObject.has("bool") ? jsonObject
//	// .getBoolean("bool") : false).commit();
//	// mapRet.put("bool",
//	// jsonObject.has("bool") ? jsonObject.getString("bool") : "");
//	// return mapRet;
//	// }
//	//
//	// return null;
//	//
//	// }
//
//	/***
//	 * 签到
//	 */
//	public static final HashMap<String, Object> createQiandao(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		// System.out.println("强制浏览------result="+result);
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//					"null".equals(jsonObject.getString("message")) || 
//					"".equals(jsonObject.getString("message"))) {
//				mapRet.put("message","");
//			}else{
//				mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			}
//			if (!jsonObject.has("changeTable")||null == jsonObject.getString("changeTable") || 
//					"null".equals(jsonObject.getString("changeTable")) || 
//					"".equals(jsonObject.getString("changeTable"))) {
//				mapRet.put("changeTable","");
//			}else{
//				mapRet.put("changeTable", jsonObject.has("changeTable") ? jsonObject.getBoolean("changeTable") : "");
//			}
//			// 签到任务中余额翻倍过期时间
//			if (!jsonObject.has("t_time")||null == jsonObject.getString("t_time") || 
//					"null".equals(jsonObject.getString("t_time")) || 
//					"".equals(jsonObject.getString("t_time"))) {
//				mapRet.put("t_time","-1");
//			}else{
//				mapRet.put("t_time", jsonObject.has("t_time") ? jsonObject.getString("t_time") : "-1");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/***
//	 * 检查是否开店
//	 */
//	public static final HashMap<String, Object> createCheckStore(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			// false代表已开店
//			if (!jsonObject.has("is_store")||null == jsonObject.getString("is_store") || 
//					"null".equals(jsonObject.getString("is_store")) || 
//					"".equals(jsonObject.getString("is_store"))) {
//				mapRet.put("is_store","");
//			}else{
//				mapRet.put("is_store", jsonObject.has("is_store") ? jsonObject.getString("is_store") : "");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/***
//	 * 选择客服
//	 */
//	public static final HashMap<String, String> createChoiceKefu(Context context, String result) throws Exception {
//		// HashMap<String, String> mapRet = new HashMap<String, String>();
//		// JSONObject jsonObject = new JSONObject(result);
//		//
//		// if (null == jsonObject || "".equals(jsonObject)) {
//		// return null;
//		// }
//		//
//		// if (1 == jsonObject.getInt("status")) {
//		// mapRet.put("id", jsonObject.has("id") ? jsonObject.getString("id")
//		// : "");
//		// return mapRet;
//		// }
//
//		HashMap<String, String> retInfo = new HashMap<String, String>();
//
//		LogYiFu.e("Kr", result + "");
//		JSONObject j = new JSONObject(result);
//
//		if (j.getString("status").equals(1 + "")) {
//
//			String data = j.getString("id");
//			if (!j.has("id")||null == j.getString("id") || 
//					"null".equals(j.getString("id")) || 
//					"".equals(j.getString("id"))) {
//				retInfo.put("id","");
//			}else{
//				retInfo.put("id", data);
//			}
//		}
//		return retInfo;
//
//	}
//
//	/***
//	 * 检查是否开店(用于5元红包弹窗)
//	 */
//	public static final HashMap<String, Object> createfiveYuanDialog(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("isOpen")||null == jsonObject.getString("isOpen") || 
//					"null".equals(jsonObject.getString("isOpen")) || 
//					"".equals(jsonObject.getString("isOpen"))) {
//				mapRet.put("isOpen","");
//			}else{
//				mapRet.put("isOpen", jsonObject.has("isOpen") ? jsonObject.getString("isOpen") : "");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 获取当前系统时间
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createSystemTime(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("now")||null == jsonObject.getString("now") || 
//					"null".equals(jsonObject.getString("now")) || 
//					"".equals(jsonObject.getString("now"))) {
//				mapRet.put("now","0");
//			}else{
//				mapRet.put("now", jsonObject.has("now") ? jsonObject.getLong("now") : "0");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 获取用户等级
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createUserGralde(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("grade")||null == jsonObject.getString("grade") || 
//					"null".equals(jsonObject.getString("grade")) || 
//					"".equals(jsonObject.getString("grade"))) {
//				mapRet.put("grade",1);
//			}else{
//				mapRet.put("grade", jsonObject.has("grade") ? jsonObject.getInt("grade") : 1);
//			}
//			if (!jsonObject.has("auseTwofold")||null == jsonObject.getString("auseTwofold") || 
//					"null".equals(jsonObject.getString("auseTwofold")) || 
//					"".equals(jsonObject.getString("auseTwofold"))) {
//				mapRet.put("auseTwofold",6);
//			}else{
//				mapRet.put("auseTwofold", jsonObject.has("auseTwofold") ? jsonObject.getInt("auseTwofold") : 6);
//			}
//			if (!jsonObject.has("buseTwofold")||null == jsonObject.getString("buseTwofold") || 
//					"null".equals(jsonObject.getString("buseTwofold")) || 
//					"".equals(jsonObject.getString("buseTwofold"))) {
//				mapRet.put("buseTwofold",6);
//			}else{
//				mapRet.put("buseTwofold", jsonObject.has("buseTwofold") ? jsonObject.getInt("buseTwofold") : 6);
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 获取是否弹余额翻倍弹窗
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createIsDialog(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("balance")||null == jsonObject.getString("balance") || 
//					"null".equals(jsonObject.getString("balance")) || 
//					"".equals(jsonObject.getString("balance"))) {
//				mapRet.put("balance",0.0);
//			}else{
//				mapRet.put("balance", jsonObject.has("balance") ? jsonObject.getDouble("balance") : 0.0);
//			}
//			if (!jsonObject.has("flag")||null == jsonObject.getString("flag") || 
//					"null".equals(jsonObject.getString("flag")) || 
//					"".equals(jsonObject.getString("flag"))) {
//				mapRet.put("flag",0);
//			}else{
//				mapRet.put("flag", jsonObject.has("flag") ? jsonObject.getInt("flag") : 0);
//			}
//			if (!jsonObject.has("xxx")||null == jsonObject.getString("xxx") || 
//					"null".equals(jsonObject.getString("xxx")) || 
//					"".equals(jsonObject.getString("xxx"))) {
//				mapRet.put("xxx",6);
//			}else{
//				mapRet.put("xxx", jsonObject.has("xxx") ? jsonObject.getInt("xxx") : 6);
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 获取当环信密码
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createHuanXinPassword(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("code")||null == jsonObject.getString("code") || 
//					"".equals(jsonObject.getString("code"))) {
//				mapRet.put("code","");
//			}else{
//				mapRet.put("code", jsonObject.has("code") ? jsonObject.getString("code") : "");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	/**
//	 * 获取我的喜好
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final String createMineLike(Context context, String result) throws Exception {
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			String str = jsonObject.getString("codes");
//			if (str.length() > 2) {
//				str.replace("[", "");
//				str.replace("]", "");
//			}
//			if (!jsonObject.has("codes")||null == jsonObject.getString("codes") || 
//					"null".equals(jsonObject.getString("codes")) || 
//					"".equals(jsonObject.getString("codes"))) {
//				return null;
//			}else{
//				return str;
//			}
//			
//			// String[] strs = str.split(",");
//			// HashSet<String> set = new HashSet<String>();
//			// if (strs != null && strs.length > 0) {
//			// for (int i = 0; i < strs.length; i++) {
//			// set.add(strs[i]);
//			// }
//			// return set;
//			// } else {
//			// return null;
//			// }
//		} else {
//			return null;
//		}
//
//	}
//
//	/**
//	 * 获取PIC与时间
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createPic(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//				"null".equals(jsonObject.getString("status")) || 
//				"".equals(jsonObject.getString("status"))) {
//			return null;
//		}
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("data")||null == jsonObject.getString("data") || 
//					"null".equals(jsonObject.getString("data")) || 
//					"".equals(jsonObject.getString("data"))) {
//				return null;
//			}
//			String str = jsonObject.getString("data");
//			JSONObject jsonObject2 = new JSONObject(str);
////			String pic = jsonObject2.getString("pic");
//			if (!jsonObject2.has("pic")||null == jsonObject2.getString("pic") || 
//					"null".equals(jsonObject2.getString("pic")) || 
//					"".equals(jsonObject2.getString("pic"))) {
//				mapRet.put("pic", "");
//			}else{
//				mapRet.put("pic", jsonObject2.getString("pic"));
//			}
//			
////			String time = jsonObject2.getString("time");
//			if (!jsonObject2.has("time")||null == jsonObject2.getString("time") || 
//					"null".equals(jsonObject2.getString("time")) || 
//					"".equals(jsonObject2.getString("time"))) {
//				mapRet.put("time", "0");
//			}else{
//				mapRet.put("time", jsonObject2.getString("time"));
//			}
//			
//			return mapRet;
//		}
//
//		return null;
//
//	}
//
//	/**
//	 * 晒单点赞
//	 */
//	public static final HashMap<String, Object> createAddClick(Context context, String result) throws Exception {
//
//		// System.out.println("晒单点赞result=" + result);
//
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		// mapRet.put("status", jsonObject.has("status") ?
//		// jsonObject.getLong("status") : "");
//		if (!jsonObject.has("message")||null == jsonObject.getString("message") || 
//				"null".equals(jsonObject.getString("message")) || 
//				"".equals(jsonObject.getString("message"))) {
//			mapRet.put("message", "");
//		}else{
//			mapRet.put("message", jsonObject.getString("message"));
//		}
//		if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//				"null".equals(jsonObject.getString("status")) || 
//				"".equals(jsonObject.getString("status"))) {
//			mapRet.put("status", "");
//		}else{
//			mapRet.put("status", jsonObject.getString("status"));
//		}
//		
//		return mapRet;
//	}
//
//	/**
//	 * 夺宝——晒单
//	 */
//	public static final List<HashMap<String, Object>> createDuoBao_ShaiDan(Context context, String result)
//			throws Exception {
//		// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String, Object>>();
//
//		JSONObject jsonObject = new JSONObject(result);
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("comments"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//
//		// //////////
//		JSONArray array = new JSONArray(jsonObject.getString("shop_codes"));
//		if (null == array || "".equals(array)) {
//			return null;
//		}
//		List<String> list = new ArrayList<String>();
//
//		// ////////////
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("shop_code")||null == jo.getString("shop_code") || 
//					"null".equals(jo.getString("shop_code")) || 
//					"".equals(jo.getString("shop_code"))) {
//				mapObject.put("shop_code", "");
//			}else{
//				mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
//			}
//			if (!jo.has("user_id")||null == jo.getString("user_id") || 
//					"null".equals(jo.getString("user_id")) || 
//					"".equals(jo.getString("user_id"))) {
//				mapObject.put("user_id", "");
//			}else{
//				mapObject.put("user_id", jo.has("user_id") ? jo.getInt("user_id") : "");
//			}
//			if (!jo.has("user_name")||null == jo.getString("user_name") || 
//					"".equals(jo.getString("user_name"))) {
//				mapObject.put("user_name", "");
//			}else{
//				mapObject.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//			}
//			if (!jo.has("user_url")||null == jo.getString("user_url") || 
//					"null".equals(jo.getString("user_url")) || 
//					"".equals(jo.getString("user_url"))) {
//				mapObject.put("user_url", "");
//			}else{
//				mapObject.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//			}
//			if (!jo.has("content")||null == jo.getString("content") || 
//					"null".equals(jo.getString("content")) || 
//					"".equals(jo.getString("content"))) {
//				mapObject.put("content", "");
//			}else{
//				mapObject.put("content", jo.has("content") ? jo.getString("content") : "");
//			}
//			if (!jo.has("pic")||null == jo.getString("pic") || 
//					"null".equals(jo.getString("pic")) || 
//					"".equals(jo.getString("pic"))) {
//				mapObject.put("pic", "");
//			}else{
//				mapObject.put("pic", jo.has("pic") ? jo.getString("pic") : "");
//			}
//			if (!jo.has("add_date")||null == jo.getString("add_date") || 
//					"null".equals(jo.getString("add_date")) || 
//					"".equals(jo.getString("add_date"))) {
//				mapObject.put("add_date", "0");
//			}else{
//				mapObject.put("add_date", jo.has("add_date") ? jo.getLong("add_date") : "0");
//			}
//			
//			if (!jo.has("comment_size")||null == jo.getString("comment_size") || 
//					"null".equals(jo.getString("comment_size")) || 
//					"".equals(jo.getString("comment_size"))) {
//				mapObject.put("comment_size", "");
//			}else{
//				mapObject.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
//			}
//			if (!jo.has("click_size")||null == jo.getString("click_size") || 
//					"null".equals(jo.getString("click_size")) || 
//					"".equals(jo.getString("click_size"))) {
//				mapObject.put("click_size", "");
//			}else{
//				mapObject.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
//			}
//
//			if (array.length() == 0) { // 位避免空指针
//				mapObject.put("shop_codes", "0");
//			} else {
//				for (int j = 0; j < array.length(); j++) {
//
//					if (j < array.length() && array.get(j).toString().equals(jo.getString("shop_code") + "")) {
//						mapObject.put("shop_codes", array.get(j).toString());
//						break; // 跳出本次的循环 避免走到最后一次
//					}
//
//					if (j == array.length() - 1) {
//						if (array.get(j).toString().equals(jo.getString("shop_code") + "")) {
//							mapObject.put("shop_codes", array.get(j).toString());
//						} else {
//							mapObject.put("shop_codes", "0.0");
//						}
//					}
//				}
//			}
//
//			// mapObject.put("shop_codes",array.get(i).toString());
//
//			mapRet.add(mapObject);
//		}
//
//		// ///////////////////////////////
//
//		// JSONArray array = new JSONArray(jsonObject.getString("shop_codes"));
//		// if (null == array || "".equals(array)) {
//		// return null;
//		// }
//		// System.out.println("-------array="+array);
//		//
//		// List<String> list = new ArrayList<String>();
//		// for (int i = 0; i < array.length(); i++) {
//		// // JSONObject jo = (JSONObject) array.opt(i);
//		// list.add(i, (String) array.get(i));
//		// Object object = array.get(i);
//		// }
//		// System.out.println("???????????????list="+list);
//		//
//		//
//		//
//		// for (String shop_code : list) {
//		// HashMap<String,Object> map = new HashMap<String,Object>();
//		// map.put("shop_codes", shop_code);
//		//
//		// mapRet.add(map);
//		// }
//		// System.out.println("yes or no--mapRet="+mapRet);
//
//		// //////////////////////////////////////
//
//		// for (int i = 0; i < array.length(); i++) {
//		// JSONObject jo = (JSONObject) array.opt(i);
//		// List<String> list = new ArrayList<String>();
//		//
//		//
//		// }
//		return mapRet;
//
//	}
//
//	/**
//	 * 夺宝 往期揭晓——往期揭晓(往期揭晓左边的)
//	 */
//
//	public static final List<HashMap<String, Object>> createWqjx_left(Context context, String result) throws Exception {
//		// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		// System.out.println("往期揭晓左边=" + result);
//		List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String, Object>>();
//		JSONObject jsonObject = new JSONObject(result);
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("shop_code")||null == jo.getString("shop_code") || 
//					"null".equals(jo.getString("shop_code")) || 
//					"".equals(jo.getString("shop_code"))) {
//				mapObject.put("shop_code", "");
//			}else{
//				mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
//			}
//			if (!jo.has("shop_name")||null == jo.getString("shop_name") || 
//					"null".equals(jo.getString("shop_name")) || 
//					"".equals(jo.getString("shop_name"))) {
//				mapObject.put("shop_name", "");
//			}else{
//				mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//			}
//			if (!jo.has("shop_pic")||null == jo.getString("shop_pic") || 
//					"null".equals(jo.getString("shop_pic")) || 
//					"".equals(jo.getString("shop_pic"))) {
//				mapObject.put("shop_pic", "");
//			}else{
//				mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") : "");
//			}
//			if (!jo.has("in_code")||null == jo.getString("in_code") || 
//					"null".equals(jo.getString("in_code")) || 
//					"".equals(jo.getString("in_code"))) {
//				mapObject.put("in_code", "");
//			}else{
//				mapObject.put("in_code", jo.has("in_code") ? jo.getString("in_code") : "");
//			}
//			if (!jo.has("status")||null == jo.getString("status") || 
//					"null".equals(jo.getString("status")) || 
//					"".equals(jo.getString("status"))) {
//				mapObject.put("status", "");
//			}else{
//				mapObject.put("status", jo.has("status") ? jo.getInt("status") : "");
//			}
//			if (!jo.has("otime")||null == jo.getString("otime") || 
//					"null".equals(jo.getString("otime")) || 
//					"".equals(jo.getString("otime"))) {
//				mapObject.put("otime", "0");
//			}else{
//				mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : "0");
//			}
//			if (!jo.has("btime")||null == jo.getString("btime") || 
//					"null".equals(jo.getString("btime")) || 
//					"".equals(jo.getString("btime"))) {
//				mapObject.put("btime", "0");
//			}else{
//				mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : "0");
//			}
//			if (!jo.has("etime")||null == jo.getString("etime") || 
//					"null".equals(jo.getString("etime")) || 
//					"".equals(jo.getString("etime"))) {
//				mapObject.put("etime", "0");
//			}else{
//				mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : "0");
//			}
//			if (!jo.has("num")||null == jo.getString("num") || 
//					"null".equals(jo.getString("num")) || 
//					"".equals(jo.getString("num"))) {
//				mapObject.put("num", "0");
//			}else{
//				mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
//			}
//			if (!jo.has("virtual_num")||null == jo.getString("virtual_num") || 
//					"null".equals(jo.getString("virtual_num")) || 
//					"".equals(jo.getString("virtual_num"))) {
//				mapObject.put("virtual_num", "0");
//			}else{
//				mapObject.put("virtual_num", jo.has("virtual_num") ? jo.getInt("virtual_num") : "0");
//			}
//			if (!jo.has("in_name")||null == jo.getString("in_name") || 
//					"".equals(jo.getString("in_name"))) {
//				mapObject.put("in_name", "");
//			}else{
//				mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") : "");
//			}
//			if (!jo.has("in_head")||null == jo.getString("in_head") || 
//					"null".equals(jo.getString("in_head")) || 
//					"".equals(jo.getString("in_head"))) {
//				mapObject.put("in_head", "");
//			}else{
//				mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") : "");
//			}
//			if (!jo.has("issue_code")||null == jo.getString("issue_code") || 
//					"null".equals(jo.getString("issue_code")) || 
//					"".equals(jo.getString("issue_code"))) {
//				mapObject.put("issue_code", "");
//			}else{
//				mapObject.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");
//			}
//			if (!jo.has("in_uid")||null == jo.getString("in_uid") || 
//					"null".equals(jo.getString("in_uid")) || 
//					"".equals(jo.getString("in_uid"))) {
//				mapObject.put("in_uid", "");
//			}else{
//				mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "");
//			}
//			mapRet.add(mapObject);
//		}
//		return mapRet;
//
//	}
//
//	/**
//	 * 夺宝记录——我的参与记录
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final List<HashMap<String, Object>> createSnatchJoin(Context context, String result)
//			throws Exception {
//		// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		// System.out.println("aaaaaaaaaaaaaaa+result=" + result);
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//
//			if (!jo.has("shop_code")||null == jo.getString("shop_code") || 
//					"null".equals(jo.getString("shop_code")) || 
//					"".equals(jo.getString("shop_code"))) {
//				mapObject.put("shop_code", "");
//			}else{
//				mapObject.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");
//			}
//			if (!jo.has("issue_code")||null == jo.getString("issue_code") || 
//					"null".equals(jo.getString("issue_code")) || 
//					"".equals(jo.getString("issue_code"))) {
//				mapObject.put("issue_code", "");
//			}else{
//				mapObject.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");
//			}
//			if (!jo.has("in_name")||null == jo.getString("in_name") || 
//					"".equals(jo.getString("in_name"))) {
//				mapObject.put("in_name", "");
//			}else{
//				mapObject.put("in_name", jo.has("in_name") ? jo.getString("in_name") : "");
//			}
//			if (!jo.has("in_head")||null == jo.getString("in_head") || 
//					"null".equals(jo.getString("in_head")) || 
//					"".equals(jo.getString("in_head"))) {
//				mapObject.put("in_head", "");
//			}else{
//				mapObject.put("in_head", jo.has("in_head") ? jo.getString("in_head") : "");
//			}
//			// mapObject.put("in_head", jo.getString("in_head"));
//			if (!jo.has("in_uid")||null == jo.getString("in_uid") || 
//					"null".equals(jo.getString("in_uid")) || 
//					"".equals(jo.getString("in_uid"))) {
//				mapObject.put("in_uid", "0");
//			}else{
//				mapObject.put("in_uid", jo.has("in_uid") ? jo.getInt("in_uid") : "0");
//			}
//			if (!jo.has("shop_name")||null == jo.getString("shop_name") || 
//					"null".equals(jo.getString("shop_name")) || 
//					"".equals(jo.getString("shop_name"))) {
//				mapObject.put("shop_name", "");
//			}else{
//				mapObject.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//			}
//			if (!jo.has("shop_pic")||null == jo.getString("shop_pic") || 
//					"null".equals(jo.getString("shop_pic")) || 
//					"".equals(jo.getString("shop_pic"))) {
//				mapObject.put("shop_pic", "");
//			}else{
//				mapObject.put("shop_pic", jo.has("shop_pic") ? jo.getString("shop_pic") : "");
//			}
//			if (!jo.has("in_code")||null == jo.getString("in_code") || 
//					"null".equals(jo.getString("in_code")) || 
//					"".equals(jo.getString("in_code"))) {
//				mapObject.put("in_code", "");
//			}else{
//				mapObject.put("in_code", jo.has("in_code") ? jo.getString("in_code") : "");
//			}
//			if (!jo.has("status")||null == jo.getString("status") || 
//					"null".equals(jo.getString("status")) || 
//					"".equals(jo.getString("status"))) {
//				mapObject.put("status", "");
//			}else{
//				mapObject.put("status", jo.has("status") ? jo.getInt("status") : "");
//			}
//			if (!jo.has("num")||null == jo.getString("num") || 
//					"null".equals(jo.getString("num")) || 
//					"".equals(jo.getString("num"))) {
//				mapObject.put("num", "0");
//			}else{
//				mapObject.put("num", jo.has("num") ? jo.getInt("num") : "0");
//			}
//			if (!jo.has("virtual_num")||null == jo.getString("virtual_num") || 
//					"null".equals(jo.getString("virtual_num")) || 
//					"".equals(jo.getString("virtual_num"))) {
//				mapObject.put("virtual_num", "0");
//			}else{
//				mapObject.put("virtual_num", jo.has("virtual_num") ? jo.getInt("virtual_num") : "0");
//			}
//			if (!jo.has("otime")||null == jo.getString("otime") || 
//					"null".equals(jo.getString("otime")) || 
//					"".equals(jo.getString("otime"))) {
//				mapObject.put("otime", "0");
//			}else{
//				mapObject.put("otime", jo.has("otime") ? jo.getLong("otime") : 0); // WTF
//			}
//			if (!jo.has("btime")||null == jo.getString("btime") || 
//					"null".equals(jo.getString("btime")) || 
//					"".equals(jo.getString("btime"))) {
//				mapObject.put("btime", "0");
//			}else{
//				mapObject.put("btime", jo.has("btime") ? jo.getLong("btime") : 0);
//			}
//			if (!jo.has("etime")||null == jo.getString("etime") || 
//					"null".equals(jo.getString("etime")) || 
//					"".equals(jo.getString("etime"))) {
//				mapObject.put("etime", "0");
//			}else{
//				mapObject.put("etime", jo.has("etime") ? jo.getLong("etime") : 0);
//			}
//
//			JSONArray jsonArray2 = new JSONArray(jo.has("user") ? jo.getString("user") : "");
//			DuoBaoJiLu_user user = new DuoBaoJiLu_user();
//			for (int p = 0; p < jsonArray2.length(); p++) {
//				JSONObject jo2 = (JSONObject) jsonArray2.get(p);
//				HashMap<String, Object> map = new HashMap<String, Object>();
//
//				// user.setNickname(jo2.getString("nickname"));
//				if (!jo2.has("uid")||null == jo2.getString("uid") || 
//						"null".equals(jo2.getString("uid")) || 
//						"".equals(jo2.getString("uid"))) {
//					map.put("uid", "0");
//				}else{
//					map.put("uid", jo2.getInt("uid"));
//				}
//				if (!jo2.has("atime")||null == jo2.getString("atime") || 
//						"null".equals(jo2.getString("atime")) || 
//						"".equals(jo2.getString("atime"))) {
//					map.put("atime", "0");
//				}else{
//					map.put("atime", jo2.getLong("atime"));
//				}
//				if (!jo2.has("uhead")||null == jo2.getString("uhead") || 
//						"null".equals(jo2.getString("uhead")) || 
//						"".equals(jo2.getString("uhead"))) {
//					map.put("uhead", "");
//				}else{
//					map.put("uhead", jo2.getString("uhead"));
//				}
//				if (!jo2.has("num")||null == jo2.getString("num") || 
//						"null".equals(jo2.getString("num")) || 
//						"".equals(jo2.getString("num"))) {
//					map.put("num", "0");
//				}else{
//					map.put("num", jo2.getInt("num"));
//				}
//				if (!jo2.has("nickname")||null == jo2.getString("nickname") || 
//						"null".equals(jo2.getString("nickname")) || 
//						"".equals(jo2.getString("nickname"))) {
//					map.put("nickname", "");
//				}else{
//					map.put("nickname", jo2.getString("nickname"));
//				}
//				if (!jo2.has("uid")||null == jo2.getString("uid") || 
//						"null".equals(jo2.getString("uid")) || 
//						"".equals(jo2.getString("uid"))) {
//					user.setUid(0);
//				}else{
//					user.setUid(jo2.getInt("uid"));
//				}
//				if (!jo2.has("atime")||null == jo2.getString("atime") || 
//						"null".equals(jo2.getString("atime")) || 
//						"".equals(jo2.getString("atime"))) {
//					user.setAtime(0L);
//				}else{
//					user.setAtime(jo2.getLong("atime"));
//				}
//				
//				if (!jo2.has("uhead")||null == jo2.getString("uhead") || 
//						"null".equals(jo2.getString("uhead")) || 
//						"".equals(jo2.getString("uhead"))) {
//					user.setUhead("");
//				}else{
//					user.setUhead(jo2.getString("uhead"));
//				}
//				if (!jo2.has("num")||null == jo2.getString("num") || 
//						"null".equals(jo2.getString("num")) || 
//						"".equals(jo2.getString("num"))) {
//					user.setNum(0);
//				}else{
//					user.setNum(jo2.getInt("num"));
//				}
//				if (!jo2.has("nickname")||null == jo2.getString("nickname") || 
//						"".equals(jo2.getString("nickname"))) {
//					user.setNickname("");
//				}else{
//					user.setNickname(jo2.getString("nickname"));
//				}
//			}
//			mapObject.put("user", user);
//			list.add(mapObject);
//		}
//		return list;
//
//	}
//
//	/**
//	 * 查询抵用券
//	 */
//	public static final List<HashMap<String, Object>> createDiYongQuan(Context context, String result)
//			throws Exception {
//		// HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		List<HashMap<String, Object>> mapRet = new ArrayList<HashMap<String, Object>>();
//		JSONObject jsonObject = new JSONObject(result);
//		// System.out.println("&&&^^^^^^&&&" + result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(jsonObject.getString("voucher"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("snum")||null == jo.getString("snum") || 
//					"null".equals(jo.getString("snum")) || 
//					"".equals(jo.getString("snum"))) {
//				mapObject.put("snum","0");
//			}else{
//				mapObject.put("snum", jo.getString("snum"));
//			}
//			if (!jo.has("price")||null == jo.getString("price") || 
//					"null".equals(jo.getString("price")) || 
//					"".equals(jo.getString("price"))) {
//				mapObject.put("price","0");
//			}else{
//				mapObject.put("price", jo.getString("price"));
//			}
//			
//			mapRet.add(mapObject);
//			// System.out.println("***--***"+mapRet);
//		}
//		// System.out.println("***------***"+mapRet);
//		return mapRet;
//
//	}
//
//	/**
//	 * 获取抵用券
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createDiYong(Context context, String result) throws Exception {
//		HashMap<String, Object> mapObj = new HashMap<String, Object>();
//		// ReturnInfo retInfo = new ReturnInfo();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		// retInfo.setStatus(j.has("status") ? j.getString(RetInfo.status) :
//		// "");
//		// retInfo.setMessage(j.has("message") ? j.getString(RetInfo.message) :
//		// "");
//		if (!j.has("status")||null == j.getString("status") || 
//				"null".equals(j.getString("status")) || 
//				"".equals(j.getString("status"))) {
//			mapObj.put("status","");
//		}else{
//			mapObj.put("status", j.has("status") ? j.getString(RetInfo.status) : "");
//		}
//		if (!j.has("message")||null == j.getString("message") || 
//				"null".equals(j.getString("message")) || 
//				"".equals(j.getString("message"))) {
//			mapObj.put("message","");
//		}else{
//			mapObj.put("message", j.has("message") ? j.getString(RetInfo.message) : "");
//		}
//		if (!j.has("num")||null == j.getString("num") || 
//				"null".equals(j.getString("num")) || 
//				"".equals(j.getString("num"))) {
//			mapObj.put("num","0");
//		}else{
//			mapObj.put("num", j.has("num") ? j.getString(RetInfo.num) : "0");
//		}
//		
//		// System.out.println("?????" + mapObj);
//		return mapObj;
//	}
//
//	/***
//	 * 特卖多订单提交确认
//	 */
//	public static final HashMap<String, Object> createShopCartSubmitConfrim(Context context, String result)
//			throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("buyCount")||null == jsonObject.getString("buyCount") || 
//					"null".equals(jsonObject.getString("buyCount")) || 
//					"".equals(jsonObject.getString("buyCount"))) {
//				mapRet.put("buyCount","0");
//			}else{
//				mapRet.put("buyCount", jsonObject.has("buyCount") ? jsonObject.optInt("buyCount") : "0");
//			}
//			if (!jsonObject.has("isZeroBuy")||null == jsonObject.getString("isZeroBuy") || 
//					"null".equals(jsonObject.getString("isZeroBuy")) || 
//					"".equals(jsonObject.getString("isZeroBuy"))) {
//				mapRet.put("isZeroBuy","-1000");
//			}else{
//				mapRet.put("isZeroBuy", jsonObject.has("isZeroBuy") ? jsonObject.optInt("isZeroBuy") : "-1000");
//			}
//			if (!jsonObject.has("integral")||null == jsonObject.getString("integral") || 
//					"null".equals(jsonObject.getString("integral")) || 
//					"".equals(jsonObject.getString("integral"))) {
//				mapRet.put("integral","0");
//			}else{
//				mapRet.put("integral", jsonObject.has("integral") ? jsonObject.optInt("integral") : "0");
//			}
//			if (!jsonObject.has("needIntegral")||null == jsonObject.getString("needIntegral") || 
//					"null".equals(jsonObject.getString("needIntegral")) || 
//					"".equals(jsonObject.getString("needIntegral"))) {
//				mapRet.put("needIntegral","0");
//			}else{
//				mapRet.put("needIntegral", jsonObject.has("needIntegral") ? jsonObject.optInt("needIntegral") : "0");
//			}
//			if (!jsonObject.has("status")||null == jsonObject.getString("status") || 
//					"null".equals(jsonObject.getString("status")) || 
//					"".equals(jsonObject.getString("status"))) {
//				mapRet.put("status","");
//			}else{
//				mapRet.put("status", jsonObject.has("status") ? jsonObject.optInt("status") : "");
//			}
//			return mapRet;
//		}
//		return null;
//
//	}
//
//	public static final int createWeixinInfo(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return 0;
//		}
//		if (!jsonObject.has("sex")||null == jsonObject.getString("sex") || 
//				"null".equals(jsonObject.getString("sex")) || 
//				"".equals(jsonObject.getString("sex"))) {
//			return 0;
//		}else{
//			return jsonObject.getInt("sex");
//		}
//	}
//
//	/**
//	 * 夺宝得到商品详情
//	 */
//
//	public static final HashMap<String, Object> createIndianaShop(Context context, String result) throws JSONException {
//		// result = result.replace("null", "\"\"");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		// retInfo.put("now", jsonObject.getLong("now"));
//
//		YDBHelper helper = new YDBHelper(context);
//		Shop shop = new Shop();
//		JSONObject j = new JSONObject(result);
//
//		if (!j.has("num")||null == j.getString("num") || 
//				"null".equals(j.getString("num")) || 
//				"".equals(j.getString("num"))) {
//			map.put("num",0);
//		}else{
//			map.put("num", j.has("num") ? j.optInt("num") : 0);
//		}
//		if (!j.has("virtual_num")||null == j.getString("virtual_num") || 
//				"null".equals(j.getString("virtual_num")) || 
//				"".equals(j.getString("virtual_num"))) {
//			map.put("virtual_num",0);
//		}else{
//			map.put("virtual_num", j.has("virtual_num") ? j.optInt("virtual_num") : 0);
//		}
//		if (!j.has("order_status")||null == j.getString("order_status") || 
//				"null".equals(j.getString("order_status")) || 
//				"".equals(j.getString("order_status"))) {
//			map.put("order_status",0);
//		}else{
//			map.put("order_status", j.has("order_status") ? j.optInt("order_status") : 0);
//		}
//		if (!j.has("ostatus")||null == j.getString("ostatus") || 
//				"null".equals(j.getString("ostatus")) || 
//				"".equals(j.getString("ostatus"))) {
//			map.put("ostatus",0);
//		}else{
//			map.put("ostatus", j.has("ostatus") ? j.optInt("ostatus") : 0);
//		}
//		
//		String str2 = "";
//		List<String> list = new ArrayList<String>();
//		if (j.has("codes")) {
//			JSONArray jarray = new JSONArray(j.getString("codes"));
//			if (jarray != null && jarray.length() > 0) {
//				for (int i = 0; i < jarray.length(); i++) {
//					str2 = jarray.getString(i);
//					list.add(str2);
//				}
//			}
//		}
//
//		map.put("codes", list);
//		if (!j.has("my_num")||null == j.getString("my_num") || 
//				"null".equals(j.getString("my_num")) || 
//				"".equals(j.getString("my_num"))) {
//			map.put("my_num",0);
//		}else{
//			map.put("my_num", j.has("my_num") ? j.optInt("my_num") : 0);
//		}
//		if (!j.has("otime")||null == j.getString("otime") || 
//				"null".equals(j.getString("otime")) || 
//				"".equals(j.getString("otime"))) {
//			map.put("otime",0);
//		}else{
//			map.put("otime", j.has("otime") ? j.optLong("otime") : 0);
//		}
//		
//		// map.put("sys_time", j.getLong("sys_time"));
//		if (!j.has("kickback")||null == j.getString("kickback") || 
//				"null".equals(j.getString("kickback")) || 
//				"".equals(j.getString("kickback"))) {
//			shop.setKickback(0);
//		}else{
//			shop.setKickback(j.optDouble("kickback"));
//		}
//		if (!j.has("color_count")||null == j.getString("color_count") || 
//				"null".equals(j.getString("color_count")) || 
//				"".equals(j.getString("color_count"))) {
//			shop.setColor_count(0);
//		}else{
//			shop.setColor_count((float) j.optInt("color_count", 0));
//		}
//		if (!j.has("type_count")||null == j.getString("type_count") || 
//				"null".equals(j.getString("type_count")) || 
//				"".equals(j.getString("type_count"))) {
//			shop.setType_count(0);
//		}else{
//			shop.setType_count((float) j.optInt("type_count", 0));
//		}
//		if (!j.has("work_count")||null == j.getString("work_count") || 
//				"null".equals(j.getString("work_count")) || 
//				"".equals(j.getString("work_count"))) {
//			shop.setWork_count(0);
//		}else{
//			shop.setWork_count((float) j.optInt("work_count", 0));
//		}
//		if (!j.has("cost_count")||null == j.getString("cost_count") || 
//				"null".equals(j.getString("cost_count")) || 
//				"".equals(j.getString("cost_count"))) {
//			shop.setCost_count(0);
//		}else{
//			shop.setCost_count((float) j.optInt("cost_count", 0));
//		}
//		if (!j.has("star_count")||null == j.getString("star_count") || 
//				"null".equals(j.getString("star_count")) || 
//				"".equals(j.getString("star_count"))) {
//			shop.setStar_count(0);
//		}else{
//			shop.setStar_count(j.optDouble("star_count"));
//		}
//		
//		if (!j.has("praise_count")||null == j.getString("praise_count") || 
//				"null".equals(j.getString("praise_count")) || 
//				"".equals(j.getString("praise_count"))) {
//			shop.setPraise_count(0);
//		}else{
//			shop.setPraise_count(j.optInt("praise_count")); // 好评数 Integer
//		}
//		if (!j.has("med_count")||null == j.getString("med_count") || 
//				"null".equals(j.getString("med_count")) || 
//				"".equals(j.getString("med_count"))) {
//			shop.setMed_count(0);
//		}else{
//			shop.setMed_count(j.optInt("med_count")); // 中评数 Integer
//		}
//		if (!j.has("bad_count")||null == j.getString("bad_count") || 
//				"null".equals(j.getString("bad_count")) || 
//				"".equals(j.getString("bad_count"))) {
//			shop.setBad_count(0);
//		}else{
//			shop.setBad_count(j.optInt("bad_count")); // 差评数 Integer
//		}
//		if (!j.has("eva_count")||null == j.getString("eva_count") || 
//				"null".equals(j.getString("eva_count")) || 
//				"".equals(j.getString("eva_count"))) {
//			shop.setEva_count(0);
//		}else{
//			shop.setEva_count((float) j.optInt("eva_count", 0)); // 评价总数 Integer
//		}
//		if (!j.has("like_id")||null == j.getString("like_id") || 
//				"null".equals(j.getString("like_id")) || 
//				"-1".equals(j.optString("like_id")) ||
//				"".equals(j.getString("like_id"))) {
//			shop.setLike_id(1);
//		}else{
//			shop.setLike_id(("-1".equals(j.optString("like_id")) || !j.has("like_id")) ? -1 : 1); 
//		}
//		if (!j.has("cart_count")||null == j.getString("cart_count") || 
//				"null".equals(j.getString("cart_count")) || 
//				"".equals(j.getString("cart_count"))) {
//			shop.setCart_count(0);
//		}else{
//			shop.setCart_count(j.optInt("cart_count"));// 购物车数量
//		}
//
//		JSONObject obj = j.getJSONObject("shop");
//		// TODO:
//		if (!obj.has("active_people_num")||null == obj.getString("active_people_num") || 
//				"null".equals(obj.getString("active_people_num")) || 
//				"".equals(obj.getString("active_people_num"))) {
//			shop.setActive_people_num(0);
//		}else{
//			shop.setActive_people_num(obj.optInt("active_people_num"));
//		}
//		if (!obj.has("num")||null == obj.getString("num") || 
//				"null".equals(obj.getString("num")) || 
//				"".equals(obj.getString("num"))) {
//			shop.setNum(0);
//		}else{
//			shop.setNum(obj.optInt("num"));
//		}
//		
//		if (!obj.has("otime")||null == obj.getString("otime") || 
//				"null".equals(obj.getString("otime")) || 
//				"".equals(obj.getString("otime"))) {
//			shop.setOtime(0);
//		}else{
//			shop.setOtime(obj.optLong("otime"));
//		}
//		
//		if (!obj.has("sys_time")||null == obj.getString("sys_time") || 
//				"null".equals(obj.getString("sys_time")) || 
//				"".equals(obj.getString("sys_time"))) {
//			shop.setSys_time(0L);
//		}else{
//			shop.setSys_time(obj.optLong("sys_time"));
//		}
//		if (!obj.has("active_start_time")||null == obj.getString("active_start_time") || 
//				"null".equals(obj.getString("active_start_time")) || 
//				"".equals(obj.getString("active_start_time"))) {
//			shop.setActive_start_time(0L);
//		}else{
//			shop.setActive_start_time(obj.optLong("active_start_time"));
//		}
//		if (!obj.has("active_end_time")||null == obj.getString("active_end_time") || 
//				"null".equals(obj.getString("active_end_time")) || 
//				"".equals(obj.getString("active_end_time"))) {
//			shop.setActive_end_time(0L);
//		}else{
//			shop.setActive_end_time(obj.optLong("active_end_time"));
//		}
//		if (!obj.has("shop_batch_num")||null == obj.getString("shop_batch_num") || 
//				"".equals(obj.getString("shop_batch_num"))) {
//			shop.setShop_batch_num("");
//		}else{
//			shop.setShop_batch_num(obj.optString("shop_batch_num"));
//		}
//		if (!obj.has("supp_id")||null == obj.getString("supp_id") || 
//				"null".equals(obj.getString("supp_id")) || 
//				"".equals(obj.getString("supp_id"))) {
//			shop.setSupp_id(0);
//		}else{
//			shop.setSupp_id(obj.optInt("supp_id"));
//		}
//		if (!obj.has("shop_se_price")||null == obj.getString("shop_se_price") || 
//				"null".equals(obj.getString("shop_se_price")) || 
//				"".equals(obj.getString("shop_se_price"))) {
//			shop.setShop_se_price(0);
//		}else{
//			shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
//		}
//		if (!obj.has("shop_price")||null == obj.getString("shop_price") || 
//				"null".equals(obj.getString("shop_price")) || 
//				"".equals(obj.getString("shop_price"))) {
//			shop.setShop_price(0);
//		}else{
//			shop.setShop_price(obj.optDouble("shop_price", 0));
//		}
//		if (!obj.has("shop_code")||null == obj.getString("shop_code") || 
//				"".equals(obj.getString("shop_code"))) {
//			shop.setShop_code("wu");
//		}else{
//			shop.setShop_code(obj.optString("shop_code", "wu"));
//		}
//		if (!obj.has("core")||null == obj.getString("core") || 
//				"null".equals(obj.getString("core")) || 
//				"".equals(obj.getString("core"))) {
//			shop.setCore(0);
//		}else{
//			shop.setCore(obj.optInt("core"));
//		}
//		if (!obj.has("remark")||null == obj.getString("remark") || 
//				"".equals(obj.getString("remark"))) {
//			shop.setRemark("wu");
//		}else{
//			shop.setRemark(obj.optString("remark", "wu"));
//		}
//		if (!obj.has("def_pic")||null == obj.getString("def_pic") || 
//				"".equals(obj.getString("def_pic"))) {
//			shop.setDef_pic("wu");
//		}else{
//			shop.setDef_pic(obj.optString("def_pic", "wu"));
//		}
//		if (!obj.has("kickback")||null == obj.getString("kickback") || 
//				"null".equals(obj.getString("kickback")) || 
//				"".equals(obj.getString("kickback"))) {
//			shop.setKickback(0);
//		}else{
//			shop.setKickback(obj.optDouble("kickback", 0));
//		}
//		if (!obj.has("shop_up_time")||null == obj.getString("shop_up_time") || 
//				"null".equals(obj.getString("shop_up_time")) || 
//				"".equals(obj.getString("shop_up_time"))) {
//			shop.setShop_up_time(0);
//		}else{
//			shop.setShop_up_time(obj.optLong("shop_up_time", 0));
//		}
//		if (!obj.has("invertory_num")||null == obj.getString("invertory_num") || 
//				"null".equals(obj.getString("invertory_num")) || 
//				"".equals(obj.getString("invertory_num"))) {
//			shop.setInvertory_num(0);
//		}else{
//			shop.setInvertory_num(obj.optInt("invertory_num", 0));
//		}
//		if (!obj.has("shop_name")||null == obj.getString("shop_name") || 
//				"".equals(obj.getString("shop_name"))) {
//			shop.setShop_name("wu");
//		}else{
//			shop.setShop_name(obj.optString("shop_name", "wu"));
//		}
//		if (!obj.has("id")||null == obj.getString("id") || 
//				"null".equals(obj.getString("id")) || 
//				"".equals(obj.getString("id"))) {
//			shop.setId(0);
//		}else{
//			shop.setId(obj.optInt("id", 0));
//		}
//		if (!obj.has("content")||null == obj.getString("content") || 
//				"".equals(obj.getString("content"))) {
//			shop.setContent("wu");
//		}else{
//			shop.setContent(obj.optString("content", "wu"));
//		}
//		if (!obj.has("actual_sales")||null == obj.getString("actual_sales") || 
//				"null".equals(obj.getString("actual_sales")) || 
//				"".equals(obj.getString("actual_sales"))) {
//			shop.setActual_sales(0);
//		}else{
//			shop.setActual_sales(obj.optInt("actual_sales", 0));
//		}
//		if (!obj.has("is_new")||null == obj.getString("is_new") || 
//				"".equals(obj.getString("is_new"))) {
//			shop.setIs_new("wu");
//		}else{
//			shop.setIs_new(obj.optString("is_new", "wu"));
//		}
//		if (!obj.has("shop_pic")||null == obj.getString("shop_pic") || 
//				"".equals(obj.getString("shop_pic"))) {
//			shop.setShop_pic("wu");
//		}else{
//			shop.setShop_pic(obj.optString("shop_pic", "wu"));
//		}
//		if (!obj.has("clicks")||null == obj.getString("clicks") || 
//				"null".equals(obj.getString("clicks")) || 
//				"".equals(obj.getString("clicks"))) {
//			shop.setClicks(0);
//		}else{
//			shop.setClicks(obj.optInt("clicks", 0));
//		}
//		if (!obj.has("is_hot")||null == obj.getString("is_hot") || 
//				"".equals(obj.getString("is_hot"))) {
//			shop.setIs_hot("wu");
//		}else{
//			shop.setIs_hot(obj.optString("is_hot", "wu"));
//		}
//		if (!obj.has("virtual_sales")||null == obj.getString("virtual_sales") || 
//				"null".equals(obj.getString("virtual_sales")) || 
//				"".equals(obj.getString("virtual_sales"))) {
//			shop.setVirtual_sales(0);
//		}else{
//			shop.setVirtual_sales(obj.optInt("virtual_sales", 0));
//		}
//		if (!obj.has("love_num")||null == obj.getString("love_num") || 
//				"null".equals(obj.getString("love_num")) || 
//				"".equals(obj.getString("love_num"))) {
//			shop.setLove_num(0);
//		}else{
//			shop.setLove_num(obj.optInt("love_num", 0));
//		}
//		if (!obj.has("shop_tag")||null == obj.getString("shop_tag") || 
//				"".equals(obj.getString("shop_tag"))) {
//			shop.setShop_tag("");
//		}else{
//			shop.setShop_tag(obj.optString("shop_tag"));
//		}
//		
//		String ss = "";
//		if (!obj.has("shop_type_id")||null == obj.getString("shop_type_id") || 
//				"".equals(obj.getString("shop_type_id"))) {
//			
//		}else{
//			ss = obj.optString("shop_type_id");
//		}
//		if (!TextUtils.isEmpty(ss)) {
//			ss = ss.replace("[", "");
//			ss = ss.replace("]", "");
//			String[] shop_type_id = ss.split(",");
//			shop.setShop_type_id(shop_type_id);
//		}
//		
//		if (!obj.has("type1")||null == obj.getString("type1") || 
//				"null".equals(obj.getString("type1")) ||
//				"".equals(obj.getString("type1"))) {
//			shop.setType1(0);
//		}else{
//			shop.setType1(obj.optInt("type1"));
//		}
//		
//		
//		String text ="";
//		if (!obj.has("shop_attr")||null == obj.getString("shop_attr") || 
//				"".equals(obj.getString("shop_attr"))) {
//		}else{
//			text = obj.getString("shop_attr");
//		}
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
//		map.put("shop", shop);
//		return map;
//	}
//
//	/**
//	 * 得到参与记录
//	 */
//	public static final List<HashMap<String, Object>> createIndianaTakeRecord(Context context, String result)
//			throws JSONException {
//		List<HashMap<String, Object>> retInfo = new ArrayList<HashMap<String, Object>>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONArray jsonArray = new JSONArray(j.getString("list"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject jo = (JSONObject) jsonArray.opt(i);
//			HashMap<String, Object> mapObject = new HashMap<String, Object>();
//			if (!jo.has("uid")||null == jo.getString("uid") || 
//					"null".equals(jo.getString("uid")) || 
//					"".equals(jo.getString("uid"))) {
//				mapObject.put("uid",0);
//			}else{
//				mapObject.put("uid", jo.getInt("uid"));
//			}
//			if (!jo.has("atime")||null == jo.getString("atime") || 
//					"null".equals(jo.getString("atime")) || 
//					"".equals(jo.getString("atime"))) {
//				mapObject.put("atime",0);
//			}else{
//				mapObject.put("atime", jo.getLong("atime"));
//			}
//			if (!jo.has("money")||null == jo.getString("money") || 
//					"null".equals(jo.getString("money")) || 
//					"".equals(jo.getString("money"))) {
//				mapObject.put("money",0);
//			}else{
//				mapObject.put("money", jo.getInt("money"));
//			}
//			if (!jo.has("uhead")||null == jo.getString("uhead") || 
//					"".equals(jo.getString("uhead"))) {
//				mapObject.put("uhead","");
//			}else{
//				mapObject.put("uhead", jo.getString("uhead"));
//			}
//			if (!jo.has("num")||null == jo.getString("num") || 
//					"null".equals(jo.getString("num")) || 
//					"".equals(jo.getString("num"))) {
//				mapObject.put("num",0);
//			}else{
//				mapObject.put("num", jo.getInt("num"));
//			}
//			if (!jo.has("nickname")||null == jo.getString("nickname") || 
//					"".equals(jo.getString("nickname"))) {
//				mapObject.put("nickname","");
//			}else{
//				mapObject.put("nickname", jo.getString("nickname"));
//			}
//			
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//	/**
//	 * 得到参与记录NEW
//	 */
//	public static final HashMap<String, Object> createIndianaTakeRecordNew(Context context, String result)
//			throws JSONException {
//		HashMap<String, Object> retInfo = new HashMap<String, Object>();
//		JSONObject j = new JSONObject(result);
//		if (null == j || "".equals(j)) {
//			return null;
//		}
//		JSONObject jsonArray = new JSONObject(j.getString("data"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		if (!jsonArray.has("head_pic")||null == jsonArray.getString("head_pic") || 
//				"".equals(jsonArray.getString("head_pic"))) {
//			retInfo.put("head_pic","");
//		}else{
//			retInfo.put("head_pic", jsonArray.has("head_pic") ? jsonArray.get("head_pic") : "");
//		}
//		if (!jsonArray.has("flag")||null == jsonArray.getString("flag") || 
//				"".equals(jsonArray.getString("flag"))) {
//			retInfo.put("flag","");
//		}else{
//			retInfo.put("flag", jsonArray.has("flag") ? (jsonArray.get("flag") + "") : "");
//		}
//		return retInfo;
//	}
//
//	// /***
//	// * 检查是否参加过包邮活动
//	// */
//	public static final HashMap<String, Object> checkBaoyou(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			if (!jsonObject.has("flag")||null == jsonObject.getString("flag") || 
//					"".equals(jsonObject.getString("flag"))) {
//				mapRet.put("flag","");
//			}else{
//				mapRet.put("flag", jsonObject.has("flag") ? jsonObject.getString("flag") : "");
//			}
//			
//			// context.getSharedPreferences("buqianka",
//			// Context.MODE_PRIVATE).edit()
//			// .putBoolean("bool", jsonObject.has("bool") ?
//			// jsonObject.getBoolean("bool") : false).commit();
//			// mapRet.put("bool", jsonObject.has("bool") ?
//			// jsonObject.getString("bool") : "");
//			return mapRet;
//		}
//
//		return null;
//
//	}
//
//	/**
//	 * 
//	 * 获取购物车数据
//	 */
//	public static final HashMap<String, Object> shopCartData(Context context, String result, int num) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//
//			if (!jsonObject.has("id")||null == jsonObject.getString("id") || 
//					"".equals(jsonObject.getString("id"))) {
//				mapRet.put("id", "");
//			}else{
//				mapRet.put("id", jsonObject.has("id") ? jsonObject.getString("id") : "");
//			}
//
//			return mapRet;
//		}
//
//		return null;
//
//	}
//
//	/***
//	 * 购物车所有数据
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<List<ShopCart>> createListShop_CartAll(Context context, String result, int a)
//			throws JSONException {// a=0代表第一次进入，a=1代表切换用户
//		JSONObject j = new JSONObject(result);
//
//		List<ShopCart> listCommn = null;
//		List<ShopCart> listMeal = null;
//
//		if (j.getInt("status") == 1) {
//			if (j.has("shop_list")) {
//				String textComm = j.getString("shop_list");
//				listCommn = JSON.parseArray(textComm, ShopCart.class);
//
//			}
//
//			if (j.has("p_shop_list")) {
//
//				String textMeal = j.getString("p_shop_list");
//				listMeal = JSON.parseArray(textMeal, ShopCart.class);
//			}
//			long p_deadline = 0;
//			long s_deadline = 0;
//			long sys_time = 0;
//			if (j.has("p_deadline") && null != j.getString("p_deadline")) {
//				p_deadline = j.getLong("p_deadline");
//			}
//			if (j.has("s_deadline") && null != j.getString("s_deadline")) {
//				s_deadline = j.getLong("s_deadline");
//
//			}
//			if (j.has("sys_time") && null != j.getString("sys_time")) {
//				sys_time = j.getLong("sys_time");
//			}
//			ShopCartDao dao = new ShopCartDao(context);
//			List<ShopCart> list_valid_temp = dao.findAll();
//			List<ShopCart> list_invalid_temp = dao.findAll_invalid();
//			if (a == 0 && ((list_valid_temp.size() + list_invalid_temp.size()) > 0)) {// 打开app与数据库有数据时，只同步数据库中没有的
//				if (listCommn != null) {// 正价
//					for (int i = 0; i < listCommn.size(); i++) {
//						boolean flag = false;
//						for (int k = 0; k < list_valid_temp.size(); k++) {
//							if (list_valid_temp.get(k).getIs_meal_flag() == 0) {
//								if (("" + listCommn.get(i).getStock_type_id())
//										.equals("" + list_valid_temp.get(k).getStock_type_id())) {
//									flag = true;// 有效中存在
//									break;
//								}
//							}
//						}
//						if (!flag) {// 有效中没有
//							for (int k = 0; k < list_invalid_temp.size(); k++) {
//								if (list_invalid_temp.get(k).getIs_meal_flag() == 0) {
//									if (("" + listCommn.get(i).getStock_type_id())
//											.equals("" + list_invalid_temp.get(k).getStock_type_id())) {
//										flag = true;// 失效中存在
//										break;
//									}
//								}
//							}
//						}
//						if (!flag) {// 有效失效都不存在，添加到有效当中
//							ShopCart s = listCommn.get(i);
//							dao.add(s.getShop_code(), s.getSize(), s.getColor(), s.getShop_num(), s.getStock_type_id(),
//									s.getDef_pic(), 0, s.getShop_name(),
//									s.getStore_code() != null ? s.getStore_code() : 0 + "", "" + s.getShop_price(),
//									"" + s.getShop_se_price(), "" + s.getSupp_id(), "" + s.getKickback(),
//									"" + s.getOriginal_price(), 0, null, null, null, null, null, s.getId(), null,0);
//						}
//					}
//				}
//
//				if (listMeal != null) {// 特卖
//					for (int i = 0; i < listMeal.size(); i++) {
//						boolean flag = false;
//						for (int k = 0; k < list_valid_temp.size(); k++) {
//							if (list_valid_temp.get(k).getIs_meal_flag() == 1) {
//								if (listMeal.get(i).getP_s_t_id().equals(list_valid_temp.get(k).getP_s_t_id())) {
//									flag = true;// 有效中存在
//									break;
//								}
//							}
//						}
//						if (!flag) {// 有效中没有
//							for (int k = 0; k < list_invalid_temp.size(); k++) {
//								if (list_invalid_temp.get(k).getIs_meal_flag() == 1) {
//									if (listMeal.get(i).getP_s_t_id().equals(list_invalid_temp.get(k).getP_s_t_id())) {
//										flag = true;// 失效中存在
//										break;
//									}
//								}
//							}
//						}
//						if (!flag) {// 有效失效都不存在，添加到有效当中
//							dao.add(null, null, null, listMeal.get(i).getShop_num(), 0,
//									(String) listMeal.get(i).getDef_pic(), 0, null, null,
//									"" + listMeal.get(i).getShop_price(), "" + listMeal.get(i).getShop_se_price(),
//									listMeal.get(i).getSupp_id() + "", "" + 0, "" + 0, 1, listMeal.get(i).getP_code(),
//									"" + listMeal.get(i).getPostage(), listMeal.get(i).getP_s_t_id(),
//									listMeal.get(i).getP_shop_code(), listMeal.get(i).getP_color(),
//									listMeal.get(i).getId(),
//									listMeal.get(i).getP_s_t_id().split(",").length > 1 ? "超值套餐" : "超值单品",0);
//						}
//					}
//				}
//
//			} else {
//				List<ShopCart> list_invalid = dao.findAll_invalid();
//				if (list_invalid != null) {// 同步数据，清空失效购物车数据库
//					for (int i = 0; i < list_invalid.size(); i++) {
//						if (list_invalid.get(i).getIs_meal_flag() == 0) {
//							dao.delete_invalid("" + list_invalid.get(i).getStock_type_id());
//						} else {
//							dao.p_delete_invalid(list_invalid.get(i).getP_s_t_id());
//						}
//					}
//				}
//				SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_COMMON_TIME,
//						"" + (System.currentTimeMillis() + s_deadline - sys_time));
//				SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME,
//						"" + (System.currentTimeMillis() + p_deadline - sys_time));
//				dao.common_first_add(context, listCommn, listMeal);// 全部数据先添加到有效购物车数据库中
//
//			}
//			LogYiFu.e("listCommn", listCommn + "");
//			LogYiFu.e("listMeal", listMeal + "");
//			LogYiFu.e("listMeal", listMeal + "");
//		}
//
//		return null;
//	}
//
//	/***
//	 * 运营数据统计
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws JSONException
//	 */
//	public static final List<List<ShopCart>> createYunYingshuju(Context context, String result) throws JSONException {
//		JSONObject j = new JSONObject(result);
//
//		return null;
//	}
//
//	/**
//	 * 获取PIC与时间
//	 * 
//	 * @param context
//	 * @param result
//	 * @return
//	 * @throws Exception
//	 */
//	public static final HashMap<String, Object> createPicLife(Context context, String result) throws Exception {
//		HashMap<String, Object> mapRet = new HashMap<String, Object>();
//		JSONObject jsonObject = new JSONObject(result);
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//
//		if (1 == jsonObject.getInt("status")) {
//			
//			String str = jsonObject.getString("data");
//
//			JSONObject jsonObject2 = new JSONObject(str);
//
////			String pic = jsonObject2.getString("pic");
//			if (!jsonObject2.has("pic")||null == jsonObject2.getString("pic") || 
//					"".equals(jsonObject2.getString("pic"))) {
//				mapRet.put("pic", "");
//			}else{
//				mapRet.put("pic", jsonObject2.getString("pic"));
//			}
//			
//
//			return mapRet;
//		}
//
//		return null;
//
//	}
//
//	public static final List<String> invalid_shopcart(Context context, String result) throws Exception {
//		List<FundDetail> funds = new ArrayList<FundDetail>();
//		JSONObject jsonObject = new JSONObject(result);
//
//		if (null == jsonObject || "".equals(jsonObject)) {
//			return null;
//		}
//		if (!"1".equals(jsonObject.getString("status"))) {
//			return null;
//		}
//		List<String> list = new ArrayList<String>();
//		if (jsonObject.has("list")) {
//			JSONArray jsonArray = new JSONArray(jsonObject.getString("list"));
//			if (jsonArray != null && !("".equals(jsonArray))) {
//				for (int i = 0; i < jsonArray.length(); i++) {
//					list.add("" + jsonArray.get(i));
//				}
//				return list;
//			}
//			return null;
//		} else {
//			return null;
//		}
//	}
//}

package com.yssj.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yssj.Json;
import com.yssj.Json.JUserInfo;
import com.yssj.Json.RetInfo;
import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.data.YDBHelper;
import com.yssj.entity.Business;
import com.yssj.entity.CheckPwdInfo;
import com.yssj.entity.Comment;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.DuoBaoJiLu_user;
import com.yssj.entity.FundDetail;
import com.yssj.entity.Help;
import com.yssj.entity.IntegralShop;
import com.yssj.entity.Like;
import com.yssj.entity.MatchAttr;
import com.yssj.entity.MatchAttr.ShopAttrBean;
import com.yssj.entity.MatchAttr.StocktypeBean;
import com.yssj.entity.MatchShop;
import com.yssj.entity.MatchShop.AttrList;
import com.yssj.entity.MatchShop.CollocationShop;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.QueryEmailInfo;
import com.yssj.entity.QueryPhoneInfo;
import com.yssj.entity.RemainShipInfo;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.entity.ShareShop;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopAttr;
import com.yssj.entity.ShopCart;
import com.yssj.entity.ShopComment;
import com.yssj.entity.ShopOption;
import com.yssj.entity.ShopTag;
import com.yssj.entity.ShopType;
import com.yssj.entity.StockType;
import com.yssj.entity.Store;
import com.yssj.entity.SuppComment;
import com.yssj.entity.UserInfo;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.YCache;
import com.yssj.utils.sqlite.ShopCartDao;

import android.content.Context;
import android.text.TextUtils;

//public class LiuEntity {
//	// 1477 ~ 2841
//
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
//			
////			shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);
//			if (!j1.has("count") || null == j1.getString("count") || "".equals(j1.getString("count"))) {
//				shareshop.setCount(0);
//			}else {
//				shareshop.setCount(j1.has("count") ? j1.optInt("count") : 0);
//			}
//
//			JSONArray jArray = new JSONArray(j1.has("user_list") ? j1.optString("user_list") : null);
//
//			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//
//			for (int i = 0; i < jArray.length(); i++) {
//				HashMap<String, Object> hashMap = new HashMap<String, Object>();
//				JSONObject object = (JSONObject) jArray.get(i);
////				Object object2 = object.getString("pic");
////				hashMap.put("pic", object2);
//				if (!object.has("pic") || null == object.getString("pic") || "".equals(object.getString("pic"))) {
//					hashMap.put("pic", "");
//				}else {
//					hashMap.put("pic", object.getString("pic"));
//				}
//				
//				list.add(hashMap);
//			}
//			shareshop.setUser_list(list);
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
////			map.put("name", pShop.optString("name", "套餐"));
//			if (!pShop.has("name") || null == pShop.getString("name") || "".equals(pShop.getString("name"))) {
//				map.put("name",  "");
//			}else {
//				map.put("name", pShop.optString("name", "套餐"));
//			}
//			
////			map.put("price", pShop.optString("price", "0"));
//			if (!pShop.has("price") || null == pShop.getString("price") || "".equals(pShop.getString("price"))) {
//				map.put("price",  "0");
//			}else {
//				map.put("price", pShop.optString("price", "0"));
//			}
//			
////			map.put("num", pShop.optString("num", "0"));// 套餐总数
//			if (!pShop.has("num") || null == pShop.getString("num") || "".equals(pShop.getString("num"))) {
//				map.put("num","0");
//			}else {
//				map.put("num", pShop.optString("num", "0"));// 套餐总数
//			}
//			
////			map.put("seq", pShop.optString("seq"));
//			if (!pShop.has("seq") || null == pShop.getString("seq") || "".equals(pShop.getString("seq"))) {
//				map.put("seq","");
//			}else {
//				map.put("seq", pShop.optString("seq"));
//			}
//			
////			map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
//			if (!pShop.has("def_pic") || null == pShop.getString("def_pic") || "".equals(pShop.getString("def_pic"))) {
//				map.put("def_pic","");
//			}else {
//				map.put("def_pic", pShop.optString("def_pic", "没有图"));// 主图
//			}
//			
////			map.put("postage", pShop.optString("postage", "0"));
//			if (!pShop.has("postage") || null == pShop.getString("postage") || "".equals(pShop.getString("postage"))) {
//				map.put("postage","0");
//			}else {
//				map.put("postage", pShop.optString("postage", "0"));
//			}
//			
////			map.put("p_type", pShop.optString("type"));
//			if (!pShop.has("type") || null == pShop.getString("type") || "".equals(pShop.getString("type"))) {
//				map.put("p_type","");
//			}else {
//				map.put("p_type", pShop.optString("type"));
//			}
//			
//		}
//
////		map.put("color_count", json.optString("color_count", "0"));
//		if (!json.has("color_count") || null == json.getString("color_count") || "".equals(json.getString("color_count"))) {
//			map.put("color_count","0");
//		}else {
//			map.put("color_count", json.optString("color_count", "0"));
//		}
//		
////		map.put("work_count", json.optString("work_count", "0"));
//		if (!json.has("work_count") || null == json.getString("work_count") || "".equals(json.getString("work_count"))) {
//			map.put("work_count","0");
//		}else {
//			map.put("work_count", json.optString("work_count", "0"));
//		}
//		
////		map.put("star_count", json.optString("star_count", "5.0"));
//		if (!json.has("star_count") || null == json.getString("star_count") || "".equals(json.getString("star_count"))) {
//			map.put("star_count","0");
//		}else {
//			map.put("star_count", json.optString("star_count", "0"));
//		}
//		
////		map.put("type_count", json.optString("type_count", "0"));
//		if (!json.has("type_count") || null == json.getString("type_count") || "".equals(json.getString("type_count"))) {
//			map.put("type_count","0");
//		}else {
//			map.put("type_count", json.optString("type_count", "0"));
//		}
//		
////		map.put("eva_count", json.optString("eva_count", "0"));
//		if (!json.has("eva_count") || null == json.getString("eva_count") || "".equals(json.getString("eva_count"))) {
//			map.put("eva_count","0");
//		}else {
//			map.put("eva_count", json.optString("eva_count", "0"));
//		}
//		
////		map.put("cost_count", json.optString("cost_count", "0"));
//		if (!json.has("cost_count") || null == json.getString("cost_count") || "".equals(json.getString("cost_count"))) {
//			map.put("cost_count","0");
//		}else {
//			map.put("cost_count", json.optString("cost_count", "0"));
//		}
//		
////		map.put("cart_count", json.optString("cart_count", "0"));
//		if (!json.has("cart_count") || null == json.getString("cart_count") || "".equals(json.getString("cart_count"))) {
//			map.put("cart_count","0");
//		}else {
//			map.put("cart_count", json.optString("cart_count", "0"));
//		}
//		
//		if (YJApplication.instance.isLoginSucess()) {
////			map.put("c_time", json.has("c_time") ? json.getLong("c_time") : 0);
//			if (!json.has("c_time") || null == json.getString("c_time") || "".equals(json.getString("c_time"))) {
//				map.put("c_time","0");
//			}else {
//				map.put("c_time", json.has("c_time") ? json.getLong("c_time") : 0);
//			}
//			
////			map.put("s_time", json.has("s_time") ? json.getLong("s_time") : 0);
//			if (!json.has("s_time") || null == json.getString("s_time") || "".equals(json.getString("s_time"))) {
//				map.put("s_time","0");
//			}else {
//				map.put("s_time", json.has("s_time") ? json.getLong("s_time") : 0);
//			}
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
////				shop.setShop_pic(obj.optString("shop_pic"));
//				if (!obj.has("shop_pic") || null == obj.getString("shop_pic") || "".equals(obj.getString("shop_pic"))) {
//					shop.setShop_pic("");
//				}else {
//					shop.setShop_pic(obj.optString("shop_pic"));;
//				}
//				
////				shop.setShop_code(obj.optString("shop_code"));
//				if (!obj.has("shop_code") || null == obj.getString("shop_code") || "".equals(obj.getString("shop_code"))) {
//					shop.setShop_code("");
//				}else {
//					shop.setShop_code(obj.optString("shop_code"));
//				}
//				
////				shop.setShop_price(obj.optDouble("shop_price", 0));
//				if (!obj.has("shop_price") || null == obj.getString("shop_price") || "".equals(obj.getString("shop_price"))) {
//					shop.setShop_price(0);
//				}else {
//					shop.setShop_price(obj.optDouble("shop_price", 0));
//				}
//				
////				shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
//				if (!obj.has("shop_se_price") || null == obj.getString("shop_se_price") || "".equals(obj.getString("shop_se_price"))) {
//					shop.setShop_se_price(0);
//				}else {
//					shop.setShop_se_price(obj.optDouble("shop_se_price", 0));
//				}
//				
////				shop.setDef_pic(obj.optString("def_pic", ""));
//				if (!obj.has("def_pic") || null == obj.getString("def_pic") || "".equals(obj.getString("def_pic"))) {
//					shop.setDef_pic("");
//				}else {
//					shop.setDef_pic(obj.optString("def_pic", ""));
//				}
//				
////				shop.setSupp_id(obj.optInt("supp_id", 0));
//				if (!obj.has("supp_id") || null == obj.getString("supp_id") || "".equals(obj.getString("supp_id"))) {
//					shop.setSupp_id(0);
//				}else {
//					shop.setSupp_id(obj.optInt("supp_id", 0));
//				}
//				
////				shop.setShop_name(obj.optString("shop_name", ""));
//				if (!obj.has("shop_name") || null == obj.getString("shop_name") || "".equals(obj.getString("shop_name"))) {
//					shop.setShop_name("");
//				}else {
//					shop.setShop_name(obj.optString("shop_name", ""));
//				}
//				
//				
//				
//				shop.setTrait(obj.optInt("age", 0) + "," + obj.optInt("stuff", 0) + "," + obj.optInt("stuff2", 0) + ","
//						+ obj.optInt("fix_price", 0) + "," + obj.optInt("occasion", 0) + "," + obj.optInt("favorite", 0)
//						+ "," + obj.optInt("stuff3", 0) + "," + obj.optInt("stuff4", 0) + ","
//						+ obj.optInt("sys_color", 0) + "," + obj.optInt("pattern", 0) + "," + obj.optInt("trait", 0)
//						+ "," + obj.optInt("trait2", 0) + "," + obj.optInt("trait3", 0) + "," + obj.optInt("style", 0));
//
////				String text = obj.getString("shop_attr");
//				String text = "";
//				if (!obj.has("shop_attr") || null == obj.getString("shop_attr") || "".equals(obj.getString("shop_attr"))) {
//					 text = "";
//				}else {
//					 text = obj.getString("shop_attr");
//				}
//				
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
//	
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
//		JSONArray jsonArray = new JSONArray(j.getString("comments"));
//		if (null == jsonArray || "".equals(jsonArray)) {
//			return null;
//		}
//		List<ShopComment> list = new ArrayList<ShopComment>();
//		for (int i = 0; i < jsonArray.length(); i++) {
//			com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject) JSON.parse(jsonArray.getString(i));
//			ShopComment sComment = new ShopComment();
////			sComment.setColor(jo.getInteger("color"));
//			if (!jo.containsKey("color") || null == jo.getString("color") || "".equals(jo.getString("color"))) {
//				sComment.setColor(0);
//			}else {
//				sComment.setColor(jo.getInteger("color"));
//			}
//			
////			sComment.setAdd_date(jo.getLong("add_date") == null ? 0 : jo.getLong("add_date"));
//			if (!jo.containsKey("add_date") || null == jo.getString("add_date") || "".equals(jo.getString("add_date"))) {
//				sComment.setAdd_date( 0 );
//			}else {
//				sComment.setAdd_date(jo.getLong("add_date") == null ? 0 : jo.getLong("add_date"));
//			}
//			
////			sComment.setComment_type(jo.getInteger("comment_type"));
//			if (!jo.containsKey("comment_type") || null == jo.getString("comment_type") || "".equals(jo.getString("comment_type"))) {
//				sComment.setComment_type(-1000);
//			}else {
//				sComment.setComment_type(jo.getInteger("comment_type"));
//			}
//			
////			sComment.setContent(jo.getString("content"));
//			if (!jo.containsKey("content") || null == jo.getString("content") || "".equals(jo.getString("content"))) {
//				sComment.setContent("");
//			}else {
//				sComment.setContent(jo.getString("content"));
//			}
//			
////			sComment.setCost(jo.getInteger("cost"));
//			if (!jo.containsKey("cost") || null == jo.getString("cost") || "".equals(jo.getString("cost"))) {
//				sComment.setCost(0);
//			}else {
//				sComment.setCost(jo.getInteger("cost"));
//			}
//			
////			sComment.setId(jo.getInteger("id"));
//			if (!jo.containsKey("id") || null == jo.getString("id") || "".equals(jo.getString("id"))) {
//				sComment.setId(-1);
//			}else {
//				sComment.setId(jo.getInteger("id"));
//			}
//			
////			sComment.setPic(jo.getString("pic"));
//			if (!jo.containsKey("pic") || null == jo.getString("pic") || "".equals(jo.getString("pic"))) {
//				sComment.setPic("");
//			}else {
//				sComment.setPic(jo.getString("pic"));
//			}
//			
////			sComment.setShop_code(jo.getString("shop_code"));
//			if (!jo.containsKey("shop_code") || null == jo.getString("shop_code") || "".equals(jo.getString("shop_code"))) {
//				sComment.setShop_code("");
//			}else {
//				sComment.setShop_code(jo.getString("shop_code"));
//			}
//			
////			sComment.setShop_color(jo.getString("shop_color"));
//			if (!jo.containsKey("shop_color") || null == jo.getString("shop_color") || "".equals(jo.getString("shop_color"))) {
//				sComment.setShop_color("");
//			}else {
//				sComment.setShop_color(jo.getString("shop_color"));
//			}
//			
////			sComment.setShop_name(jo.getString("shop_name"));
//			if (!jo.containsKey("shop_name") || null == jo.getString("shop_name") || "".equals(jo.getString("shop_name"))) {
//				sComment.setShop_name("");
//			}else {
//				sComment.setShop_name(jo.getString("shop_name"));
//			}
//			
////			sComment.setShop_price(jo.getString("shop_price"));
//			if (!jo.containsKey("shop_price") || null == jo.getString("shop_price") || "".equals(jo.getString("shop_price"))) {
//				sComment.setShop_price("0");
//			}else {
//				sComment.setShop_price(jo.getString("shop_price"));
//			}
//			
////			sComment.setShop_size(jo.getString("shop_size"));
//			if (!jo.containsKey("shop_size") || null == jo.getString("shop_size") || "".equals(jo.getString("shop_size"))) {
//				sComment.setShop_size("0");
//			}else {
//				sComment.setShop_size(jo.getString("shop_size"));
//			}
//			
////			sComment.setStar(jo.getInteger("star"));
//			if (!jo.containsKey("star") || null == jo.getString("star") || "".equals(jo.getString("star"))) {
//				sComment.setStar(-1);
//			}else {
//				sComment.setStar(jo.getInteger("star"));
//			}
//			
////			sComment.setStore_code(jo.getString("store_code"));
//			if (!jo.containsKey("store_code") || null == jo.getString("store_code") || "".equals(jo.getString("store_code"))) {
//				sComment.setStore_code("");
//			}else {
//				sComment.setStore_code(jo.getString("store_code"));
//			}
//			
////			sComment.setSupp_content(jo.getString("supp_content"));
//			if (!jo.containsKey("supp_content") || null == jo.getString("supp_content") || "".equals(jo.getString("supp_content"))) {
//				sComment.setSupp_content("");
//			}else {
//				sComment.setSupp_content(jo.getString("supp_content"));
//			}
//			
////			sComment.setSupp_pic(jo.getString("supp_pic"));
//			if (!jo.containsKey("supp_pic") || null == jo.getString("supp_pic") || "".equals(jo.getString("supp_pic"))) {
//				sComment.setSupp_pic("");
//			}else {
//				sComment.setSupp_pic(jo.getString("supp_pic"));
//			}
//			
////			sComment.setType(jo.getInteger("type"));
//			if (!jo.containsKey("type") || null == jo.getString("type") || "".equals(jo.getString("type"))) {
//				sComment.setType(-1);
//			}else {
//				sComment.setType(jo.getInteger("type"));
//			}
//			
////			sComment.setUser_id(jo.getInteger("user_id"));
//			if (!jo.containsKey("user_id") || null == jo.getString("user_id") || "".equals(jo.getString("user_id"))) {
//				sComment.setUser_id(-1);
//			}else {
//				sComment.setUser_id(jo.getInteger("user_id"));
//			}
//			
////			sComment.setUser_name(jo.getString("user_name"));
//			if (!jo.containsKey("user_name") || null == jo.getString("user_name") || "".equals(jo.getString("user_name"))) {
//				sComment.setUser_name("");
//			}else {
//				sComment.setUser_name(jo.getString("user_name"));
//			}
//			
////			sComment.setUser_url(jo.getString("user_url"));
//			if (!jo.containsKey("user_url") || null == jo.getString("user_url") || "".equals(jo.getString("user_url"))) {
//				sComment.setUser_url("");
//			}else {
//				sComment.setUser_url(jo.getString("user_url"));
//			}
//			
////			sComment.setWork(jo.getInteger("work"));
//			if (!jo.containsKey("work") || null == jo.getString("work") || "".equals(jo.getString("work"))) {
//				sComment.setWork(-1);
//			}else {
//				sComment.setWork(jo.getInteger("work"));
//			}
//			
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
////						suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						if (!joSupp.has("supp_add_date") || null == joSupp.getString("supp_add_date") || "".equals(joSupp.getString("supp_add_date"))) {
//							suppComment.setSupp_add_date(0);
//						}else {
//							suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						}
//						
////						suppComment.setSupp_content(joSupp.optString("supp_content"));
//						if (!joSupp.has("supp_content") || null == joSupp.getString("supp_content") || "".equals(joSupp.getString("supp_content"))) {
//							suppComment.setSupp_content("");
//						}else {
//							suppComment.setSupp_content(joSupp.optString("supp_content"));
//						}
//						
//						suppComments.add(suppComment);
//					}
//					sComment.setSuppComment(suppComments);
//				}
//			}
//			
//			
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
////						comment.setAdd_date(joComment.optLong("add_date", 0));
//						if (!joComment.has("supp_content") || null == joComment.getString("supp_content") || "".equals(joComment.getString("supp_content"))) {
//							comment.setAdd_date(0);
//						}else {
//							comment.setAdd_date(joComment.optLong("add_date", 0));
//						}
//						
////						comment.setContent(joComment.optString("content"));
//						if (!joComment.has("content") || null == joComment.getString("content") || "".equals(joComment.getString("content"))) {
//							comment.setContent("");
//						}else {
//							comment.setContent(joComment.optString("content"));
//						}
//						
////						comment.setPic(joComment.optString("pic"));
//						if (!joComment.has("pic") || null == joComment.getString("pic") || "".equals(joComment.getString("pic"))) {
//							comment.setPic("");
//						}else {
//							comment.setPic(joComment.optString("pic"));
//						}
//						
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
////						suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						if (!joSupp.has("pic") || null == joSupp.getString("pic") || "".equals(joSupp.getString("pic"))) {
//							suppComment.setSupp_add_date(0);
//						}else {
//							suppComment.setSupp_add_date(joSupp.optLong("supp_add_date", 0));
//						}
//						
////						suppComment.setSupp_content(joSupp.optString("supp_content"));
//						if (!joSupp.has("supp_content") || null == joSupp.getString("supp_content") || "".equals(joSupp.getString("supp_content"))) {
//							suppComment.setSupp_content("");
//						}else {
//							suppComment.setSupp_content(joSupp.optString("supp_content"));
//						}
//						
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
//	
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
////		String text = j.getString("listdt");
//		String text="";
//		if (!j.has("supp_content") || null == j.getString("supp_content") || "".equals(j.getString("supp_content"))) {
//			text="";
//		}else {
//			text = j.getString("listdt");
//		}
//		
//		List<DeliveryAddress> list = JSON.parseArray(text, DeliveryAddress.class);
//
//		return list;
//	}
//
//	
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
////		String text = j.getString("address");
//		String text="";
//		if (!j.has("address") || null == j.getString("address") || "".equals(j.getString("address"))) {
//			text="";
//		}else {
//			text = j.getString("address");
//		}
//		
//		HashMap<String, String> mapRet = new HashMap<String, String>();
//		JSONObject jo = new JSONObject(text);
//		if (jo == null || jo.equals("") || text.equals("{}")) {
//			return null;
//		}
////		mapRet.put("address", jo.getString("address"));
//		if (!jo.has("address") || null == jo.getString("address") || "".equals(jo.getString("address"))) {
//			mapRet.put("address", "");
//		}else {
//			mapRet.put("address", jo.getString("address"));
//		}
//		
////		mapRet.put("consignee", jo.getString("consignee"));
//		if (!jo.has("consignee") || null == jo.getString("consignee") || "".equals(jo.getString("consignee"))) {
//			mapRet.put("consignee", "");
//		}else {
//			mapRet.put("consignee", jo.getString("consignee"));
//		}
//		
////		mapRet.put("phone", jo.getString("phone"));
//		if (!jo.has("phone") || null == jo.getString("phone") || "".equals(jo.getString("phone"))) {
//			mapRet.put("phone", "-1");
//		}else {
//			mapRet.put("phone", jo.getString("phone"));
//		}
//		
////		mapRet.put("postcode", jo.getString("postcode"));
//		if (!jo.has("postcode") || null == jo.getString("postcode") || "".equals(jo.getString("postcode"))) {
//			mapRet.put("postcode", "-1");
//		}else {
//			mapRet.put("postcode", jo.getString("postcode"));
//		}
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
////		String text = j.getString("stocktype");
//		String text ="";
//		if (!j.has("stocktype") || null == j.getString("stocktype") || "".equals(j.getString("stocktype"))) {
//			text="";
//		}else {
//			text = j.getString("stocktype");
//		}
//		
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
////		String text = j.getString("stocktype");
//		String text ="";
//		if (!j.has("stocktype") || null == j.getString("stocktype") || "".equals(j.getString("stocktype"))) {
//			text="";
//		}else {
//			text = j.getString("stocktype");
//		}
//		
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
////		String text = j.getString("stocktype");
//		String text ="";
//		if (!j.has("stocktype") || null == j.getString("stocktype") || "".equals(j.getString("stocktype"))) {
//			text="";
//		}else {
//			text = j.getString("stocktype");
//		}
//		
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
////		String text = j.getString("shops");
//		String text ="";
//		if (!j.has("shops") || null == j.getString("shops") || "".equals(j.getString("shops"))) {
//			text="";
//		}else {
//			text = j.getString("shops");
//		}
//		
//		List<OrderShop> list = JSON.parseArray(text, OrderShop.class);
//		return list;
//	}
//
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
////		retInfo.put("praise_count", j.getString("praise_count"));
//		if (!j.has("praise_count") || null == j.getString("praise_count") || "".equals(j.getString("praise_count"))) {
//			retInfo.put("praise_count", "0");
//		}else {
//			retInfo.put("praise_count", j.getString("praise_count"));
//		}
//		
////		retInfo.put("eva_count", j.getString("eva_count"));
//		if (!j.has("eva_count") || null == j.getString("eva_count") || "".equals(j.getString("eva_count"))) {
//			retInfo.put("eva_count", "0");
//		}else {
//			retInfo.put("eva_count", j.getString("eva_count"));
//		}
//		
////		retInfo.put("med_count", j.getString("med_count"));
//		if (!j.has("med_count") || null == j.getString("med_count") || "".equals(j.getString("med_count"))) {
//			retInfo.put("med_count", "0");
//		}else {
//			retInfo.put("med_count", j.getString("med_count"));
//		}
//		
////		retInfo.put("bad_count", j.getString("bad_count"));
//		if (!j.has("bad_count") || null == j.getString("bad_count") || "".equals(j.getString("bad_count"))) {
//			retInfo.put("bad_count", "0");
//		}else {
//			retInfo.put("bad_count", j.getString("bad_count"));
//		}
//		
////		retInfo.put("status", j.getString("status"));
//		if (!j.has("status") || null == j.getString("status") || "".equals(j.getString("status"))) {
//			retInfo.put("status", "-1");
//		}else {
//			retInfo.put("status", j.getString("status"));
//		}
//		
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
////		mapRet.put("status", j.has("status") ? j.getString("status") : "");
//		if (!j.has("status") || null == j.getString("status") || "".equals(j.getString("status"))) {
//			mapRet.put("status", "-3");
//		}else {
//			mapRet.put("status", j.has("status") ? j.getString("status") : "");
//		}
//		
////		mapRet.put("message", j.has("message") ? j.getString("message") : "");
//		if (!j.has("message") || null == j.getString("message") || "".equals(j.getString("message"))) {
//			mapRet.put("message", "");
//		}else {
//			mapRet.put("message", j.has("message") ? j.getString("message") : "");
//		}
//
//		JSONObject jo = j.getJSONObject("comment");
////		mapRet.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");// 商品编号
//		if (!jo.has("shop_code") || null == jo.getString("shop_code") || "".equals(jo.getString("shop_code"))) {
//			mapRet.put("shop_code", "");// 商品编号
//		}else {
//			mapRet.put("shop_code", jo.has("shop_code") ? jo.getString("shop_code") : "");// 商品编号
//		}
//		
////		mapRet.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//		if (!jo.has("user_name") || null == jo.getString("user_name") || "".equals(jo.getString("user_name"))) {
//			mapRet.put("user_name",  "");
//		}else {
//			mapRet.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//		}
//		
////		mapRet.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//		if (!jo.has("user_url") || null == jo.getString("user_url") || "".equals(jo.getString("user_url"))) {
//			mapRet.put("user_url", "");
//		}else {
//			mapRet.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//		}
//		
////		mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
//		if (!jo.has("user_id") || null == jo.getString("user_id") || "".equals(jo.getString("user_id"))) {
//			mapRet.put("user_id", "0");
//		}else {
//			mapRet.put("user_id", jo.has("user_id") ? jo.getString("user_id") : "");
//		}
//		
////		mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
//		if (!jo.has("content") || null == jo.getString("content") || "".equals(jo.getString("content"))) {
//			mapRet.put("content",  "");
//		}else {
//			mapRet.put("content", jo.has("content") ? jo.getString("content") : "");
//		}
//		
////		mapRet.put("pic", jo.has("pic") ? jo.getString("pic") : "");// 评论图片
//		if (!jo.has("pic") || null == jo.getString("pic") || "".equals(jo.getString("pic"))) {
//			mapRet.put("pic", "");// 评论图片
//		}else {
//			mapRet.put("pic", jo.has("pic") ? jo.getString("pic") : "");// 评论图片
//		}
//		
////		mapRet.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//		if (!jo.has("add_date") || null == jo.getString("add_date") || "".equals(jo.getString("add_date"))) {
//			mapRet.put("add_date", "");
//		}else {
//			mapRet.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//		}
//		
////		mapRet.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
//		if (!jo.has("comment_size") || null == jo.getString("comment_size") || "".equals(jo.getString("comment_size"))) {
//			mapRet.put("comment_size", "");
//		}else {
//			mapRet.put("comment_size", jo.has("comment_size") ? jo.getString("comment_size") : "");
//		}
//		
////		mapRet.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
//		if (!jo.has("click_size") || null == jo.getString("click_size") || "".equals(jo.getString("click_size"))) {
//			mapRet.put("click_size", "");
//		}else {
//			mapRet.put("click_size", jo.has("click_size") ? jo.getString("click_size") : "");
//		}
//		
////		mapRet.put("click", jo.has("click") ? jo.getString("click") : "");
//		if (!jo.has("click") || null == jo.getString("click") || "".equals(jo.getString("click"))) {
//			mapRet.put("click", "");
//		}else {
//			mapRet.put("click", jo.has("click") ? jo.getString("click") : "");
//		}
//
////		mapRet.put("count", jo.has("count") ? jo.getString("count") : "");// 本期参与次数
//		if (!jo.has("count") || null == jo.getString("count") || "".equals(jo.getString("count"))) {
//			mapRet.put("count",  "0");// 本期参与次数
//		}else {
//			mapRet.put("count", jo.has("count") ? jo.getString("count") : "");// 本期参与次数
//		}
//		
////		mapRet.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");// 期数
//		if (!jo.has("issue_code") || null == jo.getString("issue_code") || "".equals(jo.getString("issue_code"))) {
//			mapRet.put("issue_code", "0");// 期数
//		}else {
//			mapRet.put("issue_code", jo.has("issue_code") ? jo.getString("issue_code") : "");// 期数
//		}
//		
////		mapRet.put("lucky_number", jo.has("lucky_number") ? jo.getString("lucky_number") : "");// 幸运号码
//		if (!jo.has("lucky_number") || null == jo.getString("lucky_number") || "".equals(jo.getString("lucky_number"))) {
//			mapRet.put("lucky_number",  "0");// 幸运号码
//		}else {
//			mapRet.put("lucky_number", jo.has("lucky_number") ? jo.getString("lucky_number") : "");// 幸运号码
//		}
//		
////		mapRet.put("otime", jo.has("otime") ? jo.getString("otime") : "");// 揭晓时间
//		if (!jo.has("otime") || null == jo.getString("otime") || "".equals(jo.getString("otime"))) {
//			mapRet.put("otime",  "0");
//		}else {
//			mapRet.put("otime", jo.has("otime") ? jo.getString("otime") : "");
//		}
//		
////		mapRet.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");// 商品名称
//		if (!jo.has("shop_name") || null == jo.getString("shop_name") || "".equals(jo.getString("shop_name"))) {
//			mapRet.put("shop_name", "");
//		}else {
//			mapRet.put("shop_name", jo.has("shop_name") ? jo.getString("shop_name") : "");
//		}
//		
//		LogYiFu.e("shaidanMap", mapRet.toString());
//		return mapRet;
//	}
//
//	
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
////			commentMap.put("to_user_id", jo.has("to_user_id") ? jo.getString("to_user_id") : "");
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") || "".equals(jo.getString("shop_name"))) {
//				commentMap.put("to_user_id",  "");
//			}else {
//				commentMap.put("to_user_id", jo.has("to_user_id") ? jo.getString("to_user_id") : "");
//			}
//			
////			commentMap.put("to_user_name", jo.has("to_user_name") ? jo.getString("to_user_name") : "");
//			if (!jo.has("to_user_name") || null == jo.getString("to_user_name") || "".equals(jo.getString("to_user_name"))) {
//				commentMap.put("to_user_name", "");
//			}else {
//				commentMap.put("to_user_name", jo.has("to_user_name") ? jo.getString("to_user_name") : "");
//			}
//			
////			commentMap.put("reuser_id", jo.has("reuser_id") ? jo.getString("reuser_id") : "");
//			if (!jo.has("reuser_id") || null == jo.getString("reuser_id") || "".equals(jo.getString("reuser_id"))) {
//				commentMap.put("reuser_id", "0");
//			}else {
//				commentMap.put("reuser_id", jo.has("reuser_id") ? jo.getString("reuser_id") : "");
//			}
//			
////			commentMap.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//			if (!jo.has("add_date") || null == jo.getString("add_date") || "".equals(jo.getString("add_date"))) {
//				commentMap.put("add_date", "");
//			}else {
//				commentMap.put("add_date", jo.has("add_date") ? jo.getString("add_date") : "");
//			}
//			
////			commentMap.put("content", jo.has("content") ? jo.getString("content") : "");
//			if (!jo.has("content") || null == jo.getString("content") || "".equals(jo.getString("content"))) {
//				commentMap.put("content", "");
//			}else {
//				commentMap.put("content", jo.has("content") ? jo.getString("content") : "");
//			}
//			
////			commentMap.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//			if (!jo.has("user_name") || null == jo.getString("user_name") || "".equals(jo.getString("user_name"))) {
//				commentMap.put("user_name", "");
//			}else {
//				commentMap.put("user_name", jo.has("user_name") ? jo.getString("user_name") : "");
//			}
//			
////			commentMap.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//			if (!jo.has("user_url") || null == jo.getString("user_url") || "".equals(jo.getString("user_url"))) {
//				commentMap.put("user_url", "");
//			}else {
//				commentMap.put("user_url", jo.has("user_url") ? jo.getString("user_url") : "");
//			}
//			
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
////			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			if (!jsonObject.has("message") || null == jsonObject.getString("message") || "".equals(jsonObject.getString("message"))) {
//				mapRet.put("message", "");
//			}else {
//				mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			}
//			
//			
//			return mapRet;
//		}
//		return null;
//
//	}
//
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
////			mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			if (!jsonObject.has("message") || null == jsonObject.getString("message") || "".equals(jsonObject.getString("message"))) {
//				mapRet.put("message", "");
//			}else {
//				mapRet.put("message", jsonObject.has("message") ? jsonObject.getString("message") : "");
//			}
//			
//			return mapRet;
//		}
//		return null;
//
//	}
//
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
////			map.put("def_pic", jo.getString("def_pic"));
//			if (!jo.has("def_pic") || null == jo.getString("def_pic") || "".equals(jo.getString("def_pic"))) {
//				map.put("def_pic", "");
//			}else {
//				map.put("def_pic", jo.getString("def_pic"));
//			}
//			
////			map.put("shop_price", jo.getString("shop_price"));
//			if (!jo.has("shop_price") || null == jo.getString("shop_price") || "".equals(jo.getString("shop_price"))) {
//				map.put("shop_price", "-1");
//			}else {
//				map.put("shop_price", jo.getString("shop_price"));
//			}
//			
////			map.put("shop_code", jo.getString("shop_code"));
//			if (!jo.has("shop_code") || null == jo.getString("shop_code") || "".equals(jo.getString("shop_code"))) {
//				map.put("shop_code", "");
//			}else {
//				map.put("shop_code", jo.getString("shop_code"));
//			}
//			
////			map.put("shop_se_price", jo.getString("shop_se_price"));
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") || "".equals(jo.getString("shop_se_price"))) {
//				map.put("shop_se_price", "-1");
//			}else {
//				map.put("shop_se_price", jo.getString("shop_se_price"));
//			}
//			
////			map.put("shop_name", jo.getString("shop_name"));
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") || "".equals(jo.getString("shop_name"))) {
//				map.put("shop_name", "");
//			}else {
//				map.put("shop_name", jo.getString("shop_name"));
//			}
//			
//			listGoods.add(map);
//		}
//		
//		//接这开始
//		//接这开始
//		//接这开始
//		//接这开始
//		//接这开始
//		
//		
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
////			map.put("shop_price", jo.getString("shop_price"));
//			if (!jo.has("shop_price") || null == jo.getString("shop_price") || "".equals(jo.getString("shop_price"))) {
//				map.put("shop_price", "0");
//			}else {
//				map.put("shop_price", jo.getString("shop_price"));
//			}
//			
////			map.put("shop_code", jo.getString("shop_code"));
//			if (!jo.has("shop_code") || null == jo.getString("shop_code") || "".equals(jo.getString("shop_code"))) {
//				map.put("shop_code", jo.getString(""));
//			}else {
//				map.put("shop_code", jo.getString("shop_code"));
//			}
//			
////			map.put("shop_se_price", jo.getString("shop_se_price"));
//			if (!jo.has("shop_se_price") || null == jo.getString("shop_se_price") || "".equals(jo.getString("shop_se_price"))) {
//				map.put("shop_se_price", "0");
//			}else {
//				map.put("shop_se_price", jo.getString("shop_se_price"));
//			}
//			
////			map.put("shop_name", jo.getString("shop_name"));
//			if (!jo.has("shop_name") || null == jo.getString("shop_name") || "".equals(jo.getString("shop_name"))) {
//				map.put("shop_name", "");
//			}else {
//				map.put("shop_name", jo.getString("shop_name"));
//			}
//			
////			map.put("four_pic", jo.getString("four_pic"));
//			if (!jo.has("four_pic") || null == jo.getString("four_pic") || "".equals(jo.getString("four_pic"))) {
//				map.put("four_pic", "");
//			}else {
//				map.put("four_pic", jo.getString("four_pic"));
//			}
//			
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
////		retInfo.put("order_code", j.getString("order_code"));
//		if (!j.has("order_code") || null == j.getString("order_code") || "".equals(j.getString("order_code"))) {
//			retInfo.put("order_code", "");
//		}else {
//			retInfo.put("order_code", j.getString("order_code"));
//		}
//		
////		retInfo.put("url", j.getInt("url"));
//		if (!j.has("url") || null == j.getString("url") || "".equals(j.getString("url"))) {
//			retInfo.put("url", 0);
//		}else {
//			retInfo.put("url", j.getInt("url"));
//		}
//		
////		retInfo.put("price", j.getDouble("price"));
//		if (!j.has("price") || null == j.getString("price") || "".equals(j.getString("price"))) {
//			retInfo.put("price", 0);
//		}else {
//			retInfo.put("price", j.getDouble("price"));
//		}
//		
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
////		retInfo.put("g_code", j.getString("g_code"));
//		if (!j.has("g_code") || null == j.getString("g_code") || "".equals(j.getString("g_code"))) {
//			retInfo.put("g_code", "");
//		}else {
//			retInfo.put("g_code", j.getString("g_code"));
//		}
//		
////		retInfo.put("url", j.getInt("url"));
//		if (!j.has("url") || null == j.getString("url") || "".equals(j.getString("url"))) {
//			retInfo.put("url", 0);
//		}else {
//			retInfo.put("url", j.getInt("url"));
//		}
//		
////		retInfo.put("price", j.getDouble("price"));
//		if (!j.has("price") || null == j.getString("price") || "".equals(j.getString("price"))) {
//			retInfo.put("price", 0);
//		}else {
//			retInfo.put("price", j.getDouble("price"));
//		}
//		
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
////		retInfo.put("order_code", j.getString("order_code"));
//		if (!j.has("order_code") || null == j.getString("order_code") || "".equals(j.getString("order_code"))) {
//			retInfo.put("order_code", "");
//		}else {
//			retInfo.put("order_code", j.getString("order_code"));
//		}
//		
////		retInfo.put("url", j.getInt("url"));
//		if (!j.has("url") || null == j.getString("url") || "".equals(j.getString("url"))) {
//			retInfo.put("url", 0);
//		}else {
//			retInfo.put("url", j.getInt("url"));
//		}
//		
////		retInfo.put("price", j.getDouble("price"));
//		if (!j.has("price") || null == j.getString("price") || "".equals(j.getString("price"))) {
//			retInfo.put("price", 0);
//		}else {
//			retInfo.put("price", j.getDouble("price"));
//		}
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
////		map.put("nonce_str", jo.getString("nonce_str"));
//		if (!jo.has("nonce_str") || null == jo.getString("nonce_str") || "".equals(jo.getString("nonce_str"))) {
//			map.put("nonce_str", "");
//		}else {
//			map.put("nonce_str", jo.getString("nonce_str"));
//		}
//		
////		map.put("appid", jo.getString("appid"));
//		if (!jo.has("appid") || null == jo.getString("appid") || "".equals(jo.getString("appid"))) {
//			map.put("appid", "");
//		}else {
//			map.put("appid", jo.getString("appid"));
//		}
//		
////		map.put("sign", jo.getString("sign"));
//		if (!jo.has("sign") || null == jo.getString("sign") || "".equals(jo.getString("sign"))) {
//			map.put("sign", "");
//		}else {
//			map.put("sign", jo.getString("sign"));
//		}
//		
////		map.put("trade_type", jo.getString("trade_type"));
//		if (!jo.has("trade_type") || null == jo.getString("trade_type") || "".equals(jo.getString("trade_type"))) {
//			map.put("trade_type", "");
//		}else {
//			map.put("trade_type", jo.getString("trade_type"));
//		}
//		
////		map.put("return_msg", jo.getString("return_msg"));
//		if (!jo.has("return_msg") || null == jo.getString("return_msg") || "".equals(jo.getString("return_msg"))) {
//			map.put("return_msg", "");
//		}else {
//			map.put("return_msg", jo.getString("return_msg"));
//		}
//		
////		map.put("result_code", jo.getString("result_code"));
//		if (!jo.has("result_code") || null == jo.getString("result_code") || "".equals(jo.getString("result_code"))) {
//			map.put("result_code", "");
//		}else {
//			map.put("result_code", jo.getString("result_code"));
//		}
//		
////		map.put("mch_id", jo.getString("mch_id"));
//		if (!jo.has("mch_id") || null == jo.getString("mch_id") || "".equals(jo.getString("mch_id"))) {
//			map.put("mch_id", "0");
//		}else {
//			map.put("mch_id", jo.getString("mch_id"));
//		}
//		
////		map.put("return_code", jo.getString("return_code"));
//		if (!jo.has("return_code") || null == jo.getString("return_code") || "".equals(jo.getString("return_code"))) {
//			map.put("return_code", "");
//		}else {
//			map.put("return_code", jo.getString("return_code"));
//		}
//		
////		map.put("prepay_id", jo.getString("prepay_id"));
//		if (!jo.has("prepay_id") || null == jo.getString("prepay_id") || "".equals(jo.getString("prepay_id"))) {
//			map.put("prepay_id", "0");
//		}else {
//			map.put("prepay_id", jo.getString("prepay_id"));
//		}
//		
//		return map;
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
////		mapRet.put("status", j.getString(RetInfo.status));
//		if (!j.has(RetInfo.status) || null == j.getString(RetInfo.status) || "".equals(j.getString(RetInfo.status))) {
//			mapRet.put("status", "-5");
//		}else {
//			mapRet.put("status", j.getString(RetInfo.status));
//		}
//		
////		mapRet.put("message", j.getString(RetInfo.message));
//		if (!j.has(RetInfo.message) || null == j.getString(RetInfo.message) || "".equals(j.getString(RetInfo.message))) {
//			mapRet.put("message", "");
//		}else {
//			mapRet.put("message", j.getString(RetInfo.message));
//		}
//		
//		JSONObject jo = new JSONObject(j.getString("logistics"));
//		if (null == jo || jo.equals("")) {
//			return null;
//		}
////		mapRet.put("logi_name", jo.getString("logi_name"));
//		if (!jo.has("logi_name") || null == jo.getString("logi_name") || "".equals(jo.getString("logi_name"))) {
//			mapRet.put("logi_name", "");
//		}else {
//			mapRet.put("logi_name", jo.getString("logi_name"));
//		}
//		
////		mapRet.put("logi_code", jo.getString("logi_code"));
//		if (!jo.has("logi_name") || null == jo.getString("logi_name") || "".equals(jo.getString("logi_name"))) {
//			mapRet.put("logi_code", "");
//		}else {
//			mapRet.put("logi_name", jo.getString("logi_name"));
//		}
//		
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
////			mapObject.put("time", jo.optString("time"));
//			if (!jo.has("time") || null == jo.getString("time") || "".equals(jo.getString("time"))) {
//				mapObject.put("time", "0");
//			}else {
//				mapObject.put("time", jo.optString("time"));
//			}
//			
////			mapObject.put("context", jo.optString("context"));
//			if (!jo.has("context") || null == jo.getString("context") || "".equals(jo.getString("context"))) {
//				mapObject.put("context", "");
//			}else {
//				mapObject.put("context", jo.optString("context"));
//			}
//			
////			mapObject.put("ftime", jo.optString("ftime"));
//			if (!jo.has("ftime") || null == jo.getString("ftime") || "".equals(jo.getString("ftime"))) {
//				mapObject.put("ftime", "0");
//			}else {
//				mapObject.put("ftime", jo.optString("ftime"));
//			}
//			
//			retInfo.add(mapObject);
//		}
//		return retInfo;
//	}
//
//}

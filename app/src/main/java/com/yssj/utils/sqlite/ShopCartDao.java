package com.yssj.utils.sqlite;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.umeng.socialize.utils.Log;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.ShopCartCommonFragment;
import com.yssj.ui.activity.shopdetails.StockBean;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.DocumentsProvider;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class ShopCartDao {
	private ShopCartDBOpenHelper mHelper;

	public ShopCartDao(Context context) {
		mHelper = new ShopCartDBOpenHelper(context);
	}

	/**
	 * 添加的方法
	 * 
	 * @param number
	 * @param type
	 * @return true添加成功，false添加失败
	 */
	public boolean add(String shop_code, String size, String color, int shop_num, int stock_type_id, String def_pic,
			int user_id, String shop_name, String store_code,

			String shop_price, String shop_se_price, String supp_id, String kickback, String original_price,
			int is_meal_flag, String p_code, String postage, String p_s_t_id, String p_shop_code, String p_color,
			int id, String p_name, int valid, String supp_label) {
		SQLiteDatabase db = mHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ShopCartDB.TableBlack.shop_code, shop_code);
		values.put(ShopCartDB.TableBlack.size, size);
		values.put(ShopCartDB.TableBlack.color, color);
		values.put(ShopCartDB.TableBlack.shop_num, shop_num);
		values.put(ShopCartDB.TableBlack.stock_type_id, stock_type_id);
		values.put(ShopCartDB.TableBlack.def_pic, def_pic);
		values.put(ShopCartDB.TableBlack.user_id, user_id);
		values.put(ShopCartDB.TableBlack.shop_name, shop_name);
		values.put(ShopCartDB.TableBlack.store_code, store_code);
		values.put(ShopCartDB.TableBlack.shop_price, shop_price);
		values.put(ShopCartDB.TableBlack.shop_se_price, shop_se_price);
		values.put(ShopCartDB.TableBlack.supp_id, supp_id);
		values.put(ShopCartDB.TableBlack.kickback, kickback);
		values.put(ShopCartDB.TableBlack.original_price, original_price);
		values.put(ShopCartDB.TableBlack.valid, valid);
		values.put(ShopCartDB.TableBlack.is_del, 0);
		values.put(ShopCartDB.TableBlack.is_meal_flag, is_meal_flag);
		values.put(ShopCartDB.TableBlack.p_code, p_code);
		values.put(ShopCartDB.TableBlack.postage, postage);
		values.put(ShopCartDB.TableBlack.p_s_t_id, p_s_t_id);
		values.put(ShopCartDB.TableBlack.p_shop_code, p_shop_code);
		values.put(ShopCartDB.TableBlack.p_color, p_color);
		values.put(ShopCartDB.TableBlack.id, id);
		values.put(ShopCartDB.TableBlack.p_name, p_name);
		values.put(ShopCartDB.TableBlack.supp_label, supp_label);
		// LogYiFu.e("zzq", values.toString());
		long insert = db.insert(ShopCartDB.TableBlack.TABLE_NAME, null, values);

		db.close();

		return insert != -1;
	}

	/**
	 * 添加失效的方法
	 * 
	 * @param number
	 * @param type
	 * @return true添加成功，false添加失败
	 */
	public boolean add_invalid(String shop_code, String size, String color, int shop_num, int stock_type_id,
			String def_pic, int user_id, String shop_name, String store_code,

			String shop_price, String shop_se_price, String supp_id, String kickback, String original_price,
			int is_meal_flag, String p_code, String postage, String p_s_t_id, String p_shop_code, String p_color,
			int id, String p_name, int valid) {
		SQLiteDatabase db = mHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ShopCartDB.TableBlack.shop_code, shop_code);
		values.put(ShopCartDB.TableBlack.size, size);
		values.put(ShopCartDB.TableBlack.color, color);
		values.put(ShopCartDB.TableBlack.shop_num, shop_num);
		values.put(ShopCartDB.TableBlack.stock_type_id, stock_type_id);
		values.put(ShopCartDB.TableBlack.def_pic, def_pic);
		values.put(ShopCartDB.TableBlack.user_id, user_id);
		values.put(ShopCartDB.TableBlack.shop_name, shop_name);
		values.put(ShopCartDB.TableBlack.store_code, store_code);
		values.put(ShopCartDB.TableBlack.shop_price, shop_price);
		values.put(ShopCartDB.TableBlack.shop_se_price, shop_se_price);
		values.put(ShopCartDB.TableBlack.supp_id, supp_id);
		values.put(ShopCartDB.TableBlack.kickback, kickback);
		values.put(ShopCartDB.TableBlack.original_price, original_price);
		values.put(ShopCartDB.TableBlack.valid, valid);
		values.put(ShopCartDB.TableBlack.is_del, 0);
		values.put(ShopCartDB.TableBlack.is_meal_flag, is_meal_flag);
		values.put(ShopCartDB.TableBlack.p_code, p_code);
		values.put(ShopCartDB.TableBlack.postage, postage);
		values.put(ShopCartDB.TableBlack.p_s_t_id, p_s_t_id);
		values.put(ShopCartDB.TableBlack.p_shop_code, p_shop_code);
		values.put(ShopCartDB.TableBlack.p_color, p_color);
		values.put(ShopCartDB.TableBlack.id, id);
		values.put(ShopCartDB.TableBlack.p_name, p_name);
		// LogYiFu.e("zzq", values.toString());
		long insert = db.insert(ShopCartDB.TableBlack.TABLE_NAME_INVALID, null, values);

		db.close();

		return insert != -1;
	}

	/**
	 * 从后台获取
	 * 
	 * @param context
	 * @param common
	 * @param special
	 */
	public void common_first_add(Context context, List<ShopCart> common, List<ShopCart> special) {
		ShopCartDao dao = new ShopCartDao(context);
		LogYiFu.e("hzz", "000");
		List<ShopCart> findAll = findAll();
		if (findAll != null) {// 清空有效的数据库列表
			for (int i = 0; i < findAll.size(); i++) {
				if (findAll.get(i).getIs_meal_flag() == 0) {
					dao.delete("" + findAll.get(i).getStock_type_id());
				} else {
					dao.p_delete(findAll.get(i).getP_s_t_id());
				}
			}
		}
		LogYiFu.e("hzz", "111");
		if (common != null) {
			// int s_count = 0;
			// int s_invalid_count = 0;
			// int count_new = 0;
			// long now = System.currentTimeMillis();
			// long s_deadline = Long
			// .parseLong("" + SharedPreferencesUtil.getStringData(context,
			// Pref.SHOPCART_COMMON_TIME, "0"));
			for (int i = 0; i < common.size(); i++) {
				ShopCart s = common.get(i);
				// if (now > s_deadline) {// 全部过期，最多取10件过期的。
				// s_invalid_count += s.getShop_num();
				// count_new++;
				// if (s_invalid_count > 20 || count_new > 10) {// TODO:调接口删除多余的
				// // i后的全删除
				// StringBuffer sb = new StringBuffer();
				// for (int j = i; j < common.size(); j++) {
				// if (j != common.size() - 1) {
				// sb.append(common.get(j).getId()).append(",");
				// } else {
				// sb.append(common.get(j).getId());
				// }
				// }
				// if (sb.toString().length() > 0 &&
				// !("").equals(sb.toString())) {
				// deleteShopCartList(context, sb.toString());
				// }
				// break;
				// } else {// 取10件到失效中
				// dao.add_invalid(s.getShop_code(), s.getSize(), s.getColor(),
				// s.getShop_num(),
				// s.getStock_type_id(), s.getDef_pic(), 0, s.getShop_name(),
				// s.getStore_code() != null ? s.getStore_code() : 0 + "", "" +
				// s.getShop_price(),
				// "" + s.getShop_se_price(), "" + s.getSupp_id(), "" +
				// s.getKickback(),
				// "" + s.getOriginal_price(), 0, null, null, null, null, null,
				// s.getId(), null,0);
				// }
				// } else {// 全部有效，或者部分有效
				// s_count += s.getShop_num();
				// if (s_count > 20) {// 其余的加10件到失效列表中
				// for (int j = i; j < common.size(); j++) {
				// ShopCart s2 = common.get(j);
				// s_invalid_count += s2.getShop_num();
				// count_new++;
				// if (s_invalid_count > 20 || count_new > 10) {//
				// //TODO:调接口删除多余的j后的全删除
				// StringBuffer sb = new StringBuffer();
				// for (int j2 = i; j2 < common.size(); j2++) {
				// if (j2 != common.size() - 1) {
				// sb.append(common.get(j2).getId()).append(",");
				// } else {
				// sb.append(common.get(j2).getId());
				// }
				// }
				// if (sb.toString().length() > 0 &&
				// !("").equals(sb.toString())) {
				// deleteShopCartList(context, sb.toString());
				// }
				// break;
				// } else {// 添加10件失效的
				// dao.add_invalid(s2.getShop_code(), s2.getSize(),
				// s2.getColor(), s2.getShop_num(),
				// s2.getStock_type_id(), s2.getDef_pic(), 0, s2.getShop_name(),
				// s2.getStore_code() != null ? s2.getStore_code() : 0 + "",
				// "" + s2.getShop_price(), "" + s2.getShop_se_price(), "" +
				// s2.getSupp_id(),
				// "" + s2.getKickback(), "" + s2.getOriginal_price(), 0, null,
				// null, null, null,
				// null, s2.getId(), null,0);
				// }
				// }
				// break;
				//
				// } else {// 添加20件有效的
				dao.add(s.getShop_code(), s.getSize(), s.getColor(), s.getShop_num(), s.getStock_type_id(),
						s.getDef_pic(), 0, s.getShop_name(), s.getStore_code() != null ? s.getStore_code() : 0 + "",
						"" + s.getShop_price(), "" + s.getShop_se_price(), "" + s.getSupp_id(), "" + s.getKickback(),
						"" + s.getOriginal_price(), 0, null, null, null, null, null, s.getId(), null, 0,
						s.getSupp_label());
				// }
				// }
				// dao.add(s.getShop_code(), s.getSize(), s.getColor(),
				// s.getShop_num(), s.getStock_type_id(),
				// s.getDef_pic(), 0, s.getShop_name(), s.getStore_code() !=
				// null ? s.getStore_code() : 0 + "",
				// "" + s.getShop_price(), "" + s.getShop_se_price(), "" +
				// s.getSupp_id(), "" + s.getKickback(),
				// "" + s.getOriginal_price(), 0, null, null, null, null, null,
				// s.getId(), null);
			}
		}
		LogYiFu.e("hzz", "222");
		if (special != null) {
			int p_count = 0;
			int p_invalid_count = 0;
			int p_count_new = 0;
			long now = System.currentTimeMillis();
			long p_deadline = Long
					.parseLong("" + SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0"));
			for (int i = 0; i < special.size(); i++) {
				ShopCart p = special.get(i);
				List<StockBean> shop_list = p.getShop_list();
				StringBuffer mShop_code_p = new StringBuffer();
				StringBuffer mColor_p = new StringBuffer();
				LogYiFu.e("hzz", "333");
				for (int j = 0; j < shop_list.size(); j++) {
					if (j < shop_list.size() - 1) {
						mShop_code_p.append(shop_list.get(j).getShop_code()).append(",");
						mColor_p.append(shop_list.get(j).getColor()).append(",");
					} else {
						mShop_code_p.append(shop_list.get(j).getShop_code());
						mColor_p.append(shop_list.get(j).getColor());
					}
				}
				// 111111
				if (now > p_deadline) {// 全部过期，最多取10件过期的。
					p_invalid_count += p.getShop_num();
					p_count_new++;
					if (p_invalid_count > 20 || p_count_new > 10) {// TODO:调接口删除多余的
																	// i后的全删除
						StringBuffer sb = new StringBuffer();
						for (int j = i; j < special.size(); j++) {
							if (j != special.size() - 1) {
								sb.append(special.get(j).getId()).append(",");
							} else {
								sb.append(special.get(j).getId());
							}
						}
						if (sb.toString().length() > 0 && !("").equals(sb.toString())) {
							deleteShopCartList(context, sb.toString());
						}
						break;
					} else {// 取10件到失效中
						boolean hh = dao.add_invalid(null, null, null, p.getShop_num(), 0, p.getDef_pic(), 0, null,
								null, "" + p.getShop_price(), "" + p.getShop_se_price(),
								p.getSupp_id() != null ? p.getSupp_id() + ""
										: "" + p.getShop_list().get(0).getSupp_id(),
								"" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(), p.getP_s_t_id(),
								mShop_code_p.toString(), mColor_p.toString(), p.getId() != null ? p.getId() : 0,
								p.getP_s_t_id().split(",").length > 1 ? "超值套餐" : "超值单品", 0);
					}
				} else {// 全部有效，或者部分有效
					p_count += p.getShop_num();
					if (p_count > 20) {// 其余的加10件到失效列表中
						for (int j = i; j < special.size(); j++) {
							ShopCart s2 = special.get(j);
							p_invalid_count += s2.getShop_num();
							p_count_new++;
							if (p_invalid_count > 20 || p_count_new > 10) {// //TODO:调接口删除多余的j后的全删除
								StringBuffer sb = new StringBuffer();
								for (int j2 = i; j2 < special.size(); j2++) {
									if (j2 != common.size() - 1) {
										sb.append(special.get(j2).getId()).append(",");
									} else {
										sb.append(special.get(j2).getId());
									}
								}
								if (sb.toString().length() > 0 && !("").equals(sb.toString())) {
									deleteShopCartList(context, sb.toString());
								}
								break;
							} else {// 添加10件失效的
								boolean hh = dao.add_invalid(null, null, null, p.getShop_num(), 0, p.getDef_pic(), 0,
										null, null, "" + p.getShop_price(), "" + p.getShop_se_price(),
										p.getSupp_id() != null ? p.getSupp_id() + ""
												: "" + p.getShop_list().get(0).getSupp_id(),
										"" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(), p.getP_s_t_id(),
										mShop_code_p.toString(), mColor_p.toString(), p.getId() != null ? p.getId() : 0,
										p.getP_s_t_id().split(",").length > 1 ? "超值套餐" : "超值单品", 0);
							}
						}
						break;

					} else {// 添加20件有效的
						boolean hh = dao.add(null, null, null, p.getShop_num(), 0, p.getDef_pic(), 0, null, null,
								"" + p.getShop_price(), "" + p.getShop_se_price(),
								p.getSupp_id() != null ? p.getSupp_id() + ""
										: "" + p.getShop_list().get(0).getSupp_id(),
								"" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(), p.getP_s_t_id(),
								mShop_code_p.toString(), mColor_p.toString(), p.getId() != null ? p.getId() : 0,
								p.getP_s_t_id().split(",").length > 1 ? "超值套餐" : "超值单品", 0, p.getSupp_label());
					}
				}

				// 111111
				LogYiFu.e("hzz", "555");
				// boolean hh = dao.add(null, null, null, p.getShop_num(), 0,
				// p.getDef_pic(), 0, null, null,
				// "" + p.getShop_price(), "" + p.getShop_se_price(),
				// p.getSupp_id() != null ? p.getSupp_id() + "" : "" +
				// p.getShop_list().get(0).getSupp_id(),
				// "" + 0, "" + 0, 1, p.getP_code(), "" + p.getPostage(),
				// p.getP_s_t_id(), mShop_code_p.toString(),
				// mColor_p.toString(), p.getId() != null ? p.getId() : 0,
				// p.getP_s_t_id().split(",").length > 1 ? "超值套餐" : "超值单品");
			}
		}
		LogYiFu.e("hzz", "444");

	}

	private void deleteShopCartList(final Context context, final String ids) {
		// TODO Auto-generated method stub
		new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) context, null, R.string.wait) {
			@Override
			protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {
				ReturnInfo r = ComModel.deleteShopCartList(context, YCache.getCacheToken(mContext), ids);
				return r;
			}

			@Override
			protected void onPostExecute(FragmentActivity mContext, ReturnInfo r, Exception e) {

				if (e != null) {
					// LogYiFu.e("异常 -----", "异常");
				} else {

				}
			};

			@Override
			protected boolean isHandleException() {
				return true;
			};
		}.execute();
	}

	/**
	 * 更新的方法
	 * 
	 * @param number
	 * @param type
	 * @return
	 */
	// public boolean update(String id, int user_id) {
	// SQLiteDatabase db = mHelper.getWritableDatabase();
	//
	// ContentValues values = new ContentValues();
	// values.put(ShopCartDB.TableBlack.id, id);
	//
	// String whereClause = ShopCartDB.TableBlack.user_id + "=?";
	// int[] whereArgs = new int[] { user_id };
	//
	// int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values,
	// whereClause, whereArgs);
	//
	// db.close();
	// return update > 0;
	// }

	/**
	 * 普通商品删除的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean delete(String stock_type_id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = ShopCartDB.TableBlack.stock_type_id + "=?";
		String[] whereArgs = new String[] { stock_type_id };

		int delete = db.delete(ShopCartDB.TableBlack.TABLE_NAME, whereClause, whereArgs);

		db.close();
		return delete > 0;
	}

	/**
	 * 失效普通商品删除的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean delete_invalid(String stock_type_id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = ShopCartDB.TableBlack.stock_type_id + "=?";
		String[] whereArgs = new String[] { stock_type_id };

		int delete = db.delete(ShopCartDB.TableBlack.TABLE_NAME_INVALID, whereClause, whereArgs);

		db.close();
		return delete > 0;
	}

	/**
	 * 获得购物车总数量
	 * 
	 * @param context
	 * @return
	 */
	public int queryCartCount(Context context) {
		// ShopCartDao dao = new ShopCartDao(context);
		// List<ShopCart> list = dao.findAll();
		// if (list != null) {
		// int a = 0;
		// for (int i = 0; i < list.size(); i++) {
		// a += list.get(i).getShop_num();
		// }
		// return a;
		// } else {
		// return 0;
		// }
		// return queryCartCommonCount(context) +
		// queryCartSpecialCount(context);
		return queryCartCommonCount(context);
	}

	/**
	 * 查询普通商品购物车总数量
	 * 
	 * @param context
	 * @return
	 */
	public int queryCartCommonCount(Context context) {
		ShopCartDao dao = new ShopCartDao(context);
		String time = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0");
		// if (Long.parseLong(time) <= System.currentTimeMillis()) {
		// return 0;
		// } else {

		// if (shopCart.getValid() == 1 || (shopCart.getIs_del() != null ?
		// shopCart.getIs_del() == 1 : false))
		List<ShopCart> list = dao.findAll();
		if (list != null) {
			int a = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getIs_meal_flag() == 0 && list.get(i).getValid() == 0 && list.get(i).getIs_del() != null
						&& list.get(i).getIs_del() == 0) {// 正价且是没有下架的
					a += list.get(i).getShop_num();
				}
			}
			return a;
		} else {
			return 0;
		}
		// }
	}

	/**
	 * 查询特卖购物车总数量
	 * 
	 * @param context
	 * @return
	 */
	public int queryCartSpecialCount(Context context) {
		ShopCartDao dao = new ShopCartDao(context);
		String time = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0");
		if (Long.parseLong(time) <= System.currentTimeMillis()) {
			return 0;
		} else {
			List<ShopCart> list = dao.findAll();
			if (list != null) {
				int a = 0;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIs_meal_flag() == 1) {
						a += list.get(i).getShop_num();
					}
				}
				return a;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 特卖商品删除的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_delete(String p_s_t_id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = ShopCartDB.TableBlack.p_s_t_id + "=?";
		String[] whereArgs = new String[] { p_s_t_id };

		int delete = db.delete(ShopCartDB.TableBlack.TABLE_NAME, whereClause, whereArgs);

		db.close();
		return delete > 0;
	}

	/**
	 * 失效特卖商品删除的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_delete_invalid(String p_s_t_id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		String whereClause = ShopCartDB.TableBlack.p_s_t_id + "=?";
		String[] whereArgs = new String[] { p_s_t_id };

		int delete = db.delete(ShopCartDB.TableBlack.TABLE_NAME_INVALID, whereClause, whereArgs);

		db.close();
		return delete > 0;
	}

	/**
	 * 普通商品修改数量的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean modify(String stock_type_id, int num) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("shop_num", num);
		String whereClause = "stock_type_id=?";
		String[] whereArgs = new String[] { String.valueOf(stock_type_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 普通商品修改颜色，尺寸，数量的方法(现根据购物车id来修改)
	 * 
	 * @param number
	 * @return
	 */
	public boolean modifyColorSize(String id, int num, String color, String size,String pic) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("shop_num", num);
		values.put("size", size);
		values.put("color", color);
		values.put("def_pic",pic);
		String whereClause = "id=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 根据商品编号普通商品修改下架状态的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean modify_state(String shop_code, int valid) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("valid", valid);
		String whereClause = "shop_code=?";
		String[] whereArgs = new String[] { String.valueOf(shop_code) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 根据商品编号普通商品修改下架状态的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean modify_state_invalid(String shop_code, int valid) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("valid", valid);
		String whereClause = "shop_code=?";
		String[] whereArgs = new String[] { String.valueOf(shop_code) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME_INVALID, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 根据商品编号特卖商品修改下架状态的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_modify_state(String p_code, int valid) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("valid", valid);
		String whereClause = "p_code=?";
		String[] whereArgs = new String[] { String.valueOf(p_code) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 根据商品编号特卖商品修改下架状态的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_modify_state_invalid(String p_code, int valid) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("valid", valid);
		String whereClause = "p_code=?";
		String[] whereArgs = new String[] { String.valueOf(p_code) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME_INVALID, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 失效普通商品修改数量的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean modify_invalid(String stock_type_id, int num) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("shop_num", num);
		String whereClause = "stock_type_id=?";
		String[] whereArgs = new String[] { String.valueOf(stock_type_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME_INVALID, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 特卖商品修改的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_modify(String p_s_t_id, int num) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("shop_num", num);
		String whereClause = "p_s_t_id=?";
		String[] whereArgs = new String[] { String.valueOf(p_s_t_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 特卖商品修改的方法
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_modify_invalid(String p_s_t_id, int num) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("shop_num", num);
		String whereClause = "p_s_t_id=?";
		String[] whereArgs = new String[] { String.valueOf(p_s_t_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME_INVALID, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 普通商品修改购物车id
	 * 
	 * @param number
	 * @return
	 */
	public boolean modify_id(String p_s_t_id, int id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", id);
		String whereClause = "p_s_t_id=?";
		String[] whereArgs = new String[] { String.valueOf(p_s_t_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 特卖商品修改购物车id
	 * 
	 * @param number
	 * @return
	 */
	public boolean p_modify_id(String p_s_t_id, int id) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", id);
		String whereClause = "p_s_t_id=?";
		String[] whereArgs = new String[] { String.valueOf(p_s_t_id) };
		int update = db.update(ShopCartDB.TableBlack.TABLE_NAME, values, whereClause, whereArgs);
		return update > 0;
	}

	/**
	 * 查询所有的
	 * 
	 * @return
	 */
	public List<ShopCart> findAll() {
		List<ShopCart> list = new ArrayList<ShopCart>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		// String shop_code = "shop_code"; // 商品编号 0
		// String size = "size"; // 商品尺码 1
		// String color = "color"; // 商品颜色 2
		// String shop_num = "shop_num"; // 商品的购买数量 integer 3
		// String stock_type_id = "stock_type_id";// 库存表id integer 4
		// String def_pic = "def_pic"; // 默认图片地址 5
		// String user_id = "user_id"; // 用户id integer 6
		// String shop_name = "shop_name"; // 商品名称 7
		// String store_code = "store_code"; // 店铺编号 8
		// String shop_price = "shop_price"; // 商品原价 Double 9
		// String shop_se_price = "shop_se_price"; // 商品折后价 Double 10
		// String supp_id = "supp_id";//integer 供货商id 11
		// String kickback = "kickback";// 返佣Double 12
		// String original_price = "original_price";// integer 13
		// String valid = "valid"; // 商品是否有效 0有效 1失效 integer 14
		// String is_del = "is_del";// 商品是否下架，1下架integer 15
		// String is_meal_flag="is_meal_flag";//表里判断是正价还是特卖integer 0代表正价，1代表特卖
		// 16
		// String p_code = "p_code";// 套餐编号 17
		// String postage = "postage";// 邮费 Double 18
		// String p_s_t_id = "p_s_t_id";// 特卖对应的每件商品库存 id，逗号隔开 19
		// // 自己添加
		// String p_shop_code = "p_shop_code"; // 套餐商品编号， 以逗号隔开 20
		// String p_color = "p_color"; // 套餐商品颜色， 以逗号隔开 21
		// String id = "id"; // 主键 ,购物车 integer id 22
		// String p_name = "p_name";// 套餐名字 23
		// String supp_label="supp_label";//供应商 24 新加
		String sql = ShopCartDB.TableBlack.QUERY_ALL_SQL;
		Cursor cursor = db.rawQuery(sql, null);
		LogYiFu.e("hzz", "2");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String shop_code = "0";

				if (!"null".equals(cursor.getString(0))) {
					shop_code = cursor.getString(0);
				}

				String size = "0";

				if (!"null".equals(cursor.getString(1))) {
					size = cursor.getString(1);
				}

				String color = "0";

				if (!"null".equals(cursor.getString(2))) {
					color = cursor.getString(2);
				}

				int shop_num = cursor.getInt(3);
				int stock_type_id = cursor.getInt(4);

				String def_pic = "0";

				if (!"null".equals(cursor.getString(5))) {
					def_pic = cursor.getString(5);
				}

				int user_id = cursor.getInt(6);

				String shop_name = "0";
				if (!"null".equals(cursor.getString(7))) {
					shop_name = cursor.getString(7);
				}

				String store_code = "0";

				if (!"null".equals(cursor.getString(8))) {
					store_code = cursor.getString(8);
				}

				String shop_price = "0";

				if (!"null".equals(cursor.getString(9))) {
					shop_price = cursor.getString(9);
				}

				String shop_se_price = "0";
				if (!cursor.getString(10).equals("null")) {
					shop_se_price = cursor.getString(10);
				}

				int supp_id = cursor.getInt(11);

				String kickback = "0";
				if (!"null".equals(cursor.getString(12))) {
					kickback = cursor.getString(12);
				}

				int original_price = cursor.getInt(13);
				int valid = cursor.getInt(14);
				int is_del = cursor.getInt(15);
				int is_meal_flag = cursor.getInt(16);

				String p_code = "0";
				if (!"null".equals(cursor.getString(17))) {
					p_code = cursor.getString(17);
				}

				String postage = "0";
				if (!"null".equals(cursor.getString(18))) {

					postage = cursor.getString(18);

				}

				String p_s_t_id = "0";

				if (!"null".equals(cursor.getString(19))) {
					p_s_t_id = cursor.getString(19);
				}

				String p_shop_code = "0";

				if (!"null".equals(cursor.getString(20))) {
					p_shop_code = cursor.getString(20);
				}

				String p_color = "0";

				if (!"null".equals(cursor.getString(21))) {
					p_color = cursor.getString(21);
				}

				int id = cursor.getInt(22);

				String p_name = "0";
				if (!"null".equals(cursor.getString(23))) {
					p_name = cursor.getString(23);
				}
				String supp_label = "";
				if (!"null".equals(cursor.getString(24))) {
					supp_label = cursor.getString(24);
				}
				ShopCart bean = new ShopCart();
				bean.setShop_code(shop_code);
				bean.setSize(size);
				bean.setColor(color);
				bean.setShop_num(shop_num);
				bean.setStock_type_id(stock_type_id);
				bean.setDef_pic(def_pic);
				bean.setUser_id(user_id);
				bean.setShop_name(shop_name);
				bean.setStore_code(store_code);
				bean.setShop_price(Double.parseDouble(shop_price));
				bean.setShop_se_price(Double.parseDouble(shop_se_price));
				bean.setSupp_id(supp_id);
				bean.setSupp_label(supp_label);
				if (is_meal_flag == 0) {
					bean.setKickback(Double.parseDouble(kickback));
				}
				bean.setOriginal_price(Double.parseDouble(original_price + ""));
				bean.setValid(valid);
				bean.setIs_del(is_del);
				bean.setIs_meal_flag(is_meal_flag);
				bean.setP_code(p_code);
				if (is_meal_flag == 1) {
					bean.setPostage(Double.parseDouble(postage));
					bean.setP_s_t_id(p_s_t_id);
					bean.setP_shop_code("" + p_shop_code);
					bean.setP_color("" + p_color);
				}
				// bean.number = number;
				// bean.type = type;
				if (is_meal_flag == 1) {

					String[] strStock_type_id = p_s_t_id.split(",");
					if (p_shop_code == null) {
						p_shop_code = "";
					}
					if (p_color == null) {
						p_color = "";
					}
					String[] strShopCode = p_shop_code.split(",");
					String[] strColor = p_color.split(",");
					List<StockBean> list2 = new ArrayList<StockBean>();
					for (int i = 0; i < strColor.length; i++) {
						StockBean bean2 = new StockBean();
						bean2.setColor(strColor[i]);
						if (i < strShopCode.length) {
							bean2.setShopCode(strShopCode[i]);
						}
						if (i < strStock_type_id.length) {
							bean2.setStock_type_id(Integer.parseInt("" + strStock_type_id[i]));
						}
						bean2.setSupp_id(supp_id);
						list2.add(bean2);
					}
					bean.setShop_list(list2);

				}
				bean.setId(id);
				bean.setP_name(p_name);
				list.add(bean);
			}

			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * 失效查询所有的
	 * 
	 * @return
	 */
	public List<ShopCart> findAll_invalid() {
		List<ShopCart> list = new ArrayList<ShopCart>();
		SQLiteDatabase db = mHelper.getReadableDatabase();
		// String shop_code = "shop_code"; // 商品编号 0
		// String size = "size"; // 商品尺码 1
		// String color = "color"; // 商品颜色 2
		// String shop_num = "shop_num"; // 商品的购买数量 integer 3
		// String stock_type_id = "stock_type_id";// 库存表id integer 4
		// String def_pic = "def_pic"; // 默认图片地址 5
		// String user_id = "user_id"; // 用户id integer 6
		// String shop_name = "shop_name"; // 商品名称 7
		// String store_code = "store_code"; // 店铺编号 8
		// String shop_price = "shop_price"; // 商品原价 Double 9
		// String shop_se_price = "shop_se_price"; // 商品折后价 Double 10
		// String supp_id = "supp_id";//integer 供货商id 11
		// String kickback = "kickback";// 返佣Double 12
		// String original_price = "original_price";// integer 13
		// String valid = "valid"; // 商品是否有效 0有效 1失效 integer 14
		// String is_del = "is_del";// 商品是否下架，1下架integer 15
		// String is_meal_flag="is_meal_flag";//表里判断是正价还是特卖integer 0代表正价，1代表特卖
		// 16
		// String p_code = "p_code";// 套餐编号 17
		// String postage = "postage";// 邮费 Double 18
		// String p_s_t_id = "p_s_t_id";// 特卖对应的每件商品库存 id，逗号隔开 19
		// // 自己添加
		// String p_shop_code = "p_shop_code"; // 套餐商品编号， 以逗号隔开 20
		// String p_color = "p_color"; // 套餐商品颜色， 以逗号隔开 21
		// String id = "id"; // 主键 ,购物车 integer id 22
		// String p_name = "p_name";// 套餐名字 23
		String sql = ShopCartDB.TableBlack.QUERY_ALL_SQL_INVALID;
		Cursor cursor = db.rawQuery(sql, null);
		LogYiFu.e("hzz", "2");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String shop_code = "0";

				if (!"null".equals(cursor.getString(0))) {
					shop_code = cursor.getString(0);
				}

				String size = "0";

				if (!"null".equals(cursor.getString(1))) {
					size = cursor.getString(1);
				}

				String color = "0";

				if (!"null".equals(cursor.getString(2))) {
					color = cursor.getString(2);
				}

				int shop_num = cursor.getInt(3);
				int stock_type_id = cursor.getInt(4);

				String def_pic = "0";

				if (!"null".equals(cursor.getString(5))) {
					def_pic = cursor.getString(5);
				}

				int user_id = cursor.getInt(6);

				String shop_name = "0";
				if (!"null".equals(cursor.getString(7))) {
					shop_name = cursor.getString(7);
				}

				String store_code = "0";

				if (!"null".equals(cursor.getString(8))) {
					store_code = cursor.getString(8);
				}

				String shop_price = "0";

				if (!"null".equals(cursor.getString(9))) {
					shop_price = cursor.getString(9);
				}

				String shop_se_price = "0";
				if (!cursor.getString(10).equals("null")) {
					shop_se_price = cursor.getString(10);
				}

				int supp_id = cursor.getInt(11);

				String kickback = "0";
				if (!"null".equals(cursor.getString(12))) {
					kickback = cursor.getString(12);
				}

				int original_price = cursor.getInt(13);
				int valid = cursor.getInt(14);
				int is_del = cursor.getInt(15);
				int is_meal_flag = cursor.getInt(16);

				String p_code = "0";
				if (!"null".equals(cursor.getString(17))) {
					p_code = cursor.getString(17);
				}

				String postage = "0";
				if (!"null".equals(cursor.getString(18))) {

					postage = cursor.getString(18);

				}

				String p_s_t_id = "0";

				if (!"null".equals(cursor.getString(19))) {
					p_s_t_id = cursor.getString(19);
				}

				String p_shop_code = "0";

				if (!"null".equals(cursor.getString(20))) {
					p_shop_code = cursor.getString(20);
				}

				String p_color = "0";

				if (!"null".equals(cursor.getString(21))) {
					p_color = cursor.getString(21);
				}

				int id = cursor.getInt(22);

				String p_name = "0";
				if (!"null".equals(cursor.getString(23))) {
					p_name = cursor.getString(23);
				}

				ShopCart bean = new ShopCart();
				bean.setShop_code(shop_code);
				bean.setSize(size);
				bean.setColor(color);
				bean.setShop_num(shop_num);
				bean.setStock_type_id(stock_type_id);
				bean.setDef_pic(def_pic);
				bean.setUser_id(user_id);
				bean.setShop_name(shop_name);
				bean.setStore_code(store_code);
				bean.setShop_price(Double.parseDouble(shop_price));
				bean.setShop_se_price(Double.parseDouble(shop_se_price));
				bean.setSupp_id(supp_id);
				if (is_meal_flag == 0) {
					bean.setKickback(Double.parseDouble(kickback));
				}
				bean.setOriginal_price(Double.parseDouble(original_price + ""));
				bean.setValid(valid);
				bean.setIs_del(is_del);
				bean.setIs_meal_flag(is_meal_flag);
				bean.setP_code(p_code);
				if (is_meal_flag == 1) {
					bean.setPostage(Double.parseDouble(postage));
					bean.setP_s_t_id(p_s_t_id);
					bean.setP_shop_code("" + p_shop_code);
					bean.setP_color("" + p_color);
				}
				// bean.number = number;
				// bean.type = type;
				if (is_meal_flag == 1) {

					String[] strStock_type_id = p_s_t_id.split(",");
					if (p_shop_code == null) {
						p_shop_code = "";
					}
					if (p_color == null) {
						p_color = "";
					}
					String[] strShopCode = p_shop_code.split(",");
					String[] strColor = p_color.split(",");
					List<StockBean> list2 = new ArrayList<StockBean>();
					for (int i = 0; i < strColor.length; i++) {
						StockBean bean2 = new StockBean();
						bean2.setColor(strColor[i]);
						if (i < strShopCode.length) {
							bean2.setShopCode(strShopCode[i]);
						}
						if (i < strStock_type_id.length) {
							bean2.setStock_type_id(Integer.parseInt("" + strStock_type_id[i]));
						}
						bean2.setSupp_id(supp_id);
						list2.add(bean2);
					}
					bean.setShop_list(list2);

				}
				bean.setId(id);
				bean.setP_name(p_name);
				list.add(bean);
			}

			cursor.close();
		}

		db.close();

		return list;
	}

	/**
	 * 根据号码查询类型
	 * 
	 * @param number
	 * @return 返回-1 说明不存在
	 */
	public int findType(String number) {
		SQLiteDatabase db = mHelper.getReadableDatabase();

		String sql = "select " + ShopCartDB.TableBlack.id + " from " + ShopCartDB.TableBlack.TABLE_NAME + " where "
				+ ShopCartDB.TableBlack.id + "=?";

		Cursor cursor = db.rawQuery(sql, new String[] { number });

		int type = -1;
		if (cursor != null) {
			if (cursor.moveToNext()) {
				type = cursor.getInt(0);// call 0 ,sms 1,all 2
			}

		}

		db.close();

		return type;
	}

	// 分页查询

	/**
	 * 
	 * @param pageSize
	 *            :每页的数量
	 * @param index
	 *            ：开始查询的下标
	 * @return
	 */
	// public List<ShopCartBean> findPart(int pageSize, int index) {
	// List<ShopCartBean> list = new ArrayList<ShopCartBean>();
	// // 数据库分页查询
	// // limit:查询的记录的条数,页面要显示的数量
	// // offset:开始查询的下标
	// // select * from black limit 10 offset 90;
	// SQLiteDatabase db = mHelper.getReadableDatabase();
	//
	// String sql = "select " + ShopCartDB.TableBlack.COLUMN_NUMBER + ","
	// + ShopCartDB.TableBlack.COLUMN_TYPE + " from "
	// + ShopCartDB.TableBlack.TABLE_NAME + " limit " + pageSize
	// + " offset " + index;
	// Cursor cursor = db.rawQuery(sql, null);
	//
	// if (cursor != null) {
	// while (cursor.moveToNext()) {
	// String number = cursor.getString(0);
	// int type = cursor.getInt(1);
	//
	// ShopCartBean bean = new ShopCartBean();
	//// bean.number = number;
	//// bean.type = type;
	//
	// list.add(bean);
	// }
	//
	// cursor.close();
	// }
	//
	// db.close();
	//
	// return list;
	// }
}

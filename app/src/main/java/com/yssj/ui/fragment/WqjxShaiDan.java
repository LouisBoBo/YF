package com.yssj.ui.fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.XListViewMealSingle;
import com.yssj.huanxin.widget.photoview.PhotoView;
import com.yssj.huanxin.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.yssj.model.ComModel2;
    import com.yssj.ui.activity.DisplayOrderDetialsActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.SharedPreferencesUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WqjxShaiDan extends Fragment {
	private static Context mContext;

	private XListViewMealSingle mList;
	private DateAdapter mAdapter;
	private List<HashMap<String, Object>> pList;
	private int index = 1;

	private boolean flag = true;

	public interface onWqjxShanDanRefreshListener {
		void onWqjxShanDanRefresh();
	}

	public void setOnWqjxShanDanRefreshListener(Fragment fragment) {
		this.fragment = fragment;

	}

	// public interface onZeroShopRefreshListener{
	// void onZeroShopRefresh();
	// }
	// public void setOnZeroRefreshListener(Fragment f){
	// this.zeroShopRefresh = (onZeroShopRefreshListener) f;
	// }

	public static WqjxShaiDan newInstances(int position, Context context) {
		WqjxShaiDan instance = new WqjxShaiDan();
		Bundle args = new Bundle();
		args.putString("position", position + "");
		mContext = context;
		instance.setArguments(args);

		return instance;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.item_fragment_view_snatch, container, false);

		// choice = SharedPreferencesUtil.getStringData(mContext, "choice",
		// "3");
		// my_choice = SharedPreferencesUtil.getStringData(mContext,
		// "my_choice", "3");
		// System.out.println("choice="+choice+"*****my_choice="+my_choice);

		account_nodata = (LinearLayout) v.findViewById(R.id.account_nodata);
		account_nodata.setVisibility(View.GONE);

		TextView tv_qin = (TextView) v.findViewById(R.id.tv_qin);
		tv_qin.setText("???????????????????????????????????????????????????~");
		tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
		tv_no_join.setText("???????????????");
		tv_no_join.setVisibility(View.GONE);
		Button btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);

		img_pic = (ImageView) v.findViewById(R.id.img_pic);
		img_pic.setAdjustViewBounds(true);
		// SetImageLoader.initImageLoader2(getActivity(), img_pic,
		// "shop_comment/share_order/default.png", "");
		PicassoUtils.initImage(getActivity(), "shop_comment/share_order/default.png", img_pic);

		mList = (XListViewMealSingle) v.findViewById(R.id.dataList);
		mList.setPullLoadEnable(true);

		mList.setXListViewListener(new XListViewMealSingle.IXListViewListener() {

			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {
				index++;
				// initData(String.valueOf(index));
			}
		});

		pList = new ArrayList<HashMap<String, Object>>();

		// mAdapter = new DateAdapter(getActivity());
		// mList.setAdapter(mAdapter);

		return v;

	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferencesUtil.saveStringData(mContext, "where", "4");
		initData(String.valueOf(index));
	}

	protected void initData(final String index) {

		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(getActivity(), 0) {
			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
					throws Exception {
				return ComModel2.WangQIShaiDan(context, index);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {

				// System.out.println("//////??????=" + result);

				if (e != null) {
					mList.stopLoadMore();
					return;
				}
				if (result != null) {
					if (WqjxShaiDan.this.index == 1) {
						pList.clear();
					}

					if (result.size() == 0 && !index.equals("1")) {

					}
					pList.addAll(result);
				} else {
					if (WqjxShaiDan.this.index == 1) {
						pList.clear();
					} else {

					}
				}
				// mAdapter.notifyDataSetChanged();
				mList.stopLoadMore();

			};
		}.execute();

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		choice = SharedPreferencesUtil.getStringData(mContext, "choice", "3");
		my_choice = SharedPreferencesUtil.getStringData(mContext, "my_choice", "3");
		// System.out.println("choice=" + choice + "*****my_choice=" +
		// my_choice);

		index = 1;

		initData(String.valueOf(index));

		initview();
	}

	public void refresh() {
		index = 1;
		initData("1");
	}

	private void initview() {

	}

	private class DateAdapter extends BaseAdapter {

		private Context context;
		// private String shop_code;
		private Handler mHandler;

		public DateAdapter(Context context) {
			super();
			this.context = context;

		}

		@Override
		public int getCount() {
			int count = 0;
			count = pList.size();

			if (count == 0) {
				account_nodata.setVisibility(View.VISIBLE);
			} else {
				account_nodata.setVisibility(View.GONE);
			}

			return count;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;

			if (convertView == null) {
				// convertView=LayoutInflater.from(context).inflate(R.layout.wqjx_right_demo,
				// null);
				convertView = LayoutInflater.from(context).inflate(R.layout.wqjx_right, null);
				holder = new ViewHolder();

				holder.img_head = (RoundImageButton) convertView.findViewById(R.id.img_head); // ??????
				holder.name_head = (TextView) convertView.findViewById(R.id.name_head); // ??????
				holder.img_huiyuan = (ImageView) convertView.findViewById(R.id.img_huiyuan); // ????????????
				holder.tv_huiyuan = (TextView) convertView.findViewById(R.id.tv_huiyuan); // ????????????
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); // ???????????????????????????
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content); // ????????????
				holder.show_all = (TextView) convertView.findViewById(R.id.show_all); // ????????????
				holder.sing_martrix = (ImageView) convertView.findViewById(R.id.sing_martrix); // ??????????????????
				holder.sing_changfangxing = (ImageView) convertView.findViewById(R.id.sing_changfangxing); // ??????????????????
				holder.sing_changfangxing_vertical = (ImageView) convertView
						.findViewById(R.id.sing_changfangxing_vertical); // ????????????????????????

				holder.img_more_ll = (LinearLayout) convertView.findViewById(R.id.img_more_ll); // ????????????LinearLayout?????????
				holder.more_img_one = (ImageView) convertView.findViewById(R.id.more_img_one); // ???????????????????????????
				holder.more_img_two = (ImageView) convertView.findViewById(R.id.more_img_two); // ??????????????????2??????
				holder.more_img_three = (ImageView) convertView.findViewById(R.id.more_img_three); // ??????????????????2??????

				holder.ll_dianzan = (LinearLayout) convertView.findViewById(R.id.ll_dianzan); // ?????????ll_dianzan??????
				holder.tv_dianzan = (ImageView) convertView.findViewById(R.id.tv_dianzan); // ????????????
				holder.tv_num_dianzan = (TextView) convertView.findViewById(R.id.tv_num_dianzan); // ????????????

				holder.ll_pingjia = (LinearLayout) convertView.findViewById(R.id.ll_pingjia); // ?????????ll_dianzan??????
				holder.img_pingjia = (ImageView) convertView.findViewById(R.id.img_pingjia); // ????????????
				holder.tv_num_pingjia = (TextView) convertView.findViewById(R.id.tv_num_pingjia); // ????????????

				/*
				 * ??????????????? holder.ll_fenxiang = (LinearLayout)
				 * convertView.findViewById(R.id.ll_fenxiang); //?????????ll_dianzan??????
				 * holder.img_fenxiang = (ImageView)
				 * convertView.findViewById(R.id.img_fenxiang); //????????????
				 * holder.tv_num_fenxiang = (TextView)
				 * convertView.findViewById(R.id.tv_num_fenxiang); //????????????
				 */
				holder.view_cut_one = convertView.findViewById(R.id.view_cut_one); // ??????????????????
																					// ?????????
				// ??????????????? holder.view_cut_two =
				// convertView.findViewById(R.id.view_cut_two); //?????????????????? ???2???

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			HashMap<String, Object> mapObj = pList.get(position);

			String content = mapObj.get("content").toString();
			holder.tv_content.setText(content); // ????????????

			final int lineCount = holder.tv_content.getLineCount();

			if (lineCount < 3) {
				holder.show_all.setVisibility(View.GONE);
			} else {
				holder.show_all.setVisibility(View.VISIBLE);
			}

			// ??????????????????????????????
			holder.show_all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (lineCount >= 3) {
						if (flag == true) {
							flag = false;
							holder.tv_content.setEllipsize(null);
							holder.tv_content.setSingleLine(flag);
							holder.show_all.setText("??????");
						} else {
							holder.tv_content.setEllipsize(TruncateAt.END);
							holder.tv_content.setMaxLines(3);
							holder.show_all.setText("????????????");
							flag = true;
						}
					}

				}
			});

			final String user_name = mapObj.get("user_name").toString();
			holder.name_head.setText(user_name); // ????????????

			// Date d2 = new Date(System.currentTimeMillis());
			// System.out.println("sjsjsjsjsjs+d2="+d2);

			/*
			 * String add_date = mapObj.get("add_date").toString();
			 *
			 * long parseLong = Long.parseLong(add_date);
			 * System.out.println("*****+parseLong="+parseLong);
			 *
			 * long time = System.currentTimeMillis() - (parseLong*1000);
			 * System.out.println("**timetimetimetime="+time);
			 *
			 * long minute = (long) Math.ceil(time/60/1000.0f);// ?????????
			 * System.out.println("***minuteminute?????????="+minute);
			 *
			 * long hour = (long) Math.ceil(time/60/60/1000.0f);// ??????
			 * System.out.println("****hour="+hour);
			 */

			// long time = System.currentTimeMillis() /*- (parseLong*1000)*/;

			// String click = mapObj.get("click").toString();
			// System.out.println("clickclickclick="+click);

			// holder.tv_time.setText(DateFormatUtils.format(Long.parseLong(time+""),"yyyy.MM.dd
			// hh:mm:ss")); //????????????

			String add_date = mapObj.get("add_date").toString(); // ????????????

			long time = System.currentTimeMillis();

			String add_date_need = DateFormatUtils.format(Long.parseLong(add_date), "yyyy.MM.dd HH:mm:ss");
			String time_sys = DateFormatUtils.format(Long.parseLong(time + ""), "yyyy.MM.dd HH:mm:ss");

			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			try {
				Date d1 = df.parse(time_sys + "");
				Date d2 = df.parse(add_date_need);
				long diff = d1.getTime() - d2.getTime();// ????????????????????????????????????
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
				long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

				// System.out.println("" + days + "???" + hours + "??????" + minutes +
				// "???");

				if (days > 0 || hours > 0) { // ?????????????????????????????????0?????????????????????
					holder.tv_time.setText(DateFormatUtils.format(Long.parseLong(add_date), "yyyy.MM.dd HH:mm:ss"));
				} else { // ???????????????????????????????????????????????????
					holder.tv_time.setText(minutes + "?????????");
				}

			} catch (Exception e) {

			}

			String comment_size = mapObj.get("comment_size").toString();
			holder.tv_num_pingjia.setText(comment_size); // ????????????

			final String click_size = mapObj.get("click_size").toString();
			holder.tv_num_dianzan.setText(click_size); // ????????????

			final String shop_code = mapObj.get("shop_code").toString();

			String shop_codes = mapObj.get("shop_codes").toString();

			if (shop_codes.equals(shop_code)) {
				// holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_red));
				holder.tv_dianzan.setImageResource(R.drawable.icon_dianzan_press);
			} else {
				// holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_gray));
				holder.tv_dianzan.setImageResource(R.drawable.icon_dianzan_gray);
			}

			final String user_url = mapObj.get("user_url").toString();

//			SetImageLoader.initImageLoader(context, holder.img_head, (String) mapObj.get("user_url"), "");
			PicassoUtils.initImage(context, (String) mapObj.get("user_url"), holder.img_head);

			final String object = mapObj.get("pic").toString();
			// System.out.println("????????????????????????="+object);

			final String[] split = object.split("[,]");
			// System.out.println("????????? split=" + split);

			// if (split.length == 1 /*&& !object.contains(",")*/) {
			// System.out.println("????????????????????????="+object);
			//
			//
			// new Thread(new Runnable() {
			// private int score;
			//
			// @Override
			// public void run() {
			//
			//
			// URL fileUrl = null;
			// Bitmap bitmap = null;
			// try {
			// String url = YUrl.imgurl+"shareOrder" + split[0];
			// fileUrl = new URL(url);
			// System.out.println("???????????????????????????url="+split[0]);
			// } catch (MalformedURLException e) {
			// e.printStackTrace();
			// }
			// try {
			// HttpURLConnection conn = (HttpURLConnection)
			// fileUrl.openConnection();
			// conn.setDoInput(true);
			// conn.connect();
			// InputStream is = conn.getInputStream();
			// bitmap = BitmapFactory.decodeStream(is);
			// System.out.println("??????????????????bitmap=" + bitmap);
			// int width = bitmap.getWidth();
			// int height = bitmap.getHeight();
			//
			// score = width/height;
			//// mHandler.sendEmptyMessage(-1);
			// android.os.Message message = new android.os.Message();
			// if (score == 0) {
			// message.obj = 0+"";
			// }
			// if (score == 1) {
			// message.obj = 1+"";
			// }
			// if (score > 1) {
			// message.obj = 2+"";
			// }
			// if (object.equals("")) {
			// message.obj = -1+"";
			// }
			//
			// mHandler.sendMessage(message);
			// is.close();
			////
			//// if (score == 0) {
			//// holder.img_more_ll.setVisibility(View.GONE);
			//// holder.sing_martrix.setVisibility(View.GONE);
			//// holder.sing_changfangxing.setVisibility(View.GONE);
			//// holder.sing_changfangxing_vertical.setVisibility(View.VISIBLE);
			//// holder.more_img_one.setVisibility(View.GONE);
			//// holder.more_img_two.setVisibility(View.GONE);
			//// holder.more_img_three.setVisibility(View.GONE);
			////
			//// SetImageLoader.initImageLoader(context,
			// holder.sing_changfangxing_vertical, "shareOrder" + split[0], "");
			//// }
			//// if (score == 1) {
			//// holder.img_more_ll.setVisibility(View.GONE);
			//// holder.sing_martrix.setVisibility(View.VISIBLE);
			//// holder.sing_changfangxing.setVisibility(View.GONE);
			//// holder.sing_changfangxing_vertical.setVisibility(View.GONE);
			//// holder.more_img_one.setVisibility(View.GONE);
			//// holder.more_img_two.setVisibility(View.GONE);
			//// holder.more_img_three.setVisibility(View.GONE);
			////
			//// SetImageLoader.initImageLoader(context, holder.sing_martrix,
			// "shareOrder" + split[0], "");
			//// }
			//// if (score > 1) {
			//// holder.img_more_ll.setVisibility(View.GONE);
			//// holder.sing_martrix.setVisibility(View.GONE);
			//// holder.sing_changfangxing.setVisibility(View.VISIBLE);
			//// holder.sing_changfangxing_vertical.setVisibility(View.GONE);
			//// holder.more_img_one.setVisibility(View.GONE);
			//// holder.more_img_two.setVisibility(View.GONE);
			//// holder.more_img_three.setVisibility(View.GONE);
			////
			//// SetImageLoader.initImageLoader(context,
			// holder.sing_changfangxing, "shareOrder" + split[0], "");
			//// }
			////
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			//
			// }
			// }).start();
			//
			// }
			//
			//
			//
			// mHandler = new Handler() {
			// public void handleMessage(android.os.Message msg) {
			// switch (msg.what) {
			// case 0:
			// holder.img_more_ll.setVisibility(View.GONE);
			// holder.sing_martrix.setVisibility(View.GONE);
			// holder.sing_changfangxing.setVisibility(View.GONE);
			// holder.sing_changfangxing_vertical.setVisibility(View.VISIBLE);
			// holder.more_img_one.setVisibility(View.GONE);
			// holder.more_img_two.setVisibility(View.GONE);
			// holder.more_img_three.setVisibility(View.GONE);
			//
			// SetImageLoader.initImageLoader(context,
			// holder.sing_changfangxing_vertical, "shareOrder" + split[0], "");
			// break;
			//
			// case 1:
			// holder.img_more_ll.setVisibility(View.GONE);
			// holder.sing_martrix.setVisibility(View.VISIBLE);
			// holder.sing_changfangxing.setVisibility(View.GONE);
			// holder.sing_changfangxing_vertical.setVisibility(View.GONE);
			// holder.more_img_one.setVisibility(View.GONE);
			// holder.more_img_two.setVisibility(View.GONE);
			// holder.more_img_three.setVisibility(View.GONE);
			//
			// SetImageLoader.initImageLoader(context, holder.sing_martrix,
			// "shareOrder" + split[0], "");
			// break;
			//
			// case 2:
			// holder.img_more_ll.setVisibility(View.GONE);
			// holder.sing_martrix.setVisibility(View.GONE);
			// holder.sing_changfangxing.setVisibility(View.VISIBLE);
			// holder.sing_changfangxing_vertical.setVisibility(View.GONE);
			// holder.more_img_one.setVisibility(View.GONE);
			// holder.more_img_two.setVisibility(View.GONE);
			// holder.more_img_three.setVisibility(View.GONE);
			//
			// SetImageLoader.initImageLoader(context,
			// holder.sing_changfangxing, "shareOrder" + split[0], "");
			// break;
			// case -1:
			// holder.img_more_ll.setVisibility(View.GONE);
			// holder.sing_martrix.setVisibility(View.GONE);
			// holder.sing_changfangxing.setVisibility(View.GONE);
			// holder.sing_changfangxing_vertical.setVisibility(View.GONE);
			// holder.more_img_one.setVisibility(View.GONE);
			// holder.more_img_two.setVisibility(View.GONE);
			// holder.more_img_three.setVisibility(View.GONE);
			//
			//// SetImageLoader.initImageLoader(context,
			// holder.sing_changfangxing, "shareOrder" + split[0], "");
			// break;
			// default:
			// break;
			// }
			// };
			// };

			if (split.length == 1) {
				holder.img_more_ll.setVisibility(View.GONE);
				holder.sing_martrix.setVisibility(View.VISIBLE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);
				holder.more_img_one.setVisibility(View.GONE);
				holder.more_img_two.setVisibility(View.GONE);
				holder.more_img_three.setVisibility(View.GONE);

//				SetImageLoader.initImageLoader(context, holder.sing_martrix, "shareOrder" + split[0], "");
				PicassoUtils.initImage(context, "shareOrder" + split[0], holder.sing_martrix);
			}

			if (split.length == 2) {
				holder.img_more_ll.setVisibility(View.VISIBLE);
				holder.sing_martrix.setVisibility(View.GONE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);
				holder.more_img_one.setVisibility(View.VISIBLE);
				holder.more_img_two.setVisibility(View.VISIBLE);
				holder.more_img_three.setVisibility(View.GONE);

//				SetImageLoader.initImageLoader(context, holder.more_img_one, "shareOrder" + split[0], "");
				PicassoUtils.initImage(context, "shareOrder" + split[0], holder.more_img_one);

//				SetImageLoader.initImageLoader(context, holder.more_img_two, "shareOrder" + split[1], "");

				PicassoUtils.initImage(context, "shareOrder" + split[1], holder.more_img_two);

				holder.more_img_three.setVisibility(View.GONE);
			}

			if (split.length == 3) {
				holder.img_more_ll.setVisibility(View.VISIBLE);
				holder.sing_martrix.setVisibility(View.GONE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);

				holder.more_img_one.setVisibility(View.VISIBLE);
				holder.more_img_two.setVisibility(View.VISIBLE);
				holder.more_img_three.setVisibility(View.VISIBLE);

//				SetImageLoader.initImageLoader(context, holder.more_img_one, "shareOrder" + split[0], "");

				PicassoUtils.initImage(context, "shareOrder" + split[0], holder.more_img_one);

//				SetImageLoader.initImageLoader(context, holder.more_img_two, "shareOrder" + split[1], "");
				PicassoUtils.initImage(context, "shareOrder" + split[1], holder.more_img_two);

//				SetImageLoader.initImageLoader(context, holder.more_img_three, "shareOrder" + split[2], "");
				PicassoUtils.initImage(context, "shareOrder" + split[2], holder.more_img_three);
			}

			// Boolean flag_dianzan =
			// SharedPreferencesUtil.getBooleanData(context, "flag_dianzan",
			// false);

			// if (flag_dianzan==true) {
			// holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_red));
			// }else {
			// holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_gray));
			// }

			// ?????????????????????????????????
			holder.ll_dianzan.setOnClickListener(new OnClickListener() {

				private int num_click_size;

				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {

					new SAsyncTask<String, Void, HashMap<String, Object>>(getActivity(), 0) {

						protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
								throws Exception {
							return ComModel2.AddClick(context, shop_code);
						}

						@Override
						protected boolean isHandleException() {
							return true;
						}

						protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
								Exception e) {

							if (result != null) {
								String status = result.get("status").toString();

								if (status.equals("1")) {
									/*
									 * if (flag_dianzan==true) {
									 * flag_dianzan=false;
									 */
									// SharedPreferencesUtil.saveBooleanData(context,
									// "flag_dianzan", true);
									// holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_red));
									holder.tv_dianzan.setImageResource(R.drawable.icon_dianzan_press);
									num_click_size = Integer.valueOf(click_size).intValue();
									num_click_size = num_click_size + 1;
									holder.tv_num_dianzan.setText(num_click_size + "");
								}

							}

						}

					}.execute();

				}
			});

			final String user_id = mapObj.get("user_id").toString();
			// System.out.println("***user_id=" + user_id);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(getActivity(), DisplayOrderDetialsActivity.class);
					intent.putExtra("shop_code", shop_code);

					startActivity(intent);

				}
			});

			//////// ????????????
			holder.sing_martrix.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[0], "");

					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[0], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}

			});

			holder.sing_changfangxing.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[0], "");
					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[0], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			holder.sing_changfangxing_vertical.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[0], "");
					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[0], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			holder.more_img_one.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[0], "");
					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[0], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			holder.more_img_two.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[1], "");
					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[1], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			holder.more_img_three.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);

					dialog.setContentView(R.layout.changbig);
					PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
					android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();

					setParams(lay);
//					SetImageLoader.initImageLoader(getActivity(), img_bigger, "shareOrder" + split[2], "");
					PicassoUtils.initImage(getActivity(),  "shareOrder" + split[2], img_bigger);


					img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

						@Override
						public void onPhotoTap(View view, float x, float y) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();

				}
			});

			///////

			return convertView;
		}

		protected void setParams(android.view.WindowManager.LayoutParams lay) {

			DisplayMetrics dm = new DisplayMetrics();

			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

			Rect rect = new Rect();

			View view = getActivity().getWindow().getDecorView();

			view.getWindowVisibleDisplayFrame(rect);

			lay.height = dm.heightPixels/* - rect.top */;

			lay.width = dm.widthPixels;

		}

	}

	private class ViewHolder {
		private TextView name_head, tv_huiyuan, tv_time, tv_content, show_all, tv_num_dianzan, tv_num_pingjia,
				tv_num_fenxiang;
		private ImageView /* img_head, */ img_huiyuan, sing_martrix, sing_changfangxing, sing_changfangxing_vertical,
				more_img_one, more_img_two, more_img_three, tv_dianzan, img_pingjia, img_fenxiang;
		private LinearLayout img_more_ll, ll_dianzan, ll_pingjia, ll_fenxiang;
		private View view_cut_one, view_cut_two;
		private RoundImageButton img_head;
	}

	private Fragment fragment;

	private String choice;

	private String my_choice;

	private LinearLayout account_nodata;

	private TextView tv_no_join;

	private ImageView img_pic;

}

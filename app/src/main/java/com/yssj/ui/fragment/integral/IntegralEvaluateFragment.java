//package com.yssj.ui.fragment.integral;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.NestedListView;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.entity.IntegralShop;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopComment;
//import com.yssj.model.ComModel;
//import com.yssj.ui.activity.GuideActivity;
//import com.yssj.ui.activity.shopdetails.BigImageDialog;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.YCache;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
///***
// * 评价
// * 
// * @author Administrator
// * 
// */
//public class IntegralEvaluateFragment extends Fragment implements
//		OnClickListener {
//	private View view;
////	private LinearLayout lay_evaluate0, lay_evaluate1, lay_evaluate2,
////			lay_evaluate3;
////	private TextView tv_all, tv_all_number, tv_like, tv_like_number, tv_middle,
////			tv_middle_number, tv_nolike, tv_nolike_number, tv_addmore;
//	
//	private TextView tv_addmore;
//	private NestedListView listView;
//	private ListViewAdpter adpter;
//	private int rows = 10, page = 1;
//	private List<ShopComment> listShopComments;
//	private LinearLayout lay_addmore;
//	private ProgressBar pbar;
//	private boolean flag = true;
//	private IntegralShop shop;
//
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//	private HashMap<String, Object> mapObject;
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//	}
//
//	public IntegralEvaluateFragment(HashMap<String, Object> mapReturn) {
//		this.mapObject = mapReturn;
//		shop = (IntegralShop) mapReturn.get("shop");
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		listShopComments = new ArrayList<ShopComment>();
//
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_evaluate, container, false);
////		tv_all = (TextView) view.findViewById(R.id.tv_all);
////		tv_all_number = (TextView) view.findViewById(R.id.tv_all_number);
////		tv_like = (TextView) view.findViewById(R.id.tv_like);
////		tv_like_number = (TextView) view.findViewById(R.id.tv_like_number);
////		tv_middle = (TextView) view.findViewById(R.id.tv_middle);
////		tv_middle_number = (TextView) view.findViewById(R.id.tv_middle_number);
////		tv_nolike = (TextView) view.findViewById(R.id.tv_nolike);
////		tv_nolike_number = (TextView) view.findViewById(R.id.tv_nolike_number);
////		lay_evaluate0 = (LinearLayout) view.findViewById(R.id.lay_evaluate0);
////		lay_evaluate0.setOnClickListener(this);
////		lay_evaluate1 = (LinearLayout) view.findViewById(R.id.lay_evaluate1);
////		lay_evaluate1.setOnClickListener(this);
////		lay_evaluate2 = (LinearLayout) view.findViewById(R.id.lay_evaluate2);
////		lay_evaluate2.setOnClickListener(this);
////		lay_evaluate3 = (LinearLayout) view.findViewById(R.id.lay_evaluate3);
////		lay_evaluate3.setOnClickListener(this);
////		chooseEvaluate(0);
//
//		int resource = R.layout.listview_evaluate;
////		listView = (NestedListView) view.findViewById(R.id.nListview);
//		adpter = new ListViewAdpter(getActivity(), resource, listShopComments);
//		listView.setAdapter(adpter);
//
//
//		return view;
//	}
//
//	public void setRefreshListView(IntegralShop shop) {
//		this.shop = shop;
//		if (flag) {
//			querySelCommentByShop();
//			flag = false;
//		}
////		setEvaluateText();
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
////		case R.id.lay_evaluate0:
////			chooseEvaluate(0);
////			break;
////		case R.id.lay_evaluate1:
////			chooseEvaluate(1);
////			break;
////		case R.id.lay_evaluate2:
////			chooseEvaluate(2);
////			break;
////		case R.id.lay_evaluate3:
////			chooseEvaluate(3);
////			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	/*private void chooseEvaluate(int type) {
//		int s_color = getResources().getColor(R.color.white);
//		int n_color = getResources().getColor(R.color.s_color);
//		switch (type) {
//		case 0:
//			lay_evaluate0
//					.setBackgroundResource(R.drawable.shape_evaluate_selleft);
//			lay_evaluate1
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate2
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate3
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoright);
//			tv_all.setTextColor(s_color);
//			tv_all_number.setTextColor(s_color);
//			tv_like.setTextColor(n_color);
//			tv_like_number.setTextColor(n_color);
//			tv_middle.setTextColor(n_color);
//			tv_middle_number.setTextColor(n_color);
//			tv_nolike.setTextColor(n_color);
//			tv_nolike_number.setTextColor(n_color);
//			break;
//		case 1:
//			lay_evaluate0
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoleft);
//			lay_evaluate1.setBackgroundResource(R.drawable.shape_evaluate_sel);
//			lay_evaluate2
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate3
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoright);
//			tv_all.setTextColor(n_color);
//			tv_all_number.setTextColor(n_color);
//			tv_like.setTextColor(s_color);
//			tv_like_number.setTextColor(s_color);
//			tv_middle.setTextColor(n_color);
//			tv_middle_number.setTextColor(n_color);
//			tv_nolike.setTextColor(n_color);
//			tv_nolike_number.setTextColor(n_color);
//			break;
//		case 2:
//			lay_evaluate0
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoleft);
//			lay_evaluate1
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate2.setBackgroundResource(R.drawable.shape_evaluate_sel);
//			lay_evaluate3
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoright);
//			tv_all.setTextColor(n_color);
//			tv_all_number.setTextColor(n_color);
//			tv_like.setTextColor(n_color);
//			tv_like_number.setTextColor(n_color);
//			tv_middle.setTextColor(s_color);
//			tv_middle_number.setTextColor(s_color);
//			tv_nolike.setTextColor(n_color);
//			tv_nolike_number.setTextColor(n_color);
//			break;
//		case 3:
//			lay_evaluate0
//					.setBackgroundResource(R.drawable.shape_evaluate_selnoleft);
//			lay_evaluate1
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate2
//					.setBackgroundResource(R.drawable.shape_evaluate_selno);
//			lay_evaluate3
//					.setBackgroundResource(R.drawable.shape_evaluate_selright);
//			tv_all.setTextColor(n_color);
//			tv_all_number.setTextColor(n_color);
//			tv_like.setTextColor(n_color);
//			tv_like_number.setTextColor(n_color);
//			tv_middle.setTextColor(n_color);
//			tv_middle_number.setTextColor(n_color);
//			tv_nolike.setTextColor(s_color);
//			tv_nolike_number.setTextColor(s_color);
//			break;
//
//		default:
//			break;
//		}
//
//	}*/
//
//	/*private void setEvaluateText() {
//		if (mapObject != null) {
//			String all_number = "(" + mapObject.get("eva_count") + ")";
//			if (!TextUtils.isEmpty(all_number)) {
//				tv_all_number.setText(all_number);
//			}
//			String like_number = "(" + mapObject.get("praise_count") + ")";
//			if (!TextUtils.isEmpty(like_number)) {
//				tv_like_number.setText(like_number);
//			}
//			String med_count = "(" + mapObject.get("med_count") + ")";
//			if (!TextUtils.isEmpty(med_count)) {
//				tv_middle_number.setText(med_count);
//			}
//			String bad_count = "(" + mapObject.get("bad_count") + ")";
//			if (!TextUtils.isEmpty(bad_count)) {
//				tv_nolike_number.setText(bad_count);
//			}
//		}
//	}*/
//
//	private void querySelCommentByShop() {
//		if (shop == null) {
//			return;
//		}
//		new SAsyncTask<String, Void, List<ShopComment>>(getActivity(), null,
//				R.string.wait) {
//			@Override
//			protected List<ShopComment> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				List<ShopComment> list = ComModel.queryShopEvaluate(
//						getActivity(),  "" + page, "" + rows,
//						"" + shop.getShop_code());
//				return list;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					List<ShopComment> list, Exception e) {
//
//				if (e != null) {// 查询异常
//					Toast.makeText(getActivity(), "连接超时，请重试", Toast.LENGTH_LONG)
//							.show();
//					pbar.setVisibility(View.GONE);
//				} else {// 查询商品详情成功，刷新界面
//					if (list != null && list.size() > 0) {
//						listShopComments.addAll(list);
//						adpter.notifyDataSetChanged();
//						pbar.setVisibility(View.GONE);
//						if (list.size() < 10) {
//							tv_addmore.setText("无更多评论");
//							lay_addmore.setEnabled(false);
//						}
//					} else if (list != null && list.size() == 0) {
//						pbar.setVisibility(View.GONE);
//						tv_addmore.setText("无更多评论");
//						lay_addmore.setEnabled(false);
//					}
//				}
//
//			};
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//		}.execute();
//
//	}
//
//	class ListViewAdpter extends ArrayAdapter<ShopComment> {
//		private Context context;
//		private int resource;
//		private List<ShopComment> objects;
//
//		public ListViewAdpter(Context context, int resource,
//				List<ShopComment> objects) {
//			super(context, resource, objects);
//			this.resource = resource;
//			this.context = context;
//			this.objects = objects;
//			options = new DisplayImageOptions.Builder()
//					.showImageOnLoading(R.drawable.ic_stub)
//					.showImageForEmptyUri(R.drawable.ic_empty)
//					.cacheInMemory(true)
//					.cacheOnDisk(true).considerExifParams(true)
//					.displayer(new FadeInBitmapDisplayer(35)).build();
//
//		}
//
//		@Override
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			Holder holder = null;
//			if (convertView == null) {
//				holder = new Holder();
//				convertView = LayoutInflater.from(context).inflate(resource,
//						null);
//				holder.img_user_header = (RoundImageButton) convertView
//						.findViewById(R.id.img_user_header);
//				holder.tv_user = (TextView) convertView
//						.findViewById(R.id.tv_user);
//				holder.tv_evaluate = (TextView) convertView
//						.findViewById(R.id.tv_evaluate);
//				holder.tv_date = (TextView) convertView
//						.findViewById(R.id.tv_date);
////				holder.tv_evxq = (TextView) convertView
////						.findViewById(R.id.tv_evxq);
//				holder.tv_color = (TextView) convertView
//						.findViewById(R.id.tv_color);
//				holder.tv_size = (TextView) convertView
//						.findViewById(R.id.tv_size);
////				holder.img0 = (ImageView) convertView.findViewById(R.id.img0);
////				holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
////				holder.img2 = (ImageView) convertView.findViewById(R.id.img2);
////				holder.img3 = (ImageView) convertView.findViewById(R.id.img3);
//
//				convertView.setTag(holder);
//			} else {
//				holder = (Holder) convertView.getTag();
//			}
//
//			ShopComment shopComment = objects.get(position);
//			String user_url = shopComment.getUser_url();
//			YJApplication.getLoader().displayImage(user_url, holder.img_user_header, options,
//					animateFirstListener);
//			String user_name = shopComment.getUser_name();
//			if (!TextUtils.isEmpty(user_name)) {
//				holder.tv_user.setText(user_name);
//			}
//			int comment_type = shopComment.getComment_type();
//			if (comment_type == 1) {
//				holder.tv_evaluate.setText("好评");
//			} else if (comment_type == 2) {
//				holder.tv_evaluate.setText("中评");
//			} else if (comment_type == 3) {
//				holder.tv_evaluate.setText("差评");
//			}
//			long add_date = shopComment.getAdd_date();
//			String date = StringUtils.timeToDate(add_date);
//			if (!TextUtils.isEmpty(date)) {
//				holder.tv_date.setText(date);
//			}
//
//			String content = shopComment.getContent();
//			if (!TextUtils.isEmpty(content)) {
//				holder.tv_evxq.setText(content);
//			}
//
//			String shop_color = shopComment.getShop_color();
//			if (!TextUtils.isEmpty(shop_color)) {
//				holder.tv_color.setText("颜色：" + shop_color);
//			}
//			String shop_size = shopComment.getShop_size();
//			if (!TextUtils.isEmpty(shop_size)) {
//				holder.tv_size.setText("尺码：" + shop_size);
//			}
//			final String pic = shopComment.getPic();
//			if (!TextUtils.isEmpty(pic)) {
//				final String[] picArray = pic.split(",");
//				for (int i = 0; i < picArray.length; i++) {
//					String url = picArray[i];
//					if (i == 0) {
//						YJApplication.getLoader().getInstance().displayImage(url,
//								holder.img0, options, animateFirstListener);
//						holder.img0
//								.setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//
//										showBigDialog(picArray, 0);
//									}
//								});
//
//					} else if (i == 1) {
//						YJApplication.getLoader().displayImage(url,
//								holder.img1, options, animateFirstListener);
//						holder.img1
//								.setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//
//										showBigDialog(picArray, 1);
//									}
//								});
//					} else if (i == 2) {
//						YJApplication.getLoader().displayImage(url,
//								holder.img2, options, animateFirstListener);
//						holder.img2
//								.setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										showBigDialog(picArray, 2);
//
//									}
//								});
//					} else if (i == 3) {
//						YJApplication.getLoader().displayImage(url,
//								holder.img3, options, animateFirstListener);
//
//						holder.img3
//								.setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										showBigDialog(picArray, 3);
//
//									}
//								});
//					}
//
//				}
//			}
//
//			return convertView;
//		}
//
//	}
//
//	private void showBigDialog(String[] url, int item) {
//		BigImageDialog dlg = new BigImageDialog(getActivity(),
//				R.style.DialogStyle, url, item);
//		dlg.show();
//
//	}
//
//	class Holder {
//		RoundImageButton img_user_header;
//		TextView tv_user;
//		TextView tv_evaluate;
//		TextView tv_date;
//		TextView tv_evxq;
//		TextView tv_color;
//		TextView tv_size;
//		ImageView img0, img1, img2, img3;
//	}
//
//	private static class AnimateFirstDisplayListener extends
//			SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections
//				.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view,
//				Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
//}

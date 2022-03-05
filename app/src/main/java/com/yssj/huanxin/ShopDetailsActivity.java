//package com.yssj.ui.activity.shopdetails;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.TimerTask;
//import java.util.regex.Pattern;
//
////import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.bean.SocializeEntity;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
//import com.umeng.socialize.media.UMImage;
//import com.yssj.Constants;
//import com.yssj.YConstance;
//import com.yssj.YConstance.Pref;
//import com.yssj.YJApplication;
//import com.yssj.YUrl;
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.ItemView;
//import com.yssj.custom.view.LoadingDialog;
//import com.yssj.custom.view.MealRecomenView;
//import com.yssj.custom.view.MyPopupwindow;
//import com.yssj.custom.view.MyPopupwindow.ShopDetailsGetShare;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.custom.view.ScaleImageView;
//import com.yssj.custom.view.ShopTopClickView;
//import com.yssj.custom.view.ShopTopClickView.OnCheckedLintener;
//import com.yssj.custom.view.ShowHoriontalView;
//import com.yssj.custom.view.ShowHoriontalView.onClickLintener;
//import com.yssj.custom.view.SizeView;
//import com.yssj.custom.view.SizeView2;
//import com.yssj.custom.view.ToLoginDialog;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.GoodsEntity;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.ShareShop;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopCart;
//import com.yssj.entity.ShopComment;
//import com.yssj.entity.ShopOption;
//import com.yssj.entity.StockType;
//import com.yssj.entity.Store;
//import com.yssj.entity.UserInfo;
//import com.yssj.huanxin.activity.KeFuActivity;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.photoView.ImagePagerActivity;
//import com.yssj.ui.HomeWatcherReceiver;
//import com.yssj.ui.activity.MakeMoneyActivity;
//import com.yssj.ui.activity.ShopCartNewNewActivity;
//import com.yssj.ui.activity.ShopImageActivity;
//import com.yssj.ui.activity.logins.LoginActivity;
//import com.yssj.ui.dialog.NewSignCommonDiaolg;
//import com.yssj.ui.dialog.PublicToastDialog;
//import com.yssj.ui.dialog.XunBaoScollDialog;
//import com.yssj.ui.fragment.circles.SignFragment;
//import com.yssj.utils.DP2SPUtil;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.MD5Tools;
//import com.yssj.utils.QRCreateUtil;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.ShareUtil;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.TongjiShareCount;
//import com.yssj.utils.YCache;
//import com.yssj.utils.YunYingTongJi;
////import com.yssj.utils.sqlite.ShopCartDao;
//import com.yssj.utils.sqlite.ShopCartDao;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnDismissListener;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.TextUtils;
//import android.text.style.ForegroundColorSpan;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.AccelerateInterpolator;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.AnimationSet;
//import android.view.animation.AnimationUtils;
//import android.view.animation.CycleInterpolator;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//import android.view.animation.ScaleAnimation;
//import android.view.animation.TranslateAnimation;
//import android.widget.AbsListView;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.ProgressBar;
//import android.widget.RatingBar;
//import android.widget.RelativeLayout;
//import android.widget.ShareActionProvider;
//import android.widget.TextView;
//import android.widget.Toast;
//import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
//import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
//
///***
// * 商品展示
// * 
// * @author Administrator
// * 
// */
//public class ShopDetailsActivity extends FragmentActivity
//		implements OnClickListener, onClickLintener, OnCheckedLintener, ShopDetailsGetShare {
//	private boolean isdown = false;
//	private int num;
//	private Double f_xiang_mongey;
//
//	private boolean first_come = true; // 防止来回滑动计算次数
//	public static PublicToastDialog shareWaitDialog = null;
//	private boolean first = false;
//
//	private int width, height, heights;
//	private Shop shop;
//	private TextView tv_shop_car, tv_buy, sign_buy;
//	private LinearLayout mSingleBuy, mTwoBuy, mGroupBuy;
//	private TextView mSinglePrice, mTwoPrice, mGroupPrice;
//	private LinearLayout mLlActivity;// 活动列表跳过来的展示
//	private ImageView /* img_fenx, */ img_xin, img_addxin;
//	private RelativeLayout img_fenx;
//	public static int setEva_count_z;
//	public static String MealType;
//	private LinearLayout img_back;
//	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//
//	private LinearLayout lin_add_like;
//	private RelativeLayout img_cart, img_cart2;
//
//	private ImageView lin_contact;
//	private TextView tv_fenxiang;
//	private RelativeLayout lin_contact_old;
//
//	private SAsyncTask<String, Void, HashMap<String, Object>> aa;
//	private SAsyncTask<Void, Void, HashMap<String, Object>> bb;
//	private SAsyncTask<Void, Void, HashMap<String, Object>> cc;
//
//	private DisplayImageOptions options;
//
//	private LinkedList<HashMap<String, Object>> dataList;
//
//	private String[] images;// 普通商品图片
//	private List<HashMap<String, String>> listTitle;
//
//	private int check = 0;
//	private RelativeLayout rlBottom;
//	private LinearLayout rlTop;
//
//	private MyPopupwindow myPopupwindow;
//
//	private TextView tv_cart_count, tv_cart_count2;
//
//	private RelativeLayout rl_retain;
//
//	private Context context;
//
//	private StickyListHeadersListView mListView;
//
//	private MyAdapter adapter;
//
//	private View headerView;
//	private String titleId;// 筛选类别的id
//	public static ShopDetailsActivity instance;
//	private static boolean isShow;
//	private ImageButton mShuaixuanNew;
//
//	private ImageView xunBaoIv;
//
//	private LinearLayout redShare;
//	private ImageView moneyShare;
//	private LinearLayout mNomarBottom, mActivityBottom;
//	private boolean mIsGroup = false;// 用来判断是否是从拼团广场跳过来的
//	private boolean mIsTwoGroup = false;// true时代表发起了2人拼团购买，为了给购买成功后的弹窗用
//	private boolean isHot;// 是购物首页热卖的商品
//	private String rollCode = "0";
//	private String r_code = "";
//	private int groupFlag = 0;
//
//	private static int isPause = 0;
//
//	private Intent qqShareIntent;
//	private Intent wXinShareIntent;
//
//	private Runnable r, shareRun;
//	private String mSupp_label = "";// 供应商名字
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//		HomeWatcherReceiver.unregisterHomeKeyReceiver(context);
//		if (newHandler != null) {
//			newHandler.removeCallbacks(r);
//		}
//		if (shareHandler != null) {
//			shareHandler.removeCallbacks(shareRun);
//		}
//		YJApplication.getLoader().stop();
//		isPause = 1;
//	}
//
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		super.onActivityResult(arg0, arg1, arg2);
//
//		if (arg1 == RESULT_OK) {
//
//			// queryShopDetails();
//			// queryShopMeal();
//			ShopCartDao dao = new ShopCartDao(context);
//			// int count = dao.queryCartCount(context);
//			int count = 0;
//			count = dao.queryCartCommonCount(context);
//			if (arg0 == 1080) {
//
//			} else if (arg0 == 233) {// 加入购物车
//				double a = entity.getOriginal_price();
//				int b = (int) a;
//				joinShopCart(entity.getSize(), entity.getColor(), entity.getShop_num(), entity.getStock_type_id(),
//						entity.getPic(), entity.getPrice(), entity.getSupp_id(), entity.getKickback(), b, mListView);
//
//			} else if (arg0 == 234) {// 购买
//				Intent intent = new Intent(ShopDetailsActivity.this, SubmitOrderActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putSerializable("shop", shop);
//				intent.putExtras(bundle);
//				intent.putExtra("id", entity.getId());
//				intent.putExtra("size", entity.getSize());
//				intent.putExtra("color", entity.getColor());
//				intent.putExtra("shop_num", entity.getShop_num());
//				intent.putExtra("stock_type_id", entity.getStock_type_id());
//				intent.putExtra("stock", entity.getStock());
//				intent.putExtra("price", entity.getPrice());
//				intent.putExtra("pic", entity.getPic());
//				startActivity(intent);
//			} else if (arg0 == 235) {
//				queryShopDetails();
//			} else if (arg0 == 15502) {
//				if (YJApplication.instance.isLoginSucess()) {
//					share(shop.getShop_code(), shop);
//
//				}
//			}
//		}
//
//	}
//
//	private List<Shop> list;// 套餐内商品集合
//
//	private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
//
//		// private int i;
//
//		@Override
//		public int getCount() {
//			int count = 0;
//			if (check == 0) {
//				count += images.length;
//				if (dataList != null) {
//					count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
//				}
//			} else if (check == 1) {
//				count += 2;
//			} else {
//				count += 2;
//				if (listShopComments != null && listShopComments.size() > 0) {
//					count += listShopComments.size();
//				} else {
//					count++;
//				}
//			}
//			return count;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			return arg0;
//		}
//
//		@Override
//		public View getView(int position, View v, ViewGroup arg2) {
//			ItemViewHolder vh;
//			if (v == null) {
//				v = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.new_shop_item, arg2, false);
//				vh = new ItemViewHolder();
//				v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
//				vh.imageGroup = v.findViewById(R.id.image_group);
//				vh.sView2 = (SizeView2) v.findViewById(R.id.size_view2);
//				vh.shopItem = v.findViewById(R.id.item_position);
//				vh.image = (ImageView) v.findViewById(R.id.image_position);
//				vh.image.getLayoutParams().height = width * 9 / 6;
//				vh.left = (ItemView) v.findViewById(R.id.left);
//				vh.left.setHeight(width / 2 * 9 / 6);
//				vh.right = (ItemView) v.findViewById(R.id.right);
//				vh.right.setHeight(width / 2 * 9 / 6);
//				vh.sView = (SizeView) v.findViewById(R.id.size_view);
//				vh.eView = v.findViewById(R.id.sevalauate_view);
//				vh.eView.setBackgroundColor(Color.WHITE);
//				vh.lin_nodata = (LinearLayout) v.findViewById(R.id.lin_nodata);
//
//				vh.pb_color_count = (ProgressBar) v.findViewById(R.id.pb_color_count);
//				vh.pb_type_count = (ProgressBar) v.findViewById(R.id.pb_type_count);
//				vh.pb_work_count = (ProgressBar) v.findViewById(R.id.pb_work_count);
//				vh.pb_cost_count = (ProgressBar) v.findViewById(R.id.pb_cost_count);
//
//				vh.tv_color_count = (TextView) v.findViewById(R.id.tv_color_count);
//				vh.tv_type_count = (TextView) v.findViewById(R.id.tv_type_count);
//				vh.tv_work_count = (TextView) v.findViewById(R.id.tv_work_count);
//				vh.tv_cost_count = (TextView) v.findViewById(R.id.tv_cost_count);
//
//				vh.viewContainer = (LinearLayout) v.findViewById(R.id.container);
//
//				vh.img_user_header = (RoundImageButton) v.findViewById(R.id.img_user_header);
//				vh.tv_user = (TextView) v.findViewById(R.id.tv_user);
//				vh.tv_evaluate = (TextView) v.findViewById(R.id.tv_evaluate);
//				vh.tv_date = (TextView) v.findViewById(R.id.tv_date);
//				vh.tv_descri = (TextView) v.findViewById(R.id.tv_descri);
//				vh.tv_size_color = (TextView) v.findViewById(R.id.tv_size_color);
//				vh.img_container = (LinearLayout) v.findViewById(R.id.img_container);
//				vh.tv_one_reply = (TextView) v.findViewById(R.id.tv_one_reply);
//				vh.tv_second_judge = (TextView) v.findViewById(R.id.tv_second_judge);
//				vh.tv_second_reply = (TextView) v.findViewById(R.id.tv_second_reply);
//
//				vh.lin_second = (LinearLayout) v.findViewById(R.id.lin_second);
//
//				vh.bar = (RatingBar) v.findViewById(R.id.smy_ratingbar);
//
//				vh.evaView = v.findViewById(R.id.evaluate_view);
//
//				vh.bai = v.findViewById(R.id.bai);
//				vh.bai.getLayoutParams().height = ShopDetailsActivity.this.height / 3;
//				// vh.search=(ImageButton) view.findViewById(R.id.search);
//				// vh.shaixuan=(ImageButton) view.findViewById(R.id.shaixuan);
//
//				vh.diver = v.findViewById(R.id.diver);
//
//				vh.sizeHint = (ImageView) v.findViewById(R.id.size_hint);
//				vh.sizeHint.getLayoutParams().height = width * 2453 / 1080;
//
//				vh.two_container = (LinearLayout) v.findViewById(R.id.two_image_container);
//				vh.imageTag = (ImageView) v.findViewById(R.id.meal_tag);
//
//				vh.mealRView = (MealRecomenView) v.findViewById(R.id.meal_r);
//
//				v.setTag(vh);
//			} else {
//				vh = (ItemViewHolder) v.getTag();
//			}
//
//			if (check == 0) {// 详情
//
//				vh.mealRView.setVisibility(View.GONE);
//				vh.sView.setVisibility(View.GONE);
//				vh.eView.setVisibility(View.GONE);
//				vh.bai.setVisibility(View.GONE);
//				vh.diver.setVisibility(View.GONE);
//				vh.sizeHint.setVisibility(View.GONE);
//
//				if (position == images.length - 1) {
//					vh.sView2.setContent(shop, false, position);
//					vh.sView2.setVisibility(View.VISIBLE);
//				} else {
//					vh.sView2.setVisibility(View.GONE);
//				}
//				vh.imageTag.setVisibility(View.GONE);
//				if (position < images.length) {
//					vh.imageGroup.setVisibility(View.VISIBLE);
//					vh.shopItem.setVisibility(View.GONE);
//					vh.image.setTag(shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
//							+ images[position] + "!450");
//					SetImageLoader.initImageLoader(null, vh.image,
//							shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + images[position],
//							"!450");
//					final int x = position;
//					vh.image.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
//							intent.putExtra("url",
//									shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + images[x]);
//							intent.putExtra("shop", shop);
//							intent.putExtra("isMembers", false);
//							intent.putExtra("supp_label", mSupp_label);
//							intent.putExtra("ShopCart", shopCart);
//							intent.putExtra("number_sold", number_sold);
//							intent.putExtra("isSignActiveShop", isSignActiveShop);
//							startActivityForResult(intent, 1080);
//						}
//					});
//
//					return v;
//				}
//				if (isforcelook == true || isforcelookMatch
//						|| (isSignActiveShop && SignFragment.doType == 4 && isSignActiveShopScan)) {// 活动商品并且是浏览个数的任务
//					if (position == images.length) {
//						if (first_come == true) {
//							first_come = false;
//
//							if (isforcelook) {/// 正价商品
//								String nowTimeForcelook = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this,
//										"forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//								forcelookNum = Integer
//										.valueOf(
//												SharedPreferencesUtil
//														.getStringData(ShopDetailsActivity.this,
//																SignFragment.signIndex + "forcelookNum"
//																		+ YCache.getCacheUser(context).getUser_id(),
//																"0"));
//								if (!df.format(new Date()).equals(nowTimeForcelook)) {
//									forcelookNum = 0;// 不是同一天点击分享任务
//														// 或者不是同一个用户 就
//														// 或者取出的数据大于浏览次数
//														// 重新开始计数分享的次数
//								}
//								forcelookNum++;
//								if (SignFragment.doNum > 1) {// 需要奖励分多次发放
//									sign();
//								} else {
//									SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//											"forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
//											df.format(new Date()));
//									SharedPreferencesUtil
//											.saveStringData(ShopDetailsActivity.this,
//													SignFragment.signIndex + "forcelookNum"
//															+ YCache.getCacheUser(context).getUser_id(),
//													"" + forcelookNum);
//									if (forcelookNum < Integer.parseInt(singvalue)) {
//										ToastUtil.showLongText(ShopDetailsActivity.this,
//												"再浏览" + (Integer.parseInt(singvalue) - forcelookNum) + "件可完成任务喔~");
//									} else if (forcelookNum >= Integer.parseInt(singvalue)) {
//										sign();
//									}
//								}
//
//							} else if (isforcelookMatch) {// 搭配购商品
//								String nowTimeForcelookMatch = SharedPreferencesUtil.getStringData(
//										ShopDetailsActivity.this,
//										"forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//								forcelookMatchNum = Integer
//										.valueOf(
//												SharedPreferencesUtil
//														.getStringData(ShopDetailsActivity.this,
//																SignFragment.signIndex + "forcelookMatchNum"
//																		+ YCache.getCacheUser(context).getUser_id(),
//																"0"));
//								if (!df.format(new Date()).equals(nowTimeForcelookMatch)) {
//									forcelookMatchNum = 0;// 不是同一天点击分享任务
//															// 或者不是同一个用户
//															// 就重新开始计数分享的次数
//								}
//								forcelookMatchNum++;
//								if (SignFragment.doNum > 1) {// 需要奖励分多次发放
//									sign();
//								} else {
//									SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//											"forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
//											df.format(new Date()));
//									SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//											SignFragment.signIndex + "forcelookMatchNum"
//													+ YCache.getCacheUser(context).getUser_id(),
//											"" + forcelookMatchNum);
//									if (forcelookMatchNum < Integer.parseInt(singvalue)) {
//										ToastUtil.showLongText(ShopDetailsActivity.this,
//												"再浏览" + (Integer.parseInt(singvalue) - forcelookMatchNum) + "件可完成任务喔~");
//									} else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
//										sign();
//									}
//								}
//							} else if (isSignActiveShop) {// 活动商品
//								String nowTimeSignActiveShop = SharedPreferencesUtil.getStringData(
//										ShopDetailsActivity.this,
//										"signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//								signActiveShopNum = Integer
//										.valueOf(
//												SharedPreferencesUtil
//														.getStringData(ShopDetailsActivity.this,
//																SignFragment.signIndex + "signActiveShopNum"
//																		+ YCache.getCacheUser(context).getUser_id(),
//																"0"));
//								if (!df.format(new Date()).equals(nowTimeSignActiveShop)) {
//									signActiveShopNum = 0;// 不是同一天点击分享任务
//															// 或者不是同一个用户
//															// 就重新开始计数分享的次数
//								}
//								signActiveShopNum++;
//								if (SignFragment.doNum > 1) {// 需要奖励分多次发放
//									sign();
//								} else {
//									SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//											"signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
//											df.format(new Date()));
//									SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//											SignFragment.signIndex + "signActiveShopNum"
//													+ YCache.getCacheUser(context).getUser_id(),
//											"" + signActiveShopNum);
//									if (signActiveShopNum < Integer.parseInt(singvalue)) {
//										ToastUtil.showLongText(ShopDetailsActivity.this,
//												"再浏览" + (Integer.parseInt(singvalue) - signActiveShopNum) + "件可完成任务喔~");
//									} else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
//										sign();
//									}
//								}
//							}
//
//							if (xunBaoIv != null && xunBaoIv.getVisibility() == View.VISIBLE) {
//								xunBaoIv.setVisibility(View.GONE);
//							}
//						}
//					}
//				} // 这些判断是强制浏览滑到最后一张才算一次的
//				position = position - images.length;
//
//				position = position * 2;
//				vh.imageGroup.setVisibility(View.GONE);
//				vh.shopItem.setVisibility(View.VISIBLE);
//
//				vh.left.iniView(dataList.get(position));
//				vh.left.setOnClickListener(new MyOnClick(position));
//				if (dataList.size() > position + 1) {
//					vh.right.setVisibility(View.VISIBLE);
//					vh.right.iniView(dataList.get(position + 1));
//					vh.right.setOnClickListener(new MyOnClick(position + 1));
//				} else {
//					vh.right.setVisibility(View.INVISIBLE);
//				}
//			} else if (check == 1) {// 尺寸
//				vh.mealRView.setVisibility(View.GONE);
//				vh.imageTag.setVisibility(View.GONE);
//				vh.diver.setVisibility(View.GONE);
//
//				if (position == 0) {
//					vh.bai.setVisibility(View.GONE);
//					vh.sView.setVisibility(View.VISIBLE);
//					vh.sizeHint.setVisibility(View.GONE);
//					vh.imageGroup.setVisibility(View.GONE);
//					vh.shopItem.setVisibility(View.GONE);
//					vh.eView.setVisibility(View.GONE);
//					vh.sView.setContent(shop, false, position);
//					return v;
//				}
//
//				if (position == 1) {
//					vh.bai.setVisibility(View.GONE);
//					vh.sView.setVisibility(View.GONE);
//					vh.imageGroup.setVisibility(View.GONE);
//					vh.shopItem.setVisibility(View.GONE);
//					vh.eView.setVisibility(View.GONE);
//					vh.sizeHint.setVisibility(View.VISIBLE);
//					vh.sizeHint.setTag("system/shop_details.png");
//					if (width > 720) {
//						SetImageLoader.initImageLoader(null, vh.sizeHint, "system/shop_details.png", "!450");
//					} else {
//						SetImageLoader.initImageLoader(null, vh.sizeHint, "system/shop_details.png", "!382");
//					}
//
//					return v;
//				}
//
//			} else {// 评论
//
//				vh.mealRView.setVisibility(View.GONE);
//				vh.imageTag.setVisibility(View.GONE);
//				vh.eView.setVisibility(View.VISIBLE);
//				vh.sView.setVisibility(View.GONE);
//				vh.imageGroup.setVisibility(View.GONE);
//				vh.shopItem.setVisibility(View.GONE);
//				vh.sizeHint.setVisibility(View.GONE);
//				if (position == 0) {
//					vh.evaView.setVisibility(View.VISIBLE);
//					vh.viewContainer.setVisibility(View.GONE);
//					vh.lin_nodata.setVisibility(View.GONE);
//					vh.diver.setVisibility(View.GONE);
//					vh.bai.setVisibility(View.GONE);
//
//					if (shop.getEva_count() == 0) {
//						vh.tv_color_count.setText("100%");
//						vh.tv_type_count.setText("100%");
//						vh.tv_work_count.setText("100%");
//						vh.tv_cost_count.setText("100%");
//
//						vh.pb_color_count.setProgress(100);
//						vh.pb_type_count.setProgress(100);
//						vh.pb_work_count.setProgress(100);
//						vh.pb_cost_count.setProgress(100);
//					} else {
//
//						vh.tv_color_count.setText(shop.getEva_count() == 0 ? "100%"
//								: (int) (shop.getColor_count() / shop.getEva_count() * 100) + "%");
//						vh.tv_type_count.setText(shop.getEva_count() == 0 ? "100%"
//								: (int) (shop.getType_count() / shop.getEva_count() * 100) + "%");
//						vh.tv_work_count.setText(shop.getEva_count() == 0 ? "100%"
//								: (int) (shop.getWork_count() / shop.getEva_count() * 100) + "%");
//						vh.tv_cost_count.setText(shop.getEva_count() == 0 ? "100%"
//								: (int) (shop.getCost_count() / shop.getEva_count() * 100) + "%");
//
//						vh.pb_color_count.setProgress(shop.getEva_count() == 0 ? 100
//								: (int) (shop.getColor_count() / shop.getEva_count() * 100));
//						vh.pb_type_count.setProgress(shop.getEva_count() == 0 ? 100
//								: (int) (shop.getType_count() / shop.getEva_count() * 100));
//						vh.pb_work_count.setProgress(shop.getEva_count() == 0 ? 100
//								: (int) (shop.getWork_count() / shop.getEva_count() * 100));
//						vh.pb_cost_count.setProgress(shop.getEva_count() == 0 ? 100
//								: (int) (shop.getCost_count() / shop.getEva_count() * 100));
//					}
//				}
//
//				return v;
//			}
//			if (position == 1 && (listShopComments == null || listShopComments.isEmpty())) {
//				vh.viewContainer.setVisibility(View.GONE);
//				vh.lin_nodata.setVisibility(View.VISIBLE);
//				vh.evaView.setVisibility(View.GONE);
//				vh.bai.setVisibility(View.GONE);
//				vh.diver.setVisibility(View.GONE);
//				return v;
//			}
//			if (position == 2 && (listShopComments == null || listShopComments.isEmpty())) {
//				vh.viewContainer.setVisibility(View.GONE);
//				vh.lin_nodata.setVisibility(View.GONE);
//				vh.evaView.setVisibility(View.GONE);
//				vh.bai.setVisibility(View.VISIBLE);
//				vh.diver.setVisibility(View.GONE);
//				return v;
//			}
//			position = position - 1;
//			if (position == listShopComments.size()) {
//				vh.viewContainer.setVisibility(View.GONE);
//				vh.lin_nodata.setVisibility(View.GONE);
//				vh.evaView.setVisibility(View.GONE);
//				vh.bai.setVisibility(View.VISIBLE);
//				vh.diver.setVisibility(View.GONE);
//				return v;
//			}
//			ShopComment shopComment = listShopComments.get(position);
//			vh.viewContainer.setVisibility(View.VISIBLE);
//			vh.lin_nodata.setVisibility(View.GONE);
//			vh.evaView.setVisibility(View.GONE);
//			vh.bai.setVisibility(View.GONE);
//			vh.diver.setVisibility(View.VISIBLE);
//			vh.img_user_header.setTag(shopComment.getUser_url());
//			SetImageLoader.initImageLoader(ShopDetailsActivity.this, vh.img_user_header, shopComment.getUser_url(), "");
//			String user_name = shopComment.getUser_name();
//			if (!TextUtils.isEmpty(user_name)) {
//
//				if (user_name.length() == 1) {
//					user_name = user_name + "****";
//				}
//
//				vh.tv_user.setText(user_name);
//			}
//			int comment_type = shopComment.getComment_type();
//
//			if (comment_type == 1) {
//				vh.tv_evaluate.setText("好评");
//
//			} else if (comment_type == 2) {
//				vh.tv_evaluate.setText("中评");
//			} else if (comment_type == 3) {
//				vh.tv_evaluate.setText("差评");
//			}
//
//			vh.bar.setRating(((float) shopComment.getStar()));
//
//			long add_date = shopComment.getAdd_date();
//			String date = StringUtils.timeToDate(add_date);
//			if (!TextUtils.isEmpty(date)) {
//				vh.tv_date.setText(date);
//			}
//
//			String content = shopComment.getContent();
//			if (!TextUtils.isEmpty(content)) {
//				vh.tv_descri.setText(content);
//			}
//
//			String shop_color = shopComment.getShop_color();
//			String shop_size = shopComment.getShop_size();
//			if (!TextUtils.isEmpty(shop_color)) {
//				vh.tv_size_color.setText("颜色：" + shop_color + "  尺码：" + shop_size);
//			}
//			String pic = shopComment.getPic();
//			vh.img_container.removeAllViews();
//			if (!TextUtils.isEmpty(pic)) {
//				LayoutParams params = new LayoutParams(80, 80);
//				params.setMargins(2, 1, 0, 1);
//				final String[] picList = pic.split(",");
//
//				for (int j = 0; j < picList.length; j++) {
//					ImageView img = new ImageView(ShopDetailsActivity.this);
//					img.setLayoutParams(params);
//					img.setScaleType(ScaleType.CENTER_CROP);
//					SetImageLoader.initImageLoader(null, img, picList[j], "!180");
//					img.setOnClickListener(new ImageOnClickLintener(j, picList));
//					vh.img_container.addView(img);
//				}
//
//			}
//
//			if (null != shopComment.getSuppComment()) {
//				vh.tv_one_reply.setVisibility(View.VISIBLE);
//				vh.tv_one_reply.setText(Html.fromHtml(ShopDetailsActivity.this.getString(R.string.tv_supp_reply,
//						shopComment.getSuppComment().get(0).getSupp_content())));
//			} else {
//				vh.tv_one_reply.setVisibility(View.GONE);
//			}
//
//			if (null != shopComment.getComment()) {
//
//				vh.lin_second.setVisibility(View.VISIBLE);
//				vh.tv_second_judge.setVisibility(View.VISIBLE);
//				vh.tv_second_judge.setText(Html.fromHtml(ShopDetailsActivity.this.getString(R.string.tv_add_judge,
//						shopComment.getComment().get(0).getContent())));
//				if (null != shopComment.getSuppEndComment() && shopComment.getSuppEndComment().size() > 0) {
//					vh.tv_second_reply.setVisibility(View.VISIBLE);
//					vh.tv_second_reply.setText(Html.fromHtml(ShopDetailsActivity.this.getString(R.string.tv_supp_reply,
//							shopComment.getSuppEndComment().get(0).getSupp_content())));
//				} else {
//					vh.tv_second_reply.setVisibility(View.GONE);
//				}
//				String pics = shopComment.getComment().get(0).getPic();
//				if (TextUtils.isEmpty(pics) == false) {
//					final String[] spic = pics.split(",");
//					vh.two_container.setVisibility(View.VISIBLE);
//					vh.two_container.removeAllViews();
//					LayoutParams params = new LayoutParams(80, 80);
//					params.setMargins(2, 1, 0, 1);
//					for (int j = 0; j < spic.length; j++) {
//						ImageView img = new ImageView(ShopDetailsActivity.this);
//						img.setLayoutParams(params);
//						img.setScaleType(ScaleType.CENTER_CROP);
//						SetImageLoader.initImageLoader(null, img, spic[j], "!180");
//						img.setOnClickListener(new ImageOnClickLintener(j, spic));
//						vh.two_container.addView(img);
//					}
//				} else {
//					vh.two_container.setVisibility(View.GONE);
//				}
//
//			} else {
//				vh.lin_second.setVisibility(View.GONE);
//				vh.tv_second_judge.setVisibility(View.GONE);
//			}
//
//			return v;
//		}
//
//		/**
//		 * 浏览商品详情的签到接口
//		 * 
//		 */
//		private void sign() {
//			String ssType = "";
//			switch (SignFragment.jiangliID) {
//			case 3:
//				ssType = "元优惠券";
//				break;
//			case 4:
//				ssType = "积分";
//				break;
//			case 5:
//				ssType = "元";
//				break;
//			case 11:// 新加 奖励衣豆
//				ssType = "个衣豆";
//				break;
//
//			default:
//				break;
//			}
//			final String ss = ssType;
//			new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {
//
//				@Override
//				protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//						throws Exception {
//
//					return ComModel2.getSignIn(ShopDetailsActivity.this, false, false, SignFragment.signIndex,
//							SignFragment.doClass);
//
//				}
//
//				protected boolean isHandleException() {
//					return true;
//				};
//
//				@Override
//				protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//					super.onPostExecute(context, result, e);
//					if (e == null && result != null) {
//						if (SignFragment.doNum > 1) {// 需要奖励分多次发放
//							if (isforcelook) {// 强制浏览普通商品
//								SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//										"forcelookNowTime" + YCache.getCacheUser(context).getUser_id(),
//										df.format(new Date()));
//								SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this, SignFragment.signIndex
//										+ "forcelookNum" + YCache.getCacheUser(context).getUser_id(),
//										"" + forcelookNum);
//
//								if (forcelookNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
//									ToastUtil.showLongText(ShopDetailsActivity.this,
//											"浏览完成，奖励"
//													// +new
//													// java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//													+ SignFragment.jiangliValue + ss + ",还有"
//													+ (Integer.parseInt(singvalue) - forcelookNum) + "次浏览机会喔~");
//								} else if (forcelookNum >= Integer.parseInt(singvalue)) {
//									NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//											R.style.DialogQuheijiao2, "liulan_sign_finish",
//											new java.text.DecimalFormat("0.##").format(
//													Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum)
//													+ ss);
//									dialog.show();
//								}
//							} else if (isforcelookMatch) {// 强制浏览搭配商品
//								SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//										"forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(),
//										df.format(new Date()));
//								SharedPreferencesUtil
//										.saveStringData(ShopDetailsActivity.this,
//												SignFragment.signIndex + "forcelookMatchNum"
//														+ YCache.getCacheUser(context).getUser_id(),
//												"" + forcelookMatchNum);
//
//								if (forcelookMatchNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
//									ToastUtil.showLongText(ShopDetailsActivity.this,
//											"浏览完成，奖励"
//													// +new
//													// java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//													+ SignFragment.jiangliValue + ss + ",还有"
//													+ (Integer.parseInt(singvalue) - forcelookMatchNum) + "次浏览机会喔~");
//								} else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
//									NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//											R.style.DialogQuheijiao2, "liulan_sign_finish",
//											new java.text.DecimalFormat("0.##").format(
//													Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum)
//													+ ss);
//									dialog.show();
//								}
//							} else if (isSignActiveShop) {// 强制浏览活动商品
//
//								SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//										"signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(),
//										df.format(new Date()));
//								SharedPreferencesUtil
//										.saveStringData(ShopDetailsActivity.this,
//												SignFragment.signIndex + "signActiveShopNum"
//														+ YCache.getCacheUser(context).getUser_id(),
//												"" + signActiveShopNum);
//
//								if (signActiveShopNum < Integer.parseInt(singvalue)) {// 小于要求的分享次数
//									ToastUtil.showLongText(ShopDetailsActivity.this,
//											"浏览完成，奖励"
//													// +new
//													// java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//													+ SignFragment.jiangliValue + ss + ",还有"
//													+ (Integer.parseInt(singvalue) - signActiveShopNum) + "次浏览机会喔~");
//								} else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
//									NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//											R.style.DialogQuheijiao2, "liulan_sign_finish",
//											new java.text.DecimalFormat("0.##").format(
//													Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum)
//													+ ss);
//									dialog.show();
//								}
//							}
//
//						} else {
//							// 其他奖励 一次性奖励
//							NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(ShopDetailsActivity.this,
//									R.style.DialogQuheijiao2, "liulan_sign_finish", SignFragment.jiangliValue + ss);
//							dialog.show();
//
//						}
//					}
//				}
//
//			}.execute();
//		}
//
//		@Override
//		public View getHeaderView(int position, View view, ViewGroup parent) {
//			HeaderViewHolder vh;
//			if (view == null) {
//				vh = new HeaderViewHolder();
//				view = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.header_item, parent, false);
//				vh.topOne = (ShopTopClickView) view.findViewById(R.id.top_one);
//
//				vh.topOne.setText2(setEva_count_z);
//
//				vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
//				vh.topOne.setCheckLintener(ShopDetailsActivity.this);
//				vh.topTwo.setOnClickLintener(ShopDetailsActivity.this);
//				vh.title = (TextView) view.findViewById(R.id.title);
//				vh.topOne.setBackgroundColor(Color.WHITE);
//				vh.title.setBackgroundColor(Color.WHITE);
//				vh.topTwo.setBackgroundColor(Color.WHITE);
//
//				// vh.title.setVisibility(view.GONE);
//				vh.topTwo.setList(listTitle);
//				view.setTag(vh);
//			} else {
//				vh = (HeaderViewHolder) view.getTag();
//			}
//
//			if (position < images.length) {
//				vh.topOne.setVisibility(View.VISIBLE);
//				vh.topOne.setIndex(check);
//				vh.title.setText("商品介绍");
//				vh.topTwo.setVisibility(View.GONE);
//				isShopTitle = false;
//				return view;
//			}
//			isShopTitle = true;
//			vh.title.setText("商品推荐");
//			vh.topOne.setVisibility(View.GONE);
//			vh.topTwo.setVisibility(View.VISIBLE);
//			vh.topTwo.setIndex(titleCheck);
//			return view;
//		}
//
//		@Override
//		public long getHeaderId(int position) {
//			if (check != 0) {
//				return 0;
//			}
//
//			if (position < images.length) {
//
//				if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//
//					rlBottom.clearAnimation();
//					rlBottom.startAnimation(animationShow);
//				}
//
//				return 0;
//			} else {
//				if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//
//					rlBottom.clearAnimation();
//					rlBottom.startAnimation(animationGone);
//				}
//				return 1;
//			}
//
//		}
//
//	}
//
//	private boolean isAnim = false;
//
//	private static class ItemViewHolder {
//
//		View imageGroup;
//		View shopItem;
//		ItemView left;
//		ItemView right;
//		ImageView image, imageTag;
//
//		SizeView sView;
//		SizeView2 sView2;
//		View eView;
//
//		ProgressBar pb_color_count, pb_type_count, pb_work_count, pb_cost_count;// 没有色差，
//																				// 版型好看，
//																				// 做工不错，
//																				// 性价比好
//		TextView tv_color_count, tv_type_count, tv_work_count, tv_cost_count;
//
//		LinearLayout viewContainer;
//
//		LinearLayout lin_nodata;
//
//		View bai;
//
//		RoundImageButton img_user_header;
//		TextView tv_user;
//		TextView tv_evaluate;
//		TextView tv_date;
//		TextView tv_descri;
//		TextView tv_size_color;
//		LinearLayout img_container;
//
//		TextView tv_one_reply;
//		TextView tv_second_judge;
//		TextView tv_second_reply;
//		LinearLayout lin_second;
//
//		RatingBar bar;
//
//		View evaView;
//
//		View diver;
//
//		ImageView sizeHint;
//
//		LinearLayout two_container;
//
//		MealRecomenView mealRView;
//	}
//
//	private class ImageOnClickLintener implements OnClickListener {
//
//		private int position;
//
//		private String[] urls;
//
//		public ImageOnClickLintener(int position, String[] urls) {
//			super();
//			this.position = position;
//			this.urls = urls;
//		}
//
//		@Override
//		public void onClick(View arg0) {
//			Intent intent = new Intent(context, ImagePagerActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putStringArray("urls", urls);
//			bundle.putInt("index", position);
//			intent.putExtras(bundle);
//			startActivity(intent);
//		}
//
//	}
//
//	private static class HeaderViewHolder {
//
//		ShopTopClickView topOne;
//
//		ShowHoriontalView topTwo;
//
//		TextView title;
//	}
//
//	private Animation animationGone; // 上拉隐藏底部按钮
//	private Animation animationShow;// 下拉显示底部按钮
//
//	private RelativeLayout rrr;
//
//	private String singvalue;// 强制浏览数量
//	private int forcelookNum;// 强制浏览的计数
//	private int forcelookMatchNum;// 搭配商品强制浏览的计数
//	private int signActiveShopNum;// 活动商品强制浏览的计数
//
//	@SuppressLint("SimpleDateFormat")
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		headerView = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header, null);
//		mIsGroup = getIntent().getBooleanExtra("mIsGroup", false);
//		isHot = getIntent().getBooleanExtra("isHot", false);
//		r_code = getIntent().getStringExtra("r_code");
//		AppManager.getAppManager().addActivity(this);
//		setContentView(R.layout.activity_shop_details);
//		isforcelook = getIntent().getBooleanExtra("isforcelook", false);
//		isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
//		isSignActiveShop = getIntent().getBooleanExtra("isSignActiveShop", false);
//		isSignActiveShopScan = getIntent().getBooleanExtra("isSignActiveShopScan", false);
//		shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
//		if (isforcelook == true || isforcelookMatch
//				|| (isSignActiveShop && SignFragment.doType == 4 && isSignActiveShopScan)) {//// 活动商品并且是浏览个数的任务
//			String value = SignFragment.doValue;
//			String values[] = value.split(",");
//			if (values.length > 1) {
//				singvalue = values[1];
//				if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
//					singvalue = "" + SignFragment.doNum;
//				}
//			} else {
//				singvalue = "" + SignFragment.doNum;
//			}
//
//			xunBaoIv = (ImageView) findViewById(R.id.scoll_xunbao);
//			df = new java.text.SimpleDateFormat("yyyy-MM-dd");
//			String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKSCOLL, "0");
//			long forceLookTime = Long.valueOf(forceLook);
//			if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
//				XunBaoScollDialog dialog = new XunBaoScollDialog(ShopDetailsActivity.this, xunBaoIv);
//				dialog.show();
//				SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKSCOLL, System.currentTimeMillis() + "");
//			} else {
//				xunBaoIv.setVisibility(View.VISIBLE);
//			}
//
//		}
//
//		shopCart = getIntent().getBooleanExtra("ShopCart", false);
//		titleId = getIntent().getStringExtra("id");
//
//		number_sold = getIntent().getStringExtra("number_sold"); // 取出是否抢完的值
//
//		getWindownPixes();
//		context = this;
//		initView();
//		if (number_sold != null) {
//			if (number_sold.equals("none")) {
//				// 没有库存
//				tv_shop_car.setTextColor(getResources().getColor(R.color.white_white));
//				tv_shop_car.setBackgroundColor(getResources().getColor(R.color.gray_white));
//
//			} else {
//				// 有库存
//				tv_shop_car.setTextColor(Color.parseColor("#ff3f8c"));
//				tv_shop_car.setBackgroundColor(Color.parseColor("#ffffff"));
//			}
//		}
//
//		YDBHelper helper = new YDBHelper(this);
//		String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
//		listTitle = helper.query(sql);
//		int a = 0;
//		int b = 0;
//		for (int i = 0; i < listTitle.size(); i++) {
//			String sort_name = listTitle.get(i).get("sort_name");
//			if ("上衣".equals(sort_name)) {
//				a = i;
//			} else if ("外套".equals(sort_name)) {
//				b = i;
//			}
//		}
//
//		HashMap<String, String> listTitle_temp1 = (HashMap<String, String>) listTitle.get(a);
//		HashMap<String, String> listTitle_temp2 = (HashMap<String, String>) listTitle.get(b);
//		if (b > a) {// 上衣在外套前面时就调换
//			listTitle.remove(a);
//			listTitle.add(a, listTitle_temp2);
//			listTitle.remove(b);
//			listTitle.add(b, listTitle_temp1);
//		}
//		initImageLoader();
//		code = getIntent().getStringExtra("code");
//		instance = this;
//
//		lin_add_like.setVisibility(View.VISIBLE);
//		if (!TextUtils.isEmpty(code)) {
//			queryShopDetails();
//		} else {
//			finish();
//		}
//
//		shareRun = new Runnable() {
//
//			@Override
//			public void run() {
//				if (!YJApplication.instance.isLoginSucess()) {
//					return;
//				}
//				if (isPause == 1) {
//					return;
//				}
//				if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == ShopDetailsActivity.this
//						.getSharedPreferences("EveryDayShareAm", Context.MODE_PRIVATE).getInt("day", 0)) {
//					return;
//				}
//				if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == ShopDetailsActivity.this
//						.getSharedPreferences("EveryDaySharePm", Context.MODE_PRIVATE).getInt("day", 0)) {
//					return;
//				}
//				List<String> taskMap = YJApplication.instance.getTaskMap();
//				if (taskMap == null) {
//					taskMap = new ArrayList<String>();
//				}
//				if (taskMap.contains("7") || taskMap.contains("8")) {
//					return;
//				}
//				if (isShow) {
//					return;
//				}
//
//				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//				// 每日上午分享一次
//				if ((hour > 7 || hour == 7) && hour < 14) {
//
//				}
//
//			}
//
//		};
//
//	}
//
//	private String pos;
//
//	private HashMap<String, Object> mealMap;
//
//	public static int shopTask = 0;
//	public static int everyDayTask1_2 = 0;
//
//	private String code;
//
//	private void downloadPic(String picPath, int i) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			// 输出的文件流 /sdcard/yssj/
//			File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
//			if (file.exists()) {
//				file.delete();
//			}
//			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
//			OutputStream os = new FileOutputStream(file);
//			// 开始读取
//			while ((len = is.read(bs)) != -1) {
//				os.write(bs, 0, len);
//			}
//			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
//			// 完毕，关闭所有链接
//			os.close();
//			is.close();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		AppManager.activityStack.remove(this);
//		isPause = 1;
//		// instance = null;
//	}
//
//	private void initImageLoader() {
//
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.image_default)
//				.showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true)
//				// .displayer(new FadeInBitmapDisplayer(35))
//				.build();
//
//	}
//
//	/***
//	 * 得到屏幕宽度和高度 像素
//	 */
//	private void getWindownPixes() {
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		width = dm.widthPixels;
//		height = dm.heightPixels;
//	}
//
//	private Handler newHandler;
//	private Handler shareHandler;
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		HomeWatcherReceiver.registerHomeKeyReceiver(context);
//		SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE, "1050");
//
//		if (isSignActiveShop) {
//			if (mIsGroup) {// 拼团广场跳过来的
//				mGroupBuy.setVisibility(View.VISIBLE);
//				mLlActivity.setVisibility(View.GONE);
//			} else {// 活动商品列表跳过来的
//				mGroupBuy.setVisibility(View.GONE);
//				mLlActivity.setVisibility(View.VISIBLE);
//			}
//			mActivityBottom.setVisibility(View.VISIBLE);
//			mNomarBottom.setVisibility(View.GONE);
//		} else {
//			setShareAnim();
//		}
//
//		ShopCartDao dao = new ShopCartDao(context);
//
//		LogYiFu.e("ShopDetailsActivity_onresume", "OK");
//		isPause = 0;
//		List<String> taskMap = YJApplication.instance.getTaskMap();
//		if (taskMap == null) {
//			taskMap = new ArrayList<String>();
//		}
//		int curr = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		if ((!taskMap.contains("17") || !YJApplication.instance.isLoginSucess())
//				&& curr != getSharedPreferences("month", Context.MODE_PRIVATE).getInt("month", 0)
//				&& (getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == Calendar.getInstance()
//						.get(Calendar.DAY_OF_WEEK)
//						|| getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == 0)) {
//			getSharedPreferences("month", Context.MODE_PRIVATE).edit()
//					.putInt("month", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//			getSharedPreferences("week", Context.MODE_PRIVATE).edit()
//					.putInt("week", Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).commit();
//		} else {
//			if (YJApplication.instance.isLoginSucess() == false
//					|| getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == curr) {
//				shareHandler = new Handler();
//				shareHandler.postDelayed(shareRun, 30 * 1000);
//				return;
//			}
//			UserInfo user = YCache.getCacheUser(context);
//			if (user.getHobby() == null || user.getHobby().equals("0")) {
//
//				newHandler = new Handler();
//				newHandler.postDelayed(r, 60 * 1000);
//			}
//
//		}
//
//	}
//
//	private void initView() {
//		mSinglePrice = (TextView) findViewById(R.id.tv_single_price);
//		mTwoPrice = (TextView) findViewById(R.id.tv_two_price);
//		mGroupPrice = (TextView) findViewById(R.id.tv_group_price);
//		mLlActivity = (LinearLayout) findViewById(R.id.activity_ll_two);
//		mSingleBuy = (LinearLayout) findViewById(R.id.activity_ll_single_buy);
//		mTwoBuy = (LinearLayout) findViewById(R.id.activity_ll_two_buy);
//		mGroupBuy = (LinearLayout) findViewById(R.id.activity_ll_group_buy);
//		mNomarBottom = (LinearLayout) findViewById(R.id.ll_abc);
//		mActivityBottom = (LinearLayout) findViewById(R.id.activity_ll_all_bottom);
//		mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
//		rrr = (RelativeLayout) findViewById(R.id.rrr);
//		redShare = (LinearLayout) findViewById(R.id.red_share_ll);
//		moneyShare = (ImageView) findViewById(R.id.money_share_iv);
//		rl_hava_twenty = (RelativeLayout) findViewById(R.id.rl_hava_twenty);
//		rl_hava_twenty.getBackground().setAlpha(130);
//
//		rlBottom = (RelativeLayout) findViewById(R.id.ray_bottom);
//
//		tv_shop_car_fake = (TextView) findViewById(R.id.tv_shop_car_fake);
//		tv_buy_now = (TextView) findViewById(R.id.tv_buy_now);
//		tv_buy_now.setOnClickListener(this);
//		tv_shop_car = (TextView) findViewById(R.id.tv_shop_car);
//		// 如果是拼团商品
//		if (isSignActiveShop) {
//			tv_shop_car_fake.setText("立即购买");
//			tv_shop_car.setText("立即购买");
//		}
//		tv_shop_car.setVisibility(View.GONE);
//		tv_shop_car.setOnClickListener(this);
//		sign_buy = (TextView) findViewById(R.id.sign_buy);
//		sign_buy.setOnClickListener(this);
//
//		lin_add_like = (LinearLayout) findViewById(R.id.lin_add_like);
//		lin_add_like.setOnClickListener(this);
//
//		img_cart = (RelativeLayout) findViewById(R.id.img_cart);
//		img_cart2 = (RelativeLayout) findViewById(R.id.img_cart2);
//		img_cart.setOnClickListener(this);
//		img_cart2.setOnClickListener(this);
//
//		findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
//		tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);// 购物车计数
//		tv_fenxiang = (TextView) findViewById(R.id.tv_fenxiang);// 右边的分享
//		tv_fenxiang.setOnClickListener(this);
//		tv_cart_count2 = (TextView) findViewById(R.id.tv_cart_count2);// 购物车计数
//		lin_contact = (ImageView) findViewById(R.id.lin_contact);
//		lin_contact.setOnClickListener(this);
//		mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
//
//		img_fenx = (RelativeLayout) findViewById(R.id.img_fenx);// 分享
//		img_fenx.setOnClickListener(this);
//
//		img_xin = (ImageView) findViewById(R.id.img_xin);// 加心
//
//		ll_bottem = (LinearLayout) findViewById(R.id.ll_bottem);
//
//		rl_retain = (RelativeLayout) findViewById(R.id.rl_retain); // 保留30秒钟的黑色条
//		rl_retain.setOnClickListener(this);
//		rl_retain.getBackground().setAlpha(204);
//		img_addxin = (ImageView) findViewById(R.id.addxin);
//
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//
//		ShareActionProvider provider = new ShareActionProvider(this);
//		Intent sendIntent = new Intent(Intent.ACTION_SEND);
//		sendIntent.putExtra(Intent.EXTRA_TEXT, "不错的商品哦，值得你一看，赶紧来吧！！！");
//		sendIntent.setType("text/plain");
//		sendIntent.setData(Uri.parse(YUrl.QUERY_SHOP_DETAILS));
//		provider.setShareIntent(sendIntent);
//
//		mListView = (StickyListHeadersListView) findViewById(R.id.data);
//		rlTop = (LinearLayout) findViewById(R.id.ray_top);
//		rlTop.setBackgroundResource(R.drawable.zhezhao2x);
//		if (mIsGroup || isSignActiveShop) {
//			tv_fenxiang.setVisibility(View.GONE);
//		} else {
//			tv_fenxiang.setVisibility(View.VISIBLE);
//		}
//		animationGone = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_gone);
//		animationShow = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_show);
//
//		animationGone.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				ll_bottem.post(new Runnable() {
//
//					@Override
//					public void run() {
//						ll_bottem.setVisibility(View.GONE);
//					}
//				});
//				isAnim = false;
//			}
//		});
//		animationShow.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//				ll_bottem.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				isAnim = false;
//			}
//		});
//
//		baoyou_animationGone = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_gone);
//		baoyou_animationShow = AnimationUtils.loadAnimation(ShopDetailsActivity.this, R.anim.shop_bottom_show);
//
//		baoyou_animationGone.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				rlBottom.post(new Runnable() {
//
//					@Override
//					public void run() {
//						rlBottom.setVisibility(View.GONE);
//					}
//				});
//				isAnim = false;
//			}
//		});
//		baoyou_animationShow.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				isAnim = true;
//				rlBottom.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				isAnim = false;
//			}
//		});
//	}
//
//	public int imageHeight;
//
//	private List<ShopComment> listShopComments;
//
//	/***
//	 * 刷新界面
//	 * 
//	 * @param shop
//	 */
//
//	private class MyOnClick implements OnClickListener {
//
//		private int position;
//
//		public MyOnClick(int position) {
//			super();
//			this.position = position;
//		}
//
//		@Override
//		public void onClick(View arg0) {
//			HashMap<String, Object> posMap = dataList.get(position);
//
//			ShopDetailsActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
//					.putBoolean("isGoDetail", true).commit();
//			if (YJApplication.instance.isLoginSucess()) {
//				addScanDataTo((String) posMap.get("shop_code"));
//			}
//			Intent intent = new Intent(ShopDetailsActivity.this, ShopDetailsActivity.class);
//			intent.putExtra("code", (String) posMap.get("shop_code"));
//			// context.startActivity(intent);
//			intent.putExtra("shopCarFragment", "shopCarFragment");
//
//			startActivity(intent);
//			finish();
//			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//		}
//	}
//
//	/*
//	 * 把浏览过的数据添加进数据库
//	 */
//	private void addScanDataTo(final String shop_code) {
//		new SAsyncTask<Void, Void, ReturnInfo>(ShopDetailsActivity.this) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
//				return ComModel.addMySteps(context, shop_code);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//	}
//
//	private boolean clickFlag = true;// 加入购物车能否点击的标识
//
//	@Override
//	public void onClick(View view) {
//		if (YJApplication.instance.isLoginSucess() == false) {
//			if (view.getId() == R.id.img_back) {
//				onBackPressed();
//				return;
//			}
//
//			if (LoginActivity.instances != null) {
//				LoginActivity.instances.finish();
//			}
//
//			Intent intent = new Intent(context, LoginActivity.class);
//			intent.putExtra("login_register", "login");
//			((FragmentActivity) context).startActivityForResult(intent, 235);
//			return;
//		}
//
//		switch (view.getId()) {
//		case R.id.lin_contact:
//
//			Intent intent = new Intent(this, KeFuActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("shop", shop);
//			if (null == shop) {
//				ToastUtil.showShortText(context, "操作无效");
//				return;
//			}
//			intent.putExtra("ISMEMBERS", false);
//			intent.putExtra("isSignActiveShop", isSignActiveShop);
//			intent.putExtras(bundle);
//
//			startActivity(intent);
//
//			break;
//		case R.id.img_back:
//			onBackPressed();
//			break;
//		case R.id.activity_ll_two_buy:// 拼团中自己发起2人团购买
//			rollCode = "1";
//			groupFlag = 1;
//			mIsTwoGroup = true;
//			queryShopQueryAttr(0); // 查询普通商品属性
//			break;
//		case R.id.activity_ll_group_buy:// 拼团中组别人的团购买
//			groupFlag = 2;
//			rollCode = "" + r_code;
//			mIsTwoGroup = false;
//			queryShopQueryAttr(0); // 查询普通商品属性
//			break;
//		case R.id.activity_ll_single_buy:// 拼团中单个立即购买
//		case R.id.tv_shop_car:// 活动商品立即购买 普通商品 加入购物车
//			rollCode = "0";
//			mIsTwoGroup = false;
//			if (isSignActiveShop) {
//				groupFlag = 0;
//				queryShopQueryAttr(0); // 查询普通商品属性
//			} else {
//				if (!clickFlag) {// 正在加入购物车时候的点击不执行
//					break;
//				}
//				int cart_count = shop.getCart_count();
//				if (cart_count >= 20) {
//					ThreeSecond();
//					break;
//				}
//
//				if (number_sold != null && number_sold.equals("none")) {
//					return;
//				} else {
//					queryShopQueryAttr(1); // 查询普通商品属性
//				}
//			}
//			break;
//		// case R.id.tv_buy:// 立即购买
//		case R.id.sign_buy:
//			if (number_sold != null && number_sold.equals("none")) {
//				return;
//			} else {
//				queryShopQueryAttr(0);
//			}
//
//			break;
//		case R.id.tv_buy_now:
//			if (number_sold != null && number_sold.equals("none")) {
//				return;
//			} else {
//				queryShopQueryAttr(0);
//			}
//
//			break;
//		case R.id.tv_fenxiang:// 右边的分享
//			// MobclickAgent.onEvent(context, "shopdetailshareclick");
//			double feedback = 0;
//			if (null == shop) {
//				ToastUtil.showShortText(context, "操作无效");
//				return;
//			}
//
//			// 清除分享动画 并保存时间(活动商品点击分享 因没有动画 不清楚 也不保存当前时间值)
//			if (redShare != null && moneyShare != null && !isSignActiveShop && !false) {
//				redShare.clearAnimation();
//				moneyShare.clearAnimation();
//				SharedPreferencesUtil.saveStringData(context, Pref.SHAREANIM, System.currentTimeMillis() + "");
//			}
//
//			showMyPopwindou(ShopDetailsActivity.this, feedback);
//			break;
//		case R.id.img_cart2:
//		case R.id.img_cart:// 购物车
//			// MobclickAgent.onEvent(context, "toshopcartclick");
//			YunYingTongJi.yunYingTongJi(context, 106);// 商品详情页购物车
//			Intent intent2 = new Intent(this, ShopCartNewNewActivity.class);
//			intent2.putExtra("where", "0");
//			startActivityForResult(intent2, 235);
//			break;
//
//		case R.id.lin_add_like: // 加喜好
//			addLikeShop(null);
//			break;
//		case R.id.img_fenx:// 一键分享
//			// MobclickAgent.onEvent(context, "shopdetailshareclick");
//			double feedback1 = 0;
//			if (null == shop) {
//				ToastUtil.showShortText(context, "操作无效");
//				return;
//			}
//
//			// 清除分享动画 并保存时间(活动商品点击分享 因没有动画 不清楚 也不保存当前时间值)
//			if (redShare != null && moneyShare != null && !isSignActiveShop && !false) {
//				redShare.clearAnimation();
//				moneyShare.clearAnimation();
//				SharedPreferencesUtil.saveStringData(context, Pref.SHAREANIM, System.currentTimeMillis() + "");
//			}
//
//			showMyPopwindou(ShopDetailsActivity.this, feedback1);
//			break;
//		case R.id.rl_retain:
//			// 跳转到购物车结算页面
//			YunYingTongJi.yunYingTongJi(context, 107);
//			Intent intentt = new Intent(ShopDetailsActivity.this, ShopCartNewNewActivity.class);
//			intentt.putExtra("where", "0");
//			startActivity(intentt);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public static String shareStatus;
//
//	/** 得到分享的链接 */
//	public void share(final String code, final Shop shop) {
//
//		shareWaitDialog.show();
//
//		ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
//		isPause = 1;
//		new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//					throws Exception {
//				if (isSignActiveShop) {
//					return ComModel2.getActiveShopLink(params[0], context, "true");
//				} else {
//					return ComModel2.getShopLink(params[0], context, "true");
//				}
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				isPause = 0;
//
//				if (instance == null) {
//					return;
//				}
//				if (null == e) {
//
//					String status = result.get("status");
//
//					// MyLogYiFu.e("统计分享过的用户",result+"");
//					if (result.get("status").equals("1")) {
//						// 没有分享过
//
//						TongjiShareCount.tongjifenxiangCount();
//						TongjiShareCount.tongjifenxiangwho(code);
//
//						// 8 普通商品
//
//						LogYiFu.e("pic", result.get("shop_pic"));
//						String[] picList = result.get("shop_pic").split(",");
//						String four_pic = result.get("four_pic").toString();
//						String link = result.get("link");
//						// download(null, picList, code, result, shop, link,
//						// four_pic);
//						getNineBmBg(null, picList, code, result, shop, link, four_pic);
//					} else if (result.get("status").equals("1050")) {// 表明
//						if (null != shareWaitDialog) {
//							shareWaitDialog.dismiss();
//
//						}
//						Intent intent = new Intent(context, NoShareActivity.class);
//						intent.putExtra("isNomal", true);
//						context.startActivity(intent); // 分享已经超过了
//
//					} else {// Dialog消失
//						if (null != shareWaitDialog) {
//							shareWaitDialog.dismiss();
//						}
//					}
//				} else {
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//				}
//			}
//
//		}.execute(code);
//	}
//
//	/** 下载分享的图片 */
//	private void download(View v, final String shop_code, final HashMap<String, Object> mapInfos, final String link) {
//
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsActivity.this, v, R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				// TODO Auto-generated method stub
//				List<String> shopCodes = (List<String>) mapInfos.get("shopCodes");
//				List<HashMap<String, String>> shopPics = (List<HashMap<String, String>>) mapInfos.get("pics");
//
//				File fileDirec = new File(YConstance.savePicPath);
//				if (!fileDirec.exists()) {
//					fileDirec.mkdir();
//				}
//				File[] listFiles = new File(YConstance.savePicPath).listFiles();
//				if (listFiles.length != 0) {
//					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//					for (File file : listFiles) {
//						file.delete();
//					}
//				}
//				List<String> pics = new ArrayList<String>();
//				for (int j = 0; j < shopCodes.size(); j++) {
//					String shop_code = shopCodes.get(j);
//					HashMap<String, String> map = shopPics.get(j);
//					String pic = map.get(shop_code);
//					String[] picStrs = pic.split(",");
//					for (int i = 0; i < picStrs.length; i++) {
//						if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
//								&& !picStrs[i].contains("real_")) {
//							pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
//						}
//					}
//				}
//
//				int j = pics.size() + 1;
//				if (pics.size() > 8) {
//					j = 9;
//				}
//				int nP = j > 5 ? 4 : j - 1;
//				for (int i = 0; i < j; i++) {
//					if (i == nP) {
//
//						Bitmap bm = QRCreateUtil.createImage(link, 500, 700, (String) mapInfos.get("shop_se_price"),
//								ShopDetailsActivity.this);// 得到二维码图片
//						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//
//						continue;
//					}
//					int m = i > 4 ? i - 1 : i;
//					downloadPic(pics.get(m) + "!450", i);
//					bmBg = downloadPic(mapInfos.get("four_pic") + "!450");
//					LogYiFu.e("热卖分享", mapInfos.get("four_pic") + "!450");
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				if (instance == null) {
//					return;
//				}
//				if (null != context && null != context.getWindow().getDecorView()) {
//
//					UMImage umImage = new UMImage(context, bmBg);
//					ShareUtil.setShareContent(context, umImage, "买了肯定不后悔，数量不多，快来抢购吧~", link);
//
//					myPopupwindow.setUmImage(umImage);
//					myPopupwindow.setLink(link);
//					shareTo(shop_code, link);
//					super.onPostExecute(context, result);
//				}
//
//			}
//
//		}.execute();
//	}
//
//	private String[] picListNine;
//	private String shop_codeNine;
//	private HashMap<String, String> mapInfosNine;
//	private Shop shopNine;
//	private String linkNine;
//	private String four_picNine;
//
//	/**
//	 * 获取九宫图的二维码背景图片
//	 * 
//	 * @param context
//	 */
//	public void getNineBmBg(View v, final String[] picList, final String shop_code,
//			final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic) {
//		new SAsyncTask<Void, Void, String>((FragmentActivity) context, R.string.wait) {
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
//				return ComModel2.getShareBg(context);
//
//			}
//
//			protected void onPostExecute(FragmentActivity context, String result, Exception e) {
//				super.onPostExecute(context, result, e);
//				picListNine = picList;
//				shop_codeNine = shop_code;
//				mapInfosNine = mapInfos;
//				shopNine = shop;
//				linkNine = link;
//				four_picNine = four_pic;
//				getPicture(result);
//
//			}
//		}.execute();
//	}
//
//	private Bitmap bmBg;
//
//	/** 下载分享的图片 */
//	private void download(View v, final String[] picList, final String shop_code,
//			final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic,
//			final Bitmap bmNineBg) {
//		final List<String> pics = new ArrayList<String>();
//		// shareWaitDialog.show();
//		new SAsyncTask<Void, Void, Void>((FragmentActivity) ShopDetailsActivity.this, v, R.string.wait) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				File fileDirec = new File(YConstance.savePicPath);
//				if (!fileDirec.exists()) {
//					fileDirec.mkdir();
//				}
//				File[] listFiles = new File(YConstance.savePicPath).listFiles();
//				if (listFiles.length != 0) {
//					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//					for (File file : listFiles) {
//						file.delete();
//					}
//				}
//				for (int j = 0; j < picList.length; j++) {
//					if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
//							&& !picList[j].contains("real_")) {
//						pics.add(picList[j]);
//					}
//				}
//				int j = pics.size() + 1;
//				if (pics.size() > 8) {
//					j = 9;
//				}
//				int nP = j > 5 ? 4 : j - 1;
//				for (int i = 0; i < j; i++) {
//					if (i == nP) {
//						Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
//						Bitmap bm = QRCreateUtil.drawNewBitmapNine2(ShopDetailsActivity.this, bmQr, bmNineBg);
//
//						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//						continue;
//					}
//					int m = i > 4 ? i - 1 : i;
//					downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(m) + "!450", i);
//					bmBg = downloadPic(
//							shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450");
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				if (instance == null) {
//					if (null != shareWaitDialog) {
//						shareWaitDialog.dismiss();
//					}
//					return;
//				}
//				if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
//					LogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" + result);
//
//					UMImage umImage = new UMImage(context, bmBg);
//					ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//
//					myPopupwindow.setLink(link);
//					myPopupwindow.setUmImage(umImage);
//
//					shareTo(shop_code, link);
//					super.onPostExecute(context, result);
//				}
//
//			}
//
//		}.execute();
//
//	}
//
//	/**
//	 * @param shop_code
//	 * 
//	 */
//	private void shareTo(String shop_code, String link) {
//		qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
//		wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//		// wXinShareIntent.putExtra("Kdescription", "点击链接了解详情"+link);
//		if (null != shareWaitDialog) {
//			shareWaitDialog.dismiss();
//		}
//		switch (myPopupwindow.getShareId()) {
//		case R.id.iv_qq_share:
//			if (myPopupwindow.isSecondShare()) {
//				myPopupwindow.onceShare(qqShareIntent, "qq空间");
//
//				yunYunTongJi(shop_code, 104, 2);
//			}
//			break;
//		case R.id.iv_wxin_share:
//
//			boolean WxCircleFlag = SharedPreferencesUtil.getBooleanData(context, Pref.RECORD_WXCIRCLE, false);
//			if (myPopupwindow.isSecondShare()) {
//				if (WxCircleFlag) {
//					SharedPreferencesUtil.saveBooleanData(context, Pref.RECORD_WXCIRCLE, false);
//					myPopupwindow.onceShare(wXinShareIntent, "微信");// 九宫图
//					yunYunTongJi(shop_code, 1, 2);
//				} else {
//					SharedPreferencesUtil.saveBooleanData(context, Pref.RECORD_WXCIRCLE, true);
//					myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);// 链接
//					yunYunTongJi(shop_code, 1, 2);
//				}
//			}
//
//			break;
//		case R.id.iv_wxin_circle_share:
//			yunYunTongJi(shop_code, 106, 2);
//			myPopupwindow.shareToWxin();
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		instance = null;
//		if (myPopupwindow != null && myPopupwindow.isShowing()) {
//			myPopupwindow.dismiss();
//			return;
//		}
//		if (shop != null) {
//			setResult(-1, new Intent().putExtra("isLike", shop.getLike_id() == -1 ? 0 : 1));
//		}
//		super.onBackPressed();
//		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//		if (aa != null && !aa.isCancelled()) {
//			aa.cancel(true);
//		}
//		if (bb != null && !bb.isCancelled()) {
//			bb.cancel(true);
//		}
//		if (cc != null && !cc.isCancelled()) {
//			cc.cancel(true);
//		}
//	}
//
//	/***
//	 * 添加和删除我喜欢的商品
//	 * 
//	 */
//	private void addLikeShop(View v) {
//		if (shop != null) {
//			// int like_id = shop.getLike_id();
//			int like_id = -1;
//			String str = SharedPreferencesUtil.getStringData(context, "" + YCache.getCacheUser(context).getUser_id(),
//					"");
//			if (str.contains(shop.getShop_code())) {
//				img_xin.setImageResource(R.drawable.hx0);
//				like_id = 1;
//			} else {
//				img_xin.setImageResource(R.drawable.icon_xihuan);
//			}
//
//			if (like_id == -1) {// 添加我的喜欢
//				LogYiFu.e("like_id  == ", " " + like_id);
//				LogYiFu.e("shop_code  == ", " " + shop.getShop_code());
//				AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
//				_alphaAnimation0.setDuration(1500);
//				_alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
//				img_addxin.setImageResource(R.drawable.pic_like_animation);
//				img_addxin.startAnimation(_alphaAnimation0);
//				img_addxin.setVisibility(View.VISIBLE);
//
//				/**
//				 * 透明度从不透明变为0.2透明度
//				 */
//				AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
//				_alphaAnimation.setDuration(1500);
//				_alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
//				img_addxin.startAnimation(_alphaAnimation);
//				shakeAnimation(5);
//				new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {
//
//					@Override
//					protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//
//						return ComModel.addLikeShop(ShopDetailsActivity.this,
//								YCache.getCacheToken(ShopDetailsActivity.this), params[0]);
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//						if (null == e) {
//							img_addxin.setVisibility(View.GONE);
//							if (null != result) {
//								Toast.makeText(ShopDetailsActivity.this, "添加我的喜欢成功", Toast.LENGTH_SHORT).show();
//								String str = SharedPreferencesUtil.getStringData(context,
//										"" + YCache.getCacheUser(context).getUser_id(), "");
//								StringBuffer sb = new StringBuffer(str);
//								sb.append(shop.getShop_code());
//								SharedPreferencesUtil.saveStringData(context,
//										"" + YCache.getCacheUser(context).getUser_id(), sb.toString());
//								LogYiFu.e("hillo", sb.toString());
//								LogYiFu.e("hillo", shop.getShop_code());
//								img_xin.setImageResource(R.drawable.hx0);
//								shop.setLike_id(1);
//								if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 6) {
//									context.getSharedPreferences("EverydayTaskMondayFridayAddLike",
//											Context.MODE_PRIVATE).edit()
//											.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//								}
//							} else {
//								Toast.makeText(ShopDetailsActivity.this, "添加我的喜欢失败", Toast.LENGTH_SHORT).show();
//							}
//						}
//						super.onPostExecute(context, result, e);
//					}
//
//				}.execute(shop.getShop_code());
//			} else {// 删除我的喜欢
//				AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
//				_alphaAnimation0.setDuration(1500);
//				_alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
//				img_addxin.setImageResource(R.drawable.cancel_add_star);
//				img_addxin.startAnimation(_alphaAnimation0);
//				img_addxin.setVisibility(View.VISIBLE);
//
//				/**
//				 * 透明度从不透明变为0.2透明度
//				 */
//				AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
//				_alphaAnimation.setDuration(1500);
//				_alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
//				img_addxin.startAnimation(_alphaAnimation);
//				shakeAnimation(5);
//
//				new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {
//
//					protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//
//						return ComModel.deleteLikeShop(ShopDetailsActivity.this,
//								YCache.getCacheToken(ShopDetailsActivity.this), params[0]);
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//						super.onPostExecute(context, result, e);
//						if (null == e) {
//							img_addxin.setVisibility(View.GONE);
//							if (null != result) {
//								String str = SharedPreferencesUtil.getStringData(context,
//										"" + YCache.getCacheUser(context).getUser_id(), "");
//								LogYiFu.e("hillo", str);
//								LogYiFu.e("hillo", shop.getShop_code());
//								String str2 = str.replace(shop.getShop_code(), "");
//								LogYiFu.e("hillo", str2);
//								SharedPreferencesUtil.saveStringData(context,
//										"" + YCache.getCacheUser(context).getUser_id(), str2);
//								Toast.makeText(ShopDetailsActivity.this, "删除我的喜欢成功", Toast.LENGTH_SHORT).show();
//								img_xin.setImageResource(R.drawable.icon_xihuan);
//								shop.setLike_id(-1);
//
//							} else {
//								Toast.makeText(ShopDetailsActivity.this, "删除我的喜欢失败", Toast.LENGTH_SHORT).show();
//							}
//						}
//					}
//
//				}.execute(shop.getShop_code());
//			}
//		} else {
//			ToastUtil.showShortText(context, "操作无效");
//		}
//	}
//
//	private Animation mShakeAnimation;
//
//	// CycleTimes动画重复的次数
//	public void shakeAnimation(int CycleTimes) {
//		if (null == mShakeAnimation) {
//			mShakeAnimation = new TranslateAnimation(0, 5, 0, 5);
//			mShakeAnimation.setInterpolator(new CycleInterpolator(5));
//			mShakeAnimation.setDuration(3000);
//			mShakeAnimation.setRepeatMode(Animation.REVERSE);// 设置反方向执行
//
//		}
//		img_addxin.startAnimation(mShakeAnimation);
//	}
//
//	private int index = 0;
//	private int mType = 0;
//
//	private View v;
//
//	private boolean isShopTitle = false;
//
//	private String attrDateStr;// 属性时间戳
//	private boolean mGoldVoucher = false;// 是否开启了优惠券变金券
//	private boolean mIsGold = false;// 是否开启了积分变金币
//	private double mVoucherPrice = 0;// 变成金券的面值
//	private HashMap<String, String> mGoldIconMap;
//	private HashMap<String, String> mGoldVoucherMap;
//
//	/**
//	 * 获取是否开启余额翻倍，金币，金券，用于弹框文案的显示
//	 */
//
//	/***
//	 * 查询普通商品详情页
//	 */
//	private void queryShopDetails() {
//		aa = new SAsyncTask<String, Void, HashMap<String, Object>>(this, null, R.string.wait) {
//
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				LoadingDialog.show(ShopDetailsActivity.this);
//			}
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
//					throws Exception {
//				Shop shop;
//				HashMap<String, Object> map;
//				if (YJApplication.instance.isLoginSucess()) {
//
//					map = ComModel.queryShopDetails(ShopDetailsActivity.this,
//							YCache.getCacheToken(ShopDetailsActivity.this), params[0], attrDateStr);
//
//				} else {
//					map = ComModel.queryShopDetails2(ShopDetailsActivity.this, params[0], attrDateStr);
//				}
//				// return shop;
//				return map;
//			}
//
//			@SuppressLint({ "NewApi", "InflateParams", "SimpleDateFormat" })
//			@Override
//			protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> map, Exception e) {
//
//				if (e != null) {// 查询异常
//
//				} else {// 查询商品详情成功，刷新界面
//					mSingleBuy.setOnClickListener(ShopDetailsActivity.this);
//					mTwoBuy.setOnClickListener(ShopDetailsActivity.this);
//					mGroupBuy.setOnClickListener(ShopDetailsActivity.this);
//					rrr.setBackgroundColor(Color.WHITE);
//					tv_shop_car.setVisibility(View.VISIBLE);
//					tv_shop_car_fake.setVisibility(View.GONE);
//					Shop shopd = null;
//					ShareShop shareshop = null;
//					if (map != null) {
//						shopd = (Shop) map.get("shop");
//						shareshop = (ShareShop) map.get("shareshop");
//					}
//					if (shopd != null) {
//						//
//						mSinglePrice.setText("￥" + new DecimalFormat("#0.0").format(shopd.getShop_se_price()));
//						mTwoPrice.setText("￥" + new DecimalFormat("#0.0").format(shopd.getShop_group_price()));
//						mGroupPrice.setText("￥" + new DecimalFormat("#0.0").format(shopd.getShop_group_price()));
//						setEva_count_z = (int) Float.parseFloat(shopd.getEva_count() + ""); // 评价总数
//
//						LogYiFu.e("评价总数", setEva_count_z + "");
//
//						shop = shopd;
//						// titleCheck = shop.getType1();
//						for (int i = 0; i < listTitle.size(); i++) {
//							if ((titleId + "").equals(listTitle.get(i).get("_id"))) {
//								titleCheck = i;
//								break;
//							}
//						}
//						if (titleCheck >= listTitle.size()) {
//							titleCheck = 0;
//						}
//
//						if (YJApplication.instance.isLoginSucess() == true) {
//							String str = SharedPreferencesUtil.getStringData(context,
//									"" + YCache.getCacheUser(context).getUser_id(), "");
//							if (str.contains(shop.getShop_code())) {
//								img_xin.setImageResource(R.drawable.hx0);
//							} else {
//								img_xin.setImageResource(R.drawable.icon_xihuan);
//							}
//						}
//
//						if (first == false) {
//							// headerView.setVisibility(View.GONE);
//							first = true;
//
//							headerView = LayoutInflater.from(ShopDetailsActivity.this).inflate(R.layout.shop_header,
//									null);
//
//							width = ShopDetailsActivity.this.getResources().getDisplayMetrics().widthPixels;
//							headerView.findViewById(R.id.meal_name).setVisibility(View.GONE);
//							// 新服务声明
//							headerView.findViewById(R.id.head_ll_new_decleration).setVisibility(View.VISIBLE);
//							headerView.findViewById(R.id.head_ll_old_decleration).setVisibility(View.GONE);
//							final HashMap<String, String> mapSupply = (HashMap<String, String>) map
//									.get("supplier_label");
//							TextView supply_name = (TextView) headerView.findViewById(R.id.head_tv_supply);// 供应商
//							LinearLayout ll_supply = (LinearLayout) headerView.findViewById(R.id.ll_supply);
//							if (null == mapSupply || "".equals(mapSupply.get("name"))) {// 没有供应商标签(隐藏标签)
//								ll_supply.setVisibility(View.GONE);
//								mSupp_label = "";
//							} else {
//								ll_supply.setVisibility(View.VISIBLE);
//								mSupp_label = "" + mapSupply.get("name") + "制造商出品";
//								supply_name.setText("" + mapSupply.get("name") + "制造商出品");
//								supply_name.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										getSupplyExplainDialog("" + mapSupply.get("name"),
//												"" + mapSupply.get("content1"), "" + mapSupply.get("content2"));
//									}
//								});
//							}
//
//							// 7天倒计时
//							if (isSignActiveShop || isHot) {
//								headerView.findViewById(R.id.ll_active_sold).setVisibility(View.VISIBLE);
//								virtual_sales = shopd.getVirtual_sales();
//								// if(virtual_sales!=0){
//								((TextView) headerView.findViewById(R.id.tv_sold)).setText("已售" + virtual_sales + "件/");
//								// }
//								String randomStr = SharedPreferencesUtil.getStringData(context,
//										Pref.SIGN_ACTIVE_SHOP_LEFT + code, "");
//								if ("".equals(randomStr)) {
//									int random = (int) (Math.random() * (25 - 3) + 3);// 生成3到25之间的随机数
//									SharedPreferencesUtil.saveStringData(context, Pref.SIGN_ACTIVE_SHOP_LEFT + code,
//											"" + random);
//									randomStr = random + "";
//								}
//								((TextView) headerView.findViewById(R.id.tv_sold_left)).setText("仅剩" + randomStr + "件");
//							} else {
//
//							}
//							headerView.findViewById(R.id.one_position).setBackgroundColor(Color.WHITE);
//							adapter = new MyAdapter();
//							String[] imgs = shop.getShop_pic().split(",");
//							StringBuffer sb = new StringBuffer();
//							for (int i = 0; i < imgs.length; i++) {
//								if (imgs[i].contains("reveal_") || imgs[i].contains("real_")
//										|| imgs[i].contains("detail_")) {
//									sb.append(imgs[i] + ",");
//								}
//							}
//
//							images = sb.toString().substring(0, sb.length() - 1).split(",");
//							headerView.findViewById(R.id.img_header).getLayoutParams().height = width * 9 / 6;
//							heights = width * 9 / 6;
//							String def_pic = shop.getDef_pic();
//							if (!TextUtils.isEmpty(def_pic)) {
//								SetImageLoader.initImageLoader(null,
//										(ScaleImageView) headerView.findViewById(R.id.img_header),
//										shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/" + def_pic,
//										"!450");
//								headerView.findViewById(R.id.img_header).setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										Intent intent = new Intent(ShopDetailsActivity.this, ShopImageActivity.class);
//										intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/"
//												+ shop.getShop_code() + "/" + shop.getDef_pic());
//										intent.putExtra("shop", shop);
//										intent.putExtra("isMembers", false);
//
//										intent.putExtra("ShopCart", shopCart);
//										intent.putExtra("supp_label", mSupp_label);
//										intent.putExtra("number_sold", number_sold);
//										intent.putExtra("isSignActiveShop", isSignActiveShop);
//
//										startActivityForResult(intent, 235);
//									}
//								});
//							}
//							((TextView) headerView.findViewById(R.id.tv_clothes_name))
//									.setText(TextUtils.isEmpty(shop.getShop_name()) ? null : shop.getShop_name());
//							String num_save = SharedPreferencesUtil.getStringData(context, "num", "1");
//							num = Integer.valueOf(num_save).intValue();
//							String dikou = shop.getKickback() + "";
//							double valueOf = Double.valueOf(dikou);
//							tv_fenxiang.setText(
//									"分享赚" + new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1) + "元");
//							
//
//							String xiang_mongey = new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
//							f_xiang_mongey = Double.valueOf(xiang_mongey);
//
//							int a = (int) shop.getKickback();
//
//							if (isSignActiveShop) {
//								double shopSePrice = shop.getShop_se_price();
//								double shopPrice = shop.getShop_price();
//
//								((TextView) headerView.findViewById(R.id.tv_price))
//										.setText("¥" + new DecimalFormat("#0.0").format(shopSePrice));
//
//								String shop_price = "淘宝价¥" + new java.text.DecimalFormat("#0.0").format(shopPrice);
//								if (!TextUtils.isEmpty(shop_price)) {
//									ToastUtil.addStrikeSpan((TextView) headerView.findViewById(R.id.tv_sjprice),
//											shop_price);
//								}
//								double discount = (shopSePrice / shopPrice) * 10;
//								double save = shopPrice - shopSePrice;
//
//								headerView.findViewById(R.id.tv_active_discount).setVisibility(View.VISIBLE);
//								headerView.findViewById(R.id.tv_active_save).setVisibility(View.VISIBLE);
//								((TextView) headerView.findViewById(R.id.tv_active_discount))
//										.setText(new DecimalFormat("#0.0").format(discount) + "折");
//								((TextView) headerView.findViewById(R.id.tv_active_save))
//										.setText("立省" + new DecimalFormat("#0.0").format(save) + "元");
//							} else {
//								((TextView) headerView.findViewById(R.id.tv_price))
//										.setText("¥" + new DecimalFormat("#0.0").format(shop.getShop_se_price()));
//
//								String shop_price = "¥"
//										+ new java.text.DecimalFormat("#0.0").format(shop.getShop_price());
//								if (!TextUtils.isEmpty(shop_price)) {
//									ToastUtil.addStrikeSpan((TextView) headerView.findViewById(R.id.tv_sjprice),
//											shop_price);
//								}
//							}
//
//							mListView.addHeaderView(headerView);
//							mListView.setAdapter(adapter);
//
//							mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//								private int myposition;
//								private ImageButton aa2;
//
//								@Override
//								public void onScrollStateChanged(AbsListView view, int arg1) {
//
//									View childAt = view.getChildAt(0);
//									switch (arg1) {
//									case SCROLL_STATE_TOUCH_SCROLL:// 滚动之前
//										if (isShopTitle) {
//											break;
//										}
//										if (childAt == null) {
//											myposition = 0;
//										} else {
//											myposition = -childAt.getTop()
//													+ view.getFirstVisiblePosition() * childAt.getHeight();
//										}
//										break;
//
//									case SCROLL_STATE_FLING: // 滚动
//										if (isShopTitle) {
//											break;
//										}
//										int newPosition = 0;
//										if (childAt == null) {
//											newPosition = 0;
//										} else {
//											newPosition = -childAt.getTop()
//													+ view.getFirstVisiblePosition() * childAt.getHeight();
//										}
//
//										if (newPosition > myposition) { // 向上滑动
//											if (ll_bottem.getVisibility() == View.VISIBLE && isAnim == false) {
//												ll_bottem.clearAnimation();
//												ll_bottem.startAnimation(animationGone);
//											}
//										} else if (newPosition < myposition) {
//											if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//												ll_bottem.clearAnimation();
//												// rlBottom.startAnimation(animationShow);
//												ll_bottem.startAnimation(animationShow);
//
//											}
//										}
//										break;
//									case SCROLL_STATE_IDLE:
//										if (view.getLastVisiblePosition() == view.getCount() - 1) {
//											if (check == 0) {
//												if (isInit == false) {
//													index++;
//													mType = 2;
//													initData(titleCheck, index + "");
//												}
//
//											} else if (check == 2 && !false) {
//
//												if (isCheck == false) {
//													page++;
//													querySelCommentByShop();
//												}
//											}
//										}
//
//										if (isShopTitle) {
//											break;
//										}
//
//										if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//
//											ll_bottem.clearAnimation();
//											// rlBottom.startAnimation(animationShow);
//											ll_bottem.startAnimation(animationShow);
//
//											// pingyi();
//										}
//
//										break;
//									}
//								}
//
//								@Override
//								public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//									int perHeight = heights / 100;
//									float currentY = 0;
//									int viewTop = -1;
//									aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);
//
//									/* 滚动title渐变的效果 */
//									if (arg1 == 0) {// 当前第一位显示为1
//										View childAt = arg0.getChildAt(0);// 这个是headerView
//										if (childAt != null) {
//											currentY = childAt.getTop();
//											viewTop = childAt.getMeasuredHeight() + childAt.getTop();
//										}
//
//									} else if (arg1 > 0) {
//										currentY = heights;
//										viewTop = rlTop.getHeight();
//									}
//
//									if (currentY == 0) {
//										rlTop.setBackgroundResource(R.drawable.zhezhao2x);
//										aa2.setBackgroundResource(R.drawable.icon_fanhui);
//										mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
//										lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_white);
//
//										rlTop.getBackground().setAlpha(255);
//										// mTopView.setVisibility(View.GONE);
//									}
//									if (Math.abs(currentY) > 0 && Math.abs(currentY) < heights / 2) {
//										aa2.setBackgroundResource(R.drawable.icon_fanhui);
//										mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
//										lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_white);
//
//										int i = (int) Math.abs(currentY / heights * 255);
//
//										if (Math.abs(currentY) == 0) {
//											i = 1;
//										}
//										aa2.getBackground().setAlpha(255 - i);
//										mShuaixuanNew.getBackground().setAlpha(255 - i);
//										lin_contact.getBackground().setAlpha(255 - i);
//
//									}
//
//									if (Math.abs(currentY) >= heights / 2 && Math.abs(currentY) < heights) {
//										aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
//										mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
//										lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
//
//										int i = (int) Math.abs(currentY / heights * 255);
//
//										if (Math.abs(currentY) == 0) {
//											i = 1;
//										}
//										aa2.getBackground().setAlpha(i);
//										mShuaixuanNew.getBackground().setAlpha(i);
//										lin_contact.getBackground().setAlpha(i);
//										// img_cart_top.getBackground()
//										// .setAlpha(255 * i / 100);
//										// img_fenx_top.getBackground()
//										// .setAlpha(255 * i / 100);
//									}
//
//									if (Math.abs(currentY) <= heights && Math.abs(currentY) > 0) {
//										rlTop.setBackgroundColor(getResources().getColor(R.color.white));
//										int i = (int) Math.abs(currentY / heights * 255);
//
//										if (Math.abs(currentY) == 0) {
//											i = 1;
//										}
//										rlTop.getBackground().setAlpha(i);
//									}
//
//									if (Math.abs(currentY) >= heights) {
//										rlTop.getBackground().setAlpha(255);
//										aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
//										mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
//										lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
//
//									}
//
//								}
//							});
//
//							findViewById(R.id.search).setOnClickListener(new OnClickListener() {
//
//								@Override
//								public void onClick(View arg0) {
//									// toggle();
//								}
//							});
//							findViewById(R.id.shaixuan).setOnClickListener(new OnClickListener() {
//
//								@Override
//								public void onClick(View arg0) {
//
//								}
//							});
//
//						}
//						////
//						////
//						// 购物车倒计时
//						// long c_time = shop.getC_time();
//
//						String time_c = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0");
//						long c_time = Long.valueOf(time_c).longValue();
//
//						// long s_time_fuwuqi = shop.getS_time();
//						long s_time_fuwuqi = System.currentTimeMillis();
//
//						if (c_time - s_time_fuwuqi > 0) {// 正数代表加入了购物车 显示倒计时
//
//						} else {// 负数代表没有加入购物车
//
//						}
//						if (YJApplication.instance.isLoginSucess()) {
//
//							ShopCartDao dao = new ShopCartDao(context);
//							// int count = dao.queryCartCount(context);
//							int count = 0;
//
//							count = dao.queryCartCommonCount(context);
//
//							if (/* shop.getCart_count() */count > 0) {
//								count = count > 99 ? 99 : count;
//								tv_cart_count.setText(/* shop.getCart_count() */count + "");// 设置购物车数量
//								tv_cart_count2
//										.setText(/* shop.getCart_count() */count + "");// 设置购物车数量
//								tv_cart_count.setVisibility(View.VISIBLE);
//								tv_cart_count2.setVisibility(View.VISIBLE);
//
//							} else {
//								tv_cart_count.setText(0 + "");
//								tv_cart_count.setVisibility(View.GONE);
//								tv_cart_count2.setText(0 + "");
//								tv_cart_count2.setVisibility(View.GONE);
//							}
//						}
//
//					}
//					// 11111111111111
//
//					if (shareshop == null || isSignActiveShop) {
//					} else {
//						tv_shop_car.setVisibility(View.VISIBLE);
//						tv_shop_car_fake.setVisibility(View.GONE);
//
//						int count = shareshop.getCount();
//						List<HashMap<String, Object>> user_list = shareshop.getUser_list();
//
//						Double random = shop.getRandom();
//
//						String format = new DecimalFormat("#0").format(count * random);
//						int ii = Integer.valueOf(format).intValue();
//						Double get_money = ii * f_xiang_mongey;
//						String last_money = new DecimalFormat("#0.0").format(get_money);
//
//						// for (int i = 0; i < user_list.size(); i++) {
//						for (int i = user_list.size() - 1; i >= 0; i--) {
//							HashMap<String, Object> hashMap = user_list.get(i);
//							String pic = hashMap.get("pic").toString();
//
//							if (!pic.contains("http")) {
//								pic = YUrl.imgurl + pic;
//							}
//
//						}
//					}
//
//				}
//
//			};
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			};
//		};
//		aa.execute(code);
//
//	}
//
//	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
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
//
//	private void createSharePic(final String link, final String picPath, final String price) {
//		new SAsyncTask<Void, Void, Void>(this, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
//				Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
//				Bitmap bmBg = downloadPic(picPath);
//
//				Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price, "");
//
//				QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//				return super.doInBackground(context, params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
//					shareMeal(file, v);
//				}
//			}
//
//		}.execute();
//	}
//	////////////////////////
//
//	private byte[] picByte;
//	private Bitmap nineBitmap;
//
//	public void getPicture(final String picPath) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				if (TextUtils.isEmpty(picPath)) {
//					Message message = new Message();
//					message.what = 99;
//					handle.sendMessage(message);
//				} else {
//					try {
//						URL url = new URL(YUrl.imgurl + picPath);
//
//						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//						conn.setRequestMethod("GET");
//						conn.setReadTimeout(10000);
//
//						if (conn.getResponseCode() == 200) {
//							InputStream fis = conn.getInputStream();
//							ByteArrayOutputStream bos = new ByteArrayOutputStream();
//							byte[] bytes = new byte[1024];
//							int length = -1;
//							while ((length = fis.read(bytes)) != -1) {
//								bos.write(bytes, 0, length);
//							}
//							picByte = bos.toByteArray();
//							bos.close();
//							fis.close();
//
//							Message message = new Message();
//							message.what = 99;
//							handle.sendMessage(message);
//						}
//
//					} catch (IOException e) {
//						e.printStackTrace();
//						if (null != shareWaitDialog) {
//							shareWaitDialog.dismiss();
//						}
//					}
//				}
//			}
//		}).start();
//	}
//
//	Handler handle = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if (msg.what == 99) {
//				if (picByte != null) {
//					nineBitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
//					download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine,
//							nineBitmap);
//				} else {
//					download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine, null);
//				}
//			}
//		}
//	};
//
//	/////////////////////
//	private Bitmap downloadPic(String picPath) {
//		try {
//			URL url = new URL(YUrl.imgurl + picPath);
//			// 打开连接
//			URLConnection con = url.openConnection();
//			// 获得文件的长度
//			int contentLength = con.getContentLength();
//			// System.out.println("长度 :" + contentLength);
//			// 输入流
//			InputStream is = con.getInputStream();
//			// 1K的数据缓冲
//			byte[] bs = new byte[8192];
//			// 读取到的数据长度
//			int len;
//			BitmapDrawable bmpDraw = new BitmapDrawable(is);
//
//			// 完毕，关闭所有链接
//			is.close();
//			return bmpDraw.getBitmap();
//		} catch (Exception e) {
//			LogYiFu.e("TAG", "下载失败");
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private void shareMeal(File file, final View v) {
//
//		if (file == null) {
//			Toast.makeText(this, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
//			return;
//		}
//
//		UMImage umImage = new UMImage(this, file);
//		ShareUtil.configPlatforms(this);
//		ShareUtil.shareShop(this, umImage);
//
//		UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE).postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
//				new SnsPostListener() {
//
//					@Override
//					public void onStart() {
//
//					}
//
//					@Override
//					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//					}
//				});
//
//	}
//
//	/***
//	 * 查找商品属性
//	 */
//	public void queryShopQueryAttr(final int i) {
//		clickFlag = false;
//		if (shop != null) {
//			List<StockType> list = shop.getList_stock_type();
//			if (list != null && list.size() > 0) {
//				showPopWindow(i);
//			} else {
//				new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
//					@Override
//					protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
//						return ComModel.queryShopQueryAttr(ShopDetailsActivity.this, shop, params[0]);
//					}
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {
//
//						if (e != null) {// 查询异常
//							Toast.makeText(ShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
//						} else {// 查询商品详情成功，刷新界面
//							if (shop != null) {
//								ShopDetailsActivity.this.shop = shop;
//								showPopWindow(i);// 商品属性选择
//							}
//						}
//
//					};
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					};
//				}.execute("false");
//			}
//
//		} else {
//			ToastUtil.showShortText(context, "无效操作");
//		}
//	}
//
//	private GoodsEntity entity;
//	int p_id_dao;
//
//	/****
//	 * 弹出底部对话框
//	 * 
//	 * @param i
//	 */
//	private void showPopWindow(int i) {
//		if (shop != null && !this.isFinishing()) {
//			final ShopDetailsDialog dlg;
//			if (groupFlag == 1 || groupFlag == 2) {// 代表是组团购买的
//				dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, options,
//						animateFirstListener, i, true, "-1", "-1", "1");// 增加最后一个boolean值是为了显示属性时的价格显示为组团价格；true代表组团购买
//			} else {
//				dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, options,
//						animateFirstListener, i, false, "-1", "-1", "1");
//			}
//			Window window = dlg.getWindow();
//			window.setGravity(Gravity.BOTTOM);
//			window.setWindowAnimations(R.style.dlg_down_to_top);
//			dlg.show();
//			dlg.setOnDismissListener(new OnDismissListener() {
//
//				@Override
//				public void onDismiss(DialogInterface arg0) {
//					// TODO Auto-generated method stub
//					clickFlag = true;
//				}
//			});
//			dlg.callBackShopCart = new ShopDetailsDialog.OnCallBackShopCart() {
//
//				@Override
//				public void callBackChoose(int type, String size, String color, double price, int shop_num,
//						int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
//						View v) {
//					dlg.dismiss();
//					// clickFlag = true;
//					entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
//							price, kickback, original_price);
//
//					if (type == 1) {// 购买 活动商品直接购买
//
//						Intent intent = new Intent(ShopDetailsActivity.this, SubmitMultiShopActivty.class);// 购买
//						Bundle bundle = new Bundle();
//						List<ShopCart> listGoods = new ArrayList<ShopCart>();
//						ShopCart shopCart = new ShopCart();
//						shopCart.setShop_code(shop.getShop_code());
//						shopCart.setShop_num(shop_num);
//						shopCart.setSize(size);
//						shopCart.setColor(color);
//						shopCart.setShop_price(shop.getShop_price());
//						if (groupFlag == 1 || groupFlag == 2) {
//							shopCart.setShop_se_price(shop.getShop_group_price());
//						} else {
//							shopCart.setShop_se_price(shop.getShop_se_price());
//						}
//						shopCart.setOriginal_price(Double.valueOf(original_price));
//						shopCart.setDef_pic(pic);
//						shopCart.setStock_type_id(stock_type_id);
//						shopCart.setSupp_id(shop.getSupp_id());
//						shopCart.setShop_name(shop.getShop_name());
//						shopCart.setCore("0");
//						shopCart.setKickback(0.0);
//						shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
//						shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
//						listGoods.add(shopCart);
//
//						bundle.putSerializable("listGoods", (Serializable) listGoods);
//						bundle.putBoolean("isSignActiveShop", isSignActiveShop);
//						bundle.putBoolean("mIsTwoGroup", mIsTwoGroup);
//						bundle.putString("rollCode", "" + rollCode);
//						intent.putExtras(bundle);
//						startActivity(intent);
//					} else {// 加入购物车
//
//						// if(true){
//						// if(loginDialog==null){
//						// loginDialog=new ToLoginDialog(context);
//						// }
//						// loginDialog.setRequestCode(233);//加入购物车
//						// loginDialog.show();
//						// return;
//						// }
//
//						if (TextUtils.isEmpty(pic)) {
//							pic = shop.getDef_pic();
//						}
//						// original_price = shop.getCore();
//						// System.out.println("***********************33333333"
//						// + original_price);
//						joinShopCart(size, color, shop_num, stock_type_id, pic, price, supp_id, kickback,
//								original_price, v);
//						// clickFlag = true;
//					}
//				}
//			};
//		}
//	}
//
//	/***
//	 * 加入购物车
//	 * 
//	 * @param size
//	 * @param color
//	 * @param shop_num
//	 * @param v
//	 */
//	// private Drawable d;
//	int id_dao;
//
//	private void joinShopCart(final String size, final String color, final int shop_num, final int stock_type_id,
//			final String pic, final double realPrice, final int supplyId, final double kickback,
//			final int original_price, final View v) {
//		final ShopCartDao dao = new ShopCartDao(context);
//		addAnimLayout();// 添加动画布局 小圆点
//		List<ShopCart> list = dao.findAll();
//		// List<ShopCart> list_invalid = dao.findAll_invalid();
//		boolean id_flag = false;
//		// boolean id_flag_invalid = false;// 失效列表有
//		int shop_num_old = 0;
//		// int shop_num_old_invalid = 0;
//		if (list != null) {
//			for (int i = 0; i < list.size(); i++) {
//				if (("" + stock_type_id).equals("" + list.get(i).getStock_type_id())) {
//					id_flag = true;
//					id_dao = list.get(i).getId();
//					shop_num_old = list.get(i).getShop_num();
//					break;
//				}
//			}
//		}
//
//		if (!id_flag) {// 需要获取id
//			if (shop_num > 2) {
//				// clickFlag = true;
//				rootView.removeView(pointRoot);
//				ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
//			} else {
//				new SAsyncTask<String, Void, HashMap<String, Object>>(this, v, R.string.wait) {
//
//					@Override
//					protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
//							throws Exception {
//
//						return ComModel2.getShopCartData(ShopDetailsActivity.this, 1);
//
//					}
//
//					@Override
//					protected boolean isHandleException() {
//						return true;
//					}
//
//					@Override
//					protected void onPreExecute() {
//						super.onPreExecute();
//					};
//
//					@Override
//					protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
//							Exception e) {
//
//						if (null == e) {
//							int id = Integer.parseInt((String) result.get("id"));
//							addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback,
//									original_price, v, id);
//							UserInfo user = YCache.getCacheUser(ShopDetailsActivity.this);
//							Store store = YCache.getCacheStore(ShopDetailsActivity.this);
//							boolean hh = dao.add(shop.getShop_code(), size, color,
//									Integer.parseInt(String.valueOf(shop_num)),
//									Integer.parseInt(String.valueOf(stock_type_id)), pic, user.getUser_id(),
//									shop.getShop_name(), store.getS_code(), "" + shop.getShop_price(),
//									"" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" + original_price, 0,
//									null, null, null, null, null, id, null, 0, mSupp_label);
//							// 普通商品过期时间
//							// SharedPreferencesUtil.saveStringData(context,
//							// Pref.SHOPCART_COMMON_TIME,
//							// new Date().getTime() + 30 * 1000 * 60 + "");
//
//							// setAnim();
//
//						} else {
//							// clickFlag = true;
//							rootView.removeView(pointRoot);
//						}
//
//						super.onPostExecute(context, result, e);
//					}
//
//				}.execute();
//			}
//
//		} else {// 不需要获取购物车id
//			if (id_flag) {// 只有有效的
//				if (shop_num + shop_num_old > 2) {
//					// clickFlag = true;
//					rootView.removeView(pointRoot);
//					ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
//				} else {
//					addCart(size, color, shop_num, stock_type_id, pic, realPrice, supplyId, kickback, original_price, v,
//							id_dao);
//					dao.modify("" + stock_type_id, shop_num + shop_num_old);
//				}
//			}
//			// else if (!id_flag && id_flag_invalid) {// 只有失效的
//			// if (shop_num + shop_num_old_invalid > 2) {
//			// // clickFlag = true;
//			// rootView.removeView(pointRoot);
//			// ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
//			// } else {
//			// addCart(size, color, shop_num, stock_type_id, pic, realPrice,
//			// supplyId, kickback, original_price, v,
//			// id_dao);
//			// UserInfo user = YCache.getCacheUser(ShopDetailsActivity.this);
//			// Store store = YCache.getCacheStore(ShopDetailsActivity.this);
//			// boolean hh = dao.add(shop.getShop_code(), size, color,
//			// Integer.parseInt(String.valueOf(shop_num)),
//			// Integer.parseInt(String.valueOf(stock_type_id)), pic,
//			// user.getUser_id(),
//			// shop.getShop_name(), store.getS_code(), "" +
//			// shop.getShop_price(),
//			// "" + shop.getShop_se_price(), supplyId + "", "" + kickback, "" +
//			// original_price, 0, null,
//			// null, null, null, null, id_dao, null, 0);
//			// // 普通商品过期时间
//			// SharedPreferencesUtil.saveStringData(context,
//			// Pref.SHOPCART_COMMON_TIME,
//			// new Date().getTime() + 30 * 1000 * 60 + "");
//			// }
//			// } else if (id_flag && id_flag_invalid) {
//			// // clickFlag = true;
//			// rootView.removeView(pointRoot);
//			// ToastUtil.showShortText(context, "抱歉，数量有限，最多只能购买2件噢！");
//			// }
//		}
//	}
//
//	/**
//	 * @param size
//	 * @param color
//	 * @param shop_num
//	 * @param stock_type_id
//	 * @param pic
//	 * @param realPrice
//	 * @param supplyId
//	 * @param kickback
//	 * @param original_price
//	 * @param v
//	 */
//	private void addCart(final String size, final String color, final int shop_num, final int stock_type_id,
//			final String pic, final double realPrice, final int supplyId, final double kickback,
//			final int original_price, View v, final int id) {
//		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//				UserInfo user = YCache.getCacheUser(ShopDetailsActivity.this);
//				Store store = YCache.getCacheStore(ShopDetailsActivity.this);
//				// try {
//				// URL url = new URL(YUrl.imgurl +
//				// shop.getShop_code().substring(1, 4) + "/" +
//				// shop.getShop_code()
//				// + "/" + shop.getDef_pic() + "!180");
//				// URLConnection conn = url.openConnection();
//				// InputStream is = conn.getInputStream();
//				// d = BitmapDrawable.createFromStream(is, null);
//				// } catch (Exception e) {
//				// }
//
//				return ComModel.joinShopCart(ShopDetailsActivity.this, params[0], params[1], params[2], params[3],
//						params[4], "" + user.getUser_id(), YCache.getCacheToken(ShopDetailsActivity.this), realPrice,
//						shop, store.getS_code(), supplyId + "", kickback, original_price, id, mSupp_label);
//
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
//
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//
//				if (null == e && null != result && result.getStatus().equals("1")) {
//					int needValue = 1;
//					try {
//						needValue = Integer.parseInt(SignFragment.doValueShopCart);
//					} catch (Exception e2) {
//					}
//					int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
//					try {
//						signNumber = SignFragment.doNumShopCart;
//					} catch (Exception e2) {
//					}
//					double jiangliValue = 5.0;
//					try {
//						jiangliValue = Double.parseDouble(SignFragment.jiangliValueShopCart);
//					} catch (Exception e2) {
//					}
//
//					String mNotice = "元";
//					int jiangliType = 5;
//					try {
//						jiangliType = SignFragment.jiangliIDShopCart;
//					} catch (Exception e2) {
//					}
//					switch (jiangliType) {
//					case 3:
//						mNotice = "元优惠券";
//						break;
//					case 4:
//						mNotice = "积分";
//						break;
//					case 5:
//						mNotice = "元";
//						break;
//
//					default:
//						break;
//					}
//					String qiandao_time = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this,
//							"qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "");
//					String qiandao_num = SharedPreferencesUtil.getStringData(ShopDetailsActivity.this,
//							"qiandao_num" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "0");
//					// ShopCartDao dao=new
//					// ShopCartDao(ShopDetailsActivity.this);
//					int now_num = Integer.parseInt(qiandao_num) + shop_num;
//					Date date = new Date();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					String now_time = sdf.format(date);
//					if (qiandao_time.equals(now_time)) {
//						SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//								"qiandao_num" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(),
//								"" + now_num);
//						if (now_num >= needValue) {
//							sign_shopcart(true, 0);
//							SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//									"qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "-1");
//						} else {
//							ToastUtil.showShortText(context, "再加" + (needValue - now_num) + "件商品可完成任务喔~");
//
//						}
//					}
//					SharedPreferencesUtil.saveBooleanData(context, "undo_view4", true);// 加入购物车时候
//																						// 主界面的购物车数量设置为重新显示
//					int cartCount = 0;
//					if (result.getIsCart() == 0) {
//						cartCount = shop.getCart_count() + 1;
//					} else {
//						cartCount = shop.getCart_count();
//					}
//					shop.setCart_count(cartCount);
//					setAnim();// 加入购物车抛物线动画
//
//					first = true;
//
//				} else {
//					// clickFlag = true;
//					rootView.removeView(pointRoot);
//					ShopCartDao dao = new ShopCartDao(context);
//					List<ShopCart> list = dao.findAll();
//					int shop_num_old = 0;
//					if (list != null) {
//						for (int i = 0; i < list.size(); i++) {
//							if (stock_type_id == list.get(i).getStock_type_id()) {
//								shop_num_old = list.get(i).getShop_num();
//								break;
//							}
//						}
//					}
//					dao.modify("" + stock_type_id, shop_num_old - shop_num);
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute(size, color, String.valueOf(shop_num), String.valueOf(stock_type_id), pic);
//	}
//
//	boolean flag0 = true, flag1 = true;
//
//	private float downY;
//
//	class TimeCount extends CountDownTimer {
//		private TextView tv = null;
//
//		public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
//			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
//			this.tv = tv;
//
//		}
//
//		@Override
//		public void onFinish() {// 计时完毕时触发
//			try {
//				tv.setText("打折结束");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {// 计时过程显示
//			try {
//				long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//				long nh = 1000 * 60 * 60;// 一小时的毫秒数
//				long nm = 1000 * 60;// 一分钟的毫秒数
//				long ns = 1000;// 一秒钟的毫秒数
//				long diff = millisUntilFinished;
//				long day = diff / nd;// 计算差多少天
//				long hour = diff % nd / nh;// 计算差多少小时
//				long min = diff % nd % nh / nm;// 计算差多少分钟
//				long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
//
//				tv.setText("距打折时间还有" + day + "天    " + hour + "小时" + min + "分" + sec + "秒");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private int titleCheck = 0;// 当前导航栏选择
//
//	@Override
//	public void myOnClick(View v) {
//		mType = 1;
//		index = 1;
//		titleCheck = v.getId();
//		initData(v.getId(), index + "");
//
//		if (mListView.getFirstVisiblePosition() > images.length) {
//			mListView.setSelection(images.length + 1);
//		}
//
//	}
//
//	private boolean isInit = false;
//
//	private void initData(final int position, final String index) {
//		isInit = true;
//		new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (null == e) {
//					if (dataList == null) {
//						dataList = new LinkedList<HashMap<String, Object>>();
//					}
//					if (mType == 1) {
//						dataList.clear();
//					}
//					if (mType == 2 && (result == null || result.isEmpty())) {
//						Toast.makeText(ShopDetailsActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
//						// myListView.onRefreshComplete();
//						return;
//					}
//					dataList.addAll(result);
//					adapter.notifyDataSetChanged();
//					isInit = false;
//				}
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getProductListUnLogin(context, index, listTitle.get(position).get("_id"),
//						String.valueOf(1), listTitle.isEmpty() ? null : listTitle.get(position).get("sort_name"),
//						Integer.parseInt("30"), false);
//			}
//
//		}.execute();
//	}
//
//	@Override
//	public void onCheck(int index) {
//
//		if (check == index) {
//			return;
//		}
//		check = index;
//		adapter.notifyDataSetChanged();
//		if (check == 2) {
//
//			if (listShopComments == null && isCheck == false) {
//				querySelCommentByShop();
//			}
//
//		}
//
//		if (mListView.getFirstVisiblePosition() > 0) {
//			mListView.setSelection(1);
//		}
//
//	}
//
//	private int rows = 10, page = 1;
//	private boolean isCheck = false;
//
//	private void querySelCommentByShop() {
//
//		rows = 10;
//		isCheck = true;
//		new SAsyncTask<Void, Void, List<ShopComment>>((FragmentActivity) context, null, R.string.wait) {
//
//			@Override
//			protected void onPreExecute() {
//				// TODO Auto-generated method stub
//				super.onPreExecute();
//				LoadingDialog.show(ShopDetailsActivity.this);
//			}
//
//			@Override
//			protected List<ShopComment> doInBackground(FragmentActivity context, Void... params) throws Exception {
//				List<ShopComment> list = ComModel.queryShopEvaluate((FragmentActivity) context, "" + page, "" + rows,
//						"" + code);
//				return list;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, List<ShopComment> list, Exception e) {
//				isCheck = false;
//				if (e != null) {// 查询异常
//					Toast.makeText(context, "连接超时，请重试", Toast.LENGTH_LONG).show();
//					page--;
//				} else {// 查询商品详情成功，刷新界面
//					rrr.setBackgroundColor(Color.WHITE);
//					if (list != null && list.size() > 0) {
//						if (listShopComments == null) {
//							listShopComments = new ArrayList<ShopComment>();
//						}
//						listShopComments.addAll(list);
//					} else {
//						// Toast.makeText(ShopDetailsActivity.this, "已经到底了",
//						// Toast.LENGTH_SHORT).show();
//						isCheck = true;
//					}
//					adapter.notifyDataSetChanged();
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
//	private String number_sold;// 库存
//	private RelativeLayout rl_hava_twenty;
//	private boolean shopCart;
//	private boolean isforcelook;
//	private boolean isforcelookMatch;
//	private boolean isSignActiveShop;// 签到活动商品（仅判断是否是活动商品）
//	private boolean isSignActiveShopScan;// 签到活动商品 并且是浏览任务
//	private SimpleDateFormat df;
//	private int virtual_sales;
//
//	private TextView tv_shop_car_fake;
//	private TextView tv_buy_now;// 立即购买
//
//	// px转成dp
//	public int Px2Dp(Context context, float px) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (px / scale + 0.5f);
//	}
//
//	private void ThreeSecond() {
//		rl_hava_twenty.setVisibility(View.VISIBLE);
//		final Handler handler = new Handler() {
//			public void handleMessage(Message msg) {
//				if (msg.what == 1) {
//					rl_hava_twenty.setVisibility(View.GONE);
//					// rl_retain.getBackground().setAlpha(0);
//				}
//			};
//		};
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(60 * 1000);
//					// rl_retain.setVisibility(View.GONE);
//					Message message = new Message();
//					message.what = 1;
//					handler.sendMessage(message);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		}).start();
//	}
//
//	private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
//		new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				return ComModel2.getOperator(context, shop_code, type, tab_type);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//				// TODO Auto-generated method stub
//				super.onPostExecute(context, result, e);
//
//			}
//
//		}.execute();
//	}
//
//	// 加入购物车动画
//	private RelativeLayout img_cart_cart;
//
//	private void setAnim() {
//
//		// 获取起点坐标
//		int[] location1 = new int[2];
//		mCartPoint.getLocationInWindow(location1);
//		int x1 = location1[0];
//		int y1 = location1[1];
//		// 获取终点坐标，最近拍摄的坐标
//		int[] location2 = new int[2];
//		tv_cart_count.getLocationInWindow(location2);
//		int x2 = location2[0];
//		int y2 = location2[1];
//		// int[] location3 = new int[2];
//		int x3 = (x1 + x2) / 2;
//		int y3 = y2 - 90;
//		// 两个位移动画
//		TranslateAnimation translateAnimationX = new TranslateAnimation(0, x3 - x1, 0, 0); // 横向动画
//		final TranslateAnimation translateAnimationX2 = new TranslateAnimation(x3 - x1, x2 - x1, 0, 0); // 横向动画
//		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, y3 - y1); // 竖向动画
//		final TranslateAnimation translateAnimationY2 = new TranslateAnimation(0, 0, y3 - y1, y2 - y1); // 竖向动画
//		translateAnimationX.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
//		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
//		translateAnimationX2.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
//		translateAnimationX2.setRepeatCount(0);// 动画重复执行的次数
//		translateAnimationY.setInterpolator(new AccelerateDecelerateInterpolator());
//		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
//		// translateAnimationY.setRepeatMode(Animation.REVERSE);
//		translateAnimationY2.setInterpolator(new AccelerateInterpolator()); // 竖向动画设为开始结尾处加速，中间迅速
//		translateAnimationY2.setRepeatCount(0);// 动画重复执行的次数
//		// 组合动画
//		final AnimationSet anim = new AnimationSet(false);
//		final AnimationSet anim2 = new AnimationSet(false);
//		anim.setFillAfter(false); // 动画结束不停留在最后一帧
//		anim2.setFillAfter(false); // 动画结束不停留在最后一帧
//		anim.addAnimation(translateAnimationX);
//		anim.addAnimation(translateAnimationY);
//
//		anim2.addAnimation(translateAnimationX2);
//		anim2.addAnimation(translateAnimationY2);
//		anim2.setDuration(400);// 动画的执行时间
//		anim2.setStartOffset(0);
//
//		anim.setAnimationListener(new Animation.AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				mCartPoint.startAnimation(anim2);
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//		});
//		anim2.setAnimationListener(new Animation.AnimationListener() { // 抛物线动画结束后
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				pointRoot.removeAllViews();
//				mCartPoint.setVisibility(View.INVISIBLE);
//				tv_cart_count.setVisibility(View.VISIBLE);// 抛物线动画结束后 即可显示数量小圆点
//				setAnim2();
//			}
//		});
//
//		// 播放
//		mCartPoint.setVisibility(View.VISIBLE);
//
//		anim.setDuration(400);// 动画的执行时间
//		anim.setStartOffset(100);
//		mCartPoint.startAnimation(anim);
//	}
//
//	/**
//	 * 购物车圆点缩放动画
//	 */
//	protected void setAnim2() {
//		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f);
//		animation.setRepeatCount(0);// 动画重复执行的次数
//		animation.setDuration(400);// 动画的执行时间
//		animation.setFillAfter(false); // 动画结束不停留在最后一帧
//		animation.setStartOffset(0);
//		tv_cart_count.startAnimation(animation);
//		animation.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				queryCartCountAdd();
//			}
//
//		});
//	}
//
//	private RelativeLayout rootView;
//	private ImageView mCartPoint;
//	private RelativeLayout pointRoot;
//	private LinearLayout ll_bottem;
//	private Animation baoyou_animationShow;
//	private Animation baoyou_animationGone;
//
//	/**
//	 * 添加动画布局
//	 */
//	private void addAnimLayout() {
//		rootView = (RelativeLayout) findViewById(R.id.rrr);
//		pointRoot = new RelativeLayout(context);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//				RelativeLayout.LayoutParams.MATCH_PARENT);
//		pointRoot.setBackgroundColor(Color.TRANSPARENT);
//		pointRoot.setLayoutParams(params);
//		rootView.addView(pointRoot);
//
//		mCartPoint = new ImageView(context);
//		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 12),
//				DP2SPUtil.dp2px(context, 12));
//		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		params2.bottomMargin = DP2SPUtil.dp2px(context, 36);
//		params2.rightMargin = DP2SPUtil.dp2px(context, 120);
//		mCartPoint.setLayoutParams(params2);
//		mCartPoint.setBackgroundResource(R.drawable.red_point_bg);
//		pointRoot.addView(mCartPoint);
//		mCartPoint.setVisibility(View.VISIBLE);
//
//	}
//
//	/**
//	 * 添加购物车时候查询 购物车数量 并显示倒计时
//	 */
//	private void queryCartCountAdd() {
//
//		ShopCartDao dao = new ShopCartDao(context);
//		int count = 0;
//
//		count = dao.queryCartCommonCount(context);
//
//		if (count > 0) {
//			count = count > 99 ? 99 : count;
//			tv_cart_count.setText(count + "");
//			tv_cart_count2.setText(count + "");
//		} else {
//			;
//		}
//
//		Long sTime = new Date().getTime();// 系统当前时间
//
//		Long sDeadline = false
//				? Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0"))
//				: Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0"));// 商品过期时间
//
//		if (sDeadline == 0) {// 表示还没有存到本地
//
//			SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_COMMON_TIME, sTime + 30 * 1000 * 60 + "");
//
//			sDeadline = sTime + 30 * 60 * 1000;
//		}
//
//	}
//
//	private void setShareAnim() {
//		SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
//		String shareAnim = SharedPreferencesUtil.getStringData(context, Pref.SHAREANIM, "0");
//		long shareAnimTime = Long.valueOf(shareAnim);
//		boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
//		if (!isRoate) {
//			return;
//		}
//		RotateAnimation ani1 = new RotateAnimation(0f, 35f, Animation.RELATIVE_TO_SELF, 1.0f,
//				Animation.RELATIVE_TO_SELF, 1.0f);
//		ScaleAnimation ani2 = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, Animation.RELATIVE_TO_SELF, 0.5f,
//				Animation.RELATIVE_TO_SELF, 0.5f);
//		final AnimationSet set = new AnimationSet(context, null);
//		ani1.setDuration(270);
//		ani1.setRepeatMode(Animation.REVERSE);
//		ani1.setFillAfter(false);
//		ani2.setDuration(270);
//		ani2.setRepeatMode(Animation.RESTART);
//		ani2.setFillAfter(false);
//
//		set.addAnimation(ani1);
//		set.addAnimation(ani2);
//		set.setStartOffset(600);
//		redShare.startAnimation(set);
//
//		final RotateAnimation ani3 = new RotateAnimation(-12f, 10f, Animation.RELATIVE_TO_SELF, 1.0f,
//				Animation.RELATIVE_TO_SELF, 1.0f);
//		ani3.setDuration(55);
//		ani3.setRepeatMode(Animation.REVERSE);
//		ani3.setRepeatCount(2);
//		ani3.setFillAfter(true);
//		final RotateAnimation ani4 = new RotateAnimation(-6f, 6f, Animation.RELATIVE_TO_SELF, 1.0f,
//				Animation.RELATIVE_TO_SELF, 1.0f);
//		ani4.setDuration(45);
//		ani4.setRepeatMode(Animation.REVERSE);
//		ani4.setRepeatCount(1);
//		ani4.setFillAfter(false);
//
//		ani1.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				moneyShare.startAnimation(ani3);
//				set.setStartOffset(1300);
//			}
//		});
//		ani3.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				moneyShare.startAnimation(ani4);
//			}
//		});
//		ani4.setAnimationListener(new AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				redShare.startAnimation(set);
//			}
//		});
//	}
//
//	public void getSupplyExplainDialog(String title, String content1, String content2) {
//		final Dialog explainDialog = new Dialog(ShopDetailsActivity.this, R.style.invate_dialog_style);
//		View view = View.inflate(ShopDetailsActivity.this, R.layout.dialog_shop_details_supply_explian, null);
//		TextView mTitle = (TextView) view.findViewById(R.id.dialog_details_title);
//		mTitle.setText(title + "制造商");
//		TextView mContent = (TextView) view.findViewById(R.id.dialog_details_content);
//		mContent.setText(content1);
//		TextView mExplain = (TextView) view.findViewById(R.id.dialog_details_explain);
//		mExplain.setText(content2);
//		TextView mClose = (TextView) view.findViewById(R.id.dialog_details_know);
//		mClose.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				explainDialog.dismiss();
//			}
//		});
//		explainDialog.setContentView(view);
//		explainDialog.setCancelable(true);
//		explainDialog.setCanceledOnTouchOutside(false);
//		explainDialog.show();
//	}
//
//	public void getSignJoinShopCartDialog(Double jiangliValue) {
//		final Dialog signDialog = new Dialog(ShopDetailsActivity.this, R.style.invate_dialog_style);
//		View view = View.inflate(ShopDetailsActivity.this, R.layout.dialog_sign_join_shopcart, null);
//		TextView mTvJiangli = (TextView) view.findViewById(R.id.dialog_sign_jiangli);
//		String mNotice = "元";
//		int jiangliType = 5;
//		try {
//			jiangliType = SignFragment.jiangliIDShopCart;
//		} catch (Exception e2) {
//		}
//		switch (jiangliType) {
//		case 3:
//			mNotice = "元优惠券";
//			break;
//		case 4:
//			mNotice = "积分";
//			break;
//		case 5:
//			mNotice = "元";
//			break;
//		case 11:
//			mNotice = "个衣豆";
//			break;
//		default:
//			break;
//		}
//		String s = "";
//		String str = "";
//		if (jiangliType == 11) {
//			str = new java.text.DecimalFormat("#0").format(jiangliValue);
//			s = new java.text.DecimalFormat("#0").format(jiangliValue) + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
//		} else {
//			str = "" + jiangliValue;
//			s = jiangliValue + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
//		}
//		String l = str + mNotice;
//		mTvJiangli.setText(s);
//		SpannableStringBuilder builder = new SpannableStringBuilder(mTvJiangli.getText().toString());
//		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
//		builder.setSpan(redSpan, 0, l.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		mTvJiangli.setText(builder);
//		TextView mClose = (TextView) view.findViewById(R.id.tv_close);
//		mClose.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				signDialog.dismiss();
//			}
//		});
//		Button liebiao = (Button) view.findViewById(R.id.dialog_liebiao);
//		liebiao.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 跳至赚钱
//				startActivity(new Intent(ShopDetailsActivity.this, MakeMoneyActivity.class));
//
//			}
//		});
//		Button gobuy = (Button) view.findViewById(R.id.gobuy2);
//		gobuy.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				signDialog.dismiss();
//				Intent intent = new Intent(ShopDetailsActivity.this, ShopCartNewNewActivity.class);
//				startActivity(intent);
//			}
//		});
//		signDialog.setContentView(view);
//		signDialog.setCancelable(true);
//		signDialog.setCanceledOnTouchOutside(false);
//		signDialog.show();
//	}
//
//	private void sign_shopcart(final boolean flag, final int num) {
//		// 签到
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) ShopDetailsActivity.this, 0) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//
//				return ComModel2.getSignIn(ShopDetailsActivity.this, false, false,
//						SharedPreferencesUtil.getStringData(context, YConstance.ADD_TO_SHOPCART, ""),
//						SignFragment.doClass);
//
//			}
//
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null && result != null) {
//					int needValue = 1;
//					try {
//						needValue = Integer.parseInt(SignFragment.doValueShopCart);
//					} catch (Exception e2) {
//					}
//					int signNumber = 1;// 1，代表一次性奖励，大于1代表多次奖励
//					try {
//						signNumber = SignFragment.doNumShopCart;
//					} catch (Exception e2) {
//					}
//					double jiangliValue = 0.0;
//					try {
//						jiangliValue = Double.parseDouble(SignFragment.jiangliValueShopCart);
//					} catch (Exception e2) {
//					}
//					if (flag) {
//						getSignJoinShopCartDialog(signNumber * jiangliValue);
//						SharedPreferencesUtil.saveStringData(ShopDetailsActivity.this,
//								"qiandao_time" + YCache.getCacheUser(ShopDetailsActivity.this).getUser_id(), "-1");
//					} else {
//						String mNotice = "元";
//						int jiangliType = 5;
//						try {
//							jiangliType = SignFragment.jiangliIDShopCart;
//						} catch (Exception e2) {
//						}
//						switch (jiangliType) {
//						case 3:
//							mNotice = "元优惠券";
//							break;
//						case 4:
//							mNotice = "积分";
//							break;
//						case 5:
//							mNotice = "元";
//							break;
//						case 11:
//							mNotice = "个衣豆";
//							break;
//						default:
//							break;
//						}
//						ToastUtil.showShortText(context, "加入成功奖励" + jiangliValue + mNotice + "，还有" + num + "次机会喔~");
//					}
//				} else {
//				}
//
//			}
//
//		}.execute();
//	}
//
//	private void showMyPopwindou(FragmentActivity context, final double feedBack) {
//
//		new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) ShopDetailsActivity.this,
//				R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//				return ComModel.ShareLifeGetPic(context);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//
//				myPopupwindow = new MyPopupwindow(context, feedBack, ShopDetailsActivity.this, shop, false, "",
//						"ShopDetails", 1, "", isSignActiveShop);
//
//				if (result == null) {
//					MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);
//
//				} else
//
//				if (null == e && result != null && !("".equals(result))) {
//
//					String mStartPic = (String) result.get("pic");
//					if (mStartPic == null || mStartPic.equals("null") || mStartPic.equals("")) {
//						mStartPic = "-1";
//					} else {
//						mStartPic = (String) result.get("pic");
//					}
//
//					if (mStartPic.equals("-1")) {
//						MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);
//					} else {
//						SetImageLoader.initImageLoader(null, MyPopupwindow.iv_img, mStartPic, "");
//					}
//
//				}
//
//				if (ShopDetailsActivity.instance != null) {
//					myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//
//				}
//
//			}
//
//		}.execute();
//
//	}
//
//	@Override
//	public void getPshareShop() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void getPshareSignShop() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
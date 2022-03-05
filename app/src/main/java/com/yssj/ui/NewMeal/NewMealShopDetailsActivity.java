//package com.yssj.ui.NewMeal;
//
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
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
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
//
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
//import com.yssj.entity.UserInfo;
//import com.yssj.huanxin.activity.KeFuActivity;
//import com.yssj.model.ComModel;
//import com.yssj.model.ComModel2;
//import com.yssj.photoView.ImagePagerActivity;
//import com.yssj.ui.HomeWatcherReceiver;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.ShopCartNewNewActivity;
//import com.yssj.ui.activity.ShopImageActivity;
//import com.yssj.ui.activity.logins.LoginActivity;
//import com.yssj.ui.activity.shopdetails.MealSubmitOrderActivity;
//import com.yssj.ui.activity.shopdetails.NoShareActivity;
//import com.yssj.ui.activity.shopdetails.ShopDetailsDialog;
//import com.yssj.ui.activity.shopdetails.StockBean;
//import com.yssj.ui.activity.shopdetails.SubmitMultiShopActivty;
//import com.yssj.ui.activity.shopdetails.SubmitOrderActivity;
//import com.yssj.ui.base.BasicActivity;
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
//import com.yssj.utils.sqlite.ShopCartDao;
//
//import org.apache.commons.lang.time.DateFormatUtils;
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
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.regex.Pattern;
//
//import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
//import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
//
////import com.yssj.utils.sqlite.ShopCartDao;
////import com.yssj.ui.activity.ShopCartActivity;
//
////import com.nostra13.universalimageloader.core.ImageLoader;
//
///***
// * 商品展示
// *
// * @author Administrator
// *
// */
//public class NewMealShopDetailsActivity extends BasicActivity
//        implements OnClickListener, onClickLintener, OnCheckedLintener, ShopDetailsGetShare {
//    private boolean isdown = false;
//    private int num;
//    private Double f_xiang_mongey;
//
//    private boolean first_come = true; // 防止来回滑动计算次数
//    private PublicToastDialog shareWaitDialog = null;
//    private Timer timer;
//    private Timer timer_seven;
//    private Timer timer_meal;
//    private boolean first = false;
//    private boolean first_meal = false;
//
//    private long recLen;
//    private long recLen_seven;
//
//    private TimerTask task;
//    private TimerTask task_seven;
//    private TimerTask task_meal;
//
//    private int countCommn;
//    private int countMeal;
//
//    private TextView tv_top_daojishis;
//
//    private int width, height, heights;
//    private Shop shop;
//    private TextView tv_shop_car, tv_buy, sign_buy;
//    private ImageView /* img_fenx, */ img_xin, img_addxin;
//    private RelativeLayout img_fenx;
//    private ImageView img_fenx_old;
//
//    public static int setEva_count_z;
//    public static String MealType;
//    private LinearLayout img_back;
//
//    // private SlidingMenu sm;
//
//    private LinearLayout lin_add_like;
//    private RelativeLayout img_cart;
//    private ImageView img_cart_old;
//
//    private ImageView lin_contact;
//    private RelativeLayout lin_contact_old;
//
//    private SAsyncTask<String, Void, HashMap<String, Object>> aa;
//    private SAsyncTask<Void, Void, HashMap<String, Object>> bb;
//    private SAsyncTask<Void, Void, HashMap<String, Object>> cc;
//
//    private SAsyncTask<String, Void, ShareShop> pp;
//
//
//    private LinkedList<HashMap<String, Object>> dataList;
//
//    private String[] images;// 普通商品图片
//
//    private String[] imageTag1, imageTag2, imageTag3;// 套餐图片
//
//    private List<HashMap<String, String>> listTitle;
//
//    private int check = 0;
//    private RelativeLayout rlBottom;
//    private LinearLayout rlTop;
//
//    private MyPopupwindow myPopupwindow;
//
//    // private static int num; // 说明框出现次数
//
//    private TextView tv_cart_count;
//    private TextView tv_time_count_down;
//    private TextView tv_time_count_down_meal;
//
//    private RelativeLayout rl_retain;
//
//    private Context context;
//
//    private StickyListHeadersListView mListView;
//
//
//    private MyAdapter adapter;
//
//    private View headerView;
//    private String titleId;// 筛选类别的id
//    public static NewMealShopDetailsActivity instance;
//    private static boolean isShow;
//    private ImageButton mShuaixuanNew;
//    private String signShopDetail;// 判断是值为 "SignShopDetail" 从签到跳转到商品详情页面
//    private int signType;// 代表 几元包邮
//    private String signValue;// 签到 商品编号
//    private String valueDuo;// 几元夺宝
//    private ImageView toDuoBaoIv; // 去夺宝的悬浮按钮
//    private ImageView xunBaoIv;
//
//    private LinearLayout redShare;
//    private ImageView moneyShare;
//
//
//    private static int isPause = 0;
//
//    private Intent qqShareIntent /*
//                                     * = ShareUtil.shareMultiplePictureToQZone(
//									 * ShareUtil.getImage())
//									 */;
//    private Intent wXinShareIntent /*
//                                     * =
//									 * ShareUtil.shareMultiplePictureToTimeLine(
//									 * ShareUtil.getImage())
//									 */;
//
//    private Runnable r, shareRun;
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        HomeWatcherReceiver.unregisterHomeKeyReceiver(context);
//        // SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE,
//        // "-1");
//        // MobclickAgent.onPageEnd("NewMealShopDetailsActivity");
//        // MobclickAgent.onPause(this);
//        if (newHandler != null) {
//            newHandler.removeCallbacks(r);
//        }
//        if (shareHandler != null) {
//            shareHandler.removeCallbacks(shareRun);
//        }
////		YJApplication.getLoader().stop();
//        isPause = 1;
//    }
//
//    @Override
//    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//        super.onActivityResult(arg0, arg1, arg2);
//
//        if (arg1 == RESULT_OK) {
//
//            // queryShopDetails();
//            // queryShopMeal();
//            ShopCartDao dao = new ShopCartDao(context);
//            // int count = dao.queryCartCount(context);
//            int count = 0;
//            if (isMeal) {
//                count = dao.queryCartSpecialCount(context);
//            } else {
//                count = dao.queryCartCommonCount(context);
//            }
//            if (arg0 == 1080) {
//
//                if (!isMeal && !"SignShopDetail".equals(signShopDetail)) {
//                    // int count = arg2.getIntExtra("count",
//                    // shop.getCart_count());
//                    // shop.setCart_count(count);
//                    tv_cart_count.setVisibility(View.VISIBLE);
//                    tv_cart_count.setText(/* shop.getCart_count() */count + "");
//
//                }
//            } else if (arg0 == 233) {// 加入购物车
//                double a = entity.getOriginal_price();
//                int b = (int) a;
//
//
//            } else if (arg0 == 234) {// 购买
//                Intent intent = new Intent(NewMealShopDetailsActivity.this, SubmitOrderActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("shop", shop);
//                intent.putExtras(bundle);
//                intent.putExtra("id", entity.getId());
//                intent.putExtra("size", entity.getSize());
//                intent.putExtra("color", entity.getColor());
//                intent.putExtra("shop_num", entity.getShop_num());
//                intent.putExtra("stock_type_id", entity.getStock_type_id());
//                intent.putExtra("stock", entity.getStock());
//                intent.putExtra("price", entity.getPrice());
//                intent.putExtra("pic", entity.getPic());
//                startActivity(intent);
//            } else if (arg0 == 235) {
//                // mListView.removeHeaderView(headerView);
//                if (isMeal) {
//                    queryShopMeal();
//                } else if ("SignShopDetail".equals(signShopDetail)) {
//                } else {
//                }
//            } else if (arg0 == 15502) {
//                if (YJApplication.instance.isLoginSucess()) {
//                    if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                        getPshareShop();
//                    } else {
//                        share(shop.getShop_code(), shop);
//                    }
//                }
//            }
//        }
//
//    }
//
//    private List<Shop> list;// 套餐内商品集合
//
//    private class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
//
//        // private int i;
//
//        @Override
//        public int getCount() {
//            int count = 0;
//            if (check == 0) {
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//
//                    count = count + imageTag1.length + imageTag2.length + imageTag3.length;
//
//                } else {
//                    count += images.length;
//                }
//                if (dataList != null) {
//                    count += dataList.size() % 2 == 0 ? dataList.size() / 2 : dataList.size() / 2 + 1;
//                }
//            } else if (check == 1) {
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    count += list.size() + 1;
//                } else {
//                    count += 2;
//                }
//            } else {
//
//                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {// 套餐取消评论区
//                    count = tuijianList == null ? 4
//                            : (tuijianList.size() % 2 == 0 ? tuijianList.size() / 2 : tuijianList.size() / 2 + 1);
//                } else {
//                    count += 2;
//                    if (listShopComments != null && listShopComments.size() > 0) {
//                        count += listShopComments.size();
//                    } else {
//                        count++;
//                    }
//                }
//            }
//            return count;
//        }
//
//        @Override
//        public Object getItem(int arg0) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int arg0) {
//            return arg0;
//        }
//
//        @Override
//        public View getView(int position, View v, ViewGroup arg2) {
//            ItemViewHolder vh;
//            if (v == null) {
//                v = LayoutInflater.from(NewMealShopDetailsActivity.this).inflate(R.layout.newmeal_shop_item, arg2, false);
//                vh = new ItemViewHolder();
//                v.findViewById(R.id.lln).setBackgroundColor(Color.WHITE);
//                vh.imageGroup = v.findViewById(R.id.image_group);
//                vh.sView2 = (SizeView2) v.findViewById(R.id.size_view2);
//                vh.shopItem = v.findViewById(R.id.item_position);
//                vh.image = (ImageView) v.findViewById(R.id.image_position);
//                vh.image.getLayoutParams().height = width * 9 / 6;
//                vh.left = (ItemView) v.findViewById(R.id.left);
//                vh.left.setHeight(width / 2 * 9 / 6);
//                vh.right = (ItemView) v.findViewById(R.id.right);
//                vh.right.setHeight(width / 2 * 9 / 6);
//                vh.sView = (SizeView) v.findViewById(R.id.size_view);
//                vh.eView = v.findViewById(R.id.sevalauate_view);
//                vh.eView.setBackgroundColor(Color.WHITE);
//                vh.lin_nodata = (LinearLayout) v.findViewById(R.id.lin_nodata);
//
//                vh.pb_color_count = (ProgressBar) v.findViewById(R.id.pb_color_count);
//                vh.pb_type_count = (ProgressBar) v.findViewById(R.id.pb_type_count);
//                vh.pb_work_count = (ProgressBar) v.findViewById(R.id.pb_work_count);
//                vh.pb_cost_count = (ProgressBar) v.findViewById(R.id.pb_cost_count);
//
//                vh.tv_color_count = (TextView) v.findViewById(R.id.tv_color_count);
//                vh.tv_type_count = (TextView) v.findViewById(R.id.tv_type_count);
//                vh.tv_work_count = (TextView) v.findViewById(R.id.tv_work_count);
//                vh.tv_cost_count = (TextView) v.findViewById(R.id.tv_cost_count);
//
//                vh.viewContainer = (LinearLayout) v.findViewById(R.id.container);
//
//                vh.img_user_header = (RoundImageButton) v.findViewById(R.id.img_user_header);
//                vh.tv_user = (TextView) v.findViewById(R.id.tv_user);
//                vh.tv_evaluate = (TextView) v.findViewById(R.id.tv_evaluate);
//                vh.tv_date = (TextView) v.findViewById(R.id.tv_date);
//                vh.tv_descri = (TextView) v.findViewById(R.id.tv_descri);
//                vh.tv_size_color = (TextView) v.findViewById(R.id.tv_size_color);
//                vh.img_container = (LinearLayout) v.findViewById(R.id.img_container);
//                vh.tv_one_reply = (TextView) v.findViewById(R.id.tv_one_reply);
//                vh.tv_second_judge = (TextView) v.findViewById(R.id.tv_second_judge);
//                vh.tv_second_reply = (TextView) v.findViewById(R.id.tv_second_reply);
//
//                vh.lin_second = (LinearLayout) v.findViewById(R.id.lin_second);
//
//                vh.bar = (RatingBar) v.findViewById(R.id.smy_ratingbar);
//
//                vh.evaView = v.findViewById(R.id.evaluate_view);
//
//                vh.bai = v.findViewById(R.id.bai);
//                vh.bai.getLayoutParams().height = NewMealShopDetailsActivity.this.height / 3;
//                // vh.search=(ImageButton) view.findViewById(R.id.search);
//                // vh.shaixuan=(ImageButton) view.findViewById(R.id.shaixuan);
//
//                vh.diver = v.findViewById(R.id.diver);
//
//                vh.sizeHint = (ImageView) v.findViewById(R.id.size_hint);
//                vh.sizeHint.getLayoutParams().height = width * 2453 / 1080;
//
//                vh.two_container = (LinearLayout) v.findViewById(R.id.two_image_container);
//                vh.imageTag = (ImageView) v.findViewById(R.id.meal_tag);
//
//                vh.mealRView = (MealRecomenView) v.findViewById(R.id.meal_r);
//
//                v.setTag(vh);
//            } else {
//                vh = (ItemViewHolder) v.getTag();
//            }
//
//            if (check == 0) {// 详情
//
//                vh.mealRView.setVisibility(View.GONE);
//                vh.sView.setVisibility(View.GONE);
//                vh.eView.setVisibility(View.GONE);
//                vh.bai.setVisibility(View.GONE);
//                vh.diver.setVisibility(View.GONE);
//                vh.sizeHint.setVisibility(View.GONE);
//
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {// 如果是套餐...
//
//                    if (position < imageTag1.length) {
//                        if (position == 0) {
//                            vh.imageTag.setVisibility(View.VISIBLE);
//                        } else {
//                            vh.imageTag.setVisibility(View.GONE);
//                        }
//
//                        vh.imageTag.setImageResource(R.drawable.shop_tag_one);
//
//                        vh.imageGroup.setVisibility(View.VISIBLE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        if (width > 720) {
//                            vh.image.setTag(list.get(0).getShop_code().substring(1, 4) + "/"
//                                    + list.get(0).getShop_code() + "/" + imageTag1[position] + "!450");
//                            SetImageLoader.initImageLoader(null, vh.image, list.get(0).getShop_code().substring(1, 4)
//                                    + "/" + list.get(0).getShop_code() + "/" + imageTag1[position], "!450");
//                        } else {
//                            vh.image.setTag(list.get(0).getShop_code().substring(1, 4) + "/"
//                                    + list.get(0).getShop_code() + "/" + imageTag1[position] + "!382");
//                            SetImageLoader.initImageLoader(null, vh.image, list.get(0).getShop_code().substring(1, 4)
//                                    + "/" + list.get(0).getShop_code() + "/" + imageTag1[position], "!382");
//                        }
//
//                        final int x = position;
//                        vh.image.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopImageActivity.class);
//                                intent.putExtra("url", list.get(0).getShop_code().substring(1, 4) + "/"
//                                        + list.get(0).getShop_code() + "/" + imageTag1[x]);
//                                intent.putExtra("code", code);
//                                intent.putExtra("mealMap", mealMap);
//                                intent.putExtra("isMeal", isMeal);
//                                intent.putExtra("signValue", signValue);
//                                intent.putExtra("signType", signType);
//                                intent.putExtra("signShopDetail", signShopDetail);
//
//                                intent.putExtra("ShopCart", shopCart);
//                                intent.putExtra("number_sold", number_sold);
//                                intent.putExtra("isSignActiveShop", isSignActiveShop);
//
//                                startActivityForResult(intent, 1080);
//                            }
//                        });
//
//                        return v;
//                    }
//                    if (position < imageTag1.length + imageTag2.length) {
//                        if (position == imageTag1.length) {
//                            vh.imageTag.setVisibility(View.VISIBLE);
//                        } else {
//                            vh.imageTag.setVisibility(View.GONE);
//                        }
//
//                        vh.imageTag.setImageResource(R.drawable.shop_tag_two);
//
//                        vh.imageGroup.setVisibility(View.VISIBLE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        if (width > 720) {
//                            vh.image.setTag(
//                                    list.get(0).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
//                                            + imageTag2[position - imageTag1.length] + "!450");
//                            SetImageLoader.initImageLoader(null,
//                                    vh.image, list.get(1).getShop_code().substring(1, 4) + "/"
//                                            + list.get(1).getShop_code() + "/" + imageTag2[position - imageTag1.length],
//                                    "!450");
//                        } else {
//                            vh.image.setTag(
//                                    list.get(1).getShop_code().substring(1, 4) + "/" + list.get(1).getShop_code() + "/"
//                                            + imageTag2[position - imageTag1.length] + "!382");
//                            SetImageLoader.initImageLoader(null,
//                                    vh.image, list.get(1).getShop_code().substring(1, 4) + "/"
//                                            + list.get(1).getShop_code() + "/" + imageTag2[position - imageTag1.length],
//                                    "!382");
//                        }
//
//                        final int x = position;
//                        vh.image.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopImageActivity.class);
//                                intent.putExtra("url", list.get(1).getShop_code().substring(1, 4) + "/"
//                                        + list.get(1).getShop_code() + "/" + imageTag2[x - imageTag1.length]);
//                                intent.putExtra("code", code);
//                                intent.putExtra("mealMap", mealMap);
//                                intent.putExtra("isMeal", isMeal);
//                                intent.putExtra("signValue", signValue);
//                                intent.putExtra("signType", signType);
//                                intent.putExtra("signShopDetail", signShopDetail);
//
//                                intent.putExtra("ShopCart", shopCart);
//                                intent.putExtra("number_sold", number_sold);
//                                intent.putExtra("isSignActiveShop", isSignActiveShop);
//                                startActivityForResult(intent, 1080);
//                            }
//                        });
//
//                        return v;
//                    }
//
//                    if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
//                        if (position == imageTag1.length + imageTag2.length) {
//                            vh.imageTag.setVisibility(View.VISIBLE);
//                        } else {
//                            vh.imageTag.setVisibility(View.GONE);
//                        }
//                        vh.imageTag.setImageResource(R.drawable.shop_tag_three);
//
//                        vh.imageGroup.setVisibility(View.VISIBLE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        if (width > 720) {
//                            vh.image.setTag(
//                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code() + "/"
//                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!450");
//                            SetImageLoader.initImageLoader(null, vh.image,
//                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code() + "/"
//                                            + imageTag3[position - imageTag1.length - imageTag2.length],
//                                    "!450");
//                        } else {
//                            vh.image.setTag(
//                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code() + "/"
//                                            + imageTag3[position - imageTag1.length - imageTag2.length] + "!382");
//                            SetImageLoader.initImageLoader(null, vh.image,
//                                    list.get(2).getShop_code().substring(1, 4) + "/" + list.get(0).getShop_code() + "/"
//                                            + imageTag3[position - imageTag1.length - imageTag2.length],
//                                    "!382");
//                        }
//
//                        final int x = position;
//                        vh.image.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopImageActivity.class);
//                                intent.putExtra("url",
//                                        list.get(2).getShop_code().substring(1, 4) + "/" + list.get(2).getShop_code()
//                                                + "/" + imageTag3[x - imageTag1.length - imageTag2.length]);
//                                intent.putExtra("code", code);
//                                intent.putExtra("mealMap", mealMap);
//                                intent.putExtra("isMeal", isMeal);
//                                intent.putExtra("signValue", signValue);
//                                intent.putExtra("signType", signType);
//                                intent.putExtra("signShopDetail", signShopDetail);
//
//                                intent.putExtra("ShopCart", shopCart);
//                                intent.putExtra("number_sold", number_sold);
//                                intent.putExtra("isSignActiveShop", isSignActiveShop);
//                                startActivityForResult(intent, 1080);
//                            }
//                        });
//
//                        return v;
//                    }
//                    vh.imageTag.setVisibility(View.GONE);
//                    position = position - imageTag1.length - imageTag2.length - imageTag3.length;
//
//                } else {
//                    if (position == images.length - 1) {
//                        vh.sView2.setContent(shop, isMeal, position);
//                        vh.sView2.setVisibility(View.VISIBLE);
//                    } else {
//                        vh.sView2.setVisibility(View.GONE);
//
//                    }
//                    vh.imageTag.setVisibility(View.GONE);
//                    if (position < images.length) {
//                        vh.imageGroup.setVisibility(View.VISIBLE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        vh.image.setTag(shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code() + "/"
//                                + images[position] + "!450");
//                        SetImageLoader.initImageLoader(null, vh.image, shop.getShop_code().substring(1, 4) + "/"
//                                + shop.getShop_code() + "/" + images[position], "!450");
//                        final int x = position;
//                        vh.image.setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopImageActivity.class);
//                                intent.putExtra("url", shop.getShop_code().substring(1, 4) + "/" + shop.getShop_code()
//                                        + "/" + images[x]);
//                                intent.putExtra("shop", shop);
//                                intent.putExtra("isMembers", isMembers);
//
//                                intent.putExtra("ShopCart", shopCart);
//                                intent.putExtra("number_sold", number_sold);
//                                intent.putExtra("isSignActiveShop", isSignActiveShop);
//                                startActivityForResult(intent, 1080);
//                            }
//                        });
//
//                        return v;
//                    }
//                    if (isforcelook == true || isforcelookMatch || (isSignActiveShop && SignFragment.doType == 4 && isSignActiveShopScan)) {//活动商品并且是浏览个数的任务
//                        if (position == images.length) {
//                            if (first_come == true) {
//                                first_come = false;
//
//                                if (isforcelook) {///正价商品
//                                    String nowTimeForcelook = SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//                                    forcelookNum = Integer.valueOf(SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookNum" + YCache.getCacheUser(context).getUser_id(), "0"));
//                                    if (!df.format(new Date()).equals(nowTimeForcelook)) {
//                                        forcelookNum = 0;//不是同一天点击分享任务    或者不是同一个用户 就  或者取出的数据大于浏览次数  重新开始计数分享的次数
//                                    }
//                                    forcelookNum++;
//                                    if (SignFragment.doNum > 1) {//需要奖励分多次发放
//                                        sign();
//                                    } else {
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookNum" + YCache.getCacheUser(context).getUser_id(), "" + forcelookNum);
//                                        if (forcelookNum < Integer.parseInt(singvalue)) {
//                                            ToastUtil.showLongText(NewMealShopDetailsActivity.this, "再浏览" + (Integer.parseInt(singvalue) - forcelookNum) + "件可完成任务喔~");
//                                        } else if (forcelookNum >= Integer.parseInt(singvalue)) {
//                                            sign();
//                                        }
//                                    }
//
//                                } else if (isforcelookMatch) {//搭配购商品
//                                    String nowTimeForcelookMatch = SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//                                    forcelookMatchNum = Integer.valueOf(SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookMatchNum" + YCache.getCacheUser(context).getUser_id(), "0"));
//                                    if (!df.format(new Date()).equals(nowTimeForcelookMatch)) {
//                                        forcelookMatchNum = 0;//不是同一天点击分享任务    或者不是同一个用户 就重新开始计数分享的次数
//                                    }
//                                    forcelookMatchNum++;
//                                    if (SignFragment.doNum > 1) {//需要奖励分多次发放
//                                        sign();
//                                    } else {
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookMatchNum" + YCache.getCacheUser(context).getUser_id(), "" + forcelookMatchNum);
//                                        if (forcelookMatchNum < Integer.parseInt(singvalue)) {
//                                            ToastUtil.showLongText(NewMealShopDetailsActivity.this, "再浏览" + (Integer.parseInt(singvalue) - forcelookMatchNum) + "件可完成任务喔~");
//                                        } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
//                                            sign();
//                                        }
//                                    }
//                                } else if (isSignActiveShop) {//活动商品
//                                    String nowTimeSignActiveShop = SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), "");
//                                    signActiveShopNum = Integer.valueOf(SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "signActiveShopNum" + YCache.getCacheUser(context).getUser_id(), "0"));
//                                    if (!df.format(new Date()).equals(nowTimeSignActiveShop)) {
//                                        signActiveShopNum = 0;//不是同一天点击分享任务    或者不是同一个用户 就重新开始计数分享的次数
//                                    }
//                                    signActiveShopNum++;
//                                    if (SignFragment.doNum > 1) {//需要奖励分多次发放
//                                        sign();
//                                    } else {
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "signActiveShopNum" + YCache.getCacheUser(context).getUser_id(), "" + signActiveShopNum);
//                                        if (signActiveShopNum < Integer.parseInt(singvalue)) {
//                                            ToastUtil.showLongText(NewMealShopDetailsActivity.this, "再浏览" + (Integer.parseInt(singvalue) - signActiveShopNum) + "件可完成任务喔~");
//                                        } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
//                                            sign();
//                                        }
//                                    }
//                                }
//
//                                if (xunBaoIv != null && xunBaoIv.getVisibility() == View.VISIBLE) {
//                                    xunBaoIv.setVisibility(View.GONE);
//                                }
////								ForceLookActivity forceLookActivity2 = new ForceLookActivity();
////								if (forceLookActivity2.click_num < Integer.parseInt(singvalue)) {
//                                // Toast.makeText(NewMealShopDetailsActivity.this,
//                                // "这里没有红包、返回浏览其它美衣可以找到噢~",
//                                // Toast.LENGTH_SHORT).show();
////									ToastUtil.showShortText(NewMealShopDetailsActivity.this, "签到奖励不在这里哟~返回其他页面看看吧");
////									NewSignCommonDiaolg dialog  = new NewSignCommonDiaolg(NewMealShopDetailsActivity.this, R.style.DialogQuheijiao2, "liulan_sign_finish",0.5+"");
////									dialog.show();
//
////								}
//
////								ForceLookActivity forceLookActivity = new ForceLookActivity();
////								forceLookActivity.click_num = forceLookActivity.click_num + 1;
//                                // Toast.makeText(context, "112233",
//                                // Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } // 这些判断是强制浏览滑到最后一张才算一次的
//                    position = position - images.length;
//                }
//
//                position = position * 2;
//                vh.imageGroup.setVisibility(View.GONE);
//                vh.shopItem.setVisibility(View.VISIBLE);
//
//                vh.left.iniView(dataList.get(position));
//                vh.left.setOnClickListener(new MyOnClick(position));
//                if (dataList.size() > position + 1) {
//                    vh.right.setVisibility(View.VISIBLE);
//                    vh.right.iniView(dataList.get(position + 1));
//                    vh.right.setOnClickListener(new MyOnClick(position + 1));
//                } else {
//                    vh.right.setVisibility(View.INVISIBLE);
//                }
//            } else if (check == 1) {// 尺寸
//                vh.mealRView.setVisibility(View.GONE);
//                vh.imageTag.setVisibility(View.GONE);
//                vh.diver.setVisibility(View.GONE);
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//
//                    if (position < list.size()) {
//                        vh.bai.setVisibility(View.GONE);
//                        vh.sView.setVisibility(View.VISIBLE);
//                        vh.sizeHint.setVisibility(View.GONE);
//                        vh.imageGroup.setVisibility(View.GONE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        vh.eView.setVisibility(View.GONE);
//                        vh.sView.setContent(list.get(position), isMeal, position);
//                        return v;
//                    }
//
//                    if (position == list.size()) {
//                        vh.bai.setVisibility(View.VISIBLE);
//                        vh.sView.setVisibility(View.GONE);
//                        vh.imageGroup.setVisibility(View.GONE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        vh.eView.setVisibility(View.GONE);
//                        vh.sizeHint.setVisibility(View.GONE);
//                        // vh.sizeHint.setTag("system/shop_details.png");
//                        // SetImageLoader.initImageLoader(null, vh.sizeHint,
//                        // "system/shop_details.png", "!450");
//                        return v;
//                    }
//                } else {
//
//                    if (position == 0) {
//                        vh.bai.setVisibility(View.GONE);
//                        vh.sView.setVisibility(View.VISIBLE);
//                        vh.sizeHint.setVisibility(View.GONE);
//                        vh.imageGroup.setVisibility(View.GONE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        vh.eView.setVisibility(View.GONE);
//                        vh.sView.setContent(shop, isMeal, position);
//                        return v;
//                    }
//
//                    if (position == 1) {
//                        vh.bai.setVisibility(View.GONE);
//                        vh.sView.setVisibility(View.GONE);
//                        vh.imageGroup.setVisibility(View.GONE);
//                        vh.shopItem.setVisibility(View.GONE);
//                        vh.eView.setVisibility(View.GONE);
//                        vh.sizeHint.setVisibility(View.VISIBLE);
//                        vh.sizeHint.setTag("system/shop_details.png");
//                        if (width > 720) {
//                            SetImageLoader.initImageLoader(null, vh.sizeHint, "system/shop_details.png", "!450");
//                        } else {
//                            SetImageLoader.initImageLoader(null, vh.sizeHint, "system/shop_details.png", "!382");
//                        }
//
//                        return v;
//                    }
//
//                }
//
//            } else {// 评论
//
//                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
//                    vh.sView.setVisibility(View.GONE);
//                    vh.eView.setVisibility(View.GONE);
//                    vh.diver.setVisibility(View.GONE);
//                    vh.sizeHint.setVisibility(View.GONE);
//                    vh.imageTag.setVisibility(View.GONE);
//                    vh.imageGroup.setVisibility(View.GONE);
//                    vh.shopItem.setVisibility(View.GONE);
//                    if (tuijianList == null) {
//                        vh.bai.setVisibility(View.VISIBLE);
//                        vh.mealRView.setVisibility(View.GONE);
//                    } else {
//                        vh.bai.setVisibility(View.GONE);
//                        vh.mealRView.setVisibility(View.VISIBLE);
//                        position = position * 2;
//                        if (position == tuijianList.size() - 1 || position == tuijianList.size() - 2) {
//                            vh.mealRView.setData(tuijianList.get(position),
//                                    tuijianList.size() == position + 1 ? null : tuijianList.get(position + 1), true);
//                        } else {
//                            vh.mealRView.setData(tuijianList.get(position),
//                                    tuijianList.size() == position + 1 ? null : tuijianList.get(position + 1), false);
//                        }
//                    }
//                } else {
//                    vh.mealRView.setVisibility(View.GONE);
//                    vh.imageTag.setVisibility(View.GONE);
//                    vh.eView.setVisibility(View.VISIBLE);
//                    vh.sView.setVisibility(View.GONE);
//                    vh.imageGroup.setVisibility(View.GONE);
//                    vh.shopItem.setVisibility(View.GONE);
//                    vh.sizeHint.setVisibility(View.GONE);
//                    if (position == 0) {
//                        vh.evaView.setVisibility(View.VISIBLE);
//                        vh.viewContainer.setVisibility(View.GONE);
//                        vh.lin_nodata.setVisibility(View.GONE);
//                        vh.diver.setVisibility(View.GONE);
//                        vh.bai.setVisibility(View.GONE);
//
//                        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                            if (mealMap.get("eva_count").equals("0")) {
//                                vh.tv_color_count.setText("100%");
//                                vh.tv_type_count.setText("100%");
//                                vh.tv_work_count.setText("100%");
//                                vh.tv_cost_count.setText("100%");
//
//                                vh.pb_color_count.setProgress(100);
//                                vh.pb_type_count.setProgress(100);
//                                vh.pb_work_count.setProgress(100);
//                                vh.pb_cost_count.setProgress(100);
//                            } else {
//                                float color_count = Float.parseFloat(mealMap.get("color_count").toString());
//                                float work_count = Float.parseFloat(mealMap.get("work_count").toString());
//                                float type_count = Float.parseFloat(mealMap.get("type_count").toString());
//                                float cost_count = Float.parseFloat(mealMap.get("cost_count").toString());
//                                float eva_count = Float.parseFloat(mealMap.get("eva_count").toString());
//
//                                // MyLogYiFu.e("评价总数",eva_count+"");
//
//                                vh.tv_color_count
//                                        .setText(eva_count == 0 ? "100%" : (int) (color_count * 100 / eva_count) + "%");
//                                // System.out.println("***************dan1" +
//                                // color_count);
//                                // System.out.println("***************zong1" +
//                                // eva_count);
//                                vh.tv_type_count
//                                        .setText(eva_count == 0 ? "100%" : (int) (type_count * 100 / eva_count) + "%");
//                                vh.tv_work_count
//                                        .setText(eva_count == 0 ? "100%" : (int) (work_count * 100 / eva_count) + "%");
//                                vh.tv_cost_count
//                                        .setText(eva_count == 0 ? "100%" : (int) (cost_count * 100 / eva_count) + "%");
//
//                                vh.pb_color_count
//                                        .setProgress(eva_count == 0 ? 100 : (int) (color_count / eva_count * 100));
//                                vh.pb_type_count
//                                        .setProgress(eva_count == 0 ? 100 : (int) (type_count / eva_count * 100));
//                                vh.pb_work_count
//                                        .setProgress(eva_count == 0 ? 100 : (int) (work_count / eva_count * 100));
//                                vh.pb_cost_count
//                                        .setProgress(eva_count == 0 ? 100 : (int) (cost_count / eva_count * 100));
//                            }
//                        } else {
//                            if (shop.getEva_count() == 0) {
//                                vh.tv_color_count.setText("100%");
//                                vh.tv_type_count.setText("100%");
//                                vh.tv_work_count.setText("100%");
//                                vh.tv_cost_count.setText("100%");
//
//                                vh.pb_color_count.setProgress(100);
//                                vh.pb_type_count.setProgress(100);
//                                vh.pb_work_count.setProgress(100);
//                                vh.pb_cost_count.setProgress(100);
//                            } else {
//
//                                vh.tv_color_count.setText(shop.getEva_count() == 0 ? "100%"
//                                        : (int) (shop.getColor_count() / shop.getEva_count() * 100) + "%");
//                                // System.out.println("***************dan" +
//                                // shop.getColor_count());
//                                // System.out.println("***************zong" +
//                                // shop.getEva_count() * 100);
//                                vh.tv_type_count.setText(shop.getEva_count() == 0 ? "100%"
//                                        : (int) (shop.getType_count() / shop.getEva_count() * 100) + "%");
//                                vh.tv_work_count.setText(shop.getEva_count() == 0 ? "100%"
//                                        : (int) (shop.getWork_count() / shop.getEva_count() * 100) + "%");
//                                vh.tv_cost_count.setText(shop.getEva_count() == 0 ? "100%"
//                                        : (int) (shop.getCost_count() / shop.getEva_count() * 100) + "%");
//
//                                vh.pb_color_count.setProgress(shop.getEva_count() == 0 ? 100
//                                        : (int) (shop.getColor_count() / shop.getEva_count() * 100));
//                                vh.pb_type_count.setProgress(shop.getEva_count() == 0 ? 100
//                                        : (int) (shop.getType_count() / shop.getEva_count() * 100));
//                                vh.pb_work_count.setProgress(shop.getEva_count() == 0 ? 100
//                                        : (int) (shop.getWork_count() / shop.getEva_count() * 100));
//                                vh.pb_cost_count.setProgress(shop.getEva_count() == 0 ? 100
//                                        : (int) (shop.getCost_count() / shop.getEva_count() * 100));
//                            }
//                        }
//
//                        return v;
//                    }
//                    if (position == 1 && (listShopComments == null || listShopComments.isEmpty())) {
//                        vh.viewContainer.setVisibility(View.GONE);
//                        vh.lin_nodata.setVisibility(View.VISIBLE);
//                        vh.evaView.setVisibility(View.GONE);
//                        vh.bai.setVisibility(View.GONE);
//                        vh.diver.setVisibility(View.GONE);
//                        return v;
//                    }
//                    if (position == 2 && (listShopComments == null || listShopComments.isEmpty())) {
//                        vh.viewContainer.setVisibility(View.GONE);
//                        vh.lin_nodata.setVisibility(View.GONE);
//                        vh.evaView.setVisibility(View.GONE);
//                        vh.bai.setVisibility(View.VISIBLE);
//                        vh.diver.setVisibility(View.GONE);
//                        return v;
//                    }
//                    position = position - 1;
//                    if (position == listShopComments.size()) {
//                        vh.viewContainer.setVisibility(View.GONE);
//                        vh.lin_nodata.setVisibility(View.GONE);
//                        vh.evaView.setVisibility(View.GONE);
//                        vh.bai.setVisibility(View.VISIBLE);
//                        vh.diver.setVisibility(View.GONE);
//                        return v;
//                    }
//                    ShopComment shopComment = listShopComments.get(position);
//                    vh.viewContainer.setVisibility(View.VISIBLE);
//                    vh.lin_nodata.setVisibility(View.GONE);
//                    vh.evaView.setVisibility(View.GONE);
//                    vh.bai.setVisibility(View.GONE);
//                    vh.diver.setVisibility(View.VISIBLE);
//                    vh.img_user_header.setTag(shopComment.getUser_url());
//                    SetImageLoader.initImageLoader(NewMealShopDetailsActivity.this, vh.img_user_header,
//                            shopComment.getUser_url(), "");
//                    String user_name = shopComment.getUser_name();
//                    if (!TextUtils.isEmpty(user_name)) {
//
//                        if (user_name.length() == 1) {
//                            user_name = user_name + "****";
//                        }
//
//                        vh.tv_user.setText(user_name);
//                    }
//                    int comment_type = shopComment.getComment_type();
//
//                    if (comment_type == 1) {
//                        vh.tv_evaluate.setText("好评");
//
//                    } else if (comment_type == 2) {
//                        vh.tv_evaluate.setText("中评");
//                    } else if (comment_type == 3) {
//                        vh.tv_evaluate.setText("差评");
//                    }
//
//                    vh.bar.setRating(((float) shopComment.getStar()));
//
//                    long add_date = shopComment.getAdd_date();
//                    String date = StringUtils.timeToDate(add_date);
//                    if (!TextUtils.isEmpty(date)) {
//                        vh.tv_date.setText(date);
//                    }
//
//                    String content = shopComment.getContent();
//                    if (!TextUtils.isEmpty(content)) {
//                        vh.tv_descri.setText(content);
//                    }
//
//                    String shop_color = shopComment.getShop_color();
//                    String shop_size = shopComment.getShop_size();
//                    if (!TextUtils.isEmpty(shop_color)) {
//                        vh.tv_size_color.setText("颜色：" + shop_color + "  尺码：" + shop_size);
//                    }
//                    String pic = shopComment.getPic();
//                    vh.img_container.removeAllViews();
//                    if (!TextUtils.isEmpty(pic)) {
//                        LayoutParams params = new LayoutParams(80, 80);
//                        params.setMargins(2, 1, 0, 1);
//                        final String[] picList = pic.split(",");
//
//                        // final ImageView[] img = new
//                        // ImageView[picList.length];
//                        for (int j = 0; j < picList.length; j++) {
//                            ImageView img = new ImageView(NewMealShopDetailsActivity.this);
//                            img.setLayoutParams(params);
//                            img.setScaleType(ScaleType.CENTER_CROP);
//                            SetImageLoader.initImageLoader(null, img, picList[j], "!180");
//                            img.setOnClickListener(new ImageOnClickLintener(j, picList));
//                            vh.img_container.addView(img);
//                        }
//
//                    }
//
//                    if (null != shopComment.getSuppComment()) {
//                        vh.tv_one_reply.setVisibility(View.VISIBLE);
//                        vh.tv_one_reply.setText(Html.fromHtml(NewMealShopDetailsActivity.this.getString(R.string.tv_supp_reply,
//                                shopComment.getSuppComment().get(0).getSupp_content())));
//                    } else {
//                        vh.tv_one_reply.setVisibility(View.GONE);
//                    }
//
//                    if (null != shopComment.getComment()) {
//
//                        vh.lin_second.setVisibility(View.VISIBLE);
//                        vh.tv_second_judge.setVisibility(View.VISIBLE);
//                        vh.tv_second_judge.setText(Html.fromHtml(NewMealShopDetailsActivity.this
//                                .getString(R.string.tv_add_judge, shopComment.getComment().get(0).getContent())));
//                        if (null != shopComment.getSuppEndComment() && shopComment.getSuppEndComment().size() > 0) {
//                            vh.tv_second_reply.setVisibility(View.VISIBLE);
//                            vh.tv_second_reply
//                                    .setText(Html.fromHtml(NewMealShopDetailsActivity.this.getString(R.string.tv_supp_reply,
//                                            shopComment.getSuppEndComment().get(0).getSupp_content())));
//                        } else {
//                            vh.tv_second_reply.setVisibility(View.GONE);
//                        }
//                        String pics = shopComment.getComment().get(0).getPic();
//                        if (TextUtils.isEmpty(pics) == false) {
//                            final String[] spic = pics.split(",");
//                            vh.two_container.setVisibility(View.VISIBLE);
//                            vh.two_container.removeAllViews();
//                            LayoutParams params = new LayoutParams(80, 80);
//                            params.setMargins(2, 1, 0, 1);
//                            for (int j = 0; j < spic.length; j++) {
//                                ImageView img = new ImageView(NewMealShopDetailsActivity.this);
//                                img.setLayoutParams(params);
//                                img.setScaleType(ScaleType.CENTER_CROP);
//                                SetImageLoader.initImageLoader(null, img, spic[j], "!180");
//                                img.setOnClickListener(new ImageOnClickLintener(j, spic));
//                                vh.two_container.addView(img);
//                            }
//                        } else {
//                            vh.two_container.setVisibility(View.GONE);
//                        }
//
//                    } else {
//                        vh.lin_second.setVisibility(View.GONE);
//                        vh.tv_second_judge.setVisibility(View.GONE);
//                    }
//                }
//            }
//            return v;
//        }
//
//        /**
//         * 浏览商品详情的签到接口
//         */
//        private void sign() {
//            String ssType = "";
//            switch (SignFragment.jiangliID) {
//                case 3:
//                    ssType = "元优惠券";
//                    break;
//                case 4:
//                    ssType = "积分";
//                    break;
//                case 5:
//                    ssType = "元";
//                    break;
//
//                default:
//                    break;
//            }
//            final String ss = ssType;
//            new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, 0) {
//
//                @Override
//                protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                        throws Exception {
//
//                    return ComModel2.getSignIn(NewMealShopDetailsActivity.this, false, false, SignFragment.signIndex, SignFragment.doClass);
//
//                }
//
//                protected boolean isHandleException() {
//                    return true;
//                }
//
//                ;
//
//                @Override
//                protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result,
//                                             Exception e) {
//                    super.onPostExecute(context, result, e);
//                    if (e == null && result != null) {
//                        if (SignFragment.doNum > 1) {//需要奖励分多次发放
//                            if (isforcelook) {//强制浏览普通商品
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "forcelookNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookNum" + YCache.getCacheUser(context).getUser_id(), "" + forcelookNum);
//
//                                if (forcelookNum < Integer.parseInt(singvalue)) {//小于要求的分享次数
//                                    ToastUtil.showLongText(NewMealShopDetailsActivity.this, "浏览完成，奖励"
////								+new java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//                                            + SignFragment.jiangliValue
//                                            + ss + ",还有" + (Integer.parseInt(singvalue) - forcelookNum) + "次浏览机会喔~");
//                                } else if (forcelookNum >= Integer.parseInt(singvalue)) {
//                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(NewMealShopDetailsActivity.this, R.style.DialogQuheijiao2, "liulan_sign_finish",
//                                            new DecimalFormat("0.##").format(Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//                                    dialog.show();
////									//签到完成之后 将本地计数数据清零 其他浏览任务开始时候就是计数为0
////									SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"forcelookNum"+YCache.getCacheUser(context).getUser_id(), "0");
////									SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"forcelookNum"+YCache.getCacheUser(context).getUser_id());
//                                }
//                            } else if (isforcelookMatch) {//强制浏览搭配商品
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "forcelookMatchNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "forcelookMatchNum" + YCache.getCacheUser(context).getUser_id(), "" + forcelookMatchNum);
//
//                                if (forcelookMatchNum < Integer.parseInt(singvalue)) {//小于要求的分享次数
//                                    ToastUtil.showLongText(NewMealShopDetailsActivity.this, "浏览完成，奖励"
////								+new java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//                                            + SignFragment.jiangliValue
//                                            + ss + ",还有" + (Integer.parseInt(singvalue) - forcelookMatchNum) + "次浏览机会喔~");
//                                } else if (forcelookMatchNum >= Integer.parseInt(singvalue)) {
//                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(NewMealShopDetailsActivity.this, R.style.DialogQuheijiao2, "liulan_sign_finish",
//                                            new DecimalFormat("0.##").format(Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//                                    dialog.show();
////									//签到完成之后 将本地计数数据清零 其他浏览任务开始时候就是计数为0
////									SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"forcelookMatchNum"+YCache.getCacheUser(context).getUser_id(), "0");
////									SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"forcelookMatchNum"+YCache.getCacheUser(context).getUser_id());
//                                }
//                            } else if (isSignActiveShop) {//强制浏览活动商品
//
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "signActiveShopNowTime" + YCache.getCacheUser(context).getUser_id(), df.format(new Date()));
//                                SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex + "signActiveShopNum" + YCache.getCacheUser(context).getUser_id(), "" + signActiveShopNum);
//
//                                if (signActiveShopNum < Integer.parseInt(singvalue)) {//小于要求的分享次数
//                                    ToastUtil.showLongText(NewMealShopDetailsActivity.this, "浏览完成，奖励"
////											+new java.text.DecimalFormat("#0.00").format(Double.valueOf(SignFragment.jiangliValue)/Integer.parseInt(singvalue))
//                                            + SignFragment.jiangliValue
//                                            + ss + ",还有" + (Integer.parseInt(singvalue) - signActiveShopNum) + "次浏览机会喔~");
//                                } else if (signActiveShopNum >= Integer.parseInt(singvalue)) {
//                                    NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(NewMealShopDetailsActivity.this, R.style.DialogQuheijiao2, "liulan_sign_finish",
//                                            new DecimalFormat("0.##").format(Double.parseDouble(SignFragment.jiangliValue) * SignFragment.doNum) + ss);
//                                    dialog.show();
////									//签到完成之后 将本地计数数据清零 其他浏览任务开始时候就是计数为0
////									SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"signActiveShopNum"+YCache.getCacheUser(context).getUser_id(), "0");
////									SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"signActiveShopNum"+YCache.getCacheUser(context).getUser_id());
//                                }
//                            }
//
//                        } else {
//                            //其他奖励 一次性奖励
//                            NewSignCommonDiaolg dialog = new NewSignCommonDiaolg(NewMealShopDetailsActivity.this, R.style.DialogQuheijiao2, "liulan_sign_finish", SignFragment.jiangliValue + ss);
//                            dialog.show();
////							//签到完成之后 将本地计数数据清零 其他浏览任务开始时候就是计数为0
////							if(isforcelook){
////								SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"forcelookNum"+YCache.getCacheUser(context).getUser_id(), "0");
////								SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"forcelookNum"+YCache.getCacheUser(context).getUser_id());
////							}else if(isforcelookMatch){
////								SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"forcelookMatchNum"+YCache.getCacheUser(context).getUser_id(), "0");
////								SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"forcelookMatchNum"+YCache.getCacheUser(context).getUser_id());
////							}else if(isSignActiveShop){
////								SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, SignFragment.signIndex+"signActiveShopNum"+YCache.getCacheUser(context).getUser_id(), "0");
////								SharedPreferencesUtil.removeData(context, SignFragment.signIndex+"signActiveShopNum"+YCache.getCacheUser(context).getUser_id());
////							}
//                        }
//                    }
//                }
//
//            }.execute();
//        }
//
//        @Override
//        public View getHeaderView(int position, View view, ViewGroup parent) {
//            HeaderViewHolder vh;
//            if (view == null) {
//                vh = new HeaderViewHolder();
//                view = LayoutInflater.from(NewMealShopDetailsActivity.this).inflate(R.layout.header_item, parent, false);
//                vh.topOne = (ShopTopClickView) view.findViewById(R.id.top_one);
//                if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
//                    vh.topOne.setText();
//                } else {
//                    vh.topOne.setText2(setEva_count_z);
//                }
//
//                vh.topTwo = (ShowHoriontalView) view.findViewById(R.id.top_two);
//                vh.topOne.setCheckLintener(NewMealShopDetailsActivity.this);
//                vh.topTwo.setOnClickLintener(NewMealShopDetailsActivity.this);
//                vh.title = (TextView) view.findViewById(R.id.title);
//                vh.topOne.setBackgroundColor(Color.WHITE);
//                vh.title.setBackgroundColor(Color.WHITE);
//                vh.topTwo.setBackgroundColor(Color.WHITE);
//
//                // vh.title.setVisibility(view.GONE);
//                vh.topTwo.setList(listTitle);
//                view.setTag(vh);
//            } else {
//                vh = (HeaderViewHolder) view.getTag();
//            }
//
//            if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//
//                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
//                    vh.topOne.setVisibility(View.VISIBLE);
//                    vh.topOne.setIndex(check);
//                    vh.title.setText("商品介绍");
//                    vh.topTwo.setVisibility(View.GONE);
//                    isShopTitle = false;
//                    return view;
//                }
//
//            } else {
//                if (position < images.length) {
//                    vh.topOne.setVisibility(View.VISIBLE);
//                    vh.topOne.setIndex(check);
//                    vh.title.setText("商品介绍");
//                    vh.topTwo.setVisibility(View.GONE);
//                    isShopTitle = false;
//                    return view;
//                }
//            }
//            isShopTitle = true;
//            vh.title.setText("商品推荐");
//            vh.topOne.setVisibility(View.GONE);
//            vh.topTwo.setVisibility(View.VISIBLE);
//            vh.topTwo.setIndex(titleCheck);
//            return view;
//        }
//
//        @Override
//        public long getHeaderId(int position) {
//            if (check != 0) {
//                return 0;
//            }
//            if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                if (position < imageTag1.length + imageTag2.length + imageTag3.length) {
//                    if (isMeal) {
//
//                        if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//                            // rlBottom.setVisibility(View.VISIBLE);
//                            // Animation animation =
//                            // AnimationUtils.loadAnimation(
//                            // NewMealShopDetailsActivity.this,
//                            // R.anim.shop_bottom_show);
//                            // rlBottom.startAnimation(animation);
//                            ll_bottem.clearAnimation();
//                            ll_bottem.startAnimation(animationShow);
//                            // rl_retain.startAnimation(animationShow);
//                        }
//                    }
//
//                    if ("SignShopDetail".equals(signShopDetail)) {
//
//                        if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//                            // rlBottom.setVisibility(View.VISIBLE);
//                            // Animation animation =
//                            // AnimationUtils.loadAnimation(
//                            // NewMealShopDetailsActivity.this,
//                            // R.anim.shop_bottom_show);
//                            // rlBottom.startAnimation(animation);
//                            rlBottom.clearAnimation();
//                            rlBottom.startAnimation(baoyou_animationShow);
//
//                            // if (!"SignShopDetail".equals(signShopDetail)) {
//                            // ll_bottem.clearAnimation();
//                            // ll_bottem.startAnimation(animationShow);
//                            // }
//                            // rl_retain.startAnimation(animationShow);
//                        }
//                    }
//                    return 0;
//                } else {
//                    if (isMeal) {
//                        if (ll_bottem.getVisibility() == View.VISIBLE && isAnim == false) {
//                            // Animation animation =
//                            // AnimationUtils.loadAnimation(
//                            // NewMealShopDetailsActivity.this,
//                            // R.anim.shop_bottom_gone);
//                            // rlBottom.startAnimation(animation);
//                            // rlBottom.setVisibility(View.GONE);
//                            ll_bottem.clearAnimation();
//                            ll_bottem.startAnimation(animationGone);
//
//                            // rl_retain.startAnimation(animationGone);
//                        }
//                        return 1;
//                    } else {
//                        if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//                            // Animation animation =
//                            // AnimationUtils.loadAnimation(
//                            // NewMealShopDetailsActivity.this,
//                            // R.anim.shop_bottom_gone);
//                            // rlBottom.startAnimation(animation);
//                            // rlBottom.setVisibility(View.GONE);
//                            rlBottom.clearAnimation();
//                            rlBottom.startAnimation(baoyou_animationGone);
//
//							/*
//                             * if (!"SignShopDetail".equals(signShopDetail)) {
//							 * rl_retain.clearAnimation();
//							 * rl_retain.startAnimation(animationGone); }
//							 */
//                            // rl_retain.startAnimation(animationGone);
//                        }
//                        return 1;
//                    }
//                    // if (rlBottom.getVisibility() == View.VISIBLE && isAnim ==
//                    // false) {
//                    // // Animation animation = AnimationUtils.loadAnimation(
//                    // // NewMealShopDetailsActivity.this,
//                    // // R.anim.shop_bottom_gone);
//                    // // rlBottom.startAnimation(animation);
//                    // // rlBottom.setVisibility(View.GONE);
//                    // rlBottom.clearAnimation();
//                    // rlBottom.startAnimation(animationGone);
//                    //
//                    // if (!"SignShopDetail".equals(signShopDetail)) {
//                    // rl_retain.clearAnimation();
//                    // rl_retain.startAnimation(animationGone);
//                    // }
//                    //// rl_retain.startAnimation(animationGone);
//                    // }
//                    // return 1;
//                }
//
//            } else {
//                if (position < images.length) {
//
//                    if (rlBottom.getVisibility() == View.GONE && isAnim == false) {
//                        // rlBottom.setVisibility(View.VISIBLE);
//                        // Animation animation = AnimationUtils.loadAnimation(
//                        // NewMealShopDetailsActivity.this,
//                        // R.anim.shop_bottom_show);
//                        // rlBottom.startAnimation(animation);
//                        rlBottom.clearAnimation();
//                        rlBottom.startAnimation(animationShow);
//                    }
//
//                    return 0;
//                } else {
//                    if (rlBottom.getVisibility() == View.VISIBLE && isAnim == false) {
//                        // Animation animation = AnimationUtils.loadAnimation(
//                        // NewMealShopDetailsActivity.this,
//                        // R.anim.shop_bottom_gone);
//                        // rlBottom.startAnimation(animation);
//                        // rlBottom.setVisibility(View.GONE);
//                        rlBottom.clearAnimation();
//                        rlBottom.startAnimation(animationGone);
//                    }
//                    return 1;
//                }
//
//            }
//        }
//
//    }
//
//    private static class ItemViewHolder {
//
//        View imageGroup;
//        View shopItem;
//        ItemView left;
//        ItemView right;
//        ImageView image, imageTag;
//
//        SizeView sView;
//        SizeView2 sView2;
//        View eView;
//
//        ProgressBar pb_color_count, pb_type_count, pb_work_count, pb_cost_count;// 没有色差，
//        // 版型好看，
//        // 做工不错，
//        // 性价比好
//        TextView tv_color_count, tv_type_count, tv_work_count, tv_cost_count;
//
//        LinearLayout viewContainer;
//
//        LinearLayout lin_nodata;
//
//        View bai;
//
//        RoundImageButton img_user_header;
//        TextView tv_user;
//        TextView tv_evaluate;
//        TextView tv_date;
//        TextView tv_descri;
//        TextView tv_size_color;
//        LinearLayout img_container;
//
//        TextView tv_one_reply;
//        TextView tv_second_judge;
//        TextView tv_second_reply;
//        LinearLayout lin_second;
//
//        RatingBar bar;
//
//        View evaView;
//
//        View diver;
//
//        ImageView sizeHint;
//
//        LinearLayout two_container;
//
//        MealRecomenView mealRView;
//    }
//
//    private class ImageOnClickLintener implements OnClickListener {
//
//        private int position;
//
//        private String[] urls;
//
//        public ImageOnClickLintener(int position, String[] urls) {
//            super();
//            this.position = position;
//            this.urls = urls;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//            Intent intent = new Intent(context, ImagePagerActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putStringArray("urls", urls);
//            bundle.putInt("index", position);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//
//    }
//
//    // private ImageView imgIcon;
//
//    /**
//     * @Description: 创建动画层 @param @return void @throws
//     */
//    private ViewGroup createAnimLayout() {
//        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
//        LinearLayout animLayout = new LinearLayout(this);
//        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
//                LayoutParams.MATCH_PARENT);
//        animLayout.setLayoutParams(lp);
//        // animLayout.setId(R.id.age);
//        animLayout.setBackgroundResource(android.R.color.transparent);
//        rootView.addView(animLayout);
//        return animLayout;
//    }
//
//    private View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
//        // vg.removeAllViews();
//        int x = location[0];
//        int y = location[1];
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeAllViews();
//        }
//        vg.addView(view);
//        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//        lp.leftMargin = x;
//        lp.topMargin = y;
//        view.setLayoutParams(lp);
//        return view;
//    }
//
//    private ViewGroup anim_mask_layout;
//
//    private ImageView img;
//
//    private void setAnim(View v) {
//        Animation mScaleAnimation = new ScaleAnimation(1.5f, 0.1f, 1.5f, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f,
//                Animation.RELATIVE_TO_SELF, 0.1f);
//        mScaleAnimation.setDuration(1000);
//        mScaleAnimation.setFillAfter(true);
//
//        int[] start_location = new int[]{0, height / 2};
//        // rlBottom.getLocationInWindow(start_location);
//        // ViewGroup vg = (ViewGroup) imgIcon.getParent();
//        // vg.removeView(imgIcon);
//
//        // 将组件添加到我们的动画层上
//        View view = addViewToAnimLayout(anim_mask_layout, img, start_location);
//        int[] end_location = new int[2];
//        tv_cart_count.getLocationInWindow(end_location);
//        // 计算位移
//        int endX = end_location[0];
//        int endY = end_location[1] - start_location[1];
//
//        Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);// 移动
//        mTranslateAnimation.setDuration(1000);
//
//        AnimationSet mAnimationSet = new AnimationSet(false);
//        // 这块要注意，必须设为false,不然组件动画结束后，不会归位。
//        mAnimationSet.setFillAfter(false);
//        mAnimationSet.addAnimation(mScaleAnimation);
//        mAnimationSet.addAnimation(mTranslateAnimation);
//        view.startAnimation(mAnimationSet);
//
//        mTranslateAnimation.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // tvNumber.setText(goodsNumber+"");
//                anim_mask_layout.removeAllViews();
//
//                ShopCartDao dao = new ShopCartDao(context);
//                // int count = dao.queryCartCount(context);
//                int count = 0;
//                if (isMeal) {
//                    count = dao.queryCartSpecialCount(context);
//                } else {
//                    count = dao.queryCartCommonCount(context);
//                }
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    tv_cart_count.setText(/* mealMap.get("cart_count") */count + "");
//
//                    tv_time_count_down.setVisibility(View.VISIBLE); // 显示倒计时
//                    tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                } else {
//                    tv_cart_count.setText(/* shop.getCart_count() */count + "");
//
//                    tv_time_count_down.setVisibility(View.VISIBLE); // 显示倒计时
//                    tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//    }
//
//    private static class HeaderViewHolder {
//
//        ShopTopClickView topOne;
//
//        ShowHoriontalView topTwo;
//
//        TextView title;
//    }
//
//    private Animation animationGone; // 上拉隐藏底部按钮
//    private Animation animationShow;// 下拉显示底部按钮
//
//    private boolean isMembers = false;// 是否是会员商品
//
//    private RelativeLayout rrr;
//
//    private String singvalue;// 强制浏览数量
//    private int forcelookNum;//强制浏览的计数
//    private int forcelookMatchNum;//搭配商品强制浏览的计数
//    private int signActiveShopNum;//活动商品强制浏览的计数
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        headerView = LayoutInflater.from(NewMealShopDetailsActivity.this).inflate(R.layout.shop_header_newmeal, null);
//
//
//        signShopDetail = getIntent().getStringExtra("SignShopDetail");
//        AppManager.getAppManager().addActivity(this);
//
//        setContentView(R.layout.activity_newmeal_shop_details);
//        isforcelook = getIntent().getBooleanExtra("isforcelook", false);
//        isforcelookMatch = getIntent().getBooleanExtra("isforcelookMatch", false);
//        isSignActiveShop = getIntent().getBooleanExtra("isSignActiveShop", false);
//        isSignActiveShopScan = getIntent().getBooleanExtra("isSignActiveShopScan", false);
////		virtual_sales = getIntent().getStringExtra("virtual_sales");
//        shareWaitDialog = new PublicToastDialog(this, R.style.DialogStyle1, "");
//        if (isforcelook == true || isforcelookMatch || (isSignActiveShop && SignFragment.doType == 4 && isSignActiveShopScan)) {////活动商品并且是浏览个数的任务
//
////			String value = SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, Pref.SINGVALUE, "a,a");
//            String value = SignFragment.doValue;
//            String values[] = value.split(",");
//            if (values.length > 1) {
//                singvalue = values[1];
//                if (!Pattern.compile("^\\+?[1-9][0-9]*$").matcher(singvalue).find()) {
//                    singvalue = "" + SignFragment.doNum;
//                }
//
//            } else {
//                singvalue = "" + SignFragment.doNum;
//            }
//
//            xunBaoIv = (ImageView) findViewById(R.id.scoll_xunbao);
//            df = new SimpleDateFormat("yyyy-MM-dd");
//            String forceLook = SharedPreferencesUtil.getStringData(context, Pref.FORCELOOKSCOLL, "0");
//            long forceLookTime = Long.valueOf(forceLook);
//            if ("0".equals(forceLook) || !df.format(new Date()).equals(df.format(new Date(forceLookTime)))) {
//                XunBaoScollDialog dialog = new XunBaoScollDialog(NewMealShopDetailsActivity.this, xunBaoIv);
//                dialog.show();
//                SharedPreferencesUtil.saveStringData(context, Pref.FORCELOOKSCOLL, System.currentTimeMillis() + "");
//            } else {
//                xunBaoIv.setVisibility(View.VISIBLE);
//            }
//
//
//        }
//
//        shopCart = getIntent().getBooleanExtra("ShopCart", false);
//        titleId = getIntent().getStringExtra("id");
//
//        number_sold = getIntent().getStringExtra("number_sold"); // 取出是否抢完的值
//
//        getWindownPixes();
//        context = this;
//        initView();
//        if (number_sold != null) {
//            if (number_sold.equals("none")) {
//                // tv_buy.setBackgroundColor(getResources().getColor(
//                // R.color.gray_white));
//                tv_shop_car.setTextColor(getResources().getColor(R.color.white_white));
//                tv_shop_car.setBackgroundColor(getResources().getColor(R.color.gray_white));
//
//            } else {
//                // tv_buy.setBackgroundColor(getResources().getColor(
//                // R.color.zero_shop_choice));
//                tv_shop_car.setTextColor(getResources().getColor(R.color.white_white));
//                tv_shop_car.setBackgroundColor(getResources().getColor(R.color.zero_shop_choice));
//            }
//        }
//
//        YDBHelper helper = new YDBHelper(this);
//        String sql = "select * from sort_info where p_id = 0 and is_show = 1 order by sequence";
//        listTitle = helper.query(sql);
//        int a = 0;
//        int b = 0;
//        for (int i = 0; i < listTitle.size(); i++) {
//            String sort_name = listTitle.get(i).get("sort_name");
//            if ("上衣".equals(sort_name)) {
//                a = i;
//            } else if ("外套".equals(sort_name)) {
//                b = i;
//            }
//        }
//
//        HashMap<String, String> listTitle_temp1 = (HashMap<String, String>) listTitle.get(a);
//        HashMap<String, String> listTitle_temp2 = (HashMap<String, String>) listTitle.get(b);
//        if (b > a) {//上衣在外套前面时就调换
//            listTitle.remove(a);
//            listTitle.add(a, listTitle_temp2);
//            listTitle.remove(b);
//            listTitle.add(b, listTitle_temp1);
//        }
//        initImageLoader();
//        // String code=null;
//        // String scheme=getIntent().getScheme();
//        // if("shopDetail".equals(scheme)){
//        // code=getIntent().getData().getQueryParameter("id");
//        // }else{
//        code = getIntent().getStringExtra("code");
//        isMeal = getIntent().getBooleanExtra("isMeal", false);
//        isMembers = getIntent().getBooleanExtra("isMembers", false);
//
//        // signShopDetail = getIntent().getStringExtra("SignShopDetail");
//        // signShopDetail = getIntent().getStringExtra("SignShopDetail");
//
//        signType = getIntent().getIntExtra("signType", 0);
//        signValue = getIntent().getStringExtra("valueBao");
//        valueDuo = getIntent().getStringExtra("valueDuo");
//
//        img_cart_new = (ImageView) findViewById(R.id.img_cart_new);
//
//        instance = this;
//        if (isMeal) {// 套餐
//            queryShopMeal();
//            // tv_shop_car.setVisibility(View.VISIBLE);
//            // tv_buy.setText("分享购买");
//            LinearLayout ll_left_left = (LinearLayout) findViewById(R.id.ll_left_left);
//            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 2);
//            ll_left_left.setLayoutParams(lp);
//            LinearLayout ll_left = (LinearLayout) findViewById(R.id.ll_left);
//            LayoutParams lp2 = new LayoutParams(0, DP2SPUtil.dp2px(context, 49), 2);
//            ll_left.setLayoutParams(lp2);
//            lin_add_like.setVisibility(View.GONE);
//            pos = getIntent().getStringExtra("pos");
//            findViewById(R.id.divider).setVisibility(View.GONE);
//            // rl_heaed_all.setVisibility(View.GONE);
//        } else if ("SignShopDetail".equals(signShopDetail)) {
//
////			queryShopSign();
//            tv_shop_car.setVisibility(View.GONE);
//            // tv_buy.setText("分享购买");
//            lin_add_like.setVisibility(View.GONE);
//            // tv_buy.setVisibility(View.GONE);
//            sign_buy.setVisibility(View.VISIBLE);
//            // rl_heaed_all.setVisibility(View.GONE);
//        } else {
//            lin_add_like.setVisibility(View.VISIBLE);
//            if (isMembers) {
//                tv_shop_car.setVisibility(View.GONE);
//                // tv_buy.setText("分享购买");
//                lin_add_like.setVisibility(View.GONE);
//                img_fenx.setVisibility(View.GONE);
//                img_cart.setVisibility(View.GONE);
//                // rl_heaed_all.setVisibility(View.GONE);
//            }
//            if (!TextUtils.isEmpty(code)) {
//                LogYiFu.e("STARTTIME", System.currentTimeMillis() + "");
//            } else {
//                finish();
//            }
//        }
//
//
//    }
//
//    private String pos;
//
//    private HashMap<String, Object> mealMap;
//
//
//    /**
//     * 查询套餐详情
//     *
//     * @param code
//     */
//
//    // private double sePrice;
//    private boolean isAnim = false;
//
//    public void queryShopMeal() {
//
//        bb = new SAsyncTask<Void, Void, HashMap<String, Object>>(NewMealShopDetailsActivity.this, R.string.wait) {
//
//            @Override
//            protected void onPreExecute() {
//                // TODO Auto-generated method stub
//                super.onPreExecute();
//                LoadingDialog.show(NewMealShopDetailsActivity.this);
//            }
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel.queryShopMeal(context, code);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e != null || result == null) {
//                    return;
//                } else {
//                    rrr.setBackgroundColor(Color.WHITE);
//                    tv_shop_car.setVisibility(View.VISIBLE);
//                    tv_shop_car_fake.setVisibility(View.GONE);
//                }
//
//                mealMap = result;
//
//                titleCheck = 0;
//
//                if (first_meal == false) {
//                    first_meal = true;
//
//                    MealType = (String) mealMap.get("p_type");
//
//                    // if (shop.getLike_id() == -1) {
//                    img_xin.setImageResource(R.drawable.icon_xihuan);
//
//                    TextView tv = (TextView) headerView.findViewById(R.id.tv_mail_free);
//                    headerView.findViewById(R.id.shop_name).setVisibility(View.GONE);
//                    headerView.findViewById(R.id.one_position).setBackgroundColor(Color.WHITE);
//                    tv.setText("全场特价");
//                    width = NewMealShopDetailsActivity.this.getResources().getDisplayMetrics().widthPixels;
//
//                    adapter = new MyAdapter();
//
//                    list = (List<Shop>) mealMap.get("shopList");
//
//                    Double price = 0d;
//                    // sePrice = 0d;
//
//                    if (list == null) {
//                        list = new ArrayList<Shop>();
//                    }
//
//                    if (list.size() != 0) {
//
//                        for (int i = 0; i < list.size(); i++) {
//                            Shop shop = list.get(i);
//                            price += shop.getShop_price();
//                            // sePrice += shop.getShop_se_price();
//                            String[] imgs = shop.getShop_pic().split(",");
//                            StringBuffer sb = new StringBuffer();
//                            for (int j = 0; j < imgs.length; j++) {
//                                if (imgs[j].contains("reveal_") || imgs[j].contains("real_")
//                                        || imgs[j].contains("detail_")) {
//                                    sb.append(imgs[j] + ",");
//                                }
//                            }
//                            switch (i) {
//                                case 0: {
//                                    imageTag1 = sb.toString().substring(0, sb.length() - 1).split(",");
//
//                                }
//                                break;
//                                case 1: {
//                                    imageTag2 = sb.toString().substring(0, sb.length() - 1).split(",");
//
//                                }
//                                break;
//                                case 2: {
//                                    imageTag3 = sb.toString().substring(0, sb.length() - 1).split(",");
//
//                                }
//                                break;
//
//                                default:
//                                    break;
//                            }
//                        }
//                    }
//
//                    imageTag1 = imageTag1 == null ? new String[0] : imageTag1;
//                    imageTag2 = imageTag2 == null ? new String[0] : imageTag2;
//                    imageTag3 = imageTag3 == null ? new String[0] : imageTag3;
//                    headerView.findViewById(R.id.img_header).getLayoutParams().height = width * 9 / 6;
//                    heights = width * 9 / 6;
//                    String def_pic = (String) mealMap.get("def_pic");
//                    if (!TextUtils.isEmpty(def_pic)) {
//                        SetImageLoader.initImageLoader(null, (ScaleImageView) headerView.findViewById(R.id.img_header),
//                                def_pic, "!450");
//                        headerView.findViewById(R.id.img_header).setOnClickListener(new OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopImageActivity.class);
//                                intent.putExtra("url", (String) mealMap.get("def_pic"));
//                                // intent.putExtra("shop", shop);
//                                intent.putExtra("code", code);
//                                intent.putExtra("mealMap", mealMap);
//                                intent.putExtra("isMeal", isMeal);
//
//                                intent.putExtra("ShopCart", shopCart);
//
//                                intent.putExtra("number_sold", number_sold);
//
//                                startActivityForResult(intent, 1080);
//                            }
//                        });
//                    }
//                    if (list.size() == 1) {
//
//                        ((TextView) headerView.findViewById(R.id.tv_meal_name)).setText(list.get(0).getShop_name(0));
//
//                    } else {
//                        ((TextView) headerView.findViewById(R.id.tv_meal_name))
//                                .setText(TextUtils.isEmpty(mealMap.get("name").toString()) ? ""
//                                        : mealMap.get("name").toString());
//                    }
//
//                    (headerView.findViewById(R.id.ll_daojishi)).setVisibility(View.GONE);
//                    (headerView.findViewById(R.id.rl_shuoming)).setVisibility(View.GONE);
//                    (headerView.findViewById(R.id.tv_xiang)).setVisibility(View.GONE);
//                    (headerView.findViewById(R.id.tv_xiang_left)).setVisibility(View.GONE);
//
//                    (headerView.findViewById(R.id.tv_return_money)).setVisibility(View.GONE);
//                    (headerView.findViewById(R.id.tv_return_money_fan)).setVisibility(View.GONE);
//                    (headerView.findViewById(R.id.post_lay)).setVisibility(View.VISIBLE);
//
//                    ((TextView) headerView.findViewById(R.id.post_price)).setText("¥"
//                            + new DecimalFormat("#0.0").format(Double.parseDouble(mealMap.get("postage").toString()))
//                            + "元");
//                    // items.barFen.setText(shop.get);
//                    ((TextView) headerView.findViewById(R.id.tv_price)).setText("¥"
//                            + new DecimalFormat("#0.0").format(Double.parseDouble(mealMap.get("price").toString())));
//
//                    String shop_price = "¥" + new DecimalFormat("#0.0").format(price);
//                    if (!TextUtils.isEmpty(shop_price)) {
//                        ToastUtil.addStrikeSpan((TextView) headerView.findViewById(R.id.tv_sjprice), shop_price);
//                    }
//
//                    mListView.addHeaderView(headerView);
//                    mListView.setAdapter(adapter);
//
//                    mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                        private int myposition;
//
//                        private ImageButton aa2;
//                        // private RelativeLayout img_cart_top;
//                        // private LinearLayout img_fenx_top;
//
//                        // private ImageView lin_contact2;
//
//                        @Override
//                        public void onScrollStateChanged(AbsListView view, int arg1) {
//
//                            View childAt = view.getChildAt(0);
//                            switch (arg1) {
//                                case SCROLL_STATE_TOUCH_SCROLL:// 滚动之前
//                                    if (isShopTitle) {
//                                        break;
//                                    }
//                                    if (childAt == null) {
//                                        myposition = 0;
//                                    } else {
//                                        myposition = -childAt.getTop()
//                                                + view.getFirstVisiblePosition() * childAt.getHeight();
//                                    }
//                                    // myposition=view.getFirstVisiblePosition();
//                                    break;
//
//                                case SCROLL_STATE_FLING: // 滚动
//                                    if (isShopTitle) {
//                                        break;
//                                    }
//                                    int newPosition = 0;
//                                    if (childAt == null) {
//                                        newPosition = 0;
//                                    } else {
//                                        newPosition = -childAt.getTop()
//                                                + view.getFirstVisiblePosition() * childAt.getHeight();
//                                    }
//
//                                    if (newPosition > myposition) { // 向上滑动
//                                        if (ll_bottem.getVisibility() == View.VISIBLE && isAnim == false) {
//
//                                            ll_bottem.clearAnimation();
//                                            ll_bottem.startAnimation(animationGone);
//                                        }
//                                    } else if (newPosition < myposition) {// 向下滑
//                                        if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//
//                                            ll_bottem.clearAnimation();
//                                            ll_bottem.startAnimation(animationShow);
//                                        }
//                                    }
//                                    break;
//                                case SCROLL_STATE_IDLE:
//                                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                                        if (check == 0) {
//                                            if (isInit == false) {
//                                                index++;
//                                                mType = 2;
//                                                initData(titleCheck, index + "");
//                                            }
//
//                                        }
//                                    }
//
//                                    if (isShopTitle) {
//                                        break;
//                                    }
//
//                                    if (ll_bottem.getVisibility() == View.GONE && isAnim == false) {
//
//                                        ll_bottem.clearAnimation();
//                                        ll_bottem.startAnimation(animationShow);
//                                    }
//
//                                    break;
//                            }
//                        }
//
//                        @Override
//                        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
//                            int perHeight = heights / 100;
//                            int currentY = 0;
//                            int viewTop = -1;
//                            aa2 = (ImageButton) findViewById(R.id.imgbtn_left_icon);
//                            lin_contact = (ImageView) findViewById(R.id.lin_contact);
//                            mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
//
//							/* 滚动title渐变的效果 */
//                            if (arg1 == 0) {// 当前第一位显示为1
//                                View childAt = arg0.getChildAt(0);// 这个是headerView
//                                if (childAt != null) {
//                                    currentY = childAt.getTop();
//                                }
//
//                            } else if (arg1 > 0) {
//                                currentY = heights;
//                            }
//
//                            if (currentY == 0) {
//                                rlTop.setBackgroundResource(R.drawable.zhezhao2x);
//                                aa2.setBackgroundResource(R.drawable.icon_fanhui);
//                                mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
//                                lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_white);
//
//                                rlTop.getBackground().setAlpha(255);
//                            }
//                            if (Math.abs(currentY) > 0 && Math.abs(currentY) < heights / 2) {
//
//                                aa2.setBackgroundResource(R.drawable.icon_fanhui);
//                                mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_white_new);
//                                lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_white);
//
//                                int i = (int) Math.abs(currentY);
//
//                                if (Math.abs(currentY) == 0) {
//                                    i = 1;
//                                }
//                                aa2.getBackground().setAlpha(255 - (i / 3));
//                                mShuaixuanNew.getBackground().setAlpha(255 - (i / 3));
//                                lin_contact.getBackground().setAlpha(255 - (i / 3));
//
//
//                            }
//
//                            if (Math.abs(currentY) >= heights / 2 && Math.abs(currentY) < heights) {
//
//                                aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
//                                mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
//                                lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
//
//                                int i = (int) Math.abs(currentY) / perHeight;
//                                if (i > 100) {
//                                    i = 100;
//                                }
//                                aa2.getBackground().setAlpha(255 * i / 100);
//                                mShuaixuanNew.getBackground().setAlpha(255 * i / 100);
//                                lin_contact.getBackground().setAlpha(255 * i / 100);
//                                // img_cart_top.getBackground().setAlpha(
//                                // 255 * i / 100);
//                                // img_fenx_top.getBackground().setAlpha(
//                                // 255 * i / 100);
//                            }
//
//                            if (Math.abs(currentY) <= heights && Math.abs(currentY) > 0) {
//                                rlTop.setBackgroundColor(getResources().getColor(R.color.white));
//                                int i = (int) Math.abs(currentY) / perHeight;
//                                if (i > 100) {
//                                    i = 100;
//                                }
//                                rlTop.getBackground().setAlpha(255 * i / 100);
//                            }
//
//                            if (Math.abs(currentY) >= heights) {
//                                rlTop.getBackground().setAlpha(255);
//                                aa2.setBackgroundResource(R.drawable.icon_fanhui_black);
//                                mShuaixuanNew.setBackgroundResource(R.drawable.icon_shaixuan_new);
//                                lin_contact.setBackgroundResource(R.drawable.icon_lianxikefu_gray);
//                                lin_contact.getBackground().setAlpha(255);
//                                mShuaixuanNew.getBackground().setAlpha(255);
//
//                            }
//
//                        }
//                    });
//
//                    findViewById(R.id.search).setOnClickListener(new OnClickListener() {
//
//                        @Override
//                        public void onClick(View arg0) {
//                            // toggle();
//                        }
//                    });
//
//                    findViewById(R.id.shaixuan).setVisibility(View.GONE);
//                }
//
//                if (YJApplication.instance.isLoginSucess()) {
//
//                    // 查询特卖购物车倒计时
//                    // Long c_time_meal = (Long) mealMap.get("c_time");
//
//                    String time_c = SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0");
//                    long c_time_meal = Long.valueOf(time_c).longValue();
//
//                    // long s_time_fuwuqi = (Long) mealMap.get("s_time");
//                    long s_time_fuwuqi = System.currentTimeMillis();
//
//                    if (c_time_meal - s_time_fuwuqi > 0) {// 正数代表加入了购物车 显示倒计时
//                        // tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                        String c_time_cart = DateFormatUtils.format(
//                                /* Long.parseLong(mealMap.get("c_time") + "") */c_time_meal, "yyyy-MM-dd HH:mm:ss");
//                        String s_time = DateFormatUtils.format(
//								/* Long.parseLong(mealMap.get("s_time") + "") */System.currentTimeMillis(),
//                                "yyyy-MM-dd HH:mm:ss");
//
//                        try {
//                            if (timer_meal != null) {
//                                timer_meal.cancel();
//                            }
//                            task_meal = new TimerTask() {
//                                @Override
//                                public void run() {
//
//                                    runOnUiThread(new Runnable() { // UI thread
//                                        @Override
//                                        public void run() {
//                                        }
//                                    });
//                                }
//                            };
//                            timer_meal = new Timer();
//                            timer_meal.schedule(task_meal, 0, 1000); // 显示倒计时
//
//                            SimpleDateFormat df_meal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date d1_meal = df_meal.parse(c_time_cart);
//                            Date d2_meal = df_meal.parse(s_time);
//                            long diff_meal = d1_meal.getTime() - d2_meal.getTime();
//                        } catch (ParseException e1) {
//                            // TODO Auto-generated catch block
//                            e1.printStackTrace();
//                        }
//                    } else {// 负数代表没有加入购物车
//                    }
//                    if (tv_time_count_down_meal.getVisibility() == View.GONE) {
//                        rl_retain.setVisibility(View.GONE);
//                    }
//                    if (YJApplication.instance.isLoginSucess()) {
//
//                        ShopCartDao dao = new ShopCartDao(context);
//                        int count = 0;
//                        if (isMeal) {
//                            count = dao.queryCartSpecialCount(context);
//                        } else {
//                            count = dao.queryCartCommonCount(context);
//                        }
//                        // int count = dao.queryCartCount(context);
//
//                        if (/*
//							 * Integer.parseInt(mealMap.get("cart_count").
//							 * toString() )
//							 */count > 0) {
//                            tv_cart_count.setText(
//									/* mealMap.get("cart_count").toString() */count + "");// 设置购物车数量
//                            tv_cart_count.setVisibility(View.VISIBLE);
//
//                            if (c_time_meal - s_time_fuwuqi <= 0) {
//                                tv_time_count_down.setVisibility(View.GONE);
//                                tv_time_count_down_meal.setText("00:00");
//                                tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                            } else {
//                                tv_time_count_down.setVisibility(View.GONE);
//                                tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                            }
//
//                            if (countCommn == 0) {
//                                tv_time_count_down.setVisibility(View.GONE);
//                            }
//
//                            if (countMeal == 0) {
//                                tv_time_count_down_meal.setVisibility(View.GONE);
//                            }
//
//                        } else {
//                            tv_cart_count.setText(0 + "");
//                            tv_cart_count.setVisibility(View.GONE);
//                            tv_time_count_down.setVisibility(View.GONE); // TODO
//                            tv_time_count_down_meal.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            }
//
//        };
//        bb.execute();
//    }
//
//
//    private String code;
//
//    private boolean isMeal = false;// 是否是套餐
//
//    private void downloadPic(String picPath, int i) {
//        try {
//            URL url = new URL(YUrl.imgurl + picPath);
//            // 打开连接
//            URLConnection con = url.openConnection();
//            // 获得文件的长度
//            int contentLength = con.getContentLength();
//            // 输入流
//            InputStream is = con.getInputStream();
//            // 1K的数据缓冲
//            byte[] bs = new byte[8192];
//            // 读取到的数据长度
//            int len;
//            // 输出的文件流 /sdcard/yssj/
//            File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(i)) + ".jpg");
//            if (file.exists()) {
//                file.delete();
//            }
//            LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
//            OutputStream os = new FileOutputStream(file);
//            // 开始读取
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
//            LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
//            // 完毕，关闭所有链接
//            os.close();
//            is.close();
//        } catch (Exception e) {
//            LogYiFu.e("TAG", "下载失败");
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AppManager.activityStack.remove(this);
//        isPause = 1;
//        // instance = null;
//    }
//
//    private void initImageLoader() {
//
//
//    }
//
//    /***
//     * 得到屏幕宽度和高度 像素
//     */
//    private void getWindownPixes() {
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        width = dm.widthPixels;
//        height = dm.heightPixels;
//    }
//
//    private Handler handler;
//
//    private Handler newHandler;
//    private Handler shareHandler;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        HomeWatcherReceiver.registerHomeKeyReceiver(context);
//        SharedPreferencesUtil.saveStringData(context, Pref.TONGJI_TYPE, "1050");
//        if (!"SignShopDetail".equals(signShopDetail)) {
//            if (isSignActiveShop || isMeal) {
//                redShare.setVisibility(View.GONE);
//                moneyShare.setVisibility(View.GONE);
//            } else {
//                setShareAnim();
//            }
//        }
//        if (isMeal) {
//            queryShopMeal();
//        }
//
//        ShopCartDao dao = new ShopCartDao(context);
//        countCommn = dao.queryCartCommonCount(context);
//        countMeal = dao.queryCartSpecialCount(context);
//
//        LogYiFu.e("ShopDetailsActivity_onresume", "OK");
//        isPause = 0;
//        // ////////////////////////////////////////////////////////////////////////////////////////////////////
//        if (isMeal) {
//            shareHandler = new Handler();
//            shareHandler.postDelayed(shareRun, 30 * 1000);
//        }
//        if (isMeal || isMembers) {
//            return;
//        }
//
//        List<String> taskMap = YJApplication.instance.getTaskMap();
//        if (taskMap == null) {
//            taskMap = new ArrayList<String>();
//        }
//        int curr = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        if ((!taskMap.contains("17") || !YJApplication.instance.isLoginSucess())
//                && curr != getSharedPreferences("month", Context.MODE_PRIVATE).getInt("month", 0)
//                && (getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == Calendar.getInstance()
//                .get(Calendar.DAY_OF_WEEK)
//                || getSharedPreferences("week", Context.MODE_PRIVATE).getInt("week", 0) == 0)) {
//            getSharedPreferences("month", Context.MODE_PRIVATE).edit()
//                    .putInt("month", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//            getSharedPreferences("week", Context.MODE_PRIVATE).edit()
//                    .putInt("week", Calendar.getInstance().get(Calendar.DAY_OF_WEEK)).commit();
//
//        } else {
//            if (YJApplication.instance.isLoginSucess() == false
//                    || getSharedPreferences("dian", Context.MODE_PRIVATE).getInt("dian", 0) == curr) {
//                shareHandler = new Handler();
//                shareHandler.postDelayed(shareRun, 30 * 1000);
//                return;
//            }
//            UserInfo user = YCache.getCacheUser(context);
//            if (user.getHobby() == null || user.getHobby().equals("0")) {
//
//                newHandler = new Handler();
//                newHandler.postDelayed(r, 60 * 1000);
//            }
//
//        }
//
//    }
//
//    private void initView() {
//        mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
//        toDuoBaoIv = (ImageView) findViewById(R.id.to_duobao);
//        rrr = (RelativeLayout) findViewById(R.id.rrr);
//        redShare = (LinearLayout) findViewById(R.id.red_share_ll);
//        moneyShare = (ImageView) findViewById(R.id.money_share_iv);
//        // rrr.setBackgroundColor(Color.WHITE);
//
//        if (!"SignShopDetail".equals(signShopDetail)) {// 打个标记 这里以后节能改
//            rl_hava_twenty = (RelativeLayout) findViewById(R.id.rl_hava_twenty);
//            rl_hava_twenty.getBackground().setAlpha(130);
//        }
//
//        rlBottom = (RelativeLayout) findViewById(R.id.ray_bottom);
//
//
//        tv_shop_car_fake = (TextView) findViewById(R.id.tv_shop_car_fake);
//        tv_shop_car = (TextView) findViewById(R.id.tv_shop_car);
//        if (isSignActiveShop) {
//            tv_shop_car_fake.setText("立即购买");
//            tv_shop_car.setText("立即购买");
//        }
//        tv_shop_car.setVisibility(View.GONE);
//        tv_shop_car.setOnClickListener(this);
//
//        if (!"SignShopDetail".equals(signShopDetail)) {// 打个标记 这里以后节能改
//
//        }
//        sign_buy = (TextView) findViewById(R.id.sign_buy);
//        sign_buy.setOnClickListener(this);
//
//        lin_add_like = (LinearLayout) findViewById(R.id.lin_add_like);
//        lin_add_like.setOnClickListener(this);
//
//        if ("SignShopDetail".equals(signShopDetail)) {
//            img_cart_old = (ImageView) findViewById(R.id.img_cart);
//            img_cart_old.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // MobclickAgent.onEvent(context, "toshopcartclick");
//                    Intent intent3 = new Intent(NewMealShopDetailsActivity.this, ShopCartNewNewActivity.class);
//                    if (isMeal) {
//                        intent3.putExtra("where", "1");
//                    } else {
//                        intent3.putExtra("where", "0");
//                    }
//
//                    startActivityForResult(intent3, 235);
//
//                }
//            });
//        } else {
//            img_cart = (RelativeLayout) findViewById(R.id.img_cart);
//            img_cart.setOnClickListener(this);
//        }
//
//        findViewById(R.id.ray_bottom).setBackgroundColor(Color.WHITE);
//        tv_cart_count = (TextView) findViewById(R.id.tv_cart_count);// 购物车计数
//
//        if (!"SignShopDetail".equals(signShopDetail)) {
//            tv_time_count_down = (TextView) findViewById(R.id.tv_time_count_down);// 购物车数量旁的倒计时
//            tv_time_count_down.setVisibility(View.GONE);
//
//            tv_time_count_down_meal = (TextView) findViewById(R.id.tv_time_count_down_meal);// 套餐购物车数量旁的倒计时
//            tv_time_count_down_meal.setVisibility(View.GONE);
//        }
//
//        if ("SignShopDetail".equals(signShopDetail)) {
//            lin_contact_old = (RelativeLayout) findViewById(R.id.lin_contact);
//            lin_contact_old.setOnClickListener(this);
//
//        } else {
//            lin_contact = (ImageView) findViewById(R.id.lin_contact);
//            lin_contact.setOnClickListener(this);
//            mShuaixuanNew = (ImageButton) findViewById(R.id.shaixuan);
//        }
//
//        if ("SignShopDetail".equals(signShopDetail)) {
//            img_fenx_old = (ImageView) findViewById(R.id.img_fenx);// 分享
//            img_fenx_old.setOnClickListener(this);
//        } else {
//            img_fenx = (RelativeLayout) findViewById(R.id.img_fenx);// 分享
//            img_fenx.setOnClickListener(this);
//        }
//
//        img_xin = (ImageView) findViewById(R.id.img_xin);// 加心
//
//        if (!"SignShopDetail".equals(signShopDetail)) {
//
//            ll_bottem = (LinearLayout) findViewById(R.id.ll_bottem);
//
//            rl_retain = (RelativeLayout) findViewById(R.id.rl_retain); // 保留30秒钟的黑色条
//            rl_retain.setOnClickListener(this);
//            rl_retain.getBackground().setAlpha(204);
//            // handler类接收数据
//            final Handler handler = new Handler() {
//                public void handleMessage(Message msg) {
//                    if (msg.what == 1) {
//                        rl_retain.setVisibility(View.GONE);
//                        // rl_retain.getBackground().setAlpha(0);
//                    }
//                }
//
//                ;
//            };
//        }
//
//
//        img_addxin = (ImageView) findViewById(R.id.addxin);
//
//        img_back = (LinearLayout) findViewById(R.id.img_back);
//        img_back.setOnClickListener(this);
//
//        ShareActionProvider provider = new ShareActionProvider(this);
//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_TEXT, "不错的商品哦，值得你一看，赶紧来吧！！！");
//        sendIntent.setType("text/plain");
//        sendIntent.setData(Uri.parse(YUrl.QUERY_SHOP_DETAILS));
//        provider.setShareIntent(sendIntent);
//
//        mListView = (StickyListHeadersListView) findViewById(R.id.data);
//        rlTop = (LinearLayout) findViewById(R.id.ray_top);
//
//        rlTop.setBackgroundResource(R.drawable.zhezhao2x);
//
//
//        animationGone = AnimationUtils.loadAnimation(NewMealShopDetailsActivity.this, R.anim.shop_bottom_gone);
//        animationShow = AnimationUtils.loadAnimation(NewMealShopDetailsActivity.this, R.anim.shop_bottom_show);
//
//        animationGone.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                ll_bottem.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        ll_bottem.setVisibility(View.GONE);
//                    }
//                });
//                isAnim = false;
//            }
//        });
//        animationShow.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//                ll_bottem.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                isAnim = false;
//            }
//        });
//
//        baoyou_animationGone = AnimationUtils.loadAnimation(NewMealShopDetailsActivity.this, R.anim.shop_bottom_gone);
//        baoyou_animationShow = AnimationUtils.loadAnimation(NewMealShopDetailsActivity.this, R.anim.shop_bottom_show);
//
//        baoyou_animationGone.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                rlBottom.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        rlBottom.setVisibility(View.GONE);
//                    }
//                });
//                isAnim = false;
//            }
//        });
//        baoyou_animationShow.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnim = true;
//                rlBottom.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                isAnim = false;
//            }
//        });
//    }
//
//    public int imageHeight;
//
//    private List<ShopComment> listShopComments;
//
//    /***
//     * 刷新界面
//     *
//     */
//
//    private class MyOnClick implements OnClickListener {
//
//        private int position;
//
//        public MyOnClick(int position) {
//            super();
//            this.position = position;
//        }
//
//        @Override
//        public void onClick(View arg0) {
//            HashMap<String, Object> posMap = dataList.get(position);
//
//            NewMealShopDetailsActivity.this.getSharedPreferences("YSSJ_yf", Context.MODE_PRIVATE).edit()
//                    .putBoolean("isGoDetail", true).commit();
//            if (YJApplication.instance.isLoginSucess()) {
//                addScanDataTo((String) posMap.get("shop_code"));
//            }
//            Intent intent = new Intent(NewMealShopDetailsActivity.this, NewMealShopDetailsActivity.class);
//            intent.putExtra("code", (String) posMap.get("shop_code"));
//            // context.startActivity(intent);
//            intent.putExtra("shopCarFragment", "shopCarFragment");
//
//            startActivity(intent);
//            finish();
//            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
//        }
//    }
//
//    /*
//	 * 把浏览过的数据添加进数据库
//	 */
//    private void addScanDataTo(final String shop_code) {
//        new SAsyncTask<Void, Void, ReturnInfo>(NewMealShopDetailsActivity.this) {
//
//            @Override
//            protected ReturnInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel.addMySteps(context, shop_code);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//                super.onPostExecute(context, result, e);
//            }
//
//        }.execute();
//    }
//
//    private ToLoginDialog loginDialog;
//    private boolean clickFlag = true;// 加入购物车能否点击的标识
//
//    @Override
//    public void onClick(View view) {
//        if (YJApplication.instance.isLoginSucess() == false) {
//            if (view.getId() == R.id.img_back) {
//                onBackPressed();
//                return;
//            }
//            // if (loginDialog == null) {
//            // loginDialog = new ToLoginDialog(NewMealShopDetailsActivity.this);
//            // }
//            // loginDialog.setRequestCode(235);
//            // loginDialog.show();
//
//            if (LoginActivity.instances != null) {
//                LoginActivity.instances.finish();
//            }
//
//            Intent intent = new Intent(context, LoginActivity.class);
//            intent.putExtra("login_register", "login");
//            ((FragmentActivity) context).startActivityForResult(intent, 235);
//            return;
//        }
//
//        switch (view.getId()) {
//            case R.id.lin_contact:
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    Intent intent = new Intent(this, KeFuActivity.class);
//                    intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("mealMap", mealMap);
//                    if (null == mealMap) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//                    bundle.putDouble("price", Double.parseDouble(mealMap.get("price").toString()));
//                    intent.putExtras(bundle);
//                    intent.putExtra("code", code);
//                    intent.putExtra("signShopDetail", signShopDetail);
//                    intent.putExtra("signValue", signValue);
//                    startActivity(intent);
//                } else {
//
//                    Intent intent = new Intent(this, KeFuActivity.class);
//                    intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("shop", shop);
//                    if (null == shop) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//                    intent.putExtra("ISMEMBERS", isMembers);
//                    intent.putExtra("isSignActiveShop", isSignActiveShop);
//                    intent.putExtras(bundle);
//
//                    startActivity(intent);
//
//                }
//                break;
//            case R.id.img_back:
//                onBackPressed();
//                break;
//
//            case R.id.tv_shop_car:// 活动商品立即购买      普通商品  加入购物车
//
//                if (!clickFlag) {// 正在加入购物车时候的点击不执行
//                    break;
//                }
//
//                if (number_sold != null && number_sold.equals("none")) {
//                    return;
//                } else {
//
//
//                    if (null == mealMap) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//
//
//                }
//
//                break;
//            // case R.id.tv_buy:// 立即购买
//            case R.id.sign_buy:
//                if (number_sold != null && number_sold.equals("none")) {
//                    return;
//                } else {
//                    // MobclickAgent.onEvent(context, "tobuyclick");
//                    // showPopWindow(1);
//                    if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                        if (null == mealMap) {
//                            ToastUtil.showShortText(context, "操作无效");
//                            return;
//                        }
//                    } else if (isMembers) {
//                        if (YCache.getCacheUser(context).getIs_member().equals("2")) {
//                            ToastUtil.showShortText(context, "您已是至尊会员，无需重复购买 ");
//                            return;
//                        }
//                        queryMemberShopQueryAttr(0);
//                    } else {
//                        queryShopQueryAttr(0);
//                    }
//                }
//
//                break;
//            case R.id.img_cart:// 购物车
//                // MobclickAgent.onEvent(context, "toshopcartclick");
//                YunYingTongJi.yunYingTongJi(context, 106);// 商品详情页购物车
//                Intent intent2 = new Intent(this, ShopCartNewNewActivity.class);
//                if (isMeal) {
//                    intent2.putExtra("where", "1");
//                } else {
//                    intent2.putExtra("where", "0");
//                }
//
//                startActivityForResult(intent2, 235);
//                break;
//
//            case R.id.lin_add_like: // 加喜好
//                // MobclickAgent.onEvent(context, "addlikeclick");
//                if (isMeal) {
//
//                } else {
//                    addLikeShop(null);
//
//                }
//                break;
//            case R.id.img_fenx:// 一键分享
//                // MobclickAgent.onEvent(context, "shopdetailshareclick");
//                double feedback = 0;
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    // getShareShop();
//                    if (null == mealMap) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//                    // getPshareShop();
//                } else {
//                    if (null == shop) {
//                        ToastUtil.showShortText(context, "操作无效");
//                        return;
//                    }
//
//                    // feedback = shop.getKickback();8
//                    String a = new DecimalFormat("#0.0").format(shop.getShop_se_price() * 0.1);
//                    feedback = Double.valueOf(a);
//                    // share(shop.getShop_code(), shop);
//                }
//                // 清除分享动画 并保存时间(活动商品点击分享 因没有动画 不清楚 也不保存当前时间值)
//                if (redShare != null && moneyShare != null && !isSignActiveShop && !isMeal) {
//                    redShare.clearAnimation();
//                    moneyShare.clearAnimation();
//                    SharedPreferencesUtil.saveStringData(context, Pref.SHAREANIM, System.currentTimeMillis() + "");
//                }
//
//                showMyPopwindou(NewMealShopDetailsActivity.this, feedback);
//                break;
//            case R.id.rl_retain:
//                // 跳转到购物车结算页面
//                YunYingTongJi.yunYingTongJi(context, 107);
//                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopCartNewNewActivity.class);
//                if (isMeal) {
//                    intent.putExtra("where", "1");
//                } else {
//                    intent.putExtra("where", "0");
//                }
//
//                startActivity(intent);
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 分享套餐
//     */
//    public void getPshareShop() {
//        shareWaitDialog.show();
//
//        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
//        isPause = 1;
//        new SAsyncTask<String, Void, HashMap<String, Object>>(this, R.string.wait) {
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                return ComModel2.getPshopLink(params[0], context, "true");
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                isPause = 0;
//                if (instance == null) {
//                    return;
//                }
//                if (null == e) {
//                    if (result.get("status").equals("1")) {
//                        // MyLogYiFu.e("pic", (String) result.get("shop_pic"));
//                        //
//                        // tongjifenxiangCount(); // 统计分享次数
//                        // tongjifenxiang(code);// 统计谁分享了
//
//                        TongjiShareCount.tongjifenxiangCount();
//                        TongjiShareCount.tongjifenxiangwho(code);
//
//                        // 8 特卖的统计
//
//                        String[] picList = ((String) result.get("shop_pic")).split(",");
//                        String link = (String) result.get("link");
//                        download(null, code, result, link);
//                    } else if (result.get("status").equals("1050")) {// 表明
//
//                        if (null != shareWaitDialog) {
//                            shareWaitDialog.dismiss();
//
//                        }
//
//                        Intent intent = new Intent(context, NoShareActivity.class);
//                        intent.putExtra("isNomal", true);
//                        context.startActivity(intent); // 分享已经超过了
//
//                    }
//                }
//            }
//
//        }.execute(code);
//    }
//
//    /**
//     * 分享包邮
//     */
//    public void getPshareSignShop() {
//
//        shareWaitDialog.show();
//
//        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
//        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
//            @Override
//            protected boolean isHandleException() {
//                // TODO Auto-generated method stub
//                return true;
//            }
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//                // TODO Auto-generated method stub
//                LogYiFu.e("shopDetials", signValue);
//                return ComModel2.getSharePShopInfoDduobao(context, signValue, true);
//
//            }
//
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null) {
//                    if ((Integer) result.get("status") == 1) {
//                        // tongjifenxiangCount(); // 统计分享次数
//                        // tongjifenxiang((String) result.get("shop_code"));//
//                        // 统计谁分享了
//
//                        TongjiShareCount.tongjifenxiangCount();
//                        TongjiShareCount.tongjifenxiangwho((String) result.get("shop_code"));
//
//                        // 8 包邮分享
//
//                        String[] picList = ((String) result.get("shop_pic")).split(",");
//                        String link = (String) result.get("link") + "&post=true";
//                        downloadBao(null, signValue, result, link);
//                    } else if (result.get("status").equals("1050")) {// 表明
//                        if (null != shareWaitDialog) {
//                            shareWaitDialog.dismiss();
//
//                        }
//                        Intent intent = new Intent(context, NoShareActivity.class);
//                        intent.putExtra("isNomal", true);
//                        context.startActivity(intent); // 分享已经超过了
//
//                    }
//                }
//            }
//
//            ;
//
//        }.execute();
//    }
//
//    /**
//     * 得到分享的商品
//     **/
//    private void getShareShop() {
//        new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//                return ComModel2.getSharePShopInfo(context, mealMap.get("p_code").toString(), true);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if ((Integer) result.get("status") == 1) {
//                        // 大图是900 X 900 二维码
//
//                        createSharePic((String) result.get("link"), (String) result.get("def_pic"),
//                                (String) result.get("price"));
//                    }
//                    // submitZeroOrder(v);
//                }
//            }
//
//        }.execute();
//    }
//
//    public static String shareStatus;
//
//    /**
//     * 得到分享的链接
//     */
//    public void share(final String code, final Shop shop) {
//
//        shareWaitDialog.show();
//
//        ShareUtil.configPlatforms(context);// 配置分享平台参数</br>
//        isPause = 1;
//        new SAsyncTask<String, Void, HashMap<String, String>>(this, R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
//                if (isSignActiveShop) {
//                    return ComModel2.getActiveShopLink(params[0], context, "true");
//                } else {
//                    return ComModel2.getShopLink(params[0], context, "true");
//                }
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                isPause = 0;
//
//                if (instance == null) {
//                    return;
//                }
//                if (null == e) {
//
//                    String status = result.get("status");
//
//                    // MyLogYiFu.e("统计分享过的用户",result+"");
//                    if (result.get("status").equals("1")) {
//                        // 没有分享过
//
//                        // tongjifenxiangCount(); // 统计分享次数
//                        // tongjifenxiang(code);// 统计谁分享了
//
//                        TongjiShareCount.tongjifenxiangCount();
//                        TongjiShareCount.tongjifenxiangwho(code);
//
//                        // 8 普通商品
//
//                        LogYiFu.e("pic", result.get("shop_pic"));
//                        String[] picList = result.get("shop_pic").split(",");
//                        String four_pic = result.get("four_pic").toString();
//                        String link = result.get("link");
//                        // download(null, picList, code, result, shop, link,
//                        // four_pic);
//                        getNineBmBg(null, picList, code, result, shop, link, four_pic);
//                    } else if (result.get("status").equals("1050")) {// 表明
//                        if (null != shareWaitDialog) {
//                            shareWaitDialog.dismiss();
//
//                        }
//                        Intent intent = new Intent(context, NoShareActivity.class);
//                        intent.putExtra("isNomal", true);
//                        context.startActivity(intent); // 分享已经超过了
//
//                    } else {// Dialog消失
//                        if (null != shareWaitDialog) {
//                            shareWaitDialog.dismiss();
//                        }
//                    }
//                } else {
//                    if (null != shareWaitDialog) {
//                        shareWaitDialog.dismiss();
//                    }
//                }
//            }
//
//        }.execute(code);
//    }
//
//    // private void tongjifenxiang(final String shopCode) {
//    //
//    // new SAsyncTask<Integer, Void, String>((FragmentActivity) context,
//    // R.string.wait) {
//    //
//    // @Override
//    // protected boolean isHandleException() {
//    // // TODO Auto-generated method stub
//    // return true;
//    // }
//    //
//    // @Override
//    // protected String doInBackground(FragmentActivity context, Integer...
//    // params) throws Exception {
//    // // TODO Auto-generated method stub
//    // return ComModel2.tongjifenxiang(context, shopCode);
//    // }
//    //
//    // @Override
//    // protected void onPostExecute(FragmentActivity context, String result,
//    // Exception e) {
//    // super.onPostExecute(context, result, e);
//    //
//    // if (e == null) {
//    // MyLogYiFu.e("谁分享了", result + "");
//    // }
//    //
//    // }
//    //
//    // }.execute();
//    //
//    // }
//
//    // private void tongjifenxiangCount() {
//    //
//    // new SAsyncTask<Integer, Void, String>((FragmentActivity) context,
//    // R.string.wait) {
//    //
//    // @Override
//    // protected boolean isHandleException() {
//    // return true;
//    // }
//    //
//    // @Override
//    // protected String doInBackground(FragmentActivity context, Integer...
//    // params) throws Exception {
//    // // TODO Auto-generated method stub
//    // return ComModel2.tongjifenxiangshu(context);
//    // }
//    //
//    // @Override
//    // protected void onPostExecute(FragmentActivity context, String result,
//    // Exception e) {
//    // super.onPostExecute(context, result, e);
//    //
//    // if (e == null) {
//    // MyLogYiFu.e("统计用户分享次数", result + "");
//    // }
//    //
//    // }
//    //
//    // }.execute();
//    //
//    // }
//
//    /**
//     * 下载分享的图片
//     */
//    private void download(View v, final String shop_code, final HashMap<String, Object> mapInfos, final String link) {
//
//        new SAsyncTask<Void, Void, Void>((FragmentActivity) NewMealShopDetailsActivity.this, v, R.string.wait) {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                // TODO Auto-generated method stub
//                List<String> shopCodes = (List<String>) mapInfos.get("shopCodes");
//                List<HashMap<String, String>> shopPics = (List<HashMap<String, String>>) mapInfos.get("pics");
//
//                File fileDirec = new File(YConstance.savePicPath);
//                if (!fileDirec.exists()) {
//                    fileDirec.mkdir();
//                }
//                File[] listFiles = new File(YConstance.savePicPath).listFiles();
//                if (listFiles.length != 0) {
//                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//                    for (File file : listFiles) {
//                        file.delete();
//                    }
//                }
//                // LogYiFu.i("TAG", "piclist=" + picList.length);
//                List<String> pics = new ArrayList<String>();
//                for (int j = 0; j < shopCodes.size(); j++) {
//                    String shop_code = shopCodes.get(j);
//                    HashMap<String, String> map = shopPics.get(j);
//                    String pic = map.get(shop_code);
//                    String[] picStrs = pic.split(",");
//                    for (int i = 0; i < picStrs.length; i++) {
//                        if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
//                                && !picStrs[i].contains("real_")) {
//                            pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
//                        }
//                    }
//                }
//
//				/*
//				 * for (int j = 0; j < picList.length; j++) { if
//				 * (!picList[j].contains("reveal_") &&
//				 * !picList[j].contains("detail_") &&
//				 * !picList[j].contains("real_")) { pics.add(picList[j]); } }
//				 */
//                int j = pics.size() + 1;
//                if (pics.size() > 8) {
//                    j = 9;
//                }
//                int nP = j > 5 ? 4 : j - 1;
//                for (int i = 0; i < j; i++) {
//                    if (i == nP) {
//						/*
//						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
//						 * shop_code);
//						 */
//                        if (isMeal) {
//                            // Bitmap bm = QRCreateUtil.createZeroImage(link,
//                            // 500, 700,
//                            // (String) mapInfos.get("shop_se_price"),
//                            // NewMealShopDetailsActivity.this);// 得到二维码图片
//                            // 九宫图二维码新样式
//                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
//                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(NewMealShopDetailsActivity.this, bmQr,
//                                    (String) mapInfos.get("shop_se_price"), true);
//                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//                        } else {
//                            Bitmap bm = QRCreateUtil.createImage(link, 500, 700, (String) mapInfos.get("shop_se_price"),
//                                    NewMealShopDetailsActivity.this);// 得到二维码图片
//                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//                        }
//                        // downloadPic(mapInfos.get("qr_pic"), 9);
//                        continue;
//                    }
//                    int m = i > 4 ? i - 1 : i;
//                    downloadPic(pics.get(m) + "!450", i);
//                    bmBg = downloadPic(mapInfos.get("four_pic") + "!450");
//                    LogYiFu.e("热卖分享", mapInfos.get("four_pic") + "!450");
//                }
//                return super.doInBackground(params);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Void result) {
//                if (instance == null) {
//                    return;
//                }
//                if (null != context && null != context.getWindow().getDecorView()) {
//                    // qqShareIntent =
//                    // ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
//                    // wXinShareIntent =
//                    // ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//                    // ShareUtil.configPlatforms(context);
//
//                    UMImage umImage = new UMImage(context, bmBg);
//                    // ShareUtil.setShareContent(context, umImage,
//                    // "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//                    ShareUtil.setShareContent(context, umImage, "买了肯定不后悔，数量不多，快来抢购吧~", link);
//
//                    // showPopwindou(link, context, umImage);
//                    myPopupwindow.setUmImage(umImage);
//                    myPopupwindow.setLink(link);
//                    shareTo(shop_code, link);
//                    super.onPostExecute(context, result);
//                }
//
//            }
//
//        }.execute();
//    }
//
//    /**
//     * 下载包邮 分享的图片
//     */
//    private void downloadBao(View v, final String shop_code, final HashMap<String, Object> mapInfos,
//                             final String link) {
//        new SAsyncTask<Void, Void, Void>((FragmentActivity) NewMealShopDetailsActivity.this, v, R.string.wait) {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//
//                File fileDirec = new File(YConstance.savePicPath);
//                if (!fileDirec.exists()) {
//                    fileDirec.mkdir();
//                }
//                File[] listFiles = new File(YConstance.savePicPath).listFiles();
//                if (listFiles.length != 0) {
//                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//                    for (File file : listFiles) {
//                        file.delete();
//                    }
//                }
//                // LogYiFu.i("TAG", "piclist=" + picList.length);
//                List<String> pics = new ArrayList<String>();
//                String pic = (String) mapInfos.get("shop_pic");
//                String[] picStrs = pic.split(",");
//                String shop_code = (String) mapInfos.get("shop_code");
//                for (int i = 0; i < picStrs.length; i++) {
//                    if (!picStrs[i].contains("reveal_") && !picStrs[i].contains("detail_")
//                            && !picStrs[i].contains("real_")) {
//                        pics.add(shop_code.substring(1, 4) + "/" + shop_code + "/" + picStrs[i]);
//                    }
//                }
//
//				/*
//				 * for (int j = 0; j < picList.length; j++) { if
//				 * (!picList[j].contains("reveal_") &&
//				 * !picList[j].contains("detail_") &&
//				 * !picList[j].contains("real_")) { pics.add(picList[j]); } }
//				 */
//                int j = pics.size() + 1;
//                if (pics.size() > 8) {
//                    j = 9;
//                }
//                int nP = j > 5 ? 4 : j - 1;
//                for (int i = 0; i < j; i++) {
//                    if (i == nP) {
//						/*
//						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
//						 * shop_code);
//						 */
//                        if ("SignShopDetail".equals(signShopDetail)) {
//                            // Bitmap bm = QRCreateUtil.createZeroImage(link,
//                            // 500, 700, (String) mapInfos.get("price"),
//                            // NewMealShopDetailsActivity.this);// 得到二维码图片
//                            // QRCreateUtil.saveBitmap(bm,
//                            // YConstance.savePicPath,
//                            // MD5Tools.md5(String.valueOf(9)) + ".jpg");//
//                            // 保存二维码图片
//                            // 九宫图二维码新样式
//                            Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);
//                            Bitmap bm = QRCreateUtil.drawNewBitmapNine(NewMealShopDetailsActivity.this, bmQr,
//                                    (String) mapInfos.get("price"), true);
//                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//                        }
//                        continue;
//                    }
//                    int m = i > 4 ? i - 1 : i;
//                    downloadPic(pics.get(m) + "!450", i);
//
//                    String baoyouWeixinSharePic = shop_code.substring(1, 4) + File.separator + shop_code
//                            + File.separator + mapInfos.get("four_pic");
//
//                    bmBg = downloadPic(baoyouWeixinSharePic + "!450");
//                    LogYiFu.e("r热卖分享", mapInfos.get("four_pic") + "!450");
//                }
//                return super.doInBackground(params);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Void result) {
//                if (instance == null) {
//                    return;
//                }
//                if (null != context && null != context.getWindow().getDecorView()) {
//
//                    // ShareUtil.configPlatforms(context);
//                    // qqShareIntent =
//                    // ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
//                    // wXinShareIntent =
//                    // ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//                    UMImage umImage = new UMImage(context, bmBg);
//                    if (myPopupwindow.getShareId() == R.id.iv_qq_share) {
//                        ShareUtil.setShareContentBaoYou(context, umImage, signType + "元带走心爱的商品。首次签到还能领3元现金哦", link,
//                                signType, 0);
//                    } else {
//                        ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//                    }
//
//                    // showPopwindou(link, context, umImage);
//                    myPopupwindow.setUmImage(umImage);
//                    myPopupwindow.setLink(link);
//                    shareTo(shop_code, link);
//                    super.onPostExecute(context, result);
//                }
//
//            }
//
//        }.execute();
//    }
//
//    /**
//     * 显示分享的myPopWindow
//     *
//     * @param context
//     */
//    private void showMyPopwindou(FragmentActivity context, final double feedBack) {
//        // myPopupwindow = new MyPopupwindow(context, 0, umImage, link);
//        // myPopupwindow = new MyPopupwindow(context,
//        // shop.getKickback(), umImage, link);
//
//        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) NewMealShopDetailsActivity.this,
//                R.string.wait) {
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//                return ComModel.ShareLifeGetPic(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//
//                myPopupwindow = new MyPopupwindow(context, feedBack, NewMealShopDetailsActivity.this, shop, isMeal,
//                        signShopDetail, "ShopDetails", signType, "", isSignActiveShop);
//
//                if (result == null) {
//                    MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);
//
//                } else if (null == e && result != null && !("".equals(result))) {
//
//                    String mStartPic = (String) result.get("pic");
//                    if (mStartPic == null || mStartPic.equals("null") || mStartPic.equals("")) {
//                        mStartPic = "-1";
//                    } else {
//                        mStartPic = (String) result.get("pic");
//                    }
//                    //
//                    // myPopupwindow = new MyPopupwindow(context, feedBack,
//                    // NewMealShopDetailsActivity.this, shop, isMeal,
//                    // signShopDetail, "ShopDetails", signType, mStartPic);
//
//                    if (mStartPic.equals("-1")) {
//                        MyPopupwindow.iv_img.setImageResource(R.drawable.putongfengxiang1);
//                    } else {
//                        SetImageLoader.initImageLoader(null, MyPopupwindow.iv_img, mStartPic, "");
//                    }
//
//                    // if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    // myPopupwindow.setGou(true);
//                    // }
//                    // if (NewMealShopDetailsActivity.instance != null) {
//                    // myPopupwindow.showAtLocation(context.getWindow().getDecorView(),
//                    // Gravity.BOTTOM, 0, 0);
//                    //
//                    // }
//
//                }
//
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    myPopupwindow.setGou(true);
//                }
//                if (NewMealShopDetailsActivity.instance != null) {
//                    myPopupwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//
//                }
//
//            }
//
//        }.execute();
//
//    }
//
//    private String[] picListNine;
//    private String shop_codeNine;
//    private HashMap<String, String> mapInfosNine;
//    private Shop shopNine;
//    private String linkNine;
//    private String four_picNine;
//
//    /**
//     * 获取九宫图的二维码背景图片
//     */
//    public void getNineBmBg(View v, final String[] picList, final String shop_code,
//                            final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic) {
//        new SAsyncTask<Void, Void, String>((FragmentActivity) context, R.string.wait) {
//            @Override
//            protected boolean isHandleException() {
//                // TODO Auto-generated method stub
//                return true;
//            }
//
//            @Override
//            protected String doInBackground(FragmentActivity context, Void... params) throws Exception {
//                // TODO Auto-generated method stub
//                return ComModel2.getShareBg(context);
//
//            }
//
//            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
//                super.onPostExecute(context, result, e);
//                // Bitmap bmNineBg = downloadPic("share/1111.jpg");
//                picListNine = picList;
//                shop_codeNine = shop_code;
//                mapInfosNine = mapInfos;
//                shopNine = shop;
//                linkNine = link;
//                four_picNine = four_pic;
//                getPicture(result);
//
//            }
//        }.execute();
//    }
//
//    private Bitmap bmBg;
//
//    /**
//     * 下载分享的图片
//     */
//    private void download(View v, final String[] picList, final String shop_code,
//                          final HashMap<String, String> mapInfos, final Shop shop, final String link, final String four_pic,
//                          final Bitmap bmNineBg) {
//        final List<String> pics = new ArrayList<String>();
//        // shareWaitDialog.show();
//        new SAsyncTask<Void, Void, Void>((FragmentActivity) NewMealShopDetailsActivity.this, v, R.string.wait) {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                File fileDirec = new File(YConstance.savePicPath);
//                if (!fileDirec.exists()) {
//                    fileDirec.mkdir();
//                }
//                File[] listFiles = new File(YConstance.savePicPath).listFiles();
//                if (listFiles.length != 0) {
//                    LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
//                    for (File file : listFiles) {
//                        file.delete();
//                    }
//                }
//                // LogYiFu.i("TAG", "piclist=" + picList.length);
//                // List<String> pics = new ArrayList<String>();
//                for (int j = 0; j < picList.length; j++) {
//                    if (!picList[j].contains("reveal_") && !picList[j].contains("detail_")
//                            && !picList[j].contains("real_")) {
//                        pics.add(picList[j]);
//                    }
//                }
//                int j = pics.size() + 1;
//                if (pics.size() > 8) {
//                    j = 9;
//                }
//                int nP = j > 5 ? 4 : j - 1;
//                for (int i = 0; i < j; i++) {
//                    if (i == nP) {
//						/*
//						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
//						 * shop_code);
//						 */
//                        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                            Bitmap bm = QRCreateUtil.createZeroImage(link, 500, 700, mapInfos.get("shop_se_price"),
//                                    NewMealShopDetailsActivity.this);// 得到二维码图片
//                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//                        } else {
//
//                            Bitmap bmQr = QRCreateUtil.createQrImage(mapInfos.get("QrLink"), 190, 190);
//                            Bitmap bm = QRCreateUtil.drawNewBitmapNine2(NewMealShopDetailsActivity.this, bmQr, bmNineBg);
//
//                            QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
//                                    MD5Tools.md5(String.valueOf(i)) + ".jpg");// 保存二维码图片
//                        }
//                        // downloadPic(mapInfos.get("qr_pic"), 9);
//                        continue;
//                    }
//                    int m = i > 4 ? i - 1 : i;
//                    downloadPic(shop_code.substring(1, 4) + "/" + shop_code + "/" + pics.get(m) + "!450", i);
//                    bmBg = downloadPic(
//                            shop_code.substring(1, 4) + "/" + shop_code + "/" + four_pic.split(",")[2] + "!450");
//                }
//                return super.doInBackground(params);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Void result) {
//                // showShareDialog();
//                if (instance == null) {
//                    if (null != shareWaitDialog) {
//                        shareWaitDialog.dismiss();
//                    }
//                    return;
//                }
//                if (null != context && null != context.getWindow().getDecorView() && !context.isFinishing()) {
//                    LogYiFu.e("TAG", "宝贝内容=" + shop.getShop_name() + ",宝贝链接=" + result);
//
//                    UMImage umImage = new UMImage(context, bmBg);
//                    ShareUtil.setShareContent(context, umImage, "我挺喜欢的宝贝，分享给你进来看看还能领现金红包哦~", link);
//
//                    myPopupwindow.setLink(link);
//                    myPopupwindow.setUmImage(umImage);
//
//                    shareTo(shop_code, link);
//                    super.onPostExecute(context, result);
//                }
//
//            }
//
//        }.execute();
//
//    }
//
//    /**
//     * @param shop_code
//     */
//    private void shareTo(String shop_code, String link) {
//        qqShareIntent = ShareUtil.shareMultiplePictureToQZone(ShareUtil.getImage());
//        wXinShareIntent = ShareUtil.shareMultiplePictureToTimeLine(ShareUtil.getImage());
//        // wXinShareIntent.putExtra("Kdescription", "点击链接了解详情"+link);
//        if (null != shareWaitDialog) {
//            shareWaitDialog.dismiss();
//        }
//        switch (myPopupwindow.getShareId()) {
//            case R.id.iv_qq_share:
//                if (myPopupwindow.isSecondShare()) {
//                    myPopupwindow.onceShare(qqShareIntent, "qq空间");
//
//                    yunYunTongJi(shop_code, 104, 2);
//                }
//                break;
//            case R.id.iv_wxin_share:
//                if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//                    if (myPopupwindow.isSecondShare()) {
//                        myPopupwindow.onceShare(wXinShareIntent, "微信");
//                        yunYunTongJi(shop_code, 1, 2);
//                    }
//                } else {
//                    boolean WxCircleFlag = SharedPreferencesUtil.getBooleanData(context, Pref.RECORD_WXCIRCLE, false);
//                    if (myPopupwindow.isSecondShare()) {
//                        if (WxCircleFlag) {
//                            SharedPreferencesUtil.saveBooleanData(context, Pref.RECORD_WXCIRCLE, false);
//                            myPopupwindow.onceShare(wXinShareIntent, "微信");// 九宫图
//                            yunYunTongJi(shop_code, 1, 2);
//                        } else {
//                            SharedPreferencesUtil.saveBooleanData(context, Pref.RECORD_WXCIRCLE, true);
//                            myPopupwindow.performShare(SHARE_MEDIA.WEIXIN_CIRCLE, wXinShareIntent);// 链接
//                            yunYunTongJi(shop_code, 1, 2);
//                        }
//                    }
//                }
//
//                break;
//            case R.id.iv_wxin_circle_share:
//                yunYunTongJi(shop_code, 106, 2);
//                myPopupwindow.shareToWxin();
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        instance = null;
//        if (myPopupwindow != null && myPopupwindow.isShowing()) {
//            myPopupwindow.dismiss();
//            return;
//        }
//        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//            super.onBackPressed();
//        } else {
//            if (shop != null) {
//                setResult(-1, new Intent().putExtra("isLike", shop.getLike_id() == -1 ? 0 : 1));
//            }
//            super.onBackPressed();
//        }
//        if (aa != null && !aa.isCancelled()) {
//            aa.cancel(true);
//        }
//        if (bb != null && !bb.isCancelled()) {
//            bb.cancel(true);
//        }
//        if (cc != null && !cc.isCancelled()) {
//            cc.cancel(true);
//        }
//    }
//
//    /***
//     * 添加和删除我喜欢的商品
//     *
//     */
//    private void addLikeShop(View v) {
//        if (shop != null) {
//            // int like_id = shop.getLike_id();
//            int like_id = -1;
//            String str = SharedPreferencesUtil.getStringData(context, "" + YCache.getCacheUser(context).getUser_id(),
//                    "");
//            if (str.contains(shop.getShop_code())) {
//                img_xin.setImageResource(R.drawable.hx0);
//                like_id = 1;
//            } else {
//                img_xin.setImageResource(R.drawable.icon_xihuan);
//            }
//
//            if (like_id == -1) {// 添加我的喜欢
//                LogYiFu.e("like_id  == ", " " + like_id);
//                LogYiFu.e("shop_code  == ", " " + shop.getShop_code());
//                AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
//                _alphaAnimation0.setDuration(1500);
//                _alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
//                img_addxin.setImageResource(R.drawable.pic_like_animation);
//                img_addxin.startAnimation(_alphaAnimation0);
//                img_addxin.setVisibility(View.VISIBLE);
//
//                /**
//                 * 透明度从不透明变为0.2透明度
//                 */
//                AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
//                _alphaAnimation.setDuration(1500);
//                _alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
//                img_addxin.startAnimation(_alphaAnimation);
//                shakeAnimation(5);
//                new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {
//
//                    @Override
//                    protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//
//                        return ComModel.addLikeShop(NewMealShopDetailsActivity.this,
//                                YCache.getCacheToken(NewMealShopDetailsActivity.this), params[0]);
//                    }
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//                        if (null == e) {
//                            img_addxin.setVisibility(View.GONE);
//                            if (null != result) {
//                                Toast.makeText(NewMealShopDetailsActivity.this, "添加我的喜欢成功", Toast.LENGTH_SHORT).show();
//                                String str = SharedPreferencesUtil.getStringData(context,
//                                        "" + YCache.getCacheUser(context).getUser_id(), "");
//                                StringBuffer sb = new StringBuffer(str);
//                                sb.append(shop.getShop_code());
//                                SharedPreferencesUtil.saveStringData(context,
//                                        "" + YCache.getCacheUser(context).getUser_id(), sb.toString());
//                                LogYiFu.e("hillo", sb.toString());
//                                LogYiFu.e("hillo", shop.getShop_code());
//                                img_xin.setImageResource(R.drawable.hx0);
//                                shop.setLike_id(1);
//                                if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 6) {
//                                    context.getSharedPreferences("EverydayTaskMondayFridayAddLike",
//                                            Context.MODE_PRIVATE).edit()
//                                            .putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).commit();
//                                }
//                            } else {
//                                Toast.makeText(NewMealShopDetailsActivity.this, "添加我的喜欢失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        super.onPostExecute(context, result, e);
//                    }
//
//                }.execute(shop.getShop_code());
//
//            } else {// 删除我的喜欢
//                AlphaAnimation _alphaAnimation0 = new AlphaAnimation(1.0f, 0.2f);
//                _alphaAnimation0.setDuration(1500);
//                _alphaAnimation0.setFillAfter(true);// 动画执行完的状态显示
//                img_addxin.setImageResource(R.drawable.cancel_add_star);
//                img_addxin.startAnimation(_alphaAnimation0);
//                img_addxin.setVisibility(View.VISIBLE);
//
//                /**
//                 * 透明度从不透明变为0.2透明度
//                 */
//                AlphaAnimation _alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
//                _alphaAnimation.setDuration(1500);
//                _alphaAnimation.setFillAfter(true);// 动画执行完的状态显示
//                img_addxin.startAnimation(_alphaAnimation);
//                shakeAnimation(5);
//
//                new SAsyncTask<String, Void, ReturnInfo>(this, v, 0) {
//
//                    protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//
//                        return ComModel.deleteLikeShop(NewMealShopDetailsActivity.this,
//                                YCache.getCacheToken(NewMealShopDetailsActivity.this), params[0]);
//                    }
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//                        super.onPostExecute(context, result, e);
//                        if (null == e) {
//                            img_addxin.setVisibility(View.GONE);
//                            if (null != result) {
//                                String str = SharedPreferencesUtil.getStringData(context,
//                                        "" + YCache.getCacheUser(context).getUser_id(), "");
//                                LogYiFu.e("hillo", str);
//                                LogYiFu.e("hillo", shop.getShop_code());
//                                String str2 = str.replace(shop.getShop_code(), "");
//                                LogYiFu.e("hillo", str2);
//                                SharedPreferencesUtil.saveStringData(context,
//                                        "" + YCache.getCacheUser(context).getUser_id(), str2);
//                                Toast.makeText(NewMealShopDetailsActivity.this, "删除我的喜欢成功", Toast.LENGTH_SHORT).show();
//                                img_xin.setImageResource(R.drawable.icon_xihuan);
//                                shop.setLike_id(-1);
//
//                            } else {
//                                Toast.makeText(NewMealShopDetailsActivity.this, "删除我的喜欢失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                }.execute(shop.getShop_code());
//            }
//        } else {
//            ToastUtil.showShortText(context, "操作无效");
//        }
//    }
//
//    private Animation mShakeAnimation;
//
//    // CycleTimes动画重复的次数
//    public void shakeAnimation(int CycleTimes) {
//        if (null == mShakeAnimation) {
//            mShakeAnimation = new TranslateAnimation(0, 5, 0, 5);
//            mShakeAnimation.setInterpolator(new CycleInterpolator(5));
//            mShakeAnimation.setDuration(3000);
//            mShakeAnimation.setRepeatMode(Animation.REVERSE);// 设置反方向执行
//
//        }
//        img_addxin.startAnimation(mShakeAnimation);
//    }
//
//    private int index = 0;
//    private int mType = 0;
//    private int position = 0;
//
//    private View v;
//
//    private boolean isShopTitle = false;
//
//    private String attrDateStr;// 属性时间戳
//    private boolean mGoldVoucher = false;// 是否开启了优惠券变金券
//    private boolean mIsGold = false;// 是否开启了积分变金币
//    private double mVoucherPrice = 0;// 变成金券的面值
//    private HashMap<String, String> mGoldIconMap;
//    private HashMap<String, String> mGoldVoucherMap;
//
//    /**
//     * 获取是否开启余额翻倍，金币，金券，用于弹框文案的显示
//     */
//    private void getGoldIsOpen(final int dikou_int) {
//        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) NewMealShopDetailsActivity.this,
//                R.string.wait) {
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                mGoldIconMap = ComModel2.getTwoFoldnessGold(NewMealShopDetailsActivity.this);
//                mGoldVoucherMap = ComModel2.getCpgold(NewMealShopDetailsActivity.this);
//                return null;
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                super.onPostExecute(context, result, e);
//
//                if (null == e) {
//                    if (null != mGoldIconMap && mGoldIconMap.size() > 0
//                            && !("".equals(mGoldIconMap.get("twofoldnessGold")))) {
//                        mIsGold = true;
//                    }
//                    if (mGoldVoucherMap != null && mGoldVoucherMap.size() > 0
//                            && "1".equals("" + mGoldVoucherMap.get("is_open"))) {
//                        mGoldVoucher = true;
//                        mVoucherPrice = Double.parseDouble(mGoldVoucherMap.get("c_price"));
//                    }
//                    // 判断是否有了余额翻倍，金币或者抵用券
//                    boolean balanceQulification = SharedPreferencesUtil.getBooleanData(NewMealShopDetailsActivity.this,
//                            Pref.IS_QULIFICATION, false);
//                    String is_open = SharedPreferencesUtil.getStringData(NewMealShopDetailsActivity.this, Pref.IS_OPEN, "-1");
//
//                    if (balanceQulification && "1".equals(is_open) && mGoldVoucher && mIsGold) {// 开启了余额翻倍，金券，金币
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("组合优惠最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou))
//                                .setText("抵扣" + (dikou_int + 12 + mVoucherPrice) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 12 - mVoucherPrice < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至" + new DecimalFormat("#0.0")
//                                    .format(shop.getShop_se_price() - dikou_int - 12 - mVoucherPrice) + "元");
//                        }
//                    } else if (balanceQulification && "1".equals(is_open) && mGoldVoucher && !mIsGold) {// 开启了余额翻倍，金券
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("组合优惠最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou))
//                                .setText("抵扣" + (dikou_int + 6 + mVoucherPrice) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 6 - mVoucherPrice < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至" + new DecimalFormat("#0.0")
//                                    .format(shop.getShop_se_price() - dikou_int - 6 - mVoucherPrice) + "元");
//                        }
//                    } else if (balanceQulification && "1".equals(is_open) && !mGoldVoucher && mIsGold) {// 开启了余额翻倍，金币
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("组合优惠最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou)).setText("抵扣" + (dikou_int + 12) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 12 < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至"
//                                    + new DecimalFormat("#0.0").format(shop.getShop_se_price() - dikou_int - 12) + "元");
//                        }
//                    } else if ((!balanceQulification || !"1".equals(is_open)) && mGoldVoucher && mIsGold) {// 开启了金币，金券
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("组合优惠最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou))
//                                .setText("抵扣" + (dikou_int + 6 + mVoucherPrice) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 6 - mVoucherPrice < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至" + new DecimalFormat("#0.0")
//                                    .format(shop.getShop_se_price() - dikou_int - 6 - mVoucherPrice) + "元");
//                        }
//                    } else if (balanceQulification && "1".equals(is_open) && !mGoldVoucher && !mIsGold) {// 开启了余额翻倍
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("抵用券+余额翻倍最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou)).setText("抵扣" + (dikou_int + 6) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 6 < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至"
//                                    + new DecimalFormat("#0.0").format(shop.getShop_se_price() - dikou_int - 6) + "元");
//                        }
//                    } else if ((!balanceQulification || !"1".equals(is_open)) && mGoldVoucher && !mIsGold) {// 开启了金券
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("抵用券+金券最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou))
//                                .setText("抵扣" + (dikou_int + mVoucherPrice) + "元");
//                        if (shop.getShop_se_price() - dikou_int - mVoucherPrice < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至" + new DecimalFormat("#0.0")
//                                    .format(shop.getShop_se_price() - dikou_int - mVoucherPrice) + "元");
//                        }
//                    } else if ((!balanceQulification || !"1".equals(is_open)) && !mGoldVoucher && mIsGold) {// 开启了金币
//                        ((TextView) headerView.findViewById(R.id.tv_zui_left)).setText("抵用券+金币最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou)).setText("抵扣" + (dikou_int + 6) + "元");
//                        if (shop.getShop_se_price() - dikou_int - 6 < 0) {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至0.01元");
//                        } else {
//                            ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText("低至"
//                                    + new DecimalFormat("#0.0").format(shop.getShop_se_price() - dikou_int - 6) + "元");
//                        }
//                    } else {
//                        // ((TextView)headerView.findViewById(R.id.tv_zui_left)).setText("抵用券最高");
//                        ((TextView) headerView.findViewById(R.id.tv_dikou)).setText("抵扣" + dikou_int + "元");
//                        ((TextView) headerView.findViewById(R.id.tv_dizhi)).setText(
//                                "低至" + new DecimalFormat("#0.0").format(shop.getShop_se_price() - dikou_int) + "元");
//                    }
//                }
//            }
//
//        }.execute();
//    }
//
//
//
//    private void createSharePic(final String link, final String picPath, final String price) {
//        new SAsyncTask<Void, Void, Void>(this, R.string.wait) {
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected Void doInBackground(FragmentActivity context, Void... params) throws Exception {
//                Bitmap bmQr = QRCreateUtil.createQrImage(link, 160, 160);// 得到二维码图片
//                Bitmap bmBg = downloadPic(picPath);
//
//                Bitmap bmNew = QRCreateUtil.drawNewBitmap1(context, bmBg, bmQr, price, "");
//
//                QRCreateUtil.saveBitmap(bmNew, YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");// 保存二维码图片
//                return super.doInBackground(context, params);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Void result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    File file = new File(YConstance.savePicPath, MD5Tools.md5(String.valueOf(9)) + ".jpg");
//                    shareMeal(file, v);
//                }
//            }
//
//        }.execute();
//    }
//    ////////////////////////
//
//    private byte[] picByte;
//    private Bitmap nineBitmap;
//
//    public void getPicture(final String picPath) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (TextUtils.isEmpty(picPath)) {
//                    Message message = new Message();
//                    message.what = 99;
//                    handle.sendMessage(message);
//                } else {
//                    try {
//                        URL url = new URL(YUrl.imgurl + picPath);
//
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.setRequestMethod("GET");
//                        conn.setReadTimeout(10000);
//
//                        if (conn.getResponseCode() == 200) {
//                            InputStream fis = conn.getInputStream();
//                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            byte[] bytes = new byte[1024];
//                            int length = -1;
//                            while ((length = fis.read(bytes)) != -1) {
//                                bos.write(bytes, 0, length);
//                            }
//                            picByte = bos.toByteArray();
//                            bos.close();
//                            fis.close();
//
//                            Message message = new Message();
//                            message.what = 99;
//                            handle.sendMessage(message);
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        if (null != shareWaitDialog) {
//                            shareWaitDialog.dismiss();
//                        }
//                    }
//                }
//            }
//        }).start();
//    }
//
//    Handler handle = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 99) {
//                if (picByte != null) {
//                    nineBitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
//                    download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine,
//                            nineBitmap);
//                } else {
//                    download(null, picListNine, shop_codeNine, mapInfosNine, shopNine, linkNine, four_picNine, null);
//                }
//            }
//        }
//    };
//
//    /////////////////////
//    private Bitmap downloadPic(String picPath) {
//        try {
//            URL url = new URL(YUrl.imgurl + picPath);
//            // 打开连接
//            URLConnection con = url.openConnection();
//            // 获得文件的长度
//            int contentLength = con.getContentLength();
//            // System.out.println("长度 :" + contentLength);
//            // 输入流
//            InputStream is = con.getInputStream();
//            // 1K的数据缓冲
//            byte[] bs = new byte[8192];
//            // 读取到的数据长度
//            int len;
//            BitmapDrawable bmpDraw = new BitmapDrawable(is);
//
//            // 完毕，关闭所有链接
//            is.close();
//            return bmpDraw.getBitmap();
//        } catch (Exception e) {
//            LogYiFu.e("TAG", "下载失败");
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private void shareMeal(File file, final View v) {
//
//        if (file == null) {
//            Toast.makeText(this, "您的网络状态不太好哦~~", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        UMImage umImage = new UMImage(this, file);
//        ShareUtil.configPlatforms(this);
//        ShareUtil.shareShop(this, umImage);
//
//        UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR_SHARE).postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
//                new SnsPostListener() {
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//                    }
//                });
//
//    }
//
//    /***
//     * 查找商品属性
//     */
//    public void queryShopQueryAttr(final int i) {
//        clickFlag = false;
//        if (shop != null) {
//            List<StockType> list = shop.getList_stock_type();
//            if (list != null && list.size() > 0) {
//                showPopWindow(i);
//            } else {
//                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
//                    @Override
//                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
//                        return ComModel.queryShopQueryAttr(NewMealShopDetailsActivity.this, shop, params[0]);
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {
//
//                        if (e != null) {// 查询异常
//                            Toast.makeText(NewMealShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
//                        } else {// 查询商品详情成功，刷新界面
//                            if (shop != null) {
//                                NewMealShopDetailsActivity.this.shop = shop;
//                                showPopWindow(i);
//                            }
//                        }
//
//                    }
//
//                    ;
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    ;
//                }.execute("false");
//            }
//
//        } else {
//            ToastUtil.showShortText(context, "无效操作");
//        }
//    }
//
//    /***
//     * 会员商品查找商品属性
//     */
//    private void queryMemberShopQueryAttr(final int i) {
//        if (shop != null) {
//            List<StockType> list = shop.getList_stock_type();
//            if (list != null && list.size() > 0) {
//                showPopWindow(i);
//            } else {
//                new SAsyncTask<String, Void, Shop>(this, null, R.string.wait) {
//                    @Override
//                    protected Shop doInBackground(FragmentActivity context, String... params) throws Exception {
//                        return ComModel.queryShopQueryAttr(NewMealShopDetailsActivity.this, shop, params[0]);
//                    }
//
//                    @Override
//                    protected void onPostExecute(FragmentActivity context, Shop shop, Exception e) {
//
//                        if (e != null) {// 查询异常
//                            Toast.makeText(NewMealShopDetailsActivity.this, "连接超时，请重试", Toast.LENGTH_LONG).show();
//
//                        } else {// 查询商品详情成功，刷新界面
//                            if (shop != null) {
//                                NewMealShopDetailsActivity.this.shop = shop;
//                                showPopWindow(i);
//                            }
//                        }
//
//                    }
//
//                    ;
//
//                    @Override
//                    protected boolean isHandleException() {
//                        return true;
//                    }
//
//                    ;
//                }.execute("false");
//            }
//
//        }
//    }
//
//    private GoodsEntity entity;
//
//
//
//    /**
//     * @param p_code
//     * @param shop_num
//     * @param p_seq
//     * @param catJson
//     * @param p_type
//     * @param postage
//     * @param shop_price
//     * @param shop_se_price
//     * @param supp_id
//     */
//
//
//    /****
//     * 弹出底部对话框
//     *
//     * @param i
//     */
//    private void showPopWindow(int i) {
//        if (shop != null && !this.isFinishing()) {
//            final ShopDetailsDialog dlg = new ShopDetailsDialog(this, R.style.DialogStyle, width, height, shop, i, false, "-1", "-1", "1");
//            Window window = dlg.getWindow();
//            window.setGravity(Gravity.BOTTOM);
//            window.setWindowAnimations(R.style.dlg_down_to_top);
//            dlg.show();
//            dlg.setOnDismissListener(new OnDismissListener() {
//
//                @Override
//                public void onDismiss(DialogInterface arg0) {
//                    // TODO Auto-generated method stub
//                    clickFlag = true;
//                }
//            });
//            dlg.callBackShopCart = new ShopDetailsDialog.OnCallBackShopCart() {
//
//                @Override
//                public void callBackChoose(int type, String size, String color, double price, int shop_num,
//                                           int stock_type_id, int stock, String pic, int supp_id, double kickback, int original_price,
//                                           View v) {
//                    dlg.dismiss();
//                    // clickFlag = true;
//                    entity = new GoodsEntity(pic, size, color, shop_num, stock_type_id, stock, supp_id, stock_type_id,
//                            price, kickback, original_price);
//
//                    if (type == 1) {// 购买  活动商品直接购买
//
//                        if (isMembers) {
//
//                        } else {
////							Intent intent = new Intent(NewMealShopDetailsActivity.this, SubmitOrderActivity.class);
//                            Intent intent = new Intent(NewMealShopDetailsActivity.this, SubmitMultiShopActivty.class);// 购买  活动商品直接购买
//                            Bundle bundle = new Bundle();
//
//                            List<ShopCart> listGoods = new ArrayList<ShopCart>();
//                            ShopCart shopCart = new ShopCart();
//                            shopCart.setShop_code(shop.getShop_code());
//                            shopCart.setShop_num(shop_num);
//                            shopCart.setSize(size);
//                            shopCart.setColor(color);
//                            shopCart.setShop_price(shop.getShop_price());
//                            shopCart.setShop_se_price(shop.getShop_se_price());
//                            shopCart.setOriginal_price(Double.valueOf(original_price));
//                            shopCart.setDef_pic(pic);
//                            shopCart.setStock_type_id(stock_type_id);
//                            shopCart.setSupp_id(shop.getSupp_id());
//                            shopCart.setShop_name(shop.getShop_name());
//                            shopCart.setCore("0");
//                            shopCart.setKickback(0.0);
//                            shopCart.setUser_id(YCache.getCacheUser(context).getUser_id());
//                            shopCart.setStore_code(YCache.getCacheStore(context).getS_code());
//                            listGoods.add(shopCart);
//
//                            bundle.putSerializable("listGoods", (Serializable) listGoods);
//                            bundle.putBoolean("isSignActiveShop", isSignActiveShop);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                        }
//                    } else {// 加入购物车
//
//
//                    }
//                }
//            };
//        }
//    }
//
//
//    class TimeCount extends CountDownTimer {
//        private TextView tv = null;
//
//        public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
//            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
//            this.tv = tv;
//
//        }
//
//        @Override
//        public void onFinish() {// 计时完毕时触发
//            try {
//                tv.setText("打折结束");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {// 计时过程显示
//            try {
//                long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
//                long nh = 1000 * 60 * 60;// 一小时的毫秒数
//                long nm = 1000 * 60;// 一分钟的毫秒数
//                long ns = 1000;// 一秒钟的毫秒数
//                long diff = millisUntilFinished;
//                long day = diff / nd;// 计算差多少天
//                long hour = diff % nd / nh;// 计算差多少小时
//                long min = diff % nd % nh / nm;// 计算差多少分钟
//                long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
//
//                tv.setText("距打折时间还有" + day + "天    " + hour + "小时" + min + "分" + sec + "秒");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private int titleCheck = 0;// 当前导航栏选择
//
//    @Override
//    public void myOnClick(View v) {
//        // mType = 1;
//        // index1 = 1;
//        // // mIndex = v.getId();
//        // position = v.getId();
//        // initData(v.getId(), index1 + "");
//        mType = 1;
//        index = 1;
//        titleCheck = v.getId();
//        initData(v.getId(), index + "");
//        if (isMeal || "SignShopDetail".equals(signShopDetail)) {
//            if (mListView.getFirstVisiblePosition() > (imageTag1.length + imageTag2.length + imageTag3.length)) {
//                mListView.setSelection(imageTag1.length + imageTag2.length + imageTag3.length + 1);
//            }
//        } else {
//            if (mListView.getFirstVisiblePosition() > images.length) {
//                mListView.setSelection(images.length + 1);
//            }
//        }
//    }
//
//    private boolean isInit = false;
//
//    private void initData(final int position, final String index) {
//        isInit = true;
//        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(this, 0) {
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (dataList == null) {
//                        dataList = new LinkedList<HashMap<String, Object>>();
//                    }
//                    if (mType == 1) {
//                        dataList.clear();
//                    }
//                    if (mType == 2 && (result == null || result.isEmpty())) {
//                        Toast.makeText(NewMealShopDetailsActivity.this, "已经到底了", Toast.LENGTH_SHORT).show();
//                        // myListView.onRefreshComplete();
//                        return;
//                    }
//                    dataList.addAll(result);
//                    adapter.notifyDataSetChanged();
//                    isInit = false;
//                }
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
//                    throws Exception {
////				return ComModel2.getProductListUnLogin(context, index, listTitle.get(position).get("_id"),
////						String.valueOf(1), listTitle.isEmpty() ? null : listTitle.get(position).get("sort_name"),
////						Integer.parseInt("30"));
//
//                return ComModel2.getProductListUnLogin(context, index, listTitle.get(position).get("_id"),
//                        String.valueOf(1), listTitle.isEmpty() ? null : listTitle.get(position).get("sort_name"),
//                        Integer.parseInt("30"), false);
//            }
//
//        }.execute();
//    }
//
//    @Override
//    public void onCheck(int index) {
//
//        if (check == index) {
//            return;
//        }
//        check = index;
//        adapter.notifyDataSetChanged();
//        if (check == 2) {
//            if (isMeal || isMembers || "SignShopDetail".equals(signShopDetail)) {
//                if (tuijianList == null) {
//                    queryTuijian();
//                }
//                return;
//            }
//            if (listShopComments == null && isCheck == false) {
//            }
//
//        }
//
//        if (mListView.getFirstVisiblePosition() > 0) {
//            mListView.setSelection(1);
//        }
//
//    }
//
//    private int rows = 10, page = 1;
//    private boolean isCheck = false;
//
//
//    private List<ShopOption> tuijianList;
//    private String number_sold;
//    private RelativeLayout rl_hava_twenty;
//    private boolean shopCart;
//    private boolean isforcelook;
//    private boolean isforcelookMatch;
//    private boolean isSignActiveShop;//签到活动商品（仅判断是否是活动商品）
//    private boolean isSignActiveShopScan;//签到活动商品 并且是浏览任务
//    private SimpleDateFormat df;
//    private int virtual_sales;
//
//    private TextView tv_shop_car_fake;
//
//    private ImageView img_cart_new;
//
//    // private RelativeLayout rl_heaed_all;
//    // private RelativeLayout rl_retain_top;
//
//    private void queryTuijian() {
//        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, null, R.string.wait) {
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel2.getMainTuijianData(context, "2");
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null) {
//                    tuijianList = (List<ShopOption>) result.get("centShops");
//                    adapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            ;
//        }.execute();
//    }
//
//
//    private void yunYunTongJi(final String shop_code, final int type, final int tab_type) {
//        new SAsyncTask<Void, Void, HashMap<String, String>>(this, R.string.wait) {
//
//            @Override
//            protected boolean isHandleException() {
//                // TODO Auto-generated method stub
//                return true;
//            }
//
//            @Override
//            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel2.getOperator(context, shop_code, type, tab_type);
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
//                // TODO Auto-generated method stub
//                super.onPostExecute(context, result, e);
//
//            }
//
//        }.execute();
//    }
//
//    // 加入购物车动画
//    private RelativeLayout img_cart_cart;
//
//    private void setAnim() {
//        if (signShopDetail == null || !signShopDetail.equals("SignShopDetail")) {
//            img_cart_cart = (RelativeLayout) this.findViewById(R.id.img_cart_cart);
//        }
//        // 获取起点坐标
//        int[] location1 = new int[2];
//        mCartPoint.getLocationInWindow(location1);
//        int x1 = location1[0];
//        int y1 = location1[1];
//        // 获取终点坐标，最近拍摄的坐标
//        int[] location2 = new int[2];
//        tv_cart_count.getLocationInWindow(location2);
//        int x2 = location2[0];
//        int y2 = location2[1];
//        // int[] location3 = new int[2];
//        int x3 = (x1 + x2) / 2;
//        int y3 = y2 - 90;
//        // 两个位移动画
//        TranslateAnimation translateAnimationX = new TranslateAnimation(0, x3 - x1, 0, 0); // 横向动画
//        final TranslateAnimation translateAnimationX2 = new TranslateAnimation(x3 - x1, x2 - x1, 0, 0); // 横向动画
//        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, y3 - y1); // 竖向动画
//        final TranslateAnimation translateAnimationY2 = new TranslateAnimation(0, 0, y3 - y1, y2 - y1); // 竖向动画
//        translateAnimationX.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
//        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
//        translateAnimationX2.setInterpolator(new LinearInterpolator()); // 横向动画设为匀速运动
//        translateAnimationX2.setRepeatCount(0);// 动画重复执行的次数
//        translateAnimationY.setInterpolator(new AccelerateDecelerateInterpolator());
//        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
//        // translateAnimationY.setRepeatMode(Animation.REVERSE);
//        translateAnimationY2.setInterpolator(new AccelerateInterpolator()); // 竖向动画设为开始结尾处加速，中间迅速
//        translateAnimationY2.setRepeatCount(0);// 动画重复执行的次数
//        // 组合动画
//        final AnimationSet anim = new AnimationSet(false);
//        final AnimationSet anim2 = new AnimationSet(false);
//        anim.setFillAfter(false); // 动画结束不停留在最后一帧
//        anim2.setFillAfter(false); // 动画结束不停留在最后一帧
//        anim.addAnimation(translateAnimationX);
//        anim.addAnimation(translateAnimationY);
//
//        anim2.addAnimation(translateAnimationX2);
//        anim2.addAnimation(translateAnimationY2);
//        anim2.setDuration(400);// 动画的执行时间
//        anim2.setStartOffset(0);
//
//        anim.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mCartPoint.startAnimation(anim2);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//        });
//        anim2.setAnimationListener(new AnimationListener() { // 抛物线动画结束后
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                pointRoot.removeAllViews();
//                mCartPoint.setVisibility(View.INVISIBLE);
//                tv_cart_count.setVisibility(View.VISIBLE);// 抛物线动画结束后 即可显示数量小圆点
//                // queryCartCount();
//                // 如果倒计时显示
//                if (isMeal) {
//                    if (tv_time_count_down_meal.getVisibility() != View.VISIBLE) {
//                        setAnim1();
//                    } else {
//                        setAnim2();
//                    }
//                } else {
//                    if (tv_time_count_down.getVisibility() != View.VISIBLE) {
//                        setAnim1();
//                    } else {
//                        setAnim2();
//                    }
//                }
//            }
//        });
//
//        // 播放
//        mCartPoint.setVisibility(View.VISIBLE);
//        // refreshCouldIndicator(activity, null, null);
//        // translateAnimationX.setDuration(800);
//        // translateAnimationY.setDuration(400);
//        anim.setDuration(400);// 动画的执行时间
//        anim.setStartOffset(100);
//        mCartPoint.startAnimation(anim);
//    }
//
//    /**
//     * 购物车左移动画
//     */
//    protected void setAnim1() {
//        RelativeLayout rel = (RelativeLayout) findViewById(R.id.img_cart);
//        int[] location1 = new int[2];
//        int[] location2 = new int[2];
//        rel.getLocationInWindow(location1);
//        img_cart_cart.getLocationInWindow(location2);
//        TranslateAnimation animation = new TranslateAnimation(0, location1[0] - location2[0] + 4, 0, 0);
//        animation.setRepeatCount(0);// 动画重复执行的次数
//        animation.setDuration(400);// 动画的执行时间
//        animation.setFillAfter(false); // 动画结束不停留在最后一帧
//        animation.setStartOffset(0);
//        // animation.setInterpolator(new BounceInterpolator());
//        img_cart_cart.startAnimation(animation);
//        animation.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // clickFlag = true;
//                img_cart_cart.clearAnimation();
//                // mAddCartTv.setClickable(true);
//                if (isMeal) {
//                    tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                    tv_time_count_down.setVisibility(View.GONE);
//                } else {
//                    tv_time_count_down_meal.setVisibility(View.GONE);
//                    tv_time_count_down.setVisibility(View.VISIBLE);
//                }
//                // 显示立即结算
//                showRetain();
//                queryCartCountAdd();
//            }
//
//        });
//    }
//
//    /**
//     * 购物车圆点缩放动画
//     */
//    protected void setAnim2() {
//        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f);
//        animation.setRepeatCount(0);// 动画重复执行的次数
//        animation.setDuration(400);// 动画的执行时间
//        animation.setFillAfter(false); // 动画结束不停留在最后一帧
//        animation.setStartOffset(0);
//        tv_cart_count.startAnimation(animation);
//        animation.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // clickFlag = true;
//                img_cart_cart.clearAnimation();
//                // 显示立即结算
//                showRetain();
//                queryCartCountAdd();
//            }
//
//        });
//    }
//
//    private RelativeLayout rootView;
//    private ImageView mCartPoint;
//    private RelativeLayout pointRoot;
//    private LinearLayout ll_bottem;
//    private Animation baoyou_animationShow;
//    private Animation baoyou_animationGone;
//
//    /**
//     * 添加动画布局
//     */
//    private void addAnimLayout() {
//        rootView = (RelativeLayout) findViewById(R.id.rrr);
//        pointRoot = new RelativeLayout(context);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT);
//        pointRoot.setBackgroundColor(Color.TRANSPARENT);
//        pointRoot.setLayoutParams(params);
//        rootView.addView(pointRoot);
//
//        mCartPoint = new ImageView(context);
//        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(DP2SPUtil.dp2px(context, 12),
//                DP2SPUtil.dp2px(context, 12));
//        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params2.bottomMargin = DP2SPUtil.dp2px(context, 36);
//        params2.rightMargin = DP2SPUtil.dp2px(context, 40);
//        mCartPoint.setLayoutParams(params2);
//        mCartPoint.setBackgroundResource(R.drawable.red_point_bg);
//        pointRoot.addView(mCartPoint);
//        mCartPoint.setVisibility(View.VISIBLE);
//
//    }
//
//    /**
//     * 添加购物车时候查询 购物车数量 并显示倒计时
//     */
//    private void queryCartCountAdd() {
//
//        // new SAsyncTask<Void, Void, HashMap<String, Object>>(this,
//        // R.string.wait) {
//        //
//        // @Override
//        // protected HashMap<String, Object> doInBackground(FragmentActivity
//        // context, Void... params)
//        // throws Exception {
//        // return ComModel2.getMatchShopCartCount(context);
//        // }
//        //
//        // @Override
//        // protected boolean isHandleException() {
//        // return true;
//        // }
//        //
//        // @Override
//        // protected void onPostExecute(FragmentActivity context,
//        // HashMap<String, Object> result, Exception e) {
//        // super.onPostExecute(context, result, e);
//        // if (e != null || result == null) {
//        // return;
//        // }
//        // String count = (String) result.get("cart_count");
//        // int cartCount = Integer.valueOf(count);
//
//        ShopCartDao dao = new ShopCartDao(context);
//        // int count = dao.queryCartCount(context);
//        int count = 0;
//        if (isMeal) {
//            count = dao.queryCartSpecialCount(context);
//        } else {
//            count = dao.queryCartCommonCount(context);
//        }
//        if (/* cartCount */count > 0) {
//            // tv_cart_count.setVisibility(View.VISIBLE);
//            tv_cart_count.setText(count + "");
//            // tv_time_count_down.setVisibility(View.VISIBLE);
//        } else {
//            // tv_cart_count.setVisibility(View.INVISIBLE);
//            // tv_time_count_down.setVisibility(View.GONE);
//        }
//
//        // Long sTime = (Long) result.get("s_time");// 系统当前时间
//        // Long sDeadline = (Long)result.get("s_deadline");//商品过期时间
//        // Long sDeadline = sTime + 30 * 60 * 1000;// 商品过期时间
//        Long sTime = new Date().getTime();// 系统当前时间
//
//        Long sDeadline = isMeal
//                ? Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_MEAL_TIME, "0"))
//                : Long.valueOf(SharedPreferencesUtil.getStringData(context, Pref.SHOPCART_COMMON_TIME, "0"));// 商品过期时间
//
//        if (sDeadline == 0) {// 表示还没有存到本地
//            if (isMeal) {
//                SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_MEAL_TIME, sTime + 30 * 1000 * 60 + "");
//            } else {
//                SharedPreferencesUtil.saveStringData(context, Pref.SHOPCART_COMMON_TIME, sTime + 30 * 1000 * 60 + "");
//            }
//            sDeadline = sTime + 30 * 60 * 1000;
//        }
//        // 购物车倒计时
//        if (/* cartCount */count > 0) {// 正数代表加入了购物车 显示倒计时
//            if (isMeal) {
//                tv_time_count_down_meal.setVisibility(View.VISIBLE);
//                tv_time_count_down.setVisibility(View.GONE);
//            } else {
//                tv_time_count_down_meal.setVisibility(View.GONE);
//                tv_time_count_down.setVisibility(View.VISIBLE);
//            }
//            String c_time_cart = DateFormatUtils.format(sDeadline, "yyyy-MM-dd HH:mm:ss");
//            String s_time = DateFormatUtils.format(sTime, "yyyy-MM-dd HH:mm:ss");
//
//            try {
//                if (isMeal) {
//                    if (timer_meal != null) {
//                        timer_meal.cancel();
//                    }
//                    task_meal = new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            runOnUiThread(new Runnable() { // UI thread
//                                @Override
//                                public void run() {
//                                }
//                            });
//                        }
//                    };
//                    timer_meal = new Timer();
//                    timer_meal.schedule(task_meal, 0, 1000); // 显示倒计时
//
//                    SimpleDateFormat df_meal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date d1_meal = df_meal.parse(c_time_cart);
//                    Date d2_meal = df_meal.parse(s_time);
//                    long diff_meal = d1_meal.getTime() - d2_meal.getTime();
//                } else {
//                    if (timer != null) {
//                        timer.cancel();
//                    }
//                    task = new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            runOnUiThread(new Runnable() { // UI thread
//                                @Override
//                                public void run() {
//                                }
//                            });
//                        }
//                    };
//                    timer = new Timer();
//                    timer.schedule(task, 0, 1000); // 显示倒计时
//
//                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date d1 = df1.parse(c_time_cart);
//                    Date d2 = df1.parse(s_time);
//                    long diff = d1.getTime() - d2.getTime();
//                    recLen = diff;
//                }
//
//            } catch (ParseException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//        } else {// 负数代表或0没有加入购物车
//            tv_time_count_down.setVisibility(View.GONE);
//            tv_time_count_down_meal.setVisibility(View.GONE);
//        }
//        // }
//        // }.execute();
//    }
//
//    private void showRetain() {
//        rl_retain.setVisibility(View.VISIBLE);
//        // rl_retain_top.setVisibility(View.VISIBLE);
//        rl_retain.getBackground().setAlpha(204);
//        final Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 1) {
//                    rl_retain.setVisibility(View.GONE);
//                    // rl_retain.getBackground().setAlpha(0);
//                }
//            }
//
//            ;
//        };
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(60 * 1000);
//                    // rl_retain.setVisibility(View.GONE);
//                    Message message = new Message();
//                    message.what = 1;
//                    handler.sendMessage(message);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
//
//    /**
//     * 动画
//     */
//    private void pingyi() {
//        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        int width = display.getWidth();
//        int height = display.getHeight();
//
//        int height_bottom = rlBottom.getHeight();
//        // System.out.println("控件高度=" + height_bottom);
//        int heitiao = rl_retain.getHeight();
//
//        Animation translateAnimation1 = new TranslateAnimation(0.1f, 100.0f, height - height_bottom, height_bottom);
//        translateAnimation1.setDuration(500); // 设置动画时间
//
//        Animation translateAnimation2 = new TranslateAnimation(0.1f, 100.0f, height - height_bottom - heitiao,
//                height - heitiao);
//        translateAnimation2.setDuration(500); // 设置动画时间
//        // this.startAnimation(translateAnimation);
//        if (rl_retain.getVisibility() == View.VISIBLE) {
//            rlBottom.startAnimation(translateAnimation1);
//            rl_retain.startAnimation(translateAnimation2);
//        } else {
//            rlBottom.startAnimation(translateAnimation1);
//        }
//    }
//
//    private void setShareAnim() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String shareAnim = SharedPreferencesUtil.getStringData(context, Pref.SHAREANIM, "0");
//        long shareAnimTime = Long.valueOf(shareAnim);
//        boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
//        if (!isRoate) {
//            return;
//        }
//        RotateAnimation ani1 = new RotateAnimation(0f, 35f, Animation.RELATIVE_TO_SELF, 1.0f,
//                Animation.RELATIVE_TO_SELF, 1.0f);
//        ScaleAnimation ani2 = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f, Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        final AnimationSet set = new AnimationSet(context, null);
//        ani1.setDuration(270);
//        ani1.setRepeatMode(Animation.REVERSE);
//        // ani1.setRepeatCount(1);
//        ani1.setFillAfter(false);
//        // ani1.setStartOffset(1500);
//        ani2.setDuration(270);
//        ani2.setRepeatMode(Animation.RESTART);
//        // ani2.setRepeatCount(Integer.MAX_VALUE);
//        ani2.setFillAfter(false);
//        // ani2.setStartOffset(1500);
//
//        set.addAnimation(ani1);
//        set.addAnimation(ani2);
//        set.setStartOffset(600);
//        // redShare.setAnimation(set);
//        redShare.startAnimation(set);
//
//        final RotateAnimation ani3 = new RotateAnimation(-12f, 10f, Animation.RELATIVE_TO_SELF, 1.0f,
//                Animation.RELATIVE_TO_SELF, 1.0f);
//        ani3.setDuration(55);
//        ani3.setRepeatMode(Animation.REVERSE);
//        ani3.setRepeatCount(2);
//        ani3.setFillAfter(true);
//        final RotateAnimation ani4 = new RotateAnimation(-6f, 6f, Animation.RELATIVE_TO_SELF, 1.0f,
//                Animation.RELATIVE_TO_SELF, 1.0f);
//        ani4.setDuration(45);
//        ani4.setRepeatMode(Animation.REVERSE);
//        ani4.setRepeatCount(1);
//        ani4.setFillAfter(false);
//
//        ani1.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                moneyShare.startAnimation(ani3);
//                set.setStartOffset(1300);
//            }
//        });
//        ani3.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                moneyShare.startAnimation(ani4);
//            }
//        });
//        ani4.setAnimationListener(new AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                redShare.startAnimation(set);
//            }
//        });
//    }
//
//
//    public void getSignJoinShopCartDialog(Double jiangliValue) {
//        final Dialog signDialog = new Dialog(NewMealShopDetailsActivity.this, R.style.invate_dialog_style);
//        View view = View.inflate(NewMealShopDetailsActivity.this, R.layout.dialog_sign_join_shopcart, null);
//        TextView mTvJiangli = (TextView) view.findViewById(R.id.dialog_sign_jiangli);
//        String mNotice = "元";
//        int jiangliType = 5;
//        try {
//            jiangliType = SignFragment.jiangliIDShopCart;
//        } catch (Exception e2) {
//        }
//        switch (jiangliType) {
//            case 3:
//                mNotice = "元优惠券";
//                break;
//            case 4:
//                mNotice = "积分";
//                break;
//            case 5:
//                mNotice = "元";
//                break;
//
//            default:
//                break;
//        }
//        String s = jiangliValue + mNotice + "奖励已存入你的账户，如果喜欢这件美衣就带它回家吧~";
//        String l = jiangliValue + mNotice;
//        mTvJiangli.setText(s);
//        SpannableStringBuilder builder = new SpannableStringBuilder(mTvJiangli.getText().toString());
//        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#ff3f8b"));
//        builder.setSpan(redSpan, 0, l.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTvJiangli.setText(builder);
//        TextView mClose = (TextView) view.findViewById(R.id.tv_close);
//        mClose.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                signDialog.dismiss();
//            }
//        });
//        Button liebiao = (Button) view.findViewById(R.id.dialog_liebiao);
//        liebiao.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NewMealShopDetailsActivity.this, MainMenuActivity.class);
//                intent.putExtra("Exit30", true);
//                startActivity(intent);
//            }
//        });
//        Button gobuy = (Button) view.findViewById(R.id.gobuy2);
//        gobuy.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                signDialog.dismiss();
//                Intent intent = new Intent(NewMealShopDetailsActivity.this, ShopCartNewNewActivity.class);
//                startActivity(intent);
//            }
//        });
//        signDialog.setContentView(view);
//        signDialog.setCancelable(true);
//        signDialog.setCanceledOnTouchOutside(false);
//        signDialog.show();
//    }
//
//    private void sign_shopcart(final boolean flag, final int num) {
//        // 签到
//        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) NewMealShopDetailsActivity.this, 0) {
//
//            @Override
//            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
//                    throws Exception {
//
//                return ComModel2.getSignIn(NewMealShopDetailsActivity.this, false, false,
//                        SharedPreferencesUtil.getStringData(context, YConstance.ADD_TO_SHOPCART, ""), SignFragment.doClass);
//
//            }
//
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            ;
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (e == null && result != null) {
//                    int needValue = 1;
//                    try {
//                        needValue = Integer.parseInt(SignFragment.doValueShopCart);
//                    } catch (Exception e2) {
//                    }
//                    int signNumber = 1;//1，代表一次性奖励，大于1代表多次奖励
//                    try {
//                        signNumber = SignFragment.doNumShopCart;
//                    } catch (Exception e2) {
//                    }
//                    double jiangliValue = 0.0;
//                    try {
//                        jiangliValue = Double.parseDouble(SignFragment.jiangliValueShopCart);
//                    } catch (Exception e2) {
//                    }
//                    if (flag) {
//                        getSignJoinShopCartDialog(signNumber * jiangliValue);
//                        SharedPreferencesUtil.saveStringData(NewMealShopDetailsActivity.this, "qiandao_time" + YCache.getCacheUser(NewMealShopDetailsActivity.this).getUser_id(), "-1");
//                    } else {
//                        String mNotice = "元";
//                        int jiangliType = 5;
//                        try {
//                            jiangliType = SignFragment.jiangliIDShopCart;
//                        } catch (Exception e2) {
//                        }
//                        switch (jiangliType) {
//                            case 3:
//                                mNotice = "元优惠券";
//                                break;
//                            case 4:
//                                mNotice = "积分";
//                                break;
//                            case 5:
//                                mNotice = "元";
//                                break;
//
//                            default:
//                                break;
//                        }
//                        ToastUtil.showShortText(context, "加入成功奖励" + jiangliValue + mNotice + "，还有" + num + "次机会喔~");
//                    }
//                } else {
////					ToastUtil.showLongText(mContext, "未知错误");
//                }
//
//            }
//
//        }.execute();
//    }
//}
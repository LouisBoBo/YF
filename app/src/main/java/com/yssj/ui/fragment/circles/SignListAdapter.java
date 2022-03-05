package com.yssj.ui.fragment.circles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.BaseData;
import com.yssj.entity.LastPTdata;
import com.yssj.entity.SignTask53Data;
import com.yssj.huanxin.PublicUtil;
import com.yssj.model.ComModelL;
import com.yssj.model.ModQingfeng;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.BuyFreeActivity;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.FriendCommissionActivity;
import com.yssj.ui.activity.GroupsDetailsActivity;
import com.yssj.ui.activity.HomePageThreeActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MineLikeActivity;
import com.yssj.ui.activity.MyLikeActivity;
import com.yssj.ui.activity.MyYJactivity;
import com.yssj.ui.activity.OneBuyGroupsDetailsActivity;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.activity.ShopPageActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.WithdrawalLimitActivity;
import com.yssj.ui.activity.logins.LoginActivity;
import com.yssj.ui.activity.main.CrazyShopListActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMadActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.HotSaleActivity;
import com.yssj.ui.activity.main.IndianaListActivity;
import com.yssj.ui.activity.main.NewPThotsaleActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.dialog.DialogSignFenzhongTishi;
import com.yssj.ui.dialog.DianZanSucceedDiaolg;
import com.yssj.ui.dialog.JiZanCommonDialog;
import com.yssj.ui.dialog.LingYUANGOUTishiRedDialog;
import com.yssj.ui.dialog.NewShareGetTXDialog;
import com.yssj.ui.dialog.NewSignCommonDiaolg;
import com.yssj.ui.dialog.ShareGetTXdialog;
import com.yssj.ui.dialog.SignShareShopDialog;
import com.yssj.ui.fragment.YaoQingFrendsActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.EntityFactory;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;
import com.yssj.wxpay.Util;
import com.yssj.wxpay.WxPayUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import static com.yssj.ui.base.BasicActivity.shareWaitDialog;
import static com.yssj.ui.fragment.circles.SignFragment.isGratis;
import static com.yssj.utils.WXminiAPPShareUtil.buildTransaction;


public class SignListAdapter extends BaseAdapter {


    public static RefreshListener mRefreshListener;

    public interface RefreshListener {
        void signRefresh();
    }

    public static void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }


    private LayoutInflater mInflater;
    private int tast; // 1---必做 2---额外 3---明日 4---惊喜 5---惊喜提现 6--集赞 7--夺宝（已经改成超级惊喜任务）
    private List<HashMap<String, Object>> taskList;// 奖励类型列表

    public static Context mContext;
    private List<HashMap<String, Object>> taskiconList;


    private List<HashMap<String, Object>> initList;// 用来填充的list

    public static String doIconId = "";

    public static String gotoShareValue = "";//分享X件商品的分享区域

    public static boolean jizanCoplete;

    private String fxqd_new = "0";

    public static boolean neeedFenzhongCompleteDiaog = false;

    public static boolean showFirstClickInSuccseeDialog = false;


    public static int orderStatus = 0;//开团任务的已完成状态：0未完成，1已完成 2：第三次开团（需要去掉开团任务，开团任务只能有两次）
    public static int orderCount = 0;//开团次数
    public static String pingTuanNum = "-1";//拼团编号
    public static int offered = 0;//要参的团的状态
    public static boolean hasGoumai;//是否有购买任务


    public static int lotterynumber;// 抽奖剩余数


    // 浏览分钟数的单独赋值
    public static int doNumShopLiulan;// num
    public static int jiangliIDLiulan; // 奖励的ID
    public static String jiangliValueLiulan = ""; // 奖励的VULUE
    public static String doValueLiulan = ""; // 做什么的value

    public static boolean isForceLookTimeOut;// 强制浏览分钟数 时间


    public static int task43Index = -1;


    // 浏览穿搭的单独赋值
    public static int doNumCD;// num-----穿搭
    public static String doValueCD = ""; // 做什么的value-----穿搭
    public static int jiangliIDCD; // 奖励的ID---穿搭
    public static String jiangliValueCD = ""; // 奖励的VULUE---穿搭

    public static int jiangliIDJX; // 奖励的ID---精选推荐
    public static String jiangliValueJX = ""; // 奖励的VULUE---精选推荐


    // 加购物车的单独赋值
    public static int doNumShopCart;// num-----购物车
    public static String doValueShopCart = ""; // 做什么的value-----购物车
    public static int jiangliIDShopCart; // 奖励的ID---购物车
    public static String jiangliValueShopCart = ""; // 奖励的VULUE---购物车


    public static String signIndex; // 点击时获取去到每个任务的index
    public static int tuanClass = 1;
    public static String canTuanIndex; // 参团任务的index
    public static String jizanIndex; // 分享邀请好友集赞任务的index

    public static boolean isSignComplete; // 点击到的当前任务是否已完成 true--已完成 false---未完成


    public static int jiangliID; // 奖励的ID
    public static int doType; // 去做什么的类型
    public static int doClass_jx; // 区分大类型 1必做 2额外 3惊喜
    public static int doClass; // 区分大类型 1必做 2额外 3惊喜
    public static int doNum;
    public static String jiangliValue = ""; // 奖励的VULUE
    public static String doValue = ""; // 做什么的value


    public static HashMap<String, String> indexMap = new HashMap<String, String>(); // 存放index，每点击一次就存一次

    public static HashMap<String, String> minuteMap = new HashMap<String, String>(); // 存放浏览分钟数的任务

    public static HashMap<String, Integer> classMap = new HashMap<String, Integer>(); // 存放task_class，每点击一次就存一次

    public static HashMap<String, Integer> jiangliIDmap = new HashMap<String, Integer>(); // 奖励id，每点击一次就存一次

    public static HashMap<String, String> jiangliValueMap = new HashMap<String, String>(); // 奖励value，每点击一次就存一次

    public static HashMap<String, String> fenzhongDoValueMap = new HashMap<String, String>(); // 分钟数的doValue，每点击一次就存一次


    public static HashMap<String, String> fenzhongIconID = new HashMap<String, String>(); // 分钟数的doIconID，每点击一次就存一次


    public static HashMap<String, String> addShopCartIconID = new HashMap<String, String>(); // 加购物车的doIconID，每点击一次就存一次


    public static String doNeedCount = "-1"; // 点击时拿到当前任务的status ，------当前任务需要做的剩余次数

    public static boolean doSignGo = false;


    private String jingxiGoumaiName = "购买一件商品全奖励翻倍";
    private String meiyiJieBot = "";
    private String quchoujiangjiangli = "555";
    public static Activity mActivity;


    public SignListAdapter(int tast,
                           List<HashMap<String, Object>> taskList, // 任务类型
                           List<HashMap<String, Object>> initList,
                           List<HashMap<String, Object>> taskiconList,
                           Context mContext,
                           Activity mActivity) {
        this.taskList = taskList;
        this.tast = tast;
        this.initList = initList;
        this.mContext = mContext;
        this.taskiconList = taskiconList;
        this.mActivity = mActivity;


        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {

        return initList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {

            for (int i = 0; i < taskList.size(); i++) { // 遍历任务类型 然后用当天务列表
                // 去做对比

                String id = initList.get(position).get("t_id").toString();
                String taskID = (String) taskList.get(i).get("t_id"); // 任务类型ID

                String task_type = initList.get(position).get("task_type").toString();

                if (task_type.equals("9") || task_type.equals("999")) { // 是免单和疯狂星期一

                    // 单独做

                    if (task_type.equals("9")) {// 免单

                        holder = new SignListAdapter.Holder();
                        convertView = mInflater.inflate(R.layout.item_signtask_new, null);
                        holder.tv_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu);
                        holder.vvv = (View) convertView.findViewById(R.id.vvv);

                        holder.tv_jiangli_count = (TextView) convertView.findViewById(R.id.tv_jiangli_count);
                        holder.tv_jiangli_cunt_danwei = (TextView) convertView
                                .findViewById(R.id.tv_jiangli_cunt_danwei);
                        holder.tv_miaoshu_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu_miaoshu);
                        holder.tv_jiangli_neirong = (TextView) convertView.findViewById(R.id.tv_jiangli_neirong);
                        holder.tv_miaoshu_gouwu = (TextView) convertView.findViewById(R.id.tv_miaoshu_gouwu);
                        holder.tv_jiahao = (TextView) convertView.findViewById(R.id.tv_jiahao);

                        holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                        holder.iv_complete = (ImageView) convertView.findViewById(R.id.iv_complete);
                        holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                        holder.sign_rl = (RelativeLayout) convertView.findViewById(R.id.sign_rl);
                        convertView.setTag(holder);

                        holder.iv_icon.setImageResource(R.drawable.icon_miandan);

                        holder.vvv.setVisibility(View.VISIBLE);


                        holder.tv_miaoshu_gouwu.setVisibility(View.VISIBLE);

                        holder.tv_jiangli_count.setVisibility(View.GONE);
                        holder.tv_jiangli_neirong.setVisibility(View.GONE);
                        holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                        holder.tv_jiahao.setVisibility(View.GONE);

                        // holder.tv_miaoshu.setTextColor(Color.parseColor("#FFFFFF"));
                        // holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg_jingxi);
//                            convertView.setBackgroundColor(Color.parseColor("#00000000"));

                        holder.tv_miaoshu.setText("你时尚我买单！");
                        Boolean signCoplete = (initList.get(position).get("signStatus") + "").equals("1"); // 任务时当时的完成状态
                        // 1代表已完成
                        if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) { // 已登录
                            if (signCoplete) {
                                holder.iv_complete.setImageResource(R.drawable.icon_yilingqu);
                                // holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg_c5);
                            } else {

                                holder.iv_complete.setVisibility(View.GONE);
                                // holder.iv_icon.clearAnimation();
                                // 动画
                                // signAnimation =
                                // AnimationUtils.loadAnimation(mContext,
                                // R.anim.signanim);
                                // holder.iv_wan_buy.startAnimation(signAnimation);


                                // 登录了且任务未完成 则提示用户获得了免单资格 ---一个版本提示一次
                                int buyCountt = 1; // 1:50元以上和100元以上,2:50元以上

                                try {

                                    buyCountt = Integer.parseInt(initList.get(position) // 任务数量（去做什么）
                                            .get("value") + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                String versionName = "-1";
                                try {
                                    PackageManager pm = mContext.getPackageManager();
                                    PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                                    versionName = "v" + pi.versionName;
                                } catch (Exception e) {

                                }

                                if (SharedPreferencesUtil.getStringData(mContext, versionName, "-1").equals("-1")) { // 代表更新了版本

                                    if (buyCountt == 2) {
                                        showFreeFormDialog(0);
                                    }
                                    if (buyCountt == 1) {
                                        showFreeFormDialog(1);
                                    }

                                }

                            }
                        } else { // 未登录

                            holder.iv_complete.setVisibility(View.GONE);

                        }

                    } else { // 疯狂星期一
                        holder = new Holder();
                        convertView = mInflater.inflate(R.layout.item_signtask_new, null);
                        holder.tv_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu);
                        holder.vvv = (View) convertView.findViewById(R.id.vvv);

                        holder.tv_jiangli_count = (TextView) convertView.findViewById(R.id.tv_jiangli_count);
                        holder.ll_jiangli = (LinearLayout) convertView.findViewById(R.id.ll_jiangli);
                        holder.tv_jiangli_cunt_danwei = (TextView) convertView
                                .findViewById(R.id.tv_jiangli_cunt_danwei);
                        holder.tv_miaoshu_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu_miaoshu);
                        holder.tv_jiangli_neirong = (TextView) convertView.findViewById(R.id.tv_jiangli_neirong);
                        holder.tv_miaoshu_gouwu = (TextView) convertView.findViewById(R.id.tv_miaoshu_gouwu);
                        holder.tv_jiahao = (TextView) convertView.findViewById(R.id.tv_jiahao);

                        holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                        holder.iv_complete = (ImageView) convertView.findViewById(R.id.iv_complete);
                        holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                        holder.sign_rl = (RelativeLayout) convertView.findViewById(R.id.sign_rl);
                        convertView.setTag(holder);

                        holder.iv_icon.setImageResource(R.drawable.icon_miandan);


                        holder.vvv.setVisibility(View.VISIBLE);

                        holder.tv_miaoshu_gouwu.setVisibility(View.VISIBLE);

                        holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                        holder.tv_jiahao.setVisibility(View.GONE);
                        holder.tv_jiangli_count.setTextColor(Color.parseColor("#FF3F88"));

                        // holder.tv_miaoshu.setTextColor(Color.parseColor("#FFFFFF"));
                        // holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg_jingxi);
//                            convertView.setBackgroundColor(Color.parseColor("#00000000"));

                        // 疯狂星期一的跟其他不同，单独填充
                        // 描述内容要根据抽奖剩余次数判断
                        String miaoshu = "";
                        miaoshu = "疯狂新衣节";


                        final Holder finalHolder2 = holder;

                        finalHolder2.ll_jiangli.setVisibility(View.GONE);


                        finalHolder2.iv_complete.setVisibility(View.GONE);
                        finalHolder2.iv_icon.setImageResource(R.drawable.icon_monday);


                        finalHolder2.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));
                        finalHolder2.tv_miaoshu.setText(Html.fromHtml("<b>" + miaoshu + "</b>"));


                        finalHolder2.tv_jiangli_count.setVisibility(View.VISIBLE);
                        finalHolder2.tv_jiangli_neirong.setVisibility(View.VISIBLE);


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_SIGN);
                                    ((Activity) mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            try {
                                                HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_SIGN);
                                                if (m != null && m.size() > 0) {
                                                    //处理数据

                                                    meiyiJieBot = m.get("t1") + "";


                                                    finalHolder2.tv_jiangli_neirong.setText(meiyiJieBot);
                                                    if (lotterynumber > 0) {
                                                        finalHolder2.tv_jiangli_count.setText("剩余" + lotterynumber + "次");
                                                        finalHolder2.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                                                        finalHolder2.tv_jiangli_count.setTextSize(15.4f);

                                                    } else {
                                                        finalHolder2.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                                                        finalHolder2.tv_jiangli_cunt_danwei.setText("中奖");
                                                        finalHolder2.tv_jiangli_count.setText("100%");
                                                        finalHolder2.tv_jiangli_count.setTextSize(17.6f);
                                                    }
                                                    finalHolder2.ll_jiangli.setVisibility(View.VISIBLE);


                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();


                    }
                } else {
                    if (id.equals(taskID)) { // 如果对上，就能找到该条目对应的信息 ，填充即可

                        holder = new Holder();
                        convertView = mInflater.inflate(R.layout.item_signtask_new, null);

                        holder.tv_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu);
                        holder.tv_kaituan = (TextView) convertView.findViewById(R.id.tv_kaituan);


                        holder.tv_jiangli_count = (TextView) convertView.findViewById(R.id.tv_jiangli_count);
                        holder.tv_jiangli_cunt_danwei = (TextView) convertView
                                .findViewById(R.id.tv_jiangli_cunt_danwei);
                        holder.tv_miaoshu_miaoshu = (TextView) convertView.findViewById(R.id.tv_miaoshu_miaoshu);
                        holder.tv_jiangli_neirong = (TextView) convertView.findViewById(R.id.tv_jiangli_neirong);
                        holder.tv_miaoshu_gouwu = (TextView) convertView.findViewById(R.id.tv_miaoshu_gouwu);
                        holder.tv_jiahao = (TextView) convertView.findViewById(R.id.tv_jiahao);
                        holder.ll_jiangli = (LinearLayout) convertView.findViewById(R.id.ll_jiangli);

                        holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                        holder.iv_complete = (ImageView) convertView.findViewById(R.id.iv_complete);
                        holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
                        holder.vvv = (View) convertView.findViewById(R.id.vvv);
                        holder.sign_rl = (RelativeLayout) convertView.findViewById(R.id.sign_rl);
                        convertView.setTag(holder);

                        // 任务描述
                        String t_name = taskList.get(i).get("t_name") + "";
                        if (t_name.equals("3元包邮")) {
                            t_name = "3元夺宝";
                        }

                        if (t_name.equals("5元包邮")) {
                            t_name = "5元夺宝";
                        }

                        // 填充任务描述任务和图标
                        int doType = 0;
                        if (initList.size() > 0) {
                            doType = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                                    .get("task_type").toString());
                        }

                        String status = "-1";
                        status = initList.get(position).get("status") + "";
                        boolean s_complete = status.equals("0");
                        boolean signCoplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // 任务时当时的完成状态
                        // 1代表已完成

                        /**
                         * doType： 0开店-1邀请好友-2夺宝-3加X件商品到购物车-4浏览普通商品-5浏览商品集合
                         * 6购买X件商品-7分享普通商品-8分享搭配购 701分享普通商品--得到余额翻倍
                         * 702分享普通商品--积分升级 703分享普通商品--优惠券升级
                         * 801分享搭配商品--得到余额翻倍 802分享搭配商品--积分升级
                         * 803搭配搭配商品--优惠券升级
                         *
                         */

                        String value = initList.get(position) // 任务数量（去做什么）
                                .get("value") + "";

                        if (doType == 1) {
                            holder.tv_miaoshu_miaoshu.setVisibility(View.VISIBLE);
                        } else {
                            holder.tv_miaoshu_miaoshu.setVisibility(View.GONE);
                        }


                        if (YJApplication.instance.isLoginSucess() || YJApplication.isLogined) {
                            // 已完成图标的显示
                            if (tast == 1 || tast == 2 || tast == 4 || tast == 5 || tast == 6 || tast == 7) { // 如果是必做、额外任务就、惊喜提现任务、夺宝任务（超级惊喜任务）---可能有完成

                                if (doType == 1 || doType == 16) { // 邀请好友已完成不改颜色 还有邀请集赞任务
                                    holder.tv_jiangli_count.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_miaoshu.setTextColor(Color.parseColor("#7d7d7d"));
                                    // 设置完成状态
                                    if (signCoplete) { // 是已完成的
                                        holder.iv_complete.setVisibility(View.VISIBLE);
                                        holder.iv_complete.setImageResource(R.drawable.icon_jxyq);
                                    } else {
                                        holder.iv_complete.setVisibility(View.GONE);
                                    }


                                } else {
                                    if (signCoplete) { // 是已完成的
                                        holder.iv_complete.setVisibility(View.VISIBLE);
                                        holder.iv_complete.setImageResource(R.drawable.icon_completed);
                                        holder.tv_jiangli_count.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiangli_count.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_jiahao.setTextColor(Color.parseColor("#d5d5d5"));
                                        holder.tv_kaituan.setTextColor(Color.parseColor("#d5d5d5"));

                                        if (doType == 18) {
                                            if (orderStatus == 1) {
                                                holder.iv_complete.setImageResource(R.drawable.icon_completed);
                                            } else {
                                                holder.iv_complete.setImageResource(R.drawable.yikaituan);
                                            }

                                        }

                                        if (doType == 23) {//千元红包雨完成状态
                                            holder.iv_complete.setImageResource(R.drawable.icon_over);
                                        }

                                    } else { // 未完成的

                                        holder.iv_complete.setVisibility(View.GONE);


                                        holder.tv_jiangli_count.setTextColor(Color.parseColor("#ff3f8b"));
                                        holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#ff3f8b"));
                                        holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#ff3f8b"));
                                        holder.tv_miaoshu.setTextColor(Color.parseColor("#7d7d7d"));
                                        holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));

                                        holder.tv_kaituan.setTextColor(Color.parseColor("#ff3f8b"));

                                        //
                                        // }

                                    }
                                }

                            }

                        } else { // 如没有登录就不存在完成的

                            holder.iv_complete.setVisibility(View.GONE);

                            holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_jiangli_count.setTextColor(Color.parseColor("#ff3f8b"));

                            holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_miaoshu.setTextColor(Color.parseColor("#7d7d7d"));
//                                holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg);
                            holder.tv_kaituan.setTextColor(Color.parseColor("#ff3f8b"));
//                                convertView.setBackgroundColor(Color.parseColor("#00000000"));
                            //
                            // }

                        }

                        //夺宝任务不再显示已完成，颜色也不改
                        if (doType == 2) {
                            holder.iv_complete.setVisibility(View.GONE);
                            holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_jiangli_count.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#ff3f8b"));
                            holder.tv_miaoshu.setTextColor(Color.parseColor("#7d7d7d"));
//                                holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg);
//                                convertView.setBackgroundResource(R.drawable.sigin_list_bg);

//                                convertView.setBackgroundColor(Color.parseColor("#00000000"));
                        }

                        // 分享的图标统一设置
                        if (doType == 7 || doType == 8 || doType == 13 || doType == 14 || doType == 20) {

                            holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                        }
                        if (tast == 3) {
                            holder.iv_complete.setVisibility(View.GONE);
                        }
                        // 描述(填充任务名称)
                        initTaskName(position, holder, doType, signCoplete, value);
                        // 填充奖励
                        initTaskJiangLi(position, holder, i, doType);

                    }
                }


            }


        } else {
            holder = (Holder) convertView.getTag();
        }

        // 任务点击
        if (convertView == null) {

            return mInflater.inflate(R.layout.item_signtask_error, null);

        }
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //处理任务点击
                initTaskClick(position);
            }

        });

        return convertView;
    }

    private void initTaskClick(int position) {
        // 未登录的话跳转到登录界面
        if (!YJApplication.instance.isLoginSucess()) {

            if (LoginActivity.instances != null) {
                LoginActivity.instances.finish();
            }

            SharedPreferencesUtil.saveBooleanData(mContext, YConstance.Pref.ISKAIDIAN_JUMP_LOGIN, true);

            Intent intent = new Intent(mContext, LoginActivity.class);
            // intent.putExtra("isSign", true);
            intent.putExtra("login_register", "login");
            mContext.startActivity(intent);
            ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_match);
            return;
        }

        //审核员只能做新手第一轮的任务
        if (YCache.getCacheUser(mContext).getReviewers() == 1 && !SignFragment.mSignCountData.getCurrent_date().equals("newbie01")) {

            Intent intent2 = new Intent(mContext, MainMenuActivity.class);
            intent2.putExtra("toYf", "toYf");
            intent2.putExtra("isFromSHYsign", true);

            mContext.startActivity(intent2);
            if (null != CommonActivity.instance) {
                CommonActivity.instance.finish();
            }

            return;

        }


        //会员不能做新手任务(从第二批开始)
        boolean mIsVip;
        mIsVip = CommonUtils.isVip(SignFragment.mSignCountData.getIsVip(), SignFragment.mSignCountData.getMaxType());
        if (mIsVip && (SignFragment.mSignCountData.getCurrent_date() + "").equals("newbie02")) {
            ToastUtil.showMyToast(mContext, "成为会员后次日开始，每日可领5个任务，请明日9点后来。", 4000);
            return;
        }
        //首日完成首次任务后，非会员点二次刷新的任意任务，直接跳转钻石会员页(没有做够1个任务除外)
        if (!mIsVip && (SignFragment.mSignCountData.getCurrent_date() + "").equals("newbie02") && SignFragment.daytaskListYet.size() > 0) {
            mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                    .putExtra("guide_vipType", 4)
                    .putExtra("isFromSign2Round", 1)
            );
            return;
        }


        if (SignFragment.whetherTask != 1) {//提示开会员
            DialogUtils.signGuideVip(mContext);
            return;
        }

        if (tast == 3) {// 明日预告没有点击事件
            return;
        }

        if (initList.size() == 0 || position > initList.size() - 1) {
            return;
        }

        //必须在H5端完成的任务不能点击----相关任务9257
        int h5 = 1;
        try {
            h5 = Integer.parseInt(initList.get(position).get("task_h5").toString());
        } catch (Exception e5) {
            e5.printStackTrace();
        }

        if (h5 >= 4) {
            ToastUtil.showShortText(mContext, "此任务只能在H5端完成哦~");
            return;
        }


        try {
            doType = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                    .get("task_type").toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        String status = "-1";
        status = initList.get(position).get("status") + "";
        boolean s_complete = status.equals("0");
        boolean isComplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // 任务时当时的完成状态
//
//                                        boolean isComplete = (initList.get(position).get("signStatus") + "")
//                                                .equals("1"); // 是否已完成
        if (isComplete) {
            isSignComplete = true; // 已完成
            //完成后还可以点击进入的任务
            if (doType != 2 && doType != 4 && doType != 5 && doType != 1 && doType != 9
                    && doType != 11 && doType != 16 && doType != 17 && doType != 18 && doType != 25) {
                return;
            }

        } else {
            isSignComplete = false; // 未完成
        }

        doValue = initList.get(position).get("value") + ""; // 做什么的value
        doNeedCount = initList.get(position).get("status") + ""; // 做什么的value

        doClass = Integer.parseInt(initList.get(position) // 任务类型（区分用的）
                .get("task_class").toString());


        doIconId = initList.get(position).get("icon") + "";


        try {
            doNum = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                    .get("num").toString()); // 奖励分几次发
        } catch (Exception eee) {
            eee.printStackTrace();
        }

        if (null == initList.get(position).get("num")
                || initList.get(position).get("num").equals("")) {
            if (!isComplete) {
                ToastUtil.showShortText(mContext, "此任务不存在,或配置错误");
                return;
            }

        }

        signIndex = initList.get(position).get("index") + "";

        // 拿到点击的条目的奖励
        for (int j = 0; j < taskList.size(); j++) {

            String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
            String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

            if (id.equals(taskID)) {
                jiangliValue = taskList.get(j).get("value") + "";
                jiangliID = Integer.parseInt(taskList.get(j).get("type_id").toString());
            }

        }
        //需要去小程序做的任务
        if (doType == 33 || doType == 35 ||
                doType == 36 || doType == 37
                || doType == 39 || doType == 41
                || doType == 44 || doType == 45) {

            if (doType == 36 && SignFragment.mSignCountData.getCurrent_date().equals("newbie01")
                    && YCache.getCacheUser(mContext).getReviewers() == 1) {
                mContext.startActivity(new Intent(mContext, ForceLookActivity.class));
                ((Activity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                doSignGo = true;

                return;
            }


//                    LayoutInflater mInflater = LayoutInflater.from(mContext);
//                    final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
//                    View view = mInflater.inflate(R.layout.dialog_guide_sign_xcx, null);
//
//
//                    final TextView btn_ok = view.findViewById(R.id.btn_ok);
//                    btn_ok.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {


            String appId = WxPayUtil.APP_ID; // 填应用AppId
            if (StringUtils.isEmpty(appId)) {
                appId = YUrl.APP_ID;
            }
            IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
            req.path = "pages/sign/sign?fromApp=1";                  ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
            // 可选打开 开发版，体验版和正式版
            if (YUrl.wxMiniDedug) {
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
            } else {
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            }
            api.sendReq(req);

            SharedPreferencesUtil.saveBooleanData(mContext, YConstance.Pref.JUMP_XCX_SIGN, true);

//                            dialog.dismiss();
//
//                        }
//                    });
//
//                    view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//                            btn_ok.performClick();
//                        }
//                    });
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT));
//
//                    ToastUtil.showDialog(dialog);


            return;


        }

        // 分享
        if (doType == 7 || doType == 8 || doType == 701 || doType == 702
                || doType == 703 || doType == 801 || doType == 802 || doType == 803 || doType == 32) {


            if (doType == 7 || doType == 32) {//分享X件商品的单独处理 和分享赚钱任务页
                new SignShareUtil().share(mContext, jiangliID);
                return;
            }

            if (!SignShareShopDialog.isShow) {

                try {
                    gotoShareValue = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                SignShareShopDialog signshareshopdialog =
                        new SignShareShopDialog(mActivity, mContext, R.style.DialogStyle1,
                                jiangliID);
                signshareshopdialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                signshareshopdialog.show();

            }

        }


        Intent intent = null;
        switch (doType) {

            case 0: // 开店 --没有开店了

                Intent intentKaidian = new Intent(mContext, MineLikeActivity.class);
                intentKaidian.putExtra("isSign", true);
                ((MainMenuActivity) mContext).startActivityForResult(intentKaidian, 13334);

                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 1:// 邀请好友


                for (int i = 0; i < taskList.size(); i++) { // 遍历任务类型

                    // 去做对比

                    String id = initList.get(position).get("t_id").toString();
                    String taskID = (String) taskList.get(i).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) { // 如果对上，就能找到该条目对应的信息

                    }

                }

                Intent intentYao = new Intent(mContext, YaoQingFrendsActivity.class);
                intentYao.putExtra("jumpFrom", "YaoQingHaoyou");

                mContext.startActivity(intentYao);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 2: // 夺宝

//                                                 indexMap.put(YConstance.DUOBAO_INDEX,
//                                                 signIndex);
                TongJiUtils.yunYunTongJi("duobao", 1001, 10, mContext);

                Intent intentDuo = new
                        Intent(mContext,
                        IndianaListActivity.class);
//                                                intentDuo.putExtra("SignShopDetail",
//                                                        "SignShopDetail");
//                                                intentDuo.putExtra("valueDuo",
//                                                        doValue);
                mContext.startActivity(intentDuo);
                ((FragmentActivity)
                        mContext).overridePendingTransition(
                        R.anim.slide_left_in,
                        R.anim.slide_match);

                break;
            case 3: // 加X件商品到购物车
                // indexMap.put(YConstance.ADD_TO_SHOPCART,
                // signIndex);
                SharedPreferencesUtil.saveStringData(mContext, YConstance.ADD_TO_SHOPCART,
                        signIndex);

                classMap.put(YConstance.ADD_TO_SHOPCART, doClass);

                // 加购物车的单独赋值

                doValueShopCart = initList.get(position).get("value").toString(); // 做什么的value

                try {
                    doNumShopCart = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        jiangliValueShopCart = taskList.get(j).get("value") + "";
                        jiangliIDShopCart = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }


                String leiileii = "";


                try {
                    doNumShopLiulan = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发

                    leiileii = doValue.split(",")[0]; // 类型

                    doValueLiulan = initList.get(position).get("value").toString(); // 做什么的value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        jiangliValueLiulan = taskList.get(j).get("value") + "";
                        jiangliIDLiulan = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }


                String gotoAdd = "";
                try {
                    gotoAdd = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                addShopCartIconID.put(YConstance.SCAN_SHOP_TIME, doIconId);

                if (gotoAdd.equals("collection=collocation_shop")
                        || gotoAdd.equals("type1=0")) {// 搭配

                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");
                        intent.putExtra("doIconId", doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {
//
//                                                    new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                            "liulandapeitishi", SignFragment.this, "").show();
//                                                    ToastUtil.showDialog(new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                            "liulandapeitishi", SignFragment.this, ""));

                        NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "addshopcarttishi_dapei", SignFragment.signFragment, "");

                        addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        addshopcartDiaolg.show();

                    }
                } else if (gotoAdd.equals("collection=csss_shop")) {// 浏览专题
                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("doIconId", doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);

                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {


                                NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "addshopcarttishi_zhuanti", SignFragment.signFragment, "");

                                addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                                addshopcartDiaolg.show();


                            }

                        }

                    }
                } else if (gotoAdd.equals("collection=shopping_page")) {// 购物页面
                    if (isComplete) {

                        // 跳至购物
//                                                    Intent intent2 = new Intent((Activity) context,
//                                                            MainMenuActivity.class);
//                                                    intent2.putExtra("toShop", "toShop");
//                                                    context.startActivity(intent2);

//                                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
//                                                            R.anim.slide_match);

                        mContext.startActivity(new Intent(mContext, ShopPageActivity.class));
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
//                                                            new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                    "liulangouwuyemian", SignFragment.this,
//                                                                    taskiconList.get(j).get("app_name") + "").show();


//                                                            ToastUtil.showDialog(
//                                                                    new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                            "liulangouwuyemian", SignFragment.this,
//                                                                            taskiconList.get(j).get("app_name") + "")
//
//
//                                                            );


                                NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "addshopcarttishi_gowuye", SignFragment.signFragment, "");

                                addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                                addshopcartDiaolg.show();


                            }

                        }

                    }
                } else if (gotoAdd.equals("collection=shop_activity")) { // 活动商品

                    if (isComplete) {

                        intent = new Intent(mContext, SignActiveShopActivity.class);

                        intent.putExtra("doIconId", doIconId);
                        intent.putExtra("isCrazy", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);


                    } else {

//                                                    NewSignCommonDiaolg huodongDialog =
//
//                                                            new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                    "liulanhuodongjishitishi", SignFragment.this, "");
//                                                    huodongDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                                    huodongDialog.show();


                        NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "addshopcarttishi_huodong", SignFragment.signFragment, "", doIconId);

                        addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        addshopcartDiaolg.show();

                    }
                } else {
                    if (isComplete) {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
                                intent = new Intent(mContext, ForceLookMadActivity.class);
                                intent.putExtra("isFilterConditionActivity", true);
                                intent.putExtra("title", "热卖");
                                intent.putExtra("pinJievalue", gotoAdd);

                                intent.putExtra("doIconId", doIconId);
                                intent.putExtra("isCrazy", true);


                                mContext.startActivity(intent);
                                ((Activity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);

                            }

                        }

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {

//
//                                                            NewSignCommonDiaolg liulanDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                    "liulanfenzhongtishi", SignFragment.this,
//                                                                    taskiconList.get(j).get("app_name") + "");
//
//                                                            liulanDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
//
//                                                            liulanDiaolg.show();


                                NewSignCommonDiaolg addshopcartDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "addshopcarttishi_qitajihe", SignFragment.signFragment, "", doIconId);

                                addshopcartDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);
                                addshopcartDiaolg.show();


                            }

                        }

                    }

                }


                break;
            case 4: // 浏览X件普通商品 //要求的是-----个数

                String lei = "";

                lei = null; // 类型
                try {
                    lei = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (lei.equals("collection=collocation_shop")) {// 搭配
                    intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, doValue);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (lei.equals("collection=shop_activity")) { // 活动商品

                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);

                    mContext.startActivity(intent);


                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


                } else if (lei.equals("collection=browse_shop")) { // 热门推首
                    // ----完全用以前的强制浏览
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("pinJievalue", lei);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // 其他的用以前的强制浏览
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, doValue);
                    for (int j = 0; j < taskiconList.size(); j++) {
                        if ((taskiconList.get(j).get("id") + "")
                                .equals(initList.get(position).get("icon") + "")) {
                            intent = new Intent(mContext, ForceLookActivity.class);
                            intent.putExtra("isFilterConditionActivity", true);
                            intent.putExtra("title",
                                    taskiconList.get(j).get("app_name") + "");
                            intent.putExtra("pinJievalue", lei);


                            intent.putExtra("doIconId", doIconId);
                            intent.putExtra("isSignLiulan", true);


                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);

                        }

                    }

                }

                break;


            case 19://浏览赢提现
                String leiTX = "";

                try {
                    leiTX = doValue.split(",")[0]; // 类型
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                if (leiTX.equals("share=myq")) {//密友圈


                    try {
                        doValueCD = initList.get(position).get("value").toString().split(",")[1]; // 做什么的value
                        doNumCD = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                                .get("num").toString()); // 奖励分几次发
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    // 拿到点击的条目的奖励
                    for (int j = 0; j < taskList.size(); j++) {

                        String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                        String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                        if (id.equals(taskID)) {
                            jiangliValueCD = taskList.get(j).get("value") + "";
                            jiangliIDCD = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }


                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "pubuliu_sign_tixian");
                    mContext.startActivity(new Intent(mContext, CommonActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                } else if (leiTX.equals("collection=collocation_shop")) {// 搭配
//                                                intent = new Intent(context, ForceLookMatchActivity.class);
//                                                intent.putExtra("type", "1");
//                                                intent.putExtra("doIconId", doIconId);
//                                                intent.putExtra("isSignLiulan", true);
//                                                startActivity(intent);
//                                                SharedPreferencesUtil.saveStringData(mContext, Pref.SINGVALUE, doValue);
//                                                ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
//                                                        R.anim.slide_match);


                    intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=shop_activity")) { // 活动商品
                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


//                                                intent = new Intent(context, ForceLookActivity.class);
//                                                intent.putExtra("isFilterConditionActivity", true);
//                                                intent.putExtra("title", "热卖");
//                                                intent.putExtra("pinJievalue", leiTX);
//
//                                                intent.putExtra("doIconId", doIconId);
//                                                intent.putExtra("isSignLiulan", true);
//
//
//                                                context.startActivity(intent);
//                                                ((Activity) context).overridePendingTransition(
//                                                        R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=browse_shop")) { // 热门推首
                    // ----完全用以前的强制浏览
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // 其他的用以前的强制浏览
//                                                    SharedPreferencesUtil.saveStringData(mContext, Pref.SINGVALUE, doValue);
//                                                    for (int j = 0; j < taskiconList.size(); j++) {
//                                                        if ((taskiconList.get(j).get("id") + "")
//                                                                .equals(initList.get(position).get("icon") + "")) {
//                                                            Intent intent = new Intent(context, ForceLookActivity.class);
//                                                            intent.putExtra("isFilterConditionActivity", true);
//                                                            intent.putExtra("title",
//                                                                    taskiconList.get(j).get("app_name") + "");
//                                                            intent.putExtra("pinJievalue", leiTX);
//                                                            context.startActivity(intent);
//                                                            ((Activity) context).overridePendingTransition(
//                                                                    R.anim.slide_left_in, R.anim.slide_match);
//
//                                                        }
//
//                                                    }


                    intent = new Intent(mContext, ForceLookActivity.class);
                    intent.putExtra("isFilterConditionActivity", true);
                    intent.putExtra("title", "热卖");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);

                }

                break;


            case 20://分享赢提现额度

                TongJiUtils.yunYunTongJi("fxytx", 1101, 11, mContext);

                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

//                                                getIndianaRuleContent();


                //                                                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

                break;


            case 21://一元夺宝（提现额度夺宝）


                Intent intentDuoTX = new
                        Intent(mContext,
                        IndianaListActivity.class);
//                                                intentDuo.putExtra("SignShopDetail",
//                                                        "SignShopDetail");
//                                                intentDuo.putExtra("valueDuo",
//                                                        doValue);
                mContext.startActivity(intentDuoTX);
                ((FragmentActivity)
                        mContext).overridePendingTransition(
                        R.anim.slide_left_in,
                        R.anim.slide_match);


                break;

            case 5:// 浏览商品集合和活动商品 X分钟
                // 要求的------是分钟
                String leii = "";


                try {
                    doNumShopLiulan = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发

                    leii = doValue.split(",")[0]; // 类型

                    doValueLiulan = initList.get(position).get("value").toString(); // 做什么的value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        jiangliValueLiulan = taskList.get(j).get("value") + "";
                        jiangliIDLiulan = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                String fenzhongDoValue = initList.get(position).get("value").toString(); // 做什么的value
                // (分钟数单独存--需要重新赋值)

                String gotoLiuLan = "";
                try {
                    gotoLiuLan = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // 通过当前的index判断点击是是否是刚才点击的任务
                boolean sameIndex = signIndex
                        .equals(indexMap.get(YConstance.SCAN_SHOP_TIME));

                // 已经开始过分钟数的任务，点击的是刚才的任务（同一个）
                if (minuteMap.size() != 0 || sameIndex) {

                }

                /**
                 * 已经开始过分钟数的任务，点击的不是同一个任务 ------
                 * -----提示还有分钟数的任务没完成，并显示剩余时间，
                 * 且有上个分钟数任务的跳转
                 */

                if (minuteMap.size() != 0 && !sameIndex) {
                    new DialogSignFenzhongTishi(mContext, R.style.DialogQuheijiao).show();
                    return;

                }

                indexMap.put(YConstance.SCAN_SHOP_TIME, signIndex);
                classMap.put(YConstance.SCAN_SHOP_TIME, doClass);
                jiangliIDmap.put(YConstance.SCAN_SHOP_TIME, jiangliIDLiulan);
                jiangliValueMap.put(YConstance.SCAN_SHOP_TIME, jiangliValueLiulan);


                try {
                    fenzhongDoValue = fenzhongDoValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                fenzhongDoValueMap.put(YConstance.SCAN_SHOP_TIME, fenzhongDoValue);


                fenzhongIconID.put(YConstance.SCAN_SHOP_TIME, doIconId);

                if (gotoLiuLan.equals("collection=collocation_shop")
                        || gotoLiuLan.equals("type1=0")) {// 搭配

                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");
                        intent.putExtra("doIconId", doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {
//
//                                                    new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                            "liulandapeitishi", SignFragment.this, "").show();
                        ToastUtil.showDialog(new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "liulandapeitishi", SignFragment.signFragment, ""));

                    }
                } else if (gotoLiuLan.equals("collection=csss_shop")) {// 浏览专题
                    if (isComplete) {
                        intent = new Intent(mContext, ForceLookMatchActivity.class);
                        intent.putExtra("type", "2");
                        intent.putExtra("doIconId", doIconId);
                        intent.putExtra("isSignLiulan", true);
                        mContext.startActivity(intent);

                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
//                                                            new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                    "liulanzhuantitishi", SignFragment.this,
//                                                                    taskiconList.get(j).get("app_name") + "").show();


                                ToastUtil.showDialog(

                                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                                "liulanzhuantitishi", SignFragment.signFragment,
                                                taskiconList.get(j).get("app_name") + "")


                                );


                            }

                        }

                    }
                } else if (gotoLiuLan.equals("collection=shopping_page")) {// 购物页面
                    if (isComplete) {

                        // 跳至购物
//                                                    Intent intent2 = new Intent((Activity) context,
//                                                            MainMenuActivity.class);
//                                                    intent2.putExtra("toShop", "toShop");
//                                                    context.startActivity(intent2);

//                                                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in,
//                                                            R.anim.slide_match);

                        mContext.startActivity(new Intent(mContext, ShopPageActivity.class));
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
//                                                            new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
//                                                                    "liulangouwuyemian", SignFragment.this,
//                                                                    taskiconList.get(j).get("app_name") + "").show();


                                ToastUtil.showDialog(
                                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                                "liulangouwuyemian", SignFragment.signFragment,
                                                taskiconList.get(j).get("app_name") + "")


                                );


                            }

                        }

                    }
                } else if (gotoLiuLan.equals("share=myq")) {// 社区首页
                    if (isComplete) {
                        intent = new Intent(mContext, ShopPageActivity.class);
                        intent.putExtra("isMiyouquan", true);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                    } else {
                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {

                                ToastUtil.showDialog(
                                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                                "shequshouye", SignFragment.signFragment,
                                                taskiconList.get(j).get("app_name") + "")


                                );


                            }

                        }

                    }
                } else if (gotoLiuLan.equals("collection=shop_activity")) { // 活动商品

                    if (isComplete) {

                        intent = new Intent(mContext, SignActiveShopActivity.class);
                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("doIconId", doIconId);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);
                    } else {

                        NewSignCommonDiaolg huodongDialog =

                                new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulanhuodongjishitishi", SignFragment.signFragment, "");
                        huodongDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                        huodongDialog.show();

                    }
                } else {
                    if (isComplete) {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {
                                intent = new Intent(mContext,
                                        ForceLookActivity.class);


                                intent.putExtra("doIconId", doIconId);
                                intent.putExtra("isSignLiulan", true);

                                intent.putExtra("isFilterConditionActivity", true);
                                intent.putExtra("title",
                                        taskiconList.get(j).get("app_name") + "");
                                intent.putExtra("pinJievalue", leii);
                                mContext.startActivity(intent);
                                ((Activity) mContext).overridePendingTransition(
                                        R.anim.slide_left_in, R.anim.slide_match);

                            }

                        }

                    } else {

                        for (int j = 0; j < taskiconList.size(); j++) {
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals(initList.get(position).get("icon") + "")) {


                                NewSignCommonDiaolg liulanDiaolg = new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                        "liulanfenzhongtishi", SignFragment.signFragment,
                                        taskiconList.get(j).get("app_name") + "");

                                liulanDiaolg.getWindow().setWindowAnimations(R.style.common_dialog_style);

                                liulanDiaolg.show();


                            }

                        }

                    }

                }

                break;
            case 6: // 购买X件商品 -------

                String name = "";

                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        name = taskiconList.get(j).get("app_name") + "";
                    }

                }


                NewSignCommonDiaolg goumaiDiaog =
                        new NewSignCommonDiaolg(mContext, R.style.DialogQuheijiao,
                                "goumaishuoming", SignFragment.signFragment, doValue + "-" + name);

                goumaiDiaog.getWindow().setWindowAnimations(R.style.common_dialog_style);

                goumaiDiaog.show();


                break;
            case 7: // 分享X件普通商品
                break;
            case 8: // 分享X套搭配购
                break;
            case 9: // 免单

                intent = new Intent(mContext, BuyFreeActivity.class);

                int valuevv = 1;

                try {
                    valuevv = Integer.parseInt(doValue);
                } catch (Exception e2) {
                }

                if (valuevv == 1) { // 100以上
                    intent.putExtra("cashBack", 1);
                }

                if (valuevv == 2) { // 50以上
                    intent.putExtra("cashBack", 0);
                }

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            // 701-803都是分享1件 奖励写在描述上
            // 701-703是分享普通商品
            // 801-803是分享搭配购
            case 701: // 分享普通商品 --- 余额翻倍
                break;
            case 702:// 分享普通商品 ----积分升级
                break;

            case 703:// 分享普通商品-----优惠券升级
                break;

            case 801:// 分享搭配购 ---余额翻倍
                break;

            case 802:// 分享搭配购 ----积分升级
                break;

            case 803:// 分享搭配购 ---优惠券升级
                break;

            case 999:// 疯狂星期一
                if (lotterynumber > 0) {
                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {
                    showFreeFormDialog(-1);
                }
                break;
            case 10:// 设置喜好

                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        String jiangliValue = taskList.get(j).get("value") + "";
                        int jiangliID = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                        // 保存在本地
                        SharedPreferencesUtil.saveStringData(mContext,
                                "jianglivaluesetlike", jiangliValue);
                        SharedPreferencesUtil.saveStringData(mContext, "jiangliidsetlike",
                                jiangliID + "");

                    }

                }

                Intent intentLike = new Intent(mContext, MyLikeActivity.class);
                intentLike.putExtra("isSignJump", true);
                mContext.startActivity(intentLike);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;

            case 11: // 去精选推荐挑美衣
                if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                    ToastUtil.showShortText(mContext, "请先设置喜好喔~");


                    intent = new Intent(mContext, MyLikeActivity.class);
                    intent.putExtra("isJingxuanJump", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                    return;
                }

                if (SharedPreferencesUtil.getBooleanData(mContext,
                        YConstance.Pref.JINGXUAN_SCAN_FINISH, false)) {
                    ToastUtil.showShortText(mContext, "已经完成今天的精选推荐了哦~");
                } else {

                    doClass_jx = Integer.parseInt(initList.get(position) // 任务类型（区分用的）
                            .get("task_class").toString());
                    // 拿到点击的条目的奖励---保存去精选推荐的是奖励
                    for (int j = 0; j < taskList.size(); j++) {
                        String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                        String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                        if (id.equals(taskID)) {
                            jiangliValueJX = taskList.get(j).get("value") + "";
                            jiangliIDJX = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }

                    if (!isSignComplete) {
                        // 保存index
                        SharedPreferencesUtil.saveStringData(mContext,
                                YConstance.LIULANJINGXUANTUJIANINDEX, signIndex);
                        // 是否没有调过签到的接口
                        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN",
                                true);

                        // 保存是从签到跳入精选推荐的标志
                        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGN", true);
                    }

                    // 进入首页并打开精选推荐
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", true);
                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
                break;
            case 12:

                // 浏览穿搭的index
                SharedPreferencesUtil.saveStringData(mContext, YConstance.LIULANCHUANDAINDEX,
                        signIndex);

                // 浏览穿搭的单独赋值

                try {
                    doValueCD = initList.get(position).get("value").toString(); // 做什么的value
                    doNumCD = Integer.parseInt(initList.get(position) // 任务类型（去做什么）
                            .get("num").toString()); // 奖励分几次发
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                // 拿到点击的条目的奖励
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // 需要做的任务的ID----tasklist中的t_id做对比，
                    String taskID = (String) taskList.get(j).get("t_id"); // 任务类型ID

                    if (id.equals(taskID)) {
                        jiangliValueCD = taskList.get(j).get("value") + "";
                        jiangliIDCD = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom",
                        "pubuliu_sign");
                mContext.startActivity(new Intent(mContext, CommonActivity.class));

                break;
            case 13:// 分享XX件品质美衣
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareShop");
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 14:// 分享XX条热门穿搭话题
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareTieZi");

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 15:// 继续点赞

                if (isGratis.equals("false")) {//需要花费5衣豆继续点赞
                    JiZanCommonDialog dialog = new JiZanCommonDialog(mActivity, R.style.DialogStyle1, "jixujizandianji");
                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    dialog.show();


                } else {//有免费点赞的机会，直接点赞即可
//                            dianZan(true, false);
                }

                break;

            case 16://邀请好友集赞
                TongJiUtils.yunYunTongJi("point", 907, 9, mContext);
                intent = new Intent(mContext, PointLikeRankingActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 17: //参团jkm

                tuanClass = 2;

                if (isSignComplete) {
                    //已参与，---点击进入拼团详情

                    intent = new Intent(mContext, GroupsDetailsActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {//未参与
                    if (offered == 1) { //当前团已结束---跳到拼团详情
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("isTuanEnd", true);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//未结束-----跳到拼团商品选择，--正常参与


                        if (offered == 2) {//只有offered = 2：可参与的时候，跳进拼团商品列表，其他的都跳至拼团详情
                            intent = new Intent(mContext, SignGroupShopActivity.class);
                            mContext.startActivity(intent);
                            ((FragmentActivity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);
                        } else {
                            intent = new Intent(mContext, GroupsDetailsActivity.class);
                            mContext.startActivity(intent);
                            ((FragmentActivity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);

                        }


                    }

                }

                break;
            case 18://开团
                tuanClass = 1;

                if (orderCount > 0) {//开过团
                    if (orderStatus == 1) { //已完成
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//已开团但未完成
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("completeStatus", 3);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    }

                } else { //未开过
                    intent = new Intent(mContext, SignGroupShopActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                }


                break;

            case 22://拼团夺宝
                intent = new Intent(mContext, IndianaListActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;

            case 23://千元红包雨
//                                            DialogUtils.redPacketDownDialog(mContext);

                DialogUtils.redPacketDownSignTCDialog(mContext);


                break;


            case 24://购买X件商品//每月惊喜任务--------------//


                DialogUtils.meuyueJingxiDialog(mContext, doValue);


                break;


            case 25://  新分享赢提现

                ToastUtil.showDialog(new NewShareGetTXDialog(mActivity, mContext, R.style.DialogStyle1,
                        jiangliID));

                break;
            case 26://  去抽奖
                PublicUtil.getBalanceNum(mContext, null, true);
                break;

            case 27: //余额抽提现


                intent = new Intent(mContext, WithdrawalLimitActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 28: //超级0元购
                ToastUtil.showDialog(new LingYUANGOUTishiRedDialog(mContext, R.style.DialogStyle1, doValue.split(",")[0]));
                break;
            case 30: //超级分享日
                intent = new Intent(mContext, FriendCommissionActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 31: //好友赢提现
                intent = new Intent(mContext, FriendCommissionActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;

            case 32:  //分享赚钱任务页

//                                        intent = new Intent(mContext, FriendCommissionActivity.class);
//                                        mContext.startActivity(intent);
//                                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 33:


                break;

            case 38://免费领一件美衣
                mContext.startActivity(new Intent(mContext, HomePageThreeActivity.class)
                        .putExtra("freeMoney", "199")
                        .putExtra("freeBuyType", 1)
                        .putExtra("fromSign", true)

                );
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 40:

//                if (SignFragment.mSignCountData.getIs_fast_raffle() != 1
//                        && SignFragment.mSignCountData.getCurrent_status_data() != 1) { //没抽完过（新手第一天且当天么没有做完）
//                    ToastUtil.showShortText2("您还未完成当前全部的赚钱任务");
//                    return;
//                }

                if (SignFragment.mSignCountData.getMaxType() == 5 || SignFragment.mSignCountData.getMaxType() == 6) {
                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                if (null != SignDrawalLimitActivity.instance) {
                    SignDrawalLimitActivity.instance.finish();
                }

                intent = new Intent(mContext, SignDrawalLimitActivity.class).putExtra("type", 1);
                intent.putExtra("fromSign", true);

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;

            case 42://新拼团(开团)
                DialogUtils.newTKtishi(mContext);
                break;
            case 43://邀请2位好友参团（新）
                goPinTuanDetail(false, initList);
                break;

            case 46://新完成全部任务去提现


                if (SignFragment.mSignCountData.getCurrent_status_data() != 1) { //当前任务没有做完的话，名称不变化的不能点击
                    ToastUtil.showShortText2("您还未完成当前全部的赚钱任务");
                    return;
                }


                if (SignFragment.mSignCountData.getMaxType() == 5 || SignFragment.mSignCountData.getMaxType() == 6) {
                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    return;
                }

                if (null != SignDrawalLimitActivity.instance) {
                    SignDrawalLimitActivity.instance.finish();
                }
                intent = new Intent(mContext, SignDrawalLimitActivity.class).putExtra("type", 1);
                intent.putExtra("fromSign", true);

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;

            case 47://免费成为皇冠会员
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 5)
                );
                doSignGo = true;
                break;
            case 48://免费领走199元美衣
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 4)
                );
                doSignGo = true;
                break;
            case 49://提现我的佣金
                intent = new Intent(mContext, MyYJactivity.class);
                mContext.startActivity(intent);
                doSignGo = true;
                break;
            case 50://与客服对话一次


                String appId = WxPayUtil.APP_ID; // 填应用AppId
                if (StringUtils.isEmpty(appId)) {
                    appId = YUrl.APP_ID;
                }

                IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
                req.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&toKF=1"; ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                // 可选打开 开发版，体验版和正式版
                if (YUrl.wxMiniDedug) {
                    req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api.sendReq(req);


                doSignGo = true;
                break;
            case 51://与公众号对话一次
                String appId51 = WxPayUtil.APP_ID; // 填应用AppId
                if (StringUtils.isEmpty(appId51)) {
                    appId51 = YUrl.APP_ID;
                }

                IWXAPI api51 = WXAPIFactory.createWXAPI(mContext, appId51);
                WXLaunchMiniProgram.Req req51 = new WXLaunchMiniProgram.Req();
                req51.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
                req51.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&toGZH=1"; ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                // 可选打开 开发版，体验版和正式版
                if (YUrl.wxMiniDedug) {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api51.sendReq(req51);

                doSignGo = true;
                break;

            case 52:
                String appId52 = WxPayUtil.APP_ID; // 填应用AppId
                if (StringUtils.isEmpty(appId52)) {
                    appId52 = YUrl.APP_ID;
                }
                IWXAPI api52 = WXAPIFactory.createWXAPI(mContext, appId52);
                WXLaunchMiniProgram.Req req52 = new WXLaunchMiniProgram.Req();
                req52.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // 填小程序原始id
                req52.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&isSQXZL=1"; ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
                // 可选打开 开发版，体验版和正式版
                if (YUrl.wxMiniDedug) {
                    req52.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req52.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api52.sendReq(req52);
                doSignGo = true;

                break;

            case 53:
                shareWaitDialog.show();
                HashMap<String, String> map = new HashMap<>();
                map.put("size", "1");
                map.put("type", "分享");
                YConn.httpPost(mContext, YUrl.GET_SHARE_IMG, map, new HttpListener<SignTask53Data>() {
                    @Override
                    public void onSuccess(SignTask53Data signTask53Data) {

                        if (signTask53Data.getList().size() == 0) {
                            shareWaitDialog.dismiss();
                            ToastUtil.showShortText2("未获取到分享内容");
                            return;
                        }

                        Picasso.get()
                                .load(YUrl.imgurl + signTask53Data.getList().get(0))
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {
                                        String appId = WxPayUtil.APP_ID; // 填应用AppId
                                        if (StringUtils.isEmpty(appId)) {
                                            appId = YUrl.APP_ID;
                                        }
                                        IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
                                        WXImageObject imgObj = new WXImageObject(bmp);
                                        WXMediaMessage msg = new WXMediaMessage();
                                        msg.mediaObject = imgObj;
                                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 200, true);
                                        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
                                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                                        req.transaction = buildTransaction("img");
                                        req.message = msg;
                                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                                        api.sendReq(req);
                                        shareWaitDialog.dismiss();
                                        doSignGo = true;

                                    }


                                    @Override
                                    public void onBitmapFailed(Exception e, Drawable drawable) {
                                        shareWaitDialog.dismiss();
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable drawable) {
                                    }

                                });

                    }

                    @Override
                    public void onError() {

                    }
                });


                break;


            default:
                break;
        }
    }

    private void initTaskJiangLi(int position, Holder holder, int i, int doType) {
        int getType = Integer.parseInt(taskList.get(i) // 奖励类型（得到什么）
                .get("type_id").toString());

        int getValue = 1;

        // 惊喜提现任务奖励是2，6

        try {
            getValue = Integer.parseInt(taskList.get(i) // 奖励数量（得到什么）
                    .get("value").toString());

        } catch (Exception e) {
            // TODO: handle exception
        }

        int getNum = 1;

        try {
            getNum = Integer.parseInt(initList.get(position) // 奖励发几次（得到什么）
                    .get("num").toString());
        } catch (Exception e) {
            // TODO: handle exception

        }
        //新手任务固定显示5-50元
        if (doType != 40 && doType != 46 && (SignFragment.mSignCountData.getCurrent_date().indexOf("newbie") != -1)) {
            holder.tv_jiangli_count.setText("5-50");
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

            return;
        }


        if (doType == 40) {

            if (YJApplication.instance.isLoginSucess()) {

                holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getUnVipRaffleMoney());


//                                if ("90.0".equals(SignFragment.mSignCountData.getUnVipRaffleMoney())) {
//                                    holder.tv_jiangli_count.setText("90");
//                                } else {
//                                    holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getUnVipRaffleMoney());
//                                }


//                if (null != SignFragment.mSignCountData && SignFragment.mSignCountData.getHasTrailNum() == 2) {
//                    holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getUnVipRaffleMoney());
//                } else {
//                    if (null != SignFragment.mSignCountData
//                            && SignFragment.mSignCountData.getHasDiamondOrVip() != 1
//                            && SignFragment.mSignCountData.getIs_fast_raffle() == 1) {
//                        if (SignFragment.mSignCountData.getHasTrailNum() == 1) {
//                            holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getUnVipRaffleMoney());
//                        } else if (SignFragment.mSignCountData.getHasTrailNum() == 2) {
//                            holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getUnVipRaffleMoney());
//                        } else {
//                            holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getRaffleFixedMoney());
//                        }
//
//                    } else {
//                        holder.tv_jiangli_count.setText(SignFragment.mSignCountData.getRaffleFixedMoney());
//
//                    }
//
//                }


            } else {
                holder.tv_jiangli_count.setText("90");

            }
            holder.tv_jiangli_count.setTextSize(15f);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("提现到微信");


        } else if (doType == 46) {
            holder.tv_jiangli_count.setText("90");
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("提现到微信");

        }

/*           else if (doType == 32) {//分享赚钱任务页

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("66")) {
                    holder.tv_jiangli_count.setText(taskiconList.get(j).get("value") + "");
                }
            }

            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

            holder.tv_jiangli_count.setVisibility(View.VISIBLE);


        } */

        else if (doType == 31) { //好友赢提现


            String typeName = "55";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("38")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }
            holder.tv_jiangli_neirong.setText("可提现");
            holder.tv_jiangli_cunt_danwei.setText("元");

            holder.tv_jiangli_count.setText(typeName);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


        } else if (doType == 30) {//超级分享日


            String typeName = "155";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("37")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }

            holder.tv_jiangli_cunt_danwei.setText("元/人");
            holder.tv_jiangli_count.setText(typeName);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        } else if (doType == 27) {

            String typeName = "100";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("36")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }

            holder.tv_jiangli_count.setText(typeName);


            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("随机提现额度");


        } else if (doType == 28) { //0元购
            final Holder finalHolder3 = holder;

            finalHolder3.tv_jiahao.setVisibility(View.GONE);
            finalHolder3.ll_jiangli.setVisibility(View.GONE);
            finalHolder3.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            finalHolder3.tv_jiangli_neirong.setVisibility(View.GONE);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJQF.KEY_JSONTEXT_CJ0YG);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {
                                    HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJQF.KEY_JSONTEXT_CJ0YG);
                                    if (m != null && m.size() > 0) {
                                        //处理数据

                                        String text1 = m.get("text1") + "";

                                        String text2 = m.get("text2") + "";


                                        finalHolder3.tv_jiahao.setVisibility(View.GONE);
                                        finalHolder3.tv_jiangli_cunt_danwei.setVisibility(View.GONE);

//                                                            finalHolder3.tv_jiangli_count.getPaint().setFakeBoldText(false);

//                                                            finalHolder3.tv_jiangli_count.sets
                                        //设置不为加粗
                                        finalHolder3.tv_jiangli_count.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                                        finalHolder3.tv_jiangli_count.setText(text1);
//                                                            finalHolder3.tv_jiangli_count.setText(Html.fromHtml("<b>"+text1+""+"</b><br/>"));


                                        finalHolder3.tv_jiangli_count.setTextSize(13);
                                        finalHolder3.ll_jiangli.setVisibility(View.VISIBLE);

                                        finalHolder3.tv_jiangli_neirong.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_neirong.setTextSize(13);

                                        finalHolder3.tv_jiangli_neirong.setText(text2);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } else if (doType == 16) {

            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);
        } else {

            // 奖励
            switch (getType) {

                case 1: // 补签卡

                    break;
                case 2: // 0元疯抢

                    break;
                case 3: // 优惠券

                    // LinearLayout.LayoutParams lp = new
                    // LinearLayout.LayoutParams(
                    // LinearLayout.LayoutParams.WRAP_CONTENT,
                    // LinearLayout.LayoutParams.WRAP_CONTENT);
                    // lp.setMargins(0, -DP2SPUtil.dp2px(mContext,
                    // 5),
                    // 0,
                    // 0);
                    // lp.gravity = Gravity.RIGHT;
                    // holder.tv_jiangli_neirong.setLayoutParams(lp);

                    holder.tv_jiangli_count.setText(new DecimalFormat("#.##")
                            .format(Double.parseDouble(getValue * getNum + "")));
                    holder.tv_jiangli_cunt_danwei.setText("元");
                    holder.tv_jiangli_neirong.setText("优惠券");
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


                    break;
                case 4: // 积分

                    holder.tv_jiangli_count.setText(new DecimalFormat("#.##")
                            .format(Double.parseDouble(getValue * getNum + "")));
                    holder.tv_jiangli_neirong.setText("积分");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiahao.setText("+");
                    // holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setText("");
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);

                    break;
                case 5: // 现金

//                                    holder.tv_jiangli_count.setText(new DecimalFormat("0.0#")
//                                            .format(Double.parseDouble(getValue * getNum + "")));

                    holder.tv_jiangli_count.setText(getValue * getNum + "");

                    holder.tv_jiangli_cunt_danwei.setText("元");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 6: // 开店

                    holder.tv_jiangli_count.setText(getValue + "");
                    holder.tv_jiangli_cunt_danwei.setText("元");
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    break;
                case 7: // 夺宝


                    String typeName = "";

                    for (int j = 0; j < taskiconList.size(); j++) {
                        if (doType == 2) {//1分钱夺宝
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals("28")) {
                                typeName = taskiconList.get(j).get("value") + "";
                            }
                        }
                        if (doType == 21) {//1元夺宝---夺提现额度
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals("28")) {
                                typeName = taskiconList.get(j).get("value") + "";
                            }
                        }
                    }


                    holder.tv_jiangli_count.setText(typeName);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);


                    break;
                case 8: // 余额翻倍
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 9: // 开启积分变金币
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 10: // 开启优惠券升级金券
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    break;
                case 11: // 衣豆

                    holder.tv_jiangli_count.setText((int) getValue * getNum + "");
                    holder.tv_jiangli_cunt_danwei.setText("个衣豆");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    break;

                case 12: // 提现额度


                    if (tast == 5) {// 惊喜提现任务 ：2,6
                        holder.tv_jiangli_count.setText("2-60");
                    } else {//其他提现额度任务
                        holder.tv_jiangli_count.setText("3-20");
                    }

                    if (tast == 5) {
                        holder.tv_jiangli_neirong.setText("随机提现额度");
                    } else {
                        holder.tv_jiangli_neirong.setText("随机提现额度");
                    }


                    if (doType == 19 || doType == 20) {  //
                        holder.tv_jiangli_neirong.setText("提现额度");
                        holder.tv_jiangli_count.setText((int) getValue * getNum + "");
                    }


                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setText("元");
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


                    break;


                default:
                    break;
            }


        }


        // 如果是惊喜任务且任务是购买商品，奖励写死600现金     ----任务名称另外取

        if (doType == 24) {

            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        }
        //如果是分享邀请好友集显示XXX元提现额度
        if (doType == 16) {
            final Holder finalHolder3 = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJQF.KEY_JSONTEXT_FXQD);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {
                                    HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJQF.KEY_JSONTEXT_FXQD);
                                    if (m != null && m.size() > 0) {
                                        //处理数据

                                        fxqd_new = m.get("text") + "";
                                        String fd = "0.0";

                                        try {
                                            fd = new DecimalFormat("0.0").format(Double.parseDouble(fxqd_new));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        finalHolder3.tv_jiangli_count.setText(fd);


                                        finalHolder3.tv_jiangli_cunt_danwei.setText("元");
                                        finalHolder3.tv_jiahao.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_count.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_neirong.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_neirong.setText("提现额度");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }


        //购买赢提现写死2-60提现额度
        if (tast == 5 && doType == 6) {

            holder.tv_jiangli_count.setText("2-60");
            holder.tv_jiangli_neirong.setText("随机提现额度");
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
        }


        //如果是继续点赞则不显示奖励  --- 和 参团
        if (doType == 15 || doType == 17) {
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);
        }


        if (doType == 18) { //开团任务固定显示9块9包邮


            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setText("件");
            holder.tv_jiangli_neirong.setVisibility(View.GONE);
            holder.tv_jiangli_count.setText("9.9");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_kaituan.setVisibility(View.VISIBLE);
            holder.tv_kaituan.setText("2");


        } else {
            holder.tv_kaituan.setVisibility(View.GONE);

        }


        //夺宝任务不再按type_id，需要单独处理奖励显示   ---一元夺宝除外
        if (doType == 2 || doType == 22) {
            String typeName = "";

            for (int j = 0; j < taskiconList.size(); j++) {
                if (doType == 2) {//1分钱夺宝
                    if ((taskiconList.get(j).get("id") + "")
                            .equals("28")) {
                        typeName = taskiconList.get(j).get("value") + "";
                    }
                }

                if (doType == 22) {//拼团夺宝
                    if ((taskiconList.get(j).get("id") + "")
                            .equals("35")) {
                        typeName = taskiconList.get(j).get("value") + "";
                    }
                }
            }


            holder.tv_jiangli_count.setText(typeName);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        }


        if (doType == 21) {  //1元夺宝单独处理
            String typeName = "100";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("33")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }

            holder.tv_jiangli_neirong.setText("提现额度");
            holder.tv_jiangli_count.setText(typeName);


            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("元");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);

        }


        if (doType == 23) {//千元红包雨
            holder.tv_jiangli_count.setText("1000元");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        }

        if (doType == 25) {//新分享赢提现
            holder.tv_jiangli_count.setText("50");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("提现额度");

        }


        if (doType == 26) {//去抽奖--千元红包


            final Holder finalHolder = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_CJJXRWQCJ);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {
                                    HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_CJJXRWQCJ);
                                    if (m != null && m.size() > 0) {
                                        //处理数据

                                        quchoujiangjiangli = m.get("text") + "";
                                        finalHolder.tv_jiangli_count.setText(quchoujiangjiangli);
                                        finalHolder.tv_jiangli_count.setVisibility(View.VISIBLE);
                                        finalHolder.tv_jiahao.setVisibility(View.VISIBLE);
                                        finalHolder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);//元
                                        finalHolder.tv_jiangli_neirong.setVisibility(View.GONE);


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
    }

    private void initTaskName(int position, Holder holder, int doType, boolean signCoplete, String value) {
        String miaoshu = "未找到任务";

        switch (doType) {
            case 0:
                miaoshu = "去开店";

                holder.iv_icon.setImageResource(R.drawable.icon_shop_new);

                break;
            case 1:
                miaoshu = "邀请好友";

                holder.iv_icon.setImageResource(R.drawable.icon_yaoqinghaoyou_sign);

                break;
            case 2: // 夺宝
                miaoshu = "去抽奖";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;
            case 3:

                int dov = 1;

                try {

                    dov = Integer.parseInt(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "加" + dov + "件商品到购物车";

                holder.iv_icon.setImageResource(R.drawable.icon_gouwuche_sign);

                break;
            case 4:

                String dovv = initList.get(position).get("num") + "";
                try {

                    dovv = (Integer.parseInt(dovv) * Integer.parseInt(value.split(",")[1])) + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }


                String liulanClass = value.split(",")[0];

                // 填充图标

                if (signCoplete) { // 如果是已完成的---出去icon（url）下载

                    for (int j = 0; j < taskiconList.size(); j++) {

                        if ((taskiconList.get(j).get("id") + "")
                                .equals(initList.get(position).get("icon") + "")) {


                            String def_pic = YUrl.imgurl + taskiconList.get(j).get("icon") + "";
                            holder.iv_icon.setTag(def_pic);

                            if (!TextUtils.isEmpty(def_pic)) {

                                PicassoUtils.initImage(mContext, def_pic, holder.iv_icon);

                            }

                        }

                    }

                } else {
                    holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);
                }

                // 填充名称
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        miaoshu = "浏览" + dovv + "件【" + taskiconList.get(j).get("app_name") + "】";
                    }

                }

                break;
            case 5:

                int dovvv = 1;

                try {

                    dovvv = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String gotoLiuLan = value.split(",")[0];

                // 填充图标
                if (signCoplete) { // 如果是已完成的---出去icon（url）下载

                    for (int j = 0; j < taskiconList.size(); j++) {

                        if ((taskiconList.get(j).get("id") + "")
                                .equals(initList.get(position).get("icon") + "")) {

                            String def_pic = YUrl.imgurl + taskiconList.get(j).get("icon") + "";
                            holder.iv_icon.setTag(def_pic);

                            if (!TextUtils.isEmpty(def_pic)) {

                                PicassoUtils.initImage(mContext, def_pic, holder.iv_icon);

                            }

                        }

                    }

                } else {
                    holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);
                }

                // 填充名称
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        miaoshu = "浏览【" + taskiconList.get(j).get("app_name") + "】" + dovvv + "分钟";
                    }

                }

                break;
            case 6: // 购买商品
                hasGoumai = true;
                if (tast == 5) {// 惊喜提现任务

                } else {// 其他普通任务
                    // 增加黄条泡泡，文案修改成“下单立得现金,更可抽千元大奖哦”，泡泡样式用疯狂星期一的
                    // ----------待定

                }


                int buyCount = 1;
                try {

                    buyCount = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 填充名称
                for (int j = 0; j < taskiconList.size(); j++) {

                    String id1 = taskiconList.get(j).get("id") + "";
                    String icon1 = initList.get(position).get("icon") + "";

                    if (id1.equals(icon1)) {
                        miaoshu = "购买" + buyCount + "件" + taskiconList.get(j).get("app_name") + "商品";
                    }

                }
                if (tast == 5) {
                    miaoshu = "购买赢提现";
                }

                // 统一图标
                holder.iv_icon.setImageResource(R.drawable.icon_shoping_normal);

                break;
            case 7:


                int dovvvvv = 1;

                try {

                    dovvvvv = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //默认显示
                miaoshu = "分享" + dovvvvv + "件商品";
                String where = value.split(",")[0];
                // 填充名称
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
//                                            miaoshu = "分享【" + taskiconList.get(j).get("app_name") + "】" + dovvv + "分钟";


                        if (where.equals("share=spellGroup") || where.equals("share=indiana") || where.equals("share=h5money")) {
                            miaoshu = taskiconList.get(j).get("app_name") + "";
                        } else {
                            miaoshu = "分享" + dovvvvv + "件【" + taskiconList.get(j).get("app_name") + "】";

                        }

                    }

                }


                break;
            case 8:

                int dovvvvvv = 1;

                try {

                    dovvvvvv = Integer.parseInt(value.split(",")[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "分享" + dovvvvvv + "件【时尚搭配】";


                // 填充名称
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {


                        miaoshu = "分享" + dovvvvvv + "件【" + taskiconList.get(j).get("app_name") + "】";


                    }

                }


                break;

            case 11:// 去精选推荐挑美衣
                miaoshu = "去精选推荐挑美衣";
                holder.iv_icon.setImageResource(R.drawable.icon_meiyi);

                break;

            case 12:// 浏览X条热门穿搭
                int dd = 1;

                try {

                    dd = Integer.parseInt(value.split(",")[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                                    miaoshu = "浏览" + dd + "条热门穿搭";
                miaoshu = "浏览" + dd + "条【SHOW社区话题】";


                holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);

                break;
            case 13:// 分享XX件品质美衣
                int dmeiyi = 1;

                try {

                    dmeiyi = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "分享" + dmeiyi + "件品质美衣";
                break;
            case 14:// 分享XX条热门穿搭话题

                int dchuanda = 1;

                try {

                    dchuanda = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "分享" + dchuanda + "条【SHOW社区话题】";

                break;
            case 15:// 继续为好友点赞

                holder.iv_icon.setImageResource(R.drawable.icon_jizan);

                miaoshu = "继续为好友点赞";


                break;

            case 16://邀请好友集赞

                holder.iv_icon.setImageResource(R.drawable.icon_jizan);

                jizanCoplete = (initList.get(position).get("signStatus") + "").equals("1"); // 集赞任务的完成状态
                jizanIndex = initList.get(position).get("index") + "";// 分享邀请好友集赞任务的index
                if (signCoplete) {
                    miaoshu = "继续集赞赢提现";
                } else {
                    miaoshu = "集赞赢提现";
                }


                break;
            case 17://我参与的超级拼团 ---参团-----参与被H5引导过来的拼团


                if (signCoplete) {
                    holder.iv_complete.setVisibility(View.VISIBLE);
                    holder.iv_complete.setImageResource(R.drawable.yicanyu);
                } else {
                    holder.iv_complete.setVisibility(View.GONE);
                }


                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);


                canTuanIndex = initList.get(position).get("index") + "";
                miaoshu = "我参与的超级拼团";

                break;


            case 18://超级拼团任务------开团

                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);

                miaoshu = "去拼团";

                break;


            case 701:

                miaoshu = "分享商品余额翻倍";
                holder.iv_icon.setImageResource(R.drawable.icon_yuefanbei);
                break;
            case 702:
                miaoshu = "分享商品积分升级为金币";// 奖励是得到积分升级
                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;

            case 703:
                miaoshu = "分享商品优惠券升级为金券";// 奖励是得到优惠券升级
                holder.iv_icon.setImageResource(R.drawable.icon_jinquanquan_shngji);
                break;

            case 801:

                holder.iv_icon.setImageResource(R.drawable.icon_yuefanbei);
                miaoshu = "分享商品余额翻倍";// 奖励是得到余额翻倍
                break;

            case 802:
                miaoshu = "分享商品积分升级为金币";// 奖励是得到积分升级

                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;

            case 803:
                miaoshu = "分享商品优惠券升级为金券";// 奖励是得到优惠券升级
                holder.iv_icon.setImageResource(R.drawable.icon_jinquanquan_shngji);

                break;
            case 10:
                miaoshu = "设置我的喜好";// 设置喜好
                holder.iv_icon.setImageResource(R.drawable.set_like_icon);

                break;

            case 19:

                miaoshu = "浏览赢提现";// 浏览赢提现
                holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);

                break;
            case 20:

                miaoshu = "分享赢提现";// 分享赢提现
                break;


            case 21://一元夺宝（提现额度夺宝）
                miaoshu = "抽奖赢提现";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;

            case 22://拼团夺宝
                miaoshu = "超级0元团";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;

            case 23://千元红包雨


                if (YJApplication.instance.isLoginSucess()) {
                    YJApplication.hasRedPacketTask = true;
                }

                miaoshu = "千元红包雨";
                holder.iv_icon.setImageResource(R.drawable.icon_honbaoyu);
                break;


            case 24:  //每月惊喜任务


                // 统一图标
                holder.iv_icon.setImageResource(R.drawable.icon_fan2);


                break;

            case 25://  新分享赢提现


                miaoshu = "分享赢提现";


                holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                break;


            case 26: //去抽奖
                miaoshu = "幸运转盘";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;

            case 27: //余额抽提现
                miaoshu = "余额抽提现";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 28: //超级0元购
                miaoshu = "超级0元购";

                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;

            case 30: //超级分享日


                miaoshu = "超级分享日";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;


            case 31: //好友赢提现


                miaoshu = "好友赢提现";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;


            case 32: //分享赚钱任务页


                miaoshu = "分享赚钱任务页";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;
            case 33:
                miaoshu = "关注衣蝠公众号";
                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;

            case 35:
                miaoshu = "添加衣蝠到我的小程序";
                holder.iv_icon.setImageResource(R.drawable.sign_dingzhi);
                break;
            case 36:
                miaoshu = "关注衣蝠公众号";

                if (YJApplication.instance.isLoginSucess()
                        && SignFragment.mSignCountData.getCurrent_date().equals("newbie01")
                        && YCache.getCacheUser(mContext).getReviewers() == 1) {
                    miaoshu = "浏览1件超值特惠";

                }

                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;
            case 37:
                miaoshu = "下载衣蝠APP";
                holder.iv_icon.setImageResource(R.drawable.sign_download_app);
                break;

            case 38:
                miaoshu = "免费领一件美衣";
                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;

            case 39:
                miaoshu = "邀请两位好友";
                holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                break;
            case 40:
                miaoshu = "完成全部任务，立即提现到微信";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 46:
                miaoshu = "新完成全部任务，立即提现到微信";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 41:
                miaoshu = "加微信客服为好友";
                holder.iv_icon.setImageResource(R.drawable.icon_jizan);
                break;
            case 42:
                miaoshu = "拼团一件热卖美衣";
                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);
                break;

            case 43:
                miaoshu = "邀请2人参团，奖励提现1次";
                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);
                break;

            case 44:
                miaoshu = "订阅奖金到账通知";
                holder.iv_icon.setImageResource(R.drawable.subscribe_first);
                break;
            case 45:
                miaoshu = "订阅大促特价通知";
                holder.iv_icon.setImageResource(R.drawable.subscribe_first);
                break;
            case 47:
                miaoshu = "成为皇冠会员赚千元佣金";
                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;
            case 48:
                miaoshu = "免费领走199元美衣";
                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;
            case 49:

                if (!YJApplication.instance.isLoginSucess()) {
                    miaoshu = "提现我的佣金";

                } else {
                    miaoshu = "提现我的佣金";

                }

                holder.iv_icon.setImageResource(R.drawable.icon_honbaoyu);
                break;
            case 50:
                miaoshu = "与客服对话一次";
                holder.iv_icon.setImageResource(R.drawable.icon_kefu);
                break;
            case 51:
                miaoshu = "与公众号对话一次";
                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;

            case 52:
                miaoshu = "申请小助理月赚千元佣金";
                holder.iv_icon.setImageResource(R.drawable.icon_yaoqinghaoyou_sign);
                break;

            case 53:
                miaoshu = "分享朋友圈赚佣金";
                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;
            default:
                break;
        }

        if (doType == 6) {
            if (tast == 1 || tast == 2 || tast == 5 || tast == 6 || tast == 7) {
//                                    convertView.setBackgroundColor(Color.parseColor("#00000000"));
            }
            holder.vvv.setVisibility(View.VISIBLE);
            // RelativeLayout.LayoutParams layoutParamm =
            // new RelativeLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT,
            // LayoutParams.WRAP_CONTENT);
            // layoutParamm.setMargins(0,
            // DP2SPUtil.dp2px(mContext, 8), 0, 0);
            // holder.sign_rl.setLayoutParams(layoutParamm);

        } else {
            if (tast == 1 || tast == 2 || tast == 5 || tast == 6) {
//                                    convertView.setBackgroundColor(Color.parseColor("#00000000"));
            }
            holder.vvv.setVisibility(View.INVISIBLE);

            // RelativeLayout.LayoutParams layoutParamm =
            // new RelativeLayout.LayoutParams(
            // LayoutParams.WRAP_CONTENT,
            // LayoutParams.WRAP_CONTENT);
            // layoutParamm.setMargins(0,
            // DP2SPUtil.dp2px(mContext, 0), 0, 0);
            // holder.sign_rl.setLayoutParams(layoutParamm);
        }

        String miaoshumiaoshu = "未找到任务";
        String miaoshu111 = miaoshu;
        if (signCoplete && (doType == 4 || doType == 5 || doType == 11)) { // 是已完成的
            // 且是浏览商品的任务
            miaoshumiaoshu = "继续" + miaoshu;
        } else { // 未完成的
            miaoshumiaoshu = miaoshu;

        }
        if (miaoshumiaoshu.length() > 11) {
            String str = miaoshumiaoshu.substring(0, 11);
            miaoshumiaoshu = str + "...";
        }

        if (doType == 24) {
            final Holder finalHolder1 = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_JINGXIGOUMAI_NAME);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                try {
                                    HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_JINGXIGOUMAI_NAME);
                                    if (m != null && m.size() > 0) {
                                        //处理数据

                                        jingxiGoumaiName = m.get("text") + "";

//                                                            finalHolder1.tv_miaoshu.setText(jingxiGoumaiName);

                                        finalHolder1.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));
                                        finalHolder1.tv_miaoshu.setText(Html.fromHtml("<b>" + jingxiGoumaiName + "</b>"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            holder.tv_miaoshu.setText(miaoshumiaoshu);

        }


        if (doType == 6 && tast == 5) {
            holder.tv_miaoshu.setText(Html.fromHtml("<b>" + miaoshu + "</b>"));
            holder.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));

        }


        if (doType == 28 || doType == 30 || doType == 31) {
            holder.tv_miaoshu.setText(Html.fromHtml("<b>" + miaoshu + "</b>"));
            holder.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));
        }


        if (doType == 40) {//完成全部任务，立即提现到微信
            holder.tv_miaoshu.setText(Html.fromHtml("<b>完成全部任务，立即提现到微信</b>"));

            if (YJApplication.instance.isLoginSucess()) {
                holder.tv_miaoshu.setText(Html.fromHtml("<b>您有" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "元可提现，立即去提现</b>"));

            }


//            if (null != SignFragment.mSignCountData && SignFragment.mSignCountData.getHasTrailNum() == 2) {
//
//                holder.tv_miaoshu.setText(Html.fromHtml("<b>您有" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "元可提现，立即去提现</b>"));
//
//
//            } else {
//                if (null != SignFragment.mSignCountData
//                        && SignFragment.mSignCountData.getHasDiamondOrVip() != 1
//                        && SignFragment.mSignCountData.getIs_fast_raffle() == 1) {
//
//
//                    if (SignFragment.mSignCountData.getHasTrailNum() == 1) {
////                                        holder.tv_miaoshu.setText(Html.fromHtml("<b>赠送50次提现机会，立即提现</b>"));
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>您有" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "元可提现，立即去提现</b>"));
//                    } else if (SignFragment.mSignCountData.getHasTrailNum() == 2) {
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>您有" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "元可提现，立即去提现</b>"));
//
//                    } else {
////                                        holder.tv_miaoshu.setText(Html.fromHtml("<b>赠送10次提现机会，立即提现</b>"));
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>赠送50次提现机会，立即提现</b>"));
//
//
//                    }
//
//
//
//
//                }
//
//            }

            holder.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));
            holder.tv_miaoshu.setTextSize(13);

        }

        if (doType == 46) {//新完成全部任务，立即提现到微信
            holder.tv_miaoshu.setText(Html.fromHtml("<b>完成全部任务，立即提现到微信</b>"));
            holder.tv_miaoshu.setTextColor(Color.parseColor("#ff3f8b"));
            holder.tv_miaoshu.setTextSize(13);

        }
    }

    class Holder {
        TextView tv_miaoshu, tv_jiangli_count, tv_jiangli_cunt_danwei, tv_jiangli_neirong, tv_miaoshu_miaoshu,
                tv_miaoshu_gouwu, tv_jiahao;
        ImageView iv_icon, iv_complete, iv_img;

        View vvv;

        RelativeLayout sign_rl;

        LinearLayout ll_jiangli;

        public TextView tv_kaituan;
    }

    public static void goPinTuanDetail(final boolean isSignBack, final List<HashMap<String, Object>> ptList) {//参团任务 获取团情况

        SharedPreferencesUtil.saveBooleanData(mContext, "yaoQingCanTaunGo", false);


        YConn.httpPost(mContext, YUrl.QUERY_LAST_PT, new HashMap<String, String>(), new HttpListener<LastPTdata>() {
            @Override
            public void onSuccess(final LastPTdata result) {
                if (null != result.getData()) {


                    LayoutInflater mInflater = LayoutInflater.from(mContext);
                    final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
                    final View view = mInflater.inflate(R.layout.dialog_new_pt_ct, null);
                    ImageView iv_close = view.findViewById(R.id.iv_close);
                    final TextView tv = view.findViewById(R.id.tv);
                    final Button btn_ok = view.findViewById(R.id.btn_ok);
                    iv_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();

                        }
                    });


                    switch (result.getData().getStatus()) {
                        case 1://没有有效的团
                            tv.setText("您还没有拼团订单，请先挑选一件心仪美衣拼团。");
                            btn_ok.setText("去拼团");
                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mContext.startActivity(new Intent(mContext, NewPThotsaleActivity.class));
                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                            R.anim.slide_match);
                                    SharedPreferencesUtil.saveBooleanData(mContext, "yaoQingCanTaunGo", true);

                                    deleteDialog.dismiss();
                                }
                            });

                            deleteDialog.setCanceledOnTouchOutside(false);
                            deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));

                            ToastUtil.showDialog(deleteDialog);

                            break;
                        case 2://开了团，但是还没有邀请2个人

                            tv.setText("邀请两位好友参团您任意拼团单，即可完成任务，并额外获得1次提现机会哦。参团免费哦。成团后才付费。");
                            if (isSignBack) {
                                tv.setText("您还未邀请两位好友参团。");

                            }
                            btn_ok.setText("去邀请好友");
                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent OneBuyintent = new Intent(mContext, OneBuyGroupsDetailsActivity.class);
                                    OneBuyintent.putExtra("roll_code", result.getData().getRoll_code() + "");
                                    mContext.startActivity(OneBuyintent);
                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                                            R.anim.slide_match);

                                    SharedPreferencesUtil.saveBooleanData(mContext, "yaoQingCanTaunGo", true);

                                    deleteDialog.dismiss();
                                }
                            });


                            deleteDialog.setCanceledOnTouchOutside(false);
                            deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT));

                            ToastUtil.showDialog(deleteDialog);

                            break;
                        case 3: //开了团，已经邀请了2个人(此时需要签掉这个任务)


                            if (task43Index < 0 || (ptList.get(task43Index).get("signStatus") + "").equals("1")) {//没有配置任务或者任务已完成
                                return;
                            }
                            HashMap<String, String> map = new HashMap<>();
                            map.put("day", EntityFactory.signDay);
                            map.put("index_id", task43Index + "");
                            YConn.httpPost(mContext, YUrl.SIGN_DATA, map, new HttpListener<BaseData>() {
                                @Override
                                public void onSuccess(BaseData result) {
                                    tv.setText("恭喜完成任务，增加1次提现机会。");
                                    btn_ok.setText("立即去提现");
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {//去转盘抽提现
                                            if (null != SignDrawalLimitActivity.instance) {
                                                SignDrawalLimitActivity.instance.finish();
                                            }
                                            Intent intent = new Intent(mContext, SignDrawalLimitActivity.class).putExtra("type", 1);
                                            intent.putExtra("fromSign", true);

                                            mContext.startActivity(intent);
                                            ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                                            deleteDialog.dismiss();
                                        }
                                    });


                                    deleteDialog.setCanceledOnTouchOutside(false);
                                    deleteDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT));

                                    ToastUtil.showDialog(deleteDialog);


                                    //刷新赚钱页数据
                                    mRefreshListener.signRefresh();

                                }

                                @Override
                                public void onError() {


                                }
                            });


                            break;


                    }
                }
            }

            @Override
            public void onError() {

            }
        });


    }


    public void showFreeFormDialog(final int i) {// 只能传0和1，0代表返还50，1代表返还100
        // -1：疯狂星期一；
        final Dialog freeDialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.free_form_dialog, null);
        freeDialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        ImageView free_iv_close = (ImageView) view.findViewById(R.id.free_iv_close);
        ImageView to_hotsale = (ImageView) view.findViewById(R.id.to_hotsale);
        free_iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                freeDialog.dismiss();

            }
        });
        to_hotsale.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (i == -1) {

                    Intent intent2 = new Intent((Activity) mContext, CrazyShopListActivity.class);
                    mContext.startActivity(intent2);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                } else {
                    Intent intent = new Intent(mContext, HotSaleActivity.class);
                    intent.putExtra("id", "6");
                    intent.putExtra("title", "热卖");
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    freeDialog.dismiss();


                }


            }
        });
        ImageView free_iv = (ImageView) view.findViewById(R.id.free_iv);
        free_iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, BuyFreeActivity.class);
                if (i == -1) {
                    intent.putExtra("isCrazyMon", true);
                    intent.putExtra("whereMon", SignFragment.whereMon);
                }
                intent.putExtra("cashBack", i);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                freeDialog.dismiss();

            }
        });
        if (i == 1) {
            to_hotsale.setVisibility(View.GONE);
//            free_iv.setImageResource(R.drawable.free_100);
        } else if (i == 0) {
            to_hotsale.setVisibility(View.GONE);
//            free_iv.setImageResource(R.drawable.free_50);
        } else if (i == -1) {
            to_hotsale.setVisibility(View.VISIBLE);
//            free_iv.setImageResource(R.drawable.bg_hdxq);
        }
        freeDialog.setContentView(view);
        freeDialog.setCancelable(false);
        freeDialog.show();

        if (i != -1) { // 疯狂星期一除外，要保存版本号。 ----免单用
            String versionName = "-1";
            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                versionName = "v" + pi.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferencesUtil.saveStringData(mContext, versionName, versionName); // 保存版本
        }

    }


//    public static void dianZan(final boolean isDianji, final boolean isYIndao) {
//
//
//        new SAsyncTask<Void, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected Boolean doInBackground(FragmentActivity context, Void... params) throws Exception {
//                if (isDianji) {
//                    return ModQingfeng.getDianZan(mContext, false);
//                } else {
//
//
//                    if (isYIndao) {
//                        return ModQingfeng.getDianZan(mContext, false);
//                    } else {
//                        return ModQingfeng.getDianZan(mContext, true);
//
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (result) {//点赞成功
//                        if (isDianji) {//用户自己点的继续点赞的任务----没有奖励
//
//
//                            DianZanSucceedDiaolg dialog = new DianZanSucceedDiaolg(mContext, R.style.DialogStyle1);
//                            dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                            dialog.show();
//
//                            //刷新赚钱页数据
//                            mRefreshListener.signRefresh();
//
//
//                        } else {//新用户第一次点赞成功弹窗--获得10元奖励  ---  和用户第二天被引导到APP自动点赞
//
//                            if (!isYIndao) { //不是引导过来的--- 第一次引导过来
//
//                                JiZanCommonDialog dialog = new JiZanCommonDialog(mActivity, R.style.DialogStyle1, "dianzanrenwuwanchengtishi");
//                                dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                dialog.show();
//
//                                SharedPreferencesUtil.saveBooleanData(mContext, YCache.getCacheUser(mContext).getUser_id() + "zdongdianzan", true);
//                                //重置集赞并刷新数据
//                                reJIzan();
//                            } else { //是H5引导过来的----不是第一次引导过来
//
//                                DianZanSucceedDiaolg dialog = new DianZanSucceedDiaolg(mContext, R.style.DialogStyle1);
//                                dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                dialog.show();
//                                //重置集赞并刷新数据
//                                reJIzan();
//                            }
//
//
//                        }
//
//
//                    }
//                }
//            }
//
//        }.execute();
//
//
//    }


    //重置集赞
    public static void reJIzan() {

        new SAsyncTask<Void, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected Boolean doInBackground(FragmentActivity context, Void... params) throws Exception {

                return ModQingfeng.getResJizan(mContext);


            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {

                    mRefreshListener.signRefresh();

                }

            }

        }.execute();


    }

}


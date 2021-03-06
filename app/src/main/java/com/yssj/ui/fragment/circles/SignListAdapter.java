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
    private int tast; // 1---?????? 2---?????? 3---?????? 4---?????? 5---???????????? 6--?????? 7--??????????????????????????????????????????
    private List<HashMap<String, Object>> taskList;// ??????????????????

    public static Context mContext;
    private List<HashMap<String, Object>> taskiconList;


    private List<HashMap<String, Object>> initList;// ???????????????list

    public static String doIconId = "";

    public static String gotoShareValue = "";//??????X????????????????????????

    public static boolean jizanCoplete;

    private String fxqd_new = "0";

    public static boolean neeedFenzhongCompleteDiaog = false;

    public static boolean showFirstClickInSuccseeDialog = false;


    public static int orderStatus = 0;//?????????????????????????????????0????????????1????????? 2??????????????????????????????????????????????????????????????????????????????
    public static int orderCount = 0;//????????????
    public static String pingTuanNum = "-1";//????????????
    public static int offered = 0;//?????????????????????
    public static boolean hasGoumai;//?????????????????????


    public static int lotterynumber;// ???????????????


    // ??????????????????????????????
    public static int doNumShopLiulan;// num
    public static int jiangliIDLiulan; // ?????????ID
    public static String jiangliValueLiulan = ""; // ?????????VULUE
    public static String doValueLiulan = ""; // ????????????value

    public static boolean isForceLookTimeOut;// ????????????????????? ??????


    public static int task43Index = -1;


    // ???????????????????????????
    public static int doNumCD;// num-----??????
    public static String doValueCD = ""; // ????????????value-----??????
    public static int jiangliIDCD; // ?????????ID---??????
    public static String jiangliValueCD = ""; // ?????????VULUE---??????

    public static int jiangliIDJX; // ?????????ID---????????????
    public static String jiangliValueJX = ""; // ?????????VULUE---????????????


    // ???????????????????????????
    public static int doNumShopCart;// num-----?????????
    public static String doValueShopCart = ""; // ????????????value-----?????????
    public static int jiangliIDShopCart; // ?????????ID---?????????
    public static String jiangliValueShopCart = ""; // ?????????VULUE---?????????


    public static String signIndex; // ????????????????????????????????????index
    public static int tuanClass = 1;
    public static String canTuanIndex; // ???????????????index
    public static String jizanIndex; // ?????????????????????????????????index

    public static boolean isSignComplete; // ??????????????????????????????????????? true--????????? false---?????????


    public static int jiangliID; // ?????????ID
    public static int doType; // ?????????????????????
    public static int doClass_jx; // ??????????????? 1?????? 2?????? 3??????
    public static int doClass; // ??????????????? 1?????? 2?????? 3??????
    public static int doNum;
    public static String jiangliValue = ""; // ?????????VULUE
    public static String doValue = ""; // ????????????value


    public static HashMap<String, String> indexMap = new HashMap<String, String>(); // ??????index??????????????????????????????

    public static HashMap<String, String> minuteMap = new HashMap<String, String>(); // ??????????????????????????????

    public static HashMap<String, Integer> classMap = new HashMap<String, Integer>(); // ??????task_class??????????????????????????????

    public static HashMap<String, Integer> jiangliIDmap = new HashMap<String, Integer>(); // ??????id??????????????????????????????

    public static HashMap<String, String> jiangliValueMap = new HashMap<String, String>(); // ??????value??????????????????????????????

    public static HashMap<String, String> fenzhongDoValueMap = new HashMap<String, String>(); // ????????????doValue??????????????????????????????


    public static HashMap<String, String> fenzhongIconID = new HashMap<String, String>(); // ????????????doIconID??????????????????????????????


    public static HashMap<String, String> addShopCartIconID = new HashMap<String, String>(); // ???????????????doIconID??????????????????????????????


    public static String doNeedCount = "-1"; // ??????????????????????????????status ???------????????????????????????????????????

    public static boolean doSignGo = false;


    private String jingxiGoumaiName = "?????????????????????????????????";
    private String meiyiJieBot = "";
    private String quchoujiangjiangli = "555";
    public static Activity mActivity;


    public SignListAdapter(int tast,
                           List<HashMap<String, Object>> taskList, // ????????????
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

            for (int i = 0; i < taskList.size(); i++) { // ?????????????????? ????????????????????????
                // ????????????

                String id = initList.get(position).get("t_id").toString();
                String taskID = (String) taskList.get(i).get("t_id"); // ????????????ID

                String task_type = initList.get(position).get("task_type").toString();

                if (task_type.equals("9") || task_type.equals("999")) { // ???????????????????????????

                    // ?????????

                    if (task_type.equals("9")) {// ??????

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

                        holder.tv_miaoshu.setText("?????????????????????");
                        Boolean signCoplete = (initList.get(position).get("signStatus") + "").equals("1"); // ??????????????????????????????
                        // 1???????????????
                        if (YJApplication.isLogined || YJApplication.instance.isLoginSucess()) { // ?????????
                            if (signCoplete) {
                                holder.iv_complete.setImageResource(R.drawable.icon_yilingqu);
                                // holder.sign_rl.setBackgroundResource(R.drawable.sigin_list_bg_c5);
                            } else {

                                holder.iv_complete.setVisibility(View.GONE);
                                // holder.iv_icon.clearAnimation();
                                // ??????
                                // signAnimation =
                                // AnimationUtils.loadAnimation(mContext,
                                // R.anim.signanim);
                                // holder.iv_wan_buy.startAnimation(signAnimation);


                                // ??????????????????????????? ???????????????????????????????????? ---????????????????????????
                                int buyCountt = 1; // 1:50????????????100?????????,2:50?????????

                                try {

                                    buyCountt = Integer.parseInt(initList.get(position) // ??????????????????????????????
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

                                if (SharedPreferencesUtil.getStringData(mContext, versionName, "-1").equals("-1")) { // ?????????????????????

                                    if (buyCountt == 2) {
                                        showFreeFormDialog(0);
                                    }
                                    if (buyCountt == 1) {
                                        showFreeFormDialog(1);
                                    }

                                }

                            }
                        } else { // ?????????

                            holder.iv_complete.setVisibility(View.GONE);

                        }

                    } else { // ???????????????
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

                        // ????????????????????????????????????????????????
                        // ?????????????????????????????????????????????
                        String miaoshu = "";
                        miaoshu = "???????????????";


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
                                                    //????????????

                                                    meiyiJieBot = m.get("t1") + "";


                                                    finalHolder2.tv_jiangli_neirong.setText(meiyiJieBot);
                                                    if (lotterynumber > 0) {
                                                        finalHolder2.tv_jiangli_count.setText("??????" + lotterynumber + "???");
                                                        finalHolder2.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                                                        finalHolder2.tv_jiangli_count.setTextSize(15.4f);

                                                    } else {
                                                        finalHolder2.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                                                        finalHolder2.tv_jiangli_cunt_danwei.setText("??????");
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
                    if (id.equals(taskID)) { // ??????????????????????????????????????????????????? ???????????????

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

                        // ????????????
                        String t_name = taskList.get(i).get("t_name") + "";
                        if (t_name.equals("3?????????")) {
                            t_name = "3?????????";
                        }

                        if (t_name.equals("5?????????")) {
                            t_name = "5?????????";
                        }

                        // ?????????????????????????????????
                        int doType = 0;
                        if (initList.size() > 0) {
                            doType = Integer.parseInt(initList.get(position) // ??????????????????????????????
                                    .get("task_type").toString());
                        }

                        String status = "-1";
                        status = initList.get(position).get("status") + "";
                        boolean s_complete = status.equals("0");
                        boolean signCoplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // ??????????????????????????????
                        // 1???????????????

                        /**
                         * doType??? 0??????-1????????????-2??????-3???X?????????????????????-4??????????????????-5??????????????????
                         * 6??????X?????????-7??????????????????-8??????????????? 701??????????????????--??????????????????
                         * 702??????????????????--???????????? 703??????????????????--???????????????
                         * 801??????????????????--?????????????????? 802??????????????????--????????????
                         * 803??????????????????--???????????????
                         *
                         */

                        String value = initList.get(position) // ??????????????????????????????
                                .get("value") + "";

                        if (doType == 1) {
                            holder.tv_miaoshu_miaoshu.setVisibility(View.VISIBLE);
                        } else {
                            holder.tv_miaoshu_miaoshu.setVisibility(View.GONE);
                        }


                        if (YJApplication.instance.isLoginSucess() || YJApplication.isLogined) {
                            // ????????????????????????
                            if (tast == 1 || tast == 2 || tast == 4 || tast == 5 || tast == 6 || tast == 7) { // ?????????????????????????????????????????????????????????????????????????????????????????????---???????????????

                                if (doType == 1 || doType == 16) { // ????????????????????????????????? ????????????????????????
                                    holder.tv_jiangli_count.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiangli_neirong.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiangli_cunt_danwei.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                                    holder.tv_miaoshu.setTextColor(Color.parseColor("#7d7d7d"));
                                    // ??????????????????
                                    if (signCoplete) { // ???????????????
                                        holder.iv_complete.setVisibility(View.VISIBLE);
                                        holder.iv_complete.setImageResource(R.drawable.icon_jxyq);
                                    } else {
                                        holder.iv_complete.setVisibility(View.GONE);
                                    }


                                } else {
                                    if (signCoplete) { // ???????????????
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

                                        if (doType == 23) {//???????????????????????????
                                            holder.iv_complete.setImageResource(R.drawable.icon_over);
                                        }

                                    } else { // ????????????

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

                        } else { // ????????????????????????????????????

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

                        //???????????????????????????????????????????????????
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

                        // ???????????????????????????
                        if (doType == 7 || doType == 8 || doType == 13 || doType == 14 || doType == 20) {

                            holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                        }
                        if (tast == 3) {
                            holder.iv_complete.setVisibility(View.GONE);
                        }
                        // ??????(??????????????????)
                        initTaskName(position, holder, doType, signCoplete, value);
                        // ????????????
                        initTaskJiangLi(position, holder, i, doType);

                    }
                }


            }


        } else {
            holder = (Holder) convertView.getTag();
        }

        // ????????????
        if (convertView == null) {

            return mInflater.inflate(R.layout.item_signtask_error, null);

        }
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //??????????????????
                initTaskClick(position);
            }

        });

        return convertView;
    }

    private void initTaskClick(int position) {
        // ????????????????????????????????????
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

        //??????????????????????????????????????????
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


        //???????????????????????????(??????????????????)
        boolean mIsVip;
        mIsVip = CommonUtils.isVip(SignFragment.mSignCountData.getIsVip(), SignFragment.mSignCountData.getMaxType());
        if (mIsVip && (SignFragment.mSignCountData.getCurrent_date() + "").equals("newbie02")) {
            ToastUtil.showMyToast(mContext, "??????????????????????????????????????????5?????????????????????9????????????", 4000);
            return;
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????(????????????1???????????????)
        if (!mIsVip && (SignFragment.mSignCountData.getCurrent_date() + "").equals("newbie02") && SignFragment.daytaskListYet.size() > 0) {
            mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                    .putExtra("guide_vipType", 4)
                    .putExtra("isFromSign2Round", 1)
            );
            return;
        }


        if (SignFragment.whetherTask != 1) {//???????????????
            DialogUtils.signGuideVip(mContext);
            return;
        }

        if (tast == 3) {// ??????????????????????????????
            return;
        }

        if (initList.size() == 0 || position > initList.size() - 1) {
            return;
        }

        //?????????H5??????????????????????????????----????????????9257
        int h5 = 1;
        try {
            h5 = Integer.parseInt(initList.get(position).get("task_h5").toString());
        } catch (Exception e5) {
            e5.printStackTrace();
        }

        if (h5 >= 4) {
            ToastUtil.showShortText(mContext, "??????????????????H5????????????~");
            return;
        }


        try {
            doType = Integer.parseInt(initList.get(position) // ??????????????????????????????
                    .get("task_type").toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        String status = "-1";
        status = initList.get(position).get("status") + "";
        boolean s_complete = status.equals("0");
        boolean isComplete = (initList.get(position).get("signStatus") + "").equals("1") && s_complete; // ??????????????????????????????
//
//                                        boolean isComplete = (initList.get(position).get("signStatus") + "")
//                                                .equals("1"); // ???????????????
        if (isComplete) {
            isSignComplete = true; // ?????????
            //???????????????????????????????????????
            if (doType != 2 && doType != 4 && doType != 5 && doType != 1 && doType != 9
                    && doType != 11 && doType != 16 && doType != 17 && doType != 18 && doType != 25) {
                return;
            }

        } else {
            isSignComplete = false; // ?????????
        }

        doValue = initList.get(position).get("value") + ""; // ????????????value
        doNeedCount = initList.get(position).get("status") + ""; // ????????????value

        doClass = Integer.parseInt(initList.get(position) // ??????????????????????????????
                .get("task_class").toString());


        doIconId = initList.get(position).get("icon") + "";


        try {
            doNum = Integer.parseInt(initList.get(position) // ??????????????????????????????
                    .get("num").toString()); // ??????????????????
        } catch (Exception eee) {
            eee.printStackTrace();
        }

        if (null == initList.get(position).get("num")
                || initList.get(position).get("num").equals("")) {
            if (!isComplete) {
                ToastUtil.showShortText(mContext, "??????????????????,???????????????");
                return;
            }

        }

        signIndex = initList.get(position).get("index") + "";

        // ??????????????????????????????
        for (int j = 0; j < taskList.size(); j++) {

            String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
            String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

            if (id.equals(taskID)) {
                jiangliValue = taskList.get(j).get("value") + "";
                jiangliID = Integer.parseInt(taskList.get(j).get("type_id").toString());
            }

        }
        //??????????????????????????????
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


            String appId = WxPayUtil.APP_ID; // ?????????AppId
            if (StringUtils.isEmpty(appId)) {
                appId = YUrl.APP_ID;
            }
            IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // ??????????????????id
            req.path = "pages/sign/sign?fromApp=1";                  ////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
            // ???????????? ?????????????????????????????????
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

        // ??????
        if (doType == 7 || doType == 8 || doType == 701 || doType == 702
                || doType == 703 || doType == 801 || doType == 802 || doType == 803 || doType == 32) {


            if (doType == 7 || doType == 32) {//??????X???????????????????????? ????????????????????????
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

            case 0: // ?????? --???????????????

                Intent intentKaidian = new Intent(mContext, MineLikeActivity.class);
                intentKaidian.putExtra("isSign", true);
                ((MainMenuActivity) mContext).startActivityForResult(intentKaidian, 13334);

                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 1:// ????????????


                for (int i = 0; i < taskList.size(); i++) { // ??????????????????

                    // ????????????

                    String id = initList.get(position).get("t_id").toString();
                    String taskID = (String) taskList.get(i).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) { // ???????????????????????????????????????????????????

                    }

                }

                Intent intentYao = new Intent(mContext, YaoQingFrendsActivity.class);
                intentYao.putExtra("jumpFrom", "YaoQingHaoyou");

                mContext.startActivity(intentYao);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 2: // ??????

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
            case 3: // ???X?????????????????????
                // indexMap.put(YConstance.ADD_TO_SHOPCART,
                // signIndex);
                SharedPreferencesUtil.saveStringData(mContext, YConstance.ADD_TO_SHOPCART,
                        signIndex);

                classMap.put(YConstance.ADD_TO_SHOPCART, doClass);

                // ???????????????????????????

                doValueShopCart = initList.get(position).get("value").toString(); // ????????????value

                try {
                    doNumShopCart = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) {
                        jiangliValueShopCart = taskList.get(j).get("value") + "";
                        jiangliIDShopCart = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }


                String leiileii = "";


                try {
                    doNumShopLiulan = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????

                    leiileii = doValue.split(",")[0]; // ??????

                    doValueLiulan = initList.get(position).get("value").toString(); // ????????????value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

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
                        || gotoAdd.equals("type1=0")) {// ??????

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
                } else if (gotoAdd.equals("collection=csss_shop")) {// ????????????
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
                } else if (gotoAdd.equals("collection=shopping_page")) {// ????????????
                    if (isComplete) {

                        // ????????????
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
                } else if (gotoAdd.equals("collection=shop_activity")) { // ????????????

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
                                intent.putExtra("title", "??????");
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
            case 4: // ??????X??????????????? //????????????-----??????

                String lei = "";

                lei = null; // ??????
                try {
                    lei = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (lei.equals("collection=collocation_shop")) {// ??????
                    intent = new Intent(mContext, ForceLookMatchActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    SharedPreferencesUtil.saveStringData(mContext, YConstance.Pref.SINGVALUE, doValue);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else if (lei.equals("collection=shop_activity")) { // ????????????

                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);

                    mContext.startActivity(intent);


                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


                } else if (lei.equals("collection=browse_shop")) { // ????????????
                    // ----??????????????????????????????
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    intent.putExtra("pinJievalue", lei);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // ?????????????????????????????????
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


            case 19://???????????????
                String leiTX = "";

                try {
                    leiTX = doValue.split(",")[0]; // ??????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                if (leiTX.equals("share=myq")) {//?????????


                    try {
                        doValueCD = initList.get(position).get("value").toString().split(",")[1]; // ????????????value
                        doNumCD = Integer.parseInt(initList.get(position) // ??????????????????????????????
                                .get("num").toString()); // ??????????????????
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    // ??????????????????????????????
                    for (int j = 0; j < taskList.size(); j++) {

                        String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                        String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                        if (id.equals(taskID)) {
                            jiangliValueCD = taskList.get(j).get("value") + "";
                            jiangliIDCD = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }


                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "pubuliu_sign_tixian");
                    mContext.startActivity(new Intent(mContext, CommonActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                } else if (leiTX.equals("collection=collocation_shop")) {// ??????
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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=shop_activity")) { // ????????????
                    intent = new Intent(mContext, SignActiveShopActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);


//                                                intent = new Intent(context, ForceLookActivity.class);
//                                                intent.putExtra("isFilterConditionActivity", true);
//                                                intent.putExtra("title", "??????");
//                                                intent.putExtra("pinJievalue", leiTX);
//
//                                                intent.putExtra("doIconId", doIconId);
//                                                intent.putExtra("isSignLiulan", true);
//
//
//                                                context.startActivity(intent);
//                                                ((Activity) context).overridePendingTransition(
//                                                        R.anim.slide_left_in, R.anim.slide_match);


                } else if (leiTX.equals("collection=browse_shop")) { // ????????????
                    // ----??????????????????????????????
                    intent = new Intent(mContext, ForceLookActivity.class);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                } else {
                    // ?????????????????????????????????
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
                    intent.putExtra("title", "??????");
                    intent.putExtra("pinJievalue", leiTX);

                    intent.putExtra("doIconId", doIconId);
                    intent.putExtra("isSignLiulan", true);


                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);

                }

                break;


            case 20://?????????????????????

                TongJiUtils.yunYunTongJi("fxytx", 1101, 11, mContext);

                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

//                                                getIndianaRuleContent();


                //                                                ToastUtil.showDialog(new ShareGetTXdialog(mContext, R.style.invate_dialog_style));

                break;


            case 21://????????????????????????????????????


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

            case 5:// ????????????????????????????????? X??????
                // ?????????------?????????
                String leii = "";


                try {
                    doNumShopLiulan = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????

                    leii = doValue.split(",")[0]; // ??????

                    doValueLiulan = initList.get(position).get("value").toString(); // ????????????value
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) {
                        jiangliValueLiulan = taskList.get(j).get("value") + "";
                        jiangliIDLiulan = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                    }

                }

                String fenzhongDoValue = initList.get(position).get("value").toString(); // ????????????value
                // (??????????????????--??????????????????)

                String gotoLiuLan = "";
                try {
                    gotoLiuLan = doValue.split(",")[0];
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // ???????????????index?????????????????????????????????????????????
                boolean sameIndex = signIndex
                        .equals(indexMap.get(YConstance.SCAN_SHOP_TIME));

                // ??????????????????????????????????????????????????????????????????????????????
                if (minuteMap.size() != 0 || sameIndex) {

                }

                /**
                 * ?????????????????????????????????????????????????????????????????? ------
                 * -----??????????????????????????????????????????????????????????????????
                 * ????????????????????????????????????
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
                        || gotoLiuLan.equals("type1=0")) {// ??????

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
                } else if (gotoLiuLan.equals("collection=csss_shop")) {// ????????????
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
                } else if (gotoLiuLan.equals("collection=shopping_page")) {// ????????????
                    if (isComplete) {

                        // ????????????
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
                } else if (gotoLiuLan.equals("share=myq")) {// ????????????
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
                } else if (gotoLiuLan.equals("collection=shop_activity")) { // ????????????

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
            case 6: // ??????X????????? -------

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
            case 7: // ??????X???????????????
                break;
            case 8: // ??????X????????????
                break;
            case 9: // ??????

                intent = new Intent(mContext, BuyFreeActivity.class);

                int valuevv = 1;

                try {
                    valuevv = Integer.parseInt(doValue);
                } catch (Exception e2) {
                }

                if (valuevv == 1) { // 100??????
                    intent.putExtra("cashBack", 1);
                }

                if (valuevv == 2) { // 50??????
                    intent.putExtra("cashBack", 0);
                }

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            // 701-803????????????1??? ?????????????????????
            // 701-703?????????????????????
            // 801-803??????????????????
            case 701: // ?????????????????? --- ????????????
                break;
            case 702:// ?????????????????? ----????????????
                break;

            case 703:// ??????????????????-----???????????????
                break;

            case 801:// ??????????????? ---????????????
                break;

            case 802:// ??????????????? ----????????????
                break;

            case 803:// ??????????????? ---???????????????
                break;

            case 999:// ???????????????
                if (lotterynumber > 0) {
                    intent = new Intent(mContext, WithdrawalLimitActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {
                    showFreeFormDialog(-1);
                }
                break;
            case 10:// ????????????

                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                    if (id.equals(taskID)) {
                        String jiangliValue = taskList.get(j).get("value") + "";
                        int jiangliID = Integer
                                .parseInt(taskList.get(j).get("type_id").toString());
                        // ???????????????
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

            case 11: // ????????????????????????
                if (!YCache.getCacheUser(mContext).getHobby().contains("_")) {
                    ToastUtil.showShortText(mContext, "?????????????????????~");


                    intent = new Intent(mContext, MyLikeActivity.class);
                    intent.putExtra("isJingxuanJump", true);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);


                    return;
                }

                if (SharedPreferencesUtil.getBooleanData(mContext,
                        YConstance.Pref.JINGXUAN_SCAN_FINISH, false)) {
                    ToastUtil.showShortText(mContext, "???????????????????????????????????????~");
                } else {

                    doClass_jx = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("task_class").toString());
                    // ??????????????????????????????---?????????????????????????????????
                    for (int j = 0; j < taskList.size(); j++) {
                        String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                        String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

                        if (id.equals(taskID)) {
                            jiangliValueJX = taskList.get(j).get("value") + "";
                            jiangliIDJX = Integer
                                    .parseInt(taskList.get(j).get("type_id").toString());
                        }

                    }

                    if (!isSignComplete) {
                        // ??????index
                        SharedPreferencesUtil.saveStringData(mContext,
                                YConstance.LIULANJINGXUANTUJIANINDEX, signIndex);
                        // ?????????????????????????????????
                        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN",
                                true);

                        // ?????????????????????????????????????????????
                        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGN", true);
                    }

                    // ?????????????????????????????????
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    SharedPreferencesUtil.saveBooleanData(mContext, "openJingxuan", true);
                    Intent intent2 = new Intent((Activity) mContext, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    mContext.startActivity(intent2);
                }
                break;
            case 12:

                // ???????????????index
                SharedPreferencesUtil.saveStringData(mContext, YConstance.LIULANCHUANDAINDEX,
                        signIndex);

                // ???????????????????????????

                try {
                    doValueCD = initList.get(position).get("value").toString(); // ????????????value
                    doNumCD = Integer.parseInt(initList.get(position) // ??????????????????????????????
                            .get("num").toString()); // ??????????????????
                } catch (Exception e1) {
                    e1.printStackTrace();
                }


                // ??????????????????????????????
                for (int j = 0; j < taskList.size(); j++) {

                    String id = initList.get(position).get("t_id").toString(); // ?????????????????????ID----tasklist??????t_id????????????
                    String taskID = (String) taskList.get(j).get("t_id"); // ????????????ID

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
            case 13:// ??????XX???????????????
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareShop");
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 14:// ??????XX?????????????????????
                intent = new Intent(mContext, YaoQingFrendsActivity.class);
                intent.putExtra("jumpFrom", "shareTieZi");

                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;
            case 15:// ????????????

                if (isGratis.equals("false")) {//????????????5??????????????????
                    JiZanCommonDialog dialog = new JiZanCommonDialog(mActivity, R.style.DialogStyle1, "jixujizandianji");
                    dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
                    dialog.show();


                } else {//?????????????????????????????????????????????
//                            dianZan(true, false);
                }

                break;

            case 16://??????????????????
                TongJiUtils.yunYunTongJi("point", 907, 9, mContext);
                intent = new Intent(mContext, PointLikeRankingActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(
                        R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 17: //??????jkm

                tuanClass = 2;

                if (isSignComplete) {
                    //????????????---????????????????????????

                    intent = new Intent(mContext, GroupsDetailsActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                } else {//?????????
                    if (offered == 1) { //??????????????????---??????????????????
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("isTuanEnd", true);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//?????????-----???????????????????????????--????????????


                        if (offered == 2) {//??????offered = 2?????????????????????????????????????????????????????????????????????????????????
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
            case 18://??????
                tuanClass = 1;

                if (orderCount > 0) {//?????????
                    if (orderStatus == 1) { //?????????
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    } else {//?????????????????????
                        intent = new Intent(mContext, GroupsDetailsActivity.class);
                        intent.putExtra("completeStatus", 3);
                        mContext.startActivity(intent);
                        ((FragmentActivity) mContext).overridePendingTransition(
                                R.anim.slide_left_in, R.anim.slide_match);
                    }

                } else { //?????????
                    intent = new Intent(mContext, SignGroupShopActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(
                            R.anim.slide_left_in, R.anim.slide_match);
                }


                break;

            case 22://????????????
                intent = new Intent(mContext, IndianaListActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in,
                        R.anim.slide_match);
                break;

            case 23://???????????????
//                                            DialogUtils.redPacketDownDialog(mContext);

                DialogUtils.redPacketDownSignTCDialog(mContext);


                break;


            case 24://??????X?????????//??????????????????--------------//


                DialogUtils.meuyueJingxiDialog(mContext, doValue);


                break;


            case 25://  ??????????????????

                ToastUtil.showDialog(new NewShareGetTXDialog(mActivity, mContext, R.style.DialogStyle1,
                        jiangliID));

                break;
            case 26://  ?????????
                PublicUtil.getBalanceNum(mContext, null, true);
                break;

            case 27: //???????????????


                intent = new Intent(mContext, WithdrawalLimitActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                break;
            case 28: //??????0??????
                ToastUtil.showDialog(new LingYUANGOUTishiRedDialog(mContext, R.style.DialogStyle1, doValue.split(",")[0]));
                break;
            case 30: //???????????????
                intent = new Intent(mContext, FriendCommissionActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;
            case 31: //???????????????
                intent = new Intent(mContext, FriendCommissionActivity.class);
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                break;

            case 32:  //?????????????????????

//                                        intent = new Intent(mContext, FriendCommissionActivity.class);
//                                        mContext.startActivity(intent);
//                                        ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 33:


                break;

            case 38://?????????????????????
                mContext.startActivity(new Intent(mContext, HomePageThreeActivity.class)
                        .putExtra("freeMoney", "199")
                        .putExtra("freeBuyType", 1)
                        .putExtra("fromSign", true)

                );
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;

            case 40:

//                if (SignFragment.mSignCountData.getIs_fast_raffle() != 1
//                        && SignFragment.mSignCountData.getCurrent_status_data() != 1) { //?????????????????????????????????????????????????????????
//                    ToastUtil.showShortText2("??????????????????????????????????????????");
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

            case 42://?????????(??????)
                DialogUtils.newTKtishi(mContext);
                break;
            case 43://??????2????????????????????????
                goPinTuanDetail(false, initList);
                break;

            case 46://??????????????????????????????


                if (SignFragment.mSignCountData.getCurrent_status_data() != 1) { //???????????????????????????????????????????????????????????????
                    ToastUtil.showShortText2("??????????????????????????????????????????");
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

            case 47://????????????????????????
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 5)
                );
                doSignGo = true;
                break;
            case 48://????????????199?????????
                mContext.startActivity(new Intent(mContext, MyVipListActivity.class)
                        .putExtra("guide_vipType", 4)
                );
                doSignGo = true;
                break;
            case 49://??????????????????
                intent = new Intent(mContext, MyYJactivity.class);
                mContext.startActivity(intent);
                doSignGo = true;
                break;
            case 50://?????????????????????


                String appId = WxPayUtil.APP_ID; // ?????????AppId
                if (StringUtils.isEmpty(appId)) {
                    appId = YUrl.APP_ID;
                }

                IWXAPI api = WXAPIFactory.createWXAPI(mContext, appId);
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // ??????????????????id
                req.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&toKF=1"; ////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
                // ???????????? ?????????????????????????????????
                if (YUrl.wxMiniDedug) {
                    req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api.sendReq(req);


                doSignGo = true;
                break;
            case 51://????????????????????????
                String appId51 = WxPayUtil.APP_ID; // ?????????AppId
                if (StringUtils.isEmpty(appId51)) {
                    appId51 = YUrl.APP_ID;
                }

                IWXAPI api51 = WXAPIFactory.createWXAPI(mContext, appId51);
                WXLaunchMiniProgram.Req req51 = new WXLaunchMiniProgram.Req();
                req51.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // ??????????????????id
                req51.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&toGZH=1"; ////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
                // ???????????? ?????????????????????????????????
                if (YUrl.wxMiniDedug) {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
                } else {
                    req51.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                }
                api51.sendReq(req51);

                doSignGo = true;
                break;

            case 52:
                String appId52 = WxPayUtil.APP_ID; // ?????????AppId
                if (StringUtils.isEmpty(appId52)) {
                    appId52 = YUrl.APP_ID;
                }
                IWXAPI api52 = WXAPIFactory.createWXAPI(mContext, appId52);
                WXLaunchMiniProgram.Req req52 = new WXLaunchMiniProgram.Req();
                req52.userName = YUrl.WX_MINIAPP_ORIGINAL_ID; // ??????????????????id
                req52.path = "/pages/mine/AppMessage/AppMessage?fromApp=1&isSQXZL=1"; ////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
                // ???????????? ?????????????????????????????????
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
                map.put("type", "??????");
                YConn.httpPost(mContext, YUrl.GET_SHARE_IMG, map, new HttpListener<SignTask53Data>() {
                    @Override
                    public void onSuccess(SignTask53Data signTask53Data) {

                        if (signTask53Data.getList().size() == 0) {
                            shareWaitDialog.dismiss();
                            ToastUtil.showShortText2("????????????????????????");
                            return;
                        }

                        Picasso.get()
                                .load(YUrl.imgurl + signTask53Data.getList().get(0))
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {
                                        String appId = WxPayUtil.APP_ID; // ?????????AppId
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
        int getType = Integer.parseInt(taskList.get(i) // ??????????????????????????????
                .get("type_id").toString());

        int getValue = 1;

        // ???????????????????????????2???6

        try {
            getValue = Integer.parseInt(taskList.get(i) // ??????????????????????????????
                    .get("value").toString());

        } catch (Exception e) {
            // TODO: handle exception
        }

        int getNum = 1;

        try {
            getNum = Integer.parseInt(initList.get(position) // ?????????????????????????????????
                    .get("num").toString());
        } catch (Exception e) {
            // TODO: handle exception

        }
        //????????????????????????5-50???
        if (doType != 40 && doType != 46 && (SignFragment.mSignCountData.getCurrent_date().indexOf("newbie") != -1)) {
            holder.tv_jiangli_count.setText("5-50");
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("???");
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
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("???????????????");


        } else if (doType == 46) {
            holder.tv_jiangli_count.setText("90");
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("???????????????");

        }

/*           else if (doType == 32) {//?????????????????????

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

        else if (doType == 31) { //???????????????


            String typeName = "55";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("38")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }
            holder.tv_jiangli_neirong.setText("?????????");
            holder.tv_jiangli_cunt_danwei.setText("???");

            holder.tv_jiangli_count.setText(typeName);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


        } else if (doType == 30) {//???????????????


            String typeName = "155";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("37")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }

            holder.tv_jiangli_cunt_danwei.setText("???/???");
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
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("??????????????????");


        } else if (doType == 28) { //0??????
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
                                        //????????????

                                        String text1 = m.get("text1") + "";

                                        String text2 = m.get("text2") + "";


                                        finalHolder3.tv_jiahao.setVisibility(View.GONE);
                                        finalHolder3.tv_jiangli_cunt_danwei.setVisibility(View.GONE);

//                                                            finalHolder3.tv_jiangli_count.getPaint().setFakeBoldText(false);

//                                                            finalHolder3.tv_jiangli_count.sets
                                        //??????????????????
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

            // ??????
            switch (getType) {

                case 1: // ?????????

                    break;
                case 2: // 0?????????

                    break;
                case 3: // ?????????

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
                    holder.tv_jiangli_cunt_danwei.setText("???");
                    holder.tv_jiangli_neirong.setText("?????????");
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


                    break;
                case 4: // ??????

                    holder.tv_jiangli_count.setText(new DecimalFormat("#.##")
                            .format(Double.parseDouble(getValue * getNum + "")));
                    holder.tv_jiangli_neirong.setText("??????");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiahao.setText("+");
                    // holder.tv_jiahao.setTextColor(Color.parseColor("#ff3f8b"));
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setText("");
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);

                    break;
                case 5: // ??????

//                                    holder.tv_jiangli_count.setText(new DecimalFormat("0.0#")
//                                            .format(Double.parseDouble(getValue * getNum + "")));

                    holder.tv_jiangli_count.setText(getValue * getNum + "");

                    holder.tv_jiangli_cunt_danwei.setText("???");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 6: // ??????

                    holder.tv_jiangli_count.setText(getValue + "");
                    holder.tv_jiangli_cunt_danwei.setText("???");
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    break;
                case 7: // ??????


                    String typeName = "";

                    for (int j = 0; j < taskiconList.size(); j++) {
                        if (doType == 2) {//1????????????
                            if ((taskiconList.get(j).get("id") + "")
                                    .equals("28")) {
                                typeName = taskiconList.get(j).get("value") + "";
                            }
                        }
                        if (doType == 21) {//1?????????---???????????????
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
                case 8: // ????????????
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 9: // ?????????????????????
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);

                    break;
                case 10: // ???????????????????????????
                    holder.tv_jiahao.setVisibility(View.GONE);
                    holder.tv_jiangli_count.setVisibility(View.GONE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    break;
                case 11: // ??????

                    holder.tv_jiangli_count.setText((int) getValue * getNum + "");
                    holder.tv_jiangli_cunt_danwei.setText("?????????");
                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_neirong.setVisibility(View.GONE);
                    break;

                case 12: // ????????????


                    if (tast == 5) {// ?????????????????? ???2,6
                        holder.tv_jiangli_count.setText("2-60");
                    } else {//????????????????????????
                        holder.tv_jiangli_count.setText("3-20");
                    }

                    if (tast == 5) {
                        holder.tv_jiangli_neirong.setText("??????????????????");
                    } else {
                        holder.tv_jiangli_neirong.setText("??????????????????");
                    }


                    if (doType == 19 || doType == 20) {  //
                        holder.tv_jiangli_neirong.setText("????????????");
                        holder.tv_jiangli_count.setText((int) getValue * getNum + "");
                    }


                    holder.tv_jiahao.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_count.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                    holder.tv_jiangli_cunt_danwei.setText("???");
                    holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);


                    break;


                default:
                    break;
            }


        }


        // ????????????????????????????????????????????????????????????600??????     ----?????????????????????

        if (doType == 24) {

            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        }
        //????????????????????????????????????XXX???????????????
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
                                        //????????????

                                        fxqd_new = m.get("text") + "";
                                        String fd = "0.0";

                                        try {
                                            fd = new DecimalFormat("0.0").format(Double.parseDouble(fxqd_new));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        finalHolder3.tv_jiangli_count.setText(fd);


                                        finalHolder3.tv_jiangli_cunt_danwei.setText("???");
                                        finalHolder3.tv_jiahao.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_count.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_neirong.setVisibility(View.VISIBLE);
                                        finalHolder3.tv_jiangli_neirong.setText("????????????");
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


        //?????????????????????2-60????????????
        if (tast == 5 && doType == 6) {

            holder.tv_jiangli_count.setText("2-60");
            holder.tv_jiangli_neirong.setText("??????????????????");
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
        }


        //???????????????????????????????????????  --- ??? ??????
        if (doType == 15 || doType == 17) {
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_count.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);
        }


        if (doType == 18) { //????????????????????????9???9??????


            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.GONE);
            holder.tv_jiangli_count.setText("9.9");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_kaituan.setVisibility(View.VISIBLE);
            holder.tv_kaituan.setText("2");


        } else {
            holder.tv_kaituan.setVisibility(View.GONE);

        }


        //?????????????????????type_id?????????????????????????????????   ---??????????????????
        if (doType == 2 || doType == 22) {
            String typeName = "";

            for (int j = 0; j < taskiconList.size(); j++) {
                if (doType == 2) {//1????????????
                    if ((taskiconList.get(j).get("id") + "")
                            .equals("28")) {
                        typeName = taskiconList.get(j).get("value") + "";
                    }
                }

                if (doType == 22) {//????????????
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


        if (doType == 21) {  //1?????????????????????
            String typeName = "100";

            for (int j = 0; j < taskiconList.size(); j++) {
                if ((taskiconList.get(j).get("id") + "")
                        .equals("33")) {
                    typeName = taskiconList.get(j).get("value") + "";
                }
            }

            holder.tv_jiangli_neirong.setText("????????????");
            holder.tv_jiangli_count.setText(typeName);


            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setText("???");
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);

        }


        if (doType == 23) {//???????????????
            holder.tv_jiangli_count.setText("1000???");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.GONE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.GONE);
            holder.tv_jiangli_neirong.setVisibility(View.GONE);

        }

        if (doType == 25) {//??????????????????
            holder.tv_jiangli_count.setText("50");
            holder.tv_jiangli_count.setVisibility(View.VISIBLE);
            holder.tv_jiahao.setVisibility(View.VISIBLE);
            holder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setVisibility(View.VISIBLE);
            holder.tv_jiangli_neirong.setText("????????????");

        }


        if (doType == 26) {//?????????--????????????


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
                                        //????????????

                                        quchoujiangjiangli = m.get("text") + "";
                                        finalHolder.tv_jiangli_count.setText(quchoujiangjiangli);
                                        finalHolder.tv_jiangli_count.setVisibility(View.VISIBLE);
                                        finalHolder.tv_jiahao.setVisibility(View.VISIBLE);
                                        finalHolder.tv_jiangli_cunt_danwei.setVisibility(View.VISIBLE);//???
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
        String miaoshu = "???????????????";

        switch (doType) {
            case 0:
                miaoshu = "?????????";

                holder.iv_icon.setImageResource(R.drawable.icon_shop_new);

                break;
            case 1:
                miaoshu = "????????????";

                holder.iv_icon.setImageResource(R.drawable.icon_yaoqinghaoyou_sign);

                break;
            case 2: // ??????
                miaoshu = "?????????";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;
            case 3:

                int dov = 1;

                try {

                    dov = Integer.parseInt(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "???" + dov + "?????????????????????";

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

                // ????????????

                if (signCoplete) { // ?????????????????????---??????icon???url?????????

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

                // ????????????
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        miaoshu = "??????" + dovv + "??????" + taskiconList.get(j).get("app_name") + "???";
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

                // ????????????
                if (signCoplete) { // ?????????????????????---??????icon???url?????????

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

                // ????????????
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
                        miaoshu = "?????????" + taskiconList.get(j).get("app_name") + "???" + dovvv + "??????";
                    }

                }

                break;
            case 6: // ????????????
                hasGoumai = true;
                if (tast == 5) {// ??????????????????

                } else {// ??????????????????
                    // ?????????????????????????????????????????????????????????,???????????????????????????????????????????????????????????????
                    // ----------??????

                }


                int buyCount = 1;
                try {

                    buyCount = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // ????????????
                for (int j = 0; j < taskiconList.size(); j++) {

                    String id1 = taskiconList.get(j).get("id") + "";
                    String icon1 = initList.get(position).get("icon") + "";

                    if (id1.equals(icon1)) {
                        miaoshu = "??????" + buyCount + "???" + taskiconList.get(j).get("app_name") + "??????";
                    }

                }
                if (tast == 5) {
                    miaoshu = "???????????????";
                }

                // ????????????
                holder.iv_icon.setImageResource(R.drawable.icon_shoping_normal);

                break;
            case 7:


                int dovvvvv = 1;

                try {

                    dovvvvv = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //????????????
                miaoshu = "??????" + dovvvvv + "?????????";
                String where = value.split(",")[0];
                // ????????????
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {
//                                            miaoshu = "?????????" + taskiconList.get(j).get("app_name") + "???" + dovvv + "??????";


                        if (where.equals("share=spellGroup") || where.equals("share=indiana") || where.equals("share=h5money")) {
                            miaoshu = taskiconList.get(j).get("app_name") + "";
                        } else {
                            miaoshu = "??????" + dovvvvv + "??????" + taskiconList.get(j).get("app_name") + "???";

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

                miaoshu = "??????" + dovvvvvv + "?????????????????????";


                // ????????????
                for (int j = 0; j < taskiconList.size(); j++) {
                    if ((taskiconList.get(j).get("id") + "")
                            .equals(initList.get(position).get("icon") + "")) {


                        miaoshu = "??????" + dovvvvvv + "??????" + taskiconList.get(j).get("app_name") + "???";


                    }

                }


                break;

            case 11:// ????????????????????????
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_meiyi);

                break;

            case 12:// ??????X???????????????
                int dd = 1;

                try {

                    dd = Integer.parseInt(value.split(",")[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                                    miaoshu = "??????" + dd + "???????????????";
                miaoshu = "??????" + dd + "??????SHOW???????????????";


                holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);

                break;
            case 13:// ??????XX???????????????
                int dmeiyi = 1;

                try {

                    dmeiyi = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "??????" + dmeiyi + "???????????????";
                break;
            case 14:// ??????XX?????????????????????

                int dchuanda = 1;

                try {

                    dchuanda = Integer.parseInt(value.split(",")[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                miaoshu = "??????" + dchuanda + "??????SHOW???????????????";

                break;
            case 15:// ?????????????????????

                holder.iv_icon.setImageResource(R.drawable.icon_jizan);

                miaoshu = "?????????????????????";


                break;

            case 16://??????????????????

                holder.iv_icon.setImageResource(R.drawable.icon_jizan);

                jizanCoplete = (initList.get(position).get("signStatus") + "").equals("1"); // ???????????????????????????
                jizanIndex = initList.get(position).get("index") + "";// ?????????????????????????????????index
                if (signCoplete) {
                    miaoshu = "?????????????????????";
                } else {
                    miaoshu = "???????????????";
                }


                break;
            case 17://???????????????????????? ---??????-----?????????H5?????????????????????


                if (signCoplete) {
                    holder.iv_complete.setVisibility(View.VISIBLE);
                    holder.iv_complete.setImageResource(R.drawable.yicanyu);
                } else {
                    holder.iv_complete.setVisibility(View.GONE);
                }


                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);


                canTuanIndex = initList.get(position).get("index") + "";
                miaoshu = "????????????????????????";

                break;


            case 18://??????????????????------??????

                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);

                miaoshu = "?????????";

                break;


            case 701:

                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_yuefanbei);
                break;
            case 702:
                miaoshu = "?????????????????????????????????";// ???????????????????????????
                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;

            case 703:
                miaoshu = "????????????????????????????????????";// ??????????????????????????????
                holder.iv_icon.setImageResource(R.drawable.icon_jinquanquan_shngji);
                break;

            case 801:

                holder.iv_icon.setImageResource(R.drawable.icon_yuefanbei);
                miaoshu = "????????????????????????";// ???????????????????????????
                break;

            case 802:
                miaoshu = "?????????????????????????????????";// ???????????????????????????

                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;

            case 803:
                miaoshu = "????????????????????????????????????";// ??????????????????????????????
                holder.iv_icon.setImageResource(R.drawable.icon_jinquanquan_shngji);

                break;
            case 10:
                miaoshu = "??????????????????";// ????????????
                holder.iv_icon.setImageResource(R.drawable.set_like_icon);

                break;

            case 19:

                miaoshu = "???????????????";// ???????????????
                holder.iv_icon.setImageResource(R.drawable.icon_liulan_sign);

                break;
            case 20:

                miaoshu = "???????????????";// ???????????????
                break;


            case 21://????????????????????????????????????
                miaoshu = "???????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;

            case 22://????????????
                miaoshu = "??????0??????";
                holder.iv_icon.setImageResource(R.drawable.icon_duobao_new);
                break;

            case 23://???????????????


                if (YJApplication.instance.isLoginSucess()) {
                    YJApplication.hasRedPacketTask = true;
                }

                miaoshu = "???????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_honbaoyu);
                break;


            case 24:  //??????????????????


                // ????????????
                holder.iv_icon.setImageResource(R.drawable.icon_fan2);


                break;

            case 25://  ??????????????????


                miaoshu = "???????????????";


                holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                break;


            case 26: //?????????
                miaoshu = "????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;

            case 27: //???????????????
                miaoshu = "???????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 28: //??????0??????
                miaoshu = "??????0??????";

                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;

            case 30: //???????????????


                miaoshu = "???????????????";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;


            case 31: //???????????????


                miaoshu = "???????????????";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;


            case 32: //?????????????????????


                miaoshu = "?????????????????????";

                holder.iv_icon.setImageResource(R.drawable.icon_fenxiangri);
                break;
            case 33:
                miaoshu = "?????????????????????";
                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;

            case 35:
                miaoshu = "??????????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.sign_dingzhi);
                break;
            case 36:
                miaoshu = "?????????????????????";

                if (YJApplication.instance.isLoginSucess()
                        && SignFragment.mSignCountData.getCurrent_date().equals("newbie01")
                        && YCache.getCacheUser(mContext).getReviewers() == 1) {
                    miaoshu = "??????1???????????????";

                }

                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;
            case 37:
                miaoshu = "????????????APP";
                holder.iv_icon.setImageResource(R.drawable.sign_download_app);
                break;

            case 38:
                miaoshu = "?????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;

            case 39:
                miaoshu = "??????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_fenxiang_nom);
                break;
            case 40:
                miaoshu = "??????????????????????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 46:
                miaoshu = "?????????????????????????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_choujiang);
                break;
            case 41:
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_jizan);
                break;
            case 42:
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);
                break;

            case 43:
                miaoshu = "??????2????????????????????????1???";
                holder.iv_icon.setImageResource(R.drawable.icon_pintuan);
                break;

            case 44:
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.subscribe_first);
                break;
            case 45:
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.subscribe_first);
                break;
            case 47:
                miaoshu = "?????????????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_jinbi_shengji);
                break;
            case 48:
                miaoshu = "????????????199?????????";
                holder.iv_icon.setImageResource(R.drawable.icon_lingyuangou);
                break;
            case 49:

                if (!YJApplication.instance.isLoginSucess()) {
                    miaoshu = "??????????????????";

                } else {
                    miaoshu = "??????????????????";

                }

                holder.iv_icon.setImageResource(R.drawable.icon_honbaoyu);
                break;
            case 50:
                miaoshu = "?????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_kefu);
                break;
            case 51:
                miaoshu = "????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.guanzhu_gzh);
                break;

            case 52:
                miaoshu = "?????????????????????????????????";
                holder.iv_icon.setImageResource(R.drawable.icon_yaoqinghaoyou_sign);
                break;

            case 53:
                miaoshu = "????????????????????????";
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

        String miaoshumiaoshu = "???????????????";
        String miaoshu111 = miaoshu;
        if (signCoplete && (doType == 4 || doType == 5 || doType == 11)) { // ???????????????
            // ???????????????????????????
            miaoshumiaoshu = "??????" + miaoshu;
        } else { // ????????????
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
                                        //????????????

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


        if (doType == 40) {//??????????????????????????????????????????
            holder.tv_miaoshu.setText(Html.fromHtml("<b>??????????????????????????????????????????</b>"));

            if (YJApplication.instance.isLoginSucess()) {
                holder.tv_miaoshu.setText(Html.fromHtml("<b>??????" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "??????????????????????????????</b>"));

            }


//            if (null != SignFragment.mSignCountData && SignFragment.mSignCountData.getHasTrailNum() == 2) {
//
//                holder.tv_miaoshu.setText(Html.fromHtml("<b>??????" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "??????????????????????????????</b>"));
//
//
//            } else {
//                if (null != SignFragment.mSignCountData
//                        && SignFragment.mSignCountData.getHasDiamondOrVip() != 1
//                        && SignFragment.mSignCountData.getIs_fast_raffle() == 1) {
//
//
//                    if (SignFragment.mSignCountData.getHasTrailNum() == 1) {
////                                        holder.tv_miaoshu.setText(Html.fromHtml("<b>??????50??????????????????????????????</b>"));
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>??????" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "??????????????????????????????</b>"));
//                    } else if (SignFragment.mSignCountData.getHasTrailNum() == 2) {
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>??????" + SignFragment.mSignCountData.getUnVipRaffleMoney() + "??????????????????????????????</b>"));
//
//                    } else {
////                                        holder.tv_miaoshu.setText(Html.fromHtml("<b>??????10??????????????????????????????</b>"));
//                        holder.tv_miaoshu.setText(Html.fromHtml("<b>??????50??????????????????????????????</b>"));
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

        if (doType == 46) {//?????????????????????????????????????????????
            holder.tv_miaoshu.setText(Html.fromHtml("<b>??????????????????????????????????????????</b>"));
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

    public static void goPinTuanDetail(final boolean isSignBack, final List<HashMap<String, Object>> ptList) {//???????????? ???????????????

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
                        case 1://??????????????????
                            tv.setText("??????????????????????????????????????????????????????????????????");
                            btn_ok.setText("?????????");
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
                        case 2://?????????????????????????????????2??????

                            tv.setText("?????????????????????????????????????????????????????????????????????????????????1????????????????????????????????????????????????????????????");
                            if (isSignBack) {
                                tv.setText("????????????????????????????????????");

                            }
                            btn_ok.setText("???????????????");
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
                        case 3: //???????????????????????????2??????(??????????????????????????????)


                            if (task43Index < 0 || (ptList.get(task43Index).get("signStatus") + "").equals("1")) {//???????????????????????????????????????
                                return;
                            }
                            HashMap<String, String> map = new HashMap<>();
                            map.put("day", EntityFactory.signDay);
                            map.put("index_id", task43Index + "");
                            YConn.httpPost(mContext, YUrl.SIGN_DATA, map, new HttpListener<BaseData>() {
                                @Override
                                public void onSuccess(BaseData result) {
                                    tv.setText("???????????????????????????1??????????????????");
                                    btn_ok.setText("???????????????");
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {//??????????????????
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


                                    //?????????????????????
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


    public void showFreeFormDialog(final int i) {// ?????????0???1???0????????????50???1????????????100
        // -1?????????????????????
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
                    intent.putExtra("title", "??????");
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

        if (i != -1) { // ????????????????????????????????????????????? ----?????????
            String versionName = "-1";
            try {
                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                versionName = "v" + pi.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            SharedPreferencesUtil.saveStringData(mContext, versionName, versionName); // ????????????
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
//                    if (result) {//????????????
//                        if (isDianji) {//???????????????????????????????????????----????????????
//
//
//                            DianZanSucceedDiaolg dialog = new DianZanSucceedDiaolg(mContext, R.style.DialogStyle1);
//                            dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                            dialog.show();
//
//                            //?????????????????????
//                            mRefreshListener.signRefresh();
//
//
//                        } else {//????????????????????????????????????--??????10?????????  ---  ??????????????????????????????APP????????????
//
//                            if (!isYIndao) { //?????????????????????--- ?????????????????????
//
//                                JiZanCommonDialog dialog = new JiZanCommonDialog(mActivity, R.style.DialogStyle1, "dianzanrenwuwanchengtishi");
//                                dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                dialog.show();
//
//                                SharedPreferencesUtil.saveBooleanData(mContext, YCache.getCacheUser(mContext).getUser_id() + "zdongdianzan", true);
//                                //???????????????????????????
//                                reJIzan();
//                            } else { //???H5???????????????----???????????????????????????
//
//                                DianZanSucceedDiaolg dialog = new DianZanSucceedDiaolg(mContext, R.style.DialogStyle1);
//                                dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                                dialog.show();
//                                //???????????????????????????
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


    //????????????
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


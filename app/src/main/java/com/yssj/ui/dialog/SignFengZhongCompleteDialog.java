//package com.yssj.ui.dialog;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.YConstance;
//import com.yssj.activity.R;
//import com.yssj.ui.activity.CommonActivity;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.circles.SweetFriendsDetails;
//import com.yssj.ui.fragment.circles.SignFragment;
//import com.yssj.ui.fragment.circles.SignListAdapter;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.SignUtil;
//
//import java.util.Timer;
//
///**
// * 分钟数浏览完成
// *
// * @author qingfeng
// */
//public class SignFengZhongCompleteDialog extends Dialog implements View.OnClickListener {
//    private TextView tv1, tv2, tv3, tv4, title;
//    private Button gobuy1, gobuy2, liebiao;
//    private RelativeLayout rl_twobt;
//    private Context context;
//    private String jumpFrom;
//    private ImageView icon_close;
//
//
//    SignUtil.ShareCompleteCallBack shareCompleteCallBack;
//
//
//    public SignFengZhongCompleteDialog(Context context, int style, String jumpFrom, SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
//        super(context, style);
//        this.context = context;
//        this.jumpFrom = jumpFrom;
//        this.shareCompleteCallBack = shareCompleteCallBack;
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_sign_common);
//
//        tv1 = (TextView) findViewById(R.id.tv1);
//        tv2 = (TextView) findViewById(R.id.tv2);
//        tv3 = (TextView) findViewById(R.id.tv3);
//        tv4 = (TextView) findViewById(R.id.tv4);
//
//        title = (TextView) findViewById(R.id.title);
//
//        gobuy1 = (Button) findViewById(R.id.gobuy1); // 一个按钮时的按钮
//        gobuy2 = (Button) findViewById(R.id.gobuy2);
//        liebiao = (Button) findViewById(R.id.liebiao);
//        icon_close = (ImageView) findViewById(R.id.icon_close);
//
//        rl_twobt = (RelativeLayout) findViewById(R.id.rl_twobt); // 两个按钮
//
//        gobuy1.setOnClickListener(this);
//        gobuy2.setOnClickListener(this);
//        liebiao.setOnClickListener(this);
//        icon_close.setOnClickListener(this);
//
//        SignListAdapter.neeedFenzhongCompleteDiaog = false;
//
//        initData();
//
//    }
//
//    private void initData() {
//
//
//        title.setText("任务完成！");
//
//        tv1.setVisibility(View.VISIBLE);
//        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
//        tv2.setVisibility(View.VISIBLE);
//
//        // 浏览版块
//        finishScanMode(1);
//        // 奖励的Value
//
//
//        if (null != SignListAdapter.jiangliValueMap.get(YConstance.SCAN_SHOP_TIME)) {
//
//
//            String jianglivalue = SignListAdapter.jiangliValueMap.get(YConstance.SCAN_SHOP_TIME);
//
//            // 奖励可能是积分现金优惠券
//            switch (SignListAdapter.jiangliIDmap.get(YConstance.SCAN_SHOP_TIME)) {
//                case 3: // 优惠券
//
//                    String jianglicontent = jianglivalue + "元优惠券";
//                    String textorther = jianglicontent + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
//                    SpannableString tttText = new SpannableString(textorther);
//                    tttText.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, jianglicontent.length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv2.setText(tttText);
//
//                    break;
//                case 4: // 积分
//
//                    String jianglicontentjifen = jianglivalue + "积分";
//                    String textortherjifen = jianglicontentjifen + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
//                    SpannableString tttTextjifen = new SpannableString(textortherjifen);
//                    tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
//                            jianglicontentjifen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv2.setText(tttTextjifen);
//
//                    break;
//                case 5: // 现金
//
//                    String jianglicontentxianjin = jianglivalue + "元";
//                    String textortherxinjin = jianglicontentxianjin + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
//                    SpannableString tttTextxianjin = new SpannableString(textortherxinjin);
//                    tttTextxianjin.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
//                            jianglicontentxianjin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv2.setText(tttTextxianjin);
//
//                    break;
//                case 11: // 衣豆
//
//                    String jianglicontentYidou = jianglivalue + "个衣豆";
//                    String textortherxinYidou = jianglicontentYidou + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
//                    SpannableString tttTextYidou = new SpannableString(textortherxinYidou);
//                    tttTextYidou.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
//                            jianglicontentYidou.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv2.setText(tttTextYidou);
//
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//        tv3.setVisibility(View.GONE);
//        tv4.setVisibility(View.GONE);
//
////
////        if (SignListAdapter.signIsShow) {
////
////
////            rl_twobt.setVisibility(View.GONE);
////            gobuy1.setVisibility(View.VISIBLE);
////            gobuy1.setText("一键做下个任务");
////
////
////        } else {
//        //只要是分钟数，就用两个按钮
//        rl_twobt.setVisibility(View.VISIBLE);
//        gobuy1.setVisibility(View.GONE);
//
//        gobuy2.setTextSize(14);
//        liebiao.setTextSize(14);
//
//
//        gobuy2.setText("一键做下个任务");
//        liebiao.setText("继续浏览");
////        }
//
//
//    }
//
//    /**
//     * 浏览板块 mode = 1 分钟数 mode = 0 个数
//     */
//    private void finishScanMode(int mode) {
//        if (null != SignListAdapter.fenzhongDoValueMap.get(YConstance.SCAN_SHOP_TIME)) {
//
//            String bankuai = SignListAdapter.fenzhongDoValueMap.get(YConstance.SCAN_SHOP_TIME);
//
//            if (bankuai.equals("collection=collocation_shop") || bankuai.equals("type1=0")) {
//                tv1.setText("完成【时尚搭配】浏览~");
//            } else if (bankuai.equals("collection=shop_activity")) {
//                tv1.setText("完成【超值活动商品】浏览~");
//            } else {
//                tv1.setText("完成【" + NewSignCommonDiaolg.app_name + "】浏览~");
//            }
//        }
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
////            case R.id.gobuy1: // 去购物
////                dismiss();
////                shareCompleteCallBack.clickNext();
////                break;
//
//            case R.id.gobuy2:
//
//                if (SignFragment.signIsShow) {
//                    shareCompleteCallBack.clickNext();
//                } else {
//                    if (null != CommonActivity.instance) {
//                        CommonActivity.instance.finish();
//                    }
//
//                    if (null != SweetFriendsDetails.instance) {
//                        SweetFriendsDetails.instance.finish();
//                    }
//
//                    // 跳至赚钱
//
//                    SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//
//                    Intent intent = new Intent(context, CommonActivity.class);
//                    intent.putExtra("isTastComplete", true);
//                    context.startActivity(intent);
//                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//
//                }
//
//
//                dismiss();
//                break;
//
//            case R.id.liebiao://继续浏览
//                dismiss();
//
//                break;
//            case R.id.icon_close:
//                dismiss();
//                break;
//        }
//    }
//
//}

package com.yssj.ui.dialog;

import java.util.Timer;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.circles.SweetFriendsDetails;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置喜好完成
 *
 * @author qingfeng
 */
public class SignFinishDialogNew extends Dialog implements android.view.View.OnClickListener {
    private TextView tv1, tv2, tv3, tv4, title;
    private Button gobuy1, gobuy2, liebiao;
    private RelativeLayout rl_twobt;
    private Context context;
    private String jumpFrom;
    private ImageView icon_close;
    public static Timer overtimer;
    public static Timer overtimershow;
    public static Timer timer;

    SignUtil.ShareCompleteCallBack shareCompleteCallBack;

    public SignFinishDialogNew(Context context, int style, String jumpFrom) {
        super(context, style);
        this.context = context;
        this.jumpFrom = jumpFrom;

    }


    public SignFinishDialogNew(Context context, int style, String jumpFrom, SignUtil.ShareCompleteCallBack shareCompleteCallBack) {
        super(context, style);
        this.context = context;
        this.jumpFrom = jumpFrom;
        this.shareCompleteCallBack = shareCompleteCallBack;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_common);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        title = (TextView) findViewById(R.id.title);

        gobuy1 = (Button) findViewById(R.id.gobuy1); // 一个按钮时的按钮
        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);

        rl_twobt = (RelativeLayout) findViewById(R.id.rl_twobt); // 两个按钮

        gobuy1.setOnClickListener(this);
        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);

        initData();

    }

    private void initData() {

        if (jumpFrom.equals("setLikeCoplete")) {

            title.setText("任务完成！");
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv1.setVisibility(View.VISIBLE);
            tv1.setText("设置成功~");
            tv2.setVisibility(View.VISIBLE);

            /**
             * //保存在本地 SharedPreferencesUtil.saveStringData(mContext,
             * "jianglivaluesetlike", jiangliValue);
             * SharedPreferencesUtil.saveStringData(mContext,
             * "jiangliidsetlike", jiangliID+"");
             */

            String jianglivaluesetlike = SharedPreferencesUtil.getStringData(context, "jianglivaluesetlike", "0");
            // 目前只有现金奖励
            String jiangliidsetlike = SharedPreferencesUtil.getStringData(context, "jiangliidsetlike", "0");

            String jianglicontentxianjin = jianglivaluesetlike + "元";
            String textortherxinjin = jianglicontentxianjin + "奖励已经存入余额，赶紧去买买买吧~";
            SpannableString tttTextxianjin = new SpannableString(textortherxinjin);
            tttTextxianjin.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                    jianglicontentxianjin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv2.setText(tttTextxianjin);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            gobuy1.setVisibility(View.VISIBLE);
            gobuy1.setText("一键做下个任务");
            SharedPreferencesUtil.saveBooleanData(context, "isSignSetLikeComplete", false);

        } else {

            title.setText("任务完成！");

            tv1.setVisibility(View.VISIBLE);
            tv1.setGravity(Gravity.CENTER_HORIZONTAL);
            tv2.setVisibility(View.VISIBLE);

            // 浏览版块
            finishScanMode(1);

            // 奖励的Value

            String jianglivalue = SignListAdapter.jiangliValueMap.get(YConstance.SCAN_SHOP_TIME);
            // 奖励可能是积分现金优惠券
            switch (SignListAdapter.jiangliIDmap.get(YConstance.SCAN_SHOP_TIME)) {
                case 3: // 优惠券

                    String jianglicontent = jianglivalue + "元优惠券";
                    String textorther = jianglicontent + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                    SpannableString tttText = new SpannableString(textorther);
                    tttText.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, jianglicontent.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv2.setText(tttText);

                    break;
                case 4: // 积分

                    String jianglicontentjifen = jianglivalue + "积分";
                    String textortherjifen = jianglicontentjifen + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                    SpannableString tttTextjifen = new SpannableString(textortherjifen);
                    tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                            jianglicontentjifen.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv2.setText(tttTextjifen);

                    break;
                case 5: // 现金

                    String jianglicontentxianjin = jianglivalue + "元";
                    String textortherxinjin = jianglicontentxianjin + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                    SpannableString tttTextxianjin = new SpannableString(textortherxinjin);
                    tttTextxianjin.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                            jianglicontentxianjin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv2.setText(tttTextxianjin);

                    break;
                case 11: // 衣豆

                    String jianglicontentYidou = jianglivalue + "个衣豆";
                    String textortherxinYidou = jianglicontentYidou + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                    SpannableString tttTextYidou = new SpannableString(textortherxinYidou);
                    tttTextYidou.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                            jianglicontentYidou.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv2.setText(tttTextYidou);

                    break;

                default:
                    break;
            }

            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);

//			rl_twobt.setVisibility(View.GONE);
//			gobuy1.setVisibility(View.VISIBLE);
//
//			gobuy1.setText("买买买");


            rl_twobt.setVisibility(View.VISIBLE);
            gobuy1.setVisibility(View.GONE);

            gobuy2.setTextSize(14);
            liebiao.setTextSize(14);


//            gobuy2.setText("继续浏览");
//            liebiao.setText("一键做下个任务");

            gobuy2.setText("一键做下个任务");
            liebiao.setText("继续浏览");
        }

    }

    /**
     * 浏览板块 mode = 1 分钟数 mode = 0 个数
     */
    private void finishScanMode(int mode) {

        String bankuai = SignListAdapter.fenzhongDoValueMap.get(YConstance.SCAN_SHOP_TIME);

        if (bankuai.equals("collection=collocation_shop") || bankuai.equals("type1=0")) {
            tv1.setText("完成【时尚搭配】浏览~");
        } else if (bankuai.equals("collection=shop_activity")) {
            tv1.setText("完成【超值活动商品】浏览~");
        } else {
            tv1.setText("完成【" + NewSignCommonDiaolg.app_name + "】浏览~");
        }

        //
        // else if (bankuai.equals("type1=2")) {
        // tv1.setText("完成【宝宝的上衣】浏览~");
        // } else if (bankuai.equals("type1=3")) {
        // tv1.setText("完成【仙女的裙子】浏览~");
        // } else if (bankuai.equals("type1=4")) {
        // tv1.setText("完成【萌妹的裤子】浏览~");
        // } else if (bankuai.equals("type1=6")) {
        // tv1.setText("完成【最热销单品】浏览~");
        // } else if (bankuai.equals("type1=7")) {
        // tv1.setText("完成【女王的套装】浏览~");
        // } else if (bankuai.equals("type1=8")) {
        // tv1.setText("完成【潮流新品】浏览~");
        // }
        //
        // else if (bankuai.equals("collection=shop_activity")) {
        // tv1.setText("完成【超值活动商品】浏览~");
        // } else if (bankuai.equals("type2=11")) {// 小外套
        // tv1.setText("完成【帅气外套】浏览~");
        // } else if (bankuai.equals("type2=23")) {
        // tv1.setText("完成【气质美裙】浏览~");
        // } else if (bankuai.equals("favorite=29")) {
        // tv1.setText("完成【甜美韩系】浏览~");
        // } else if (bankuai.equals("favorite=30")) {
        // tv1.setText("完成【欧美潮范】浏览~");
        // } else if (bankuai.equals("fix_price=20")) {
        // tv1.setText("完成【超值特惠】浏览~");
        // } else if (bankuai.equals("fix_price=22")) {
        // tv1.setText("完成【流行趋势】浏览~");
        // }
        //
        // else if (bankuai.equals("style=105")) {
        // tv1.setText("完成【萌系可爱风】浏览~");
        // }
        //
        // else if (bankuai.equals("style=103")) {
        // tv1.setText("完成【简约通勤】浏览~");
        // }
        //
        // else if (bankuai.equals("style=112")) {
        // tv1.setText("完成【运动休闲】浏览~");
        // }
        //
        // else if (bankuai.equals("style=750")) {
        // tv1.setText("完成【经典百搭】浏览~");
        // }
        //
        // else if (bankuai.equals("style=102")) {
        // tv1.setText("完成【文艺复古】浏览~");
        // }
        //
        // else if (bankuai.equals("occasion=24")) {
        // tv1.setText("完成【上班族必备】浏览~");
        // } else {
        // tv1.setText("完成浏览任务~");
        // }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gobuy1: // 去购物

                if (jumpFrom.equals("setLikeCoplete")) {
                    shareCompleteCallBack.clickNext();
                } else {

                    // if (jumpFrom.equals("bankuailiulanwancheng")) {// 浏览分钟shu 完成
                    // dian击
                    // 买买买
                    CommonUtils.finishActivity(MainMenuActivity.instances);

                    Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
                    intent2.putExtra("toYf", "toYf");
                    context.startActivity(intent2);
                    // }
                }


                break;

            case R.id.gobuy2:

                if (SignFragment.signIsShow) {
                    shareCompleteCallBack.clickNext();
                } else {




                    if(null != CommonActivity.instance){
                        CommonActivity.instance.finish();
                    }

                    if(null != SweetFriendsDetails.instance){
                        SweetFriendsDetails.instance.finish();
                    }

                    // 跳至赚钱

                    SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");

                    Intent intent = new Intent(context, CommonActivity.class);
                    intent.putExtra("isTastComplete", true);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                }

                dismiss();
                break;

            case R.id.liebiao:



                dismiss();

                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}

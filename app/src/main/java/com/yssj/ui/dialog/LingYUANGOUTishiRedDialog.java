package com.yssj.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.main.ForceLookActivity;
import com.yssj.ui.activity.main.ForceLookMadActivity;
import com.yssj.ui.activity.main.ForceLookMatchActivity;
import com.yssj.ui.activity.main.SignActiveShopActivity;
import com.yssj.ui.fragment.circles.SignFragment;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class LingYUANGOUTishiRedDialog extends Dialog implements View.OnClickListener {
    private TextView tv2, tv3, tv4;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    private String luodiye = "-1";


    public LingYUANGOUTishiRedDialog(Context context, int style, String luodiye) {
        super(context, style);
        this.context = context;
        this.luodiye = luodiye;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lingyuangou_tishi);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);


//        tv_ketixian.setText(Html.fromHtml("可提现 <b>0.00</b>"));


//        textView1.setText(Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩"));


//        tv2.setText(Html.fromHtml("1、<font color='#FDCC21'>购买美衣立即返全部金额入账户余额</font>，还能抽取最高1000元提现红包大奖。"));
//
//
//        tv3.setText(Html.fromHtml("2、1-3个月内每日登录衣蝠并完成全部任务，<font color='#FDCC21'>购买美衣立即返全部金额入账户余额 </font>。相当于在衣蝠买美衣永远白送。"));
//        tv4.setText(Html.fromHtml("3、如3个月未能全额提现，且用户每日登陆衣蝠并完成全部任务，<font color='#FDCC21'>平台将按首月返10%，次月返20%，第三个月返30%的比例</font>把首单购衣款打入提现额度。48小时内到账！"));




        tv2.setText(Html.fromHtml("1、<font color='#FDCC21'>购买美衣立即返全部金额入账户余额</font>，还能抽取最高1000元提现红包大奖。"));
        tv3.setText( Html.fromHtml("2、1-3个月内每日登录衣蝠并完成全部任务，即可<font color='#FDCC21'>通过惊喜提现任务及抽奖任务全额提现</font>。相当于在衣蝠买美衣永远白送。"));
        tv4.setText(Html.fromHtml("3、如3个月未能全额提现，且用户每日登陆衣蝠并完成全部任务，<font color='#FDCC21'>平台将按首月返10%，次月返20%，第三个月返30%的比例</font>把首单购衣款打入提现额度。48小时内到账！"));



        gobuy2 = (Button) findViewById(R.id.gobuy2);
        liebiao = (Button) findViewById(R.id.liebiao);
        icon_close = (ImageView) findViewById(R.id.icon_close);


        gobuy2.setOnClickListener(this);
        liebiao.setOnClickListener(this);
        icon_close.setOnClickListener(this);


    }


    /**
     * 设置未成功时候 赚钱提醒文案
     */


    public static int zidongGundongY = 0;

    @Override
    public void onClick(View v) {
        Intent intent2;
        switch (v.getId()) {
            case R.id.liebiao: //我去逛逛

                intent2 = new Intent((Activity) context, MainMenuActivity.class);
                intent2.putExtra("toYf", "toYf");
                context.startActivity(intent2);


                break;
            case R.id.gobuy2: //立即0元购美衣

                if ("-1".equals(luodiye)) {
                    Intent intent = new Intent(context, ForceLookMatchActivity.class);
                    intent.putExtra("isCrazy", true);
                    intent.putExtra("type", "2");
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                } else {


                    String where = "type_name=热卖&notType=true";
                    try {
                        where = luodiye;
                    } catch (Exception e) {
                    }

                    if (where.equals("collection=shopping_page")) {// 购物
                        // 跳至购物
                        intent2 = new Intent((Activity) context, MainMenuActivity.class);
                        intent2.putExtra("toShop", "toShop");
                        context.startActivity(intent2);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=collocation_shop")) {// 搭配

                        Intent intent = new Intent(context, ForceLookMatchActivity.class);
                        intent.putExtra("type", "1");

                        intent.putExtra("isCrazy", true);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);


                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_activity")) {// 活动

                        // 跳转到活动商品

                        Intent intent = new Intent(context, SignActiveShopActivity.class);
                        intent.putExtra("isCrazy", true);


                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=csss_shop")) {// 专题
                        Intent intent = new Intent(context, ForceLookMatchActivity.class);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        intent.putExtra("type", "2");
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    } else if (where.equals("type_name=热卖&notType=true")) {// 热卖

                        Intent intent = new Intent(context, ForceLookMadActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "热卖");
                        intent.putExtra("doIconId", SignListAdapter.doIconId);


                        intent.putExtra("isCrazy", true);

                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else if (where.equals("collection=shop_home")) {// 首页

                        // 跳至首页
                        intent2 = new Intent((Activity) context, MainMenuActivity.class);
                        intent2.putExtra("toHome", "toHome");
                        context.startActivity(intent2);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    } else { // 找不到的话还是用热卖

                        Intent intent = new Intent(context, ForceLookMadActivity.class);
                        intent.putExtra("id", "6");
                        intent.putExtra("title", "热卖");
                        intent.putExtra("isCrazy", true);

                        intent.putExtra("doIconId", SignListAdapter.doIconId);


                        intent.putExtra("isSignLiulan", true);
                        intent.putExtra("isGaoMai", true);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    }


                }


                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

}

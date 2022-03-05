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
import com.yssj.ui.activity.main.ForceLookMatchActivity;

/**
 * 赚钱提示
 *
 * @author qingfeng
 */
public class LingYUANGOUTishiDialog extends Dialog implements View.OnClickListener {
    private TextView tv2, tv3, tv4;
    private Button gobuy2, liebiao;
    private Context context;
    private ImageView icon_close;
    private boolean isShopCilick;
    ShopDetialClickCallBack shopDetialClickCallBack;

    public LingYUANGOUTishiDialog(Context context, int style) {
        super(context, style);
        this.context = context;


    }


    public LingYUANGOUTishiDialog(Context context, int style,boolean isShopCilick ,ShopDetialClickCallBack shopDetialClickCallBack) {
        super(context, style);
        this.context = context;
        this.isShopCilick = isShopCilick;
        this.shopDetialClickCallBack = shopDetialClickCallBack;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lingyuangou_tishi_white);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);


//        tv_ketixian.setText(Html.fromHtml("可提现 <b>0.00</b>"));


//        textView1.setText(Html.fromHtml("北京市发布霾黄色预警，<font color='#ff0000'><big><big>外出携带好</big></big></font>口罩"));


//        tv2.setText(Html.fromHtml("1、<font color='#ff0000'>购买美衣立即返全部金额入账户余额</font>，还能抽取最高1000元提现红包大奖。"));


//        tv3.setText( Html.fromHtml("2、1-3个月内每日登录衣蝠并完成全部任务，<font color='#ff0000'>购买美衣立即返全部金额入账户余额 </font>。相当于在衣蝠买美衣永远白送。"));
        tv2.setText(Html.fromHtml("1、<font color='#ff3f8b'>购买美衣立即返全部金额入账户余额</font>，还能抽取最高1000元提现红包大奖。"));
        tv3.setText( Html.fromHtml("2、1-3个月内每日登录衣蝠并完成全部任务，即可<font color='#ff3f8b'>通过惊喜提现任务及抽奖任务全额提现</font>。相当于在衣蝠买美衣永远白送"));
        tv4.setText(Html.fromHtml("3、如3个月未能全额提现，且用户每日登陆衣蝠并完成全部任务，<font color='#ff3f8b'>平台将按首月返10%，次月返20%，第三个月返30%的比例</font>把首单购衣款打入提现额度。48小时内到账"));


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

                if(isShopCilick){
                    shopDetialClickCallBack.goumaiClick();
                }else{
                    Intent intent = new Intent(context, ForceLookMatchActivity.class);
                    intent.putExtra("isCrazy", true);
                    intent.putExtra("type", "2");
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                }






                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

    public interface ShopDetialClickCallBack {
        void goumaiClick();
    }

}

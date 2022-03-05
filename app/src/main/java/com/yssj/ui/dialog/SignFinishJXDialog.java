package com.yssj.ui.dialog;

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
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 精选推荐浏览完成提示
 *
 * @author qingfeng
 */
public class SignFinishJXDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.liebiao)
    Button liebiao;
    private TextView tv1, title;
    private Button gobuy1;
    private Context context;
    private ImageView icon_close;
    private String jianglivalue;
    private int jiangliID;

    public SignFinishJXDialog(Context context, int style, String jiangliValue, int jiangliID) {
        super(context, style);
        this.context = context;
        this.jiangliID = jiangliID;
        this.jianglivalue = jiangliValue;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_jx_finish);


        ButterKnife.bind(this);

        tv1 = (TextView) findViewById(R.id.tv1);
        title = (TextView) findViewById(R.id.title);
        gobuy1 = (Button) findViewById(R.id.gobuy1); // 一个按钮时的按钮
        icon_close = (ImageView) findViewById(R.id.icon_close);
        gobuy1.setOnClickListener(this);
        icon_close.setOnClickListener(this);
        initData();

    }

    private void initData() {

        title.setText("任务完成！");
        tv1.setVisibility(View.VISIBLE);
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        // 奖励可能是积分现金优惠券
        switch (jiangliID) {
            case 3: // 优惠券

                String jianglicontent = jianglivalue + "元优惠券";
                String textorther = jianglicontent + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                SpannableString tttText = new SpannableString(textorther);
                tttText.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, jianglicontent.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv1.setText(tttText);

                break;
            case 4: // 积分

                String jianglicontentjifen = jianglivalue + "积分";
                String textortherjifen = jianglicontentjifen + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                SpannableString tttTextjifen = new SpannableString(textortherjifen);
                tttTextjifen.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, jianglicontentjifen.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv1.setText(tttTextjifen);

                break;
            case 5: // 现金

                String jianglicontentxianjin = jianglivalue + "元";
                String textortherxinjin = jianglicontentxianjin + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                SpannableString tttTextxianjin = new SpannableString(textortherxinjin);
                tttTextxianjin.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0,
                        jianglicontentxianjin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv1.setText(tttTextxianjin);

                break;
            case 11: // 衣豆

                String jianglicontentYidou = jianglivalue + "个衣豆";
                String textortherxinYidou = jianglicontentYidou + "奖励已经存入你的账户，如果喜欢这件美衣就带它回家吧~";
                SpannableString tttTextYidou = new SpannableString(textortherxinYidou);
                tttTextYidou.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, jianglicontentYidou.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv1.setText(tttTextYidou);

                break;

            default:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gobuy1: // 一键做下个任务

                // if (jumpFrom.equals("bankuailiulanwancheng")) {// 浏览分钟shu 完成
                // dian击
                // 买买买
//			Intent intent2 = new Intent((Activity) context, MainMenuActivity.class);
//			intent2.putExtra("toYf", "toYf");
//			context.startActivity(intent2);
                // }


                SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");

                Intent intent = new Intent(context, CommonActivity.class);
                intent.putExtra("isTastComplete", true);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);


                dismiss();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

    @OnClick(R.id.liebiao)
    public void onViewClicked() {


        dismiss();

    }
}

package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/****
 * 获得好友提成提示
 *
 * @author Administrator
 *
 */
public class ShareTCtishiDiaog extends Dialog {


    @Bind(R.id.tv_xj_num)
    TextView xjNumTv;//xx人获得现金

    @Bind(R.id.tv_f_money)
    TextView fMoneyTv;//下级获得的现金,

    @Bind(R.id.tv_ed_num)
    TextView ednumTv;//xx人获得额度

    @Bind(R.id.tv_f_extra)
    TextView fExtraTv;//下级得到额度

    @Bind(R.id.tv_money)
    TextView moneyTv;//我拿到好友奖励 余额

    @Bind(R.id.tv_extra)
    TextView extraTv;//我拿到好友奖励 提现

    private Context mContext;
    @Bind(R.id.icon_close)
    ImageView iconClose;

    private HashMap<String,String> map ;


    public ShareTCtishiDiaog(Context context, HashMap<String,String> map,int style) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.mContext = context;
        this.map = map;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ticheng_tishi);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initData();

    }

    private void initData() {
        xjNumTv.setText(map.get("xj_num"));
        fMoneyTv.setText(map.get("f_money"));
        ednumTv.setText(map.get("ed_num"));
        fExtraTv.setText(map.get("f_extra"));
        moneyTv.setText(map.get("money"));
        extraTv.setText(map.get("extra"));
    }


    @OnClick({R.id.icon_close})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.icon_close:
                dismiss();
                break;
        }
    }


}
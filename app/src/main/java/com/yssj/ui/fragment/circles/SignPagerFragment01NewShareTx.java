package com.yssj.ui.fragment.circles;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasePager;
import com.yssj.utils.StringUtils;


public class SignPagerFragment01NewShareTx extends BasePager {
    //    @Bind(R.id.tv_money_unregist)
    private TextView tvMoneyUnregist;
//    @Bind(R.id.tv_money_regist)

    private View view;
//    private NewShareGetTXDialog dialog;

    public SignPagerFragment01NewShareTx(Context context ){
        super(context);
//        this.dialog = dialog;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View initView() {
        view = ((Activity) context).getLayoutInflater().inflate(R.layout.sign_viewpager01_newtx, null);
        StringUtils.initShareText(
                (TextView) view.findViewById(R.id.tv1),
                (TextView) view.findViewById(R.id.tv2),
                (TextView) view.findViewById(R.id.tv3),
                (TextView) view.findViewById(R.id.tv4)
        );
        return view;
    }

    @Override
    public void initData() {
    }


}

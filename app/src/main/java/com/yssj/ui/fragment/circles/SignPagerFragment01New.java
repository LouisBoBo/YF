package com.yssj.ui.fragment.circles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.NewShareGetTXDialog;
import com.yssj.ui.dialog.SignShareShopDialog;
import com.yssj.utils.StringUtils;

import java.util.HashMap;


public class SignPagerFragment01New extends BasePager {
    //    @Bind(R.id.tv_money_unregist)
    private TextView tvMoneyUnregist;
//    @Bind(R.id.tv_money_regist)

    private View view;
//    private NewShareGetTXDialog dialog;

    public SignPagerFragment01New(Context context ){
        super(context);
//        this.dialog = dialog;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View initView() {
        view = ((Activity) context).getLayoutInflater().inflate(R.layout.sign_viewpager01_new, null);
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

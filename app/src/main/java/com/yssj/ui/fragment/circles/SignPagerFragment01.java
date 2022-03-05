package com.yssj.ui.fragment.circles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.ui.activity.PointLikeRankingActivity;
import com.yssj.ui.base.BasePager;
import com.yssj.ui.dialog.SignShareShopDialog;
import com.yssj.utils.ToastUtil;

import java.util.HashMap;


public class SignPagerFragment01 extends BasePager {
//    @Bind(R.id.tv_money_unregist)
    private TextView tvMoneyUnregist;
//    @Bind(R.id.tv_money_regist)
  private   TextView tvMoneyRegist;
    private TextView tv_liaojie_more;
    private View view;
    private SignShareShopDialog dialog;

    public SignPagerFragment01(Context context, SignShareShopDialog dialog) {
        super(context);
        this.dialog = dialog;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View initView() {
        view = ((Activity) context).getLayoutInflater().inflate(R.layout.sign_viewpager01, null);
        tv_liaojie_more = (TextView) view.findViewById(R.id.tv_liaojie_more);
        tvMoneyRegist= (TextView) view.findViewById(R.id.tv_money_regist);
        tvMoneyUnregist= (TextView) view.findViewById(R.id.tv_money_unregist);
        tv_liaojie_more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//			dialog.dismiss();
                context.startActivity(new Intent(context, PointLikeRankingActivity.class));
                ((Activity) context).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
            }
        });
      getMoney();
        return view;

    }

    @Override
    public void initData() {
        getMoney();
    }
private void getMoney(){
    new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, null) {

        @Override
        protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params) throws Exception {
//            return ComModel2.getPointGuiZeContent(context);

            return ComModelL.getContentTextJZJL(YConstance.KeyJT.KEY_JSONTEXT_JZJL);
        }

        @Override
        protected boolean isHandleException() {
            return true;
        }

        @Override
        protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
            super.onPostExecute(context, result, e);
            if (null == e && result != null) {
                String n2 = result.get("n2");
                String n3 = result.get("n3");
                if (!TextUtils.isEmpty(n2)) {
                    String text2 = tvMoneyUnregist.getText().toString();
                    text2 = text2.replace("2元", n2);
                    tvMoneyUnregist.setText(text2);
                }
                if (!TextUtils.isEmpty(n3)) {
                    String text3 = tvMoneyRegist.getText().toString();
                    text3 = text3.replace("0.2元", n3);
                    tvMoneyRegist.setText(text3);
                }
            }
        }

    }.execute();
}

}

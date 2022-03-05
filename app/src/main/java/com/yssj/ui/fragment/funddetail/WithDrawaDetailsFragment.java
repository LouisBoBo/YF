package com.yssj.ui.fragment.funddetail;

import java.sql.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.ToastUtil;

public class WithDrawaDetailsFragment extends BaseFragment {

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private ImageView img_line1, img_line2;
    private Button btn_status, bt_re_submit;

    private TextView tv_withdraw_sum, tv_bank_card, tv_submit_time,
            tv_submit_status, tv_begin_img, tv_handler_img, tv_end_img,
            tv_FailureCause;

    private String business_code;//是否是订单列表进来的 如果是需要调接口拿数据

    @Override
    public View initView() {

        view = View.inflate(context,
                R.layout.activity_mywalet_withdaraw_detail, null);
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("提现详情");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        tv_begin_img = (TextView) view.findViewById(R.id.tv_begin_img);
        img_line1 = (ImageView) view.findViewById(R.id.img_line1);
        tv_handler_img = (TextView) view.findViewById(R.id.tv_handler_img);
        img_line2 = (ImageView) view.findViewById(R.id.img_line2);
        tv_end_img = (TextView) view.findViewById(R.id.tv_end_img);

        btn_status = (Button) view.findViewById(R.id.btn_status);

        tv_withdraw_sum = (TextView) view.findViewById(R.id.tv_withdraw_sum);
        tv_bank_card = (TextView) view.findViewById(R.id.tv_bank_card);
        tv_submit_time = (TextView) view.findViewById(R.id.tv_submit_time);
        tv_submit_status = (TextView) view.findViewById(R.id.tv_submit_status);

        bt_re_submit = (Button) view.findViewById(R.id.bt_re_submit);
        bt_re_submit.setOnClickListener(this);

        tv_FailureCause = (TextView) view.findViewById(R.id.tv_FailureCause);

        return view;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        HashMap<String, Object> map = (HashMap<String, Object>) bundle
                .getSerializable("map");

        business_code = bundle.getString("business_code", null);

        if (null != business_code) {//从订单列表过来的需要查询

            new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

                @Override
                protected boolean isHandleException() {
                    return true;
                }

                @Override
                protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params) throws Exception {
                    return ComModel2.queryTiXianDetail(context,business_code);
                }

                @Override
                protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> result, Exception e) {
                    if (null == e) {
                        showData(result);
                    }

                    super.onPostExecute(context, result, e);
                }

            }.execute();


        } else {
            showData(map);

        }


    }

    private void showData(HashMap<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            tv_bank_card.setText(map.get("collect_bank_name").toString() + "**"
                    + map.get("collect_bank_code").toString());
            tv_withdraw_sum.setText(Float.parseFloat(map.get("money")
                    .toString()) + "元");
//            tv_submit_time.setText(DateFormatUtils.format(
//                    new Date(Long.parseLong(map.get("add_date").toString())),
//                    "yyyy-MM-dd HH:mm:ss"));
            tv_submit_time.setText(map.get("add_date")+"");

            String checkCode = map.get("check").toString();
            if ("0".equals(checkCode)) {
                tv_submit_status.setText("待审核");
            } else if ("1".equals(checkCode)) {
                tv_submit_status.setText("通过");
            } else if ("2".equals(checkCode)) {
                tv_submit_status.setText("不通过");
                tv_FailureCause.setVisibility(View.VISIBLE);
                tv_FailureCause.setText("原因: "
                        + map.get("transfer_error").toString());

            } else if ("3".equals(checkCode)) {
                tv_submit_status.setText("提现成功");
            } else if ("4".equals(checkCode)) {
                tv_submit_status.setText("审核已通过");
            } else if ("6".equals(checkCode)) {
                tv_submit_status.setText("提现已发起");
            } else if ("7".equals(checkCode)) {
                tv_submit_status.setText("提现已提交至开户行");
            } else if ("8".equals(checkCode)) {
                tv_submit_status.setText("开户行发放中，预计1个工作日内到账");
            } else if ("9".equals(checkCode)) {
                tv_submit_status.setText("开户行发放中，预计1个工作日内到账");
            } else if ("10".equals(checkCode)) {
                tv_submit_status.setText("提现成功");
            } else if ("12".equals(checkCode)) {
                tv_submit_status.setText("已重新申请");
            } else if ("11".equals(checkCode)) {
                tv_submit_status.setText("转账失败");
                tv_FailureCause.setVisibility(View.VISIBLE);
                bt_re_submit.setVisibility(View.VISIBLE);
                tv_FailureCause.setText("原因: "
                        + map.get("transfer_error").toString());
            } else {
                tv_submit_status.setText("未知的状态");
            }

            // 这里添加状态 ----提现失败 ----- 显示失败原因 和重新申请按钮

            // if("0".equals(checkCode)){
            // tv_submit_status.setText("处理中");
            // btn_status.setText("处理中");
            // }else if("1".equals(checkCode)){
            // tv_submit_status.setText("处理中");
            // btn_status.setText("处理中");
            // img_line1.setImageResource(R.drawable.withdraw_pink_line);
            // tv_handler_img.setBackgroundResource(R.drawable.withdraw_pinkcolor_icon);
            // }else
            if ("0".equals(checkCode)) {
                btn_status.setText("处理中，预计1-3个工作日到账");
                img_line1.setImageResource(R.drawable.withdraw_gray_line);
                img_line2.setImageResource(R.drawable.withdraw_gray_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
            } else if ("2".equals(checkCode)) {
                btn_status.setText("失败");
                img_line1.setImageResource(R.drawable.withdraw_gray_line);
                img_line2.setImageResource(R.drawable.withdraw_gray_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
            } else if ("3".equals(checkCode)) {
                btn_status.setText("成功");
                btn_status.setTextColor(getResources().getColor(R.color.white));
                img_line1.setImageResource(R.drawable.withdraw_pink_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_pinkcolor_icon);
                img_line2.setImageResource(R.drawable.withdraw_pink_line);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_pinkcolor_icon);
                btn_status
                        .setBackgroundResource(R.drawable.withdrawa_success_icon);
            } else if ("10".equals(checkCode)) {
                btn_status.setText("处理中，预计1-3个工作日到账");
                img_line1.setImageResource(R.drawable.withdraw_pink_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_pinkcolor_icon);
                img_line2.setImageResource(R.drawable.withdraw_gray_line);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
                btn_status
                        .setBackgroundResource(R.drawable.zero_shop_item_postage);
            } else if ("11".equals(checkCode)) {
                btn_status.setText("失败");
                img_line1.setImageResource(R.drawable.withdraw_gray_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
                img_line2.setImageResource(R.drawable.withdraw_gray_line);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
            } else {//其他情况都是处理中
//				btn_status.setText("处理中，预计1-3个工作日到账");
//				img_line1.setImageResource(R.drawable.withdraw_pink_line);
//				tv_handler_img
//						.setBackgroundResource(R.drawable.withdraw_pinkcolor_icon);
//				img_line2.setImageResource(R.drawable.withdraw_gray_line);
//				tv_end_img
//						.setBackgroundResource(R.drawable.withdraw_graycolor_icon);


                btn_status.setText("处理中，预计1-3个工作日到账");
                img_line1.setImageResource(R.drawable.withdraw_gray_line);
                img_line2.setImageResource(R.drawable.withdraw_gray_line);
                tv_handler_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
                tv_end_img
                        .setBackgroundResource(R.drawable.withdraw_graycolor_icon);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.bt_re_submit:

                Intent intent = new Intent(context,
                        MyWalletCommonFragmentActivity.class);
                intent.putExtra("flag", "withDrawalFragment");
                // intent.putExtra("balance",
                // MyWalletCommonFragmentActivity.balance);
                intent.putExtra("alliance", "wallet");
                startActivity(intent);

                break;

            default:
                break;
        }
    }

}

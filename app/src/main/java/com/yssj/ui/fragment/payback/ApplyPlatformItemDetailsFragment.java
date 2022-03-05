package com.yssj.ui.fragment.payback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.WXminiAppUtil;

public class ApplyPlatformItemDetailsFragment extends BaseFragment {
	private ReturnShop returnShop;
	private LinearLayout img_back;
	private TextView mTitle;
	private TextView mMoney;
	private TextView mNums;
	private TextView mTimes;
	private ImageView img_right_icon;
	private TextView apply_result;
	private TextView apply_message1;
private TextView mRefuseMsg;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			getActivity().finish();

			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		default:
			break;
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			int returnShopType = returnShop.getReturn_type();
			switch (returnShopType) {
			case 1:
				apply_message1.setText("申请换货");
				mTitle.setText("换货详情");
				break;
			case 2:
				apply_message1.setText("申请退货");
				mTitle.setText("退货详情");
				break;
			case 3:
				apply_message1.setText("申请退款");
				mTitle.setText("退款详情");
				break;
			}
			switch (returnShop.getYs_intervene()) {
			case 3:
				apply_result.setText("交易成功");
				break;
			case 4:
				switch (returnShopType) {
				case 1:
					apply_result.setText("换货成功");
					break;
				case 2:
					apply_result.setText("退货成功");
					break;
				case 3:
					apply_result.setText("退款成功");
					break;
				}

				break;
			case 5:
				apply_result.setText("售后关闭");
				break;
			case 6:
				apply_result.setText("售后关闭");
				break;
			default:
				apply_result.setText("此订单平台已接入处理纠纷,请耐心等候");
				break;
			}
			if (returnShop != null) {
				mRefuseMsg.setText("拒绝理由："+returnShop.getSupp_refuse_msg());
				mMoney.setText("订单金额 ¥" + returnShop.getShop_price());
				mNums.setText("订单号:" + returnShop.getOrder_code());
				String time = returnShop.getAdd_time() + "";
//				mTimes.setText("退货时间:" + returnShop.getAdd_time());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss Z yyyy", new Locale("ENGLISH",
								"CHINA"));

				Date myDate;
				try {
					myDate = sdf.parse(time);
					SimpleDateFormat sdf2 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", new Locale("CHINESE",
									"CHINA"));

					mTimes.setText("退货时间:" + sdf2.format(myDate));

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		img_back.setOnClickListener(this);
	}

	@Override
	public View initView() {
		view = View.inflate(context,
				R.layout.activity_payback_detail_seller_not_receive_detail,
				null);
		mTitle = (TextView) view.findViewById(R.id.tvTitle_base);
		view.setBackgroundColor(Color.WHITE);
		apply_message1 = (TextView) view.findViewById(R.id.apply_message1);
		mMoney = (TextView) view.findViewById(R.id.apply_menoy1);
		mNums = (TextView) view.findViewById(R.id.apply_nums1);
		mTimes = (TextView) view.findViewById(R.id.apply_time1);
		mRefuseMsg=(TextView) view.findViewById(R.id.apply_refuse_msg1);
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		apply_result = (TextView) view.findViewById(R.id.apply_result);
		img_right_icon.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}

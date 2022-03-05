package com.yssj.ui.fragment.payback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.WXminiAppUtil;

public class ApplyPlatformItemFragment extends BaseFragment {
	private Button btn_connect_seller;
	private ReturnShop returnShop;
	private LinearLayout img_back;
	private TextView mTitle;
	private TextView mMoney;
	private TextView mNums;
	private TextView mTimes;
	private ImageView img_right_icon;
	private TextView mRefuseMsg;
private TextView apply_message;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_connect_seller:

			Intent intent = new Intent(getActivity(),
					ApplyPlatformActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("order", returnShop);
			intent.putExtras(bundle);
			getActivity().startActivity(intent);

			break;
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
			
			btn_connect_seller.setOnClickListener(this);
			if (returnShop != null) {
				int returnShopType = returnShop.getReturn_type();
				switch (returnShopType) {
				case 1:
					apply_message.setText("申请换货");
					mTitle.setText("换货详情");
					break;
				case 2:
					apply_message.setText("申请退货");
					mTitle.setText("退货详情");
					break;
				case 3:
					apply_message.setText("申请退款");
					mTitle.setText("退款详情");
					break;
				}
				mRefuseMsg.setText("拒绝理由："+returnShop.getSupp_refuse_msg());
				mMoney.setText("订单金额 ¥" + returnShop.getShop_price());
				mNums.setText("订单号:" + returnShop.getOrder_code());
				String time = returnShop.getAdd_time() + "";
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		img_back.setOnClickListener(this);
	}

	@Override
	public View initView() {
		view = View.inflate(context,
				R.layout.activity_payback_detail_seller_not_receive, null);
		mTitle = (TextView) view.findViewById(R.id.tvTitle_base);
		view.setBackgroundColor(Color.WHITE);
		apply_message=(TextView) view.findViewById(R.id.apply_message);
		mMoney = (TextView) view.findViewById(R.id.apply_money);
		mNums = (TextView) view.findViewById(R.id.apply_nums);
		mTimes = (TextView) view.findViewById(R.id.apply_time);
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		mRefuseMsg=(TextView) view.findViewById(R.id.apply_refuse_msg);
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);

		btn_connect_seller = (Button) view
				.findViewById(R.id.btn_connect_seller);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}

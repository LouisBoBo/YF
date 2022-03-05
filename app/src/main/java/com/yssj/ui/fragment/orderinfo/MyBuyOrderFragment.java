package com.yssj.ui.fragment.orderinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.VipInfo;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MyBuyOrderFragment extends Fragment implements
		OnCheckedChangeListener {

	private int index = 0;
	private static int mtype;
	public  static int pos = -1;

	private RadioGroup rg_stat;
	private RadioButton rb_all, rb_obligation, rb_deliver, rb_receive,
			rb_judge;

	// private OrderInfoFragment orderAll, orderDeliver, orderReceive,
	// orderJudge;
	//
	// private OrderObligationFragment orderObligation;

	private FragmentManager fm;
	private FragmentTransaction ft;
	private boolean hongbaoAnimStart = false;
	private ImageView iv_hongbao;



	public MyBuyOrderFragment() {
		super();

		pos = -1;
		// TODO Auto-generated constructor stub
	}


	private void setHongbaoAnim() {

		if (YJApplication.instance.isLoginSucess()) {
			if(YCache.getCacheUser(getActivity()).getReviewers() == 1){
				iv_hongbao.setVisibility(View.GONE);
				return;
			}
			HashMap<String, String> pairsMap = new HashMap<>();
			YConn.httpPost(getActivity(), YUrl.QUERY_VIP_INFO2, pairsMap
					, new HttpListener<VipInfo>() {
						@Override
						public void onSuccess(VipInfo vipInfo) {
							if (vipInfo.getIsVip() > 0) {
								iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_600);
								setHongBaoAnim();
							}else{
								iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
								setHongBaoAnim();
							}
						}

						@Override
						public void onError() {

						}
					});
		}else{
			iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
			setHongBaoAnim();
		}


	}

	private void setHongBaoAnim() {
		if (hongbaoAnimStart) {
			return;
		}

		ObjectAnimator animatorBigX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1, 1.4f);

		animatorBigX.setDuration(800);

		ObjectAnimator animatorBigY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1, 1.4f);

		animatorBigY.setDuration(800);


		ObjectAnimator animRot = ObjectAnimator.ofFloat(iv_hongbao, "rotation", 0f, -30f, 0f, 30f, 0);
		animRot.setDuration(800);


		ObjectAnimator animatorSmallX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1.4f, 1);
		animatorSmallX.setDuration(800);

		ObjectAnimator animatorSmallY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1.4f, 1);
		animatorSmallY.setDuration(800);

		AnimatorSet.Builder buildBigX = animatorSet.play(animatorBigX);
		buildBigX.with(animatorBigY);//一起放大


		AnimatorSet.Builder buildSmallX = animatorSet.play(animatorSmallX);
		buildSmallX.with(animatorSmallY);//一起缩小


		//放大后旋转
		buildBigX.before(animRot);


		//旋转后缩小
		buildSmallX.after(animRot);

		hongbaoAnimStart = true;
		//循环播放
		handler.postDelayed(runnable, 100);
	}



	AnimatorSet animatorSet = new AnimatorSet();

	Handler handler = new Handler();

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			animatorSet.start();

			handler.postDelayed(this, 2600);
		}
	};


	@SuppressLint("ValidFragment")
	public MyBuyOrderFragment(int index) {
		this.index = index;

		pos = -1;

		LogYiFu.e("TAG", "index="+this.index);
		
	}

	private List<Fragment> fragList = new ArrayList<Fragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		index = getActivity().getIntent().getIntExtra("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.status_info, container, false);
		findViewById(view);

		fm = this.getChildFragmentManager();
		LogYiFu.e("index", index + ",pos="+pos);
		if(pos!=-1){
			switch (pos) {
			case 0:
				rb_all.setChecked(true);
				break;
			case 1:
				rb_obligation.setChecked(true);
				break;
			case 2:
				rb_deliver.setChecked(true);
				break;
			case 3:
				rb_receive.setChecked(true);
				break;
			case 4:
				rb_judge.setChecked(true);
				break;

			default:
				break;
			}
			return view;
		}
		
		switch (index) {
		case 0:
			rb_all.setChecked(true);
			break;
		case 1:
			rb_obligation.setChecked(true);
			break;
		case 2:
			rb_deliver.setChecked(true);
			break;
		case 3:
			rb_receive.setChecked(true);
			break;
		case 4:
			rb_judge.setChecked(true);
			break;

		default:
			break;
		}
		return view;
	}


	@Override
	public void onResume() {
		super.onResume();
		setHongbaoAnim();
	}


	private void findViewById(View view) {
		rg_stat = (RadioGroup) view.findViewById(R.id.rg_stat);
		rg_stat.setOnCheckedChangeListener(this);
		rb_all = (RadioButton) view.findViewById(R.id.rb_all);
		rb_obligation = (RadioButton) view.findViewById(R.id.rb_obligation);
		rb_deliver = (RadioButton) view.findViewById(R.id.rb_deliver);
		rb_receive = (RadioButton) view.findViewById(R.id.rb_receive);
		rb_judge = (RadioButton) view.findViewById(R.id.rb_judge);

		iv_hongbao = view.findViewById(R.id.iv_hongbao);
		iv_hongbao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferencesUtil.saveStringData(getActivity(), "commonactivityfrom", "sign");
				startActivity(new Intent(getActivity(), CommonActivity.class));
				getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			}
		});
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		ft = fm.beginTransaction();
		switch (arg1) {
		case R.id.rb_all: //全部
			// ft.show(orderAll);
			// ft.hide(orderObligation);
			// ft.hide(orderDeliver);
			// ft.hide(orderReceive);
			// ft.hide(orderJudge);
			pos = 0;
			ft.replace(R.id.content_container, new OrderInfoFragment(0));
			ft.commitAllowingStateLoss();
			break;
		case R.id.rb_obligation://待付款
			// ft.hide(orderAll);
			// ft.show(orderObligation);
			// ft.hide(orderDeliver);
			// ft.hide(orderReceive);
			// ft.hide(orderJudge);
			pos = 1;
			ft.replace(R.id.content_container, new OrderObligationFragment(1));
			ft.commitAllowingStateLoss();
			break;
		case R.id.rb_deliver://待发货-改为待疯抢
			// ft.hide(orderAll);
			// ft.hide(orderObligation);
			// ft.show(orderDeliver);
			// ft.hide(orderReceive);
			// ft.hide(orderJudge);
			pos = 2;
			ft.replace(R.id.content_container, new OrderInfoFragment(2));
			ft.commitAllowingStateLoss();
			break;
		case R.id.rb_receive://待收货
			// ft.hide(orderAll);
			// ft.hide(orderObligation);
			// ft.hide(orderDeliver);
			// ft.show(orderReceive);
			// ft.hide(orderJudge);
			pos = 3;
			ft.replace(R.id.content_container, new OrderInfoFragment(3));
			ft.commitAllowingStateLoss();
			break;//待评价
		case R.id.rb_judge:
			// ft.hide(orderAll);
			// ft.hide(orderObligation);
			// ft.hide(orderDeliver);
			// ft.hide(orderReceive);
			// ft.show(orderJudge);
			pos = 4;
			ft.replace(R.id.content_container, new OrderInfoFragment(4));
			ft.commitAllowingStateLoss();
			break;

		default:
			break;
		}

	}
}

//package com.yssj.ui.activity.league;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.yssj.activity.R;
//import com.yssj.utils.LogYiFu;
//
//public class MyBuyOrderFragment extends Fragment implements
//		OnCheckedChangeListener {
//
//	private int index = 0;
//	private static int mtype;
//	public  static int pos = -1;
//
//	private RadioGroup rg_stat;
//	private RadioButton rb_all, rb_obligation, rb_deliver, rb_receive,
//			rb_judge;
//
//	// private OrderInfoFragment orderAll, orderDeliver, orderReceive,
//	// orderJudge;
//	//
//	// private OrderObligationFragment orderObligation;
//
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//
//	private String user_id;
//
//	public MyBuyOrderFragment() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public MyBuyOrderFragment(int index, String user_id) {
//		this.index = index;
//		LogYiFu.e("TAG", "index="+this.index);
//		this.user_id = user_id;
//	}
//
//	private List<Fragment> fragList = new ArrayList<Fragment>();
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		index = getActivity().getIntent().getIntExtra("index", 0);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.status_info, container, false);
//		findViewById(view);
//
//		fm = this.getChildFragmentManager();
//		LogYiFu.e("index", index + ",pos="+pos);
//		if(pos!=-1){
//			switch (pos) {
//			case 0:
//				rb_all.setChecked(true);
//				break;
//			case 1:
//				rb_obligation.setChecked(true);
//				break;
//			case 2:
//				rb_deliver.setChecked(true);
//				break;
//			case 3:
//				rb_receive.setChecked(true);
//				break;
//			case 4:
//				rb_judge.setChecked(true);
//				break;
//
//			default:
//				break;
//			}
//			return view;
//		}
//
//		switch (index) {
//		case 0:
//			rb_all.setChecked(true);
//			break;
//		case 1:
//			rb_obligation.setChecked(true);
//			break;
//		case 2:
//			rb_deliver.setChecked(true);
//			break;
//		case 3:
//			rb_receive.setChecked(true);
//			break;
//		case 4:
//			rb_judge.setChecked(true);
//			break;
//
//		default:
//			break;
//		}
//		return view;
//	}
//
//	private void findViewById(View view) {
//		rg_stat = (RadioGroup) view.findViewById(R.id.rg_stat);
//		rg_stat.setOnCheckedChangeListener(this);
//		rb_all = (RadioButton) view.findViewById(R.id.rb_all);
//		rb_obligation = (RadioButton) view.findViewById(R.id.rb_obligation);
//		rb_deliver = (RadioButton) view.findViewById(R.id.rb_deliver);
//		rb_receive = (RadioButton) view.findViewById(R.id.rb_receive);
//		rb_judge = (RadioButton) view.findViewById(R.id.rb_judge);
//	}
//
//	@Override
//	public void onCheckedChanged(RadioGroup arg0, int arg1) {
//		// TODO Auto-generated method stub
//		ft = fm.beginTransaction();
//		switch (arg1) {
//		case R.id.rb_all:
//			// ft.show(orderAll);
//			// ft.hide(orderObligation);
//			// ft.hide(orderDeliver);
//			// ft.hide(orderReceive);
//			// ft.hide(orderJudge);
//			pos = 0;
//			ft.replace(R.id.content_container, new OrderInfoFragment(0, user_id));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_obligation:
//			// ft.hide(orderAll);
//			// ft.show(orderObligation);
//			// ft.hide(orderDeliver);
//			// ft.hide(orderReceive);
//			// ft.hide(orderJudge);
//			pos = 1;
//			ft.replace(R.id.content_container, new OrderInfoFragment(1, user_id));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_deliver:
//			// ft.hide(orderAll);
//			// ft.hide(orderObligation);
//			// ft.show(orderDeliver);
//			// ft.hide(orderReceive);
//			// ft.hide(orderJudge);
//			pos = 2;
//			ft.replace(R.id.content_container, new OrderInfoFragment(2, user_id));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_receive:
//			// ft.hide(orderAll);
//			// ft.hide(orderObligation);
//			// ft.hide(orderDeliver);
//			// ft.show(orderReceive);
//			// ft.hide(orderJudge);
//			pos = 3;
//			ft.replace(R.id.content_container, new OrderInfoFragment(3, user_id));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_judge:
//			// ft.hide(orderAll);
//			// ft.hide(orderObligation);
//			// ft.hide(orderDeliver);
//			// ft.hide(orderReceive);
//			// ft.show(orderJudge);
//			pos = 4;
//			ft.replace(R.id.content_container, new OrderInfoFragment(4, user_id));
//			ft.commitAllowingStateLoss();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//}

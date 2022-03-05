//package com.yssj.ui.fragment.orderinfo;
//
//import com.yssj.activity.R;
//import com.yssj.utils.LogYiFu;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//public class MySellOrderFragment extends Fragment implements
//		OnCheckedChangeListener {
//
//	private int index = 0;
//	private static int mtype = 0;
//
//	private RadioGroup rg_stat;
//	private RadioButton rb_all, rb_obligation, rb_deliver, rb_receive,
//			rb_judge;
//
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//
////	private MySellOrderItemFragment orderItemAll, orderItemUnpay,
////			orderItemUnReceive, orderItemReceived, orderItemUnComment;
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
//		View view = inflater.inflate(R.layout.my_sell_status_info, container,
//				false);
//		findViewById(view);
//
//		fm = this.getChildFragmentManager();
////		ft = fm.beginTransaction();
////		orderItemAll = new MySellOrderItemFragment(0);
////		orderItemUnpay = new MySellOrderItemFragment(1);
////		orderItemUnReceive = new MySellOrderItemFragment(2);
////		orderItemReceived = new MySellOrderItemFragment(3);
////		orderItemUnComment = new MySellOrderItemFragment(4);
////		ft.add(R.id.content_container, orderItemAll);
////		ft.add(R.id.content_container, orderItemUnpay);
////		ft.add(R.id.content_container, orderItemUnReceive);
////		ft.add(R.id.content_container, orderItemReceived);
////		ft.add(R.id.content_container, orderItemUnComment);
////		ft.commitAllowingStateLoss();
//		LogYiFu.e("index", index + "");
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
////			ft.show(orderItemAll);
////			ft.hide(orderItemUnpay);
////			ft.hide(orderItemUnReceive);
////			ft.hide(orderItemReceived);
////			ft.hide(orderItemUnComment);
//			ft.replace(R.id.content_container, new MySellOrderItemFragment(0));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_obligation:
////			ft.hide(orderItemAll);
////			ft.show(orderItemUnpay);
////			ft.hide(orderItemUnReceive);
////			ft.hide(orderItemReceived);
////			ft.hide(orderItemUnComment);
//			ft.replace(R.id.content_container, new MySellOrderItemFragment(1));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_deliver:
////			ft.hide(orderItemAll);
////			ft.hide(orderItemUnpay);
////			ft.show(orderItemUnReceive);
////			ft.hide(orderItemReceived);
////			ft.hide(orderItemUnComment);
//			ft.replace(R.id.content_container, new MySellOrderItemFragment(2));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_receive:
////			ft.hide(orderItemAll);
////			ft.hide(orderItemUnpay);
////			ft.hide(orderItemUnReceive);
////			ft.show(orderItemReceived);
////			ft.hide(orderItemUnComment);
//			ft.replace(R.id.content_container, new MySellOrderItemFragment(3));
//			ft.commitAllowingStateLoss();
//			break;
//		case R.id.rb_judge:
////			ft.hide(orderItemAll);
////			ft.hide(orderItemUnpay);
////			ft.hide(orderItemUnReceive);
////			ft.hide(orderItemReceived);
////			ft.show(orderItemUnComment);
//			ft.replace(R.id.content_container, new MySellOrderItemFragment(4));
//			ft.commitAllowingStateLoss();
//			break;
//
//		default:
//			break;
//		}
//	}
//}

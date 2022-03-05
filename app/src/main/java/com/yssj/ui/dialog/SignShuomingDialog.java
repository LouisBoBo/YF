package com.yssj.ui.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.YConn;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.utils.GetShopCart;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

/****
 * 赚钱任务说明
 * 
 * @author Administrator
 * 
 */
public class SignShuomingDialog extends Dialog implements OnClickListener {
	private ImageView icon_close;
	private Context context;
	private Button bt1, bt2;
	public static HashMap<String, String> userGradeTable; // 存放用户等级对照表

	public SignShuomingDialog(Context context, int style) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_sign_explain);
		// GetShopCart.querShopCart(context,1);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		icon_close = (ImageView) findViewById(R.id.icon_close);
		com.yssj.custom.view.VerticalTextView vt =  findViewById(R.id.vt);

		TextView tv1 = findViewById(R.id.tv1);
		TextView tv2 = findViewById(R.id.tv2);
		TextView tv3 = findViewById(R.id.tv3);




		Spanned tv1Str = Html.fromHtml("完成赚钱小任务可再去提现<font color='#FF0000'><strong>10</strong></font>次，保底提现<font color='#FF0000'><strong>5-50元秒到微信零钱</strong></font>！");
		tv1.setText(tv1Str);

		Spanned tv2Str = Html.fromHtml("完成小任务即当日打卡成功。<font color='#FF0000'><strong>新人打卡3天可领66元可提现奖金。</strong></font>");
		tv2.setText(tv2Str);

		Spanned tv3Str = Html.fromHtml("提现后返回，<font color='#FF0000'><strong>小任务会刷新</strong></font>，可继续领任务并提现。");
		tv3.setText(tv3Str);



		vt.setText("会员");
		vt.setTextSize(15);
		vt.setTextColor(Color.parseColor("#ff3f8b"));



		icon_close.setOnClickListener(this);
		findViewById(R.id.bt1).setOnClickListener(this);
		findViewById(R.id.bt2).setOnClickListener(this);

//		Date dt = new Date();
//		SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
//		String riqi = matter1.format(dt);
//
//		LogYiFu.e("当前日期", riqi);

		// int[] a = {1,2,3,4,5};
//
//		List<Integer> a = new ArrayList<Integer>();
//		a.add(1);
//		a.add(2);
//		a.add(3);
//
//		List<Integer> b = new ArrayList<Integer>();
//		b.add(4);
//		b.add(5);
//		b.add(6);
//		b.add(7);
//		b.add(8);
//		b.add(9);
//		List<Integer> c = joinLists(a, b);

	}

	public static List<Integer> joinLists(List<Integer> list1, List<Integer> list2) {
		// 构建结果list，长度为两个入参list长度之和
		List<Integer> list = new ArrayList<Integer>(list1.size() + list2.size());

		// 若list1长度大于list2
		if (list1.size() > list2.size()) {

			// 以list2的长度为限制，开始循环
			for (int i = 0; i < list2.size(); i++) {
				list.add(list1.get(i));
				list.add(list2.get(i));
			}

			// 将list1 中多于list2长度的元素，放入list中
			list.addAll(list1.subList(list2.size(), list1.size()));

		} else if (list1.size() < list2.size()) { // 若list2 的长度大于list1

			// 以list1的长度为限制，开始循环
			for (int i = 0; i < list1.size(); i++) {
				list.add(list1.get(i));
				list.add(list2.get(i));
			}

			// 将list2 中多于list1长度的元素，放入list中
			list.addAll(list2.subList(list1.size(), list2.size()));

		} else { // list1 长度与list2 长度相等

			for (int i = 0; i < list1.size(); i++) {
				list.add(list1.get(i));
				list.add(list2.get(i));
			}
		}

		return list;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			this.dismiss();
			break;
		case R.id.bt1:// 金币
			//
			// new Thread(){
			// public void run() {
			// try {
			// ComModel2.updateJinBI(context);
			// } catch (Exception e) {
			//
			// }
			// };
			// }.start();
			// ToastUtil.showShortText(context, "升级金币成功！");
			break;
		case R.id.bt2:// 金券

			//
			// new Thread(){
			// public void run() {
			// try {
			// ComModel2.updateJinQUAN(context);
			// } catch (Exception e) {
			//
			// }
			// };
			// }.start();
			//
			// ToastUtil.showShortText(context, "升级金券成功！");
			//

			break;
		default:
			break;
		}

	}

}
package com.yssj.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.NoScrollGridView;
import com.yssj.data.YDBHelper;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.adpter.JingxuanTuijianMySelfGridViewAdapter;
import com.yssj.ui.adpter.JingxuanTuijianMySelfGridViewAdapter.CheckCallbackChoice;
import com.yssj.ui.adpter.JingxuanTuijianMySelfGridViewAdapter.CheckChoiceCallback;
import com.yssj.ui.adpter.JingxuanTuijianMySelfGridViewAdapter.ViewHolderStyle;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.ChoicenessDialog;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChoiceMyselfActivity extends BasicActivity implements OnClickListener,
		// OnLayoutChangeListener,
CheckCallbackChoice, CheckChoiceCallback {
	private ArrayList<Integer> isCheckedList = new ArrayList<Integer>(); // 风格被已经选中的
	private NoScrollGridView gv_styles; // 喜爱风格
	private ImageButton imgbtn_left_icon;

	public Context context;

	private YDBHelper helper;
	// 用于提交 ----------最好写三个
	private List<HashMap<String, String>> linkList = new ArrayList<HashMap<String, String>>();
	private  List<HashMap<String, String>> linkStyleList = new ArrayList<HashMap<String, String>>();
	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();

	// Activity最外层的Layout视图
	private View root;
	private TextView tv_sub;
	public static ScrollView sl;
	private HashMap<String, ArrayList<HashMap<String, String>>> allList = new HashMap<String, ArrayList<HashMap<String, String>>>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_my_shangpin);
		helper = new YDBHelper(this);
		this.context = this;
		root = findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		intitView();
		queryData();

	}

	private void intitView() {
		/* 实例化各个控件 */
		gv_styles = (NoScrollGridView) findViewById(R.id.lv);
		gv_styles.setFocusable(false);
		sl = (ScrollView) findViewById(R.id.sl);
		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
		imgbtn_left_icon.setOnClickListener(this);
		tv_sub = (TextView) findViewById(R.id.tv_sub);
		tv_sub.setOnClickListener(this);

	}

	private void queryData() {

		new SAsyncTask<Void, Void, ArrayList<HashMap<String, String>>>((FragmentActivity) context, 0) {
			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ModQingfeng.getJinPintuijianList(context);
			}
			protected boolean isHandleException() {
				return true;
			};
			@Override
			protected void onPostExecute(FragmentActivity context, ArrayList<HashMap<String, String>> result,
					Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					if (result.size() != 0) {
						ArrayList<HashMap<String, String>> arrayListForEveryGridView2 = result;
						for (HashMap<String, String> hashMap : arrayListForEveryGridView2) {
								hashMap.put("isChecked", "0");
						}
						JingxuanTuijianMySelfGridViewAdapter gridViewAdapter2 = new JingxuanTuijianMySelfGridViewAdapter(
								ChoiceMyselfActivity.this.context, arrayListForEveryGridView2, true, true);

						// 监听
						gridViewAdapter2.setStyleListener(ChoiceMyselfActivity.this);
						gridViewAdapter2.setListener(ChoiceMyselfActivity.this);
						gv_styles.setAdapter(gridViewAdapter2);
						gv_styles.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								ViewHolderStyle holder = (ViewHolderStyle) view.getTag();
								holder.cb.toggle();// 让整个item都有点击切换开关的效果
							}
						});
					
					}

				}

			}

		}.execute();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_sub: // 确定
			// 用户未选择
			if (linkStyleList.size() == 0) {
				onBackPressed();
				return;
			}
			// 用户选择超过9件
//			if (linkStyleList.size() > 9) {
//				ToastUtil.showShortText(context, "选择的美衣图片不可以超过9件哦~");
//			}
			ChoicenessDialog.arrayListForEveryGridView2.clear();
			ChoicenessDialog.arrayListForEveryGridView2.addAll(linkStyleList);
			HashMap<String, String> myChoice = new HashMap<String, String>();
			myChoice.put("show_pic", "0");
			ChoicenessDialog.arrayListForEveryGridView2.add(0, myChoice);
			ChoicenessDialog.adapter.notifyDataSetChanged();
			finish();
			LogYiFu.e("linkStyleList", linkStyleList + "");
			break;

		case R.id.imgbtn_left_icon:
			onBackPressed();
			break;

		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
	}

	// "isChecked", "1" 0代表选中，1代表未选中
	@Override
	public void onCheckFGCallback(int positon, boolean isChecked, HashMap<String, String> map,
			JingxuanTuijianMySelfGridViewAdapter mAdapter, boolean isDuoxuan,CheckBox box) {
		if (linkStyleList.size() == 9) {
			return;
		}
		// TODO Auto-generated method stub
		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();
			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckFGCallback(i, false, list.get(i), mAdapter, isDuoxuan, box);
				}
			}
			list.get(positon).put("isChecked", "1");
			if (!linkStyleList.contains(list.get(positon))) {
				linkStyleList.add(list.get(positon));
			}
		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			if (linkStyleList.contains(list.get(positon))) {
				linkStyleList.remove(list.get(positon));
			}
		}
	}

	@Override
	public void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
			JingxuanTuijianMySelfGridViewAdapter mAdapter, boolean isDuoxuan,CheckBox box) {
		if (linkStyleList.size() == 9) {
			ToastUtil.showShortText(context, "最多只能选择9张图片哦~");
			return;
		}
		if (isChecked) {
			box.setBackgroundResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
			List<HashMap<String, String>> list = mAdapter.getData();

			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckCallback(i, false, list.get(i), mAdapter, isDuoxuan,box);
				}
			}

			list.get(positon).put("isChecked", "1");

			// listMap.add(list.get(positon));
			if (!linkList.contains(list.get(positon))) {
				linkList.add(list.get(positon));
			}
		} else {
			box.setBackgroundResource(R.drawable.wodexihao_fengge_icon_weixuanzhong);
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			// listMap.remove(list.get(positon));
			if (linkList.contains(list.get(positon))) {
				linkList.remove(list.get(positon));
			}
		}

		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
		
	}

}

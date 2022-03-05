package com.yssj.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.custom.view.NoScrollGridView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.adpter.FilterGridViewAdapter;
import com.yssj.ui.adpter.MyLikeStyleAdapter;
import com.yssj.ui.adpter.FilterGridViewAdapter.CheckAgeCallback;
import com.yssj.ui.adpter.FilterGridViewAdapter.CheckCallback;
import com.yssj.ui.adpter.FilterGridViewAdapter.CheckStyleCallback;
import com.yssj.ui.adpter.FilterGridViewAdapter.CheckXiaofeixiguanCallback;
import com.yssj.ui.adpter.FilterGridViewAdapter.ViewHolderStyle;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyLikeActivity extends BasicActivity implements OnClickListener,
		// OnLayoutChangeListener,
		CheckCallback, CheckXiaofeixiguanCallback, CheckStyleCallback, CheckAgeCallback {

	private MyLikeStyleAdapter mAdapter;
	private ArrayList<String> list = new ArrayList<String>();;

	private ArrayList<Integer> isCheckedList = new ArrayList<Integer>(); // 风格被已经选中的
	private Button ok, cancel;
	private int checkNum; // 记录选中的条目数量
	private TextView tv_show;// 用于显示选中的条目数量
	private String hobbyList;// 最终用于提交的喜好
	private NoScrollGridView gv_styles; // 喜爱风格
	private com.yssj.custom.view.NoScrollGridView gv_xiaofei;// 消费习惯
	private com.yssj.custom.view.NoScrollGridView gv_age;// 年龄段

	private ImageButton imgbtn_left_icon;

	private boolean isSignJump; // 是否是从签到任务跳过来
	private boolean isJingxuanJump; // 精选推荐跳过来的

	public Context context;

	private YDBHelper helper;
	// 用于提交 ----------最好写三个
	private List<HashMap<String, String>> linkList = new ArrayList<HashMap<String, String>>();

	private List<HashMap<String, String>> linkXiaofeiList = new ArrayList<HashMap<String, String>>();
	private List<HashMap<String, String>> linkStyleList = new ArrayList<HashMap<String, String>>();
	private List<HashMap<String, String>> linkAgeList = new ArrayList<HashMap<String, String>>();

	// 整个列表（每个大类的名称和图标在此 attr_name，icon ，还有每个子目录的名称）
	// private List<HashMap<String, String>> categoryList = new
	// ArrayList<HashMap<String, String>>();

	// 整个列表----放好了各个数据 第二层ArrayList：一级目录； 第三层：HashMap，具体的各个条目，包含了标志isChecked
	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();

	// Activity最外层的Layout视图
	private View root;
	// 屏幕高度
	private int screenHeight = 0;
	// 软件盘弹起后所占高度阀值ֵ
	private int keyHeight = 0;

	public static ScrollView sl;

	private HashMap<String, ArrayList<HashMap<String, String>>> allList = new HashMap<String, ArrayList<HashMap<String, String>>>();

	private String oldWeight; // 以前体重
	private String oldHeight;// 以前身高

	private Handler handler = new Handler();

	private EditText et_height, et_weight; // 身体高体重
	private UserInfo user;
	private String hobby; //用户以前的hobby
	// 是否设置过喜好
	private boolean isHasHobby = false;

	/** Called when the activity is first created. */

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ActionBar aBar = getActionBar();
		// aBar.setDisplayHomeAsUpEnabled(true);
		// aBar.hide();

		setContentView(R.layout.activity_set_my_like);
		helper = new YDBHelper(this);
		this.context = this;
		root = findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		isSignJump = getIntent().getBooleanExtra("isSignJump", false);
		isJingxuanJump = getIntent().getBooleanExtra("isJingxuanJump", false);
		
		
//		isSignJump = true;
		
		user = YCache.getCacheUser(context);
		// 通过用户信息查询是否已经设置过喜好
		hobby = user.getHobby();
		// hobby ="2002,2004,2056_170,50"; //假数据

		if (null != hobby && hobby.contains("_")) {
			isHasHobby = true;
			try {
				oldHeight = hobby.split("_")[1].split(",")[0];
				oldWeight = hobby.split("_")[1].split(",")[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			isHasHobby = false;
		}

		// 获取屏幕高度
		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
		// 阀值设置为屏幕高度的1/3
		keyHeight = screenHeight / 3;
		intitView();
		queryData();

	}

	private void intitView() {
		/* 实例化各个控件 */
		gv_styles = (NoScrollGridView) findViewById(R.id.lv);
		gv_styles.setFocusable(false);
		gv_xiaofei = (NoScrollGridView) findViewById(R.id.gv_xiaofei);
		gv_xiaofei.setFocusable(false);
		gv_age = (NoScrollGridView) findViewById(R.id.gv_age);
		gv_age.setFocusable(false);

		sl = (ScrollView) findViewById(R.id.sl);
		ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(this);
		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
		imgbtn_left_icon.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);

		// 身高 体重
		et_height = (EditText) findViewById(R.id.et_height);
		et_weight = (EditText) findViewById(R.id.et_weight);

		// 只能输入数学和小数点
		et_height.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		et_weight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		if (null != oldHeight) {
			et_height.setText(oldHeight);
		}
		if (null != oldWeight) {
			et_weight.setText(oldWeight);
		}

	}

	private void queryData() {

		new SAsyncTask<Void, Void, HashMap<String, ArrayList<HashMap<String, String>>>>((FragmentActivity) context, 0) {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) context);
			}

			@Override
			protected HashMap<String, ArrayList<HashMap<String, String>>> doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel2.getHobbyList(context);
			}

			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, ArrayList<HashMap<String, String>>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null && result != null) {
					if (result.size() != 0) {
						allList = result;
						// 现在没有多出的ID，用户能看到的就是所有的
						String FirstThree = hobby.split("_")[0];
						// 取出三个List
						// --------------------------------------------------------------------------------------------------------------------------------
						// 定价
						ArrayList<HashMap<String, String>> arrayListForEveryGridView1 = allList.get("1");
						for (HashMap<String, String> hashMap : arrayListForEveryGridView1) {

							if (isHasHobby) {
								if (FirstThree.contains(hashMap.get("like_id"))) {
									hashMap.put("isChecked", "1");
									linkXiaofeiList.add(hashMap);
								} else {
									hashMap.put("isChecked", "0");
								}
							} else {
								hashMap.put("isChecked", "0");
							}

						}

						FilterGridViewAdapter gridViewAdapter1 = new FilterGridViewAdapter(MyLikeActivity.this.context,
								arrayListForEveryGridView1, true, false);

						// 监听消费习惯选中的个数
						gridViewAdapter1.setXiaofeixiguanListener(MyLikeActivity.this);
						gridViewAdapter1.setListener(MyLikeActivity.this);
						gv_xiaofei.setAdapter(gridViewAdapter1);

						// ----------------------------------------------------------------------------------------------------------------------------
						// 风格
						ArrayList<HashMap<String, String>> arrayListForEveryGridView2 = allList.get("2");
						for (HashMap<String, String> hashMap : arrayListForEveryGridView2) {
							if (isHasHobby) {
								if (FirstThree.contains(hashMap.get("like_id"))) {
									hashMap.put("isChecked", "1");
									linkStyleList.add(hashMap);
								} else {
									hashMap.put("isChecked", "0");
								}
							} else {
								hashMap.put("isChecked", "0");
							}

						}
						FilterGridViewAdapter gridViewAdapter2 = new FilterGridViewAdapter(MyLikeActivity.this.context,
								arrayListForEveryGridView2, true, true);

						// 监听风格选中的个数
						gridViewAdapter2.setStyleListener(MyLikeActivity.this);
						gridViewAdapter2.setListener(MyLikeActivity.this);
						gv_styles.setAdapter(gridViewAdapter2);
						gv_styles.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								ViewHolderStyle holder = (ViewHolderStyle) view.getTag();
								holder.cb.toggle();// 让整个item都有点击切换开关的效果
							}
						});
						// ----------------------------------------------------------------------------------------------------------------------------
						// 年龄段
						ArrayList<HashMap<String, String>> arrayListForEveryGridView3 = allList.get("3");
						for (HashMap<String, String> hashMap : arrayListForEveryGridView3) {
							if (isHasHobby) {
								if (FirstThree.contains(hashMap.get("like_id"))) {
									hashMap.put("isChecked", "1");
									linkAgeList.add(hashMap);
								} else {
									hashMap.put("isChecked", "0");
								}
							} else {
								hashMap.put("isChecked", "0");
							}
						}
						FilterGridViewAdapter gridViewAdapter3 = new FilterGridViewAdapter(MyLikeActivity.this.context,
								arrayListForEveryGridView3, false, false);
						// 监听年龄段选中的个数
						gridViewAdapter3.setAgeListener(MyLikeActivity.this);
						gridViewAdapter3.setListener(MyLikeActivity.this);
						gv_age.setAdapter(gridViewAdapter3);

						// 填充身高体重

						if (isHasHobby) {
							et_height.setText(oldHeight);
							et_weight.setText(oldWeight);
						}

					}

				}

			}

		}.execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 添加layout大小发生改变监听器
		// root.addOnLayoutChangeListener(this);
		// root.addOnLayoutChangeListener(this);

	}

	@Override
	public void onClick(View v) {

		final Dialog dialog = new Dialog(this, R.style.DialogQuheijiao);
		View view = View.inflate(this, R.layout.dialog_like_hint, null);
		TextView tv = (TextView) view.findViewById(R.id.tv);

		switch (v.getId()) {
		case R.id.ok: // 确定
			// 1,2,3,4一个一个的判断
			// 消费习惯

			if (linkXiaofeiList.size() == 0) {
				tv.setText("亲爱的你还没选择消费习惯哦！");
				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
								sl.fullScroll(ScrollView.FOCUS_UP);
							}
						});
					}
				});
				dialog.setContentView(view);
				dialog.show();
				return;
			}

			if (linkStyleList.size() == 0) {
				tv.setText("亲爱的你还没选择喜爱风格哦！");
				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
								sl.fullScroll(ScrollView.FOCUS_UP);
							}
						});
					}
				});
				dialog.setContentView(view);
				dialog.show();
				return;
			}
			if (linkAgeList.size() == 0) {
				tv.setText("亲爱的你还没选择年龄段哦！");
				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						});
					}
				});
				dialog.setContentView(view);
				dialog.show();
				return;
			}

			// 身高 et_height
			String height = et_height.getText().toString().trim();
			if (height.endsWith(".")) {
				height = height + "0";
			}
			if (height.startsWith(".")) {
				height = "0" + height;
			}
			if (height.equals(".")) {
				height = "0.0";
			}
			et_height.setText(height);

			if (height.length() == 0) {

				tv.setText("亲爱的你还没填写你的身高哦！");
				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						});
					}
				});
				dialog.setContentView(view);
				dialog.show();
				return;
			}

			// 体重 et_weight
			String weight = et_weight.getText().toString().trim();
			if (weight.endsWith(".")) {
				weight = weight + "0";
			}
			if (weight.startsWith(".")) {
				weight = "0" + weight;
			}
			if (weight.equals(".")) {
				weight = "0.0";
			}
			et_weight.setText(weight);
			if (weight.length() == 0) {
				tv.setText("亲爱的你还没填写你的体重哦！");
				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						});
					}
				});
				dialog.setContentView(view);
				dialog.show();
				return;
			}

			if (Double.parseDouble(height) < 100 || Double.parseDouble(height) > 200) {
				ToastUtil.showShortText(this, "请输入正确的身高！");
				return;
			}
			if (Double.parseDouble(weight) < 30 || Double.parseDouble(weight) > 100) {
				ToastUtil.showShortText(this, "请输入正确的体重！");
				return;
			}
			summitMyLike(v);
//			ToastUtil.showShortText(this, "通过");

			break;

		case R.id.cancel:
			onBackPressed();
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
	public void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
		// TODO Auto-generated method stub
		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();

			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckCallback(i, false, list.get(i), mAdapter, isDuoxuan);
				}
			}

			list.get(positon).put("isChecked", "1");

			// listMap.add(list.get(positon));
			if (!linkList.contains(list.get(positon))) {
				linkList.add(list.get(positon));
			}
		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			// listMap.remove(list.get(positon));
			if (linkList.contains(list.get(positon))) {
				linkList.remove(list.get(positon));
			}
		}

		// 通过通知gridView刷新来更新选中状态
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCheckNLCallback(int positon, boolean isChecked, HashMap<String, String> map,
			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {

		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();

			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckNLCallback(i, false, list.get(i), mAdapter, isDuoxuan);
				}
			}

			list.get(positon).put("isChecked", "1");

			// listMap.add(list.get(positon));
			if (!linkAgeList.contains(list.get(positon))) {
				linkAgeList.add(list.get(positon));
			}
		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			// listMap.remove(list.get(positon));
			if (linkAgeList.contains(list.get(positon))) {
				linkAgeList.remove(list.get(positon));
			}
		}
		// 通过通知gridView刷新来更新选中状态
		mAdapter.notifyDataSetChanged();

	}

	@Override
	public void onCheckFGCallback(int positon, boolean isChecked, HashMap<String, String> map,
			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {

		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();

			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckFGCallback(i, false, list.get(i), mAdapter, isDuoxuan);
				}
			}

			list.get(positon).put("isChecked", "1");

			// listMap.add(list.get(positon));
			if (!linkStyleList.contains(list.get(positon))) {
				linkStyleList.add(list.get(positon));
			}
		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			// listMap.remove(list.get(positon));
			if (linkStyleList.contains(list.get(positon))) {
				linkStyleList.remove(list.get(positon));
			}
		}
		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCheckXFCallback(int positon, boolean isChecked, HashMap<String, String> map,
			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
		if (isChecked) {
			List<HashMap<String, String>> list = mAdapter.getData();

			// 单选多选的切换
			if (!isDuoxuan) {
				for (int i = 0; i < list.size(); i++) {
					onCheckXFCallback(i, false, list.get(i), mAdapter, isDuoxuan);
				}
			}

			list.get(positon).put("isChecked", "1");

			// listMap.add(list.get(positon));
			if (!linkXiaofeiList.contains(list.get(positon))) {
				linkXiaofeiList.add(list.get(positon));
			}
		} else {
			List<HashMap<String, String>> list = mAdapter.getData();
			list.get(positon).put("isChecked", "0");
			// listMap.remove(list.get(positon));
			if (linkXiaofeiList.contains(list.get(positon))) {
				linkXiaofeiList.remove(list.get(positon));
			}
		}
		// 通过通知gridView刷新来更新选中状态
		mAdapter.notifyDataSetChanged();
	}

	private void summitMyLike(View v) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < linkList.size(); i++) {
			HashMap<String, String> map = linkList.get(i);
			sb.append(map.get("like_id"));
			if (i != linkList.size() - 1) {
				sb.append(",");
			}
		}
		hobbyList = sb.toString() + "_" + et_height.getText().toString().trim() + ","
				+ et_weight.getText().toString().trim();

		LogYiFu.e("提交结果", hobbyList);
		
		
		
		if(hobbyList.equals(hobby)){
			ToastUtil.showShortText(context, "您的喜好标签未修改！");
			return;
		}
		
		
		
		

		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
				// TODO Auto-generated method stub

				// 用下划线隔开加上身高体重 -----------21,31,17,104,34_身高,体重
				return ComModel2.updateMyLike(context, hobbyList);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					if (result.getStatus().equals("1")) {
						if (result != null) {

							UserInfo user = YCache.getCacheUser(context);
							user.setHobby(hobbyList);
							YCache.setCacheUser(context, user);
							if (isSignJump) {// 跳到签到弹出获得奖励



								if(null != CommonActivity.instance ){
									CommonActivity.instance.finish();
								}
								//模拟签到奖励
								SharedPreferencesUtil.saveBooleanData(context, "isSignSetLikeComplete", true);
								SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
								Intent intent = new Intent(context, CommonActivity.class);
								// 签到奖励暂时拿不到--------------------------------------------------------------------
								intent.putExtra("isFirstSetHobbyComplete", true);
								context.startActivity(intent);
							} else {
								ToastUtil.showShortText(MyLikeActivity.this.context, "喜好设置修改成功~");
							}
							
							if (isJingxuanJump) {
								setResult(-1001);
								onBackPressed();
							}else{
								finish();
							}
							
						}

					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute();

	}

}
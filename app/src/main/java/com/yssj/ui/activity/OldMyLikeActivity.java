//package com.yssj.ui.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.NoScrollGridView;
//import com.yssj.data.YDBHelper;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.adpter.FilterGridViewAdapter;
//import com.yssj.ui.adpter.MyLikeStyleAdapter;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckAgeCallback;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckCallback;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckStyleCallback;
//import com.yssj.ui.adpter.FilterGridViewAdapter.CheckXiaofeixiguanCallback;
//import com.yssj.ui.adpter.FilterGridViewAdapter.ViewHolderStyle;
//import com.yssj.ui.adpter.MyLikeStyleAdapter.ViewHolder;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.LogYiFu;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.SharedPreferencesUtil;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.FragmentActivity;
//import android.text.InputType;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnLayoutChangeListener;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//public class OldMyLikeActivity extends BasicActivity implements OnClickListener, OnLayoutChangeListener, CheckCallback,
//		CheckXiaofeixiguanCallback, CheckStyleCallback, CheckAgeCallback {
//
//	private MyLikeStyleAdapter mAdapter;
//	private ArrayList<String> list = new ArrayList<String>();;
//
//	private ArrayList<Integer> isCheckedList = new ArrayList<Integer>(); // 风格被已经选中的
//	private Button ok, cancel;
//	private int checkNum; // 记录选中的条目数量
//	private TextView tv_show;// 用于显示选中的条目数量
//	private NoScrollGridView gv_styles; // 喜爱风格
//	private com.yssj.custom.view.NoScrollGridView gv_xiaofei;// 消费习惯
//	private com.yssj.custom.view.NoScrollGridView gv_age;// 年龄段
//
//	private ImageButton imgbtn_left_icon;
//
//	private boolean isSignJump; // 是否是从签到任务跳过来
//
//	public Context context;
//
//	private YDBHelper helper;
//	// 用于提交 ----------最好写三个
//	private List<HashMap<String, String>> linkList = new ArrayList<HashMap<String, String>>();
//
//	private List<HashMap<String, String>> linkXiaofeiList = new ArrayList<HashMap<String, String>>();
//	private List<HashMap<String, String>> linkStyleList = new ArrayList<HashMap<String, String>>();
//	private List<HashMap<String, String>> linkAgeList = new ArrayList<HashMap<String, String>>();
//
//	// 整个列表（每个大类的名称和图标在此 attr_name，icon ，还有每个子目录的名称）
//	private List<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//	// 整个列表----放好了各个数据 第二层ArrayList：一级目录； 第三层：HashMap，具体的各个条目，包含了标志isChecked
//	private List<ArrayList<HashMap<String, String>>> listDatas = new ArrayList<ArrayList<HashMap<String, String>>>();
//
//	// Activity最外层的Layout视图
//	private View root;
//	// 屏幕高度
//	private int screenHeight = 0;
//	// 软件盘弹起后所占高度阀值ֵ
//	private int keyHeight = 0;
//
//	public static ScrollView sl;
//
//	private boolean isFirstSetHobby;
//
//	private String oldWeight; // 以前体重
//	private String oldHeight;// 以前身高
//
//	private Handler handler = new Handler();
//
//	private EditText et_height, et_weight; // 身体高体重
//	private ArrayList<Integer> beginStyle = new ArrayList<Integer>(); // 刚进来是喜爱风格的选中位置
//
//	// 是否设置过喜好
//	private boolean isHasHobby = false;
//
//	/** Called when the activity is first created. */
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		// ActionBar aBar = getActionBar();
//		// aBar.setDisplayHomeAsUpEnabled(true);
//		// aBar.hide();
//
//		setContentView(R.layout.activity_set_my_like);
//		helper = new YDBHelper(this);
//		this.context = this;
//		root = findViewById(R.id.root);
//		root.setBackgroundColor(Color.WHITE);
//		isSignJump = getIntent().getBooleanExtra("isSignJump", false);
//
//		// 用户信息中是否已经保存喜好，如果有说明之前已经保存过
//		final UserInfo user = YCache.getCacheUser(context);
//		String hobby = user.getHobby();
//		// 如果已经设置过，形式就是
//		// "21,31,17,104,34_身高,体重"
//
//		if (null == hobby || hobby.length() == 0) {
//			isFirstSetHobby = true;
//		} else {
//			isFirstSetHobby = false;
//			if (hobby.split("_").length > 1) {
//				oldHeight = hobby.split("_")[1].split(",")[0];
//				oldWeight = hobby.split("_")[1].split(",")[1];
//			}
//
//
//		}
//
//		// 获取屏幕高度
//		screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
//		// 阀值设置为屏幕高度的1/3
//		keyHeight = screenHeight / 3;
//		intitView();
//		queryData();
//
//	}
//
//	private void queryData() {
//		final UserInfo user = YCache.getCacheUser(context);
//		// 通过用户信息查询是否已经设置过喜好
//		if (null == user.getHobby() || user.getHobby().equals("0")) {
//			isHasHobby = false;
//		} else {
//			isHasHobby = true;
//		}
//		new SAsyncTask<Void, Void, Void>(this) {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//				// 查询数据库得到喜好的整个列表
//				categoryList = helper.query("select * from tag_info where p_id=0 and is_show=1 order by sequence");
//				// 子目录 ，最爱，定价，风格。。。。。
//				ArrayList<HashMap<String, String>> listChild;
//				// 遍历整个列表，通过 _id 拿到单个子目录
//				for (int i = 0; i < categoryList.size(); i++) {
//					// 通过 _id查询数据库
//					listChild = (ArrayList<HashMap<String, String>>) helper.query("select * from tag_info where p_id = "
//							+ categoryList.get(i).get("_id") + " order by sequence");
//
//					// 遍历子目录信息 ，将是否已经设置过的标志加进去 0代表设置过，1代表设置过
//					for (HashMap<String, String> map : listChild) {
//						if (isHasHobby) {
//							if (("," + user.getHobby() + ",").contains("," + map.get("_id") + ",")) {
//								map.put("isChecked", "1");
//								// 这里提交要加上
//								linkList.add(map);
//
//								if (categoryList.get(i).get("attr_name").equals("定价")) {
//									linkXiaofeiList.add(map);
//								}
//								if (categoryList.get(i).get("attr_name").equals("风格")) {
//									linkStyleList.add(map);
//								}
//								if (categoryList.get(i).get("attr_name").equals("年龄")) {
//									linkAgeList.add(map);
//								}
//
//							} else {
//								map.put("isChecked", "0");
//							}
//						} else {
//							map.put("isChecked", "0");
//						}
//					}
//					// 将子目录全部填充， 里面已经加入标志 isChecked： 0 未设置过 1设置过
//					listDatas.add(listChild);
//				}
//				return super.doInBackground(params);
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Void result) {
//				// 填充整个列表 展示的不同，分开填充
//				// 消费习惯 和 年龄段 ,风格
//				initGridView();
//				// 喜爱风格 --风格的布局跟别的不一样 单独写
//				// addStyle();
//
//				super.onPostExecute(context, result);
//			}
//
//		}.execute();
//
//	}
//
//	// 填充喜爱风格
//	// private void addStyle() {
//	//
//	// // gridView数据-- 需要掉接口拿
//	// for (int i = 0; i < 8; i++) {
//	// list.add("data" + " " + i);
//	// }
//	// // 刚进来就设置2个已经勾选过的 ---掉接口拿
//	// isCheckedList.add(2);
//	// isCheckedList.add(4);
//	// beginStyle.addAll(isCheckedList);
//	//
//	// // 实例化自定义的MyAdapter
//	// mAdapter = new MyLikeStyleAdapter(list, isCheckedList, this);
//	// // 绑定Adapter
//	// gv_styles.setAdapter(mAdapter);
//	//
//	// // 绑定listView的监听器
//	// gv_styles.setOnItemClickListener(new OnItemClickListener() {
//	// @Override
//	// public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//	// long arg3) {
//	// // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//	// ViewHolder holder = (ViewHolder) arg1.getTag();
//	// // 改变CheckBox的状态״̬
//	// holder.cb.toggle();
//	// // 将CheckBox的选中状况记录下来
//	// MyLikeStyleAdapter.getIsSelected().put(position, holder.cb.isChecked());
//	// // 调整选定条目
//	// if (holder.cb.isChecked() == true) {
//	// checkNum++;
//	// beginStyle.add(position);
//	// } else {
//	// checkNum--;
//	// for (int i = 0; i < beginStyle.size(); i++) {
//	// if (position == beginStyle.get(i)) {
//	// beginStyle.remove(i);
//	// }
//	// }
//	//
//	// }
//	//
//	// }
//	// });
//	//
//	// }
//
//	// 填充消费习惯
//	private void initGridView() {
//		// 填充消费习惯
//
//		for (int i = 0; i < listDatas.size(); i++) {
//
//			// 这里大类的名称和图标在categoryList中，而categoryList 和
//			// listDatas的长度是一样的，可以用i直接去取
//
//			// 取到年龄段 ---单选
//			if (categoryList.get(i).get("attr_name").equals("年龄")) {
//				ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas.get(i);
//				FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(this, arrayListForEveryGridView,
//						false, false);
//				// 监听年龄段选中的个数
//				gridViewAdapter.setAgeListener(this);
//				gridViewAdapter.setListener(this);
//				gv_age.setAdapter(gridViewAdapter);
//
//			}
//
//			// 取到风格-----多选
//			if (categoryList.get(i).get("attr_name").equals("风格")) {
//				ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas.get(i);
//				FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(this, arrayListForEveryGridView, true,
//						true);
//
//				// 监听风格选中的个数
//				gridViewAdapter.setStyleListener(this);
//				gridViewAdapter.setListener(this);
//				gv_styles.setAdapter(gridViewAdapter);
//
//				gv_styles.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//						ViewHolderStyle holder = (ViewHolderStyle) view.getTag();
//						holder.cb.toggle();// 让整个item都有点击切换开关的效果
//					}
//				});
//
//			}
//
//			// 取到消费习惯---定价 多选
//			if (categoryList.get(i).get("attr_name").equals("定价")) {
//				ArrayList<HashMap<String, String>> arrayListForEveryGridView = this.listDatas.get(i);
//				FilterGridViewAdapter gridViewAdapter = new FilterGridViewAdapter(this, arrayListForEveryGridView, true,
//						false);
//
//				// 监听消费习惯选中的个数
//				gridViewAdapter.setXiaofeixiguanListener(this);
//				gridViewAdapter.setListener(this);
//				gv_xiaofei.setAdapter(gridViewAdapter);
//
//			}
//
//		}
//
//	}
//
//	private void intitView() {
//		/* 实例化各个控件 */
//		gv_styles = (NoScrollGridView) findViewById(R.id.lv);
//		gv_styles.setFocusable(false);
//		gv_xiaofei = (NoScrollGridView) findViewById(R.id.gv_xiaofei);
//		gv_xiaofei.setFocusable(false);
//		gv_age = (NoScrollGridView) findViewById(R.id.gv_age);
//		gv_age.setFocusable(false);
//
//		sl = (ScrollView) findViewById(R.id.sl);
//		ok = (Button) findViewById(R.id.ok);
//		ok.setOnClickListener(this);
//		imgbtn_left_icon = (ImageButton) findViewById(R.id.imgbtn_left_icon);
//		imgbtn_left_icon.setOnClickListener(this);
//		cancel = (Button) findViewById(R.id.cancel);
//		cancel.setOnClickListener(this);
//
//		// 身高 体重
//		et_height = (EditText) findViewById(R.id.et_height);
//		et_weight = (EditText) findViewById(R.id.et_weight);
//
//		// 只能输入数学和小数点
//		et_height.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//		et_weight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//
//		// et_weight.setOnClickListener(new OnClickListener() {
//		// @Override
//		// public void onClick(View v) {
//		// et_height.clearFocus();
//		// }
//		// });
//
//		if (null != oldHeight) {
//			et_height.setText(oldHeight);
//		}
//		if (null != oldWeight) {
//			et_weight.setText(oldWeight);
//		}
//
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		// 添加layout大小发生改变监听器
//		// root.addOnLayoutChangeListener(this);
//		root.addOnLayoutChangeListener(this);
//
//	}
//
//	// 刷新listview和TextView的显示
//	private void dataChanged() {
//		// 通知listView刷新
//		mAdapter.notifyDataSetChanged();
//		// TextView显示最新的选中数目
//		tv_show.setText("已选中" + checkNum + "项");
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		final Dialog dialog = new Dialog(this, R.style.DialogQuheijiao);
//		View view = View.inflate(this, R.layout.dialog_like_hint, null);
//		TextView tv = (TextView) view.findViewById(R.id.tv);
//
//		switch (v.getId()) {
//		case R.id.ok: // 确定
//			// 1,2,3,4一个一个的判断
//			// 消费习惯
//
//			if (linkXiaofeiList.size() == 0) {
//				tv.setText("亲爱的你还没选择消费习惯哦！");
//				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								dialog.dismiss();
//								sl.fullScroll(ScrollView.FOCUS_UP);
//							}
//						});
//					}
//				});
//				dialog.setContentView(view);
//				dialog.show();
//				return;
//			}
//
//			if (linkStyleList.size() == 0) {
//				tv.setText("亲爱的你还没选择喜爱风格哦！");
//				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								dialog.dismiss();
//								sl.fullScroll(ScrollView.FOCUS_UP);
//							}
//						});
//					}
//				});
//				dialog.setContentView(view);
//				dialog.show();
//				return;
//			}
//			if (linkAgeList.size() == 0) {
//				tv.setText("亲爱的你还没选择年龄段哦！");
//				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								dialog.dismiss();
//							}
//						});
//					}
//				});
//				dialog.setContentView(view);
//				dialog.show();
//				return;
//			}
//
//			// 身高 et_height
//			String height = et_height.getText().toString().trim();
//			if (height.endsWith(".")) {
//				height = height + "0";
//			}
//			if (height.startsWith(".")) {
//				height = "0" + height;
//			}
//			if (height.equals(".")) {
//				height = "0.0";
//			}
//			et_height.setText(height);
//
//			if (height.length() == 0) {
//
//				tv.setText("亲爱的你还没填写你的身高哦！");
//				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								dialog.dismiss();
//							}
//						});
//					}
//				});
//				dialog.setContentView(view);
//				dialog.show();
//				return;
//			}
//
//			// 体重 et_weight
//			String weight = et_weight.getText().toString().trim();
//			if (weight.endsWith(".")) {
//				weight = weight + "0";
//			}
//			if (weight.startsWith(".")) {
//				weight = "0" + weight;
//			}
//			if (weight.equals(".")) {
//				weight = "0.0";
//			}
//			et_weight.setText(weight);
//			if (weight.length() == 0) {
//				tv.setText("亲爱的你还没填写你的体重哦！");
//				view.findViewById(R.id.bt_ok).setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								dialog.dismiss();
//							}
//						});
//					}
//				});
//				dialog.setContentView(view);
//				dialog.show();
//				return;
//			}
//
//			if (Double.parseDouble(height) < 100 || Double.parseDouble(height) > 200) {
//				ToastUtil.showShortText(this, "请输入正确的身高！");
//				return;
//			}
//			if (Double.parseDouble(weight) < 30 || Double.parseDouble(weight) > 100) {
//				ToastUtil.showShortText(this, "请输入正确的体重！");
//				return;
//			}
//			summitMyLike(v);
//			ToastUtil.showShortText(this, "通过");
//
//			break;
//
//		case R.id.cancel:
//			onBackPressed();
//			break;
//		case R.id.imgbtn_left_icon:
//			onBackPressed();
//			break;
//
//		default:
//			break;
//		}
//
//	}
//
//	private String hobbyList;
//
//	private void summitMyLike(View v) {
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < linkList.size(); i++) {
//			HashMap<String, String> map = linkList.get(i);
//			sb.append(map.get("_id"));
//			if (i != linkList.size() - 1) {
//				sb.append(",");
//			}
//		}
//		hobbyList = sb.toString();
//
//		LogYiFu.e("提交结果", hobbyList);
//
//		new SAsyncTask<String, Void, ReturnInfo>(this, null, R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
//				// TODO Auto-generated method stub
//
//				// 体重 et_weight
//				String weight = et_weight.getText().toString().trim();
//
//				// 用下划线隔开加上身高体重 -----------21,31,17,104,34_身高,体重
//				return ComModel2.updateMyLike(context, hobbyList + "_" + et_height.getText().toString().trim() + ","
//						+ et_weight.getText().toString().trim());
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
//				// TODO Auto-generated method stub
//				if (null == e) {
//					if (result.getStatus().equals("1")) {
//						if (result != null) {
//
//							UserInfo user = YCache.getCacheUser(context);
//							user.setHobby(hobbyList);
//							YCache.setCacheUser(context, user);
//							if (isFirstSetHobby) {// 调回签到弹出获得奖励
//								Intent intent = new Intent(context, MainMenuActivity.class);
//								// 签到奖励暂时拿不到--------------------------------------------------------------------
////								intent.putExtra("Exit30", true);
////								context.startActivity(intent);
//								
//								SharedPreferencesUtil.saveStringData(context, "commonactivityfrom", "sign");
//								// 跳至赚钱
//								context.startActivity(new Intent(context, CommonActivity.class));
//								
//							} else {
//								ToastUtil.showShortText(OldMyLikeActivity.this.context, "喜好设置修改成功~");
//							}
//
//							finish();
//						}
//
//					}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute();
//
//	}
//
//	@Override
//	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
//			int oldBottom) {
//		// old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
//
//		// System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " +
//		// oldBottom);
//		// System.out.println(left + " " + top +" " + right + " " + bottom);
//
//		// 现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
//		if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
//
//			// handler.postDelayed(new Runnable() {
//			// @Override
//			// public void run() {
//			// sl.fullScroll(ScrollView.FOCUS_DOWN);
//			// }
//			// }, 50);
//
//			// Toast.makeText(MyLikeActivity.this, "监听到软键盘弹起...",
//			// Toast.LENGTH_SHORT).show();
//
//		} else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
//
//			// Toast.makeText(MyLikeActivity.this, "监听到软件盘关闭...",
//			// Toast.LENGTH_SHORT).show();
//
//		}
//	};
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		overridePendingTransition(R.anim.slide_match, R.anim.slide_left_out);
//	}
//
//	// "isChecked", "1" 0代表选中，1代表未选中
//	@Override
//	public void onCheckCallback(int positon, boolean isChecked, HashMap<String, String> map,
//			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
//		// TODO Auto-generated method stub
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//
//			// 单选多选的切换
//			if (!isDuoxuan) {
//				for (int i = 0; i < list.size(); i++) {
//					onCheckCallback(i, false, list.get(i), mAdapter, isDuoxuan);
//				}
//			}
//
//			list.get(positon).put("isChecked", "1");
//
//			// listMap.add(list.get(positon));
//			if (!linkList.contains(list.get(positon))) {
//				linkList.add(list.get(positon));
//			}
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			// listMap.remove(list.get(positon));
//			if (linkList.contains(list.get(positon))) {
//				linkList.remove(list.get(positon));
//			}
//		}
//
//		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
//	}
//
//	@Override
//	public void onCheckNLCallback(int positon, boolean isChecked, HashMap<String, String> map,
//			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
//
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//
//			// 单选多选的切换
//			if (!isDuoxuan) {
//				for (int i = 0; i < list.size(); i++) {
//					onCheckNLCallback(i, false, list.get(i), mAdapter, isDuoxuan);
//				}
//			}
//
//			list.get(positon).put("isChecked", "1");
//
//			// listMap.add(list.get(positon));
//			if (!linkAgeList.contains(list.get(positon))) {
//				linkAgeList.add(list.get(positon));
//			}
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			// listMap.remove(list.get(positon));
//			if (linkAgeList.contains(list.get(positon))) {
//				linkAgeList.remove(list.get(positon));
//			}
//		}
//		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
//
//	}
//
//	@Override
//	public void onCheckFGCallback(int positon, boolean isChecked, HashMap<String, String> map,
//			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
//
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//
//			// 单选多选的切换
//			if (!isDuoxuan) {
//				for (int i = 0; i < list.size(); i++) {
//					onCheckFGCallback(i, false, list.get(i), mAdapter, isDuoxuan);
//				}
//			}
//
//			list.get(positon).put("isChecked", "1");
//
//			// listMap.add(list.get(positon));
//			if (!linkStyleList.contains(list.get(positon))) {
//				linkStyleList.add(list.get(positon));
//			}
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			// listMap.remove(list.get(positon));
//			if (linkStyleList.contains(list.get(positon))) {
//				linkStyleList.remove(list.get(positon));
//			}
//		}
//		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
//
//	}
//
//	@Override
//	public void onCheckXFCallback(int positon, boolean isChecked, HashMap<String, String> map,
//			FilterGridViewAdapter mAdapter, boolean isDuoxuan) {
//		if (isChecked) {
//			List<HashMap<String, String>> list = mAdapter.getData();
//
//			// 单选多选的切换
//			if (!isDuoxuan) {
//				for (int i = 0; i < list.size(); i++) {
//					onCheckXFCallback(i, false, list.get(i), mAdapter, isDuoxuan);
//				}
//			}
//
//			list.get(positon).put("isChecked", "1");
//
//			// listMap.add(list.get(positon));
//			if (!linkXiaofeiList.contains(list.get(positon))) {
//				linkXiaofeiList.add(list.get(positon));
//			}
//		} else {
//			List<HashMap<String, String>> list = mAdapter.getData();
//			list.get(positon).put("isChecked", "0");
//			// listMap.remove(list.get(positon));
//			if (linkXiaofeiList.contains(list.get(positon))) {
//				linkXiaofeiList.remove(list.get(positon));
//			}
//		}
//		// 通过通知gridView刷新来更新选中状态
//		mAdapter.notifyDataSetChanged();
//
//	}
//
//}
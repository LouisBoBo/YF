package com.yssj.ui.activity.circles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.activity.SuppAndClassActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class AddTagsActivity extends BasicActivity implements OnClickListener, OnTouchListener {
	private TextView mTvNext;// 下一步
	private TextView mTvCancle;// 取消
	private ImageView mIvPic;
	private RelativeLayout mRlTagsContain;// 标签容器
	private TextView mTvTags;
	private String picPath;// 选择的图片路径
	private Context mContext;
	private int screenWidth;// 获取屏幕的总宽度
	private int screenHeight;
	private float picRadio;// 图片的宽高比
	private float imageViewRadio;// ImageView 的宽高比
	private int picWidth;// 图片实际宽度
	private int picHeight;// 图片实际高度
	private int picWidthEdgeDistance;// 图片距离手机左边缘的距离
	private int picHeightEdgeDistance;// 图片距离手机右边缘的距离
	private float heightRadio;// 标签的高度和图片的高度之比;
	private float widthRadio;// 标签的宽度和图片的宽度之比;
	private int REQUEST_TAGS = 1;// 添加标签
	private int REQUEST_MODFY = 2;// 长按修改;
	private float x;
	private float y;
	private int tvChildPosition;
	private int statusBarHeight;
	private List<IssueBean> issueList = new ArrayList<IssueBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_tags);
		mContext = this;
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels - DP2SPUtil.dp2px(mContext, 134);
		picPath = getIntent().getStringExtra("picPath");
		initView();
		initData();
	}

	private void initData() {
		// Rect frame = new Rect();
		// getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		// int statusBarHeight = 50;
		// int imageViewHight = screenHeight - statusBarHeight;
		// imageViewRadio = (float) screenWidth / (float) imageViewHight;
		// Bitmap bitMap = BitmapFactory.decodeFile(picPath);
		// picRadio = (float) bitMap.getWidth() / (float) bitMap.getHeight();
		// if (picRadio > imageViewRadio) {// 图片横向居中铺满
		// picWidth = screenWidth;
		// picHeight = (int) (screenWidth / picRadio);// 图片实际高度
		// picHeightEdgeDistance = (imageViewHight - picHeight) / 2;
		// picWidthEdgeDistance = 0;
		// } else {// 图片竖向居中铺满铺满
		// picHeight = imageViewHight;
		// picWidth = (int) (imageViewHight * picRadio);// 图片实际宽度
		// picWidthEdgeDistance = (screenWidth - picWidth) / 2;
		// picHeightEdgeDistance = 0;
		// }
		// ImageLoader.getInstance(3, Type.LIFO).loadImage(picPath, mIvPic);
		File file = new File(picPath);
//		Picasso.with(mContext).load(file).into(mIvPic);
		Picasso.get()
				.load(file)
				.memoryPolicy(NO_CACHE, NO_STORE)
				.placeholder(R.drawable.image_default)
				.error(R.drawable.image_default)
				.into(mIvPic);
	}

	private void initView() {
		mTvNext = (TextView) findViewById(R.id.issue_tv_next);
		mTvNext.setOnClickListener(this);
		mTvCancle = (TextView) findViewById(R.id.issue_tv_cancle);
		mTvCancle.setOnClickListener(this);
		mIvPic = (ImageView) findViewById(R.id.issue_iv_pic);
		mIvPic.setOnClickListener(this);
		mIvPic.setOnTouchListener(this);
		mRlTagsContain = (RelativeLayout) findViewById(R.id.issue_rl_contain);
		mTvTags = (TextView) findViewById(R.id.issue_tv_tags);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.issue_iv_pic:

			break;
		case R.id.issue_tv_cancle:
			finish();
			break;
		case R.id.issue_tv_next:
			String jsonSuppLabel = changeArrayDateToJson();
			Intent intent = new Intent(mContext, IssueSweetFriendsActivity.class);
			intent.putExtra("tag_pic_path", picPath);
			intent.putExtra("jsonSuppLabel", jsonSuppLabel);
			intent.putExtra("isHasUserDefined", isHasUserDefined);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.issue_iv_pic:
			int action = event.getAction();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (mRlTagsContain.getChildCount() < 4) {
					y = event.getRawY();
					x = event.getRawX();

					Rect frame = new Rect();
					getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
					statusBarHeight = frame.top;

					int imageViewHight = screenHeight - statusBarHeight;
					imageViewRadio = (float) screenWidth / (float) imageViewHight;
					Bitmap bitMap = BitmapFactory.decodeFile(picPath);
					picRadio = (float) bitMap.getWidth() / (float) bitMap.getHeight();
					if (picRadio > imageViewRadio) {// 图片横向居中铺满
						picWidth = screenWidth;
						picHeight = (int) (screenWidth / picRadio);// 图片实际高度
						picHeightEdgeDistance = (imageViewHight - picHeight) / 2;
						picWidthEdgeDistance = 0;
					} else {// 图片竖向居中铺满铺满
						picHeight = imageViewHight;
						picWidth = (int) (imageViewHight * picRadio);// 图片实际宽度
						picWidthEdgeDistance = (screenWidth - picWidth) / 2;
						picHeightEdgeDistance = 0;
					}

					if (x >= picWidthEdgeDistance && x <= screenWidth - picWidthEdgeDistance
							&& y >= statusBarHeight + DP2SPUtil.dp2px(mContext, 50) + picHeightEdgeDistance
							&& y <= statusBarHeight + DP2SPUtil.dp2px(mContext, 50) + picHeight
									+ picHeightEdgeDistance) {
						Intent intent = new Intent(mContext, SuppAndClassActivity.class);// 添加标签
						startActivityForResult(intent, REQUEST_TAGS);
					}
				} else {
					ToastUtil.showShortText(mContext, "最多只可添加4个标签哦");
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
			}

			// int left = v.getLeft() + dx;
			// int top = v.getTop() + dy;
			// int right = v.getRight() + dx;
			// int bottom = v.getBottom() + dy;
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_MODFY && (resultCode == 10000 || resultCode == 10001)) {// 长按修改
			String label_id = data.getStringExtra("label_id");
			String label_name = data.getStringExtra("label_name");
			String label_type = data.getStringExtra("label_type");
			((TextView) (mRlTagsContain.getChildAt(tvChildPosition))).setText("" + label_name);
			IssueBean bean = issueList.get(tvChildPosition);
			if ("1".equals(label_type)) {
				try {
					bean.setLabel_id(Integer.parseInt(label_id));
				} catch (Exception e) {
				}
			}
			bean.setLabel_name("" + label_name);
			bean.setLabel_type(Integer.parseInt(label_type));
			if (resultCode == 10000) {
				String type1 = data.getStringExtra("type1");
				String type2 = data.getStringExtra("type2");
				String style = data.getStringExtra("style");
				bean.setType1(Integer.parseInt(type1));
				bean.setType2(Integer.parseInt(type2));
				bean.setStyle(Integer.parseInt(style));
			} else if (resultCode == 10001) {
				String shop_code = data.getStringExtra("shop_code");
				bean.setShop_code("" + shop_code);
			}

		} else if (requestCode == REQUEST_TAGS && (resultCode == 10000 || resultCode == 10001)) {
			TextView tv1 = (TextView) findViewById(R.id.issue_tv1);
			TextView tv2 = (TextView) findViewById(R.id.issue_tv2);
			TextView tv3 = (TextView) findViewById(R.id.issue_tv_move);
			TextView tv4 = (TextView) findViewById(R.id.issue_tv_move2);
			tv1.setVisibility(View.GONE);
			tv2.setVisibility(View.GONE);
			tv3.setVisibility(View.VISIBLE);
			tv4.setVisibility(View.VISIBLE);
			final IssueBean bean = new IssueBean();
			String label_id = data.getStringExtra("label_id");
			String label_name = data.getStringExtra("label_name");
			// String type1 = data.getStringExtra("type1");
			// String type2 = data.getStringExtra("type2");
			// String style = data.getStringExtra("style");
			String label_type = data.getStringExtra("label_type");
			if ("1".equals(label_type)) {
				try {
					bean.setLabel_id(Integer.parseInt(label_id));
				} catch (Exception e) {
				}
			}
			bean.setLabel_name("" + label_name);
			// bean.setType1(Integer.parseInt(type1));
			// bean.setType2(Integer.parseInt(type2));
			// bean.setStyle(Integer.parseInt(style));
			bean.setLabel_type(Integer.parseInt(label_type));
			if (resultCode == 10000) {
				String type1 = data.getStringExtra("type1");
				String type2 = data.getStringExtra("type2");
				String style = data.getStringExtra("style");
				bean.setType1(Integer.parseInt(type1));
				bean.setType2(Integer.parseInt(type2));
				bean.setStyle(Integer.parseInt(style));
			} else if (resultCode == 10001) {
				String shop_code = data.getStringExtra("shop_code");
				bean.setShop_code("" + shop_code);
			}
			issueList.add(bean);

			mTvTags.setVisibility(View.GONE);
			final TextView tv = new TextView(mContext);
			int tvHeigh = 0;
			if (resultCode == 10000) {
				tvHeigh = 35;
				bean.setFlagShop("0");
			} else {
				tvHeigh = 46;
				bean.setFlagShop("1");
			}
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, DP2SPUtil.dp2px(mContext, tvHeigh));

			tv.setText("" + label_name);
			tv.setSingleLine();
			tv.setTextColor(Color.parseColor("#ffffff"));
			tv.setTextSize(12);
			tv.setGravity(Gravity.CENTER);

			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			tv.measure(w, h);
			int width = tv.getMeasuredWidth();
			if (x < screenWidth / 2) {
				bean.setDirection("0");
				// tv.setPadding(DP2SPUtil.dp2px(mContext, 10), 0,
				// DP2SPUtil.dp2px(mContext, 6), 0);
				if (resultCode == 10000) {
					tv.setBackgroundResource(R.drawable.issue_tag);
				} else if (resultCode == 10001) {
					tv.setBackgroundResource(R.drawable.left_shop_buy);
				}
				if (picHeightEdgeDistance + DP2SPUtil.dp2px(mContext, 50) + statusBarHeight + picHeight - y < DP2SPUtil
						.dp2px(mContext, tvHeigh)) {
					params.setMargins((int) x, picHeight + picHeightEdgeDistance - DP2SPUtil.dp2px(mContext, tvHeigh),
							0, 0);
					bean.setLabel_x("" + (float) (x - picWidthEdgeDistance) / (float) (picWidth));
					bean.setLabel_y("-" + (float) 0);
				} else {
					params.setMargins((int) x, (int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight, 0, 0);
					bean.setLabel_x("" + (float) (x - picWidthEdgeDistance) / (float) (picWidth));
					if ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight - picHeightEdgeDistance > picHeight
							/ 2) {
						bean.setLabel_y("-" + (float) (picHeight
								- ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight - picHeightEdgeDistance)
								- h) / (float) picHeight);
					} else {
						bean.setLabel_y("" + (float) ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight
								- picHeightEdgeDistance) / (float) picHeight);
					}
				}

			} else {
				bean.setDirection("1");
				if (resultCode == 10000) {
					tv.setBackgroundResource(R.drawable.issue_tag_right);
				} else if (resultCode == 10001) {
					tv.setBackgroundResource(R.drawable.right_shop_buy);
				}
				// tv.setPadding(DP2SPUtil.dp2px(mContext, 6), 0,
				// DP2SPUtil.dp2px(mContext, 10), 0);
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				if (picHeightEdgeDistance + DP2SPUtil.dp2px(mContext, 50) + statusBarHeight + picHeight - y < DP2SPUtil
						.dp2px(mContext, tvHeigh)) {
					params.setMargins(0, picHeight + picHeightEdgeDistance - DP2SPUtil.dp2px(mContext, tvHeigh),
							(int) (screenWidth - x), 0);
					bean.setLabel_x("-" + (float) (screenWidth - x - picWidthEdgeDistance) / (float) picWidth);
					bean.setLabel_y("-" + (float) 0);
				} else {
					params.setMargins(0, (int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight,
							(int) (screenWidth - x), 0);
					bean.setLabel_x("-" + (float) (screenWidth - x - picWidthEdgeDistance) / (float) picWidth);
					LogYiFu.e("gggggg", "" + (float) (x - width - picWidthEdgeDistance));
					LogYiFu.e("gggggg", "" + ((float) (x - width - picWidthEdgeDistance) / (float) picWidth));
					// bean.setLabel_y("" + (float) ((int) y -
					// DP2SPUtil.dp2px(mContext, 50) - statusBarHeight
					// - picHeightEdgeDistance) / (float) picHeight);
					if ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight - picHeightEdgeDistance > picHeight
							/ 2) {
						bean.setLabel_y("-" + (float) (picHeight
								- ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight - picHeightEdgeDistance)
								- h) / (float) picHeight);
					} else {
						bean.setLabel_y("" + (float) ((int) y - DP2SPUtil.dp2px(mContext, 50) - statusBarHeight
								- picHeightEdgeDistance) / (float) picHeight);
					}
				}

			}
			LogYiFu.e("fristRadio", "tvWidth:" + width);
			LogYiFu.e("fristRadio", "xFrist:" + bean.getLabel_x());
			LogYiFu.e("fristRadio", "yFrist:" + bean.getLabel_y());
			// tv.setOnClickListener(new OnClickListener() {// 长按修改
			//
			// @Override
			// public void onClick(View v) {
			// for (int i = 0; i < mRlTagsContain.getChildCount(); i++) {
			// if(tv==mRlTagsContain.getChildAt(i)){
			// tvChildPosition=i;
			// break;
			// }
			// }
			// Intent intent = new Intent(mContext, SuppAndClassActivity.class);
			// startActivityForResult(intent, REQUEST_TAGS);
			// }
			// });
			tv.setOnTouchListener(new OnTouchListener() {

				int lastX, lastY;
				int downX, downY;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					int ea = event.getAction();
					Log.i("TAG", "Touch:" + ea);

					// Toast.makeText(DraftTest.this, "位置："+x+","+y,
					// Toast.LENGTH_SHORT).show();

					switch (ea) {
					case MotionEvent.ACTION_DOWN:
						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();
						downX = (int) event.getRawX();
						downY = (int) event.getRawY();
						break;
					/**
					 * layout(l,t,r,b) l Left position, relative to parent t Top
					 * position, relative to parent r Right position, relative
					 * to parent b Bottom position, relative to parent
					 */
					case MotionEvent.ACTION_MOVE:
						int dx = (int) event.getRawX() - lastX;
						int dy = (int) event.getRawY() - lastY;

						int left = v.getLeft() + dx;
						int top = v.getTop() + dy;
						int right = v.getRight() + dx;
						int bottom = v.getBottom() + dy;

						if (left < picWidthEdgeDistance) {
							left = picWidthEdgeDistance;
							right = left + v.getWidth();
						}
						
						if (right > screenWidth - picWidthEdgeDistance) {
							right = screenWidth - picWidthEdgeDistance;
							left = right - v.getWidth();
							LogYiFu.e("radio", "v.getWidth():" + (float) v.getWidth());
						}

						if (top < picHeightEdgeDistance) {
							top = picHeightEdgeDistance;
							bottom = top + v.getHeight();
						}
						Rect frame = new Rect();
						getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
						int statusBarHeight = frame.top;
						if (bottom > screenHeight - statusBarHeight - picHeightEdgeDistance) {
							bottom = screenHeight - statusBarHeight - picHeightEdgeDistance;
							top = bottom - v.getHeight();
						}
						LogYiFu.e("zzqYif", "screenHeight:" + screenHeight);
						LogYiFu.e("zzqYif", "bottom:" + bottom);
						LogYiFu.e("zzqYif", "statusBarHeight:" + statusBarHeight);
						LogYiFu.e("zzqYif", "and:" + (bottom + statusBarHeight));
						RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
						params2.setMargins(left, top, screenWidth - right, screenHeight - bottom - statusBarHeight); // 控件相对父控件左上右下的距离
						if (left > screenWidth / 2) {
							bean.setLabel_x(
									"-" + (float) (screenWidth - right - picWidthEdgeDistance) / (float) picWidth);
						} else {
							bean.setLabel_x("" + (float) (left - picWidthEdgeDistance) / (float) picWidth);
						}
						if (top - picHeightEdgeDistance > picHeight / 2) {
							bean.setLabel_y(
									"-" + (float) (picHeight - (bottom - picHeightEdgeDistance)) / (float) picHeight);
						} else {
							bean.setLabel_y("" + (float) (top - picHeightEdgeDistance) / (float) picHeight);
						}
						LogYiFu.e("radio", "xLeft:" + (float) left + picWidthEdgeDistance);
						LogYiFu.e("radio", "picWidthEdgeDistance:" + (float) picWidthEdgeDistance);
						LogYiFu.e("radio", "picWidth:" + (float) picWidth);
						if (left - picWidthEdgeDistance > picWidth / 2) {// 记录右边的位置
							LogYiFu.e("radio", "xRadio:" + (float) (left - picWidthEdgeDistance) / (float) picWidth);
						} else {
							LogYiFu.e("radio", "xRadio:" + (float) (left - picWidthEdgeDistance) / (float) picWidth);
						}
						LogYiFu.e("radio", "yRadio:" + (float) (top - picHeightEdgeDistance) / (float) picHeight);
						// params.width = btn_upload.getWidth();m
						// params.height = btn_upload.getHeight();
						tv.setLayoutParams(params2);
						// v.layout(left, top, right, bottom);

						Log.i("", "position：" + left + ", " + top + ", " + right + ", " + bottom);

						lastX = (int) event.getRawX();
						lastY = (int) event.getRawY();

						break;
					case MotionEvent.ACTION_UP:
						int dx2 = (int) event.getRawX() - downX;
						int dy2 = (int) event.getRawY() - downY;
						LogYiFu.e("dxxxx", "dx2:" + dx2);
						LogYiFu.e("dxxxx", "dy2:" + dy2);
						if (Math.abs(dx2) < 5 && Math.abs(dy2) < 5) {
							for (int i = 0; i < mRlTagsContain.getChildCount(); i++) {
								if (tv == mRlTagsContain.getChildAt(i)) {
									tvChildPosition = i;
									break;
								}
							}
							Intent intent = new Intent(mContext, SuppAndClassActivity.class);// 对标签进行修改
							intent.putExtra("label_name", "" + issueList.get(tvChildPosition).getLabel_name());
							intent.putExtra("type1", "" + issueList.get(tvChildPosition).getType1());
							intent.putExtra("type2", "" + issueList.get(tvChildPosition).getType2());
							intent.putExtra("style", "" + issueList.get(tvChildPosition).getStyle());
							intent.putExtra("label_type", "" + issueList.get(tvChildPosition).getLabel_type());
							if ("1".equals("" + issueList.get(tvChildPosition).getLabel_type())) {
								intent.putExtra("label_id", "" + issueList.get(tvChildPosition).getLabel_id());
							} else {
								intent.putExtra("label_id", "");
							}
							intent.putExtra("flagShop", issueList.get(tvChildPosition).getFlagShop());
							startActivityForResult(intent, REQUEST_MODFY);
						}
						break;
					}
					return true;
				}
			});

			mRlTagsContain.addView(tv, params);
		}
	}

	private boolean isHasUserDefined;

	private String changeArrayDateToJson() { // 把一个集合转换成json格式的字符串
		JSONArray jsonArray = null;
		JSONObject object = null;
		jsonArray = new JSONArray();
		object = new JSONObject();
		for (int i = 0; i < issueList.size(); i++) { // 遍历上面初始化的集合数据，把数据加入JSONObject里面
			JSONObject object2 = new JSONObject();// 一个user对象，使用一个JSONObject对象来装
			try {
				// 从集合取出数据，放入JSONObject里面
				// JSONObject对象和map差不多用法,以键和值形式存储数据
				if ("1".equals("" + issueList.get(i).getLabel_type())) {
					object2.put("label_id", issueList.get(i).getLabel_id());
				} else {
					isHasUserDefined = true;// 有用户自定义的品牌 传递到发布页面 ture时候 发布成功
											// 更新品牌数据库表格
				}
				if ("0".equals(issueList.get(i).getFlagShop())) {// 一般标签
					object2.put("label_name", issueList.get(i).getLabel_name());
					object2.put("type1", issueList.get(i).getType1());
					object2.put("type2", issueList.get(i).getType2());
					object2.put("style", issueList.get(i).getStyle());
				} else if ("1".equals(issueList.get(i).getFlagShop())) {// 商品标签
					object2.put("shop_code", issueList.get(i).getShop_code());
				}
				object2.put("label_type", issueList.get(i).getLabel_type());
				object2.put("label_x", issueList.get(i).getLabel_x());
				object2.put("label_y", issueList.get(i).getLabel_y());
				object2.put("direction", issueList.get(i).getDirection());
				jsonArray.put(object2); // 把JSONObject对象装入jsonArray数组里面
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// try {
		// object.put("jsonSuppLabel", jsonArray); //
		// 再把JSONArray数据加入JSONObject对象里面(数组也是对象)
		// // object.put("time", "2013-11-14");
		// // //这里还可以加入数据，这样json型字符串，就既有集合，又有普通数据
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		String jsonString = null;
		jsonString = jsonArray.toString(); // 把JSONObject转换成json格式的字符串
		LogYiFu.i("hck", "转换成json字符串: " + jsonString);
		return jsonString;
	}
}

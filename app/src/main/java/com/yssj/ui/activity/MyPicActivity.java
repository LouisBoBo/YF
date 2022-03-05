package com.yssj.ui.activity;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.LoadingDialog;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyPicActivity extends BasicActivity {
	private TextView tvTitle_base;
	private Context context;
	private com.yssj.custom.view.NoScrollGridView gv;
	public static List<HashMap<String, Object>> list;
	private ScrollView sl;
	private LinearLayout account_nodata;
	private Button btn_view_allcircle;
	private TextView tv_no_join;
	private TextView tv_qin;

	public static int width;
	public static int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pic);
		context = this;
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		sl = (ScrollView) findViewById(R.id.sl);
		account_nodata = (LinearLayout) findViewById(R.id.account_nodata);
		btn_view_allcircle = (Button) findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);
		tv_qin = (TextView) findViewById(R.id.tv_qin);
		tv_qin.setText("O(∩_∩)O~亲~");
		tv_no_join = (TextView) findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无图片喔~");

		findViewById(R.id.imgbtn_left_icon).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		tvTitle_base.setText("个人相册");
		gv = (com.yssj.custom.view.NoScrollGridView) findViewById(R.id.gv);



		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

		width = dm.widthPixels;
		height = dm.heightPixels;


		new SAsyncTask<Void, Void, List<HashMap<String, Object>>>((FragmentActivity) context, 0) {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				LoadingDialog.show((FragmentActivity) context);

			};

			@Override
			protected boolean isHandleException() {
				return true;
			};

			@Override
			protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, Void... params)
					throws Exception {
				return ModQingfeng.getMyPic(context);

			};

			@Override
			protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e && null != result) {
					if (result.size() == 0) {
						account_nodata.setVisibility(View.VISIBLE);
					} else {
						list = result;
						gv.setAdapter(new MyAdapter());
					}

				} else {
					// ToastUtil.showShortText(context, "数据--------异常");
				}

			};

		}.execute();

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_mypic, null);
				holder.iv = (ImageView) convertView.findViewById(R.id.iv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// PicassoUtils.initImage(context, ("myq/theme/" +
			// YCache.getCacheUser(context).getUser_id() + "/"
			// + list.get(position).get("pic") + "").split(":")[0], holder.iv);




			ViewGroup.LayoutParams lp = holder.iv.getLayoutParams();
			lp.width = width/4- DP2SPUtil.dp2px(context, 9);
			lp.height =  width/4-DP2SPUtil.dp2px(context, 9);
			holder.iv.setLayoutParams(lp);




			String pic = "";
			if ((list.get(position).get("theme_type") + "").equals("1")) {
				HashMap<String, Object> shop;
				shop = ((List<HashMap<String, Object>>) list.get(position).get("shop_list")).get(0);
				pic = list.get(position).get("pic") + "";
				pic = (shop.get("shop_code") + "").substring(1, 4) + "/" + shop.get("shop_code") + "/" + pic;
			
			} else {
				pic = ("myq/theme/" + (list.get(position).get("user_id") + "") + "/" + list.get(position).get("pic")
						+ "").split(":")[0];
			}

//			SetImageLoader.initImageLoader3(context, holder.iv, pic, "");
			PicassoUtils.initImage(context, pic+"!180", holder.iv);

			convertView.setOnClickListener(new MyAdapterListener(position));
			return convertView;
		}

		class MyAdapterListener implements OnClickListener {
			private int position;

			public MyAdapterListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(View v) {
				// 查看图片
				// context.startActivity(new Intent(context,
				// ShowMyPicActivity.class));
				Intent intent = new Intent(context, ShowMyPicActivity.class);
				intent.putExtra("position", position);
				context.startActivity(intent);

			}
		}

	}

	class ViewHolder {
		ImageView iv;
	}

}

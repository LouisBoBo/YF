package com.yssj.ui.fragment.payback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.EvaluateParam;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.orderinfo.OrderDetailsActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

/****
 * 申请平台介入界面
 * 
 * @author Administrator
 * 
 */
public class ApplyPlatformActivity extends BasicActivity {

	private LinearLayout img_back;
	private TextView tvTitle_base;
	private ImageView img_right_icon;
	private List<String> listPicPaths = new ArrayList<String>();// 上传的图片地址集合

	private HashMap<String, Object> map = new HashMap<String, Object>();

	private String content;// 问题描述内容

	private ReturnShop returnShop;// 退换货表
	private String mPicString;
	private ApplyPlatformFragment f;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		getActionBar().hide();
		setContentView(R.layout.activity_apply_platform);
		f = new ApplyPlatformFragment();
		// listPicPaths = getIntent().getStringArrayListExtra("listpicPath");
		getSupportFragmentManager().beginTransaction().add(R.id.fl_content, f)
				.commit();
		returnShop = (ReturnShop) getIntent().getSerializableExtra("order");

		initView();
	}

	private void initView() {

		ImageView img_product = (ImageView) findViewById(R.id.img_product1);
		TextView tv_product_name = (TextView) findViewById(R.id.tv_product_name);

		TextView tv_shop_num = (TextView) findViewById(R.id.tv_shop_num);
		TextView tv_price = (TextView) findViewById(R.id.tv_price);
		TextView tv_product_color = (TextView) findViewById(R.id.tv_product_color);
		TextView tv_product_size = (TextView) findViewById(R.id.tv_product_size);
		// TextView tv_status = (TextView) findViewById(R.id.tv_status);

		TextView meal = (TextView) findViewById(R.id.meal);

		String shop_name = returnShop.getShop_name();
		if (!TextUtils.isEmpty(shop_name)) {
			tv_product_name.setText(shop_name);
		}
		String pic = returnShop.getShop_code().substring(1, 4) + "/"
				+ returnShop.getShop_code() + "/" + returnShop.getPic();
		if (-1 == returnShop.getOrder_shop_id()) {
			meal.setVisibility(View.VISIBLE);
			tv_product_color.setVisibility(View.GONE);
			tv_product_size.setVisibility(View.GONE);
			if (returnShop.getShop_num() > 1) {
				meal.setText("超值套餐");
			} else {
				meal.setText("超值单品");
			}
			pic = returnShop.getPic();
		} else {
			meal.setVisibility(View.GONE);
			tv_product_color.setVisibility(View.VISIBLE);
			tv_product_size.setVisibility(View.VISIBLE);
		}

		if (!TextUtils.isEmpty(pic)) {
//			SetImageLoader.initImageLoader(this, img_product, pic, "");
			PicassoUtils.initImage(context, pic, img_product);
		}

		tv_product_color.setText("颜色 : " + returnShop.getShop_color());
		tv_product_size.setText("尺寸 : " + returnShop.getShop_size());
		String price = new java.text.DecimalFormat("#0.00").format(returnShop
				.getShop_price());
		tv_price.setText("¥" + price);
		int num = returnShop.getShop_num();
		tv_shop_num.setText("数量" + num);

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("申请平台介入");
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);

		findViewById(R.id.submit).setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		/*
		 * 右上角点点点
		 */

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back: // 返回
			finish();
			break;

		case R.id.submit: // 提交申请

			submit(v);
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(this);

			break;

		default:
			break;
		}
	}

	private void commentRequest(View v) {
		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// method stub
				/*
				 * StringBuffer sbPic = new StringBuffer(); for (int i = 0; i <
				 * listUploadedUrl.size(); i++) {
				 * sbPic.append(listUploadedUrl.get(i).split("/")[2]); if (i !=
				 * listUploadedUrl.size() - 1) { sbPic.append(","); } }
				 */

				return ComModel2.applyPlatform(context, returnShop.id, content,
						mPicString);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if (null == e) {
					if (result != null && "1".equals(result.getStatus())) {
						ToastUtil.showShortText(context, result.getMessage());
						Intent intent = new Intent(ApplyPlatformActivity.this,
								ApplyPlatformFinishActivity.class);
						startActivity(intent);
						if (null != OrderDetailsActivity.instance) {
							OrderDetailsActivity.instance.finish();
						}
						finish();
					} else {
						ToastUtil.showShortText(context, result.getMessage());
					}
				}
			}

		}.execute();
	}

	/***
	 * 提交图片信息，提交文字信息
	 */
	private void submit(final View v) {
		// listPicPaths.clear();
		map = f.getData();
		content = (String) map.get("content");
		listPicPaths = (List<String>) map.get("listPicPath");
//		System.out.println("listPicPaths_________________________"
//				+ listPicPaths.size());
		if (TextUtils.isEmpty(content) || content.equals("")) {
			ToastUtil.showShortText(context, "描述能容不能为空");
			return;
		}
		if (content.length() > 300) {
			ToastUtil.showShortText(context, "描述不能超过三百个字");
			return;
		}

		if (listPicPaths == null || listPicPaths.isEmpty()
				|| listPicPaths.size() == 0) {
			// commentRequest(v);
			ToastUtil.showShortText(context, "上传照片不能为空");

			return;
		}

		new SAsyncTask<Void, Void, Void>(this, v, R.string.wait) {

			@Override
			protected boolean isHandleException() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			protected Void doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < listPicPaths.size(); i++) {
					try {
						System.out
								.println("listPicPaths_____________111____________"
										+ listPicPaths.get(i));
						// 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
						String SAVE_KEY = File.separator + "returnShop"
								+ File.separator + System.currentTimeMillis()
								+ ".jpg";

						// 取得base64编码后的policy
						String policy = UpYunUtils.makePolicy(SAVE_KEY,
								Uploader.EXPIRATION, Uploader.BUCKET);

						// 根据表单api签名密钥对policy进行签名
						// 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
						String signature = UpYunUtils.signature(policy + "&"
								+ Uploader.TEST_API_KEY);

						// 上传文件到对应的bucket中去。
						String string = Uploader.upload(policy, signature,
								Uploader.BUCKET, listPicPaths.get(i));
						// aa = Integer.valueOf(params[1]);
						sb.append(string);
						if (i != listPicPaths.size()) {
							sb.append(",");
						}
					} catch (UpYunException e) {
						e.printStackTrace();
					}
				}
				mPicString = sb.toString();
//				System.out.println("___//___" + mPicString);
				return super.doInBackground(context, params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result,
					Exception e) {
				super.onPostExecute(context, result, e);
				if (e == null) {
					commentRequest(v);
				}
			}

		}.execute();

	}
}

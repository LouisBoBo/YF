package com.yssj.ui.activity.shopdetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ShopCart;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.infos.StatusInfoActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.MD5Tools;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.QRCreateUtil;
import com.yssj.utils.SetImageLoader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentSuccessActivity extends BasicActivity implements
		OnClickListener {

	// private LinearLayout llContaint;
	private List<ShopCart> listGoods;
	private List<ShopCart> shareShopCats = new ArrayList<ShopCart>();
	private List<ImageView> ivSelectList = new ArrayList<ImageView>();
	private ShopCart shop;
	private String order_code;

	private boolean isMulti = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// aBar.hide();
		setContentView(R.layout.layout_success_payment);
		listGoods = (List<ShopCart>) getIntent().getSerializableExtra(
				"listGoods");

		/*((TextView) findViewById(R.id.tvTitle_base)).setText("分享购买");
		findViewById(R.id.img_back).setOnClickListener(this);
		findViewById(R.id.btn_share).setOnClickListener(this);

//		llContaint = (LinearLayout) findViewById(R.id.ll_containt);
		addView(llContaint, listGoods);

		llContaint = (LinearLayout) findViewById(R.id.ll_containt);*/

		/*
		 * ((TextView) findViewById(R.id.tvTitle_base)).setText("分享购买");
		 * findViewById(R.id.img_back).setOnClickListener(this);
		 * findViewById(R.id.btn_share).setOnClickListener(this); llContaint =
		 * (LinearLayout) findViewById(R.id.ll_containt);
		 */

		order_code = getIntent().getStringExtra("order_code");
		isMulti = getIntent().getBooleanExtra("isMulti", false);
		// addView(llContaint, listGoods);
		shop = listGoods.get(0);
		getShopLink();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
	}
	// 添加item
//	private void addView(LinearLayout llContaint2, List<ShopCart> listGoods2) {
//		llContaint2.removeAllViews();
//		for (int i = 0; i < listGoods2.size(); i++) {
//			ShopCart shopCart = listGoods2.get(i);
//			final View view = LayoutInflater.from(this).inflate(
//					R.layout.share_good_item, null);
//			LinearLayout llSelect = (LinearLayout) view
//					.findViewById(R.id.ll_select);
//			ImageView ivSelect = (ImageView) view.findViewById(R.id.iv_select);
//			ImageView ivProduct = (ImageView) view
//					.findViewById(R.id.img_pro_pic);
//			TextView tvSum = (TextView) view.findViewById(R.id.tv_sum);
//			TextView tvName = (TextView) view.findViewById(R.id.tv_pro_name);
//			TextView tvDescri = (TextView) view
//					.findViewById(R.id.tv_pro_descri);
//			TextView tvDiscount = (TextView) view
//					.findViewById(R.id.tv_pro_discount);
//			TextView tvPrice = (TextView) view.findViewById(R.id.tv_pro_price);
//
//			ivSelectList.add(ivSelect);
//			// 绑定数据
//			SetImageLoader.initImageLoader(this, ivProduct,
//					shopCart.getDef_pic(),"");
//			tvSum.setText("X" + shopCart.getShop_num());
//			tvName.setText(shopCart.getShop_name());
//			tvDiscount.setText("￥" + shopCart.getShop_se_price());
//			tvPrice.setText("￥"
//					+ new java.text.DecimalFormat("#0.00").format(shopCart
//							.getShop_price()));
//			tvDescri.setText("颜色-" + shopCart.getColor() + " 尺寸-"
//					+ shopCart.getSize());
//			// tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//			view.setTag(i);
//			llSelect.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					setSelected(view);
//				}
//			});
//
//			llContaint2.addView(view);
//		}
//	}

	// 改变勾选框的状态，实现单选
	protected void setSelected(View view2) {
		int pos = (Integer) view2.getTag();
		shop = listGoods.get(pos);
		LogYiFu.i("TAG", "shopcart= " + shop.toString());
		for (int i = 0; i < ivSelectList.size(); i++) {
			if (i == pos) {
				ivSelectList.get(i).setSelected(true);
			} else {
				ivSelectList.get(i).setSelected(false);
			}
		}

	}

	// 判断是否选择分享的内容
	public boolean isSelected() {
		for (int i = 0; i < ivSelectList.size(); i++) {
			ImageView view = ivSelectList.get(i);
			if (view.isSelected()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.img_back:// 返回上一层

			intent = new Intent(this, StatusInfoActivity.class);
			intent.putExtra("index", 1);
			startActivityForResult(intent, 10002);

			finish();
			break;
		case R.id.btn_share:// 分享
			if (!isSelected()) {
				Toast.makeText(this, "请选择分享的内容", Toast.LENGTH_SHORT).show();
				break;
			}
			LogYiFu.i("TAG", "下载要分享的图片==>");
			// getPicPath(shop.getShop_code(), null);
			getShopLink();

			break;

		default:
			break;
		}
	}

	private void download(View v, final String[] picList,
			final String shop_code, final HashMap<String, String> mapInfos) {
		new SAsyncTask<Void, Void, Void>(PaymentSuccessActivity.this, v,
				R.string.wait) {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				File fileDirec = new File(YConstance.savePicPath);
				if(!fileDirec.exists()){
					fileDirec.mkdir();
				}
				File[] listFiles = new File(YConstance.savePicPath).listFiles();
				if (listFiles.length != 0) {
					LogYiFu.e("TAG", "存在文件夹 删除中。。。。");
					for (File file : listFiles) {
						file.delete();
					}
				}
				// LogYiFu.i("TAG", "piclist=" + picList.length);
				List<String> pics = new ArrayList<String>();
				for (int j = 0; j < picList.length; j++) {
					if (!picList[j].contains("reveal_")
							&& !picList[j].contains("detail_")
							&& !picList[j].contains("real_")) {
						pics.add(picList[j]);
					}
				}
				int j = pics.size() + 1;
				if (pics.size() > 8) {
					j = 9;
				}
				for (int i = 0; i < j; i++) {
					if (i == j - 1) {
						/*
						 * ComModel2.saveQRCode(PaymentSuccessActivity.this,
						 * shop_code);
						 */
//						downloadPic(mapInfos.get("qr_pic"), 9);
						Bitmap bm = QRCreateUtil.createImage(
								mapInfos.get("QrLink"), 500, 700, mapInfos.get("shop_se_price"), PaymentSuccessActivity.this);//得到二维码图片
						QRCreateUtil.saveBitmap(bm, YConstance.savePicPath,
								MD5Tools.md5(String.valueOf(9)) + ".jpg");//保存二维码图片
						break;
					}
					downloadPic(shop_code.substring(1, 4)+"/"+shop_code+"/"+pics.get(i) + "!450", i);
				}
				return super.doInBackground(params);
			}

			@Override
			protected void onPostExecute(FragmentActivity context, Void result) {
				// TODO Auto-generated method stub
				// showShareDialog();
				super.onPostExecute(context, result);
				// getShopLink();
				Intent intent = new Intent(PaymentSuccessActivity.this,
						ShowShareActivity.class);
				intent.putExtra("shop_link", mapInfos.get("link"));
				intent.putExtra("content", mapInfos.get("content"));
				intent.putExtra("four_pic", mapInfos.get("four_pic"));
				intent.putExtra("order_code", order_code);
				if (isMulti) {
					intent.putExtra("is_g_code", true);
				}
				intent.putExtra("listGoods", (Serializable) listGoods);
				startActivity(intent);
				PaymentSuccessActivity.this.finish();
			}

		}.execute();
	}

	// 得到商品链接
	private void getShopLink() {
		new SAsyncTask<String, Void, HashMap<String, String>>(
				PaymentSuccessActivity.this) {

			@Override
			protected HashMap<String, String> doInBackground(
					FragmentActivity context, String... params)
					throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getShopLink(params[0], context, "true");
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result.get("status").equals("1")) {
					String[] picList = result.get("shop_pic").split(",");
					download(null, picList, shop.getShop_code(), result);
				} else if (result.get("status").equals("1050")) {// 表明 分享已经超过了
				// ToastUtil.showShortText(context, "您已达到了分享次数上限"); // 4次限制
					startActivity(new Intent(PaymentSuccessActivity.this,
							NoShareActivity.class));
					PaymentSuccessActivity.this.finish();
				}
				}
			}

		}.execute(shop.getShop_code());
	}

	private void downloadPic(String picPath, int i) {
		try {
			URL url = new URL(YUrl.imgurl + picPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
//			System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[8192];
			// 读取到的数据长度
			int len;
			// 输出的文件流 /sdcard/yssj/
			File file = new File(YConstance.savePicPath, MD5Tools.md5(String
					.valueOf(i)) + ".jpg");
			if (file.exists()) {
				file.delete();
			}
			LogYiFu.e("TAG", "多分享选择下载的图片。。。。");
			OutputStream os = new FileOutputStream(file);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);

			}
			LogYiFu.i("TAG", "下载完毕。。。file=" + file.toString());
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			LogYiFu.e("TAG", "下载失败");
			e.printStackTrace();
		}
	}

	/** 保存图片到硬盘中 */
	public void saveBitmap(final String picName, final Bitmap bm) {

		if (null != bm) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					LogYiFu.e("ss", "保存图片");
					File f = new File(YConstance.savePicPath, picName);
					if (f.exists()) {
						f.delete();
					}
					try {
						FileOutputStream out = new FileOutputStream(f);
						bm.compress(Bitmap.CompressFormat.PNG, 90, out);
						out.flush();
						out.close();
						LogYiFu.i("ss", "已经保存");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

}

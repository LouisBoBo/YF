package com.yssj.ui.activity.infos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.HorizontalListView;
import com.yssj.custom.view.RatingBarView;
import com.yssj.entity.Order;
import com.yssj.entity.OrderShop;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;

/****
 * 评价界面
 * 
 * @author Administrator
 * 
 */
public class EvaluateOrderActivity extends BasicActivity {
	
	private LinearLayout img_back;
	private TextView tvTitle_base;
	
	private HorizontalListView hv_content;		// 水平listView
	
	private ViewPager content_pager;
	
	private RadioGroup rg_no_color,rg_bx_beautify,rg_zg_ok,rg_xjb_good;

	private RatingBarView starView;		// 星级评论
	private EditText et_content;		// 评论内容
	
	private LinearLayout container;
	
	private int screenWidth, screenHeight;
	private ScrollView root;
	
	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
	
	private int star = 1;
	
	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL
	
	private Order order;
	private String content;
	
	private List<View> detailViews = new ArrayList<View>();
	
	private List<OrderShop> list = new ArrayList<OrderShop>();
	private  Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		context = this;
		order = (Order) getIntent().getSerializableExtra("order");
		if(order != null){
			list = order.getList();
		}
		
		new MyAdapter();
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		initView();
	}

	private void initView() {
		setContentView(R.layout.evaluate_order);
		root= (ScrollView) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("评价订单");
		
//		hv_content = (HorizontalListView2) findViewById(R.id.hv_content);
//		hv_content.setAdapter(new MyAdapter());
		
		content_pager = (ViewPager) findViewById(R.id.content_pager);
		content_pager.setAdapter(new MyPagerAdapter());
		
		
		starView = (RatingBarView) findViewById(R.id.starView);
		starView.setmClickable(true);
		starView.setBindObject(3);
		starView.setStar(3);
		starView.setOnRatingListener(new RatingBarView.OnRatingListener() {
			@Override
			public void onRating(Object bindObject, int RatingScore) {
				ToastUtil.showShortText(EvaluateOrderActivity.this,
						"bindObject : " + RatingScore);
			}
		});
		findViewById(R.id.choose_pic).setOnClickListener(this);
		findViewById(R.id.submit).setOnClickListener(this);
		container = (LinearLayout) findViewById(R.id.container);
		et_content = (EditText) findViewById(R.id.et_content);
	}
	
	
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return detailViews.size();
		}
		
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			
			hv_content = new HorizontalListView(EvaluateOrderActivity.this, null);
			hv_content.setAdapter(new MyAdapter());
			container.addView(hv_content);
			return detailViews.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}
	
	class MyAdapter extends BaseAdapter{

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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = View.inflate(context,R.layout.evaluate_order_list_item, null);
				holder.iv_goods_icon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
				holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
				holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
				holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.item_ll= (LinearLayout ) convertView.findViewById(R.id.item_ll);


				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			final OrderShop shop = (OrderShop) getItem(position);
				
//			SetImageLoader.initImageLoader(context, holder.iv_goods_icon,shop.getShop_pic(),"");
			PicassoUtils.initImage(context, shop.getShop_pic(), holder.iv_goods_icon);
			holder.tv_goods_name.setText(shop.getShop_name(0));
			
			holder.tv_color.setText("颜色:" + shop.getColor());
			holder.tv_size.setText("尺寸:" + shop.getSize());
			holder.tv_money.setText("¥ " + shop.getShop_price());
			holder.item_ll.setBackgroundColor(Color.WHITE);
			

			detailViews.add(convertView);
			return convertView;
		}
	}
	
	class ViewHolder {
		ImageView iv_goods_icon;
		TextView tv_goods_name,tv_color,tv_size,tv_money;
		LinearLayout item_ll;
	}

	private void addImageView(List<Bitmap> listBitmap) {
		container.removeAllViews();
		for (int i = 0; i < listBitmap.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(screenWidth / 3,
					LayoutParams.WRAP_CONTENT));
			imageView.setAdjustViewBounds(true);
			imageView.setImageBitmap(listBitmap.get(i));
			imageView.setScaleType(ScaleType.FIT_XY);
			container.addView(imageView);
		}
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:		// 返回
			finish();
			break;
		case R.id.choose_pic:	// 选择图片
			TakePhotoUtil.doPickPhotoAction(this);
			break;
		case R.id.submit:	// 发表评论
			submit(v);
			break;

		default:
			break;
		}
	}
	
	/***
	 * 提交图片信息，提交文字信息
	 */
	private void submit(final View v) {
		content = et_content.getText().toString().trim();
		
		for (int k = 0; k < listPicPath.size(); k++) {
			boolean isUpload = false;
			isUploads.add(isUpload);
			new SAsyncTask<String, Void, String>(this, v,R.string.wait) {

				private int aa = 0;

				@Override
				protected String doInBackground(FragmentActivity context,
						String... params) throws Exception {
					String string = null;

					try {
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
						string = Uploader.upload(policy, signature, Uploader.BUCKET,
								params[0]);
						aa = Integer.valueOf(params[1]);

					} catch (UpYunException e) {
						e.printStackTrace();
					}

					return string;
				}

				@Override
				protected void onPostExecute(FragmentActivity context,
						String result) {
					// TODO Auto-generated method stub
					super.onPostExecute(context, result);
					if (result != null) {
						LogYiFu.e("result", result);
						listUploadedUrl.add(result);
						isUploads.set(aa, true);
						boolean isUploaded = false;
						for (int i = 0; i < isUploads.size(); i++) {
							isUploaded = false;
							if (isUploads.get(i)) {
								isUploaded = true;
							}
						}

						if (isUploaded) {
							// 上传退款请求
							commentRequest(v);
						}
					}

				}

			}.execute(listPicPath.get(k), k + "");
		}
	}
	
	private void commentRequest(View v){
		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				StringBuffer sbPic = new StringBuffer();
				for (int i = 0; i < listUploadedUrl.size(); i++) {
					sbPic.append(listUploadedUrl.get(i).split("/")[2]);
					if(i != listUploadedUrl.size() -1){
						sbPic.append(",");
					}
				}
				return ComModel2.addComment(context, order.getId(), content, sbPic.toString(), order.getOrder_code(), star);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if(null == e){
				ToastUtil.showShortText(context, result.getMessage());
				}
				super.onPostExecute(context, result, e);
			}
			
		}.execute();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == TakePhotoUtil.RESULT_LOAD_IMAGE) {
				// BitmapFactory bf = BitmapFactory.decodeFile(pathName)
				// add_qx_pic.set

				final Uri originalUri = data.getData(); // 获得图片的uri
				String path;
				if (originalUri.getScheme().equals("content")) {
					path = TakePhotoUtil.getRealPathFromURI(originalUri,EvaluateOrderActivity.this);
				} else {
					path = originalUri.getPath();
				}
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				listBitmap.add(bm);
				listPicPath.add(path);
				addImageView(listBitmap);
				// new_parklot_pic_path = path;
				// add_parklot_pic.setImageBitmap(bm);
				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
				// newParkLotPicSize = new File(new_parklot_pic_path).length();

			} else if (requestCode == TakePhotoUtil.RESULT_LOAD_PICTURE) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				String path = YConstance.saveUploadPicPath + TakePhotoUtil.fileImageName;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				listBitmap.add(bm);
				addImageView(listBitmap);
				listPicPath.add(path);
				// new_parklot_pic_path = Pic_location.PHOTO_DIR +
				// fileImageName;
				// add_parklot_pic.setImageBitmap(bm);
				// add_parklot_pic.setScaleType(ScaleType.FIT_XY);
				//
				// newParkLotPicSize = new File(new_parklot_pic_path).length();
				// bm.recycle();
			}
		}
	}

}

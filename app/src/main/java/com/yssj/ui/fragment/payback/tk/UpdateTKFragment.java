package com.yssj.ui.fragment.payback.tk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.PubImage;
import com.yssj.custom.view.PubImage.onDeteleImgLintener;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.ReturnShop;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.ui.activity.payback.PayBackDetailsActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.ui.fragment.payback.hh.HHFragment;
import com.yssj.ui.fragment.payback.thtk.THFragment;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

/**
 * 退款Fragment
 * @author Administrator
 *
 */
@SuppressLint("NewApi") public class UpdateTKFragment extends BaseFragment implements onDeteleImgLintener{
	
	private static final int RESULT_OK = -1;
	private TextView tvTitle_base;
	private LinearLayout img_back;
	private ImageView img_right_icon;
	
	private EditText et_apply_service,et_wl_status,et_tk_exlpain;
	private Button btn_submit_apply;
	
	private String order_code;		// 订单编号
	private String return_type = "";
	
	private PopupWindow popupWindow;
	private ListView listView;
	private List<String> listService = new ArrayList<String>();
	private List<String> listTK = new ArrayList<String>();
	private MyAdapter mAdapter;
	
	private ImageView iv_upload_pz;
	
	private static int RESULT_LOAD_IMAGE = 3;
	private static int RESULT_LOAD_PICTURE = 4;

	private String fileImageName;
	private LinearLayout container;
	private int screenWidth, screenHeight;

	private List<Bitmap> listBitmap = new ArrayList<Bitmap>();// 上传的图片集合
	private List<String> listPicPath = new ArrayList<String>();// 上传的图片地址集合
	private List<Boolean> isUploads = new ArrayList<Boolean>();// 是否上传的标识集合
	private List<String> listUploadedUrl = new ArrayList<String>();// 上传图片之后返回的URL
	private String order_price;
	private ReturnShop returnShop = new ReturnShop();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		
		Bundle bundle = getArguments();
		if(bundle != null){
			returnShop = (ReturnShop) bundle.getSerializable("returnShop");
			order_code  = returnShop.getOrder_code();	// 获取订单号
			order_price = String.valueOf(returnShop.getMoney());
		}
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.activity_payback_tk, null);
		
		tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("申请退款");
		img_back = (LinearLayout) view.findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		img_right_icon = (ImageView) view.findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
		
		et_apply_service = (EditText) view.findViewById(R.id.et_apply_service);		// 申请服务
		et_apply_service.setOnClickListener(this);
		
		et_wl_status = (EditText) view.findViewById(R.id.et_wl_status);			// 物流状态
		et_wl_status.setOnClickListener(this);
		if(returnShop.getOrder_shop_status() == 1){
			et_wl_status.setText("未发货");
		}else if(returnShop.getOrder_shop_status() == 2){
			et_wl_status.setText("已发货");
		}else if(returnShop.getOrder_shop_status() == 3){
			et_wl_status.setText("已签收");
		}
		
		et_tk_exlpain = (EditText) view.findViewById(R.id.et_tk_exlpain);		// 退款说明
		et_tk_exlpain.setText(returnShop.getExplain());
		
		btn_submit_apply = (Button) view.findViewById(R.id.btn_submit_apply);
		btn_submit_apply.setOnClickListener(this);
		
		iv_upload_pz = (ImageView) view.findViewById(R.id.iv_upload_pz);
		iv_upload_pz.setOnClickListener(this);
		
		container = (LinearLayout) view.findViewById(R.id.container);
		
		return view;
	}

	@Override
	public void initData() {
	
		
		listService.add("退货退款");
		listService.add("仅退款");
		listService.add("换货");
		
		
		listTK.add("已收到货");
		listTK.add("未收到货");
		
		initListView();
	}
	
	private void initListView(){
		listView = new ListView(context);
		
		listView.setBackgroundResource(R.drawable.payback_hh_selectdown_bg);
		listView.setVerticalScrollBarEnabled(false);  // 隐藏ListView滚动条
		listView.setDivider(null);
		
	}
	
	private void showSelectServiceDown(){
		
		mAdapter = new MyAdapter(listService, et_apply_service);
		listView.setAdapter(mAdapter);
		
		if(popupWindow == null){
			popupWindow = new PopupWindow(listView, et_apply_service.getWidth(),215);
		}
		
		// 要让其子view获取焦点，必须设置为true
		popupWindow.setFocusable(true);
		// 还必须设置一个背景图片，可以是空的
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击消失
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.showAsDropDown(et_apply_service,0,0);
	}
	
	private void showSelectWLDown(){
		mAdapter = new MyAdapter(listTK, et_wl_status);
		listView.setAdapter(mAdapter);
		
		
		if(popupWindow == null){
			popupWindow = new PopupWindow(listView, et_wl_status.getWidth(),274);
		}
		
		// 要让其子view获取焦点，必须设置为true
		popupWindow.setFocusable(true);
		// 还必须设置一个背景图片，可以是空的
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击消失
		popupWindow.setOutsideTouchable(true);
		
		popupWindow.showAsDropDown(et_wl_status,0,0);
	}
	
	
	@Override
	public void onClick(View v) {
		Fragment mFragment;
		Bundle bundle = new Bundle();
		bundle.putSerializable("returnShop", returnShop);
		switch (v.getId()) {
		case R.id.img_back:
			mFragment = new TKDetailOnlyFragment();
			mFragment.setArguments(bundle);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
			break;
		case R.id.et_apply_service:		// 下拉列表选择申请服务
			showSelectServiceDown();
			break;
		case R.id.et_wl_status:			// 下拉列表选择物流状态
			showSelectWLDown();
			break;
		case R.id.iv_upload_pz:			// 打开相册或者选择图片
			doPickPhotoAction();
			break;
		case R.id.btn_submit_apply:		// 提交申请
			submit(v);
			break;
		case R.id.img_right_icon:// 消息盒子
			WXminiAppUtil.jumpToWXmini(getActivity());

			break;
		}
	}
	
	
	class MyAdapter extends BaseAdapter{

		private List<String> list;
		private EditText et;
		public MyAdapter(List<String> list, EditText et){
			this.list = list;
			this.et = et;
		}
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view = View.inflate(context, R.layout.payback_hh_selectdown_item, null);
			TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
			tv_item.setText(list.get(position));
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					et.setText(list.get(position));
					popupWindow.dismiss();
					
					Bundle bundle = new Bundle();
					bundle.putString("order_price", order_price);
					bundle.putString("order_code", order_code);
					if("换货".equals(list.get(position))){
						Fragment mFragment = new HHFragment();
						mFragment.setArguments(bundle);
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
						
					}else if("退货退款".equals(list.get(position))){
						Fragment mFragment = new THFragment();
						mFragment.setArguments(bundle);
						getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
					}
				}
			});
			return view;
		}
		
	}
	
	
	/**
	 * 提交申请
	 */
	private void submitApply(){
		
		
		String service = et_apply_service.getText().toString();
		
		if("退货退款".equals(service)){
			return_type = "2" ;
		}else if("仅退款".equals(service)){
			return_type = "3" ;
		}else if("换货".equals(service)){
			return_type = "1" ;
		}
		
		final String cause = et_wl_status.getText().toString();
		final String explain = et_tk_exlpain.getText().toString();
		
		if(explain.length() > 200){
			ToastUtil.showLongText(context, "对不起，退款说明不能超过200字");
			return;
		}
		
		new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity)context, R.string.wait){

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				
				StringBuffer sbPic = new StringBuffer();
				
				for (int i = 0; i < listUploadedUrl.size(); i++) {
//					sbPic.append(listUploadedUrl.get(i).split("/")[2]);
					sbPic.append(listUploadedUrl.get(i));
					if(i != listUploadedUrl.size() -1){
						sbPic.append(",");
					}
				}
				
				String pic_list = sbPic.toString();
				
				return ComModel.updateReturnShop(context, explain , pic_list,"", return_type, order_code);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if (result != null && "1".equals(result.getStatus()) ) {
					
//					ToastUtil.showLongText(context, "申请退款成功");
					getActivity().finish();
					
					Intent intent = new Intent(context, PayBackDetailsActivity.class);
					intent.putExtra("order_code", order_code);
					context.startActivity(intent);
					
				}else{
					ToastUtil.showLongText(context, "糟糕，出错了~~~~");
				}
				}
			}
			
		}.execute();
	}
	
	
	
	/***
	 * 提交图片信息，提交文字信息
	 */
	private void submit(final View v) {
		
//		System.out.println("listPicPath:" + listPicPath);

		for (int k = 0; k < listPicPath.size(); k++) {
			boolean isUpload = false;
			isUploads.add(isUpload);
			new SAsyncTask<String, Void, String>((FragmentActivity)context, v) {

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
							// 上传凭证请求
							submitApply();
						}
					}

				}

			}.execute(listPicPath.get(k), k + "");
		}
	}
	
	/**
	 * 获取相片信息
	 */
	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(context,
				android.R.style.Theme_Light); // R.style.Dialog
		String[] choices;
		choices = new String[2];
		choices[0] = "拍照"; // 拍照
		choices[1] = "从相册中选择"; // 从相册中选择
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		builder.setTitle("");
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
								doTakePhoto();
							} else {
								ToastUtil.showLongText(context, "无内存卡");
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery();
							break;
						}
					}
				});
		builder.create().show();
	}
	
	/**
	 * 拍照获取图片
	 * 
	 */
	protected void doTakePhoto() {
		try {
			File mCurrentPhotoFile = new File(YConstance.saveUploadPicPath);
			if (!mCurrentPhotoFile.exists()) {
				boolean iscreat = mCurrentPhotoFile.mkdirs();// 创建照片的存储目录
			}

			fileImageName = System.currentTimeMillis()+"";

			mCurrentPhotoFile = new File(mCurrentPhotoFile, fileImageName);
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, RESULT_LOAD_PICTURE);
		} catch (ActivityNotFoundException e) {
			ToastUtil.showLongText(context, "photoPickerNotFoundText");
		}
	}
	
	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	private void addImageView(List<Bitmap> listBitmap) {
		container.removeAllViews();
		for (int i = 0; i < listBitmap.size(); i++) {
//			ImageView imageView = new ImageView(context);
//			imageView.setLayoutParams(new LayoutParams(screenWidth / 3,
//					LayoutParams.WRAP_CONTENT));
//			imageView.setAdjustViewBounds(true);
//			imageView.setImageBitmap(listBitmap.get(i));
//			imageView.setScaleType(ScaleType.FIT_XY);
//			container.addView(imageView);
			PubImage img=new PubImage(context,this);
			img.setTag(i);
			img.setBitmap(listBitmap.get(i));
			container.addView(img);
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == RESULT_LOAD_IMAGE) {
				// BitmapFactory bf = BitmapFactory.decodeFile(pathName)
				// add_qx_pic.set

				final Uri originalUri = data.getData(); // 获得图片的uri
				String path;
				if (originalUri.getScheme().equals("content")) {
					path = getRealPathFromURI(originalUri);
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

			} else if (requestCode == RESULT_LOAD_PICTURE) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				String path = YConstance.saveUploadPicPath + fileImageName;
				Bitmap bm = BitmapFactory.decodeFile(path, options);
				listBitmap.add(bm);
				addImageView(listBitmap);
				listPicPath.add(path);
			}
		}
	}

	private String getRealPathFromURI(Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA};
		Cursor cursor = context.getContentResolver().query(originalUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
		cursor.close();
		return path;
	}

	protected void doPickPhotoFromGallery() {
		try {
			Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, RESULT_LOAD_IMAGE);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		return intent;
	}

	@Override
	public void deleteImg(int index) {
		listBitmap.remove(index);
		listPicPath.remove(index);
		addImageView(listBitmap);
	}

}

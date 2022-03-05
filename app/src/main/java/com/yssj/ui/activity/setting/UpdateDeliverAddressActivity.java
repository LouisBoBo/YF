package com.yssj.ui.activity.setting;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;

public class UpdateDeliverAddressActivity extends BasicActivity {

	private EditText receiver_name, receiver_phone, zip_code, detail_address;
	private String receiverName, receiverPhone, zipCode, detailAddress;
	private TextView tv_area, tv_street;
	private String provinceId, cityId, areaId, streetId;
	private Button btn_save, btn_delete, btn_set_default, btn_right;
	private CheckBox is_default_addr;
	private RelativeLayout rel_tv_area, rel_delete,root;

	private TextView tvTitle_base;
	private LinearLayout img_back;

	private DeliveryAddress dlAddress;
	private DBService db = new DBService(this);
	private int is_default;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.update_deliver_address);
		dlAddress = (DeliveryAddress) getIntent().getSerializableExtra("item");
		initView();
	}

	private void initView() {
		root = (RelativeLayout)findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		btn_set_default = (Button) findViewById(R.id.btn_set_default);
		btn_set_default.setOnClickListener(this);

		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		if (0 == dlAddress.getIs_default()) {
			tvTitle_base.setText("收货地址");
			btn_set_default.setVisibility(View.VISIBLE);
		} else {
			tvTitle_base.setText("默认收货地址");
			btn_set_default.setVisibility(View.GONE);
		}
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setText("保存");
		btn_right.setTextColor(Color.parseColor("#222222"));
		btn_right.setTextSize(16);
		btn_right.setOnClickListener(this);

		receiver_name = (EditText) findViewById(R.id.receiver_name);
		receiver_phone = (EditText) findViewById(R.id.receiver_phone);
		zip_code = (EditText) findViewById(R.id.zip_code);
		detail_address = (EditText) findViewById(R.id.detail_address);
		tv_area = (TextView) findViewById(R.id.tv_area);
		tv_area.setOnClickListener(this);

		rel_tv_area = (RelativeLayout) findViewById(R.id.rel_tv_area);
		rel_tv_area.setOnClickListener(this);

		rel_delete = (RelativeLayout) findViewById(R.id.rel_delete);
		rel_delete.setOnClickListener(this);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		// btn_save = (Button) findViewById(R.id.btn_save);
		// btn_save.setOnClickListener(this);
		// btn_delete = (Button) findViewById(R.id.btn_delete);
		// btn_delete.setOnClickListener(this);
		/*
		 * is_default_addr = (CheckBox) findViewById(R.id.is_default_addr);
		 * 
		 * is_default_addr .setOnCheckedChangeListener(new
		 * OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton arg0, boolean
		 * arg1) { // TODO Auto-generated method stub if (arg1) {
		 * dlAddress.setIs_default(1); } else dlAddress.setIs_default(0); } });
		 */
		if (null != dlAddress) {
			receiver_name.setText(dlAddress.getConsignee());
			receiver_phone.setText(dlAddress.getPhone());
//			zip_code.setText(dlAddress.getPostcode());
			detail_address.setText(dlAddress.getAddress());
			StringBuffer sb = new StringBuffer();


			if(dlAddress.getProvince()!=null && 0 != dlAddress.getProvince()){

				sb.append(db
						.query("select * from areatbl where id="
								+ dlAddress.getProvince()).get(0).get("AreaName"));

			}



			if(dlAddress.getCity()!=null && 0 != dlAddress.getCity()){

				sb.append(db
						.query("select * from areatbl where id="
								+ dlAddress.getCity()).get(0).get("AreaName"));
			}









			if(dlAddress.getArea()!=null && 0 != dlAddress.getArea()){
				sb.append(db
						.query("select * from areatbl where id="
								+ dlAddress.getArea()).get(0).get("AreaName"));
				areaId = dlAddress.getArea() + "";
			}
			
			provinceId = dlAddress.getProvince() + "";
			cityId = dlAddress.getCity() + "";
			
			if (null != dlAddress.getStreet() && 0 != dlAddress.getStreet()) {
				streetId = dlAddress.getStreet() + "";
				sb.append(db
						.query("select * from areatbl where id="
								+ dlAddress.getStreet()).get(0).get("AreaName"));
			}
			tv_area.setText(sb.toString());
			// is_default_addr.setChecked(dlAddress.getIs_default() == 0 ? false
			// : true);
		}
	}

	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	private void saveAddress(View v) {
		receiverName = receiver_name.getText().toString().trim();
		receiverPhone = receiver_phone.getText().toString().trim();
//		zipCode = zip_code.getText().toString().trim();
		detailAddress = detail_address.getText().toString().trim();
		if (null == receiverName || "".equals(receiverName)) {
			receiver_name.requestFocus();
			Toast.makeText(this, "请输入收件人姓名", Toast.LENGTH_SHORT).show();
			return;
		}

		int len = 0;
		char[] nickchar = receiverName.toCharArray();
		if (isChinese(nickchar[0])) {
			for (int i = 0; i < nickchar.length; i++) {

				if (isChinese(nickchar[i])) {
					len += 2;
				} else {
					len += 1;
				}
			}
			if (len < 4 || len > 8) {
				ToastUtil.showShortText(UpdateDeliverAddressActivity.this,
						"姓名只能由2-4个汉字或2-8个数字与字母组成");
				return;
			}
		} else if (RegisterFragment.getWordCount(receiverName) > 8
				|| RegisterFragment.getWordCount(receiverName) < 2) {
			receiver_name.requestFocus();
			ToastUtil.showShortText(UpdateDeliverAddressActivity.this,
					"姓名只能由2-4个汉字或2-8个数字与字母组成");
			return;
		}

		if (null == receiverPhone || "".equals(receiverPhone)) {
			receiver_phone.requestFocus();
			Toast.makeText(this, "请输入收件人电话", Toast.LENGTH_SHORT).show();
			return;
		}
//		if (null == zipCode || "".equals(zipCode)) {
//			zip_code.requestFocus();
//			Toast.makeText(this, "请输入邮政编码", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		if (!CheckStrUtil.isZipNO(zipCode)) {
//			zip_code.requestFocus();
//			Toast.makeText(this, "请输入正确的邮政编码", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		if (null == provinceId || null == cityId || null == areaId) {
		if (null == provinceId || null == cityId) {
			Toast.makeText(this, "请选择省市区", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == detailAddress || "".equals(detailAddress)) {
			detail_address.requestFocus();
			Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.containsEmoji(receiverName)
				|| StringUtils.containsEmoji(detailAddress)) {
			ToastUtil.showShortText(UpdateDeliverAddressActivity.this,
					"不能输入特殊字符");
			return;
		}
		if (RegisterFragment.getWordCount(detailAddress) > 50) {
			detail_address.requestFocus();
			ToastUtil.showShortText(UpdateDeliverAddressActivity.this,
					"详细地址不能超过50个字符");
			return;
		}

		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.updateReceiverAddr(context, provinceId,
						cityId, areaId, streetId, receiverName, receiverPhone,
						zipCode, detailAddress, dlAddress.getId(),
						dlAddress.getIs_default());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					if (result.getStatus().equals("1")) {
						Toast.makeText(UpdateDeliverAddressActivity.this,
								result.getMessage(), Toast.LENGTH_SHORT).show();
						UpdateDeliverAddressActivity.this.finish();
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(provinceId, cityId, areaId, streetId, receiverName,
				receiverPhone, zipCode, detailAddress);
	}

	// 设置默认地址
	private void setDefaultAddress(View v) {
		receiverName = receiver_name.getText().toString().trim();
		receiverPhone = receiver_phone.getText().toString().trim();
//		zipCode = zip_code.getText().toString().trim();
		detailAddress = detail_address.getText().toString().trim();
		if (null == receiverName || "".equals(receiverName)) {
			receiver_name.requestFocus();
			Toast.makeText(this, "请输入收件人姓名", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == receiverPhone || "".equals(receiverPhone)) {
			receiver_phone.requestFocus();
			Toast.makeText(this, "请输入收件人电话", Toast.LENGTH_SHORT).show();
			return;
		}
//		if (null == zipCode || "".equals(zipCode)) {
//			zip_code.requestFocus();
//			Toast.makeText(this, "请输入邮政编码", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		if (!CheckStrUtil.isZipNO(zipCode)) {
//			zip_code.requestFocus();
//			Toast.makeText(this, "请输入正确的邮政编码", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		if (null == provinceId || null == cityId || null == areaId) {
		if (null == provinceId || null == cityId) {
			Toast.makeText(this, "请选择省市区", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == detailAddress || "".equals(detailAddress)) {
			detail_address.requestFocus();
			Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
			return;
		}

		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.updateReceiverAddr(context, provinceId,
						cityId, areaId, streetId, receiverName, receiverPhone,
						zipCode, detailAddress, dlAddress.getId(), 1);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					if (result.getStatus().equals("1")) {
						Toast.makeText(UpdateDeliverAddressActivity.this,
								result.getMessage(), Toast.LENGTH_SHORT).show();
						UpdateDeliverAddressActivity.this.finish();
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(provinceId, cityId, areaId, streetId, receiverName,
				receiverPhone, zipCode, detailAddress);
	}

	// 删除地址
	private void deleteAddress(View v) {
		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.deleteReceiverAddr(context, dlAddress.getId());
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					ReturnInfo result, Exception e) {
				// TODO Auto-generated method stub
				if (null == e) {
					if (result.getStatus().equals("1")) {
						Toast.makeText(UpdateDeliverAddressActivity.this,
								result.getMessage(), Toast.LENGTH_SHORT).show();
						UpdateDeliverAddressActivity.this.finish();
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(provinceId, cityId, areaId, streetId, receiverName,
				receiverPhone, zipCode, detailAddress);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rel_tv_area:
		case R.id.tv_area:
			Intent intent = new Intent(this, UpdateAreaActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("item", dlAddress);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1001);

			break;
		case R.id.btn_right:
			saveAddress(v);
			break;
		case R.id.rel_delete:
			deleteDialog();
			break;
		case R.id.btn_set_default: // 设为默认地址
			setDefaultAddress(v);
			break;
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
	}

	private void deleteDialog() {
		AlertDialog.Builder builder = new Builder(this);
		// 自定义一个布局文件
		View view = View.inflate(this, R.layout.delete_address_dialog, null);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deleteAddress(v);
			}
		});

		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1001 && null != intent) {
			provinceId = cityId = areaId = streetId = null;

			StringBuffer sb = new StringBuffer();
			List<HashMap<String, String>> listData = (List<HashMap<String, String>>) intent
					.getSerializableExtra("list");
			for (int i = 0; i < listData.size(); i++) {
				HashMap<String, String> map = listData.get(i);
				sb.append(map.get("AreaName"));
				String id = map.get("id");
				switch (i) {
				case 0:
					provinceId = id;
					break;
				case 1:
					cityId = id;
					break;
				case 2:
					areaId = id;
					break;
				case 3:
					streetId = id;
					break;
				default:
					break;
				}
			}
			tv_area.setText(sb.toString());
		}
	}
}

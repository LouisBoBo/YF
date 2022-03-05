package com.yssj.ui.activity.setting;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.logins.RegisterFragment;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.CheckStrUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class SetDeliverAddressActivity extends BasicActivity {

	private EditText receiver_name, receiver_phone, zip_code, detail_address;
	private String receiverName, receiverPhone, zipCode, detailAddress;
	private TextView tv_area, tv_street;
	private String provinceId, cityId, areaId, streetId;
	private Button btn_save;
	private CheckBox is_default_addr;
	private int isDefault = 0;
	private RelativeLayout rel_tv_area, root;

	private TextView tvTitle_base;
	private LinearLayout img_back;
	private Button btn_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.set_deliver_address);
		initView();
	}

	private void initView() {
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("新建收货地址");
		btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setText("保存");
		btn_right.setTextColor(Color.parseColor("#222222"));
		btn_right.setTextSize(16);
		btn_right.setOnClickListener(this);
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		root = (RelativeLayout) findViewById(R.id.root);
		root.setBackgroundColor(Color.WHITE);
		receiver_name = (EditText) findViewById(R.id.receiver_name);
		receiver_phone = (EditText) findViewById(R.id.receiver_phone);

		String phone = YCache.getCacheUser(this).getPhone();
		if (null != phone && !("".equals(phone)) && !"null".equals(phone)) {
			receiver_phone.setText(phone);
		}
		zip_code = (EditText) findViewById(R.id.zip_code);
		detail_address = (EditText) findViewById(R.id.detail_address);
		tv_area = (TextView) findViewById(R.id.tv_area);
		rel_tv_area = (RelativeLayout) findViewById(R.id.rel_tv_area);
		rel_tv_area.setOnClickListener(this);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_save.setOnClickListener(this);
		/*
		 * is_default_addr = (CheckBox) findViewById(R.id.is_default_addr);
		 * is_default_addr.setOnCheckedChangeListener(new
		 * OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton arg0, boolean
		 * arg1) { // TODO Auto-generated method stub if(arg1){ isDefault = 1;
		 * }else isDefault = 0; } });
		 */

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
		zipCode = zip_code.getText().toString().trim();
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
				ToastUtil.showShortText(SetDeliverAddressActivity.this, "姓名只能由2-4个汉字或2-8个数字与字母组成");
				return;
			}
		} else if (RegisterFragment.getWordCount(receiverName) > 8 || RegisterFragment.getWordCount(receiverName) < 2) {
			receiver_name.requestFocus();
			ToastUtil.showShortText(SetDeliverAddressActivity.this, "姓名只能由2-4个汉字或2-8个数字与字母组成");
			return;
		}

		if (null == receiverPhone || "".equals(receiverPhone)) {
			receiver_phone.requestFocus();
			Toast.makeText(this, "请输入收件人电话", Toast.LENGTH_SHORT).show();
			return;
		}

		if (receiverPhone.length() != 11) {
			receiver_phone.requestFocus();
			Toast.makeText(this, "请输入正确的收件人电话", Toast.LENGTH_SHORT).show();
			return;
		}
		// TODO:可以不要邮编
		// if (null == zipCode || "".equals(zipCode)) {
		// zip_code.requestFocus();
		// Toast.makeText(this, "请输入邮政编码", Toast.LENGTH_SHORT).show();
		// return;
		// }

		// if (null != zipCode &&!( "".equals(zipCode))) {
		// if (!CheckStrUtil.isZipNO(zipCode)) {
		// zip_code.requestFocus();
		// Toast.makeText(this, "请输入正确的邮政编码", Toast.LENGTH_SHORT).show();
		// return;
		// }
		// }

		// if (null == provinceId || null == cityId || null == areaId) {
		if (null == provinceId || null == cityId) {
			Toast.makeText(this, "请选择省市区", Toast.LENGTH_SHORT).show();
			return;
		}
		if (null == detailAddress || "".equals(detailAddress)) {
			detail_address.requestFocus();
			Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
			return;
		}
		if (StringUtils.containsEmoji(receiverName) || StringUtils.containsEmoji(detailAddress)) {
			ToastUtil.showShortText(SetDeliverAddressActivity.this, "不能输入特殊字符");
			return;
		}

		if (RegisterFragment.getWordCount(detailAddress) > 50) {
			detail_address.requestFocus();
			ToastUtil.showShortText(SetDeliverAddressActivity.this, "详细地址不能超过50个字符");
			return;
		}

		new SAsyncTask<String, Void, ReturnInfo>(this, v, R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context, String... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.addReceiverAddr(context, provinceId, cityId, areaId, streetId, receiverName,
						receiverPhone, zipCode, detailAddress, isDefault);
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
						Toast.makeText(SetDeliverAddressActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
						
						
						
						try {

							InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
									Context.INPUT_METHOD_SERVICE);
							inputMethodManager.hideSoftInputFromWindow(
									SetDeliverAddressActivity.this.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);

						} catch (Exception ee) {
							ee.printStackTrace();
						}
						
						
						
						SetDeliverAddressActivity.this.setResult(1);
						SetDeliverAddressActivity.this.finish();
					}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute(provinceId, cityId, areaId, streetId, receiverName, receiverPhone, zipCode, detailAddress);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rel_tv_area:
			Intent intent = new Intent(this, SelectAreaActivity.class);
			startActivityForResult(intent, 1001);
			// provinceId = cityId = areaId = streetId = null;
			// tv_area.setText("省-市-区-街道");
			break;
		case R.id.btn_right:
			saveAddress(v);
			break;
		case R.id.img_back:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 1001 && null != intent) {
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

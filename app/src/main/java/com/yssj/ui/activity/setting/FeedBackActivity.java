package com.yssj.ui.activity.setting;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DeviceUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

public class FeedBackActivity extends BasicActivity {

	private EditText comment_edit;
	private Button btn_submit;

	private String submitContent;
	private String versionName;
	
	private ImageView iv_addpic;
	private LinearLayout img_back;
	private ImageView img_right_icon;

	private HashMap<String, String> mapRequest = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.feedback_activity);
		comment_edit = (EditText) findViewById(R.id.comment_edit);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
		
		img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
		img_right_icon.setVisibility(View.VISIBLE);
		img_right_icon.setImageResource(R.drawable.mine_message_center);
		img_right_icon.setOnClickListener(this);
		
//		iv_addpic = (ImageView) findViewById(R.id.iv_addpic);
//		iv_addpic.setOnClickListener(this);
		
		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("意见反馈");
		
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		img_right_icon.setVisibility(View.GONE);
	}

	private void submit(View v) {
		submitContent = comment_edit.getText().toString().trim();
		if(TextUtils.isEmpty(submitContent)){
			ToastUtil.showShortText(FeedBackActivity.this, "建议不能为空！");
			return;
		}
		
		if(StringUtils.containsEmoji(submitContent)){
			ToastUtil.showShortText(FeedBackActivity.this, "不能输入特殊字符");
			return;
		}	
		versionName = DeviceUtils.getVersionName(this);
		mapRequest.put("content", submitContent);
		mapRequest.put("version_no", versionName);
		UserInfo  usr = YCache.getCacheUserSafe(FeedBackActivity.this);
		if(null != usr){
			mapRequest.put("userid", usr.getUser_id()+"");
		}
		String token = YCache.getCacheToken(FeedBackActivity.this);
		if(null != token){
			mapRequest.put("token", token);
		}
		new SAsyncTask<Void, Void, ReturnInfo>(FeedBackActivity.this, v,
				R.string.wait) {

			@Override
			protected ReturnInfo doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.feedback(FeedBackActivity.this, mapRequest);
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
				if (result != null) {
					showToast(result.getMessage());
					if (result.getStatus().equals("1")) {
						FeedBackActivity.this.finish();
					}
				}
				}
				super.onPostExecute(context, result, e);
			}

		}.execute((Void[]) null);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_submit:
			submit(v);
			break;
		/*case R.id.iv_addpic:    // 添加
			break;*/
		case R.id.img_back:    // 添加
			finish();
			break;
		case R.id.img_right_icon:    // 右上角。。。
			WXminiAppUtil.jumpToWXmini(this);

			break;
		default:
			break;
		}
	}

}

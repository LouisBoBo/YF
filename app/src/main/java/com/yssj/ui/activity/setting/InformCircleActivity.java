//package com.yssj.ui.activity.setting;
//
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.entity.UserInfo;
//import com.yssj.huanxin.activity.ChatAllHistoryActivity;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.DeviceUtils;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//import com.yssj.utils.YCache;
//
//public class InformCircleActivity extends BasicActivity {
//
//	private Button btn_sure;
//
//	private String versionName;
//	private String content;
//	
//	private ImageView img_right_arrow1,img_right_arrow2,img_right_arrow3,img_right_arrow4,img_right_arrow5;
//	private LinearLayout img_back;
//	private RelativeLayout rel_about_sex,rel_about_adv,rel_about_repeat,rel_about_violence,rel_about_else;
//
//	private HashMap<String, String> mapRequest = new HashMap<String, String>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.inform_circle_activity);
//		btn_sure = (Button) findViewById(R.id.btn_sure);
//		btn_sure.setOnClickListener(this);
//		rel_about_sex = (RelativeLayout) findViewById(R.id.rel_about_sex);
//		rel_about_sex.setOnClickListener(this);
//		rel_about_adv = (RelativeLayout) findViewById(R.id.rel_about_adv);
//		rel_about_adv.setOnClickListener(this);
//		rel_about_repeat = (RelativeLayout) findViewById(R.id.rel_about_repeat);
//		rel_about_repeat.setOnClickListener(this);
//		rel_about_violence = (RelativeLayout) findViewById(R.id.rel_about_violence);
//		rel_about_violence.setOnClickListener(this);
//		rel_about_else = (RelativeLayout) findViewById(R.id.rel_about_else);
//		rel_about_else.setOnClickListener(this);
//		
//		img_right_arrow1 = (ImageView) findViewById(R.id.img_right_arrow1);
//		img_right_arrow2 = (ImageView) findViewById(R.id.img_right_arrow2);
//		img_right_arrow3 = (ImageView) findViewById(R.id.img_right_arrow3);
//		img_right_arrow4 = (ImageView) findViewById(R.id.img_right_arrow4);
//		img_right_arrow5 = (ImageView) findViewById(R.id.img_right_arrow5);
////		iv_addpic = (ImageView) findViewById(R.id.iv_addpic);
////		iv_addpic.setOnClickListener(this);
//		
//		TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("举报");
//		
//		
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//	}
//
//	private void submit(View v) {
//		
//		versionName = DeviceUtils.getVersionName(this);
//		mapRequest.put("content", content);
//		mapRequest.put("version_no", versionName);
//		UserInfo  usr = YCache.getCacheUserSafe(InformCircleActivity.this);
//		if(null != usr){
//			mapRequest.put("userid", usr.getUser_id()+"");
//		}
//		String token = YCache.getCacheToken(InformCircleActivity.this);
//		if(null != token){
//			mapRequest.put("token", token);
//		}
//		new SAsyncTask<Void, Void, ReturnInfo>(InformCircleActivity.this, v,
//				R.string.wait) {
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.feedback(InformCircleActivity.this, mapRequest);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo result, Exception e) {
//				// TODO Auto-generated method stub
//				if(null == e){
//				if (result != null) {
//					showToast(result.getMessage());
//					if (result.getStatus().equals("1")) {
//						InformCircleActivity.this.finish();
////						ToastUtil.showShortText(context, "举报成功！");
//					}
//				}
//				}
//				super.onPostExecute(context, result, e);
//			}
//
//		}.execute((Void[]) null);
//	}
//
//	@Override
//	public void onClick(View v) {
//		Intent intent = null;
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.btn_sure:
//			submit(v);
//			break;
//		case R.id.rel_about_sex:
//			clickListener(1);
//			break;
//		case R.id.rel_about_adv:
//			clickListener(2);
//			break;
//		case R.id.rel_about_repeat:
//			clickListener(3);
//			break;
//		case R.id.rel_about_violence:
//			clickListener(4);
//			break;
//		case R.id.rel_about_else:
//			clickListener(5);
//			break;
//		/*case R.id.iv_addpic:    // 添加
//			break;*/
//		case R.id.img_back:    // 添加
//			finish();
//			break;
//		default:
//			break;
//		}
//	}
//	private void clickListener(int pos){
//		switch(pos){
//		case 1:
//			content = "色情";
//			img_right_arrow1.setVisibility(View.VISIBLE);
//			img_right_arrow2.setVisibility(View.GONE);
//			img_right_arrow3.setVisibility(View.GONE);
//			img_right_arrow4.setVisibility(View.GONE);
//			img_right_arrow5.setVisibility(View.GONE);
//			break;
//		case 2:
//			content = "广告";
//			img_right_arrow1.setVisibility(View.GONE);
//			img_right_arrow2.setVisibility(View.VISIBLE);
//			img_right_arrow3.setVisibility(View.GONE);
//			img_right_arrow4.setVisibility(View.GONE);
//			img_right_arrow5.setVisibility(View.GONE);
//			break;
//		case 3:
//			content = "反动";
//			img_right_arrow1.setVisibility(View.GONE);
//			img_right_arrow2.setVisibility(View.GONE);
//			img_right_arrow3.setVisibility(View.VISIBLE);
//			img_right_arrow4.setVisibility(View.GONE);
//			img_right_arrow5.setVisibility(View.GONE);
//			break;
//		case 4:
//			content = "暴力";
//			img_right_arrow1.setVisibility(View.GONE);
//			img_right_arrow2.setVisibility(View.GONE);
//			img_right_arrow3.setVisibility(View.GONE);
//			img_right_arrow4.setVisibility(View.VISIBLE);
//			img_right_arrow5.setVisibility(View.GONE);
//			break;
//		case 5:
//			content = "其他";
//			img_right_arrow1.setVisibility(View.GONE);
//			img_right_arrow2.setVisibility(View.GONE);
//			img_right_arrow3.setVisibility(View.GONE);
//			img_right_arrow4.setVisibility(View.GONE);
//			img_right_arrow5.setVisibility(View.VISIBLE);
//			break;
//			
//		}
//	}
//
//}

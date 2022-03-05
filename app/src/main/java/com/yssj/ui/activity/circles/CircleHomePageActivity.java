package com.yssj.ui.activity.circles;

import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.data.DBService;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel2;
import com.yssj.utils.SetImageLoader;

public class CircleHomePageActivity extends FragmentActivity {
//	//private ImageView img_bg;
//	private RoundImageButton img_user;
//	private LinearLayout img_back,ll_head;
//	private TextView tvTitle_base,tv_nickname,tv_attention_count,tv_fans_count,tv_area,tv_birthday,tv_hobby,tv_person_sign;
//	private Button btn_sendmsg,btn_attention;
//
//	private Map<String,Object> map ;
//	private String user_id;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.activity_circle_home_page);
//		user_id = getIntent().getStringExtra("user_id");
//		
//		initView();
//		initData();
}
//	@Override
//	protected void onResume() {
//		super.onResume();
////		JPushInterface.onResume(this);   
//		MobclickAgent.onResume(this);
//	}
//
//	/*@Override
//	protected void onPause() {
//		super.onPause();
//		JPushInterface.onPause(this);
//		
//	}*/
//	
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
//	}
//	
//	private void initView() {
//		//img_bg = (ImageView) findViewById(R.id.img_bg);
//		img_user = (RoundImageButton)findViewById(R.id.img_user);
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		ll_head = (LinearLayout) findViewById(R.id.ll_head);
//		ll_head.setBackgroundColor(Color.TRANSPARENT);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("个人主页");
//		tv_nickname = (TextView) findViewById(R.id.tv_nickname);
//		tv_attention_count = (TextView) findViewById(R.id.tv_attention_count);
//		tv_fans_count = (TextView) findViewById(R.id.tv_fans_count);
//		tv_area = (TextView) findViewById(R.id.tv_area);
////		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
////		tv_hobby = (TextView) findViewById(R.id.tv_hobby);
////		tv_person_sign = (TextView) findViewById(R.id.tv_person_sign);
//		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
//		btn_sendmsg.setOnClickListener(this);
//		btn_attention = (Button) findViewById(R.id.btn_attention);
//		btn_attention.setOnClickListener(this);
//		
//	}
//	
//	/**查询个人主页*/
//	private void initData() {
//		new SAsyncTask<String, Void, Map<String,Object>>(this, R.string.wait){
//			@Override
//			protected Map<String, Object> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getCircleHomePager(context, user_id);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				map = result;
//				SetImageLoader.initImageLoader(context, img_user, (String)result.get("pic"),"");
//				tv_nickname.setText((String)result.get("nickname"));
//				tv_attention_count.setText("关注   " + (String)result.get("circle_count"));
//				tv_fans_count.setText("粉丝   " + (String)result.get("fans_count"));
////				tv_area.setText((String)result.get("person_sign"));
//				tv_birthday.setText((String)result.get("birthday"));
//				tv_person_sign.setText((String)result.get("person_sign"));
////				tv_hobby.setText((String)result.get("hobby"));
////				MyLogYiFu.e("射之前",result.toString());
////				
////				String city=DBService.getIntance().queryAreaNameById(Integer.parseInt((String) result.get("province")))+" "+DBService.getIntance().queryAreaNameById(Integer.parseInt((String) result.get("city")));
////				MyLogYiFu.e("设置后",city.toString());
////				tv_area.setText(city);
//	@Override
//	protected void onResume() {
//		super.onResume();
////		JPushInterface.onResume(this);   
//		MobclickAgent.onResume(this);
//	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}

	/*@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		
	}*/
	
	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		YJApplication.getLoader().stop();
//		MobclickAgent.onPause(this);
//	}
	
//	private void initView() {
//		//img_bg = (ImageView) findViewById(R.id.img_bg);
//		img_user = (RoundImageButton)findViewById(R.id.img_user);
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		ll_head = (LinearLayout) findViewById(R.id.ll_head);
//		ll_head.setBackgroundColor(Color.TRANSPARENT);
//		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
//		tvTitle_base.setText("个人主页");
//		tv_nickname = (TextView) findViewById(R.id.tv_nickname);
//		tv_attention_count = (TextView) findViewById(R.id.tv_attention_count);
//		tv_fans_count = (TextView) findViewById(R.id.tv_fans_count);
//		tv_area = (TextView) findViewById(R.id.tv_area);
////		tv_birthday = (TextView) findViewById(R.id.tv_birthday);
////		tv_hobby = (TextView) findViewById(R.id.tv_hobby);
////		tv_person_sign = (TextView) findViewById(R.id.tv_person_sign);
//		btn_sendmsg = (Button) findViewById(R.id.btn_sendmsg);
//		btn_sendmsg.setOnClickListener(this);
//		btn_attention = (Button) findViewById(R.id.btn_attention);
//		btn_attention.setOnClickListener(this);
//		
//	}
	
	/**查询个人主页*/
//	private void initData() {
//		new SAsyncTask<String, Void, Map<String,Object>>(this, R.string.wait){
//			@Override
//			protected Map<String, Object> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//				return ComModel2.getCircleHomePager(context, user_id);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					Map<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				map = result;
//				SetImageLoader.initImageLoader(context, img_user, (String)result.get("pic"),"");
//				tv_nickname.setText((String)result.get("nickname"));
//				tv_attention_count.setText("关注   " + (String)result.get("circle_count"));
//				tv_fans_count.setText("粉丝   " + (String)result.get("fans_count"));
////				tv_area.setText((String)result.get("person_sign"));
//				tv_birthday.setText((String)result.get("birthday"));
//				tv_person_sign.setText((String)result.get("person_sign"));
////				tv_hobby.setText((String)result.get("hobby"));
////				MyLogYiFu.e("射之前",result.toString());
////				
////				String city=DBService.getIntance().queryAreaNameById(Integer.parseInt((String) result.get("province")))+" "+DBService.getIntance().queryAreaNameById(Integer.parseInt((String) result.get("city")));
////				MyLogYiFu.e("设置后",city.toString());
////				tv_area.setText(city);
//				
//				if("1".equals(result.get("isNo"))){
//					btn_attention.setText("取消关注");
//				}else{
//					btn_attention.setText("关注");
//				}
//				}
//			}
//			
//		}.execute(user_id);
//	}
//	
//	/**关注与取消关注*/
//	private void attention() {
//		new SAsyncTask<String, Void, ReturnInfo>(this, R.string.wait){
//			@Override
//			protected ReturnInfo doInBackground(
//					FragmentActivity context, String... params)
//							throws Exception {
//				if("关注".equals(btn_attention.getText())){
//					return ComModel2.getCircleHomePagerAttention(context, (String)map.get("fol_user_id"));
//				}else{
//					return ComModel2.getCircleHomePagerUnAttention(context, (String)map.get("fol_user_id"));
//				}
//			}
//			
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo returnInfo, Exception e) {
//				super.onPostExecute(context, returnInfo, e);
//				if(null == e){
//				if("1".equals(returnInfo.getStatus())){
//					if("关注".equals(btn_attention.getText())){
//						btn_attention.setText("取消关注");
//						Toast.makeText(getApplicationContext(), "关注成功", 0).show();
//					}else{
//						btn_attention.setText("关注");
//						Toast.makeText(getApplicationContext(), "取消关注成功", 0).show();
//					}
//				}
//				}
//			}
//			
//		}.execute((String)map.get("fol_user_id"));
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.btn_sendmsg:
//			//Toast.makeText(getApplicationContext(), "发消息晚点再完善。。。。", 0).show();
//			break;
//		case R.id.btn_attention:
//			attention();
//			break;
//
//		default:
//			break;
//		}
//	}
//				if("1".equals(result.get("isNo"))){
//					btn_attention.setText("取消关注");
//				}else{
//					btn_attention.setText("关注");
//				}
//				}
//			}
//			
//		}.execute(user_id);
//	}
	
	/**关注与取消关注*/
//	private void attention() {
//		new SAsyncTask<String, Void, ReturnInfo>(this, R.string.wait){
//			@Override
//			protected ReturnInfo doInBackground(
//					FragmentActivity context, String... params)
//							throws Exception {
//				if("关注".equals(btn_attention.getText())){
//					return ComModel2.getCircleHomePagerAttention(context, (String)map.get("fol_user_id"));
//				}else{
//					return ComModel2.getCircleHomePagerUnAttention(context, (String)map.get("fol_user_id"));
//				}
//			}
//			
//			@Override
//			protected boolean isHandleException() {
//				return true;
//			}
//			
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					ReturnInfo returnInfo, Exception e) {
//				super.onPostExecute(context, returnInfo, e);
//				if(null == e){
//				if("1".equals(returnInfo.getStatus())){
//					if("关注".equals(btn_attention.getText())){
//						btn_attention.setText("取消关注");
//						Toast.makeText(getApplicationContext(), "关注成功", 0).show();
//					}else{
//						btn_attention.setText("关注");
//						Toast.makeText(getApplicationContext(), "取消关注成功", 0).show();
//					}
//				}
//				}
//			}
//			
//		}.execute((String)map.get("fol_user_id"));
//	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.btn_sendmsg:
//			//Toast.makeText(getApplicationContext(), "发消息晚点再完善。。。。", 0).show();
//			break;
//		case R.id.btn_attention:
//			attention();
//			break;
//
//		default:
//			break;
//		}
//	}

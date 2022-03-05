package com.yssj.ui.activity.circles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.protocol.h;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.HorizontalListView;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.ToLoginDialog;
import com.yssj.entity.ReturnInfo;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.payback.PayBackChoiceServiceFragment;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

public class CircleDetailNewActivity 


//extends BasicActivity implements OnClickListener
{
//	private ImageView img_bg;
//	private RoundImageButton img_user;
//	private TextView tv_ucount,tv_ncount,tv_dialog_count,tv_circle_rule_content,tvTitle_base,tv_title;
//	private LinearLayout img_back,ll_head;
//	private HorizontalListView lv_content;
//	private Map<String,List<HashMap<String, Object>>> result;
////	private MyAdapter adapter;
//	private ImageButton imgbtn_left_icon;
//	
//	private ImageView iv_apply_admin;	// 申请管理员
//	private AlertDialog dialog;
//	private ToLoginDialog loginDialog;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.circle_detail_new);
//		result = (Map<String,List<HashMap<String, Object>>>)getIntent().getSerializableExtra("item");
//		
////		initView();
//		
//	}
//
//
////	private void initView() {
////		img_bg = (ImageView) findViewById(R.id.img_bg);
////		img_user = (RoundImageButton) findViewById(R.id.img_user);
////		tv_title = (TextView) findViewById(R.id.tv_title);
////		tv_ucount = (TextView) findViewById(R.id.tv_ucount);
////		tv_ncount = (TextView) findViewById(R.id.tv_ncount);
////		tv_dialog_count = (TextView) findViewById(R.id.tv_dialog_count);
////		tv_circle_rule_content = (TextView) findViewById(R.id.tv_circle_rule_content);
////		tv_ucount = (TextView) findViewById(R.id.tv_ucount);
////		tv_ucount = (TextView) findViewById(R.id.tv_ucount);
////		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
////		tvTitle_base.setText("圈子详情");
////		imgbtn_left_icon = (ImageButton)findViewById(R.id.imgbtn_left_icon);
////		imgbtn_left_icon.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_return_back_icon));
////		tvTitle_base.setTextColor(getResources().getColor(R.color.white));
////		ll_head = (LinearLayout) findViewById(R.id.ll_head);
////		ll_head.setBackgroundColor(Color.TRANSPARENT);
////		img_back = (LinearLayout) findViewById(R.id.img_back);
////		img_back.setOnClickListener(this);
////		
////		lv_content = (HorizontalListView) findViewById(R.id.lv_content);
////		
////		iv_apply_admin = (ImageView) findViewById(R.id.iv_apply_admin);		// 申请管理员
////		iv_apply_admin.setOnClickListener(this);
////		
////		if(result != null){
////			SetImageLoader.initImageLoader(this, img_bg, (String)result.get("circlesData").get(0).get("bg_pic"),"");
////			SetImageLoader.initImageLoader(this, img_user, (String)result.get("circlesData").get(0).get("pic"),"!180");
////			tv_title.setText((String)result.get("circlesData").get(0).get("title"));
////			if(result.get("nCountData")==null||result.get("nCountData").size()==0){
////				tv_ncount.setText("0");
////			}else{
////			tv_ncount.setText((String)result.get("nCountData").get(0).get("count"));
////			}
////			if(result.get("uCountData")==null||result.get("uCountData").size()==0){
////				tv_ucount.setText("0");
////			}else{
////				tv_ucount.setText((String)result.get("uCountData").get(0).get("count"));
////			}
////			tv_dialog_count.setText("对话数:" +result.get("rnCountData").get(0).get("rn_count"));
////			tv_circle_rule_content.setText((String)result.get("circlesData").get(0).get("content"));
////			if(YJApplication.instance.isLoginSucess()){
////			UserInfo user = YCache.getCacheUser(context);
////			String user_id = user.getUser_id()+"";
////			iv_apply_admin.setVisibility(View.VISIBLE);
////			if(result.get("adminsData") != null && !result.get("adminsData").isEmpty()){
////				for(int j=0;j<result.get("adminsData").size();j++){
////					if(result.get("adminsData").get(j).equals(user_id)){
////						iv_apply_admin.setVisibility(View.GONE);
////					}
////				}
////				
////			}
//////				if("1".equals(result.get("adminsData").get(0).get("admin").toString())){
//////					iv_apply_admin.setVisibility(View.GONE);
//////				}else{
//////					iv_apply_admin.setVisibility(View.VISIBLE);
//////				}
////				
////			}
////			
////			if(adapter == null){
////				adapter = new  MyAdapter(result.get("adminsData"));
////				lv_content.setAdapter(adapter);
////			}else{
////				adapter.notifyDataSetChanged();
////			}
////			
////		}
////		
////	}
//	
////	class MyAdapter extends BaseAdapter{
////		private List<HashMap<String, Object>> list;
////		
////		public MyAdapter(List<HashMap<String, Object>> list) {
////			this.list = list;
////		}
////
////		@Override
////		public int getCount() {
////			return list.size();
////		}
////
////		@Override
////		public Object getItem(int position) {
////			return list.get(position);
////		}
////
////		@Override
////		public long getItemId(int position) {
////			return position;
////		}
////
////		@Override
////		public View getView(int position, View convertView, ViewGroup parent) {
////			ViewHolder holder;
////			if(convertView == null){
////				holder = new ViewHolder();
////				convertView = View.inflate(getApplication(), R.layout.circle_detail_new_item, null);
////				holder.img_user = (RoundImageButton) convertView.findViewById(R.id.img_user);
////				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
////				
////				convertView.setTag(holder);
////			}else{
////				holder = (ViewHolder) convertView.getTag();
////			}
////			final HashMap<String, Object> map = list.get(position);
////			
////			SetImageLoader.initImageLoader(getApplication(), holder.img_user,(String)map.get("pic"),"");
////			holder.tv_name.setText((String)map.get("nickname"));
////			
////			holder.img_user.setOnClickListener(new OnClickListener() {
////				
////				@Override
////				public void onClick(View v) {
////					Intent intent = new Intent(getApplicationContext(), CircleCommonFragmentActivity.class);
////					intent.putExtra("user_id", (String)map.get("user_id"));
////					intent.putExtra("flag", "circleHomePage");
////					startActivity(intent);
////				}
////			});
////			
////			return convertView;
////		}
////	}
//	
//	class ViewHolder{
//		RoundImageButton img_user;
//		TextView tv_name;
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.img_back:
//			finish();
//			break;
//		case R.id.iv_apply_admin:
//			if(!YJApplication.instance.isLoginSucess()){
//				
//				if(loginDialog==null){
//					loginDialog=new ToLoginDialog(context);
//				}
//				loginDialog.show();
//				return;
//			}
//			customDialog();
//			break;
//		}
//	}
//	
//	private void customDialog() {
//		AlertDialog.Builder builder = new Builder(this);
//		// 自定义一个布局文件
//		View view = View.inflate(this, R.layout.payback_esc_apply_dialog, null);
//		TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
//		tv_des.setText("是否要申请为管理员 ？");
//		
//		Button ok = (Button) view.findViewById(R.id.ok);
//		ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
//		Button cancel = (Button) view.findViewById(R.id.cancel);
//		
//		cancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// 把这个对话框取消掉
//				dialog.dismiss();
//			}
//		});
//		
//		ok.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				apply();
//				dialog.dismiss();
//			}
//		});
//		
//		dialog = builder.create();
//		dialog.setView(view, 0, 0, 0, 0);
//		dialog.show();
//	}
//	
//	
//	private void apply(){
//		new SAsyncTask<Void, Void, ReturnInfo>(this, R.string.wait){
//
//			@Override
//			protected ReturnInfo doInBackground(FragmentActivity context,
//					Void... params) throws Exception {
//				return ComModel.applyAdmin(context,result.get("circlesData").get(0).get("circle_id").toString());
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
//				super.onPostExecute(context, result, e);
//				if(null == e){
//				if(result != null && "1".equals(result.getStatus())){
//					ToastUtil.showShortText(context, result.getMessage());
//				}else{
//					ToastUtil.showShortText(context, "糟糕，出错了~~~");
//				}
//				}
//			}
//			
//		}.execute();
//	}

}

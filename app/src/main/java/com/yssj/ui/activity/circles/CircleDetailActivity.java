//package com.yssj.ui.activity.circles;
//
//import java.util.HashMap;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.yssj.YJApplication;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
////import com.yssj.ui.fragment.circles.PostListFragment;
//import com.yssj.utils.SetImageLoader;
//
//public class CircleDetailActivity extends BasicActivity {
////
////	private Button btn_circle_mem, btn_manager, btn_essence;// Ȧ��Ա������Ա��Ȧ����
////	private ImageView img_title;
////	private Button btn_add;// ����Ȧ��
////	private TextView circle_name, tv_post_sum, tv_circle_mem_sum;// Ȧ�����ƣ� ����������
////																	// Ȧ��Ա����
////	private HashMap<String, Object> map;
////	private FragmentManager fm;
////	private FragmentTransaction ft;
////	
////	private PostListFragment plf;
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
////		
////		plf = new PostListFragment((String) map.get("circle_id"));
////		initView();
////	}
////
////	private void initView() {
////		setContentView(R.layout.circle_detail);
////		// Ȧ��Ա
////		btn_circle_mem = (Button) findViewById(R.id.btn_circle_mem);
////		btn_circle_mem.setOnClickListener(this);
////		// ����Ա
////		btn_manager = (Button) findViewById(R.id.btn_manager);
////		btn_manager.setOnClickListener(this);
////		// Ȧ����
////		btn_essence = (Button) findViewById(R.id.btn_essence);
////		btn_essence.setOnClickListener(this);
////
////		img_title = (ImageView) findViewById(R.id.img_title);
////		// ����ȦȦ
////		btn_add = (Button) findViewById(R.id.btn_add);
////		btn_add.setOnClickListener(this);
////		String flag = getIntent().getStringExtra("flag");  //�����Ǵ��ĸ�Activity������
////		if("MyCircleFragment".equals(flag)){
////			btn_add.setVisibility(View.INVISIBLE);
////		}else if("AllCircleFragment".equals(flag)){
////			btn_add.setVisibility(View.VISIBLE);
////		}
////
////		circle_name = (TextView) findViewById(R.id.circle_name);// Ȧ����
////		tv_post_sum = (TextView) findViewById(R.id.tv_post_sum);// ������
////		tv_circle_mem_sum = (TextView) findViewById(R.id.tv_circle_mem_sum);// Ȧ�ӳ�Ա��
////		
////		circle_name.setText((CharSequence) map.get("title"));
////		tv_post_sum.setText((CharSequence) map.get("u_count"));
////		tv_circle_mem_sum.setText((CharSequence) map.get("n_count"));
////		
////		fm = this.getSupportFragmentManager();
////		ft = fm.beginTransaction();
////		ft.add(R.id.list_container, plf);
////		ft.commitAllowingStateLoss();
////		SetImageLoader.initRoundImageLoader(this, img_title, (String) map.get("pic"));
////		
////		if (YJApplication.getbigimagelimit() > YJApplication.getbigimagemax()) {
////			ImageLoader.getInstance().clearMemoryCache();
////			YJApplication.setbigimagelimit(0);
////			MyLogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
////					+ " getsdimage():" + YJApplication.getsdimage());
////		} else {
////			MyLogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
////					+ " getsdimage():" + YJApplication.getsdimage());
////			YJApplication
////					.setbigimagelimit(YJApplication.getbigimagelimit() + 1);
////		}
////		
////	}
////	
////
////	private void addCircle(View v){
////		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait){
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				// TODO Auto-generated method stub
////				return ComModel2.addCircle(context, (String) map.get("circle_id"));
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result, Exception e) {
////				super.onPostExecute(context, result,e);
////				if(null == e){
////				Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
////				}
////			}
////			
////		}.execute();
////	}
////	@Override
////	public void onClick(View v) {
////		// TODO Auto-generated method stub
////		Intent intent = null;
////		Bundle bundle = null;
////		super.onClick(v);
////		switch (v.getId()) {
////		case R.id.btn_circle_mem://Ȧ��Ա
////			intent = new Intent(this,CircleUserActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			intent.putExtra("admin", 0);//�ǹ���Ա
////			startActivity(intent);
////			break;
////		case R.id.btn_manager://����Ա
////			intent = new Intent(this,CircleUserActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			intent.putExtra("admin", 1);//����Ա
////			startActivity(intent);
////			break;
////		case R.id.btn_essence://Ȧ����
////			intent = new Intent(this, CircleEssenceActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			startActivity(intent);
////			break;
////		case R.id.btn_add://��Ȧ��
////			addCircle(v);
////			break;
////
////		default:
////			break;
////		}
////	}
//
//<<<<<<< .mine
//=======
//	private Button btn_circle_mem, btn_manager, btn_essence;// Ȧ��Ա������Ա��Ȧ����
//	private ImageView img_title;
//	private Button btn_add;// ����Ȧ��
//	private TextView circle_name, tv_post_sum, tv_circle_mem_sum;// Ȧ�����ƣ� ����������
//																	// Ȧ��Ա����
//	private HashMap<String, Object> map;
//	private FragmentManager fm;
//	private FragmentTransaction ft;
//	
//	private PostListFragment plf;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
//		
//		plf = new PostListFragment((String) map.get("circle_id"));
////		initView();
//	}
//
////	private void initView() {
////		setContentView(R.layout.circle_detail);
////		// Ȧ��Ա
////		btn_circle_mem = (Button) findViewById(R.id.btn_circle_mem);
////		btn_circle_mem.setOnClickListener(this);
////		// ����Ա
////		btn_manager = (Button) findViewById(R.id.btn_manager);
////		btn_manager.setOnClickListener(this);
////		// Ȧ����
////		btn_essence = (Button) findViewById(R.id.btn_essence);
////		btn_essence.setOnClickListener(this);
////
////		img_title = (ImageView) findViewById(R.id.img_title);
////		// ����ȦȦ
////		btn_add = (Button) findViewById(R.id.btn_add);
////		btn_add.setOnClickListener(this);
////		String flag = getIntent().getStringExtra("flag");  //�����Ǵ��ĸ�Activity������
////		if("MyCircleFragment".equals(flag)){
////			btn_add.setVisibility(View.INVISIBLE);
////		}else if("AllCircleFragment".equals(flag)){
////			btn_add.setVisibility(View.VISIBLE);
////		}
////
////		circle_name = (TextView) findViewById(R.id.circle_name);// Ȧ����
////		tv_post_sum = (TextView) findViewById(R.id.tv_post_sum);// ������
////		tv_circle_mem_sum = (TextView) findViewById(R.id.tv_circle_mem_sum);// Ȧ�ӳ�Ա��
////		
////		circle_name.setText((CharSequence) map.get("title"));
////		tv_post_sum.setText((CharSequence) map.get("u_count"));
////		tv_circle_mem_sum.setText((CharSequence) map.get("n_count"));
////		
////		fm = this.getSupportFragmentManager();
////		ft = fm.beginTransaction();
////		ft.add(R.id.list_container, plf);
////		ft.commitAllowingStateLoss();
////		SetImageLoader.initRoundImageLoader(this, img_title, (String) map.get("pic"));
////		
////		if (YJApplication.getbigimagelimit() > YJApplication.getbigimagemax()) {
////			ImageLoader.getInstance().clearMemoryCache();
////			YJApplication.setbigimagelimit(0);
////			MyLogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
////					+ " getsdimage():" + YJApplication.getsdimage());
////		} else {
////			MyLogYiFu.e("", "getbigimagelimit():" + YJApplication.getbigimagelimit()
////					+ " getsdimage():" + YJApplication.getsdimage());
////			YJApplication
////					.setbigimagelimit(YJApplication.getbigimagelimit() + 1);
////		}
////		
////	}
////	
////
////	private void addCircle(View v){
////		new SAsyncTask<Void, Void, ReturnInfo>(this, v, R.string.wait){
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					Void... params) throws Exception {
////				// TODO Auto-generated method stub
////				return ComModel2.addCircle(context, (String) map.get("circle_id"));
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					ReturnInfo result, Exception e) {
////				super.onPostExecute(context, result,e);
////				if(null == e){
////				Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
////				}
////			}
////			
////		}.execute();
////	}
////	@Override
////	public void onClick(View v) {
////		// TODO Auto-generated method stub
////		Intent intent = null;
////		Bundle bundle = null;
////		super.onClick(v);
////		switch (v.getId()) {
////		case R.id.btn_circle_mem://Ȧ��Ա
////			intent = new Intent(this,CircleUserActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			intent.putExtra("admin", 0);//�ǹ���Ա
////			startActivity(intent);
////			break;
////		case R.id.btn_manager://����Ա
////			intent = new Intent(this,CircleUserActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			intent.putExtra("admin", 1);//����Ա
////			startActivity(intent);
////			break;
////		case R.id.btn_essence://Ȧ����
////			intent = new Intent(this, CircleEssenceActivity.class);
////			bundle = new Bundle();
////			bundle.putSerializable("item", map);
////			intent.putExtras(bundle);
////			startActivity(intent);
////			break;
////		case R.id.btn_add://��Ȧ��
////			addCircle(v);
////			break;
////
////		default:
////			break;
////		}
////	}
//
//>>>>>>> .r26813
//}

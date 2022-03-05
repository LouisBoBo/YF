//package com.yssj.ui.activity.circles;
//
//import java.util.HashMap;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.entity.ReturnInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.fragment.circles.CommentListFragment;
//
//public class PostDetailActivity extends BasicActivity {
////
////	private HashMap<String, Object> map;
////	
////	private Button btn_post;
////	private EditText edit_post;
////	private FragmentManager fm;
////	private FragmentTransaction ft;
////	private CommentListFragment clf;
////	private String circle_id = null;
////	@Override
////	protected void onCreate(Bundle savedInstanceState) {
////		super.onCreate(savedInstanceState);
////		map = (HashMap<String, Object>) getIntent().getSerializableExtra("item");
////		fm = this.getSupportFragmentManager();
////		ft = fm.beginTransaction();
////		clf = new CommentListFragment((String) map.get("news_id"));
////		initData();
////		
////	}
////	
////	private void initData(){
////		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait){
////
////			@Override
////			protected HashMap<String, Object> doInBackground(
////					FragmentActivity context, Void... params) throws Exception {
////				return ComModel2.getPostInfo(context, (String) map.get("news_id"));
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(FragmentActivity context,
////					HashMap<String, Object> result, Exception e) {
////				super.onPostExecute(context, result, e);
////				if(null == e){
////				initView();
////				circle_id = (String) result.get("circle_id");
////				}
////			}
////			
////		}.execute();
////	}
////	
////	private void initView(){
////		setContentView(R.layout.post_detail);
////		btn_post = (Button) findViewById(R.id.btn_post);
////		btn_post.setOnClickListener(this);
////		edit_post = (EditText) findViewById(R.id.edit_post);
////		ft.add(R.id.frag_post_list, clf);
////		ft.commitAllowingStateLoss();
////	}
//	
////	private void postComment(View v){
////		String comment = edit_post.getText().toString().trim();
////		String news_id = (String) map.get("news_id");
////		
////		new SAsyncTask<String, Void, ReturnInfo>(PostDetailActivity.this, v, R.string.wait){
////
////			@Override
////			protected ReturnInfo doInBackground(FragmentActivity context,
////					String... params) throws Exception {
////				// TODO Auto-generated method stub
////				return ComModel2.postComment(context, params[0], params[1], params[2]);
////			}
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			}
////			
////			@Override
////			protected void onPostExecute(final FragmentActivity context,
////					final ReturnInfo result, Exception e) {
////				// TODO Auto-generated method stub
////				if(null == e){
////				if("100".equals(result.getStatus())){
////					runOnUiThread(new Runnable() {
////						
////						@Override
////						public void run() {
////							Toast.makeText(context, result.getMessage(), 0).show();
////						}
////					});
////				}else{
////					ft = fm.openTransaction();
////					clf = new CommentListFragment((String) map.get("news_id"));
////					ft.replace(R.id.frag_post_list, clf);
////					ft.commit();
////					edit_post.setText("");
////				}
////				}
////				super.onPostExecute(context, result, e);
////			}
////			
////		}.execute(news_id,comment,circle_id);
////	}
////	
////	@Override
////	public void onClick(View v) {
////		// TODO Auto-generated method stub
////		super.onClick(v);
////		switch (v.getId()) {
////		case R.id.btn_post:
////			postComment(v);
////			break;
////
////		default:
////			break;
////		}
////	}
//}

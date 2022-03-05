//package com.yssj.ui.activity.infos;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.shopdetails.RedPacketsPaymentActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.dialog.RedPackgeMatchDialog;
//import com.yssj.ui.dialog.RedPackgeMatchDialog.RedpackageMatchListener;
//import com.yssj.ui.fragment.MineShopFragment;
//import com.yssj.utils.SingleChoicePopupWindow;
//import com.yssj.ui.fragment.MineShopFragment;
//import com.yssj.utils.SingleChoicePopupWindow;
//import com.yssj.utils.StringUtils;
//import com.yssj.utils.ToastUtil;
//
//public class SendRedPacketsActivity extends BasicActivity implements
//		RedpackageMatchListener {
//	private LinearLayout img_back;
//	private EditText et_fill_money, et_packets_counts, et_packets_content;
//	private TextView tv_fill_money, tv_match_score;
//	private List<String> cancleOrderDatas = new ArrayList<String>();
//	private RelativeLayout rl_expectation;
//	private Button bt_submit;
//	private String counts, money, matchscore, content, uid;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		getActionBar().hide();
//		setContentView(R.layout.activity_send_redpackets);
//		uid = getIntent().getStringExtra("uid");
//		initView();
//	}
//
//	private void initView() {
//		bt_submit = (Button) findViewById(R.id.bt_submit);
//		bt_submit.setOnClickListener(this);
//		img_back = (LinearLayout) findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		rl_expectation = (RelativeLayout) findViewById(R.id.rl_expectation);
//		rl_expectation.setOnClickListener(this);
//		tv_match_score = (TextView) findViewById(R.id.tv_match_score);
//		tv_match_score.setOnClickListener(this);
//		tv_fill_money = (TextView) findViewById(R.id.tv_fill_money);
//		et_packets_counts = (EditText) findViewById(R.id.et_packets_counts);
//		et_packets_content = (EditText) findViewById(R.id.et_packets_content);
//		et_fill_money = (EditText) findViewById(R.id.et_fill_money);
//		et_fill_money.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				tv_fill_money.setText("¥" + et_fill_money.getText().toString());
//			}
//		});
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		super.onClick(arg0);
//		Intent intent = null;
//		switch (arg0.getId()) {
//		case R.id.img_back:// 返回按钮
//			finish();
//			break;
//		case R.id.rl_expectation:
//
//			// RedPackgeMatchDialog dialog = new RedPackgeMatchDialog(context,
//			// R.style.DialogStyle1,
//			//
//			//
//			// new RedpackageMatchListener() {
//			//
//			// @Override
//			// public void callBack(String match) {
//			// // TODO Auto-generated method stub
//			//
//			// }
//			// });
//			//
//
//			final RedPackgeMatchDialog dialog = new RedPackgeMatchDialog(this,
//					R.style.DialogStyle1);
//			dialog.setRedpackageListener(this);
//			dialog.show();
//
//			// tv_match_score.setText(RedPackgeMatchDialog.num);
//
//			// new Jifen200Dialog(getActivity(),
//			// R.style.DialogStyle1).show();
//
//			//
//			// final CharSequence[] charSequences =
//			// {"10","20","30","40","50","60","70","80","90","100"};
//			// MatchScoreDialog.Builder builder= new
//			// MatchScoreDialog.Builder(this);
//			//
//			// builder.setInverseBackgroundForced(true);
//			// builder.setItems(charSequences, new
//			// DialogInterface.OnClickListener() {
//			//
//			// @Override
//			// public void onClick(DialogInterface dialog, int which) {
//			// Toast.makeText(context, charSequences[which],
//			// Toast.LENGTH_SHORT).show();
//			// tv_match_score.setText(charSequences[which]);
//			// }
//			// }).show();
//			//
//
//			break;
//		case R.id.bt_submit:// 塞进红包
//			sendRedPackets();
//			break;
//		}
//
//	}
//
//	// private class MatchScoreDialog extends AlertDialog{
//	// private ListView listview;
//	// protected MatchScoreDialog(Context context) {
//	// super(context,R.style.my_invate_dialog);
//	// this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);
//	// setContentView(R.layout.match_score_popu_dialog);
//	// listview = (ListView) findViewById(R.id.lv_reason);
//	// listview.setAdapter(new Adapter());
//	//
//	// }
//	//
//	// private class Adapter extends BaseAdapter{
//	//
//	// @Override
//	// public int getCount() {
//	// // TODO Auto-generated method stub
//	// return 0;
//	// }
//	//
//	// @Override
//	// public Object getItem(int position) {
//	// // TODO Auto-generated method stub
//	// return null;
//	// }
//	//
//	// @Override
//	// public long getItemId(int position) {
//	// // TODO Auto-generated method stub
//	// return 0;
//	// }
//	//
//	// @Override
//	// public View getView(int position, View convertView, ViewGroup parent) {
//	// // TODO Auto-generated method stub
//	// return null;
//	// }
//	//
//	// }
//	//
//	// }
//
//	private void sendRedPackets() {
//		final String money = et_fill_money.getText().toString().trim();
//		final String counts = et_packets_counts.getText().toString().trim();
//		final String content = et_packets_content.getText().toString().trim();
//		final String matchscore = tv_match_score.getText().toString();
//		if (StringUtils.containsEmoji(content)) {
//			ToastUtil.showShortText(context, "语音文字不能包含特殊符号");
//			return;
//		}
//		if (content.length() < 5 || content.length() > 100) {
//			ToastUtil.showShortText(context, "语音文字只能输入5-100个汉字");
//			return;
//		}
//		
//		if(!checkNameChese(content)){
//			ToastUtil.showShortText(context, "语音文字只能是汉字");
//			return;
//		}
//
//		new SAsyncTask<Void, Void, HashMap<String, Object>>(this, R.string.wait) {
//
//			@Override
//			protected HashMap<String, Object> doInBackground(
//					FragmentActivity context, Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				return ComModel2.sendPackets(context, uid, matchscore, content,
//						money, counts);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//			@Override
//			protected void onPostExecute(FragmentActivity context,
//					HashMap<String, Object> result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null) {
//					if ("1".equals(result.get("status"))) {
//						Intent intent = new Intent(SendRedPacketsActivity.this,
//								RedPacketsPaymentActivity.class);
//						intent.putExtra("rp_id", result.get("rp_id") + "");
//						intent.putExtra("totlaAccount", money);
//						startActivity(intent);
//						finish();
//					} else {
//
//						ToastUtil.showShortText(context, result.get("message")
//								.toString());
//					}
//				}
//			}
//
//		}.execute();
//	}
//
//	@Override
//	public void callBack(String match) {
//		tv_match_score.setText(match);
//	}
//
//	/**
//	 * 判定输入汉字
//	 * 
//	 * @param c
//	 * @return
//	 */
//	private boolean isChinese(char c) {
//		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
//				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
//				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 检测String是否全是中文
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public boolean checkNameChese(String name) {
//		boolean res = true;
//		char[] cTemp = name.toCharArray();
//		for (int i = 0; i < name.length(); i++) {
//			if (!isChinese(cTemp[i])) {
//				res = false;
//				break;
//			}
//		}
//		return res;
//	}
//
//}

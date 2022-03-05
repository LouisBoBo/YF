package com.yssj.ui.activity.infos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Help;
import com.yssj.model.ComModel2;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.WXminiAppUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HelpCenterActivity extends BasicActivity {

	private TextView tv_service_time;

	private LinearLayout lin_message_contact, lin_phone_contact,bangzhu;

	private TextView tvTitle_base, tv_phone_num;
	private LinearLayout img_back, container;
	private Context context;
	// private HelpListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.help_center);
		context = this;
		initView();
		initData();
	}

	private void initView() {
		bangzhu= (LinearLayout)findViewById(R.id.bangzhu);
		bangzhu.setBackgroundColor(Color.WHITE);
		tv_service_time = (TextView) findViewById(R.id.tv_service_time);
		tv_service_time.setText(Html
				.fromHtml(getString(R.string.tv_serverce_time)));
		lin_phone_contact = (LinearLayout) findViewById(R.id.lin_phone_contact);
		lin_phone_contact.setOnClickListener(this);
		lin_message_contact = (LinearLayout) findViewById(R.id.lin_message_contact);
		lin_message_contact.setOnClickListener(this);
		container = (LinearLayout) findViewById(R.id.container);

		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("帮助中心");

		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);

		tv_phone_num = (TextView) findViewById(R.id.tv_phone_num);

	}

	private void initData() {
		new SAsyncTask<Void, Void, HashMap<String, Object>>(
				HelpCenterActivity.this, R.string.wait) {

			@Override
			protected HashMap<String, Object> doInBackground(
					FragmentActivity context, Void... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getHelpList(context);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, Object> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result, e);
				// mAdapter = new HelpListAdapter(context, result);
				// list_help.setAdapter(mAdapter);
				if(null == e){
				addView(result);
				}
			}

		}.execute((Void[]) null);
	}

	private static LinkedHashMap<Integer, String> sortMapByValues(Map<Integer, String> aMap) { 
		 
        Set<Entry<Integer,String>> mapEntries = aMap.entrySet(); 
 
        // used linked list to sort, because insertion of elements in linked list is faster than an array list. 
        List<Entry<Integer,String>> aList = new LinkedList<Entry<Integer,String>>(mapEntries); 
 
        // sorting the List 
        Collections.sort(aList, new Comparator<Entry<Integer,String>>() { 
 
            @Override 
            public int compare(Entry<Integer, String> ele1, 
                    Entry<Integer, String> ele2) { 
 
                return ele1.getKey().compareTo(ele2.getKey()); 
            } 
        }); 
 
        // Storing the list into Linked HashMap to preserve the order of insertion. 
        LinkedHashMap<Integer,String> aMap2 = new LinkedHashMap<Integer, String>(); 
        for(Entry<Integer,String> entry: aList) { 
            aMap2.put(entry.getKey(), entry.getValue()); 
        } 
 
       return aMap2;
 
    } 
	
	private void addView(HashMap<String, Object> map) {
		
		List<Help> helps = (List<Help>) map.get("helps");
		LinkedHashMap<Integer, String> mapStr = (LinkedHashMap<Integer, String>) map
				.get("helpType");
		mapStr = sortMapByValues(mapStr);
		Iterator<Entry<Integer, String>> iterator = mapStr.entrySet().iterator();
		while (iterator.hasNext()) {
			
			Map.Entry<Integer, String> entry = iterator.next();
			TextView title = new TextView(this);
			LayoutParams paramsT = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			title.setLayoutParams(paramsT);
			title.setGravity(Gravity.CENTER_VERTICAL);

			title.setPadding(25, 15, 0, 15);
			title.setTextSize(20);
			title.setBackgroundColor(getResources().getColor(R.color.f4f4f4));
			title.setTextColor(getResources().getColor(R.color.all2));
			title.setText(entry.getValue());
			container.addView(title);
			
			
			for(int j = 0; j < helps.size(); j++){
				if(helps.get(j).getType() == entry.getKey()){
					final Help help = helps.get(j);
					TextView tView = new TextView(this);

					LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);

					params.setMargins(20, 10, 20, 10);
					tView.setLayoutParams(params);
					tView.setGravity(Gravity.CENTER_VERTICAL);

					tView.setPadding(0, 15, 0, 15);
					tView.setTextSize(17);
					tView.setText(help.getQuestion());

					tView.setTextColor(getResources().getColor(R.color.black));
					tView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context,
									HelpQuestionActivity.class);
							intent.putExtra("id", help.getId() + "");
							intent.putExtra("questinon", help.getQuestion());
							intent.putExtra("answer", help.getAnswer());
							context.startActivity(intent);
						}
					});

					container.addView(tView);
				}
			}

			View view = new View(context);
			LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
					1);
			view.setLayoutParams(params2);
			view.setBackgroundResource(R.color.line_color);
			container.addView(view);
		}
	}

	/*// 添加热门问题
	protected void addView(List<Help> helpList) {

		for (int i = 0; i < helpList.size(); i++) {
			final Help help = helpList.get(i);
			int type = help.getType();

			TextView tView = new TextView(this);

			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);

			params.setMargins(15, 5, 15, 5);
			tView.setLayoutParams(params);
			tView.setGravity(Gravity.CENTER_VERTICAL);

			tView.setPadding(0, 15, 0, 15);
			tView.setTextSize(17);
			tView.setText(help.getQuestion());

			tView.setTextColor(getResources().getColor(R.color.black));
			tView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,
							HelpQuestionActivity.class);
					intent.putExtra("id", help.getId() + "");
					intent.putExtra("questinon", help.getQuestion());
					intent.putExtra("answer", help.getAnswer());
					context.startActivity(intent);
				}
			});

			View view = new View(context);
			LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT,
					1);
			view.setLayoutParams(params2);
			view.setBackgroundResource(R.color.line_color);

			switch (type) {
			case 1: // 账号问题
				if (llContaint1.getChildCount() != 0) {
					llContaint1.addView(view);
				}
				llContaint1.addView(tView);
				break;
			case 2: // 售前问题
				if (llContaint2.getChildCount() != 0) {
					llContaint2.addView(view);
				}
				llContaint2.addView(tView);
				break;
			case 3: // 售后问题 退货/退款
				if (llContaint3.getChildCount() != 0) {
					llContaint3.addView(view);
				}
				llContaint3.addView(tView);
				break;
			case 4: // 支付问题
				if (llContaint4.getChildCount() != 0) {
					llContaint4.addView(view);
				}
				llContaint4.addView(tView);
				break;
			case 5: // 我的店铺
				if (llContaint5.getChildCount() != 0) {
					llContaint5.addView(view);
				}
				llContaint5.addView(tView);
				break;
			case 6: // 特色服务
				if (llContaint6.getChildCount() != 0) {
					llContaint6.addView(view);
				}
				llContaint6.addView(tView);
				break;

			default:
				break;
			}
		}
	}*/

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.lin_message_contact:

//			intent = new Intent(this, ChatActivity.class);
//			intent.putExtra("userId", SharedPreferencesUtil.getStringData(context, "kefuNB", "0"));
//			startActivity(intent);
			WXminiAppUtil.jumpToWXmini(this);
			break;
		case R.id.lin_phone_contact:
			// intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+
			// "15675365752"));
			// startActivity(intent);
			String phoneNumber = tv_phone_num.getText().toString();
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ phoneNumber));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}

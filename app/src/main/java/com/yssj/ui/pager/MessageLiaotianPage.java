package com.yssj.ui.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasePager;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageLiaotianPage extends BasePager {
	private HashMap<String, Object> map;

	private InputMethodManager inputMethodManager;
	private ListView listView;
//	private ChatAllHistoryAdapter adapter;
	// private EditText query;
	private ImageButton clearSearch;
	public RelativeLayout errorItem;

	public TextView errorText;
	private LinearLayout dian;
	private ArrayList conversationList;

	private TextView tv_no_data;
	private ListView list;

	public MessageLiaotianPage(Context context) {
		super(context);

	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.pager_message_center, null);
		inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		errorItem = (RelativeLayout) view.findViewById(R.id.rl_error_item);
		errorText = (TextView) view.findViewById(R.id.tv_connect_errormsg);
		list = (ListView) view.findViewById(R.id.list);

		tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);
		dian = (LinearLayout) view.findViewById(R.id.dian);
		dian.setBackgroundColor(Color.WHITE);

		list.setVisibility(View.GONE);
		tv_no_data.setVisibility(View.VISIBLE);

		return view;
	}







	@Override
	public void initData() {

	}

}

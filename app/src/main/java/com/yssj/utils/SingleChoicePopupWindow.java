package com.yssj.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.adpter.SingleChoicePopuWindowAdapter;

public class SingleChoicePopupWindow extends PopupWindow implements OnClickListener{


	private Button btn_sure;
	private ListView lv_reason;
	private TextView tv_title;
	private View view;
	private SingleChoicePopuWindowAdapter mAdapter;
	private OnClickListener mOkListener;
	private LinearLayout pop_layout;
	
	public SingleChoicePopupWindow(Context context,List<String> listData) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.singlechoice_popu_dialog, null);
		btn_sure = (Button) view.findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(this);
		
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		
		lv_reason = (ListView) view.findViewById(R.id.lv_reason);
		mAdapter = new SingleChoicePopuWindowAdapter(context,listData,R.drawable.btn_style_select_reason); 
		lv_reason.setAdapter(mAdapter);
		pop_layout = (LinearLayout) view.findViewById(R.id.pop_layout);
		lv_reason.setOnItemClickListener(mAdapter);
		pop_layout.setBackgroundColor(Color.WHITE);
		this.setContentView(view);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = view.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});
		
	}
	
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	public String getSelectItem() {
		return mAdapter.getSelectItem();
	}
	
	public void setOnOKButtonListener(OnClickListener onClickListener) {
		mOkListener = onClickListener;
	}
	
	public void onButtonOK(View v) {
		if (mOkListener != null) {
			mOkListener.onClick(v);
		}
	}
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure:
			onButtonOK(v);
			break;
		}
	}

}

package com.yssj.custom.view;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yssj.activity.R;

/**
 * CategoryView.java
 * 
 * @author zimo2013
 * @see http://blog.csdn.net/zimo2013
 * 
 */
public class CategoryView extends LinearLayout implements
		OnCheckedChangeListener {
	private LayoutInflater inflater;

	public CategoryView(Context context) {
		this(context, null);
	}

	public CategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
	}

	/** 添加方法 */
	public void add(List<HashMap<String, String>> list) {
		if (list.size() > 0) {
			// 加载布局
			View view = inflater.inflate(R.layout.category_container, null);
			addView(view);

			RadioGroup radioGroup = (RadioGroup) view
					.findViewById(R.id.container);
			radioGroup.setPadding(15, 0, 0, 0);
			RadioButton bt = newRadioButton("全部");
			bt.setPadding(15, 5, 15, 5);
			bt.setGravity(Gravity.CENTER);
			bt.setId(0);
			radioGroup.addView(bt);

			// 默认选中
			radioGroup.check(bt.getId());
			// 全部
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, String> map = list.get(i);
				bt = newRadioButton(map.get("sort_name"));// 实例化新的RadioButton
				bt.setId(i + 1);
				bt.setPadding(15, 5, 15, 5);
				bt.setGravity(Gravity.CENTER);
				radioGroup.addView(bt);
			}
			// 为当前RadioGroup设置监听器
			radioGroup.setOnCheckedChangeListener(this);
		}
	}

	/** 创建RadioButton */
	private RadioButton newRadioButton(String text) {
		RadioButton button = new RadioButton(getContext());

		RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
				RadioGroup.LayoutParams.WRAP_CONTENT,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		// 设置内外边距
		params.leftMargin = 6;
		params.rightMargin = 6;
		button.setLayoutParams(params);
		button.setPadding(4, 0, 4, 0);

		// 设置背景
		button.setBackgroundResource(R.drawable.selector_category_bg);
		// 去掉左侧默认的圆点
		button.setButtonDrawable(android.R.color.transparent);
		// 设置不同状态下文字颜色，通过ColorStateList，对应的selector放在res/color文件目录中，否则没有效果
		button.setTextColor(getResources().getColorStateList(
				R.color.selector_category_text));
		button.setGravity(Gravity.CENTER);
		button.setText(text);

		return button;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (mListener != null) {
			mListener.click(group, checkedId);
		}
	}

	/** 指定监听器 */
	public void setOnClickCategoryListener(OnClickCategoryListener l) {
		mListener = l;
	}

	private OnClickCategoryListener mListener;

	/** 回掉接口 */
	public interface OnClickCategoryListener {
		/** 点击事件发生 */
		public void click(RadioGroup group, int checkedId);
	}
}

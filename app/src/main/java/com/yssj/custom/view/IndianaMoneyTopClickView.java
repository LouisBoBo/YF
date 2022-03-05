package com.yssj.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.yssj.activity.R;

/**
 * 商品详情按钮
 * 
 * @author lbp
 * 
 */
public class IndianaMoneyTopClickView extends LinearLayout implements
		OnCheckedChangeListener {

	private OnCheckedLintener checkLintener;
	RadioGroup rg;
	RadioButton rb_evaluate;

	private Context context;

	int mEva_count; // 评价总数

	public IndianaMoneyTopClickView(Context context, int evacount) {
		super(context);
		this.mEva_count = evacount;
		this.context = context;
	}

	public void setCheckLintener(OnCheckedLintener checkLintener) {
		this.checkLintener = checkLintener;
	}

	public IndianaMoneyTopClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.indiana_money_top_view,
				this);
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.check(R.id.rb_evaluate);
		rg.setOnCheckedChangeListener(this);
		rb_evaluate = (RadioButton) findViewById(R.id.rb_evaluate);

	}

	public interface OnCheckedLintener {
		public void onCheck(int index);
	}

	public void setIndex(int index) {
		switch (index) {
		case 0: {
			rg.check(R.id.rb_details);
		}
			break;
		case 1: {
			rg.check(R.id.rb_size);
		}
			break;
		case 2: {
			rg.check(R.id.rb_evaluate);
		}
			break;

		default:
			break;
		}
	}

	public void setText() {
		RadioButton r = (RadioButton) findViewById(R.id.rb_evaluate);
		r.setText("热卖推荐");
	}

	public void setText2() {
		RadioButton r = (RadioButton) findViewById(R.id.rb_evaluate);
		r.setText("抽奖规则");
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		if (this.getVisibility() != View.VISIBLE) {
			return;
		}
		switch (arg1) {
		case R.id.rb_details:// 详情
		{
			checkLintener.onCheck(0);
//			MobclickAgent.onEvent(context, "goodsDetail");
		}
			break;

		case R.id.rb_size:// 尺寸
		{
			checkLintener.onCheck(1);
//			MobclickAgent.onEvent(context, "size");
		}
			break;
		case R.id.rb_evaluate:// 评价
		{
			checkLintener.onCheck(2);
//			MobclickAgent.onEvent(context, "evaluate");
		}
			break;
		default:
			break;
		}
	}

}

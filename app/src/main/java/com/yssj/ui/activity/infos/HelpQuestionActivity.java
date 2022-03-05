package com.yssj.ui.activity.infos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Help;
import com.yssj.model.ComModel;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

public class HelpQuestionActivity extends BasicActivity {

	
	private TextView tvTitle_base;
	private LinearLayout img_back,remen;

	private TextView tv_title,tv_anwser;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.help_question_detail);
		initView();
		
//		id = getIntent().getStringExtra("id");
//		getQuestionDetail();
		String question = getIntent().getStringExtra("questinon");
		String answer = getIntent().getStringExtra("answer").replace("\\n","\n");
		tv_title.setText(question);
		tv_anwser.setText(answer);
	}

	private void initView() {
		remen = (LinearLayout)findViewById(R.id.remen);
		remen.setBackgroundColor(Color.WHITE);
		tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
		tvTitle_base.setText("热门问题");
		
		img_back = (LinearLayout) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_anwser = (TextView) findViewById(R.id.tv_anwser);

	}

	
	/**
	 * 获取问题详情
	 */
	private void getQuestionDetail() {
		new SAsyncTask<Void, Void, Help>(HelpQuestionActivity.this,
				R.string.wait) {

			@Override
			protected Help doInBackground(FragmentActivity context,
					Void... params) throws Exception {
				return ComModel.getHelpQuestion(context, id);
			}

			@Override
			protected boolean isHandleException() {
				return true;
			}
			
			@Override
			protected void onPostExecute(FragmentActivity context,
					Help result, Exception e) {
				super.onPostExecute(context, result, e);
				if(null == e){
				if(result != null){
					tv_title.setText(result.getQuestion());
					tv_anwser.setText("\t\t" + result.getAnswer().replace("\\n","\n"));
				}else{
					ToastUtil.showLongText(HelpQuestionActivity.this, "糟糕，出错了~~~");
				}
				}
				
			}


		}.execute((Void[]) null);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		}
	}
}

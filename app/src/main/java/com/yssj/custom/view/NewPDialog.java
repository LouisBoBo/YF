package com.yssj.custom.view;

import com.yssj.activity.R;
import com.yssj.ui.activity.logins.LoginActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
/**
 * 新手流程弹出对话框
 * @author lbp
 *
 */
public class NewPDialog extends Dialog {
	
	private TaskLintener l;
	
	private FinishLintener f;
	
	

	public void setL(TaskLintener l) { 
		this.l = l;
		findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { //红包注释
			
			@Override
			public void onClick(View v) {
				dismiss();
				NewPDialog.this.l.onOKClickLintener();               
			}
		});
		
	}
	public void setF(FinishLintener f) {
		this.f = f;
		findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NewPDialog.this.dismiss();
				NewPDialog.this.f.onFinishClickLintener();
			}
		});
	}
	
	
	@Override
	public void show() {
		super.show();
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
	}
	
//	public static void yThread(final String action,final Context context){
//		i++;
//		new Thread(){
//			public void run() {
//				try {
//					Thread.sleep(30*1000*i);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				context.sendBroadcast(new Intent(action));
//			};
//		}.start();
//		
//	}
	
	
	public NewPDialog(final Context context,int ly) {
		super(context,R.style.new_people_dialogs);
		
		setContentView(ly);
		
		LayoutParams p=getWindow().getAttributes();
		
		p.width=context.getResources().getDisplayMetrics().widthPixels;
		p.height=context.getResources().getDisplayMetrics().heightPixels;
		
		getWindow().setAttributes(p);
		
		findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NewPDialog.this.dismiss();
			}
		});
		
		this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);
		setCanceledOnTouchOutside(false);
		if(findViewById(R.id.shouzhi)!=null){
			AnimationDrawable anim=(AnimationDrawable) ((ImageView)findViewById(R.id.shouzhi)).getDrawable();
			anim.start();
			findViewById(R.id.shouzhi).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dismiss();
					l.onShouZhiClickLintener();
				}
			});
		}
		
		if(findViewById(R.id.register)!=null){
			findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {  
					NewPDialog.this.dismiss();
					
					if (LoginActivity.instances != null){
						LoginActivity.instances .finish();
					}
					
					Intent i=new Intent(context, LoginActivity.class);
					i.putExtra("login_register", "register");
					((Activity)context).startActivityForResult(i,56);
				}
			});
		}
		
	}
	
	public interface TaskLintener{
		public void onOKClickLintener();
		public void onShouZhiClickLintener();
	}
	public interface FinishLintener{
		public void onFinishClickLintener();
	}
	
}

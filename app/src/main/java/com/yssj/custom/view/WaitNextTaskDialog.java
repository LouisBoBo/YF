package com.yssj.custom.view;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.yssj.activity.R;


/********************************************************************
 * [Summary]
 *       TODO 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * [Remarks]
 *       TODO 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 *******************************************************************/

public class WaitNextTaskDialog extends Dialog {
	private Context context = null;
	private static WaitNextTaskDialog customProgressDialog = null;

	public WaitNextTaskDialog(Context context){
		super(context);
		this.context = context;
	}

	public WaitNextTaskDialog(Context context, int theme) {
        super(context, theme);
    }
	
	
	public Context getmContext() {
		return context;
	}

	public static WaitNextTaskDialog createDialog(Context context){
		customProgressDialog = new WaitNextTaskDialog(context,R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.dialog_sign_wait_nexttask);


		customProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		//在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
		WindowManager.LayoutParams lp= customProgressDialog.getWindow().getAttributes();
		lp.dimAmount=0.25f;
		customProgressDialog.getWindow().setAttributes(lp);
		customProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


		AnimationDrawable anim=(AnimationDrawable) customProgressDialog.findViewById(R.id.loadingImageView).getBackground();
		anim.start();
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		customProgressDialog.setCancelable(false);
		return customProgressDialog;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		customProgressDialog=null;
	}


}

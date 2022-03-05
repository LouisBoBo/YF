package com.yssj.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.yssj.custom.view.LoadingDialog;
import com.yssj.network.YConn;


public class SAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	private Exception exception;
	private FragmentActivity context;
	private int text;
	private View v;
	private String errorUrl = "";

	public SAsyncTask(FragmentActivity context) {
		this(context, null, 0);
	}
	

	public SAsyncTask(FragmentActivity context, View v) {
		this(context, v, 0);
	}

	public SAsyncTask(FragmentActivity context, int loadingText) {
		this(context, null, loadingText);
	}
	

	public SAsyncTask(FragmentActivity context, View v, int loadingText) {
		this.v = v;
		this.context = context;
		this.text = loadingText;
	}



	public SAsyncTask(FragmentActivity context, View v, int loadingText,String errorUrl) {
		this.v = v;
		this.context = context;
		this.text = loadingText;
		this.errorUrl = errorUrl;
	}






	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (v != null) {
			v.setEnabled(false);
//			v.setVisibility(View.GONE);
		}
		if(null == context || ((Activity)context).isFinishing()){
			return;
		}
		if(this.isCancelled()){
			return;
		}
		if (text != 0) {
//			LoadingDialog.show(context);
		}
		onPreExecute(context);
	}

	@Override
	protected Result doInBackground(Params... params) {
		try {
			return doInBackground(context, params);
		} catch (Exception e) {
			e.printStackTrace();
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		if (v != null) {
			v.setEnabled(true);
//			v.setVisibility(View.VISIBLE);
		}
		
		if(null == context || ((Activity)context).isFinishing()){
			return;
		}
		if(this.isCancelled()){
			return;
		}
		
	
			LoadingDialog.hide(context);
		
				
		
		
		if (exception != null) {
			String temp;
			temp =exception.getMessage();
			if(temp == null){
				return;
			}
			if("".equals(errorUrl)){
				YConn.showErrorToast(context, exception, "网络请求异常");

			}else{
				YConn.showErrorToastErrorUrl(context, exception, "网络请求异常",errorUrl);

			}
			if (!isHandleException()) {
				return;
			}

		}
		if (isHandleException()) {
			onPostExecute(context, result, exception);
		} else {
			onPostExecute(context, result);
		}
		exception = null;
	}

	protected boolean isHandleException() {
		return false;
	}

	protected void onPreExecute(FragmentActivity context) {
	}

	protected Result doInBackground(FragmentActivity context, Params... params)
			throws Exception {
		return null;
	}

	protected void onPostExecute(FragmentActivity context, Result result) {
	}

	protected void onPostExecute(FragmentActivity context, Result result,
			Exception e) {
	}

}

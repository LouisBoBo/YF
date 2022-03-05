package com.yssj.ui.dialog;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.socialize.utils.Log;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
//import com.nostra13.universalimageloader.core.ImageLoader;

/****
 * 卡券说明
 * 
 * @author Administrator
 * 
 */
public class kaquanShuomingDialog2 extends Dialog implements OnClickListener {
	private ImageView icon_close;
	private Context context;
	private ListView lv;
	private Myadapter myAdapter;
	public kaquanShuomingDialog2(Context context, int style) {
		super(context, style);
		setCanceledOnTouchOutside(true);
		this.context = context;

	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_kaquan_explain);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		icon_close = (ImageView) findViewById(R.id.icon_close);
		icon_close.setOnClickListener(this);
		//获取说明详情
		getShuoMing();
		lv = (ListView) findViewById(R.id.lv);
		
	}
	private void getShuoMing() {
		new SAsyncTask<Integer, Void, HashMap<String, String>>((FragmentActivity) context,null,0){

			@Override
			protected HashMap<String, String> doInBackground(FragmentActivity context,
					Integer... params) throws Exception {
				// TODO Auto-generated method stub
				return ComModel2.getKaquanShuoming(context);
			}

			@Override
			protected void onPostExecute(FragmentActivity context,
					HashMap<String, String> result, Exception e) {
				// TODO Auto-generated method stub
				super.onPostExecute(context, result);
				
				if (e != null) {
					return;
				}
				String data = result.get("data");
				
				String [] datas = data.split("\\::");//每一个条目 里面的标题 和 内容
				myAdapter = new Myadapter(datas);
				lv.setAdapter(myAdapter);
			}
			
			@Override
			protected boolean isHandleException() {
				return true;
			};
			
		}.execute();
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_close:
			this.dismiss();
			break;
		default:
			break;
		}

	}
	
	
	class Myadapter  extends BaseAdapter{
		private String [] datas;
		private LayoutInflater mInflater;

		public Myadapter(String [] datas) {
			this.datas = datas;
			mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return datas.length;
		}

		@Override
		public Object getItem(int position) {
			return datas[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder();
				convertView = mInflater.inflate(
						R.layout.dialog_kaquan_explain_list, null);
				holder.title = (TextView) convertView.findViewById(R.id.dialog_kajuan_title_tv);
				holder.content = (TextView) convertView.findViewById(R.id.dialog_kajuan_content_tv);

				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}
			String showData = datas[position];
			String[] showDatas=showData.split("_");
			if(showDatas.length>1){
				String title = showDatas[0];
				String content = showDatas[1];
				holder.title.setText(title);
				holder.content.setText(content);
			}
			return convertView;
		}

		class Holder {
			TextView title, content;
		}
	}

}
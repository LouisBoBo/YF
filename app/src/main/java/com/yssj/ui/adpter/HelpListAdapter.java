package com.yssj.ui.adpter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Help;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.HelpCenterActivity;
import com.yssj.ui.activity.infos.HelpQuestionActivity;

/***
 * 抽屉适配器
 * 
 * @author Administrator
 * 
 */
public class HelpListAdapter extends BaseAdapter {
	private Context context;
	private List<Help> helps;

	public HelpListAdapter(Context context, List<Help> helps) {
		this.context = context;
		this.helps = helps;

	}

	@Override
	public int getCount() {
		return helps.size();
	}

	@Override
	public Object getItem(int position) {

		return helps.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.listview_drawer, null);
			holder.tv_drawer = (TextView) convertView
					.findViewById(R.id.tv_drawer);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.tv_drawer.setText(helps.get(position).getQuestion());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, HelpQuestionActivity.class);
				intent.putExtra("id", helps.get(position).getId() + "");
				context.startActivity(intent);
			}
		});

		return convertView;
	}
	
	

	class Holder {
		TextView tv_drawer;

	}
	
	


}

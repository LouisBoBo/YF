package com.yssj.spl;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.ui.activity.circles.SweetFriendsDetails;
import com.yssj.ui.fragment.YaoQingFrendsActivity;
import com.yssj.utils.GlideUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.YCache;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PuBuAdapter extends BaseAdapter {
	private Context mContext;

	private List<HashMap<String, Object>> listData;
	private boolean isSign;
	private boolean isInvite;//邀请好友

	private boolean isTixian;//浏览赢提现


	// private int newPos = 19;

	public PuBuAdapter(Context context, List<HashMap<String, Object>> mListDatas, boolean isSign,boolean isInvite,boolean isTixian) {
		mContext = context;
		this.listData = mListDatas;
		this.isSign = isSign;
		this.isInvite = isInvite;
		this.isTixian = isTixian;
		// getMoreItem();
	}

	/**
	 *
	 * @param context
	 * @param mListDatas
	 * @param isSign 浏览X件穿搭
	 * @param isTixian 浏览X件穿搭 浏览赢提现
	 */
	public PuBuAdapter(Context context, List<HashMap<String, Object>> mListDatas, boolean isSign,boolean isTixian) {
		mContext = context;
		this.listData = mListDatas;
		this.isSign = isSign;
		this.isTixian = isTixian;
		// getMoreItem();
	}

	public PuBuAdapter(Context context, List<HashMap<String, Object>> mListDatas) {
		mContext = context;
		this.listData = mListDatas;
		// getMoreItem();
	}

	@Override
	public int getCount() {
		return listData == null ? 0 : listData.size();

	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.cell_stgv, null);
			holder.img_content = (ImageView) convertView.findViewById(R.id.img_content);
			holder.tv_dianzan = (ImageView) convertView.findViewById(R.id.tv_dianzan);

			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_zancount = (TextView) convertView.findViewById(R.id.tv_zancount);
			holder.img_head = (ImageView) convertView.findViewById(R.id.img_head);
			holder.root = (LinearLayout) convertView.findViewById(R.id.root);
			holder.iconInviteSelect = (ImageView) convertView.findViewById(R.id.iv_select_chuanda);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		// 处理展示 // 处理点击

		final HashMap<String, Object> datas = listData.get(position);
		final String theme_id = (String) datas.get("theme_id");// 帖子ID
		final String user_id = (String) datas.get("user_id");// 发帖用户ID
		final String theme_type = (String) datas.get("theme_type"); // 帖子类型
		String inviteSharePic ="";
		List<HashMap<String, Object>> shop_list = (List<HashMap<String, Object>>) datas.get("shop_list");
		// SetImageLoader.initImageLoader(mContext, holder.imgHead, (String)
		// datas.get("head_pic"), "");
//		PicassoUtils.initImage(mContext, (String) datas.get("head_pic"), holder.img_head);

		GlideUtils.initRoundImage(Glide.with(mContext),mContext, (String) datas.get("head_pic"), holder.img_head);

		// 内容文本
		final String content = (String) datas.get("content");
		holder.tv_content.setText(content);
		// 昵称
		String nickname = (String) datas.get("nickname");
		holder.tv_name.setText(nickname);
		// 加心数量
		String applaud_num = (String) datas.get("applaud_num");
		// 加心状态
		String applaud_status = (String) datas.get("applaud_status");
		holder.tv_zancount.setText(applaud_num);
		if (applaud_status.equals("1")) {
			holder.tv_dianzan.setImageResource(R.drawable.sweet_icon_xihuan_pre);
		} else {
			holder.tv_dianzan.setImageResource(R.drawable.sweet_icon_xihuan);
		}
		
		// 主图
		if ("1".equals(theme_type)) {// 精选推荐
			if (shop_list.size() > 0) {
				String shop_code = shop_list.get(0).get("shop_code").toString();
				String url = shop_code.substring(1, 4) + File.separator + shop_code + File.separator
						+ shop_list.get(0).get("def_pic").toString();
				// SetImageLoader.initImageLoader(mContext,
				// holder.img_small,url, "!450");
				if(isInvite){
					inviteSharePic = url;
				}
				PicassoUtils.initImage(mContext, url + "!280", holder.img_content);
			}

		} else { // 话题
			String pics = (String) datas.get("pics");
			String picImg[];
			if (TextUtils.isEmpty(pics)) {
				holder.img_content.setVisibility(View.GONE);
				// holder.img_content.setImageResource(R.drawable.free_50);
				if(isInvite){
					inviteSharePic = "";
				}
			} else {
				holder.img_content.setVisibility(View.VISIBLE);
				picImg = pics.split(",");
				PicassoUtils.initImage(mContext, "myq/theme" + "/" + user_id + "/" + picImg[0].split(":")[0] + "!280",
						holder.img_content);
				if(isInvite){
					inviteSharePic = "myq/theme" + "/" + user_id + "/" + picImg[0].split(":")[0];
				}
			}

		}

		holder.root.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SweetFriendsDetails.class);
				intent.putExtra("isSign", isSign);


				intent.putExtra("isTixian", isTixian);


				intent.putExtra("theme_id", theme_id);
				((FragmentActivity) mContext).startActivity(intent);
				((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
			}
		});
		final String inviteSharePicFinal = inviteSharePic;
		if(isInvite){
			holder.iconInviteSelect.setVisibility(View.VISIBLE);
			final boolean isSelect = (Boolean) datas.get("isChecked");
			if(isSelect){
				holder.iconInviteSelect.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
			}else{
				holder.iconInviteSelect.setImageResource(R.drawable.wodexihao_fengge_icon_weixuanzhong);
			}
			holder.iconInviteSelect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(isSelect){
						return;
					}
					for (HashMap<String, Object> hashMap : listData) {
						hashMap.put("isChecked", false);
					}
					listData.get(position).put("isChecked", true);
					notifyDataChange();
					YaoQingFrendsActivity.tieziID = theme_id;
					YaoQingFrendsActivity.tieziPIC = inviteSharePicFinal;
					YaoQingFrendsActivity.tieziContent = content;
				}
			});
		}else{
			holder.iconInviteSelect.setVisibility(View.GONE);
		}

		return convertView;
	}

	private void notifyDataChange(){
		this.notifyDataSetChanged();
	}
	class Holder {
		ImageView tv_dianzan;
		LinearLayout root;
		ImageView img_content,iconInviteSelect;
		TextView tv_content;
		TextView tv_zancount;
		TextView tv_name;
		ImageView img_head;
	}

}

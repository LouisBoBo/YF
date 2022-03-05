package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.Shop;
import com.yssj.entity.ShopComment;
import com.yssj.model.ComModel;
//import com.yssj.ui.activity.shopdetails.BigImageDialog;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluateView extends LinearLayout {
	
	private ProgressBar pb_color_count, pb_type_count, pb_work_count, pb_cost_count;//没有色差， 版型好看， 做工不错， 性价比好
	private TextView tv_color_count, tv_type_count, tv_work_count, tv_cost_count;
	
	private LinearLayout viewContainer;
	
	private LinearLayout lin_nodata;
	
	private Context context;
	
	private View evaView;//商品好评率
	
	public EvaluateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.fragment_evaluate, this);
		
		
	}
	
	public void setShop(Shop  shop){
//		evaView.setVisibility(View.VISIBLE);
//		viewContainer.setVisibility(View.GONE);
//		lin_nodata.setVisibility(View.GONE);
//		pb_color_count.setProgress(shop.getColor_count());
//		pb_type_count.setProgress(shop.getType_count());
//		pb_work_count.setProgress(shop.getWork_count());
//		pb_cost_count.setProgress(shop.getCost_count());
//		
//		tv_color_count.setText(shop.getColor_count() +"%");
//		tv_type_count.setText(shop.getType_count()+"%");
//		tv_work_count.setText(shop.getWork_count() +"%");
//		tv_cost_count.setText(shop.getCost_count() +"%");
		
	}
	
	public void setNoContent(){
//		viewContainer.setVisibility(View.GONE);
//		lin_nodata.setVisibility(View.VISIBLE);
//		evaView.setVisibility(View.GONE);
	}
	
	/**
	public void setDate(ShopComment shopComment){
			viewContainer.setVisibility(View.VISIBLE);
			lin_nodata.setVisibility(View.GONE);
			evaView.setVisibility(View.GONE);
			
			img_user_header.setTag(shopComment.getUser_url());
			SetImageLoader.initImageLoader(context, img_user_header, shopComment.getUser_url());
			String user_name = shopComment.getUser_name();
			if (!TextUtils.isEmpty(user_name)) {
				tv_user.setText(user_name);
			}
			int comment_type = shopComment.getComment_type();
			if (comment_type == 1 ) {
				tv_evaluate.setText("好评");
			}else if (comment_type == 2) {
				tv_evaluate.setText("中评");
			}else if (comment_type == 3) {
				tv_evaluate.setText("差评");
			}
			long add_date = shopComment.getAdd_date();
			String date = StringUtils.timeToDate(add_date);
			if (!TextUtils.isEmpty(date)) {
				tv_date.setText(date);	
			}
			
			String content = shopComment.getContent();
			if (!TextUtils.isEmpty(content)) {
				tv_descri.setText(content);
			}
			
			String shop_color = shopComment.getShop_color();
			String shop_size = shopComment.getShop_size();
			if (!TextUtils.isEmpty(shop_color)) {
				tv_size_color.setText("颜色："+shop_color+"  尺码："+ shop_size);
			}
			final String pic = shopComment.getPic();
			if (!TextUtils.isEmpty(pic)) {
				String[] picList = pic.split(",");
				for(int j=0; j<picList.length; j++){
					ImageView img = new ImageView(context);
					img.setLayoutParams(new LayoutParams(30, 30));
					SetImageLoader.initImageLoader(context, img, picList[j]);
					img_container.addView(img);
				}
			}
			
			if(null != shopComment.getSuppComment()){
				tv_one_reply.setVisibility(View.VISIBLE);
				tv_one_reply.setText(Html.fromHtml(context.getString(R.string.tv_supp_reply,shopComment.getSuppComment().get(0).getSupp_content())));
			}
			
			if(null != shopComment.getComment()){
				lin_second.setVisibility(View.VISIBLE);
				tv_second_judge.setVisibility(View.VISIBLE);
				tv_second_judge.setText(Html.fromHtml(context.getString(R.string.tv_add_judge, shopComment.getComment().get(0).getContent())));
			}
			if(null != shopComment.getSuppEndComment()){
				lin_second.setVisibility(View.VISIBLE);
				tv_second_reply.setVisibility(View.VISIBLE);
				tv_second_reply.setText(Html.fromHtml(context.getString(R.string.tv_supp_reply, shopComment.getSuppEndComment().get(0).getSupp_content())));
			}
			
	}
	
	private void showBigDialog(String[] url , int item){
		BigImageDialog dlg = new BigImageDialog(context, R.style.DialogStyle, url, item);
		dlg.show();
		
	}
	
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}*/
}

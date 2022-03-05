//package com.yssj.ui.activity.shopdetails;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.text.Html;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.yssj.activity.R;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.MyScrollView;
//import com.yssj.custom.view.MyScrollView.OnScrollListener;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.entity.Shop;
//import com.yssj.entity.ShopComment;
//import com.yssj.model.ComModel;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.StringUtils;
///***
// *	评价
// * @author Administrator
// *
// */
////public class EvaluateFragment extends Fragment implements OnClickListener{
////	private View view ; 
//////	private LinearLayout lay_evaluate0,lay_evaluate1,lay_evaluate2,lay_evaluate3; 
//////	private TextView tv_all,tv_all_number,tv_like,tv_like_number,tv_middle,tv_middle_number,tv_nolike,tv_nolike_number,
////	private ProgressBar pb_color_count, pb_type_count, pb_work_count, pb_cost_count;//没有色差， 版型好看， 做工不错， 性价比好
////	private TextView tv_color_count, tv_type_count, tv_work_count, tv_cost_count;
////	
////	private LinearLayout viewContainer;
////	
////	private int rows = 10 , page = 1 ;
////	private List<ShopComment> listShopComments = new ArrayList<ShopComment>();
////	private ProgressBar pbar ;
////	private boolean flag = true ; 
////	private static Shop shop ;
////	
////	private static MyScrollView myScollView;
////	
////	private LinearLayout lin_nodata;
////	
////	public static EvaluateFragment newInstance(Shop mshop, MyScrollView myscrollView) {
////		 EvaluateFragment evaluateFragment = new EvaluateFragment();
////		 shop = mshop;
////		 myScollView = myscrollView;
////		 return evaluateFragment;
////	}
////	
////	@Override
////	public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
//////		Bundle bundle = getArguments();
//////		shop = (Shop) bundle.getSerializable("shop");
//////		myScollView = (MyScrollView) bundle.getSerializable("myScrollview");
////		view = inflater.inflate(R.layout.fragment_evaluate, container, false);
////		
////		lin_nodata = (LinearLayout) view.findViewById(R.id.lin_nodata);
////		pb_color_count = (ProgressBar) view.findViewById(R.id.pb_color_count);
////		pb_type_count = (ProgressBar) view.findViewById(R.id.pb_type_count);
////		pb_work_count = (ProgressBar) view.findViewById(R.id.pb_work_count);
////		pb_cost_count = (ProgressBar) view.findViewById(R.id.pb_cost_count);
////		
////		tv_color_count = (TextView) view.findViewById(R.id.tv_color_count);
////		tv_type_count = (TextView) view.findViewById(R.id.tv_type_count);
////		tv_work_count = (TextView) view.findViewById(R.id.tv_work_count);
////		tv_cost_count = (TextView) view.findViewById(R.id.tv_cost_count);
////		
////		viewContainer = (LinearLayout) view.findViewById(R.id.container);
//////		pb_color_count.setProgress(shop.getColor_count());
//////		pb_type_count.setProgress(shop.getType_count());
//////		pb_work_count.setProgress(shop.getWork_count());
//////		pb_cost_count.setProgress(shop.getCost_count());
////		
////		tv_color_count.setText(shop.getColor_count() +"%");
////		tv_type_count.setText(shop.getType_count()+"%");
////		tv_work_count.setText(shop.getWork_count() +"%");
////		tv_cost_count.setText(shop.getCost_count() +"%");
////		
////		myScollView.getView();
////		myScollView.setOnScrollListener(new OnScrollListener() {
////
////			@Override
////			public void onTop() {
////				// 滚动到最顶端
////			}
////
////			@Override
////			public void onScroll() {
////
////			}
////
////			@Override
////			public void onBottom() {
////				// 滚动到最低端
////			}
////
////			@Override
////			public void onAutoScroll(int l, int t, int oldl, int oldt) {}
////
////			@Override
////			public void onScrollStop() {
////				// TODO Auto-generated method stub
////				
////			}
////		});
////		querySelCommentByShop();
////		return view;
////	}
////	
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////
////		}
////		
////	}
////	
////	
////	private void querySelCommentByShop(){
////		if (shop == null) {
////			return;
////		}
////		new SAsyncTask<Void, Void, List<ShopComment>>(getActivity(), null, R.string.wait) {
////			@Override
////			protected List<ShopComment> doInBackground(FragmentActivity context,Void... params) throws Exception {
////				List<ShopComment> list  = ComModel.queryShopEvaluate(getActivity(), ""+page, ""+rows, ""+shop.getShop_code());
////				return list;
////			}
////
////			@Override
////			protected void onPostExecute(FragmentActivity context, List<ShopComment> list,
////					Exception e) {
////
////				if (e != null) {//查询异常
////					Toast.makeText(getActivity(), "连接超时，请重试", Toast.LENGTH_LONG).show();
////				}  else {//查询商品详情成功，刷新界面
////					if (list != null && list.size()>0) {
////						listShopComments.addAll(list);
////						addView(viewContainer, listShopComments);
////					}else if(list != null && list.size() == 0){
////						lin_nodata.setVisibility(View.VISIBLE);
////						viewContainer.setVisibility(View.GONE);
////					}
////				}
////
////			};
////
////			@Override
////			protected boolean isHandleException() {
////				return true;
////			};
////		}.execute();
////		
////	}
////	
////	private void addView(LinearLayout container, List<ShopComment> objects){
////		container.removeAllViews();
////		LayoutInflater inflater = LayoutInflater.from(getActivity());
////		for(int i =0; i< objects.size(); i++){
////			View convertView = inflater.inflate(R.layout.listview_evaluate, null);
////			RoundImageButton img_user_header = (RoundImageButton) convertView.findViewById(R.id.img_user_header);
////			TextView tv_user = (TextView) convertView.findViewById(R.id.tv_user);
////			TextView tv_evaluate = (TextView) convertView.findViewById(R.id.tv_evaluate);
////			TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
////			TextView tv_descri = (TextView) convertView.findViewById(R.id.tv_descri);
////			TextView tv_size_color = (TextView) convertView.findViewById(R.id.tv_size_color);
////			LinearLayout img_container = (LinearLayout) convertView.findViewById(R.id.img_container);
////			
////			TextView tv_one_reply =  (TextView) convertView.findViewById(R.id.tv_one_reply);
////			TextView tv_second_judge = (TextView) convertView.findViewById(R.id.tv_second_judge);
////			TextView tv_second_reply = (TextView) convertView.findViewById(R.id.tv_second_reply);
////			
////			LinearLayout lin_second = (LinearLayout) convertView.findViewById(R.id.lin_second); 
////			
////			ShopComment shopComment = objects.get(i);
////			SetImageLoader.initImageLoader(getActivity(), img_user_header, shopComment.getUser_url(),"");
////			String user_name = shopComment.getUser_name();
////			if (!TextUtils.isEmpty(user_name)) {
////				tv_user.setText(user_name);
////			}
////			int comment_type = shopComment.getComment_type();
////			if (comment_type == 1 ) {
////				tv_evaluate.setText("好评");
////			}else if (comment_type == 2) {
////				tv_evaluate.setText("中评");
////			}else if (comment_type == 3) {
////				tv_evaluate.setText("差评");
////			}
////			long add_date = shopComment.getAdd_date();
////			String date = StringUtils.timeToDate(add_date);
////			if (!TextUtils.isEmpty(date)) {
////				tv_date.setText(date);	
////			}
////			
////			String content = shopComment.getContent();
////			if (!TextUtils.isEmpty(content)) {
////				tv_descri.setText(content);
////			}
////			
////			String shop_color = shopComment.getShop_color();
////			String shop_size = shopComment.getShop_size();
////			if (!TextUtils.isEmpty(shop_color)) {
////				tv_size_color.setText("颜色："+shop_color+"  尺码："+ shop_size);
////			}
////			final String pic = shopComment.getPic();
////			if (!TextUtils.isEmpty(pic)) {
////				String[] picList = pic.split(",");
////				for(int j=0; j<picList.length; j++){
////					ImageView img = new ImageView(getActivity());
////					img.setLayoutParams(new LayoutParams(30, 30));
////					SetImageLoader.initImageLoader(getActivity(), img, picList[j],"");
////					img_container.addView(img);
////				}
////			}
////			
////			if(null != shopComment.getSuppComment()){
////				tv_one_reply.setVisibility(View.VISIBLE);
////				tv_one_reply.setText(Html.fromHtml(getString(R.string.tv_supp_reply,shopComment.getSuppComment().get(0).getSupp_content())));
////			}
////			
////			if(null != shopComment.getComment()){
////				lin_second.setVisibility(View.VISIBLE);
////				tv_second_judge.setVisibility(View.VISIBLE);
////				tv_second_judge.setText(Html.fromHtml(getString(R.string.tv_add_judge, shopComment.getComment().get(0).getContent())));
////			}
////			if(null != shopComment.getSuppEndComment()){
////				lin_second.setVisibility(View.VISIBLE);
////				tv_second_reply.setVisibility(View.VISIBLE);
////				tv_second_reply.setText(Html.fromHtml(getString(R.string.tv_supp_reply, shopComment.getSuppEndComment().get(0).getSupp_content())));
////			}
////			
////			container.addView(convertView);
////		}
////	}
////	
////	private void showBigDialog(String[] url , int item){
////		BigImageDialog dlg = new BigImageDialog(getActivity(), R.style.DialogStyle, url, item);
////		dlg.show();
////		
////	}
////	
////	private static class AnimateFirstDisplayListener extends
////			SimpleImageLoadingListener {
////
////		static final List<String> displayedImages = Collections
////				.synchronizedList(new LinkedList<String>());
////
////		@Override
////		public void onLoadingComplete(String imageUri, View view,
////				Bitmap loadedImage) {
////			if (loadedImage != null) {
////				ImageView imageView = (ImageView) view;
////				boolean firstDisplay = !displayedImages.contains(imageUri);
////				if (firstDisplay) {
////					FadeInBitmapDisplayer.animate(imageView, 500);
////					displayedImages.add(imageUri);
////				}
////			}
////		}
////	}
//}

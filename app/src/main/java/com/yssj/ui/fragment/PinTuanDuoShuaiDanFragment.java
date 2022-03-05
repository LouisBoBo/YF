package com.yssj.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.activity.R.drawable;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.XListViewMealSingle;
import com.yssj.entity.UserInfo;
import com.yssj.huanxin.widget.photoview.PhotoView;
import com.yssj.huanxin.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.DisplayOrderDetialsActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.yssj.custom.view.XListView;

public class PinTuanDuoShuaiDanFragment extends Fragment{
	private TextView tv_qin, tv_no_join;
	private static Context mContext;

	private XListViewMealSingle mList;
	private DateAdapter mAdapter;
	private List<HashMap<String, Object>> pList;
	private int index=1;

	private boolean flag=true;
	private boolean flag_dianzan=true;


	public static PinTuanDuoShuaiDanFragment newInstances(int position, Context context) {
		PinTuanDuoShuaiDanFragment instance = new PinTuanDuoShuaiDanFragment();
		Bundle args = new Bundle();
		args.putString("position", position+"");
		mContext = context;
		instance.setArguments(args);

		return instance;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

		View v = inflater
				.inflate(R.layout.item_fragment_view_meal_single, container, false);

		account_nodata = (LinearLayout)v.findViewById(R.id.account_nodata);
		account_nodata.setVisibility(View.GONE);

		TextView	tv_qin = (TextView) v.findViewById(R.id.tv_qin);
		tv_qin.setText("亲，赶紧抽奖晒单吧~");
		tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
		tv_no_join.setText("暂无优惠券");
		tv_no_join.setVisibility(View.GONE);
		Button btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
		btn_view_allcircle.setVisibility(View.GONE);

		choice = SharedPreferencesUtil.getStringData(mContext, "choice", "3");
		my_choice = SharedPreferencesUtil.getStringData(mContext, "my_choice", "3");
//		System.out.println("choice="+choice+"*****my_choice="+my_choice);

		mList=(XListViewMealSingle) v.findViewById(R.id.dataList);


		//没有晒单了
		account_nodata.setVisibility(View.VISIBLE);
		mList.setVisibility(View.GONE);





//		mList.setPullLoadEnable(true);
//
//		mList.setXListViewListener(new XListViewMealSingle.IXListViewListener() {
//
//
//			@Override
//			public void onRefresh() {
//
//			}
//
//			@Override
//			public void onLoadMore() {
//				index++;
//				initData(String.valueOf(index));
//			}
//		});
//
//
//		pList   = new ArrayList<HashMap<String, Object>>();
//
//
//
//		mAdapter = new DateAdapter(getActivity());
//		mList.setAdapter(mAdapter);

		return v;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferencesUtil.saveStringData(mContext, "where", "2");
		initData(String.valueOf(index));
	}

	protected void initData(final String  index) {

//		UserInfo user=YCache.getCacheUser(mContext);
//		final String user_id = user.getUser_id()+"";
//
//		new SAsyncTask<String, Void,  List<HashMap<String, Object>>>(
//				getActivity(), 0){
//			@Override
//			protected List<HashMap<String, Object>> doInBackground(
//					FragmentActivity context, String... params)
//					throws Exception {
//						return ComModel2.DuoBaoShaiDan(context,user_id,index);
//			}
//
//			@Override
//			protected boolean isHandleException() {
//				// TODO Auto-generated method stub
//				return true;
//			}
//
//
//			protected void onPostExecute(FragmentActivity context,
//					List<HashMap<String, Object>> result,Exception e) {
////				System.out.println("********夺宝晒单="+result);
////				if (result.equals("[]")) {
////					account_nodata.setVisibility(View.VISIBLE);
////					mList.setVisibility(View.GONE);
////				}
//				if(e!=null){
//					mList.stopLoadMore();
////					account_nodata.setVisibility(View.VISIBLE);
////					mList.setVisibility(View.GONE);
//					return ;
//				}
//				if(result!=null){
//
//					if(PinTuanDuoShuaiDanFragment.this.index==1){
//						pList.clear();
//					}
//
//					if(result.size() == 0 && !index.equals("1")){
//					}
//					pList.addAll(result);
//				}else{
//
//					if(PinTuanDuoShuaiDanFragment.this.index==1){
//						pList.clear();
//					}else{
//					}
//				}
//				mAdapter.notifyDataSetChanged();
//				mList.stopLoadMore();
//			};
//		}.execute();

	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		choice = SharedPreferencesUtil.getStringData(mContext, "choice", "3");
		my_choice = SharedPreferencesUtil.getStringData(mContext, "my_choice", "3");
//		System.out.println("choice="+choice+"*****my_choice="+my_choice);


		index=1;

		initData(String.valueOf(index));

		initview();
	}
	public void refresh(){
		index=1;
		initData("1");
	}

	private void initview() {


	}


	@SuppressLint("NewApi") private class DateAdapter extends BaseAdapter{

		private Context context;
//		private String shop_code;

		public DateAdapter(Context context) {
			super();
			this.context = context;

		}

		@Override
		public int getCount() {
			int count=0;
			count =pList.size();

			if (count == 0) {
				account_nodata.setVisibility(View.VISIBLE);
			}else {
				account_nodata.setVisibility(View.GONE);
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 final ViewHolder holder;


			if (convertView==null) {
//				convertView=LayoutInflater.from(context).inflate(R.layout.duobaojilu_shaidan, null);
				convertView=LayoutInflater.from(context).inflate(R.layout.wqjx_right, null);
				holder = new ViewHolder();


				holder.img_head = (RoundImageButton) convertView.findViewById(R.id.img_head); //头像
				holder.name_head = (TextView) convertView.findViewById(R.id.name_head); //名称
				holder.img_huiyuan = (ImageView) convertView.findViewById(R.id.img_huiyuan); //会员图标
				holder.tv_huiyuan = (TextView) convertView.findViewById(R.id.tv_huiyuan); //至尊会员
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); //至尊会员后面的时间
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content); //发布内容
				holder.show_all = (TextView) convertView.findViewById(R.id.show_all); //显示全部
				holder.sing_martrix = (ImageView) convertView.findViewById(R.id.sing_martrix); //单个正方形图
				holder.sing_changfangxing = (ImageView) convertView.findViewById(R.id.sing_changfangxing); //单个长方形图
				holder.sing_changfangxing_vertical = (ImageView) convertView.findViewById(R.id.sing_changfangxing_vertical); //单个竖直长方形图


				holder.img_more_ll = (LinearLayout) convertView.findViewById(R.id.img_more_ll);  //多个图的LinearLayout父容器
				holder.more_img_one = (ImageView) convertView.findViewById(R.id.more_img_one); //多个图中的第一个图
				holder.more_img_two = (ImageView) convertView.findViewById(R.id.more_img_two); //多个图中的第2个图
				holder.more_img_three = (ImageView) convertView.findViewById(R.id.more_img_three); //多个图中的第2个图


				holder.ll_dianzan = (LinearLayout) convertView.findViewById(R.id.ll_dianzan);  //点赞的ll_dianzan范围
				holder.tv_dianzan = (ImageView) convertView.findViewById(R.id.tv_dianzan); //点赞图标
				holder.tv_num_dianzan = (TextView) convertView.findViewById(R.id.tv_num_dianzan); //点赞数量


				holder.ll_pingjia = (LinearLayout) convertView.findViewById(R.id.ll_pingjia);  //评价的ll_dianzan范围
				holder.img_pingjia = (ImageView) convertView.findViewById(R.id.img_pingjia); //评价图标
				holder.tv_num_pingjia = (TextView) convertView.findViewById(R.id.tv_num_pingjia); //点赞数量


/*需求不需要				holder.ll_fenxiang = (LinearLayout) convertView.findViewById(R.id.ll_fenxiang);  //分享的ll_dianzan范围
				holder.img_fenxiang = (ImageView) convertView.findViewById(R.id.img_fenxiang); //分享图标
				holder.tv_num_fenxiang = (TextView) convertView.findViewById(R.id.tv_num_fenxiang); //分享数量
*/
				holder.view_cut_one = convertView.findViewById(R.id.view_cut_one); //最底下分割线  第一个
//需求不需要				holder.view_cut_two = convertView.findViewById(R.id.view_cut_two); //最底下分割线  第2个

				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}

			HashMap<String, Object> mapObj = pList.get(position);

			String content = mapObj.get("content").toString(); //评论内容
			holder.tv_content.setText(content);

			final int lineCount = holder.tv_content.getLineCount();
			if (lineCount <3) {
				holder.show_all.setVisibility(View.GONE);
			}else {
				holder.show_all.setVisibility(View.VISIBLE);
			}

			final String shop_code = mapObj.get("shop_code").toString(); //商品编号
			String shop_codes = mapObj.get("shop_codes").toString();

			holder.show_all.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (lineCount>=3) {
						if (flag==true) {
							flag=false;
							holder.tv_content.setEllipsize(null);
							holder.tv_content.setSingleLine(flag);
							holder.show_all.setText("收起");
						}else {
							holder.tv_content.setEllipsize(TruncateAt.END);
							holder.tv_content.setMaxLines(3);
							holder.show_all.setText("显示全部");
							flag=true;
						}
					}

				}
			});







			if (shop_codes.equals(shop_code)) {
//				holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_red));
				holder.tv_dianzan.setImageResource(drawable.icon_dianzan_press);
			}else {
//				holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_gray));
				holder.tv_dianzan.setImageResource(drawable.icon_dianzan_gray);
			}



			String user_name = mapObj.get("user_name").toString();
			holder.name_head.setText(user_name);   // 用户昵称

			String add_date = mapObj.get("add_date").toString();  //评论时间

			long time = System.currentTimeMillis();

			String add_date_need = DateFormatUtils.format(Long.parseLong(add_date),"yyyy.MM.dd HH:mm:ss");
			String time_sys = DateFormatUtils.format(Long.parseLong(time+""),"yyyy.MM.dd HH:mm:ss");

			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			try
			{
			   Date d1 = df.parse(time_sys+"");
			   Date d2 = df.parse(add_date_need);
			   long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
			   long days = diff / (1000 * 60 * 60 * 24);
			   long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			   long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

//			   System.out.println(""+days+"天"+hours+"小时"+minutes+"分");

			   if (days>0 || hours>0 ) {  //如果天数或者小时数大于0就直接显示时间
				   holder.tv_time.setText(DateFormatUtils.format(Long.parseLong(add_date),"yyyy.MM.dd HH:mm:ss"));
			   }else { //当时间差小于一小时就显示多少分钟前
				   holder.tv_time.setText(minutes+"分钟前");
			   }

			}catch (Exception e){

			}



			String comment_size = mapObj.get("comment_size").toString(); //评论总数
			holder.tv_num_pingjia.setText(comment_size);

			final String click_size = mapObj.get("click_size").toString(); //点赞总数
			holder.tv_num_dianzan.setText(click_size);


//			SetImageLoader.initImageLoader(context, holder.img_head,
//					/*mapObj.get("in_uid").toString().substring(1, 4)+"/"+mapObj.get("in_uid").toString()+"/"+*/(String) mapObj.get("user_url"),"");
			PicassoUtils.initImage(context, (String) mapObj.get("user_url"), holder.img_head);

			String object = mapObj.get("pic").toString();
			final String[] split = object.split("[,]");

//			System.out.println("长度="+split.length);
//			System.out.println(".........="+mapObj.get("pic").toString());


			if (split.length==1) {
				holder.img_more_ll.setVisibility(View.GONE);
				holder.sing_martrix.setVisibility(View.VISIBLE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);
				holder.more_img_one.setVisibility(View.GONE);
				holder.more_img_two.setVisibility(View.GONE);
				holder.more_img_three.setVisibility(View.GONE);



//				SetImageLoader.initImageLoader(context, holder.sing_martrix,
//						"shareOrder"+split[0],"");

				PicassoUtils.initImage(context, "shareOrder"+split[0], holder.sing_martrix);

				System.out.println("我是单个="+split[0]);
			}

			if (split.length==2) {
				holder.img_more_ll.setVisibility(View.VISIBLE);
				holder.sing_martrix.setVisibility(View.GONE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);
				holder.more_img_one.setVisibility(View.VISIBLE);
				holder.more_img_two.setVisibility(View.VISIBLE);
				holder.more_img_three.setVisibility(View.GONE);


//				SetImageLoader.initImageLoader(context, holder.more_img_one,
//						"shareOrder"+split[0],"");
				PicassoUtils.initImage(mContext, "shareOrder"+split[0], holder.more_img_one);
//				System.out.println("我是多个第一个="+split[0]);

//				SetImageLoader.initImageLoader(context, holder.more_img_two,
//						"shareOrder"+split[1],"");
				PicassoUtils.initImage(mContext, "shareOrder"+split[1], holder.more_img_two);

//				System.out.println("我是多个第2个="+split[1]);
				holder.more_img_three.setVisibility(View.GONE);
			}

			if (split.length==3) {
				holder.img_more_ll.setVisibility(View.VISIBLE);
				holder.sing_martrix.setVisibility(View.GONE);
				holder.sing_changfangxing.setVisibility(View.GONE);
				holder.sing_changfangxing_vertical.setVisibility(View.GONE);

				holder.more_img_one.setVisibility(View.VISIBLE);
				holder.more_img_two.setVisibility(View.VISIBLE);
				holder.more_img_three.setVisibility(View.VISIBLE);


//				SetImageLoader.initImageLoader(context, holder.more_img_one,
//						"shareOrder"+split[0],"");

				PicassoUtils.initImage(mContext, "shareOrder"+split[0], holder.more_img_one);

//				SetImageLoader.initImageLoader(context, holder.more_img_two,
//						"shareOrder"+split[1],"");
				PicassoUtils.initImage(mContext, "shareOrder"+split[1], holder.more_img_two);

//				SetImageLoader.initImageLoader(context, holder.more_img_three,
//						"shareOrder"+split[2],"");
				PicassoUtils.initImage(mContext, "shareOrder"+split[2], holder.more_img_three);
			}



			//点击条目跳转
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),DisplayOrderDetialsActivity.class);
					intent.putExtra("shop_code", shop_code);
					startActivity(intent);
				}
			});


			//点赞切换点赞图标的颜色
			holder.ll_dianzan.setOnClickListener(new OnClickListener() {

				private int num_click_size;

				@SuppressLint("NewApi") @Override
				public void onClick(View v) {

					new SAsyncTask<String, Void,  HashMap<String, Object>>(
							getActivity(), 0){




						protected HashMap<String, Object> doInBackground(
								FragmentActivity context, String... params)
								throws Exception {
									return ComModel2.AddClick(context,shop_code);
						}


						@Override
						protected boolean isHandleException() {
							return true;
						}


						protected void onPostExecute(FragmentActivity context,
								HashMap<String, Object> result,Exception e) {


							if (result!=null) {
								String status = result.get("status").toString();

								if (status.equals("1")) {
//										holder.tv_dianzan.setBackground(getResources().getDrawable(R.drawable.icon_dianzan_red));
										holder.tv_dianzan.setImageResource(drawable.icon_dianzan_press);
										num_click_size = Integer.valueOf(click_size).intValue();
										num_click_size=num_click_size+1;
										holder.tv_num_dianzan.setText(num_click_size+"");
								}

							}



						}

					}.execute();

				}
			});


				//点击图片的功用部分
			  final Dialog dialog = new Dialog(getActivity(), R.style.Notitle_Fullscreen);
	          dialog.setContentView(R.layout.changbig);
	          final PhotoView img_bigger = (PhotoView) dialog.findViewById(R.id.img_bigger);
	          android.view.WindowManager.LayoutParams lay = dialog.getWindow().getAttributes();
	          setParams(lay);
	          img_bigger.setOnPhotoTapListener(new OnPhotoTapListener() {

					@Override
					public void onPhotoTap(View view, float x, float y) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});



			holder.sing_martrix.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[0],"");


			          PicassoUtils.initImage(context, "shareOrder"+split[0], img_bigger);

			          dialog.show();
				}

			});



			holder.sing_changfangxing.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[0],"");


			          PicassoUtils.initImage(getActivity(), "shareOrder"+split[0], img_bigger);


			          dialog.show();
				}
			});



			holder.sing_changfangxing_vertical.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[0],"");

			          PicassoUtils.initImage(getActivity(), "shareOrder"+split[0], img_bigger);


			          dialog.show();
				}
			});


			holder.more_img_one.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {


//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[0],"");
			          PicassoUtils.initImage(getActivity(), "shareOrder"+split[0], img_bigger);

			          dialog.show();
				}
			});




			holder.more_img_two.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[1],"");
			          PicassoUtils.initImage(getActivity(), "shareOrder"+split[1], img_bigger);

			          dialog.show();
				}
			});



			holder.more_img_three.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//			          SetImageLoader.initImageLoader(getActivity(), img_bigger,
//			        		  "shareOrder"+split[2],"");
			          PicassoUtils.initImage(getActivity(), "shareOrder"+split[2], img_bigger);

			          dialog.show();
				}
			});

			return convertView;
		}
		protected void setParams(android.view.WindowManager.LayoutParams lay) {

			  DisplayMetrics dm = new DisplayMetrics();

			  getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

			  Rect rect = new Rect();

			  View view = getActivity().getWindow().getDecorView();

			  view.getWindowVisibleDisplayFrame(rect);

			  lay.height = dm.heightPixels/* - rect.top*/;

			  lay.width = dm.widthPixels;

		}
	}

	private class ViewHolder{
		private TextView name_head,tv_huiyuan,tv_time,tv_content,show_all,tv_num_dianzan,tv_num_pingjia,tv_num_fenxiang;
		private ImageView	/*img_head,*/img_huiyuan,sing_martrix,sing_changfangxing,sing_changfangxing_vertical,more_img_one,more_img_two,more_img_three
		,tv_dianzan,img_pingjia,img_fenxiang;
		private LinearLayout img_more_ll,ll_dianzan,ll_pingjia,ll_fenxiang;
		private View view_cut_one,view_cut_two;
		private RoundImageButton img_head;
	}

	private Fragment fragment;

	private ArrayList list;

	private String choice;

	private String my_choice;

	private LinearLayout account_nodata;

	public void setOnZeroRefreshListener(Fragment fragment) {
		this.fragment=fragment;

	}
}

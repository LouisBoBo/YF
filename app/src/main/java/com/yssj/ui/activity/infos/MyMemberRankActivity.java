//package com.yssj.ui.activity.infos;
//
//import java.util.HashMap;
//
//import com.yssj.activity.R;
//import com.yssj.app.AppManager;
//import com.yssj.app.SAsyncTask;
//import com.yssj.custom.view.RoundImageButton;
//import com.yssj.entity.UserInfo;
//import com.yssj.model.ComModel2;
//import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.main.ForceLookActivity;
//import com.yssj.ui.activity.main.HotSaleActivity;
//import com.yssj.ui.base.BasicActivity;
//import com.yssj.utils.SetImageLoader;
//import com.yssj.utils.YCache;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
///***
// * 我的会员 等级权益详情查看
// */
//public class MyMemberRankActivity extends BasicActivity implements OnClickListener {
//
//	private View img_back;
//	private TextView range_btn;
//	private ImageView mRankImg, mRrankPoint0, mRrankPoint1, mRrankPoint2, mRrankPoint3, mRrankPoint4;// 进度条上的分割点小圆
//	private RoundImageButton mHead;
//	private TextView mUserNmae, mRankTv, mRrank0, mRrank1, mRrank2, mRrank3, mRrank4;// 用户昵称
//																						// 会员活力值
//																						// 活力值等级的分割点的textview
//	private ProgressBar mRankPb;// 显示活力值的进度条
//	private int rank0, rank1, rank2, rank3, rank4;// 活力值等级的分割点
//	private String member_nor_name,member_bro_name,member_sil_name,member_gold_name;//会员名称
//	private TextView member_nor,member_bro,member_sil,member_gold;//会员名称Textview
//	private int rankValue;// 活力值
////	private int mRankImgValue;// 会员等级
//
//	private UserInfo info ;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_my_member_rank);
//		AppManager.getAppManager().addActivity(this);
//		initView();
//	}
//
//	/**
//	 * 初始化View
//	 */
//	private void initView() {
//		img_back = findViewById(R.id.img_back);
//		img_back.setOnClickListener(this);
//		range_btn = (TextView) findViewById(R.id.my_member_rank_range_btn);
//		range_btn.setOnClickListener(this);
//		mHead = (RoundImageButton) findViewById(R.id.img_user_head);
//		mRankImg = (ImageView) findViewById(R.id.img_user_head_rank);
//		mRankTv = (TextView) findViewById(R.id.my_member_rank_range);
//		mUserNmae = (TextView) findViewById(R.id.my_member_rank_user_name);
//		mRrank0 = (TextView) findViewById(R.id.range_prgress_tv0);
//		mRrank1 = (TextView) findViewById(R.id.range_prgress_tv1);
//		mRrank2 = (TextView) findViewById(R.id.range_prgress_tv2);
//		mRrank3 = (TextView) findViewById(R.id.range_prgress_tv3);
//		mRrank4 = (TextView) findViewById(R.id.range_prgress_tv4);
//		member_nor=(TextView) findViewById(R.id.member_nor);
//		member_bro=(TextView) findViewById(R.id.member_bro);
//		member_sil=(TextView) findViewById(R.id.member_sil);
//		member_gold=(TextView) findViewById(R.id.member_gold);
//		mRrankPoint0 = (ImageView) findViewById(R.id.range_prgress_point0);
//		mRrankPoint1 = (ImageView) findViewById(R.id.range_prgress_point1);
//		mRrankPoint2 = (ImageView) findViewById(R.id.range_prgress_point2);
//		mRrankPoint3 = (ImageView) findViewById(R.id.range_prgress_point3);
//		mRrankPoint4 = (ImageView) findViewById(R.id.range_prgress_point4);
//		mRankPb = (ProgressBar) findViewById(R.id.range_prgress_pb);
//
//		info = YCache.getCacheUserSafe(this);
//		mUserNmae.setText(info.getNickname());
//		SetImageLoader.initImageLoader(this, mHead, info.getPic(), "");
//		getMemberRank();
//		initData();
//
//	}
//
//	/**
//	 * 获取会员等级分割点和等级名称
//	 */
//	private void getMemberRank(){
//		HashMap<String, String> map = MainMenuActivity.userGradeTable;
//		try {
//			rank0 = 0;
//			rank1 = Integer.parseInt((String)(map.get("1")).split(",")[0].split("-")[0].trim());
//			rank2 = Integer.parseInt((String)(map.get("2")).split(",")[0].split("-")[0].trim());
//			rank3 = Integer.parseInt((String)(map.get("3")).split(",")[0].trim());
//			rank4 = rank3+200;	
//		} catch (Exception e) {
//			rank0 = 0;
//			rank1 = 40;
//			rank2 = 100;
//			rank3 = 300;
//			rank4 = 500;
//		}
//		try {
//			member_nor_name = map.get("0").split(",")[1].trim();
//			member_bro_name = map.get("1").split(",")[1].trim();
//			member_sil_name = map.get("2").split(",")[1].trim();
//			member_gold_name = map.get("3").split(",")[1].trim();
//			member_nor.setText(member_nor_name+"会员");
//			member_bro.setText(member_bro_name+"会员");
//			member_sil.setText(member_sil_name+"会员");
//			member_gold.setText(member_gold_name+"会员");
//		} catch (Exception e) {
//			
//		}
//		mRrank0.setText(rank0 + "");
//		mRrank1.setText(rank1 + "");
//		mRrank2.setText(rank2 + "");
//		mRrank3.setText(rank3 + "");
//		mRrank4.setText(rank4 + "");
//	}
//	/**
//	 * 获取数据 活力值
//	 */
//	private void initData() {
//		new SAsyncTask<Void, Void, Integer>((FragmentActivity) context, 0) {
//			@Override
//			protected Integer doInBackground(FragmentActivity context, Void... params)
//					throws Exception {
//				return ComModel2.getVitality(context);
//			}
//
//			protected boolean isHandleException() {
//				return true;
//			};
//
//			@Override
//			protected void onPostExecute(FragmentActivity context, Integer result, Exception e) {
//				super.onPostExecute(context, result, e);
//				if (e == null && result != null) {
//					rankValue =result;
//				}else{
//					rankValue =0;
//				}
//				mRankTv.setText("活力值：" + rankValue);
//				setPregress();
//			}
//		}.execute();
//
//	}
//	
//	/**
//	 * 设置活力值的进度条
//	 * 共有四段 每一段不均  rankValue在那一段 就按照哪一段的差值为准来设置setMax和setProgress
//	 */
//	private void setPregress(){
//		if(rankValue<=0){//普通会员
//			mRankPb.setMax(100);
//			mRankPb.setProgress(0);
//			mRankImg.setImageResource(R.drawable.icon_vip_nor_s);
//		}else
//		if (rankValue > 0 && rankValue < rank1) {//普通会员
//			mRankPb.setMax(rank1 * 4);
//			mRankPb.setProgress(rankValue);
//			mRrankPoint0.setImageResource(R.drawable.shape_point_full);
//			mRankImg.setImageResource(R.drawable.icon_vip_nor_s);
//		} else if (rankValue >= rank1 && rankValue < rank2) {//青铜会员
//			mRankPb.setMax((rank2 - rank1) * 4);
//			mRankPb.setProgress((rankValue - rank1) + (rank2 - rank1) * 1);
//			mRrankPoint0.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint1.setImageResource(R.drawable.shape_point_full);
//			mRankImg.setImageResource(R.drawable.icon_vip_bronze_s);
//		} else if (rankValue >= rank2 && rankValue < rank3) {//白银会员
//			mRankPb.setMax((rank3 - rank2) * 4);
//			mRankPb.setProgress((rankValue - rank2) + (rank3 - rank2) * 2);
//			mRrankPoint0.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint1.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint2.setImageResource(R.drawable.shape_point_full);
//			mRankImg.setImageResource(R.drawable.icon_vip_silver_s);
//		} else if (rankValue >= rank3 && rankValue < rank4) {//黄金会员
//			mRankPb.setMax((rank4 - rank3) * 4);
//			mRankPb.setProgress((rankValue - rank3) + (rank4 - rank3) * 3);
//			mRrankPoint0.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint1.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint2.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint3.setImageResource(R.drawable.shape_point_full);
//			mRankImg.setImageResource(R.drawable.icon_vip_gold_s);
//		} else if (rankValue >= rank4) {//黄金会员
//			mRankPb.setMax(100);
//			mRankPb.setProgress(100);
//			mRrankPoint0.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint1.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint2.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint3.setImageResource(R.drawable.shape_point_full);
//			mRrankPoint4.setImageResource(R.drawable.shape_point_full);
//			mRankImg.setImageResource(R.drawable.icon_vip_gold_s);
//		}
//
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.img_back:
//			onBackPressed();
//			break;
//		case R.id.my_member_rank_range_btn:// 补充活力值
//			Intent intent = new Intent(context, HotSaleActivity.class);
//			intent.putExtra("id", "6");
//			intent.putExtra("title", "热卖");
//			context.startActivity(intent);
//			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//			break;
//		default:
//			break;
//		}
//	}
//
//}
//
//
////暂时不用
//// if(rankValue>0&&rankValue<rank1){//进度条分段显示
//// if(rankValue>0&&rankValue<(rank1/3)){
//// mRankPb.setProgress(8);
//// }else if(rankValue>=(rank1/3)&&rankValue<(2*rank1/3)){
//// mRankPb.setProgress(16);
//// }else if(rankValue>=(2*rank1/3)){
//// mRankPb.setProgress(20);
//// }
//// }else if(rankValue>=rank1&&rankValue<rank2){
//// if(rankValue==rank1){
//// mRankPb.setProgress(25);
//// }else
//// if(rankValue-rank1>0&&rankValue-rank1<((rank2-rank1)/3)){
//// mRankPb.setProgress(33);
//// }else
//// if(rankValue-rank1>=((rank2-rank1)/3)&&rankValue-rank1<(2*(rank2-rank1)/3)){
//// mRankPb.setProgress(40);
//// }else if(rankValue-rank1>=(2*(rank2-rank1)/3)){
//// mRankPb.setProgress(45);
//// }
//// }else if(rankValue>=rank2&&rankValue<rank3){
//// if(rankValue==rank2){
//// mRankPb.setProgress(50);
//// }else
//// if(rankValue-rank2>0&&rankValue-rank2<((rank3-rank2)/3)){
//// mRankPb.setProgress(58);
//// }else
//// if(rankValue-rank2>=((rank3-rank2)/3)&&rankValue-rank2<(2*(rank3-rank2)/3)){
//// mRankPb.setProgress(66);
//// }else if(rankValue-rank2>=(2*(rank3-rank2)/3)){
//// mRankPb.setProgress(70);
//// }
//// }else if(rankValue>=rank3&&rankValue<rank4){
//// if(rankValue==rank3){
//// mRankPb.setProgress(75);
//// }else
//// if(rankValue-rank3>0&&rankValue-rank3<((rank4-rank3)/3)){
//// mRankPb.setProgress(83);
//// }else
//// if(rankValue-rank3>=((rank4-rank3)/3)&&rankValue-rank3<(2*(rank4-rank3)/3)){
//// mRankPb.setProgress(90);
//// }else if(rankValue-rank3>=(2*(rank4-rank3)/3)){
//// mRankPb.setProgress(95);
//// }
//// }else if(rankValue>=rank4){
//// mRankPb.setMax(100);
//// mRankPb.setProgress(100);
//// }

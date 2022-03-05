package com.yssj.custom.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

//import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
//import com.nostra13.universalimageloader.core.ImageLoader;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.entity.ShopOption;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.ui.activity.H5Activity2;
import com.yssj.ui.activity.MainFragment;
import com.yssj.ui.activity.MainMenuActivity;
//import com.yssj.ui.activity.MainMenuActivity.GetSlidingMenu;
//import com.yssj.ui.activity.infos.InviteCodeActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;
import com.yssj.utils.WXminiAppUtil;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
 * 
 * @author caizhiming
 * 
 */

public class ZeroShopSlideShowView extends FrameLayout {

	// 轮播图图片数量
	private final static int IMAGE_COUNT = 5;
	// 自动轮播的时间间隔
	// private final static int TIME_INTERVAL = 5;
	// 自动轮播启用开关
	private final static boolean isAutoPlay = true;
	// private Boolean isZero=true;
	// 自定义轮播图的资源ID
	private int[] imagesResIds;
	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;
	private Boolean isFrist;
	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;
	// 轮播图片数据
	private List<ShopOption> options = null;
	private View view1, view2, view3, view4, view5, view6, view7;
	private int[] images = new int[] { R.drawable.advertisement };

	private String[] codes = new String[] { "/shop_option/2015-11-17/19_05_10.jpg" };
	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public ZeroShopSlideShowView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ZeroShopSlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZeroShopSlideShowView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.layout_ad_viewpager,
				this, true);
		initData();
		isRefresh = true;
	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		if (scheduledExecutorService == null) {
			scheduledExecutorService = Executors
					.newSingleThreadScheduledExecutor();
			scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
					1, 6, TimeUnit.SECONDS);
		}
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	private MyPagerAdapter adapter;

	/**
	 * 初始化相关Data
	 */
	private void initData() {
		imagesResIds = new int[] { R.drawable.m_top_img };
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);
		adapter = new MyPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	private boolean isRefresh = false;

	/***
	 * 设置shopOption数据
	 */
	public void setData(List<ShopOption> options, Context context) {
		if (isRefresh == false) {
			return;
		}
		isRefresh = false;
		this.options = options;
		initUI(context);
		if (isAutoPlay) {
			startPlay();
		}
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	/**
	 * 初始化Views等UI
	 */
	private void initUI(final Context context) {
		if (null != options && options.size() != 0) {
			imageViewsList.clear();
			for (int i = 0; i < options.size() + 2; i++) {
				// final ShopOption option = options.get(i);
				ImageView view = new ImageView(context);
				view.setScaleType(ScaleType.FIT_XY);

				if (i == 0) {

				    PicassoUtils.initImage(context,options.get(options.size() - 1)
                            .getUrl().substring(1, options.get(options.size() - 1).getUrl().length())+"!560",view);



//					SetImageLoader
//							.initImageLoader(
//									context,
//									view, options.get(options.size() - 1)
//											.getUrl().substring(1, options.get(options.size() - 1).getUrl().length()),
//									"!560");
//					view.setId(options.size() - 1);




				} else if (i == options.size() + 1) {
					if (options.get(0).getUrl().equals(codes[0])) {
						view.setImageResource(images[0]);
					} else {

					    PicassoUtils.initImage(context,	options.get(0)
                                .getUrl()
                                .substring(
                                        1,
                                        options.get(0).getUrl()
                                                .length()) +"!560",view);



//						SetImageLoader.initImageLoader(
//								context,
//								view,
//								options.get(0)
//										.getUrl()
//										.substring(
//												1,
//												options.get(0).getUrl()
//														.length()), "!560");
					}
					view.setId(0);
				} else {
					if (i == 1) {
						if (options.get(0).getUrl().equals(codes[0])) {
							view.setImageResource(images[0]);
						} else {


						    PicassoUtils.initImage(context,options.get(0)
                                    .getUrl()
                                    .substring(
                                            1,
                                            options.get(0).getUrl()
                                                    .length())+"!560",view);

//							SetImageLoader.initImageLoader(
//									context,
//									view,
//									options.get(0)
//											.getUrl()
//											.substring(
//													1,
//													options.get(0).getUrl()
//															.length()), "!560");
						}
						view.setId(0);
					} else {

					    PicassoUtils.initImage(context,	options.get(i - 1)
                                .getUrl()
                                .substring(
                                        1,
                                        options.get(i - 1).getUrl()
                                                .length())+ "!560",view);

//						SetImageLoader.initImageLoader(
//								context,
//								view,
//								options.get(i - 1)
//										.getUrl()
//										.substring(
//												1,
//												options.get(i - 1).getUrl()
//														.length()), "!560");
						view.setId(i - 1);
					}
				}

				imageViewsList.add(view);
				// if (i == 1) {
				// view.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View arg0) {
				// // TODO Auto-generated method stub
				// if (YJApplication.instance.isLoginSucess() == false) {
				// ToLoginDialog dialog = new ToLoginDialog(
				// context);
				// dialog.show();
				// return;
				// }
				// Intent intent = new Intent(context,
				// InviteCodeActivity.class);
				// // intent.putExtra("code", option.getShop_code());
				// ((FragmentActivity) context).startActivity(intent);
				// }
				// });
				// } else
				// if (i == options.size() + 1) {
				// view.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View arg0) {
				// if (YJApplication.instance.isLoginSucess() == false) {
				// ToLoginDialog dialog = new ToLoginDialog(
				// context);
				// dialog.show();
				// return;
				// }
				// Intent intent = new Intent(context,
				// InviteCodeActivity.class);
				// // intent.putExtra("code", option.getShop_code());
				// ((FragmentActivity) context).startActivity(intent);
				// }
				// });
				// }
				// else {
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						/*
						 * Intent intent = new Intent(context,
						 * ShopDetailsActivity.class); intent.putExtra("code",
						 * options.get(arg0.getId()) .getShop_code());
						 * MyLogYiFu.e("0Yuangou", options.get(arg0.getId())
						 * .getShop_code()); intent.putExtra("isMeal", true);
						 * ((FragmentActivity) context)
						 * .startActivityForResult(intent, 102);
						 * ((FragmentActivity) context)
						 * .overridePendingTransition( R.anim.slide_left_in,
						 * R.anim.slide_left_out);
						 */

						ShopOption option = options.get(arg0.getId());

						if (option.getOption_type() == null) {
							return;
						}
						if (option.getOption_type() == 5) {
							// 跳往h5活动页
							Intent intent = new Intent(context,
									H5Activity2.class);
							intent.putExtra("h5_code", options
									.get(arg0.getId()).getShop_code());
							context.startActivity(intent);

						} else if (option.getOption_type() == 4) {
							// 跳往签到页
							((MainFragment) MainMenuActivity.instances
									.getSupportFragmentManager()
									.findFragmentByTag("tag")).setIndex(2);
						} else if (option.getOption_type() == 2) {

							if (YJApplication.instance.isLoginSucess() == false) {
								ToLoginDialog dialog = new ToLoginDialog(
										context);
								dialog.show();
								return;
							}
//							Intent intent = new Intent(context,
//									InviteCodeActivity.class);
//							((FragmentActivity) context).startActivity(intent);
						} else if (option.getOption_type() == 3) {
							if (YJApplication.instance.isLoginSucess() == false) {
								ToLoginDialog dialog = new ToLoginDialog(
										context);
								dialog.show();
								return;
							}
							WXminiAppUtil.jumpToWXmini(context);

						} else if(option.getOption_type() == 1) {
							Intent intent = new Intent(context,
									ShopDetailsActivity.class);
							intent.putExtra("code", options.get(arg0.getId())
									.getShop_code());
							LogYiFu.e("0Yuangou", options.get(arg0.getId())
									.getShop_code());
							intent.putExtra("isMeal", true);
							((FragmentActivity) context)
									.startActivityForResult(intent, 102);
							((FragmentActivity) context)
									.overridePendingTransition(
											R.anim.slide_left_in,
											R.anim.slide_left_out);

						}
					}

				});
				// }
			}
			adapter.notifyDataSetChanged();
			dotViewsList.clear();
			for (int i = 0; i < options.size(); i++) {
				switch (i) {
				case 0: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.GONE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.GONE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.GONE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.GONE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.GONE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot1));
				}
					break;
				case 1: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.GONE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.GONE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.GONE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.GONE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot2));
				}
					break;
				case 2: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.VISIBLE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.GONE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.GONE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.GONE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot3));
				}
					break;
				case 3: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.VISIBLE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.VISIBLE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.GONE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.GONE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot4));
				}
					break;
				case 4: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.VISIBLE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.VISIBLE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.VISIBLE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.GONE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot5));
				}
					break;
				case 5: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.VISIBLE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.VISIBLE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.VISIBLE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.VISIBLE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.GONE);
					dotViewsList.add(findViewById(R.id.v_dot6));
				}
					break;
				case 6: {
					view1 = findViewById(R.id.v_dot1);
					view1.setVisibility(View.VISIBLE);
					view2 = findViewById(R.id.v_dot2);
					view2.setVisibility(View.VISIBLE);
					view3 = findViewById(R.id.v_dot3);
					view3.setVisibility(View.VISIBLE);
					view4 = findViewById(R.id.v_dot4);
					view4.setVisibility(View.VISIBLE);
					view5 = findViewById(R.id.v_dot5);
					view5.setVisibility(View.VISIBLE);
					view6 = findViewById(R.id.v_dot6);
					view6.setVisibility(View.VISIBLE);
					view7 = findViewById(R.id.v_dot7);
					view7.setVisibility(View.VISIBLE);
					dotViewsList.add(findViewById(R.id.v_dot7));
				}
					break;
				default:
					break;
				}

			}
			viewPager.setCurrentItem(1);
		} else {
			for (int imageID : imagesResIds) {
				ImageView view = new ImageView(context);
				view.setImageResource(imageID);
				view.setScaleType(ScaleType.FIT_XY);
				imageViewsList.add(view);
			}
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 填充ViewPager的页面适配器
	 *
	 * @author caizhiming
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 *
	 * @author caizhiming
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		// boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:// 手势滑动，空闲中
					// isAutoPlay = false;
				break;
			case 2:// 界面切换中
					// isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
					// if (viewPager.getCurrentItem() == viewPager.getAdapter()
				// .getCount() - 1 && !isAutoPlay) {
				// viewPager.setCurrentItem(0);
				// }
				// // 当前为第一张，此时从左向右滑，则切换到最后一张
				// else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
				// viewPager
				// .setCurrentItem(viewPager.getAdapter().getCount() - 1);
				// }
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			currentItem = pos;
			if (pos == 0) {
				pos = imageViewsList.size() - 2;
				viewPager.setCurrentItem(pos, false);
			}

			if (pos == imageViewsList.size() - 1) {
				pos = 1;
				viewPager.setCurrentItem(1, false);
			}

			pos = pos - 1;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.img_round_checked);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.img_round_default);
				}
			}
		}

	}

	/**
	 * 执行轮播图切换任务
	 *
	 * @author caizhiming
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * 销毁ImageView资源，回收内存
	 *
	 * @author caizhiming
	 */
	private void destoryBitmaps() {

		for (int i = 0; i < IMAGE_COUNT; i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}

}
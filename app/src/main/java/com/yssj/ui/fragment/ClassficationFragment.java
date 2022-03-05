package com.yssj.ui.fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yssj.YConstance.Pref;
import com.yssj.YJApplication;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.ClassficationListCustomView;
import com.yssj.custom.view.MyGridView;
import com.yssj.custom.view.YuanJiaoImageView;
import com.yssj.data.YDBHelper;
import com.yssj.entity.VipInfo;
import com.yssj.model.ComModel2;
import com.yssj.model.ComModelL;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.classfication.ClassficationActivity;
import com.yssj.ui.activity.classfication.ClassficationSearchActivity;
import com.yssj.ui.activity.classfication.ManufactureActivity;
import com.yssj.ui.activity.main.MoreSubjectActivity;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.ReadJsonFileUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang.time.DateFormatUtils;
import org.json.JSONObject;

/***
 * 购物------------就是老版的分类
 */
public class ClassficationFragment extends Fragment implements View.OnClickListener {
    private static final String TAB = "tab1";
    private boolean tongJiFirst = true;
    private Context mContext;
    private LinearLayout search_ll;
    private LinearLayout mContainer;

    private LinkedHashMap<String, List<HashMap<String, String>>> mapAll;
    private TextView et_search;
    private View rl_sign;
    private TextView tv_fenlei;
    private ImageView iv_hongbao;
    private MyGridView mMyGridView;
    private List<HashMap<String, String>> listDataTop;
    //	private boolean isActivity;// 从购物页面跳转的分类页面
//	private View historical_search_rootview;
//	private ImageView et_search_xx;
//	private View historical_search;
//	private ScrollView scoll_view;
//	private View ll_title, imageBack;
//	private FlowLayout historical_search_flowlayout, hot_search_flowlayout;// 历史搜索
//	private LayoutInflater mInflater;
//	private List<HashMap<String, String>> mDatasHis, mDatasHot;
//	private RecordsDao recordsDao;
    private YDBHelper dbHelp;
    private String[] titles;
    private int[] types;
    private LinearLayout redShare;
    private ImageView moneyShare;

    private boolean mIsSignLiulan;

    public static ClassficationFragment newInstance(String title, Context context) {
        ClassficationFragment fragment = new ClassficationFragment();
        Bundle args = new Bundle();
        args.putString(TAB, title);
        fragment.setArguments(args);
//		mContext = context;
        return fragment;

    }

    public static ClassficationFragment newInstance(String title, Context context, boolean isSignLiulan) {
        ClassficationFragment fragment = new ClassficationFragment();
        Bundle args = new Bundle();
        args.putString(TAB, title);
        args.putBoolean("isSignLiulan", isSignLiulan);
        fragment.setArguments(args);
//		mContext = context;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_classfication, container, false);
//		mInflater = LayoutInflater.from(mContext);
//		mDatasHis = new ArrayList<HashMap<String, String>>();
//		mDatasHot = new ArrayList<HashMap<String, String>>();
//		String title = (String) getArguments().get(TAB);
        mIsSignLiulan = getArguments().getBoolean("isSignLiulan");


        mContext = getActivity();
        redShare = (LinearLayout) v.findViewById(R.id.red_share_ll);
        moneyShare = (ImageView) v.findViewById(R.id.money_share_iv);
        mContainer = (LinearLayout) v.findViewById(R.id.container);
        iv_hongbao = (ImageView) v.findViewById(R.id.iv_hongbao);
        iv_hongbao.setOnClickListener(this);

        et_search = (TextView) v.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);
        rl_sign = v.findViewById(R.id.rl_sign);
        rl_sign.setOnClickListener(this);
        search_ll = (LinearLayout) v.findViewById(R.id.search_ll);
        if (mIsSignLiulan) {
            search_ll.setVisibility(View.GONE);
        } else {
            search_ll.setVisibility(View.VISIBLE);

        }


        if (!GuideActivity.hasSign) {
            rl_sign.setVisibility(View.INVISIBLE);
        }


        tv_fenlei = (TextView) v.findViewById(R.id.tv_fenlei);
        tv_fenlei.setOnClickListener(this);
        mapAll = new LinkedHashMap<String, List<HashMap<String, String>>>();
//		if ("ClassficationActivity".equals(title)) {
//			isActivity = true;
//		}
        // ll_title = v.findViewById(R.id.ll_title);
        // imageBack = v.findViewById(R.id.img_back);
        // imageBack.setOnClickListener(this);
//		historical_search_rootview = v.findViewById(R.id.historical_search_rootview);
//		et_search_xx = (ImageView) v.findViewById(R.id.et_search_xx);
//		et_search_xx.setOnClickListener(this);
//		historical_search = v.findViewById(R.id.historical_search);
//		v.findViewById(R.id.historical_search_delete_icon).setOnClickListener(this);
//		scoll_view = (ScrollView) v.findViewById(R.id.scoll_view_classfalication);
//		historical_search_flowlayout = (FlowLayout) v.findViewById(R.id.historical_search_flowlayout);
//		historical_search_flowlayout.setMaxLine(3);
//		hot_search_flowlayout = (FlowLayout) v.findViewById(R.id.hot_search_flowlayout);

//		recordsDao = new RecordsDao(mContext);
//		if (isActivity) {
//			// ll_title.setVisibility(View.GONE);
//			// imageBack.setVisibility(View.VISIBLE);
//			tv_zhuanqian.setVisibility(View.VISIBLE);
//			historical_search_rootview.setVisibility(View.VISIBLE);
//			v.findViewById(R.id.horizontal_line).setVisibility(View.VISIBLE);
////			et_search.requestFocus();// 获得焦点
//		} else {
//			// ll_title.setVisibility(View.VISIBLE);
//			// imageBack.setVisibility(View.GONE);
//			// tv_zhuanqian.setVisibility(View.GONE);
//			historical_search_rootview.setVisibility(View.GONE);
//			v.findViewById(R.id.horizontal_line).setVisibility(View.GONE);
//		}
        mMyGridView = (MyGridView) v.findViewById(R.id.gv_classfication);
        return v;
    }

    private void addView(LinearLayout container, HashMap<String, List<HashMap<String, String>>> mapAll) {
        container.removeAllViews();
        Iterator<Entry<String, List<HashMap<String, String>>>> iterator = mapAll.entrySet().iterator();
        for (int j = 0; j < mapAll.size(); j++) {
            Map.Entry<String, List<HashMap<String, String>>> entry = iterator.next();
            List<HashMap<String, String>> listData = entry.getValue();
            String fLevel = entry.getKey();
            ClassficationListCustomView clcView = new ClassficationListCustomView(mContext);
            clcView.setTextView(fLevel);
            clcView.setGridView(listData);
            if (listData.size() != 0) {
                container.addView(clcView);
            }
        }
//		if (isActivity) {
//			((ClassficationListCustomView) container.getChildAt(0)).hideTopLine();
//		}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//		setEditextLstener();
//		setEditextDeleteListener();
//		setScollListener();
        setZhuanIconAnim();
        titles = new String[]{"流行趋势", "上衣", "裤子", "裙子", "套装"};// 一级类目名称
        types = new int[]{0, 2, 4, 3, 7};// 一级类目ID
        if (null != mContext) {
            dbHelp = new YDBHelper(mContext);
        } else {
            dbHelp = new YDBHelper(getActivity());
        }
        setTopGridView();
        for (int i = 0; i < titles.length; i++) {
            String sql = "select * from type_tag where type = " + types[i] + " and class_type = 1 order by _id";
            List<HashMap<String, String>> listSLevel = dbHelp.query(sql);
            mapAll.put(titles[i], listSLevel);
        }
        addView(mContainer, mapAll);

        getHotTag();
    }

    /**
     * 获取热门搜索标签
     */
    private void getHotTag() {

        new SAsyncTask<Void, Void, List<HashMap<String, String>>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected List<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getHotTag(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<HashMap<String, String>> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null && result.size() > 0) {
//					for (int i = 0; i < result.size(); i++) {
//						String tag_id = result.get(i).get("tag_id");
//						String hotSql = "select * from tag_info where _id = " + tag_id;
//						List<HashMap<String, String>> datasHot = dbHelp.query(hotSql);
//						if (i == 0 && datasHot.size() > 0) {
//							et_search.setText(datasHot.get(0).get("attr_name"));
//						}
//						mDatasHot.addAll(dbHelp.query(hotSql));
//					}
//					initHotChildViews(mDatasHot, 1);// type_tag 和 tag_info
//													// name键值 不同

                    String tag_id = result.get(0).get("tag_id");
                    String hotSql = "select * from tag_info where _id = " + tag_id;
                    List<HashMap<String, String>> datasHot = dbHelp.query(hotSql);
                    if (datasHot.size() > 0) {
                        et_search.setText(datasHot.get(0).get("attr_name"));
                    }

                }
//				else {
//					String hotSql = "select * from type_tag where type = " + types[0] + " order by _id";// 没有数据时候
//																										// 使用流行趋势填充
//					mDatasHot = dbHelp.query(hotSql);
//					initHotChildViews(mDatasHot, 0);// type_tag 和 tag_info
//													// name键值 不同
//				}

            }
        }.execute();
    }

//	/**
//	 * 填充热门搜索
//	 */
//	public void initHotChildViews(List<HashMap<String, String>> mDatasHot, int type) {
//		for (int i = 0; i < mDatasHot.size(); i++) {
//			TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, hot_search_flowlayout, false);
//			String name = "";// type_tag 和 tag_info name键值 不同
//			if (type == 1) {
//				name = mDatasHot.get(i).get("attr_name");
//			} else {
//				name = mDatasHot.get(i).get("class_name");
//			}
//			tv.setText(name);
//			final String words = name;
//			// 点击事件
//			tv.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO 点击跳转
//					// HashMap<String, String> recordsMap = new HashMap<String,
//					// String>();
//					// recordsMap.put("class_name", class_name);
//					// recordsMap.put("class_id", class_id);
//					// recordsDao.addRecords(recordsMap);//添加搜索记录
//					Intent intent = new Intent();
//					intent = new Intent(mContext, WordSearchResultActivity.class);
//					intent.putExtra("words", words.trim());
//					intent.putExtra("notType", true);
//					// intent.putExtra("class_id", class_id.trim());
//					startActivity(intent);
//				}
//			});
//			hot_search_flowlayout.addView(tv);// 添加到父View
//		}
//	}
//	/**
//	 * 填充历史搜索
//	 * 
//	 * @date 2016年12月29日下午3:32:37
//	 */
//	public void initHistoryChildViews() {
//		/**
//		 * 找到搜索标签的控件
//		 */
//		historical_search_flowlayout.removeAllViews();
//		for (int i = 0; i < mDatasHis.size(); i++) {
//			TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv, historical_search_flowlayout, false);
//			// final String class_id = mDatasHis.get(i).get("class_id");
//			final String words = mDatasHis.get(i).get("class_name");
//			tv.setText(words);
//			// 点击事件
//			tv.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO 点击跳转
//					// HashMap<String, String> recordsMap = new HashMap<String,
//					// String>();
//					// recordsMap.put("class_name", class_name);
//					// recordsMap.put("class_id", class_id);
//					// recordsDao.addRecords(recordsMap);//添加搜索记录
//
//					Intent intent = new Intent();
//					intent = new Intent(mContext, WordSearchResultActivity.class);
//					intent.putExtra("words", words.trim());
//					intent.putExtra("notType", true);
//					startActivity(intent);
//				}
//			});
//			historical_search_flowlayout.addView(tv);// 添加到父View
//		}
//	}

//	private void setScollListener() {
//		scoll_view.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (historical_search_rootview.getVisibility() == View.VISIBLE) {// 搜索历史显示出来就让
//																					// ScollView不可以滚动
//					return true;
//				}
//				return false;
//			}
//		});
//	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sign:

                //如果是审核员，不在注册当天就不能点
                if(YJApplication.instance.isLoginSucess() && YCache.getCacheUser(mContext).getReviewers() ==1 ){
                    //判断是否是在注册当天
                    long serviceTime = System.currentTimeMillis()+ YJApplication.serviceDifferenceTime;
                    String add_date = "";
                    try {
                        add_date = YCache.getCacheUser(mContext).getAdd_date().split(" ")[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String currentDate = DateFormatUtils.format(serviceTime, "yyyy-MM-dd");
                    if (!add_date.equals(currentDate)) {
                        ToastUtil.showShortText2("您已领取奖励，祝您购物愉快。");
                        return;
                    }
                }



                // 跳至赚钱
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                startActivity(new Intent(mContext, CommonActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                redShare.clearAnimation();
                moneyShare.clearAnimation();
                SharedPreferencesUtil.saveStringData(mContext, Pref.SHAREANIMZHUNA, System.currentTimeMillis() + "");

                // et_search.getText().clear();
                // et_search.clearFocus();// 输入框失去焦点
                // tv_zhuanqian.setVisibility(View.GONE);
                // View view = getActivity().getWindow().getDecorView();
                // InputMethodManager inputmanger = (InputMethodManager) ((Activity)
                // mContext)
                // .getSystemService(Context.INPUT_METHOD_SERVICE);
                // if (inputmanger != null) {
                // inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);//
                // 关闭键盘
                // }
                // if (historical_search_rootview.getVisibility() == View.VISIBLE) {
                // TranslateAnimation mHiddenAction = new
                // TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                // Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                // 0.0f,
                // Animation.RELATIVE_TO_SELF, -1.0f);
                // mHiddenAction.setDuration(100);
                // historical_search_rootview.startAnimation(mHiddenAction);
                // historical_search_rootview.setVisibility(View.GONE);
                // }
                break;
//		case R.id.historical_search_delete_icon:
//			deleteHositorySearch();
//			break;
            case R.id.tv_fenlei: // 跳至分类
                Intent intentClassfication = new Intent(getActivity(), ClassficationActivity.class);
                startActivity(intentClassfication);

                break;
            case R.id.img_back:
                getActivity().onBackPressed();
//			et_search.clearFocus();// 输入框失去焦点
                break;
//		case R.id.et_search_xx:
//			et_search.getText().clear();
//			break;
            case R.id.et_search://跳至搜索页
//			ToastUtil.showShortText(mContext, "跳至搜索界面");
                Intent intentSearch = new Intent(getActivity(), ClassficationSearchActivity.class);
                ((Activity) mContext).getWindow().getDecorView().getRootView().setDrawingCacheEnabled(true);
                Bitmap bitmap_bg = ((Activity) mContext).getWindow().getDecorView().getRootView().getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap_bg.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                byte[] bitmapDatas = baos.toByteArray();
                intentSearch.putExtra("bitmapDatas", bitmapDatas);
                startActivity(intentSearch);
                try {
                    baos.close();
                    ((Activity) mContext).getWindow().getDecorView().getRootView().destroyDrawingCache();
                } catch (IOException e) {

                }
                break;



            case R.id.iv_hongbao:
//                if (YJApplication.instance.isLoginSucess()) {
//                    //查询交易记录，没有的跳首页3  有就跳赚钱
//                    new SAsyncTask<String, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                        @Override
//                        protected Boolean doInBackground(FragmentActivity context, String... params)
//                                throws Exception {
//                            return ComModel2.queryHasJYJL(mContext);
//                        }
//
//                        @Override
//                        protected boolean isHandleException() {
//                            return true;
//                        }
//
//                        @Override
//                        protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                            super.onPostExecute(context, result, e);
//                            if (null == e) {
//
//                                if (result) {
//                                    // 分享+跳至首页3
//
//                                } else {
//                                    // 跳至赚钱
//                                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
//
//                                }
//                                startActivity(new Intent(mContext, CommonActivity.class));
//                                ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//
//                            }
//                        }
//
//                    }.execute();
//
//
//                } else {
                    // 跳至赚钱
                    SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                    startActivity(new Intent(mContext, CommonActivity.class));
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setHongbaoAnim();
        LogYiFu.e("2020-2020","购物onResume");

//		mDatasHis.clear();
//		mDatasHis = recordsDao.getRecordsList();
//		if (mDatasHis.size() == 0) {
//			historical_search.setVisibility(View.GONE);
//		} else {
//			historical_search.setVisibility(View.VISIBLE);
//			initHistoryChildViews();
//		}
        if ("107".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))
                && !tongJiFirst) {
            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "107");
            TongJiUtils.TongJi(mContext, 7 + "");
            LogYiFu.e("TongJiNew", 7 + "");
        }
        tongJiFirst = false;//首次进来时候 在点击首页下面五个按钮时候统计 所以首次这里不统计避免重复
    }

    @Override
    public void onPause() {
        super.onPause();
        if ("107".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
            TongJiUtils.TongJi(mContext, 107 + "");
            LogYiFu.e("TongJiNew", 107 + "");
        }
    }

//	/**
//	 * 设置编辑框的监听
//	 */
//	private void setEditextLstener() {
//		et_search.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					// mDatasHis.clear();
//					// mDatasHis = recordsDao.getRecordsList();
//					// if(mDatasHis.size()==0){
//					// historical_search.setVisibility(View.GONE);
//					// }else{
//					// historical_search.setVisibility(View.VISIBLE);
//					// }
//					// initHistoryChildViews();
//					tv_zhuanqian.setVisibility(View.VISIBLE);
//					// 此处为得到焦点时的处理内容
//					if (historical_search_rootview.getVisibility() != View.VISIBLE) {
//						TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//								Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
//								Animation.RELATIVE_TO_SELF, 0.0f);
//						mShowAction.setDuration(200);
//						historical_search_rootview.startAnimation(mShowAction);
//						historical_search_rootview.setVisibility(View.VISIBLE);
//					}
//				} else {
//					// // 此处为失去焦点时的处理内容
//					// tvCancle.setVisibility(View.GONE);
//					// View view = ((Activity)
//					// mContext).getWindow().getDecorView();
//					// InputMethodManager inputmanger = (InputMethodManager)
//					// ((Activity) mContext)
//					// .getSystemService(Context.INPUT_METHOD_SERVICE);
//					// if (inputmanger != null) {
//					// inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
//					// 0);// 关闭键盘
//					// }
//					// if (historical_search_rootview.getVisibility() ==
//					// View.VISIBLE) {
//					// TranslateAnimation mHiddenAction = new
//					// TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//					// Animation.RELATIVE_TO_SELF, 0.0f,
//					// Animation.RELATIVE_TO_SELF, 0.0f,
//					// Animation.RELATIVE_TO_SELF, -1.0f);
//					// mHiddenAction.setDuration(100);
//					// historical_search_rootview.startAnimation(mHiddenAction);
//					// historical_search_rootview.setVisibility(View.GONE);
//					// }
//				}
//			}
//		});
//
//		et_search.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//					// 先隐藏键盘
//					((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
//							.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
//									InputMethodManager.HIDE_NOT_ALWAYS);
//
//					if (et_search.getText().toString().trim().equals("")) {
//						ToastUtil.showShortText(mContext, "请输入搜索条件");
//						return false;
//					}
//					if (StringUtils.containsEmoji(et_search.getText().toString().trim())) {
//						ToastUtil.showShortText(mContext, "搜索条件不能含特殊字符");
//						return false;
//					}
//					HashMap<String, String> recordsMap = new HashMap<String, String>();
//					recordsMap.put("class_name", et_search.getText().toString().trim());
//					recordsMap.put("class_id", "");
//					recordsMap.put("user_id", YJApplication.instance.isLoginSucess()
//							? YCache.getCacheUserSafe(mContext).getUser_id() + "" : "-1");// 区分用户
//																							// 未登录时候默认id
//																							// ：-1
//					recordsDao.addRecords(recordsMap);// 添加搜索记录
//					Intent intent = new Intent();
//					intent = new Intent(mContext, WordSearchResultActivity.class);
//					// if (null == id)
//					intent.putExtra("words", et_search.getText().toString().trim());
//					intent.putExtra("notType", true);
//					// else
//					// intent.putExtra("_id", id);
//					startActivity(intent);
//					et_search.getText().clear();
//					return true;
//				}
//				return false;
//			}
//		});
//	}
//
//	/**
//	 * 删除历史记录
//	 */
//	private void deleteHositorySearch() {
//		final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
//		View view = View.inflate(mContext, R.layout.delete_hository_search_dialog, null);
//		view.findViewById(R.id.btn_left).setOnClickListener(new OnClickListener() {
//			// 关闭 取消
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//
//		view.findViewById(R.id.btn_right).setOnClickListener(new OnClickListener() {
//			// 确定删除
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				historical_search.setVisibility(View.GONE);
//				mDatasHis.clear();
//				recordsDao.deleteAllRecords();
//			}
//		});
//
//		// // 创建自定义样式dialog
//		dialog.setContentView(view,
//				new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270), LinearLayout.LayoutParams.MATCH_PARENT));
//		dialog.show();
//	}
//
//	private void setEditextDeleteListener() {
//		et_search.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				String et_search_text = et_search.getText().toString();
////				if (et_search_text.length() > 0) {
////					et_search_xx.setVisibility(View.VISIBLE);
////				} else {
////					et_search_xx.setVisibility(View.GONE);
////				}
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//
//			}
//		});
//	}

    /**
     * 赚钱图标动画效果
     */
    private void setZhuanIconAnim() {

        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String shareAnim = SharedPreferencesUtil.getStringData(mContext, Pref.SHAREANIMZHUNA, "0");
        long shareAnimTime = Long.valueOf(shareAnim);
        boolean isRoate = "0".equals(shareAnim) || !df.format(new Date()).equals(df.format(new Date(shareAnimTime)));
        if (!isRoate) {
            return;
        }
        RotateAnimation ani1 = new RotateAnimation(0f, 35f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        ScaleAnimation ani2 = new ScaleAnimation(1.0f, 0.85f, 1.0f, 0.85f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final AnimationSet set = new AnimationSet(mContext, null);
        ani1.setDuration(270);
        ani1.setRepeatMode(Animation.REVERSE);
//		ani1.setRepeatCount(1);
        ani1.setFillAfter(false);
//		ani1.setStartOffset(1500);
        ani2.setDuration(270);
        ani2.setRepeatMode(Animation.RESTART);
//		ani2.setRepeatCount(Integer.MAX_VALUE);
        ani2.setFillAfter(false);
//		ani2.setStartOffset(1500);

        set.addAnimation(ani1);
        set.addAnimation(ani2);
        set.setStartOffset(600);
//		redShare.setAnimation(set);
        redShare.startAnimation(set);

        final RotateAnimation ani3 = new RotateAnimation(-12f, 10f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        ani3.setDuration(55);
        ani3.setRepeatMode(Animation.REVERSE);
        ani3.setRepeatCount(2);
        ani3.setFillAfter(true);
        final RotateAnimation ani4 = new RotateAnimation(-6f, 6f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        ani4.setDuration(45);
        ani4.setRepeatMode(Animation.REVERSE);
        ani4.setRepeatCount(1);
        ani4.setFillAfter(false);

        ani1.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani3);
                set.setStartOffset(1300);
            }
        });
        ani3.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moneyShare.startAnimation(ani4);
            }
        });
        ani4.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redShare.startAnimation(set);
            }
        });

    }

    /**
     * 显示最上面的数据
     *
     * @date 2017年1月17日上午11:44:55
     */
    private void setTopGridView() {
//        listDataTop = new ArrayList<HashMap<String, String>>();
////        String sql = "select * from supp_label where type = 1 order by _id";
//        String sql = "select * from supp_label where type = 1 order by sort desc";
//        listDataTop = dbHelp.query(sql);
//        TopGridViewAdapter adapter = new TopGridViewAdapter(mContext, listDataTop);
//        mMyGridView.setAdapter(adapter);

    }

    private class TopGridViewAdapter extends BaseAdapter {
        private Context context;
        private List<HashMap<String, String>> listData;
        private LayoutInflater mInflater;

        public TopGridViewAdapter(Context context, List<HashMap<String, String>> listData) {
            this.context = context;
            this.listData = listData;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listData.size() > 12 ? 12 : listData.size();
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
                convertView = mInflater.inflate(R.layout.classfication_top_gridview_list_item, null);
                holder.ivImage = (YuanJiaoImageView) convertView.findViewById(R.id.manufacture_bg_iv);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.manufacture_title);
                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
            }
            if (position < 11) {
                final String name = listData.get(position).get("name");
                String icon = listData.get(position).get("icon");
                if (!TextUtils.isEmpty(icon)) {
                    holder.tvTitle.setVisibility(View.GONE);
                    holder.ivImage.setVisibility(View.VISIBLE);
//					SetImageLoader.initImageLoader(mContext, holder.ivImage, icon, "");

                    holder.ivImage.setBorderRadius(DP2SPUtil.dp2px(context,6));
                    PicassoUtils.initImage2(mContext, icon, holder.ivImage);
//                    PicassoUtils.initYuanJiao(mContext,icon,holder.ivImage,5);

                } else {
                    holder.tvTitle.setText(name);
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.ivImage.setVisibility(View.GONE);
                }

                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(mContext, ManufactureActivity.class);
                        intent.putExtra("supple_data", listData.get(position));
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.activity_from_right,
                                R.anim.activity_search_close);
                    }
                });
            } else if (position == 11) {//第八条数据 显示查看更多
//                holder.tvTitle.setText("MORE");
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.ivImage.setVisibility(View.GONE);
                convertView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, MoreSubjectActivity.class);
                        intent.putExtra("isManufacture", true);
                        context.startActivity(intent);
                        ((Activity) context).overridePendingTransition(R.anim.activity_from_right,
                                R.anim.activity_search_close);
                    }
                });
            }


            return convertView;

        }

        class Holder {
            YuanJiaoImageView ivImage;
            TextView tvTitle;
        }
    }
    private boolean hongbaoAnimStart = false;
    AnimatorSet animatorSet = new AnimatorSet();
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            animatorSet.start();

            handler.postDelayed(this, 2600);
        }
    };


    private void setHongbaoAnim() {

        if (YJApplication.instance.isLoginSucess()) {
            if(YCache.getCacheUser(mContext).getReviewers() == 1){
                iv_hongbao.setVisibility(View.GONE);
                return;
            }
            HashMap<String, String> pairsMap = new HashMap<>();
            YConn.httpPost(mContext, YUrl.QUERY_VIP_INFO2, pairsMap
                    , new HttpListener<VipInfo>() {
                        @Override
                        public void onSuccess(VipInfo vipInfo) {
                            if (vipInfo.getIsVip() > 0) {
                                iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_600);
                            }else{
                                iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
                            }
                            setHongBaoAnim();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }else{
            iv_hongbao.setImageResource(R.drawable.small_redhongbao_nintymoney_90);
            setHongBaoAnim();
        }


    }

    private void setHongBaoAnim() {
        if (hongbaoAnimStart) {
            return;
        }

        ObjectAnimator animatorBigX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1, 1.4f);

        animatorBigX.setDuration(800);

        ObjectAnimator animatorBigY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1, 1.4f);

        animatorBigY.setDuration(800);


        ObjectAnimator animRot = ObjectAnimator.ofFloat(iv_hongbao, "rotation", 0f, -30f, 0f, 30f, 0);
        animRot.setDuration(800);


        ObjectAnimator animatorSmallX = ObjectAnimator.ofFloat(iv_hongbao, "scaleX", 1.4f, 1);
        animatorSmallX.setDuration(800);

        ObjectAnimator animatorSmallY = ObjectAnimator.ofFloat(iv_hongbao, "scaleY", 1.4f, 1);
        animatorSmallY.setDuration(800);

        AnimatorSet.Builder buildBigX = animatorSet.play(animatorBigX);
        buildBigX.with(animatorBigY);//一起放大


        AnimatorSet.Builder buildSmallX = animatorSet.play(animatorSmallX);
        buildSmallX.with(animatorSmallY);//一起缩小


        //放大后旋转
        buildBigX.before(animRot);


        //旋转后缩小
        buildSmallX.after(animRot);

        hongbaoAnimStart = true;
        //循环播放
        handler.postDelayed(runnable, 100);
    }




}

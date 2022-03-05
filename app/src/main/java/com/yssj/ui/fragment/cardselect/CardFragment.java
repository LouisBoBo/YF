package com.yssj.ui.fragment.cardselect;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yssj.YConstance;
import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.ShopCartNewNewActivity;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.MainMenuActivity.CardQuitListener;
import com.yssj.ui.activity.shopdetails.ShopDetailsActivity;
import com.yssj.ui.dialog.CardJingxuanDialog;
import com.yssj.ui.dialog.ChoicenessDialog;
import com.yssj.ui.dialog.SignFinishJXDialog;
import com.yssj.ui.fragment.HomePageFragment;
import com.yssj.ui.fragment.cardselect.CardSlidePanel.CardSwitchListener;
import com.yssj.ui.fragment.circles.SignListAdapter;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.SignCompleteDialogUtil;
import com.yssj.utils.TongJiUtils;
import com.yssj.utils.YCache;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 卡片Fragment
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class CardFragment extends Fragment implements OnClickListener {

    private CardSwitchListener cardSwitchListener;
    private CardQuitListener cardQuitListener;
    private MainMenuActivity activity;
    private static Context mContext;

    private List<CardDataItem> dataList;
    private List<CardDataItem> likeDataList;
    private List<CardDataItem> reDeleteDataList;// 浏览过要请求后台删除的
    private View leftBtn, rightBtn, shareBtn, reBackBtn;
    private CardSlidePanel slidePanel;
    private CardItemView cardItemView1;
    private CardItemView cardItemView2;
    private CardItemView cardItemView3;
    private CardItemView cardItemView4;
    private ImageView ivSelect;
    private ImageView closeIcon;
    private int curIndext;
    private int pageCount;
    private boolean jxfromsigngetsign; // 是否没有调用过签到接口

    public static CardFragment newInstances(String title, Context context) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString("tag", title);
        mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        dataList = new ArrayList<CardDataItem>();
        likeDataList = new ArrayList<CardDataItem>();
        reDeleteDataList = new ArrayList<CardDataItem>();
        activity = (MainMenuActivity) getActivity();
        // 将是否是从签到跳过来的标志改为false --- 下次进来就不是签到了
        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGN", false);

        initView(rootView);
        return rootView;
    }

    private boolean isSelect = false;

    private void sign() {
        // 签到
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) mContext, 0) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                // 是否没有调用签到接口------改为false
                SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN", false);

                String intdex = SharedPreferencesUtil.getStringData(context, YConstance.LIULANJINGXUANTUJIANINDEX, "");
                return ComModel2.getSignIn(mContext, false, false, intdex, SignListAdapter.doClass);

            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    String jiangliValue = SignListAdapter.jiangliValueJX;
                    int jiangliID = SignListAdapter.jiangliIDJX;


                    if (Integer.valueOf(result.get("isNewbie01") + "") == 1) {
                        SignCompleteDialogUtil.firstClickInGoToZP(mContext);
                        SharedPreferencesUtil.saveBooleanData(mContext, "JXFROMSIGNGETSIGN", false);
                        return;
                    }


                    new SignFinishJXDialog(mContext, R.style.DialogQuheijiao, jiangliValue, jiangliID).show();

                } else {
                    // ToastUtil.showLongText(mContext, "未知错误");
                }

            }

        }.execute();
    }

    private void initView(View rootView) {
        leftBtn = rootView.findViewById(R.id.card_left_btn);
        rightBtn = rootView.findViewById(R.id.card_right_btn);
        shareBtn = rootView.findViewById(R.id.card_left_share);
        reBackBtn = rootView.findViewById(R.id.card_right_reback);
        ivSelect = (ImageView) rootView.findViewById(R.id.icon_select);
        ivSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = !isSelect;
                if (isSelect) {
                    ivSelect.setImageResource(R.drawable.icon_jinxuan_select);
                } else {
                    ivSelect.setImageResource(R.drawable.icon_jinxuan_normal);
                }
                SharedPreferencesUtil.saveBooleanData(mContext, Pref.JINGXUAN_SELECT, isSelect);
                TongJiUtils.TongJi(mContext, 3 + "");
                LogYiFu.e("TongJiNew", 3 + "");
            }
        });

        closeIcon = (ImageView) rootView.findViewById(R.id.icon_close);
        closeIcon.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        reBackBtn.setOnClickListener(this);
        if (curIndext > 0) {
            reBackBtn.setBackgroundResource(R.drawable.icon_reback_jingxuan);
            reBackBtn.setClickable(true);
        } else {
            reBackBtn.setBackgroundResource(R.drawable.icon_reback_jingxuan_dis);
            reBackBtn.setClickable(false);
        }
        slidePanel = (CardSlidePanel) rootView.findViewById(R.id.image_slide_panel);
        cardItemView1 = (CardItemView) rootView.findViewById(R.id.top_carditem1);
        cardItemView2 = (CardItemView) rootView.findViewById(R.id.top_carditem2);
        cardItemView3 = (CardItemView) rootView.findViewById(R.id.top_carditem3);
        cardItemView4 = (CardItemView) rootView.findViewById(R.id.top_carditem4);
        cardSwitchListener = new CardSwitchListener() {

            @Override
            public void onShow(int index) {
                // LogYiFu.e("CardFragment", "正在显示- index:" +index +" "+
                // dataList.get(index).getShop_name());
                leftBtn.setScaleX(1.0f);
                leftBtn.setScaleY(1.0f);
                rightBtn.setScaleX(1.0f);
                rightBtn.setScaleY(1.0f);
                CardFragment.this.curIndext = (CardFragment.this.curIndext == index && index != 0) ? index + 1 : index;// 每一组最后一个Item显示完后
                // index不变
                // 为最后一个的index
                // 所以显示完后curIndext=index+1
                if (curIndext > 0 && curIndext <= dataList.size() - 1) {
                    reBackBtn.setBackgroundResource(R.drawable.icon_reback_jingxuan);
                    reBackBtn.setClickable(true);
                } else {
                    reBackBtn.setBackgroundResource(R.drawable.icon_reback_jingxuan_dis);
                    reBackBtn.setClickable(false);
                    if (curIndext > dataList.size() - 1) {
                        deleteData();// 删除后台数据
                        if (pageCount == 1) {// 所有数据浏览完成
                            getShareQua(2);

                            SharedPreferencesUtil.saveBooleanData(mContext, Pref.JINGXUAN_SCAN_FINISH, true);
                            HomePageFragment.iv_xuanfurugou.setVisibility(View.GONE);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            // 当前日期
                            String date = sdf.format(new Date());
                            SharedPreferencesUtil.saveStringData(mContext, Pref.JINGXUAN_SCAN_FINISH_DATE, date);
                        } else {
                            showGroupFinishDialog();
                        }
                    }
                }
            }

            @Override
            public void onCardVanish(int index, int type) {
                // LogYiFu.e("CardFragment", "正在消失-" +
                // dataList.get(index).getShop_name() + " 消失type=" + type);
                // type 0左边 type 1 :右边
                // dataList.remove(index);
                leftBtn.setScaleX(1.0f);
                leftBtn.setScaleY(1.0f);
                rightBtn.setScaleX(1.0f);
                rightBtn.setScaleY(1.0f);
                if (index == dataList.size() - 1) {
                    // 每一组最后的一张消失
                    reBackBtn.setBackgroundResource(R.drawable.icon_reback_jingxuan_dis);
                    reBackBtn.setClickable(false);
                }

                // -----------滑动了--------------------
                jxfromsigngetsign = SharedPreferencesUtil.getBooleanData(mContext, "JXFROMSIGNGETSIGN", false);
                if (jxfromsigngetsign) {
                    sign();

                }

                if (type == 0) {// 不喜欢
                    // 先判断喜欢列表中有没有 有的话删掉
                    for (int i = 0; i < likeDataList.size(); i++) {
                        if (dataList.get(index).getShop_code().equals(likeDataList.get(i).getShop_code())) {
                            likeDataList.remove(i);
                            break;
                        }
                    }
                    // //判断不喜欢列表有没有 没有的话 才添加
                    // boolean isHaving= false;
                    // for (int i = 0; i < disLikeDataList.size(); i++) {
                    // if(dataList.get(index).getShop_code().equals(disLikeDataList.get(i).getShop_code())){
                    // isHaving = true;
                    // }
                    // }
                    // if(!isHaving){
                    // disLikeDataList.add(dataList.get(index));
                    // }
                } else if (type == 1) {
                    if (dataList.size() > index) {
                        addLike(dataList.get(index).getShop_code());
                    }


                    // for (int i = 0; i < disLikeDataList.size(); i++) {
                    // if(dataList.get(index).getShop_code().equals(disLikeDataList.get(i).getShop_code())){
                    // disLikeDataList.remove(i);
                    // break;
                    // }
                    // }

                    boolean isHaving = false;
                    for (int i = 0; i < likeDataList.size(); i++) {
                        if (dataList.get(index).getShop_code().equals(likeDataList.get(i).getShop_code())) {
                            isHaving = true;
                        }
                    }
                    if (!isHaving && dataList.size() > index) {
                        likeDataList.add(dataList.get(index));
                    }
                }
                // 把浏览过 已经选择喜欢或者不喜欢的 都加到准备删除的列表中
                boolean isHaving = false;
                for (int i = 0; i < reDeleteDataList.size(); i++) {
                    if (dataList.get(index).getShop_code().equals(reDeleteDataList.get(i).getShop_code())) {
                        isHaving = true;
                    }
                }
                if (!isHaving && dataList.size() > index) {
                    reDeleteDataList.add(dataList.get(index));
                }

            }

            @Override
            public void onItemClick(View cardView, int index) {
                ImageView imageNumIv1 = cardItemView1.getImageCenterIv();
                ImageView imageNumIv2 = cardItemView2.getImageCenterIv();
                ImageView imageNumIv3 = cardItemView3.getImageCenterIv();
                ImageView imageNumIv4 = cardItemView4.getImageCenterIv();
                imageNumIv1.setVisibility(View.GONE);
                imageNumIv2.setVisibility(View.GONE);
                imageNumIv3.setVisibility(View.GONE);
                imageNumIv4.setVisibility(View.GONE);
                if (dataList.size() > 0) {
                    Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                    intent.putExtra("code", dataList.get(index).getShop_code() + "");
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                }
            }

            @Override
            public void onCenterImgShow(View child, int left, int dx) {
                // TODO Auto-generated method stub
                CardItemView cv = (CardItemView) child;
                ImageView imageNumIv = cv.getImageCenterIv();
                LogYiFu.e("leftBtn", dx + "    dx");
                LogYiFu.e("leftBtn", left + "    left");
                if (left <= -1) {
                    imageNumIv.setVisibility(View.VISIBLE);
                    imageNumIv.setImageResource(R.drawable.icon_jingxuan_dislike_01);
                    // 不喜欢按钮缩放动画效果
                    float l = left < -200 ? -200 : left;
                    leftBtn.setScaleX(1.0f - (l / 1000));
                    leftBtn.setScaleY(1.0f - (l / 1000));
                    rightBtn.setScaleX(1.0f);
                    rightBtn.setScaleY(1.0f);
                } else if (left >= 1) {
                    imageNumIv.setVisibility(View.VISIBLE);
                    imageNumIv.setImageResource(R.drawable.icon_jingxuan_like_01);
                    // 喜欢按钮缩放动画效果
                    float r = left > 200 ? 200 : left;
                    rightBtn.setScaleX(1.0f + (r / 1000));
                    rightBtn.setScaleY(1.0f + (r / 1000));
                    leftBtn.setScaleX(1.0f);
                    leftBtn.setScaleY(1.0f);
                } else {
                    imageNumIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCenterImgHide(View releasedChild, float xvel, float yvel) {
                // TODO Auto-generated method stub
                CardItemView cv = (CardItemView) releasedChild;
                ImageView imageNumIv = cv.getImageCenterIv();
                if (imageNumIv.getVisibility() == View.VISIBLE) {
                    imageNumIv.setVisibility(View.GONE);
                }

            }

            @Override
            public void onBackCenter() {
                leftBtn.setScaleX(1.0f);
                leftBtn.setScaleY(1.0f);
                rightBtn.setScaleX(1.0f);
                rightBtn.setScaleY(1.0f);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);
        prepareDataList();
        // 退出APP时候 删除已经浏览过的数据
        cardQuitListener = new CardQuitListener() {

            @Override
            public void delete() {
                deleteData();
            }
        };
        if (activity != null) {
            activity.setCardQuitListener(cardQuitListener);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        boolean jingxuanTip = SharedPreferencesUtil.getBooleanData(mContext, Pref.JINGXUANTIP, false);
        // long jingxuanTime = Long.valueOf(jingxuan);
        // if ("0".equals(jingxuan) || !df.format(new
        // Date()).equals(df.format(new Date(jingxuanTime)))) {
        if (!jingxuanTip) {
            new CardJingxuanDialog(mContext).show();
            // SharedPreferencesUtil.saveStringData(mContext, Pref.JINGXUAN,
            // System.currentTimeMillis() + "");
            SharedPreferencesUtil.saveBooleanData(mContext, Pref.JINGXUANTIP, true);
        }
        SharedPreferencesUtil.saveStringData(mContext, Pref.JINGXUAN, System.currentTimeMillis() + "");
    }

    private void prepareDataList() {
        new SAsyncTask<String, Void, List<CardDataItem>>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected void onPostExecute(FragmentActivity mContext, List<CardDataItem> result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e && result != null && result.size() > 0) {
                    dataList.clear();
                    // likeDataList.clear();
                    // disLikeDataList.clear();

                    dataList.addAll(result);
                    slidePanel.fillData(dataList, curIndext);
                    pageCount = Integer
                            .parseInt(SharedPreferencesUtil.getStringData(mContext, Pref.JINGXUAN_PAGECOUNT, "1"));
                } else {
                    slidePanel.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected List<CardDataItem> doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.getLikeQua(mContext, 20);

            }

        }.execute();

        // int num = names.length;
        //
        // for (int i = 0; i < num; i++) {
        // CardDataItem dataItem = new CardDataItem();
        // dataItem.shopName = names[i];
        // dataItem.imagePath = names[i];
        // dataItem.shopPrice = (int) (Math.random() * 10)+"";
        // dataItem.manufacturer = names[i];
        // dataList.add(dataItem);
        // }
    }

    /**
     * 删除的推荐
     */
    private void deleteData() {
        if (reDeleteDataList.size() == 0) {
            return;
        }
        StringBuilder shopCodes = new StringBuilder();
        for (int i = 0; i < reDeleteDataList.size(); i++) {
            shopCodes.append(reDeleteDataList.get(i).getShop_code() + ",");
        }
        // LogYiFu.e("CardFragment", new String(shopCodes));
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel2.deleteDisLikeShop(mContext, params[0]);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, ReturnInfo result, Exception e) {
                super.onPostExecute(mContext, result, e);
                if (null == e) {
                    reDeleteDataList.clear();
                }
            }

        }.execute(new String(shopCodes));
    }

    /**
     * 右滑 喜欢的推荐
     */
    private void addLike(final String shop_code) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, 0) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params) throws Exception {

                return ComModel.addLikeShop(mContext, YCache.getCacheToken(mContext), params[0]);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    String str = SharedPreferencesUtil.getStringData(context,
                            "" + YCache.getCacheUser(context).getUser_id(), "");
                    StringBuffer sb = new StringBuffer(str);
                    sb.append(shop_code);
                    SharedPreferencesUtil.saveStringData(context, "" + YCache.getCacheUser(context).getUser_id(),
                            sb.toString());

                }
            }

        }.execute(shop_code);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.card_left_btn:
                if (slidePanel.getVisibility() == View.VISIBLE) {
                    slidePanel.bottomClick(v, 0);
                }
                break;
            case R.id.card_right_btn:
                if (slidePanel.getVisibility() == View.VISIBLE) {
                    slidePanel.bottomClick(v, 1);
                }
                break;
            case R.id.icon_close:
                // 测试用
                // new ChoicenessDialog(getActivity(), R.style.DialogStyle1,
                // 1).show();
                getShareQua(1);
                deleteData();
                break;
            // case R.id.card_left_share://分享
            // new ChoicenessDialog(getActivity(), R.style.DialogStyle1).show();
            // break;
            case R.id.card_right_reback:// 上一页
                slidePanel.fillData(dataList, curIndext - 1);
                break;

            default:
                break;
        }

    }

    /**
     * 一组图片浏览完后的弹框
     */
    private void showGroupFinishDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.jingxuan_group_finish_dialog, null);

        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                closeIcon.performClick();
            }
        });
        view.findViewById(R.id.btn_next_red).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (reDeleteDataList.size() == 0) {
                    dialog.dismiss();
                    curIndext = 0;
                    prepareDataList();
                }
                // slidePanel.fillData(dataList,curIndext);

            }
        });
        view.findViewById(R.id.btn_like_white).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (reDeleteDataList.size() == 0) {
                    dialog.dismiss();
                    Intent intent = new Intent(mContext, ShopCartNewNewActivity.class);
                    intent.putExtra("where", "0");
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                    curIndext = 0;
                    prepareDataList();
                }
            }
        });

        // // 创建自定义样式dialog
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 一组图片浏览完后的弹框
     */
    private void showAllFinishDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.DialogQuheijiao2);
        View view = View.inflate(mContext, R.layout.jingxuan_group_finish_dialog, null);

        TextView contentTv = (TextView) view.findViewById(R.id.scan_finish_content);
        contentTv.setText("我们为你精心挑选的美物已经全部浏览完毕！你喜欢的商品被推荐至购物车——我的喜欢");
        view.findViewById(R.id.icon_close).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                HomePageFragment.hideCardView();
            }
        });
        TextView redTv = (TextView) view.findViewById(R.id.btn_next_red);
        redTv.setText("我的喜欢");
        redTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, ShopCartNewNewActivity.class);
                intent.putExtra("where", "0");
                mContext.startActivity(intent);
                ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
                HomePageFragment.hideCardView();
                // slidePanel.fillData(dataList,curIndext);

            }
        });
        TextView whiteTv = (TextView) view.findViewById(R.id.btn_like_white);
        whiteTv.setText("取消");
        whiteTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                HomePageFragment.hideCardView();
            }
        });

        // // 创建自定义样式dialog
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 获取分享资格 并弹出分享弹框
     */
    private void getShareQua(final int i) {
        new SAsyncTask<Void, Void, String>((FragmentActivity) mContext, 0) {

            @Override
            protected String doInBackground(FragmentActivity mContext, Void... params) throws Exception {

                return ComModel.getShareQua(mContext);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e && result != null && "1".equals(result)) {// 有资格
                    // 查询是否有分享商品如果有才弹出来分享
                    getShareData(i);

                } else {// 无资格
                    if (i == 2) {
                        showAllFinishDialog();
                    } else {
                        // HomePageFragment.cardRootView.setVisibility(View.GONE);
                        HomePageFragment.hideCardView();
                    }
                }
            }

        }.execute();
    }

    protected void getShareData(final int i) {

        new SAsyncTask<Void, Void, ArrayList<HashMap<String, String>>>((FragmentActivity) mContext, 0) {

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ModQingfeng.getJinPintuijianList(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            ;

            @Override
            protected void onPostExecute(FragmentActivity context, ArrayList<HashMap<String, String>> result,
                                         Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {
                    if (result.size() != 0) {
                        new ChoicenessDialog(mContext, R.style.DialogStyle1, i).show();
                    } else {
                        if (i == 2) {
                            showAllFinishDialog();
                        } else {
                            // HomePageFragment.cardRootView.setVisibility(View.GONE);
                            HomePageFragment.hideCardView();
                        }
                    }

                } else {
                    if (i == 2) {
                        showAllFinishDialog();
                    } else {
                        // HomePageFragment.cardRootView.setVisibility(View.GONE);
                        HomePageFragment.hideCardView();
                    }
                }

            }

        }.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_HOME, "102");
            TongJiUtils.TongJi(mContext, 2 + "");
            LogYiFu.e("TongJiNew", 2 + "");
            Long nowTimes = System.currentTimeMillis();
            SharedPreferencesUtil.saveStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, "" + nowTimes);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ("102".equals(SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_HOME, ""))) {
            TongJiUtils.TongJi(mContext, 102 + "");
            LogYiFu.e("TongJiNew", 102 + "");

            Long nowTimesEnd = System.currentTimeMillis();
            Long nowTimesStart = Long.parseLong(
                    SharedPreferencesUtil.getStringData(mContext, Pref.TONGJI_TIMES_JINXUAN, nowTimesEnd + ""));
            Long duration = (nowTimesEnd - nowTimesStart) / 1000;// 以 秒 为单位
            TongJiUtils.TongJiDuration(mContext, 1002 + "", duration + "");
            LogYiFu.e("TongJiNew", duration + "秒  " + 1002);
        }
    }

}

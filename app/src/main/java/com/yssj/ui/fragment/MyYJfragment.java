package com.yssj.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.activity.wxapi.WXEntryActivity;
import com.yssj.custom.view.MyDividerItemDecoration;
import com.yssj.entity.FriendsRewardCount;
import com.yssj.entity.FriendsRewardList;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.GuideActivity;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.ui.activity.infos.YJdetailActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BaseFragment;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAPPShareUtil;
import com.yssj.utils.WXminiAppUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 个人中心---好友奖励
 * Created by qingfeng on 2017/12/13.
 */

public class MyYJfragment extends BaseFragment {

    private static Context mContext;
    private static int page = 1;
    @Bind(R.id.tv_all_money)
    TextView tvAllMoney;
    @Bind(R.id.tv_qingling)
    TextView tvQingling;


    @Bind(R.id.tv_friend_count)
    TextView tvFriendCount;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;

    private TextView tv_fans_count;

    private RecyclerView recyclerView;

    SmartRefreshLayout refreshLayout;
    private QuickAdapter mAdapter;

    private View mHeadView;// 头部
    private ClassicsHeader mClassicsHeader;

    private FriendsRewardCount.DataBean walletData;
    private ImageView ivKefuText, iv_kefu;
    private int hastips;//点提现按钮是否要跳到佣金明细


    public static MyYJfragment newInstances(Context context) {
        MyYJfragment fragment = new MyYJfragment();
        mContext = context;
        return fragment;
    }


    @Override
    public View initView() {
        page = 1;
        mContext = getActivity();
        if (GuideActivity.needFengKong) {
            ToastUtil.showMyToast(mContext, "奖励已进入账户，祝您购物愉快。", 3000);
            getActivity().finish();
        }

        View view = View.inflate(mContext, R.layout.fragment_frlendsjl, null);
        mHeadView = View.inflate(mContext, R.layout.headerview_friends_jiangli, null);
        ButterKnife.bind(this, mHeadView);
        tv_fans_count = mHeadView.findViewById(R.id.tv_fans_count);
        recyclerView = view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        ivKefuText = view.findViewById(R.id.iv_kefu_text);
        iv_kefu = view.findViewById(R.id.iv_kefu);
        mClassicsHeader = (ClassicsHeader) refreshLayout.getRefreshHeader();
        mClassicsHeader.setEnableLastTime(false);

        return view;
    }

    @Override
    public void initData() {
        mAdapter = new QuickAdapter();
        recyclerView.addItemDecoration(new MyDividerItemDecoration(mContext, MyDividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeadView);
        mAdapter.openLoadAnimation();

        initHeaderData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                page = 1;
                initListData(1);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout rl) {
//                page++;
                refreshLayout.finishLoadMore();
                ToastUtil.showShortText2("只显示最近72小时的粉丝明细。");

//                initListData(2);


            }
        });
        initKefu();

    }


    private void initKefu() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivKefuText.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivKefuText.setVisibility(View.GONE);

                    }
                }, 3000);
            }
        }, 2000);

        iv_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WXminiAppUtil.jumpToWXmini(mContext);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        CommonUtils.disableScreenshots(getActivity());
    }

    private void initListData(final int type) {

        HashMap<String, String> parisMap = new HashMap<>();
        parisMap.put("page", page + "");
        parisMap.put("rows", "36");

        YConn.httpPost(mContext, YUrl.FRIEND_JIANGLI_LIST, parisMap, new HttpListener<FriendsRewardList>() {
            @Override
            public void onSuccess(FriendsRewardList result) {


                if (type == 1) {

                    if (result.getData().size() > 0) {
                        mAdapter.replaceData(result.getData());
                        tvNoData.setVisibility(View.GONE);
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }

                    refreshLayout.finishRefresh();


                } else {
                    if (result.getData().size() > 0) {
                        mAdapter.addData(result.getData());
                    } else {
                        ToastUtil.showShortText2("没有更多数据了哦~");
                    }
//                    refreshLayout.finishLoadMoreWithNoMoreData();

                    refreshLayout.finishLoadMore();

                }

            }

            @Override
            public void onError() {
                if (type == 1) {
                    refreshLayout.finishRefresh();

                } else {
                    refreshLayout.finishLoadMore();

                }

            }
        });
    }


    private void initHeaderData() {
        YConn.httpPost(mContext, YUrl.FRIEND_JIANGLI_DAY_AND_COUNT, new HashMap<String, String>(), new HttpListener<FriendsRewardCount>() {
            @Override
            public void onSuccess(FriendsRewardCount result) {
                FriendsRewardCount.DataBean dataBean = result.getData();
                hastips = dataBean.getHastips();
                walletData = result.getData();
                tvAllMoney.setText("¥ " + dataBean.getExt_money() + "");
//                if (dataBean.getExt_time() > 0) {
//                    tvQingling.setText("(非会员" + dataBean.getTime() + "日后收益清0)");
//                } else {
//                    tvQingling.setVisibility(View.GONE);
//                }
//                tvTodayMoney.setText(dataBean.getExt_now() + "");
//                tvYesMoney.setText(dataBean.getExt_yet() + "");
//                tvFriendCount.setText(dataBean.getExt_num() + "");
                tv_fans_count.setText("粉丝数" + dataBean.getExt_num() + "人");

                initListData(1);

            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(mHeadView);
    }

    @Override
    public void onClick(View view) {

    }


    @OnClick({R.id.tv_to_tixian, R.id.ll_fans_count})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_to_tixian:

                CommonUtils.getVip(mContext, new CommonUtils.GetVipListener() {
                    @Override
                    public void callBack(boolean isVip, int maxType) {

                        if (isVip && (maxType == 5 || maxType == 6)) {

                            //如果后台返回跳佣金提现并且没有跳够3次就跳
                            if (hastips == 1) {
                                int yjPageJumpYJMX = Integer.parseInt(SharedPreferencesUtil.getStringData(mContext, "yjPageJumpYJMXcount", "3"));
                                if (yjPageJumpYJMX > 0) {
                                    mContext.startActivity(new Intent(mContext, YJdetailActivity.class));
                                    yjPageJumpYJMX --;
                                    SharedPreferencesUtil.saveStringData(mContext,"yjPageJumpYJMXcount",yjPageJumpYJMX+"");
                                    return;
                                }
                            }



                            Intent intent = new Intent(mContext, MyWalletCommonFragmentActivity.class);
                            intent.putExtra("flag", "withDrawalFragment");
                            intent.putExtra("alliance", "wallet");
                            intent.putExtra("extract", walletData.getExt_money() + "");
                            startActivity(intent);


                        } else {
                            startActivity(new Intent(mContext, MyVipListActivity.class)
                                    .putExtra("guide_vipType", 5)
                            );
                        }
                    }
                });

                break;

            case R.id.ll_fans_count:


                LayoutInflater mInflater = LayoutInflater.from(mContext);
                final Dialog deleteDialog = new Dialog(mContext, R.style.invate_dialog_style);
                View dialogView = mInflater.inflate(R.layout.dialog_yj_detail_wen, null);
                TextView tv1 = dialogView.findViewById(R.id.tv1);
                TextView tv2 = dialogView.findViewById(R.id.tv2);
                TextView tv3 = dialogView.findViewById(R.id.tv3);
                TextView tv4 = dialogView.findViewById(R.id.tv4);
                TextView btn_ok = dialogView.findViewById(R.id.btn_ok);
                btn_ok.setText("我知道了");


                Spanned tv1tr = Html.fromHtml("1、系统首周会随机分配不定数量的无上级<font color='#ff3f8b'><strong>新注册用户成为您的粉丝</strong></font>。");
                tv1.setText(tv1tr);


                Spanned tv2tr = Html.fromHtml("2、您直接分享或朋友转发您分享的链接或二维码，有人点击或扫码后，即成为您的粉丝。<font color='#ff3f8b'><strong>记得多多分享哦</strong></font>。");
                tv2.setText(tv2tr);


                Spanned tv3tr = Html.fromHtml("3、您可以建立粉丝群后申请智能小助理进群。小助理每天在群里自动发送9.9元特价美衣信息。<font color='#ff3f8b'><strong>群友点击即成为您的粉丝</strong></font>。");
                tv3.setText(tv3tr);

                Spanned tv4tr = Html.fromHtml("4、<font color='#ff3f8b'><strong>粉丝终身有效</strong></font>，粉丝预存或消费，您都可得<font color='#ff3f8b'><strong>15%佣金</strong></font>哦！");
                tv4.setText(tv4tr);

                dialogView.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();

                    }
                });
                dialogView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();

                    }
                });


                deleteDialog.setCanceledOnTouchOutside(false);
                deleteDialog.addContentView(dialogView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));

                ToastUtil.showDialog(deleteDialog);

                break;


        }


    }


    public class QuickAdapter extends BaseQuickAdapter<FriendsRewardList.DataBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.myfriends_jiangli_list_item);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, FriendsRewardList.DataBean item) {
            PicassoUtils.initImage(mContext, item.getPic(), (ImageView) viewHolder.getView(R.id.img_userpic));


            if (viewHolder.getLayoutPosition() == 1) {//有header就用1
                viewHolder.getView(R.id.vv_first_item_lin).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.vv_first_item_lin).setVisibility(View.GONE);

            }

            String nickName = item.getNickName() + "";
            if (nickName.length() > 7) {
                nickName = nickName.substring(0, 7) + "...";
            }
            viewHolder.setText(R.id.tv_name, nickName)
                    .setText(R.id.tv_time, item.getTime() + "");

            if (null != item.getMoney()) {
                viewHolder.setText(R.id.tv_price, "+" + item.getMoney() + "元佣金");
            } else {
                viewHolder.setText(R.id.tv_price, "");
            }

            viewHolder.getView(R.id.tv_yaoqing).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    share();
                }
            });

        }
    }

    private void share() {

        String str = YCache.getCacheUser(mContext).getUser_id() + "," + "ThreePage" + "," + "QRcode";

        String wxMiniPathdUO = "/pages/shouye/redHongBao?scene=" + str;

        String shareMIniAPPimgPic = YUrl.imgurl + "small-iconImages/heboImg/freeling_share199yuan.jpg";
        String shareTitle = "199元购物红包免费抢，多平台可用，快来试试人品吧\t\uD83D\uDC49";
        //分享到微信统一分享小程序
        WXminiAPPShareUtil.shareToWXminiAPP(mContext, shareMIniAPPimgPic, shareTitle, wxMiniPathdUO, false);
        WXEntryActivity.setWXminiShareListener(new WXEntryActivity.WXminiAPPshareListener() {
            @Override
            public void wxMiniShareSuccess() {

            }
        });

    }
}

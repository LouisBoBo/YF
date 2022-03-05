package com.yssj.ui.activity.infos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.BaseData;
import com.yssj.entity.NewWalletBean;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewWalletActivity extends BasicActivity {


    @Bind(R.id.tv_yue)
    TextView tvYue;
    @Bind(R.id.daka_jindu)
    TextView dakaJindu;
    private ArrayList<HashMap<String, String>> mVals = new ArrayList<>();
    private TagFlowLayout tf_tixian_list;
    private TextView tvTitle_base;
    private TagAdapter tagAdapter;
    private NewWalletBean newWalletBean;
    private Context mContext;
    private double checkPercentage = -1;//选中的打卡进度
    private double nowUcipv;//当前打卡进度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wallet_activity);
        ButterKnife.bind(this);
        mContext = this;
        tf_tixian_list = findViewById(R.id.tf_tixian_list);
        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("我的钱包");


    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();

    }

    private void getData() {
        checkPercentage = -1;

        YConn.httpPost(mContext, YUrl.QUERY_NEW_WALLET_DATA, new HashMap<String, String>(), new HttpListener<NewWalletBean>() {
            @Override
            public void onSuccess(NewWalletBean result) {

                newWalletBean = result;

                final List<NewWalletBean.DataBean.PlistBean> buttonListData = newWalletBean.getData().getPlist();

                //将第一个设为默认选中
                for (int i = 0; i < buttonListData.size(); i++) {
                    if (i == 0) {
                        buttonListData.get(i).setCheck(1);
                    }
                }

                tvYue.setText("¥ " + newWalletBean.getData().getMoney());
                dakaJindu.setText("当前打卡进度" + newWalletBean.getData().getUcipv() + "%，每完天完成赚钱小任务即");
                nowUcipv = newWalletBean.getData().getUcipv();

                tagAdapter = new TagAdapter<NewWalletBean.DataBean.PlistBean>(buttonListData) {    //ArrayList<HashMap<String, String>>
                    @Override
                    public View getView(FlowLayout parent, int position, NewWalletBean.DataBean.PlistBean plistBean) {
                        View view = LayoutInflater.from(NewWalletActivity.this).inflate(R.layout.new_wallet_list_item, tf_tixian_list, false);

                        TextView tv_money = view.findViewById(R.id.tv_money);
                        TextView tv_yuan = view.findViewById(R.id.tv_yuan);
                        TextView tv_daka_progress = view.findViewById(R.id.tv_daka_progress);
                        TextView tv_suiji = view.findViewById(R.id.tv_suiji);


                        tv_money.setText(plistBean.getMoney() + "");
                        tv_daka_progress.setText("打卡进度" + plistBean.getPercentage() + "%");

                        if (plistBean.getCheck() == 1) {

                            tv_money.setTextColor(Color.parseColor("#ffffff"));
                            tv_yuan.setTextColor(Color.parseColor("#ffffff"));
                            tv_daka_progress.setTextColor(Color.parseColor("#ffffff"));
                            view.setBackground(getResources().getDrawable(R.drawable.newwallet_tixian_list_item_chenk_bg));


                        } else {
                            tv_money.setTextColor(Color.parseColor("#000000"));
                            tv_yuan.setTextColor(Color.parseColor("#000000"));
                            tv_daka_progress.setTextColor(Color.parseColor("#bdc3c9"));
                            view.setBackground(getResources().getDrawable(R.drawable.newwallet_tixian_list_item_no_chenk_bg));


                        }


                        if (position != 0 && position != buttonListData.size() - 1) {
                            tv_suiji.setText("累计");
                            tv_suiji.setVisibility(View.VISIBLE);
                        } else {

                            if (position == buttonListData.size() - 1) {
                                tv_money.setText("剩余金额");
                                tv_yuan.setVisibility(View.GONE);

                                if (plistBean.getCheck() == 1) {
                                    tv_suiji.setVisibility(View.VISIBLE);
                                } else {
                                    tv_suiji.setVisibility(View.INVISIBLE);
                                }

                            } else {
                                tv_yuan.setVisibility(View.VISIBLE);
                                tv_suiji.setVisibility(View.INVISIBLE);

                            }

                        }

                        return view;
                    }

                };

                tf_tixian_list.setAdapter(tagAdapter);
                tf_tixian_list.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        //点击的打卡进度大于当前的进度就提示
                        if (buttonListData.get(position).getPercentage() > nowUcipv) {
                            ToastUtil.showMyToast(mContext, "您的打卡进度还不够。完成每天的赚钱小任务即打卡成功。请先去打卡吧。", 4000);
                            return true;
                        }
                        for (int i = 0; i < buttonListData.size(); i++) {
                            if (position == i) {
                                buttonListData.get(i).setCheck(1);
                            } else {
                                buttonListData.get(i).setCheck(0);
                            }
                        }
                        tagAdapter.notifyDataChanged();
                        return true;
                    }
                });


            }

            @Override
            public void onError() {

            }
        });


    }


    @OnClick({R.id.ll_go_sign, R.id.bt_sub, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_go_sign:
                SharedPreferencesUtil.saveStringData(mContext, "commonactivityfrom", "sign");
                Intent intent = new Intent(mContext, CommonActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_sub://申请提现

                String showNewWalletTXallMoney = "0.0";

                for (int i = 0; i < newWalletBean.getData().getPlist().size(); i++) {
                    if (newWalletBean.getData().getPlist().get(i).getCheck() == 1) {
                        checkPercentage = newWalletBean.getData().getPlist().get(i).getPercentage();
                        showNewWalletTXallMoney = newWalletBean.getData().getPlist().get(i).getMoney();

                        break;
                    }
                }

                if (checkPercentage <= 0) {
                    ToastUtil.showShortText2("请选择您要提现的金额哦~");
                    return;
                }
                //当前选中的进度是否已经提现过
                boolean isTXyet = false;
                for (int i = 0; i < newWalletBean.getData().getUplist().size(); i++) {
                    if (newWalletBean.getData().getUplist().get(i).getPercentage() == checkPercentage) {//找到已经提过的进度
                        isTXyet = true;
                        break;
                    }
                }

                if (isTXyet) {
                    ToastUtil.showShortText2("您已提现过了。");
                    return;
                }

                final String finalShowNewWalletTXallMoney = showNewWalletTXallMoney;
                HashMap<String, String> map = new HashMap<>();
                map.put("percentage", checkPercentage + "");
                YConn.httpPost(mContext, YUrl.LINGQU_NEW_WALLET_DATA, map, new HttpListener<BaseData>() {
                    @Override
                    public void onSuccess(BaseData result) {
                        if (result.getStatus().equals("1")) {


                            Intent intent = new Intent(mContext, SignDrawalLimitActivity.class)
                                    .putExtra("type", 1);
                            intent.putExtra("isFromNewWallet", true);
                            intent.putExtra("showNewWalletTXallMoney", finalShowNewWalletTXallMoney);
                            startActivity(intent);
                            ((FragmentActivity) mContext).overridePendingTransition(
                                    R.anim.slide_left_in, R.anim.slide_match);
                        }
                    }

                    @Override
                    public void onError() {
                    }
                });


                break;
        }
    }
}

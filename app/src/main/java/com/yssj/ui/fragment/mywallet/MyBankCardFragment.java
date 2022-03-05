package com.yssj.ui.fragment.mywallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.MyBankCard;
import com.yssj.model.ComModel;
import com.yssj.model.ComModelL;
import com.yssj.ui.adpter.MyBankCardListAdapter;
import com.yssj.ui.base.BaseFragment;

public class MyBankCardFragment extends BaseFragment implements OnClickListener {

    private TextView tvTitle_base;
    private LinearLayout img_back;

    private RelativeLayout rel_support_bank_card, rel_add_bank_card, rl_listview;
    private ListView lv_card;


    private MyBankCardListAdapter adapter;

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.activity_mywallet_mycard, null);
        view.setBackgroundColor(Color.WHITE);
        tvTitle_base = (TextView) view.findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("我的银行卡");
        img_back = (LinearLayout) view.findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        rel_support_bank_card = (RelativeLayout) view.findViewById(R.id.rel_support_bank_card);
        rel_support_bank_card.setOnClickListener(this);

        rl_listview = (RelativeLayout) view.findViewById(R.id.rl_listview);

        rel_add_bank_card = (RelativeLayout) view.findViewById(R.id.rel_add_bank_card);
        rel_add_bank_card.setOnClickListener(this);

        lv_card = (ListView) view.findViewById(R.id.lv_card);

        return view;
    }

    @Override
    public void initData() {
        new SAsyncTask<Void, Void, List<MyBankCard>>((FragmentActivity) context, R.string.wait) {

            @Override
            protected List<MyBankCard> doInBackground(FragmentActivity context,
                                                      Void... params) throws Exception {
                return ComModel.findMyBankCard(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         List<MyBankCard> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {


//                    if (result != null && !result.isEmpty()) {
//                        rl_listview.setVisibility(View.VISIBLE);
////					rel_support_bank_card.setVisibility(View.GONE);
//
//                        adapter = new MyBankCardListAdapter(context, result);
//                        lv_card.setAdapter(adapter);
//
//
//                    } else {
//                        rl_listview.setVisibility(View.GONE);
////					rel_support_bank_card.setVisibility(View.VISIBLE);
//                    }
                    getTXweixin(result);

                }


            }

        }.execute();
    }

    private void getTXweixin(final List<MyBankCard> bankResult) {

        new SAsyncTask<Void, Void, HashMap<String, String>>((FragmentActivity) context, 0) {
            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {

                return ComModelL.getWxIsBind(context);
            }

            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, HashMap<String, String> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null && "1".equals(result.get("status"))) {
                    if ("1".equals(result.get("data"))) {//1已经绑定 0 未绑定


                        List<MyBankCard> tempList = bankResult;
                        String wxOpenId = result.get("wxOpenId");
                        MyBankCard wxBankCard = new MyBankCard();
                        wxBankCard.setBank_name("微信支付");
                        wxBankCard.setBank_no(wxOpenId);

                        if (tempList != null && !tempList.isEmpty()) {//绑定了银行卡
                            tempList.add(wxBankCard);
                        } else { //未绑定银行卡
                            List<MyBankCard> wxBankList = new ArrayList<>();
                            wxBankList.add(wxBankCard);
                            tempList = wxBankList;

                        }

                        adapter = new MyBankCardListAdapter(context, tempList);
                        lv_card.setAdapter(adapter);

                        rl_listview.setVisibility(View.VISIBLE);


                    } else if ("0".equals(result.get("data"))) { //没有绑定微信提现账户

                        if (bankResult != null && !bankResult.isEmpty()) {
                            rl_listview.setVisibility(View.VISIBLE);

                            adapter = new MyBankCardListAdapter(context, bankResult);
                            lv_card.setAdapter(adapter);
                        } else {
                            rl_listview.setVisibility(View.GONE);
                        }
                    }

                }

            }
        }.execute();


    }

    @Override
    public void onClick(View v) {
        Fragment mFragment;
        switch (v.getId()) {
            case R.id.img_back:
                getActivity().finish();
                break;

            case R.id.rel_support_bank_card:
//			mFragment = new ViewSupportBankActivity();
//			Bundle bundle = new Bundle();
//			bundle.putString("flag", "myBankCardFragment");
//			mFragment.setArguments(bundle);
//			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();


                Intent intent = new Intent(context, ViewSupportBankActivity.class);

                startActivity(intent);
                break;

            case R.id.rel_add_bank_card:
                mFragment = new AddMyBankCardFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("flag", "myBankCardFragment");
                mFragment.setArguments(bundle2);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, mFragment).commit();
                break;

        }
    }


}

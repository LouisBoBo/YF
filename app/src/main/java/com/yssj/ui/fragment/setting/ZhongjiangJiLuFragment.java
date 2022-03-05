package com.yssj.ui.fragment.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.XListViewDuoBao;
import com.yssj.entity.DuoBaoJiLu_user;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsMoneyIndianaActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.YCache;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.yssj.custom.view.XListView;

public class ZhongjiangJiLuFragment extends Fragment {
    private static Context mContext;

    private XListViewDuoBao mList;
    private DateAdapter mAdapter;
    private List<HashMap<String, Object>> pList;
    private int index = 1;

    private Fragment fragment;
    private TextView demo;
    private String choice;
    private String my_choice;

    private String pos;

    public onZeroShopRefreshListener zeroShopRefresh;

    private LinearLayout account_nodata;

    private TextView tv_no_join;

    public interface onZeroShopRefreshListener {
        void onZeroShopRefresh();
    }

    public void setOnZeroRefreshListener(Fragment fragment) {
        this.zeroShopRefresh = (onZeroShopRefreshListener) fragment;

    }

    public static ZhongjiangJiLuFragment newInstances(int position, Context context) {
        ZhongjiangJiLuFragment instance = new ZhongjiangJiLuFragment();
        Bundle args = new Bundle();
        args.putString("position", position + "");
        mContext = context;
        instance.setArguments(args);

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.item_duobao, container, false);

        account_nodata = (LinearLayout) v.findViewById(R.id.account_nodata);
        account_nodata.setVisibility(View.GONE);

        TextView tv_qin = (TextView) v.findViewById(R.id.tv_qin);
        tv_qin.setText("亲，您还未参与抽奖哦~");
        tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
        tv_no_join.setText("暂无优惠券");
        tv_no_join.setVisibility(View.GONE);



        Button btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
        btn_view_allcircle.setVisibility(View.GONE);

        // pos=getArguments().getString("position");

        mList = (XListViewDuoBao) v.findViewById(R.id.dataList);
        mList.setPullLoadEnable(true);

        // initData();

        mList.setXListViewListener(new XListViewDuoBao.IXListViewListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                index++;
                initData(String.valueOf(index));
            }
        });

        pList = new ArrayList<HashMap<String, Object>>();

        mAdapter = new DateAdapter(getActivity());
        mList.setAdapter(mAdapter);

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesUtil.saveStringData(mContext, "where", "1");
    }

    protected void initData(final String index) {

        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(getActivity(), 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
                return ComModel2.yiyuanDuobaoJilu(context, index,"1"); // TODO
            }

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                // System.out.println("******+result="+result);

                if (e != null) {
                    mList.stopLoadMore();
                    return;
                }
                if (result != null) {
                    if (ZhongjiangJiLuFragment.this.index == 1) {
                        pList.clear();
                    }

                    if (result.size() == 0 && !index.equals("1")) {

                    }
                    pList.addAll(result);
                } else {
                    if (ZhongjiangJiLuFragment.this.index == 1) {
                        pList.clear();
                    } else {

                    }
                }
                mAdapter.notifyDataSetChanged();
                mList.stopLoadMore();
                // zeroShopRefresh.onZeroShopRefresh();
            }

            ;
        }.execute();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        // choice = SharedPreferencesUtil.getStringData(mContext, "choice",
        // "3");
        // my_choice = SharedPreferencesUtil.getStringData(mContext,
        // "my_choice", "3");
        // System.out.println("choice="+choice+"*****my_choice="+my_choice);

        index = 1;

        initData(String.valueOf(index));
        initview();
    }

    public void refresh() {
        index = 1;
        initData("1");
    }

    public void setSelecttion() {
        if (mAdapter.getCount() > 0 && mList.getFirstVisiblePosition() != 0) {
            mList.setSelection(0);
        }
    }

    private void initview() {

    }

    private class DateAdapter extends BaseAdapter {

        private Context context;
        // private String issue_code;
        // private String in_code;
        // private String status;
        // private String otime;
        // private String shop_code;
        // private String in_name;
        // private String in_head;
        // private String in_uid;

        public DateAdapter(Context context) {
            super();
            this.context = context;

        }

        @Override
        public int getCount() {
            int count = 0;
            count = pList.size();

            if (count == 0) {
                account_nodata.setVisibility(View.VISIBLE);
            } else {
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
            ViewHolder holder;
            String virtual_num1;
            final String virtual_num;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.duobaojilu, null);
                holder = new ViewHolder();

                holder.tv_number_qi = (TextView) convertView.findViewById(R.id.tv_number_qi); // 多少期
                holder.tv_time_answer = (TextView) convertView.findViewById(R.id.tv_time_answer); // 揭晓时间，开始时间
                // holder.tv_time_date=(TextView)
                // convertView.findViewById(R.id.tv_time_date); //揭晓时间前部分
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time); // 揭晓时间后部分
                holder.img_treasure = (ImageView) convertView.findViewById(R.id.img_treasure);// 夺宝物品
                holder.tv_name_treasure = (TextView) convertView.findViewById(R.id.tv_name_treasure); // 宝物名称
                holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);// 参与夺宝人数
                holder.tv_tuikuan = (TextView) convertView.findViewById(R.id.tv_tuikuan);// 已退款
                holder.tv_ing = (TextView) convertView.findViewById(R.id.tv_ing);// 进行中
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);// 获奖者名称
                holder.tv_winner_number = (TextView) convertView.findViewById(R.id.tv_winner_number); // 中奖者号码

                holder.tv_yjx = (TextView) convertView.findViewById(R.id.tv_yjx);

                holder.img_zhongjiang = (ImageView) convertView.findViewById(R.id.img_zhongjiang);// 中奖图片

                holder.rl_bootom = (RelativeLayout) convertView.findViewById(R.id.rl_bootom);// 获奖者大条目
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, Object> mapObj = pList.get(position);

            final String issue_code = mapObj.get("issue_code") + "";
            holder.tv_number_qi.setText("第" + issue_code + "期"); // 商品期号

            final String shop_code = mapObj.get("shop_code").toString();

            String shop_name = mapObj.get("shop_name") + ""; // 商品名称
            holder.tv_name_treasure.setText(shop_name);

            // mapObj.get(key) //图片等下在写

//			SetImageLoader
//					.initImageLoader(context, holder.img_treasure,
//							mapObj.get("shop_code").toString().substring(1, 4) + "/"
//									+ mapObj.get("shop_code").toString() + "/" + (String) mapObj.get("shop_pic"),
//							"!382");

            PicassoUtils.initImage(
                    context, mapObj.get("shop_code").toString().substring(1, 4) + "/"
                            + mapObj.get("shop_code").toString() + "/" + (String) mapObj.get("shop_pic") + "!382",
                    holder.img_treasure);
            DuoBaoJiLu_user user = (DuoBaoJiLu_user) mapObj.get("user");

            final String in_code = mapObj.get("in_code") + "";
            // System.out.println("********in_code="+in_code);
            holder.tv_winner_number.setText(in_code); // 中奖号码

            final String in_name = mapObj.get("in_name") + "";
            holder.tv_name.setText(in_name); // 获奖者名称

            final String otime = mapObj.get("otime") + ""; // 开奖时间 (揭晓时间前半分)
            String btime = mapObj.get("btime") + ""; // 开始时间
            // holder.tv_time_date.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("otime").toString()),"yyyy.MM.dd"));8

            String etime = mapObj.get("etime") + "";
            holder.tv_time.setText(
                    DateFormatUtils.format(Long.parseLong(mapObj.get("otime").toString()), "yyyy.MM.dd HH:mm:ss"));

            final String in_uid = mapObj.get("in_uid").toString(); // 中奖用户id

            final String in_head = mapObj.get("in_head").toString();

            String status = mapObj.get("status").toString();

            // if (status.equals("") || status.equals(null)) {
            // holder.tv_ing.setVisibility(View.VISIBLE);
            // holder.tv_tuikuan.setVisibility(View.GONE);
            // holder.img_zhongjiang.setVisibility(View.GONE);
            // holder.tv_yjx.setVisibility(View.GONE);
            // holder.rl_bootom.setVisibility(View.GONE);
            // holder.tv_time_answer.setText("开始时间");
            // }

            if (status.equals("0") /* || status.equals("") */) {
                holder.tv_ing.setVisibility(View.VISIBLE);
                holder.tv_tuikuan.setVisibility(View.GONE);
                holder.img_zhongjiang.setVisibility(View.GONE);
                holder.tv_yjx.setVisibility(View.GONE);
                holder.rl_bootom.setVisibility(View.GONE);
                holder.tv_time_answer.setText("开始时间");
                holder.tv_time.setText(
                        DateFormatUtils.format(Long.parseLong(mapObj.get("btime").toString()), "yyyy.MM.dd HH:mm:ss"));
            }
            if (status.equals("2")) {
                holder.tv_tuikuan.setVisibility(View.VISIBLE);
                holder.tv_ing.setVisibility(View.GONE);
                holder.img_zhongjiang.setVisibility(View.GONE);
                holder.tv_yjx.setVisibility(View.GONE);
                holder.rl_bootom.setVisibility(View.GONE);
                holder.tv_time.setText("未满足预定开奖人数");

                // holder.tv_time_date.setVisibility(View.INVISIBLE);
                holder.tv_time_answer.setVisibility(View.INVISIBLE);

            }
            // if (status.equals("3") && in_uid.equals(user.getUid())) {
            // holder.img_zhongjiang.setVisibility(View.VISIBLE);
            // holder.tv_ing.setVisibility(View.GONE);
            // holder.tv_tuikuan.setVisibility(View.GONE);
            // holder.tv_yjx.setVisibility(View.GONE);
            // holder.rl_bootom.setVisibility(View.VISIBLE);
            // holder.tv_time_answer.setText("揭晓时间");
            // }
            // if(status.equals("3") &&
            // !in_uid.equals(user.getUid())){//!in_name.equals(user.getNickname())
            // System.out.println("中奖用户id="+in_uid+" 用户id="+user.getUid());
            // holder.tv_yjx.setVisibility(View.VISIBLE);
            // holder.tv_ing.setVisibility(View.GONE);
            // holder.tv_tuikuan.setVisibility(View.GONE);
            // holder.img_zhongjiang.setVisibility(View.GONE);
            // holder.rl_bootom.setVisibility(View.VISIBLE);
            // holder.tv_time_answer.setText("揭晓时间");
            // }

            // String phone = YCache.getCacheUser(context).getPhone();
            int user_id = YCache.getCacheUser(context).getUser_id();

            if (status.equals("3")) {
                // System.out.println("中奖用户id="+in_uid+" 用户id="+user.getUid());
                if (!in_uid.equals(user_id + "")) { // user里面拿出来的userid不对
                    holder.tv_yjx.setVisibility(View.VISIBLE);
                    holder.tv_ing.setVisibility(View.GONE);
                    holder.tv_tuikuan.setVisibility(View.GONE);
                    holder.img_zhongjiang.setVisibility(View.GONE);
                    holder.rl_bootom.setVisibility(View.VISIBLE);
                    holder.tv_time_answer.setText("揭晓时间");
                } else {
                    holder.img_zhongjiang.setVisibility(View.VISIBLE);
                    holder.tv_ing.setVisibility(View.GONE);
                    holder.tv_tuikuan.setVisibility(View.GONE);
                    holder.tv_yjx.setVisibility(View.GONE);
                    holder.rl_bootom.setVisibility(View.VISIBLE);
                    holder.tv_time_answer.setText("揭晓时间");
                }
            }

            if (status.equals("4")) { //4,等待开奖
                // System.out.println("中奖用户id="+in_uid+" 用户id="+user.getUid());
                if (!in_uid.equals(user_id + "")) { // user里面拿出来的userid不对
                    holder.tv_yjx.setVisibility(View.GONE);


                    holder.tv_ing.setVisibility(View.VISIBLE);


                    holder.tv_tuikuan.setVisibility(View.GONE);
                    holder.img_zhongjiang.setVisibility(View.GONE);


                    holder.rl_bootom.setVisibility(View.GONE);
                    holder.tv_time_answer.setVisibility(View.VISIBLE);
                    holder.tv_time.setVisibility(View.VISIBLE);

                    holder.tv_time_answer.setText("开始时间");
                    holder.tv_time.setText(
                            DateFormatUtils.format(Long.parseLong(mapObj.get("btime").toString()), "yyyy.MM.dd HH:mm:ss"));


                }
            }


            virtual_num1 = mapObj.get("virtual_num").toString();
            String num = mapObj.get("num").toString();

            int num1 = Integer.valueOf(num).intValue();
            int num2 = Integer.valueOf(virtual_num1).intValue();
            int num3 = num1 + num2;

            holder.tv_count.setText(" " + num3); // 参与次数

            virtual_num = String.valueOf(num3);

            // 点击条目跳转
            if (status.equals("2")) {

            } else {
                convertView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (null != ShopDetailsMoneyIndianaActivity.instance) {
                            ShopDetailsMoneyIndianaActivity.instance.finish();
                        }

//                        Intent intent = new Intent(getActivity(), ShopDetailsIndianaActivity.class);
//                        intent.putExtra("shop_code", shop_code);
//                        intent.putExtra("in_code", in_code);
//                        intent.putExtra("otime", otime);
//                        intent.putExtra("in_name", in_name);
//                        intent.putExtra("in_head", in_head);
//                        intent.putExtra("in_uid", in_uid);
//                        intent.putExtra("issue_code", issue_code);
//                        intent.putExtra("virtual_num", virtual_num);
//                        startActivity(intent);
//
//                        ((FragmentActivity)
//                                getActivity()).overridePendingTransition(R.anim.slide_left_in,
//                                R.anim.slide_match);






                        Intent intent = new Intent(context, ShopDetailsMoneyIndianaActivity.class);
                        intent.putExtra("shop_code", shop_code);
                        FragmentActivity activity = (FragmentActivity) context;
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);






                    }
                });
            }

            return convertView;
        }

    }

    private class ViewHolder {
        private TextView tv_number_qi, tv_time_answer, /* tv_time_date, */
                tv_time, tv_name_treasure, tv_count, tv_yjx,
                tv_tuikuan, tv_ing, tv_name, tv_winner_number;
        private ImageView img_treasure, img_zhongjiang;
        private RelativeLayout rl_bootom;
    }

}

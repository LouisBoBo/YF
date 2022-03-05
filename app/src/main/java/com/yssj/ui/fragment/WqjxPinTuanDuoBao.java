package com.yssj.ui.fragment;

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
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.RoundImageButton;
import com.yssj.custom.view.XListViewDuoBao;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.shopdetails.ShopDetailsGroupIndianaActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsIndianaActivity;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.yssj.custom.view.XListView;
//往期揭晓 ---拼团夺宝
public class WqjxPinTuanDuoBao extends Fragment {
    private static Context mContext;

    private XListViewDuoBao mList;
    private DateAdapter mAdapter;
    private List<HashMap<String, Object>> pList;
    private int index = 1;

    private Fragment fragment;
    private ArrayList list;
    private String choice;
    private String my_choice;
    public onSnatchRefreshListener SnatchRefresh;

    private TextView tv_no_join;

    private LinearLayout account_nodata;

    public interface onSnatchRefreshListener {
        void onSnatchRefresh();
    }

    public void setOnSnatchRefreshListener(Fragment fragment) {
        this.SnatchRefresh = (onSnatchRefreshListener) fragment;

    }

    // public static wqjx_duobao newInstances(int position, Context context) {
    // wqjx_duobao instance = new wqjx_duobao();
    // Bundle args = new Bundle();
    // args.putString("position", position+"");
    // mContext = context;
    // instance.setArguments(args);
    //
    // return instance;
    // }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.item_duobao, container, false);
        mContext = getActivity();

        account_nodata = (LinearLayout) v.findViewById(R.id.account_nodata);
        account_nodata.setVisibility(View.GONE);

        TextView tv_qin = (TextView) v.findViewById(R.id.tv_qin);
        tv_qin.setText("亲，暂时还没有往期揭晓记录~");
        tv_no_join = (TextView) v.findViewById(R.id.tv_no_join);
        tv_no_join.setText("暂无优惠券");
        tv_no_join.setVisibility(View.GONE);
        Button btn_view_allcircle = (Button) v.findViewById(R.id.btn_view_allcircle);
        btn_view_allcircle.setVisibility(View.GONE);

        mList = (XListViewDuoBao) v.findViewById(R.id.dataList);
        mList.setPullLoadEnable(true);

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
        // TODO Auto-generated method stub
        super.onResume();
        SharedPreferencesUtil.saveStringData(mContext, "where", "3");
    }

    protected void initData(final String index) {

        new SAsyncTask<String, Void, List<HashMap<String, Object>>>(getActivity(), 0) {
            @Override
            protected List<HashMap<String, Object>> doInBackground(FragmentActivity context, String... params)
                    throws Exception {
//                return ModQingfeng.getPintuanDuobaoWQJX(context, index);
                return ModQingfeng.getPintuanDuobaoWQJX(context, index);

            }

            @Override
            protected boolean isHandleException() {
                // TODO Auto-generated method stub
                return true;
            }

            protected void onPostExecute(FragmentActivity context, List<HashMap<String, Object>> result, Exception e) {
                // System.out.println("*****666+result="+result);

                if (e != null) {
                    mList.stopLoadMore();
                    return;
                }
                if (result != null) {
                    if (WqjxPinTuanDuoBao.this.index == 1) {
                        pList.clear();
                    }

                    if (result.size() == 0 && !index.equals("1")) {

                    }
                    pList.addAll(result);
                    // System.out.println("*****pList="+pList);
                } else {
                    if (WqjxPinTuanDuoBao.this.index == 1) {
                        pList.clear();
                    } else {

                    }
                    // System.out.println("*pList="+pList);
                }
                // System.out.println("////pList="+pList);
                mAdapter.notifyDataSetChanged();
                mList.stopLoadMore();
                // SnatchRefresh.onSnatchRefresh(); //WTF
            }

            ;
        }.execute();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        choice = SharedPreferencesUtil.getStringData(mContext, "choice", "3");
        my_choice = SharedPreferencesUtil.getStringData(mContext, "my_choice", "3");
        // System.out.println("choice="+choice+"*****my_choice="+my_choice);

        index = 1;

        initData(String.valueOf(index));
        initview();
    }

    public void refresh() {
        index = 1;
        initData("1");
    }

    private void initview() {

    }

    private class DateAdapter extends BaseAdapter {

        private Context context;

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
            final ViewHolder holder;

            String virtual_num1;
            final String virtual_num;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.wangqijiexiao_pintuan_duobao, null);
                holder = new ViewHolder();

                holder.tv_duoshaoqi = (TextView) convertView.findViewById(R.id.tv_duoshaoqi);// 第多少期
                holder.tv_years = (TextView) convertView.findViewById(R.id.tv_years); // 揭晓时间中的年月份
                holder.tv_sfm = (TextView) convertView.findViewById(R.id.tv_sfm); // 揭晓时间中的时分秒
                holder.img_head = (RoundImageButton) convertView.findViewById(R.id.img_head); // 头像
                holder.name_of_winner = (TextView) convertView.findViewById(R.id.name_of_winner); // 获奖者名称
                holder.tv_chakan = (TextView) convertView.findViewById(R.id.tv_chakan); // 查看详情(就这几个字)
                holder.see = (ImageView) convertView.findViewById(R.id.see); // 查看详情图标箭头
                holder.tv_join_num = (TextView) convertView.findViewById(R.id.tv_join_num); // 本期参与人数数量
                holder.number_of_winner = (TextView) convertView.findViewById(R.id.number_of_winner); // 幸运号码的号码

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, Object> mapObj = pList.get(position);

            final String issue_code = mapObj.get("issue_code").toString();
            holder.tv_duoshaoqi.setText("第"+issue_code+"期"); // 期号

//            final String otime = mapObj.get("etime").toString();

            try {
                holder.tv_years
                        .setText(DateFormatUtils.format(Long.parseLong(mapObj.get("etime").toString()), "yyyy.MM.dd")); // 揭晓时间年月日
                holder.tv_sfm.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("etime").toString()), "HH:mm:ss")); // 揭晓时间时分秒
            } catch (Exception e) {
                e.printStackTrace();
            }

            final String in_rollUserName = mapObj.get("in_rollUserName").toString();
            holder.name_of_winner.setText(in_rollUserName+"的团"); // 获奖者昵称

            String in_sum = mapObj.get("in_sum").toString();
//            virtual_num1 = mapObj.get("virtual_num").toString();

//            int num1 = Integer.valueOf(num).intValue();
//            int num2 = Integer.valueOf(virtual_num1).intValue();
//            int num3 = num1 + num2;

            holder.tv_join_num.setText(in_sum + ""); // 本期参与人数

//            virtual_num = String.valueOf(num3);
            // System.out.println("??????="+virtual_num);

            final String in_code = mapObj.get("in_code").toString();
            holder.number_of_winner.setText(in_code); // 幸运号码

//			SetImageLoader.initImageLoader(context, holder.img_head, (String) mapObj.get("in_head"), "");

            PicassoUtils.initImage(context, (String) mapObj.get("in_rollHead"), holder.img_head);//头像
            holder.img_head.setFocusable(false);
            final String shop_code = mapObj.get("shop_code").toString(); // 商品编号
            // System.out.println("商品编号对照shop_code="+shop_code);

//            final String in_head = mapObj.get("in_rollHead").toString(); // 用户头像
//
//            final String in_uid = mapObj.get("in_uid").toString();

            // 点击条目跳转
            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != ShopDetailsGroupIndianaActivity.instance) {
                        ShopDetailsGroupIndianaActivity.instance.finish();
                    }
                    Intent intent = new Intent(getActivity(), ShopDetailsGroupIndianaActivity.class);
                    intent.putExtra("shop_code", shop_code);
                    intent.putExtra("old_issue_code", issue_code);//期号

//                    intent.putExtra("in_code", in_code);
//                    intent.putExtra("otime", otime);
//                    intent.putExtra("in_name", in_name);
//                    intent.putExtra("in_head", in_head);
//                    intent.putExtra("in_uid", in_uid);
//                    intent.putExtra("issue_code", issue_code);
//                    intent.putExtra("virtual_num", virtual_num);
//                    intent.putExtra("issue_code", issue_code);

                    // System.out.println("****/////----="+virtual_num);

                    // System.out.println("shop_code="+shop_code+"
                    // in_code="+in_code+" otime="+otime+" in_name="+in_name+"
                    // in_head="+in_head+" in_uid="+in_uid+"
                    // issue_code="+issue_code);
                    FragmentActivity activity = (FragmentActivity) getActivity();
                    activity.startActivity(intent);
                    ((FragmentActivity)
                            mContext).overridePendingTransition(R.anim.slide_left_in,
                            R.anim.slide_match);
                }
            });

            return convertView;
        }

    }

    private class ViewHolder {
        private TextView tv_duoshaoqi, tv_years, tv_sfm, name_of_winner, tv_chakan, tv_join_num, number_of_winner;
        private ImageView /* img_head, */ see;
        private RoundImageButton img_head;
    }

}

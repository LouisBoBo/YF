package com.yssj.ui.activity.vip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.StringUtils;

import java.util.List;

/**
 * Created by test on 2017/11/22.
 */


public class VipCardListAdapter extends RecyclerView.Adapter<VipCardListAdapter.MzViewHolder> {

    private List<VipListBean.ViplistBean> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    public VipCardListAdapter(Context context, List<VipListBean.ViplistBean> urlList) {
        this.urlList = urlList;
    }

    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public MzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vip_banner_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MzViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty())
            return;
        VipListBean.ViplistBean item = urlList.get(position % urlList.size());

        String url = item.getUrl();


        PicassoUtils.initImageNoDefPic(url, holder.imageView);



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(position % urlList.size());
                }

            }
        });

        //处理会员卡名
        String vipCardName;
        if (item.getArrears_price() > 0) {//欠费
            vipCardName = (item.getVip_num() - 1 <= 0) ? item.getVip_name() :
                    "已有" + item.getVip_name() + "X" + (item.getVip_num() - 1);
        } else {
            vipCardName = item.getVip_num() > 0 ? "已有" + item.getVip_name() + "X" + item.getVip_num() :
                    item.getVip_name();
        }
        holder.tv_card_name.setText(vipCardName);


        if (StringUtils.isEmpty(item.getContext())) {
            holder.tv_context.setVisibility(View.GONE);
        } else {
            holder.tv_context.setText(item.getContext());
        }
        if (StringUtils.isEmpty(item.getSubstance())) {
            holder.tv_substance.setVisibility(View.GONE);
        } else {
            holder.tv_substance.setText(item.getSubstance());
        }

//        if (item.getVip_balance() > 0) {
//            holder.tv_userVipMoney.setText("卡费￥" + new DecimalFormat("#0.0").format(item.getVip_balance()));
//        } else {
//            holder.tv_userVipMoney.setText("");
//        }

        if(MyVipListActivity.balance > 0){
            holder.tv_userVipMoney.setText("已预存¥"+MyVipListActivity.balance);
        }

    }

    @Override
    public int getItemCount() {
        if (urlList != null) {
            return urlList.size();
        }
        return 0;


    }


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_card_name;
        TextView tv_context;
        TextView tv_substance;
        TextView tv_userVipMoney;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            tv_card_name = itemView.findViewById(R.id.tv_card_name);
            tv_context = itemView.findViewById(R.id.tv_context);
            tv_substance = itemView.findViewById(R.id.tv_substance);
            tv_userVipMoney = itemView.findViewById(R.id.tv_userVipMoney);

        }
    }

}

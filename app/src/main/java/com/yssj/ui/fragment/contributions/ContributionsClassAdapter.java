package com.yssj.ui.fragment.contributions;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;

import java.util.List;

public class ContributionsClassAdapter extends RecyclerView.Adapter<ContributionsClassAdapter.VH> {
    private List<String> areaModelList;
    private int type;


    public ContributionsClassAdapter.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(int index);
    }
    public void setOnItemClick(ContributionsClassAdapter.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public void setmDatas(List<String> modelList,int tp) {
        areaModelList = modelList;
        type = tp;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContributionsClassAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contribution_class, parent, false);
        return new ContributionsClassAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, int i) {
        vh.type.setText(areaModelList.get(i).toString());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            //item 点击事件
            public void onClick(View v) {
                mOnItemClick.click(vh.getAdapterPosition());
            }
        });
        vh.goimg.setVisibility(type == 1?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        if(areaModelList == null || areaModelList.size() ==0){
            return 0;
        }else
            return areaModelList.size();
    }

    //② 创建ViewHolder 绑定item元素
    public static class VH extends RecyclerView.ViewHolder {
        public TextView type;
        public ImageView goimg;

        public VH(View v) {
            super(v);
            type = v.findViewById(R.id.tv_type);
            goimg = v.findViewById(R.id.img_right_arrow2);
        }
    }

    public void refreshUI(int position) {
        notifyDataSetChanged();
    }

}
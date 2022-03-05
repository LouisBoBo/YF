package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.R.bool;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.custom.view.ResultItemView;
import com.yssj.entity.VipDikouData;
import com.yssj.ui.fragment.YaoQingFrendsActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;

public class StaggeredAdapter extends BaseAdapter {
    private List<HashMap<String, Object>> mInfos;
    // private ImageLoadingListener animateFirstListener = new
    // AnimateFirstDisplayListener();
    private int imageWidth;

    private int picHeight;
    private String id;
    private String isWhere = "";
    private boolean isInviteHot = false;
    private boolean mIsFabu = false;
    private StaggeredAdapter adapter;
    private VipDikouData vipDikouData;

    public StaggeredAdapter(Context context) {
        mInfos = new ArrayList<HashMap<String, Object>>();

        int width = context.getResources().getDisplayMetrics().widthPixels;
        imageWidth = width / 2;
        picHeight = (imageWidth - DP2SPUtil.dp2px(context, 18)) * 900 / 600;
    }

    public StaggeredAdapter(Context context, boolean isInviteHot, boolean mIsFabu) {
        mInfos = new ArrayList<HashMap<String, Object>>();

        int width = context.getResources().getDisplayMetrics().widthPixels;
        imageWidth = width / 2;
        picHeight = (imageWidth - DP2SPUtil.dp2px(context, 18)) * 900 / 600;
        this.isInviteHot = isInviteHot;
        this.mIsFabu = mIsFabu;
        adapter = this;
    }
    // public StaggeredAdapter(Context context,String where) {
    // mInfos = new ArrayList<HashMap<String, Object>>();
    //
    // int width = context.getResources().getDisplayMetrics().widthPixels;
    // imageWidth = width / 2;
    // picHeight=(imageWidth-DP2SPUtil.dp2px(context, 18))*900/600;
    // }

    public StaggeredAdapter(Context context, String id, String isWhere) {
        mInfos = new ArrayList<HashMap<String, Object>>();

        int width = context.getResources().getDisplayMetrics().widthPixels;
        imageWidth = width / 2;
        picHeight = (imageWidth - DP2SPUtil.dp2px(context, 18)) * 900 / 600;
        this.id = id;
        this.isWhere = isWhere;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());
            convertView = layoutInflator.inflate(R.layout.infos_list, null);

            holder.left = (ResultItemView) convertView.findViewById(R.id.left);
            holder.right = (ResultItemView) convertView.findViewById(R.id.right);
            holder.ivRight = (ImageView) holder.right.findViewById(R.id.iv_selector);
            holder.ivLeft = (ImageView) holder.left.findViewById(R.id.iv_selector);
            holder.left.setInviteHot(isInviteHot);
            holder.right.setInviteHot(isInviteHot);
            holder.left.getLayoutParams().height = picHeight;

            holder.right.getLayoutParams().height = picHeight;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final int index = position * 2;

        boolean mIsVip = false;

        if (YJApplication.instance.isLoginSucess()) {
            mIsVip = CommonUtils.isVip(vipDikouData.getIsVip(), vipDikouData.getMaxType());
        }

        if (mIsVip) {
            holder.left.initViewVip(mInfos.get(index), id, isWhere, mIsFabu, vipDikouData);

        } else {
            holder.left.initView(mInfos.get(index), id, isWhere, mIsFabu);

        }
        if (isInviteHot) {
            holder.ivLeft.setVisibility(View.VISIBLE);
            if ("0".equals(mInfos.get(index).get("isSeletorFlag"))) {
                holder.ivLeft.setImageResource(R.drawable.wodexihao_fengge_icon_weixuanzhong);
            } else {
                holder.ivLeft.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
            }
            holder.ivLeft.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if ("0".equals(mInfos.get(index).get("isSeletorFlag"))) {
                        holder.ivLeft.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
                        mInfos.get(index).put("isSeletorFlag", "1");
                        YaoQingFrendsActivity.shopCode = "" + mInfos.get(index).get("shop_code");
                        YaoQingFrendsActivity.shopPIC = "" + mInfos.get(index).get("def_pic");
                    } else {
                        return;
                    }
                    for (int i = 0; i < mInfos.size(); i++) {
                        if (i != index) {
                            mInfos.get(i).put("isSeletorFlag", "0");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }
        if (mInfos.size() > index + 1) {
            holder.right.setVisibility(View.VISIBLE);


            if (mIsVip) {
                holder.right.initViewVip(mInfos.get(index + 1), id, isWhere, mIsFabu, vipDikouData);

            } else {
                holder.right.initView(mInfos.get(index + 1), id, isWhere, mIsFabu);


            }

            if (isInviteHot) {

                holder.ivRight.setVisibility(View.VISIBLE);
                if ("0".equals(mInfos.get(index + 1).get("isSeletorFlag"))) {
                    holder.ivRight.setImageResource(R.drawable.wodexihao_fengge_icon_weixuanzhong);
                } else {
                    holder.ivRight.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
                }
                holder.ivRight.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if ("0".equals(mInfos.get(index + 1).get("isSeletorFlag"))) {
                            holder.ivRight.setImageResource(R.drawable.wodexihao_fengge_icon_xuanzhong);
                            mInfos.get(index + 1).put("isSeletorFlag", "1");
                            YaoQingFrendsActivity.shopCode = "" + mInfos.get(index + 1).get("shop_code");
                            YaoQingFrendsActivity.shopPIC = "" + mInfos.get(index + 1).get("def_pic");
                        } else {
                            return;
                        }
                        for (int i = 0; i < mInfos.size(); i++) {
                            if (i != index + 1) {
                                mInfos.get(i).put("isSeletorFlag", "0");
                            }
                        }
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                });
            }


        } else {
            holder.right.setVisibility(View.INVISIBLE);
            if (isInviteHot) {
                holder.right.setVisibility(View.INVISIBLE);
            }
        }


        if (mIsFabu) {//如果是发帖就隐藏勾勾
            holder.ivLeft.setVisibility(View.GONE);
            holder.ivRight.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {

        ResultItemView left;
        ResultItemView right;
        ImageView ivLeft;
        ImageView ivRight;
    }

    @Override
    public int getCount() {
        return mInfos.size() % 2 == 0 ? mInfos.size() / 2 : mInfos.size() / 2 + 1;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void addItemLast(List<HashMap<String, Object>> datas) {
        if (datas != null) {
            mInfos.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public void addItemTop(List<HashMap<String, Object>> datas) {
        mInfos.clear();
        if (datas != null) {
            mInfos.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public void clearData() {
        mInfos.clear();
        this.notifyDataSetChanged();
    }

    // private static class AnimateFirstDisplayListener extends
    // SimpleImageLoadingListener {
    //
    // static final List<String> displayedImages = Collections
    // .synchronizedList(new LinkedList<String>());
    //
    // @Override
    // public void onLoadingComplete(String imageUri, View view,
    // Bitmap loadedImage) {
    // if (loadedImage != null) {
    // ImageView imageView = (ImageView) view;
    // imageView.setImageBitmap(loadedImage);
    // boolean firstDisplay = !displayedImages.contains(imageUri);
    // if (firstDisplay) {
    // FadeInBitmapDisplayer.animate(imageView, 500);
    // displayedImages.add(imageUri);
    // }
    // }
    // }
    // }
    //
    public void setData(List<HashMap<String, Object>> result, VipDikouData vipDikouData) {
        this.vipDikouData = vipDikouData;
        clearData();
        if (result != null) {
            mInfos.addAll(result);
        }
        this.notifyDataSetChanged();
    }

}

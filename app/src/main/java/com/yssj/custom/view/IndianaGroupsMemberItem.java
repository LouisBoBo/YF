package com.yssj.custom.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.DP2SPUtil;

import java.util.HashMap;
import java.util.List;

/**
 *我的第几团 显示参与团员 和 团满或未满状态
 */
public class IndianaGroupsMemberItem extends LinearLayout {

    private LinearLayout mGroupV;
    private LinearLayout mGroup;

    private Context mContext;
    private IndianaGroupsMemberView memberView;
//    private DecimalFormat pFormate;
    private LayoutInflater mInflater;
    private TextView tvNum,tvLuckNumber;
    private int width;

    public IndianaGroupsMemberItem(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public IndianaGroupsMemberItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        width = mContext.getResources().getDisplayMetrics().widthPixels;
        mInflater = LayoutInflater.from(mContext);
        mInflater.inflate(R.layout.indiana_groups_member_item, this, true);
        mGroup = (LinearLayout) findViewById(R.id.indiana_groups_member_container);
        mGroupV = (LinearLayout) findViewById(R.id.indiana_groups_member_container_v);
        mGroupV.setVisibility(View.GONE);
        mGroup.setVisibility(View.VISIBLE);
        tvNum = (TextView) findViewById(R.id.groups_num);
        tvLuckNumber= (TextView) findViewById(R.id.groups_luck_number);
    }

    /**
     *
     * @param num 第几团
     * @param u_code 幸运号码
     * @param issue_code ==0 时表示没满足人数
     */
    public void setTopData(String num,String u_code,String issue_code){
        String numState = "0".equals(issue_code)
                ?"第"+num+"团，未满"
                :"第"+num+"团，满员";
        SpannableString textSpanState = new SpannableString(numState);
        textSpanState.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3F8B")), 1, num.length()+1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvNum.setText(textSpanState);
        if(!TextUtils.isEmpty(u_code)&&!"0".equals(u_code)){
            tvLuckNumber.setVisibility(View.VISIBLE);
            tvLuckNumber.setText("（幸运号码："+u_code+"）");
        }else{
            tvLuckNumber.setVisibility(View.GONE);
        }

    }

    /**
     * 参与用户列表
     * @param list
     * @param groupCount 多少人成团
     */
    public void setData(List<HashMap<String, String>> list,int groupCount,String shop_name,String issue_code) {
        int max = 5;//一行最多显示5个参团用户
//        pFormate = new DecimalFormat("#0.0");

        int count = ("0".equals(issue_code)||TextUtils.isEmpty(issue_code))?groupCount:list.size();//每一行数据人数

        int dp = DP2SPUtil.dp2px(mContext, 30);
        if(count==3){
            dp = (width-DP2SPUtil.dp2px(mContext, 64)*2-DP2SPUtil.dp2px(mContext, 60)*count)/(count-1);
        }else if(count==4){
            dp = (width-DP2SPUtil.dp2px(mContext, 64)*4)/5;
        }else if(count>=5){
            dp = (width-DP2SPUtil.dp2px(mContext, 64)*5)/6;
        }
        if(dp<=0){
            dp = 0;
        }
        mGroup.removeAllViews();
        mGroupV.removeAllViews();
        if(count<=max){//只显示一行
            mGroupV.setVisibility(View.GONE);//垂直布局的LinearLayout（只显示一行时候不需要）
            mGroup.setVisibility(View.VISIBLE);//水平布局的LinearLayout
            for (int i = 0; i < count; i++) {
                memberView = new IndianaGroupsMemberView(mContext);
                LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    lp.leftMargin = DP2SPUtil.dp2px(mContext, 2);//团长icon图标超出参团用户4dp 整体向右移动2dp 居中
                    memberView.setIconViewVisible();
                }else{
                    lp.leftMargin = dp;
                    memberView.setIconViewInvisible();
                }
                //TODO　填充数据
                if(i>=list.size()){
                    if("0".equals(issue_code)||TextUtils.isEmpty(issue_code)){
                        // groupCount 大于 list.size  但是团成员未满 填充谁要文案
                        memberView.setEmptyView(shop_name);
                    }else{
                        break;//groupCount 大于 list.size 但是团员已满
                    }

                }else{
                    memberView.setHeaderView(list.get(i).get("head"),list.get(i).get("nickname"));
                }

                memberView.setLayoutParams(lp);
                mGroup.addView(memberView);
            }
        }else{
            mGroupV.setVisibility(View.VISIBLE);//垂直布局的LinearLayout
            mGroup.setVisibility(View.GONE);//水平布局的LinearLayout（多行时候不需要）动态创建再add到mGroupV中
            int MaxH = count%max==0?count/max:count/max+1;//最大行数
            for (int h = 0; h <MaxH ; h++) {
                int maxI;

                LinearLayout mGroupH = new LinearLayout(mContext);
                mGroupH.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lpH = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lpH.leftMargin=DP2SPUtil.dp2px(mContext, 2);//团长icon图标超出参团用户4dp 整体向右移动2dp 居中
                lpH.topMargin = DP2SPUtil.dp2px(mContext, 10);///每行间隔10dp

                if(count%max!=0&&MaxH-1==h){//最后一行并且最后一行不足5个
                    lpH.gravity = Gravity.LEFT;
                    maxI =max*h+count%max;
                }else{
                    lpH.gravity = Gravity.CENTER_HORIZONTAL;
                    maxI = max*(h+1);
                }

                for (int i = max*h; i < maxI; i++) {
                    memberView = new IndianaGroupsMemberView(mContext);
                    LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    if (i%max == 0) {//每一行的左边第一个
                        lp.leftMargin = 0;
                    } else{
                        lp.leftMargin = dp;
                    }

                    if(i==0){
                        memberView.setIconViewVisible();
                    }else{
                        memberView.setIconViewInvisible();
                    }

                    //TODO　填充数据
                    if(i>=list.size()){
                        if("0".equals(issue_code)||TextUtils.isEmpty(issue_code)){
                            // groupCount 大于 list.size  但是团成员未满 填充谁要文案
                            memberView.setEmptyView(shop_name);
                        }else{
                            break;//groupCount 大于 list.size 但是团员已满
                        }
                    }else{
                        memberView.setHeaderView(list.get(i).get("head"),list.get(i).get("nickname"));
                    }

                    memberView.setLayoutParams(lp);
                    mGroupH.addView(memberView);
                }
                mGroupH.setLayoutParams(lpH);
                mGroupV.addView(mGroupH);

            }

        }

    }


}

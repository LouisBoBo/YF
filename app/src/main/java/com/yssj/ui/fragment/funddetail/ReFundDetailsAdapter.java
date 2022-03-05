package com.yssj.ui.fragment.funddetail;

import java.io.ObjectInputStream.GetField;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.entity.FundDetail;
import com.yssj.ui.activity.infos.MyWalletCommonFragmentActivity;
import com.yssj.utils.DateUtil;
import com.yssj.utils.YCache;

public class ReFundDetailsAdapter extends BaseAdapter {//返现的列表
    private List<FundDetail> listData;
    private Context context;
    private int type;

    public ReFundDetailsAdapter(Context context, List<FundDetail> listData, int type) {
        this.context = context;
        this.listData = listData;
        this.type = type;
    }

    public List<FundDetail> getData() {
        return listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fund_details_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.amount = (TextView) convertView.findViewById(R.id.amount);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.opration = (TextView) convertView.findViewById(R.id.opration);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FundDetail detail = listData.get(position);


        if ("1".equals(detail.getType())) {
            holder.opration.setText("支付");
            holder.name.setText("订单号" + detail.getOrder_code());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("2".equals(detail.getType())) {
            holder.opration.setText("转账");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (YCache.getCacheUser(context).getUser_id() == detail.getUser_id()) {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            } else {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            }
        } else if ("3".equals(detail.getType())) {
            holder.opration.setText("提现");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("4".equals(detail.getType())) {
            holder.opration.setText("充值");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("5".equals(detail.getType())) {
            holder.opration.setText("提现额度");
//            holder.name.setText("订单号" + detail.getOrder_code());
            holder.name.setText("邀请好友奖励");
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("6".equals(detail.getType())) {
            holder.opration.setText("返现");
            holder.name.setText("订单号" + detail.getOrder_code());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("7".equals(detail.getType())) {
            holder.opration.setText("返现");
            holder.name.setText("订单号" + detail.getOrder_code());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }
        } else if ("8".equals(detail.getType())) {
            holder.opration.setText("退款成功");
            holder.name.setText("订单号" + detail.getOrder_code());
//            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
//                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
//            } else {
//                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
//            }

            holder.amount.setText("" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
        } else if ("9".equals(detail.getType())) {
            holder.opration.setText("转账");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (YCache.getCacheUser(context).getUser_id() == detail.getUser_id()) {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            } else {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            }
        } else if ("10".equals(detail.getType())) {
            holder.opration.setText("转账");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (YCache.getCacheUser(context).getUser_id() == detail.getUser_id()) {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            } else {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            }
        } else if ("11".equals(detail.getType())) {
            holder.opration.setText("转账");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
            if (YCache.getCacheUser(context).getUser_id() == detail.getUser_id()) {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            } else {
                if (String.valueOf(detail.getMoney()).contains("-")
                        || String.valueOf(detail.getMoney()).contains("-")) {
                    holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                } else {
                    holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
                }
            }
        } else if ("12".equals(detail.getType())) {
            holder.opration.setText("供应商提现");
            holder.name.setText(detail.getName() + "**" + detail.getPay_user());
        } else if ("16".equals(detail.getType())) {
            holder.opration.setText("发红包");
            holder.name.setText("订单号" + detail.getOrder_code());
            holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
        } else if ("17".equals(detail.getType())) {
            holder.opration.setText("抢红包");
            holder.name.setText("订单号" + detail.getOrder_code());
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("19".equals(detail.getType())) {
            holder.opration.setText("余额");
            holder.name.setText("任务余额奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("20".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("提现失败退款");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("30".equals(detail.getType())) { // 浏览奖励
            holder.opration.setText("分享");
            holder.name.setText("分享额外奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("31".equals(detail.getType())) { // 粉丝奖励
            holder.opration.setText("分享");
            holder.name.setText("分享额外奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("32".equals(detail.getType())) { // 签到额外奖励(翻倍)
            holder.opration.setText("签到");
            holder.name.setText("签到额外奖励(翻倍)");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("33".equals(detail.getType())) { // 免付返现
            holder.opration.setText("返现");
            holder.name.setText("免付返现");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("34".equals(detail.getType())) { // 粉丝奖励
            holder.opration.setText("赠送");
            holder.name.setText("新用户注册赠送");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("35".equals(detail.getType())) {
            holder.opration.setText("赠送");
            holder.name.setText(detail.getName());
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("36".equals(detail.getType())) { // 集赞奖励
            holder.opration.setText("赠送");
//            holder.name.setText(detail.getName());
            holder.name.setText("集赞奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("37".equals(detail.getType())) { // 体验抽奖
            holder.opration.setText("赠送");
            holder.name.setText(detail.getName());
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("38".equals(detail.getType())) {
            holder.opration.setText("返现");
//            holder.name.setText("0元购返现");
//            holder.name.setText(detail.getName());
            holder.name.setText("订单号" + detail.getOrder_code());
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("39".equals(detail.getType())) {
            holder.opration.setText("余额");
//            holder.name.setText("余额抽额度");
            holder.name.setText("余额抽奖");
            holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("40".equals(detail.getType())) {
            holder.opration.setText("H5签到");
            holder.name.setText("" + detail.getOrder_code());
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("41".equals(detail.getType())) {

            holder.name.setText("好友任务奖励");
            holder.opration.setText("余额");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));

        } else if ("42".equals(detail.getType())) {
            holder.opration.setText("抽奖");
            holder.name.setText("账户余额");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("43".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("余额抽奖");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("44".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("疯抢返还");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("45".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("提现余额抵扣返还");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("46".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("提现余额抵扣");
            holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("47".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("任务提现奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("48".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("疯抢中奖");
            holder.amount.setText("-" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else if ("49".equals(detail.getType())) {
            holder.opration.setText("提现额度");
            holder.name.setText("好友提现奖励");
            holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()));
        } else {
            // 签到现金券
            holder.opration.setText("已到账");
            holder.name.setText("订单号" + detail.getOrder_code());
            if (String.valueOf(detail.getMoney()).contains("-") || String.valueOf(detail.getMoney()).contains("-")) {
                holder.amount.setText(new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            } else {
                holder.amount.setText("+" + new DecimalFormat("#0.00").format(detail.getMoney()) + "");
            }

        }

        if (type == 0) {//交易的不要负号
            String amount = holder.amount.getText().toString();
            if (amount.startsWith("-")) {
                holder.amount.setText(amount.substring(1, amount.length()));
            }
        }

        if (type == 2) {//售后的不要加号
            String amount = holder.amount.getText().toString();
            if (amount.startsWith("+")) {
                holder.amount.setText(amount.substring(1, amount.length()));
            }
        }


        holder.time.setText(DateUtil.FormatMillisecond(detail.getAdd_time()));


        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyWalletCommonFragmentActivity.class);
                intent.putExtra("item", (Serializable) detail);
                intent.putExtra("flag", "reFundDetailsFragment");
                context.startActivity(intent);
            }
        });


        return convertView;
    }


    class ViewHolder {
        TextView name, amount, time, opration;
    }


}

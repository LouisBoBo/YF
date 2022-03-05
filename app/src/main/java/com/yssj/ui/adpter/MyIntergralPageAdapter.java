package com.yssj.ui.adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.base.BaseMainAdapter;
import com.yssj.ui.fragment.IntergralDetailListFragment;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MyIntergralPageAdapter extends BaseMainAdapter {
    private int indext;
    private boolean isDongjie;

    public MyIntergralPageAdapter(Context context, int indext, boolean isDongjie) {
        super(context);
        this.indext = indext;
        this.isDongjie = isDongjie;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(context, R.layout.intergral_detail_item, null);

            holder = new ViewHolder();
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            // holder.ll_intergral_detail = (LinearLayout)
            // convertView.findViewById(R.id.ll_intergral_detail);
            // holder.rl_intergral_detail = (RelativeLayout)
            // convertView.findViewById(R.id.rl_intergral_detail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (indext == 1) {
            holder.tv_num.setTextColor(Color.parseColor("#FB3F3F"));
        }

        final HashMap<String, Object> mapObj = result.get(position);

        if (isDongjie) { //提现冻结额度明细

            int type = Integer.parseInt(mapObj.get("type").toString());


            if (type == 3) {
                holder.tv_type.setText("冻结衣豆抽奖");
            } else {
                holder.tv_type.setText("订单" + mapObj.get("r_code").toString() + "冻结");

            }


            String money = new DecimalFormat("0.0#").format(Double.parseDouble(mapObj.get("num").toString()));
            holder.tv_num.setText(money + "元");
            holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()),
                    "yyyy-MM-dd  HH:mm:ss  "));

        } else {

            if (IntergralDetailListFragment.isTiXianMingXi) {

                // 提现额度明细
                int type = Integer.parseInt(mapObj.get("type").toString());
                /**
                 * type:分类 1抽红包增加 2 抽奖退款 3粉丝购物 4官方赠送 5余额提现 6官方减少
                 */

                String money = new DecimalFormat("0.0#").format(Double.parseDouble(mapObj.get("num").toString()));

                switch (type) {
                    case 1:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("抽红包增加");
                        break;
                    case 2:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("抽奖退款");
                        break;
                    case 3:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("粉丝购物获得奖励");
                        break;
                    case 4:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("官方赠送");
                        break;
                    case 5:
                        holder.tv_num.setText(money);
                        holder.tv_type.setText("余额提现");
                        break;
                    case 6:
                        holder.tv_num.setText(money);
                        holder.tv_type.setText("官方扣除");
                        break;
                    case 7:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("签到");
                        break;
                    case 8:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("邀请好友");
                        break;
                    case 9:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("提现审核失败返还");
                        break;
                    case 10:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("新用户注册赠送");
                        break;
                    case 11:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("疯狂新衣节");
                        break;
                    case 12:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("购买任务");
                        break;
                    case 13:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("集赞");
                        break;
                    case 14:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("集赞奖励");
                        break;
                    case 15:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("抽奖");
                        break;
                    case 16:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("分享返现");
                        break;
                    case 17:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("余额抽奖");
                        break;
                    case 18:
                        holder.tv_num.setText("+" + money);
                        holder.tv_type.setText("提成奖励");
                        break;

                    default:
                        break;
                }
                holder.tv_num.append("元");
                holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_date").toString()),
                        "yyyy-MM-dd  HH:mm:ss"));

            } else {

                // 积分明细
                // 1签到2购物3做任务4分享5别人点击其分享的链接,6退回积分0其他
                if ("1".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("签到赚取积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("2".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("" + mapObj.get("remark").toString());
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText(mapObj.get("num").toString());
                } else if ("3".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("任务完成奖励积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("4".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("分享奖励积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("5".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("别人点击其分享的链接奖励积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss  "));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("6".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("退回积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("7".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("官方奖励/补贴积分");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                } else if ("0".equals(mapObj.get("type").toString())) {
                    holder.tv_type.setText("其他");
                    holder.tv_add_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("add_time").toString()),
                            "yyyy-MM-dd  HH:mm:ss"));
                    holder.tv_num.setText("+" + mapObj.get("num").toString());
                }

            }

        }

        return convertView;
    }

    class ViewHolder {

        TextView tv_add_time, tv_type, tv_num;
        // LinearLayout ll_intergral_detail;
        // RelativeLayout rl_intergral_detail;
    }
}

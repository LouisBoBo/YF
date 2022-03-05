package com.yssj.ui.adpter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.MyBankCard;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.utils.ToastUtil;

public class MyBankCardListAdapter extends BaseAdapter {
    private Context context;
    private List<MyBankCard> list = new ArrayList<MyBankCard>();
    protected AlertDialog dialog;

    private int bankIcons[] = {R.drawable.zg_bank_icon, R.drawable.ny_bank_icon, R.drawable.gs_bank_icon, R.drawable.js_bank_icon, R.drawable.jt_bank_icon, R.drawable.yz_bank_icon,
            R.drawable.zs_bank_icon, R.drawable.zx_bank_icon, R.drawable.pf_bank_icon, R.drawable.gd_bank_icon};


    public MyBankCardListAdapter(Context context, List<MyBankCard> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.mywallet_bank_list_item, null);
            holder.iv_bank_icon = (ImageView) convertView.findViewById(R.id.iv_bank_icon);
            holder.tv_bank_name = (TextView) convertView.findViewById(R.id.tv_bank_name);
            holder.tv_bank_num = (TextView) convertView.findViewById(R.id.tv_bank_num);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String bank_name = list.get(position).getBank_name();

        if ("微信支付".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(R.drawable.icon_wechat);

        } else if ("中国银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[0]);
        } else if ("农业银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[1]);
        } else if ("工商银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[2]);
        } else if ("建设银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[3]);
        } else if ("交通银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[4]);
        } else if ("邮政银行".equals(bank_name) || "邮储银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[5]);
        } else if ("招商银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[6]);
        } else if ("中信银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[7]);
        } else if ("浦发银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[8]);
        } else if ("光大银行".equals(bank_name)) {
            holder.iv_bank_icon.setImageResource(bankIcons[9]);
        } else {
//			为了避免出现复用混乱
            holder.iv_bank_icon.setImageResource(bankIcons[9]);
        }

        holder.tv_bank_name.setText(list.get(position).getBank_name());
        holder.tv_bank_num.setText("**" + list.get(position).getBank_no());


        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"微信支付".equals(bank_name)){
                    customDialog(list.get(position).getId().toString(), position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView iv_bank_icon;
        TextView tv_bank_name;
        TextView tv_bank_num;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    private void customDialog(final String id, final int position) {
        AlertDialog.Builder builder = new Builder(context);
        // 自定义一个布局文件
        View view = View.inflate(context, R.layout.payback_esc_apply_dialog,
                null);
        TextView tv_des = (TextView) view.findViewById(R.id.tv_des);

//		tv_des.setText("  确认要删除该银行卡吗？");
        tv_des.setText("  确认要删除该银行卡吗");
        Button ok = (Button) view.findViewById(R.id.ok);
        ok.setBackgroundResource(R.drawable.payback_esc_apply_esc);
        Button cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 把这个对话框取消掉
                dialog.dismiss();

            }
        });

        ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteBankCard(id, position);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }


    private void deleteBankCard(final String id, final int position) {
        new SAsyncTask<Void, Void, ReturnInfo>((FragmentActivity) context, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModel.delMybankCard(context, id);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null && "1".equals(result.getStatus())) {
                        ToastUtil.showShortText(context, result.getMessage());
                        list.remove(position);
                        notifyDataSetChanged();
                    } else {
                        ToastUtil.showShortText(context, "糟糕，出错了~~~");
                    }
                }
            }

        }.execute();

    }

}

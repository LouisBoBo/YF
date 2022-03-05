package com.yssj.ui.adpter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DateFormatUtils;

import com.yssj.YConstance.Pref;
import com.yssj.activity.R;
//import com.yssj.activity.R.integer;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.base.BaseMainAdapter;
//import com.yssj.utils.GetJinBiJinQuanUtils;
import com.yssj.utils.SharedPreferencesUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyCouponsAllPagerAdapter extends BaseMainAdapter {
    private java.text.DecimalFormat pFormate;
    private long recLen;//剩余时间
    private Timer timer;
    private String timeText;
    private SpannableString ssTimeText;

    public MyCouponsAllPagerAdapter(Context context) {
        super(context);
        pFormate = new DecimalFormat("0");
//		getSystemTime();
    }

    /**
     * 获取系统时间
     */
    private void getSystemTime(final TextView tv) {
        new SAsyncTask<Void, Void, HashMap<String, Object>>((FragmentActivity) context, R.string.wait) {

            @Override
            protected HashMap<String, Object> doInBackground(FragmentActivity context, Void... params)
                    throws Exception {
                return ComModel2.getSystemTime(context);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context, HashMap<String, Object> result, Exception e) {
                super.onPostExecute(context, result, e);
                long nowTime = (Long) result.get("now");
                String end_date = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_END_DATE, "0");
                long endDate = Long.parseLong(end_date);
                recLen = endDate - nowTime;
                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        ((Activity) context).runOnUiThread(new Runnable() { // UI thread
                            @Override
                            public void run() {
                                recLen -= 1000;
                                String hours;
                                String minutes;
                                String seconds;
                                long minute = recLen / 60000;
                                long second = (recLen % 60000) / 1000;

                                long hour = minute / 60;
                                minute = minute % 60;

                                if (hour < 10) {
                                    hours = "0" + hour;
                                } else {
                                    hours = "" + hour;
                                }
                                if (minute < 10) {
                                    minutes = "0" + minute;
                                } else {
                                    minutes = "" + minute;
                                }
                                if (second < 10) {
                                    seconds = "0" + second;
                                } else {
                                    seconds = "" + second;
                                }
                                timeText = "距离金币失效还剩:" + hours + "时" + minutes + "分" + seconds + "秒";
                                ssTimeText = new SpannableString(timeText);
                                ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), timeText.length() - 3, timeText.length() - 1,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), timeText.length() - 6, timeText.length() - 4,
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), 9, 9 + (hours + "").length(),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                tv.setText(ssTimeText);
                                // mTvTime.setText("" + recLen);
                                if (recLen <= 0) {
//										GetJinBiJinQuanUtils.getJinQuan(context);
                                    timer.cancel();
//										ssTimeText = new SpannableString("距离金币失效还剩:00时00分00秒");
                                    timeText = "距离金币失效还剩:00时00分00秒";
                                    ssTimeText = new SpannableString(timeText);
                                    ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), 9, 11,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), 12, 14,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    ssTimeText.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB745")), 15, 17,
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    tv.setText(ssTimeText);
                                }
                            }

                        });
                    }
                }, 0, 1000);
            }
        }.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final HashMap<String, Object> mapObj = result.get(position);
        String is_open = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_OPEN, "0");
        String is_use = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_IS_USE, "");
        String c_id = SharedPreferencesUtil.getStringData(context, Pref.JINQUAN_C_ID, "");//升级金券的优惠券
//		if (null == convertView||convertView.findViewById(R.id.tv_begin_time)==View.GONE) {
        if ("1".equals(is_open) && ((Integer) mapObj.get("id") + "").equals(c_id) && "0".equals(is_use)) {
            convertView = View.inflate(context, R.layout.mycoupons_all_list_gold_item, null);
        } else {
            convertView = View.inflate(context, R.layout.mycoupons_all_list_item, null);
        }


        holder = new ViewHolder();
        holder.tv_cprice = (TextView) convertView.findViewById(R.id.tv_cprice);
        holder.tv_end_date = (TextView) convertView.findViewById(R.id.tv_end_date);

        holder.tv_begin_time = (TextView) convertView.findViewById(R.id.tv_begin_time);
        // 满多少钱可用		holder.tv_ccond = (TextView) convertView.findViewById(R.id.tv_ccond);
        holder.ll_all_bg = (RelativeLayout) convertView.findViewById(R.id.ll_all_bg);
//黄色块背景 现在不用了			holder.rl_part_bg = (LinearLayout) convertView.findViewById(R.id.rl_part_bg);
//满多少减 现在不用			holder.tv_enough  = (TextView) convertView.findViewById(R.id.tv_enough);
//			holder.tv_shop_opnion = (TextView) convertView.findViewById(R.id.tv_shop_opnion);
        holder.tv_manshiyong = (TextView) convertView.findViewById(R.id.tv_manshiyong);
        holder.tv_to_use = (TextView) convertView.findViewById(R.id.tv_to_use);
        holder.tv_time_use = (TextView) convertView.findViewById(R.id.tv_time_use);
        convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}

		
/* 33		if("2".equals(mapObj.get("is_use").toString()) || (Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis())){ // 已使用和已失效的
            holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_none);
	//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_userless_white);
	//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.enough_useless_color));
			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.white_white));
			
		}else if("1".equals(mapObj.get("is_use").toString()) && (Long.parseLong(mapObj.get("c_last_time").toString()) > System.currentTimeMillis())){	// 有效的
			String string = mapObj.get("is_use").toString();
			System.out.println("****2222"+string);
			holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_new);
//			holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_white);
	//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_yellow);
	//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.shop_opinion_color));
			holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
			holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.pink_color));
		}
		*/
        if ((mapObj != null || !mapObj.isEmpty()) && "1".equals(is_open) && ((Integer) mapObj.get("id") + "").equals(c_id) && "0".equals(is_use)) {//有效的金券
            getSystemTime(holder.tv_time_use);
            holder.tv_cprice.setText(pFormate.format(mapObj.get("c_price")) + "");
//			holder.tv_end_date.setText("-"+ DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy.MM.dd"));
//			holder.tv_begin_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("c_add_time").toString()),"yyyy.MM.dd"));
            holder.tv_end_date.setVisibility(View.GONE);
            holder.tv_begin_time.setVisibility(View.GONE);
            String sss = pFormate.format(mapObj.get("c_price")) + ".01";
            holder.tv_manshiyong.setText("(满" + sss + "元可使用)");
//			recLen = endDate - System.currentTimeMillis();
            //sdff
//			private SpannableString ssTextName;
//			ssTimeText = new SpannableString(str);
//			ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 0, 5,
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			ssTextName.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3f8b")), 11, textName.length() - 5,
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if ("2".equals(mapObj.get("is_use").toString()) || (Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis())) {
                holder.ll_all_bg.setVisibility(View.GONE);
                holder.tv_end_date.setVisibility(View.GONE);
                holder.tv_cprice.setVisibility(View.GONE);
                holder.tv_begin_time.setVisibility(View.GONE);
            }


        } else {


            if (mapObj != null || !mapObj.isEmpty()) {
//			holder.tv_cprice.setText(pFormate.format(mapObj.get("c_price"))+"");
                holder.tv_cprice.setText(
                        new java.text.DecimalFormat("#0.00").format(Float.parseFloat(mapObj.get("c_price").toString()))
                );


//			holder.tv_end_date.setText("有效期至/ " + DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy-MM-dd"));
                holder.tv_end_date.setText("-" + DateFormatUtils.format(Long.parseLong(mapObj.get("c_last_time").toString()), "yyyy.MM.dd"));
                holder.tv_begin_time.setText(DateFormatUtils.format(Long.parseLong(mapObj.get("c_add_time").toString()), "yyyy.MM.dd"));
//			System.out.println(mapObj.get("c_add_time").toString()+"****///");
                String sss = mapObj.get("c_cond").toString();
                String s = new java.text.DecimalFormat("#0.00").format(Float.parseFloat(sss));

                holder.tv_manshiyong.setText("(满" + s + "元可使用)");

                //满多少可用		holder.tv_ccond.setText("【订单满" + mapObj.get("c_cond") + "元可用】");
            }
            if ("2".equals(mapObj.get("is_use").toString()) || (Long.parseLong(mapObj.get("c_last_time").toString()) < System.currentTimeMillis())) {
			/*	holder.ll_all_bg.setBackgroundResource(R.drawable.youhuiquan_new);
//				holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_white);
		//.		holder.rl_part_bg.setBackgroundResource(R.drawable.coupont_all_yellow);
		//.		holder.tv_enough.setTextColor(context.getResources().getColor(R.color.shop_opinion_color));
				holder.tv_shop_opnion.setTextColor(context.getResources().getColor(R.color.white));
				holder.tv_end_date.setTextColor(context.getResources().getColor(R.color.pink_color));*/

                holder.ll_all_bg.setVisibility(View.GONE);
//				holder.tv_shop_opnion.setVisibility(View.GONE);
                holder.tv_end_date.setVisibility(View.GONE);
                holder.tv_cprice.setVisibility(View.GONE);
                holder.tv_begin_time.setVisibility(View.GONE);
            }
        }
        //点击使用
        holder.tv_to_use.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainMenuActivity.class);
                intent.putExtra("toYf", "toYf");
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    private void jishu(long recLen, TextView tv_time_use) {

    }

    class ViewHolder {

        TextView tv_end_date, tv_cprice, tv_ccond, tv_enough,/*tv_shop_opnion,*/
                tv_begin_time, tv_manshiyong, tv_to_use, tv_time_use;
        RelativeLayout ll_all_bg,
                rl_part_bg;
    }
}

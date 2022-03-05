package com.yssj.utils;

import android.content.Context;

import com.yssj.YConstance;
import com.yssj.YJApplication;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.GuideActivity;

import java.util.HashMap;

import static com.yssj.YJApplication.isLogined;

/**
 * Created by qingfeng on 2017/10/14.
 */

public class ModUtil {

    public static void hasMond(final Context context,final  boolean needRedPackage){


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    boolean isCrazyMod = ModQingfeng.getCrazyMod(context);
                    // 保存是否有疯狂星期一的任务
                    SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.ISMADMONDAY, isCrazyMod);

                    boolean haslingyuan = ModQingfeng.getLingyuangou(context);
                    // 保存是否有疯狂星期一的任务
                    SharedPreferencesUtil.saveBooleanData(context, YConstance.Pref.ISHASLINGYUANGOU, haslingyuan);


                    if(needRedPackage){
                        //如果当天下过单，引导弹窗没有弹出 -就重新开始2分钟的引导
                        if (!isCrazyMod && isLogined) {
                            //当天是否有未支付订单
                            boolean hasNoPayOrder = ModQingfeng.getNotFUoder(context);
                            if (hasNoPayOrder) {//有未支付订单
                                HashMap<String, String> map = ComModel2.getYiDouHalve(context);
                                if (null == map || map.size() == 0 || "0".equals(map.get("end_date"))) {//说明没有参与过红包的任务-----用户走完流程，支持成功依旧会返回map
                                    YJApplication.startFukuanYndao();
                                }
                            } else {//没有未支付订单---无需任何操作----不考虑任何情况，只要没有未付款订单就不用引导红包


                            }

                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}

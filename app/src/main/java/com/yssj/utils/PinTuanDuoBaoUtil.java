package com.yssj.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.entity.ReturnInfo;
import com.yssj.model.ComModelL;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.shopdetails.ShopDetailsGroupIndianaActivity;

import java.util.HashMap;

/**
 * Created by qingfeng on 2017/9/1.
 */

public class PinTuanDuoBaoUtil {


    public static void getDuobaoH5(final Context context) {


        new SAsyncTask<String, Void, HashMap<String, String>>((FragmentActivity) context, R.string.wait) {

            @Override
            protected HashMap<String, String> doInBackground(FragmentActivity mContext, String... params)
                    throws Exception {

                return ModQingfeng.getDuoBaoh5(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity mContext, HashMap<String, String> map, Exception e) {
                if (e == null) {


                    String status = "-1";

                    try {
                        status = map.get("status");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                    if (!"-1".equals(status)) {//被引导过
                        if ("0".equals(status)) {
                            //去参团
                            canTaun(context, map.get("shop_code"), map.get("sup_user_id"));
                        }
                    }


                }
            }


            @Override
            protected boolean isHandleException() {
                return true;
            }

        }.execute();
    }

    private static void canTaun(final Context mContext, final String shop_code, final String sup_user_id) {

        new SAsyncTask<Void, Void, ReturnInfo>(((FragmentActivity) mContext)) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity context,
                                                Void... params) throws Exception {
                return ComModelL.getRollTrea(context, shop_code, "2", sup_user_id);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(final FragmentActivity context,
                                         ReturnInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (e == null && result != null) {

                    if ("1".equals(result.getStatus())) {
                        //更新参团状态
                        updateTuanStatus(mContext, shop_code);
                    }

                }
            }

        }.execute();


    }

    private static void updateTuanStatus(final Context mContext, final String shop_code) {
        new SAsyncTask<String, Void, ReturnInfo>((FragmentActivity) mContext, R.string.wait) {

            @Override
            protected ReturnInfo doInBackground(FragmentActivity mContext, String... params)
                    throws Exception {

                return ModQingfeng.updatePINtuanDUObao(mContext);

            }

            @Override
            protected void onPostExecute(FragmentActivity context, ReturnInfo result, Exception e) {
                if (e == null) {


                    if ("1".equals(result.getStatus())) {
                        //更新拼团状态后跳转至拼团商品详情
                        if (null != ShopDetailsGroupIndianaActivity.instance) {
                            ShopDetailsGroupIndianaActivity.instance.finish();
                        }
                        Intent intent = new Intent(mContext, ShopDetailsGroupIndianaActivity.class);
                        intent.putExtra("shop_code", shop_code);
                        intent.putExtra("isCanTuan", true);
                        mContext.startActivity(intent);
                        ((FragmentActivity)
                                mContext).overridePendingTransition(R.anim.slide_left_in,
                                R.anim.slide_match);
                    }


                }
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

        }.execute();

    }


}

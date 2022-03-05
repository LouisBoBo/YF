package com.yssj.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.wxpay.WxPayUtil;

import java.util.List;

/**
 * Created by qingfeng on 2018/8/27.
 */

public class WXcheckUtil {


    private static IWXAPI api; // 相应的包，请集成SDK后自行引入

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled(Context context) {


        api = WXAPIFactory.createWXAPI(context, WxPayUtil.APP_ID);
        api.registerApp(WxPayUtil.APP_ID);

        if(api.isWXAppInstalled()) {
            return true;
        } else {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


}

package com.yssj.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yssj.YConstance;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.model.ComModelL;
import com.yssj.wxpay.Util;
import com.yssj.wxpay.WxPayUtil;

import java.util.HashMap;

import static com.yssj.ui.base.BasicActivity.shareWaitDialog;

/**
 * Created by qingfeng on 2017/12/15.
 * <p>
 * 分享到微信要程序专用
 * WXEntryActivity里有分享成功的回调
 */


public class WXminiAPPShareUtil {


    public static IWXAPI wXapi;//微信分享api


    public static void shareShopToWXminiAPP(final Context mContext, String shop_name, final String shop_group_price, String imgUrl, final String wxMiniPath, final boolean isShareSign) {


        //初始化微信API
        wXapi = WXAPIFactory.createWXAPI(mContext, WxPayUtil.APP_ID);
        wXapi.registerApp(WxPayUtil.APP_ID);


        String imageUrl;
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("http://")) {
            imageUrl = imgUrl;
        } else if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("https://")) {
            imageUrl = imgUrl;
        } else {
            imageUrl = YUrl.imgurl + imgUrl;
        }

        LogYiFu.e("miniAPP_imageUrl:", imageUrl);
        LogYiFu.e("wxMiniPath:", wxMiniPath);


        final WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = YUrl.YSS_URL_ANDROID_H5 + "view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
//

        miniProgram.userName = YUrl.WX_MINIAPP_ORIGINAL_ID;//正式

//        miniProgram.userName = "gh_01f3abb24f0b"; //测试
//        miniProgram.userName = "gh_05d342ef9932";//正式
        miniProgram.path = wxMiniPath;
        final WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = "点击购买👆【" + shop_name + "】今日特价" + shop_group_price + "元！";
        msg.description = "衣蝠分享小程序";


        if (isShareSign) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                String shareMIniAPPimgPic = YUrl.imgurl + m.get("icon");
                                msg.title = m.get("title") + "";
                                miniProgram.path = "/pages/shouye/redHongBao?shouYePage=ThreePage&isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);


                                Picasso.get()
                                        .load(shareMIniAPPimgPic)
//                .resize(500, 400)
//                .centerCrop()
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图


                                                if (bmp != null) {
                                                    msg.setThumbImage(bmp);
                                                } else {
                                                    Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
                                                            R.drawable.ic_launcher);
                                                    msg.setThumbImage(temp);
                                                }


                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable drawable) {

                                                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                                                Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                                                msg.thumbData = Util.bmpToByteArray(bitmap, false);
                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable drawable) {

                                            }


                                        });

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        } else {

            if (imageUrl.contains("oneBuySharePic")) {

                msg.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.all_sub));


                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                wXapi.sendReq(req);
                shareWaitDialog.dismiss();


                return;

            }

            Picasso.get()
                    .load(imageUrl)
//                .resize(500, 400)
//                .centerCrop()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图

//
//                            if (bmp != null) {
//                                msg.setThumbImage(bmp);
//                            } else {
//                                Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
//                                        R.drawable.indiana_gift);
                            Bitmap bp = drawableToBitamp(mContext.getResources().getDrawable(R.drawable.share_anvas_price_bg));


                            Bitmap shareBitMap = new DrawBpOnBpUtils(mContext, bmp, bp).onDrawBitmap(120, 94, 0, 130);

                            shareBitMap = drawTextToBitmap(shareBitMap, shop_group_price);


                            msg.setThumbImage(shareBitMap);


                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);
                            shareWaitDialog.dismiss();


                        }


                        @Override
                        public void onBitmapFailed(Exception e, Drawable drawable) {

                            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                            Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                            msg.thumbData = Util.bmpToByteArray(bitmap, false);
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);


                            shareWaitDialog.dismiss();


                        }

                        @Override
                        public void onPrepareLoad(Drawable drawable) {

                        }


                    });
        }


    }


    public static void sharePTtoWXminiAPP(final Context mContext, String shareText, final String shop_group_price, String imgUrl, final String wxMiniPath, final boolean isShareSign) {


        //初始化微信API
        wXapi = WXAPIFactory.createWXAPI(mContext, WxPayUtil.APP_ID);
        wXapi.registerApp(WxPayUtil.APP_ID);


        String imageUrl;
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("http://")) {
            imageUrl = imgUrl;
        } else if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("https://")) {
            imageUrl = imgUrl;
        } else {
            imageUrl = YUrl.imgurl + imgUrl;
        }

        LogYiFu.e("miniAPP_imageUrl:", imageUrl);
        LogYiFu.e("wxMiniPath:", wxMiniPath);


        final WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = YUrl.YSS_URL_ANDROID_H5 + "view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();


        miniProgram.userName = YUrl.WX_MINIAPP_ORIGINAL_ID;


//        miniProgram.userName = "gh_01f3abb24f0b"; //测试
//        miniProgram.userName = "gh_05d342ef9932";//正式
        miniProgram.path = wxMiniPath;
        final WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = shareText;
        msg.description = "衣蝠分享小程序";


        if (isShareSign) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                String shareMIniAPPimgPic = YUrl.imgurl + m.get("icon");
                                msg.title = m.get("title") + "";
                                miniProgram.path = "/pages/shouye/redHongBao?shouYePage=ThreePage&isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);


                                Picasso.get()
                                        .load(shareMIniAPPimgPic)
//                .resize(500, 400)
//                .centerCrop()
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图


                                                if (bmp != null) {
                                                    msg.setThumbImage(bmp);
                                                } else {
                                                    Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
                                                            R.drawable.ic_launcher);
                                                    msg.setThumbImage(temp);
                                                }


                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable drawable) {

                                                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                                                Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                                                msg.thumbData = Util.bmpToByteArray(bitmap, false);
                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable drawable) {

                                            }


                                        });

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        } else {

            if (imageUrl.contains("oneBuySharePic")) {

                msg.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.all_sub));


                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                wXapi.sendReq(req);
                shareWaitDialog.dismiss();


                return;

            }

            Picasso.get()
                    .load(imageUrl)
//                .resize(500, 400)
//                .centerCrop()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图

//
//                            if (bmp != null) {
//                                msg.setThumbImage(bmp);
//                            } else {
//                                Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
//                                        R.drawable.indiana_gift);
                            Bitmap bp = drawableToBitamp(mContext.getResources().getDrawable(R.drawable.share_anvas_price_bg));


                            Bitmap shareBitMap = new DrawBpOnBpUtils(mContext, bmp, bp).onDrawBitmap(120, 94, 0, 130);

                            shareBitMap = drawTextToBitmapPT(shareBitMap, shop_group_price);


                            msg.setThumbImage(shareBitMap);


                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);
                            shareWaitDialog.dismiss();


                        }


                        @Override
                        public void onBitmapFailed(Exception e, Drawable drawable) {

                            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                            Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                            msg.thumbData = Util.bmpToByteArray(bitmap, false);
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);


                            shareWaitDialog.dismiss();


                        }

                        @Override
                        public void onPrepareLoad(Drawable drawable) {

                        }


                    });
        }


    }


    public static Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }


    /**
     * @param mContext
     * @param imgUrl     ---分享图片又拍云路径
     * @param shareTitle ----分享的title
     * @param wxMiniPath ------小程序界面路径
     */
    public static void shareToWXminiAPP(final Context mContext, String imgUrl, final String shareTitle, final String wxMiniPath, final boolean isShareSign) {


        //初始化微信API
        wXapi = WXAPIFactory.createWXAPI(mContext, WxPayUtil.APP_ID);
        wXapi.registerApp(WxPayUtil.APP_ID);


        String imageUrl;
        if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("http://")) {
            imageUrl = imgUrl;
        } else if (!TextUtils.isEmpty(imgUrl) && imgUrl.contains("https://")) {
            imageUrl = imgUrl;
        } else {
            imageUrl = YUrl.imgurl + imgUrl;
        }

        LogYiFu.e("miniAPP_imageUrl:", imageUrl);
        LogYiFu.e("wxMiniPath:", wxMiniPath);


        final WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl = YUrl.YSS_URL_ANDROID_H5 + "view/activity/mission.html?realm=" + YCache.getCacheUser(mContext).getUser_id();
        miniProgram.userName = YUrl.WX_MINIAPP_ORIGINAL_ID;


//        miniProgram.userName = "gh_01f3abb24f0b"; //测试
//        miniProgram.userName = "gh_05d342ef9932";//正式
        miniProgram.path = wxMiniPath;
        final WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = shareTitle;
        msg.description = "衣蝠分享小程序";


        if (isShareSign) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, Object> map = ComModelL.getContentText(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        final HashMap<String, Object> m = (HashMap<String, Object>) map.get(YConstance.KeyJT.KEY_JSONTEXT_NEW_HONGBAO_TEXT);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                String shareMIniAPPimgPic = YUrl.imgurl + m.get("icon");
                                msg.title = m.get("title") + "";
                                miniProgram.path = "/pages/shouye/redHongBao?shouYePage=ThreePage&isShareFlag=true&goto=sign&user_id=" + YCache.getCacheUser(mContext);


                                Picasso.get()
                                        .load(shareMIniAPPimgPic)
//                .resize(500, 400)
//                .centerCrop()
                                        .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图


                                                if (bmp != null) {
                                                    msg.setThumbImage(bmp);
                                                } else {
                                                    Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
                                                            R.drawable.ic_launcher);
                                                    msg.setThumbImage(temp);
                                                }


                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable drawable) {

                                                Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                                                Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                                                msg.thumbData = Util.bmpToByteArray(bitmap, false);
                                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                                                req.message = msg;
                                                req.scene = SendMessageToWX.Req.WXSceneSession;
                                                wXapi.sendReq(req);
                                                shareWaitDialog.dismiss();


                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable drawable) {

                                            }


                                        });

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        } else {

            if (imageUrl.contains("oneBuySharePic")) {

                msg.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.all_sub));


                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                wXapi.sendReq(req);
                shareWaitDialog.dismiss();


                return;

            }

            Picasso.get()
                    .load(imageUrl)
//                .resize(500, 400)
//                .centerCrop()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bmp, Picasso.LoadedFrom from) {


//
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
//                        LogYiFu.e("miniAPP_thumbData:",Util.bmpToByteArray(bitmap, false) +"");
//                        msg.thumbData = Util.bmpToByteArray(bitmap, false); //设置缩略图


                            if (bmp != null) {
                                msg.setThumbImage(bmp);
                            } else {
                                Bitmap temp = BitmapFactory.decodeResource(mContext.getResources(),
                                        R.drawable.ic_launcher);
                                msg.setThumbImage(temp);
                            }


                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);
                            shareWaitDialog.dismiss();


                        }


                        @Override
                        public void onBitmapFailed(Exception e, Drawable drawable) {

                            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                            Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 170, 136, true);
                            msg.thumbData = Util.bmpToByteArray(bitmap, false);
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("YFSHAREWXMINIAPP");
                            req.message = msg;
                            req.scene = SendMessageToWX.Req.WXSceneSession;
                            wXapi.sendReq(req);
                            shareWaitDialog.dismiss();


                        }

                        @Override
                        public void onPrepareLoad(Drawable drawable) {

                        }


                    });
        }


    }


    public static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    private static Bitmap drawTextToBitmap(Bitmap bg, String price) {
        int width = bg.getWidth();
        int hight = bg.getHeight();
        //建立一个空的Bitmap
        Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
        // 初始化画布绘制的图像到icon上
        Canvas canvas = new Canvas(icon);
        // 建立画笔
        Paint photoPaint = new Paint();
        // 获取更清晰的图像采样，防抖动
        photoPaint.setDither(true);
        // 过滤一下，抗剧齿
        photoPaint.setFilterBitmap(true);

        Rect src = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标


        canvas.drawBitmap(bg, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint


        TextPaint textPaint1 = myTextPaint(16);
        canvas.drawText("今日特价", 18, 165, textPaint1);


        TextPaint textPaint2 = myTextPaint(15);
        canvas.drawText("¥", 7, 210, textPaint2);


        TextPaint textPaint3 = myTextPaint(43);
        canvas.drawText(price, 22, 210, textPaint3);

        canvas.save();
        canvas.restore();


        return icon;
    }

    private static Bitmap drawTextToBitmapPT(Bitmap bg, String price) {
        int width = bg.getWidth();
        int hight = bg.getHeight();
        //建立一个空的Bitmap
        Bitmap icon = Bitmap.createBitmap(width, hight, Bitmap.Config.ARGB_8888);
        // 初始化画布绘制的图像到icon上
        Canvas canvas = new Canvas(icon);
        // 建立画笔
        Paint photoPaint = new Paint();
        // 获取更清晰的图像采样，防抖动
        photoPaint.setDither(true);
        // 过滤一下，抗剧齿
        photoPaint.setFilterBitmap(true);

        Rect src = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标


        canvas.drawBitmap(bg, src, dst, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint


        TextPaint textPaint1 = myTextPaint(16);
        canvas.drawText("拼团特价", 18, 165, textPaint1);


        TextPaint textPaint2 = myTextPaint(15);
        canvas.drawText("¥", 7, 210, textPaint2);


        TextPaint textPaint3 = myTextPaint(43);
        canvas.drawText(price, 22, 210, textPaint3);

        canvas.save();
        canvas.restore();


        return icon;
    }

    public static TextPaint myTextPaint(float textSize) {

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
//        int TEXT_SIZE = Math.round(25 * getRATIO());
        textPaint.setTextSize(textSize);// 字体大小
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
        textPaint.setColor(Color.parseColor("#FFFFFF"));// 采用的颜色
        return textPaint;

    }
}

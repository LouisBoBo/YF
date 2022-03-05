package com.yssj.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.squareup.picasso.Picasso;
import com.yssj.YUrl;
import com.yssj.activity.R;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class GlideUtils {


//    public static void initRoundImage(final Context context, String url, final ImageView imageView) {
//
//        String mUrl;
//        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
//            mUrl = url;
//        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
//            mUrl = url;
//        } else {
//            mUrl = YUrl.imgurl + url;
//        }
//
//
//        //圆形
//        Glide.with(context).load(mUrl).asBitmap().centerCrop() .error(R.drawable.image_default).into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//
//    }


    public static void loadImage(RequestManager glide, String url, ImageView view) {
        glide.load(url).into(view);
    }


    //圆形图片 ---imageView
//    public static void initRoundImage(final Context context, String url, final ImageView imageView) {
//
//        String mUrl;
//        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
//            mUrl = url;
//        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
//            mUrl = url;
//        } else {
//            mUrl = YUrl.imgurl + url;
//        }
//
//
//        //圆形
////        Glide.with(context).load(mUrl).asBitmap().centerCrop() .error(R.drawable.image_default).into(new BitmapImageViewTarget(imageView) {
////            @Override
////            protected void setResource(Bitmap resource) {
////                RoundedBitmapDrawable circularBitmapDrawable =
////                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
////                circularBitmapDrawable.setCircular(true);
////                imageView.setImageDrawable(circularBitmapDrawable);
////            }
////        });
//        if (UIutil.isOnMainThread()) {
//            Glide.with(context)
//                    .load(mUrl)
//                    .transform(new GlideCircleTransform(context))
//                    .error(R.drawable.image_default)
//                    .into(imageView);
//        }
//
//    }
//


    //圆形图片 ---imageView
    public static void initRoundImage(RequestManager glide, final Context context, String url, final ImageView imageView) {

        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }


        //圆形
//        Glide.with(context).load(mUrl).asBitmap().centerCrop() .error(R.drawable.image_default).into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });

        try {
            glide
                    .load(mUrl)
                    .transform(new GlideCircleTransform(context))
                    .error(R.drawable.image_default)
                    .into(imageView);
        } catch (Exception mE) {
            return;
        }


    }

    //圆圆角图片 ---imageView
    public static void initRoundJiaoImage(final Context context, String url, final ImageView imageView) {

        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }


//        //圆形
//        Glide.with(context).load(mUrl).asBitmap().centerCrop() .error(R.drawable.image_default).into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });



            Glide.with(context)
                    .load(mUrl)
                    .transform(new GlideCircleTransform(context))
                    .error(R.drawable.image_default)
                    .into(imageView);


//        Glide.with(context).load("https://www.baidu.com/img/bdlogo.png").transform(new GlideRoundTransform(context)).into(imageView);

//        Glide.with(context).load(mUrl).transform(new GlideRoundTransform(context, 5)).error(R.drawable.image_default).into(imageView);


    }


}

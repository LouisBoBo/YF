package com.yssj.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yssj.YUrl;
import com.yssj.activity.R;


import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class PicassoUtils {

    public static void initImage(Context context, String url, ImageView imageView) {
        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }
//        mUrl ="http://pic.sc.chinaz.com/files/pic/pic9/201906/bpic12146.jpg";
        Picasso.get().load(mUrl).placeholder(R.drawable.image_default).error(R.drawable.image_default)
                .into(imageView);


    }

    public static void initImageNoDefPic(String url, ImageView imageView) {
        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }
//        mUrl ="http://pic.sc.chinaz.com/files/pic/pic9/201906/bpic12146.jpg";
        Picasso.get().load(mUrl).placeholder(R.drawable.test).error(R.drawable.test)
                .into(imageView);



    }

    public static void initImageNoDefPic2(String url, ImageView imageView) {
        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }
//        mUrl ="http://pic.sc.chinaz.com/files/pic/pic9/201906/bpic12146.jpg";
        Picasso.get().load(mUrl).error(R.drawable.test)
                .into(imageView);



    }


    public static void initImage(String url, ImageView imageView,Callback callback) {
        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }
//        mUrl ="http://pic.sc.chinaz.com/files/pic/pic9/201906/bpic12146.jpg";
        Picasso.get().load(mUrl)
                .into(imageView,callback);


    }

    /**
     * ???????????? ???????????????
     *
     * @date 2017???3???17?????????2:42:18
     */
    public static void initImage2(Context context, String url, ImageView imageView) {




        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }
        Picasso.get().load(mUrl)
                .into(imageView);

        // Picasso.with(context).load(mUrl).placeholder(R.drawable.image_default).error(R.drawable.image_default)
        // .transform(new CropSquareTransformation()).into(imageView);

    }

    /**
     * ???????????????????????????-----????????????
     * @param context
     * @param url
     * @param imageView
     */
    public static void initBigImage(Context context, String url, ImageView imageView) {

        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }


        Picasso.get()
                .load(mUrl)
                .memoryPolicy(NO_CACHE, NO_STORE)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .into(imageView);
    }



    /**
     * ????????????
     * @param context
     * @param url
     * @param imageView
     */
    public static void initYuanJiao(Context context, String url, ImageView imageView, int  radius) {

        String mUrl;
        if (!TextUtils.isEmpty(url) && url.contains("http://")) {
            mUrl = url;
        } else if (!TextUtils.isEmpty(url) && url.contains("https://")) {
            mUrl = url;
        } else {
            mUrl = YUrl.imgurl + url;
        }


//        Picasso.with(context)
//                .load(mUrl)
//                .memoryPolicy(NO_CACHE, NO_STORE)
//                .placeholder(R.drawable.image_default)
//                .error(R.drawable.image_default)
//                .into(imageView);

//        Picasso.with(context).load(mUrl).transform(new RoundTransform(radius)).into(imageView);
//        Picasso.with(context).load(mUrl).transform(new RoundTransform(radius)).into(imageView);

//        Glide.with(context).load(mUrl).transform(new CornersTransform(context,radius)).into(imageView);

//        Glide.with(context).load(url).transform(new CircleTransform(context),new CornersTransform(context,radius)).into(imageView);



    }




//    /**
//     * ??????????????????-Picasso
//     */
//    static class RoundTransform implements Transformation {
//        private int radius;//?????????
//
//        public RoundTransform(int radius) {
//            this.radius = radius;
//        }
//
//        @Override
//        public Bitmap transform(Bitmap source) {
//            int width = source.getWidth();
//            int height = source.getHeight();
//            //??????
//            Bitmap bitmap = Bitmap.createBitmap(width, height, source.getConfig());
//            Paint paint = new Paint();
//            Canvas canvas = new Canvas(bitmap);//????????????????????????
//            paint.setAntiAlias(true);//???????????????
//            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//            //???????????????
//            RectF rectF = new RectF(new Rect(0, 0, width, height));//??????
//            canvas.drawRoundRect(rectF, radius, radius, paint);//???????????????
//            //
//            paint.setFilterBitmap(true);
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//            canvas.drawBitmap(source, 0, 0, paint);
//            source.recycle();//??????
//
//            return bitmap;
//        }
//
//        @Override
//        public String key() {
//            return "round : radius = " + radius;
//        }
//    }





//    static class RoundTransform2 implements Transformation {
//
//        private Context mContext;
//        private int mRadius;
//
//        public RoundTransform2(Context context, int radius) {
//            mContext = context;
//            mRadius = radius;
//        }
//
//        @Override
//        public Bitmap transform(Bitmap source) {
//
//            int widthLight = source.getWidth();
//            int heightLight = source.getHeight();
//            int radius = DensityUtil.dip2px(mContext, mRadius); // ????????????
//
//            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//
//            Canvas canvas = new Canvas(output);
//            Paint paintColor = new Paint();
//            paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);
//
//            RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));
//
//            canvas.drawRoundRect(rectF, radius, radius, paintColor);
////        canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);
//
//            Paint paintImage = new Paint();
//            paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//            canvas.drawBitmap(source, 0, 0, paintImage);
//            source.recycle();
//            return output;
//        }
//
//        @Override
//        public String key() {
//            return "roundcorner";
//        }
//
//    }
//





}

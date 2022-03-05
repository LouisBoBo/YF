package com.yssj.ui.activity.circles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YConstance;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.activity.picselect.cutimage.BitmapUtils;
import com.yssj.ui.activity.picselect.cutimage.CropImageView;
import com.yssj.ui.activity.picselect.cutimage.ImageViewTouch;
import com.yssj.ui.activity.picselect.cutimage.ImageViewTouchBase;
import com.yssj.ui.activity.picselect.cutimage.Matrix3;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CutImageActivity extends BasicActivity implements OnClickListener {
    private TextView mTvNext;// 下一步
    private LinearLayout mTvBack;// 返回
    private RelativeLayout mRlRoot;
    //	private ImageView mIvPic;
    private String picPath;// 选择的图片路径
    private Context mContext;
    public ImageViewTouch mainImage;
    private Bitmap mainBitmap;
    private LoadImageTask mLoadImageTask;
    private int imageWidth, imageHeight;// 展示图片控件 宽 高
    public CropImageView mCropPanel;// 剪切操作控件
    private LinearLayout mLlRadio1;
    private LinearLayout mLlRadio3;
    private LinearLayout mLlRadio9;
    private TextView mTvRadio1;
    private TextView mTvRadio3;
    private TextView mTvRadio9;
    private TextView mTvBg1;
    private TextView mTvBg3;
    private TextView mTvBg9;

    private List<String> mListPath;//图片路径集合
    private int picIndex = 0;//图片当前的位置
    private int mIndex = 2;
    private boolean nextFlag;//图片加载完成才能点击下一步
    private boolean mAddFirstPic;//代表添加标签的那张图片
    private List<String> mListCutImagePath;//保存剪裁后的新图片路径
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cut_image);
        mContext = this;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;
//        picPath = getIntent().getStringExtra("picPath");
        mAddFirstPic = getIntent().getBooleanExtra("add_first_pic", false);
        mListPath = (List<String>) getIntent().getSerializableExtra("picPathList");

    }

    @Override
    protected void onResume() {
        super.onResume();
        isFlag = false;
        picIndex = 0;
        mIndex = 2;
        nextFlag = false;//图片加载完成才能点击下一步
        if (mListCutImagePath != null) {
            mListCutImagePath = null;
        }
        mListCutImagePath = new ArrayList<>();//保存剪裁后的新图片路径
        initView();
        initData();
    }

    private void initData() {
        loadImage(mListPath.get(picIndex));
    }

    private void initView() {
        mTvNext = (TextView) findViewById(R.id.issue_tv_next);
        mTvNext.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (mListPath.size() == 1) {
            mTvNext.setText("下一步");
        } else {
            mTvNext.setText("下一张");
        }
        tv_title.setText("裁剪(1/" + mListPath.size() + ")");
        mTvBack = (LinearLayout) findViewById(R.id.img_back);
        mTvBack.setOnClickListener(this);
        mRlRoot = (RelativeLayout) findViewById(R.id.rl_root);

        //new-----
        mLlRadio1 = (LinearLayout) findViewById(R.id.ll_radio_1);
        mLlRadio1.setOnClickListener(this);
        mLlRadio3 = (LinearLayout) findViewById(R.id.ll_radio_3);
        mLlRadio3.setOnClickListener(this);
        mLlRadio9 = (LinearLayout) findViewById(R.id.ll_radio_9);
        mLlRadio9.setOnClickListener(this);
        mTvRadio1 = (TextView) findViewById(R.id.tv_radio_1);
        mTvRadio3 = (TextView) findViewById(R.id.tv_radio_3);
        mTvRadio9 = (TextView) findViewById(R.id.tv_radio_9);
        mTvRadio1 = (TextView) findViewById(R.id.tv_radio_1);
        mTvRadio3 = (TextView) findViewById(R.id.tv_radio_3);
        mTvRadio9 = (TextView) findViewById(R.id.tv_radio_9);

        mTvBg1 = (TextView) findViewById(R.id.tv_bg1);
        mTvBg3 = (TextView) findViewById(R.id.tv_bg3);
        mTvBg9 = (TextView) findViewById(R.id.tv_bg9);
        mTvRadio3.setTextColor(Color.parseColor("#ff3f8b"));
        mTvRadio1.setTextColor(Color.parseColor("#7d7d7d"));
        mTvRadio9.setTextColor(Color.parseColor("#7d7d7d"));
        mTvBg3.setBackgroundResource(R.drawable.shape_cut_image_red1);
        mTvBg1.setBackgroundResource(R.drawable.shape_cut_image_gray1);
        mTvBg9.setBackgroundResource(R.drawable.shape_cut_image_gray1);
        mCropPanel = (CropImageView) findViewById(R.id.crop_panel);
        mainImage = (ImageViewTouch) findViewById(R.id.main_image);
        mainImage.setFlingListener(new ImageViewTouch.OnImageFlingListener() {
            @Override
            public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //System.out.println(e1.getAction() + " " + e2.getAction() + " " + velocityX + "  " + velocityY);
                if (velocityY > 1) {
//					closeInputMethod();
//                    ToastUtil.showShortText(CutImageActivity.this, "");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.issue_iv_pic:

                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.issue_tv_next:

                if (nextFlag && mCropPanel.getVisibility() == View.VISIBLE) {//图片加载完成及剪裁完成才能点击下一张
                    picIndex++;
                    if (picIndex <= mListPath.size() - 1) {
                        tv_title.setText("裁剪(" + (picIndex + 1) + "/" + mListPath.size() + ")");
                        mTvRadio3.setTextColor(Color.parseColor("#ff3f8b"));
                        mTvRadio1.setTextColor(Color.parseColor("#7d7d7d"));
                        mTvRadio9.setTextColor(Color.parseColor("#7d7d7d"));
                        mTvBg3.setBackgroundResource(R.drawable.shape_cut_image_red1);
                        mTvBg1.setBackgroundResource(R.drawable.shape_cut_image_gray1);
                        mTvBg9.setBackgroundResource(R.drawable.shape_cut_image_gray1);
                        mIndex = 2;
                    }
                    if (picIndex <= mListPath.size() - 2) {//倒数第二张可以点击下一张

                        mCropPanel.setVisibility(View.GONE);


                        applyCropImage();
                    } else {
                        mTvNext.setText("下一步");
                        applyCropImage();
                    }
                }


                break;
            case R.id.ll_radio_1:
                setRadio(1);
                break;
            case R.id.ll_radio_3:
                setRadio(2);
                break;
            case R.id.ll_radio_9:
                setRadio(3);
                break;
            default:
                break;
        }

    }

    public void setRadio(int index) {
        if (mIndex == index) {
            return;
        }
        mIndex = index;
        if (index == 1) {
            mTvRadio1.setTextColor(Color.parseColor("#ff3f8b"));
            mTvRadio3.setTextColor(Color.parseColor("#7d7d7d"));
            mTvRadio9.setTextColor(Color.parseColor("#7d7d7d"));
            mTvBg1.setBackgroundResource(R.drawable.shape_cut_image_red1);
            mTvBg3.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mTvBg9.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mCropPanel.setRatioCropRect(mainImage.getBitmapRect(),
                    1f);
        } else if (index == 2) {
            mTvRadio3.setTextColor(Color.parseColor("#ff3f8b"));
            mTvRadio1.setTextColor(Color.parseColor("#7d7d7d"));
            mTvRadio9.setTextColor(Color.parseColor("#7d7d7d"));
            mTvBg3.setBackgroundResource(R.drawable.shape_cut_image_red1);
            mTvBg1.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mTvBg9.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mCropPanel.setRatioCropRect(mainImage.getBitmapRect(),
                    3 / 4f);
        } else if (index == 3) {
            mTvRadio9.setTextColor(Color.parseColor("#ff3f8b"));
            mTvRadio3.setTextColor(Color.parseColor("#7d7d7d"));
            mTvRadio1.setTextColor(Color.parseColor("#7d7d7d"));
            mTvBg9.setBackgroundResource(R.drawable.shape_cut_image_red1);
            mTvBg3.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mTvBg1.setBackgroundResource(R.drawable.shape_cut_image_gray1);
            mCropPanel.setRatioCropRect(mainImage.getBitmapRect(),
                    9 / 16f);
        }
    }

    /**
     * 异步载入编辑图片
     *
     * @param filepath
     */
    public void loadImage(String filepath) {
        if (mLoadImageTask != null) {
            mLoadImageTask.cancel(true);
        }
        mLoadImageTask = new LoadImageTask();
        mLoadImageTask.execute(filepath);
    }

    private final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {

            return BitmapUtils.getSampledBitmap(params[0], imageWidth,
                    imageHeight);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result == null) {
                nextFlag = true;
                ToastUtil.showShortText(mContext, "图片格式有误,请重新选择");
                finish();
                return;
            }
            if (mainBitmap != null) {
                mainBitmap.recycle();
                mainBitmap = null;
                System.gc();
            }
            mainBitmap = result;
            mainImage.setImageBitmap(result);
            mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

            onShow();
        }
    }

    private boolean isFlag = false;//用来判断布局第一次加载完成

    public void onShow() {
        mainImage.setImageBitmap(mainBitmap);
        mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        mainImage.setScaleEnabled(false);// 禁用缩放
        isFlag = false;
        nextFlag = true;
        mRlRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFlag) {
                    mCropPanel.setVisibility(View.VISIBLE);
                    RectF r = mainImage.getBitmapRect();
                    mCropPanel.setCropRect(r);
                    mCropPanel.setRatioCropRect(mainImage.getBitmapRect(),
                            3 / 4f);
                    isFlag = true;
                }
            }
        });

    }

    /**
     * 保存剪切图片
     */
    public void applyCropImage() {
        CropImageTask task = new CropImageTask();
        task.execute(mainBitmap);
    }

    /**
     * 图片剪裁生成 异步任务
     *
     * @author panyi
     */
    private final class CropImageTask extends AsyncTask<Bitmap, Void, Bitmap> {
//        private Dialog dialog;

        @Override
        protected void onCancelled() {
            super.onCancelled();
//            dialog.dismiss();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCancelled(Bitmap result) {
            super.onCancelled(result);
//            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = BaseActivity.getLoadingDialog(getActivity(), R.string.saving_image,
//                    false);
//            dialog.show();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            RectF cropRect = mCropPanel.getCropRect();// 剪切区域矩形
            Matrix touchMatrix = mainImage.getImageViewMatrix();
            // Canvas canvas = new Canvas(resultBit);
            float[] data = new float[9];
            touchMatrix.getValues(data);// 底部图片变化记录矩阵原始数据
            Matrix3 cal = new Matrix3(data);// 辅助矩阵计算类
            Matrix3 inverseMatrix = cal.inverseMatrix();// 计算逆矩阵
            Matrix m = new Matrix();
            m.setValues(inverseMatrix.getValues());
            m.mapRect(cropRect);// 变化剪切矩形

            Bitmap resultBit = Bitmap.createBitmap(params[0],
                    (int) cropRect.left, (int) cropRect.top,
                    (int) cropRect.width(), (int) cropRect.height());

//            saveBitmap(resultBit, activity.saveFilePath);
            return resultBit;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
//            dialog.dismiss();
            if (result == null)
                return;

//            changeMainBitmap(result);
//            mCropPanel.setCropRect(mainImage.getBitmapRect());
            if (mAddFirstPic) {
                saveBitmap(result, YConstance.saveCutPicPath, 0);
            } else {
                saveBitmap(result, YConstance.saveCutPicPath, picIndex);
            }
        }
    }

    /**
     * 保存Bitmap图片到指定文件
     */
    public void saveBitmap(Bitmap bm, String filePath, int location) {
        File f2 = new File(filePath);
        if (!f2.exists()) {
            f2.mkdir();
        } else {
            if (mAddFirstPic) {
                File files[] = f2.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    if (files[i].isFile()) { // 判断是否是文件
                        files[i].delete(); // delete()方法 你应该知道 是删除的意思;
                    }
                }
            }
        }
        long longTime = System.currentTimeMillis();
        File f = new File(filePath + longTime + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

            mListCutImagePath.add(filePath + longTime + ".jpg");
            if (picIndex == mListPath.size()) {//已经是最后一张图片
                if (mAddFirstPic) {//添加第一张图
                    Intent intent = new Intent(CutImageActivity.this, AddTagsActivity.class);
                    intent.putExtra("picPath", filePath + longTime + ".jpg");
                    startActivity(intent);
                    mCropPanel.setVisibility(View.GONE);
                    ((Activity) mContext).overridePendingTransition(R.anim.activity_from_right,
                            R.anim.activity_search_close);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("image_listss", (Serializable) mListCutImagePath);
                    setResult(30001, intent);
                    finish();
                }
            } else {//加载新图片
                loadImage(mListPath.get(picIndex));
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 切换底图Bitmap
     *
     * @param newBit
     */
    public void changeMainBitmap(Bitmap newBit) {
        if (newBit == null)
            return;

        if (mainBitmap != null) {
            if (!mainBitmap.isRecycled()) {// 回收
                mainBitmap.recycle();
            }
        }
        mainBitmap = newBit;
        mainImage.setImageBitmap(mainBitmap);
        mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

//		increaseOpTimes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadImageTask != null) {
            mLoadImageTask.cancel(true);
        }

//		if (mSaveImageTask != null) {
//			mSaveImageTask.cancel(true);
//		}
    }


}

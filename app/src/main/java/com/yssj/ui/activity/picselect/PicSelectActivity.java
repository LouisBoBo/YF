package com.yssj.ui.activity.picselect;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.ui.activity.circles.AddTagsActivity;
import com.yssj.ui.activity.circles.CutImageActivity;
import com.yssj.ui.activity.picselect.ListImageDirPopupWindow.OnImageDirSelected;
import com.yssj.ui.activity.picselect.MyAdapter.SelectPicListener;
import com.yssj.ui.base.BasicActivity;
//import com.yssj.ui.activity.picture.ImageItem;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PicSelectActivity extends BasicActivity implements OnImageDirSelected, OnClickListener, SelectPicListener {
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    private List<File> mListFile = new ArrayList<File>();
    private List<String> mFloaderPath = new ArrayList<String>();
    /**
     * 所有的图片
     */
    private List<String> mImgs = new ArrayList<String>();

    private GridView mGirdView;
    private MyAdapter mAdapter;
    private TextView mTvOk;
    private int availableSize;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    private RelativeLayout mBottomLy;

    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private LinearLayout mBack;
    private TextView mPicBack;
    private int mScreenHeight;
    private boolean headFlag = false;// true，设置头像
    private boolean mIssueSweetFriends = false;// true,发布密友圈
    private ListImageDirPopupWindow mListImageDirPopupWindow;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        List<File> list = new ArrayList<File>();
        if (mListFile.size() == 0) {
            Toast.makeText(getApplicationContext(), "没有图片", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < mListFile.size(); i++) {
            // mImgs.addAll(Arrays.asList(mListFile.get(i).list(new
            // FilenameFilter() {
            // @Override
            // public boolean accept(File dir, String filename) {
            // if (filename.endsWith(".jpg") || filename.endsWith(".png") ||
            // filename.endsWith(".jpeg"))
            // return true;
            // return false;
            // }
            // })));

            File[] files = mListFile
                    .get(i)
                    .listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    });
            if (files != null) {
                for (int j = 0; j < files.length; j++) {
                    list.add(files[j]);
                }
            }

//			list.addAll(Arrays
//					.asList(
//							mListFile
//							.get(i)
//							.listFiles(new FilenameFilter() {
//				@Override
//				public boolean accept(File dir, String filename) {
//					if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))
//						return true;
//					return false;
//				}
//			})));


            // for (int j = 0; j < Arrays.asList(mListFile.get(i).list(new
            // FilenameFilter() {
            // @Override
            // public boolean accept(File dir, String filename) {
            // if (filename.endsWith(".jpg") || filename.endsWith(".png") ||
            // filename.endsWith(".jpeg"))
            // return true;
            // return false;
            // }
            // })).size(); j++) {
            // mFloaderPath.add(mListFile.get(i).getAbsolutePath());
            // }
        }
        // List<File> orderFile=new ArrayList<File>();
        // System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(list, new FileComparator());// 通过重写Comparator的实现类

        // File[] orderFile = new File[list.size()];
        // for (int j = 0; j < list.size(); j++) {
        //// String str = mFloaderPath.get(j) + "/" + mImgs.get(j);
        //// File file = new File(str);
        // orderFile[j] = list.get(j);
        // }
        // File temp; // 记录临时中间值
        // int size = orderFile.length; // 数组大小
        // for (int i = 0; i < size - 1; i++) {
        // for (int j = i + 1; j < size; j++) {
        // if (orderFile[i].lastModified() < orderFile[j].lastModified()) { //
        // 交换两数的位置
        // temp = orderFile[i];
        // orderFile[i] = orderFile[j];
        // orderFile[j] = temp;
        // }
        // }
        // }
        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            strList.add(list.get(i).getAbsolutePath());
        }
        // for (int j = 0; j < list.size(); j++) {
        // strList.add(list.get(j).getAbsolutePath());
        // }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new MyAdapter(PicSelectActivity.this, strList, R.layout.grid_item, mFloaderPath, availableSize, this,
                null, headFlag, mIssueSweetFriends);
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    ;

    public class FileComparator implements Comparator<File> {
        public int compare(File file1, File file2) {
            if (file1.lastModified() > file2.lastModified()) {
                return -1;
            } else if (file1.lastModified() == file2.lastModified()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public FileFilter fileFilter = new FileFilter() {
        public boolean accept(File file) {
            String tmp = file.getName().toLowerCase();
            if (tmp.endsWith(".mov") || tmp.endsWith(".jpg")) {
                return true;
            }
            return false;
        }
    };

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_pic);
        headFlag = getIntent().getBooleanExtra("set_head_pic", false);
        mIssueSweetFriends = getIntent().getBooleanExtra("sweet_friends_issue", false);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        availableSize = getIntent().getIntExtra("can_add_image_size", 9);// 单次最多添加九张
        initView();
        if (headFlag || mIssueSweetFriends) {
            mTvOk.setVisibility(View.GONE);
        }
        getImages();
        initEvent();

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */

    public void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PicSelectActivity.this.getContentResolver();

                // 只查询jpeg和png的图片
                // Cursor mCursor = mContentResolver.query(mImageUri, null,
                // MediaStore.Images.Media.MIME_TYPE + "=? or "
                // + MediaStore.Images.Media.MIME_TYPE + "=?",
                // new String[] { "image/jpeg", "image/png" },
                // MediaStore.Images.Media.DATE_MODIFIED);
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"}, MediaStore.Images.Media.DATE_TAKEN);

                LogYiFu.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    int picSize = 0;
                    try {
                        picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                                        || filename.endsWith(".jpeg"))
                                    return true;
                                return false;
                            }
                        }).length;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);
                    mListFile.add(parentFile);
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);

        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvOk.setOnClickListener(this);
        mBack = (LinearLayout) findViewById(R.id.tv_back);
        mPicBack = (TextView) findViewById(R.id.tv_pic_back);
        mPicBack.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void selected(ImageFloder floder) {

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        mFloaderPath.clear();
        for (int i = 0; i < mImgs.size(); i++) {
            mFloaderPath.add(mImgDir.getAbsolutePath());
        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        List<String> picPath = mAdapter.getPicPath();
        List<String> strList = new ArrayList<String>();
        for (int j = 0; j < mImgs.size(); j++) {
            String str = mFloaderPath.get(j) + "/" + mImgs.get(j);
            strList.add(str);
        }
        // mAdapter = new MyAdapter(getApplicationContext(), mImgs,
        // R.layout.grid_item, mFloaderPath, availableSize,
        // PicSelectActivity.this, picPath);
        mAdapter = new MyAdapter(PicSelectActivity.this, strList, R.layout.grid_item, mFloaderPath, availableSize,
                PicSelectActivity.this, picPath, headFlag, mIssueSweetFriends);
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                Intent intent = new Intent(PicSelectActivity.this, CutImageActivity.class);
                List<String> picPath = mAdapter.getPicPath();
                if(picPath.size()==0){
                    ToastUtil.showShortText(this,"请至少选择一张图片");
                    return;
                }
                intent.putExtra("picPathList", (Serializable) picPath);
                startActivityForResult(intent, 1000);

//			List<String> picPath = mAdapter.getPicPath();
//			Intent intent = new Intent();
//			intent.putExtra("image_listss", (Serializable) picPath);
//			setResult(30001, intent);
//                finish();
                break;
            case R.id.tv_pic_back:
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void clickPic(int a) {
        mTvOk.setText("完成" + "(" + a + "/" + availableSize + ")");
    }

    @Override
    public void clickPicHead(String picPath, ImageView iv) {
        if (headFlag) {
            Uri uri = getUri(picPath);
            TakePhotoUtil.cropImageUri(PicSelectActivity.this, uri);
            iv.setVisibility(View.GONE);
        }

    }

    @Override
    public void clickPicIssue(String picPath, ImageView iv) {
        if (mIssueSweetFriends) {
            Intent intent = new Intent(PicSelectActivity.this, CutImageActivity.class);//添加第一张图片
//			intent.putExtra("picPath", picPath);
            List<String> picList = new ArrayList<>();
            picList.add(picPath);
            intent.putExtra("picPathList", (Serializable) picList);
            intent.putExtra("add_first_pic", true);
            startActivity(intent);
            iv.setVisibility(View.GONE);
        }

    }

    public Uri getUri(String picPath) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            if (picPath.equals(data)) {
                int ringtoneID = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
                break;
            }
            cursor.moveToNext();
        }
        return mImageUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TakePhotoUtil.RESULT_LOAD_PICTURE) {
                Intent intet = new Intent();
                setResult(TakePhotoUtil.RESULT_LOAD_FINAL);
                PicSelectActivity.this.finish();
            }
        } else if (requestCode == 1000 && resultCode == 30001) {
            List<String> incomingDataList = (List<String>) data.getSerializableExtra("image_listss");
            Intent intent = new Intent();
            intent.putExtra("image_listss", (Serializable) incomingDataList);
            setResult(30001, intent);
            finish();
        }
    }

}

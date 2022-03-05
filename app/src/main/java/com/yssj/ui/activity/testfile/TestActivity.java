package com.yssj.ui.activity.testfile;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.entity.ShopCart;
import com.yssj.huanxin.activity.BaseActivity;
import com.yssj.ui.activity.main.SignGroupShopActivity;
import com.yssj.ui.adpter.SignGroupShopAdapter;
import com.yssj.ui.base.BasicActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends BasicActivity implements View.OnClickListener{

    public static List<ShopCart> listClick = new ArrayList<ShopCart>();
    public static TestActivity instance;
    private Context context;
    private View img_back;
    private ImageView img_click;

    public static final int TAKE_CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int TAKE_GALLERY_PERMISSION_REQUEST_CODE = 101;
    public static final int TAKE_CAMERA_PIC_FILE_REQUEST_CODE = 200;
    public static final int TAKE_GALLERY_PIC_FILE_REQUEST_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        context = this;
//        instance=this;
//        listClick.clear();
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_test_test);
//        initView();
    }

//    private void initView() {
//        instance = this;
//
//        img_back = findViewById(R.id.img_back);
//        img_click = findViewById(R.id.clcikimg);
//        img_back.setOnClickListener(this);
//        img_click.setOnClickListener(this);
//    }

//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.img_back:
//                onBackPressed();
//                break;
//            case R.id.clcikimg:
////                takePicFromCamera();
//                takePicFromGallery();
//                break;
//
//            default:
//                break;
//        }
//    }

//    private void takePicFromCamera() {
//        //相机的话 相机权限和存储权限都需要
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
//                (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                        || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//                )
//        ) {
//            String[] permissions = new String[2];
//            permissions[0] = Manifest.permission.CAMERA;
//            permissions[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                this.requestPermissions(permissions, TAKE_CAMERA_PERMISSION_REQUEST_CODE);
//            }
//            return;
//        }
//        Intent intent = new Intent();
//        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, TAKE_CAMERA_PIC_FILE_REQUEST_CODE);
//
//    }

//    private void takePicFromGallery() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            String[] permissions = new String[2];
//            permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                this.requestPermissions(permissions, TAKE_GALLERY_PERMISSION_REQUEST_CODE);
//            }
//            return;
//        }
//
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            ArrayList<String> mimeTypes = new ArrayList();
//            mimeTypes.add("image/jpeg");
//            mimeTypes.add("image/png");
//            mimeTypes.add("image/jpg");
//            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        }
//        startActivityForResult(
//                Intent.createChooser(
//                        intent,
//                        "选择图片"
//                ), TAKE_GALLERY_PIC_FILE_REQUEST_CODE
//        );
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case TAKE_CAMERA_PIC_FILE_REQUEST_CODE:
//                    Uri uriImageData;
//                    Bundle bundle = data.getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
//                    img_click.setImageBitmap(bitmap);
//
//                    if (null != data.getData()) {
//                        uriImageData = data.getData();
//                    } else {
//                        uriImageData = Uri.parse(
//
//                                MediaStore.Images.Media.insertImage(
//                                        context.getContentResolver(),
//                                        bitmap,
//                                        null,
//                                        null
//                                )
//                        );
//                    }
//                    try {
//                        UpLoadPic(uriImageData);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//                case TAKE_GALLERY_PIC_FILE_REQUEST_CODE:
//                    Uri gallerySelectUrl = data.getData();
//                    img_click.setImageURI(gallerySelectUrl);
//                    try {
//                        UpLoadPic(gallerySelectUrl);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                default:
//
//
//                    break;
//            }
//        }
//    }
//
//    //上传图片
//    private void UpLoadPic(Uri gallerySelectUrl) throws IOException {
//
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor actualimagecursor = this.managedQuery(gallerySelectUrl, proj, null, null, null);
//        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        actualimagecursor.moveToFirst();
//        String img_path = actualimagecursor.getString(actual_image_column_index);
//        File file = new File(img_path);
//        UpLoadUtil.uploadImage(context, img_path, new UpLoadUtil.UpLoadImgListener() {
//            @Override
//            public void upLoadSuccess(int imgId) {
//                Log.i("sfakfa", "upLoadSuccess: ");
//            }
//
//            @Override
//            public void upLoadFail() {
//                Log.i("fajfaj", "upLoadFail: ");
//            }
//        });
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case TAKE_CAMERA_PERMISSION_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    takePicFromCamera();
//                    return;
//                }
//                //拒绝了
////                ToastUtils.showNormalToast(getActivity(),"请点权限，并允许授权相机和存储权限。");
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//                        String pkg = "com.android.settings";
//                        String cls = "com.android.settings.applications.InstalledAppDetails";
//                        i.setComponent(new ComponentName(pkg, cls));
//                        i.setData(Uri.parse("package:" + context.getPackageName()));
//                        startActivity(i);
//                    }
//                }, 1000);
//                break;
//            case TAKE_GALLERY_PERMISSION_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    takePicFromGallery();
//                    return;
//                }
//                //拒绝了
////                ToastUtils.showNormalToast(getActivity(),"请点权限，并允许授权存储权限。");
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//                        String pkg = "com.android.settings";
//                        String cls = "com.android.settings.applications.InstalledAppDetails";
//                        i.setComponent(new ComponentName(pkg, cls));
//                        i.setData(Uri.parse("package:" + context.getPackageName()));
//                        startActivity(i);
//                    }
//                }, 1000);
//                break;
//        }
//    }
}

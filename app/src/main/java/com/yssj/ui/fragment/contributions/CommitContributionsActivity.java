package com.yssj.ui.fragment.contributions;

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
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.AppManager;
import com.yssj.entity.ShopCart;
import com.yssj.entity.VipDikouData;
import com.yssj.eventbus.MessageEvent;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.activity.testfile.TestActivity;
import com.yssj.ui.activity.testfile.UpLoadUtil;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.dialog.SelectPicDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class CommitContributionsActivity extends BasicActivity implements View.OnClickListener {

    public static List<ShopCart> listClick = new ArrayList<ShopCart>();
    public static List<Uri> uriList = new ArrayList<>();
    public static CommitContributionsActivity instance;
    private Context context;
    private View img_back;
    private View img_example;

    private View add_img1;
    private View add_img2;
    private View add_img3;
    private View add_img4;
    private View add_img5;
    private View add_img6;
    private View pub_img;
    private TextView sub_tv;
    private View zhedie_view;
    private View type_view;
    private View size_view;
    private TextView type_content;
    private TextView size_content;
    private FlexboxLayout flexboxLayout_type;
    private FlexboxLayout flexboxLayout_size;
    private List<TextView> type_textViews = new ArrayList<>();
    private List<TextView> size_textViews = new ArrayList<>();

    private SelectPicDialog selectPicDialog;


    public static final int TAKE_CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int TAKE_GALLERY_PERMISSION_REQUEST_CODE = 101;
    public static final int TAKE_CAMERA_PIC_FILE_REQUEST_CODE = 200;
    public static final int TAKE_GALLERY_PIC_FILE_REQUEST_CODE = 201;

    private static int RESULT_LOAD_IMAGE = 3;
    private static int RESULT_LOAD_PICTURE = 4;
    private static final int RESULT_OK = -1;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent message){
        if(message.getMessage().equals("审核返回")){
            onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SelectType message){
        if(message.type == 1){
            type_content.setText(message.name);
        }else {
            size_content.setText(message.name);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        instance = this;
        listClick.clear();
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.commit_contributions);

        initView();
    }

    private void initView() {
        instance = this;
        EventBus.getDefault().register(this);

//        flexboxLayout_type = findViewById(R.id.flexbox_layout1);
//        flexboxLayout_size = findViewById(R.id.flexbox_layout2);

        add_img1 = findViewById(R.id.add_image1);
        add_img2 = findViewById(R.id.add_image2);
        add_img3 = findViewById(R.id.add_image3);
        add_img4 = findViewById(R.id.add_image4);
        add_img5 = findViewById(R.id.add_image5);
        add_img6 = findViewById(R.id.add_image6);

        img_back = findViewById(R.id.img_back);
        img_example = findViewById(R.id.explain_limit);
        sub_tv = findViewById(R.id.submit);
        zhedie_view = findViewById(R.id.zhedie_base);
        type_view = findViewById(R.id.type_base);
        size_view = findViewById(R.id.size_base);
        type_content = findViewById(R.id.type_content);
        size_content = findViewById(R.id.size_content);

        img_back.setOnClickListener(this);
        sub_tv.setOnClickListener(this);
        img_example.setOnClickListener(this);
        add_img1.setOnClickListener(this);
        add_img2.setOnClickListener(this);
        add_img3.setOnClickListener(this);
        add_img4.setOnClickListener(this);
        add_img5.setOnClickListener(this);
        add_img6.setOnClickListener(this);
        zhedie_view.setOnClickListener(this);
        type_view.setOnClickListener(this);
        size_view.setOnClickListener(this);

//        String[] tags1 = {"西装", "外套", "毛呢外套", "半身群"};
//        for (int i = 0; i < tags1.length; i++) {
//            Book model = new Book();
//            model.setId(i);
//            model.setName(tags1[i]);
//            flexboxLayout_type.addView(createNewFlexItemTextView(model,1));
//        }
//
//        String[] tags2 = {"2XS及以下", "XS", "S", "M"};
//        for (int i = 0; i < tags2.length; i++) {
//            Book model = new Book();
//            model.setId(i);
//            model.setName(tags2[i]);
//            flexboxLayout_size.addView(createNewFlexItemTextView(model,2));
//        }

        Getdata();
    }

    /**
     * 动态创建TextView
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final Book book, final int type) {
        final TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getName());
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.light_gray));
        textView.setBackgroundResource(R.drawable.bg_choice_btn_default);
        textView.setTag(book.getId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("sdafjsf", book.getName());

                List<TextView> textViews = type == 1?type_textViews:size_textViews;
                for (TextView textView1 : textViews) {
                    textView1.setTextColor(getResources().getColor(R.color.light_gray));
                    textView1.setBackgroundResource(R.drawable.bg_choice_btn_default);
                }

                TextView vv = (TextView)view;
                vv.setTextColor(getResources().getColor(R.color.white));
                vv.setBackgroundResource(R.drawable.bg_choice_btn_checked);
            }
        });
        int padding = TagUtil.dpToPixel(context, 4);
        int paddingLeftAndRight = TagUtil.dpToPixel(context, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = TagUtil.dpToPixel(context, 6);
        int marginTop = TagUtil.dpToPixel(context, 0);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);

        if(type ==1){
            type_textViews.add(textView);
        }else {
            size_textViews.add(textView);
        }

        return textView;
    }

    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.submit:
                submitData();
                break;
            case R.id.add_image1:
                selectPicDialog(v);
                break;
            case R.id.add_image2:
                selectPicDialog(v);
                break;
            case R.id.add_image3:
                selectPicDialog(v);
                break;
            case R.id.add_image4:
                selectPicDialog(v);
                break;
            case R.id.add_image5:
                selectPicDialog(v);
                break;
            case R.id.add_image6:
                selectPicDialog(v);
                break;
            case R.id.explain_limit:
                Intent intent = new Intent(context, ScanEeamplesActivity.class);
                startActivity(intent);
                break;
            case R.id.zhedie_base:
                zhedie_view.setSelected(!zhedie_view.isSelected());
                type_view.setVisibility(zhedie_view.isSelected()?View.INVISIBLE:View.VISIBLE);
                size_view.setVisibility(zhedie_view.isSelected()?View.INVISIBLE:View.VISIBLE);
                break;
            case R.id.type_base:
                Intent intent1 = new Intent(context, ContributionClassActivity.class);
                Bundle bundleSimple1 = new Bundle();
                bundleSimple1.putString("type", "1");
                intent1.putExtras(bundleSimple1);

                startActivity(intent1);
                break;
            case R.id.size_base:
                Intent intent2 = new Intent(context, ContributionClassActivity.class);
                Bundle bundleSimple2 = new Bundle();
                bundleSimple2.putString("type", "2");
                intent2.putExtras(bundleSimple2);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //数据去重

    public static List<Uri> removeDuplicate(List<Uri> list)

    {
        Set set = new LinkedHashSet<String>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    public void submitData(){
//        List<Uri> uris = removeDuplicate(uriList);

        Intent intent = new Intent(context, ContributionStatusActivity.class);
        startActivity(intent);
    }

    public void selectPicDialog(View v){
//        pub_img = (ImageView) v;
        selectPicDialog = new SelectPicDialog(context, new SelectPicDialog.OnSelectPicDialogBtnClickListener() {
            @Override
            public void click(int btnId) {
                if (btnId == R.id.iv_camera) {
                    takePicFromCamera();
                    selectPicDialog.dismiss();
                } else if (btnId == R.id.iv_gallery) {
                    takePicFromGallery();
                    selectPicDialog.dismiss();
                }
            }
        });

        selectPicDialog.show();
    }
    private void takePicFromCamera() {
        //相机的话 相机权限和存储权限都需要
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                )
        ) {
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.CAMERA;
            permissions[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(permissions, TAKE_CAMERA_PERMISSION_REQUEST_CODE);
            }
            return;
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_CAMERA_PIC_FILE_REQUEST_CODE);

    }

    private void takePicFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {

            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(permissions, TAKE_GALLERY_PERMISSION_REQUEST_CODE);
            }
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ArrayList<String> mimeTypes = new ArrayList();
            mimeTypes.add("image/jpeg");
            mimeTypes.add("image/png");
            mimeTypes.add("image/jpg");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "选择图片"
                ), TAKE_GALLERY_PIC_FILE_REQUEST_CODE
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_CAMERA_PIC_FILE_REQUEST_CODE:
                    Uri uriImageData;
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");

//                    pub_img.setImageBitmap(bitmap);

                    if (null != data.getData()) {
                        uriImageData = data.getData();
                    } else {
                        uriImageData = Uri.parse(

                                MediaStore.Images.Media.insertImage(
                                        context.getContentResolver(),
                                        bitmap,
                                        null,
                                        null
                                )
                        );
                    }

                    try {
                        UpLoadPic(uriImageData);
                        uriList.add(uriImageData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case TAKE_GALLERY_PIC_FILE_REQUEST_CODE:
                    Uri gallerySelectUrl = data.getData();
//                    pub_img.setImageURI(gallerySelectUrl);
                    try {
                        UpLoadPic(gallerySelectUrl);
                        uriList.add(gallerySelectUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:


                    break;
            }
        }
    }

    //上传图片
    private void UpLoadPic(Uri gallerySelectUrl) throws IOException {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.managedQuery(gallerySelectUrl, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        File file = new File(img_path);
        UpLoadUtil.uploadImage(context, img_path, new UpLoadUtil.UpLoadImgListener() {
            @Override
            public void upLoadSuccess(int imgId) {
                Log.i("sfakfa", "upLoadSuccess: ");
            }

            @Override
            public void upLoadFail() {
                Log.i("fajfaj", "upLoadFail: ");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case TAKE_CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePicFromCamera();
                    return;
                }
                //拒绝了
//                ToastUtils.showNormalToast(getActivity(),"请点权限，并允许授权相机和存储权限。");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        String pkg = "com.android.settings";
                        String cls = "com.android.settings.applications.InstalledAppDetails";
                        i.setComponent(new ComponentName(pkg, cls));
                        i.setData(Uri.parse("package:" + context.getPackageName()));
                        startActivity(i);
                    }
                }, 1000);
                break;
            case TAKE_GALLERY_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicFromGallery();
                    return;
                }
                //拒绝了
//                ToastUtils.showNormalToast(getActivity(),"请点权限，并允许授权存储权限。");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        String pkg = "com.android.settings";
                        String cls = "com.android.settings.applications.InstalledAppDetails";
                        i.setComponent(new ComponentName(pkg, cls));
                        i.setData(Uri.parse("package:" + context.getPackageName()));
                        startActivity(i);
                    }
                }, 1000);
                break;
        }
    }

    public void Getdata(){
        HashMap<String, String> pairsMap = new HashMap<>();
        YConn.httpPost(context, YUrl.CLOUD_API_WAR_SUPPLYMATERIAL_SUPPLY, pairsMap, new HttpListener<VipDikouData>() {
            @Override
            public void onSuccess(VipDikouData result) {


            }

            @Override
            public void onError() {

            }
        });
    }
}

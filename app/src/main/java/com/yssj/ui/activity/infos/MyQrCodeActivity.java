package com.yssj.ui.activity.infos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.QrCodeData;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.zinc.libpermission.annotation.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyQrCodeActivity extends BasicActivity {
    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;
    @Bind(R.id.iv_qr_code)
    ImageView ivQrCode;
    @Bind(R.id.rl_qrcode)
    RelativeLayout rlQrcode;
    @Bind(R.id.tv_save)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_my_qrcode);
        ButterKnife.bind(this);
        tvTitleBase.setText("我的二维码");
        initData();


    }

    private void initData() {
        HashMap<String, String> pairsMap = new HashMap<>();
        String sss = YCache.getCacheUser(this).getUser_id() + ",ThreePage" + ",QRcode";

        pairsMap.put("scene",sss);
        pairsMap.put("isXCXqrCode","1");
        pairsMap.put("page","pages/shouye/redHongBao");


        YConn.httpPost(this, YUrl.GET_MY_QRCODE, pairsMap, new HttpListener<QrCodeData>() {
            @Override
            public void onSuccess(final QrCodeData result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String qrCode = result.getImgUrl() + "";
                        //测试用
//                        qrCode = "https://www.incursion.wang//user/QRCode/createQRCode_1563297.jpg";
                        setQrCode(YUrl.imgurl+qrCode);
                    }
                }).start();
            }
            @Override
            public void onError() {

            }
        });

    }

    @OnClick({R.id.img_back, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_save:
                requestWRITEpremission();
                break;
        }
    }
    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private void requestWRITEpremission() {
        saveImageToGallery(this, loadBitmapFromViewBySystem(rlQrcode));

    }

    public Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }


    private void setQrCode(String picPath) {
        try {
            URL url = new URL(picPath);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            InputStream is = con.getInputStream();
            final BitmapDrawable bmpDraw = new BitmapDrawable(is);
            // 完毕，关闭所有链接
            is.close();
            final Bitmap bitmap = bmpDraw.getBitmap();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int w = bitmap.getWidth(); // 得到图片的宽，高
                    int cropHeight = (int) (w * 0.98);
                    Bitmap cropBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, cropHeight, null, false);
                    ivQrCode.setImageBitmap(cropBitmap);
                    rlQrcode.setVisibility(View.VISIBLE);
                    tvSave.setVisibility(View.VISIBLE);
                }
            });


        } catch (Exception e) {
            LogYiFu.e("TAG", "下载失败");
            e.printStackTrace();
        }
    }

    //保存文件到指定路径
    public void  saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "yssjimg";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            ToastUtil.showShortText2("保存成功~");
//            if (isSuccess) {
//                return true;
//            } else {
//                return false;
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return false;
    }

}

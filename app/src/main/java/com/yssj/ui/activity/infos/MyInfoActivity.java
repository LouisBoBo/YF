package com.yssj.ui.activity.infos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.custom.view.XCRoundImageView;
import com.yssj.data.DBService;
import com.yssj.entity.DeliveryAddress;
import com.yssj.entity.UserInfo;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.MyLikeActivity;
import com.yssj.ui.activity.MyYJactivity;
import com.yssj.ui.activity.picselect.PicSelectActivity;
import com.yssj.ui.activity.setting.ManMyDeliverAddr;
import com.yssj.ui.activity.setting.SignatureActivity;
import com.yssj.ui.activity.setting.UpdateNickNameActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.upyun.UpYunException;
import com.yssj.upyun.UpYunUtils;
import com.yssj.upyun.Uploader;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.TakePhotoUtil;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;
import com.yssj.widget.LocationSelectorDialogBuilder;
import com.yssj.widget.LocationSelectorDialogBuilder.OnSaveLocationLister;

import java.io.File;
import java.util.List;

//import com.yssj.ui.activity.ModifyMineLikeActivity;
//import com.yssj.ui.activity.OldMyLikeActivity;
//import com.yssj.ui.activity.ModifyMineLikeActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends BasicActivity implements OnClickListener, OnSaveLocationLister {

    private RelativeLayout rel_user_img, rel_user_nickname, rel_address, rel_signature, rel_xiugai_biaoqian,rl_haoyoujiangli;
    private TextView tvTitle_base, tv_user_city, tv_user_birthday, tv_nicheng, tv_sign, tv_shouhuodizhi;
    private LinearLayout img_back, my;

    private XCRoundImageView img_user;
    private String nickName = "";
    private String userSign = "";

    private Context context;

    // Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    //
    // // 当点击DatePickerDialog控件的设置按钮时，调用该方法
    // DatePickerDialog.OnDateSetListener d = new
    // DatePickerDialog.OnDateSetListener() {
    // @Override
    // public void onDateSet(DatePicker view, int year, int monthOfYear, int
    // dayOfMonth) {
    // // 修改日历控件的年，月，日
    // // 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
    // dateAndTime.set(Calendar.YEAR, year);
    // dateAndTime.set(Calendar.MONTH, monthOfYear);
    // dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    // // 将页面TextView的显示更新为最新时间
    // updateBir();
    // }
    // };

    // private DateTimeSelectorDialogBuilder dialogBuilder;
    private LocationSelectorDialogBuilder locationBuilder;


    @OnClick({R.id.rl_yaoqingma, R.id.rl_my_erweima, R.id.rl_haoyoujiangli})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yaoqingma:

                startActivity(new Intent(this,InviteCodeActivity.class));
                break;
            case R.id.rl_my_erweima:
                startActivity(new Intent(this, MyQrCodeActivity.class));


                break;
            case R.id.rl_haoyoujiangli:
                if(YCache.getCacheUser(this).getReviewers() ==1){
                    return;
                }
                startActivity(new Intent(this, MyYJactivity.class));


                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getActionBar().hide();
        setContentView(R.layout.my_info);
        context = this;
        ButterKnife.bind(this);
        db = new DBService(context);
        initView();
        // initData();
        // updateBir();
    }

    /*
     * @Override protected void onResume() { super.onResume();
     * JPushInterface.onResume(this); }
     *
     * @Override protected void onPause() { super.onPause();
     * JPushInterface.onPause(this);
     *
     * }
     */
    @Override
    protected void onResume() {
        super.onResume();

        initData();
        // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		YJApplication.getLoader().stop();
        // MobclickAgent.onPause(this);
    }

    private void initView() {
        my = (LinearLayout) findViewById(R.id.my);
        my.setBackgroundColor(Color.WHITE);
        rel_user_img = (RelativeLayout) findViewById(R.id.rel_user_img);
        rel_user_img.setOnClickListener(this);

        rel_user_nickname = (RelativeLayout) findViewById(R.id.rel_user_nickname);
        rel_user_nickname.setOnClickListener(this);

        rel_address = (RelativeLayout) findViewById(R.id.rel_address);
        rel_address.setOnClickListener(this);

        rel_xiugai_biaoqian = (RelativeLayout) findViewById(R.id.rel_xiugai_biaoqian);
        rel_xiugai_biaoqian.setOnClickListener(this);

        tv_nicheng = (TextView) findViewById(R.id.tv_nicheng);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
        tv_shouhuodizhi = (TextView) findViewById(R.id.tv_shouhuodizhi);

        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);

        img_user = (XCRoundImageView) findViewById(R.id.img_user);

        tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("个人资料");

        tv_user_city = (TextView) findViewById(R.id.tv_user_city);
        tv_user_city.setOnClickListener(this);

        tv_user_birthday = (TextView) findViewById(R.id.tv_user_birthday);
        tv_user_birthday.setOnClickListener(this);

        rel_signature = (RelativeLayout) findViewById(R.id.rel_signature);
        rel_signature.setOnClickListener(this);

        rl_haoyoujiangli = (RelativeLayout) findViewById(R.id.rl_haoyoujiangli);

        if(YCache.getCacheUser(this).getReviewers() ==1){
            rl_haoyoujiangli.setVisibility(View.GONE);
        }

    }

    /**
     * 请求数据
     */
    private void initData() {

        UserInfo user = YCache.getCacheUser(this);
        // 通过用户信息查询是否已经设置过喜好
        String hobby = user.getHobby();
        // hobby ="2002,2004,2056_170,50"; //假数据

        if (null != hobby && hobby.contains("_")) {
            rel_xiugai_biaoqian.setVisibility(View.VISIBLE);
        } else {
            // 如没有设置过或者没有按现在的喜好格式设置过就隐藏修改喜好
            rel_xiugai_biaoqian.setVisibility(View.GONE);
        }

        if (null == YCache.getCacheUserSafe(this).getIs_member()
                || YCache.getCacheUserSafe(this).getIs_member().equals("0")
                || YCache.getCacheUserSafe(this).getIs_member().equals("1")) {
            tv_nicheng.setText(YCache.getCacheUserSafe(this).getNickname());
            tv_sign.setText(YCache.getCacheUserSafe(this).getUserSign());
        } else {
            tv_nicheng.setVisibility(View.GONE);
            tv_sign.setVisibility(View.GONE);
        }
        // 查询城市和生日
        new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel.queryUserInfo(context);
                // return YCache.getCacheUser(context);
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo result) {
                super.onPostExecute(context, result);
//				SetImageLoader.initImageLoader(context, img_user, result.getPic(), "");
                PicassoUtils.initImage(context, result.getPic(), img_user);


//				GlideUtils.initRoundImage(context,  result.getPic(), img_user);

                nickName = result.getNickname();
                try {
                    String city = DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getProvince()))
                            + DBService.getIntance().queryAreaNameById(Integer.parseInt(result.getCity()));
                    tv_user_city.setText(city);
                } catch (NumberFormatException e) {

                }

                if (TextUtils.isEmpty(result.getBirthday()) || "null".equals(result.getBirthday())) {
                    return;
                }

                tv_user_birthday.setText(
                        result.getBirthday().split(" ")[0].equals("null") ? "" : result.getBirthday().split(" ")[0]);
                LogYiFu.e("sheng生日", result.getBirthday());

            }

        }.execute();

        // 查询收货地址

        new SAsyncTask<Void, Void, List<DeliveryAddress>>(this, R.string.wait) {

            @Override
            protected List<DeliveryAddress> doInBackground(FragmentActivity context, Void... params) throws Exception {
                listData = ComModel2.getDeliverAddr(context);
                if (listData != null && !listData.isEmpty()) {
                    for (int i = 0; i < listData.size(); i++) {
                        StringBuffer sb = new StringBuffer();
                        if (null != listData.get(i).getProvince() && 0 < listData.get(i).getProvince()) {
                            sb.append(db.query("select * from areatbl where id = '" + listData.get(i).getProvince() + "'")
                                    .get(0).get("AreaName"));
                        }
                        if (null != listData.get(i).getCity() && 0 < listData.get(i).getCity()) {
                            sb.append(db.query("select * from areatbl where id = '" + listData.get(i).getCity() + "'")
                                    .get(0).get("AreaName"));
                        }
                        if (null != listData.get(i).getArea() && 0 < listData.get(i).getArea()) {
                            sb.append(db.query("select * from areatbl where id = '" + listData.get(i).getArea() + "'")
                                    .get(0).get("AreaName"));
                        }
                        if (null != listData.get(i).getStreet() && 0 != listData.get(i).getStreet()) {
                            sb.append(db.query("select * from areatbl where id = '" + listData.get(i).getStreet() + "'")
                                    .get(0).get("AreaName"));
                        }
                        sb.append(listData.get(i).getAddress());

                        listData.get(i).setDetailAddress(sb.toString());
                    }
                }
                return listData;
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, List<DeliveryAddress> result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result.isEmpty() || result == null) {
                        tv_shouhuodizhi.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).getIs_default() != 0) {// 默认地址

                                String dress = result.get(i).getDetailAddress();
                                if (dress.length() <= 13) {
                                    tv_shouhuodizhi.setText(dress);
                                } else {
                                    tv_shouhuodizhi.setText(dress.substring(0, 12) + "...");
                                }

                            }
                        }
                    }
                }
            }

        }.execute();

    }

    private List<DeliveryAddress> listData;
    private DBService db;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.rel_user_img: // 打开相机或相册 更换头像
                // ToastUtil.showShortText(this, "打开相机或相册修改图片。。。。");
//			 TakePhotoUtil.doPickPhotoAction(this);
                // TakePhotoUtil.doUserPic(this);
//                intent = new Intent(MyInfoActivity.this, PicSelectActivity.class);
//                intent.putExtra("set_head_pic", true);
//                ((Activity) context).startActivityForResult(intent, TakePhotoUtil.REQUEST_TAKE_HEAD_PIC);
                break;

            case R.id.rel_user_nickname: // 修改昵称
                intent = new Intent(this, UpdateNickNameActivity.class);
                intent.putExtra("nickName", nickName);
                startActivityForResult(intent, 10001);
                break;

            case R.id.rel_address:// 收货地址
                intent = new Intent(this, ManMyDeliverAddr.class);
                startActivity(intent);
                break;
            case R.id.rel_xiugai_biaoqian: // 我的喜好
                // intent = new Intent(this, ModifyMineLikeActivity.class);
                intent = new Intent(this, MyLikeActivity.class);

                startActivity(intent);
                ((FragmentActivity) this).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                break;
            case R.id.tv_user_city:
                // if (locationBuilder == null) {
                locationBuilder = LocationSelectorDialogBuilder.getInstance(this);
                locationBuilder.setOnSaveLocationLister(this);
                // }
                locationBuilder.show();
                //
                break;
            case R.id.tv_user_birthday:

                // DatePickerDialog dialog = new
                // DatePickerDialog(MyInfoActivity.this,
                // DatePickerDialog.THEME_HOLO_LIGHT, d,
                // dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),
                // dateAndTime.get(Calendar.DAY_OF_MONTH));
                // dialog.show();

                //
                // if (dialogBuilder == null) {
                // dialogBuilder = DateTimeSelectorDialogBuilder.getInstance(this);
                // dialogBuilder.setOnSaveListener(this);
                // }
                // dialogBuilder.show();
                break;
            case R.id.rel_signature:
                intent = new Intent(this, SignatureActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TakePhotoUtil.REQUEST_TAKE_HEAD_PIC && resultCode == TakePhotoUtil.RESULT_LOAD_FINAL) {
            submit("sdcard/temp.jpg");
        }
        // else
        // if (resultCode == RESULT_OK) {
        // if (requestCode == TakePhotoUtil.RESULT_LOAD_FINAL) {
        // submit("sdcard/temp.jpg");
        // } else if (requestCode == 10086) {
        // TakePhotoUtil.cropImageUri(this, data == null ? null :
        // data.getData());
        // }
        // }
        else if (requestCode == 10001 && resultCode == 10001) {
            nickName = data.getStringExtra("nickName");
        }
    }

    /***
     * 提交图片信息，提交文字信息
     */
    private void submit(String path) {

        new SAsyncTask<String, Void, String>(this, R.string.wait) {

            @Override
            protected String doInBackground(FragmentActivity context, String... params) throws Exception {
                String string = null;

                try {
                    // 设置服务器上保存文件的目录和文件名，如果服务器上同目录下已经有同名文件会被自动覆盖的。
                    String SAVE_KEY = File.separator + "userinfo/head_pic" + File.separator + System.currentTimeMillis()
                            + ".jpg";

                    // 取得base64编码后的policy
                    String policy = UpYunUtils.makePolicy(SAVE_KEY, Uploader.EXPIRATION, Uploader.BUCKET);

                    // 根据表单api签名密钥对policy进行签名
                    // 通常我们建议这一步在用户自己的服务器上进行，并通过http请求取得签名后的结果。
                    String signature = UpYunUtils.signature(policy + "&" + Uploader.TEST_API_KEY);

                    // 上传文件到对应的bucket中去。
                    string = Uploader.upload(policy, signature, Uploader.BUCKET, params[0]);

                } catch (UpYunException e) {
                    e.printStackTrace();
                }

                return string;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(context, result);
                if (result != null) {
                    LogYiFu.e("result", result);
                    // 提交服务器请求
                    submitUserImg(result);
                }

            }

        }.execute(path);
    }

    private void submitUserImg(String picPath) {
        new SAsyncTask<String, Void, UserInfo>(this, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, String... params) throws Exception {
                // TODO Auto-generated method stub
                return ComModel2.resetUserPic(context, params[0]);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
                // TODO Auto-generated method stub
                if (null == e) {
//					SetImageLoader.initImageLoader(context, img_user, result.getPic(), "");
                    PicassoUtils.initImage(context, result.getPic(), img_user);
                    ToastUtil.showShortText(context, "操作成功");
                    userInfoUpdate.update();
                }
                super.onPostExecute(context, result, e);
            }

        }.execute(picPath);
    }

    private static onUserInfoUpdate userInfoUpdate;

    public static void setOnUserInfoUpdate(Fragment f) {
        userInfoUpdate = (onUserInfoUpdate) f;
    }



    public interface onUserInfoUpdate {
        void update();
    }

    @Override
    public void onSaveLocation(String location, String provinceId, String cityId) {
        // TODO Auto-generated method stub
        tv_user_city.setText(location);
        updateLocation(provinceId, cityId);
    }

    // @Override
    // public void onSaveSelectedDate(String selectedDate) {
    // tv_user_birthday.setText(selectedDate);
    // updateLBirthday(selectedDate);
    // }

    private void updateLocation(final String provinceId, final String cityId) {
        new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {

            @Override
            protected UserInfo doInBackground(FragmentActivity context, Void... params) throws Exception {
                return ComModel2.updateLocation(MyInfoActivity.this, provinceId, cityId);
            }

            @Override
            protected boolean isHandleException() {
                return true;
            }

            @Override
            protected void onPostExecute(FragmentActivity context, UserInfo result, Exception e) {
                super.onPostExecute(context, result, e);
                if (null == e) {
                    if (result != null) {
                        ToastUtil.showShortText(MyInfoActivity.this, "操作成功");
                        // Intent intent = new
                        // Intent(UpdateNickNameActivity.this,
                        // MyInfoActivity.class);
                        // startActivity(intent);
                    }
                }
            }

        }.execute();
    }

    // private void updateLBirthday(final String selectedDate) {
    // new SAsyncTask<Void, Void, UserInfo>(this, R.string.wait) {
    //
    // @Override
    // protected UserInfo doInBackground(FragmentActivity context, Void...
    // params) throws Exception {
    // return ComModel2.updateBirthday(MyInfoActivity.this, selectedDate);
    // }
    //
    // @Override
    // protected boolean isHandleException() {
    // return true;
    // }
    //
    // @Override
    // protected void onPostExecute(FragmentActivity context, UserInfo result,
    // Exception e) {
    // super.onPostExecute(context, result, e);
    // if (null == e) {
    // if (result != null) {
    // ToastUtil.showShortText(MyInfoActivity.this, "操作成功");
    // // Intent intent = new
    // // Intent(UpdateNickNameActivity.this,
    // // MyInfoActivity.class);
    // // startActivity(intent);
    // }
    // }
    // }
    //
    // }.execute();
    // }

    // protected void updateBir() {
    //
    // Date dd = dateAndTime.getTime();
    //
    // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //
    // String dateString = dateFormat.format(dd);
    //
    // long cd = System.currentTimeMillis();
    //
    // if (cd < dd.getTime()) {
    // ToastUtil.showLongText(getApplicationContext(), "难道你是生在未来吗?未保存~");
    // return;
    // }
    //
    // tv_user_birthday.setText(dateString);
    // updateLBirthday(dateString);// 保存
    //
    // }
}

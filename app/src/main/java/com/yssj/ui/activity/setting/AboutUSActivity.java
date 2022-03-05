package com.yssj.ui.activity.setting;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.huanxin.activity.ChatAllHistoryActivity;
import com.yssj.model.ComModel;
import com.yssj.model.ComModel2;
import com.yssj.network.YConn;
import com.yssj.service.ApkDownloadManager;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.AuthKeyTools;
import com.yssj.utils.CheckVersionUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.WXminiAppUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Administrator
 */
public class AboutUSActivity extends BasicActivity {
    private RelativeLayout rel_check_version, rel_user_proto, rel_user_proto1, rel_about_us, rel_suggest_feedback;
    private LinearLayout img_back, about;
    private ImageView img_right_icon;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getActionBar().hide();
        setContentView(R.layout.activity_about_us);

        TextView tvTitle_base = (TextView) findViewById(R.id.tvTitle_base);
        tvTitle_base.setText("关于");
        about = (LinearLayout) findViewById(R.id.about);
        about.setBackgroundColor(Color.WHITE);
        img_right_icon = (ImageView) findViewById(R.id.img_right_icon);
        img_right_icon.setVisibility(View.GONE);
        img_right_icon.setImageResource(R.drawable.mine_message_center);
        img_right_icon.setOnClickListener(this);

        img_back = (LinearLayout) findViewById(R.id.img_back);

        rel_check_version = (RelativeLayout) findViewById(R.id.rel_check_version);
        rel_user_proto = (RelativeLayout) findViewById(R.id.rel_user_proto);
        rel_user_proto1 = (RelativeLayout) findViewById(R.id.rel_user_proto1);
        rel_about_us = (RelativeLayout) findViewById(R.id.rel_about_us);
        rel_suggest_feedback = (RelativeLayout) findViewById(R.id.rel_suggest_feedback);

        img_back.setOnClickListener(this);
        rel_check_version.setOnClickListener(this);
        rel_user_proto.setOnClickListener(this);
        rel_user_proto1.setOnClickListener(this);
        rel_about_us.setOnClickListener(this);
        rel_suggest_feedback.setOnClickListener(this);
        img_right_icon.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rel_about_us:   // 关于我们
                intent = new Intent(AboutUSActivity.this, AboutUSDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_check_version:        // 检测版本
//			checkVersion(view);
                CheckVersionUtils.checkVersionAuout(this);

                break;
            case R.id.rel_suggest_feedback:        // 意见反馈
                intent = new Intent(this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_user_proto:            // 用户协议
                intent = new Intent(AboutUSActivity.this, UserProtocolActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_user_proto1:            // 用户协议
                intent = new Intent(AboutUSActivity.this, UserProtocolActivity.class);
                intent.putExtra("isYSQX",true);
                startActivity(intent);
                break;

            case R.id.img_right_icon:    // 右上角。。。
                WXminiAppUtil.jumpToWXmini(this);

                break;
        }
    }


    // 检测更新
//		private void checkVersion(View v) {
//			try {
//				PackageManager pm = getPackageManager();
//				PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
//				int versionCode = pi.versionCode;
//				final String versionName = "v" + pi.versionName;
//				new SAsyncTask<Void, Void, HashMap<String, String>>(
//						AboutUSActivity.this, v, R.string.wait) {
//
//					@Override
//					protected HashMap<String, String> doInBackground(
//							FragmentActivity context, Void... params)
//							throws Exception {
//						// TODO Auto-generated method stub
//						HashMap<String, String> mapRet = ComModel2
//								.checkVersion(AboutUSActivity.this);
//						return mapRet;
//					}
//
//					protected boolean isHandleException() {
//						return true;
//					};
//
//					protected void onPostExecute(FragmentActivity context,
//							final java.util.HashMap<String, String> result, Exception e) {
//						if (null == e) {
//							if (StringUtils.isDownload(result.get("version_no"), versionName)) {
//								AlertDialog.Builder builder = new Builder(
//										AboutUSActivity.this);
//								View view = View.inflate(AboutUSActivity.this,
//										R.layout.is_force_update_dialog, null);
//								TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
//								tv_version.setText("" + result.get("version_no") + "更新了以下内容：");
//								TextView tv_content = (TextView) view
//										.findViewById(R.id.tv_content);
//								tv_content.setText(result.get("msg"));
//								Button btn_cancel = (Button) view
//										.findViewById(R.id.btn_cancel);
//								btn_cancel
//										.setOnClickListener(new OnClickListener() {
//
//											@Override
//											public void onClick(View arg0) {
//												// TODO Auto-generated method stub
//												dialog.dismiss();
//											}
//										});
//
//								Button btn_ok = (Button) view
//										.findViewById(R.id.btn_ok);
//								btn_ok.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View arg0) {
//										// TODO Auto-generated method stub
//										ApkDownloadManager UpgradeApk = new ApkDownloadManager(
//												AboutUSActivity.this);
//										UpgradeApk
//												.downloadUpgradeApk(YUrl.apkUrl +result.get("path"));
//										dialog.dismiss();
//									}
//								});
//
//
//
//								dialog = builder.create();
//								dialog.setView(view, 0, 0, 0, 0);
//								dialog.setCancelable(false);
//								dialog.show();
//							}else{
//								ToastUtil.showShortText(context, "当前已是最新版本");
//							}
//						}
//					};
//
//				}.execute((Void[]) null);
//			} catch (NameNotFoundException e) {
//				e.printStackTrace();
//			}
//		}

    // 得到更新的URL
//	private String getAuthUrl(String url) {
//		try {
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			nameValuePairs.add(new BasicNameValuePair("version",
//					ComModel.versionCode));
//			HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs);
//			String urlAuth = url + "?" + EntityUtils.toString(entity, "utf-8");
//			String authKey = AuthKeyTools.MD5(urlAuth, YConn.AUTH_Key);
//			nameValuePairs.add(new BasicNameValuePair("authKey", authKey));
//			entity = new UrlEncodedFormEntity(nameValuePairs);
//			LogYiFu.e("url", "" + url);
//			LogYiFu.e("urlEntity",
//					"" + url + "?" + EntityUtils.toString(entity, "utf-8"));
//			return url + "?" + EntityUtils.toString(entity, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return null;
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}

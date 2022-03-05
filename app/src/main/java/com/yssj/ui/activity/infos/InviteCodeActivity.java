package com.yssj.ui.activity.infos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.YUrl;
import com.yssj.activity.R;
import com.yssj.entity.BaseData;
import com.yssj.entity.NewUserInfo;
import com.yssj.network.HttpListener;
import com.yssj.network.YConn;
import com.yssj.ui.base.BasicActivity;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.StringUtils;
import com.yssj.utils.ToastUtil;
import com.yssj.utils.YCache;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteCodeActivity extends BasicActivity {


    @Bind(R.id.ed_yaoqingma)
    EditText edYaoqingma;
    @Bind(R.id.tv_my_yaoqingma)
    TextView tvMyYaoqingma;
    @Bind(R.id.ll_fuzhi)
    LinearLayout llFuzhi;
    @Bind(R.id.ll_top1)
    LinearLayout llTop1;
    @Bind(R.id.tv_my_yaoqingma2)
    TextView tvMyYaoqingma2;
    @Bind(R.id.ll_top2)
    LinearLayout llTop2;
    @Bind(R.id.tvTitle_base)
    TextView tvTitleBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_code_activity);
        ButterKnife.bind(this);
        tvTitleBase.setText("邀请码");
        initData();
    }

    private void initData() {

        YConn.httpPost(this, YUrl.GET_USER_INFO, new HashMap<String, String>(), new HttpListener<NewUserInfo>() {
            @Override
            public void onSuccess(NewUserInfo result) {

                if (result.getStatus().equals("1")) {
                    if (null != result.getUserinfo().getParent_id() && (result.getUserinfo().getParent_id()).length() > 0) {//有上级
                        llTop1.setVisibility(View.GONE);
                        llTop2.setVisibility(View.VISIBLE);
                    } else {
                        llTop1.setVisibility(View.VISIBLE);
                        llTop2.setVisibility(View.GONE);
                    }
                    tvMyYaoqingma.setText(result.getUserinfo().getUser_id() + "");
                    tvMyYaoqingma2.setText(result.getUserinfo().getUser_id() + "");
                } else {
                    llTop1.setVisibility(View.GONE);
                    llTop2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.img_back, R.id.tv_yaoqingma_wenhao, R.id.tv_sumit, R.id.ll_top2, R.id.ll_fuzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_yaoqingma_wenhao:
                DialogUtils.showYQMtishiDialog(this);
                break;
            case R.id.tv_sumit:
                submit();
                break;
            case R.id.ll_top2:
                StringUtils.copyString(this, tvMyYaoqingma.getText().toString().trim());
                ToastUtil.showShortText2("复制成功~");
                break;
            case R.id.ll_fuzhi:
                StringUtils.copyString(this, tvMyYaoqingma.getText().toString().trim());
                ToastUtil.showShortText2("复制成功~");
                break;
        }
    }

    private void submit() {
        String code = edYaoqingma.getText().toString().trim();
        if (code.length() <= 0) {
            ToastUtil.showShortText2("请输入邀请码~");
            return;
        }

        HashMap<String, String> pairsMap = new HashMap<>();
        pairsMap.put("userid", YCache.getCacheUser(this).getUser_id() + "");
        pairsMap.put("parent_id", code);

        YConn.httpPost(this, YUrl.SET_REFEREE, pairsMap, new HttpListener<BaseData>() {
            @Override
            public void onSuccess(BaseData result) {

                if (result.getStatus().equals("1")) {
                    ToastUtil.showShortText2("提交成功~");
                    initData();
                }
            }
            @Override
            public void onError() {
            }
        });
    }


}

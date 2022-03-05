package com.yssj.ui.activity.infos;

import com.yssj.activity.R;
import com.yssj.ui.activity.CommonActivity;
import com.yssj.ui.activity.SignDrawalLimitActivity;
import com.yssj.ui.activity.vip.MyVipListActivity;
import com.yssj.ui.base.BasicActivity;
import com.yssj.ui.fragment.orderinfo.MyBuyOrderFragment;
//import com.yssj.ui.fragment.orderinfo.MySellOrderFragment;
import com.yssj.utils.DP2SPUtil;
import com.yssj.utils.DialogUtils;
import com.yssj.utils.LogYiFu;
import com.yssj.utils.SharedPreferencesUtil;
import com.yssj.utils.ToastUtil;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.Timer;

public class StatusInfoActivity extends BasicActivity implements
        OnCheckedChangeListener {

    private int index = 0;
    private String key = "index";
    private View barView;
    private ImageView img_search;
    private LinearLayout img_back;
    private String strTitle = "我的买单";

    private MyBuyOrderFragment buyFragment;
    //	private MySellOrderFragment sellFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private RadioButton rb_sale_orders;
    private RadioButton rb_pay_orders;
    private LinearLayout stas;

    private RadioGroup rg_orders;

    public static boolean isNewUserFirstOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_info);
        index = getIntent().getIntExtra("index", 0);
//		aBar.hide();
        // index = getIntent().getIntExtra(key, 0);

        isNewUserFirstOrder = getIntent().getBooleanExtra("isNewUserFirstOrder", false);


        initView();
    }

    private void initView() {
        rg_orders = (RadioGroup) findViewById(R.id.rg_orders);
        rg_orders.setOnCheckedChangeListener(this);

        // rb_sale_orders = (RadioButton) findViewById(R.id.rb_sale_orders);
        // rb_sale_orders.setOnClickListener(this);
        // rb_pay_orders = (RadioButton) findViewById(R.id.rb_pay_orders);
        // rb_pay_orders.setOnClickListener(this);

        stas = (LinearLayout) findViewById(R.id.stas);
        stas.setBackgroundColor(Color.WHITE);
        img_back = (LinearLayout) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        img_search = (ImageView) findViewById(R.id.img_search);
        img_search.setVisibility(View.GONE);
        img_search.setOnClickListener(this);
        buyFragment = new MyBuyOrderFragment();
//		sellFragment = new MySellOrderFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        /*
         * if (strTitle.equals("我的买单")) { rb_pay_orders.setChecked(true);
		 * ft.add(R.id.container_f, buyFragment); } else {// 我的卖单
		 * rb_sale_orders.setChecked(true); ft.add(R.id.container_f,
		 * sellFragment); }
		 */
        ft.add(R.id.container_f, buyFragment);
        ft.commit();
//		if(getIntent().getBooleanExtra("isUserFirstOrder",false)){
//			DialogUtils.newUserFirstOrderDialog(this);
//		}

//
//        if (isNewUserFirstOrder) {
//            DialogUtils.showNewUserFirstOrderDialog(this);
//        }

    }

//    private void showNewUserFirstOrderDialog() {
//        final Dialog mDialog;
//
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        mDialog = new Dialog(this, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_order_list_new_user, null);
//        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//
//                showNewUserFirstOrderToSignDialog();
//
//
//            }
//        });
//
//
//        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(StatusInfoActivity.this, MyVipListActivity.class)
//                        .putExtra("isNewUserGuideVIP", true)
//
//                );
//            }
//        });
//        view.findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(StatusInfoActivity.this, MyVipListActivity.class)
//                        .putExtra("isGuideFHK", true)
//
//                );
//
//            }
//        });
//
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.addContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        ToastUtil.showDialog(mDialog);
//
//    }


//    private void showNewUserFirstOrderToSignDialog() {
//        final Dialog mDialog;
//
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        mDialog = new Dialog(this, R.style.invate_dialog_style);
//        View view = mInflater.inflate(R.layout.dialog_new_user_order_guide_sign, null);
//        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//
//            }
//        });
//
//
//        view.findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialog.dismiss();
//
//                SharedPreferencesUtil.saveStringData(StatusInfoActivity.this, "commonactivityfrom", "sign");
//                StatusInfoActivity.this.startActivity(new Intent(StatusInfoActivity.this, CommonActivity.class));
//                StatusInfoActivity.this.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);
//                mDialog.dismiss();
//            }
//        });
//
//
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(context, 275),
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        ToastUtil.showDialog(mDialog);
//
//
//    }


    @Override
    public void onClick(View v) {

        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.img_back:
//			setResult(1);修改bug7142 返回到购物车
                MyBuyOrderFragment.pos = -1;
                finish();
                break;
            case R.id.img_search:// 搜索
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyBuyOrderFragment.pos = -1;
    }

    @Override
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        ft = fm.beginTransaction();
        switch (arg1) {
            case R.id.rb_pay_orders:
                buyFragment = new MyBuyOrderFragment();
                ft.replace(R.id.container_f, buyFragment);
                ft.commitAllowingStateLoss();
                break;
            case R.id.rb_sale_orders:
//			sellFragment = new MySellOrderFragment();
//			ft.replace(R.id.container_f, sellFragment);
//			ft.commitAllowingStateLoss();
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);
        LogYiFu.i("TAG", "刷新数据");
        if (arg0 == 101) { // 待付款界面刷新
            fm.beginTransaction()
                    .replace(R.id.container_f, new MyBuyOrderFragment(1))
                    .commitAllowingStateLoss();
        } else if (arg0 == 102) {// 全部界面刷新
            LogYiFu.i("TAG", "全部界面刷新");
            fm.beginTransaction()
                    .replace(R.id.container_f, new MyBuyOrderFragment(0))
                    .commitAllowingStateLoss();
        } else if (arg0 == 103) { // 从详情里支付完成 刷新待付款
            fm.beginTransaction()
                    .replace(R.id.container_f, new MyBuyOrderFragment(1))
                    .commitAllowingStateLoss();
        } else if (arg0 == 104) { // 从详情支付完成，刷新全部界面
            fm.beginTransaction()
                    .replace(R.id.container_f, new MyBuyOrderFragment(0))
                    .commitAllowingStateLoss();
        }
    }

    public interface OnStatusListener {
        public void setOnStatus(int pos);
    }

}

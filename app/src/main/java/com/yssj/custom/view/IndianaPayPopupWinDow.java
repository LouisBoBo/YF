package com.yssj.custom.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.ui.activity.ShopImageActivity;
import com.yssj.ui.activity.shopdetails.ShopDetailsMoneyIndianaActivity;
import com.yssj.utils.ToastUtil;

import java.text.DecimalFormat;


/**
 * Created by Administrator on 2017/6/27.
 */

public class IndianaPayPopupWinDow extends PopupWindow implements View.OnClickListener {
    TextView indianaPayTvCancel;
    ImageView btnReduce;
    TextView tvGoodsNum;
    ImageView btnAdd;
    LinearLayout edCount;
    TextView indianaPayTvCountMoney;
    TextView indianaPayTvDeduction;
    TextView indianaPayTvNeed;
    TextView indianaPayTvPay;
    LinearLayout indianaLlBottom;
    private Context mContext;
    private int print_count = 1;
    private IndianaInterFace indianaInterFace;
    private int flag = 1;//=1代表1分钱夺宝，2代表正常参与
    private int takeCount = 1;//参与次数
    private double payCountMoney = 0;//需要支付的总金额
    private double shopMoney = 0;//商品价格
    private int branchCount = 1;//1分钱夺宝次数
    private int needPeopleCount = 1;//本次夺宝活动还需要的人数
    private int allPeopleCount=1;
    private boolean moneyFlag=false;
    public IndianaPayPopupWinDow(ShopDetailsMoneyIndianaActivity context, int flag,double shopMoney,int needPeopleCount,int allPeopleCount,boolean moneyFlag) {
        super(context);
        mContext = context;
        this.flag=flag;
        this.indianaInterFace = context;
        this.shopMoney=shopMoney;
        this.needPeopleCount=needPeopleCount;
        this.allPeopleCount=allPeopleCount;
        this.moneyFlag=moneyFlag;
        initView();
    }
    public IndianaPayPopupWinDow(ShopImageActivity context, int flag, double shopMoney, int needPeopleCount,int allPeopleCount,boolean moneyFlag) {
        super(context);
        mContext = context;
        this.flag=flag;
        this.indianaInterFace = context;
        this.shopMoney=shopMoney;
        this.needPeopleCount=needPeopleCount;
        this.allPeopleCount=allPeopleCount;
        this.moneyFlag=moneyFlag;
        initView();
    }

    public IndianaPayPopupWinDow(Context context, IndianaPayDialog f, int flag) {
        super(context);
        mContext = context;
        this.flag = flag;
        this.indianaInterFace = f;
        shopMoney = f.getShopMoney();
        initView();
    }

    private void initView() {

        View popupView = LayoutInflater.from(mContext).inflate(R.layout.popup_pay_view, null);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);


        setAnimationStyle(R.style.mypopwindow_anim_style);
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
        setContentView(popupView);
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.white)));

        indianaPayTvCancel = (TextView) popupView.findViewById(R.id.indiana_pay_tv_cancel);
        btnReduce = (ImageView) popupView.findViewById(R.id.indiana_btn_reduce);
        tvGoodsNum = (TextView) popupView.findViewById(R.id.indiana_tv_goods_num);
        btnAdd = (ImageView) popupView.findViewById(R.id.indiana_btn_add);
        edCount = (LinearLayout) popupView.findViewById(R.id.ed_count);
        indianaPayTvCountMoney = (TextView) popupView.findViewById(R.id.indiana_pay_tv_count_money);//应付总额
        indianaPayTvCountMoney.setText("¥" + new DecimalFormat("#0.00").format(shopMoney));
        indianaPayTvDeduction = (TextView) popupView.findViewById(R.id.indiana_pay_tv_deduction);//分享抵扣
        indianaPayTvDeduction.setText("-¥" + new DecimalFormat("#0.00").format(shopMoney - 0.01));
        indianaPayTvNeed = (TextView) popupView.findViewById(R.id.indiana_pay_tv_need);//还需支付
        indianaPayTvNeed.setText("¥0.01");
        indianaPayTvPay = (TextView) popupView.findViewById(R.id.indiana_pay_tv_pay);//确认按钮
        indianaLlBottom = (LinearLayout) popupView.findViewById(R.id.indiana_ll_bottom);//控制1分夺宝与2元夺宝的显示隐藏
        if (flag == 1) {//1分钱支付
            indianaLlBottom.setVisibility(View.VISIBLE);
        } else {//正常支付
            indianaLlBottom.setVisibility(View.GONE);
        }
        indianaPayTvCancel.setOnClickListener(this);
        btnReduce.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        indianaPayTvPay.setOnClickListener(this);
    }

    public void show() {
        showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.indiana_pay_tv_cancel://取消
//                float bgAlpha=1.0f;
//                WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
//                lp.alpha = bgAlpha; //0.0-1.0
//                ((Activity)mContext).getWindow().setAttributes(lp);
                dismiss();
//                if (indianaInterFace != null) {
//                    indianaInterFace.showDialog();
//                }
                break;
            case R.id.indiana_btn_reduce://输入减
                if (print_count == 1) {
                    return;
                } else {
                    print_count--;
                    if (print_count == 1) {
                        btnReduce.setImageResource(R.drawable.icon_jian_disable);
                    }
                }
                btnAdd.setImageResource(R.drawable.icon_jia);
                if (flag == 1) {//1分钱夺宝
                    indianaPayTvCountMoney.setText("¥" + new DecimalFormat("#0.00").format(shopMoney * print_count));
                    indianaPayTvDeduction.setText("-¥" + new DecimalFormat("#0.00").format((shopMoney - 0.01) * print_count));
                    indianaPayTvNeed.setText("¥" + new DecimalFormat("#0.00").format(0.01 * print_count));
                } else {
                    indianaPayTvCountMoney.setText("¥" + new DecimalFormat("#0.00").format(shopMoney * print_count));
                }
                tvGoodsNum.setText("" + print_count);
                break;
            case R.id.indiana_btn_add://输入加
                print_count++;
                if (print_count > 200) {
                    print_count = 200;
                    ToastUtil.showMyToast(mContext, "一次最多只能参与200次哦~",3000);
                    btnAdd.setImageResource(R.drawable.icon_jia_disable);
                    return;
                } else {
                    btnAdd.setImageResource(R.drawable.icon_jia);
                }


                if (flag == 1) {//1分钱夺宝
                    branchCount = ((IndianaPayDialog) indianaInterFace).getBranchCount();
                    if (print_count > branchCount) {
                        print_count = branchCount;
//                        ToastUtil.showShortText(mContext, "你只有" + branchCount + "次1分钱夺宝机会");
                        ToastUtil.showMyToast(mContext,"你的参与次数不足~",3000);
                        btnAdd.setImageResource(R.drawable.icon_jia_disable);
                        return;
                    }
                }
                if(indianaInterFace instanceof IndianaPayDialog) {
                    needPeopleCount = ((IndianaPayDialog) indianaInterFace).getNeedPeoPleCount();
                }
                if (print_count > needPeopleCount) {
                    print_count = needPeopleCount;
                    ToastUtil.showMyToast(mContext, "本次夺宝只剩余" + needPeopleCount + "次夺宝机会",3000);
                    btnAdd.setImageResource(R.drawable.icon_jia_disable);
                    return;
                }

                if (print_count > 1) {
                    btnReduce.setImageResource(R.drawable.icon_jian);
                }
                if (flag == 1) {//1分钱夺宝
                    indianaPayTvCountMoney.setText("¥" + new DecimalFormat("#0.00").format(shopMoney * print_count));
                    indianaPayTvDeduction.setText("-¥" + new DecimalFormat("#0.00").format((shopMoney - 0.01) * print_count));
                    indianaPayTvNeed.setText("¥" + new DecimalFormat("#0.00").format(0.01 * print_count));
                } else {
                    indianaPayTvCountMoney.setText("¥" + new DecimalFormat("#0.00").format(shopMoney * print_count));
                }
                tvGoodsNum.setText("" + print_count);
                if(moneyFlag&&5*(needPeopleCount-print_count)<allPeopleCount){
                    ToastUtil.showMyToast(mContext, "期满即开奖，参与记录可能被分散在两期内",3000);
                }
                break;
            case R.id.indiana_pay_tv_pay://确定

                if (indianaInterFace != null) {
                    takeCount = print_count;
                    if (flag == 1) {//1分钱夺宝
                        payCountMoney = takeCount * 0.1;
                    } else {//正常夺宝
                        payCountMoney = takeCount * shopMoney;
                    }
                    indianaInterFace.confirm(takeCount, payCountMoney, flag);
                }

                break;
        }
    }

    public interface IndianaInterFace {
        void confirm(int takeCount, double payCountMoney, int flag);

//        void showDialog();
    }

}

package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;
import com.yssj.ui.activity.MainMenuActivity;
import com.yssj.ui.activity.infos.MyWalletActivity;
import com.yssj.ui.activity.main.HotSaleActivity;
import com.yssj.utils.CommonUtils;
import com.yssj.utils.DP2SPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/****
 * 集赞提示共用
 *
 * @author Administrator
 *
 */
public class JiZanCommonDialog extends Dialog {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.bt_left)
    Button btLeft;
    @Bind(R.id.bt_right)
    Button btRight;

    private String jumpfrom;
    private Context mContext;


    public JiZanCommonDialog(Context context, int style, String jumpfrom) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.jumpfrom = jumpfrom;
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share_ewaijiangli);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        if (("jixujizandianji").equals(jumpfrom)) {//签到继续点赞的点击和用户在H5点击后自动弹出

            tvContent.setText("你今日的免费点赞次数已经用完，可以花费5衣豆为上一次点赞的好友继续点赞");
            tvTitle.setText("温馨提示");
            btLeft.setText("取消");
            btRight.setText("确定");
            //重置集赞
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        ModQingfeng.getResJizan(mContext);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        } else if (("yidoubuzu").equals(jumpfrom)) {//衣豆不足
            tvTitle.setText("衣豆不足提示");
            tvContent.setText("你当前衣豆不足，请及时补充哦~");
            btLeft.setText("取消");
            btRight.setText("确定");
        } else if (("dianzanrenwuwanchengtishi").equals(jumpfrom)) {//点赞任务完成提示
            tvTitle.setText("任务完成");
            tvContent.setText("恭喜你完成了好友点赞任务~\n" + "任务奖励已放到账户了哦~");
            btLeft.setText("知道了");
            btRight.setText("查看余额");
        }


    }


    @OnClick({R.id.bt_left, R.id.bt_right, R.id.icon_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_left://
                dismiss();
                break;
            case R.id.bt_right:
                //测试用
//                dismiss();
//                new JiZanCommonDialog(mContext, R.style.DialogStyle1, "jixujizandianji").show();

//                dianZan();


                if (jumpfrom.equals("jixujizandianji")) {//--花费5衣豆继续点赞
                    //调点赞接口

//                    dianZan();


                } else if (jumpfrom.equals("yidoubuzu")) {//衣豆不足
                    //赚衣豆
//                    Intent intent = new Intent(mContext, HotSaleActivity.class);
//                    intent.putExtra("id", "6");
//                    intent.putExtra("title", "热卖");
//                    mContext.startActivity(intent);
//                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    toGetYiDou();

                    dismiss();

                } else if (jumpfrom.equals("dianzanrenwuwanchengtishi")) {//此时是查看余额
                    Intent intent = new Intent(mContext, MyWalletActivity.class);
                    mContext.startActivity(intent);
                    ((FragmentActivity) mContext).overridePendingTransition(R.anim.slide_left_in, R.anim.slide_match);

                    dismiss();
                }


                break;
            case R.id.icon_close:
                dismiss();
                break;
        }

    }

//    private void dianZan() {
//
//        //查询衣豆是否够点赞
//        new SAsyncTask<Void, Void, String[]>((FragmentActivity) mContext, R.string.wait) {
//
//            @Override
//            protected String[] doInBackground(FragmentActivity context, Void... params) throws Exception {
//                return ComModel2.myWalletInfo(context);
//            }
//
//            @Override
//            protected boolean isHandleException() {
//                return true;
//            }
//
//            @Override
//            protected void onPostExecute(FragmentActivity context, String[] result, Exception e) {
//                super.onPostExecute(context, result, e);
//                if (null == e) {
//                    if (result != null && result.length > 0) {
//
//                        int usedYidou = 0;
//
//                        try {
//                            usedYidou = Integer.parseInt(result[4]);
//                        } catch (NumberFormatException e1) {
//                            e1.printStackTrace();
//                        }
//
//                        zan(usedYidou);
//
//
//                    }
//                }
//            }
//
//        }.execute();
//
//
//    }


    /**
     * 如何获取衣豆 提示弹框
     */
    private void toGetYiDou() {
        final Dialog dialog = new Dialog(mContext, R.style.invate_dialog_style);
        View view = View.inflate(mContext, R.layout.withdrawal_limit_yidou_exp_putong, null);
        dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
        view.setBackgroundResource(R.drawable.bg_singn_dialog_shape);
        view.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_yellow).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
//				Intent intent = new Intent(context, HotSaleActivity.class);
//				intent.putExtra("id", "6");
//				intent.putExtra("title", "热卖");
//				context.startActivity(intent);
                CommonUtils.finishActivity(MainMenuActivity.instances);

                Intent intent = new Intent(mContext, MainMenuActivity.class);
                intent.putExtra("toYf", "toYf");
                mContext.startActivity(intent);
            }
        });

        // // 创建自定义样式dialog
        dialog.setContentView(view, new LinearLayout.LayoutParams(DP2SPUtil.dp2px(mContext, 270),
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
    }


//    private void zan(int usedYidou) {
//
//        if (usedYidou < 5) {//衣豆不足5个
//            JiZanCommonDialog jizancommondialog = new JiZanCommonDialog(mContext, R.style.DialogStyle1, "yidoubuzu");
//            jizancommondialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//            jizancommondialog.show();
//
//            dismiss();
//
//
//        } else {
//            new SAsyncTask<Void, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                @Override
//                protected Boolean doInBackground(FragmentActivity context, Void... params) throws Exception {
//                    return ModQingfeng.getDianZan(mContext, false);
//                }
//
//                @Override
//                protected boolean isHandleException() {
//                    return true;
//                }
//
//                @Override
//                protected void onPostExecute(FragmentActivity context, Boolean result, Exception e) {
//                    super.onPostExecute(context, result, e);
//                    if (null == e) {
//                        if (result) {//点赞成功
//                            DianZanSucceedDiaolg dialog = new DianZanSucceedDiaolg(mContext, R.style.DialogStyle1);
//                            dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//                            dialog.show();
//                        }
//                    }
//                    dismiss();
//                }
//
//            }.execute();
//
//
//        }
//
//
//    }
}
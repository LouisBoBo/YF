package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yssj.activity.R;
import com.yssj.app.SAsyncTask;
import com.yssj.model.ComModel2;
import com.yssj.model.ModQingfeng;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/****
 * 集赞提示共用
 *
 * @author Administrator
 *
 */
public class DianZanSucceedDiaolg extends Dialog {


    @Bind(R.id.bt_right)
    Button btRight;
    @Bind(R.id.icon_close)
    ImageView iconClose;
    private Context mContext;


    public DianZanSucceedDiaolg(Context context, int style) {
        super(context, style);
        setCanceledOnTouchOutside(true);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dianzan_succeed);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }


    @OnClick({R.id.bt_right, R.id.icon_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_right:
                //继续点赞
//                dianZan();
                break;
            case R.id.icon_close:
                dismiss();
                break;
        }
    }


//    private void dianZan() {
//
//
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

//    private void zan(int usedYidou) {
//
//        if (usedYidou < 5) {//衣豆不足5个
//            dismiss();
//            JiZanCommonDialog dialog =  new JiZanCommonDialog(mContext, R.style.DialogStyle1, "yidoubuzu");
//            dialog.getWindow().setWindowAnimations(R.style.common_dialog_style);
//            dialog.show();
//
//        } else {
//            new SAsyncTask<Void, Void, Boolean>((FragmentActivity) mContext, R.string.wait) {
//
//                @Override
//                protected Boolean doInBackground(FragmentActivity context, Void... params) throws Exception {
//                    return ModQingfeng.getDianZan(mContext,false);
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
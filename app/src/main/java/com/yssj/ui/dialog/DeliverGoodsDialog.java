package com.yssj.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.ToastUtil;

public class DeliverGoodsDialog extends Dialog implements android.view.View.OnClickListener{

    private Context context;

    private int requestCode;

    private TextView deliver_num;
    private TextView deliver_com;

    public DeliverGoodsDialog.OnItemClick mOnItemClick;
    public interface OnItemClick {
        void click(String address ,String num ,String com);
    }
    public void setOnItemClick(DeliverGoodsDialog.OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public DeliverGoodsDialog(Context context) {
        super(context, R.style.my_invate_dialog);
        this.context=context;
        setContentView(R.layout.deliver_goods_dialog);
        this.getWindow().setWindowAnimations(R.style.my_dialog_anim_style);
        findViewById(R.id.deliver).setOnClickListener(this);

        deliver_com = findViewById(R.id.deliver_com);
        deliver_num = findViewById(R.id.deliver_num);
    }


    public void setRequestCode(int code){
        this.requestCode=code;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.deliver:
            {
                mOnItemClick.click("深圳市南山区",deliver_num.getText().toString(),deliver_com.getText().toString());
            }
            break;

            default:
                break;
        }
    }

}

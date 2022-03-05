package com.yssj.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;

/**
 * Created by Administrator on 2020/5/12.
 * 竖排textView
 */

public class VerticalTextView extends LinearLayout {
    private LinearLayout llLayout;
    private TextView textView;

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        color = getResources().getColor(R.color.white);
        View view = LayoutInflater.from(context).inflate(R.layout.vertical_text_view, this, true);
        llLayout = (LinearLayout) view.findViewById(R.id.llLayout);
        textView = (TextView) view.findViewById(R.id.textView);
    }

    private String text;
    private Context context;
    private int color;
    private int size = 20;

    public VerticalTextView(Context context) {
        super(context);
        this.context = context;
    }


    public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    public void setTextColor(int color) {
        this.color = color;
        textView.setTextColor(color);
    }

    public void setTextSize(int size) {
        this.size = size;
        textView.setTextSize(size);
    }

    /**
     * 设置背景
     */
    public void setBackground(int resId) {
        llLayout.setBackgroundResource(resId);
    }
}

package com.yssj.utils.sqlite;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lifeng on 2018/3/25.
 */

public class UiUtlis {


    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

}

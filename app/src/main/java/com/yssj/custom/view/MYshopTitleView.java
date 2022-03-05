package com.yssj.custom.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yssj.activity.R;
import com.yssj.utils.DP2SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MYshopTitleView extends LinearLayout implements OnClickListener {

    private LinearLayout mGroup;
    private LinearLayout mViewLine;
    private Context context;
    private boolean isIntimateTitle;//密友圈标题
    private boolean isMatchTitle;//标题


    public void setIntimateTitle(boolean isIntimateTitle) {
        this.isIntimateTitle = isIntimateTitle;
    }


    public void setMatchTitle(boolean isMatchTitle) {
        this.isMatchTitle = isMatchTitle;
    }


    private HorizontalScrollView myScrollView;

    private List<String> urlList;

    private List<HashMap<String, String>> list;

    private int index = 0;
    private int indexLine = 500;
    private OnCheckTitleLentener checkLintener;
    private OnCheckTitleLentener2 checkLintener2;

    public OnCheckTitleLentener getCheckLintener() {
        return checkLintener;
    }

    public void setCheckLintener(OnCheckTitleLentener checkLintener) {
        this.checkLintener = checkLintener;
    }

    public OnCheckTitleLentener2 getCheckLintener2() {
        return checkLintener2;
    }

    public void setCheckLintener2(OnCheckTitleLentener2 checkLintener2) {
        this.checkLintener2 = checkLintener2;
    }

    public MYshopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.my_horizontal_view_title, this);
        mGroup = (LinearLayout) findViewById(R.id.hor_group);
        mViewLine = (LinearLayout) findViewById(R.id.hor_line);
        myScrollView = (HorizontalScrollView) findViewById(R.id.my_h_scroll);
        urlList = new ArrayList<String>();
    }

    public void setPosition(int position) {
        onClick(mGroup.getChildAt(position));
    }

    public void setData(List<HashMap<String, String>> list) {
        this.list = list;
        mGroup.removeAllViews();
        int showLen = (list.size() > 5 && list.size() != 0) ? 5 : list.size();//屏幕能显示的条目数
        for (int i = 0; i < list.size(); i++) {
            TextView rb = new TextView(context);
            rb.setId(i);
            rb.setBackgroundColor(Color.WHITE);
//			getIcon(list.get(i).get("icon").split(",")[0], rb);
            if (isIntimateTitle || isMatchTitle) {
                rb.setTextSize(15);
            } else {
                rb.setTextSize(13);
            }

            if(i == 0){
                rb.setTextColor(Color.parseColor("#ff3f8b"));

            }else{
                rb.setTextColor(Color.parseColor("#3e3e3e"));

            }
            rb.setPadding(20, 0, 0, 0);


//            rb.setText(list.get(i).get("sort_name"));
            rb.setText(Html.fromHtml("<b>" + list.get(i).get("sort_name") + "</b>"));
            rb.setLayoutParams(new LayoutParams(context.getResources()
                    .getDisplayMetrics().widthPixels / showLen,
                    LayoutParams.MATCH_PARENT));
            rb.setGravity(Gravity.CENTER);
            rb.setOnClickListener(this);
            TextView view = new TextView(context);
            if (isIntimateTitle) {
                view.setLayoutParams(new LayoutParams(context.getResources()
                        .getDisplayMetrics().widthPixels * 3 / 14, DP2SPUtil.dp2px(context, 2)));
            } else {
                view.setLayoutParams(new LayoutParams(context.getResources()
                        .getDisplayMetrics().widthPixels / 6, DP2SPUtil.dp2px(context, 2)));
            }
            view.setTag(i + 500);
            View view1 = new View(context);
            view1.setBackgroundColor(Color.WHITE);
            if (isIntimateTitle) {
                view1.setLayoutParams(new LayoutParams((context.getResources()
                        .getDisplayMetrics().widthPixels / showLen - context
                        .getResources().getDisplayMetrics().widthPixels * 3 / 14) / 2, DP2SPUtil.dp2px(context, 2)));
            } else {
                view1.setLayoutParams(new LayoutParams((context.getResources()
                        .getDisplayMetrics().widthPixels / showLen - context
                        .getResources().getDisplayMetrics().widthPixels / 6) / 2, DP2SPUtil.dp2px(context, 2)));
            }
            View view2 = new View(context);
            view2.setBackgroundColor(Color.WHITE);

            if (isIntimateTitle) {
                view2.setLayoutParams(new LayoutParams((context.getResources()
                        .getDisplayMetrics().widthPixels / showLen - context
                        .getResources().getDisplayMetrics().widthPixels * 3 / 14) / 2, DP2SPUtil.dp2px(context, 2)));
            } else {
                view2.setLayoutParams(new LayoutParams((context.getResources()
                        .getDisplayMetrics().widthPixels / showLen - context
                        .getResources().getDisplayMetrics().widthPixels / 6) / 2, DP2SPUtil.dp2px(context, 2)));
            }
            mGroup.addView(rb);
            mViewLine.addView(view1);
            mViewLine.addView(view);
            mViewLine.addView(view2);
        }
        TextView rb = (TextView) mGroup.getChildAt(index);
        // TextView line=(TextView) mViewLine.getChildAt(500);
        TextView line = (TextView) findViewWithTag(indexLine);
        //下标线
        line.setBackgroundColor(Color.parseColor("#FF3F8B"));
        line.setVisibility(GONE);

//		getIcon(list.get(index).get("icon").split(",")[1], rb);
//        rb.setTextColor(Color.parseColor("#3e3e3e"));


    }

//	private class msgObject {
//		public String url;
//		public Bitmap bm;
//		public TextView rb;
//	}

//	private void getIcon(String url, TextView rb) {
//		// Drawable d =
//		// getResources().getDrawable(YJApplication.mapIcon.get(url));
//		// rb.setCompoundDrawablesWithIntrinsicBounds(d, null, null,null);
//	}


    public interface OnCheckTitleLentener {
        public void checkTitle(View v);
    }

    //密友圈点击title
    public interface OnCheckTitleLentener2 {
        public void checkTitle2(View v);
    }

    @Override
    public void onClick(View arg0) {
        if (index == arg0.getId()) {
            return;
        }
//		if(arg0.getId()==1&&isIntimateTitle&&!YJApplication.instance.isLoginSucess()){
//			checkLintener2.checkTitle2(arg0);
//			return;
//		}
        index = arg0.getId();
        indexLine = arg0.getId() + 500;
        checkLintener.checkTitle(arg0);
        TextView rb = (TextView) mGroup.getChildAt(arg0.getId());
//		getIcon(list.get(arg0.getId()).get("icon").split(",")[1], rb);
        rb.setTextColor(Color.parseColor("#FF3F8B"));

        TextView line = (TextView) findViewWithTag(indexLine);
        line.setBackgroundColor(Color.parseColor("#FF3F8B"));
        if (list.size() > 5) {
            myScrollView
                    .smoothScrollTo(
                            (arg0.getId() > 1 ? ((TextView) mGroup.getChildAt(arg0
                                    .getId())).getLeft() : 0)
                                    - ((TextView) mGroup.getChildAt(2)).getLeft(),
                            0);
        }

        for (int i = 0; i < mGroup.getChildCount(); i++) {
            if (i != arg0.getId()) {
                TextView rbs = (TextView) mGroup.getChildAt(i);
//				getIcon(((List<HashMap<String, String>>) list).get(i)
//						.get("icon").split(",")[0], rbs);
                rbs.setTextColor(Color.parseColor("#3e3e3e"));

                TextView lines = (TextView) findViewWithTag(i + 500);
                lines.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

    }
}

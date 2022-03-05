package com.yssj.custom.view.SignCalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


import com.yssj.YJApplication;
import com.yssj.activity.R;
import com.yssj.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author airsaid
 *         <p>
 *         自定义可多选日历 View.
 */
public class CalendarView extends View {

    public static int reQuestYear;//当前年份
    public static int reQuestMon;//当前月份
    public static List<Integer> signDateList;
    public static long clock_in_start_date;//起始打卡的毫秒值


    /**
     * 默认的日期格式化格式
     */
    private static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    /**
     * 默认文字颜色
     */
    private int mTextColor;
    /**
     * 选中后文字颜色
     */
    private int mSelectTextColor;
    /**
     * 默认文字大小
     */
    private float mTextSize;
    /**
     * 选中后文字大小
     */
    private float mSelectTextSize;
    /**
     * 默认天的背景
     */
    private Drawable mDayBackground;
    /**
     * 选中后天的背景
     */
    private Drawable mSelectDayBackground;
    /**
     * 日期格式化格式
     */
    private String mDateFormatPattern;
    /**
     * 字体
     */
    private Typeface mTypeface;
    /**
     * 日期状态是否能够改变
     */
    private boolean mIsChangeDateStatus;

    /**
     * 每列宽度
     */
    private int mColumnWidth;
    /**
     * 每行高度
     */
    private int mRowHeight;
    /**
     * 已选择日期数据
     */
    private List<String> mSelectDate;
    /**
     * 存储对应列行处的天
     */
    private int[][] mDays = new int[6][7];

    private OnDataClickListener mOnDataClickListener;
    private OnDateChangeListener mChangeListener;
    private SimpleDateFormat mDateFormat;
    private Calendar mSelectCalendar;
    private Calendar mCalendar;
    private Paint mPaint;
    private int mSlop;

    public static boolean requestEd;

    public interface OnDataClickListener {

        /**
         * 日期点击监听.
         *
         * @param view  与次监听器相关联的 View.
         * @param year  对应的年.
         * @param month 对应的月.
         * @param day   对应的日.
         */
        void onDataClick(@NonNull CalendarView view, int year, int month, int day);
    }

    public interface OnDateChangeListener {

        /**
         * 选中的天发生了改变监听回调, 改变有 2 种, 分别是选中和取消选中.
         *
         * @param view   与次监听器相关联的 View.
         * @param select true 表示是选中改变, false 是取消改变.
         * @param year   对应的年.
         * @param month  对应的月.
         * @param day    对应的日.
         */
        void onSelectedDayChange(@NonNull CalendarView view, boolean select, int year, int month, int day);
    }

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mSelectCalendar = Calendar.getInstance(Locale.CHINA);
        mCalendar = Calendar.getInstance(Locale.CHINA);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectDate = new ArrayList<>();
        setClickable(true);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);


        int textColor = a.getColor(R.styleable.CalendarView_cv_textColor, Color.BLACK);
        setTextColor(textColor);

        int selectTextColor = a.getColor(R.styleable.CalendarView_cv_can_sign_textColor, Color.WHITE);
        setSelectTextColor(selectTextColor);


        float textSize = a.getDimension(R.styleable.CalendarView_cv_textSize, sp2px(14));
        setTextSize(textSize);

        float selectTextSize = a.getDimension(R.styleable.CalendarView_cv_selectTextSize, sp2px(14));
        setSelectTextSize(selectTextSize);


        Drawable dayBackground = a.getDrawable(R.styleable.CalendarView_cv_not_sign_Background);


        setDayBackground(dayBackground);


        Drawable selectDayBackground = a.getDrawable(R.styleable.CalendarView_cv_sign_Background);
        setSelectDayBackground(selectDayBackground);


        String pattern = a.getString(R.styleable.CalendarView_cv_dateFormatPattern);
        setDateFormatPattern(pattern);

        boolean isChange = a.getBoolean(R.styleable.CalendarView_cv_isChangeDateStatus, false);
        setChangeDateStatus(isChange);

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mColumnWidth = getWidth() / 7;
        mRowHeight = getHeight() / 6;
        mPaint.setTextSize(mTextSize);

        int year = mCalendar.get(Calendar.YEAR);
        // 获取的月份要少一月, 所以这里 + 1
        int month = mCalendar.get(Calendar.MONTH) + 1;
        // 获取当月的天数
        int days = DateUtils.getMonthDays(year, month);
        // 获取当月第一天位于周几
        int week = DateUtils.getFirstDayWeek(year, month);
        // 绘制每天
        for (int day = 1; day <= days; day++) {
            // 获取天在行、列的位置
            int column = (day + week - 1) % 7;
            int row = (day + week - 1) / 7;
            // 存储对应天
            if (week == 7) {
                row = row - 1;
            }
            mDays[row][column] = day;
            String dayStr = String.valueOf(day);
            float textWidth = mPaint.measureText(dayStr);
            int x = (int) (mColumnWidth * column + (mColumnWidth - textWidth) / 2);
            int y = (int) (mRowHeight * row + mRowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2);


            String currentDate = year + "-" + (month < 10 ? "0" + month : month) + "-" + day + " 0-0-0";
            long chooseMill = 0;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//24小时制
            try {
                chooseMill = simpleDateFormat.parse(currentDate).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //确定文字

            if (DateUtil.FormatMillisecondForDay(chooseMill).equals(DateUtil.FormatMillisecondForDay(System.currentTimeMillis()))) {
                dayStr = "今";
            }
            if (DateUtil.FormatMillisecondForDay(clock_in_start_date).equals(DateUtil.FormatMillisecondForDay(chooseMill))) {
                dayStr = "始";
            }


            int textColor = mTextColor;
            Drawable background = null;


            //未登录的单独处理
            if (!YJApplication.instance.isLoginSucess()) {

                if (dayStr.equals("今")) {
                    textColor = mSelectTextColor;
                    background = mDayBackground;
                }
                //开始画
                if (background != null) {

                    if (dayStr.equals("今")) {
                        drawBackgroundLocal(dayStr, canvas, column, row, false);
                    } else {
                        drawBackground(canvas, background, column, row);
                    }
                }
                drawText(canvas, dayStr, textColor, mTextSize, x, y);
            } else {


                if (!requestEd) {
                    //这里区分未登录和登录了但是还没有请求数据的
                    textColor = mTextColor;


                } else {

                    //确定文字颜色
                    if (null == signDateList) {
                        if (dayStr.equals("今")) {
                            textColor = mSelectTextColor;
                        } else {
                            textColor = mTextColor;
                        }
                    } else {
                        if (chooseMill >= clock_in_start_date && chooseMill <= System.currentTimeMillis()) {//可以打卡的日期
                            textColor = mSelectTextColor;
                        } else {
                            textColor = mTextColor;
                        }
                    }
                    if (dayStr.equals("始")) {
                        textColor = mSelectTextColor;

                    }

                    boolean isClickEd = false;//当前位置有没有打卡

                    //确定背景
                    if (null == signDateList) {//未打过卡的
                        if (dayStr.equals("今")) {
                            background = mDayBackground;
                        }
                        isClickEd = false;
                    } else {

                        if (signDateList.indexOf(day) != -1) {
                            background = mSelectDayBackground;
                            isClickEd = true;

                        } else {

                            if (dayStr.equals("始")) {
                                background = mDayBackground;
                                isClickEd = true;

                            } else {
                                if (chooseMill > clock_in_start_date && chooseMill <= System.currentTimeMillis()) {//可以打卡的日期单未打
                                    background = mDayBackground;
                                    isClickEd = false;

                                }
                            }

                        }

                    }


//                    if (background != null) {
//                        drawBackground(canvas, background, column, row);
//
//                    }
//                    drawText(canvas, dayStr, textColor, mTextSize, x, y);
//                    if (dayStr.equals("1")) {
//                        dayStr = "始";
//                    }
//
//
//                    if (dayStr.equals("21") || dayStr.equals("20") || dayStr.equals("17") || dayStr.equals("18")) {
//                        background = mDayBackground;
//                        textColor = mSelectTextColor;
//
//                    }
//                    if (dayStr.equals("8") || dayStr.equals("9")) {
//                        background = mSelectDayBackground;
//                        textColor = mSelectTextColor;
//                    }

//                    if (dayStr.equals("始") || dayStr.equals("16") || dayStr.equals("14") || dayStr.equals("22") || dayStr.equals("22")
//                            || dayStr.equals("18")) {
//                        background = getResources().getDrawable(R.drawable.daka_red_shi);
//                    }

                    if (dayStr.equals("始") || dayStr.equals("今")) {
                        drawBackgroundLocal(dayStr, canvas, column, row, isClickEd);

                    } else {
                        //开始画
                        if (background != null) {
                            drawBackground(canvas, background, column, row);

                        }
                    }


                    drawText(canvas, dayStr, textColor, mTextSize, x, y);


                }

            }


        }
    }

    private void drawBackground(Canvas canvas, Drawable background, int column, int row) {
        if (background != null) {
            canvas.save();
            int dx = (mColumnWidth * column) + (mColumnWidth / 2) -
                    (background.getIntrinsicWidth() / 2);
            int dy = (mRowHeight * row) + (mRowHeight / 2) -
                    (background.getIntrinsicHeight() / 2);
            canvas.translate(dx, dy);
            background.draw(canvas);
            canvas.restore();
        }
    }


    private void drawBackgroundLocal(String dayStr, Canvas canvas, int column, int row, boolean isClickEd) {


        Drawable background;
        if (dayStr.equals("始")) {
            background = getResources().getDrawable(R.drawable.daka_red_shi);
        } else {
            if (isClickEd) {
                background = getResources().getDrawable(R.drawable.daka_red_jin);

            } else {
                background = getResources().getDrawable(R.drawable.daka_hui_jin);

            }

        }


        canvas.save();


        int dx = (mColumnWidth * column) + (mColumnWidth / 2) -
                (background.getIntrinsicWidth() / 2);
        int dy = (mRowHeight * row) + (mRowHeight / 2) -
                (background.getIntrinsicHeight() / 2);


        Bitmap bg;


        if (dayStr.equals("始")) {
            bg = BitmapFactory.decodeResource(getResources(),
                    R.drawable.daka_red_shi);
        } else {
            if (isClickEd) {
                bg = BitmapFactory.decodeResource(getResources(),
                        R.drawable.daka_red_jin);
            } else {
                bg = BitmapFactory.decodeResource(getResources(),
                        R.drawable.daka_hui_jin);
            }

        }


        // 建立画笔
        Paint photoPaint = new Paint();
        // 获取更清晰的图像采样，防抖动
        photoPaint.setDither(true);
        // 过滤一下，抗剧齿
        photoPaint.setFilterBitmap(true);
        canvas.drawBitmap(bg, dx, dy, photoPaint);// 将photo 缩放或则扩大到dst使用的填充区photoPaint
        canvas.save();
        canvas.restore();


//
//            canvas.translate(dx, dy);
//            background.draw(canvas);
//            canvas.restore();
    }

    private void drawText(Canvas canvas, String text, @ColorInt int color, float size, int x, int y) {
        if (text.equals("今") || text.equals("始")) {
            return;
        }
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        if (mTypeface != null) {
            mPaint.setTypeface(mTypeface);
        }




        canvas.drawText(text, x, y, mPaint);


//        if (text.equals("今") || text.equals("始")) {
//            canvas.drawText(text, x - 10, y, mPaint);
//
//        } else {
//        canvas.drawText(text, x, y, mPaint);
//
//        }

//        if (text.equals("今")) {
//            canvas.drawText(text, x - 10, y, mPaint);
//
//        } else {
//            canvas.drawText(text, x, y, mPaint);
//
//        }
    }

    private int mDownX = 0, mDownY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isClickable()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                int diffX = Math.abs(upX - mDownX);
                int diffY = Math.abs(upY - mDownY);
                if (diffX < mSlop && diffY < mSlop) {
                    int column = upX / mColumnWidth;
                    int row = upY / mRowHeight;
                    onClick(mDays[row][column]);
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    private void onClick(int day) {
        if (day < 1) {
            return;
        }

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        if (mOnDataClickListener != null) {
            mOnDataClickListener.onDataClick(this, year, month, day);
        }

        if (mIsChangeDateStatus) {
            // 如果选中的天已经选择则取消选中
            String date = getFormatDate(year, month, day);
            if (mSelectDate != null && mSelectDate.contains(date)) {
                mSelectDate.remove(date);
                if (mChangeListener != null) {
                    mChangeListener.onSelectedDayChange(this, false, year, month, day);
                }
            } else {
                if (mSelectDate == null) {
                    mSelectDate = new ArrayList<>();
                }
                mSelectDate.add(date);
                if (mChangeListener != null) {
                    mChangeListener.onSelectedDayChange(this, true, year, month, day);
                }
            }
            invalidate();
        }
    }

    /**
     * 设置选中的日期数据.
     *
     * @param days 如果没有设置则以默认的格式 {@link #DATE_FORMAT_PATTERN} 进行格式化.
     */
    public void setSelectDate(List<String> days) {
        this.mSelectDate = days;
        invalidate();
    }

    /**
     * 获取选中的日期数据.
     *
     * @return 日期数据.
     */
    public List<String> getSelectDate() {
        return mSelectDate;
    }

    /**
     * 切换到下一个月.
     */
    public void nextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        invalidate();
    }

    /**
     * 切换到上一个月.
     */
    public void lastMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        invalidate();
    }

    /**
     * 刷新日历
     */
    public void refreshMonth() {
        invalidate();
    }

    /**
     * 获取当前年份.
     *
     * @return year.
     */
    public int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份.
     *
     * @return month. (思考后, 决定这里直接按 Calendar 的 API 进行返回, 不进行 +1 处理)
     */
    public int getMonth() {
        return mCalendar.get(Calendar.MONTH);
    }

    /**
     * 设置当前显示的 Calendar 对象.
     *
     * @param calendar 对象.
     */
    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
        invalidate();
    }

    /**
     * 获取当前显示的 Calendar 对象.
     *
     * @return Calendar 对象.
     */
    public Calendar getCalendar() {
        return mCalendar;
    }

    /**
     * 设置文字颜色.
     *
     * @param textColor 文字颜色 {@link ColorInt}.
     */
    public void setTextColor(@ColorInt int textColor) {
        this.mTextColor = textColor;
    }

    /**
     * 设置选中后的的文字颜色.
     *
     * @param textColor 文字颜色 {@link ColorInt}.
     */
    public void setSelectTextColor(@ColorInt int textColor) {
        this.mSelectTextColor = textColor;
    }

    /**
     * 设置文字大小.
     *
     * @param textSize 文字大小 (sp).
     */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    /**
     * 设置选中后的的文字大小.
     *
     * @param textSize 文字大小 (sp).
     */
    public void setSelectTextSize(float textSize) {
        this.mSelectTextSize = textSize;
    }

    /**
     * 设置天的背景.
     *
     * @param background 背景 drawable.
     */
    public void setDayBackground(Drawable background) {
        if (background != null && mDayBackground != background) {
            this.mDayBackground = background;
            setCompoundDrawablesWithIntrinsicBounds(mDayBackground);
        }
    }

    /**
     * 设置选择后天的背景.
     *
     * @param background 背景 drawable.
     */
    public void setSelectDayBackground(Drawable background) {
        if (background != null && mSelectDayBackground != background) {
            this.mSelectDayBackground = background;
            setCompoundDrawablesWithIntrinsicBounds(mSelectDayBackground);
        }
    }

    /**
     * 设置日期格式化格式.
     *
     * @param pattern 格式化格式, 如: yyyy-MM-dd.
     */
    public void setDateFormatPattern(String pattern) {
        if (!TextUtils.isEmpty(pattern)) {
            this.mDateFormatPattern = pattern;
        } else {
            this.mDateFormatPattern = DATE_FORMAT_PATTERN;
        }
        this.mDateFormat = new SimpleDateFormat(mDateFormatPattern, Locale.CHINA);
    }

    /**
     * 获取日期格式化格式.
     *
     * @return 格式化格式.
     */
    public String getDateFormatPattern() {
        return mDateFormatPattern;
    }

    /**
     * 设置字体.
     *
     * @param typeface {@link Typeface}.
     */
    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
        invalidate();
    }

    /**
     * 获取 {@link Paint} 对象.
     *
     * @return {@link Paint}.
     */
    public Paint getPaint() {
        return mPaint;
    }

    /**
     * 设置点击是否能够改变日期状态 (默认或选中状态).
     * <p>
     * 默认是 false, 即点击只会响应点击事件 {@link OnDataClickListener}, 日期状态而不会做出任何改变.
     *
     * @param isChanged 是否能改变日期状态.
     */
    public void setChangeDateStatus(boolean isChanged) {
        this.mIsChangeDateStatus = isChanged;
    }

    /**
     * 获取是否能改变日期状态.
     *
     * @return {@link #mIsChangeDateStatus}.
     */
    public boolean isChangeDateStatus() {
        return mIsChangeDateStatus;
    }

    /**
     * 设置日期点击监听.
     *
     * @param listener 被通知的监听器.
     */
    public void setOnDataClickListener(OnDataClickListener listener) {
        this.mOnDataClickListener = listener;
    }

    /**
     * 设置选中日期改变监听器.
     *
     * @param listener 被通知的监听器.
     */
    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.mChangeListener = listener;
    }

    /**
     * 根据指定的年月日按当前日历的格式格式化后返回.
     *
     * @param year  年.
     * @param month 月.
     * @param day   日.
     * @return 格式化后的日期.
     */
    public String getFormatDate(int year, int month, int day) {
        mSelectCalendar.set(year, month, day);
        return mDateFormat.format(mSelectCalendar.getTime());
    }

    private void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getContext().getResources().getDisplayMetrics());
    }
}

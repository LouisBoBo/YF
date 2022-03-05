package com.yssj.custom.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.yssj.activity.R;
import com.yssj.custom.view.AddAndSubView.OnTextChangeListener;
import com.yssj.utils.ToastUtil;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineEditText extends EditText {
	private Paint mPaint;

	/**
	 * @param context
	 * @param attrs
	 */
	public LineEditText(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        mPaint = new Paint();  
          
        mPaint.setStyle(Paint.Style.STROKE);  
        mPaint.setColor(getResources().getColor(R.color.text1_color)); 
        mPaint.setStrokeWidth(1.4f);
        
        
        addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				String editable = getText().toString();  
		          String str = stringFilter(editable.toString());
		          if(!editable.equals(str)){
		        	  	setText(str);
		              //设置新的光标所在位置  
		        	  	ToastUtil.showShortText(getContext(), "不能输入汉字或者特殊字符");
		              setSelection(str.length());
		          }
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
       
		
	}
        
        
        
        
        

	public static String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母、数字和汉字
		String regEx = "[\u4E00-\u9FA5]"; // "[\u4e00-\u9fa5]"
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
		// R.drawable.login_register_bg), new Matrix(), mPaint);
		// 画底线
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth(),
				this.getHeight() - 1, mPaint);
	}
}

package com.yssj.custom.view;

import com.yssj.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;
import android.view.SurfaceView;
import android.view.TextureView;

public class LuckyPanView extends TextureView implements TextureView.SurfaceTextureListener, Runnable{

//	private SurfaceHolder mHolder;
	/**
	 * 与SurfaceHolder绑定的Canvas
	 */
	private Canvas mCanvas;
	/**
	 * 用于绘制的线程
	 */
	private Thread t;
	/**
	 * 线程的控制开关
	 */
	private boolean isRunning;
	
	private boolean isMad;
	

	public boolean isMad() {
		return isMad;
	}

	public void setMad(boolean isMad) {
		this.isMad = isMad;
	}
	private String mStrsBottom ="提现红包";
	private boolean isBalanceLottery;

	public void setmStrsBottom(String mStrsBottom) {
		this.mStrsBottom = mStrsBottom;
//		this.mStrs[7] = "1-5元";
	}

	public void setBalanceLottery(boolean isBalanceLottery) {
		this.isBalanceLottery = isBalanceLottery;
	}

	/**
	 * 抽奖的文字
	 */
	private String[] mStrs = new String[] {"谢谢参与!","1-50元", "20-50元", "10-20元", "5-10元",
			"3-5元", "1-3元","0.01-1元"};
	/**
	 * 每个盘块的颜色
	 */
	private int[] mColors = new int[] { 0XFFFFCC3F, 0XFFFEB415, 0XFFFFCC3F, 0XFFFEB415, 0XFFFFCC3F, 0XFFFEB415, 0XFFFFCC3F,0XFFFEB415};
	/**
	 * 与文字对应的图片
	 */
//	private int[] mImgs = new int[] { R.drawable.chat_face, R.drawable.chat_face,
//			R.drawable.chat_face, R.drawable.chat_face, R.drawable.chat_face,
//			R.drawable.chat_face ,R.drawable.chat_face,R.drawable.chat_face};

	/**
	 * 与文字对应图片的bitmap数组
	 */
//	private Bitmap[] mImgsBitmap;
	/**
	 * 盘块的个数
	 */
	private int mItemCount = 8;

	/**
	 * 绘制盘块的范围
	 */
	private RectF mRange = new RectF();
	/**
	 * 圆的直径
	 */
	private int mRadius;
	/**
	 * 绘制盘快的画笔
	 */
	private Paint mArcPaint;

	/**
	 * 绘制文字的画笔
	 */
	private Paint mTextPaint;

	/**
	 * 滚动的速度
	 */
	private double mSpeed;
	private volatile float mStartAngle =-(360/16+3*360/8);
	/**
	 * 是否点击了停止
	 */
	private boolean isShouldEnd;

	/**
	 * 控件的中心位置
	 */
	private int mCenter;
	/**
	 * 控件的padding，这里我们认为4个padding的值一致，以paddingleft为标准
	 */
	private int mPadding;

	/**
	 * 背景图的bitmap
	 */
	public Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),
			R.drawable.zhuanpan);
	/**
	 * 文字的大小
	 */
	private float mTextSize = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());

	public LuckyPanView(Context context){
		this(context, null);
	}

	public LuckyPanView(Context context, AttributeSet attrs){
		super(context, attrs);

//		mHolder = getHolder();
//		mHolder.addCallback(this);
		this.setSurfaceTextureListener(this);  

//		 setZOrderOnTop(true);// 设置画布 背景透明
//		 setZOrderMediaOverlay(true);
//		 mHolder.setFormat(PixelFormat.TRANSLUCENT);

		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setKeepScreenOn(true);

	}

	/**
	 * 设置控件为正方形
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
		// 获取圆形的直径
		mRadius = width - getPaddingLeft() - getPaddingRight();
		// padding值
		mPadding = getPaddingLeft();
		// 中心点
		mCenter = width / 2;
		setMeasuredDimension(width, width);
	}

//	@Override
	public void surfaceCreated(SurfaceHolder holder){
		try {
			mCanvas=this.lockCanvas();
			if(mCanvas!=null){
				if(isBalanceLottery){
					mCanvas.drawColor(0XFFA377FF);
				}else
				if(isMad){
					mCanvas.drawColor(0XFF8813E2);
				}else{
					mCanvas.drawColor(0XFFA377FF);	
				}
//				drawBg();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(mCanvas!=null){
				this.unlockCanvasAndPost(mCanvas);
			}
		}
		// 初始化绘制圆弧的画笔
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setDither(true);
		// 初始化绘制文字的画笔
		mTextPaint = new Paint();
		mTextPaint.setColor(0xFFE44025);
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(mTextSize);
		// 圆弧的绘制范围
		mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mRadius
				+ getPaddingLeft(), mRadius + getPaddingLeft());

		// 初始化图片
//		mImgsBitmap = new Bitmap[mItemCount];
//		for (int i = 0; i < mItemCount; i++)
//		{
//			mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(),
//					mImgs[i]);
//		}

		// 开启线程
		isRunning = true;
		t = new Thread(this);
		t.start();
	}

//	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height){
		// TODO Auto-generated method stub

	}

//	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		// 通知关闭线程
		isRunning = false;
	}

	@Override
	public void run(){
		// 不断的进行draw
		while (isRunning){
			long start = System.currentTimeMillis();
			draw();
			long end = System.currentTimeMillis();
			try
			{
				if (end - start < 50)
				{
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}

	}

	private void draw(){
		try
		{
			// 获得canvas
//			mCanvas = mHolder.lockCanvas();
			mCanvas=this.lockCanvas();
			mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG)); 
			if (mCanvas != null)
			{
				// 绘制背景图
				drawBg();

				/**
				 * 绘制每个块块，每个块块上的文本，每个块块上的图片
				 */
				float tmpAngle = mStartAngle;
				float sweepAngle = (float) (360 / mItemCount);
				for (int i = 0; i < mItemCount; i++)
				{
					// 绘制快快
					mArcPaint.setColor(mColors[i]);
//					mArcPaint.setStyle(Style.STROKE);
					mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true,
							mArcPaint);
					// 绘制文本
					drawText(tmpAngle, sweepAngle, mStrs[i]);
					// 绘制Icon
					drawIcon(tmpAngle, sweepAngle,i);

					tmpAngle += sweepAngle;
				}

				// 如果mSpeed不等于0，则相当于在滚动
				mStartAngle += mSpeed;

				// 点击停止时，设置mSpeed为递减，为0值转盘停止
				if (isShouldEnd)
				{
					mSpeed -= 0.5;
				}
				if (mSpeed <= 0)
				{
					mSpeed = 0;
					isShouldEnd = false;
				}
				if(mSpeed <0.5&&isShouldEnd){
					this.post(new Runnable() {
						
						@Override
						public void run() {
							//TODO 转盘停下来之后的操作 
//							Toast.makeText(getContext(), "QQQQ", Toast.LENGTH_LONG).show();
							mOnStopListening.stopListening(luckyIndex);
							
						}
					});
				}
				// 根据当前旋转的mStartAngle计算当前滚动到的区域
				calInExactArea(mStartAngle);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (mCanvas != null)
//				mHolder.unlockCanvasAndPost(mCanvas);
				this.unlockCanvasAndPost(mCanvas);
		}

	}

	/**
	 * 根据当前旋转的mStartAngle计算当前滚动到的区域 绘制背景，不重要，完全为了美观
	 */
	private void drawBg(){
//		mCanvas.drawColor(0XFFD980FE);
		if(isBalanceLottery){
			mCanvas.drawColor(0XFFA377FF);
		}else
		if(isMad){
			mCanvas.drawColor(0XFF8813E2);
		}else{
			mCanvas.drawColor(0XFFA377FF);	
		}
		mCanvas.drawBitmap(mBgBitmap, null, new Rect(0 / 2,
				0 / 2, getMeasuredWidth() - 0 / 2,
				getMeasuredWidth() - 0 / 2), null);
	}

	/**
	 * 根据当前旋转的mStartAngle计算当前滚动到的区域
	 * 
	 * @param startAngle
	 */
	public void calInExactArea(float startAngle){
		// 让指针从水平向右开始计算
		float rotate = startAngle + 90;
		rotate %= 360.0;
		for (int i = 0; i < mItemCount; i++)
		{
			// 每个的中奖范围
			float from = 360 - (i + 1) * (360 / mItemCount);
			float to = from + 360 - (i) * (360 / mItemCount);

			if ((rotate > from) && (rotate < to))
			{
				Log.d("TAG", mStrs[i]);
				return;
			}
		}
	}

	/**
	 * 绘制图片
	 * 
	 * @param startAngle
	 * @param sweepAngle
	 * @param i
	 */
	private void drawIcon(float startAngle,float sweepAngle,int i){
		// 设置图片的宽度为直径的1/16
		int imgWidth = mRadius / 16;

		float angle = (float) ((360 / 8 / 2  + startAngle) * (Math.PI / 180));

		int x = (int) (mCenter + mRadius*2/2/3  * Math.cos(angle));
		int y = (int) (mCenter + mRadius*2/2/3  * Math.sin(angle));

		// 确定绘制图片的位置
		Rect rect = new Rect(x - imgWidth / 2, y - imgWidth / 2, x + imgWidth
				/ 2, y + imgWidth / 2);

		if(i==0){
//			mCanvas.drawBitmap(mImgsBitmap[i], null, rect, null);
//			drawText(startAngle, sweepAngle, "");
		}else{

		    if(i == 1){
                mStrsBottom = "余  额";
            }else{
                mStrsBottom = "提  现  现  金";

            }

			drawText(startAngle, sweepAngle, mStrsBottom);
		}

	}

	/**
	 * 绘制文本
	 * 
	 * @param startAngle
	 * @param sweepAngle
	 * @param string
	 */
	private void drawText(float startAngle, float sweepAngle, String string){
		Path path = new Path();
		path.addArc(mRange, startAngle, sweepAngle);
		float textWidth = mTextPaint.measureText(string);
		// 利用水平偏移让文字居中
		float hOffset = (float) (mRadius * Math.PI / mItemCount / 2 - textWidth / 2);// 水平偏移
		float vOffset = mRadius / 2 /5;// 垂直偏移
		if(mStrsBottom.equals(string)){
			vOffset = (mRadius /2)*5/14;// 垂直偏移
		}
		mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
	}

	/**
	 * 点击开始旋转
	 * 
	 * @param luckyIndex
	 */
	private int luckyIndex;
	public void luckyStart(int luckyIndex){
		mStartAngle = 0;
		this.luckyIndex = luckyIndex;
		// 每项角度大小
		float angle = (float) (360 / mItemCount);
		// 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
		float from = 270 - (luckyIndex + 1) * angle;
		float to = from + angle;
		// 停下来时旋转的距离
		float targetFrom = 6 * 360 + from;
		/**
		 * <pre>
		 *  (v1 + 0) * (v1+1) / 2 = target ;
		 *  v1*v1 + v1 - 2target = 0 ;
		 *  v1=-1+(1*1 + 8 *1 * target)/2;
		 * </pre>
		 * mSpeed -= 0.5; 从-=1改为-=0.5 转盘停下来速度变慢了 这里公式V1 和 v2 做相应的调整
		 */
		float v1 = (float) (Math.sqrt(1 * 1 + 16* 1 * targetFrom) - 1) / 4;
		float targetTo = 6 * 360 + to;
		float v2 = (float) (Math.sqrt(1 * 1 + 16 * 1 * targetTo) - 1) / 4;

		mSpeed = (float) (v1 + 
			   (Math.random()*(0.95-0.05)+0.05)* (v2 - v1));//防止压线 随机数 取值从0.05到0.95
		isShouldEnd = false;
	}

	public void luckyEnd(){
		mStartAngle = 0;
		isShouldEnd = true;
	}

	public boolean isStart(){
		return mSpeed != 0;
	}

	public boolean isShouldEnd(){
		return isShouldEnd;
	}
	
	//转盘停止后的监听
	 public interface OnStopListening{
		 /**
		  * 
		  * @param luckyIndex 中奖的位置
		  */
		  void stopListening(int luckyIndex);
	 }
		      
      private OnStopListening mOnStopListening;
      //转盘停止后的监听
      public void setOnStopListening(OnStopListening lister){
    	  mOnStopListening=lister;
      }

	/* (non-Javadoc)
	 * @see android.view.TextureView.SurfaceTextureListener#onSurfaceTextureAvailable(android.graphics.SurfaceTexture, int, int)
	 */
	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		surfaceCreated(null);
		
	}

	/* (non-Javadoc)
	 * @see android.view.TextureView.SurfaceTextureListener#onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture, int, int)
	 */
	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.view.TextureView.SurfaceTextureListener#onSurfaceTextureDestroyed(android.graphics.SurfaceTexture)
	 */
	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		surfaceDestroyed(null);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.TextureView.SurfaceTextureListener#onSurfaceTextureUpdated(android.graphics.SurfaceTexture)
	 */
	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		
	}

}

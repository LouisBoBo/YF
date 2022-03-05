package com.yssj.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yssj.activity.R;
import com.yssj.entity.MatchShop.CollocationShop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class QRCreateUtil {

	// 生成QR图不加文字
	public static Bitmap createQrImage(String text, int QR_WIDTH, int QR_HEIGHT) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			}

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

//			System.out.println("w:" + martix.getWidth() + "h:"
//					+ martix.getHeight());

			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 0);
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// qr_image.setImageBitmap(bitmap);

			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 生成QR图加文字
	public static Bitmap createImage(String text, int QR_WIDTH, int QR_HEIGHT,
			String price, Context context) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			}

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

//			System.out.println("w:" + martix.getWidth() + "h:"
//					+ martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// qr_image.setImageBitmap(bitmap);

			return drawNewBitmap(price, context, bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 生成QR图加文字
	public static Bitmap createZeroImage(String text, int QR_WIDTH,
			int QR_HEIGHT, String price, Context context) {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();

			if (text == null || "".equals(text) || text.length() < 1) {
				return null;
			}

			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			System.out.println("w:" + martix.getWidth() + "h:"
					+ martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// qr_image.setImageBitmap(bitmap);

			return drawZeroBitmap(price, context, bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Bitmap drawZeroBitmap(String price, Context context, Bitmap bm) {

		double itemPrice = Double.valueOf(price);
		String textPrice = "";
		if (itemPrice == 0) {
			textPrice = "你没看错，真的0元哦~";
		} else {
			textPrice = "天了个噜，只要" + itemPrice + "元哦~";
		}
		int width = bm.getWidth();
		int hight = bm.getHeight();
//		System.out.println("宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bm.getWidth(), bm.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bm, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(35.0f);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.parseColor("#000000"));// 采用的颜色

		float textPriceWidth = textPaint.measureText(textPrice);// 计算文字所占宽度
		float marginLeft = (width - textPriceWidth) / 2;// 设置左边距离
		canvas.drawText(textPrice, marginLeft, 90, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制

		String textNotice = "长按二维码识别购买";
		float textNoticeWidth = textPaint.measureText(textNotice);// 计算文字所占宽度
		float noticeMarginLeft = (width - textNoticeWidth) / 2;

		canvas.drawText(textNotice, noticeMarginLeft, hight - 60, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		canvas.save();
		canvas.restore();
		// imageView.setImageBitmap(icon);
		// saveMyBitmap(icon, "test");
		return icon;

	}

	/**
	 * 在图片上面写字
	 * 
	 */
	public static Bitmap drawNewBitmap(String price, Context context, Bitmap bm) {

		int width = bm.getWidth();
		int hight = bm.getHeight();
//		System.out.println("宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bm.getWidth(), bm.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bm, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(45.0f);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.parseColor("#000000"));// 采用的颜色

		String textPrice = "美衣只要" + price + "元哦";
		float textPriceWidth = textPaint.measureText(textPrice);// 计算文字所占宽度
		float marginLeft = (width - textPriceWidth) / 2;// 设置左边距离
		canvas.drawText(textPrice, marginLeft, 90, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制

		String textNotice = "长按二维码识别购买";
		float textNoticeWidth = textPaint.measureText(textNotice);// 计算文字所占宽度
		float noticeMarginLeft = (width - textNoticeWidth) / 2;

		canvas.drawText(textNotice, noticeMarginLeft, hight - 60, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		canvas.save();
		canvas.restore();
		// imageView.setImageBitmap(icon);
		// saveMyBitmap(icon, "test");
		return icon;
	}

	/**
	 * 在图片上面加图
	 * 
	 */
	public static Bitmap drawNewBitmap1(Context context, Bitmap bmBg,
			Bitmap qrBm, String price,String type) {

		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		LogYiFu.e("QR", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint

		/** 画半透明背景 **/
		Paint paintAlflaBg = new Paint();
		paintAlflaBg.setAlpha(225);
		paintAlflaBg.setColor(Color.parseColor("#e1ffffff"));

		Rect ret = new Rect(0, hight - 200, width, hight);
		canvas.drawRect(ret, paintAlflaBg);
		// canvas.drawBitmap(qrBm, src, dst, photoPaint);// 将photo 缩放或则扩大到

		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, 20, hight - 180, photoPaint);

		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setTextSize(52.0f);// 字体大小
		textPaint.setAntiAlias(true);
		String text;
		
		if(type.equals("1")){//签到现金分享单宫图
//			price = new DecimalFormat("0").format(Double.valueOf(price));
//			text = "喜欢我吗？";
			text = "你穿肯定很好看";
		}else if(Double.valueOf(price) == 0)
			text = "你没看错，真的0元哦~";
		else if(type.equals("2")){//签到包邮购买分享单宫图
			price = new DecimalFormat("0").format(Double.valueOf(price));
			text = "包邮专享更划算";
		}else if(type.equals("3")){//签到夺宝购买分享单宫图
			price = new DecimalFormat("0").format(Double.valueOf(price));
			text = "不中奖就退款!!";
		}else{
//			price = new DecimalFormat("0").format(Double.valueOf(price));
//			text = "天了个噜，只要"+price+"元哦~";
			text = "你穿肯定很好看";
		}
		textPaint.setColor(Color.parseColor("#3E3E3E"));
		canvas.drawText(text, 200, hight - 117, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		String text1;
		if(type.equals("3")){
			text1 = "长按图片识别二维码了解";
		}else{
			text1 = "长按图片识别二维码购买";
		}
		textPaint.setTextSize(37.0f);// 改变字体大小 写第二排字
		textPaint.setColor(Color.parseColor("#7D7D7D"));
		canvas.drawText(text1, 200, hight - 48, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制

		/*** 画价格背景 */
//		Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paintCircle.setColor(Color.parseColor("#a0797a7c"));
//		paintCircle.setAntiAlias(true);
//		canvas.drawCircle(width - 135, 135, 115, paintCircle);
		Paint paintPriceBg = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintPriceBg.setColor(Color.parseColor("#ff3f8b"));
		paintPriceBg.setAntiAlias(true);
//		canvas.drawCircle(width - 135, 135, 115, paintPriceBg);
		canvas.drawRect(width-260, hight - 200, width, hight,paintPriceBg);

		/*** 画价格字段 */
//		String text2 = "￥";
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置文字画笔
		textPaint.setTextSize(56.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#ffffff"));
//		float text2Width = textPaint.measureText(text2);// 计算文字所占宽度 t
//		text2Width = text2Width / 2;
//		canvas.drawText(text2, width - 135 - text2Width, 115, textPaint);
		

		price ="¥" +new DecimalFormat("0.0").format(Double.valueOf(price));
		float textPriceWidth = textPaint.measureText(price);
		textPriceWidth = textPriceWidth / 2;
		
//		canvas.drawText(price, width - 135 - textPriceWidth, 180, textPaint);
		canvas.drawText(price, width - 130 - textPriceWidth, hight-83, textPaint);
		/*
		 * Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG |
		 * Paint.DEV_KERN_TEXT_FLAG);// 设置画笔 textPaint.setTextSize(45.0f);//
		 * 字体大小 textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		 * textPaint.setColor(Color.parseColor("#000000"));// 采用的颜色
		 * 
		 * String textPrice = "美衣只要" + price + "元哦"; float textPriceWidth =
		 * textPaint.measureText(textPrice);// 计算文字所占宽度 float marginLeft =
		 * (width - textPriceWidth) / 2;// 设置左边距离 canvas.drawText(textPrice,
		 * marginLeft, 90, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		 * 
		 * String textNotice = "长按二维码识别购买"; float textNoticeWidth =
		 * textPaint.measureText(textNotice);// 计算文字所占宽度 float noticeMarginLeft
		 * = (width - textNoticeWidth) / 2;
		 * 
		 * canvas.drawText(textNotice, noticeMarginLeft, hight - 60,
		 * textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		 */
		canvas.save();
		canvas.restore();
		// imageView.setImageBitmap(icon);
		// saveMyBitmap(icon, "test");
		return icon;
	}
	
	
	/**
	 * 在图片上面加图 搭配购单宫图 添加搭配购价格牌
	 * 900*900图片单宫图
	 */
	public static Bitmap drawNewBitmapMatch2(Context context, Bitmap bmBg,
			Bitmap qrBm, String title,List<CollocationShop> collocationShopsList) {
		
		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		LogYiFu.e("QR", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上
		
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		
		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		/*** */
		/** 画价格牌 */
		Bitmap bmN1 = BitmapFactory.decodeResource(context.getResources(),  
                R.drawable.bg_label);  
		Bitmap bmN2 = BitmapFactory.decodeResource(context.getResources(),  
				R.drawable.bg_label_2);  
		Bitmap bmpO = BitmapFactory.decodeResource(context.getResources(),  
				R.drawable.pricetag);  
		 int bmpOwidth = bmpO.getWidth();    
		 int bmpOheight = bmpO.getHeight();    
		// 计算缩放比例    
		 float scaleWidth = ((float) 75) / bmpOwidth;    
		 float scaleHeight = ((float) 100) / bmpOheight;    
		 // 取得想要缩放的matrix参数    
		 Matrix matrix = new Matrix();    
		 matrix.postScale(scaleWidth, scaleHeight);  
		Bitmap bmp = Bitmap.createBitmap(bmpO, 0, 0, bmpOwidth, bmpOheight,matrix,true);    
		Bitmap bmp2 = BitmapFactory.decodeResource(context.getResources(),  
				R.drawable.red_point);  
        NinePatch np1 = new NinePatch(bmN1, bmN1.getNinePatchChunk(), null); 
        NinePatch np2 = new NinePatch(bmN2, bmN2.getNinePatchChunk(), null); 
        
        Paint textPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
        textPaint1.setAntiAlias(true);
        Paint photoPaint1 = new Paint(); // 建立画笔
		photoPaint1.setDither(true); // 获取跟清晰的图像采样
		for (int i = 0; i < collocationShopsList.size(); i++) {
			if(i==0){
				String shopName1 = collocationShopsList.get(0).getShop_name();
				if(shopName1.length()>7){//控制文字长度最多七个字
					shopName1 = shopName1.substring(0,7);
				}
				textPaint1.setTextSize(27f);
				textPaint1.setColor(Color.parseColor("#ffffffff"));
				float shopNameWidth = textPaint1.measureText(shopName1);
				
				double shopX = collocationShopsList.get(0).getShop_x();
				double shopY = collocationShopsList.get(0).getShop_y();
				if(shopX<=0||shopY<=0){
					continue;
				}
				int option_flag = collocationShopsList.get(i).getOption_flag();
				if(option_flag==0){
					shopY = shopY>0.67?0.67:shopY;	
				}else{
					shopY = shopY>0.75?0.75:shopY;	
				}
				Rect rect = new Rect((int)(shopX*width-(shopNameWidth+46)),(int)(shopY*hight)-26, (int)(shopX*width), (int)(shopY*hight)+26);  
		        np1.draw(canvas, rect); //画商品名称背景.9图片
		        canvas.drawText(shopName1, (int)(shopX*width) - shopNameWidth-26, (int)(shopY*hight)+10, textPaint1); //画商品名称
		        if(option_flag==0){
		        	canvas.drawBitmap(bmp, (float)shopX*width-bmp.getWidth()/2, (float)shopY*hight-bmp2.getHeight()/2, photoPaint1);//画价格背景图片
			        textPaint1.setTextSize(17f);
			        textPaint1.setColor(Color.parseColor("#ffffffff"));
//			        double price1 = collocationShopsList.get(0).getShop_se_price()- (int)collocationShopsList.get(0).getKickback();
			        double price1 = collocationShopsList.get(0).getShop_se_price()*0.9;//显示九折价格
			        String price1Text ="¥" +new DecimalFormat("0.0").format(Double.valueOf(price1));
			        float priceWidth = textPaint1.measureText(price1Text);
			        canvas.drawText(price1Text, (int)(shopX*width) - priceWidth/2, (int)(shopY*hight)+bmp.getHeight()*7/8-bmp2.getHeight()/2, textPaint1); //画商品价格	
		        }else{
		        	canvas.drawBitmap(bmp2, (float)shopX*width-bmp2.getWidth()/2, (float)shopY*hight-bmp2.getHeight()/2, photoPaint1);//画小红点图片
		        }
		        
			}else if(i==1){
				String shopName2 = collocationShopsList.get(1).getShop_name();
				if(shopName2.length()>7){
					shopName2 = shopName2.substring(0,7);
				}
				textPaint1.setTextSize(27f);
				textPaint1.setColor(Color.parseColor("#ffffffff"));
				float shopNameWidth = textPaint1.measureText(shopName2);
				
				double shopX = collocationShopsList.get(1).getShop_x();
				double shopY = collocationShopsList.get(1).getShop_y();
				if(shopX<=0||shopY<=0){
					continue;
				}
				int option_flag = collocationShopsList.get(i).getOption_flag();
				if(option_flag==0){
					shopY = shopY>0.67?0.67:shopY;	
				}else{
					shopY = shopY>0.75?0.75:shopY;	
				}
				
				Rect rect = new Rect((int)(shopX*width),(int)(shopY*hight)-26, (int)(shopX*width+shopNameWidth+46),(int)(shopY*hight)+26);  
		        np2.draw(canvas, rect); //画商品名称背景 .9图片
		        
		        canvas.drawText(shopName2, (int)(shopX*width) +26, (int)(shopY*hight)+10, textPaint1); //画商品名称
		        
		        if(option_flag==0){
			        canvas.drawBitmap(bmp, (float)shopX*width-bmp.getWidth()/2, (float)shopY*hight-bmp2.getHeight()/2, photoPaint1);//画价格背景图片
			        textPaint1.setTextSize(17f);
			        textPaint1.setColor(Color.parseColor("#ffffffff"));
//			        double price2 = collocationShopsList.get(1).getShop_se_price()-(int)collocationShopsList.get(1).getKickback();
			        double price2 = collocationShopsList.get(1).getShop_se_price()*0.9;//显示九折价格
			        String price2Text ="¥" +new DecimalFormat("0.0").format(Double.valueOf(price2));
			        float priceWidth = textPaint1.measureText(price2Text);
			        canvas.drawText(price2Text, (int)(shopX*width) - priceWidth/2, (int)(shopY*hight)+bmp.getHeight()*7/8-bmp2.getHeight()/2, textPaint1); //画商品价格
		        }else{
		        	canvas.drawBitmap(bmp2, (float)shopX*width-bmp2.getWidth()/2, (float)shopY*hight-bmp2.getHeight()/2, photoPaint1);//画小红点图片
		        }
			}
			
		}
		
		/** 画半透明背景 **/
		Paint paintAlflaBg = new Paint();
		paintAlflaBg.setAlpha(225);
		paintAlflaBg.setColor(Color.parseColor("#e1ffffff"));
		
		Rect ret = new Rect(0, hight - 200, width, hight);
		canvas.drawRect(ret, paintAlflaBg);
		
		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, 20, hight - 180, photoPaint);
		
		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setTextSize(43f);// 字体大小
		textPaint.setAntiAlias(true);
		String text;
//		text = "你穿肯定很好看";
//		title = "你穿肯定很好看，你穿肯定很好看你穿肯定很好看";
		text = title.trim();
		if(title.length()>15){
			text = text.substring(0, 15)+"...";
		}
		textPaint.setColor(Color.parseColor("#3E3E3E"));
		canvas.drawText(text, 200, hight - 117, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		String text1;
		text1 = "长按图片识别二维码购买";
		textPaint.setTextSize(37.0f);// 改变字体大小 写第二排字
		textPaint.setColor(Color.parseColor("#7D7D7D"));
		canvas.drawText(text1, 200, hight - 48, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		
//		/*** 画价格背景 */
//		Paint paintPriceBg = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paintPriceBg.setColor(Color.parseColor("#ff3f8b"));
//		paintPriceBg.setAntiAlias(true);
//		canvas.drawRect(width-260, hight - 200, width, hight,paintPriceBg);
//		
//		/*** 画价格字段 */
////		String text2 = "￥";
//		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置文字画笔
//		textPaint.setTextSize(56.0f);// 字体大小
//		textPaint.setColor(Color.parseColor("#ffffff"));
		
//		price ="￥" +new DecimalFormat("0.0").format(Double.valueOf(price));
//		float textPriceWidth = textPaint.measureText(price);
//		textPriceWidth = textPriceWidth / 2;
////		canvas.drawText(price, width - 135 - textPriceWidth, 180, textPaint);
//		canvas.drawText(price, width - 130 - textPriceWidth, hight-83, textPaint);
		
		canvas.save();
		canvas.restore();
		// imageView.setImageBitmap(icon);
		// saveMyBitmap(icon, "test");
		return icon;
	}

	/** 保存方法 */
	public static void saveBitmap(Bitmap bm, String path, String picName) {
		File f = new File(path, picName);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			LogYiFu.i(picName, "已经保存");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 在图片上面加图（搭配购单宫图的分享）
	 * 600*900图片单宫图
	 */
	public static Bitmap drawNewBitmapMatch(Context context, Bitmap bmBg,
			Bitmap qrBm, String price) {

		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		
		LogYiFu.e("QR", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标 是图片在Canvas画布中显示的区域，
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint

		/** 画半透明背景 **/
		Paint paintAlflaBg = new Paint();
		paintAlflaBg.setAlpha(225);
		paintAlflaBg.setColor(Color.parseColor("#e1ffffff"));

		Rect ret = new Rect(0, hight - 135, width, hight);
		canvas.drawRect(ret, paintAlflaBg);
		// canvas.drawBitmap(qrBm, src, dst, photoPaint);// 将photo 缩放或则扩大到

		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, 0, hight - 135, photoPaint);

		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setTextSize(35.0f);// 字体大小
		textPaint.setAntiAlias(true);
		String text;
//		price = new DecimalFormat("0").format(Double.valueOf(price));
		text = "你穿肯定很好看";
		textPaint.setColor(Color.parseColor("#3e3e3e"));
		canvas.drawText(text, 140, hight - 80, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		
		textPaint.setTextSize(25.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#7D7D7D"));
		String text1 = "长按图片识别二维码购买";
		canvas.drawText(text1, 140, hight - 32, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制

		/*** 画价格背景 */
		Paint paintPriceBg = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintPriceBg.setColor(Color.parseColor("#ff3f8b"));
		paintPriceBg.setAntiAlias(true);
		canvas.drawRect(width-170, hight - 135, width, hight,paintPriceBg);

		/*** 画价格字段 */
//		String text2 = "￥";
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置文字画笔
		textPaint.setTextSize(36.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#ffffff"));
//		float text2Width = textPaint.measureText(text2);// 计算文字所占宽度 t
//		text2Width = text2Width / 2;
//		canvas.drawText(text2, width - 135 - text2Width, 115, textPaint);

		price ="¥"+ new DecimalFormat("0.0").format(Double.valueOf(price));
		float textPriceWidth = textPaint.measureText(price);
		textPriceWidth = textPriceWidth / 2;
		
		canvas.drawText(price, width - 85 - textPriceWidth, hight-50, textPaint);
		canvas.save();
		canvas.restore();
		return icon;
	}
	
	/**
	 * 在图片上面加图
	 * 九宫图二维码的背景图片 新样式(正价和特卖)
	 * @param isMeal ture:特卖
	 */
	public static Bitmap drawNewBitmapNine(Context context,
			Bitmap qrBm, String price,boolean isMeal) {
//		Bitmap bmQr = QRCreateUtil.createQrImage(link, 250, 250);// 得到二维码图片
//		Bitmap bmBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.nine_bg);
		@SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.nine_bg);
		BitmapDrawable  bmpDraw = new BitmapDrawable(is);
		Bitmap bmBg = bmpDraw.getBitmap();
		//900*1401
		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		LogYiFu.e("drawNewBitmapNine", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些

		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标 是图片在Canvas画布中显示的区域,
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		
		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, width/2-125, 670, photoPaint);

		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setTextSize(64.0f);// 字体大小
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.parseColor("#3E3E3E"));
		String text = "小主~";
		float textWidth = textPaint.measureText(text);
		textWidth = textWidth / 2;
		canvas.drawText(text, width/2-textWidth, 505, textPaint);
		
		String text1 =isMeal?"买了肯定不后悔":"你穿肯定很好看";
		textWidth = textPaint.measureText(text1)/2;
		canvas.drawText(text1, width/2-textWidth, 575, textPaint);
		
		textPaint.setTextSize(45.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#7D7D7D"));
		String text2 = "长按图片识别二维码购买";
		textWidth = textPaint.measureText(text2)/2;
		canvas.drawText(text2, width/2-textWidth,990, textPaint);

		/*** 画价格字段 */
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);// 设置文字画笔
		textPaint.setFakeBoldText(true); //true为粗体，false为非粗体
		textPaint.setTextSize(85.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#ffffff"));
		price ="¥"+ new DecimalFormat("0.0").format(Double.valueOf(price));
		textWidth = textPaint.measureText(price)/2;
		canvas.drawText(price, width/2 - textWidth, 1136, textPaint);
		canvas.save();
		canvas.restore();
		return icon;
	}
	
	/**
	 * 在图片上面加图
	 * 九宫图二维码的背景图片 新样式(正价和特卖)
	 */
	public static Bitmap drawNewBitmapNine2(Context context,
			Bitmap qrBm,Bitmap bmBg) {
//		Bitmap bmQr = QRCreateUtil.createQrImage(link, 200, 200);// 得到二维码图片
//		Bitmap bmBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.nine_bg);
		if(bmBg==null){
			@SuppressLint("ResourceType") InputStream is = context.getResources().openRawResource(R.drawable.nine_bg_2_2);
			BitmapDrawable  bmpDraw = new BitmapDrawable(is);
			bmBg = bmpDraw.getBitmap();	
		}
		
		//900*1401
		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		LogYiFu.e("drawNewBitmapNine", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上
		
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		
		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标 是图片在Canvas画布中显示的区域,
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		
		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, width/2-95, hight-230, photoPaint);
		
		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setAntiAlias(true);
		
//		textPaint.setTextSize(65.0f);// 字体大小
//		textPaint.setColor(Color.parseColor("#3E3E3E"));
//		String text1 = "秋天更要美美哒，";
//		float textWidth = textPaint.measureText(text1);
//		textWidth = textWidth / 2;
//		canvas.drawText(text1, width/2-textWidth, hight-330, textPaint);
//		
//		textPaint.setTextSize(60.0f);// 字体大小
//		String text11 = "10000+秋季新款";
//		String text12 = "一折起。";
//		textWidth = textPaint.measureText(text11+text12);
//		textWidth = textWidth / 2;
//		canvas.drawText(text11, width/2-textWidth, hight-260, textPaint);
		
//		textPaint.setTextSize(38.0f);// 字体大小
//		textPaint.setColor(Color.parseColor("#ff3f8b"));
//		float textWidth1 = textPaint.measureText(text11);
////		float textWidth2 = textPaint.measureText(text12);
//		canvas.drawText(text12, width/2-textWidth+textWidth1, hight-260, textPaint);
		
//		String text1 =isMeal?"买了肯定不后悔":"你穿肯定很好看";
//		textWidth = textPaint.measureText(text1)/2;
//		canvas.drawText(text1, width/2-textWidth, 575, textPaint);
		
		textPaint.setTextSize(32.0f);// 字体大小
		textPaint.setColor(Color.parseColor("#050404"));
		String text2 = "长按图片识别二维码";
		float textWidth = textPaint.measureText(text2);
		canvas.drawText(text2, width/2-textWidth-95-24, hight-125, textPaint);
		
		String text22 = "查看海量秋装新上市";
		textWidth = textPaint.measureText(text22);
		canvas.drawText(text22, width/2+95+24, hight-125, textPaint);
		
		canvas.save();
		canvas.restore();
		return icon;
	}
	
	/**
	 * 在图片上面加图
	 * 邀请好友，中间九宫图片
	 */
	public static Bitmap drawInVitingBitmapNine(Context context,Bitmap bmBg,
			Bitmap qrBm) {
//		Bitmap bmQr = QRCreateUtil.createQrImage(link, 200, 200);// 得到二维码图片
//		Bitmap bmBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.nine_bg);
//		if(bmBg==null){
//			InputStream is = context.getResources().openRawResource(R.drawable.nine_bg_2_2);
//			BitmapDrawable  bmpDraw = new BitmapDrawable(is);
//		bmBg = bmpDraw.getBitmap();	
//		BitmapDrawable whiteBg=(BitmapDrawable) context.getResources().getDrawable(R.drawable.test_test_test);
//		Bitmap bmBg=whiteBg.getBitmap();
//		}
		
		//900*1401
		int width = bmBg.getWidth();
		int hight = bmBg.getHeight();
		LogYiFu.e("drawNewBitmapNine", "宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上
		
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		
		Rect src = new Rect(0, 0, bmBg.getWidth(), bmBg.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标 是图片在Canvas画布中显示的区域,
		canvas.drawBitmap(bmBg, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		
		/** 画二维码图片 **/
		canvas.drawBitmap(qrBm, width/2-100, hight/2-100, photoPaint);
		
		/*** 写文字 **/
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置文字画笔
		textPaint.setAntiAlias(true);
		
		
//		textPaint.setTextSize(32.0f);// 字体大小
//		textPaint.setColor(Color.parseColor("#050404"));
//		String text2 = "随时随地";
//		float textWidth = textPaint.measureText(text2);
//		canvas.drawText(text2, (600-textWidth)/2, hight-20, textPaint);
//		
//		String text22 = "想赚就赚";
//		textWidth = textPaint.measureText(text22);
//		int textHight=
//		canvas.drawText(text22, (600-textWidth)/2, 600-125, textPaint);
		
		canvas.save();
		canvas.restore();
		return icon;
	}
	
}

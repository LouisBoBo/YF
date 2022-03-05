package com.yssj.custom.view;

import com.yssj.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 图片发帖
 * @author lbp
 *
 */

public class PubImage extends LinearLayout {
	
	private ImageView img;
	
	private ImageView bt;
	
	private onDeteleImgLintener d;
	
	
	
	public PubImage(Context context,onDeteleImgLintener d) {
		super(context);
		this.d=d;
		LayoutInflater.from(context).inflate(R.layout.publ_top_img, this);
		img=(ImageView) findViewById(R.id.iv_image1);
		bt=(ImageView) findViewById(R.id.iv_cancel1);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PubImage.this.d.deleteImg(Integer.parseInt(PubImage.this.getTag().toString()));
			}
		});
		int width=context.getResources().getDisplayMetrics().widthPixels;
		img.getLayoutParams().width=width/3;
		img.getLayoutParams().height=width/3;
	}
	
	public interface onDeteleImgLintener{
		public void deleteImg(int index);
	}
	public void setBitmap(Bitmap bm){
		img.setImageBitmap(bm);
	}
}

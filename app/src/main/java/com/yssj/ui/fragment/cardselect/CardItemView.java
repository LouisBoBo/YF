package com.yssj.ui.fragment.cardselect;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
//import com.nostra13.universalimageloader.core.ImageLoader;
import com.yssj.activity.R;
import com.yssj.utils.PicassoUtils;
//import com.yssj.utils.PicassoUtils;
import com.yssj.utils.SetImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * 卡片View项
 *
 * @author xmuSistone
 */
@SuppressLint("NewApi")
public class CardItemView extends FrameLayout {
    private Spring springX, springY;
    public ImageView imageView;
    public View maskView;
    private TextView shopNameTv;
    private TextView shopPriceTv;
    private TextView manufacturerTv;
    private ImageView imageCenterIv;
    private DecimalFormat pFormate;


    public ImageView getImageCenterIv() {
		return imageCenterIv;
	}

	public void setImageCenterIv(ImageView imageCenterIv) {
		this.imageCenterIv = imageCenterIv;
	}

    private CardSlidePanel parentView;
    private View topLayout/*, bottomLayout*/;

    public CardItemView(Context context) {
        this(context, null);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.card_item, this);
        imageView = (ImageView) findViewById(R.id.card_image_view);
        maskView = findViewById(R.id.maskView);
        shopNameTv = (TextView) findViewById(R.id.shop_name_tv);
        imageCenterIv = (ImageView) findViewById(R.id.card_center_pic);
        shopPriceTv = (TextView) findViewById(R.id.shop_price_tv);
        manufacturerTv = (TextView) findViewById(R.id.manufacturer_tv);
        topLayout = findViewById(R.id.card_top_layout);
//        bottomLayout = findViewById(R.id.card_bottom_layout);
        initSpring();
    }

    private void initSpring() {
        SpringConfig springConfig = SpringConfig.fromBouncinessAndSpeed(15, 20);
        SpringSystem mSpringSystem = SpringSystem.create();
        springX = mSpringSystem.createSpring().setSpringConfig(springConfig);
        springY = mSpringSystem.createSpring().setSpringConfig(springConfig);

        springX.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int xPos = (int) spring.getCurrentValue();
                setScreenX(xPos);
                parentView.onViewPosChanged(CardItemView.this);
            }
        });

        springY.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                setScreenY(yPos);
                parentView.onViewPosChanged(CardItemView.this);
            }
        });
    }

    public void fillData(CardDataItem itemData) {
        pFormate=new DecimalFormat("#0.0");
//        ImageLoader.getInstance().displayImage(itemData.imagePath, imageView);
//        imageView.setImageResource(R.drawable.share0021);
//    	SetImageLoader.initImageLoader(getContext(), imageView, 
//    			itemData.getShop_code().substring(1, 4) + "/" + itemData.getShop_code() + "/" + itemData.getDef_pic(), "!450");
    	
    	PicassoUtils.initImage(getContext(), itemData.getShop_code().substring(1, 4) + "/" + itemData.getShop_code() + "/" + itemData.getDef_pic()+"!450",imageView);
    	
    	
//    	PicassoUtils.initImage(getContext(), itemData.getShop_code().substring(1, 4) + "/" + itemData.getShop_code() + "/" + itemData.getDef_pic(), imageView);
        shopNameTv.setText(itemData.getShop_name());
        shopPriceTv.setText("¥"+pFormate.format(Double.parseDouble(itemData.getShop_se_price())*0.9));//显示9折价格
//        imageNumTv.setText(itemData.imageNum + "");
//        likeNumTv.setText(itemData.likeNum + "");
        if(!TextUtils.isEmpty(itemData.getSupp_label())){
        	manufacturerTv.setVisibility(View.VISIBLE);
        	manufacturerTv.setText(itemData.getSupp_label()+"制造商出品");
        }else{
        	manufacturerTv.setVisibility(View.INVISIBLE);
        }
       
    }


    /**
     * 动画移动到某个位置
     */
    public void animTo(int xPos, int yPos) {
        setCurrentSpringPos(getLeft(), getTop());
        springX.setEndValue(xPos);
        springY.setEndValue(yPos);
    }

    /**
     * 设置当前spring位置
     */
    private void setCurrentSpringPos(int xPos, int yPos) {
        springX.setCurrentValue(xPos);
        springY.setCurrentValue(yPos);
    }

    public void setScreenX(int screenX) {
        this.offsetLeftAndRight(screenX - getLeft());
    }

    public void setScreenY(int screenY) {
        this.offsetTopAndBottom(screenY - getTop());
    }

    public void setParentView(CardSlidePanel parentView) {
        this.parentView = parentView;
    }

    public void onStartDragging() {
        springX.setAtRest();
        springY.setAtRest();
    }

    /**
     * 判断(x, y)是否在可滑动的矩形区域内
     * 这个函数也被CardSlidePanel调用
     *
     * @param x 按下时的x坐标
     * @param y 按下时的y坐标
     * @return 是否在可滑动的矩形区域
     */
    public boolean shouldCapture(int x, int y) {
        int captureLeft = getLeft() + topLayout.getPaddingLeft();
        int captureTop = getTop() + topLayout.getTop() + topLayout.getPaddingTop();
        int captureRight = getRight()/* - bottomLayout.getPaddingRight()*/;
        int captureBottom = getBottom() - getPaddingBottom()/* - bottomLayout.getPaddingBottom()*/;

        if (x > captureLeft && x < captureRight && y > captureTop && y < captureBottom) {
            return true;
        }
        return false;
    }
}

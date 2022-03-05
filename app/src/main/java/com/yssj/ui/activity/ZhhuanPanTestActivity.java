package com.yssj.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.yssj.activity.R;
import com.yssj.ui.base.BasicActivity;

import java.util.ArrayList;

/**
 * Created by lifeng on 2018/2/26.
 */


public class ZhhuanPanTestActivity extends BasicActivity implements View.OnClickListener {

    ObjectAnimator animator;


    ObjectAnimator animatorStop;


    ImageView im_scan;

    ArrayList<Integer> numList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pan);


        im_scan = (ImageView) findViewById(R.id.im_scan);
        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        numList = new ArrayList<>();
        for (int i = 10; i <= 350; i++) {
            numList.add(i);
        }


        animator = ObjectAnimator.ofFloat(im_scan, "rotation", 0f, 360.0f);
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());//不停顿
        animator.setRepeatCount(-1);//设置动画重复次数
        animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式


    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt1: //开始
                animator.start();//开始动画
                break;

            case R.id.bt2: //停止

                animator.pause();//暂停动画
                animator.reverse();

//                int num = new Random().nextInt(350); //0-350随机数（不包括350）

//                if(num > 10)
                int index=(int)(Math.random()* numList.size());
                int rand = numList.get(index);

                animatorStop = ObjectAnimator.ofFloat(im_scan, "rotation", 0f, rand);
                animatorStop.setDuration(10);
                animatorStop.start();


                break;

        }


    }
}

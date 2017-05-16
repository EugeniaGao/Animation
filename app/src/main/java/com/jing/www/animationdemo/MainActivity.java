package com.jing.www.animationdemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.jing.www.animationdemo.R.id.goods;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView imags;
    private RelativeLayout cart;
    private PathMeasure pathMeasure;
    private RelativeLayout ivContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cart = (RelativeLayout) findViewById(R.id.cart);
        imags = (ImageView) findViewById(goods);
        ivContainer = (RelativeLayout) findViewById(R.id.activity_main);
        imags.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {

        startAnimation();
    }

    private void startAnimation() {

        //1.得到起始点坐标
        int parentLoc[] = new int[2];
        imags.getLocationInWindow(parentLoc);
        int startLoc[] = new int[2];
        imags.getLocationInWindow(startLoc);
        int endLoc[] = new int[2];
        cart.getLocationInWindow(endLoc);


       //2.在原位置放入圆图片,以防被移动
        final  ImageView goodsnew = new ImageView(getApplicationContext());
        //goodsnew.setImageDrawable(goods.getDrawable());
        goodsnew.setImageResource(R.drawable.d);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
        ivContainer.addView(goodsnew,params);

        //3.获取起始x,y
        float startX = startLoc[0]  ;
        float startY = startLoc[1] - parentLoc[1];
        float toX = endLoc[0]  + cart.getWidth() / 3;
        float toY = endLoc[1] ;
        //4.指定path
        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        pathMeasure = new PathMeasure(path, false);
        final float[] mCurrentPosition = new float[2];




        //5.执行动画

        //属性动画实现
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速插值器
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pathMeasure.getPosTan(value, mCurrentPosition, null);
                goodsnew.setTranslationX(mCurrentPosition[0]);
                goodsnew.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();


//6.添加监听

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
}

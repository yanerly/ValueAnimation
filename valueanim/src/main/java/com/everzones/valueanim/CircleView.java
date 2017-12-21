package com.everzones.valueanim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunny on 2017/12/21.
 * anthor:sunny
 * date: 2017/12/21
 * function:自定义圆
 */

public class CircleView extends View{
    // 设置需要用到的变量
    public static final float RADIUS = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔
    private String color; // 设置背景颜色属性

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
        invalidate();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
    }

    //绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //位移变换
        // 如果当前点坐标为空(即第一次)
        if (currentPoint == null){
            currentPoint = new Point(RADIUS,RADIUS);

            //在该点画一个圆:圆心 = (70,70),半径 = 70
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);

            //设置开始和结束时的点
            Point startPoint = new Point(RADIUS,RADIUS);
            Point endPoint = new Point(700,1000);

            //创建动画对象
            ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
            anim.setDuration(2000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentPoint = (Point) animation.getAnimatedValue();
                    //每次赋值后就重新绘制，从而实现动画效果
                    invalidate();
                }
            });
            anim.start();
        }else {
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x,y,RADIUS,mPaint);
        }
    }
}

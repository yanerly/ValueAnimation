package com.everzones.valueanim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_click;
    private CircleView2 circle;
    ViewWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
        circle = findViewById(R.id.circle);
        bt_click = findViewById(R.id.bt_click);
        bt_click.setOnClickListener(this);

        wrapper = new ViewWrapper(bt_click);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_click:
                startAnim(); //改变按钮的宽度
                startXMLAnim();//通过xml改变按钮的宽度

                startObjectAlphaAnim();//透明度变化
                startObjectRotateAnim();//旋转
                startObjectTranseAnim();//X轴平移
                startObjectScaleAnim();//缩放

                startColorAnim();//点击按钮后圆形颜色渐变

                startWrapAnim();//包装

                startSetAnim();//组合动画
                break;
        }
    }

    private void startSetAnim() {
        //方式1
       /* int curTranslationX = bt_click.getLayoutParams().width;
        ObjectAnimator translation = ObjectAnimator.ofFloat(bt_click, "translationX", curTranslationX, 300,curTranslationX);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(bt_click, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(bt_click, "alpha", 1f, 0f, 1f);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(translation).with(rotate).before(alpha);
        animSet.setDuration(5000);
        animSet.start();*/

       //方式2：
        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.set);
        anim.setTarget(bt_click);
        anim.start();
    }

    private void startWrapAnim() {
        ObjectAnimator.ofInt(wrapper, "width", 500).setDuration(3000).start();
    }

    //自定义颜色的变化动画
    private void startColorAnim() {
        ObjectAnimator anim = ObjectAnimator.ofObject(circle,"color",new ColorEvaluator(),"#0000FF","#FF0000");
        anim.setDuration(6000);
        anim.start();
    }

    private void startObjectScaleAnim() {
        //x轴上放大：scaleX
        //y轴上方法：scaleY
        ObjectAnimator anim = ObjectAnimator.ofFloat(bt_click,"scaleX",1f,2f);
        anim.setDuration(2000);
        anim.start();
    }

    private void startObjectTranseAnim() {
        //初始位置-->150--->初始位置
        //x轴移动：translationX
        //y轴移动：translationY
        float x = bt_click.getTranslationX();
        ObjectAnimator anim = ObjectAnimator.ofFloat(bt_click,"translationX",x,150,x);
        anim.setDuration(2000);
        anim.start();
    }

    private void startObjectRotateAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(bt_click,"rotation",0,350);
        anim.setDuration(2000);
        anim.start();
    }

    //设置透明度的变化
    private void startObjectAlphaAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(bt_click, "alpha",1f,0f,1f);
        anim.setDuration(1500);
        anim.start();
    }


    //在xml中设置，res/animator文件夹/<animator>
    private void startXMLAnim() {
        Animator animator = AnimatorInflater.loadAnimator(this.getApplicationContext(),R.animator.set_animation);
        // 设置动画对象
        animator.setTarget(bt_click);
        bt_click.requestLayout();
        animator.start();
    }


    private void startAnim() {
        ValueAnimator anim = ValueAnimator.ofInt(bt_click.getLayoutParams().width,500);
        anim.setDuration(2000);
        anim.setStartDelay(500);
        anim.setRepeatCount(0);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
               @Override
               public void onAnimationUpdate(ValueAnimator animation) {
                   int currentValue = (Integer) animation.getAnimatedValue();
                   // 每次值变化时，将值手动赋值给对象的属性
                   bt_click.getLayoutParams().width = currentValue;

                   //刷新视图，即重新绘制，从而实现动画效果
                   bt_click.requestLayout();
               }
           }
        );
        anim.start();
    }

    // 提供ViewWrapper类,用于包装View对象
    // 本例:包装Button对象
    private static class ViewWrapper {
        private View mTarget;

        // 构造方法:传入需要包装的对象
        public ViewWrapper(View target) {
            mTarget = target;
        }

        // 为宽度设置get（） & set（）
        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}

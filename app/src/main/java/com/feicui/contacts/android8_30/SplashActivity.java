package com.feicui.contacts.android8_30;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;

import android.widget.ImageView;


/**
 * @author cpp 2016-09-02
 * @description 开屏页
 */
public class SplashActivity extends BaseActivity {
    //开屏页图片
    ImageView iv_splash;
    ValueAnimator alphaAnim;

    @Override
    protected int setContent() {

        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
    }

    @Override
    protected void setListener() {

    }

    /**
     * @description 加载动画
     */
    private void initAnimation() {
        //淡入动画
        alphaAnim = ObjectAnimator.ofFloat(
                iv_splash, "alpha", 0.0f, 1.0f);
        //设置动画时间
        alphaAnim.setDuration(3000);
        //设置动画播放结束后的监听
        alphaAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //跳转到主页
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //关闭开屏页
                finish();
            }
        });
        //开启动画
        alphaAnim.start();
    }

    /**
     * 当前页是否已获得焦点
     *
     * @param hasFocus true代表获得焦点，false相反
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            initAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        alphaAnim.pause();
        super.onDestroy();
    }
}

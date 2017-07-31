package com.aikaifa.loadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * -----------------------------------------------------------
 * Copyright (c) 2016 洪生鹏 All rights reserved.
 * -----------------------------------------------------------
 * 时间： 16/5/31 14:37
 * 微信公众号: aikaifa
 * QQ交流群 : 154950206
 */
public class LoadingView extends FrameLayout {
    private View mView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private AnimatorSet mAnimatorSet;
    /**
     * 动画间隔
     */
    private long interval = 500L;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mView = LayoutInflater.from(context).inflate(R.layout.loading_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView1 = ((ImageView) findViewById(R.id.iv_load1));
        imageView2 = ((ImageView) findViewById(R.id.iv_load2));
        imageView3 = ((ImageView) findViewById(R.id.iv_load3));
        imageView4 = ((ImageView) findViewById(R.id.iv_load4));
        showLoading();
    }

    public void showLoading() {
        if (getVisibility() != View.VISIBLE)
            return;
        if (mAnimatorSet == null)
            initAnimation();
        if (mAnimatorSet.isRunning() || mAnimatorSet.isStarted())
            return;
        mAnimatorSet.start();
    }

    public void hideLoading() {
        if (mAnimatorSet == null) {
            return;
        }
        if ((!mAnimatorSet.isRunning()) && (!mAnimatorSet.isStarted())) {
            return;
        }
        mAnimatorSet.removeAllListeners();
        mAnimatorSet.cancel();
        mAnimatorSet.end();
    }

    private void initAnimation() {
        mAnimatorSet = new AnimatorSet();
        List<ImageView> imageViewList = Arrays.asList(imageView1, imageView2, imageView3, imageView4);
        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ObjectAnimator loadAnimator = ObjectAnimator.ofFloat(imageViewList.get(i), "alpha", new float[]{1.0F, 0.5F}).setDuration(interval);
            loadAnimator.setStartDelay(100 * i);
            loadAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            loadAnimator.setRepeatCount(-1);
            animatorList.add(loadAnimator);
        }
        mAnimatorSet.playTogether(animatorList);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE)
            hideLoading();
    }
}

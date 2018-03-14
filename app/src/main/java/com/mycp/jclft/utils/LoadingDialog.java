package com.mycp.jclft.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycp.jclft.R;

/**
 * Created by suning on 2018/1/24.
 */

public class LoadingDialog extends Dialog {

    private ImageView mImageview;
    private TextView mLoadingTv;

    public LoadingDialog(Context context) {
        this(context, R.style.Circle_LoadingDialogStyle);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, R.style.Circle_LoadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading);

        initView();
    }

    private void initView() {

        mImageview = (ImageView) findViewById(R.id.iv_refresh_circle);
        mLoadingTv = (TextView) findViewById(R.id.tv_refresh_title);

        mLoadingTv.setVisibility(View.VISIBLE);
        mLoadingTv.setText("加载中...");

        startAnimation();
    }

    public void startAnimation() {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        LinearInterpolator li = new LinearInterpolator();
        animation.setInterpolator(li);
        mImageview.startAnimation(animation);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus) {
            dismiss();
        }
    }

    public void setLoadingMsg(String loadingMsg) {
        mLoadingTv.setVisibility(View.VISIBLE);
        mLoadingTv.setText(loadingMsg);
    }

}


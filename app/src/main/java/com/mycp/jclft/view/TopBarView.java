package com.mycp.jclft.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mycp.jclft.R;

/**
 * Des:
 * Author: leo
 * Date：2018/3/2.
 */

public class TopBarView extends LinearLayout {

    private RelativeLayout mLeftLayout;
    private ImageView mLeftImage;
    private TextView mTitle;
    private RelativeLayout mRightLayout;
    private ImageView mRightImage;
    private RelativeLayout mRootLayout;
    private TextView mRightTv;

    public TopBarView(Context context) {
        super(context);
        init(context);
    }

    public TopBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_top, this, true);
        mRootLayout = (RelativeLayout) findViewById(R.id.root);
        mLeftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        mLeftImage = (ImageView) findViewById(R.id.left_img);
        mTitle = (TextView) findViewById(R.id.title_txt);
        mRightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        mRightImage = (ImageView) findViewById(R.id.right_img);
        mRightTv = (TextView) findViewById(R.id.tv_right);
    }

    public void setBackgroundResource(@DrawableRes int backgroundResource) {
        mRootLayout.setBackgroundResource(backgroundResource);
    }

    public void setBackgroundDrawable(Drawable background) {
        if (null != mRootLayout) {
            mRootLayout.setBackgroundDrawable(background);
        }
    }

    public RelativeLayout getLeftLayout() {
        return mLeftLayout;
    }

    public RelativeLayout getRightLayout() {
        return mRightLayout;
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setBackBtnVisible(boolean visible) {
        if (mLeftLayout != null) {
            mLeftLayout.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    public void setRightText(String text) {
        mRightTv.setText(text);
        mRightLayout.setVisibility(VISIBLE);
    }
}

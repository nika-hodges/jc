package com.mycp.jclft.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout.LayoutParams;

public class ExpandAnimation extends Animation {

    private View mAnimationView = null;
    private LayoutParams mViewLayoutParams = null;
    private int mStart = 0;
    private int mEnd = 0;
    
    public ExpandAnimation(View view, boolean expand){
        animationSettings(view, 300, expand);
    }

    public ExpandAnimation(View view, int duration, boolean expand){
        animationSettings(view, duration, expand);
    }
    
    private void animationSettings(View view, int duration, boolean expand){
        setDuration(duration);
        mAnimationView = view;
        mViewLayoutParams = (LayoutParams) view.getLayoutParams();
        if(expand)
        {
            mStart = mViewLayoutParams.bottomMargin;
            mEnd = 0;
        }else
        {
            mStart = mViewLayoutParams.bottomMargin;
            mEnd = - view.getHeight();
        }
        view.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        
        if(interpolatedTime < 1.0f){
            mViewLayoutParams.bottomMargin = mStart + (int) ((mEnd - mStart) * interpolatedTime);
            // invalidate
            mAnimationView.requestLayout();
        }else{
            mViewLayoutParams.bottomMargin = mEnd;
            mAnimationView.requestLayout();
            if(mEnd != 0){
                mAnimationView.setVisibility(View.GONE);
            }
        }
    }
}

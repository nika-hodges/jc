package com.mycp.jclft.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        initView(view);
        initData();
        return view;
    }

    protected abstract void initView(View view);

    protected abstract int bindLayout();

    protected abstract void initData();

    /**
     * fragment可见的时候操作，取代onResume，且在可见状态切换到可见的时候调用
     */
//    protected void onVisible() {
//        initData();
//    }


    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
//    protected void onHidden() {
//
//    }


//    @Override
//    public void onResume() {//和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
//        super.onResume();
//        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
//            onVisible();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        if (isVisible())
//            onHidden();
//        super.onPause();
//    }
//
//    @Override
//    public void onHiddenChanged(boolean hidden) {//默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            onVisible();
//        } else {
//            onHidden();
//        }
//    }
}
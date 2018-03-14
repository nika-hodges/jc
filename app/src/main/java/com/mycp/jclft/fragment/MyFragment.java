package com.mycp.jclft.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mycp.jclft.R;
import com.mycp.jclft.utils.LoadingDialog;
import com.mycp.jclft.view.TopBarView;

/**
 * Des:
 * Author: leo
 * Date：2018/2/27.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private TopBarView mTop;
    private TextView mClear;
    private LoadingDialog mDialog;

    private static final int CLEAR_CACHE = 0x128;
    private static final int CLEAR_CACHE_COMPLETE = 0x129;
    private static final int CHECK_UPDATE = 0x130;
    private static final int CHECH_UPDATE_COMPLETE = 0x131;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAR_CACHE:
                    if (getActivity() == null && getActivity().isDestroyed() && getActivity().isFinishing()) {
                        return;
                    }
                    mDialog.show();
                    mDialog.startAnimation();
                    mDialog.setLoadingMsg("缓存清除中。。。");
                    mHandler.sendEmptyMessageDelayed(CLEAR_CACHE_COMPLETE, 2000);
                    break;
                case CLEAR_CACHE_COMPLETE:
                    if (getActivity() == null && getActivity().isDestroyed() && getActivity().isFinishing()) {
                        return;
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), "清除完成", Toast.LENGTH_SHORT).show();
                    break;
                case CHECK_UPDATE:
                    if (getActivity() == null && getActivity().isDestroyed() && getActivity().isFinishing()) {
                        return;
                    }
                    mDialog.show();
                    mDialog.startAnimation();
                    mDialog.setLoadingMsg("检查升级中。。。");
                    mHandler.sendEmptyMessageDelayed(CHECH_UPDATE_COMPLETE, 2000);
                    break;
                case CHECH_UPDATE_COMPLETE:
                    if (getActivity() == null && getActivity().isDestroyed() && getActivity().isFinishing()) {
                        return;
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    private TextView mCheckBox;
    private boolean mPush;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mDialog = new LoadingDialog(getActivity());
        mTop = (TopBarView) view.findViewById(R.id.view_top);
        mCheckBox = (TextView) view.findViewById(R.id.cb_push);
        view.findViewById(R.id.tv_check_update).setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mTop.setTitle("我的");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mClear = (TextView) view.findViewById(R.id.setting_cache_view);
        mClear.setOnClickListener(this);
        SharedPreferences share = getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        mPush = share.getBoolean("push", true);
        setPushStatus();
    }

    private void setPushStatus() {
        if (mPush) {
            mCheckBox.setBackgroundResource(R.drawable.push_open);
        } else {
            mCheckBox.setBackgroundResource(R.drawable.push_close);
        }
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_cache_view:
                mHandler.sendEmptyMessage(CLEAR_CACHE);
                break;
            case R.id.tv_check_update:
                mHandler.sendEmptyMessage(CHECK_UPDATE);
                break;
            case R.id.cb_push:
                mPush = !mPush;
                setPushStatus();
                SharedPreferences share = getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.putBoolean("push", mPush);
                edit.apply();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

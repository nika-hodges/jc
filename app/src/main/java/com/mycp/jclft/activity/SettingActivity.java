package com.mycp.jclft.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mycp.jclft.R;
import com.mycp.jclft.utils.LoadingDialog;
import com.mycp.jclft.view.TopBarView;

/**
 * Created by leo on 2018/3/12.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CLEAR_CACHE = 0x128;
    private static final int CLEAR_CACHE_COMPLETE = 0x129;
    private static final int CHECK_UPDATE = 0x130;
    private static final int CHECH_UPDATE_COMPLETE = 0x131;

    private TopBarView mTop;
    private TextView mClear;
    private LoadingDialog mDialog;
    private TextView mCheckBox;
    private boolean mPush;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLEAR_CACHE:
                    if (SettingActivity.this == null || SettingActivity.this.isDestroyed() || SettingActivity.this.isFinishing()) {
                        return;
                    }
                    mDialog.show();
                    mDialog.startAnimation();
                    mDialog.setLoadingMsg("缓存清除中。。。");
                    mHandler.sendEmptyMessageDelayed(CLEAR_CACHE_COMPLETE, 2000);
                    break;
                case CLEAR_CACHE_COMPLETE:
                    if (SettingActivity.this == null || SettingActivity.this.isDestroyed() || SettingActivity.this.isFinishing()) {
                        return;
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    Toast.makeText(SettingActivity.this, "清除完成", Toast.LENGTH_SHORT).show();
                    break;
                case CHECK_UPDATE:
                    if (SettingActivity.this == null || SettingActivity.this.isDestroyed() || SettingActivity.this.isFinishing()) {
                        return;
                    }
                    mDialog.show();
                    mDialog.startAnimation();
                    mDialog.setLoadingMsg("检查升级中。。。");
                    mHandler.sendEmptyMessageDelayed(CHECH_UPDATE_COMPLETE, 2000);
                    break;
                case CHECH_UPDATE_COMPLETE:
                    if (SettingActivity.this == null || SettingActivity.this.isDestroyed() || SettingActivity.this.isFinishing()) {
                        return;
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    Toast.makeText(SettingActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SettingActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (!isE_MUI3_1()) {
//                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
        initView();
        initData();
    }

    private void initView() {
        mDialog = new LoadingDialog(this);
        mTop = (TopBarView) findViewById(R.id.view_top);
        mCheckBox = (TextView) findViewById(R.id.cb_push);
        findViewById(R.id.tv_check_update).setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mTop.setTitle("设置");
        mTop.getLeftLayout().setVisibility(View.VISIBLE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mTop.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mClear = (TextView) findViewById(R.id.setting_cache_view);
        mClear.setOnClickListener(this);
        SharedPreferences share = getSharedPreferences("share", Context.MODE_PRIVATE);
        mPush = share.getBoolean("push", true);
        setPushStatus();
    }

    private void initData() {

    }

    private void setPushStatus() {
        if (mPush) {
            mCheckBox.setBackgroundResource(R.drawable.on);
        } else {
            mCheckBox.setBackgroundResource(R.drawable.off);
        }
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
                SharedPreferences share = this.getSharedPreferences("share", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.putBoolean("push", mPush);
                edit.apply();
                break;
            default:
                break;
        }
    }
}

package com.mycp.jclft.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.OpenDetailAdapter;
import com.mycp.jclft.adapter.OpenNumberAdapter;
import com.mycp.jclft.adapter.OpenNumberAdapter1;
import com.mycp.jclft.entity.NearlyLotteryResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.utils.LoadingDialog;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.NumberFormat;

import okhttp3.Call;

/**
 * Des:最近一期开奖结果
 * Author: leo
 * Date：2018/3/4.
 */

public class LastOpenActivity extends AppCompatActivity {

    private String mName;
    private TextView mPricePoolTv;
    private TextView mSaleTv;
    private TextView mTimeTv;
    private TextView mExpectTv;
    private TextView mTitleTv;
    private ListView mDetailLv;
    private OpenDetailAdapter mOpenDetailAdapter;
    private RecyclerView mNumberRv;
    private OpenNumberAdapter mOpenNumberAdapter;
    private LinearLayout mPollLl;
    private TopBarView mTop;
    private ImageView mIconIv;
    private LoadingDialog mDialog;
    private OpenNumberAdapter1 mOpenNumberAdapter1;
    private String mCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_open);

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

        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mCode = intent.getStringExtra("code");
        if ("超级大乐透".equals(mName)) {
            mName = "大乐透";
        }
        if ("任选9".equals(mName)) {
            mName = "任选九";
        }
        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mName = intent.getStringExtra("name");
        if ("超级大乐透".equals(mName)) {
            mName = "大乐透";
        }
        if ("任选9".equals(mName)) {
            mName = "任选九";
        }
    }

    private void initView() {
        mDialog = new LoadingDialog(this);
        mDialog.show();
        mTop = (TopBarView) findViewById(R.id.view_top);
        mTop.getLeftLayout().setVisibility(View.VISIBLE);
        mTop.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTop.setRightText("查看往期");
        mTop.getRightLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastOpenActivity.this, PastOpenActivity.class);
                intent.putExtra("code", mCode);
                intent.putExtra("name", mName);
                startActivity(intent);
            }
        });
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mExpectTv = (TextView) findViewById(R.id.tv_expect);
        mTimeTv = (TextView) findViewById(R.id.tv_time);
        mIconIv = (ImageView) findViewById(R.id.iv_icon);
        mSaleTv = (TextView) findViewById(R.id.tv_sale);
        mPricePoolTv = (TextView) findViewById(R.id.tv_price_pool);
        mPollLl = (LinearLayout) findViewById(R.id.ll_pool);
        mNumberRv = (RecyclerView) findViewById(R.id.rv_number);
        mNumberRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDetailLv = (ListView) findViewById(R.id.lv_detail);
    }

    private void initData() {
        switch (mName) {
            case "七星彩":
                mIconIv.setBackgroundResource(R.drawable.ic_qxc);
                break;
            case "七乐彩":
                mIconIv.setBackgroundResource(R.drawable.ic_qlc);
                break;
            case "排列3":
                mIconIv.setBackgroundResource(R.drawable.ic_pls);
                break;
            case "排列5":
                mIconIv.setBackgroundResource(R.drawable.ic_pl5);
                break;
            case "双色球":
                mIconIv.setBackgroundResource(R.drawable.ic_ssq);
                break;
            case "大乐透":
                mIconIv.setBackgroundResource(R.drawable.ic_dlt);
                break;
            case "胜负彩":
                mIconIv.setBackgroundResource(R.drawable.ic_sfc);
                break;
            case "任选9":
                mIconIv.setBackgroundResource(R.drawable.ic_rx9);
                break;
            case "竞猜足球":
                mIconIv.setBackgroundResource(R.drawable.ic_jczq);
                break;
            case "竞猜篮球":
                mIconIv.setBackgroundResource(R.drawable.ic_jclq);
                break;
            case "足球单场":
                mIconIv.setBackgroundResource(R.drawable.ic_zqdc);
                break;
            default:
                break;
        }
        mTop.setTitle(mName);
        if (mOpenDetailAdapter == null) {
            mOpenDetailAdapter = new OpenDetailAdapter(this);
        }
        if (mOpenNumberAdapter == null) {
            mOpenNumberAdapter = new OpenNumberAdapter(this);
        }
        if (mOpenNumberAdapter1 == null) {
            mOpenNumberAdapter1 = new OpenNumberAdapter1(this);
        }
        OkHttpUtils
                .get()
                .url("http://apicloud.mob.com/lottery/query")
                .addParams("key", "220bb0a30d947")
                .addParams("name", mName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                        Gson gson = new Gson();
                        NearlyLotteryResult result = gson.fromJson(response, NearlyLotteryResult.class);
                        if (result == null || !"200".equals(result.retCode)) {
                            return;
                        }
                        if (result.result != null && !CommUtil.isEmpty(result.result.lotteryDetails) && !CommUtil.isEmpty(result.result.lotteryNumber)) {
                            if (!TextUtils.isEmpty(result.result.name)) {
                                mTitleTv.setText(result.result.name);
                                if ("双色球".equals(result.result.name)) {
                                    result.result.lotteryNumber.set(result.result.lotteryNumber.size() - 1, result.result.lotteryNumber.get(result.result.lotteryNumber.size() - 1) + "特别");
                                }
                                if ("七乐彩".equals(result.result.name)) {
                                    result.result.lotteryNumber.set(result.result.lotteryNumber.size() - 1, result.result.lotteryNumber.get(result.result.lotteryNumber.size() - 1) + "特别");
                                }
                                if ("大乐透".equals(result.result.name)) {
                                    result.result.lotteryNumber.set(result.result.lotteryNumber.size() - 2, result.result.lotteryNumber.get(result.result.lotteryNumber.size() - 2) + "特别");
                                    result.result.lotteryNumber.set(result.result.lotteryNumber.size() - 1, result.result.lotteryNumber.get(result.result.lotteryNumber.size() - 1) + "特别");
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(result.result.awardDateTime)) {
                            if (result.result.awardDateTime.contains(" ")) {
                                mTimeTv.setText(result.result.awardDateTime.substring(0, result.result.awardDateTime.indexOf(" ")));
                            }
                        }
                        if (!TextUtils.isEmpty(result.result.period)) {
                            mExpectTv.setText(getResources().getString(R.string.expect, result.result.period));
                        }
                        NumberFormat nf1 = NumberFormat.getInstance();
                        if (result.result.sales == -1) {
                            mSaleTv.setText("暂无数据");
                        }
                        mSaleTv.setText(nf1.format(result.result.sales));
                        if ("0".equals(nf1.format(result.result.pool))) {
                            mPollLl.setVisibility(View.GONE);
                        } else {
                            mPollLl.setVisibility(View.VISIBLE);
                        }
                        mPricePoolTv.setText(nf1.format(result.result.pool));
                        if (mName.contains("胜负彩") || mName.contains("任选九")) {
                            mNumberRv.setAdapter(mOpenNumberAdapter1);
                            mOpenNumberAdapter1.setData(result.result.lotteryNumber);
                        } else {
                            mNumberRv.setAdapter(mOpenNumberAdapter);
                            mOpenNumberAdapter.setData(result.result.lotteryNumber);
                        }
                        mDetailLv.setAdapter(mOpenDetailAdapter);
                        mOpenDetailAdapter.setData(result.result.lotteryDetails);
                    }

                });
    }
}

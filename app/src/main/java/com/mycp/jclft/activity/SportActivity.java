package com.mycp.jclft.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.LqAdapter;
import com.mycp.jclft.adapter.ZqAdapter;
import com.mycp.jclft.entity.LqMatchInfoResult;
import com.mycp.jclft.entity.MatchInfoResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class SportActivity extends AppCompatActivity {

    private TopBarView mTop;
    private ListView mSportLv;
    private ZqAdapter mZqAdapter;
    private LqAdapter mLqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
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
        mTop = (TopBarView) findViewById(R.id.view_top);
        mTop.getLeftLayout().setVisibility(View.VISIBLE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mTop.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSportLv = (ListView) findViewById(R.id.lv_sport);
        mSportLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    private void initData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            if ("竞猜足球".equals(name) || "足球单场".equals(name)) {
                if (mZqAdapter == null) {
                    mZqAdapter = new ZqAdapter(this);
                }
                mSportLv.setAdapter(mZqAdapter);
                getZqData();
            } else if ("竞猜篮球".equals(name)) {
                if (mLqAdapter == null) {
                    mLqAdapter = new LqAdapter(this);
                }
                mSportLv.setAdapter(mLqAdapter);
                getLqData();
            }
            mTop.setTitle(name);
        }
    }

    private void getLqData() {
        OkHttpUtils
                .get()
                .url("http://api.caipiao.163.com/live_jclq.html")
                .addParams("mobileType", "android")
                .addParams("channel", "qq_tab1")
                .addParams("apiLevel", "27")
                .addParams("apiVer", "1.1")
                .addParams("ver", "4.31")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String message = e.getMessage();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        LqMatchInfoResult result = gson.fromJson(response, LqMatchInfoResult.class);
                        if (result != null && result.result == 100 &&
                                result.data.get(0) != null && !CommUtil.isEmpty(result.data.get(0).matchInfo)) {
                            mLqAdapter.setData(result.data.get(0).matchInfo);
                        }
                    }
                });
    }

    private void getZqData() {
        OkHttpUtils
                .get()
                .url("http://api.caipiao.163.com/jjc_live.html")
                .addParams("gameEn", "jczq")
                .addParams("product", "caipiao_client")
                .addParams("mobileType", "iphone")
                .addParams("ver", "4.33")
                .addParams("channel", "appstore")
                .addParams("apiVer", "1.1")
                .addParams("apiLevel", "27")
                .addParams("deviceId", "9D2FC859-2CA2-4EF3-96DC-5B7EE672869E")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String message = e.getMessage();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MatchInfoResult result = gson.fromJson(response, MatchInfoResult.class);
                        if (result.result == 100 && !CommUtil.isEmpty(result.data) && result.data.get(0) != null && !CommUtil.isEmpty(result.data.get(0).matchInfo)) {
                            mZqAdapter.setData(result.data.get(0).matchInfo);
                        }
                    }
                });
    }

}

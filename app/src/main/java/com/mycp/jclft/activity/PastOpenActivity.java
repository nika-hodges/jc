package com.mycp.jclft.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.OpenAdapter;
import com.mycp.jclft.entity.OpenBean;
import com.mycp.jclft.entity.OpenResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class PastOpenActivity extends AppCompatActivity {

    private ListView mPastLv;
    private ArrayList<OpenBean> mData;
    private OpenAdapter mOpenAdapter;
    private String mCode;
    private TopBarView mTop;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_open);

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
        mCode = intent.getStringExtra("code");
        mName = intent.getStringExtra("name");
        initView();
        initData();
    }

    private void initView() {
        mTop = (TopBarView) findViewById(R.id.view_top);
        mTop.getLeftLayout().setVisibility(View.VISIBLE);
        mTop.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTop.getRightLayout().setVisibility(View.GONE);
        mPastLv = (ListView) findViewById(R.id.lv_past);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mName = intent.getStringExtra("name");
        mCode = intent.getStringExtra("code");
        if ("rx9".equals(mCode)) {
            mTop.getRightLayout().setVisibility(View.GONE);
        }
        mTop.setTitle(mName);
    }

    private void initData() {
        mTop.setTitle(mName);
        if ("rx9".equals(mCode)) {
            mTop.getRightLayout().setVisibility(View.GONE);
        }
        mOpenAdapter = new OpenAdapter(this);
        OkHttpUtils
                .get()
                .url("http://route.showapi.com/44-2")
                .addParams("showapi_appid", "49035")
                .addParams("showapi_sign", "6f6b85bce5e347139a9fc1affb25abd1")
                .addParams("code", mCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        OpenResult openResult = gson.fromJson(response, OpenResult.class);
                        if (openResult != null &&
                                openResult.showapi_res_code == 0
                                && openResult.showapi_res_body != null
                                && openResult.showapi_res_body.ret_code == 0
                                && !CommUtil.isEmpty(openResult.showapi_res_body.result)) {
                            mData = new ArrayList<>();
                            for (OpenBean openBean : openResult.showapi_res_body.result) {
                                if (openBean != null
                                        && !TextUtils.isEmpty(openBean.code)
                                        && ("ssq".equals(openBean.code)
                                        || "dlt".equals(openBean.code)
                                        || "fcd".equals(openBean.code)
                                        || "pl3".equals(openBean.code)
                                        || "pl5".equals(openBean.code)
                                        || "qxc".equals(openBean.code)
                                        || "qlc".equals(openBean.code))) {
                                    mData.add(openBean);
                                }
                            }
                            mPastLv.setAdapter(mOpenAdapter);
                            mOpenAdapter.setData(mData);
                        }
                    }
                });
        mPastLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PastOpenActivity.this, LastOpenActivity.class);
                intent.putExtra("name", mData.get(position).name);
                startActivity(intent);
            }
        });
    }
}

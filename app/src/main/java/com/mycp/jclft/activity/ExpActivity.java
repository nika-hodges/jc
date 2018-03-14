package com.mycp.jclft.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.NewsAdapter;
import com.mycp.jclft.entity.NewsBean;
import com.mycp.jclft.entity.NewsResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by leo on 2018/3/13.
 */

public class ExpActivity extends AppCompatActivity {
    private NewsAdapter mNewsAdapter;
    private List<NewsBean> mData;
    private ListView mExpLv;
    private TopBarView mTop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp);
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
        mTop.setTitle("专家预测");
        mTop.getLeftLayout().setVisibility(View.VISIBLE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mTop.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mExpLv = (ListView) findViewById(R.id.lv_exp);
    }

    private void initData() {
        mNewsAdapter = new NewsAdapter(this);
        OkHttpUtils
                .get()
                .url("http://api.caipiao.163.com/getNewsList.html")
                .addParams("product", "caipiao_client")
                .addParams("pageNo", "1")
                .addParams("subClass", "dongtai")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Exception e1 = e;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NewsResult newsResult = gson.fromJson(response, NewsResult.class);
                        if (newsResult != null
                                && !TextUtils.isEmpty(newsResult.result)
                                && !CommUtil.isEmpty(newsResult.news)
                                && "100".equals(newsResult.result)) {
                            mData = newsResult.news;
                            mNewsAdapter.setData(newsResult.news);
                            mExpLv.setAdapter(mNewsAdapter);
                        }
                    }
                });

        mExpLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mData != null && mData.get(position) != null) {
                    Intent intent = new Intent(ExpActivity.this, WebActivity.class);
                    intent.putExtra(WebActivity.URL, mData.get(position).wapLink);
                    startActivity(intent);
                }
            }
        });
    }
}

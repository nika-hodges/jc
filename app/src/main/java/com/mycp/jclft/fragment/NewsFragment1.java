package com.mycp.jclft.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.activity.WebActivity;
import com.mycp.jclft.adapter.NewsAdapter;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.entity.NewsBean;
import com.mycp.jclft.entity.NewsResult;
import com.mycp.jclft.utils.CommUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by leo on 2018/3/12.
 */

public class NewsFragment1 extends BaseFragment {
    private static final String POSITION = "position";
    private ListView mNewsLv;
    private NewsAdapter mNewsAdapter;
    private List<NewsBean> mData;
    private String mName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            mName = arguments.getString(POSITION);
        }
    }

    @Override
    protected void initView(View view) {
        mNewsLv = (ListView) view.findViewById(R.id.lv_news);

    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_three;
    }

    @Override
    protected void initData() {
        mNewsAdapter = new NewsAdapter(getActivity());
        OkHttpUtils
                .get()
                .url("http://api.caipiao.163.com/getNewsList.html")
                .addParams("product", "caipiao_client")
                .addParams("pageNo", "1")
                .addParams("subClass", mName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

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
                            mNewsLv.setAdapter(mNewsAdapter);
                            mNewsAdapter.setData(newsResult.news);
                        }
                    }
                });

        mNewsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mData != null && mData.get(position) != null) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra(WebActivity.URL, mData.get(position).wapLink);
                    startActivity(intent);
                }
            }
        });
    }

    public static NewsFragment1 newInstance(String name) {
        NewsFragment1 fragment = new NewsFragment1();
        Bundle args = new Bundle();
        args.putString(POSITION, name);
        fragment.setArguments(args);
        return fragment;
    }
}

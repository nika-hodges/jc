package com.mycp.jclft.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.activity.WebActivity;
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
 * Des:
 * Author: leo
 * Date：2018/2/27.
 */

public class NewsFragment extends Fragment {

    private ListView mNewsLv;
    private NewsAdapter mNewsAdapter;
    private List<NewsBean> mData;
    private TopBarView mTop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        initView(view);
        initData();
        return view;

    }

    private void initView(View view) {
        mTop = (TopBarView) view.findViewById(R.id.view_top);
        mTop.setTitle("资讯");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mNewsLv = (ListView) view.findViewById(R.id.lv_news);
    }

    private void initData() {
        if (getActivity() != null) {
            mNewsAdapter = new NewsAdapter(getActivity());
        }
        OkHttpUtils
                .get()
                .url("http://api.caipiao.163.com/getNewsList.html")
                .addParams("product", "caipiao_client")
                .addParams("pageNo", "1")
                .addParams("subClass", "ssq")
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
}

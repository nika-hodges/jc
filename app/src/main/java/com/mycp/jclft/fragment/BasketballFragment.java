package com.mycp.jclft.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.LqAdapter;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.entity.LqMatchInfoResult;
import com.mycp.jclft.utils.CommUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by leo on 2018/3/20.
 */

public class BasketballFragment extends BaseFragment {

    private ListView mBasketballLv;
    private LqAdapter mLqAdapter;

    @Override
    protected void initView(View view) {
        mBasketballLv = (ListView) view.findViewById(R.id.lv_basketball);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_basketball;
    }

    @Override
    protected void initData() {
        if (mLqAdapter == null) {
            mLqAdapter = new LqAdapter(getActivity());
        }
        mBasketballLv.setAdapter(mLqAdapter);
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
}

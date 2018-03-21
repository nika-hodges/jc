package com.mycp.jclft.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.adapter.ZqAdapter;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.entity.MatchInfoResult;
import com.mycp.jclft.utils.CommUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by leo on 2018/3/20.
 */

public class FootballFragment extends BaseFragment {

    private ListView mFootBallLv;
    private ZqAdapter mZqAdapter;

    @Override
    protected void initView(View view) {
        mFootBallLv = (ListView) view.findViewById(R.id.lv_football);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_football;
    }

    @Override
    protected void initData() {
        if (mZqAdapter == null) {
            mZqAdapter = new ZqAdapter(getActivity());
        }
        mFootBallLv.setAdapter(mZqAdapter);
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

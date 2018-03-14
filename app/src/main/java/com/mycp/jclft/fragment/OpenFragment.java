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
import com.mycp.jclft.activity.LastOpenActivity;
import com.mycp.jclft.adapter.OpenAdapter;
import com.mycp.jclft.entity.OpenBean;
import com.mycp.jclft.entity.OpenResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Des:
 * Author: leo
 * Date：2018/2/27.
 */

public class OpenFragment extends Fragment {

    private TopBarView mTop;
    private OpenAdapter mOpenAdapter;
    private ListView mOpenLV;
    private List<OpenBean> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mTop = (TopBarView) view.findViewById(R.id.top);
        mTop.setTitle("开奖信息");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mOpenLV = (ListView) view.findViewById(R.id.lv_open);
    }

    private void initData() {
        if (getActivity() != null) {
            mOpenAdapter = new OpenAdapter(getActivity());
        }
        OkHttpUtils
                .get()
                .url("http://route.showapi.com/44-1")
                .addParams("showapi_appid", "49035")
                .addParams("showapi_sign", "6f6b85bce5e347139a9fc1affb25abd1")
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
                            mOpenLV.setAdapter(mOpenAdapter);
                            mOpenAdapter.setData(mData);
                        }
                    }
                });
        mOpenLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), LastOpenActivity.class);
                intent.putExtra("name", mData.get(position).name);
                intent.putExtra("code", mData.get(position).code);
                startActivity(intent);
            }
        });
    }
}

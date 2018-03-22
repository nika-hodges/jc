package com.mycp.jclft.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycp.jclft.R;
import com.mycp.jclft.activity.ExpActivity;
import com.mycp.jclft.activity.HelpActivity;
import com.mycp.jclft.activity.LastOpenActivity;
import com.mycp.jclft.activity.NewsActivity;
import com.mycp.jclft.activity.OpenActivity;
import com.mycp.jclft.activity.PastOpenActivity;
import com.mycp.jclft.activity.SettingActivity;
import com.mycp.jclft.activity.SportActivity1;
import com.mycp.jclft.adapter.OpenNumberAdapterLittle;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.entity.NearlyLotteryResult;
import com.mycp.jclft.utils.CommUtil;
import com.mycp.jclft.view.TopBarView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.NumberFormat;

import okhttp3.Call;

/**
 * Created by leo on 2018/3/11.
 */

public class DiscoveryFragment extends BaseFragment implements View.OnClickListener {
    private TopBarView mTop;
    private RecyclerView mNumbers;
    private TextView mExpectTv;
    private OpenNumberAdapterLittle mOpenNumberAdapter;

    @Override
    protected void initView(View view) {
        mTop = (TopBarView) view.findViewById(R.id.view_top);
        mTop.setTitle("发现");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);

        mExpectTv = (TextView) view.findViewById(R.id.tv_expect);
        mNumbers = (RecyclerView) view.findViewById(R.id.rv_number);
        mNumbers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        view.findViewById(R.id.rl_news).setOnClickListener(this);
        view.findViewById(R.id.rl_exp).setOnClickListener(this);
        view.findViewById(R.id.rl_help).setOnClickListener(this);
        view.findViewById(R.id.rl_setting).setOnClickListener(this);
        view.findViewById(R.id.rl_bifen).setOnClickListener(this);
        view.findViewById(R.id.rv_num).setOnClickListener(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initData() {
        OkHttpUtils
                .get()
                .url("http://apicloud.mob.com/lottery/query")
                .addParams("key", "220bb0a30d947")
                .addParams("name", "双色球")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        NearlyLotteryResult result = gson.fromJson(response, NearlyLotteryResult.class);
                        if (result == null || !"200".equals(result.retCode)) {
                            return;
                        }
                        if (result.result != null && !CommUtil.isEmpty(result.result.lotteryDetails) && !CommUtil.isEmpty(result.result.lotteryNumber)) {
                            if (!TextUtils.isEmpty(result.result.name)) {
                                result.result.lotteryNumber.set(result.result.lotteryNumber.size() - 1, result.result.lotteryNumber.get(result.result.lotteryNumber.size() - 1) + "特别");
                            }
                        }
                        if (!TextUtils.isEmpty(result.result.period)) {
                            mExpectTv.setText(getActivity().getResources().getString(R.string.period, result.result.period));
                        }
                        if (mOpenNumberAdapter == null) {
                            mOpenNumberAdapter = new OpenNumberAdapterLittle(getActivity());
                        }
                        mNumbers.setAdapter(mOpenNumberAdapter);
                        mOpenNumberAdapter.setData(result.result.lotteryNumber);
                    }

                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_news:
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_exp:
                Intent intent1 = new Intent(getActivity(), ExpActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_help:
                Intent intent2 = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_setting:
                Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_bifen:
                Intent intent4 = new Intent(getActivity(), SportActivity1.class);
                startActivity(intent4);
                break;
            case R.id.rv_num:
                Intent intent5 = new Intent(getActivity(), OpenActivity.class);
                startActivity(intent5);
                break;
        }
    }
}

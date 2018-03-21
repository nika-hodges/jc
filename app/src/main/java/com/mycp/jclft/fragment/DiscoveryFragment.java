package com.mycp.jclft.fragment;

import android.content.Intent;
import android.view.View;

import com.mycp.jclft.R;
import com.mycp.jclft.activity.ExpActivity;
import com.mycp.jclft.activity.HelpActivity;
import com.mycp.jclft.activity.NewsActivity;
import com.mycp.jclft.activity.SettingActivity;
import com.mycp.jclft.activity.SportActivity1;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.view.TopBarView;

/**
 * Created by leo on 2018/3/11.
 */

public class DiscoveryFragment extends BaseFragment implements View.OnClickListener {
    private TopBarView mTop;

    @Override
    protected void initView(View view) {
        mTop = (TopBarView) view.findViewById(R.id.view_top);
        mTop.setTitle("发现");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);
        view.findViewById(R.id.rl_news).setOnClickListener(this);
        view.findViewById(R.id.rl_exp).setOnClickListener(this);
        view.findViewById(R.id.rl_help).setOnClickListener(this);
        view.findViewById(R.id.rl_setting).setOnClickListener(this);
        view.findViewById(R.id.rl_bifen).setOnClickListener(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initData() {

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
        }
    }
}

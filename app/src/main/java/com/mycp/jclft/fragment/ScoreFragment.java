package com.mycp.jclft.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.adapter.LqAdapter;
import com.mycp.jclft.adapter.SportAdapter;
import com.mycp.jclft.adapter.ZqAdapter;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2018/3/21.
 */

public class ScoreFragment extends BaseFragment implements View.OnClickListener {
    private TopBarView mTop;
    private List<Fragment> mFragments;
    private RelativeLayout mZqRl;
    private TextView mZqTv;
    private View mZqV;
    private RelativeLayout mLqRl;
    private TextView mLqTv;
    private View mLqV;
    private FootballFragment mFootballFragment;
    private BasketballFragment mBasketballFragment;

    @Override
    protected void initView(View view) {
        mTop = (TopBarView) view.findViewById(R.id.view_top);
        mTop.setTitle("实时比分");
        mTop.getLeftLayout().setVisibility(View.GONE);
        mTop.getRightLayout().setVisibility(View.GONE);
        mZqRl = (RelativeLayout) view.findViewById(R.id.rl_zq);
        mLqRl = (RelativeLayout) view.findViewById(R.id.rl_lq);
        mZqTv = (TextView) view.findViewById(R.id.tv_zq);
        mLqTv = (TextView) view.findViewById(R.id.tv_lq);
        mZqV = view.findViewById(R.id.view_zq);
        mLqV = view.findViewById(R.id.view_lq);
        mZqRl.setOnClickListener(this);
        mLqRl.setOnClickListener(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_score;
    }

    @Override
    protected void initData() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mFootballFragment == null) {
            mFootballFragment = new FootballFragment();
        }
        if (mBasketballFragment == null) {
            mBasketballFragment = new BasketballFragment();
        }
        transaction.add(R.id.view_container, mFootballFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_zq:
                mZqTv.setTextColor(getActivity().getResources().getColor(R.color.red));
                mLqTv.setTextColor(getActivity().getResources().getColor(R.color.common_60));
                mZqV.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                mLqV.setBackgroundColor(getActivity().getResources().getColor(R.color.circle_remark_publish_bg));
                if (!mFootballFragment.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(mFootballFragment)
                            .add(R.id.view_container, mFootballFragment)
                            .commit();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(mBasketballFragment)
                            .show(mFootballFragment)
                            .commit();
                }
                break;
            case R.id.rl_lq:
                mLqTv.setTextColor(getActivity().getResources().getColor(R.color.red));
                mZqTv.setTextColor(getActivity().getResources().getColor(R.color.common_60));
                mLqV.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
                mZqV.setBackgroundColor(getActivity().getResources().getColor(R.color.circle_remark_publish_bg));
                if (!mBasketballFragment.isAdded()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(mFootballFragment)
                            .add(R.id.view_container, mBasketballFragment)
                            .commit();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(mFootballFragment)
                            .show(mBasketballFragment)
                            .commit();
                }
                break;
            default:
                break;
        }

    }
}

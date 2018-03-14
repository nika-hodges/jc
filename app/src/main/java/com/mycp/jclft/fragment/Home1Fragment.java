package com.mycp.jclft.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mycp.jclft.R;
import com.mycp.jclft.activity.LastOpenActivity;
import com.mycp.jclft.activity.SportActivity;
import com.mycp.jclft.base.BaseFragment;
import com.mycp.jclft.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;

/**
 * Created by leo on 2018/3/11.
 */

public class Home1Fragment extends BaseFragment implements View.OnClickListener {
    private ArrayList<Integer> BannerList = new ArrayList<>();
    private RelativeLayout mSsqRl;
    private RelativeLayout mDltRl;
    private RelativeLayout mPl3Rl;
    private RelativeLayout mPl5Rl;
    private RelativeLayout mQxcRl;
    private RelativeLayout mQlcRl;
    private RelativeLayout mZqRl;
    private RelativeLayout mLqRl;
    private RelativeLayout mRx9Rl;
    private RelativeLayout mSfcRl;
    private RelativeLayout mZqdcRl;
    private RelativeLayout m3DRl;

    @Override
    protected void initView(View view) {
        Banner banner = (Banner) view.findViewById(R.id.banner);
        BannerList.add(R.drawable.file1);
        BannerList.add(R.drawable.file3);
        BannerList.add(R.drawable.file2);
        BannerList.add(R.drawable.banner4);
        banner.setImages(BannerList)
                .setImageLoader(new GlideImageLoader())
                .start();

        mSsqRl = (RelativeLayout) view.findViewById(R.id.rl_ssq);
        mDltRl = (RelativeLayout) view.findViewById(R.id.rl_dlt);
        m3DRl = (RelativeLayout) view.findViewById(R.id.rl_3d);
        mPl3Rl = (RelativeLayout) view.findViewById(R.id.rl_pl3);
        mPl5Rl = (RelativeLayout) view.findViewById(R.id.rl_pl5);
        mQxcRl = (RelativeLayout) view.findViewById(R.id.rl_qxc);
        mQlcRl = (RelativeLayout) view.findViewById(R.id.rl_qlc);
        mZqRl = (RelativeLayout) view.findViewById(R.id.rl_jczq);
        mLqRl = (RelativeLayout) view.findViewById(R.id.rl_jclq);

        mRx9Rl = (RelativeLayout) view.findViewById(R.id.rl_rx9);
        mSfcRl = (RelativeLayout) view.findViewById(R.id.rl_sfc);
        mZqdcRl = (RelativeLayout) view.findViewById(R.id.rl_zqdc);

        m3DRl.setOnClickListener(this);
        mSsqRl.setOnClickListener(this);
        mDltRl.setOnClickListener(this);
        mPl3Rl.setOnClickListener(this);
        mPl5Rl.setOnClickListener(this);
        mQxcRl.setOnClickListener(this);
        mQlcRl.setOnClickListener(this);
        mZqRl.setOnClickListener(this);
        mLqRl.setOnClickListener(this);
        mRx9Rl.setOnClickListener(this);
        mSfcRl.setOnClickListener(this);
        mZqdcRl.setOnClickListener(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home1;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_ssq:
                jumpToLastOpenActivity("双色球");
                break;
            case R.id.rl_dlt:
                jumpToLastOpenActivity("大乐透");
                break;
            case R.id.rl_3d:
                jumpToLastOpenActivity("3D");
                break;
            case R.id.rl_pl3:
                jumpToLastOpenActivity("排列3");
                break;
            case R.id.rl_pl5:
                jumpToLastOpenActivity("排列5");
                break;
            case R.id.rl_qxc:
                jumpToLastOpenActivity("七星彩");
                break;
            case R.id.rl_qlc:
                jumpToLastOpenActivity("七乐彩");
                break;
            case R.id.rl_sfc:
                jumpToLastOpenActivity("胜负彩");
                break;
            case R.id.rl_rx9:
                jumpToLastOpenActivity("任选9");
                break;
            case R.id.rl_jczq:
                Intent intent = new Intent(getActivity(), SportActivity.class);
                intent.putExtra("name", "竞猜足球");
                getActivity().startActivity(intent);
                break;
            case R.id.rl_jclq:
                Intent intent1 = new Intent(getActivity(), SportActivity.class);
                intent1.putExtra("name", "竞猜篮球");
                getActivity().startActivity(intent1);
                break;
            case R.id.rl_zqdc:
                Intent intent2 = new Intent(getActivity(), SportActivity.class);
                intent2.putExtra("name", "足球单场");
                getActivity().startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void jumpToLastOpenActivity(String name) {
        if (getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed()) {
            return;
        }
        Intent intent = new Intent(getActivity(), LastOpenActivity.class);
        intent.putExtra("name", name);
        getActivity().startActivity(intent);
    }
}

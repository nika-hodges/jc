package com.mycp.jclft.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.adapter.NewsFragmentAdapter;
import com.mycp.jclft.fragment.NewsFragment1;
import com.mycp.jclft.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2018/3/12.
 */

public class NewsActivity extends AppCompatActivity {
    private TopBarView mTopBar;
    private TabLayout mTab;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private String mUsername;
    private NewsFragmentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
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

    public void initView() {
        mTopBar = (TopBarView) findViewById(R.id.view_top);
        mTopBar.setTitle("资讯");
        mTopBar.getLeftLayout().setVisibility(View.VISIBLE);
        mTopBar.getRightLayout().setVisibility(View.GONE);
        mTopBar.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTab = (TabLayout) findViewById(R.id.view_tab_layout);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        initFragments();
        initTabData();
        mAdapter = new NewsFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        mTab.getTabAt(0).setText(mTabs.get(0));
        mTab.getTabAt(1).setText(mTabs.get(1));
        mTab.getTabAt(2).setText(mTabs.get(2));
        mTab.getTabAt(3).setText(mTabs.get(3));
        mTab.getTabAt(4).setText(mTabs.get(4));
        mTab.getTabAt(5).setText(mTabs.get(5));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(7);
//        setTabStyle();
//        setTabStyle(mTab.getTabAt(0), true);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabStyle(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabStyle(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        String name = "";
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    name = "toutiao";
                    break;
                case 1:
                    name = "hangye";
                    break;
                case 2:
                    name = "ssq";
                    break;
                case 3:
                    name = "dlt";
                    break;
                case 4:
                    name = "3d";
                    break;
                case 5:
                    name = "pl3";
                    break;
                default:
                    name = "toutiao";
                    break;
            }
            NewsFragment1 newsFragment1 = NewsFragment1.newInstance(name);
            mFragments.add(newsFragment1);
        }
    }

    private void initTabData() {
        mTabs = new ArrayList<>();
        mTabs.add("头条");
        mTabs.add("行业");
        mTabs.add("双色球");
        mTabs.add("大乐透");
        mTabs.add("福彩3D");
        mTabs.add("排列3");

    }

    public void initData() {
    }



    /**
     * 设置动态TabLayout样式
     *
     * @param tab
     */
    private void setTabStyle(TabLayout.Tab tab, boolean isSelected) {
        if (tab == null) {
            return;
        }
        View sView = tab.getCustomView();
        if (sView != null) {
            TextView sText = (TextView) sView.findViewById(R.id.tab_title);
            if (isSelected) {
                sText.setTextColor(getResources().getColor(R.color.common_33));
                sText.getPaint().setFakeBoldText(true);
            } else {
                sText.setTextColor(getResources().getColor(R.color.color_e8));
                sText.getPaint().setFakeBoldText(false);
            }
            View divideV = sView.findViewById(R.id.view_indicator);
            divideV.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        }
    }
}

package com.mycp.jclft.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mycp.jclft.R;
import com.mycp.jclft.adapter.HomeFragmentAdapter;
import com.mycp.jclft.fragment.DiscoveryFragment;
import com.mycp.jclft.fragment.Home1Fragment;
import com.mycp.jclft.fragment.Home2Fragment;
import com.mycp.jclft.fragment.HomeFragment;
import com.mycp.jclft.fragment.NewsFragment;
import com.mycp.jclft.fragment.OpenFragment;
import com.mycp.jclft.fragment.OpenFragment1;
import com.mycp.jclft.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView mTab;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        mTab = (BottomNavigationView) findViewById(R.id.view_tab_layout);
        BottomNavigationViewHelper.disableShiftMode(mTab);
        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        initFragments();
        mViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager(), mFragments));
        mTab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btm_nav_item1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.btm_nav_item2:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.btm_nav_item3:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(4);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        Home2Fragment home2Fragment = new Home2Fragment();
//        Home1Fragment home1Fragment = new Home1Fragment();
        OpenFragment1 openFragment1 = new OpenFragment1();
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
//        NewsFragment fragment3 = new NewsFragment();
//        MyFragment fragment4 = new MyFragment();
        mFragments.add(home2Fragment);
        mFragments.add(openFragment1);
        mFragments.add(discoveryFragment);
//        mFragments.add(fragment4);
    }
}

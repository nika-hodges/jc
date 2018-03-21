package com.mycp.jclft.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.mycp.jclft.activity.SportActivity1;

import java.util.List;

/**
 * Created by leo on 2018/3/20.
 */

public class SportAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mData;

    public SportAdapter(FragmentManager supportFragmentManager, Context context) {
        super(supportFragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Fragment> data) {
        mData = data;
    }
}

package com.mycp.jclft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.entity.LqMatchInfo;

import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/9.
 */

public class LqAdapter extends BaseAdapter {

    private final Context mContext;
    private List<LqMatchInfo> mData;

    public LqAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mData == null) {
            return null;
        }
        if (position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lq1, parent, false);
            viewHolder.mMatchTv = (TextView) convertView.findViewById(R.id.tv_match);
            viewHolder.mStatus = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.mTeamNameA = (TextView) convertView.findViewById(R.id.tv_name_a);
            viewHolder.mScoreA = (TextView) convertView.findViewById(R.id.tv_score_a);
            viewHolder.mTeamNameB = (TextView) convertView.findViewById(R.id.tv_name_b);
            viewHolder.mScoreB = (TextView) convertView.findViewById(R.id.tv_score_b);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LqMatchInfo lqMatchInfo = mData.get(position);
        if (!TextUtils.isEmpty(lqMatchInfo.statusDesc)) {
            viewHolder.mStatus.setText(lqMatchInfo.statusDesc);
            if ("直播中".equals(lqMatchInfo.statusDesc)) {
                viewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            } else if ("已结束".equals(lqMatchInfo.statusDesc)) {
                viewHolder.mStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
            }
        }
        if (!TextUtils.isEmpty(lqMatchInfo.leagueName)) {
            viewHolder.mMatchTv.setText(lqMatchInfo.leagueName);
        }
        if (!TextUtils.isEmpty(lqMatchInfo.hostName)) {
            viewHolder.mTeamNameA.setText(lqMatchInfo.hostName);
        }
        if (!TextUtils.isEmpty(lqMatchInfo.hostGoal)) {
            viewHolder.mScoreA.setText(lqMatchInfo.hostGoal);
        }
        if (!TextUtils.isEmpty(lqMatchInfo.visitGoal)) {
            viewHolder.mScoreB.setText(lqMatchInfo.visitGoal);
        }
        if (!TextUtils.isEmpty(lqMatchInfo.visitName)) {
            viewHolder.mTeamNameB.setText(lqMatchInfo.visitName);
        }
        if (!TextUtils.isEmpty(lqMatchInfo.matchDay)) {
            viewHolder.mTime.setText(lqMatchInfo.matchDay);
        }
        return convertView;
    }

    public void setData(List<LqMatchInfo> data) {
        mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mMatchTv;
        TextView mStatus;
        TextView mTeamNameA;
        TextView mScoreA;
        TextView mTeamNameB;
        TextView mScoreB;
        TextView mTime;
    }
}

package com.mycp.jclft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.entity.MatchInfo;

import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/9.
 */

public class ZqAdapter extends BaseAdapter {

    private final Context mContext;
    private List<MatchInfo> mData;

    public ZqAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sport, parent, false);
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
        MatchInfo matchInfo = mData.get(position);
        if (!TextUtils.isEmpty(matchInfo.statusDesc)) {
            viewHolder.mStatus.setText(matchInfo.statusDesc);
        }
        if (!TextUtils.isEmpty(matchInfo.leagueName)) {
            viewHolder.mMatchTv.setText(matchInfo.leagueName);
        }
        if (!TextUtils.isEmpty(matchInfo.teamA)) {
            viewHolder.mTeamNameA.setText(matchInfo.teamA);
        }
        if (!TextUtils.isEmpty(matchInfo.teamAGoal)) {
            viewHolder.mScoreA.setText(matchInfo.teamAGoal);
        }
        if (!TextUtils.isEmpty(matchInfo.teamBGoal)) {
            viewHolder.mScoreB.setText(matchInfo.teamBGoal);
        }
        if (!TextUtils.isEmpty(matchInfo.teamB)) {
            viewHolder.mTeamNameB.setText(matchInfo.teamB);
        }
        if (!TextUtils.isEmpty(matchInfo.matchDay)) {
            viewHolder.mTime.setText(matchInfo.matchTime);
        }
        return convertView;
    }

    public void setData(List<MatchInfo> matchInfo) {
        mData = matchInfo;
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

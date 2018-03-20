package com.mycp.jclft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.entity.LotteryDetails;

import java.text.NumberFormat;
import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/5.
 */

public class OpenDetailAdapter extends BaseAdapter {

    private List<LotteryDetails> mData;
    private final Context mContext;

    public OpenDetailAdapter(Context context) {
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
        OpenDetailViewHolder holder = null;
        if (convertView == null) {
            holder = new OpenDetailViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_price, parent, false);
            holder.awardsTv = (TextView) convertView.findViewById(R.id.tv_awards);
            holder.awardsPriceTv = (TextView) convertView.findViewById(R.id.tv_award_price);
            holder.awardNumberTv = (TextView) convertView.findViewById(R.id.tv_award_number);
            convertView.setTag(holder);
        } else {
            holder = (OpenDetailViewHolder) convertView.getTag();
        }
        LotteryDetails lotteryDetails = mData.get(position);
        if (lotteryDetails == null) {
            return null;
        }
        if (position % 2 == 0) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.common_f8));
        } else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        if (!TextUtils.isEmpty(lotteryDetails.awards)) {
            if (!TextUtils.isEmpty(lotteryDetails.type)) {
                holder.awardsTv.setText(lotteryDetails.awards + "(" + lotteryDetails.type + ")");
            } else {
                holder.awardsTv.setText(lotteryDetails.awards);
            }
        }
        NumberFormat format = NumberFormat.getNumberInstance();
        holder.awardsPriceTv.setText(format.format(lotteryDetails.awardPrice));
        holder.awardNumberTv.setText(format.format(lotteryDetails.awardNumber));
        return convertView;
    }

    public void setData(List<LotteryDetails> lotteryDetails) {
        mData = lotteryDetails;
        notifyDataSetChanged();
    }

    class OpenDetailViewHolder {
        TextView awardsTv;
        TextView awardsPriceTv;
        TextView awardNumberTv;
    }
}

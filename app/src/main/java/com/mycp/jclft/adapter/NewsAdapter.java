package com.mycp.jclft.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycp.jclft.R;
import com.mycp.jclft.entity.NewsBean;

import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/2/27.
 */

public class NewsAdapter extends BaseAdapter {
    private List<NewsBean> mData;
    private final Context mContext;

    public NewsAdapter(Context context) {
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
        if (mData.get(position) == null) {
            return null;
        }
        NewsBean newsBean = mData.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
            viewHolder.mCoverIv = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.mTimeTv = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mTitleTv = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mSubTitleTv = (TextView) convertView.findViewById(R.id.tv_sub_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(newsBean.imgLink)) {
            Glide.with(mContext)
                    .load(newsBean.imgLink)
                    .into(viewHolder.mCoverIv);
        }
        viewHolder.mTitleTv.setText(newsBean.title);
        viewHolder.mTimeTv.setText(newsBean.pubTime);

        return convertView;
    }

    public void setData(List<NewsBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView mTitleTv;
        TextView mSubTitleTv;
        TextView mTimeTv;
        ImageView mCoverIv;
    }
}

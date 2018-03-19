package com.mycp.jclft.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.entity.OpenBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/3.
 */

public class OpenAdapter extends BaseAdapter {
    private List<OpenBean> mData;
    private Context mContex;

    public OpenAdapter(Context context) {
        mContex = context;
    }

    public void setData(List<OpenBean> data) {
        mData = data;
        notifyDataSetChanged();
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
        OpenViewHolder holder = null;
        OpenNumberAdapter openNumberAdapter = null;
        if (convertView == null) {
            holder = new OpenViewHolder();
            convertView = LayoutInflater.from(mContex).inflate(R.layout.item_opne, parent, false);
            holder.iconIv = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time);
            holder.expectTv = (TextView) convertView.findViewById(R.id.tv_expect);
            holder.numberRv = (RecyclerView) convertView.findViewById(R.id.rv_number);
            holder.numberRv.setLayoutManager(new LinearLayoutManager(mContex, LinearLayoutManager.HORIZONTAL, false));
            convertView.setTag(holder);
        } else {
            holder = (OpenViewHolder) convertView.getTag();
        }
        OpenBean openBean = mData.get(position);
        if (openBean != null) {
            if (!TextUtils.isEmpty(openBean.name)) {
                holder.titleTv.setText(openBean.name);
            }
            switch (openBean.code) {
                case "ssq":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_ssq));
                    break;
                case "dlt":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_dlt));
                    break;
                case "fc3d":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_3d));
                    break;
                case "pl3":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_pls));
                    break;
                case "pl5":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_pl5));
                    break;
                case "qxc":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_qxc));
                    break;
                case "qlc":
                    holder.iconIv.setBackground(mContex.getResources().getDrawable(R.drawable.ic_qlc));
                    break;
            }
            if (!TextUtils.isEmpty(openBean.expect)) {
                holder.expectTv.setText(mContex.getResources().getString(R.string.expect, openBean.expect));
            }
            if (!TextUtils.isEmpty(openBean.time)) {
                if (openBean.time.contains(" ")) {
                    holder.timeTv.setText(openBean.time.substring(0, openBean.time.indexOf(" ")));
                }
            }
            if (!TextUtils.isEmpty(openBean.openCode)) {
                List<String> data = new ArrayList<>();
                if (openBean.openCode.contains("+")) {
                    String[] split = openBean.openCode.split("\\+");
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            if (split[i].length() > 0) {
                                String[] split1 = split[i].split(",");
                                for (String s : split1) {
                                    data.add(s);
                                }
                            }
                        } else {
                            if (split[i].length() > 0) {
                                String[] split1 = split[i].split(",");
                                for (String s : split1) {
                                    data.add(s + "特别");
                                }
                            }
                        }
                    }
                } else if (openBean.openCode.contains(",")) {
                    String[] split = openBean.openCode.split(",");
                    for (String s : split) {
                        data.add(s);
                    }
                }
                if (openNumberAdapter == null) {
                    openNumberAdapter = new OpenNumberAdapter(mContex);
                }
//                if (openBean.name == "胜负彩" || openBean.name)
                holder.numberRv.setAdapter(openNumberAdapter);
                openNumberAdapter.setData(data);
            }
        }

        return convertView;
    }

    class OpenViewHolder {
        ImageView iconIv;
        TextView titleTv;
        TextView expectTv;
        TextView timeTv;
        RecyclerView numberRv;
    }
}

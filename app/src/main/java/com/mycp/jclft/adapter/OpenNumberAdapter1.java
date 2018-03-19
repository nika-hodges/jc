package com.mycp.jclft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycp.jclft.R;

import java.util.List;

/**
 * Created by leo on 2018/3/12.
 */

public class OpenNumberAdapter1 extends RecyclerView.Adapter<OpenNumberAdapter1.MyViewHolder> {
    private Context mContext;

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<String> data;

    public OpenNumberAdapter1(Context context) {
        mContext = context;
    }

    @Override
    public OpenNumberAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OpenNumberAdapter1.MyViewHolder myViewHolder = new OpenNumberAdapter1.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_opne_code1, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(data.get(position))) {
            if (data.get(position).contains("特别")) {
                holder.codeTv.setBackgroundResource(R.drawable.ic_bg_bifen);
                holder.codeTv.setText(data.get(position).substring(0, data.get(position).indexOf("特")));
            } else {
                holder.codeTv.setBackgroundResource(R.drawable.ic_bg_bifen);
                holder.codeTv.setText(data.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView codeTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            codeTv = (TextView) itemView.findViewById(R.id.tv_code);
        }
    }
}

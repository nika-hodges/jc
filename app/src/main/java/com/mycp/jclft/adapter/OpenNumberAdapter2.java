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
 * Created by leo on 2017/7/7.
 */
public class OpenNumberAdapter2 extends RecyclerView.Adapter<OpenNumberAdapter2.MyViewHolder> {

    private Context mContext;
    private final int mParentPos;

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<String> data;

    public OpenNumberAdapter2(Context context, int position) {
        mContext = context;
        mParentPos = position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_opne_code, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(data.get(position))) {
            if (0 == mParentPos) {
                if (data.get(position).contains("特别")) {
                    holder.codeTv.setBackgroundResource(R.drawable.ic_ball_blue2);
                    holder.codeTv.setText(data.get(position).substring(0, data.get(position).indexOf("特")));
                } else {
                    holder.codeTv.setBackgroundResource(R.drawable.ic_ball_red2);
                    holder.codeTv.setText(data.get(position));
                }
            } else {
                if (data.get(position).contains("特别")) {
                    holder.codeTv.setBackgroundResource(0);
                    holder.codeTv.setTextColor(mContext.getResources().getColor(R.color.circle_blue1));
                    holder.codeTv.setText(data.get(position).substring(0, data.get(position).indexOf("特")));
                } else {
                    holder.codeTv.setBackgroundResource(0);
                    holder.codeTv.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.codeTv.setText(data.get(position));
                }
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

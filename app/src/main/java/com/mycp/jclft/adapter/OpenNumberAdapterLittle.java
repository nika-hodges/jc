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
public class OpenNumberAdapterLittle extends RecyclerView.Adapter<OpenNumberAdapterLittle.MyViewHolder> {

    private Context mContext;

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<String> data;

    public OpenNumberAdapterLittle(Context context) {
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_opne_code_little, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(data.get(position))) {
            if (data.get(position).contains("特别")) {
                holder.codeTv.setBackgroundResource(R.drawable.ic_ball_blue3);
                holder.codeTv.setText(data.get(position).substring(0, data.get(position).indexOf("特")));
            } else {
                holder.codeTv.setBackgroundResource(R.drawable.ic_ball_red3);
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

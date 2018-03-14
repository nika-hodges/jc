package com.mycp.jclft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mycp.jclft.R;
import com.mycp.jclft.entity.QNABean;
import com.mycp.jclft.utils.DimenUtils;

import java.util.ArrayList;


public class CommonQuestionsAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<QNABean> dataList;

    private int currentItem = -1;

    public CommonQuestionsAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<QNABean> list) {
        dataList = list;
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_help, parent, false);
            holder.queRl = (RelativeLayout) convertView.findViewById(R.id.rl_help_que);
            holder.queTv = (TextView) convertView.findViewById(R.id.tv_help_que);
            holder.queIv = (ImageView) convertView.findViewById(R.id.iv_help);
            holder.anLl = (LinearLayout) convertView.findViewById(R.id.ll_help_an);
            holder.anTv = (TextView) convertView.findViewById(R.id.tv_help_an);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.queRl.setTag(position);
        QNABean bean = dataList.get(position);
        if (bean != null) {
            holder.queTv.setText(bean.question);
            holder.anTv.setText(bean.answer);
        }
        if (currentItem == position) {
            holder.anLl.setVisibility(View.VISIBLE);
            holder.queIv.setImageResource(R.drawable.usercenter_question_item_expand);
        } else {
            holder.anLl.setVisibility(View.GONE);
            holder.queIv.setImageResource(R.drawable.usercenter_question_item_packet);
        }

        holder.queRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (tag == currentItem) {
                    currentItem = -1;
                } else {
                    currentItem = tag;
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        RelativeLayout queRl;
        TextView queTv;
        ImageView queIv;
        LinearLayout anLl;
        TextView anTv;
    }
}

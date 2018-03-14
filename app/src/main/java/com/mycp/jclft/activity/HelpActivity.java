package com.mycp.jclft.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.mycp.jclft.R;
import com.mycp.jclft.adapter.CommonQuestionsAdapter;
import com.mycp.jclft.entity.QNABean;
import com.mycp.jclft.view.TopBarView;

import java.util.ArrayList;

/**
 * Created by leo on 2018/3/12.
 */

public class HelpActivity extends AppCompatActivity {
    private ArrayList<QNABean> mData = new ArrayList<>();
    private ListView mHelpLv;
    private TopBarView mTopBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (!isE_MUI3_1()) {
//                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
        initView();
        initData();
    }

    private void initView() {
        mTopBarView = (TopBarView) findViewById(R.id.view_top);
        mTopBarView.setTitle("常见问题");
        mTopBarView.getLeftLayout().setVisibility(View.VISIBLE);
        mTopBarView.getRightLayout().setVisibility(View.GONE);
        mTopBarView.getLeftLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mHelpLv = (ListView) findViewById(R.id.lv_help);
    }

    private void initData() {
        QNABean bean = new QNABean();
        QNABean bean1 = new QNABean();
        QNABean bean2 = new QNABean();
        QNABean bean3 = new QNABean();
        QNABean bean4 = new QNABean();
        bean.question = "在哪里查看我购买的彩票是否中奖?";
        bean.answer = "您可以在app--开奖查看最近一期开奖号码。";
        bean1.question = "中奖后会扣税吗？";
        bean1.answer = "根据彩票管理机构的相关规定,中奖彩票单注奖金超过1万元时，中奖人需要交纳奖金的20%作为个人偶然所得税；税金由彩票中心代扣代缴。";
        bean2.question = "中奖后如何兑奖？";
        bean2.answer = "您可以直接去投注站兑奖，大奖需由店主带领赴当地体彩福彩中心兑奖";
        bean3.question = "app上彩票信息准确吗？";
        bean3.answer = "我们的app会及时更新彩票中奖号码信息及业内资讯，所有信息均真实有效";
        mData.add(bean);
        mData.add(bean1);
        mData.add(bean2);
        mData.add(bean3);
        CommonQuestionsAdapter adapter = new CommonQuestionsAdapter(this);
        adapter.setData(mData);
        mHelpLv.setAdapter(adapter);
    }
}

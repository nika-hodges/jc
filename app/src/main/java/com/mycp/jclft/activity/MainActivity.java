package com.mycp.jclft.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mycp.jclft.R;
import com.mycp.jclft.receiver.MyInstalledReceiver;
import com.mycp.jclft.utils.CacheActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbupdate;
    private TextView pos;
    private AlertDialog dialog;
    private int Hight;
    private String appurl;
    private MyInstalledReceiver installedReceiver;
    private String url = "http://app.kaiguan1700.com/lottery/back/get_init_data.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CacheActivity.addActivity(this);
        String bagname= getResources().getString(R.string.bagname);
        try {
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(bagname);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (final Exception e) {
            initdata();
            //跳转接口
            OkHttpUtils
                    .get()
                    .url(url)
                    .addParams("type", "android")
                    .addParams("appid", "test")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }

                @Override
                public void onResponse(String response, int id) {
//                    Gson gson = new Gson();
//                    AppBean appBean = gson.fromJson(response, AppBean.class);
//                    String rt_code = appBean.getRt_code();
//                    String data = appBean.getData();
//                    //解密
//                    String uidFromBase64 = Base64Util.getUidFromBase64(data);
//                    DataBean dataBean = gson.fromJson(uidFromBase64, DataBean.class);
//                    if (rt_code.equals("200")){
//                        String h5url = dataBean.getUrl();
//                        String show_url = dataBean.getShow_url();
//                        //show_url为0就跳转自己的主页，否则跳走删包或者H5逻辑
//                        if (show_url.equals("0")){
//                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                            finish();
//                        }else {
//                            //h5url为空就走删包，否则跳h5
//                            if (TextUtils.isEmpty(h5url)){
//                                autoUpdate();
//                            }else {
//                                Intent intent = new Intent(MainActivity.this,WebActivity.class);
//                                intent.putExtra("url",h5url);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                        }
//                    }else {
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        finish();
//                    }
                }
            });
        }
    }
    private void initdata(){
        WindowManager wm = getWindowManager();
        Hight = wm.getDefaultDisplay().getHeight();
        appurl = getResources().getString(R.string.appurl);
    }
    //自动更新的方法
    private void autoUpdate() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_update, null);//获取自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        pbupdate = (ProgressBar) view.findViewById(R.id.pb_update);
        pos = (TextView) view.findViewById(R.id.tv_pos);
        //builder.setTitle("正在更新,请稍后...");//设置标题
        builder.setIcon(R.drawable.appicon);//设置图标
        builder.setView(view);//设置自定义样式布局到对话框
        builder.setCancelable(false);
        //获取dialog
        dialog = builder.create();
        dialog.show();//显示对话框
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.height = Hight - Hight / 5;
        dialog.getWindow().setAttributes(params);
        HttpUtils http = new HttpUtils();
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir, SystemClock.uptimeMillis() + ".apk");
        http.download(appurl, file.getAbsolutePath(), new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog.dismiss();
                Log.e("onFailure", "onFailure: " + s);
                Toast.makeText(MainActivity.this, "自动更新失败" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                pbupdate.setMax((int) total / 1024);
                pbupdate.setProgress((int) current / 1024);
                pos.setText((current * 100) / total + "%");
            }
        });
    }
    //广播监听新app安装,以便于卸载旧app
    @Override
    protected void onStart() {
        super.onStart();
        installedReceiver = new MyInstalledReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(installedReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (installedReceiver != null) {
            this.unregisterReceiver(installedReceiver);
        }
    }
}

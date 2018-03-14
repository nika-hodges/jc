package com.mycp.jclft.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mycp.jclft.R;
import com.mycp.jclft.base.BaseWebView;
import com.mycp.jclft.utils.LoadingDialog;


public class WebActivity extends AppCompatActivity {
    public static final String URL = "URL";
    private ProgressBar mProgressBar;
    private LoadingDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mDialog = new LoadingDialog(this);
        mDialog.show();
        mDialog.startAnimation();
        mDialog.setLoadingMsg("加载中。。。");
        BaseWebView webView = (BaseWebView) findViewById(R.id.home_image_text_webview);
        String url = getIntent().getStringExtra(URL);
        if (!TextUtils.isEmpty(url)) {
            //支持javascript
            webView.getSettings().setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webView.getSettings().setSupportZoom(true);
            // 设置出现缩放工具
            webView.getSettings().setBuiltInZoomControls(true);
            //扩大比例的缩放
            webView.getSettings().setUseWideViewPort(true);
            //自适应屏幕
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView.getSettings().setLoadWithOverviewMode(true);


            //如果不设置WebViewClient，请求会跳转系统浏览器
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                    //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                    //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242

//                    if (url.toString().contains("sina.cn")){
//                        view.loadUrl("http://ask.csdn.net/questions/178242");
//                        return true;
//                    }

                    return false;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                    //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        if (request.getUrl().toString().contains("sina.cn")){
//                            view.loadUrl("http://ask.csdn.net/questions/178242");
//                            return true;
//                        }
                    }

                    return false;
                }

            });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress > 95) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }
                }
            });
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

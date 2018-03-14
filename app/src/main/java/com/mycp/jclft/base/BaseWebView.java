package com.mycp.jclft.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by suning on 2018/2/6.
 */

public class BaseWebView extends WebView {

    private Context context;

    protected ProgressBar progressBar;

    protected WebViewLoadListener wvLoadListener;

    public interface WebViewLoadListener {
        void onPageFinished(final WebView view, String url);
    }

    public void setWvLoadListener(WebViewLoadListener wvLoadListener) {
        this.wvLoadListener = wvLoadListener;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public BaseWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        initWebSetting();
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient());
    }

    /**
     * 设置WebSetting
     */
    private void initWebSetting() {
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setBuiltInZoomControls(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);

        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);

        // 解决Https中有Http链接的问题setAppCacheEnabled
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

    }

    protected void addListener() {
        //下载
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }
}

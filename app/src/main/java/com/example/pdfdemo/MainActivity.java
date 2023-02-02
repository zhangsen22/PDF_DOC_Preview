package com.example.pdfdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by WangYi
 *
 * @Date : 2018/3/13
 * @Desc : 利用pdf.js预览文件demo
 */
public class MainActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //加载本地pdf文件
//      preViewPDF("file:///android_asset/demo.pdf");
        //加载允许跨域访问的pdf文件
        preViewPDF("https://lenovo-hs-uat.oss-cn-beijing.aliyuncs.com/crmdev/productEcc/%E9%99%84%E4%BB%B63%EF%BC%9A%E5%91%98%E5%B7%A5%E6%89%8B%E5%86%8CV2.4.pdf");
        //跨域加载文件 先将pdf下载到本地在加载
//        download("http://p5grppofr.bkt.clouddn.com/pdf-js-demo.pdf");
          //加载docx文件
//        preViewDOC("https://lenovo-hs-uat.oss-cn-beijing.aliyuncs.com/crmdev/productEcc/%E8%81%94%E6%83%B3%E8%AE%B0%E5%BD%95.docx");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = findViewById(R.id.webView);
        //去掉横向滚动条
        mWebView.setHorizontalScrollBarEnabled(false);
        //去掉纵向滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = mWebView.getSettings();
        //设置字体缩放倍数，默认100
        webSettings.setTextZoom(100);
        //支持 Js 使用
        webSettings.setJavaScriptEnabled(true);
        // 开启DOM缓存
        webSettings.setDomStorageEnabled(true);
        // 开启数据库缓存
        webSettings.setDatabaseEnabled(true);
        // 支持启用缓存模式
        webSettings.setAppCacheEnabled(true);
        // 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
        webSettings.setAppCacheMaxSize((8 * 1024 * 1024));
        // Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath());
        // 关闭密码保存提示功能
        webSettings.setSavePassword(false);
        // 支持缩放
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        // 设置 UserAgent 属性
        webSettings.setUserAgentString("");
        // 允许加载本地 html 文件/false
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    /**
     * 下载pdf文件到本地
     *
     * @param url 文件url
     */
    private void download(String url) {
        DownloadUtil.download(url, getCacheDir() + "/temp.pdf",
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final String path) {
                        Log.d("MainActivity", "onDownloadSuccess: " + path);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                preViewPDF(path);
                            }
                        });
                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.d("MainActivity", "onDownloading: " + progress);
                    }

                    @Override
                    public void onDownloadFailed(String msg) {
                        Log.d("MainActivity", "onDownloadFailed: " + msg);
                    }
                });
    }

    /**
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preViewPDF(String pdfUrl) {
        // 只使用pdf.js渲染功能，自定义预览UI界面  加载pdf
        mWebView.loadUrl("file:///android_asset/index.html?" + pdfUrl);

    }

    private void preViewDOC(String docUrl) {
        //加载docx
        mWebView.loadUrl("http://view.officeapps.live.com/op/view.aspx?src="+docUrl);

    }
}

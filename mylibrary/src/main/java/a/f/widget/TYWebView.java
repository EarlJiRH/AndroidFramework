package a.f.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import a.f.utils.constant.AFConfig;

/**
 * ================================================
 * 类名：a.f.widget
 * 时间：2021/7/20 15:26
 * 描述：自定义 WebView
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class TYWebView extends WebView {

    private TextView textView; // 标题
    private ProgressBar progressBar; // 进度
    private OnWebEventListener mOnWebEventListener;

    public TYWebView(Context context) {
        super(context);
        initTYWebView();
    }

    public TYWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initTYWebView();
    }

    /** 初始化 */
    @SuppressLint("SetJavaScriptEnabled")
    private void initTYWebView() {
        try {
            WebSettings webSettings = getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setGeolocationEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            setWebViewClient(new TYWebViewClient());
            setWebChromeClient(new TYWebChromeClient());
            setDownloadListener(new TYDownloadListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 设置 标题控件 */
    public void setTextViewTitle(TextView textViewTitle) {
        this.textView = textViewTitle;
    }

    /** 设置 进度控件 */
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /** 设置Web事件监听 */
    public void setOnWebEventListener(OnWebEventListener onWebEventListener) {
        this.mOnWebEventListener = onWebEventListener;
    }

    /** 设置禁止长按 */
    public void setBanLongPress(boolean isBan) {
        setOnLongClickListener(view -> isBan);
    }

    /** 清除缓存 */
    public void clearCache() {
        clearCache(true);
        clearFormData();
        clearHistory();
        clearSslPreferences();
    }

    @Override
    public void destroy() {
        super.destroy();
        textView = null;
        progressBar = null;
        mOnWebEventListener = null;
    }

    /** 自定义 WebViewClient */
    private class TYWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            try {
                if (url.startsWith("tel:")) {
                    ArmsUtils.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + url)));
                } else if (url.startsWith("sms:") || url.startsWith("mailto:")) {
                    ArmsUtils.startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
                } else {
                    webView.loadUrl(url);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return super.shouldOverrideUrlLoading(webView, url);
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
            if (progressBar != null) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
            }
            try {
                if (mOnWebEventListener != null) {
                    mOnWebEventListener.onPageFinished(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            super.onReceivedError(webView, webResourceRequest, webResourceError);
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
            }
        }

    }

    /** 自定义 WebChromeClient */
    private class TYWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView webView, String title) {
            super.onReceivedTitle(webView, title);
            try {
                if (textView != null) {
                    textView.setText(TextUtils.isEmpty(title) ? AFConfig.APP_NAME : title);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProgressChanged(WebView webView, int newProgress) {
            super.onProgressChanged(webView, newProgress);
            try {
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /** 自定义 DownloadListener */
    private class TYDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
            try {
                ArmsUtils.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** 网页事件监听 */
    public static abstract class OnWebEventListener {
        /** 网页加载完成 */
        public void onPageFinished(String url) throws Exception {
        }

        // 可扩展
    }

}

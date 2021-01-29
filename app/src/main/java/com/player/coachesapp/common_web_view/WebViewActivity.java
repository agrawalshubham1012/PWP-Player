package com.player.coachesapp.common_web_view;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.coachesapp.BaseActivity;
import com.player.coachesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    private String titleString = "";
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        try {
            Intent intent = getIntent();
            titleString = intent.getStringExtra("title");
            url = intent.getStringExtra("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setWebView();
    }

    private void setWebView() {
        title.setText(titleString);
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);
    }

    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        onBackPressed();
    }
}

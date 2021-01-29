package com.player.coachesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.player.coachesapp.common_web_view.MyWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.player.coachesapp.Utils.Utils.ShowAlert;

public class SummaryActivity extends BaseActivity {

    TextView nextBtn;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summey_screen);
        ButterKnife.bind(this);
        setWebView();

        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    startActivity(new Intent(SummaryActivity.this, SignInActivity.class));
                    finish();
                } else {
                    ShowAlert(SummaryActivity.this, "please select terms and conditions.");
//                    Toast.makeText(SummaryActivity.this, "please select terms and conditions.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setWebView() {
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl("https://practicewithpros.app/term_and_condition.html");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public class MyWebViewClient extends WebViewClient {
        private Context context;
        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showLoader();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideLoader();
        }
    }
}

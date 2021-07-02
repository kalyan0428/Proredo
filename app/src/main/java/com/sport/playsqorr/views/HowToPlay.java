package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sport.playsqorr.R;

public class HowToPlay extends AppCompatActivity implements View.OnClickListener {
    String title = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
        }
        init();
    }

    private void init() {


        title = getIntent().getExtras().getString("title");

        url = getIntent().getExtras().getString("url");


        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);

        toolbar_title_x.setText(title);

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);


//        WebView wb = findViewById(R.id.webu);
//
//        wb.loadUrl(url);
//        wb.setVerticalScrollBarEnabled(false);
//        wb.setHorizontalScrollBarEnabled(false);
//        WebSettings webSettings = wb.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        WebView htmlWebView = findViewById(R.id.webu);
        htmlWebView.setWebViewClient(new HowToPlay.CustomWebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
//        webSetting.setDisplayZoomControls(true);
        htmlWebView.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
//                overridePendingTransition(  R.anim.slide_down,R.anim.slide_up );
                finish();
                break;

        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

package com.erdr.rlbvideolib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

public class OpenMyPdf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_my_pdf);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://drive.google.com/viewerng/viewer?embedded=true&url="+getIntent().getStringExtra("MyPDFFile");
        // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
        webView.loadUrl(url);
    }
}

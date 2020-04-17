package com.erdr.rlbvideolib;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.content.ContextCompat;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ShowAllMyYoutubeVideo extends YouTubeBaseActivity {
YouTubePlayerView youTubePlayerView;
YouTubePlayer.OnInitializedListener onInitializedListener;
    String myURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_my_youtube_video);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }
        myURL = getIntent().getStringExtra("myURL");
        youTubePlayerView = findViewById(R.id.YouTube);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(myURL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


    youTubePlayerView.initialize("AIzaSyD5um-LOz2G_Hq_bK6vn_GeDFUau42Ygpg",onInitializedListener);
    }
}
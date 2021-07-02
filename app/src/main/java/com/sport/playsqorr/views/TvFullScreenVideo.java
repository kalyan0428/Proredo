package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sport.playsqorr.R;

public class TvFullScreenVideo extends AppCompatActivity {
    ToggleButton video_music_toggle_button;
    ToggleButton fullscreen_button;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_full_screen_video);
        initUi();
        getIntentMethod();
    }

    private void getIntentMethod() {
        if (getIntent().hasExtra("video_id")) {
            youTubePlayerView.setEnableAutomaticInitialization(false);
            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    mYouTubePlayer = youTubePlayer;
                    youTubePlayer.loadVideo(getIntent().getStringExtra("video_id"), 0f);
                    video_music_toggle_button.setClickable(true);
                }
            });
        }
    }

    private void initUi() {
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        video_music_toggle_button = findViewById(R.id.video_music_toggle_button);
        fullscreen_button = findViewById(R.id.fullscreen_exit_button);
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);
        getLifecycle().addObserver(youTubePlayerView);
        video_music_toggle_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mYouTubePlayer.setVolume(0);
                } else {
                    mYouTubePlayer.setVolume(90);
                }
            }
        });
        fullscreen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

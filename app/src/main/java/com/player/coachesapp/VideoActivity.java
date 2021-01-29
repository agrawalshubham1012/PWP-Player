package com.player.coachesapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    ImageView PlayBtn;
    VideoView MapVideo;
    TextView skipTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_screen);
        PlayBtn = findViewById(R.id.PlayBtn);
        skipTxt = findViewById(R.id.skipTxt);
        PlayBtn.setVisibility(View.GONE);
        MapVideo = findViewById(R.id.MapVideo);

        mute();
        String path = "android.resource://" + getPackageName() + "/" + R.raw.wave;
        MapVideo.setVideoURI(Uri.parse(path));
        MapVideo.start();
        MapVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                PlayBtn.setVisibility(View.VISIBLE);
            }
        });

        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayBtn.setVisibility(View.GONE);
                MapVideo.start();
            }
        });

        skipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoActivity.this, SummaryActivity.class));
                finish();
            }
        });

    }

    public void mute() {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

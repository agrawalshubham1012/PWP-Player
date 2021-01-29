package com.player.coachesapp;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoPlayActivity extends BaseActivity {
    public static final String TAG = VideoPlayActivity.class.getSimpleName();
    @BindView(R.id.imageViewBackButton)
    ImageView imageViewBackButton;
    @BindView(R.id.textViewUserName)
    TextView textViewUserName;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ep_video_view)
    PlayerView epVideoView;
    private String url, fileName, folderName;
    private SimpleExoPlayer exoPlayer;
    private ComponentListener componentListener;
    DataSource.Factory dataSourceFactory;
    MediaSource firstVideoSource;
    ConcatenatingMediaSource concatenatedSource;
    boolean toMute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
        componentListener = new ComponentListener();
        url = getIntent().getStringExtra("URL");
        initializePlayer();

    }

    private void initializePlayer() {
        progressBar.setVisibility(View.GONE);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        epVideoView.setPlayer(exoPlayer);
        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
        firstVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));
        concatenatedSource = new ConcatenatingMediaSource(firstVideoSource);
        exoPlayer.prepare(concatenatedSource);
        exoPlayer.addListener(componentListener);


//        PlaybackParameters param = new PlaybackParameters(0.5f);
//        exoPlayer.setPlaybackParameters(param);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            releasePlayer();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releasePlayer();
        }
    }

    public void releasePlayer() {
        if (null != exoPlayer) {
            exoPlayer.removeListener(componentListener);
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @OnClick(R.id.imageViewBackButton)
    public void onViewClicked() {
        onBackPressed();
    }

    private class ComponentListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    progressBar.setVisibility(View.VISIBLE);
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    progressBar.setVisibility(View.GONE);
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    progressBar.setVisibility(View.GONE);
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }
}

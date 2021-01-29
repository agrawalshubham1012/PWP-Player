package com.player.coachesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Adapter.SkillNonScrollAdapter;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Model.CoachDetailResponse;
import com.player.coachesapp.Model.FavoriteInput;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.NonScrollListView;
import com.player.coachesapp.Utils.PlayerPrefrences;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends BaseActivity implements APICallBack.FavoriteCallback, APICallBack.CoachDetailCallback {


    String coachId = "";
    boolean flage = true;
    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.headerLayout)
    RelativeLayout headerLayout;
    @BindView(R.id.imgBanner)
    ImageView imgBanner;
    @BindView(R.id.imgCoache)
    CircleImageView imgCoache;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewPosition)
    TextView textViewPosition;
    @BindView(R.id.imgTrophy)
    ImageView imgTrophy;
    @BindView(R.id.favoritesImage)
    ImageView favoritesImage;
    @BindView(R.id.submitVideo)
    TextView submitVideo;
    @BindView(R.id.nonScrollSkillList)
    NonScrollListView nonScrollSkillList;
    @BindView(R.id.noSkillFound)
    TextView noSkillFound;
    @BindView(R.id.textViewAbout)
    TextView textViewAbout;
    @BindView(R.id.ep_video_view)
    PlayerView epVideoView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.video_player)
    RelativeLayout videoPlayer;
    @BindView(R.id.img_muteUnmute)
    ImageView imgMuteUnmute;
    @BindView(R.id.textViewMemorable)
    TextView textViewMemorable;
    private CoachDetailData coachDetailData = null;
    private SimpleExoPlayer exoPlayer;
    private ComponentListener componentListener;
    DataSource.Factory dataSourceFactory;
    MediaSource firstVideoSource;
    ConcatenatingMediaSource concatenatedSource;
    boolean toMute = true; // initially player is mute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coaches_about_screen);
        ButterKnife.bind(this);
        try {
            coachId = getIntent().getStringExtra("id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        CALLDETAILAPI();

        backBtn.setOnClickListener(v -> onBackPressed());

        favoritesImage.setOnClickListener(v -> {
            if (null != coachDetailData) {
                APICALL(coachId);
            }


        });

        submitVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != coachDetailData) {
                    startActivity(new Intent(DetailActivity.this, UploadVideoActivity.class)
                            .putExtra("coach_data", coachDetailData));
                    finish();
                }
            }
        });

        imgMuteUnmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toMute) {
                    toMute = false;
                    exoPlayer.setVolume(1f);
                    imgMuteUnmute.setImageResource(R.drawable.unmute);
                } else {
                    toMute = true;
                    exoPlayer.setVolume(0f);
                    imgMuteUnmute.setImageResource(R.drawable.mute);
                }


            }
        });
    }

    public void CALLDETAILAPI() {
        new CallAPI(this).CallCoachDetailAPI(this, true, coachId, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));
    }

    public void APICALL(String id) {
        FavoriteInput favoriteInput = new FavoriteInput();
        favoriteInput.setFc_coach_id(id);
        new CallAPI(this).CallFavoriteApi(this, true, favoriteInput, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccessDetail(CoachDetailResponse response) {
        try {
            if (null != response.getData()) {
                coachDetailData = response.getData();
                textViewName.setText(coachDetailData.getCoName());
                textViewMemorable.setText("" + coachDetailData.getCoMoments());

                textViewAbout.setText("" + coachDetailData.getCoAbout());
                if (coachDetailData.getCoPosition() != null) {
                    if (coachDetailData.getCoPosition().size() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < coachDetailData.getCoPosition().size(); i++) {
                            stringBuilder.append(coachDetailData.getCoPosition().get(i));
                            if (i != (coachDetailData.getCoPosition().size() - 1)) {
                                stringBuilder.append(", ");
                            }
                        }
                        textViewPosition.setText(coachDetailData.getCoOrganization() + " | " + stringBuilder.toString());
                    } else {
                        textViewPosition.setText("");
                    }
                }

                if (!TextUtils.isEmpty(coachDetailData.getCoCoachType())) {
                    switch (coachDetailData.getCoCoachType().toLowerCase()) {
                        case "silver":
                            imgTrophy.setImageResource(R.drawable.silver_trophy);
                            break;
                        case "bronze":
                            imgTrophy.setImageResource(R.drawable.bronze_trophy);
                            break;
                        case "platinum":
                            imgTrophy.setImageResource(R.drawable.platinum_trophy);
                            break;
                        case "gold":
                            imgTrophy.setImageResource(R.drawable.gold_trophy);
                            break;
                    }
                }

                if (coachDetailData.getIsFavorite().equalsIgnoreCase("true")) {
                    favoritesImage.setImageResource(R.drawable.favorite);
                } else {
                    favoritesImage.setImageResource(R.drawable.unfavorite);
                }

                if (!TextUtils.isEmpty(coachDetailData.getCoAvatar())) {
                    Glide.with(this).load(coachDetailData.getCoAvatar()).placeholder(R.drawable.avatar).into(imgCoache);
                }

                if (!TextUtils.isEmpty(coachDetailData.getCoBanner())) {
                    if (coachDetailData.getCoBannerType().equalsIgnoreCase("video")) {
                        componentListener = new ComponentListener();
                        initializePlayer(coachDetailData.getCoBanner());
                        videoPlayer.setVisibility(View.VISIBLE);
                        imgBanner.setVisibility(View.GONE);
                        imgMuteUnmute.setVisibility(View.VISIBLE);
                    } else {
                        videoPlayer.setVisibility(View.GONE);
                        imgBanner.setVisibility(View.VISIBLE);
                        imgMuteUnmute.setVisibility(View.GONE);
                        Glide.with(this).load(coachDetailData.getCoBanner()).placeholder(R.color.baseball_drawable_solid).into(imgBanner);
                    }
                } else {
                    videoPlayer.setVisibility(View.GONE);
                    imgBanner.setVisibility(View.VISIBLE);
                    imgMuteUnmute.setVisibility(View.GONE);
                }

                if (null != coachDetailData.getSkills() && coachDetailData.getSkills().size() > 0) {
                    nonScrollSkillList.setFocusable(false);
                    SkillNonScrollAdapter nonScrollAdapter = new SkillNonScrollAdapter(this, coachDetailData.getSkills());
                    if (null != nonScrollSkillList) {
                        nonScrollSkillList.setAdapter(nonScrollAdapter);
                        noSkillFound.setVisibility(View.GONE);
                        nonScrollSkillList.setVisibility(View.VISIBLE);
                    }
                } else {
                    noSkillFound.setVisibility(View.VISIBLE);
                    nonScrollSkillList.setVisibility(View.GONE);
                }
            }
            try {
                String name = coachDetailData.getCoName();
                String lastName = "";
                String firstName = "";
                if (name.split("\\w+").length > 1) {

                    lastName = name.substring(name.lastIndexOf(" ") + 1);
                    firstName = name.substring(0, name.indexOf(' '));
                } else {
                    firstName = name;
                }
                submitVideo.setText("Practice with " + firstName);
                headerTitle.setText("Practice with " + firstName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessFavorite(FavoriteResponse response) {
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        try {
            if (null != coachDetailData) {
                if (coachDetailData.getIsFavorite().equalsIgnoreCase("true")) {
                    favoritesImage.setImageResource(R.drawable.unfavorite);
                    coachDetailData.setIsFavorite("false");
                } else {
                    favoritesImage.setImageResource(R.drawable.favorite);
                    coachDetailData.setIsFavorite("true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTokenChangeError(String message) {
        userLogout();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void initializePlayer(String coBannerUrl) {
        try {
            progressBar.setVisibility(View.GONE);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this);
            epVideoView.setPlayer(exoPlayer);
            dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));
            firstVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(coBannerUrl));
            concatenatedSource = new ConcatenatingMediaSource(firstVideoSource);
            exoPlayer.prepare(concatenatedSource);
            exoPlayer.addListener(componentListener);
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.setVolume(0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Log.d("kjgk", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }


}

package com.player.coachesapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.player.coachesapp.Adapter.FavoritesAdapter;
import com.player.coachesapp.Adapter.VideoAdapter;
import com.player.coachesapp.HomeScreen;
import com.player.coachesapp.Model.CoachListModel;
import com.player.coachesapp.Model.CoachListResponse;
import com.player.coachesapp.Model.FavoriteInput;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.Model.FavoriteVideoResponse;
import com.player.coachesapp.Model.VideoInput;
import com.player.coachesapp.Model.VideoModel;
import com.player.coachesapp.Model.VideoResponse;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.VideoPlayActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteFragment extends Fragment implements APICallBack.CoachFavoriteListCallBack, FavoritesAdapter.SelectListener, APICallBack.FavoriteVideoListCallBack, VideoAdapter.SelectListener, APICallBack.FavoriteCallback, APICallBack.VideoFavoriteCallback {

    @BindView(R.id.favoriteList)
    RecyclerView favoriteList;
    @BindView(R.id.switchLayout)
    LinearLayout switchLayout;
    @BindView(R.id.videoTxt)
    TextView videoTxt;
    @BindView(R.id.coachesTxt)
    TextView coachesTxt;
    @BindView(R.id.messageTxt)
    TextView messageTxt;
    @BindView(R.id.ep_video_view)
    PlayerView epVideoView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.viewPlayLayout)
    RelativeLayout viewPlayLayout;
    private FavoritesAdapter adapter;
    private Context context;
    private VideoAdapter libraryAdapter;
    private int DeletePosition = 0;
    private String favoriteSelection = "";
    private SimpleExoPlayer exoPlayer;
    private ComponentListener componentListener;
    private DataSource.Factory dataSourceFactory;
    private MediaSource firstVideoSource;
    private ConcatenatingMediaSource concatenatedSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,
                container, false);
        context = getActivity();

        ButterKnife.bind(this, view);
        CallVideoAPI();

//      SetLibraryData();

        return view;
    }

    @OnClick({R.id.videoTxt, R.id.coachesTxt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoTxt:
                if (!favoriteSelection.equals("1")) {
                    videoTxt.setBackgroundResource(R.drawable.select);
                    coachesTxt.setBackgroundResource(R.drawable.unselect);
                    videoTxt.setTextColor(context.getResources().getColor(R.color.white));
                    coachesTxt.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                SetLibraryData();
                    CallVideoAPI();
                }
                break;
            case R.id.coachesTxt:
                if (!favoriteSelection.equals("2")) {
                    videoTxt.setBackgroundResource(R.drawable.unselect);
                    coachesTxt.setBackgroundResource(R.drawable.select);
                    videoTxt.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    coachesTxt.setTextColor(context.getResources().getColor(R.color.white));
//                SetAdapterData();
                    CallAPI();
                }
                break;
        }
    }


    public void CallAPI() {
        ArrayList<VideoModel> videoModels = new ArrayList<>();
        if (null != libraryAdapter) {
            libraryAdapter.NotifyVideoAdapter(videoModels, -1);
            libraryAdapter.notifyDataSetChanged();
        }
        favoriteSelection = "2";
        new CallAPI(context).CallFavoriteCoachListAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }


    public void CallVideoAPI() {
        ArrayList<CoachListModel> coachListModels = new ArrayList<>();
        if (null != adapter) {
            adapter.NotifyFavoriteAdapter(coachListModels);
            adapter.notifyDataSetChanged();
        }
        favoriteSelection = "1";
        new CallAPI(context).CallFavoriteVideoListAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }


    public void SetAdapterData(List<CoachListModel> list) {
        if (list == null || list.size() == 0) {
            favoriteList.setVisibility(View.GONE);
            messageTxt.setVisibility(View.VISIBLE);
        } else {
            messageTxt.setVisibility(View.GONE);
            favoriteList.setVisibility(View.VISIBLE);
        }
        adapter = new FavoritesAdapter(context, list, this);
        favoriteList.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        favoriteList.setLayoutManager(manager);
    }

    public void SetLibraryData(List<VideoModel> list) {
        if (list == null || list.size() == 0) {
            messageTxt.setVisibility(View.VISIBLE);
            favoriteList.setVisibility(View.GONE);
        } else {
            messageTxt.setVisibility(View.GONE);
            favoriteList.setVisibility(View.VISIBLE);
        }
        libraryAdapter = new VideoAdapter(context, list, this);
        favoriteList.setAdapter(libraryAdapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        favoriteList.setLayoutManager(manager);
    }

    @Override
    public void onFavoriteCoachListSuccess(CoachListResponse response) {
        SetAdapterData(response.getData());
    }

    @Override
    public void onTokenChangeError(String message) {
        try {
            ((HomeScreen) context).userLogout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFavoriteVideoListSuccess(VideoResponse response) {
        SetLibraryData(response.getData());
    }

    @Override
    public void onSuccessFavorite(FavoriteResponse response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        if (null != adapter)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessVideoFavorite(FavoriteVideoResponse response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        if (null != libraryAdapter)
            libraryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSelect(int pos, String id) {
        DeletePosition = pos;
        APICALL(id);
    }

    @Override
    public void OnVideoSelect(List<VideoModel> list, int pos) {

//        if(null != libraryAdapter) {
//            libraryAdapter.NotifyVideoAdapter(list, pos);
//            libraryAdapter.notifyDataSetChanged();
//        }

//        playVideo(list.get(pos).getViFileName());

        startActivity(new Intent(context, VideoPlayActivity.class).putExtra("URL",list.get(pos).getViFileName()));
    }

    @Override
    public void OnUnfavoriteClick(String id) {
        UnfavoriteAPICALL(id);
    }

    public void UnfavoriteAPICALL(String id) {
        VideoInput videoInput = new VideoInput();
        videoInput.setVideo_id(id);
        new CallAPI(context).CallVideoFavoriteApi(this, true, videoInput, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }

    public void APICALL(String id) {
        FavoriteInput favoriteInput = new FavoriteInput();
        favoriteInput.setFc_coach_id(id);
        new CallAPI(context).CallFavoriteApi(this, true, favoriteInput, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }


    public void playVideo(String viFileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releasePlayer();
        }
        componentListener = new ComponentListener();
        viewPlayLayout.setVisibility(View.VISIBLE);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        epVideoView.setPlayer(exoPlayer);
        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));
        firstVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(viFileName));
        concatenatedSource = new ConcatenatingMediaSource(firstVideoSource);
        exoPlayer.prepare(concatenatedSource);
        exoPlayer.addListener(componentListener);
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
//            Log.d(TAG, "changed state to " + stateString
//                    + " playWhenReady: " + playWhenReady);
        }
    }


}

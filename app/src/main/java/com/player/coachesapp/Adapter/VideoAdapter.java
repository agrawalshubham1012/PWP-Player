package com.player.coachesapp.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.player.coachesapp.Model.VideoModel;
import com.player.coachesapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private Context context;

    // The items to display in your RecyclerView
    private ArrayList<String> items;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private boolean flage = true;
    private List<VideoModel> list;
    private SelectListener listener;
    private int playingPosition = -1;

    public VideoAdapter(Context context, List<VideoModel> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void NotifyVideoAdapter(List<VideoModel> items, int position) {
        this.list = items;
        this.playingPosition = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.text.setText(items.get(position));
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);

        if (!TextUtils.isEmpty(list.get(position).getViFileImage())) {
            Glide.with(context).load(list.get(position).getViFileImage()).placeholder(R.drawable.img).error(R.drawable.img).into(holder.imgVideo);
        }

//
//        if(playingPosition >= 0){
//            if(playingPosition == position){
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    releasePlayer();
//                }
//                componentListener = new ComponentListener(holder);
//                holder.videoViewLayout.setVisibility(View.GONE);
//                holder.viewPlayLayout.setVisibility(View.VISIBLE);
//                exoPlayer = ExoPlayerFactory.newSimpleInstance(context);
//                holder.epVideoView.setPlayer(exoPlayer);
//                dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));
//                firstVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(list.get(position).getViFileName()));
//                concatenatedSource = new ConcatenatingMediaSource(firstVideoSource);
//                exoPlayer.prepare(concatenatedSource);
//                exoPlayer.addListener(componentListener);
//            }else {
//                holder.videoViewLayout.setVisibility(View.VISIBLE);
//                holder.viewPlayLayout.setVisibility(View.GONE);
//            }
//        }

        holder.videoTitleTxt.setText(list.get(position).getViTitle());

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favoriteIcon.setImageResource(R.drawable.unfavorite_video);
                listener.OnUnfavoriteClick(list.get(position).getVideoId());
                list.remove(position);
            }
        });

        holder.videoViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                playingPosition = position;
//                notifyDataSetChanged();

                listener.OnVideoSelect(list, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgVideo)
        ImageView imgVideo;
        @BindView(R.id.videoTitleTxt)
        TextView videoTitleTxt;
        @BindView(R.id.favoriteIcon)
        ImageView favoriteIcon;
        @BindView(R.id.videoViewLayout)
        RelativeLayout videoViewLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public interface SelectListener {
        void OnVideoSelect(List<VideoModel> list, int position);

        void OnUnfavoriteClick(String id);
    }




}
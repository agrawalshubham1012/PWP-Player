package com.player.coachesapp.tab_library.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.player.coachesapp.R;
import com.player.coachesapp.VideoPlayActivity;
import com.player.coachesapp.tab_library.model.LibraryVideoModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryVideoAdapter extends RecyclerView.Adapter<LibraryVideoAdapter.ViewHolder> {
    @BindView(R.id.videoTitleTxt)
    TextView videoTitleTxt;
    @BindView(R.id.favoriteIcon)
    ImageView favoriteIcon;
    @BindView(R.id.imgVideo)
    ImageView imgVideo;
    @BindView(R.id.ViewLayout)
    RelativeLayout ViewLayout;
    private Context context;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private ArrayList<LibraryVideoModel> list;
    private SelectListener listener;

    public LibraryVideoAdapter(Context context, ArrayList<LibraryVideoModel> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void NotifyVideoAdapter(ArrayList<LibraryVideoModel> items) {
        this.list = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.text.setText(items.get(position));
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);

//        if(!TextUtils.isEmpty(list.get(position).getVi))

        if (!TextUtils.isEmpty(list.get(position).getViFileImage())) {
            Glide.with(context).load(list.get(position).getViFileImage()).placeholder(R.drawable.img).error(R.drawable.img).into(holder.imgVideo);
        }

        if (list.get(position).getIsFavorite().equalsIgnoreCase("true")) {
            holder.favoriteIcon.setImageResource(R.drawable.favorite);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.unfavorite_video);
        }

        holder.videoTitleTxt.setText(list.get(position).getViTitle());

        holder.favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsFavorite().equalsIgnoreCase("true")) {
                    holder.favoriteIcon.setImageResource(R.drawable.unfavorite_video);
                    listener.OnUnFavoriteClick("" + list.get(position).getVideoId());
                    list.get(position).setIsFavorite("false");
                } else {
                    holder.favoriteIcon.setImageResource(R.drawable.favorite);
                    listener.OnUnFavoriteClick("" + list.get(position).getVideoId());
                    list.get(position).setIsFavorite("true");
                }
            }
        });

        holder.ViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoPlayActivity.class).putExtra("URL",list.get(position).getViFileName()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videoTitleTxt)
        TextView videoTitleTxt;
        @BindView(R.id.favoriteIcon)
        ImageView favoriteIcon;
        @BindView(R.id.imgVideo)
        ImageView imgVideo;
        @BindView(R.id.ViewLayout)
        RelativeLayout ViewLayout;

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
        void OnVideoSelect(int pos, String id);

        void OnUnFavoriteClick(String id);
    }
}
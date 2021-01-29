package com.player.coachesapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.player.coachesapp.Model.ChatModel;
import com.player.coachesapp.R;
import com.player.coachesapp.VideoPlayActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChatModel> chatArrayList;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatArrayList) {
        this.context = context;
        this.chatArrayList = chatArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.text.setText(items.get(position));
        if(chatArrayList.get(position).getMsgSendBy().equalsIgnoreCase("player")){
            holder.layoutPlayer.setVisibility(View.VISIBLE);
            holder.layoutCoach.setVisibility(View.GONE);
            try{
                Glide.with(context).load(chatArrayList.get(position).getAvtar()).into( holder.imgPlayerProfile);
                holder.tvMsgPlayer.setText(chatArrayList.get(position).getMsgContent());
                holder.tvPlayerName.setText(chatArrayList.get(position).getName());
                holder.tvTimePlayer.setText(chatArrayList.get(position).getMsgCreatedAt());
                if(null != chatArrayList.get(position).getThumbimage()){
                    Glide.with(context).load(chatArrayList.get(position).getThumbimage()).placeholder(R.drawable.baseball_img).error(R.drawable.baseball_img).into(holder.imgThumbPlayer);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            holder.layoutPlayer.setVisibility(View.GONE);
            holder.layoutCoach.setVisibility(View.VISIBLE);
            try{
                Glide.with(context).load(chatArrayList.get(position).getAvtar()).into( holder.imgCoachProfile);
                holder.tvMsgCoach.setText(chatArrayList.get(position).getMsgContent());
                holder.tvCoachName.setText(chatArrayList.get(position).getName());
                holder.tvCoachTime.setText(chatArrayList.get(position).getMsgCreatedAt());
                if(null != chatArrayList.get(position).getThumbimage()){
                    Glide.with(context).load(chatArrayList.get(position).getThumbimage()).placeholder(R.drawable.baseball_img).error(R.drawable.baseball_img).into(holder.imgThumbCoach);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        holder.imgLayoutPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoPlayActivity.class).putExtra("URL",chatArrayList.get(position).getMsgFileUrl()));

            }
        });
        holder.imgLayoutCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoPlayActivity.class).putExtra("URL", chatArrayList.get(position).getMsgFileUrl()));

            }
        });


    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPlayerProfile)
        CircleImageView imgPlayerProfile;
        @BindView(R.id.tvPlayerName)
        TextView tvPlayerName;
        @BindView(R.id.tvTimePlayer)
        TextView tvTimePlayer;
        @BindView(R.id.imgThumbPlayer)
        ImageView imgThumbPlayer;
        @BindView(R.id.imgLayoutPlayer)
        RelativeLayout imgLayoutPlayer;
        @BindView(R.id.tvMsgPlayer)
        TextView tvMsgPlayer;
        @BindView(R.id.layoutPlayer)
        LinearLayout layoutPlayer;
        @BindView(R.id.imgCoachProfile)
        CircleImageView imgCoachProfile;
        @BindView(R.id.tvCoachName)
        TextView tvCoachName;
        @BindView(R.id.tvCoachTime)
        TextView tvCoachTime;
        @BindView(R.id.imgLayoutCoach)
        RelativeLayout imgLayoutCoach;
        @BindView(R.id.tvMsgCoach)
        TextView tvMsgCoach;
        @BindView(R.id.layoutCoach)
        LinearLayout layoutCoach;
        @BindView(R.id.imgThumbCoach)
        ImageView imgThumbCoach;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
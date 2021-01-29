package com.player.coachesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.player.coachesapp.ChatActivity;
import com.player.coachesapp.DetailActivity;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Model.CoachListModel;
import com.player.coachesapp.Model.CoachMessageList;
import com.player.coachesapp.R;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private Context context;

    // The items to display in your RecyclerView
    private ArrayList<String> items;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private ArrayList<CoachDetailData> coachListModels;

    public MessageListAdapter(Context context, ArrayList<CoachDetailData> coachListModels) {
        this.context = context;
        this.coachListModels = coachListModels;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message_list_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.text.setText(items.get(position));
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatActivity.class)
                .putExtra("id", ""+coachListModels.get(position).getCoachId())
//                .putExtra("name",coachListModels.get(position).getCoName())
               .putExtra("coach_data",coachListModels.get(position)));



            }
        });

        try {
            holder.coachName.setText(coachListModels.get(position).getCoName());
            holder.textOne.setText(coachListModels.get(position).getCoOrganization());
            if (coachListModels.get(position).getCoPosition() != null) {
                if (coachListModels.get(position).getCoPosition().size() > 0) {
                    holder.textTwo.setText(" | " + coachListModels.get(position).getCoPosition().get(0));
                }else {
                    holder.textTwo.setText("");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }





        if (!TextUtils.isEmpty(coachListModels.get(position).getCoCoachType())) {
            switch (coachListModels.get(position).getCoCoachType().toLowerCase()) {
                case "silver":
                    holder.img_trophy.setImageResource(R.drawable.silver_trophy);
                    break;
                case "bronze":
                    holder.img_trophy.setImageResource(R.drawable.bronze_trophy);
                    break;
                case "platinum":
                    holder.img_trophy.setImageResource(R.drawable.platinum_trophy);
                    break;
                case "gold":
                    holder.img_trophy.setImageResource(R.drawable.gold_trophy);
                    break;
            }
        }
        if (!TextUtils.isEmpty(coachListModels.get(position).getCoAvatar()))
            Glide.with(context).load(coachListModels.get(position).getCoAvatar()).placeholder(R.drawable.avatar).into(holder.playerImg);



    }

    @Override
    public int getItemCount() {
        return coachListModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coachName, textOne, textTwo, message;
        ImageView playerImg;
        LinearLayout parentLayout;
        ImageView  img_trophy;

        public ViewHolder(View itemView) {
            super(itemView);

            playerImg =  itemView.findViewById(R.id.playerImg);
            coachName = (TextView) itemView.findViewById(R.id.coachName);
            textOne = (TextView) itemView.findViewById(R.id.textOne);
            textTwo = (TextView) itemView.findViewById(R.id.textTwo);
            message = (TextView) itemView.findViewById(R.id.message);
            img_trophy = itemView.findViewById(R.id.img_trophy);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
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

}
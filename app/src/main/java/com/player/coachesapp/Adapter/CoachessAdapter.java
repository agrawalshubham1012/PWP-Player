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
import com.player.coachesapp.DetailActivity;
import com.player.coachesapp.Model.CoachListModel;
import com.player.coachesapp.R;

import java.util.ArrayList;
import java.util.List;

public class CoachessAdapter extends RecyclerView.Adapter<CoachessAdapter.ViewHolder> {
    private Context context;

    // The items to display in your RecyclerView
    private ArrayList<String> items;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private SelectListener listener;
    private List<CoachListModel> coachListModels;

    public CoachessAdapter(Context context, List<CoachListModel> coachListModels, SelectListener listener) {
        this.context = context;
        this.coachListModels = coachListModels;
        this.listener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coaches_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

//        holder.text.setText(items.get(position));
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailActivity.class)
                .putExtra("id", coachListModels.get(position).getCoachId()));
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


        try {
            String name = coachListModels.get(position).getCoName();
            String lastName = "";
            String firstName = "";
            if (name.split("\\w+").length > 1) {

                lastName = name.substring(name.lastIndexOf(" ") + 1);
                firstName = name.substring(0, name.indexOf(' '));
            } else {
                firstName = name;
            }
            holder.viewDetails.setText("Practice with " + firstName);
        } catch (Exception e) {
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

        if (coachListModels.get(position).getIsFavorite().equalsIgnoreCase("true")) {
            holder.favoritesImage.setImageResource(R.drawable.favorite);
        } else {
            holder.favoritesImage.setImageResource(R.drawable.unfavorite);
        }

        holder.favoritesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coachListModels.get(position).getIsFavorite().equalsIgnoreCase("true")){
                    holder.favoritesImage.setImageResource(R.drawable.unfavorite);
                    listener.OnSelect(position,coachListModels.get(position).getCoachId());
                    coachListModels.get(position).setIsFavorite("false");
                }else {
                    holder.favoritesImage.setImageResource(R.drawable.favorite);
                    listener.OnSelect(position,coachListModels.get(position).getCoachId());
                    coachListModels.get(position).setIsFavorite("true");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return coachListModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coachName, textOne, textTwo, viewDetails;
        ImageView playerImg;
        LinearLayout parentLayout;
        RelativeLayout rlLayout;
        ImageView favoritesImage, img_trophy;

        public ViewHolder(View itemView) {
            super(itemView);

            playerImg = (ImageView) itemView.findViewById(R.id.playerImg);

            coachName = (TextView) itemView.findViewById(R.id.coachName);
            textOne = (TextView) itemView.findViewById(R.id.textOne);
            textTwo = (TextView) itemView.findViewById(R.id.textTwo);
            viewDetails = (TextView) itemView.findViewById(R.id.viewDetails);
            img_trophy = itemView.findViewById(R.id.img_trophy);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
            rlLayout = (RelativeLayout) itemView.findViewById(R.id.rlLayout);
            favoritesImage = itemView.findViewById(R.id.favoritesImage);

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
        void OnSelect(int pos, String id);
    }
}
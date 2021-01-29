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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Context context;

    // The items to display in your RecyclerView
    private ArrayList<String> items;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;
    private boolean flage = true;
    private List<CoachListModel> list;
    private SelectListener listener;

    public FavoritesAdapter(Context context, List<CoachListModel> list, SelectListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }



    public void  NotifyFavoriteAdapter(ArrayList<CoachListModel> items){
        this.list = items ;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coaches_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setAnimation(holder.itemView, position);

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailActivity.class)
                        .putExtra("id", list.get(position).getCoachId()));            }
        });
        try {
            holder.coachName.setText(list.get(position).getCoName());
            holder.textOne.setText(list.get(position).getCoOrganization());
            if (list.get(position).getCoPosition() != null) {
                if (list.get(position).getCoPosition().size() > 0) {
                    holder.textTwo.setText(" | " + list.get(position).getCoPosition().get(0));
                }else {
                    holder.textTwo.setText("");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if (!TextUtils.isEmpty(list.get(position).getCoCoachType()))
            switch (list.get(position).getCoCoachType().toLowerCase()) {
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

        if (!TextUtils.isEmpty(list.get(position).getCoAvatar()))
            Glide.with(context).load(list.get(position).getCoAvatar()).placeholder(R.drawable.avatar).into(holder.playerImg);

        try {

            if (list.get(position).getIsFavorite().equalsIgnoreCase("true")) {
                holder.favoritesImage.setImageResource(R.drawable.favorite);
            } else {
                holder.favoritesImage.setImageResource(R.drawable.unfavorite);
            }
        }catch (Exception e){
          e.printStackTrace();
        }


        holder.favoritesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getIsFavorite().equalsIgnoreCase("true")){
                    holder.favoritesImage.setImageResource(R.drawable.unfavorite);
                    listener.OnSelect(position,list.get(position).getCoachId());
                    list.get(position).setIsFavorite("false");
                    list.remove(position);
                }else {
                    holder.favoritesImage.setImageResource(R.drawable.favorite);
                    listener.OnSelect(position,list.get(position).getCoachId());
                    list.get(position).setIsFavorite("true");
                }
            }
        });

        try {
            String name = list.get(position).getCoName();
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



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coachName, textOne, textTwo, viewDetails;
        ImageView playerImg, img_trophy, favoritesImage;
        LinearLayout parentLayout;
        RelativeLayout rlLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            favoritesImage = itemView.findViewById(R.id.favoritesImage);
            playerImg = (ImageView) itemView.findViewById(R.id.playerImg);
            coachName = (TextView) itemView.findViewById(R.id.coachName);
            textOne = (TextView) itemView.findViewById(R.id.textOne);
            textTwo = (TextView) itemView.findViewById(R.id.textTwo);
            viewDetails = (TextView) itemView.findViewById(R.id.viewDetails);
            img_trophy =  itemView.findViewById(R.id.img_trophy);
            parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
            rlLayout = (RelativeLayout) itemView.findViewById(R.id.rlLayout);
//            rlLayout.setVisibility(View.GONE);

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
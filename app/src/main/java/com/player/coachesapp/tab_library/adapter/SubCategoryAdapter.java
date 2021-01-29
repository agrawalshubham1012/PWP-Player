package com.player.coachesapp.tab_library.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.player.coachesapp.R;
import com.player.coachesapp.tab_library.LibraryVideoListActivity;
import com.player.coachesapp.tab_library.model.CatSubCatModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {


    private Context context;
    private int lastPosition = -1;
    private ArrayList<CatSubCatModel> list;
    private ItemClickLCallback itemClickLCallback;

    public SubCategoryAdapter(Context context, ArrayList<CatSubCatModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        setAnimation(holder.itemView, position);

        holder.imgExpandBtn.setVisibility(View.GONE);

        holder.videoTxt.setText(list.get(position).getCatTitle());

        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, LibraryVideoListActivity.class).putExtra("KEY", list.get(position).getCatTitle())
                        .putExtra("CAT_ID", list.get(position).getParentId())
                        .putExtra("SUB_CAT_ID", list.get(position).getCategoryId()));
            }
        });

        try{
            if(list.get(position).getCatImage().equalsIgnoreCase("default.png")){
                holder.catImage.setImageResource(R.drawable.background_rectangle);
            }else {
                Glide.with(context).load(list.get(position).getMedia()).placeholder(R.drawable.background_rectangle).error(R.drawable.background_rectangle).into(holder.catImage);
            }
        }catch (Exception e){
            e.printStackTrace();
            holder.catImage.setImageResource(R.drawable.background_rectangle);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videoTxt)
        TextView videoTxt;
        @BindView(R.id.imgExpandBtn)
        ImageView imgExpandBtn;
        @BindView(R.id.viewLayout)
        RelativeLayout viewLayout;
        @BindView(R.id.catImage)
        ImageView catImage;
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


    public interface ItemClickLCallback {
        void onItemClicked(String title, String subCatId);
    }
}
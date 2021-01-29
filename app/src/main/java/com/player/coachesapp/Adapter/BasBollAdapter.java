package com.player.coachesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.player.coachesapp.Model.BasBoll;
import com.player.coachesapp.R;
import java.util.List;


public class BasBollAdapter extends RecyclerView.Adapter<BasBollAdapter.ViewHolder> {

    public Context context;
    public List<BasBoll> list;
    private OnSelectListener listener;

    public BasBollAdapter(Context context, List<BasBoll> list,OnSelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_ball_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Txt.setText(list.get(position).getName());

        if (list.get(position).isSelection()) {
            holder.Txt.setBackgroundResource(R.drawable.position_select_border);
            holder.Txt.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.Txt.setBackgroundResource(R.drawable.position_unselected_border);
            holder.Txt.setTextColor(context.getResources().getColor(R.color.black));
        }

        holder.Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Txt;

        public ViewHolder(View itemView) {
            super(itemView);
            Txt = itemView.findViewById(R.id.Txt);
        }

    }

    public interface OnSelectListener {
        void OnClick(int pos);
    }

}

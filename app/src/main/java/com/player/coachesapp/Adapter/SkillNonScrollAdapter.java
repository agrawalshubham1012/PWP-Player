package com.player.coachesapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.player.coachesapp.Model.CoachSkill;
import com.player.coachesapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillNonScrollAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CoachSkill> skillArrayList;
    private LayoutInflater layoutInflater;

    public SkillNonScrollAdapter(Context context, ArrayList<CoachSkill> skillArrayList) {
        this.context = context;
        this.skillArrayList = skillArrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return skillArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.row_skill, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.tvSkillTitle.setText(skillArrayList.get(position).getSkTitle());



        return view;
    }


    static
    class ViewHolder {
        @BindView(R.id.tvSkillTitle)
        TextView tvSkillTitle;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}

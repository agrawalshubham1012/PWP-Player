package com.player.coachesapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrophyActivity extends AppCompatActivity {

    @BindView(R.id.backBtn)
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.backBtn)
    public void onViewClicked() {
        onBackPressed();
    }
}

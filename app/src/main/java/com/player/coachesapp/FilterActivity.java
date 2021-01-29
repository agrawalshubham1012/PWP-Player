package com.player.coachesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.player.coachesapp.Utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterActivity extends AppCompatActivity {

    @BindView(R.id.backBtn)
    ImageView backBtn;

    @BindView(R.id.silverLayout)
    LinearLayout silverLayout;
    @BindView(R.id.goldLayout)
    LinearLayout goldLayout;
    @BindView(R.id.platinumLayout)
    LinearLayout platinumLayout;

    private Unbinder unbinder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.backBtn,R.id.silverLayout,R.id.goldLayout,R.id.platinumLayout, R.id.textViewClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.silverLayout:
                Constant.FILTTERTYPE = "silver";
                onBackPressed();
                break;
            case R.id.goldLayout:
                Constant.FILTTERTYPE = "gold";
                onBackPressed();
                break;
            case R.id.platinumLayout:
                Constant.FILTTERTYPE = "platinum";
                onBackPressed();
                break;
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.textViewClear:
                Constant.FILTTERTYPE = "";
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}

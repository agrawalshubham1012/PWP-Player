package com.player.coachesapp.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseClassActivity extends AppCompatActivity {

    Unbinder unbinder;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(getActivityLayout());
        unbinder = ButterKnife.bind(this);
        context = this;

    }

    public Context getContext(){
        return context;
    }



    protected abstract int getActivityLayout();

}

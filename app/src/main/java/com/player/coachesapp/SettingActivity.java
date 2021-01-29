package com.player.coachesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Model.CommonResponseModel;
import com.player.coachesapp.Model.NotificationMuteUnMuteParam;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements APICallBack.SettingCallback {

    @BindView(R.id.backBtn)
    ImageView backBtn;
    @BindView(R.id.switchLayout)
    SwitchCompat switchLayout;
    @BindView(R.id.logoutRl)
    RelativeLayout logoutRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);
        ButterKnife.bind(this);

        try{
            SignInResponse signInResponse = PlayerPrefrences.GetUserProfile(this);
            if(null != signInResponse && null != signInResponse.getData()){
                if(signInResponse.getData().getPlNotification().equalsIgnoreCase("off")){
                    switchLayout.setOnCheckedChangeListener (null);
                    switchLayout.setChecked(false);
                }else {
                    switchLayout.setOnCheckedChangeListener (null);
                    switchLayout.setChecked(true);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        switchLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                callNotificationMuteUnMuteApi(b);
            }
        });

    }

    private void callNotificationMuteUnMuteApi(boolean isChecked) {
        NotificationMuteUnMuteParam param = new NotificationMuteUnMuteParam();
        if(isChecked){
            param.setNotification("on");
        }else {
            param.setNotification("off");
        }
        new CallAPI(this).callNotificationMuteUnMuteApi(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this), param);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }




    @OnClick({R.id.backBtn, R.id.logoutRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.logoutRl:
                logoutDialog();
                break;
        }
    }

    private void logoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo);
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.AreYouSure))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callLogoutApi();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void callLogoutApi() {
        googleSignOut();
//        new CallAPI(this).callLogoutApi(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));
        PlayerPrefrences.deleteSharedPreference(this);
        startActivity(new Intent(this, SignInActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    public void onNotificationMuteUnmute(CommonResponseModel commonResponseModel) {
               showToast(commonResponseModel.getMessage());
        SignInResponse sign = PlayerPrefrences.GetUserProfile(this);
        if(switchLayout.isChecked()){
            sign.getData().setPlNotification("on");
        }else {
            sign.getData().setPlNotification("off");

        }

        Gson gson = new Gson();
        PlayerPrefrences.saveInfo(Constant.PLAYER_PROFILE, gson.toJson(sign), this);
    }

    @Override
    public void onLogoutSuccess(CommonResponseModel commonResponseModel) {
        PlayerPrefrences.deleteSharedPreference(this);
        startActivity(new Intent(this, SignInActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
       finish();
    }

    @Override
    public void onError(String error) {
     showToast(error);
    }

  /*  private void googleSignOut(){
         GoogleSignInClient mGoogleSignInClient;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getResources().getString(R.string.server_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {
                        // ...
                    }
                });
    }*/
}

package com.player.coachesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Adapter.ChatAdapter;
import com.player.coachesapp.Model.ChatListParameter;
import com.player.coachesapp.Model.ChatListResponse;
import com.player.coachesapp.Model.ChatModel;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Model.CoachDetailResponse;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements APICallBack.ChatListCallBack, APICallBack.CoachDetailCallback {


    @BindView(R.id.CoachesList)
    RecyclerView CoachesList;
    String coachId = "";
    String coachName = "";
    @BindView(R.id.tvHeaderName)
    TextView tvHeaderName;
    CoachDetailData coachDetailData = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        coachId = getIntent().getStringExtra("id");
        try {
            coachDetailData = (CoachDetailData) getIntent().getSerializableExtra("coach_data");
           if(null != coachDetailData){
               coachName = coachDetailData.getCoName();
               try{
                   String name = coachDetailData.getCoName();
                   String lastName = "";
                   String firstName = "";
                   if (name.split("\\w+").length > 1) {

                       lastName = name.substring(name.lastIndexOf(" ") + 1);
                       firstName = name.substring(0, name.indexOf(' '));
                   } else {
                       firstName = name;
                   }
                   coachName = firstName;
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
            if (!TextUtils.isEmpty(coachName)) {
                tvHeaderName.setText("Practice with " + coachName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        CallAPI();


    }

    public void CallAPI() {
        ChatListParameter parameter = new ChatListParameter();
        parameter.setId(coachId);
        new CallAPI(this).callChatList(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this), parameter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeScreen.SetSelection(Utils.SETSELECTION);
    }

    private void setAdapter(ArrayList<ChatModel> chatArrayList) {
        ChatAdapter chatAdapter = new ChatAdapter(this, chatArrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if (null != CoachesList) {
            CoachesList.setLayoutManager(mLayoutManager);
            CoachesList.setAdapter(chatAdapter);
            CoachesList.scrollToPosition(chatArrayList.size() - 1);

            try{
                if(null == coachDetailData) {
//                    int length = chatArrayList.size();
//                    for (int i = length - 1; i > 0; i--) {
//                        if (chatArrayList.get(i).getMsgSendBy().equalsIgnoreCase("coach")) {
//                           coachDetailData.setCoachId(""+chatArrayList.get(i).getCoachId());
//                            coachDetailData.setCoCoachType("");
//                            coachDetailData.setCoAvatar(""+chatArrayList.get(i).getCoachId());
//                            coachDetailData.setCoName(""+chatArrayList.get(i).getName());
//                        }
//                    }
                    new CallAPI(this).CallCoachDetailAPI(this, true, coachId, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onSuccess(ChatListResponse response) {
        if (null != response.getData() && response.getData().size() > 0) {
            setAdapter(response.getData());
        }
    }

    @Override
    public void onSuccessDetail(CoachDetailResponse response) {
        try{
            if(null != response.getData()){
                coachDetailData = response.getData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessFavorite(FavoriteResponse response) {
        // not in use
    }

    @Override
    public void onTokenChangeError(String message) {
        userLogout();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.backBtn, R.id.sendVideoLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.sendVideoLayout:
                if (null != coachDetailData) {
                    startActivity(new Intent(this, UploadVideoActivity.class)
                            .putExtra("coach_data", coachDetailData));
                    finish();
                }

                break;
        }
    }
}

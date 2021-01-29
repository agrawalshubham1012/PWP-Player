package com.player.coachesapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Adapter.MessageListAdapter;
import com.player.coachesapp.HomeScreen;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Model.CoachMessageList;
import com.player.coachesapp.Model.MessageListResponse;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatFragment extends Fragment implements APICallBack.CoachMessageListCallBack {

    @BindView(R.id.CoachesList)
    RecyclerView CoachesList;
    Unbinder unbinder;
    @BindView(R.id.messageTxt)
    TextView messageTxt;
    private Context context;
    private View view;
    private MessageListAdapter adapter;
    private ArrayList<CoachDetailData> coachArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_fragment,
                container, false);
        unbinder = ButterKnife.bind(this, view);
        CallAPI();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void CallAPI() {
        new CallAPI(context).callCoachMessageList(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }

    public void SetAdapterData() {
        if (null != coachArrayList) {
            messageTxt.setVisibility(View.GONE);
            adapter = new MessageListAdapter(context, coachArrayList);
            CoachesList.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
            CoachesList.setLayoutManager(manager);
        }else {
            messageTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(MessageListResponse response) {
        coachArrayList = new ArrayList<>();
        try {

            if (null != response.getData() && response.getData().size() > 0) {
                coachArrayList = response.getData();
                SetAdapterData();
            }
        } catch (Exception e) {
            messageTxt.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    @Override
    public void onTokenChangeError(String message) {
        try {
            ((HomeScreen) context).userLogout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        messageTxt.setVisibility(View.VISIBLE);
    }
}

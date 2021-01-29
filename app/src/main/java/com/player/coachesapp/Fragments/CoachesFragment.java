package com.player.coachesapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Adapter.CoachessAdapter;
import com.player.coachesapp.Adapter.VideoAdapter;
import com.player.coachesapp.FilterActivity;
import com.player.coachesapp.HomeScreen;
import com.player.coachesapp.Model.CoachInput;
import com.player.coachesapp.Model.CoachListModel;
import com.player.coachesapp.Model.CoachListResponse;
import com.player.coachesapp.Model.FavoriteInput;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CoachesFragment extends Fragment implements APICallBack.CoachListCallBack, APICallBack.FavoriteCallback {

    @BindView(R.id.CoachesList)
    RecyclerView CoachesList;
    @BindView(R.id.switchLayout)
    LinearLayout switchLayout;
    @BindView(R.id.videoTxt)
    TextView videoTxt;
    @BindView(R.id.coachesTxt)
    TextView coachesTxt;
    @BindView(R.id.imgFilter)
    ImageView imgFilter;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    private CoachessAdapter adapter;
    private VideoAdapter libraryAdapter;
    private Context context;
    private Unbinder unbinder;
    private List<CoachListModel> coachArrayList;
    private Constant FILTTERTYPE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coaches_fragment,
                container, false);
        context = getActivity();

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CoachInput input = new CoachInput();
        input.setCoach_type(Constant.FILTTERTYPE);
        CallAPI(input);
    }

    public void CallAPI(CoachInput input) {
        new CallAPI(context).CallCoachListAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context), input);
    }

    @OnClick({R.id.videoTxt, R.id.coachesTxt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoTxt:
                videoTxt.setBackgroundResource(R.drawable.select);
                coachesTxt.setBackgroundResource(R.drawable.unselect);
                videoTxt.setTextColor(context.getResources().getColor(R.color.white));
                coachesTxt.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                SetLibraryData();
                break;
            case R.id.coachesTxt:
                videoTxt.setBackgroundResource(R.drawable.unselect);
                coachesTxt.setBackgroundResource(R.drawable.select);
                videoTxt.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                coachesTxt.setTextColor(context.getResources().getColor(R.color.white));
                SetAdapterData();
                break;
        }
    }


    public void SetAdapterData() {
        if (null != coachArrayList) {
            CoachesList.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            adapter = new CoachessAdapter(context, coachArrayList, new CoachessAdapter.SelectListener() {
                @Override
                public void OnSelect(int pos, String id) {
                    APICALL(id);
//                    startActivity(new Intent(getActivity(), DetailActivity.class));
                }
            });
            CoachesList.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
            CoachesList.setLayoutManager(manager);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imgFilter)
    public void onViewClicked() {
        startActivity(new Intent(context, FilterActivity.class));
    }


    @Override
    public void onSuccess(CoachListResponse response) {
        try {
            coachArrayList = new ArrayList<>();
            if (null != response.getData() && response.getData().size() > 0) {
                coachArrayList = response.getData();
                SetAdapterData();
            }else {
                CoachesList.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
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
    public void onSuccessFavorite(FavoriteResponse response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }


    public void APICALL(String id) {
        FavoriteInput favoriteInput = new FavoriteInput();
        favoriteInput.setFc_coach_id(id);
        new CallAPI(context).CallFavoriteApi(this, true, favoriteInput, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
    }

}

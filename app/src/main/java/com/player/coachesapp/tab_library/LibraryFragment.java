package com.player.coachesapp.tab_library;


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
import com.player.coachesapp.HomeScreen;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.tab_library.adapter.LibCategoryAdapter;
import com.player.coachesapp.tab_library.model.CatSubCatModel;
import com.player.coachesapp.tab_library.model.LibraryCategoryResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LibraryFragment extends Fragment implements APICallBack.GetLibraryCategoryListCallBack {

    @BindView(R.id.LibraryList)
    RecyclerView LibraryList;

    Context context;
    @BindView(R.id.messageTxt)
    TextView messageTxt;
    private LibCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_library_fragment,
                container, false);

        ButterKnife.bind(this, view);
        context = getActivity();
        GetCategoryList();

        return view;
    }


    /*
     * Set Data
     * */
    public void SetAdapterData(List<CatSubCatModel> list) {
        if(list==null || list.size()==0){
            LibraryList.setVisibility(View.GONE);
            messageTxt.setVisibility(View.VISIBLE);
        }else {
            messageTxt.setVisibility(View.GONE);
            LibraryList.setVisibility(View.VISIBLE);
            adapter = new LibCategoryAdapter(context, list);
            LibraryList.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
            LibraryList.setLayoutManager(manager);
        }

    }


    public void GetCategoryList() {
        new CallAPI(context).CallLibraryCategoriesAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context), "0");
    }


    @Override
    public void onCategoryListSuccess(LibraryCategoryResponse response) {
        SetAdapterData(response.getData());
    }

    @Override
    public void onTokenChangeError(String message) {
        try {
            ((HomeScreen)context).userLogout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        LibraryList.setVisibility(View.GONE);
        messageTxt.setVisibility(View.VISIBLE);

    }
}

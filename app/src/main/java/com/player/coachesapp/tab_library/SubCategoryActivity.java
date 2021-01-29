package com.player.coachesapp.tab_library;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.BaseActivity;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.tab_library.adapter.SubCategoryAdapter;
import com.player.coachesapp.tab_library.model.CatSubCatModel;
import com.player.coachesapp.tab_library.model.LibraryCategoryResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SubCategoryActivity extends BaseActivity implements APICallBack.GetLibraryCategoryListCallBack {

    @BindView(R.id.LibraryList)
    RecyclerView LibraryList;
    @BindView(R.id.TitleTxt)
    TextView TitleTxt;
    @BindView(R.id.messageTxt)
    TextView messageTxt;
    String KEY = "";
    String ID = "";
    private Context context;
    private SubCategoryAdapter adapter;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_sub_category_activity);
        unbinder = ButterKnife.bind(this);
        context = this;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            KEY = getIntent().getStringExtra("KEY");
            ID = getIntent().getStringExtra("ID");
            TitleTxt.setText(KEY);
            GetCategoryList(ID);
        }

    }


    public void GetCategoryList(String ID) {
        new CallAPI(context).CallLibraryCategoriesAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context), ID);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void SetAdapterData(ArrayList<CatSubCatModel> list) {
        if (list == null || list.size() == 0) {
            LibraryList.setVisibility(View.GONE);
            messageTxt.setVisibility(View.VISIBLE);
        } else {
            messageTxt.setVisibility(View.GONE);
            LibraryList.setVisibility(View.VISIBLE);
            adapter = new SubCategoryAdapter(context, list);
            LibraryList.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
            LibraryList.setLayoutManager(manager);
        }

    }


    @Override
    public void onCategoryListSuccess(LibraryCategoryResponse response) {
        SetAdapterData(response.getData());
    }

    @Override
    public void onTokenChangeError(String message) {
        userLogout();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        LibraryList.setVisibility(View.GONE);
        messageTxt.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.backBtn)
    public void onViewClicked() {
        onBackPressed();
    }
}

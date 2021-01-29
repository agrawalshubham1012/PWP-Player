package com.player.coachesapp.tab_library;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.BaseActivity;
import com.player.coachesapp.Model.FavoriteVideoResponse;
import com.player.coachesapp.Model.VideoInput;
import com.player.coachesapp.R;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.tab_library.adapter.LibraryVideoAdapter;
import com.player.coachesapp.tab_library.model.LibraryVideoModel;
import com.player.coachesapp.tab_library.model.VideoListParameter;
import com.player.coachesapp.tab_library.model.VideoListResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LibraryVideoListActivity extends BaseActivity implements LibraryVideoAdapter.SelectListener, APICallBack.VideoFavoriteCallback, APICallBack.GetLibraryVideoListCallBack {

    @BindView(R.id.TitleTxt)
    TextView TitleTxt;
    @BindView(R.id.messageTxt)
    TextView messageTxt;
    @BindView(R.id.LibraryList)
    RecyclerView LibraryList;
    @BindView(R.id.backBtn)
    ImageView backBtn;
    private String catID = "";
    private String subCatID = "";
    private String title = "";
    private LibraryVideoAdapter libraryVideoAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("KEY");
            catID = extras.getString("CAT_ID");
            subCatID = extras.getString("SUB_CAT_ID");
            TitleTxt.setText(title);
            VideoListParameter parameter = new VideoListParameter();
            parameter.setCategoryId(catID);
            parameter.setSubCategoryId(subCatID);
            GetVideoListApi(parameter);
        }
    }

    public void SetAdapterData(ArrayList<LibraryVideoModel> list) {
        if (list == null || list.size() == 0) {
            LibraryList.setVisibility(View.GONE);
            messageTxt.setVisibility(View.VISIBLE);
        } else {
            messageTxt.setVisibility(View.GONE);
            LibraryList.setVisibility(View.VISIBLE);
            libraryVideoAdapter = new LibraryVideoAdapter(this, list, this);
            LibraryList.setAdapter(libraryVideoAdapter);
            GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
            LibraryList.setLayoutManager(manager);
        }

    }


    private void GetVideoListApi(VideoListParameter parameter) {
        new CallAPI(this).callLibraryVideoListAPI(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this), parameter);

    }

    @OnClick(R.id.backBtn)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onVideoListSuccess(VideoListResponse response) {
        SetAdapterData(response.getData());
    }

    @Override
    public void onTokenChangeError(String message) {
        userLogout();
    }

    @Override
    public void onErrorNoDataFound(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        LibraryList.setVisibility(View.GONE);
        messageTxt.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSuccessVideoFavorite(FavoriteVideoResponse response) {
        Toast.makeText(this,response.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnVideoSelect(int pos, String id) {

    }

    @Override
    public void OnUnFavoriteClick(String id) {
        VideoInput input = new VideoInput();
        input.setVideo_id(id);
        new CallAPI(this).CallVideoFavoriteApi(this, true, input, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));

    }
}

package com.player.coachesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.player.coachesapp.Fragments.ChatFragment;
import com.player.coachesapp.Fragments.CoachesFragment;
import com.player.coachesapp.Fragments.FavoriteFragment;
import com.player.coachesapp.Fragments.ProfileFragment;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.Utils.Utils;
import com.player.coachesapp.tab_library.LibraryFragment;

import java.util.ArrayList;

public class HomeScreen extends BaseActivity {


    public static BottomNavigationView navigationMenu;
    FrameLayout ContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        System.out.println(PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, this));
        Init();
        checkFromNotification();
    }


    private void checkFromNotification() {
        try{
            String id = "", type = "";
            if(null != getIntent() && getIntent().hasExtra(Constant.REFERENCE_ID)){
                id = getIntent().getStringExtra(Constant.REFERENCE_ID);
                type = getIntent().getStringExtra(Constant.NOTIFY_TYPE);

                if(!TextUtils.isEmpty(type)) {
                    if (type.equalsIgnoreCase("message")) {
                        if(!TextUtils.isEmpty(id)) {
                            CoachDetailData coachDetailData = null;
                            startActivity(new Intent(this, ChatActivity.class)
                                    .putExtra("id", "" + id)
                            .putExtra("coach_data",coachDetailData));
                        }

                    } else {
                        Utils.SETSELECTION = 3;
                        navigationMenu.getMenu().findItem(R.id.favorite_ID).setChecked(true);
                        CallFragment(new FavoriteFragment());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void Init() {

        ContainerLayout = findViewById(R.id.ContainerLayout);
        navigationMenu = findViewById(R.id.bottom_navigation);
        CallFragment(new CoachesFragment());
        navigationMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.coaches_ID:
                        Utils.SETSELECTION = 0;
                        navigationMenu.getMenu().findItem(R.id.coaches_ID).setChecked(true);
                        CallFragment(new CoachesFragment());
                        return true;
                    case R.id.library_ID:
                        Utils.SETSELECTION = 1;
                        navigationMenu.getMenu().findItem(R.id.library_ID).setChecked(true);
                        CallFragment(new LibraryFragment());
                        return true;
                    case R.id.chat_ID:
                        Utils.SETSELECTION = 2;
                        CallFragment(new ChatFragment());
                        return true;
                    case R.id.favorite_ID:
                        Utils.SETSELECTION = 3;
                        navigationMenu.getMenu().findItem(R.id.favorite_ID).setChecked(true);
                        CallFragment(new FavoriteFragment());
                        return true;
                    case R.id.profile_ID:
                        Utils.SETSELECTION = 4;
                        CallFragment(new ProfileFragment());
                        navigationMenu.getMenu().findItem(R.id.profile_ID).setChecked(true);
                        return true;
                }
                return false;

            }
        });
    }


    public static void SetSelection(int pos) {
        if (pos == 0) {
            navigationMenu.getMenu().findItem(R.id.coaches_ID).setChecked(true);
        } else if (pos == 1) {
            navigationMenu.getMenu().findItem(R.id.library_ID).setChecked(true);
        } else if (pos == 3) {
            navigationMenu.getMenu().findItem(R.id.favorite_ID).setChecked(true);
        } else if (pos == 4) {
            navigationMenu.getMenu().findItem(R.id.profile_ID).setChecked(true);
        }
    }

    public void CallFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ContainerLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
////            Toast.makeText(getApplicationContext(),returnValue.get(0),Toast.LENGTH_LONG).show();
//        }
//
//    }
}

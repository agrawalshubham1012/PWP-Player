package com.player.coachesapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Adapter.BasBollAdapter;
import com.player.coachesapp.Model.BasBoll;
import com.player.coachesapp.Model.RegistrationParameter;
import com.player.coachesapp.Model.RegistrationResponse;
import com.player.coachesapp.Model.SignInParameter;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.player.coachesapp.Utils.Utils.ShowAlert;

public class RegistrationActivity extends AppCompatActivity implements BasBollAdapter.OnSelectListener, APICallBack.RegistrationCallback, APICallBack.SignInCallback {

    StateProgressBar stateBar;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.DateEdit)
    EditText DateEdit;
    private TextView backBtn, nextBtn;
    private LinearLayout LayoutOne, LayoutTwo, LayoutThree, LayoutFour, ListLayout;
    int position = 0;
    private RecyclerView ListRecy;
    private List<BasBoll> bollList;
    private BasBoll basBoll;
    private Context context;
    private BasBollAdapter adapter;
    private DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_step_screen);
        ButterKnife.bind(this);
        context = this;

        DateEdit = findViewById(R.id.DateEdit);
        LayoutOne = findViewById(R.id.LayoutOne);
        LayoutTwo = findViewById(R.id.LayoutTwo);
        LayoutThree = findViewById(R.id.LayoutThree);
        ListLayout = findViewById(R.id.ListLayout);
        ListRecy = findViewById(R.id.ListRecy);

        stateBar = findViewById(R.id.stateBar);
        backBtn = findViewById(R.id.backBtn);
        nextBtn = findViewById(R.id.nextBtn);

        stateBar.setStateLineThickness(2.05f);
        stateBar.setAnimationDuration(1000);

        nextBtn.setPaintFlags(nextBtn.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        backBtn.setPaintFlags(backBtn.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position != 0) {
                    position--;
                    GetPosition(position);
                }

                if (position == 0) {
                    backBtn.setText("");
                }

                nextBtn.setText(getResources().getString(R.string.next));

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    hideKeyboard(RegistrationActivity.this);
                    position++;
                    if (position <= 3) {
                        GetPosition(position);
                    } else if (position == 4) {
                        position = 3;
                        SeginUpAPI();
//                        startActivity(new Intent(RegistrationActivity.this, SignInActivity.class));
//                        finish();
                    }
                    if(position == 3){
                        nextBtn.setText(getResources().getString(R.string.submit));
                    }
                    backBtn.setText("Back");
                }

            }
        });

        setData();

        PopulateList();
        SetAdapter();

        DateEdit.setFocusableInTouchMode(false);
        DateEdit.setFocusable(false);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataPopup();
            }
        });


    }

    private void setData() {
        if(null !=  PlayerPrefrences.getInfo(Constant.FIRST_NAME, context)){

        }

        try {
            etName.setText(PlayerPrefrences.getInfo(Constant.FIRST_NAME, context));
            etLastName.setText(PlayerPrefrences.getInfo(Constant.LAST_NAME, context));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SeginUpAPI() {
        RegistrationParameter parameter = new RegistrationParameter();
        parameter.setFirst_name(etName.getText().toString());
        parameter.setLast_name(etLastName.getText().toString());
        parameter.setDate_of_birth(Constant.convertDateFormat(DateEdit.getText().toString().trim()));
        parameter.setPosition(PlayerPrefrences.getInfo(Constant.SELECTION, context));
        parameter.setEmail(PlayerPrefrences.getInfo(Constant.GOOGLE_EMAIL, context));
        parameter.setFacebook_id(PlayerPrefrences.getInfo(Constant.FACEBOOK_ID, context));
        parameter.setGoogle_id(PlayerPrefrences.getInfo(Constant.GOOGLE_ID, context));
        parameter.setAuth_type(PlayerPrefrences.getInfo(Constant.SOCIAL_AUTH, context));
        parameter.setDevice_type(Constant.DEVICE_TYPE);
        parameter.setFcm_token(FirebaseInstanceId.getInstance().getToken());

        new CallAPI(context).CallRegistrationApi(this, true, parameter);

    }

    private boolean validate() {
        if (position == 0) {
            if (etName.getText().toString().trim().length() == 0) {

                ShowAlert(RegistrationActivity.this, "please enter your name.");
//               Toast.makeText(context, "please enter your name.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (position == 1) {
            if (etLastName.getText().toString().trim().length() == 0) {

                ShowAlert(RegistrationActivity.this, "please enter last name.");
//                Toast.makeText(context, "please enter last name.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (position == 2) {
            if (DateEdit.getText().toString().trim().length() == 0) {

                ShowAlert(RegistrationActivity.this, "please select date of birth");
//                Toast.makeText(context, "please select date of birth", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (position == 3) {
            if (GetCount(bollList) != 3) {

                ShowAlert(RegistrationActivity.this, "please select 3 baseball positions");
//                Toast.makeText(context, "please select 3 baseball positions", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    public void PopulateList() {

        bollList = new ArrayList<>();
        basBoll = new BasBoll("Outfield", false);
        bollList.add(basBoll);
        basBoll = new BasBoll("Pitcher", false);
        bollList.add(basBoll);
        basBoll = new BasBoll("Catcher", false);
        bollList.add(basBoll);
        basBoll = new BasBoll("1st Base", false);
//        basBoll = new BasBoll(getResources().getString(R.string.base_1), false);
        bollList.add(basBoll);
        basBoll = new BasBoll("2nd Base", false);
//        basBoll = new BasBoll(getResources().getString(R.string.base_2), false);
        bollList.add(basBoll);
        basBoll = new BasBoll("Shortstop", false);
        bollList.add(basBoll);
        basBoll = new BasBoll("3rd Base", false);
//        basBoll = new BasBoll(getResources().getString(R.string.base_3), false);
        bollList.add(basBoll);
    }

    public void SetAdapter() {
        adapter = new BasBollAdapter(context, bollList, this);
        ListRecy.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        ListRecy.setLayoutManager(manager);
    }

    public void GetPosition(int pos) {
        if (pos == 0) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            LayoutOne.setVisibility(View.VISIBLE);
            LayoutTwo.setVisibility(View.GONE);
            LayoutThree.setVisibility(View.GONE);
            ListLayout.setVisibility(View.GONE);


        } else if (pos == 1) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            LayoutOne.setVisibility(View.GONE);
            LayoutTwo.setVisibility(View.VISIBLE);
            LayoutThree.setVisibility(View.GONE);
            ListLayout.setVisibility(View.GONE);
        } else if (pos == 2) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            LayoutOne.setVisibility(View.GONE);
            LayoutTwo.setVisibility(View.GONE);
            LayoutThree.setVisibility(View.VISIBLE);
            ListLayout.setVisibility(View.GONE);
        } else if (pos == 3) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
            LayoutOne.setVisibility(View.GONE);
            LayoutTwo.setVisibility(View.GONE);
            LayoutThree.setVisibility(View.GONE);
            ListLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnClick(int pos) {
        if (bollList.get(pos).isSelection()) {
            bollList.get(pos).setSelection(false);
            adapter.notifyDataSetChanged();
        } else {
            if (GetCount(bollList) != 3) {
                bollList.get(pos).setSelection(true);
                adapter.notifyDataSetChanged();
            } else {
                ShowAlert(RegistrationActivity.this, "Can not select more than 3 positions");
//                Toast.makeText(context, "Can not select more than 3 positions", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public int GetCount(List<BasBoll> list) {
        StringBuffer buffer = new StringBuffer();
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelection()) {
                count++;
                buffer.append(list.get(i).getName());
                PlayerPrefrences.saveInfo(Constant.SELECTION, buffer.toString(), context);

                if (count == 3)
                    return count;
                else
                    buffer.append(",");


            }
        }
        return 0;
    }




    public void GetDataPopup() {
        final Calendar cldr = Calendar.getInstance();
        cldr.set(1990, Calendar.JANUARY , 1);
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = (cldr.get(Calendar.YEAR));
        // date picker dialog
        picker = new DatePickerDialog(RegistrationActivity.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DateEdit.setText(String.format("%02d", (monthOfYear + 1)) + "/" + String.format("%02d", dayOfMonth) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
        picker.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    @Override
    public void onSuccessRegistration(RegistrationResponse response) {
        if (response.getCode().equalsIgnoreCase("200"))
            if (response.getStatus().equalsIgnoreCase("success")) {
                onLoggedIn();
            }
    }

    @Override
    public void onSuccessSignIn(SignInResponse response) {
        if (response.getStatus().equalsIgnoreCase("success")) {

            Gson gson = new Gson();
            PlayerPrefrences.saveInfo(Constant.PLAYER_PROFILE, gson.toJson(response), context);
            PlayerPrefrences.saveInfo(Constant.ACCESS_TOKEN, response.getData().getAccess_token(), context);
            PlayerPrefrences.saveInfo(Constant.LOGIN_STATUS, "1", context);
            startActivity(new Intent(RegistrationActivity.this, HomeScreen.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        } else {
            startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));
            finish();
        }
    }

    @Override
    public void onError(String error) {
        ShowAlert(RegistrationActivity.this, error);
    }


    private void onLoggedIn() {
        SignInParameter parameter = new SignInParameter();
        parameter.setAuthType(PlayerPrefrences.getInfo(Constant.SOCIAL_AUTH, context));
        parameter.setDeviceType(Constant.DEVICE_TYPE);
        parameter.setEmail(PlayerPrefrences.getInfo(Constant.SOCIAL_AUTH, context));
        parameter.setFacebookId("");
        parameter.setGoogleId(PlayerPrefrences.getInfo(Constant.GOOGLE_ID, context));
        new CallAPI(context).CallSignInAPI(this, true, parameter);
    }

    public  static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

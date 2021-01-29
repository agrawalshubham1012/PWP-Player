package com.player.coachesapp.Utils;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;


public class BaseLoader extends AppCompatActivity {

    ProgressDialog dialog;

    /*
     *
     * */
    public BaseLoader(Context context){

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);

    }

    /*
     * Show Loader
     * */
    public void hideLoader() {
        if (dialog.isShowing()){
            dialog.dismiss();
        }

    }

    /*
     * Hide Loader
     * */
    public void showLoader() {
        if (!dialog.isShowing()){
            dialog.show();
        }
    }

}
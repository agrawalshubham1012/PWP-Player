package com.player.coachesapp.Utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.ViewGroup;

import com.irozon.sneaker.Sneaker;
import com.player.coachesapp.R;

public class Utils {

    public static int SETSELECTION = 0;


    public static void ShowAlert(Activity context, String msg) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontsfree_net_sf_pro_rounded_regular.ttf");
        Sneaker.with(context)
                .setTitle("Alert", R.color.white)
                .setMessage(msg, R.color.white)
                .setDuration(3000)
                .setTypeface(font)
                .autoHide(true)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setIcon(R.drawable.logo_pln, R.color.white, false)
                .setCornerRadius(5, 5)
                .sneak(R.color.colorPrimary);

    }
}

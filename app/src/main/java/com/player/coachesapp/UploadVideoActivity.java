package com.player.coachesapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.APIClient;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Model.CoachDetailData;
import com.player.coachesapp.Model.SendVideoParameter;
import com.player.coachesapp.Model.SendVideoResponse;
import com.player.coachesapp.Model.UploadVideoResponse;
import com.player.coachesapp.Utils.CameraHelper;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.CustomMultiPartEntity;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.Utils.video.Util;
import com.player.coachesapp.Utils.video.VideoCompress;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.player.coachesapp.Utils.Utils.ShowAlert;

public class UploadVideoActivity extends BaseActivity implements APICallBack.UploadVideoCallback, BillingProcessor.IBillingHandler {

    @BindView(R.id.stateBar)
    StateProgressBar stateBar;
    @BindView(R.id.LayoutOne)
    LinearLayout LayoutOne;
    @BindView(R.id.LayoutTwo)
    LinearLayout LayoutTwo;
    @BindView(R.id.LayoutThree)
    LinearLayout LayoutThree;
    @BindView(R.id.uploadedBtn)
    TextView uploadedBtn;
    @BindView(R.id.successTxt)
    TextView successTxt;
    @BindView(R.id.commentExt)
    EditText commentExt;
    @BindView(R.id.priceTxt)
    TextView priceTxt;
    @BindView(R.id.backBtn)
    TextView backBtn;
    @BindView(R.id.nextBtn)
    TextView nextBtn;
    @BindView(R.id.purchseBtn)
    TextView purchseBtn;
    @BindView(R.id.okayBtnUploadVideo)
    TextView okayBtnUploadVideo;
    @BindView(R.id.layout_upload_video_success)
    RelativeLayout layoutUploadVideoSuccess;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.tvCoachName)
    TextView tvCoachName;
    @BindView(R.id.imgCoachProfile)
    CircleImageView imgCoachProfile;
    @BindView(R.id.imgTrophy)
    CircleImageView imgTrophy;
    @BindView(R.id.tvPriceString)
    TextView tvPriceString;
    @BindView(R.id.tvPriceConfirmText)
    TextView tvPriceConfirmText;
    private File mOutputCompressedFile = null, mOutputFile = null;
    private UploadVideoResponse.UploadVideoData uploadVideoData = null;
    private String coachID;
    long totalSize = 0;
    String filename = "";
    int position = 0;
    long startTime, endTime;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private Context context;
    BillingProcessor bp;
    private boolean readyToPurchase = false;
    private CoachDetailData coachDetailData = null;
    private String coachName ="";

    // PRODUCT & SUBSCRIPTION IDS
    private static final String SUBSCRIPTION_ID_SILVER = "silver";
    private static final String SUBSCRIPTION_ID_GOLD = "gold";
    private static final String SUBSCRIPTION_ID_PLATINUM = "platinum";

    private String selected_subscription ="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_video_screen);
        ButterKnife.bind(this);
        context = this;

        stateBar.setStateLineThickness(2.05f);
        stateBar.setAnimationDuration(1000);

        try {
            coachDetailData = (CoachDetailData) getIntent().getSerializableExtra("coach_data");
            if (null != coachDetailData) {
                coachID = coachDetailData.getCoachId();
                coachName = coachDetailData.getCoName() ;
                tvCoachName.setText(coachName);
                tvPriceConfirmText.setText("Ready to Practice with "+coachName+" ? Confirm your purchase of");
                String price = "";

                if (!TextUtils.isEmpty(coachDetailData.getCoCoachType())) {
                    switch (coachDetailData.getCoCoachType().toLowerCase()) {
                        case "silver":
                            selected_subscription = SUBSCRIPTION_ID_SILVER;
                            price = getResources().getString(R.string.silver_coach_price) ;
                            imgTrophy.setImageResource(R.drawable.silver_trophy);
                            break;
                        case "bronze":
                            tvPriceString.setText("For "+getResources().getString(R.string.upload_video_2));
                            imgTrophy.setImageResource(R.drawable.bronze_trophy);
                            break;
                        case "platinum":
                            selected_subscription = SUBSCRIPTION_ID_PLATINUM;
                           price =  getResources().getString(R.string.platinium_coach_price);
                        imgTrophy.setImageResource(R.drawable.platinum_trophy);
                            break;
                        case "gold":
                            selected_subscription = SUBSCRIPTION_ID_GOLD;
                            price =  getResources().getString(R.string.gold_coach_price);
                            imgTrophy.setImageResource(R.drawable.gold_trophy);
                            break;
                    }
                }
                priceTxt.setText(price);
                tvPriceString.setText("For "+price + " " + getResources().getString(R.string.upload_video_2));


                if (!TextUtils.isEmpty(coachDetailData.getCoAvatar())) {
                    Glide.with(this).load(coachDetailData.getCoAvatar()).placeholder(R.drawable.avatar).into(imgCoachProfile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        nextBtn.setPaintFlags(nextBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        backBtn.setPaintFlags(backBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        bp = new BillingProcessor(UploadVideoActivity.this, getResources().getString(R.string.Base64EncodeInAppBilling), this);
        bp.initialize();

//        bp.loadOwnedPurchasesFromGoogle();


    }


    @OnClick({R.id.uploadedBtn, R.id.backBtn, R.id.nextBtn, R.id.purchseBtn, R.id.backBtnMenu, R.id.okayBtnUploadVideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uploadedBtn:
                requestRead();
                break;
            case R.id.backBtn:
                if (position != 0) {
                    position--;
                    GetPosition(position);
                }
                if (position == 0) {
                    backBtn.setText("");
                }
                break;
            case R.id.nextBtn:
                if (validate()) {
                    position++;
                    if (position <= 2) {
                        GetPosition(position);
                        if(position == 2) {


                            boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
                            if (isAvailable) {
                                boolean isOneTimePurchaseSupported = bp.isOneTimePurchaseSupported();
                                if (isOneTimePurchaseSupported) {
                                    // launch payment flow
                                    if(readyToPurchase) {
                                        bp.purchase(this, selected_subscription);
                                        System.out.println("purchase id" + selected_subscription);
                                    }else {
                                        showToast("Billing not initialized.");
                                    }
                                }

                            } else {
                                Toast.makeText(context, "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else if (position == 3) {
//                        position = 1;
//                        finish();
                        callUploadDoneApi();
                    }
                    backBtn.setText("Back");

                }

                break;
            case R.id.purchseBtn:

                break;
            case R.id.backBtnMenu:
                onBackPressed();
                break;
            case R.id.okayBtnUploadVideo:
                layoutUploadVideoSuccess.setVisibility(View.GONE);
                startActivity(new Intent(this, ChatActivity.class)
                        .putExtra("id", "" + coachID)
                .putExtra("coach_data",coachDetailData));
                finish();
                break;
        }
    }

    private void callUploadDoneApi() {
        SendVideoParameter parameter = new SendVideoParameter();
        parameter.setCoachId(coachID);
        parameter.setPlayerId("");
        parameter.setMsgContent(commentExt.getText().toString().trim());
        parameter.setMsgFileUrl(uploadVideoData.getFileUrl());
        parameter.setMsgSendBy("player");
        new CallAPI(context).callSendVideoApi(this, true, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context), parameter);

    }

    private boolean validate() {
        if (position == 0) {
            if (null == uploadVideoData) {
                showToast("Upload video first");
                return false;
            } else {
                return true;
            }
        } else if (position == 1) {
            if (commentExt.getText().toString().trim().length() == 0) {
                showToast("Enter comment");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public void CallUploadVideoAPI(File file) {
        try {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-dara"), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("videos", file.getName(), requestFile);
            uploadedBtn.setText(file.getName());
            new CallAPI(context).CallUploadVideoApi(this, true, body, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetPosition(int pos) {
        if (pos == 0) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            LayoutOne.setVisibility(View.VISIBLE);
            LayoutTwo.setVisibility(View.GONE);
            LayoutThree.setVisibility(View.GONE);
        } else if (pos == 1) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            LayoutOne.setVisibility(View.GONE);
            LayoutTwo.setVisibility(View.VISIBLE);
            LayoutThree.setVisibility(View.GONE);
        } else if (pos == 2) {
            stateBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            LayoutOne.setVisibility(View.GONE);
            LayoutTwo.setVisibility(View.GONE);
            LayoutThree.setVisibility(View.VISIBLE);
        }
    }

    public void GetSelectVideo() {
        uploadVideoData = null;
        uploadedBtn.setText("Upload Video");
        successTxt.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), 101);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                try {
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        filename = getPathFromURI(this, selectedImageUri);
                        new Uploadtask().execute();
//                        callVideoCompress(getPathFromURI(this, selectedImageUri));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void callVideoCompress(String inputPath) {

        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

            String outputDir = CameraHelper.getOutputMediaFilePath();

            String destPath = outputDir + File.separator +
                    "VID_" + timeStamp + ".mp4";
            VideoCompress.compressVideoLow(inputPath, destPath, new VideoCompress.CompressListener() {
                @Override
                public void onStart() {
//                    tv_indicator.setText("Compressing..." + "\n"
//                            + "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()));
//                    pb_compress.setVisibility(View.VISIBLE);
                    showLoader();
                    startTime = System.currentTimeMillis();
                    Util.writeFile(UploadVideoActivity.this, "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                }

                @Override
                public void onSuccess() {
//                    String previous = tv_indicator.getText().toString();
//                    tv_indicator.setText(previous + "\n"
//                            + "Compress Success!" + "\n"
//                            + "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()));
//                    pb_compress.setVisibility(View.INVISIBLE);
                    hideLoader();
                    endTime = System.currentTimeMillis();
                    Util.writeFile(UploadVideoActivity.this, "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                    Util.writeFile(UploadVideoActivity.this, "Total: " + ((endTime - startTime) / 1000) + "s" + "\n");
                    Util.writeFile(UploadVideoActivity.this);
                    filename = destPath;
                    new Uploadtask().execute();
                }

                @Override
                public void onFail() {
//                    tv_indicator.setText("Compress Failed!");
//                    pb_compress.setVisibility(View.INVISIBLE);
                    hideLoader();
                    endTime = System.currentTimeMillis();
                    Util.writeFile(UploadVideoActivity.this, "Failed Compress!!!" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }

                @Override
                public void onProgress(float percent) {
//                    tv_progress.setText(String.valueOf(percent) + "%");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }


    public String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void requestRead() {
        boolean read_permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean write_permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (read_permission && write_permission) {
            GetSelectVideo();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {

            boolean read_permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            boolean write_permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            if (read_permission && write_permission) {

//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetSelectVideo();
            } else {
                // Permission Denied
                Toast.makeText(UploadVideoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    public void onSuccessVideoUpload(UploadVideoResponse uploadVideoResponse) {
        showToast(uploadVideoResponse.getMessage());
        successTxt.setVisibility(View.VISIBLE);
        if (null != uploadVideoResponse.getData()) {
            this.uploadVideoData = uploadVideoResponse.getData();
        }
    }

    @Override
    public void onSuccessSendVideo(SendVideoResponse response) {
        boolean val = bp.consumePurchase(selected_subscription);
        System.out.println("consume value "+selected_subscription+" "+val);
        layoutUploadVideoSuccess.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTokenChangeError(String message) {
        userLogout();
    }

    @Override
    public void onError(String error) {
        showToast(error);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        /*
         * Called when requested PRODUCT ID was successfully purchased
         */
        Toast.makeText(context, "Product Purchased Successfully", Toast.LENGTH_SHORT).show();
        callUploadDoneApi();

    }

    @Override
    public void onPurchaseHistoryRestored() {
        /*
         * Called when purchase history was restored and the list of all owned PRODUCT ID's
         * was loaded from Google Play
         */


    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        /*
         * Called when some error occurred. See Constants class for more details
         *
         * Note - this includes handling the case where the user canceled the buy dialog:
         * errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED
         */
        showToast("onBillingError: " + Integer.toString(errorCode));

    }

    @Override
    public void onBillingInitialized() {
        /*
         * Called when BillingProcessor was initialized and it's ready to purchase
         */
        readyToPurchase = true;

    }


//    class VideoCompressor extends AsyncTask<Void, Void, Boolean> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            showLoader();
//            showToast("Start video compression");
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            return MediaController.getInstance().convertVideo(mOutputFile.getPath(), mOutputCompressedFile);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean compressed) {
//            super.onPostExecute(compressed);
//            hideLoader();
//            if (compressed) {
//                showToast("Compression successfully!");
//                CallUploadVideoAPI(mOutputFile);
//
//            } else {
//                mOutputCompressedFile = null;
//            }
//        }
//    }

    private class Uploadtask extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            successTxt.setVisibility(View.VISIBLE);
            successTxt.setText("Uploading " + 0 + "%");
            pb.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
//            pb.setProgress(progress[0]);
            if (progress[0] == 100) {
                successTxt.setText("Uploading " + 99 + "%");
            } else {
                successTxt.setText("Uploading " + progress[0] + "%");
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return upload();
        }

        private String upload() {
            String responseString = "no";

            File sourceFile = new File(filename);
            if (!sourceFile.isFile()) {
                return "not a file";
            }
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIClient.BASE_URL + "videoupload");
            try {
                uploadedBtn.setText(sourceFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                CustomMultiPartEntity entity = new CustomMultiPartEntity(new CustomMultiPartEntity.ProgressListener() {

                    @Override
                    public void transferred(long num) {
                        publishProgress((int) ((num / (float) totalSize) * 100));
                    }
                });
                httppost.setHeader("ACCESS-TOKEN", PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, UploadVideoActivity.this));
                entity.addPart("videos", new FileBody(sourceFile));
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                responseString = EntityUtils.toString(r_entity);

            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }


        @Override
        protected void onPostExecute(String result) {
            successTxt.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
            hideLoader();
            try {
                if (result != null && !result.equalsIgnoreCase("")) {
                    try {
                        if (!result.equalsIgnoreCase("")) {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<UploadVideoResponse>() {
                            }.getType();
                            UploadVideoResponse reviewCartModel = gson.fromJson(result, collectionType);

                            if (null != reviewCartModel && reviewCartModel.getStatus().equalsIgnoreCase("success")) {

                                if (null != reviewCartModel.getData()) {
                                    uploadVideoData = reviewCartModel.getData();
//                                    uploadedBtn.setText(reviewCartModel.getData().getOriginalname());
                                    successTxt.setText("Uploaded successfully");
                                } else {
                                    uploadedBtn.setText("Upload Video");
                                    successTxt.setText("Upload Failed");
                                }
                            } else {
                                successTxt.setText("Upload Failed");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();

    }
}

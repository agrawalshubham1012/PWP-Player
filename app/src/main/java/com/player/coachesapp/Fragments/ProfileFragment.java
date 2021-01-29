package com.player.coachesapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.HomeScreen;
import com.player.coachesapp.Model.ProfileUploadResponse;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.R;
import com.player.coachesapp.SettingActivity;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.common_web_view.WebViewActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;





public class ProfileFragment extends Fragment implements APICallBack.ProfileUpdateCallback {

    Unbinder unbinder1;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.editImg)
    ImageView editImg;
    @BindView(R.id.from_camera)
    LinearLayout fromCamera;
    @BindView(R.id.from_gallery)
    LinearLayout fromGallery;
    @BindView(R.id.cancelPopup)
    TextView cancelPopup;
    @BindView(R.id.picImageLayout)
    RelativeLayout picImageLayout;
    private Context context;
    private View view;
    private Unbinder unbinder;

    @BindView(R.id.profileName)
    TextView profileName;
    @BindView(R.id.profileAge)
    TextView profileAge;
    @BindView(R.id.firstOne)
    TextView firstOne;
    @BindView(R.id.secondOne)
    TextView secondOne;
    @BindView(R.id.lastOne)
    TextView lastOne;
    final int REQUEST_CODE_ASK_PERMISSIONS_GALLERY = 555;
    final int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 666;
    private boolean isRefresh = true ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        context = getActivity();
        unbinder1 = ButterKnife.bind(this, view);
        SetProfileData();
        return view;
    }


    @SuppressLint("SetTextI18n")
    public void SetProfileData() {

        try {
            SignInResponse sign = PlayerPrefrences.GetUserProfile(context);

            profileName.setText(sign.getData().getPl_first_name()
                    + " " + sign.getData().getPl_last_name());

            if (sign.getData().getPl_position().size() == 3) {
                firstOne.setText(sign.getData().getPl_position().get(0));
                secondOne.setText(sign.getData().getPl_position().get(1));
                lastOne.setText(sign.getData().getPl_position().get(2));
            }

            if(!TextUtils.isEmpty(sign.getData().getPl_avatar())){
                Glide.with(this).load(sign.getData().getPl_avatar()).into(profileImg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick({R.id.settingsRL, R.id.contactUsRL, R.id.termsRL, R.id.privacyRL, R.id.editImg, R.id.from_camera, R.id.from_gallery, R.id.cancelPopup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settingsRL:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.contactUsRL:
                startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("title", "Contact Us")
                        .putExtra("url", "https://practicewithpros.app"));
                break;
            case R.id.termsRL:
            case R.id.privacyRL:
                startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("title", "Terms & Privacy Policy")
                        .putExtra("url", "https://practicewithpros.app/term_and_condition.html"));
                break;
            case R.id.editImg:
                picImageLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.from_camera:
                picImageLayout.setVisibility(View.GONE);
                click_pic();
                break;
            case R.id.from_gallery:
                picImageLayout.setVisibility(View.GONE);
                if (checkAndRequestPermissions()) {
                    galleryIntent();
                }
                break;
            case R.id.cancelPopup:
                picImageLayout.setVisibility(View.GONE);
                break;
        }
    }


//Image Upload
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == SELECT_FILE){
            Bitmap bm;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), data.getData());
                    if(null != bm && null != getContext()) {
                        CropImage.activity(getImageUri(context, bm)).setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(getContext(), this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(resultCode == RESULT_OK && requestCode == REQUEST_CAMERA){
            try {
                    Bitmap bannerBitmap = (Bitmap) data.getExtras().get("data");
                    if (null != bannerBitmap && null != getContext()) {
                        CropImage.activity(getImageUri(context, bannerBitmap)).setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .start(getContext(), this);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
             Uri destination = null;
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                destination = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            try {
                if (destination != null) {
                   Bitmap photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), destination);
//                    CALLAPI(getUserImageFile(photo));
                    CALLAPI(compressImage(destination.getPath()), photo);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap rotateImage(Bitmap b, int angle) {
        Matrix matrix = new Matrix();

        matrix.postRotate(angle);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    private int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    rotate = 0;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public File compressImage(String filePath) {
        File originalFile = new File(filePath);
        long fileSizeInBytes = originalFile.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        if (fileSizeInKB < 150) {
            return originalFile;
        }
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File f = new File(getContext().getCacheDir(), UUID.randomUUID().toString() + "images.png");
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    click_pic();
                }
            }
            break;
            case REQUEST_CODE_ASK_PERMISSIONS_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                }
            }
            break;
        }
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    private void requestClickPic() {
        requestPermissions(new String[]{CAMERA,WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);

    }

    private void click_pic() {
        int result1 = ContextCompat.checkSelfPermission(context, CAMERA);
        int result3 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean result = (result1 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED);
        if (result) {
            scanFish();
        } else {
            requestClickPic();
        }
    }

    private void scanFish() {
        try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private boolean checkAndRequestPermissions() {


        int SD_CARD = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int WRITE_PERMISSION = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (WRITE_PERMISSION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (SD_CARD != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
           requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CODE_ASK_PERMISSIONS_GALLERY);
            return false;
        }
        return true;
    }



    public void  CALLAPI(File file, Bitmap photo) {
        try {
            if(null != file) {
                try{
                    isRefresh = false ;
                    profileImg.setImageBitmap(photo);
                }catch (Exception e){
                    e.printStackTrace();
                }
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-dara"), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("pl_avatar", file.getName(), requestFile);
                new CallAPI(context).CallUploadProfileApi(this, false, body, PlayerPrefrences.getInfo(Constant.ACCESS_TOKEN, context));
            }
            }catch (Exception e){
            e.printStackTrace();
        }

    }


    private File getUserImageFile(Bitmap bitmap) {
        try {
            File f = new File(context.getCacheDir(), "images.png");
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    @Override
    public void onSuccessUpdate(ProfileUploadResponse response) {
        try {
            SignInResponse sign = PlayerPrefrences.GetUserProfile(context);
            sign.getData().setPl_avatar(response.getData().getImageUrl());
            Gson gson = new Gson();
            PlayerPrefrences.saveInfo(Constant.PLAYER_PROFILE, gson.toJson(sign), context);
            try {
                if(null != context) {
                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if(null != profileImg && isRefresh) {
                    if (!TextUtils.isEmpty(sign.getData().getPl_avatar())) {
                        Glide.with(this).load(sign.getData().getPl_avatar()).into(profileImg);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }
}

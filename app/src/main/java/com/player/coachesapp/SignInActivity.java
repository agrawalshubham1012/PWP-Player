package com.player.coachesapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.player.coachesapp.API.APICallBack;
import com.player.coachesapp.API.CallAPI;
import com.player.coachesapp.Model.SignInParameter;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Utils.Constant;
import com.player.coachesapp.Utils.PlayerPrefrences;
import com.player.coachesapp.common_web_view.WebViewActivity;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.player.coachesapp.Utils.Utils.ShowAlert;

public class SignInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, APICallBack.SignInCallback {

    private static final int RC_SIGN_IN = 100;
    @BindView(R.id.FaceBookLayout)
    RelativeLayout FaceBookLayout;
    @BindView(R.id.GoogleLayout)
    RelativeLayout GoogleLayout;
    @BindView(R.id.sign_in_button)
    SignInButton sign_in_button;
    CallbackManager callbackManager;
    LoginButton loginButton;
    @BindView(R.id.terms_conditions)
    TextView termsConditions;
    private Context context = this;

    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = getClass().getSimpleName();
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_screen);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        ButterKnife.bind(this);
        deleteAccessToken();
        GoogleSignInConfig();
        facebookSignInConfig();

        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("title", "Terms & Privacy Policy")
                        .putExtra("url", "https://practicewithpros.app/term_and_condition.html"));
            }
        });
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
                System.out.println(hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
//            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
//            Log.e(TAG, "printHashKey()", e);
        }
    }


    @OnClick({R.id.FaceBookLayout, R.id.GoogleLayout, R.id.sign_in_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.FaceBookLayout:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                break;
            case R.id.GoogleLayout:
                googleSignOut();
                signIn();
                break;
        }
    }


    /*
     * Google Sign In
     * */
    private void signIn() {
        showLoader();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void GoogleSignInConfig() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(SignInActivity.this.getResources().getString(R.string.server_client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
            hideLoader();

            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            } catch (Exception e) {
                // The ApiException status code indicates the detailed failure reason.
                e.printStackTrace();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }


    /*
     * Result Handle by Google
     * */
 /*   private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Toast.makeText(context,email,Toast.LENGTH_LONG).show();
            Toast.makeText(context,personName,Toast.LENGTH_LONG).show();
        }
    }*/

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            onLoggedIn(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.getStatusCode(), Toast.LENGTH_SHORT).show();
            // updateUI(null);
        }


    }


    private void facebookSignInConfig() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println(loginResult.getAccessToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {
                                String id = "", email = "", first_name = "", last_name = "";
                                try {
                                    id = jsonObject.optString("id");
                                    first_name = jsonObject.optString("first_name");
                                    last_name = jsonObject.optString("last_name");
                                    email = jsonObject.optString("email");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                PlayerPrefrences.saveInfo(Constant.SOCIAL_AUTH, "facebook", context);
                                PlayerPrefrences.saveInfo(Constant.FACEBOOK_ID, id, context);
                                PlayerPrefrences.saveInfo(Constant.FIRST_NAME, first_name, context);
                                PlayerPrefrences.saveInfo(Constant.LAST_NAME, last_name, context);
                                PlayerPrefrences.saveInfo(Constant.GOOGLE_EMAIL, email, context);
                                PlayerPrefrences.saveInfo(Constant.GOOGLE_ID, "", context);

                                SignInParameter parameter = new SignInParameter();
                                parameter.setAuthType(PlayerPrefrences.getInfo(Constant.SOCIAL_AUTH, context));
                                parameter.setDeviceType(Constant.DEVICE_TYPE);
                                parameter.setEmail(email);
                                parameter.setFacebookId(id);
                                parameter.setGoogleId("");
                                parameter.setFcmToken(FirebaseInstanceId.getInstance().getToken());

                                callApi(parameter);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,last_name,first_name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                deleteAccessToken();
            }
        });

    }


    private void onLoggedIn(GoogleSignInAccount account) {

        PlayerPrefrences.saveInfo(Constant.SOCIAL_AUTH, "google", context);
        PlayerPrefrences.saveInfo(Constant.FACEBOOK_ID, "", context);

        SignInParameter parameter = new SignInParameter();
        parameter.setAuthType(PlayerPrefrences.getInfo(Constant.SOCIAL_AUTH, context));
        parameter.setDeviceType(Constant.DEVICE_TYPE);
        parameter.setEmail(account.getEmail());
        parameter.setFacebookId("");
        parameter.setGoogleId(account.getId());
        parameter.setFcmToken(FirebaseInstanceId.getInstance().getToken());
        String firstname = "";
        String lastname = "";
        try {
            String fullname = account.getDisplayName();
            if (null != fullname && !fullname.equals("")) {
                String[] parts = fullname.split("\\s+");
                if (parts.length >= 2) {
                    firstname = parts[0];
                    lastname = parts[1];
                } else {
                    firstname = fullname;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerPrefrences.saveInfo(Constant.FIRST_NAME, firstname, context);
        PlayerPrefrences.saveInfo(Constant.LAST_NAME, lastname, context);
        PlayerPrefrences.saveInfo(Constant.GOOGLE_EMAIL, account.getEmail(), context);
        PlayerPrefrences.saveInfo(Constant.GOOGLE_ID, account.getId(), context);
        callApi(parameter);


    }

    private void callApi(SignInParameter parameter) {
        new CallAPI(context).CallSignInAPI(this, true, parameter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(context, "", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSuccessSignIn(SignInResponse response) {
        if (response.getStatus().equalsIgnoreCase("success")) {
            Gson gson = new Gson();
            PlayerPrefrences.saveInfo(Constant.PLAYER_PROFILE, gson.toJson(response), context);
            PlayerPrefrences.saveInfo(Constant.ACCESS_TOKEN, response.getData().getAccess_token(), context);
            PlayerPrefrences.saveInfo(Constant.LOGIN_STATUS, "1", context);
            startActivity(new Intent(SignInActivity.this, HomeScreen.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        } else {
            startActivity(new Intent(SignInActivity.this, RegistrationActivity.class));
            finish();
        }

    }

    @Override
    public void onError(String error) {
        ShowAlert(SignInActivity.this, error);
    }


}

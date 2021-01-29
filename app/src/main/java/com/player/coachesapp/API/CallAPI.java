package com.player.coachesapp.API;

import android.content.Context;

import com.player.coachesapp.Model.ChatListParameter;
import com.player.coachesapp.Model.ChatListResponse;
import com.player.coachesapp.Model.CoachDetailResponse;
import com.player.coachesapp.Model.CoachInput;
import com.player.coachesapp.Model.CoachListResponse;
import com.player.coachesapp.Model.CommonResponseModel;
import com.player.coachesapp.Model.FavoriteInput;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.Model.FavoriteVideoResponse;
import com.player.coachesapp.Model.MessageListResponse;
import com.player.coachesapp.Model.NotificationMuteUnMuteParam;
import com.player.coachesapp.Model.ProfileUploadResponse;
import com.player.coachesapp.Model.RegistrationParameter;
import com.player.coachesapp.Model.RegistrationResponse;
import com.player.coachesapp.Model.SendVideoParameter;
import com.player.coachesapp.Model.SendVideoResponse;
import com.player.coachesapp.Model.SignInParameter;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Model.UploadVideoResponse;
import com.player.coachesapp.Model.VideoInput;
import com.player.coachesapp.Model.VideoResponse;
import com.player.coachesapp.Utils.BaseLoader;
import com.player.coachesapp.tab_library.model.LibraryCategoryResponse;
import com.player.coachesapp.tab_library.model.VideoListParameter;
import com.player.coachesapp.tab_library.model.VideoListResponse;

import java.io.IOException;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallAPI extends BaseLoader {

    public static BaseLoader loader;

    public CallAPI(Context context) {
        super(context);
        loader = new BaseLoader(context);
    }


    /*
     * Get All Coach List
     * */
    public static void CallCoachListAPI(APICallBack.CoachListCallBack callback, boolean Isloader, String token, CoachInput input) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<CoachListResponse> callLoginApi = anInterface.GetCoachListApi(token, input);
        callLoginApi.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            callback.onSuccess(response.body());
                        }else {
                            if(response.body().getCode() == 400){
                                callback.onTokenChangeError(response.body().getMessage());
                            }else {
                                callback.onError(response.body().getMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    /*
     * Get All Coach List
     * */
    public static void CallCoachDetailAPI(APICallBack.CoachDetailCallback callback, boolean Isloader, String coachID, String token) {
        if (Isloader)
            loader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<CoachDetailResponse> callLoginApi = anInterface.GetCoachDetailAPI(token, coachID);
        callLoginApi.enqueue(new Callback<CoachDetailResponse>() {
            @Override
            public void onResponse(Call<CoachDetailResponse> call, Response<CoachDetailResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if(null != response.body()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        callback.onSuccessDetail(response.body());
                    }else {
                        if(response.body().getCode() == 400){
                            callback.onTokenChangeError(response.body().getMessage());
                        }else {
                            callback.onError(response.body().getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CoachDetailResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }



    public static void CallFavoriteCoachListAPI(APICallBack.CoachFavoriteListCallBack callback, boolean Isloader, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<CoachListResponse> callLoginApi = anInterface.GetFavoriteCoachListApi(token);
        callLoginApi.enqueue(new Callback<CoachListResponse>() {
            @Override
            public void onResponse(Call<CoachListResponse> call, Response<CoachListResponse> response) {
                if (Isloader)
                    loader.hideLoader();

                if (response.isSuccessful()) {
                    if (null != response.body()) {
                            callback.onFavoriteCoachListSuccess(response.body());
                    }

            }
            }

            @Override
            public void onFailure(Call<CoachListResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    /*
     * Get All Coach List
     * */
    public static void CallFavoriteVideoListAPI(APICallBack.FavoriteVideoListCallBack callback, boolean Isloader, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<VideoResponse> callLoginApi = anInterface.GetFavoriteVideoListApi(token);
        callLoginApi.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if (null != response.body()) {
                            callback.onFavoriteVideoListSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void callLibraryVideoListAPI(APICallBack.GetLibraryVideoListCallBack callback, boolean Isloader, String token, VideoListParameter parameter) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<VideoListResponse> callLoginApi = anInterface.callLibraryVideoListAPI(token,parameter);
        callLoginApi.enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                if (Isloader)
                    loader.hideLoader();

                if (response.isSuccessful()) {
                    callback.onVideoListSuccess(response.body());
                }

            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onErrorNoDataFound("Server down or no internet connection");
                } else {
                    callback.onErrorNoDataFound("Oops something went wrong");
                }
            }
        });
    }


    public static void CallLibraryCategoriesAPI(APICallBack.GetLibraryCategoryListCallBack callback, boolean Isloader, String token, String ID) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<LibraryCategoryResponse> callLoginApi = anInterface.callLibraryCategoriesAPI(token, ID);
        callLoginApi.enqueue(new Callback<LibraryCategoryResponse>() {
            @Override
            public void onResponse(Call<LibraryCategoryResponse> call, Response<LibraryCategoryResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            callback.onCategoryListSuccess(response.body());
                        }else {
                            callback.onError(response.body().getMessage());

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LibraryCategoryResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void CallSignInAPI(APICallBack.SignInCallback callback, boolean Isloader, SignInParameter parameter) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SignInResponse> callLoginApi = anInterface.callSignInApi(parameter);
        callLoginApi.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    callback.onSuccessSignIn(response.body());
                } else {
                    callback.onError("Oops something went wrong");
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void CallRegistrationApi(APICallBack.RegistrationCallback callback, boolean Isloader, RegistrationParameter parameter) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<RegistrationResponse> callLoginApi = anInterface.callRegistration(parameter);
        callLoginApi.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (null != response.body()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        callback.onSuccessRegistration(response.body());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Oops something went wrong");
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void CallFavoriteApi(APICallBack.FavoriteCallback callback, boolean Isloader, FavoriteInput input, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavoriteResponse> callLoginApi = anInterface.AddFavoriteAPI(token,input);
        callLoginApi.enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (null != response.body()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        callback.onSuccessFavorite(response.body());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Oops something went wrong");
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void CallVideoFavoriteApi(APICallBack.VideoFavoriteCallback callback, boolean Isloader, VideoInput input, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<FavoriteVideoResponse> callLoginApi = anInterface.AddFavoriteVideoAPI(token,input);
        callLoginApi.enqueue(new Callback<FavoriteVideoResponse>() {
            @Override
            public void onResponse(Call<FavoriteVideoResponse> call, Response<FavoriteVideoResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (null != response.body()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        callback.onSuccessVideoFavorite(response.body());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Oops something went wrong");
                }
            }

            @Override
            public void onFailure(Call<FavoriteVideoResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }


    public static void CallUploadProfileApi(APICallBack.ProfileUpdateCallback callback, boolean Isloader, MultipartBody.Part input, String token) {
        if (Isloader)
            loader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<ProfileUploadResponse> callLoginApi = anInterface.UploadProfileAPI(token,input);
        callLoginApi.enqueue(new Callback<ProfileUploadResponse>() {
            @Override
            public void onResponse(Call<ProfileUploadResponse> call, Response<ProfileUploadResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (null != response.body()) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        callback.onSuccessUpdate(response.body());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } else {
                    callback.onError("Oops something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ProfileUploadResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void callLogoutApi(APICallBack.SettingCallback callback, boolean Isloader, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<CommonResponseModel> callLoginApi = anInterface.callLogoutApi(token);
        callLoginApi.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (Isloader)
                    loader.hideLoader();

                if (response.isSuccessful()) {
                    callback.onLogoutSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void callNotificationMuteUnMuteApi(APICallBack.SettingCallback callback, boolean Isloader, String token, NotificationMuteUnMuteParam param) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<CommonResponseModel> callLoginApi = anInterface.callNotificationMuteUnMuteApi(token, param);
        callLoginApi.enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (Isloader)
                    loader.hideLoader();

                if (response.isSuccessful()) {
                    callback.onNotificationMuteUnmute(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void CallUploadVideoApi(APICallBack.UploadVideoCallback callback, boolean Isloader, MultipartBody.Part input, String token) {
        if (Isloader)
            loader.showLoader();
        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<UploadVideoResponse> callLoginApi = anInterface.callUploadVideoApi(token,input);
        callLoginApi.enqueue(new Callback<UploadVideoResponse>() {
            @Override
            public void onResponse(Call<UploadVideoResponse> call, Response<UploadVideoResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                            callback.onSuccessVideoUpload(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadVideoResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void callSendVideoApi(APICallBack.UploadVideoCallback callback, boolean Isloader, String token, SendVideoParameter param) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<SendVideoResponse> callLoginApi = anInterface.callSendVideoApi(token, param);
        callLoginApi.enqueue(new Callback<SendVideoResponse>() {
            @Override
            public void onResponse(Call<SendVideoResponse> call, Response<SendVideoResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            callback.onSuccessSendVideo(response.body());
                        }else {
                            callback.onError(response.body().getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SendVideoResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void callCoachMessageList(APICallBack.CoachMessageListCallBack callback, boolean Isloader, String token) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<MessageListResponse> callLoginApi = anInterface.GetCoachMessageListApi(token);
        callLoginApi.enqueue(new Callback<MessageListResponse>() {
            @Override
            public void onResponse(Call<MessageListResponse> call, Response<MessageListResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            callback.onSuccess(response.body());
                        }else {
                            if(response.body().getCode() == 400){
                                callback.onTokenChangeError(response.body().getMessage());
                            }else {
                                callback.onError(response.body().getMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageListResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

    public static void callChatList(APICallBack.ChatListCallBack callback, boolean Isloader, String token, ChatListParameter input) {
        if (Isloader)
            loader.showLoader();

        APIInterface anInterface = APIClient.getClient().create(APIInterface.class);
        Call<ChatListResponse> callLoginApi = anInterface.GetChatList(token, input);
        callLoginApi.enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                if (Isloader)
                    loader.hideLoader();
                if (response.isSuccessful()) {
                    if(null != response.body()) {
                        if (response.body().getStatus().equalsIgnoreCase("success")) {
                            callback.onSuccess(response.body());
                        }else {
                            if(response.body().getCode() == 400){
                                //TODO
                                callback.onError(response.body().getMessage());
                            }else {
                                callback.onError(response.body().getMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {
                if (Isloader)
                    loader.hideLoader();
                if (t instanceof IOException) {
                    callback.onError("Server down or no internet connection");
                } else {
                    callback.onError("Oops something went wrong");
                }
            }
        });
    }

}

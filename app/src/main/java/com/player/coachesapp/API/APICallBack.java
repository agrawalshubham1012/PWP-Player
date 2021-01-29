package com.player.coachesapp.API;

import com.player.coachesapp.Model.ChatListResponse;
import com.player.coachesapp.Model.CoachDetailResponse;
import com.player.coachesapp.Model.CoachListResponse;
import com.player.coachesapp.Model.CommonResponseModel;
import com.player.coachesapp.Model.FavoriteResponse;
import com.player.coachesapp.Model.FavoriteVideoResponse;
import com.player.coachesapp.Model.MessageListResponse;
import com.player.coachesapp.Model.PlayerRegisterResponse;
import com.player.coachesapp.Model.ProfileUploadResponse;
import com.player.coachesapp.Model.RegistrationResponse;
import com.player.coachesapp.Model.SendVideoResponse;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Model.UploadVideoResponse;
import com.player.coachesapp.Model.VideoResponse;
import com.player.coachesapp.tab_library.model.LibraryCategoryResponse;
import com.player.coachesapp.tab_library.model.VideoListResponse;

public interface APICallBack {

    interface CoachListCallBack  {
        void onSuccess(CoachListResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface CoachFavoriteListCallBack  {
        void onFavoriteCoachListSuccess(CoachListResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface FavoriteVideoListCallBack  {
        void onFavoriteVideoListSuccess(VideoResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface GetLibraryVideoListCallBack {
        void onVideoListSuccess(VideoListResponse response);
        void onTokenChangeError(String message);
        void onErrorNoDataFound(String error);
    }

    interface GetLibraryCategoryListCallBack {
        void onCategoryListSuccess(LibraryCategoryResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface ProfileCallBack  {
        void onSuccess(CoachListResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface SignInCallback  {
        void onSuccessSignIn(SignInResponse response);
        void onError(String error);
    }

    interface RegisterPlayerCallBack  {
        void onSuccess(PlayerRegisterResponse response);
        void onError(String error);
    }

    interface RegistrationCallback  {
        void onSuccessRegistration(RegistrationResponse response);
        void onError(String error);
    }

    interface FavoriteCallback  {
        void onSuccessFavorite(FavoriteResponse response);
        void onError(String error);
    }

    interface VideoFavoriteCallback  {
        void onSuccessVideoFavorite(FavoriteVideoResponse response);
        void onError(String error);
    }

    interface ProfileUpdateCallback  {
        void onSuccessUpdate(ProfileUploadResponse response);
        void onError(String error);
    }

    interface CoachDetailCallback  {
        void onSuccessDetail(CoachDetailResponse response);
        void onSuccessFavorite(FavoriteResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface SettingCallback {
        void onNotificationMuteUnmute(CommonResponseModel commonResponseModel);
        void onLogoutSuccess(CommonResponseModel commonResponseModel);
        void  onError(String error);
    }

    interface UploadVideoCallback {
        void onSuccessVideoUpload(UploadVideoResponse uploadVideoResponse);
        void onSuccessSendVideo(SendVideoResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }


    interface CoachMessageListCallBack  {
        void onSuccess(MessageListResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }

    interface ChatListCallBack  {
        void onSuccess(ChatListResponse response);
        void onTokenChangeError(String message);
        void onError(String error);
    }
}

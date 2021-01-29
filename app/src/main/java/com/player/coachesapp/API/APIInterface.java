package com.player.coachesapp.API;

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
import com.player.coachesapp.Model.PlayerRegisterResponse;
import com.player.coachesapp.Model.ProfileUploadResponse;
import com.player.coachesapp.Model.RegisterInputModel;
import com.player.coachesapp.Model.SendVideoParameter;
import com.player.coachesapp.Model.SendVideoResponse;
import com.player.coachesapp.Model.SignInParameter;
import com.player.coachesapp.Model.SignInResponse;
import com.player.coachesapp.Model.RegistrationParameter;
import com.player.coachesapp.Model.RegistrationResponse;
import com.player.coachesapp.Model.UploadVideoResponse;
import com.player.coachesapp.Model.VideoInput;
import com.player.coachesapp.Model.VideoResponse;
import com.player.coachesapp.tab_library.model.LibraryCategoryResponse;
import com.player.coachesapp.tab_library.model.VideoListParameter;
import com.player.coachesapp.tab_library.model.VideoListResponse;

import java.util.HashMap;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 *  Created by Suraj Shakya on 01/06/2018.
 */
public interface APIInterface {

    /*
     * API for Get Rss Blog
     * */
    @POST("coachlist")
    Call<CoachListResponse> GetCoachListApi(@Header("ACCESS-TOKEN")String token, @Body CoachInput input);


    @GET("favoritecoachlist")
    Call<CoachListResponse> GetFavoriteCoachListApi(@Header("ACCESS-TOKEN")String token);


    @GET("favoritevideolist")
    Call<VideoResponse> GetFavoriteVideoListApi(@Header("ACCESS-TOKEN")String token);


    @GET("get_category")
    Call<LibraryCategoryResponse> callLibraryCategoriesAPI(@Header("ACCESS-TOKEN")String token, @Query("parent_id") String parentID);



    @POST("videolist")
    Call<VideoListResponse> callLibraryVideoListAPI(@Header("ACCESS-TOKEN")String token, @Body VideoListParameter parameter);


    /*
     *  Get Player Detail
     * */
    @GET("coachdetails")
    Call<CoachDetailResponse> GetCoachDetailAPI(@Header("ACCESS-TOKEN")String token, @Query("id") String id);


    /*
     * Register Player
     * */
    @POST("registerplayer")
    Call<PlayerRegisterResponse> RegisterPlayerAPI(@Body RegisterInputModel input);

    /*
     * add coach to favorite
     * */
    @POST("addfavoritecoach")
    Call<FavoriteResponse> AddFavoriteAPI(@Header("ACCESS-TOKEN")String token, @Body FavoriteInput input);

    /*
     * add video to favorite
     * */
    @POST("addfavoritevideo")
    Call<FavoriteVideoResponse> AddFavoriteVideoAPI(@Header("ACCESS-TOKEN")String token, @Body VideoInput input);


    @Multipart
    @POST("uploadprofile")
    Call<ProfileUploadResponse> UploadProfileAPI(@Header("ACCESS-TOKEN")String token, @Part MultipartBody.Part file);

    /*
     * get Google Access Token
     * */
    @FormUrlEncoded
    @POST("https://accounts.google.com/o/oauth2/token")
    Call<ResponseBody> getGoogleAcceesToken(@FieldMap HashMap<String,String> map);



    @POST("playerlogin")
    Call<SignInResponse>callSignInApi(@Body SignInParameter parameter);

    @POST("signup")
    Call<RegistrationResponse>callRegistration(@Body RegistrationParameter parameter);

    /*
     * logout user
     * */
    @GET("logout")
    Call<CommonResponseModel> callLogoutApi(@Header("ACCESS-TOKEN")String token);

    /*
     * notifications on off
     * */
    @POST("notification")
    Call<CommonResponseModel> callNotificationMuteUnMuteApi(@Header("ACCESS-TOKEN")String token, @Body NotificationMuteUnMuteParam param);



    @Multipart
    @POST("videoupload")
    Call<UploadVideoResponse> callUploadVideoApi(@Header("ACCESS-TOKEN")String token, @Part MultipartBody.Part file);

    @POST("sendvideo")
    Call<SendVideoResponse> callSendVideoApi(@Header("ACCESS-TOKEN")String token, @Body SendVideoParameter param);

    @POST("coachmessagelist")
    Call<MessageListResponse> GetCoachMessageListApi(@Header("ACCESS-TOKEN")String token);

    @POST("messagelist")
    Call<ChatListResponse> GetChatList(@Header("ACCESS-TOKEN")String token, @Body ChatListParameter parameter);

}

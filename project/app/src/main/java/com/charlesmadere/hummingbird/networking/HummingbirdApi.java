package com.charlesmadere.hummingbird.networking;

import com.charlesmadere.hummingbird.models.AbsAnime;
import com.charlesmadere.hummingbird.models.AnimeDigest;
import com.charlesmadere.hummingbird.models.AnimeV2;
import com.charlesmadere.hummingbird.models.AuthInfo;
import com.charlesmadere.hummingbird.models.Feed;
import com.charlesmadere.hummingbird.models.LibraryEntry;
import com.charlesmadere.hummingbird.models.LibraryUpdate;
import com.charlesmadere.hummingbird.models.MangaDigest;
import com.charlesmadere.hummingbird.models.SearchBundle;
import com.charlesmadere.hummingbird.models.SearchDepth;
import com.charlesmadere.hummingbird.models.SearchScope;
import com.charlesmadere.hummingbird.models.UserDigest;
import com.charlesmadere.hummingbird.models.UserV2;
import com.charlesmadere.hummingbird.models.WatchingStatus;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HummingbirdApi {

    /*
     * v1
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v1-Methods
     */

    @POST("api/v1/libaries/{id}")
    Call<LibraryEntry> addOrUpdateLibraryEntry(@Path("id") String id,
            @Body LibraryUpdate libraryUpdate);

    @POST("api/v1/users/authenticate")
    Call<String> authenticate(@Body AuthInfo authInfo);

    @GET("api/v1/users/{username}/favorite_anime")
    Call<ArrayList<AbsAnime>> getFavoriteAnime(@Path("username") String username);

    @GET("api/v1/users/{username}/library")
    Call<ArrayList<LibraryEntry>> getLibraryEntries(@Path("username") String username,
            @Query("status") WatchingStatus watchingStatus);

    @POST("api/v1/libraries/{id}/remove")
    Call<Boolean> removeLibraryEntry(@Header("auth_token") String authToken,
            @Path("id") String id);


    /*
     * v2
     * https://github.com/hummingbird-me/hummingbird/wiki/API-v2-Methods
     */

    @GET("api/v2/anime/{id}")
    Call<AnimeV2> getAnimeById(@Header("X-Client-Id") String apiKey, @Path("id") String id);

    @GET("api/v2/anime/myanimelist:{malid}")
    Call<AnimeV2> getAnimeByMyAnimeListId(@Header("X-Client-Id") String apiKey,
            @Path("malid") String malid);


    /*
     * internal API
     */

    @GET("full_anime/{animeId}")
    Call<AnimeDigest> getAnimeDigest(@Header("Cookie") String authToken,
            @Path("animeId") String animeId);

    @GET("users")
    Call<Feed> getFollowedUsers(@Header("Cookie") String authToken,
            @Query("followed_by") String username, @Query("page") Integer page);

    @GET("users")
    Call<Feed> getFollowingUsers(@Header("Cookie") String authToken,
            @Query("followers_of") String username, @Query("page") Integer page);

    @GET("stories")
    Call<Feed> getGroup(@Header("Cookie") String authToken, @Query("group_id") String groupId,
            @Query("page") Integer page);

    @GET("group_members")
    Call<Feed> getGroupMembers(@Header("Cookie") String authToken,
            @Query("group_id") String groupId, @Query("page") Integer page);

    @GET("full_manga/{mangaId}")
    Call<MangaDigest> getMangaDigest(@Header("Cookie") String authToken,
            @Path("mangaId") String mangaId);

    @GET("stories")
    Call<Feed> getNewsFeed(@Header("Cookie") String authToken,
            @Query("news_feed") Boolean newsFeed, @Query("page") Integer page);

    @GET("notifications")
    Call<Feed> getNotifications(@Header("Cookie") String authToken, @Header("Accept") String json);

    @GET("substories")
    Call<Feed> getSubstories(@Header("Cookie") String authToken, @Query("story_id") String storyId);

    @GET("users/{username}")
    Call<UserV2> getUser(@Header("Cookie") String authToken, @Header("Accept") String json,
            @Path("username") String username);

    @GET("user_infos/{username}")
    Call<UserDigest> getUserDigest(@Header("Cookie") String authToken,
            @Path("username") String username);

    @GET("stories")
    Call<Feed> getUserStories(@Header("Cookie") String authToken,
            @Query("user_id") String username);

    @PUT("stories/{storyId}")
    Call<Void> likeStory(@Header("Cookie") String authToken, @Path("storyId") String storyId,
            @Body JsonElement json);

    @POST("substories")
    Call<Void> postComment(@Header("Cookie") String authToken, @Body JsonElement json);

    @GET("search.json")
    Call<SearchBundle> search(@Query("scope") SearchScope searchScope,
            @Query("depth") SearchDepth depth, @Query("query") String query);

}

package com.charlesmadere.hummingbird.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.charlesmadere.hummingbird.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimeV3 extends AbsAnime implements Parcelable {

    @Nullable
    @SerializedName("genres")
    private ArrayList<String> mGenres;

    @SerializedName("started_airing_date_known")
    private boolean mStartedAiringDateKnown;

    @Nullable
    @SerializedName("finished_airing")
    private SimpleDate mFinishedAiring;

    @Nullable
    @SerializedName("started_airing")
    private SimpleDate mStartedAiring;

    @Nullable
    @SerializedName("age_rating_guide")
    private String mAgeRatingGuide;

    @SerializedName("canonical_title")
    private String mCanonicalTitle;

    @Nullable
    @SerializedName("english_title")
    private String mEnglishTitle;

    @SerializedName("poster_image")
    private String mPosterImage;

    @SerializedName("poster_image_thumb")
    private String mPosterImageThumb;

    @Nullable
    @SerializedName("romaji_title")
    private String mRomajiTitle;


    @Nullable
    public String getAgeRatingGuide() {
        return mAgeRatingGuide;
    }

    public String getCanonicalTitle() {
        return mCanonicalTitle;
    }

    @Nullable
    public String getEnglishTitle() {
        return mEnglishTitle;
    }

    @Nullable
    @Override
    public SimpleDate getFinishedAiringDate() {
        return mFinishedAiring;
    }

    @Override
    public String getGenresString(final Resources res) {
        if (!hasGenres()) {
            return "";
        }

        return TextUtils.join(res.getText(R.string.delimiter), mGenres);
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    public String getPosterImageThumb() {
        return mPosterImageThumb;
    }

    @Nullable
    @Override
    public SimpleDate getStartedAiringDate() {
        return mStartedAiring;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public Version getVersion() {
        return Version.V3;
    }

    @Override
    public boolean hasGenres() {
        return mGenres != null && !mGenres.isEmpty();
    }

    public boolean isStartedAiringDateKnown() {
        return mStartedAiringDateKnown;
    }

    @Override
    protected void readFromParcel(final Parcel source) {
        super.readFromParcel(source);
        mGenres = source.createStringArrayList();
        mStartedAiringDateKnown = source.readInt() != 0;
        mFinishedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mStartedAiring = source.readParcelable(SimpleDate.class.getClassLoader());
        mAgeRatingGuide = source.readString();
        mCanonicalTitle = source.readString();
        mEnglishTitle = source.readString();
        mPosterImage = source.readString();
        mPosterImageThumb = source.readString();
        mRomajiTitle = source.readString();
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(mGenres);
        dest.writeInt(mStartedAiringDateKnown ? 1 : 0);
        dest.writeParcelable(mFinishedAiring, flags);
        dest.writeParcelable(mStartedAiring, flags);
        dest.writeString(mAgeRatingGuide);
        dest.writeString(mCanonicalTitle);
        dest.writeString(mEnglishTitle);
        dest.writeString(mPosterImage);
        dest.writeString(mPosterImageThumb);
        dest.writeString(mRomajiTitle);
    }

    public static final Creator<AnimeV3> CREATOR = new Creator<AnimeV3>() {
        @Override
        public AnimeV3 createFromParcel(final Parcel source) {
            final AnimeV3 a = new AnimeV3();
            a.readFromParcel(source);
            return a;
        }

        @Override
        public AnimeV3[] newArray(final int size) {
            return new AnimeV3[size];
        }
    };

}

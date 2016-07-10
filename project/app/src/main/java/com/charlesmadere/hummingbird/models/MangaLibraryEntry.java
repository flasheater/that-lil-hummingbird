package com.charlesmadere.hummingbird.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class MangaLibraryEntry implements Parcelable {

    @SerializedName("is_favorite")
    private boolean mIsFavorite;

    @SerializedName("private")
    private boolean mIsPrivate;

    @SerializedName("rereading")
    private boolean mIsReReading;

    @SerializedName("chapters_read")
    private int mChaptersRead;

    @SerializedName("reread_count")
    private int mReReadCount;

    @SerializedName("volumes_read")
    private int mVolumesRead;

    @Nullable
    @SerializedName("rating")
    private Rating mRating;

    @SerializedName("status")
    private ReadingStatus mStatus;

    @SerializedName("last_read")
    private SimpleDate mLastRead;

    @SerializedName("id")
    private String mId;

    @SerializedName("manga_id")
    private String mMangaId;

    @Nullable
    @SerializedName("notes")
    private String mNotes;

    // hydrated fields
    private Manga mManga;


    @Override
    public boolean equals(final Object o) {
        return o instanceof MangaLibraryEntry && mId.equalsIgnoreCase(((MangaLibraryEntry) o).getId());
    }

    public int getChaptersRead() {
        return mChaptersRead;
    }

    public String getId() {
        return mId;
    }

    public SimpleDate getLastRead() {
        return mLastRead;
    }

    public Manga getManga() {
        return mManga;
    }

    public String getMangaId() {
        return mMangaId;
    }

    @Nullable
    public String getNotes() {
        return mNotes;
    }

    @Nullable
    public Rating getRating() {
        return mRating;
    }

    public int getReReadCount() {
        return mReReadCount;
    }

    public ReadingStatus getStatus() {
        return mStatus;
    }

    public int getVolumesRead() {
        return mVolumesRead;
    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    public boolean hasNotes() {
        return !TextUtils.isEmpty(mNotes);
    }

    public boolean hasRating() {
        return mRating != null;
    }

    public void hydrate(final Feed feed) {
        for (final Manga manga : feed.getManga()) {
            if (mMangaId.equalsIgnoreCase(manga.getId())) {
                mManga = manga;
                return;
            }
        }
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public boolean isPrivate() {
        return mIsPrivate;
    }

    public boolean isReReading() {
        return mIsReReading;
    }

    @Override
    public String toString() {
        return mManga.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(mIsFavorite ? 1 : 0);
        dest.writeInt(mIsPrivate ? 1 : 0);
        dest.writeInt(mIsReReading ? 1 : 0);
        dest.writeInt(mChaptersRead);
        dest.writeInt(mReReadCount);
        dest.writeInt(mVolumesRead);
        dest.writeParcelable(mRating, flags);
        dest.writeParcelable(mStatus, flags);
        dest.writeParcelable(mLastRead, flags);
        dest.writeString(mId);
        dest.writeString(mMangaId);
        dest.writeString(mNotes);
        dest.writeParcelable(mManga, flags);
    }

    public static final Creator<MangaLibraryEntry> CREATOR = new Creator<MangaLibraryEntry>() {
        @Override
        public MangaLibraryEntry createFromParcel(final Parcel source) {
            final MangaLibraryEntry mle = new MangaLibraryEntry();
            mle.mIsFavorite = source.readInt() != 0;
            mle.mIsPrivate = source.readInt() != 0;
            mle.mIsReReading = source.readInt() != 0;
            mle.mChaptersRead = source.readInt();
            mle.mReReadCount = source.readInt();
            mle.mVolumesRead = source.readInt();
            mle.mRating = source.readParcelable(Rating.class.getClassLoader());
            mle.mStatus = source.readParcelable(ReadingStatus.class.getClassLoader());
            mle.mLastRead = source.readParcelable(SimpleDate.class.getClassLoader());
            mle.mId = source.readString();
            mle.mMangaId = source.readString();
            mle.mNotes = source.readString();
            mle.mManga = source.readParcelable(Manga.class.getClassLoader());
            return mle;
        }

        @Override
        public MangaLibraryEntry[] newArray(final int size) {
            return new MangaLibraryEntry[size];
        }
    };

}
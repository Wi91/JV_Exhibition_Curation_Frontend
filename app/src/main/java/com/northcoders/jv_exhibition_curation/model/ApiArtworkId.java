package com.northcoders.jv_exhibition_curation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ApiArtworkId implements Parcelable {

    Long artworkId;

    String apiOrigin;

    public ApiArtworkId(Long artworkId, String apiOrigin) {
        this.artworkId = artworkId;
        this.apiOrigin = apiOrigin;

    }

    protected ApiArtworkId(Parcel in) {
        if (in.readByte() == 0) {
            artworkId = null;
        } else {
            artworkId = in.readLong();
        }
        apiOrigin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (artworkId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(artworkId);
        }
        dest.writeString(apiOrigin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiArtworkId> CREATOR = new Creator<ApiArtworkId>() {
        @Override
        public ApiArtworkId createFromParcel(Parcel in) {
            return new ApiArtworkId(in);
        }

        @Override
        public ApiArtworkId[] newArray(int size) {
            return new ApiArtworkId[size];
        }
    };

    public Long getArtworkId() {
        return artworkId;
    }

    public String getApiOrigin() {
        return apiOrigin;
    }
}

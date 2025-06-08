package com.northcoders.jv_exhibition_curation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artwork implements Parcelable{
    Long id;
    Long apiId;
    String title;
    String description;
    String altText;
    String artistName;
    String imageUrl;
    String apiOrigin;

    public Artwork(Long id, Long apiId, String title, String description, String altText, String artistName, String imageUrl, String apiOrigin) {
        this.id = id;
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.altText = altText;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
        this.apiOrigin = apiOrigin;
    }

    protected Artwork(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            apiId = null;
        } else {
            apiId = in.readLong();
        }
        title = in.readString();
        description = in.readString();
        altText = in.readString();
        artistName = in.readString();
        imageUrl = in.readString();
        apiOrigin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (apiId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(apiId);
        }
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(altText);
        dest.writeString(artistName);
        dest.writeString(imageUrl);
        dest.writeString(apiOrigin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };

    public Long getId() {
        return id;
    }

    public Long getApiId() {
        return apiId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAltText() {
        return altText;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getApiOrigin() {
        return apiOrigin;
    }
}


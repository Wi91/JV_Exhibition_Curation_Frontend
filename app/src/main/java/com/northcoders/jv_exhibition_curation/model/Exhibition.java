package com.northcoders.jv_exhibition_curation.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Exhibition implements Parcelable {

    private Long id;
    private String title;
    private List<Artwork> artList;

    public Exhibition(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public List<Artwork> getArtList() {
        return artList;
    }

    public Exhibition(Long id, String title, List<Artwork> artList) {
        this.id = id;
        this.title = title;
        this.artList = artList;
    }

    protected Exhibition(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        artList = in.createTypedArrayList(Artwork.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeTypedList(artList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exhibition> CREATOR = new Creator<Exhibition>() {
        @Override
        public Exhibition createFromParcel(Parcel in) {
            return new Exhibition(in);
        }

        @Override
        public Exhibition[] newArray(int size) {
            return new Exhibition[size];
        }
    };

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}

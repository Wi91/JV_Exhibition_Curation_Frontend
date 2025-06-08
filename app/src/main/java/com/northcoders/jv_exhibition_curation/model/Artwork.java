package com.northcoders.jv_exhibition_curation.model;

public class Artwork {
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


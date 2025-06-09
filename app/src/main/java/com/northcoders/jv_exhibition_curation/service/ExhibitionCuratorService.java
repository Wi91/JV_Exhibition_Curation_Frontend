package com.northcoders.jv_exhibition_curation.service;

import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.model.Exhibition;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExhibitionCuratorService {

    @GET("artwork/home")
    Call<List<Artwork>> getAllHomeArtworks(@Query(value = "page") Integer page);

    @GET("exhibitions")
    Call<List<Exhibition>> getAllExhibitions();

    @POST("exhibitions")
    Call<Void> createNewExhibition();

    @POST("exhibitions/{exhibitionId}/artworks")
    Call<Void> addArtworkToExhibition(@Path("exhibitionId") Long exhibitionId, @Body ApiArtworkId artworkId);
}

package com.northcoders.jv_exhibition_curation.service;

import androidx.annotation.Nullable;

import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.ui.SearchArtworks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExhibitionCuratorService {

    @GET("artwork/home")
    Call<List<Artwork>> getAllHomeArtworks(@Query(value = "page") Integer page, @Query(value = "origin") String origin);

    @GET("exhibitions")
    Call<List<Exhibition>> getAllExhibitions();

    @POST("exhibitions")
    Call<Void> createNewExhibition();

    @POST("exhibitions/{exhibitionId}/artworks")
    Call<Void> addArtworkToExhibition(@Path("exhibitionId") Long exhibitionId, @Body ApiArtworkId artworkId);

    @GET("exhibitions/{exhibitionId}")
    Call<Exhibition> getAllExhibitionArtworks(@Path("exhibitionId") Long exhibitionId);

    @DELETE("exhibitions/{exhibitionId}")
    Call<Void> deleteExhibition(@Path("exhibitionId") Long exhibitionId);
    @HTTP(method = "DELETE", path = "exhibitions/{exhibitionId}/artworks", hasBody = true)
    Call<Void> deleteArtworkFromExhibition(@Path("exhibitionId") Long exhibitionId, @Body ApiArtworkId artworkId);

    @GET("artwork/home/search")
    Call<List<Artwork>> searchArtworks(@Query(value = "query") String query, @Query(value = "page") Integer page);
}

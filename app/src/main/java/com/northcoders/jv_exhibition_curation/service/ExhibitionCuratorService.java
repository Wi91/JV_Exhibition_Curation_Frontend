package com.northcoders.jv_exhibition_curation.service;

import com.northcoders.jv_exhibition_curation.model.Artwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExhibitionCuratorService {

    @GET("artwork/home")
    Call<List<Artwork>> getAllHomeArtworks (@Query(value = "page")Integer page);

}

package com.northcoders.jv_exhibition_curation.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.service.ExhibitionCuratorService;
import com.northcoders.jv_exhibition_curation.service.RetroFitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetAllArtworksRepository {

    private MutableLiveData<List<Artwork>> mutableLiveData = new MutableLiveData<>();

    private Application application;

    public GetAllArtworksRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Artwork>> getMutableLiveData(Integer page, MutableLiveData<Boolean> isLoading){
        ExhibitionCuratorService service = RetroFitInstance.getService();

        Call<List<Artwork>> call = service.getAllHomeArtworks(page);
        call.enqueue(new Callback<List<Artwork>>() {
            @Override
            public void onResponse(Call<List<Artwork>> call, Response<List<Artwork>> response) {
                isLoading.setValue(false);
                if(response.code() == 200) {
                    List<Artwork> artworkList = response.body();
                    mutableLiveData.setValue(artworkList);
                } else {
                    //TODO Different toasts for different codes
                    Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Artwork>> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
        return mutableLiveData;
    }

}

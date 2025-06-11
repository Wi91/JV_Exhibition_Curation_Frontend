package com.northcoders.jv_exhibition_curation.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.service.ExhibitionCuratorService;
import com.northcoders.jv_exhibition_curation.service.RetroFitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExhibitionsRepository {

    private MutableLiveData<List<Exhibition>> liveExhibitions = new MutableLiveData<>();

    private MutableLiveData<Exhibition> exhibition = new MutableLiveData<>();

    private Application application;

    public ExhibitionsRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Exhibition>> getAllExhibitions(MutableLiveData<Boolean> isLoading) {
        ExhibitionCuratorService service = RetroFitInstance.getService();

        Call<List<Exhibition>> call = service.getAllExhibitions();
        call.enqueue(new Callback<List<Exhibition>>() {
            @Override
            public void onResponse(Call<List<Exhibition>> call, Response<List<Exhibition>> response) {
                isLoading.setValue(false);
                if (response.code() == 200) {
                    List<Exhibition> exhibitionList = response.body();
                    liveExhibitions.setValue(exhibitionList);
                } else {
                    liveExhibitions.setValue(new ArrayList<>());
                    Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Exhibition>> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();


            }
        });
        return liveExhibitions;
    }

    public void createNewExhibition(MutableLiveData<Boolean> isLoading) {
        ExhibitionCuratorService service = RetroFitInstance.getService();

        Call<Void> call = service.createNewExhibition();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                if (response.code() == 201) {
                    Toast.makeText(application, "Exhibition Created", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(application, "Cannot create new exhibition", Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Request Failed", Toast.LENGTH_SHORT);


            }
        });
    }

    public void addArtworkToExhibition(Long exhibitionId, ApiArtworkId apiArtworkId, MutableLiveData<Boolean> isLoading) {
        ExhibitionCuratorService service = RetroFitInstance.getService();
        Call<Void> call = service.addArtworkToExhibition(exhibitionId, apiArtworkId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 200:
                        Toast.makeText(application, "Artwork Added", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition does not exist", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(application, "Artwork already added", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Failed to add artwork", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public MutableLiveData<Exhibition> getAllExhibitionArtworks(Long exhibitionId, MutableLiveData<Boolean> isLoading) {
        ExhibitionCuratorService service = RetroFitInstance.getService();
        Call<Exhibition> call = service.getAllExhibitionArtworks(exhibitionId);
        call.enqueue(new Callback<Exhibition>() {
            @Override
            public void onResponse(Call<Exhibition> call, Response<Exhibition> response) {
                isLoading.setValue(false);
                switch (response.code()) {
                    case 200:
                        exhibition.setValue(response.body());
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Exhibition> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        return exhibition;
    }

    public void deleteArtworkFromExhibition(Long exhibitionId, ApiArtworkId apiArtworkId, MutableLiveData<Boolean> isSuccessful, MutableLiveData<Boolean> isLoading) {
        ExhibitionCuratorService service = RetroFitInstance.getService();
        Call<Void> call = service.deleteArtworkFromExhibition(exhibitionId, apiArtworkId);
        call.enqueue((new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);

                switch (response.code()) {
                    case 200:
                        isSuccessful.setValue(true);
                        Toast.makeText(application, "Artwork Removed From Exhibition", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "No Exhibitions Found", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(application, "Artwork has already been removed", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Unable to remove artwork", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Server Error", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void deleteExhibition(Long exhibitionId, MutableLiveData<Boolean> isSuccessful, MutableLiveData<Boolean> isLoading){
        ExhibitionCuratorService service = RetroFitInstance.getService();
        Call<Void> call = service.deleteExhibition(exhibitionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                isSuccessful.setValue(true);
                switch (response.code()){
                    case 204:
                        isSuccessful.setValue(true);
                        Toast.makeText(application, "Exhibition Successfully Deleted", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(application, "Exhibition Does Not Exist", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                Toast.makeText(application, "Internal Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

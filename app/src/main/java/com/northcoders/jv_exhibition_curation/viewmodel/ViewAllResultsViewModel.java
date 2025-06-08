package com.northcoders.jv_exhibition_curation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.repository.GetAllArtworksRepository;

import java.util.List;

public class ViewAllResultsViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    GetAllArtworksRepository repository;
    public ViewAllResultsViewModel(@NonNull Application application) {
        super(application);
        this.repository = new GetAllArtworksRepository(application);
    }

    public MutableLiveData<List<Artwork>> getAllArtworks(Integer page) {
        isLoading.setValue(true);
        return repository.getMutableLiveData(page, isLoading);
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }
}

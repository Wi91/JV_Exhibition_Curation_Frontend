package com.northcoders.jv_exhibition_curation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.repository.ExhibitionsRepository;

import java.util.List;

public class ExhibitionViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    ExhibitionsRepository repository;

    MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();


    public ExhibitionViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ExhibitionsRepository(application);
    }

    public MutableLiveData<List<Exhibition>> getAllExhibitions(){
        isLoading.setValue(true);
        return repository.getAllExhibitions(isLoading);
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public LiveData<Boolean> getIsSuccessful(){
        return isSuccessful;
    }
    public void createNewExhibition(){
        isLoading.setValue(true);
        repository.createNewExhibition(isLoading);
    }

    public void addArtworkToExhibition(Long exhibitionId, ApiArtworkId apiArtworkId){
        isLoading.setValue(true);
        repository.addArtworkToExhibition(exhibitionId, apiArtworkId, isLoading);
    }
    public MutableLiveData<Exhibition> getAllExhibitionArtworks(Long exhibitionId){
        return repository.getAllExhibitionArtworks(exhibitionId);
    }

    public void deleteArtworkFromExhibition(Long exhibitionId, ApiArtworkId apiArtworkId){
        isSuccessful.setValue(false);
        repository.deleteArtworkFromExhibition(exhibitionId, apiArtworkId, isSuccessful);
    }

    public void deleteExhibition(Long exhibitionId){
        isSuccessful.setValue(false);
        repository.deleteExhibition(exhibitionId, isSuccessful);
    }
}

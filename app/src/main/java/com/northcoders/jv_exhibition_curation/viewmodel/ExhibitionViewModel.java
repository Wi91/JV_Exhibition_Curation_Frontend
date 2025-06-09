package com.northcoders.jv_exhibition_curation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.repository.ExhibitionsRepository;

import java.util.List;

public class ExhibitionViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    ExhibitionsRepository repository;


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

    public void createNewExhibition(){
        isLoading.setValue(true);
        repository.createNewExhibition(isLoading);
    }
}

package com.northcoders.jv_exhibition_curation.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

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

    private Application application;

    public ExhibitionsRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Exhibition>> getAllExhibitions(MutableLiveData<Boolean> isLoading){
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
}

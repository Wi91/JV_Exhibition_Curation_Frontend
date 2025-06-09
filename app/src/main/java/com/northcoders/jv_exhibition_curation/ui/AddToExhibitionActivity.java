package com.northcoders.jv_exhibition_curation.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.adapter.ExhibitionListAdapter;
import com.northcoders.jv_exhibition_curation.adapter.RecyclerViewInterface;
import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.viewmodel.ExhibitionViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddToExhibitionActivity extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<Exhibition> exhibitionList;
    private RecyclerView recyclerView;

    private ExhibitionViewModel viewModel;

    private ExhibitionListAdapter adapter;

    private ApiArtworkId apiArtworkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_to_exhibition);

       View loadingOverlay = findViewById(R.id.progressBarLayer);
        apiArtworkId = getIntent().getParcelableExtra("ARTWORK");

        viewModel = new ViewModelProvider(this).get(ExhibitionViewModel.class);
        initialiseBackButton();
        getAllExhibitions();

        }

    private void getAllExhibitions() {
        viewModel.getAllExhibitions().observe(this, new Observer<List<Exhibition>>() {
            @Override
            public void onChanged(List<Exhibition> exhibitions) {
                exhibitionList = (ArrayList<Exhibition>) exhibitions;
                displayInRecyclerView();
            }
        });

    }

    private void displayInRecyclerView() {
        recyclerView = findViewById(R.id.exhibitionsList);
        adapter = new ExhibitionListAdapter(exhibitionList, this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        adapter.notifyDataSetChanged();
    }

    private void initialiseBackButton() {
       AppCompatButton button =  findViewById(R.id.backToArtworksButton);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }

    @Override
    public void onItemClick(int position) {
        Long exhibitionId = exhibitionList.get(position).getId();
        viewModel.addArtworkToExhibition(exhibitionId, apiArtworkId);

    }
}
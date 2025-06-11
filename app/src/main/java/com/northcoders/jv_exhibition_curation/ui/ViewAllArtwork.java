package com.northcoders.jv_exhibition_curation.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.adapter.RecyclerViewInterface;
import com.northcoders.jv_exhibition_curation.adapter.ViewAllArtworksAdapter;
import com.northcoders.jv_exhibition_curation.databinding.FragmentViewAllBinding;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.viewmodel.ViewAllResultsViewModel;

import java.util.ArrayList;
import java.util.List;


public class ViewAllArtwork extends Fragment implements RecyclerViewInterface {

    FragmentViewAllBinding binding;

    ViewAllResultsViewModel viewModel;

    int counter = 1;

    ArrayList<Artwork> artworksList;

    RecyclerView recyclerView;

    ViewAllArtworksAdapter adapter;



    public ViewAllArtwork() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewAllResultsViewModel.class);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableNavButtons("previous", false);
        initialiseNavButtons();
        getAllArtworkResults(counter);
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null){
                binding.loadingStateOverlay.setVisibility(isLoading ? VISIBLE:GONE);
            }
        });
    }

    private void enableNavButtons(String button, Boolean isEnabled){
        switch(button){
            case "next":
            binding.nextButton.setEnabled(isEnabled);
            break;
            case "previous":
                binding.previousButton.setEnabled(isEnabled);
                break;
        }
    }

    private void initialiseNavButtons(){
        previousButton();
        nextButton();
    }

    private void previousButton(){
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAllArtworkResults(counter - 1);
            }
        });
    }

    private void nextButton(){
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllArtworkResults(counter + 1);

            }
        });
    }

    private void getAllArtworkResults(Integer page) {
        viewModel.getAllArtworks(page).observe(getViewLifecycleOwner(), new Observer<List<Artwork>>() {
            @Override
            public void onChanged(List<Artwork> artworks) {
                artworksList = (ArrayList<Artwork>) artworks;
                counter = page;
                if(page == 1){
                    enableNavButtons("previous", false);
                } else {
                    enableNavButtons("previous", true);
                }
//                if(page == 1){
//                    counter =1;
//                }
//                if(artworksList.isEmpty()){
//                    enableNavButtons("next", false);
//                } else {
//                    enableNavButtons("next", true);
//                    if(page != 1){
//                        counter++;
//                    }
//                }
//                enableNavButtons("previous", page > 1);
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView() {
        recyclerView = binding.artworksRecyclerView;
        adapter = new ViewAllArtworksAdapter(artworksList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_all, container, false);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        Artwork artwork = artworksList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARTWORK", artwork);
        AddArtworkFragment addArtworkFragment = new AddArtworkFragment();
        addArtworkFragment.setArguments(bundle);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.baseFragment, addArtworkFragment)
                .addToBackStack(null)
                .commit();
    }
}
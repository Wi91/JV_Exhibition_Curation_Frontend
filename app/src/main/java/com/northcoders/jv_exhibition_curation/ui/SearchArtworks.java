package com.northcoders.jv_exhibition_curation.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
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
import com.northcoders.jv_exhibition_curation.databinding.FragmentSearchArtworksBinding;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.repository.GetAllArtworksRepository;
import com.northcoders.jv_exhibition_curation.viewmodel.ViewAllResultsViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchArtworks extends Fragment implements RecyclerViewInterface {

    FragmentSearchArtworksBinding binding;

    ViewAllResultsViewModel viewModel;

    RecyclerView recyclerView;

    List<Artwork> searchedArtworkList;

    ViewAllArtworksAdapter adapter;

    SearchView searchView;

    int counter;

    String query;


    public SearchArtworks() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewAllResultsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_artworks, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        enableNavButtons("previous", false);
        initialiseNavButtons();
        initialiseSearchBar();
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null){
                binding.loadingStateOverlay.setVisibility(isLoading ? VISIBLE:GONE);
            }
        });
        if(query != null) {
            getAllSearchResults(query, counter);
        } else {
            query = "";
        }
    }

    private void enableNavButtons(String button, Boolean isEnabled) {
        switch (button) {
            case "next":
                binding.nextButton.setEnabled(isEnabled);
                break;
            case "previous":
                binding.previousButton.setEnabled(isEnabled);
                break;
        }
    }

    private void initialiseNavButtons() {
        previousButton();
        nextButton();
    }

    private void nextButton() {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllSearchResults(query, counter + 1);
            }
        });
    }

    private void previousButton() {
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllSearchResults(query, counter - 1);

            }
        });
    }

    private void initialiseSearchBar() {
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = binding.searchBar.getQuery().toString();
                counter = 1;
                getAllSearchResults(query, counter);
                binding.searchBar.setQuery(query, false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getAllSearchResults(String query, Integer page) {
        viewModel.searchArtworks(query, page).observe(getViewLifecycleOwner(), new Observer<List<Artwork>>() {
            @Override
            public void onChanged(List<Artwork> artworks) {
                searchedArtworkList = (ArrayList<Artwork>) artworks;
                counter = page;
                if (page == 1) {
                    enableNavButtons("previous", false);
                } else {
                    enableNavButtons("previous", true);
                }
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView() {
        recyclerView = binding.searchResultsRecyclerview;
        adapter = new ViewAllArtworksAdapter(searchedArtworkList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Artwork artwork = searchedArtworkList.get(position);
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
package com.northcoders.jv_exhibition_curation.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.Toast;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.adapter.ExhibitionListAdapter;
import com.northcoders.jv_exhibition_curation.adapter.RecyclerViewInterface;
import com.northcoders.jv_exhibition_curation.adapter.ViewAllArtworksAdapter;
import com.northcoders.jv_exhibition_curation.databinding.FragmentExhibitionBinding;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.viewmodel.ExhibitionViewModel;

import java.util.ArrayList;

public class ExhibitionFragment extends Fragment implements RecyclerViewInterface {

    private FragmentExhibitionBinding binding;

    private ExhibitionViewModel viewModel;

    private Exhibition curExhibition;
    private RecyclerView recyclerView;
    private ArrayList<Artwork> artworkArrayList;
    private ViewAllArtworksAdapter adapter;

    private Long exhibitionId;

    private ArrayList<Artwork> filteredList;

    private String query;


    public ExhibitionFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exhibitionId = getArguments().getLong("ID");
        }
        viewModel = new ViewModelProvider(this).get(ExhibitionViewModel.class);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseBackButton();

        initialiseSearchBar();

        getArtworksInExhibition();

        initialiseDeleteButton();

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.loadingStateOverlay.setVisibility(isLoading ? VISIBLE : GONE);
            }
        });

        viewModel.getIsSuccessful().observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void getArtworksInExhibition() {
        viewModel.getAllExhibitionArtworks(exhibitionId).observe(getViewLifecycleOwner(), new Observer<Exhibition>() {
            @Override
            public void onChanged(Exhibition exhibition) {
                curExhibition = exhibition;
                if (curExhibition != null) {
                    artworkArrayList = (ArrayList<Artwork>) curExhibition.getArtList();
                    displayInRecyclerView();
                    binding.exhibitionName.setText(exhibition.getTitle());
                }
            }
        });
    }

    private void displayInRecyclerView() {
        recyclerView = binding.savedArtworkList;
        adapter = new ViewAllArtworksAdapter(artworkArrayList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    private void initialiseBackButton() {
        AppCompatButton button = binding.backToExhibitionsButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void initialiseSearchBar() {
        binding.exhibitionsSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String searchQuery) {
        filteredList = new ArrayList<>();

        for (Artwork artwork : artworkArrayList) {
            if (artwork.getTitle().toLowerCase().contains(searchQuery.toLowerCase())
                    || artwork.getArtistName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredList.add(artwork);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No Artworks Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exhibition, container, false);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {

        if(filteredList == null || filteredList.isEmpty()) {
            Artwork artwork = artworkArrayList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("ARTWORK", artwork);
            bundle.putLong("ID", exhibitionId);
            DeleteArtworkFragment deleteArtworkFragment = new DeleteArtworkFragment();
            deleteArtworkFragment.setArguments(bundle);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.baseFragment, deleteArtworkFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Artwork artwork = filteredList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("ARTWORK", artwork);
            bundle.putLong("ID", exhibitionId);
            DeleteArtworkFragment deleteArtworkFragment = new DeleteArtworkFragment();
            deleteArtworkFragment.setArguments(bundle);
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.baseFragment, deleteArtworkFragment)
                    .addToBackStack(null)
                    .commit();

            binding.exhibitionsSearchBar.setQuery("", false);
            binding.exhibitionsSearchBar.clearFocus();
        }
    }

    private void initialiseDeleteButton() {
        binding.deleteExhibitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder delete = new AlertDialog.Builder(getContext())
                        .setTitle("Delete Exhibition")
                        .setMessage("Are you sure you want to delete this exhibition?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewModel.deleteExhibition(exhibitionId);

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                delete.show();

            }
        });
    }
}
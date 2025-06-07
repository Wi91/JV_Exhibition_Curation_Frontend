package com.northcoders.jv_exhibition_curation.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.databinding.FragmentHomepageBinding;


public class HomepageFragment extends Fragment {

FragmentHomepageBinding binding;

    public HomepageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initializeButtons();
    }

    private void initializeButtons() {
        Button viewAllArtworksButton = binding.viewAllArtworksButton;
        Button searchArtworksButton = binding.searchArtworksButton;

        viewAllArtworksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAllArtwork viewAllArtwork = new ViewAllArtwork();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.baseFragment, viewAllArtwork)
                        .commit();
            }
        });

        searchArtworksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchArtworks searchArtworks = new SearchArtworks();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.baseFragment, searchArtworks)
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homepage, container, false);

        return binding.getRoot();
    }
}
package com.northcoders.jv_exhibition_curation.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.databinding.FragmentDeleteArtworkBinding;
import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Artwork;
import com.northcoders.jv_exhibition_curation.viewmodel.ExhibitionViewModel;

public class DeleteArtworkFragment extends Fragment {

    FragmentDeleteArtworkBinding binding;

    ExhibitionViewModel model;

    Artwork artwork;

    Long exhibitionId;
    ApiArtworkId apiArtworkId;

    public DeleteArtworkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ExhibitionViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artwork = getArguments().getParcelable("ARTWORK");
            exhibitionId = getArguments().getLong("ID");
            apiArtworkId = new ApiArtworkId(artwork.getId(), artwork.getApiOrigin());
        }
        importImage();
        setTextViews();
        initialiseBackButton();
        initialiseRemoveArtworkButton();

        model.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading != null){
                binding.loadingStateOverlay.setVisibility(isLoading ? VISIBLE:GONE);
            }
        });

        model.getIsSuccessful().observe(getViewLifecycleOwner(), isSuccessful -> {
            if (isSuccessful) {
                getParentFragmentManager().popBackStack();
            }
        });


    }

    private void initialiseBackButton() {
        binding.backToExhibitionViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void initialiseRemoveArtworkButton() {
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiArtworkId apiArtworkId = new ApiArtworkId(artwork.getApiId(), artwork.getApiOrigin());
                model.deleteArtworkFromExhibition(exhibitionId,apiArtworkId);
            }
        });
    }

    private void setTextViews() {
        binding.artworkName.setText(artwork.getTitle());
        binding.artistName.setText(artwork.getArtistName());
        if (artwork.getDescription().equals("No Description Provided")) {
            binding.artworkDescription.setText(artwork.getAltText());
        } else {
            binding.artworkDescription.setText(artwork.getDescription());
        }
        binding.artworkDescription.setMovementMethod(new ScrollingMovementMethod());
    }

    private void importImage() {
        ImageView artworkImage = binding.artworkImage;
        Glide.with(artworkImage).load(artwork.getImageUrl())
                .into(artworkImage);
        artworkImage.setContentDescription(artwork.getAltText());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delete_artwork, container, false);
        return binding.getRoot();
    }
}

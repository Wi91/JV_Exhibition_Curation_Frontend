package com.northcoders.jv_exhibition_curation.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.databinding.FragmentAddArtworkBinding;
import com.northcoders.jv_exhibition_curation.model.ApiArtworkId;
import com.northcoders.jv_exhibition_curation.model.Artwork;


public class AddArtworkFragment extends Fragment {

    FragmentAddArtworkBinding binding;

    Artwork artwork;

    public AddArtworkFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            artwork = getArguments().getParcelable("ARTWORK");

            importImage();
            setTextViews();
            initialiseBackButton();
            initialiseAddArtworkButton();
        }
    }

    private void initialiseBackButton() {

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    private void setTextViews() {

        binding.artworkName.setText(artwork.getTitle());
        binding.artistName.setText(artwork.getArtistName());
        if(artwork.getDescription().equals("No Description Provided")){
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_artwork, container, false);
        return binding.getRoot();
    }

    public void initialiseAddArtworkButton(){
        binding.addToExhibitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(artwork != null){
                    ApiArtworkId apiArtworkId = new ApiArtworkId(artwork.getApiId(), artwork.getApiOrigin());
                    Intent intent = new Intent(getContext(), AddToExhibitionActivity.class);
                    intent.putExtra("ARTWORK", apiArtworkId);
                    startActivity(intent);
                }
            }
        });
    }

}
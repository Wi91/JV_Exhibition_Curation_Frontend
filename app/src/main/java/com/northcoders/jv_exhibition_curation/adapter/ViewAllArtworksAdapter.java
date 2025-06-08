package com.northcoders.jv_exhibition_curation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.databinding.ArtworkItemLayoutBinding;
import com.northcoders.jv_exhibition_curation.model.Artwork;

import java.util.List;

public class ViewAllArtworksAdapter extends RecyclerView.Adapter<ViewAllArtworksAdapter.ViewAllArtworksViewHolder> {


    List<Artwork> artworkList;
    Context context;
    RecyclerViewInterface recyclerViewInterface;


    public ViewAllArtworksAdapter(List<Artwork> artworkList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.artworkList = artworkList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public ViewAllArtworksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArtworkItemLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.artwork_item_layout,
                parent,
                false);

        return new ViewAllArtworksViewHolder(binding,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllArtworksViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        ImageView artImage = holder.binding.artworkImage;
        Glide.with(artImage).load(artwork.getImageUrl())
                .into(artImage);
        holder.binding.setArtwork(artwork);

    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }

    public static class ViewAllArtworksViewHolder extends RecyclerView.ViewHolder{
        private ArtworkItemLayoutBinding binding;
        public ViewAllArtworksViewHolder(ArtworkItemLayoutBinding binding,RecyclerViewInterface recyclerViewInterface){
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}



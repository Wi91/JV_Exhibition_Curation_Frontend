package com.northcoders.jv_exhibition_curation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.databinding.ArtworkItemLayoutBinding;
import com.northcoders.jv_exhibition_curation.databinding.ExhibitionItemLayoutBinding;
import com.northcoders.jv_exhibition_curation.model.Exhibition;

import java.util.List;

public class ExhibitionListAdapter extends RecyclerView.Adapter<ExhibitionListAdapter.ExhibitionListViewHolder>{

    List<Exhibition> exhibitionList;

    Context context;

    RecyclerViewInterface recyclerViewInterface;

    public ExhibitionListAdapter(List<Exhibition> exhibitionList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.exhibitionList = exhibitionList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ExhibitionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExhibitionItemLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.exhibition_item_layout,
                parent,
                false);

        return new ExhibitionListViewHolder(binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ExhibitionListViewHolder holder, int position) {
        Exhibition exhibition = exhibitionList.get(position);
        holder.binding.setExhibitionList(exhibition);

    }

    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }


    public static class ExhibitionListViewHolder extends RecyclerView.ViewHolder{
        private ExhibitionItemLayoutBinding binding;


        public ExhibitionListViewHolder(ExhibitionItemLayoutBinding binding, RecyclerViewInterface recyclerViewInterface){
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


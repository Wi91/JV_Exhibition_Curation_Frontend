package com.northcoders.jv_exhibition_curation.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.jv_exhibition_curation.R;
import com.northcoders.jv_exhibition_curation.adapter.RecyclerViewInterface;


public class ViewAllArtwork extends Fragment implements RecyclerViewInterface {


    public ViewAllArtwork() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all, container, false);
    }

    @Override
    public void onItemClick(int position) {

    }
}
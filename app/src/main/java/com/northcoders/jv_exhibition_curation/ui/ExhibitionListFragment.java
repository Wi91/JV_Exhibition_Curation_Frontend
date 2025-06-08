package com.northcoders.jv_exhibition_curation.ui;

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
import com.northcoders.jv_exhibition_curation.adapter.ExhibitionListAdapter;
import com.northcoders.jv_exhibition_curation.adapter.RecyclerViewInterface;
import com.northcoders.jv_exhibition_curation.databinding.FragmentExhibitionListBinding;
import com.northcoders.jv_exhibition_curation.model.Exhibition;
import com.northcoders.jv_exhibition_curation.viewmodel.ExhibitionViewModel;

import java.util.ArrayList;
import java.util.List;


public class ExhibitionListFragment extends Fragment implements RecyclerViewInterface {

    FragmentExhibitionListBinding binding;

    ArrayList<Exhibition> exhibitionList;

    ExhibitionViewModel viewModel;

    RecyclerView recyclerView;

    ExhibitionListAdapter adapter;


    public ExhibitionListFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExhibitionViewModel.class);

    }

    private void displayInRecyclerView() {
        recyclerView = binding.exhibitionListRecyclerView;
        adapter = new ExhibitionListAdapter(exhibitionList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    private void initialiseAddExhibitionButton(){

    }

    private void getAllExhibitionResults(){
        viewModel.getAllExhibitions().observe(getViewLifecycleOwner(), new Observer<List<Exhibition>>() {
            @Override
            public void onChanged(List<Exhibition> exhibitions) {
                exhibitionList = (ArrayList<Exhibition>) exhibitions;
                displayInRecyclerView();
            }
        });
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getAllExhibitionResults();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exhibition_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        Exhibition exhibition = exhibitionList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("EXHIBITION", exhibition);


    }
}
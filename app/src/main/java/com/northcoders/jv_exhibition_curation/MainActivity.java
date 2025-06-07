package com.northcoders.jv_exhibition_curation;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.jv_exhibition_curation.ui.ExhibitionListFragment;
import com.northcoders.jv_exhibition_curation.ui.HomepageFragment;
import com.northcoders.jv_exhibition_curation.ui.SearchArtworks;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    HomepageFragment homepageFragment = new HomepageFragment();

    SearchArtworks searchArtworks = new SearchArtworks();

    ExhibitionListFragment exhibitionListFragment = new ExhibitionListFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavBar);

        navigationBarView.setOnItemSelectedListener(this);

        navigationBarView.setSelectedItemId(R.id.home_Button);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.home_Button) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.baseFragment, homepageFragment)
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.search_button) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.baseFragment, searchArtworks)
                    .commit();
            return true;
        }

        if (item.getItemId() == R.id.exhibitions) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.baseFragment, exhibitionListFragment)
                    .commit();

        }
        return true;
    }
}



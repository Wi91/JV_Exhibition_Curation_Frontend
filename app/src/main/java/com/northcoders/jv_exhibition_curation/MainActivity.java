package com.northcoders.jv_exhibition_curation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    HomepageFragment homepageFragment = new HomepageFragment();


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
        return false;
}
}



package com.example.android.chuckchuck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.jokes_screen);
        loadFragment(new JokesFragment());

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_jokes:
                        toolbar.setTitle(R.string.jokes_screen);
                        fragment = new JokesFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_api_info:
                        toolbar.setTitle(R.string.api_info_screen);
                        fragment = new ApiInfoFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

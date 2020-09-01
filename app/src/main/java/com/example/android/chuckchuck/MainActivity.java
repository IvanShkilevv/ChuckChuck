package com.example.android.chuckchuck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new JokesFragment());

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_jokes:
                    currentFragment = new JokesFragment();
                    loadFragment(currentFragment);
                    return true;
                case R.id.navigation_api_info:
                    currentFragment = new ApiInfoFragment();
                    loadFragment(currentFragment);
                    return true;
            }
            return false;
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

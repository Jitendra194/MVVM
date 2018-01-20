package com.example.android.eshowtestapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.model.Restaurant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainMenuFragment mainMenuFragment = new MainMenuFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mainMenuFragment, MainMenuFragment.TAG)
                    .commit();
        }
    }

    public void show(int mItemClick) {
        switch (mItemClick) {
            case 1:
                RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("restaurant")
                        .replace(R.id.fragment_container, restaurantListFragment, RestaurantListFragment.TAG)
                        .commit();
                break;
            case 2:
                ChefsListFragment chefsListFragment = new ChefsListFragment();
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack("chef")
                        .replace(R.id.fragment_container, chefsListFragment, ChefsListFragment.TAG)
                        .commit();
                break;
            default:
                break;
        }
    }

    public void show(Restaurant restaurant) {
        RestaurantDetailsFragment detailsFragment = RestaurantDetailsFragment.forProduct(Integer.parseInt(restaurant.getId()));
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("restaurantdetails")
                .replace(R.id.fragment_container, detailsFragment, RestaurantDetailsFragment.TAG)
                .commit();
    }
}

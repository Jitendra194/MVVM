package com.example.android.eshowtestapplication.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.databinding.RestaurantListItemBinding;
import com.example.android.eshowtestapplication.model.Restaurant;

import java.util.List;

/**
 * Created by jiten on 1/14/2018.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    List<? extends Restaurant> mRestaurants;

    private final RestaurantMenuItemClickCallback clickCallback;

    public RestaurantListAdapter(RestaurantMenuItemClickCallback restaurantMenuItemClickCallback) {
        clickCallback = restaurantMenuItemClickCallback;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RestaurantListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.restaurant_list_item,
                parent, false);
        binding.setCallback(clickCallback);
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.restaurantListItemBinding.setRestaurant(mRestaurants.get(position));
        holder.restaurantListItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRestaurants == null ? 0 : mRestaurants.size();
    }

    public void setProductList(final List<? extends Restaurant> myRestaurants) {
        if (mRestaurants == null) {
            mRestaurants = myRestaurants;
            notifyItemChanged(0, myRestaurants.size());
        }
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        final RestaurantListItemBinding restaurantListItemBinding;

        public RestaurantViewHolder(RestaurantListItemBinding binding) {
            super(binding.getRoot());
            restaurantListItemBinding = binding;
        }
    }
}

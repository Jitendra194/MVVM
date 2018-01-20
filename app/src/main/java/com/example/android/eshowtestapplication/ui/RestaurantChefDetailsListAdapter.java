package com.example.android.eshowtestapplication.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.databinding.RestaurantChefDetailsItemBinding;
import com.example.android.eshowtestapplication.model.Chef;

import java.util.List;
import java.util.Objects;

/**
 * Created by jiten on 1/18/2018.
 */

public class RestaurantChefDetailsListAdapter extends
        RecyclerView.Adapter<RestaurantChefDetailsListAdapter.ChefViewHolder> {

    private List<? extends Chef> mChefs;

    @Override
    public ChefViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RestaurantChefDetailsItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.restaurant_chef_details_item,
                parent, false);
        return new ChefViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ChefViewHolder holder, int position) {
        holder.restaurantChefDetailsItemBinding.setChef(mChefs.get(position));
        holder.restaurantChefDetailsItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mChefs == null ? 0 : mChefs.size();
    }

    public void setChefsList(final List<? extends Chef> myChefs) {
        if (mChefs == null) {
            mChefs = myChefs;
            notifyItemChanged(0, myChefs.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mChefs.size();
                }

                @Override
                public int getNewListSize() {
                    return myChefs.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mChefs.get(oldItemPosition).getId() ==
                            myChefs.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Chef newProduct = myChefs.get(newItemPosition);
                    Chef oldProduct = mChefs.get(oldItemPosition);
                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getFirstName(), oldProduct.getFirstName())
                            && Objects.equals(newProduct.getLastName(), oldProduct.getLastName())
                            && newProduct.getAddress() == oldProduct.getAddress();
                }
            });
            mChefs = myChefs;
            result.dispatchUpdatesTo(this);
        }
    }

    static class ChefViewHolder extends RecyclerView.ViewHolder {

        final RestaurantChefDetailsItemBinding restaurantChefDetailsItemBinding;

        public ChefViewHolder(RestaurantChefDetailsItemBinding binding) {
            super(binding.getRoot());
            restaurantChefDetailsItemBinding = binding;
        }
    }
}

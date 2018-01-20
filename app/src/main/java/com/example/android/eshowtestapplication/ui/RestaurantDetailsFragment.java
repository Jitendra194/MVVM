package com.example.android.eshowtestapplication.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.databinding.FragmentRestaurantDetailsBinding;
import com.example.android.eshowtestapplication.viewmodel.RestaurantDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailsFragment extends Fragment {

    public static final String TAG = "RestaurantDetailsFragment";

    private static final String KEY_PRODUCT_ID = "product_id";

    private FragmentRestaurantDetailsBinding mBinding;

    private RestaurantChefDetailsListAdapter mAdapter;

    public RestaurantDetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_details
                , container, false);
        mAdapter = new RestaurantChefDetailsListAdapter();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RestaurantDetailViewModel.Factory factory = new RestaurantDetailViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_PRODUCT_ID));
        final RestaurantDetailViewModel model = ViewModelProviders.of(this, factory)
                .get(RestaurantDetailViewModel.class);
        subscribeUi(model);

    }

    private void subscribeUi(RestaurantDetailViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRestaurant().observe(this, restaurant ->
                mBinding.setRestaurant(restaurant));
        viewModel.getChefsFromWorkTable().observe(this, chefs -> {
            mAdapter.setChefsList(chefs);
            mBinding.restaurantChefsList.setAdapter(mAdapter);
            mBinding.executePendingBindings();
        });
    }


    public static RestaurantDetailsFragment forProduct(int productId) {
        RestaurantDetailsFragment fragment = new RestaurantDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

}

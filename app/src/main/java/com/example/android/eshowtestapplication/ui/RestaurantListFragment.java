package com.example.android.eshowtestapplication.ui;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.databinding.FragmentRestaurantListBinding;
import com.example.android.eshowtestapplication.viewmodel.RestaurantViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    public static final String TAG = "RestaurantListFragment";

    private FragmentRestaurantListBinding mBinding;

    private RestaurantListAdapter adapter;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list
                , container, false);
        adapter = new RestaurantListAdapter(clickCallback);
        mBinding.restaurantsList.setAdapter(adapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RestaurantViewModel viewModel = ViewModelProviders.of(this)
                .get(RestaurantViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(RestaurantViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRestaurants().observe(this, myRestaurants -> {
            if (myRestaurants != null) {
                adapter.setProductList(myRestaurants);
                mBinding.restaurantsList.setAdapter(adapter);
            }
            mBinding.executePendingBindings();
        });
    }

    private final RestaurantMenuItemClickCallback clickCallback = restaurant -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(restaurant);
        }
    };
}

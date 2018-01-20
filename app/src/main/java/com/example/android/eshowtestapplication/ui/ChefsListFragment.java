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
import com.example.android.eshowtestapplication.databinding.FragmentChefsListBinding;
import com.example.android.eshowtestapplication.viewmodel.RestaurantViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChefsListFragment extends Fragment {

    public static final String TAG = "ChefsListFragment";

    private FragmentChefsListBinding mBinding;

    private RestaurantChefDetailsListAdapter mAdapter;

    public ChefsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chefs_list,
                container, false);
        mAdapter = new RestaurantChefDetailsListAdapter();
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
        viewModel.getChefs().observe(this, myChefs -> {
            if (myChefs != null) {
                mAdapter.setChefsList(myChefs);
                mBinding.chefsListRecyclerView.setAdapter(mAdapter);
            }
            mBinding.executePendingBindings();
        });
    }
}

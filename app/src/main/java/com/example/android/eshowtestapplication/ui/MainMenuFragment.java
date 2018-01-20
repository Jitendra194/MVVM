package com.example.android.eshowtestapplication.ui;


import android.arch.lifecycle.Lifecycle;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.eshowtestapplication.R;
import com.example.android.eshowtestapplication.databinding.FragmentMainMenuBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {

    public static final String TAG = "MainMenuFragmentTag";

    private FragmentMainMenuBinding mBinding;

    private int mItemClick;

    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu
                , container, false);
        mBinding.setCallback(mainMenuItemClickCallback);
        return mBinding.getRoot();
    }

    private final MainMenuItemClickCallback mainMenuItemClickCallback = new MainMenuItemClickCallback() {
        @Override
        public void onRestaurantsClick() {
            mItemClick = 1;
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(mItemClick);
        }
//            ((MainActivity) getActivity()).show(mItemClick);
        }

        @Override
        public void onChefsClick() {
            mItemClick = 2;
//        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
//            ((MainActivity) getActivity()).show(mItemClick);
//        }
            ((MainActivity) getActivity()).show(mItemClick);
        }
    };

}

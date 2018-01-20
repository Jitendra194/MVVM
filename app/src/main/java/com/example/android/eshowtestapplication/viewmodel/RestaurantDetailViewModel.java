package com.example.android.eshowtestapplication.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.eshowtestapplication.BasicApp;
import com.example.android.eshowtestapplication.db.DataRepository;
import com.example.android.eshowtestapplication.model.Chef;
import com.example.android.eshowtestapplication.model.Restaurant;

import java.util.List;

/**
 * Created by jiten on 1/16/2018.
 */

public class RestaurantDetailViewModel extends AndroidViewModel {

    private final int mRestaurantId;

    private final MutableLiveData<Restaurant> mObservableRestaurant;
    private final MutableLiveData<List<Chef>> mObservableChefs;


    public RestaurantDetailViewModel(@NonNull Application application, DataRepository dataRepository,
                                     final int restaurantID) {
        super(application);
        mRestaurantId = restaurantID;
        mObservableRestaurant = dataRepository.getRestaurant(mRestaurantId, application.getApplicationContext());
        mObservableChefs = dataRepository.getMatchingChefsFromWorkTable(mRestaurantId, application.getApplicationContext());
    }

    public LiveData<Restaurant> getRestaurant() {
        return mObservableRestaurant;
    }

    public LiveData<List<Chef>> getChefsFromWorkTable() {
        return mObservableChefs;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mProductId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RestaurantDetailViewModel(mApplication, mRepository, mProductId);
        }
    }

}

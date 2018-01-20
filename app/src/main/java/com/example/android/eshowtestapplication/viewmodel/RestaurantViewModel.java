package com.example.android.eshowtestapplication.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.android.eshowtestapplication.BasicApp;
import com.example.android.eshowtestapplication.model.Chef;
import com.example.android.eshowtestapplication.model.Restaurant;

import java.util.List;

/**
 * Created by jiten on 1/14/2018.
 */

public class RestaurantViewModel extends AndroidViewModel {

    private MutableLiveData<List<Restaurant>> mObservableRestaurants;

    private MutableLiveData<List<Chef>> mObservablechefs;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        mObservableRestaurants = ((BasicApp) application).getRepository().getRestaurants(application.getApplicationContext());
        mObservablechefs = ((BasicApp) application).getRepository().getAllChefs(application.getApplicationContext());
    }

    //expose live data
    public LiveData<List<Restaurant>> getRestaurants() {
        return mObservableRestaurants;
    }

    public LiveData<List<Chef>> getChefs() {
        return mObservablechefs;
    }

}

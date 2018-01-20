package com.example.android.eshowtestapplication.db;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.android.eshowtestapplication.AppExecutors;
import com.example.android.eshowtestapplication.DataGenerator;
import com.example.android.eshowtestapplication.db.ContractClass.ChefEntry;
import com.example.android.eshowtestapplication.db.ContractClass.RestaurantEntry;
import com.example.android.eshowtestapplication.db.ContractClass.WorkEntry;
import com.example.android.eshowtestapplication.model.Chef;
import com.example.android.eshowtestapplication.model.Restaurant;

import java.util.List;

/**
 * Created by jiten on 1/14/2018.
 */

public class DataRepository {

    private static DataRepository sInstance;

    private static final String[] RESTAURANT_PROJECTION = {
            RestaurantEntry.COLUMN_RESTAURANT_ID,
            RestaurantEntry.COLUMN_NAME,
            RestaurantEntry.COLUMN_ADDRESS_1,
            RestaurantEntry.COLUMN_ADDRESS_2,
            RestaurantEntry.COLUMN_CITY,
            RestaurantEntry.COLUMN_STATE,
            RestaurantEntry.COLUMN_COUNTRY,
            RestaurantEntry.COLUMN_PHONE
    };

    private static final String[] CHEF_PROJECTION = {
            ChefEntry.COLUMN_CHEF_ID,
            ChefEntry.COLUMN_FIRST_NAME,
            ChefEntry.COLUMN_LAST_NAME,
            ChefEntry.COLUMN_ADDRESS,
            ChefEntry.COLUMN_CITY,
            ChefEntry.COLUMN_STATE,
            ChefEntry.COLUMN_COUNTRY,
            ChefEntry.COLUMN_EMAIL
    };

    private static final String[] WORK_PROJECTION = {
            WorkEntry.COLUMN_RESTAURANT_ID,
            WorkEntry.COLUMN_CHEF_ID
    };

    private MutableLiveData<List<Restaurant>> mObservableRestaurants = new MutableLiveData<>();
    private MutableLiveData<List<Chef>> mObservableChefs = new MutableLiveData<>();
    private MutableLiveData<Restaurant> mObservableRestaurant = new MutableLiveData<>();
    private MutableLiveData<List<Chef>> mObservableChefFromWork = new MutableLiveData<>();


    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private DataRepository(Context context, AppExecutors executors) {
        if (mIsDatabaseCreated.getValue() == null) {
            DataGenerator.insertDataToDatabase(context, executors);
            mIsDatabaseCreated.postValue(true);
        }
    }

    public static DataRepository getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(context, executors);
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<Restaurant>> getRestaurants(Context context) {
        mObservableRestaurants.postValue(DataGenerator.generateRestaurants(context, RESTAURANT_PROJECTION));
        return mObservableRestaurants;
    }

    public MutableLiveData<Restaurant> getRestaurant(final int restaurantId, Context context) {
        mObservableRestaurant.postValue(DataGenerator.generateRestaurant(restaurantId, context, RESTAURANT_PROJECTION));
        return mObservableRestaurant;
    }

    public MutableLiveData<List<Chef>> getAllChefs(Context context) {
        mObservableChefs.postValue(DataGenerator.generateAllChefs(context, CHEF_PROJECTION));
        return mObservableChefs;
    }

    public MutableLiveData<List<Chef>> getMatchingChefsFromWorkTable(int mRestaurantId, Context applicationContext) {
        mObservableChefFromWork.postValue(DataGenerator.generateChefsFromWork(mRestaurantId, applicationContext, WORK_PROJECTION, CHEF_PROJECTION));
        return mObservableChefFromWork;
    }
}

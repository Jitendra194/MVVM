package com.example.android.eshowtestapplication;

import android.app.Application;

import com.example.android.eshowtestapplication.db.DataRepository;

/**
 * Created by jiten on 1/15/2018.
 */

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(this, mAppExecutors);
    }
}

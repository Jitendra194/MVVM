package com.example.android.eshowtestapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.eshowtestapplication.db.ContractClass.ChefEntry;
import com.example.android.eshowtestapplication.db.ContractClass.RestaurantEntry;
import com.example.android.eshowtestapplication.db.ContractClass.WorkEntry;
import com.example.android.eshowtestapplication.model.Chef;
import com.example.android.eshowtestapplication.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiten on 1/14/2018.
 */

public class DataGenerator {

    private static final String[] RESTAURANT_ID = new String[]{
            "1", "2", "3", "4", "5"};
    private static final String[] RESTAURANT_NAME = new String[]{
            "Apple Bees", "Honey World", "Cafe Milano", "Streets of NY", "Yum Yum"};
    private static final String[] RESTAURANT_ADDRESS1 = new String[]{
            "1107 N Underhill St", "1108 N Underhill St", "1109 N Underhill St", "1110 N Underhill St", "1111 N Underhill St"};
    private static final String[] RESTAURANT_CITY = new String[]{
            "South Barrington", "Peoria", "Chicago", "Austin", "Dallas"};
    private static final String[] RESTAURANT_STATE = new String[]{
            "IL", "TX", "CA", "FL", "GA"};
    private static final String[] RESTAURANT_COUNTRY = new String[]{
            "US", "US", "US", "US", "US"};
    private static final String[] RESTAURANT_PHONE = new String[]{
            "773-651-2465", "773-895-4552", "654-432-7884", "488-562-3665", "254-532-8874"};


    private static final String[] CHEF_ID = new String[]{
            "1", "2", "3", "4", "5"};
    private static final String[] CHEF_FIRST_NAME = new String[]{
            "Robert", "Amy", "Hugh", "Tommy", "Jim"};
    private static final String[] CHEF_LAST_NAME = new String[]{
            "Brown", "Morgan", "Ackerman", "Hay", "Uskov"};
    private static final String[] CHEF_ADDRESS = new String[]{
            "1101 N Underhill St", "1102 N Underhill St", "1103 N Underhill St", "1104 N Underhill St", "1105 N Underhill St"};
    private static final String[] CHEF_CITY = new String[]{
            "South Barrington", "Peoria", "Chicago", "Austin", "Dallas"};
    private static final String[] CHEF_STATE = new String[]{
            "IL", "TX", "CA", "FL", "GA"};
    private static final String[] CHEF_COUNTRY = new String[]{
            "US", "US", "US", "US", "US"};
    private static final String[] CHEF_EMAIL = new String[]{
            "773-651-2465", "773-895-4552", "654-432-7884", "488-562-3665", "254-532-8874"};


    private static final String[] WORK_RESTAURANT_ID = new String[]{
            "1", "1", "1", "2", "2", "3", "3", "4", "4", "4", "5", "5", "3"};
    private static final String[] WORK_CHEF_ID = new String[]{
            "1", "4", "5", "3", "1", "1", "2", "1", "2", "3", "4", "5", "4"};


    public static void insertDataToDatabase(Context context, AppExecutors executors) {
        executors.diskIO().execute(() -> {
            ContentValues[] contentValuesRestaurants = new ContentValues[RESTAURANT_ID.length];
            for (int i = 0; i < RESTAURANT_ID.length; i++) {
                ContentValues values = new ContentValues();
                values.put(RestaurantEntry.COLUMN_RESTAURANT_ID, RESTAURANT_ID[i]);
                values.put(RestaurantEntry.COLUMN_NAME, RESTAURANT_NAME[i]);
                values.put(RestaurantEntry.COLUMN_ADDRESS_1, RESTAURANT_ADDRESS1[i]);
                values.put(RestaurantEntry.COLUMN_CITY, RESTAURANT_CITY[i]);
                values.put(RestaurantEntry.COLUMN_STATE, RESTAURANT_STATE[i]);
                values.put(RestaurantEntry.COLUMN_COUNTRY, RESTAURANT_COUNTRY[i]);
                values.put(RestaurantEntry.COLUMN_PHONE, RESTAURANT_PHONE[i]);
                contentValuesRestaurants[i] = values;
            }
            context.getContentResolver().bulkInsert(RestaurantEntry.CONTENT_URI, contentValuesRestaurants);

            ContentValues[] contentValuesChefs = new ContentValues[CHEF_ID.length];
            for (int i = 0; i < CHEF_ID.length; i++) {
                ContentValues values = new ContentValues();
                values.put(ChefEntry.COLUMN_CHEF_ID, CHEF_ID[i]);
                values.put(ChefEntry.COLUMN_FIRST_NAME, CHEF_FIRST_NAME[i]);
                values.put(ChefEntry.COLUMN_LAST_NAME, CHEF_LAST_NAME[i]);
                values.put(ChefEntry.COLUMN_ADDRESS, CHEF_ADDRESS[i]);
                values.put(ChefEntry.COLUMN_CITY, CHEF_CITY[i]);
                values.put(ChefEntry.COLUMN_STATE, CHEF_STATE[i]);
                values.put(ChefEntry.COLUMN_COUNTRY, CHEF_COUNTRY[i]);
                values.put(ChefEntry.COLUMN_EMAIL, CHEF_EMAIL[i]);
                contentValuesChefs[i] = values;
            }
            context.getContentResolver().bulkInsert(ChefEntry.CONTENT_URI, contentValuesChefs);

            ContentValues[] contentValuesWork = new ContentValues[WORK_RESTAURANT_ID.length];
            for (int i = 0; i < WORK_RESTAURANT_ID.length; i++) {
                ContentValues values = new ContentValues();
                values.put(WorkEntry.COLUMN_CHEF_ID, WORK_CHEF_ID[i]);
                values.put(WorkEntry.COLUMN_RESTAURANT_ID, WORK_RESTAURANT_ID[i]);
                contentValuesWork[i] = values;
            }
            context.getContentResolver().bulkInsert(WorkEntry.CONTENT_URI, contentValuesWork);
        });
    }


    public static List<Restaurant> generateRestaurants(Context context, String[] restaurantProjection) {
        List<Restaurant> restaurants = new ArrayList<>(5);

        Cursor cursor = null;
        try {
            cursor = getQueryData(context, RestaurantEntry.CONTENT_URI, restaurantProjection);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    restaurants.add(setRestaurantData(cursor));
                } while (cursor.moveToNext());
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return restaurants;
    }


    public static Restaurant generateRestaurant(int restaurantId, Context context, String[] restaurantProjection) {
        Restaurant restaurant = null;
        Uri uri = RestaurantEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(restaurantId)).build();
        Cursor cursor = null;
        try {
            cursor = getQueryData(context, uri, restaurantProjection);
            if (cursor != null && cursor.moveToFirst()) {
                restaurant = setRestaurantData(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return restaurant;
    }

    private static Restaurant setRestaurantData(Cursor cursor) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_RESTAURANT_ID)));
        restaurant.setName(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_NAME)));
        restaurant.setAddress1(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_ADDRESS_1)));
        restaurant.setAddress2(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_ADDRESS_2)));
        restaurant.setCity(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_CITY)));
        restaurant.setState(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_STATE)));
        restaurant.setCountry(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_COUNTRY)));
        restaurant.setPhone(cursor.getString(cursor.getColumnIndex(RestaurantEntry.COLUMN_PHONE)));
        return restaurant;
    }

    public static Chef generateChef(String chefId, Context context, String[] chefProjection) {
        Chef chef = null;
        Uri uri = ChefEntry.CONTENT_URI.buildUpon()
                .appendPath(chefId).build();
        Cursor cursor = null;
        try {
            cursor = getQueryData(context, uri, chefProjection);
            if (cursor != null && cursor.moveToFirst()) {
                chef = setChefsData(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return chef;
    }

    public static List<Chef> generateAllChefs(Context context, String[] chefProjection) {
        List<Chef> chefs = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = getQueryData(context, ChefEntry.CONTENT_URI, chefProjection);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    chefs.add(setChefsData(cursor));
                } while (cursor.moveToNext());
            }

        } finally {
            assert cursor != null;
            cursor.close();
        }
        return chefs;
    }

    public static List<Chef> generateChefsFromWork(int restaurantId, Context context,
                                                   String[] workProjection, String[] chefProjection) {
        Uri uriWork = WorkEntry.CONTENT_URI.buildUpon()
                .appendPath(WorkEntry.COLUMN_RESTAURANT_ID)
                .appendPath(String.valueOf(restaurantId)).build();
        Cursor workCursor = null;
        List<Chef> chefs = new ArrayList<>();
        try {
            workCursor = getQueryData(context, uriWork, workProjection);
            if (workCursor != null && workCursor.moveToFirst()) {
                do {
                    String chefID = workCursor.getString(workCursor.getColumnIndex(WorkEntry.COLUMN_CHEF_ID));
                    chefs.add(generateChef(chefID, context, chefProjection));
                } while (workCursor.moveToNext());
//                Cursor chefCursor = null;
//                try {
//                    do {
//                        String chefID = workCursor.getString(workCursor.getColumnIndex(WorkEntry.COLUMN_CHEF_ID));
//                        Uri uriChef = ChefEntry.CONTENT_URI.buildUpon()
//                                .appendPath(chefID)
//                                .build();
//                        chefCursor = getQueryData(context, uriChef, chefProjection);
//                        chefs.add(setChefsData(chefCursor));
//                    } while (workCursor.moveToNext());
//                } finally {
//                    if (chefCursor != null) {
//                        chefCursor.close();
//                    }
//                }
            }
        } finally {
            if (workCursor != null) {
                workCursor.close();
            }
        }
        return chefs;
    }

    private static Chef setChefsData(Cursor cursor) {
        Chef chef = new Chef();
        chef.setId(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_CHEF_ID)));
        chef.setFirstName(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_FIRST_NAME)));
        chef.setLastName(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_LAST_NAME)));
        chef.setAddress(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_ADDRESS)));
        chef.setCity(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_CITY)));
        chef.setState(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_STATE)));
        chef.setCountry(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_COUNTRY)));
        chef.setEmail(cursor.getString(cursor.getColumnIndex(ChefEntry.COLUMN_EMAIL)));
        return chef;
    }

    private static Cursor getQueryData(Context context, Uri uri, String[] projection) {
        return context.getContentResolver()
                .query(uri, projection, null, null, null);
    }
}


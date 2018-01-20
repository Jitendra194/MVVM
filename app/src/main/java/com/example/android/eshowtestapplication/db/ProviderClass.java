package com.example.android.eshowtestapplication.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.eshowtestapplication.db.ContractClass.ChefEntry;
import com.example.android.eshowtestapplication.db.ContractClass.RestaurantEntry;
import com.example.android.eshowtestapplication.db.ContractClass.WorkEntry;


/**
 * Created by jiten on 1/12/2018.
 */

public class ProviderClass extends ContentProvider {

    private static final String TAG = ContractClass.class.getSimpleName();

    private static final int CODE_RESTAURANT = 100;
    private static final int CODE_RESTAURANT_ID = 101;
    private static final int CODE_CHEF = 200;
    private static final int CODE_CHEF_ID = 201;
    private static final int CODE_WORK = 300;
    private static final int CODE_WORK_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ContractClass.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, ContractClass.PATH_RESTAURANT_TABLE, CODE_RESTAURANT);
        uriMatcher.addURI(authority, ContractClass.PATH_RESTAURANT_TABLE + "/#", CODE_RESTAURANT_ID);
        uriMatcher.addURI(authority, ContractClass.PATH_CHEF_TABLE, CODE_CHEF);
        uriMatcher.addURI(authority, ContractClass.PATH_CHEF_TABLE + "/#", CODE_CHEF_ID);
        uriMatcher.addURI(authority, ContractClass.PATH_WORK_TABLE, CODE_WORK);
        uriMatcher.addURI(authority, ContractClass.PATH_WORK_TABLE + "/" + WorkEntry.COLUMN_RESTAURANT_ID + "/#", CODE_WORK_ID);
        return uriMatcher;
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_RESTAURANT:
                cursor = database.query(RestaurantEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_RESTAURANT_ID:
                String restaurantID = uri.getLastPathSegment();
                String mSelection = RestaurantEntry.COLUMN_RESTAURANT_ID + "=?";
                String[] mSelectionArguments = new String[]{restaurantID};
                cursor = database.query(RestaurantEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_CHEF:
                cursor = database.query(ChefEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_CHEF_ID:
                String chefID = uri.getLastPathSegment();
                String mSelectionChef = ChefEntry.COLUMN_CHEF_ID + "=?";
                String[] mSelectionArgumentsChef = new String[]{chefID};
                cursor = database.query(ChefEntry.TABLE_NAME,
                        projection,
                        mSelectionChef,
                        mSelectionArgumentsChef,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_WORK:
                cursor = database.query(WorkEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_WORK_ID:
                String restaurantId = uri.getLastPathSegment();
                String mSelectionRestaurant = WorkEntry.COLUMN_RESTAURANT_ID + "=?";
                String[] mSelectionArgumentsRestaurant = new String[]{restaurantId};
                cursor = database.query(WorkEntry.TABLE_NAME,
                        projection,
                        mSelectionRestaurant,
                        mSelectionArgumentsRestaurant,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Query is not supported for " + uri);
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long id;
        switch (match) {
            case CODE_RESTAURANT:
                id = database.insert(RestaurantEntry.TABLE_NAME, null, values);
                break;
            case CODE_CHEF:
                id = database.insert(ChefEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int numInserted = 0;
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CODE_RESTAURANT:
                database.beginTransaction();
                try {
                    for (ContentValues contentValues : values) {
                        long newID = database.insertOrThrow(RestaurantEntry.TABLE_NAME, null, contentValues);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;
            case CODE_CHEF:
                database.beginTransaction();
                try {
                    for (ContentValues contentValues : values) {
                        long newID = database.insertOrThrow(ChefEntry.TABLE_NAME, null, contentValues);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;
            case CODE_WORK:
                database.beginTransaction();
                try {
                    for (ContentValues contentValues : values) {
                        long newID = database.insertOrThrow(WorkEntry.TABLE_NAME, null, contentValues);
                        if (newID <= 0) {
                            throw new SQLException("Failed to insert row into " + uri);
                        }
                    }
                    database.setTransactionSuccessful();
                    getContext().getContentResolver().notifyChange(uri, null);
                    numInserted = values.length;
                } finally {
                    database.endTransaction();
                }
                break;
        }
        return numInserted;
    }
}

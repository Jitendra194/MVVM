package com.example.android.eshowtestapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.eshowtestapplication.db.ContractClass.ChefEntry;
import com.example.android.eshowtestapplication.db.ContractClass.RestaurantEntry;
import com.example.android.eshowtestapplication.db.ContractClass.WorkEntry;

/**
 * Created by jiten on 1/12/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database";

    private static final int DATABASE_VERSION = 7;

    private static final String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
            RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RestaurantEntry.COLUMN_RESTAURANT_ID + " INTEGER NOT NULL, " +
            RestaurantEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            RestaurantEntry.COLUMN_ADDRESS_1 + " TEXT NOT NULL, " +
            RestaurantEntry.COLUMN_ADDRESS_2 + " TEXT, " +
            RestaurantEntry.COLUMN_CITY + " TEXT NOT NULL, " +
            RestaurantEntry.COLUMN_STATE + " TEXT NOT NULL, " +
            RestaurantEntry.COLUMN_COUNTRY + " TEXT NOT NULL, " +
            RestaurantEntry.COLUMN_PHONE + " TEXT NOT NULL, " +
            " UNIQUE (" + RestaurantEntry.COLUMN_RESTAURANT_ID + ") ON CONFLICT REPLACE);";

    private static final String CREATE_CHEF_TABLE = "CREATE TABLE " + ChefEntry.TABLE_NAME + " (" +
            ChefEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChefEntry.COLUMN_CHEF_ID + " INTEGER NOT NULL, " +
            ChefEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_CITY + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_STATE + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_COUNTRY + " TEXT NOT NULL, " +
            ChefEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
            " UNIQUE (" + ChefEntry.COLUMN_CHEF_ID + ") ON CONFLICT REPLACE);";

    private static final String CREATE_WORK_TABLE = "CREATE TABLE " + WorkEntry.TABLE_NAME + " (" +
            WorkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            WorkEntry.COLUMN_RESTAURANT_ID + " INTEGER NOT NULL, " +
            WorkEntry.COLUMN_CHEF_ID + " INTEGER NOT NULL, " +
            " UNIQUE (" + WorkEntry.COLUMN_RESTAURANT_ID + ", " + WorkEntry.COLUMN_CHEF_ID + ") ON CONFLICT REPLACE);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_CHEF_TABLE);
        db.execSQL(CREATE_WORK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ChefEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WorkEntry.TABLE_NAME);
        onCreate(db);
    }
}

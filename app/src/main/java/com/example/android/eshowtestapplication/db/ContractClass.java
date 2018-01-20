package com.example.android.eshowtestapplication.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jiten on 1/12/2018.
 */

public class ContractClass {

    public static final String CONTENT_AUTHORITY = "com.example.android.eshowtestapplication";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESTAURANT_TABLE = "restaurant";

    public static final String PATH_CHEF_TABLE = "chef";

    public static final String PATH_WORK_TABLE = "work";

    public static final class RestaurantEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RESTAURANT_TABLE)
                .build();

        public static final String TABLE_NAME = "Restaurants";

        public static final String COLUMN_RESTAURANT_ID = "id";

        public static final String COLUMN_NAME = "Name";

        public static final String COLUMN_ADDRESS_1 = "address1";

        public static final String COLUMN_ADDRESS_2 = "address2";

        public static final String COLUMN_CITY = "city";

        public static final String COLUMN_STATE = "state";

        public static final String COLUMN_COUNTRY = "country";

        public static final String COLUMN_PHONE = "Phone";

    }

    public static final class ChefEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CHEF_TABLE)
                .build();

        public static final String TABLE_NAME = "Chefs";

        public static final String COLUMN_CHEF_ID = "id";

        public static final String COLUMN_FIRST_NAME = "First_Name";

        public static final String COLUMN_LAST_NAME = "Last_Name";

        public static final String COLUMN_ADDRESS = "Address";

        public static final String COLUMN_CITY = "City";

        public static final String COLUMN_STATE = "State";

        public static final String COLUMN_COUNTRY = "Country";

        public static final String COLUMN_EMAIL = "email";

    }

    public static final class WorkEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WORK_TABLE)
                .build();

        public static final String TABLE_NAME = "work";

        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";

        public static final String COLUMN_CHEF_ID = "chef_id";
    }

}

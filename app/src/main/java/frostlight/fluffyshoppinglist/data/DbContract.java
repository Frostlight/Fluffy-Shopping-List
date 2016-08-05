package frostlight.fluffyshoppinglist.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * DbContract
 * Defines table and column names for the databases, and relevant content provider constants
 * Created by Vincent on 5/19/2015.
 */
public class DbContract {

    // Content authority is the name of the package of the app
    public static final String CONTENT_AUTHORITY = "frostlight.fluffyshoppinglist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible Paths
    public static final String PATH_SHOPPINGLIST = "shoppinglist";
    public static final String PATH_GROCERY = "grocery";

    /**
     * Inner class that defines the table contents of the shopping list table
     * The calendar table stores the names and dates created of each saved shopping list
     * Table: ID | Shopping List Name | Date/Time
     */
    public static final class ShoppingListEntry implements BaseColumns {
        /** ----------------  Uri definitions and functions ---------------- */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SHOPPINGLIST).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SHOPPINGLIST;

        /**
         * Builds a Uri for the ShoppingListEntry table with a specified ID
         *
         * @param id ID of Uri to build
         * @return Resultant Uri
         */
        public static Uri buildShoppingListUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /** ------------------------  Table Details ------------------------ */
        public static final String TABLE_NAME = "shoppinglist";

        // The name of the shopping list
        public static final String COLUMN_LISTNAME = "list_name";

        // The date the shopping list was created
        public static final String COLUMN_DATE = "date";
    }

    /**
     * Inner class that defines the table contents of the Grocery table
     * The Grocery table stores grocery list entries
     * Table: ID | Shopping List ID (foreign key constraint) | Grocery Name | Grocery Quantity
     */
    public static final class GroceryEntry implements BaseColumns {
        /** ----------------  Uri definitions and functions ---------------- */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GROCERY).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GROCERY;

        /**
         * Builds a Uri for the GroceryEntry table with a specified ID
         *
         * @param id ID of Uri to build
         * @return Resultant Uri
         */
        public static Uri buildGroceryId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /** ------------------------  Table Details ------------------------ */
        public static final String TABLE_NAME = "grocery";

        // The ID of the grocery list
        public static final String COLUMN_SHOPPINGLISTID = GroceryEntry.COLUMN_SHOPPINGLISTID;

        // The name of the grocery entry
        public static final String COLUMN_NAME = GroceryEntry.COLUMN_NAME;

        // The quantity of the grocery entry
        public static final String COLUMN_QUANTITY = GroceryEntry.COLUMN_QUANTITY;
    }
}

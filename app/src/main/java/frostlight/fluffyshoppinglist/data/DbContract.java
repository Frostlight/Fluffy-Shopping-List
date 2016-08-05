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

    // TODO: Update
    public static final String PATH_TWITTER = "twitter";
    public static final String PATH_TRANSLATION = "translation";
    public static final String PATH_EMERGENCYQUEST = "emergencyquest";

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
        public static Uri buildCalendarUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /** ------------------------  Table Details ------------------------ */
        public static final String TABLE_NAME = "shoppinglist";

        // The name of the emergency quest
        public static final String COLUMN_LISTNAME = "list_name";

        // The time the emergency quest occurs
        public static final String COLUMN_DATE = "date";
    }

    // TODO: Update below

    /**
     * Inner class that defines the table contents of the Twitter table
     * The twitter table stores emergency quest alert tweets obtained from Twitter bots
     * Table: ID | EQ Name | Date/Time
     */
    public static final class TwitterEntry implements BaseColumns {
        /** ----------------  Uri definitions and functions ---------------- */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TWITTER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TWITTER;

        /**
         * Builds a Uri for the TwitterEntry table with a specified ID
         *
         * @param id ID of Uri to build
         * @return Resultant Uri
         */
        public static Uri buildTwitterUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "twitter";

        // Columns should be the same as in CalendarEntry
        // The name of the emergency quest
        public static final String COLUMN_EQNAME = CalendarEntry.COLUMN_EQNAME;

        // The time the emergency quest occurs
        public static final String COLUMN_DATE = CalendarEntry.COLUMN_DATE;
    }

    /**
     * Inner class that defines the table contents of the translation table
     * The translation table stores the japanese/english pairs for the emergency quest names
     * Table: ID | Japanese Name | English Name
     */
    public static final class TranslationEntry implements BaseColumns {
        /** ----------------  Uri definitions and functions ---------------- */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSLATION).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSLATION;

        /**
         * Builds a Uri for the TranslationEntry table with a specified ID
         *
         * @param id ID of Uri to build
         * @return Resultant Uri
         */
        public static Uri buildTranslationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "translation";

        // The name of the emergency quest in Japanese
        public static final String COLUMN_JAPANESE = "japanese";

        // The corresponding english translation
        public static final String COLUMN_ENGLISH = "english";
    }

    /**
     * Inner class that contains the union of the calendar table and Twitter table
     * Table: ID | Japanese Name | English Name
     */
    public static final class EmergencyQuest {
        /** ----------------  Uri definitions and functions ---------------- */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EMERGENCYQUEST).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EMERGENCYQUEST;
    }
}

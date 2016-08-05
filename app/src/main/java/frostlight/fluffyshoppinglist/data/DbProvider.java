package frostlight.fluffyshoppinglist.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * DbProvider
 * Manages the content provider for the app
 * Created by Vincent on 6/7/2015.
 */
public class DbProvider extends ContentProvider {

    // Matches URIs for content provider requests
    private static final UriMatcher sUriMatcher = uriMatcher();

    // Database helper object
    private DbHelper mDbHelper;

    // IDs for URI types
    static final int SHOPPINGLIST = 1;
    static final int GROCERY = 2;

    /**
     * Creates a UriMatcher for the content provider
     * @return UriMatcher with all the relevant tables
     */
    static UriMatcher uriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;

        // Add all URIs for content
        uriMatcher.addURI(authority, DbContract.PATH_SHOPPINGLIST, SHOPPINGLIST);
        uriMatcher.addURI(authority, DbContract.PATH_GROCERY, GROCERY);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        // Initialise the database helper
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case SHOPPINGLIST:
                return DbContract.ShoppingListEntry.CONTENT_TYPE;
            case GROCERY:
                return DbContract.GroceryEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Error: Unknown URI " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Start by getting a readable database
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        Cursor returnCursor;

        // Use the uriMatcher to match the URI's we are going to handle
        // If it doesn't match these, throw an UnsupportedOperationException
        switch(sUriMatcher.match(uri)) {
            case SHOPPINGLIST:
                returnCursor = sqLiteDatabase.query(
                        DbContract.ShoppingListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case GROCERY:
                returnCursor = sqLiteDatabase.query(
                        DbContract.GroceryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Error: Unknown URI " + uri);
        }

        // Set the Uri to observe on the cursor for content observers
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor containing the results of the query
        return returnCursor;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Start by getting a writable database
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        Uri returnUri;
        long _id; // ID of the new database entry

        // Use the uriMatcher to match the URI's we are going to handle
        // If it doesn't match these, throw an UnsupportedOperationException
        int uriMatch = sUriMatcher.match(uri);
        switch(uriMatch)
        {
            case SHOPPINGLIST:
                _id = sqLiteDatabase.insert(DbContract.ShoppingListEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DbContract.ShoppingListEntry.buildShoppingListUri(_id);
                else
                    throw new android.database.SQLException("Error: Failed to insert row into " + uri);
                break;
            case GROCERY:
                _id = sqLiteDatabase.insert(DbContract.GroceryEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DbContract.GroceryEntry.buildGroceryId(_id);
                else
                    throw new android.database.SQLException("Error: Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Error: Unknown URI " + uri);
        }

        // Notify the content observers
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the Uri of the entry that was just inserted
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Start by getting a writable database
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int deleteCount; // Number of rows deleted

        // Use the uriMatcher to match the URI's we are going to handle
        // If it doesn't match these, throw an UnsupportedOperationException
        int uriMatch = sUriMatcher.match(uri);
        switch(uriMatch)
        {
            case SHOPPINGLIST:
                deleteCount = sqLiteDatabase.delete(DbContract.ShoppingListEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case GROCERY:
                deleteCount = sqLiteDatabase.delete(DbContract.GroceryEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Error: Unknown URI " + uri);
        }

        // If rows were deleted, notify the content observers
        if (deleteCount > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of rows deleted
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Start by getting a writable database
        final SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        int updateCount;

        // Use the uriMatcher to match the URI's we are going to handle
        // If it doesn't match these, throw an UnsupportedOperationException
        int uriMatch = sUriMatcher.match(uri);
        switch(uriMatch)
        {
            case SHOPPINGLIST:
                updateCount = sqLiteDatabase.update(DbContract.ShoppingListEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case GROCERY:
                updateCount = sqLiteDatabase.update(DbContract.GroceryEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Error: Unknown URI " + uri);
        }

        // If rows were updated, notify the content observers
        if (updateCount > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        // Return the number of rows updated
        return updateCount;
    }
}

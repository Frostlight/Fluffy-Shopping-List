package frostlight.fluffyshoppinglist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import frostlight.fluffyshoppinglist.data.DbContract.ShoppingListEntry;
import frostlight.fluffyshoppinglist.data.DbContract.GroceryEntry;

/**
 * DbHelper
 * Manages a local database for relevant data.
 * Created by Vincent on 5/19/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    // If the database schema is changed, increment the database version
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "grocery.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Enable foreign key constraints
        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");

        // Create each table with columns as specified in DbContract
        final String SQL_CREATE_SHOPPINGLIST_TABLE = "CREATE TABLE " + ShoppingListEntry.TABLE_NAME + " (" +
                ShoppingListEntry._ID + " INTEGER PRIMARY KEY," +
                ShoppingListEntry.COLUMN_LISTNAME + " TEXT NOT NULL," +
                ShoppingListEntry.COLUMN_DATE + " INTEGER NOT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_SHOPPINGLIST_TABLE);

        final String SQL_CREATE_GROCERY_TABLE = "CREATE TABLE " + GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry._ID + " INTEGER PRIMARY KEY," +
                GroceryEntry.COLUMN_SHOPPINGLISTID + " INTEGER NOT NULL," +
                GroceryEntry.COLUMN_NAME + " TEXT NOT NULL," +
                GroceryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + GroceryEntry.COLUMN_SHOPPINGLISTID + ") REFERENCES shoppinglist(id) );";
        sqLiteDatabase.execSQL(SQL_CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Do not clear existing databases for now
        onCreate(sqLiteDatabase);
    }
}

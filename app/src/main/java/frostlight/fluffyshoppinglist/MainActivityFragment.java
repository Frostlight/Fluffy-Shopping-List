package frostlight.fluffyshoppinglist;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import frostlight.fluffyshoppinglist.data.DbContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private GroceryAdapter mMainAdapter;
    private ListView mListView;

    public static final int GROCERY_LOADER = 0;
    private static final String[] GROCERY_COLUMNS = {
            DbContract.GroceryEntry._ID,
            DbContract.GroceryEntry.COLUMN_SHOPPINGLISTID,
            DbContract.GroceryEntry.COLUMN_NAME,
            DbContract.GroceryEntry.COLUMN_QUANTITY
    };

    // These indices are tied to GROCERY_COLUMNS
    // Change this when GROCERY_COLUMNS changes
    static final int COL_ID = 0;
    static final int COL_SHOPPINGLISTID = 1;
    static final int COL_NAME = 2;
    static final int COL_QUANTITY = 3;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the main fragment XML, and set member variables for the full fragment and ListView
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // CursorAdapter takes data from the cursor and populates the ListView
        mMainAdapter = new GroceryAdapter(getActivity(), null, 0);
        mListView = (ListView) rootView.findViewById(R.id.groceryListView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Start the loader to load the EQ schedule from the database
        getLoaderManager().initLoader(GROCERY_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Where clause: all events scheduled from 30 minutes in the past
        String whereClause = DbContract.GroceryEntry.COLUMN_SHOPPINGLISTID + " = 0";
        // Sort order: ascending by ID
        // String sortOrder = DbContract.GroceryEntry._ID + " ASC";

        return new CursorLoader(getActivity(),
                DbContract.GroceryEntry.CONTENT_URI,
                GROCERY_COLUMNS,
                whereClause,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Set the adapter and swap the loaded cursor so the adapter can populate the ListView
        mListView.setAdapter(mMainAdapter);
        mMainAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Garbage collection to avoid memory leak
        mMainAdapter.swapCursor(null);
    }
}

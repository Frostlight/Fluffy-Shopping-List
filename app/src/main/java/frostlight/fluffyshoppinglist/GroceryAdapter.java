package frostlight.fluffyshoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * MainAdapter
 * Exposes a schedule from a Cursor to a ListView
 * Created by Vincent on 8/16/2015.
 */
public class GroceryAdapter extends CursorAdapter {

    private Context mContext;

    public GroceryAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);

        mContext = context;
    }

    // Cache of the children views for a list item.
    public static class ViewHolder {
        public final TextView groceryNameView;
        public final TextView groceryUnitView;

        public ViewHolder(View view) {
            groceryNameView = (TextView) view.findViewById(R.id.list_item_grocery_name);
            groceryUnitView = (TextView) view.findViewById(R.id.list_item_grocery_units);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_grocery, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(final View view, Context context, Cursor cursor) {
        // Make a ViewHolder for the current view
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // Set grocery name and units for each entry
        viewHolder.groceryNameView.setText(Html.fromHtml(cursor.getString(MainActivityFragment.COL_NAME)));
        viewHolder.groceryUnitView.setText(Html.fromHtml(cursor.getString(MainActivityFragment.COL_QUANTITY)));
    }
}

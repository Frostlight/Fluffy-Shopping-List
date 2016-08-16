package frostlight.fluffyshoppinglist;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import frostlight.fluffyshoppinglist.data.DbContract;

public class MainActivity extends AppCompatActivity {
    Button mAddButton;          // Button to add groceries with
    EditText mGroceryItemEdit;  // Name of the grocery
    EditText mGroceryUnitEdit;  // Unit of the grocery (optional)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind UI elements
        mAddButton = (Button)findViewById(R.id.groceryAddButton);
        mGroceryItemEdit = (EditText)findViewById(R.id.groceryItemEdit);
        mGroceryUnitEdit = (EditText)findViewById(R.id.groceryUnitEdit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Add button should insert grocery into databases
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert each element into the database
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContract.GroceryEntry.COLUMN_NAME, mGroceryItemEdit.getText().toString());
                contentValues.put(DbContract.GroceryEntry.COLUMN_QUANTITY, mGroceryUnitEdit.getText().toString());

                // Placeholder ID
                contentValues.put(DbContract.GroceryEntry.COLUMN_SHOPPINGLISTID, "0");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.njordfjallen.offs3t.hobomaps.R;

import java.util.ArrayList;

/**
 * The activity that allows a user to create a new Checklist Item.
 *
 * A Checklist Item represents
 * a destination or recreational activity
 * that interests the user.
 *
 * A Checklist Item represents
 * a stop that the user wants to make
 * during their roadtrip.
 *
 * Constants                                {@link #TAG}<br>
 * Member variables
 * UI elements
 *
 * Android Activity lifecycle methods       {@link #onCreate(Bundle)}<br>
 * Initialization methods                   {@link #getUiHooks()}<br>
 * Methods that respond to user clicks
 * Methods that write to the database
 * Methods that read from the database
 * Methods that manage the options menu
 */
public class ChecklistAddActivity extends AppCompatActivity {

    /* Constants */

    /**
     * Android logging tag
     */
    private static final String TAG = "checklist/ChecklistAddActivity";

    public static final String ROW_ID_SELECTION =
            ChecklistItemContract.ChecklistItemEntry._ID + " = ?";

    public static final String[] PROJECTION = {
            ChecklistItemContract.ChecklistItemEntry._ID,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_START,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_END,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_DURATION,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_OTHER,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_NAME,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_STATE,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_CITY,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_ZIP,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_TYPE,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_LOW,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_HI,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_GOOG_MAPS
    };

    /**
     * A projection that only uses columns of fields that there are currently input fields for
     * (input/output)
     */
    public static final String[] PROJECTION_CURRENT_VIEWS = {
            ChecklistItemContract.ChecklistItemEntry._ID,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_START,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_END,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_DURATION,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_OTHER,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_NAME,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_STATE,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_CITY,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_ZIP,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_TYPE,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_LOW,
            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_HI,
//            ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_GOOG_MAPS
    };

    /**
     * An Intent extra key.
     * If this key is received, this activity will edit an existing Checklist Item
     * instead of creating a new one (when the user fills-in the initially-blank fields.
     * This key is used to pass the row ID of the Checklist Item that should be
     * displayed, made editable, and overwritten with the user's new values.
     */
    public static final String ROW_ID_KEY = "RowID";

    /**
     * The value for the row ID if a value was not received as an Intent extra
     */
    private static final long ROW_ID_NOT_RECEIVED = -1;

    /**
     * An Intent extra key.
     * If this key is received, this activity will set the focus to the View
     * that has the <code>int</code> ID that was given.
     */
    public static final String VIEW_FOCUS_KEY = "ViewFocus";

    /**
     * The maximum value allowable in the quantity number picker
     * TODO Currently un-used, need to change to value that will make sense
     */
    public static final int NUMBER_PICKER_MAX_VALUE = 999;



    /* Member variables */

    /**
     * Is set to true if the activity should edit an existing Checklist Item
     * instead of creating a new Checklist Item;
     * is set to false if it should create a new Checklist Item, as is normal
     */
    private boolean editMode;

    /**
     * The row ID of the Checklist Item in the database to display, if Edit Mode is on (set to true)
     */
    private long rowID;

    /**
     * The database helper for the Checklist Item table
     */
    private ChecklistItemDbHelper checklistItemDbHelper;



    /* UI elements */

    /* TODO
       Considerations:

       Time_Start should always be shown
       Time_End and Time_Duration, only one should be shown (switchable)

       Likewise for State/City or Zip code

       Time_Other and Goog_Maps unused
     */

    private EditText editItemName;

    private NumberPicker editTimeStart;

    private NumberPicker editTimeEnd;

    private NumberPicker editTimeDuration;

    private EditText editLocationName;

    private EditText editLocationState;

    private EditText editLocationCity;

    private EditText editLocationZip;

    private EditText editCost;

    private EditText editCostLow;

    private EditText editCostHi;

    // TODO EditText, Spinner, NumberPicker, CheckBox



    /* Android Activity lifecycle methods */

    /**
     * Called when the activity is starting
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     *                           down, then this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Stuff?

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    /* Initialization methods */

    /**
     * Set references to the UI elements, and log error messages if any necessary Views
     * are not found
     */
    private void getUiHooks() {
        // Get the UI hooks
        editItemName = (EditText) findViewById(R.id.edit_item_name);

        // TODO Hook up the NumberPicker views when they are completed in /res/layout/content_checklist_add.xml
        // editTimeStart = (NumberPicker) findViewById(R.id.edit_time_start);
        // editTimeEnd = (NumberPicker) findViewById(R.id.edit_time_end);
        // editTimeDuration= (NumberPicker) findViewById(R.id.edit_time_duration);

        // Location
        editLocationName = (EditText) findViewById(R.id.edit_location_name);
        editLocationState = (EditText) findViewById(R.id.edit_location_state);
        editLocationCity = (EditText) findViewById(R.id.edit_location_city);
        editLocationZip = (EditText) findViewById(R.id.edit_location_zip);

        // Cost
        editCost = (EditText) findViewById(R.id.edit_cost);
        editCostHi = (EditText) findViewById(R.id.edit_cost_hi);
        editCostLow = (EditText) findViewById(R.id.edit_cost_low);

        // Log error messages if any necessary Views are not found
        if (editItemName == null) {
            // TODO Error about >23 characters
//          Log.e(TAG, "EditText 'edit_item_name' not found!");
        }
        // TODO Log other error messages, if necessary
    }



    /* Methods that respond to user clicks */

    /**
     * Respond when the user presses the 'Submit' button.
     * Create the new Item in the database if running in normal mode,
     * and update the Item in the database if running in edit mode.
     * @param v The View that was clicked
     */
    public void submit(View v) {

        // Create three different methods,
        // #1: ContentValues readFromUserInput(void?)
        // #2a: long writeUserInputToDb(ContentValues values)
        // #2b: boolean overwriteUserInputToDb(ContentValues values)
        //     (This is the "update" method)

        // Read values from user input and create a ContentValues map
        ContentValues values = readFromUserInput();

        /* Normal mode: Add a new Checklist Item */
        if (!editMode) {
            // TODO Log the new Checklist Item's information in some way

            // Pass the ContentValues map,
            // write these values to the database,
            // and receive the row ID
            long newRowId = writeUserInputToDb(values);

            // TODO Did you log the (new) row ID in the writeUserInputToDb() method?

            // Close the database helper and finish the activity
            checklistItemDbHelper.close();
            this.finish();
        }

        /* Edit mode: Update an existing Item */
        else {
            // TODO Log the updated Checklist Item's information in some way

            // Pass the ContentValues map,
            // overwrite the existing values in the database,
            // and receive the row ID
            boolean updateSuccessful = overwriteUserInputToDb(values);

            // TODO Log update success or failure in the overwriteUserInputToDb() method

            // Close the database helper and finish the activity
            checklistItemDbHelper.close();
            this.finish();
        }
    }

    /**
     * Clear all of the input fields in the activity's UI
     * @param v
     */
    public void clearFields(View v) {
//        Utility.clearFields((ViewGroup) findViewById(R.id.viewgroup_add_item));
    }

    /**
     * Read user input from the input fields, store the values in a <code>ContentValues</code>
     * object, and return the <code>ContentValues</code> object
     * @return ContentValues A map that stores column names as map keys,
     * and their values as map values
     */
    private ContentValues readFromUserInput() {
        // Read values from user input
        String itemName = editItemName.getText().toString();

        String itemLocationName = editLocationName.getText().toString();
        String itemLocationState = editLocationState.getText().toString();
        String itemLocationCity = editLocationCity.getText().toString();
        String itemLocationZip = editLocationZip.getText().toString();

        String itemCost = editCost.getText().toString();
        String itemCostLow = editCostLow.getText().toString();
        String itemCostHi = editCostHi.getText().toString();

        /*
        TODO Need to define the NumberPickers
        String itemTimeStart = editTimeStart.getText().toString();
        String itemTimeEnd = editTimeEnd.getText().toString();
        String itemTimeDuration = editTimeDuration.getText().toString();
        String itemTimeOther = ""; // Don't forget me
        */

        // TODO Maybe at this exact point, the time should be
        // held in an int (to soon become a SQL Integer type),
        // instead of in a String

        String itemGoogMaps = ""; // Don't forget me

        // Create a map of values, with column names as the keys and data attributes as the values
        ContentValues values = new ContentValues();
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_ITEM_NAME,
                itemName);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_NAME,
                itemLocationName);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_STATE,
                itemLocationState);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_CITY,
                itemLocationCity);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_ZIP,
                itemLocationZip);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST,
                itemCost);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_LOW,
                itemCostLow);
        values.put(ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_HI,
                itemCostHi);

        return values;
    }



    /* Methods that write to the database */

    /**
     * Add a completely new Checklist Item to the database
     * @param values A map that stores column names as map keys, and their values as map values
     * @return <code>long</code> row ID for the newly created Checklist Item in the database,
     * or <code>-1</code> if the database insert was not successful
     */
    private long writeUserInputToDb(ContentValues values) {
        // Get the database in write mode
        SQLiteDatabase db = checklistItemDbHelper.getWritableDatabase();

        // Insert the new row
        long newRowId = db.insert(
                ChecklistItemContract.ChecklistItemEntry.TABLE_NAME,
                null,
                values);

        if (newRowId == -1) {
            /* The database insert was unsuccessful */
//            Log.e(TAG, "New Checklist Item DB insert was unsuccessful!");
            // TODO The logging message may be too long, >23 chars or w/e
        } else {
            /* The database insert was successful */
//            Log.i(TAG, "New Checklist Item row ID, " + newRowId);
            // TODO Check app1/.../AddItemActivity.java, Line #538, History.log() method
            // Figure out why you created the History class, instead of using Android's logging
        }
        return newRowId;
    }

    /**
     * This method returns a boolean because the row ID is unaffected by the update operation.
     * It returns true if the operation was successful (exactly one row was changed),
     * or returns false if the operation was unsuccessful (zero rows were changed, or other failure)
     * @param values A map that stores column names as map keys, and their values as map values
     * @return True if the operation was successful, false otherwise
     */
    private boolean overwriteUserInputToDb(ContentValues values) {
        // Get the database in write mode
        SQLiteDatabase db = checklistItemDbHelper.getWritableDatabase();

        // In app1's updateItemInDatabase() method, you first read existing values from the DB
        // so that you only update values that are different from existing values.
        // This might be an inefficient pre-optimization, after all, it takes time to read from
        // the DB and run the String comparisons,

        // when it might be easier to just overwrite all.

        // TODO Yes, ^that^ is definitely a better idea

        if (rowID == 0) {
            // This situation does not make sense, so log it
//            Log.e(TAG, "Method 'overwriteUserInputToDb()' was called with a row ID of 0");
        }

        // Select by the row ID.
        // Remember that 'rowID' is a member variable,
        // is only set if 'editMode' is true,
        // and is received as an Intent extra
        String[] selectionArg = {String.valueOf(rowID)};

        // Update the row
        int numRowsAffected = db.update(
                ChecklistItemContract.ChecklistItemEntry.TABLE_NAME,
                values,
                ROW_ID_SELECTION,
                selectionArg);

        if (numRowsAffected == 1) {
            /* The database update was successful */
//            Log.i(TAG, "Checklist Item updated in database, row ID " + rowID); TODO
            return true;
        } else if (numRowsAffected == 0) {
            /* The database update was unsuccessful */
//            Log.e(TAG, "Checklist Item update for row ID " + rowID + " was unsuccessful!"); TODO
            return false;
        } else {
            // The number of rows affected was not zero or one,
            // which means that db.update() returned a number greater than one,
            // or a negative number. This is cause for concern!
            // Multiple rows may exist in the database that have the same row ID,
            // or db.update() returned a completely unexpected and unspecified result.
            Log.wtf(TAG, "Checklist Item update returned an unexpected result: " + numRowsAffected + "!");
            return false;
        }
    }



    /* Methods that read from the database XXX*/

    // TODO TODO Just return the Cursor !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // Fuck, I have no idea why 'getExistingItem()' takes a long myRowID, while
    //                          'getExistingItemName()' takes a long myRowID, and a SQLiteDatabase db
    // !!
    // The reason why is because getExistingItem() is a non-static method,
    // and getExistingItemName() is a STATIC METHOD!
    // ...
    // Now what was the reason for making one of these methods static, and the other not static?

    // This method is actually necessary because the input fields have to be filled in with
    // existing values when this Activity is being run in Edit mode

    /**
     * TODO Rewrite me
     * https://developer.android.com/training/basics/data-storage/databases.html#ReadDbRow
     * https://developer.android.com/reference/android/database/Cursor.html
     * https://developer.android.com/reference/android/database/sqlite/SQLiteCursor.html
     * @param myRowID The row ID of the Item to fetch
     * @return The Item that has the row ID <code>rowID</code>, or null if no such Item exists
     */
    private ArrayList<String> getExistingItem(long myRowID) {
        // Access the database
        SQLiteDatabase db = checklistItemDbHelper.getReadableDatabase();

        // Select by the row ID
        String[] selectionArg = { String.valueOf(myRowID) };

        // Query the database and get a cursor
        Cursor c = db.query(
                ChecklistItemContract.ChecklistItemEntry.TABLE_NAME,
                PROJECTION,
                ROW_ID_SELECTION,   // Select by the row ID.
                selectionArg,       // Pass the row ID as the selection argument.
                null,               // Do not group the rows in the result.
                null,               // Set to null because 'groupBy' is also set to null.
                null);              // Do not sort the result, only 1 row is expected.

//        Log.i(TAG, c.getCount() + " row(s) in the cursor, 1 row expected");

        // Return null if a row was not found with the given row ID
        if (c.getCount() == 0) {
//            itemDbHelper.close();
//            Log.d(TAG, "No row found with row ID " + myRowID);
            return null;
        } else if (c.getCount() != 1) {
//            Log.d(TAG, "Query for row ID " + myRowID + " was unsuccessful");
        }

        /*
        // Read the fields from the cursor
        c.moveToFirst();
        String existingItemName =
                c.getString(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_ITEM_NAME));
        String existingItemCategory =
                c.getString(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_CATEGORY));
        int existingItemWeight =
                c.getInt(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_WEIGHT));
        String existingItemWeightUnits =
                c.getString(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_WEIGHT_UNITS));
        int existingItemQuantity =
                c.getInt(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_WEIGHT_IN_GRAMS));
        boolean existingItemIsEssential =
                (c.getInt(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_IS_ESSENTIAL)) == 1);
        String existingItemNotes =
                (c.getString(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_NOTES)));
        String existingItemPhotoFilePath =
                (c.getString(c.getColumnIndexOrThrow(ItemContract.ItemEntry.COLUMN_NAME_PHOTO_FILE_PATH)));

        Item existingItem = new Item(existingItemName,
                existingItemCategory,
                existingItemWeight,
                existingItemWeightUnits,
                existingItemQuantity,
                existingItemIsEssential,
                existingItemNotes,
                existingItemPhotoFilePath);
        return existingItem;
        */
        return null;
    }



    /* Methods that manage the options menu */

    /**
     * Initialize the activity's options menu
     * @param menu The options menu in which to place items
     * @return True if the menu is to be displayed, false otherwise
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_item, menu); TODO
        return true;
    }

    /**
     * Called whenever an item in the options menu is selected
     * @param item The menu item that was selected
     * @return Return false to allow normal menu processing to proceed, true to consume it here
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

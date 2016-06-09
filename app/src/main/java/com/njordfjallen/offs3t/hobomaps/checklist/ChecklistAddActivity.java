import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.njordfjallen.offs3t.hobomaps.R;

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
 * Methods that read from the database
 * Methods that write to the database
 * Methods that manage the options menu
 */
public class ChecklistAddActivity extends AppCompatActivity {

    /* Constants */

    /**
     * Android logging tag
     */
    private static final String TAG = "checklist/ChecklistAddActivity";

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
     * The row ID of the Checklist Item in the database to display
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



    /* Android lifecycle methods */

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
    }



    /* Methods that respond to user clicks */

    /**
     * Respond when the user presses the 'Submit' button.
     * Create the new Item in the database if running in normal mode,
     * and update the Item in the database if running in edit mode.
     * @param v The View that was clicked
     */
    public void submit(View v) {
        // Read the input fields
        String itemName = editItemName.getText().toString();

        // TODO Maybe at this exact point, the time should be
        // held in an int (SQL Integer type), instead of a String
        String itemTimeStart = editTimeStart.getText().toString();
        String itemTimeEnd = editTimeEnd.getText().toString();
        String itemTimeDuration = editTimeDuration.getText().toString();
        String itemTimeOther = ""; // Don't forget me

        String itemLocationName = editLocationName.getText().toString();
        String itemLocationState = editLocationState.getText().toString();
        String itemLocationCity = editLocationCity.getText().toString();
        String itemLocationZip = editLocationZip.getText().toString();

        String itemCost = editCost.getText().toString();
        String itemCostLow = editCostLow.getText().toString();
        String itemCostHi = editCostHi.getText().toString();

        String itemGoogMaps = ""; // Don't forget me

        /* Normal mode: Add a new Checklist Item */
        if (!editMode) {
            // Log the new Checklist Item's toString
            /*
            TODO
            Instead of logging the toString(),
            since we're avoiding object creation,
            log the values using the Strings,
            and maybe create an ArrayList of Strings
            that can be easily dumped to output.
            Remember to remove the ArrayList later
            to avoid object creation of /that/.
             */

            // Write the new Checklist Item to the Checklist Item database and get its row ID
            long rowID = addItemToDatabase("");
            // TODO How do dis?
            // The row ID was already logged in addItemToDatabase()

            // Close the database helper and finish the activity
            checklistItemDbHelper.close();
            this.finish();
        }

        /* Edit mode: Update an existing Item */
        else {
            // Log the updated Item's toString
            // TODO

            // Update the Item that already exists in the database
            updateItemInDatabase(""); // TODO Check that updates complete ok, and that logging is solid

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

    // TODO End current


    /*
    * Methods that read from the database
    * Methods that write to the database
    * Methods that manage the options menu
    */








}

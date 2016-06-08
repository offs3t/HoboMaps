import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    // TODO



    /* Android lifecycle methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);:w
                

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
}

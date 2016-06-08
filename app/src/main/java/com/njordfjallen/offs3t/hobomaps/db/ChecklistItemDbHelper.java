import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * To access the database, instantiate the subclass <code>ChecklistItemDbHelper</code>:
 * <code>ChecklistItemDbHelper mDbHelper = new ChecklistItemDbHelper(getContext());</code>
 *
 * https://en.wikipedia.org/wiki/Boilerplate_code
 */
public class ChecklistItemDbHelper extends SQLiteOpenHelper {

    // If you change the database SCHEMA, you must increment the database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ChecklistItems.db";

    /**
     * SQL statements that create and delete the Pack table.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ChecklistItemContract.ChecklistItemEntry.TABLE_NAME
                    + " (" + ChecklistItemContract.ChecklistItemEntry._ID + " INTEGER PRIMARY KEY,"
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_ITEM_NAME
                    + DbStuff.TEXT_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_HI
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_COST_LOW
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_GOOG_MAPS
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_CITY
                    + DbStuff.TEXT_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_NAME
                    + DbStuff.TEXT_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_STATE
                    + DbStuff.TEXT_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_TYPE
                    + DbStuff.TEXT_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_LOCATION_ZIP
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_DURATION
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_END
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_OTHER
                    + DbStuff.INTEGER_TYPE + DbStuff.COMMA
                    + ChecklistItemContract.ChecklistItemEntry.COLUMN_NAME_TIME_START
                    + DbStuff.INTEGER_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ChecklistItemContract.ChecklistItemEntry.TABLE_NAME;

    public ChecklistItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
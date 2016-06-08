
import android.provider.BaseColumns;

public final class ChecklistItemContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor
    public ChecklistItemContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ChecklistItemEntry implements BaseColumns {
        // Inherited fields _ID and _COUNT
        public static final String TABLE_NAME = "pack";
        public static final String COLUMN_NAME_ITEM_NAME = "name";

        public static final String COLUMN_NAME_TIME_START = "time_start";
        public static final String COLUMN_NAME_TIME_END = "time_end";
        public static final String COLUMN_NAME_TIME_DURACTION = "time_duraction";
        public static final String COLUMN_NAME_TIME_OTHER = "time_other"; // Know I'll need this

        public static final String COLUMN_NAME_LOCATION_NAME = "location_name";
        public static final String COLUMN_NAME_LOCATION_STATE = "location_state";
        public static final String COLUMN_NAME_LOCATION_CITY = "location_city";
        public static final String COLUMN_NAME_LOCATION_ZIP = "location_zip";
        public static final String COLUMN_NAME_LOCATION_TYPE = "location_type";

        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_COST_LOW = "cost_low"; // The frugal option
        public static final String COLUMN_NAME_COST_HI = "cost_hi"; // The ball out option

        public static final String COLUMN_NAME_GOOG_MAPS = "goog_maps";
        // Column added based on the simple assumption that a single String might be enough to link
        // a [ChecklistItemEntry] with a [Goog Maps location-point




    }

}
package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "aerocontrol_user";
    private static final String TABLE_NAME_TICKETS = "flight_tickets";

    private static final int DB_VERSION=1;

    private final SQLiteDatabase database;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void truncateTableTickets() {
    }
}

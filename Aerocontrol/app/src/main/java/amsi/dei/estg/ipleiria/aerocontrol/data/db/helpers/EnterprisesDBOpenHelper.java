package amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;

public class EnterprisesDBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "aerocontrol_enterprises";
    private static final int DB_VERSION = 1;

    public static final String TBL_RESTAURANT ="restaurant";
    public static final String TBL_RESTAURANT_ITEM ="restaurant_item";



    // Campos da tabela restaurant/store(enterprise)
    public static final String COL_ENTERPRISE_ID = "id";
    public static final String COL_ENTERPRISE_NAME = "name";
    public static final String COL_ENTERPRISE_DESCRIPTION = "description";
    public static final String COL_ENTERPRISE_PHONE = "phone";
    public static final String COL_ENTERPRISE_LOGO = "logo";
    public static final String COL_ENTERPRISE_WEBSITE = "website";
    public static final String COL_ENTERPRISE_OPEN_TIME = "open_time";
    public static final String COL_ENTERPRISE_CLOSE_TIME = "close_time";

    // Campos da tabela restaurante item
    public static final String COL_RESTAURANT_ITEM_ID = "id";
    public static final String COL_RESTAURANT_ITEM_ITEM = "item";
    public static final String COL_RESTAURANT_ITEM_IMAGE = "image";
    public static final String COL_RESTAURANT_ITEM_STATE = "state";
    public static final String COL_RESTAURANT_ITEM_RESTAURANT_ID = "restaurant_id";

    // Sql para criar a tabela restaurante
    private static final String CREATE_TBL_RESTAURANT =
            "CREATE TABLE " + TBL_RESTAURANT + "( " +
                    COL_ENTERPRISE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COL_ENTERPRISE_NAME + " TEXT NOT NULL," +
                    COL_ENTERPRISE_DESCRIPTION + " TEXT NOT NULL," +
                    COL_ENTERPRISE_PHONE + " TEXT NOT NULL, " +
                    COL_ENTERPRISE_LOGO + " TEXT," +
                    COL_ENTERPRISE_WEBSITE + " TEXT," +
                    COL_ENTERPRISE_OPEN_TIME + " TEXT," +
                    COL_ENTERPRISE_CLOSE_TIME + " TEXT" +
                    ");";

    // Sql para criar a tabela restaurant item
    private static final String CREATE_TBL_RESTAURANT_ITEM =
            "CREATE TABLE " + TBL_RESTAURANT_ITEM + "( " +
                    COL_RESTAURANT_ITEM_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    COL_RESTAURANT_ITEM_ITEM + " TEXT NOT NULL," +
                    COL_RESTAURANT_ITEM_IMAGE + " TEXT ," +
                    COL_RESTAURANT_ITEM_STATE + " BOOLEAN NOT NULL, " +
                    COL_RESTAURANT_ITEM_RESTAURANT_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + COL_RESTAURANT_ITEM_RESTAURANT_ID + ") REFERENCES " + TBL_RESTAURANT + "(" + COL_ENTERPRISE_ID + ")" +
                    ");";

    private final SQLiteDatabase database;

    public EnterprisesDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_RESTAURANT);
        db.execSQL(CREATE_TBL_RESTAURANT_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RESTAURANT_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RESTAURANT);
        this.onCreate(db);
    }
}

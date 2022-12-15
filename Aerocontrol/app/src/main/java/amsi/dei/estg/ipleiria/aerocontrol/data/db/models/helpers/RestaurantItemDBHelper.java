package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;

public class RestaurantItemDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="aerocontrol";
    private static final String TABLE_NAME="restaurant_items";
    private static final int DB_VERSION=1;

    public static final String ID = "id";
    public static final String ITEM = "item";
    public static final String IMAGE = "image";
    public static final String STATE = "state";
    public static final String RESTAURANT_ID = "restaurant_id";

    private final SQLiteDatabase database;

    public RestaurantItemDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRestaurantItemsTable = "CREATE TABLE " + TABLE_NAME +
                "( " + ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ITEM + " TEXT NOT NULL," +
                IMAGE + " TEXT NOT NULL," +
                STATE + " INTEGER NOT NULL, " +
                RESTAURANT_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)"+");";
        db.execSQL(createRestaurantItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void createItem (RestaurantItem item){
        ContentValues values = new ContentValues();
        values.put(ID, item.getId());
        values.put(ITEM, item.getItem());
        values.put(IMAGE, item.getImage());
        if (item.getState()) values.put(STATE,1);
        else values.put(STATE,0);
        values.put(RESTAURANT_ID,item.getRestaurant_id());
        this.database.insert(TABLE_NAME, null, values);
    }

    public ArrayList<RestaurantItem> readItems(int restaurant_id){
        ArrayList<RestaurantItem> items = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + RESTAURANT_ID + " == " +  restaurant_id +";", null);
        if(cursor.moveToFirst()){
            do{
                items.add(new RestaurantItem(cursor.getInt(0),
                        cursor.getInt(1) != 0,  // Verifica se é 0 ou 1
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            }while(cursor.moveToNext());
        }
        return items;
    }

    public void truncateTable(){
        database.execSQL("delete from "+ TABLE_NAME);
    }
}

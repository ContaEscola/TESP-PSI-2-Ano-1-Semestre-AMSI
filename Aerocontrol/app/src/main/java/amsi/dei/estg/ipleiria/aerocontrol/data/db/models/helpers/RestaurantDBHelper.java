package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="aerocontrol";
    private static final String TABLE_NAME="restaurant";
    private static final int DB_VERSION=1;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PHONE = "phone";
    public static final String LOGO = "logo";
    public static final String WEBSITE = "website";
    public static final String OPEN_TIME = "opentime";
    public static final String CLOSE_TIME = "closetime";

    private final SQLiteDatabase database;

    public RestaurantDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRestaurantsTable = "CREATE TABLE " + TABLE_NAME +
                "( " + ID + " INTEGER PRIMARY KEY NOT NULL, " +
                NAME + " TEXT NOT NULL," +
                DESCRIPTION + " TEXT NOT NULL," +
                PHONE + " TEXT NOT NULL, " +
                LOGO + " TEXT," +
                WEBSITE + " TEXT," +
                OPEN_TIME + " TEXT," +
                CLOSE_TIME + " TEXT" +");";
        db.execSQL(createRestaurantsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void createRestaurant (Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put(ID, restaurant.getId());
        values.put(NAME, restaurant.getName());
        values.put(DESCRIPTION, restaurant.getDescription());
        values.put(PHONE,restaurant.getPhone());
        if (restaurant.getLogo() != null) values.put(LOGO,restaurant.getLogo());
        if (restaurant.getWebsite() != null) values.put(WEBSITE, restaurant.getWebsite());
        if (restaurant.getOpenTime() != null) values.put(OPEN_TIME, restaurant.getOpenTime());
        if (restaurant.getCloseTime() != null) values.put(CLOSE_TIME, restaurant.getCloseTime());
        this.database.insert(TABLE_NAME, null, values);
    }

    public ArrayList<Restaurant> readRestaurants(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do{
                restaurants.add(new Restaurant(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            }while(cursor.moveToNext());
        }
        return restaurants;
    }

    public void truncateTable(){
        database.execSQL("delete from "+ TABLE_NAME);
    }
}

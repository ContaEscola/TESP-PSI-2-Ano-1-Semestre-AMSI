package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;

public class EnterprisesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="aerocontrol_enterprises";
    private static final String TABLE_NAME_RESTAURANTS="restaurant";
    private static final String TABLE_NAME_ITEMS="restaurant_items";

    private static final int DB_VERSION=1;

    // Campos da tabela Restaurante
    public static final String ITEMS_ID = "id";
    public static final String ITEMS_ITEM = "item";
    public static final String ITEMS_IMAGE = "image";
    public static final String ITEMS_STATE = "state";
    public static final String ITEMS_RESTAURANT_ID = "restaurant_id";

    // Campos da tabela Store
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PHONE = "phone";
    public static final String LOGO = "logo";
    public static final String WEBSITE = "website";
    public static final String OPEN_TIME = "opentime";
    public static final String CLOSE_TIME = "closetime";

    private final SQLiteDatabase database;

    public EnterprisesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRestaurantsTable = "CREATE TABLE " + TABLE_NAME_RESTAURANTS +
                "( " + ID + " INTEGER PRIMARY KEY NOT NULL, " +
                NAME + " TEXT NOT NULL," +
                DESCRIPTION + " TEXT NOT NULL," +
                PHONE + " TEXT NOT NULL, " +
                LOGO + " TEXT," +
                WEBSITE + " TEXT," +
                OPEN_TIME + " TEXT," +
                CLOSE_TIME + " TEXT" +");";

        String createRestaurantItemsTable = "CREATE TABLE " + TABLE_NAME_ITEMS +
                "( " + ITEMS_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ITEMS_ITEM + " TEXT NOT NULL," +
                ITEMS_IMAGE + " TEXT NOT NULL," +
                ITEMS_STATE + " INTEGER NOT NULL, " +
                ITEMS_RESTAURANT_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)"+");";

        db.execSQL(createRestaurantsTable);
        db.execSQL(createRestaurantItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESTAURANTS);
        this.onCreate(db);
    }

    /**
     * Cria um restaurante na BD local
     * @param restaurant Restaurante a criar
     */
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
        this.database.insert(TABLE_NAME_RESTAURANTS, null, values);
    }

    /**
     * Lê os restaurantes da BD
     * @return Devolve todos os restaurantes que estão na BD local
     */
    public ArrayList<Restaurant> readRestaurants(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_RESTAURANTS, null);
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

    /**
     * Dá truncate à tabela dos restaurantes
     */
    public void truncateTableRestaurants(){
        database.execSQL("delete from "+ TABLE_NAME_RESTAURANTS);
    }

    /**
     * Cria um item de um restaurante na BD local
     * @param item Item a criar
     */
    public void createItem (RestaurantItem item){
        ContentValues values = new ContentValues();
        values.put(ID, item.getId());
        values.put(ITEMS_ITEM, item.getItem());
        values.put(ITEMS_IMAGE, item.getImage());
        if (item.getState()) values.put(ITEMS_STATE,1);
        else values.put(ITEMS_STATE,0);
        values.put(ITEMS_RESTAURANT_ID,item.getRestaurant_id());
        this.database.insert(TABLE_NAME_ITEMS, null, values);
    }

    /**
     * Lê os itens do restaurantes da BD.
     * @param restaurant_id Id do restaurante.
     * @return Devolve uma lista de itens do restaurante, o menu do restaurante.
     */
    public ArrayList<RestaurantItem> readItems(int restaurant_id){
        ArrayList<RestaurantItem> items = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + TABLE_NAME_ITEMS +
                " WHERE " + ITEMS_RESTAURANT_ID + " == " +  restaurant_id +";", null);
        if(cursor.moveToFirst()){
            do{
                items.add(new RestaurantItem(cursor.getInt(0),
                        cursor.getInt(3) != 0,  // Verifica se é 0 ou 1
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(4)));
            }while(cursor.moveToNext());
        }
        return items;
    }

    /**
     * Dá truncate à tabela dos itens do restaurante
     */
    public void truncateTableItems(){
        database.execSQL("delete from "+ TABLE_NAME_ITEMS);
    }
}

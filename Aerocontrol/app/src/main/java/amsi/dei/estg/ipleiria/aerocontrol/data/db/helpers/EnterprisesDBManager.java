package amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;

public class EnterprisesDBManager {


    private static EnterprisesDBManager instance = null;
    private static EnterprisesDBOpenHelper dbOpenHelper = null;

    private Context context;
    private final SQLiteDatabase database;

    private EnterprisesDBManager(Context context) {
        this.context = context;
        if (this.dbOpenHelper == null)
            this.dbOpenHelper = new EnterprisesDBOpenHelper(context);
        this.database = dbOpenHelper.getWritableDatabase();
    }

    public static synchronized EnterprisesDBManager getInstance(Context context) {
        if (instance == null)
            instance = new EnterprisesDBManager(context);

        return instance;
    }

    private ContentValues convertRestaurantToContentValues(Restaurant restaurant) {
        ContentValues values = new ContentValues();

        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_ID, restaurant.getId());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_NAME, restaurant.getName());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_DESCRIPTION, restaurant.getDescription());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_PHONE, restaurant.getPhone());
        if (restaurant.getLogo() != null)
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_LOGO, restaurant.getLogo());
        if (restaurant.getWebsite() != null)
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_WEBSITE, restaurant.getWebsite());
        if (restaurant.getOpenTime().equals(ApiConfig.API_CUSTOM_NULL))
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_OPEN_TIME, restaurant.getOpenTime());
        if (restaurant.getCloseTime().equals(ApiConfig.API_CUSTOM_NULL))
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_CLOSE_TIME, restaurant.getCloseTime());

        return values;
    }

    /**
     * Cria um restaurante na BD local
     *
     * @param restaurant Restaurante a criar
     */
    public void createRestaurant(Restaurant restaurant) {
        ContentValues values = convertRestaurantToContentValues(restaurant);

        database.insert(EnterprisesDBOpenHelper.TBL_RESTAURANT, null, values);
    }

    /**
     * Lê os restaurantes da BD
     *
     * @return Devolve todos os restaurantes que estão na BD local
     */
    public ArrayList<Restaurant> readRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EnterprisesDBOpenHelper.TBL_RESTAURANT, null);

        if (cursor.moveToFirst()) {
            do {
                restaurants.add(new Restaurant(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        return restaurants;
    }

    /**
     * Dá truncate à tabela dos restaurantes
     */
    public void truncateTableRestaurants() {
        truncateTableRestaurantItems();
        database.execSQL("DELETE FROM " + EnterprisesDBOpenHelper.TBL_RESTAURANT);
    }

    private ContentValues convertRestaurantItemToContentValues(RestaurantItem restaurantItem) {
        ContentValues values = new ContentValues();

        values.put(EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_ID, restaurantItem.getId());
        values.put(EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_ITEM, restaurantItem.getItem());
        if (restaurantItem.getImage() != null)
            values.put(EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_IMAGE, restaurantItem.getImage());
        values.put(EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_STATE, restaurantItem.getState());
        values.put(EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_RESTAURANT_ID, restaurantItem.getRestaurantId());

        return values;
    }

    /**
     * Cria um item de um restaurante na BD local
     *
     * @param item Item a criar
     */
    public void createItem(RestaurantItem item) {
        ContentValues values = convertRestaurantItemToContentValues(item);

        database.insert(EnterprisesDBOpenHelper.TBL_RESTAURANT_ITEM, null, values);
    }

    /**
     * Lê os itens do restaurantes da BD.
     *
     * @param restaurant_id Id do restaurante.
     * @return Devolve uma lista de itens do restaurante, o menu do restaurante.
     */
    public ArrayList<RestaurantItem> readRestaurantItems(int restaurant_id) {
        ArrayList<RestaurantItem> items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * " +
                "FROM " + EnterprisesDBOpenHelper.TBL_RESTAURANT_ITEM + " " +
                "WHERE " + EnterprisesDBOpenHelper.COL_RESTAURANT_ITEM_RESTAURANT_ID + " == " + restaurant_id + ";", null);

        if (cursor.moveToFirst()) {
            do {
                items.add(new RestaurantItem(cursor.getInt(0),
                        cursor.getInt(3) != 0,  // Verifica se é 0 ou 1
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        return items;
    }

    /**
     * Dá truncate à tabela dos itens do restaurante
     */
    public void truncateTableRestaurantItems() {
        database.execSQL("DELETE FROM " + EnterprisesDBOpenHelper.TBL_RESTAURANT_ITEM);
    }


    private ContentValues convertStoreToContentValues(Store store) {
        ContentValues values = new ContentValues();

        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_ID, store.getId());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_NAME, store.getName());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_DESCRIPTION, store.getDescription());
        values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_PHONE, store.getPhone());
        if (store.getLogo() != null)
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_LOGO, store.getLogo());
        if (store.getWebsite() != null)
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_WEBSITE, store.getWebsite());
        if (store.getOpenTime().equals(ApiConfig.API_CUSTOM_NULL))
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_OPEN_TIME, store.getOpenTime());
        if (store.getCloseTime().equals(ApiConfig.API_CUSTOM_NULL))
            values.put(EnterprisesDBOpenHelper.COL_ENTERPRISE_CLOSE_TIME, store.getCloseTime());

        return values;
    }

    /**
     * Cria uma loja na BD local
     * @param store Loja a criar
     */
    public void createStore(Store store){
        ContentValues values = convertStoreToContentValues(store);

        this.database.insert(EnterprisesDBOpenHelper.TBL_STORE, null, values);
    }

    /**
     * Lê as lojas da BD
     * @return Devolve todas as lojas que estão na BD local
     */
    public ArrayList<Store> readStores(){
        ArrayList<Store> stores = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + EnterprisesDBOpenHelper.TBL_STORE, null);

        if(cursor.moveToFirst()){
            do{
                stores.add(new Store(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));
            }while(cursor.moveToNext());
        }
        return stores;
    }

    /**
     * Dá truncate à tabela das lojas
     */
    public void truncateTableStores(){
        database.execSQL("DELETE FROM "+ EnterprisesDBOpenHelper.TBL_STORE);
    }

}

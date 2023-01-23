package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Collections;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.helpers.EnterprisesDBManager;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.RestaurantsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.StoresListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class SingletonEnterprises {

    private static SingletonEnterprises instance = null;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Store> stores;

    private Context context;

    private RequestQueue volleyQueue;

    private RestaurantsListener restaurantsListener;
    private StoresListener storesListener;


    public void setRestaurantsListener(RestaurantsListener restaurantsListener) {
        this.restaurantsListener = restaurantsListener;
    }
	
	public void setStoresListener(StoresListener storesListener) {
        this.storesListener = storesListener;
    }

    private SingletonEnterprises(Context context){
        this.restaurants = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.context = context;

        //https://developer.android.com/training/volley/requestqueue?hl=pt-br
        volleyQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized SingletonEnterprises getInstance(Context context){
        if (instance == null) instance = new SingletonEnterprises(context);
        return instance;
    }

    /**
     *
     * @param id Id do restaurante
     * @return Devolve o restaurante ou null caso não encontre o restaurante.
     */
    public Restaurant getRestaurantById(int id){
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getId() == id) {
                return restaurant;
            }
        }
        return null;
    }

    /**
     *
     * @return Devolve a lista de todos os restaurantes.
     */
    public ArrayList<Restaurant> getRestaurants(){
        return restaurants;
    }

    /**
     * Vai buscar os dados dos restaurantes à API
     * @param context context da atividade ou fragment
     */
    public void getRestaurantsAPI(final Context context){

        // Dados já foram recarregados, para evitar que o utilizador dê spam
        if(restaurants.size() > 0) {
            restaurantsListener.onRefreshList(restaurants);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readRestaurantsDB();
            restaurantsListener.onRefreshList(restaurants);
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.ENDPOINT_RESTAURANTS, null,
            response -> {
                try {
                    Restaurant[] restaurantsArray = Restaurant.parseJsonToRestaurants(response.toString());
                    restaurants.clear();
                    Collections.addAll(restaurants, restaurantsArray);

                    if (restaurantsListener != null && restaurants.size()>0){
                        EnterprisesDBManager.getInstance(context).truncateTableRestaurants();
                        createRestaurantsDB(restaurants);
                        restaurantsListener.onRefreshList(restaurants);
                    }
                } catch (JsonProcessingException e) {
                    Toast.makeText(context, R.string.error_restaurants, Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                Toast.makeText(context, R.string.error_restaurants, Toast.LENGTH_SHORT).show();
                readRestaurantsDB();
        });

        volleyQueue.add(jsonArrayRequest);
    }

    /**
     * Cria todos os restaurantes numa base de dados local para que possam ser visualizados offline
     * @param restaurants lista dos restaurantes a criar na BD
     */
    private void createRestaurantsDB(ArrayList<Restaurant> restaurants){
        for (Restaurant restaurant: restaurants) {
            EnterprisesDBManager.getInstance(context).createRestaurant(restaurant);
            for (RestaurantItem item : restaurant.getMenu()){
                EnterprisesDBManager.getInstance(context).createItem(item);
            }
        }
    }

    /**
     * Vai buscar à BD local todos os restaurantes existentes na mesma.
     */
    private void readRestaurantsDB(){
        restaurants = EnterprisesDBManager.getInstance(context).readRestaurants();
        for (Restaurant restaurant: restaurants){
            restaurant.setMenu(EnterprisesDBManager.getInstance(context).readRestaurantItems(restaurant.getId()));
        }
    }

    /**
     *
     * @param id Id do restaurante.
     * @return Devolve todos os itens do restaurante ou null caso não encontre o restaurante.
     */
    public ArrayList<RestaurantItem> getRestaurantItems(int id){
        Restaurant restaurant = getRestaurantById(id);
        if(restaurant != null)
            return restaurant.getMenu();

        return null;
    }

    /**
     * Adiciona um item a um restaurante
     * @param restaurant O restaurante.
     * @param restaurantItem Item do restaurante a adicionar.
     */
    public void addMenuItem(Restaurant restaurant, RestaurantItem restaurantItem){
        restaurant.addMenuItem(restaurantItem);
    }

    /**
     *
     * @param id Id da Loja
     * @return Devolve a loja ou null caso não encontre a loja.
     */
    public Store getStoreById(int id){
        for(Store store : stores) {
            if(store.getId() == id) {
                return store;
            }
        }
        return null;
    }

    /**
     *
     * @return Devolve a lista de todos as lojas.
     */
    public ArrayList<Store> getStores(){
        return stores;
    }

    /**
     * Vai buscar os dados das lojas à API
     * @param context context da atividade ou fragment
     */
    public void getStoresAPI(final Context context){

        // Dados já foram recarregados, para evitar que o utilizador dê spam
        if(stores.size() > 0) {
            storesListener.onRefreshList(stores);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readStoresDB();
            storesListener.onRefreshList(stores);
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.ENDPOINT_STORES, null,
                response -> {

                    try {
                        Store[] storesArray = Store.parseJsonToStores(response.toString());
                        stores.clear();
                        Collections.addAll(stores, storesArray);

                        if (storesListener != null && stores.size()>0){
                            EnterprisesDBManager.getInstance(context).truncateTableStores();
                            createStoresDB(stores);
                            storesListener.onRefreshList(stores);
                        }
                    } catch (JsonProcessingException e) {
                        Toast.makeText(context, R.string.error_stores, Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            Toast.makeText(context, R.string.error_stores, Toast.LENGTH_SHORT).show();
            readStoresDB();
        });

        volleyQueue.add(jsonArrayRequest);
    }

    /**
     * Cria todas as lojas numa base de dados local para que possam ser visualizados offline
     * @param stores lista das lojas a criar na BD
     */
    private void createStoresDB(ArrayList<Store> stores){
        for (Store store: stores) {
            EnterprisesDBManager.getInstance(context).createStore(store);
        }
    }

    /**
     * Vai buscar à BD local todas as lojas existentes na mesma.
     */
    private void readStoresDB(){
        stores = EnterprisesDBManager.getInstance(context).readStores();
    }

    /**
     *
     * @param store Loja a adicionar.
     */
    public void addStore(Store store){
        stores.add(store);
    }


}
package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers.EnterprisesDBHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListenerRestaurant;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListenerStore;
import amsi.dei.estg.ipleiria.aerocontrol.utils.EnterprisesJsonParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class SingletonEnterprises {
    private static SingletonEnterprises instance = null;

    private EnterprisesListenerRestaurant enterprisesListenerRestaurant;
    private EnterprisesListenerStore enterprisesListenerStore;

    private static EnterprisesDBHelper enterprisesDB;

    private static RequestQueue volleyQueue;

    private ArrayList<Restaurant> restaurants;
    private ArrayList<Store> stores;

    private SingletonEnterprises(Context context){
        restaurants = new ArrayList<>();
        stores = new ArrayList<>();
        enterprisesDB = new EnterprisesDBHelper(context);
    }

    public static synchronized SingletonEnterprises getInstance(Context context){
        volleyQueue = Volley.newRequestQueue(context);

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
            enterprisesListenerRestaurant.onRefreshList(restaurants);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readRestaurantsDB();
            enterprisesListenerRestaurant.onRefreshList(restaurants);
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.RESTAURANTS, null,
            response -> {
                restaurants = EnterprisesJsonParser.parserJsonRestaurants(response);
                if (enterprisesListenerRestaurant != null && restaurants.size()>0){
                    enterprisesDB.truncateTableItems();
                    enterprisesDB.truncateTableRestaurants();
                    createRestaurantsDB(restaurants);
                    enterprisesListenerRestaurant.onRefreshList(restaurants);
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
            enterprisesDB.createRestaurant(restaurant);
            for (RestaurantItem item : restaurant.getMenu()){
                enterprisesDB.createItem(item);
            }
        }
    }

    /**
     * Vai buscar à BD local todos os restaurantes existentes na mesma.
     */
    private void readRestaurantsDB(){
        restaurants = enterprisesDB.readRestaurants();
        for (Restaurant restaurant: restaurants){
            restaurant.setMenu(enterprisesDB.readItems(restaurant.getId()));
        }
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
            enterprisesListenerStore.onRefreshList(stores);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readStoresDB();
            enterprisesListenerStore.onRefreshList(stores);
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.STORES, null,
                response -> {
                    stores = EnterprisesJsonParser.parserJsonStores(response);
                    if (enterprisesListenerStore != null && stores.size()>0){
                        enterprisesDB.truncateTableItems();
                        enterprisesDB.truncateTableStores();
                        createStoresDB(stores);
                        enterprisesListenerStore.onRefreshList(stores);
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
            enterprisesDB.createStore(store);
        }
    }

    /**
     * Vai buscar à BD local todas as lojas existentes na mesma.
     */
    private void readStoresDB(){
        stores = enterprisesDB.readStores();
    }

    public void setEnterprisesListenerRestaurant(EnterprisesListenerRestaurant enterprisesListener) {
        this.enterprisesListenerRestaurant = enterprisesListener;
    }

    public void setEnterprisesListenerStore(EnterprisesListenerStore enterprisesListener) {
        this.enterprisesListenerStore = enterprisesListener;
    }
}
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
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers.RestaurantDBHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.helpers.RestaurantItemDBHelper;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.EnterprisesJsonParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class SingletonEnterprises {
    private static SingletonEnterprises instance = null;

    private EnterprisesListener enterprisesListener;

    private static RestaurantDBHelper restaurantsDB;
    private static RestaurantItemDBHelper restaurantItemDB;

    private static RequestQueue volleyQueue;

    private ArrayList<Restaurant> restaurants;
    private ArrayList<Store> stores;

    private SingletonEnterprises(Context context){
        restaurants = new ArrayList<>();
        stores = new ArrayList<>();
        restaurantsDB = new RestaurantDBHelper(context);
        restaurantItemDB = new RestaurantItemDBHelper(context);
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

        // Dados já foram recarregados, para evitar que o utilizador spamme
        if(restaurants.size() > 0) {
            enterprisesListener.onRefreshList(restaurants);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            readRestaurantsDB();
            System.out.println("Restaurantes:"+restaurants);
            enterprisesListener.onRefreshList(restaurants);
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.RESTAURANTS, null,
            response -> {
                restaurants = EnterprisesJsonParser.parserJsonRestaurants(response);
                if (enterprisesListener != null && restaurants.size()>0){
                    restaurantItemDB.truncateTable();
                    restaurantsDB.truncateTable();
                    createRestaurantsDB(restaurants);
                    enterprisesListener.onRefreshList(restaurants);

                }
            }, error -> {
                Toast.makeText(context, R.string.error_restaurants, Toast.LENGTH_SHORT).show();
        });

        volleyQueue.add(jsonArrayRequest);
    }

    /**
     * Cria todos os restaurantes numa base de dados local para que possam ser visualizados offline
     * @param restaurants lista dos restaurantes a criar na BD
     */
    private void createRestaurantsDB(ArrayList<Restaurant> restaurants){
        for (Restaurant restaurant: restaurants) {
            restaurantsDB.createRestaurant(restaurant);
            for (RestaurantItem item : restaurant.getMenu()){
                restaurantItemDB.createItem(item);
            }
        }
    }

    /**
     * Vai buscar à BD local todos os restaurantes existentes na mesma.
     */
    private void readRestaurantsDB(){
        restaurants = restaurantsDB.readRestaurants();
        for (Restaurant restaurant: restaurants){
            restaurant.setMenu(restaurantItemDB.readItems(restaurant.getId()));
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
     *
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
     *
     * @param store Loja a adicionar.
     */
    public void addStore(Store store){
        stores.add(store);
    }

    public void setEnterprisesListener(EnterprisesListener enterprisesListener) {
        this.enterprisesListener = enterprisesListener;
    }
}
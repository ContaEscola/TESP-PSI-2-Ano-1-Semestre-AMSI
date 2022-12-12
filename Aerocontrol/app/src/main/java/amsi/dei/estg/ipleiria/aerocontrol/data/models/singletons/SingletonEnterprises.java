package amsi.dei.estg.ipleiria.aerocontrol.data.models.singletons;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.models.Store;

public class SingletonEnterprises {
    private static SingletonEnterprises instance = null;

    private ArrayList<Restaurant> restaurants;
    private ArrayList<Store> stores;

    private SingletonEnterprises(){
        restaurants = new ArrayList<>();
        stores = new ArrayList<>();
    }

    public static synchronized SingletonEnterprises getInstance(){
        if (instance == null) instance = new SingletonEnterprises();
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
     *
     * @param restaurant Restaurante a adicionar
     */
    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
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
}
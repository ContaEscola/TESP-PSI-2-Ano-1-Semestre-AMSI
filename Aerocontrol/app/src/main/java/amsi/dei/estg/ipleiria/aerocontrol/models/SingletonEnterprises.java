package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.ArrayList;

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

    public Restaurant getRestaurantById(int id){
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getId() == id) {
                return restaurant;
            }
        }
        return null;
    }

    public ArrayList<Restaurant> getRestaurants(){
        return restaurants;
    }

    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
    }

    public Store getStoreById(int id){
        for(Store store : stores) {
            if(store.getId() == id) {
                return store;
            }
        }
        return null;
    }

    public ArrayList<Store> getStore(){
        return stores;
    }

    public void addStore(Store store){
        stores.add(store);
    }

    public ArrayList<RestaurantItem> getRestaurantItems(int id){
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getId() == id) {
                return restaurant.getMenu();
            }
        }
        return null;
    }

    public void addMenuItem(int id, RestaurantItem restaurantItem){
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getId() == id) {
                restaurant.setMenuItem(restaurantItem);
            }
        }
    }

}
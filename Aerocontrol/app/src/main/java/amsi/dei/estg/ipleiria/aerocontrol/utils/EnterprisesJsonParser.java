package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.RestaurantItem;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;

public class EnterprisesJsonParser {

    /**
     * Converte o arrayJson para uma lista de restaurantes
     * @param jsonArray Array Json que vem da API
     * @return Lista de todos os restaurantes recebidos da API
     */
    public static ArrayList<Restaurant> parserJsonRestaurants(JSONArray jsonArray){
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        if(jsonArray != null){
            try {
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Restaurant restaurant = new Restaurant(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("phone"),
                            jsonObject.getString("logo"),
                            jsonObject.getString("website"),
                            jsonObject.getString("open_time"),
                            jsonObject.getString("close_time"));
                    restaurants.add(restaurant);

                    // Vai buscar o Menu do Restaurante
                    JSONArray menu = jsonObject.getJSONArray("menu");
                    for (int j=0; j < menu.length(); j++){
                        JSONObject menuItem = (JSONObject) menu.get(j);
                        boolean state;
                        if (menuItem.getInt("state") == 0) state = false;
                        else state = true;
                        RestaurantItem item = new RestaurantItem(
                                menuItem.getInt("id"),
                                state,
                                menuItem.getString("item"),
                                menuItem.getString("image"),
                                menuItem.getInt("restaurant_id")
                        );
                        restaurant.addMenuItem(item);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return restaurants;
    }

    public static ArrayList<Store> parserJsonStores(JSONArray jsonArray){
        ArrayList<Store> stores = new ArrayList<>();

        if(jsonArray != null){
            try {
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Store store = new Store(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("phone"),
                            jsonObject.getString("logo"),
                            jsonObject.getString("website"),
                            jsonObject.getString("open_time"),
                            jsonObject.getString("close_time"));
                    stores.add(store);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return stores;
    }

    public static String parserJsonLogin(String response){
        try {
            JSONObject resposta = new JSONObject(response);
            return resposta.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

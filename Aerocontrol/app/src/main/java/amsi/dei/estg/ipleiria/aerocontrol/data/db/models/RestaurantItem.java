package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.utils.StringUtils;

@JsonPropertyOrder({
    "id",
    "item",
    "image",
    "state",
    "restaurant_id"
})
public class RestaurantItem {

    private int id;
    private boolean state;
    private String item;
    private String image;
    private int restaurant_id;

    public RestaurantItem(int id, boolean state, String item, String image, int restaurant_id){
        this.setId(id);
        this.setState(state);
        this.setItem(item);
        this.setImage(image);
        this.setRestaurantId(restaurant_id);
    }

    public RestaurantItem() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("restaurant_id")
    public int getRestaurantId() {
        return restaurant_id;
    }

    @JsonProperty("restaurant_id")
    public void setRestaurantId(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }


    /**
     * @param restaurant O restaurante a que este item está associado
     * @return Devolve o caminho da imagem onde está guardado na Api
     */
    public String getImageAPIPath(Restaurant restaurant) {
        String restaurantPath = restaurant.getUploadAPIPath();
        String itemImagePath = restaurantPath + ApiEndPoint.RESTAURANT_ITEMS_FOLDER + "/" + getImage();
        return itemImagePath;
    }
}

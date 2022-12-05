package amsi.dei.estg.ipleiria.aerocontrol.models;

public class RestaurantItem {

    private int id;
    private int state;
    private int restaurant_id;
    private String item;
    private String image;

    public RestaurantItem(int id, int state, int restaurant_id, String item, String image){
        this.setId(id);
        this.setState(state);
        this.setRestaurant_id(restaurant_id);
        this.setItem(item);
        this.setImage(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
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
}

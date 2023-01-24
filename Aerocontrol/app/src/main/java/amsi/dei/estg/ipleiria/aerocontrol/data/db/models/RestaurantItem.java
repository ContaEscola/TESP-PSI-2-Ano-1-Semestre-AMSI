package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

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
        this.setRestaurant_id(restaurant_id);
    }

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

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}

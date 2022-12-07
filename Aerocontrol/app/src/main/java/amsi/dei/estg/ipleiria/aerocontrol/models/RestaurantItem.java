package amsi.dei.estg.ipleiria.aerocontrol.models;

public class RestaurantItem {

    private int id;
    private int state;
    private String item;
    private String image;

    public RestaurantItem(int id, int state, String item, String image){
        this.setId(id);
        this.setState(state);
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

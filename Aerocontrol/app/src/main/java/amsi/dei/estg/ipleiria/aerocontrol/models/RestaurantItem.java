package amsi.dei.estg.ipleiria.aerocontrol.models;

public class RestaurantItem {

    private int id;
    private boolean state;
    private String item;
    private String image;

    public RestaurantItem(int id, boolean state, String item, String image){
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
}

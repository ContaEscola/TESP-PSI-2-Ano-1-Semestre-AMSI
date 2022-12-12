package amsi.dei.estg.ipleiria.aerocontrol.data.models;

public class LostItem {
    private int id;
    private String description;
    private String state;
    private String image;

    public LostItem(int id, String description, String state, String image){
        this.setId(id);
        this.setDescription(description);
        this.setState(state);
        this.setImage(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

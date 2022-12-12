package amsi.dei.estg.ipleiria.aerocontrol.models;

public class PaymentMethod {
    private int id;
    private int state;
    private String name;

    public PaymentMethod(int id, int state, String name){
        this.setId(id);
        this.setState(state);
        this.setName(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

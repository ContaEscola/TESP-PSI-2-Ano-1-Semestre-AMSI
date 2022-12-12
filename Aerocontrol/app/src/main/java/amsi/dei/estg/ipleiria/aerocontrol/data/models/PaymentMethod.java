package amsi.dei.estg.ipleiria.aerocontrol.data.models;

public class PaymentMethod {
    private int id;
    private boolean state;
    private String name;

    public PaymentMethod(int id, boolean state, String name){
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

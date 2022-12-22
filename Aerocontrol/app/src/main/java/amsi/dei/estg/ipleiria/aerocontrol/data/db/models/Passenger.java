package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class Passenger {
    private int id;
    private String name;
    private String gender;

    public Passenger(int id, String name, String gender){
        this.setId(id);
        this.setName(name);
        this.setGender(gender);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class Passenger {
    private int id;
    private String name;
    private String gender;
    private String seat;
    private boolean extraBaggage;

    public Passenger(int id, String name, String gender, String seat, boolean extraBaggage){
        this.setSeat(seat);
        this.setExtraBaggage(extraBaggage);
        this.setId(id);
        this.setName(name);
        this.setGender(gender);
    }

    public Passenger() {
        this.setExtraBaggage(false);
        this.setGender(User.GENDERS[0]);
        this.setName("");
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public boolean haveExtraBaggage() {
        return extraBaggage;
    }

    public void setExtraBaggage(boolean extraBaggage) {
        this.extraBaggage = extraBaggage;
    }
}

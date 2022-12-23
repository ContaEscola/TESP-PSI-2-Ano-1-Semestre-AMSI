package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class Passenger {
    private int id;
    private String name;
    private String gender;
    private int seat;
    private boolean extraBaggage;

    public Passenger(int id, String name, String gender, int seat, boolean extraBaggage){
        this.setSeat(seat);
        this.setExtraBaggage(extraBaggage);
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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public boolean haveExtraBaggage() {
        return extraBaggage;
    }

    public void setExtraBaggage(boolean extraBaggage) {
        this.extraBaggage = extraBaggage;
    }
}

package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Passenger {
    private int id;
    private String name;
    private String gender;
    private String seat;

    @JsonProperty("extra_baggage")
    private boolean extraBaggage;

    public Passenger(int id, String name, String gender, String seat, boolean extraBaggage){
        this.setSeat(seat);
        this.setExtraBaggage(extraBaggage);
        this.setId(id);
        this.setName(name);
        this.setGender(gender);
    }

    public Passenger() {}

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

    @JsonGetter("extra_baggage")
    public boolean haveExtraBaggage() {
        return extraBaggage;
    }

    @JsonProperty("extra_baggage")
    public void setExtraBaggage(boolean extraBaggage) {
        this.extraBaggage = extraBaggage;
    }
}

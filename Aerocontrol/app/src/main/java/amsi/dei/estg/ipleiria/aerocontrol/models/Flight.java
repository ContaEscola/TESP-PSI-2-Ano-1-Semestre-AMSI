package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.Date;

public class Flight {
    private int id;
    private int discountPercentage;
    private String terminal;
    private String state;
    private String originAirport;
    private String arrivalAirport;
    private Date departureDate;
    private Date arrivalDate;
    private float price;
    private float distance;

    // Avião / Datas estimadas e não estimadas?

    public Flight(int id, int discountPercentage, String terminal, String state, String originAirport,
                  String arrivalAirport, Date departureDate, Date arrivalDate, float price, float distance){
        this.setId(id);
        this.setDiscountPercentage(discountPercentage);
        this.setTerminal(terminal);
        this.setState(state);
        this.setOriginAirport(originAirport);
        this.setArrivalAirport(arrivalAirport);
        this.setDepartureDate(departureDate);
        this.setArrivalDate(arrivalDate);
        this.setPrice(price);
        this.setDistance(distance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}

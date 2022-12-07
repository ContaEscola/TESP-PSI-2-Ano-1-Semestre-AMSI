package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.ArrayList;
import java.util.Date;

public class FlightTicket {

    private int id;
    private int flight_id;
    private String paymentMethod;
    private float price;
    private Date purchaseDate;
    private boolean checkIn;

    private ArrayList<Passenger> passengers;

    public FlightTicket(int id, int flight_id, String paymentMethod, float price, Date purchaseDate, boolean checkIn){
        this.setId(id);
        this.setFlight_id(flight_id);
        this.setPaymentMethod(paymentMethod);
        this.setPrice(price);
        this.setPurchaseDate(purchaseDate);
        this.setCheckIn(checkIn);
        passengers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }
}

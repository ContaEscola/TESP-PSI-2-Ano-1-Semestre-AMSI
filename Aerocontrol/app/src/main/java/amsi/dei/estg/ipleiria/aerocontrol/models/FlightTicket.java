package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.ArrayList;
import java.util.Date;

public class FlightTicket {

    private int flightTicket_id;
    private int flight_id;
    private int paymentMethod_id;
    private float price;
    private Date purchaseDate;
    private boolean checkIn;

    private ArrayList<Passenger> passengers;

    public FlightTicket(int flightTicket_id, int flight_id, int paymentMethod_id, float price, Date purchaseDate, boolean checkIn){
        this.setFlightTicket_id(flightTicket_id);
        this.setFlight_id(flight_id);
        this.setPaymentMethod_id(paymentMethod_id);
        this.setPrice(price);
        this.setPurchaseDate(purchaseDate);
        this.setCheckIn(checkIn);
        passengers = new ArrayList<>();
    }

    public int getFlightTicket_id() {
        return flightTicket_id;
    }

    public void setFlightTicket_id(int flightTicket_id) {
        this.flightTicket_id = flightTicket_id;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public int getPaymentMethod_id() {
        return paymentMethod_id;
    }

    public void setPaymentMethod_id(int paymentMethod_id) {
        this.paymentMethod_id = paymentMethod_id;
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

    public void setPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }
}

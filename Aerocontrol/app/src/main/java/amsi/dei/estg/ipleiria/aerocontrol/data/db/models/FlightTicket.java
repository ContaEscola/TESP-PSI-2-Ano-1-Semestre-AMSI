package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import java.util.ArrayList;
import java.util.Date;

public class FlightTicket {

    private int id;
    private String paymentMethod;
    private double price;
    private Date purchaseDate;
    private boolean checkIn;

    private ArrayList<Passenger> passengers;

    public FlightTicket(int id, String paymentMethod, double price, Date purchaseDate, boolean checkIn){
        this.setId(id);
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

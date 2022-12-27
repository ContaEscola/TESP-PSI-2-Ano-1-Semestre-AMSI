package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import java.util.ArrayList;

public class FlightTicket {

    private int id;
    private String paymentMethod;
    private String flightState;
    private String flightOrigin;
    private String flightArrival;
    private String flightDepartureTime;
    private String flightArrivalTime;
    private String terminal;
    private double originalPrice;
    private double pricePaid;
    private String flightDate;
    private String purchaseDate;
    private float distance;
    private boolean checkIn;

    private ArrayList<Passenger> passengers;

    public FlightTicket(int id, String paymentMethod, String flightState, String flightOrigin, String flightArrival,
                        String flightDepartureTime, String flightArrivalTime, String terminal, double originalPrice,
                        double pricePaid, String flightDate, String purchaseDate, float distance, boolean checkIn){
        this.setId(id);
        this.setPaymentMethod(paymentMethod);
        this.setFlightState(flightState);
        this.setFlightOrigin(flightOrigin);
        this.setFlightArrival(flightArrival);
        this.setFlightDepartureTime(flightDepartureTime);
        this.setFlightArrivalTime(flightArrivalTime);
        this.setTerminal(terminal);
        this.setOriginalPrice(originalPrice);
        this.setPricePaid(pricePaid);
        this.setFlightDate(flightDate);
        this.setPurchaseDate(purchaseDate);
        this.setDistance(distance);
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

    public String getFlightState() {
        return flightState;
    }

    public void setFlightState(String flightState) {
        this.flightState = flightState;
    }

    public String getFlightOrigin() {
        return flightOrigin;
    }

    public void setFlightOrigin(String flightOrigin) {
        this.flightOrigin = flightOrigin;
    }

    public String getFlightArrival() {
        return flightArrival;
    }

    public void setFlightArrival(String flightArrival) {
        this.flightArrival = flightArrival;
    }

    public String getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(String flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public String getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(String flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
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

    public void setPassengers(ArrayList<Passenger> passengers){
        this.passengers = passengers;
    }
}

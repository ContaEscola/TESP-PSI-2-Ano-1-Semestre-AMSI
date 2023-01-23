package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;

import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;

public class FlightTicket {

    private int id;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private String flightState;
    private String flightOrigin;
    private String flightArrival;

    @JsonProperty("flightOriginTime")
    private String flightDepartureTime;

    private String flightArrivalTime;
    private String terminal;
    private double originalPrice;
    private double paidPrice;
    private String flightDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ApiConfig.API_DATE_TIME_FORMAT)
    private Date purchaseDate;

    private float distance;

    @JsonProperty("checkin")
    private boolean checkIn;

    private ArrayList<Passenger> passengers;

    public FlightTicket(int id, String paymentMethod, String flightState, String flightOrigin, String flightArrival,
                        String flightDepartureTime, String flightArrivalTime, String terminal, double originalPrice,
                        double paidPrice, String flightDate, Date purchaseDate, float distance, boolean checkIn){
        this.setId(id);
        this.setPaymentMethod(paymentMethod);
        this.setFlightState(flightState);
        this.setFlightOrigin(flightOrigin);
        this.setFlightArrival(flightArrival);
        this.setFlightDepartureTime(flightDepartureTime);
        this.setFlightArrivalTime(flightArrivalTime);
        this.setTerminal(terminal);
        this.setOriginalPrice(originalPrice);
        this.setPaidPrice(paidPrice);
        this.setFlightDate(flightDate);
        this.setPurchaseDate(purchaseDate);
        this.setDistance(distance);
        this.setCheckIn(checkIn);

        passengers = new ArrayList<>();
    }

    public FlightTicket() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("payment_method")
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

    @JsonProperty("flightOriginTime")
    public String getFlightDepartureTime() {
        return flightDepartureTime;
    }

    @JsonProperty("flightOriginTime")
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

    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @JsonGetter("checkin")
    public boolean isCheckIn() {
        return checkIn;
    }

    @JsonProperty("checkin")
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

    //    Converter array de json para array de objetos
    //    https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
    public static FlightTicket[] parseJsonToFlightTickets(String jsonString) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, FlightTicket[].class);

    }
}

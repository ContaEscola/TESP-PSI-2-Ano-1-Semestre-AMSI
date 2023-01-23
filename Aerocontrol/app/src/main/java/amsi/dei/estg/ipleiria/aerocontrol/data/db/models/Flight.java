package amsi.dei.estg.ipleiria.aerocontrol.data.db.models;

public class Flight {
    private int id;
    private int discountPercentage;
    private String terminal;
    private String state;
    private String originAirport;
    private String arrivalAirport;
    private String estimatedDepartureDate;
    private String estimatedArrivalDate;
    private String departureDate;
    private String arrivalDate;
    private double price;
    private float distance;
    private String passengersLeft;


    public Flight(int id, int discountPercentage, String terminal, String state, String originAirport,
                  String arrivalAirport, String departureDate, String arrivalDate,String estimatedDepartureDate,
                  String estimatedArrivalDate, double price, float distance, String passengersLeft){
        this.setId(id);
        this.setDiscountPercentage(discountPercentage);
        this.setTerminal(terminal);
        this.setState(state);
        this.setOriginAirport(originAirport);
        this.setArrivalAirport(arrivalAirport);
        this.setDepartureDate(departureDate);
        this.setArrivalDate(arrivalDate);
        this.setEstimatedDepartureDate(estimatedDepartureDate);
        this.setEstimatedArrivalDate(estimatedArrivalDate);
        this.setPrice(price);
        this.setDistance(distance);
        this.setPassengersLeft(passengersLeft);
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getEstimatedDepartureDate() {
        return estimatedDepartureDate;
    }

    public void setEstimatedDepartureDate(String estimatedDepartureDate) {
        this.estimatedDepartureDate = estimatedDepartureDate;
    }

    public String getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(String estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public String getPassengersLeft() {
        return passengersLeft;
    }

    public void setPassengersLeft(String passengersLeft) {
        this.passengersLeft = passengersLeft;
    }
}

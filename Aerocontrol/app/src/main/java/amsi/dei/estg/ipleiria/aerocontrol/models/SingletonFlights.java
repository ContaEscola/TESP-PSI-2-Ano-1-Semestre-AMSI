package amsi.dei.estg.ipleiria.aerocontrol.models;

import java.util.ArrayList;

public class SingletonFlights {

    private static SingletonFlights instance = null;

    private ArrayList<Flight> flights;
    private ArrayList<PaymentMethod> paymentMethods;

    private SingletonFlights(){
        flights = new ArrayList<>();
        paymentMethods = new ArrayList<>();
    }

    public static synchronized SingletonFlights getInstance(){
        if (instance == null) instance = new SingletonFlights();
        return instance;
    }

    /*
        Flight Data
     */

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Flight getFlightById(int id){
        for(Flight flight : flights) {
            if(flight.getId() == id) {
                return flight;
            }
        }
        return null;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    /*
        PaymentMethods Data
     */

    public ArrayList<PaymentMethod> getPaymentMethod() {
        return paymentMethods;
    }

    public PaymentMethod getPaymentMethodById(int id){
        for(PaymentMethod paymentMethod : paymentMethods) {
            if(paymentMethod.getId() == id) {
                return paymentMethod;
            }
        }
        return null;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
    }
}

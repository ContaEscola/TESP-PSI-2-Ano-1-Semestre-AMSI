package amsi.dei.estg.ipleiria.aerocontrol.models.singletons;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.models.PaymentMethod;

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


    /**
     *
     * @return Devolve a lista de todos os voos.
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

    /**
     *
     * @param flight Voo a adicionar
     */
    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }


    /**
     *
     * @return Devolve a lista de todos os métodos de pagamento.
     */
    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    /**
     *
     * @param id Id do método de pagamento.
     * @return Devolve o método de pagamento.
     */
    public PaymentMethod getPaymentMethodById(int id){
        for(PaymentMethod paymentMethod : paymentMethods) {
            if(paymentMethod.getId() == id) {
                return paymentMethod;
            }
        }
        return null;
    }

    /**
     *
     * @param paymentMethod Método de pagamento a adicionar.
     */
    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
    }
}

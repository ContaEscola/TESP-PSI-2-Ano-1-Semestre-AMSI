package amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.PaymentMethod;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiEndPoint;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.AirportsListener;
import amsi.dei.estg.ipleiria.aerocontrol.utils.FlightsJsonParser;
import amsi.dei.estg.ipleiria.aerocontrol.utils.NetworkUtils;

public class SingletonFlights {

    private static SingletonFlights instance = null;

    private AirportsListener airportsListener;

    private static RequestQueue volleyQueue;

    private ArrayList<Airport> airports;
    private ArrayList<Flight> flights;
    private ArrayList<PaymentMethod> paymentMethods;

    private SingletonFlights(){
        flights = new ArrayList<>();
        paymentMethods = new ArrayList<>();
        airports = new ArrayList<>();
    }

    public static synchronized SingletonFlights getInstance(Context context){
        volleyQueue = Volley.newRequestQueue(context);
        if (instance == null) instance = new SingletonFlights();
        return instance;
    }

    /**
     * Obtém os aeroportos da API
     * @param context Contexto da atividade ou fragment
     */
    public void getAirportsAPI(Context context){
        // Dados já foram recarregados, para evitar que o utilizador dê spam
        if(airports.size() > 0) {
            airportsListener.onRefreshAirports(airports);
            return;
        }

        // Caso não haja internet
        if (!NetworkUtils.isConnectedInternet(context)){
            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            return;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiEndPoint.AIRPORTS, null,
                response -> {
                    airports = FlightsJsonParser.parserJsonAirports(response);
                    if (airportsListener != null && airports.size() > 0){
                        airportsListener.onRefreshAirports(airports);
                    }
                }, error -> {
            Toast.makeText(context, R.string.error_airports, Toast.LENGTH_SHORT).show();
        });

        volleyQueue.add(jsonArrayRequest);
    }

    /**
     *
     * @return Devolve a lista de todos os voos.
     */
    public ArrayList<Flight> getFlights() {
        return flights;
    }


    /**
     *
     * @param id Id do voo
     * @return Devolve o voo ou null caso não encontre o voo.
     */
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

    public void setAirportsListener(AirportsListener airportsListener){
        this.airportsListener = airportsListener;
    }
}

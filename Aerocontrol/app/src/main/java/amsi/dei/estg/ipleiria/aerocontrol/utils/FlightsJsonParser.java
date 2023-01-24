package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.PaymentMethod;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;

public class FlightsJsonParser {
    public static ArrayList<Airport> parserJsonAirports(JSONArray response) {
        ArrayList<Airport> airports = new ArrayList<>();
        if(response != null){
            try {
                for (int i = 0; i < response.length(); i++){
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    Airport airport = new Airport(
                            jsonObject.getInt("id"),
                            jsonObject.getString("country"),
                            jsonObject.getString("city"),
                            jsonObject.getString("name"),
                            jsonObject.getString("website"));
                    airports.add(airport);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return airports;
    }

    public static ArrayList<Flight> parserJsonFlights(String response, boolean flightsGo, Context context) {

        ArrayList<Flight> flightsList = new ArrayList<>();
        if(response != null){

            try {
                JSONObject responseArray = new JSONObject(response);
                JSONArray flights;
                if (flightsGo)
                    flights = responseArray.getJSONArray("flightsGo");
                else flights = responseArray.getJSONArray("flightsBack");
                for (int i = 0; i < flights.length(); i++){
                    JSONObject jsonObject = (JSONObject) flights.get(i);
                    int originAirport_id = jsonObject.getInt("origin_airport_id");
                    int arrival_Airport_id = jsonObject.getInt("arrival_airport_id");
                    Flight flight = new Flight(
                            jsonObject.getInt("id"),
                            jsonObject.getInt("discount_percentage"),
                            jsonObject.getString("terminal"),
                            jsonObject.getString("state"),
                            SingletonFlights.getInstance(context).getAirportById(originAirport_id).getCity(),
                            SingletonFlights.getInstance(context).getAirportById(arrival_Airport_id).getCity(),
                            jsonObject.getString("departure_date"),
                            jsonObject.getString("arrival_date"),
                            jsonObject.getString("estimated_departure_date"),
                            jsonObject.getString("estimated_arrival_date"),
                            jsonObject.getDouble("price"),
                            (float) jsonObject.getDouble("distance"),
                            jsonObject.getString("passengers_left"));
                    flightsList.add(flight);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return flightsList;
    }

    public static ArrayList<PaymentMethod> parserPaymentMethods(JSONArray response) {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();

        if(response != null){
            try {
                for (int i = 0; i < response.length(); i++){
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    PaymentMethod paymentMethod = new PaymentMethod(
                            jsonObject.getInt("id"),
                            jsonObject.getInt("state") == 1,
                            jsonObject.getString("name"));
                    paymentMethods.add(paymentMethod);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return paymentMethods;
    }
}

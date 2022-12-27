package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;

public class UserJsonParser {
    public static ArrayList<FlightTicket> parserJsonTickets(JSONArray jsonArray) {
        ArrayList<FlightTicket> tickets = new ArrayList<>();

        if(jsonArray != null){
            try {
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    int checkInInt = jsonObject.getInt("checkin");
                    boolean checkIn = checkInInt == 1;
                    FlightTicket ticket = new FlightTicket(
                            jsonObject.getInt("id"),
                            jsonObject.getString("payment_method"),
                            jsonObject.getString("flightState"),
                            jsonObject.getString("flightOrigin"),
                            jsonObject.getString("flightArrival"),
                            jsonObject.getString("flightOriginTime"),
                            jsonObject.getString("flightArrivalTime"),
                            jsonObject.getString("terminal"),
                            jsonObject.getDouble("originalPrice"),
                            jsonObject.getDouble("paidPrice"),
                            jsonObject.getString("flightDate"),
                            jsonObject.getString("purchaseDate"),
                            (float) jsonObject.getDouble("distance"),
                            checkIn);
                    tickets.add(ticket);

                    JSONArray passengers = jsonObject.getJSONArray("passengers");
                    for (int j=0; j < passengers.length(); j++){
                        JSONObject jsonObjectPassenger = (JSONObject) passengers.get(j);
                        boolean extraBaggage = jsonObjectPassenger.getInt("extra_baggage") == 1;
                        Passenger passenger = new Passenger(
                                jsonObjectPassenger.getInt("id"),
                                jsonObjectPassenger.getString("name"),
                                jsonObjectPassenger.getString("gender"),
                                jsonObjectPassenger.getInt("seat"),
                                extraBaggage);
                        ticket.addPassenger(passenger);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tickets;
    }
}

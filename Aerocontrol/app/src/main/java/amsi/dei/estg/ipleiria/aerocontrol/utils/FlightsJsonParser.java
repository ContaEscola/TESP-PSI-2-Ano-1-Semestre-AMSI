package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;

public class FlightsJsonParser {
    public static ArrayList<Airport> parserJsonAirports(JSONArray response) {
        ArrayList<Airport> airports = new ArrayList<>();
        if(response != null){
            try {
                for (int i = 0; i < response.length(); i++){
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    System.out.println(jsonObject);
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
}

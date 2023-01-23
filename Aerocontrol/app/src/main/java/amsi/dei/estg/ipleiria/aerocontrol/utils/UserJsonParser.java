package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.SupportTicket;

public class UserJsonParser {

    public static ArrayList<SupportTicket> parserJsonSupportTickets(JSONArray jsonArray) {
        ArrayList<SupportTicket> supportTickets = new ArrayList<>();

        if(jsonArray != null){
            try {
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    SupportTicket supportTicket = new SupportTicket(
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("state"));
                    supportTickets.add(supportTicket);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return supportTickets;
    }
}

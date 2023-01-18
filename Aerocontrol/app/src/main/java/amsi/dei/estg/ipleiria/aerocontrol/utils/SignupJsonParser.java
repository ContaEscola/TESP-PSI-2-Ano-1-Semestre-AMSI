package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupJsonParser {
    public static String parserJsonSignup(String response){
        try {
            JSONObject message = new JSONObject(response);
            return message.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

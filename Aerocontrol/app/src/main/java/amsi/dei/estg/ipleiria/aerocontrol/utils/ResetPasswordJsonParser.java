package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordJsonParser {
    public static String parserJsonResetPassword(String response){
        try {
            JSONObject message = new JSONObject(response);
            return message.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

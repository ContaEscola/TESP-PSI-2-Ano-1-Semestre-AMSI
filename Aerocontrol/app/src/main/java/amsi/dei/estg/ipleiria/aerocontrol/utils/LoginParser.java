package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginParser {
    public static String parserJsonLogin(String response){
        try {
            JSONObject resposta = new JSONObject(response);
            if (resposta.getString("success").equals("true")){
                return resposta.getString("token");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

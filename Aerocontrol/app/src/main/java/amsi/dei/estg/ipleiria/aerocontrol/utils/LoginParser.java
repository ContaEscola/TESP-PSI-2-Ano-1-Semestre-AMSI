package amsi.dei.estg.ipleiria.aerocontrol.utils;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;

public class LoginParser {
    public static User parserJsonLogin(String response){
        try {
            JSONObject user = new JSONObject(response);
            return new User(
                    user.getInt("id"),
                    user.getString("username"),
                    user.getString("token"),
                    null,
                    user.getString("first_name"),
                    user.getString("last_name"),
                    user.getString("gender"),
                    user.getString("country"),
                    user.getString("city"),
                    user.getString("email"),
                    user.getString("phone"),
                    user.getString("phone_country_code"),
                    user.getString("birthdate")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

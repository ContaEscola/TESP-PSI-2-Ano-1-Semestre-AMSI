package amsi.dei.estg.ipleiria.aerocontrol.data.network.models;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class LoginRequest {

    public static String getHttpBasicAuthToken (String username, String password){

        String userAndPass = username + ":" + password;
        byte[] data = userAndPass.getBytes(StandardCharsets.UTF_8);
        String authorizationToken  = "Basic " + Base64.encodeToString(data,Base64.DEFAULT);
        return authorizationToken;
    }
}

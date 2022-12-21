package amsi.dei.estg.ipleiria.aerocontrol.listeners;


import android.content.Context;

public interface LoginListener {
    void onValidateLogin(final String token, final String username, final Context context);
}

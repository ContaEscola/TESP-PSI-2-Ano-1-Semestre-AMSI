package amsi.dei.estg.ipleiria.aerocontrol.listeners;


import android.content.Context;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.User;

public interface LoginListener {
    void onValidateLogin(final User user, final Context context);
}

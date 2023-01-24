package amsi.dei.estg.ipleiria.aerocontrol.utils;

import android.app.Application;
import android.content.res.Resources;

public class App extends Application {

    private static Resources res;


    @Override
    public void onCreate() {
        super.onCreate();
        res = getResources();
    }

    public static Resources getRes() {
        return res;
    }
}
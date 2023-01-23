package amsi.dei.estg.ipleiria.aerocontrol.listeners;


import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;

public interface StoresListener {
    void onRefreshList(ArrayList<Store> stores);
}

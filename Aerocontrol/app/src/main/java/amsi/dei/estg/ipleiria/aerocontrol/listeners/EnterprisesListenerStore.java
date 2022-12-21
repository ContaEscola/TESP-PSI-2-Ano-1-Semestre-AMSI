package amsi.dei.estg.ipleiria.aerocontrol.listeners;


import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;

public interface EnterprisesListenerStore {
    void onRefreshList(ArrayList<Store> stores);
}

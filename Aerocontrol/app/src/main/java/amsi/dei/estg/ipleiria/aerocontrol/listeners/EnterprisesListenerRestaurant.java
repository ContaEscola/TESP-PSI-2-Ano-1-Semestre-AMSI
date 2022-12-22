package amsi.dei.estg.ipleiria.aerocontrol.listeners;


import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;

public interface EnterprisesListenerRestaurant {
    void onRefreshList(ArrayList<Restaurant> restaurants);
}

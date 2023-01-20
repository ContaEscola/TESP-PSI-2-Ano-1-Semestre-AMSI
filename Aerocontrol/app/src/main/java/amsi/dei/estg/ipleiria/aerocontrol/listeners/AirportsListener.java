package amsi.dei.estg.ipleiria.aerocontrol.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;

public interface AirportsListener {
    void onRefreshAirports(ArrayList<Airport> airports);
}

package amsi.dei.estg.ipleiria.aerocontrol.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;

public interface FlightsListener {
    void onRefreshFlights(ArrayList<Flight> flightsGo, ArrayList<Flight> flightsBack);
}

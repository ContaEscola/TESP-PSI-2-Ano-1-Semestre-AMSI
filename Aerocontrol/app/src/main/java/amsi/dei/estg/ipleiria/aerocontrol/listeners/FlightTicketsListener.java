package amsi.dei.estg.ipleiria.aerocontrol.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;

public interface FlightTicketsListener {
    void onRefreshList(ArrayList<FlightTicket> tickets);
}

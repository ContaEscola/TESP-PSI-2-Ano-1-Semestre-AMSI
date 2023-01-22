package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.FlightTicket;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.FlightTicketsActivity;

public class RecyclerViewFlightTicketsAdapter extends RecyclerView.Adapter<RecyclerViewFlightTicketsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FlightTicket> tickets;

    public RecyclerViewFlightTicketsAdapter(Context context, ArrayList<FlightTicket> tickets){
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlightTicket ticket = tickets.get(position);
        holder.updateTicket(ticket,context);
    }

    @Override
    public int getItemCount(){
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate, tvState, tvDeparture, tvArrival, tvDepartureTime, tvArrivalTime;
        Button btDetails;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.tvDate = view.findViewById(R.id.Ticket_Tv_Date);
            this.tvState = view.findViewById(R.id.Ticket_Tv_State);
            this.tvDeparture = view.findViewById(R.id.Ticket_Tv_DepartureCity);
            this.tvArrival = view.findViewById(R.id.Ticket_Tv_ArrivalCity);
            this.tvDepartureTime = view.findViewById(R.id.Ticket_Tv_DepartureTime);
            this.tvArrivalTime = view.findViewById(R.id.Ticket_Tv_ArrivalTime);
            this.btDetails = view.findViewById(R.id.Ticket_Bt_Details);
        }

        public void updateTicket(FlightTicket ticket, Context context){
            this.tvDate.setText(ticket.getFlightDate());
            this.tvState.setText(ticket.getFlightState());
            this.tvDeparture.setText(ticket.getFlightOrigin());
            this.tvArrival.setText(ticket.getFlightArrival());
            this.tvDepartureTime.setText(ticket.getFlightDepartureTime());
            this.tvArrivalTime.setText(ticket.getFlightArrivalTime());
            this.btDetails.setOnClickListener(view -> {
                ((FlightTicketsActivity) context).showTicketDetails(ticket.getId());
            });
        }
    }

}

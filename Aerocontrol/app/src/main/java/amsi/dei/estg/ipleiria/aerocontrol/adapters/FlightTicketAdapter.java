package amsi.dei.estg.ipleiria.aerocontrol.adapters;

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
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.TicketsActivity;

public class FlightTicketAdapter extends RecyclerView.Adapter<FlightTicketAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FlightTicket> tickets;

    public FlightTicketAdapter(Context context, ArrayList<FlightTicket> tickets){
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public FlightTicketAdapter.ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket,parent,false);
        return new ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightTicketAdapter.ViewHolderList holder, int position) {
        FlightTicket ticket = tickets.get(position);
        holder.updateTicket(ticket,context);
    }

    @Override
    public int getItemCount(){
        return tickets.size();
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

        TextView tvDate, tvState, tvDeparture, tvArrival, tvDepartureTime, tvArrivalTime;
        Button btDetails;

        public ViewHolderList(@NonNull View view) {
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
                ((TicketsActivity) context).showTicketDetails(ticket.getId());
            });
        }
    }

}

package amsi.dei.estg.ipleiria.aerocontrol.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;

public class RecyclerViewFlightTicketPassengersAdapter extends RecyclerView.Adapter<RecyclerViewFlightTicketPassengersAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Passenger> passengers;

    public RecyclerViewFlightTicketPassengersAdapter(Context context, ArrayList<Passenger> passengers){
        this.context = context;
        this.passengers = passengers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_passengers,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Passenger passenger = passengers.get(position);
        holder.updatePassenger(passenger);
    }

    @Override
    public int getItemCount(){
        return passengers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvGender, tvSeat;
        CheckBox cbBaggage;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.tvName = view.findViewById(R.id.Passenger_Tv_Name);
            this.tvGender = view.findViewById(R.id.Passenger_Tv_Gender);
            this.tvSeat = view.findViewById(R.id.Passenger_Tv_Seat);
            this.cbBaggage = view.findViewById(R.id.Passenger_Cb_Baggage);
        }

        public void updatePassenger(Passenger passenger){
            this.tvName.setText(passenger.getName());
            this.tvGender.setText(passenger.getGender());
            this.tvSeat.setText(String.valueOf(passenger.getSeat()));
            this.cbBaggage.setChecked(passenger.haveExtraBaggage());

        }
    }
}

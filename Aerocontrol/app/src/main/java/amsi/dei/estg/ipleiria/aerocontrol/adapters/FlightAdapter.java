package amsi.dei.estg.ipleiria.aerocontrol.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.FlightSearchResultsActivity;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolderList> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Flight> flights;
    boolean two_way_trip;

    private Flight selectedFlight = null;
    private int currentClickedPosition = -1;

    public FlightAdapter(Context context, ArrayList<Flight> flights){
        this.context = context;
        this.flights = flights;
    }

    @NonNull
    @Override
    public FlightAdapter.ViewHolderList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight_search_result,parent,false);
        return new FlightAdapter.ViewHolderList(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolderList holder, int position) {
        Flight flight = getFlights().get(position);

        if (holder.getAdapterPosition() == currentClickedPosition) {
            holder.btReserve.setBackgroundResource(R.drawable.button_secondary_outline_background_states);
            holder.btReserve.setTextColor(context.getResources().getColor(R.color.moss_green_400));
            holder.btReserve.setText(R.string.reserved);
        } else {
            holder.btReserve.setBackgroundResource(R.drawable.button_primary);
            holder.btReserve.setText(R.string.reserve);
        }

        holder.btReserve.setOnClickListener(v -> {
            currentClickedPosition = holder.getAdapterPosition();
            FlightSearchResultsActivity.handleButtonClick(context, flight);
        });

        holder.updateFlight(flight,context);
    }

    @Override
    public int getItemCount(){
        return getFlights().size();
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setSelectedFlight(Flight flight) {
        this.selectedFlight = flight;
    }

    public Flight getSelectedFlight(){
        return selectedFlight;
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

        TextView tvPriceDiscount, tvSeats, tvDate, tvDepartureTime, tvArrivalTime, tvOrigin, tvDestiny, tvPrice;
        Button btReserve;

        public ViewHolderList(@NonNull View view) {
            super(view);
            this.tvPriceDiscount = view.findViewById(R.id.FlightSearchResults_Tv_PriceDiscount);
            this.tvSeats = view.findViewById(R.id.FlightSearchResults_Tv_Seats);
            this.tvDate = view.findViewById(R.id.FlightSearchResults_Tv_Date);
            this.tvDepartureTime = view.findViewById(R.id.FlightSearchResults_Tv_DepartureTime);
            this.tvArrivalTime = view.findViewById(R.id.FlightSearchResults_Tv_ArrivalTime);
            this.tvOrigin = view.findViewById(R.id.FlightSearchResults_Tv_DepartureCity);
            this.tvDestiny = view.findViewById(R.id.FlightSearchResults_Tv_ArrivalCity);
            this.tvPrice = view.findViewById(R.id.FlightSearchResults_Tv_Price);
            this.btReserve = view.findViewById(R.id.FlightSearchResults_Bt_Reserve);
        }

        public void updateFlight(Flight flight, Context context){
            this.tvPrice.setText(context.getString(R.string.euro_symbol,flight.getPrice()));
            if (flight.getDiscountPercentage() == 0)
                this.tvPriceDiscount.setVisibility(View.GONE);
            else {
                double priceDiscount = flight.getPrice() - (flight.getPrice() * flight.getDiscountPercentage());
                this.tvPrice.setText(context.getString(R.string.euro_symbol,priceDiscount));
                this.tvPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.orange_red));
                this.tvPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputTimeFormat = new SimpleDateFormat("HH:mm");
            try {
                Date departureDate = inputFormat.parse(flight.getEstimatedDepartureDate());
                Date arrivalDate = inputFormat.parse(flight.getEstimatedArrivalDate());
                String departureTime = outputTimeFormat.format(departureDate);
                String arrivalTime = outputTimeFormat.format(arrivalDate);
                String departureDateString = outputDateFormat.format(departureDate);

                this.tvDate.setText(departureDateString);
                this.tvDepartureTime.setText(departureTime);
                this.tvArrivalTime.setText(arrivalTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.tvSeats.setText(flight.getPassengersLeft());
            this.tvOrigin.setText(flight.getOriginAirport());
            this.tvDestiny.setText(flight.getArrivalAirport());
        }
    }

}

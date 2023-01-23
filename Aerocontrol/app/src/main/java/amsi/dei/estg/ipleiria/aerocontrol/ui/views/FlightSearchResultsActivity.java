package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewFlightsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityFlightSearchResultsBinding;

public class FlightSearchResultsActivity extends AppCompatActivity {

    public static final String TWO_WAY_TRIP = "two_way_trip";
    public static final String NUM_PASSENGERS = "num_passengers";

    private static ActivityFlightSearchResultsBinding binding;

    private static boolean two_way_trip;
    private static int num_passengers;

    private static RecyclerViewFlightsAdapter adapterGo;
    private static RecyclerViewFlightsAdapter adapterBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightSearchResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.FlightSearchResultsToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        two_way_trip = getIntent().getBooleanExtra(TWO_WAY_TRIP,false);
        num_passengers = getIntent().getIntExtra(NUM_PASSENGERS,-1);

        setFlightsIntoRV();
    }

    public void setFlightsIntoRV(){
        if (two_way_trip && (SingletonFlights.getInstance(this).getFlightsBack() == null || SingletonFlights.getInstance(this).getFlightsGo() == null)){
            Toast.makeText(this, R.string.no_flights_found, Toast.LENGTH_SHORT).show();
        } else if (!two_way_trip && SingletonFlights.getInstance(this).getFlightsGo() == null){
            Toast.makeText(this, R.string.no_flights_found, Toast.LENGTH_SHORT).show();
        } else {
            binding.FlightSearchResultsRvFlightsGo.setLayoutManager(new LinearLayoutManager(this));
            adapterGo = new RecyclerViewFlightsAdapter(this, SingletonFlights.getInstance(this).getFlightsGo());
            binding.FlightSearchResultsRvFlightsGo.setAdapter(adapterGo);
            binding.FlightSearchResultsRvFlightsGo.setItemAnimator(new DefaultItemAnimator());
            binding.FlightSearchResultsTvOriginArrivalGo.setText(SingletonFlights.getInstance(this).getFlightsGo().get(0).getOriginAirport() + " - " + SingletonFlights.getInstance(this).getFlightsGo().get(0).getArrivalAirport());
            binding.FlightSearchResultsTvResultsGo.setText(SingletonFlights.getInstance(this).getFlightsGo().size() + " Resultados encontrados");

            if (two_way_trip) {
                binding.FlightSearchResultsRvFlightsBack.setLayoutManager(new LinearLayoutManager(this));
                adapterBack = new RecyclerViewFlightsAdapter(this, SingletonFlights.getInstance(this).getFlightsBack());
                binding.FlightSearchResultsRvFlightsBack.setAdapter(adapterBack);
                binding.FlightSearchResultsRvFlightsBack.setItemAnimator(new DefaultItemAnimator());
                binding.FlightSearchResultsTvOriginArrivalBack.setText(SingletonFlights.getInstance(this).getFlightsBack().get(0).getOriginAirport() + " - " + SingletonFlights.getInstance(this).getFlightsBack().get(0).getArrivalAirport());
                binding.FlightSearchResultsTvResultsBack.setText(SingletonFlights.getInstance(this).getFlightsBack().size() + " Resultados encontrados");
            } else {
                binding.FlightSearchResultsTvOriginArrivalBack.setVisibility(View.GONE);
                binding.FlightSearchResultsRvFlightsBack.setVisibility(View.GONE);
                binding.FlightSearchResultsTvResultsBack.setVisibility(View.GONE);

            }
        }
    }

    public static void handleButtonClick(Context context, Flight flight) {
        if (two_way_trip) {
            if (adapterGo.getFlights().contains(flight)) {
                adapterGo.setSelectedFlight(flight);
                binding.FlightSearchResultsRvFlightsGo.setAdapter(adapterGo);
                binding.FlightSearchResultsRvFlightsGo.setItemAnimator(new DefaultItemAnimator());
            } else {
                adapterBack.setSelectedFlight(flight);
                binding.FlightSearchResultsRvFlightsBack.setAdapter(adapterBack);
                binding.FlightSearchResultsRvFlightsBack.setItemAnimator(new DefaultItemAnimator());
            }

            if (adapterGo.getSelectedFlight() != null && adapterBack.getSelectedFlight() != null) {
                Intent intent = new Intent(context, FlightTicketPassengersActivity.class);
                intent.putExtra(FlightTicketPassengersActivity.FLIGHT_GO_ID, adapterGo.getSelectedFlight().getId());
                intent.putExtra(FlightTicketPassengersActivity.TWO_WAY_TRIP, true);
                intent.putExtra(FlightTicketPassengersActivity.FLIGHT_BACK_ID, adapterBack.getSelectedFlight().getId());
                intent.putExtra(FlightTicketPassengersActivity.NUM_PASSENGERS, num_passengers);
                context.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(context, FlightTicketPassengersActivity.class);
            intent.putExtra(FlightTicketPassengersActivity.FLIGHT_GO_ID, flight.getId());
            intent.putExtra(FlightTicketPassengersActivity.TWO_WAY_TRIP, false);
            intent.putExtra(FlightTicketPassengersActivity.NUM_PASSENGERS, num_passengers);
            context.startActivity(intent);
        }
    }

}
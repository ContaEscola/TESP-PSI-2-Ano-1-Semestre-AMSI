package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.FlightTicketPassengersAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Passenger;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityFlightTicketPassengersBinding;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;

public class FlightTicketPassengersActivity extends AppCompatActivity {

    public static final String FLIGHT_GO_ID = "flight_go_id";
    public static final String FLIGHT_BACK_ID = "flight_back_id";
    public static final String NUM_PASSENGERS = "num_passengers";
    public static final String TWO_WAY_TRIP = "two_way_trip";

    private int num_passengers, flightGoId, flightBackId;
    private boolean two_way_trip;

    private ActivityFlightTicketPassengersBinding binding;

    private FlightTicketPassengersAdapter adapter;

    private ArrayList<Passenger> passengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightTicketPassengersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.FlightTicketPassengersToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        two_way_trip = getIntent().getBooleanExtra(TWO_WAY_TRIP,false);
        flightGoId = getIntent().getIntExtra(FLIGHT_GO_ID,-1);
        if (two_way_trip) flightBackId = getIntent().getIntExtra(FLIGHT_BACK_ID,-1);
        num_passengers = getIntent().getIntExtra(NUM_PASSENGERS,-1);
        passengers = new ArrayList<>();

        createPassengersArrayList();
        setAdapter();

        binding.FlightTicketPassengersBtNext.setOnClickListener(v -> {
            if (isValid()){
                goToPaymentMethod();
            } else Toast.makeText(this, R.string.error_passenger_name, Toast.LENGTH_SHORT).show();
        });
    }

    private void goToPaymentMethod() {
        SingletonFlights.getInstance(this).setTicketPassengers(passengers);
        Intent intent = new Intent(this, PaymentMethodActivity.class);
        intent.putExtra(PaymentMethodActivity.TWO_WAY_TRIP, two_way_trip);
        intent.putExtra(PaymentMethodActivity.FLIGHT_GO_ID, flightGoId);
        if (two_way_trip) intent.putExtra(PaymentMethodActivity.FLIGHT_BACK_ID, flightBackId);
        intent.putExtra(PaymentMethodActivity.NUM_PASSENGERS, num_passengers);
        startActivity(intent);
    }

    private boolean isValid() {
        boolean valid = true;
        for (Passenger passenger: passengers) {
            if (!Validations.validateRequired(passenger.getName()) || !Validations.validateMaxLength(50, passenger.getName()))
                valid = false;
        }
        return valid;
    }

    private void createPassengersArrayList() {
        for (int i = 0; i < num_passengers; i++)
            passengers.add(new Passenger());
    }

    private void setAdapter() {
        binding.FlightTicketPassengersRvPassengers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FlightTicketPassengersAdapter(this, passengers);
        binding.FlightTicketPassengersRvPassengers.setAdapter(adapter);
        binding.FlightTicketPassengersRvPassengers.setItemAnimator(new DefaultItemAnimator());
    }
}
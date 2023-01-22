package amsi.dei.estg.ipleiria.aerocontrol.ui.views.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Flight;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentFlightSearchBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.AirportsListener;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.FlightsListener;
import amsi.dei.estg.ipleiria.aerocontrol.ui.views.Activities.FlightSearchResultsActivity;
import amsi.dei.estg.ipleiria.aerocontrol.utils.Validations;

public class FlightSearchFragment extends Fragment implements AirportsListener, FlightsListener {

    FragmentFlightSearchBinding binding;

    private boolean two_way_trip = true;

    private Calendar calendar;

    public FlightSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFlightSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        SingletonFlights.getInstance(getContext()).setAirportsListener(this);
        SingletonFlights.getInstance(getContext()).setFlightsListener(this);
        SingletonFlights.getInstance(getContext()).getAirportsAPI(getContext());

        binding.FlightSearchBtOneWay.setOnClickListener(v -> {
            if (!two_way_trip) return;
            two_way_trip = false;
            binding.FlightSearchBtRoundTrip.setTextColor(getResources().getColor(R.color.blue_400));
            binding.FlightSearchBtOneWay.setTextColor(getResources().getColor(R.color.white));
            binding.FlightSearchBtOneWay.setBackgroundResource(R.drawable.button_pill_primary);
            binding.FlightSearchBtRoundTrip.setBackgroundResource(R.drawable.button_pill_primary_outline_background_states);
            binding.FlightSearchEtComeBackDate.setVisibility(View.GONE);
            binding.FlightSearchTvComeBackDate.setVisibility(View.GONE);
        });

        binding.FlightSearchBtRoundTrip.setOnClickListener(v -> {
            if (two_way_trip) return;
            two_way_trip = true;
            binding.FlightSearchBtRoundTrip.setTextColor(getResources().getColor(R.color.white));
            binding.FlightSearchBtOneWay.setTextColor(getResources().getColor(R.color.blue_400));
            binding.FlightSearchBtRoundTrip.setBackgroundResource(R.drawable.button_pill_primary);
            binding.FlightSearchBtOneWay.setBackgroundResource(R.drawable.button_pill_primary_outline_background_states);
            binding.FlightSearchEtComeBackDate.setVisibility(View.VISIBLE);
            binding.FlightSearchTvComeBackDate.setVisibility(View.VISIBLE);
        });

        startCalender();
        binding.FlightSearchEtDepartureDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker(true);
        });
        binding.FlightSearchEtComeBackDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker(false);
        });

        binding.FlightSearchBtSearch.setOnClickListener(v -> {
            if (validate())
                searchFlights();
            else Toast.makeText(getContext(), "Preencha os campos!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void searchFlights() {
        SingletonFlights.getInstance(getContext()).getFlightsAPI(
                getContext(),
                two_way_trip,
                String.valueOf(binding.FlightSearchACTVOrigin.getText()).trim(),
                String.valueOf(binding.FlightSearchACTVDestiny.getText()).trim(),
                String.valueOf(binding.FlightSearchEtPassengers.getText()).trim(),
                String.valueOf(binding.FlightSearchEtDepartureDate.getText()).trim(),
                two_way_trip ? String.valueOf(binding.FlightSearchEtComeBackDate.getText()).trim() : null,    // Se for ida e volta manda Data senão manda null
                0   // Primeira vez a procurar os voos
        );
    }

    private boolean validate() {
        boolean validate = true;
        if (!Validations.validateRequired(String.valueOf(binding.FlightSearchACTVOrigin.getText())))
            validate = false;
        if (!Validations.validateRequired(String.valueOf(binding.FlightSearchACTVDestiny.getText())))
            validate = false;
        if (!Validations.validateRequired(String.valueOf(binding.FlightSearchEtPassengers.getText())))
            validate = false;
        return validate;
    }

    private void startCalender(){
        Date date = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date);
    }

    /**
     * Mostra o datePicker
     * @param departureDate True se o user clicar na data de origem, false se clicar na data de chegada
     */
    private void showDatePicker(final boolean departureDate){
        DatePickerDialog picker = new DatePickerDialog(getContext(), R.style.customDatePickerStyle,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String date_dd_mm_yyyy = getString(R.string.date_format,dayOfMonth,monthOfYear+1,year);
                    if (departureDate)
                        binding.FlightSearchEtDepartureDate.setText(date_dd_mm_yyyy);
                    else binding.FlightSearchEtComeBackDate.setText(date_dd_mm_yyyy);
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        picker.setTitle(getString(R.string.insert_birth_date));

        picker.setOnShowListener(dialog -> {
            Button postiveBt = picker.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeBt = picker.getButton(DialogInterface.BUTTON_NEGATIVE);
            postiveBt.setTextColor(getResources().getColor(R.color.black_400));
            postiveBt.setText(R.string.confirm);
            negativeBt.setTextColor(getResources().getColor(R.color.black_400));
            negativeBt.setText(R.string.cancel);
        });

        picker.show();
    }

    @Override
    public void onRefreshAirports(ArrayList<Airport> airports) {
        ArrayAdapter<Airport> adapter = new ArrayAdapter<>(getContext(), R.layout.gender_list_item, airports);
        binding.FlightSearchACTVOrigin.setAdapter(adapter);
        binding.FlightSearchACTVDestiny.setAdapter(adapter);
    }

    @Override
    public void onRefreshFlights(ArrayList<Flight> flightsGo, ArrayList<Flight> flightsBack) {
        Intent intent = new Intent(getContext(), FlightSearchResultsActivity.class);
        intent.putExtra(FlightSearchResultsActivity.TWO_WAY_TRIP, two_way_trip);
        int numPassengers = Integer.parseInt(binding.FlightSearchEtPassengers.getText().toString());
        intent.putExtra(FlightSearchResultsActivity.NUM_PASSENGERS, numPassengers);
        startActivity(intent);
    }
}
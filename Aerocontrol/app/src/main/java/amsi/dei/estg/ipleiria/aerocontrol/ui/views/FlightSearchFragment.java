package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentFlightSearchBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.AirportsListener;

public class FlightSearchFragment extends Fragment implements AirportsListener {

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
        SingletonFlights.getInstance(getContext()).getAirportsAPI(getContext());

        binding.FlightSearchBtOneWay.setOnClickListener(v -> {
            if (!two_way_trip) return;
            two_way_trip = false;
            binding.FlightSearchBtRoundTrip.setTextColor(getResources().getColor(R.color.blue_400));
            binding.FlightSearchBtOneWay.setTextColor(getResources().getColor(R.color.white));
            binding.FlightSearchBtOneWay.setBackgroundResource(R.drawable.button_pill_primary);
            binding.FlightSearchBtRoundTrip.setBackgroundResource(R.drawable.button_pill_primary_outline_background_states);
        });

        binding.FlightSearchBtRoundTrip.setOnClickListener(v -> {
            if (two_way_trip) return;
            two_way_trip = true;
            binding.FlightSearchBtRoundTrip.setTextColor(getResources().getColor(R.color.white));
            binding.FlightSearchBtOneWay.setTextColor(getResources().getColor(R.color.blue_400));
            binding.FlightSearchBtRoundTrip.setBackgroundResource(R.drawable.button_pill_primary);
            binding.FlightSearchBtOneWay.setBackgroundResource(R.drawable.button_pill_primary_outline_background_states);
        });

        startCalender();
        binding.FlightSearchEtDepartureDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker(true);
        });
        binding.FlightSearchEtArrivalDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) showDatePicker(false);
        });

        return view;
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
                    else binding.FlightSearchEtArrivalDate.setText(date_dd_mm_yyyy);
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
}
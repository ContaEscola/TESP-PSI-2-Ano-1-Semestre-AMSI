package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Airport;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonFlights;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentFlightSearchBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.AirportsListener;

public class FlightSearchFragment extends Fragment implements AirportsListener {

    FragmentFlightSearchBinding binding;

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

        return view;
    }

    @Override
    public void onRefreshAirports(ArrayList<Airport> airports) {
        ArrayAdapter<Airport> adapter = new ArrayAdapter<>(getContext(), R.layout.gender_list_item, airports);
        binding.FlightSearchACTVOrigin.setAdapter(adapter);
        binding.FlightSearchACTVDestiny.setAdapter(adapter);
        binding.FlightSearchACTVOrigin.setOnItemClickListener((parent, view, position, id) -> {

        });
    }
}
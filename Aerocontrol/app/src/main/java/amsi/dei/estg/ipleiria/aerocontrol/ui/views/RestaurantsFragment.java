package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewRestaurantsAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.RestaurantsListener;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentRestaurantsBinding;

public class RestaurantsFragment extends Fragment implements RestaurantsListener {

    private RecyclerViewRestaurantsAdapter adapter;

    private FragmentRestaurantsBinding binding;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRestaurantsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        
        binding.RestaurantsRvRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.RestaurantsRvRestaurants.setItemAnimator(new DefaultItemAnimator());

        SingletonEnterprises.getInstance(this.getContext()).setRestaurantsListener(this);
        SingletonEnterprises.getInstance(this.getContext()).getRestaurantsAPI(this.getContext());

        binding.RestaurantsEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, binding.RestaurantsRvRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));binding.RestaurantsRvRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Restaurant> restaurants) {
        adapter = new RecyclerViewRestaurantsAdapter(this.getContext(), restaurants);
        binding.RestaurantsRvRestaurants.setAdapter(adapter);
    }
}
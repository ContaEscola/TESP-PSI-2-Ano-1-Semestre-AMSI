package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.aerocontrol.adapters.RestaurantsListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.FragmentRestaurantsBinding;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListenerRestaurant;

public class RestaurantsFragment extends Fragment implements EnterprisesListenerRestaurant {

    private RestaurantsListAdapter adapter;

    private FragmentRestaurantsBinding binding;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRestaurantsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.RestaurantsRvRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonEnterprises.getInstance(this.getContext()).setEnterprisesListenerRestaurant(this);
        SingletonEnterprises.getInstance(this.getContext()).getRestaurantsAPI(this.getContext());

        binding.RestaurantsEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Restaurant> auxRestaurants = new ArrayList<>();
                for (Restaurant restaurant: SingletonEnterprises.getInstance(getContext()).getRestaurants()) {
                    if (restaurant.getName().toUpperCase().contains(s.toString().toUpperCase())){
                        auxRestaurants.add(restaurant);
                    }
                }
                binding.RestaurantsRvRestaurants.setAdapter(new RestaurantsListAdapter(getContext(),auxRestaurants));
                binding.RestaurantsRvRestaurants.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Restaurant> restaurants) {
        adapter = new RestaurantsListAdapter(this.getContext(),SingletonEnterprises.getInstance(getContext()).getRestaurants());
        binding.RestaurantsRvRestaurants.setAdapter(adapter);
        binding.RestaurantsRvRestaurants.setItemAnimator(new DefaultItemAnimator());
    }
}
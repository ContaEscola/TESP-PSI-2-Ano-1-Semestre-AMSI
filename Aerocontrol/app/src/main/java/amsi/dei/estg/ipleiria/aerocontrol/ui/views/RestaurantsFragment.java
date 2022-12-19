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

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.adapters.RestaurantsListAdapter;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Store;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.listeners.EnterprisesListenerRestaurant;

public class RestaurantsFragment extends Fragment implements EnterprisesListenerRestaurant {

    private RestaurantsListAdapter adapter;
    private RecyclerView recyclerView;

    private EditText tvSearch;

    public RestaurantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        recyclerView = view.findViewById(R.id.Restaurants_Rv_Restaurants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonEnterprises.getInstance(this.getContext()).setEnterprisesListenerRestaurant(this);
        SingletonEnterprises.getInstance(this.getContext()).getRestaurantsAPI(this.getContext());

        tvSearch = view.findViewById(R.id.Restaurants_Et_Search);

        tvSearch.addTextChangedListener(new TextWatcher() {
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
                recyclerView.setAdapter(new RestaurantsListAdapter(getContext(),auxRestaurants));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return view;
    }

    @Override
    public void onRefreshList(ArrayList<Restaurant> restaurants) {
        adapter = new RestaurantsListAdapter(this.getContext(),SingletonEnterprises.getInstance(getContext()).getRestaurants());
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data
    }
}
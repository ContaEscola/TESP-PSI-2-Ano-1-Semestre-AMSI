package amsi.dei.estg.ipleiria.aerocontrol.ui.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import amsi.dei.estg.ipleiria.aerocontrol.R;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.Restaurant;
import amsi.dei.estg.ipleiria.aerocontrol.data.db.models.singletons.SingletonEnterprises;
import amsi.dei.estg.ipleiria.aerocontrol.databinding.ActivityRestaurantDetailsBinding;
import amsi.dei.estg.ipleiria.aerocontrol.data.network.ApiConfig;
import amsi.dei.estg.ipleiria.aerocontrol.ui.adapters.RecyclerViewRestaurantItemsAdapter;

public class RestaurantDetailsActivity extends AppCompatActivity {

    public static final String RESTAURANT_ID = "restaurant_id";

    private ActivityRestaurantDetailsBinding binding;

    private RecyclerViewRestaurantItemsAdapter adapter;

    private Restaurant restaurant;
    private int idRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.RestaurantDetailsToolbar.getRoot());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        getRestaurantIdFromIntent();

    }

    private void initialize() {
        binding.RestaurantDetailsRvMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.RestaurantDetailsRvMenu.setItemAnimator(new DefaultItemAnimator());
    }

    private void getRestaurantIdFromIntent() {
        idRestaurant = getIntent().getIntExtra(RESTAURANT_ID,-1);

        if (idRestaurant != -1){
            restaurant = SingletonEnterprises.getInstance(this).getRestaurantById(idRestaurant);
            setRestaurantInView();
        } else Toast.makeText(this, R.string.error_no_restaurant, Toast.LENGTH_SHORT).show();
    }

    private void setRestaurantInView() {
        if (restaurant.getMenu().size() > 0){
            adapter = new RecyclerViewRestaurantItemsAdapter(this, restaurant);
            binding.RestaurantDetailsRvMenu.setAdapter(adapter);
        } else binding.RestaurantDetailsTvMenu.setText("");

        if (!restaurant.getName().equals("null")) binding.RestaurantDetailsTvName.setText(restaurant.getName());

        if(restaurant.getOpenTime().equals(ApiConfig.API_CUSTOM_NULL) || restaurant.getCloseTime().equals(ApiConfig.API_CUSTOM_NULL) || restaurant.getOpenTime().equals(restaurant.getCloseTime()))
            binding.RestaurantDetailsTvSchedule.setText(R.string.open_anytime);
        else
            binding.RestaurantDetailsTvSchedule.setText(restaurant.getOpenTime() + " - " + restaurant.getCloseTime());

        if (!restaurant.getDescription().equals("null")) binding.RestaurantDetailsTvDescription.setText(restaurant.getDescription());
        if (!restaurant.getPhone().equals("null")) binding.RestaurantDetailsTvPhone.setText(restaurant.getPhone());
        if (!restaurant.getWebsite().equals("null")) binding.RestaurantDetailsTvWebsite.setText(restaurant.getWebsite());
    }
}